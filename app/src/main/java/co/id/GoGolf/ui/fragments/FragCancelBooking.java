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
import co.id.GoGolf.models.response.history.DataHistory;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.CancelBookingCallback;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/24/16.
 */

public class FragCancelBooking extends DialogFragment {

    private Context context;
    private View view;
    private DataHistory dataHistory;
    private int countPlayer = 0;
    private CancelBookingCallback callback;

    @InjectView(R.id.ivCloseCancelBooking)
    ImageView ivCloseCancelBooking;

    @InjectView(R.id.tvDateCancelBooking)
    CustomTextView tvDateCancelBooking;

    @InjectView(R.id.tvCourseCancelBooking)
    CustomTextView tvCourseCancelBooking;

    @InjectView(R.id.tvFlightCancelBooking)
    CustomTextView tvFlightCancelBooking;

    @InjectView(R.id.tvPlayerCancelBooking)
    CustomTextView tvPlayerCancelBooking;

    @InjectView(R.id.tvTotPriceCancelBooking)
    CustomTextView tvTotPriceCancelBooking;

    @InjectView(R.id.btnYesCancelBooking)
    CustomButton btnYesCancelBooking;

    @InjectView(R.id.btnNoCancelBooking)
    CustomButton btnNoCancelBooking;

    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    public FragCancelBooking() {

    }

    @SuppressLint("ValidFragment")
    public FragCancelBooking(Context context, DataHistory dataHistory, CancelBookingCallback listener) {
        this.context = context;
        this.dataHistory = dataHistory;
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

        view = inflater.inflate(R.layout.dialog_cancel_booking, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        this.otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        this.otherSymbols.setDecimalSeparator(',');
        this.otherSymbols.setGroupingSeparator('.');

        tvDateCancelBooking.setText(dataHistory.getDate());
        tvCourseCancelBooking.setText(dataHistory.getGname());
        tvFlightCancelBooking.setText("" + dataHistory.getFlightarr().size());

        for (int i = 0; i < dataHistory.getFlightarr().size(); i++) {
            countPlayer = countPlayer + Integer.valueOf(dataHistory.getFlightarr().get(i).getNumber());
        }

        tvPlayerCancelBooking.setText("" + countPlayer);
        tvTotPriceCancelBooking.setText("Rp. " + new DecimalFormat(this.formatString, this.otherSymbols).format(Integer.valueOf(dataHistory.getTprice())));
    }

    @OnClick(R.id.ivCloseCancelBooking)
    public void onDismissCancelBooking() {
        dismiss();
    }

    @OnClick(R.id.btnYesCancelBooking)
    public void onConfirmCancelBooking() {
        callback.ConfirmCancelBooking();
        dismiss();
    }

    @OnClick(R.id.btnNoCancelBooking)
    public void onCancelBooking() {
        dismiss();
    }
}
