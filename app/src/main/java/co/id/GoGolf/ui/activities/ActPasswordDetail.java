package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ChangePasswordEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.UIUtils;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/13/16.
 */

public class ActPasswordDetail extends BaseAct {

    private String currentPassword = "";
    private Context context;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.svContainerPassDetail)
    ScrollView svContainerPassDetail;

    @InjectView(R.id.tvOldPass)
    CustomTextView tvOldPass;

    @InjectView(R.id.etOldPass)
    CustomEditText etOldPass;

    @InjectView(R.id.tvNewPass)
    CustomTextView tvNewPass;

    @InjectView(R.id.etNewPass)
    CustomEditText etNewPass;

    @InjectView(R.id.tvConfPass)
    CustomTextView tvConfPass;

    @InjectView(R.id.etConfPass)
    CustomEditText etConfPass;

    @InjectView(R.id.rvBottomPassDetail)
    RelativeLayout rvBottomPassDetail;

    @InjectView(R.id.btnSubmitPass)
    CustomButton btnSubmitPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_password_detail);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        context = getApplicationContext();
        currentPassword = PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.PASSWORD);

        initToolbar(getResources().getString(R.string.pf_nav), null);

//        etOldPass.setText(PreferenceUtility.getInstance().loadDataString(context,
//                PreferenceUtility.PASSWORD));
        etNewPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerPassDetail.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerPassDetail.scrollTo(0, rvBottomPassDetail.getBottom());
                        }
                    }, 250);
                }
            }
        });
        etConfPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerPassDetail.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerPassDetail.scrollTo(0, rvBottomPassDetail.getBottom());
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

    @OnClick(R.id.btnSubmitPass)
    public void onPasswordUpdate() {
        showLoadingDialog();
        mainPresenter.postChangePassword(PreferenceUtility.getInstance().loadDataString(context,
                PreferenceUtility.ACCESS_TOKEN),
                etOldPass.getText().toString(),
                etNewPass.getText().toString(),
                etConfPass.getText().toString());
//        if (UIUtils.validateInputPassword(ActPasswordDetail.this, etOldPass)) {
//            if (UIUtils.validateInputPassword(ActPasswordDetail.this, etNewPass)) {
//                if (UIUtils.validateConfirmPassword(ActPasswordDetail.this, etConfPass, etNewPass.getText().toString())) {
//                    showLoadingDialog();
//                    mainPresenter.postChangePassword(PreferenceUtility.getInstance().loadDataString(context,
//                            PreferenceUtility.ACCESS_TOKEN),
//                            etOldPass.getText().toString(),
//                            etNewPass.getText().toString(),
//                            etConfPass.getText().toString());
//                }
//            }
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
    public void onEventThread(ChangePasswordEvent event) {
        dismissLoadingDialog();
        getWindow().getDecorView().clearFocus();
        HideSoftKeyboardForActivity(this);
        PreferenceUtility.getInstance().saveData(context, PreferenceUtility.PASSWORD, etNewPass.getText().toString());
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
        ActPasswordDetail.this.finish();
    }

    public void HideSoftKeyboardForActivity(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
