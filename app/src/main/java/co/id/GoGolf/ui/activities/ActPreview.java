package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import co.id.GoGolf.Config;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.BookingEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.models.request.BookReq;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.models.response.point.DataPoint;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.callback.PointUseCallback;
import co.id.GoGolf.ui.callback.StripeCallback;
import co.id.GoGolf.ui.fragments.FragPointUse;
import co.id.GoGolf.ui.fragments.FragStripes;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

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
public class ActPreview extends BaseAct implements PointUseCallback, StripeCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.tvName)
    CustomTextView tvName;

    @InjectView(R.id.tvDate)
    CustomTextView tvDate;

    @InjectView(R.id.tvFlight)
    CustomTextView tvFlight;

    @InjectView(R.id.tvTotalPrice)
    CustomTextView tvTotalPrice;

    @InjectView(R.id.tvTotalOldPrice)
    CustomTextView tvTotalOldPrice;

    @InjectView(R.id.tvCancel)
    CustomTextView tvCancel;

    @InjectView(R.id.tvActPoint)
    CustomTextView tvActPoint;

    @InjectView(R.id.tvActDeposit)
    CustomTextView tvActDeposit;

    @InjectView(R.id.tvBookingCode)
    CustomTextView tvBookingCode;

    @InjectView(R.id.llBcode)
    LinearLayout llBcode;

    @InjectView(R.id.borderBcode)
    View borderBcode;

    @InjectView(R.id.btBookNow)
    CustomButton btBookNow;

    @InjectView(R.id.llForm)
    LinearLayout llForm;

    @InjectView(R.id.llAcquisition)
    LinearLayout llAcquisition;

    @InjectView(R.id.llPopbox)
    LinearLayout llPopbox;

    @InjectView(R.id.llDeposit)
    LinearLayout llDeposit;

    private Context context;
    private DataPoint dataPoint;
    private String usePoint = "0";
    private User user;
    private FragPointUse fragPointUse;
    private FragStripes fragStripes;

    private String gname, gdate, gid, totalPrice, bcode, bid, cancelLimit;
    private BookReq bookReq;
    int priceDp = 0;
    int deposite_rate = 0;
    double totprice, drate;
    private boolean fromHistory = false, fromStatus = false;
    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_preview);

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.SF_USER_DATA), User.class);

        cancelLimit = getIntent().getStringExtra("cancel");
        gname = getIntent().getStringExtra("gname");
        deposite_rate = getIntent().getIntExtra("deposite_rate", 0);

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        gid = getIntent().getStringExtra("gid");
        gdate = getIntent().getStringExtra("gdate");
        totalPrice = getIntent().getStringExtra("sTotal");
        bcode = getIntent().getStringExtra("bcode");
        dataPoint = (DataPoint) getIntent().getSerializableExtra("data_point");
        fromHistory = getIntent().getBooleanExtra("fromHistory", false);
        fromStatus = getIntent().getBooleanExtra("fromStatus", false);

        bookReq = (BookReq) getIntent().getSerializableExtra("bookreq");

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);
        context = getApplicationContext();

        initToolbar("Booking Confirm", null);

        llPopbox.setVisibility(View.VISIBLE);
        llAcquisition.setVisibility(View.VISIBLE);
        String text = String.format(getResources().getString(R.string.cancel), cancelLimit, cancelLimit);
        tvCancel.setText(text);
        tvName.setText(gname);
        tvDate.setText(gdate);
        tvFlight.setText("" + bookReq.getFlightarr().size());

        for (int i = 0; i < bookReq.getFlightarr().size(); i++) {
            View orderForm = getLayoutInflater().inflate(R.layout.preview_form, null);

            CustomTextView tvFlight = (CustomTextView) orderForm.findViewById(R.id.tvFlight);
            CustomTextView tvTeeTime = (CustomTextView) orderForm.findViewById(R.id.tvTeeTime);
            CustomTextView tvPrice = (CustomTextView) orderForm.findViewById(R.id.tvPrice);
            CustomTextView tvPlayerType = (CustomTextView) orderForm.findViewById(R.id.tvPlayerType);
            CustomTextView tvPlayerNumber = (CustomTextView) orderForm.findViewById(R.id.tvPlayerNumber);
            CustomTextView tvCart = (CustomTextView) orderForm.findViewById(R.id.tvCart);

            tvFlight.setText("Flight " + (i + 1));
            tvTeeTime.setText(bookReq.getFlightarr().get(i).getTtime());
            tvPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.valueOf(bookReq.getFlightarr().get(i).getTotalPrice())));
            tvPlayerNumber.setText(bookReq.getFlightarr().get(i).getPlayer());
            tvCart.setText((bookReq.getFlightarr().get(i).getCart().equals("1")) ? "Yes" : "No");

            String builder = "";

            for (int x = 0; x < bookReq.getFlightarr().get(i).getPlayerarr().size(); x++) {
                builder = builder + "- " + bookReq.getFlightarr().get(i).getPlayerarr().get(x).getType() +
                          " Rp. " + new DecimalFormat(formatString, otherSymbols).format(bookReq.getFlightarr().get(i).getPlayerarr().get(x).getIntPrice()) + "\n";
            }
            tvPlayerType.setText(builder);

            llForm.addView(orderForm);
        }

        if (dataPoint.getSpend_case() != null) {
            showPointDialog(dataPoint);
        }

        if (fromHistory) {
            llBcode.setVisibility(View.VISIBLE);
            borderBcode.setVisibility(View.VISIBLE);
            tvBookingCode.setText(bcode);
            btBookNow.setText("Cancel Booking");
            if (fromStatus) {
                btBookNow.setVisibility(View.VISIBLE);
            } else {
                btBookNow.setVisibility(View.GONE);
            }
        } else {
            llBcode.setVisibility(View.GONE);
            borderBcode.setVisibility(View.GONE);
            btBookNow.setText("BOOK");
        }

        llDeposit.setVisibility(View.VISIBLE);

        totprice = Double.valueOf(totalPrice);
        drate = (double) deposite_rate;

        priceDp = (int) (totprice * (drate/100));

        tvActDeposit.setText("Rp. "+ new DecimalFormat(formatString, otherSymbols).format(priceDp) +" ("+deposite_rate+"%)");
        tvActPoint.setText(dataPoint.getGet_case().getReward_point() + " Point");

        int paymentPrice = Integer.parseInt(totalPrice) - priceDp;
        tvTotalPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(paymentPrice));
        tvTotalOldPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(Integer.parseInt(totalPrice)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Config.PREBOOKING_SUCCESS && data != null) {
            if (data.getStringExtra("success_paid").equals("success_paid")) {
                setResult(RESULT_OK, data);
                ActPreview.this.finish();
            }
        }
    }

    @OnClick(R.id.btBookNow)
    public void onBookNow() {
        if (fromHistory) {
            finish();
        } else {
            showLoadingDialog();
            mainPresenter.postBooking(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), bookReq, String.valueOf(priceDp));
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
        Toast.makeText(getApplicationContext(), event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void showPointDialog(final DataPoint dataPoint) {
        fragPointUse = new FragPointUse(ActPreview.this, user, dataPoint, totalPrice, this);
        fragPointUse.show(getSupportFragmentManager(), "fragPointUse");
//        LayoutInflater li = getLayoutInflater();
//        View promptsView = li.inflate(R.layout.dialog_point_use, null);
//
//        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                ActPreview.this);
//
//        alertDialogBuilder.setView(promptsView);
//
//        final TextView tvCurPoint = (TextView) promptsView
//                .findViewById(R.id.tvCurPoint);
//        final TextView tvNecPoint = (TextView) promptsView
//                .findViewById(R.id.tvNecPoint);
//        final TextView tvDiscount = (TextView) promptsView
//                .findViewById(R.id.tvDiscount);
//        final TextView tvFirstPrice = (TextView) promptsView
//                .findViewById(R.id.tvFirstPrice);
//        final TextView tvLastPrice = (TextView) promptsView
//                .findViewById(R.id.tvLastPrice);
//
//        tvCurPoint.setText(user.getPoint()+" point");
//        tvNecPoint.setText(dataPoint.getSpend_case().getSpend_point()+" point");
//        tvDiscount.setText(dataPoint.getSpend_case().getDisc_rate() + "%");
//
//        String priceFormatted1 = "";
//        priceFormatted1 = priceFormatted1.format("%,d", Integer.valueOf(totalPrice));
//
//
//        String priceFormatted2 = "";
//        priceFormatted2 = priceFormatted2.format("%,d", Integer.valueOf(dataPoint.getSpend_case().getFinal_price()));
//
//        tvFirstPrice.setText("Rp. " + priceFormatted1);
//        tvLastPrice.setText("Rp. " + priceFormatted2);
//
//        final String finalPriceFormatted = priceFormatted2;
//
//        alertDialogBuilder
//                .setCancelable(false)
//                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        usePoint = "0";
//                        bookReq.setUse_point(usePoint);
//                    }
//                })
//                .setPositiveButton("YES",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.dismiss();
//                                usePoint = "1";
//                                llAcquisition.setVisibility(View.VISIBLE);
//                                tvActPoint.setText(dataPoint.getPoint_availability() + " Point");
//                                priceDp = (int) (Double.valueOf(dataPoint.getSpend_case().getFinal_price()) * (drate/100));
//                                tvActDeposit.setText("Rp. "+priceDp+" ("+deposite_rate+"%)");
//                                tvTotalPrice.setText("Rp. " + finalPriceFormatted);
//                                bookReq.setUse_point(usePoint);
//                            }
//                        });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//
//        alertDialog.show();
    }

    @Subscribe
    public void onEventThread(BookingEvent event) {
        dismissLoadingDialog();
        bcode = event.getData().getBcode();
        bid = event.getData().getBid();
//        fragStripes = new FragStripes(ActPreview.this, priceDp, bcode, bid, this);
//        fragStripes.show(getSupportFragmentManager(), "fragStripes");
        startActivityForResult(new Intent(getApplicationContext(), ActPreBookingSuccess.class), Config.PREBOOKING_SUCCESS);
    }

    @Override
    public void SubmitPointUse(String finalPriceFormatted) {
        usePoint = "1";
        int payment = Integer.parseInt(finalPriceFormatted.replace(".", ""));
        tvActPoint.setText("0 Point");
        priceDp = (int) (Double.valueOf(dataPoint.getSpend_case().getFinal_price()) * (drate/100));
        tvActDeposit.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(priceDp) + " (" + deposite_rate + "%)");
        tvTotalPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(payment));
        bookReq.setUse_point(usePoint);
    }

    @Override
    public void CancelPointUse() {
        usePoint = "0";
        bookReq.setUse_point(usePoint);
    }

    @Override
    public void ShowSuccessfulPaymentView() {
        startActivityForResult(new Intent(getApplicationContext(), ActSuccessPaid.class).putExtra("totalprice", priceDp).putExtra("bcode", bcode), 25);
    }
}
