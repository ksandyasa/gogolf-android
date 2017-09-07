package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Switch;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.ui.CustomButton;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.presenters.MainPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by apridosandyasa on 10/13/16.
 */

public class ActPushNotifDetail extends BaseAct {

    private Context context;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.tvPushNotif)
    CustomTextView tvPushNotif;
    @InjectView(R.id.swPushNotif)
    Switch swPushNotif;
    @InjectView(R.id.btnSubmitPush)
    CustomButton btnSubmitPush;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pushnotif_detail);

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        context = getApplicationContext();

        initToolbar(getResources().getString(R.string.pf_nav), null);
    }

    @OnClick(R.id.btnSubmitPush)
    public void onPushNotificationUpdate() {
        Toast.makeText(getApplicationContext(), "Update Push Notification", Toast.LENGTH_SHORT).show();
    }
}
