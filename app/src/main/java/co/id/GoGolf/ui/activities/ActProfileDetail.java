package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.ProfileUpdateEvent;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.UIUtils;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by prumacadmin on 9/2/16.
 */
public class ActProfileDetail extends BaseAct {

    private String TAG = ActProfileDetail.class.getSimpleName();
    private User user;
    private Context context;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.svContainerProfDetail)
    ScrollView svContainerProfDetail;

    @InjectView(R.id.tvFirstName)
    CustomTextView tvFirstName;

    @InjectView(R.id.etFirstName)
    CustomEditText etFirstName;

    @InjectView(R.id.tvLastName)
    CustomTextView tvLastName;

    @InjectView(R.id.etLastName)
    CustomEditText etLastName;

    @InjectView(R.id.btnSubmitName)
    CustomButton btnSubmitName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profile_detail);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        context = getApplicationContext();
        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);
        Log.d(TAG, user.toString());

        initToolbar(getResources().getString(R.string.pf_nav), null);

        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            getWindow().getDecorView().clearFocus();
            HideSoftKeyboardForActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        etFirstName.setText(user.getFname());
        etLastName.setText(user.getLname());
        etLastName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerProfDetail.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerProfDetail.scrollTo(0, etLastName.getBottom());
                        }
                    }, 250);
                }
            }
        });
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        getWindow().getDecorView().clearFocus();
        HideSoftKeyboardForActivity(this);
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnSubmitName)
    public void onNameUpdate() {
        showLoadingDialog();
        mainPresenter.postProfileUpdate(PreferenceUtility.getInstance().loadDataString(context,
                PreferenceUtility.ACCESS_TOKEN),
                etFirstName.getText().toString(),
                etLastName.getText().toString(),
                user.getPhone(),
                user.getGender(),
                user.getBirthdate(),
                "en");//(user.getCountry_id().equals("0")) ? "en" : (user.getCountry_id().equals("1")) ? "id" : "jp");
//        if (UIUtils.validateFirstName(ActProfileDetail.this, etFirstName)) {
//            if (UIUtils.validateLastName(ActProfileDetail.this, etLastName)) {
//                showLoadingDialog();
//                mainPresenter.postProfileUpdate(PreferenceUtility.getInstance().loadDataString(context,
//                        PreferenceUtility.ACCESS_TOKEN),
//                        etFirstName.getText().toString(),
//                        etLastName.getText().toString(),
//                        user.getPhone(),
//                        user.getGender(),
//                        user.getBirthdate(),
//                        "en");//(user.getCountry_id().equals("0")) ? "en" : (user.getCountry_id().equals("1")) ? "id" : "jp");
//            }
//        }
    }

    @Subscribe
    public void onEventThread(ProfileUpdateEvent event) {
        dismissLoadingDialog();
        getWindow().getDecorView().clearFocus();
        HideSoftKeyboardForActivity(this);
        user.setFname(etFirstName.getText().toString());
        user.setLname(etLastName.getText().toString());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_USER_DATA, new Gson().toJson(user));
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
        ActProfileDetail.this.finish();
    }

    public void HideSoftKeyboardForActivity(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
