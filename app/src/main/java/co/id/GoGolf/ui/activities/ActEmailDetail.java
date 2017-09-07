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
import co.id.GoGolf.events.EmailUpdateEvent;
import co.id.GoGolf.events.ErrorEvent;
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

public class ActEmailDetail extends BaseAct {

    private User user;
    private Context context;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.svContainerEmailDetail)
    ScrollView svContainerEmailDetail;

    @InjectView(R.id.tvEmailAddr)
    CustomTextView tvEmailAddr;

    @InjectView(R.id.etEmailAddr)
    CustomEditText etEmailAddr;

    @InjectView(R.id.btnSubmitEmail)
    CustomButton btnSubmitEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_email_detail);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        context = getApplicationContext();
        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);

        initToolbar(getResources().getString(R.string.pf_nav), null);

        etEmailAddr.setText(user.getEmail());
        etEmailAddr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerEmailDetail.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerEmailDetail.scrollTo(0, etEmailAddr.getBottom());
                        }
                    }, 250);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            getWindow().getDecorView().clearFocus();
            HideSoftKeyboardForActivity(this);
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.btnSubmitEmail)
    public void onEmailUpdate() {
//        boolean isValidate = UIUtils.validateEmail(ActEmailDetail.this, etEmailAddr);
//        if (isValidate) {
//            showLoadingDialog();
//            mainPresenter.postEmailUpdate(PreferenceUtility.getInstance().loadDataString(context,
//                    PreferenceUtility.ACCESS_TOKEN),
//                    etEmailAddr.getText().toString());
//        }
        showLoadingDialog();
        mainPresenter.postEmailUpdate(PreferenceUtility.getInstance().loadDataString(context,
                PreferenceUtility.ACCESS_TOKEN),
                etEmailAddr.getText().toString());
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        getWindow().getDecorView().clearFocus();
        HideSoftKeyboardForActivity(this);
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(EmailUpdateEvent event) {
        dismissLoadingDialog();
        getWindow().getDecorView().clearFocus();
        HideSoftKeyboardForActivity(this);
        user.setEmail(etEmailAddr.getText().toString());
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.SF_USER_DATA, new Gson().toJson(user));
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
        ActEmailDetail.this.finish();
    }

    public void HideSoftKeyboardForActivity(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
