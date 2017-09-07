package co.id.GoGolf.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.PromotionEvent;
import co.id.GoGolf.events.SearchFilterPromoEvent;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.activities.ActPreBookingV3;
import co.id.GoGolf.ui.activities.ActSearchPromo;
import co.id.GoGolf.ui.activities.BaseAct;
import co.id.GoGolf.ui.adapters.PromotionAdapter;
import co.id.GoGolf.ui.callback.PromotionAdapterCallback;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.Clog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URLEncoder;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dedepradana on 5/3/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class FragPro extends Fragment implements PromotionAdapterCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.srlPro)
    SwipeRefreshLayout srlPro;

    @InjectView(R.id.rvDefault)
    RecyclerView rvHistory;

    @InjectView(R.id.tvEmptyPromotion)
    CustomTextView tvEmptyPromotion;

    @InjectView(R.id.fabSearch)
    FloatingActionButton fabSearch;

    private PromotionAdapter promotionAdapter;

    private SearchFilterPromoEvent filterEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_pro, container, false);

        ButterKnife.inject(this, rootView);
        DaggerInjection.get().inject(this);

        initRecyclerView();

        srlPro.setOnRefreshListener(new RefreshPromoList());

        mainPresenter.getPromotion("","","","","","","","en");

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void initRecyclerView() {
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(rvHistory.getContext()));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
//        rvSerp.addItemDecoration(new DividerItemDecoration(rvSerp.getContext(),
//                DividerItemDecoration.VERTICAL_LIST));
        promotionAdapter = new PromotionAdapter(getActivity(), this);
        rvHistory.setAdapter(promotionAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Subscribe
    public void onEventThread(SearchFilterPromoEvent event) {
        promotionAdapter.clearData();
        filterEvent = event;
        mainPresenter.getPromotion(filterEvent.getPricemin(),filterEvent.getPricemax(),filterEvent.getDate(), URLEncoder.encode(filterEvent.getStime()).replace("%3A", ":"), URLEncoder.encode(filterEvent.getEtime()).replace("%3A", ":"),filterEvent.getArea_id(),"",filterEvent.getLang());
        Clog.e(filterEvent.toString());
    }

    @Subscribe
    public void onEventThread(PromotionEvent event) {
        ((BaseAct)getContext()).dismissLoadingDialog();
        srlPro.setRefreshing(false);
        promotionAdapter.clearData();
        if (event.getData().size() == 0) {
            tvEmptyPromotion.setText(event.getMessage());
            tvEmptyPromotion.setVisibility(View.VISIBLE);
        }else {
            promotionAdapter.addData(event.getData());
            tvEmptyPromotion.setVisibility(View.INVISIBLE);
        }

    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        Toast.makeText(getActivity(), "Failed! " + errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fabSearch)
    public void onSearchPromo(){
        startActivity(new Intent(getContext(), ActSearchPromo.class));
    }

    @Override
    public void ShowPreBookingView(String gid, String date) {
        startActivityForResult(new Intent(getContext(), ActPreBookingV3.class).putExtra("gid", gid).putExtra("date", date), 22);
    }

    private class RefreshPromoList implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            srlPro.setRefreshing(true);

            mainPresenter.getPromotion("","","","","","","","en");
        }
    }
}
