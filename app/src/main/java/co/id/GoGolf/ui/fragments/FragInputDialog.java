package co.id.GoGolf.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.VerifyEvent;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomEditText;
import co.id.GoGolf.ui.UIUtils;
import co.id.GoGolf.ui.activities.BaseAct;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/18/16.
 */

public class FragInputDialog extends DialogFragment {

    private Context context;
    private View view;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.ivCloseInput)
    ImageView ivCloseInput;

    @InjectView(R.id.etCodeInput)
    CustomEditText etCodeInput;

    @InjectView(R.id.btVerifyInput)
    CustomButton btVerifyInput;

    public FragInputDialog() {

    }

    @SuppressLint("ValidFragment")
    public FragInputDialog(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.97);
        windowParams.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.4);
        window.setAttributes(windowParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.dialog_input, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);
    }

    @OnClick(R.id.btVerifyInput)
    public void onVerifyPromotionCode() {
        if (UIUtils.validatePromotionCode(this.context, etCodeInput.getText().toString())) {
            ((BaseAct)context).showLoadingDialog();
            mainPresenter.postVerify(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), etCodeInput.getText().toString(), "en");
        }
    }

    @OnClick(R.id.ivCloseInput)
    public void onCloseInputDialog() {
        dismiss();
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        ((BaseAct) context).dismissLoadingDialog();
        Toast.makeText(context, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(VerifyEvent event) {
        ((BaseAct) context).dismissLoadingDialog();
        dismiss();
    }

}
