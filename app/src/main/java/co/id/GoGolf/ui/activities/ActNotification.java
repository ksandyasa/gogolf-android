package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.NotifEvent;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.DividerItemDecoration;
import co.id.GoGolf.ui.adapters.NotifAdapter;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by prumacadmin on 9/4/16.
 */


public class ActNotification extends BaseAct {

    private NotifAdapter notifAdapter;
    private Context context;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.rvDefault)
    RecyclerView rvHistory;

    @InjectView(R.id.tvEmptyNotif)
    CustomTextView tvEmptyNotif;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notif);

        context = getApplicationContext();

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        initToolbar("Notification", null);
        initRecyclerView();

        mainPresenter.getNotif(PreferenceUtility.getInstance().loadDataString(context, PreferenceUtility.ACCESS_TOKEN), "en");

        showLoadingDialog();
    }

    public void initRecyclerView() {
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(rvHistory.getContext()));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.addItemDecoration(new DividerItemDecoration(rvHistory.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        notifAdapter = new NotifAdapter(ActNotification.this);
        rvHistory.setAdapter(notifAdapter);
    }


    @Subscribe
    public void onEventThread(NotifEvent event) {
        dismissLoadingDialog();
        notifAdapter.addData(event.getData());
        if (event.getData().size() == 0) {
            tvEmptyNotif.setText(event.getMessage());
            tvEmptyNotif.setVisibility(View.VISIBLE);
//            Toast.makeText(context, "There is no data in this page.", Toast.LENGTH_SHORT).show();
        }else{
            tvEmptyNotif.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        dismissLoadingDialog();
        Toast.makeText(context, errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
