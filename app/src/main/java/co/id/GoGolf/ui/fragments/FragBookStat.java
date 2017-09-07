package co.id.GoGolf.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.BookHisEvent;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.ui.DividerItemDecoration;
import co.id.GoGolf.ui.adapters.BookStatAdapter;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 6/26/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class FragBookStat extends Fragment {

    @InjectView(R.id.rvDefault)
    RecyclerView rvHistory;

    private BookStatAdapter historyAdapter;

    @Inject
    MainPresenter mainPresenter;

    Calendar mcurrentTime = Calendar.getInstance();
    SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_history, container, false);

        ButterKnife.inject(this, rootView);
        DaggerInjection.get().inject(this);

        initRecyclerView();

        Date today = mcurrentTime.getTime();
        mcurrentTime.add(Calendar.YEAR, 1);
        Date nextYear = mcurrentTime.getTime();

        mainPresenter.getBookHis(PreferenceUtility.getInstance().loadDataString(getContext(), PreferenceUtility.ACCESS_TOKEN), sdr.format(today), sdr.format(nextYear),"0,1,2", "en");

        return rootView;
    }

    public void initRecyclerView() {
        rvHistory.setHasFixedSize(true);
        rvHistory.setLayoutManager(new LinearLayoutManager(rvHistory.getContext()));
        rvHistory.setItemAnimator(new DefaultItemAnimator());
        rvHistory.addItemDecoration(new DividerItemDecoration(rvHistory.getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        historyAdapter = new BookStatAdapter(getActivity());
        rvHistory.setAdapter(historyAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Subscribe
    public void onEventThread(BookHisEvent event) {
        historyAdapter.addData(event.getData());
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        Toast.makeText(getActivity(), "Failed! " + errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
