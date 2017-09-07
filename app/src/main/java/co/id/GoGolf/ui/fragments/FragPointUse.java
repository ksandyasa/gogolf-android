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
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.models.response.point.DataPoint;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.PointUseCallback;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/23/16.
 */

public class FragPointUse extends DialogFragment {

    private Context context;
    private View view;
    private User user;
    private DataPoint dataPoint;
    private String totalPrice;
    private PointUseCallback callback;
    private String priceFormatted1 = "";
    private String priceFormatted2 = "";
    private String finalPriceFormatted = "";

    @InjectView(R.id.ivClosePointUse)
    ImageView ivClosePointUse;

    @InjectView(R.id.tvCurPoint)
    CustomTextView tvCurPoint;

    @InjectView(R.id.tvNecPoint)
    CustomTextView tvNecPoint;

    @InjectView(R.id.tvDiscount)
    CustomTextView tvDiscount;

    @InjectView(R.id.tvFirstPrice)
    CustomTextView tvFirstPrice;

    @InjectView(R.id.tvLastPrice)
    CustomTextView tvLastPrice;

    @InjectView(R.id.btnYesPointUse)
    CustomButton btnYesPointUse;

    @InjectView(R.id.btnNoPointUse)
    CustomButton btnNoPointUse;

    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    public FragPointUse() {

    }

    @SuppressLint("ValidFragment")
    public FragPointUse(Context context, User user, DataPoint dataPoint, String totalPrice, PointUseCallback listener) {
        this.context = context;
        this.user = user;
        this.dataPoint = dataPoint;
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
        windowParams.height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.6);
        window.setAttributes(windowParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.dialog_point_use, container, false);

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

        tvCurPoint.setText(user.getPoint()+" point");
        tvNecPoint.setText(dataPoint.getSpend_case().getSpend_point()+" point");
        tvDiscount.setText(dataPoint.getSpend_case().getDisc_rate() + "%");

        priceFormatted1 = new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(totalPrice));

        priceFormatted2 = new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(dataPoint.getSpend_case().getFinal_price()));

        tvFirstPrice.setText("Rp. " + priceFormatted1);
        tvLastPrice.setText("Rp. " + priceFormatted2);

        finalPriceFormatted = priceFormatted2;
    }

    @OnClick(R.id.ivClosePointUse)
    public void onDismissPointUse() {
        dismiss();
    }

    @OnClick(R.id.btnYesPointUse)
    public void onSubmitPointUse() {
        callback.SubmitPointUse(finalPriceFormatted);
        dismiss();
    }

    @OnClick(R.id.btnNoPointUse)
    public void onCancelPointUse() {
        callback.CancelPointUse();
        dismiss();
    }

}
