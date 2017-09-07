package co.id.GoGolf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.SearchEvent;
import co.id.GoGolf.events.SearchFilterEvent;
import co.id.GoGolf.ui.adapters.GolfWeekendAdapter;
import co.id.GoGolf.ui.callback.GolfWeekendAdapterCallback;
import co.id.GoGolf.ui.presenters.MainPresenter;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by prumacadmin on 9/28/16.
 */
public class ActWeekend extends BaseAct implements GolfWeekendAdapterCallback {

    private Context context;

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.rvDefault)
    RecyclerView rvHistory;

    @InjectView(R.id.tabs)
    TabLayout tabLayout;

    boolean sunday = false;

//    private PromotionAdapter promotionAdapter;
    private GolfWeekendAdapter golfAdapter;
    private SearchFilterEvent filterEvent;
    private String date;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_weekend);

        context = getApplicationContext();
        date = getIntent().getStringExtra("date");

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        initToolbar(getResources().getString(R.string.wk_nav), null);

        initRecyclerView();

        showLoadingDialog();

        mainPresenter.getGolfWeekend("this_saturday");

        setupTabLayout();

    }

    private void setupTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.wk_saturday)), true);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.wk_sunday)));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals(getResources().getString(R.string.wk_saturday))){
                    showLoadingDialog();
                    sunday = false;
                    mainPresenter.getGolfWeekend("this_saturday");
                }else {
                    showLoadingDialog();
                    sunday = true;
                    mainPresenter.getGolfWeekend("this_sunday");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initRecyclerView() {
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(rvHistory.getContext()));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
//        rvSerp.addItemDecoration(new DividerItemDecoration(rvSerp.getContext(),
//                DividerItemDecoration.VERTICAL_LIST));
        golfAdapter = new GolfWeekendAdapter(this, this);
        rvHistory.setAdapter(golfAdapter);
    }

//    @Subscribe
//    public void onEventThread(SearchFilterPromoEvent event) {
//        filterEvent = event;
//        mainPresenter.getPromotion(filterEvent.getPricemin(),filterEvent.getPricemax(),filterEvent.getDate(),filterEvent.getStime(),filterEvent.getEtime(),filterEvent.getArea_id(),"",filterEvent.getLang());
//        Clog.e(filterEvent.toString());
//    }

    @Subscribe
    public void onEventThread(SearchFilterEvent event) {
        dismissLoadingDialog();
        filterEvent = event;
        if (sunday){
            showLoadingDialog();
            mainPresenter.getGolfWeekend("this_sunday");
        }else {
            showLoadingDialog();
            mainPresenter.getGolfWeekend("this_saturday");
        }
    }


    @Subscribe
    public void onEventThread(SearchEvent event) {
        dismissLoadingDialog();
        golfAdapter.clearData();
        golfAdapter.addData(event.getData());
    }


    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        dismissLoadingDialog();
        Toast.makeText(context, "Failed! " + errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fabSearch)
    public void onSearchPromo(){
        startActivity(new Intent(context, ActSearch.class));
    }

    @Override
    public void ShowPreBookingView(String gid, String date) {
        startActivityForResult(new Intent(context, ActPreBookingV3.class).putExtra("gid", gid).putExtra("date", date), 22);
    }
}
