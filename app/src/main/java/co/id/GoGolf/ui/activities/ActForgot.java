package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.ForgotPasswordEvent;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.UIUtils;
import co.id.GoGolf.ui.presenters.OAuthPresenter;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by prumacadmin on 9/27/16.
 */
public class ActForgot extends BaseAct {

    private Context context;

    @Inject
    OAuthPresenter oAuthPresenter;

    @InjectView(R.id.svContainerForgot)
    ScrollView svContainerForgot;

    @InjectView(R.id.etEmailForgot)
    CustomEditText etEmailForgot;

    @InjectView(R.id.btnSendForgot)
    CustomButton btnSendForgot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_forgot);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        context = getApplicationContext();

        initToolbar(getResources().getString(R.string.fg_nav), null);

        etEmailForgot.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    svContainerForgot.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            svContainerForgot.scrollTo(0, svContainerForgot.getBottom());
                        }
                    }, 250);
                }
            }
        });
        etEmailForgot.setOnEditorActionListener(new SendForgotPasswordFromInput());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            getWindow().getDecorView().clearFocus();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.btnSendForgot)
    public void onSendPassword() {
        boolean isValidate = UIUtils.validateEmail(ActForgot.this, etEmailForgot);
        if (isValidate) {
            showLoadingDialog();
            oAuthPresenter.postForgotPassword(etEmailForgot.getText().toString());
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(ForgotPasswordEvent event) {
        dismissLoadingDialog();
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etEmailForgot.getWindowToken(), 0);
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
        etEmailForgot.setText("");
    }

    private class SendForgotPasswordFromInput implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_GO) {
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                boolean isValidate = UIUtils.validateEmail(ActForgot.this, etEmailForgot);
//                if (isValidate) {
//                    showLoadingDialog();
//                    oAuthPresenter.postForgotPassword(etEmailForgot.getText().toString());
//                }
                showLoadingDialog();
                oAuthPresenter.postForgotPassword(etEmailForgot.getText().toString());
                getWindow().getDecorView().clearFocus();

            }

            return false;
        }
    }

}
