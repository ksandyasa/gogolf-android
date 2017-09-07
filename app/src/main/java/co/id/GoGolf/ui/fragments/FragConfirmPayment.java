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
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.ConfirmPaymentCallback;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/23/16.
 */

public class FragConfirmPayment extends DialogFragment {

    private Context context;
    private View view;
    private String cardNumber;
    private String totalPrice;
    private ConfirmPaymentCallback callback;

    @InjectView(R.id.ivCloseConfirmPayment)
    ImageView ivCloseConfirmPayment;

    @InjectView(R.id.tvCreditCard)
    CustomTextView tvCreditCard;

    @InjectView(R.id.tvPriceAmount)
    CustomTextView tvPriceAmount;

    @InjectView(R.id.btnConfirmPayment)
    CustomButton btnConfirmPayment;

    @InjectView(R.id.btnCancelPayment)
    CustomButton btnCancelPayment;

    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    public FragConfirmPayment() {

    }

    @SuppressLint("ValidFragment")
    public FragConfirmPayment(Context context, String cardNumber, String totalPrice, ConfirmPaymentCallback listener) {
        this.context = context;
        this.cardNumber = cardNumber;
        this.totalPrice = totalPrice;
        this.callback = listener;
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
        windowParams.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.5);
        window.setAttributes(windowParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.dialog_confirm_payment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        tvCreditCard.setText(cardNumber);
        tvPriceAmount.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(totalPrice)));
    }

    @OnClick(R.id.ivCloseConfirmPayment)
    public void onDismissConfirmPayment() {
        dismiss();
    }

    @OnClick(R.id.btnConfirmPayment)
    public void onConfirmPayment() {
        callback.ConfirmPayment();
        dismiss();
    }

    @OnClick(R.id.btnCancelPayment)
    public void onCancelPayment() {
        callback.CancelPayment();
        dismiss();
    }

}
