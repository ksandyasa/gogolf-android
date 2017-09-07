package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.BaseEvent;
import co.id.GoGolf.events.BookingEvent;
import co.id.GoGolf.events.CancelEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.StripeEvent;
import co.id.GoGolf.models.response.history.DataHistory;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.CancelBookingCallback;
import co.id.GoGolf.ui.callback.StripeCallback;
import co.id.GoGolf.ui.fragments.FragCancelBooking;
import co.id.GoGolf.ui.fragments.FragStripes;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dedepradana on 6/19/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class ActBookingDetail extends BaseAct implements CancelBookingCallback, StripeCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.tvName)
    CustomTextView tvName;

    @InjectView(R.id.tvDate)
    CustomTextView tvDate;

    @InjectView(R.id.tvFlight)
    TextView tvFlight;

    @InjectView(R.id.tvTotalPrice)
    CustomTextView tvTotalPrice;

    @InjectView(R.id.tvTotalOldPrice)
    CustomTextView tvTotalOldPrice;

    @InjectView(R.id.tvActDeposit)
    CustomTextView tvActDeposit;

    @InjectView(R.id.tvActPoint)
    CustomTextView tvActPoint;

    @InjectView(R.id.tvBookingCode)
    CustomTextView tvBookingCode;

    @InjectView(R.id.llBcode)
    LinearLayout llBcode;

    @InjectView(R.id.btBookNow)
    CustomButton btBookNow;

    @InjectView(R.id.btBookPayNow)
    CustomButton btBookPayNow;

    @InjectView(R.id.llForm)
    LinearLayout llForm;

    @InjectView(R.id.llAcquisition)
    LinearLayout llAcquisition;

    @InjectView(R.id.llPopbox)
    LinearLayout llPopbox;

    @InjectView(R.id.llDeposit)
    LinearLayout llDeposit;

    private Context context;
    private DataHistory dataHistory;
    private boolean fromHis = false;
    private FragCancelBooking fragCancelBooking;
    private FragStripes fragStripes;
    private String formatString = "#,###,###,###.##";
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_book_detail);

        dataHistory = (DataHistory) getIntent().getSerializableExtra("data");
        fromHis = getIntent().getBooleanExtra("fromHis", false);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);
        context = getApplicationContext();

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        initToolbar("Booking Detail", null);

        llDeposit.setVisibility(View.VISIBLE);
        llAcquisition.setVisibility(View.VISIBLE);

        tvName.setText(dataHistory.getGname());
        tvDate.setText(dataHistory.getDate());

        int payment = Integer.parseInt(dataHistory.getTprice()) - Integer.parseInt(dataHistory.getDeposit_price());

        tvTotalPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(payment));//String.format("%.s", Double.valueOf(dataHistory.getTprice())));
        tvTotalOldPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.parseInt(dataHistory.getTprice())));//String.format("%.s", Double.valueOf(dataHistory.getTprice())));
        tvActDeposit.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.parseInt(dataHistory.getDeposit_price())));
        tvActPoint.setText(dataHistory.getReward_point() + " Point");
        tvFlight.setText("" + dataHistory.getFlightarr().size());

        for (int i = 0; i < dataHistory.getFlightarr().size(); i++) {
            View orderForm = getLayoutInflater().inflate(R.layout.preview_form, null);

            CustomTextView tvFlight = (CustomTextView) orderForm.findViewById(R.id.tvFlight);
            CustomTextView tvTeeTime = (CustomTextView) orderForm.findViewById(R.id.tvTeeTime);
            CustomTextView tvPrice = (CustomTextView) orderForm.findViewById(R.id.tvPrice);
            CustomTextView tvPlayerType = (CustomTextView) orderForm.findViewById(R.id.tvPlayerType);
            CustomTextView tvPlayerNumber = (CustomTextView) orderForm.findViewById(R.id.tvPlayerNumber);
            CustomTextView tvCart = (CustomTextView) orderForm.findViewById(R.id.tvCart);

            tvFlight.setText("Flight " + (i + 1));
            tvTeeTime.setText(dataHistory.getFlightarr().get(i).getTtime());
            if (dataHistory.getFlightarr().get(i).getTprice() != null)
                tvPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.parseInt(dataHistory.getFlightarr().get(i).getTprice())));//String.format("%.s", Double.valueOf(dataHistory.getFlightarr().get(i).getPrice())));
            else
                tvPrice.setText("Rp. -");
            tvPlayerNumber.setText(dataHistory.getFlightarr().get(i).getNumber());
            tvCart.setText((dataHistory.getFlightarr().get(i).getCart().equals("1")) ? "Yes" : "No");

            String builder = "";

            for (int x = 0; x < dataHistory.getFlightarr().get(i).getPlayerarr().size(); x++) {
                builder = builder + "- " + dataHistory.getFlightarr().get(i).getPlayerarr().get(x).getType() +
                        " Rp. " + new DecimalFormat(formatString, otherSymbols).format(dataHistory.getFlightarr().get(i).getPlayerarr().get(x).getIntPrice()) + "\n";
            }
            tvPlayerType.setText(builder);

            llForm.addView(orderForm);
        }

        llBcode.setVisibility(View.VISIBLE);
        tvBookingCode.setText(dataHistory.getBcode());
        if (fromHis){
            btBookNow.setVisibility(View.GONE);
        }else {
            btBookNow.setVisibility(View.VISIBLE);
            if (dataHistory.getStatus().equals("Unpaid"))
                btBookPayNow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 24 && data != null) {
            if (data.getStringExtra("success_paid").equals("success_paid")) {
                setResult(RESULT_OK, data);
                ActBookingDetail.this.finish();
            }
        }
    }

    @OnClick(R.id.btBookNow)
    public void onBookNow() {
        fragCancelBooking = new FragCancelBooking(ActBookingDetail.this, dataHistory, this);
        fragCancelBooking.show(getSupportFragmentManager(), "fragCancelBooking");
    }

    @OnClick(R.id.btBookPayNow)
    public void onBookPayNow() {
//        fragStripes = new FragStripes(ActBookingDetail.this, Integer.parseInt(dataHistory.getDeposit_price()), dataHistory.getBcode(), dataHistory.getBid(), this);
//        fragStripes.show(getSupportFragmentManager(), "fragStripes");
        startActivityForResult(new Intent(getApplicationContext(), ActPayment.class).putExtra("price", Integer.valueOf(dataHistory.getDeposit_price())).putExtra("bcode", dataHistory.getBcode()).putExtra("bid", dataHistory.getBid()), 24);

//        showLoadingDialog();
//        BookReq bookReq = new BookReq();
//        bookReq.setDate(dataHistory.getDate());
//        bookReq.setGid(dataHistory.getGid());
//        bookReq.setUse_point(dataHistory.getReward_point());
//        bookReq.setLang("en");
//        ArrayList<FlightarrNew> flightarrNewArrayList = new ArrayList<>();
//        for (int i = 0; i < dataHistory.getFlightarr().size(); i++) {
//            FlightarrNew flightarrNew = new FlightarrNew();
//            flightarrNew.setCart(dataHistory.getFlightarr().get(i).getCart());
//            flightarrNew.setPlayer(dataHistory.getFlightarr().get(i).getNumber());
//            flightarrNew.setTtime(dataHistory.getFlightarr().get(i).getTtime());
//            flightarrNew.setPlayerarr(dataHistory.getFlightarr().get(i).getPlayerarr());
//            flightarrNewArrayList.add(flightarrNew);
//        }
//        bookReq.setFlightarr(flightarrNewArrayList);
//        mainPresenter.postBooking(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), bookReq, String.valueOf(dataHistory.getTprice()));
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(BookingEvent event) {
        dismissLoadingDialog();
        //startActivityForResult(new Intent(getApplicationContext(), ActCreditCard.class).putExtra("price", dataHistory.getTprice()).putExtra("bcode", event.getData().getBcode()).putExtra("bid", event.getData().getBid()), 24);
    }

    @Subscribe
    public void onEventThread(CancelEvent event) {
        dismissLoadingDialog();
        ShowCancelBookingAlert(event.getMessage());
    }

    @Override
    public void ConfirmCancelBooking() {
        showLoadingDialog();
        mainPresenter.postCancelBooking(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), dataHistory.getBid(), "en");
    }

    private void ShowCancelBookingAlert(String message) {
        this.alertDialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent refreshIntent = new Intent();
                        refreshIntent.putExtra("page_refresh", "refresh");
                        setResult(RESULT_OK, refreshIntent);
                        ActBookingDetail.this.finish();
                    }
                })
                .setIcon(R.mipmap.ic_launcher);
        this.alertDialog = this.alertDialogBuilder.create();
        this.alertDialog.show();

    }

    @Override
    public void ShowSuccessfulPaymentView() {
        fragStripes.dismiss();
        startActivityForResult(new Intent(getApplicationContext(), ActSuccessPaid.class).putExtra("totalprice", Integer.valueOf(dataHistory.getDeposit_price())).putExtra("bcode", dataHistory.getBcode()), 25);
    }
}
