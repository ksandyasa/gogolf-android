package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
 * Created by apridosandyasa on 10/13/16.
 */

public class ActPhoneDetail extends BaseAct {

    private User user;
    private Context context;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.svContainerPhoneDetail)
    ScrollView svContainerPhoneDetail;

    @InjectView(R.id.tvPhoneDetail)
    CustomTextView tvPhoneDetail;

    @InjectView(R.id.etCodeDetail)
    CustomEditText etCodeDetail;

    @InjectView(R.id.etPhoneDetail)
    CustomEditText etPhoneDetail;

    @InjectView(R.id.btnSubmitPhone)
    CustomButton btnSubmitPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_phone_detail);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        context = getApplicationContext();
        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);

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
        etPhoneDetail.setText(user.getPhone());
        etPhoneDetail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerPhoneDetail.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerPhoneDetail.scrollTo(0, etPhoneDetail.getBottom());
                        }
                    }, 250);
                }
            }
        });
    }

    @OnClick(R.id.btnSubmitPhone)
    public void onPhoneUpdate() {
        showLoadingDialog();
        mainPresenter.postProfileUpdate1(PreferenceUtility.getInstance().loadDataString(context,
                PreferenceUtility.ACCESS_TOKEN),
                user.getFname(),
                user.getLname(),
                etPhoneDetail.getText().toString(),
                user.getGender(),
                user.getBirthdate(),
                "en",//(user.getCountry_id().equals("0")) ? "en" : (user.getCountry_id().equals("1")) ? "id" : "jp",
                etCodeDetail.getText().toString().replace("+", ""));
//        if (UIUtils.validatePhoneNumber(ActPhoneDetail.this, etPhoneDetail)) {
//            showLoadingDialog();
//            mainPresenter.postProfileUpdate1(PreferenceUtility.getInstance().loadDataString(context,
//                    PreferenceUtility.ACCESS_TOKEN),
//                    user.getFname(),
//                    user.getLname(),
//                    etPhoneDetail.getText().toString(),
//                    user.getGender(),
//                    user.getBirthdate(),
//                    "en",//(user.getCountry_id().equals("0")) ? "en" : (user.getCountry_id().equals("1")) ? "id" : "jp",
//                    etCodeDetail.getText().toString().replace("+", ""));
//        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        getWindow().getDecorView().clearFocus();
        HideSoftKeyboardForActivity(this);
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(ProfileUpdateEvent event) {
        dismissLoadingDialog();
        getWindow().getDecorView().clearFocus();
        HideSoftKeyboardForActivity(this);
        user.setPhone(etPhoneDetail.getText().toString());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_USER_DATA, new Gson().toJson(user));
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
        ActPhoneDetail.this.finish();
    }

    public void HideSoftKeyboardForActivity(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
