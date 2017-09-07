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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.AreaEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.SearchEvent;
import co.id.GoGolf.events.SearchFilterEvent;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.activities.ActPreBookingV3;
import co.id.GoGolf.ui.activities.ActSearch;
import co.id.GoGolf.ui.activities.BaseAct;
import co.id.GoGolf.ui.adapters.GolfAdapter;
import co.id.GoGolf.ui.callback.GolfAdapterCallback;
import co.id.GoGolf.ui.presenters.MainPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dedepradana on 5/3/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class FragGolf extends Fragment implements GolfAdapterCallback {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.srlDefault)
    SwipeRefreshLayout srlDefault;

    @InjectView(R.id.rvDefault)
    RecyclerView rvHistory;

    @InjectView(R.id.tvEmptySearch)
    CustomTextView tvEmptySearch;

    @InjectView(R.id.fabSearch)
    FloatingActionButton fabSearch;

    private GolfAdapter golfAdapter;
    private SearchFilterEvent filterEvent;
    private AreaEvent areaEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_golf, container, false);

//        areaEvent = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getContext(), PreferenceUtility.SF_AREA_DATA), AreaEvent.class);
        filterEvent = new SearchFilterEvent(10, 5000000, "", "", "en");

        ButterKnife.inject(this, rootView);
        DaggerInjection.get().inject(this);

        initRecyclerView();

        srlDefault.setOnRefreshListener(new RefreshCourseList());

        mainPresenter.getCourseList();

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
        golfAdapter = new GolfAdapter(getActivity(), this);
        rvHistory.setAdapter(golfAdapter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Subscribe
    public void onEventThread(SearchFilterEvent event) {
        Log.d("retro", "" + String.valueOf(event.getPricemin()) + String.valueOf(event.getPricemax()) + event.getArea_id() + event.getGname());
        filterEvent = event;
        Log.d("retro", "" + String.valueOf(filterEvent.getPricemin()) + String.valueOf(filterEvent.getPricemax()) + filterEvent.getArea_id() + filterEvent.getGname());
        mainPresenter.getGolf((filterEvent.getPricemin() == 0) ? "" : String.valueOf(filterEvent.getPricemin()), (filterEvent.getPricemax() == 0) ? "" : String.valueOf(filterEvent.getPricemax()), filterEvent.getArea_id(), filterEvent.getGname());
    }

    @Subscribe
    public void onEventThread(SearchEvent event) {
        ((BaseAct)getContext()).dismissLoadingDialog();
        srlDefault.setRefreshing(false);
        golfAdapter.clearData();
        if (event.getData().size() == 0) {
            tvEmptySearch.setText(event.getMessage());
            tvEmptySearch.setVisibility(View.VISIBLE);
        }else{
            golfAdapter.addData(event.getData());
            tvEmptySearch.setVisibility(View.INVISIBLE);
        }
    }


    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        Toast.makeText(getActivity(), "Failed! " + errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fabSearch)
    public void onSearch() {
        startActivity(new Intent(getContext(), ActSearch.class));
    }

    @Override
    public void ShowPreBookingView(String gid, String date) {
        startActivityForResult(new Intent(getContext(), ActPreBookingV3.class).putExtra("gid", gid).putExtra("date", date), 22);
    }

    private class RefreshCourseList implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            srlDefault.setRefreshing(true);

            mainPresenter.getCourseList();
        }
    }
}
