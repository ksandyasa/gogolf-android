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

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.ui.CustomTextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/18/16.
 */

public class FragSuccessDialog extends DialogFragment {

    private Context context;
    private View view;
    private String messageSuccess;
    @InjectView(R.id.ivCloseSuccess)
    ImageView ivCloseSuccess;
    @InjectView(R.id.tvMessageSuccess)
    CustomTextView tvMessageSuccess;

    public FragSuccessDialog() {

    }

    @SuppressLint("ValidFragment")
    public FragSuccessDialog(Context context, String msg) {
        this.context = context;
        this.messageSuccess = msg;
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

        tvMessageSuccess.setText(messageSuccess);
    }

    @OnClick(R.id.ivCloseSuccess)
    public void onCloseSuccess() {
        dismiss();
    }
}
