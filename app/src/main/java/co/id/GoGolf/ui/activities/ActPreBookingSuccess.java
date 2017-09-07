package co.id.GoGolf.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;

/**
 * Created by apridosandyasa on 12/27/16.
 */

public class ActPreBookingSuccess extends BaseAct {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_prebooking_success);

        ButterKnife.inject(this);

        initToolbarWithoutHomeButton("Prebooking Successful", null);
    }

    @OnClick(R.id.btnFinishPreBookingSuccess)
    public void onSuccessPreBooking() {
        Intent successIntent = new Intent();
        successIntent.putExtra("success_paid", "success_paid");
        setResult(RESULT_OK, successIntent);
        ActPreBookingSuccess.this.finish();
    }
}
