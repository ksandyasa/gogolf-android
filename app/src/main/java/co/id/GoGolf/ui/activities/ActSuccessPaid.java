package co.id.GoGolf.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import co.id.GoGolf.R;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.ui.CustomTextView;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by prumacadmin on 8/29/16.
 */
public class ActSuccessPaid extends BaseAct {

    @InjectView(R.id.tvTotalPrice)
    CustomTextView tvTotalPrice;

    @InjectView(R.id.tvDateTime)
    CustomTextView tvDateTime;

    @InjectView(R.id.tvBookingCode)
    CustomTextView tvBookingCode;

    private String formatString = "#,###,###,###.##";
    private DecimalFormatSymbols otherSymbols;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_success_paid);

        ButterKnife.inject(this);

        initToolbarWithoutHomeButton("Payment Successful", null);

        otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        String formattedDate = df.format(c.getTime());

        tvTotalPrice.setText("Rp. " + new DecimalFormat(formatString, otherSymbols).format(getIntent().getIntExtra("totalprice", 0)));
        tvBookingCode.setText(getIntent().getStringExtra("bcode"));
        tvDateTime.setText(formattedDate);
    }

    @OnClick(R.id.btnFinish)
    public void onFinish(){
        Intent successIntent = new Intent();
        successIntent.putExtra("success_paid", "success_paid");
        setResult(RESULT_OK, successIntent);
        ActSuccessPaid.this.finish();
    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
    }

}
