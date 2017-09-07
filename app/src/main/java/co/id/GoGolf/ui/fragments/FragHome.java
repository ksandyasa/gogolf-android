package co.id.GoGolf.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.id.GoGolf.R;
import co.id.GoGolf.models.response.home.Bottomarr;
import co.id.GoGolf.ui.CustomTabLayout;
import co.id.GoGolf.ui.activities.ActSearch;
import co.id.GoGolf.ui.activities.ActWeekend;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by dedepradana on 5/15/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class FragHome extends Fragment {

    @InjectView(R.id.tabs)
    CustomTabLayout tabs;

    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    ViewPagerAdapter adapter;

    private FragPoint fragPoint;
    private FragOne fragOne;

    public FragHome() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_home, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        setupViewPager(viewpager);

        tabs.setupWithViewPager(viewpager);

        viewpager.setCurrentItem(2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Log.d("TAG", data.getStringExtra("point_refresh"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void refreshPointPageFromHome() {
        if (fragPoint != null) {
            fragPoint.refreshPointFromServer();
        }
    }

    public void openWeekendFromMain() {
        if (fragOne != null) {
            fragOne.openWeekendFromHome();
        }
    }

    public void showPointPageFromHome() {
        viewpager.setCurrentItem(adapter.getCount());
    }

    private void setupViewPager(ViewPager viewpager){
        fragOne = new FragOne();
        fragPoint = new FragPoint();
        adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new FragMap(), getResources().getString(R.string.hmt_map));
        adapter.addFragment(new FragGolf(), getResources().getString(R.string.hmt_search));
        adapter.addFragment(fragOne, getResources().getString(R.string.hmt_home));
        adapter.addFragment(new FragPro(), getResources().getString(R.string.hmt_promotion));
        adapter.addFragment(fragPoint, getResources().getString(R.string.hmt_point));
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(adapter.getCount());
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Subscribe
    public void onEventThread(Bottomarr bottomarr) {
        if (bottomarr.getEvent().equals("tab")){
            viewpager.setCurrentItem(Integer.valueOf(bottomarr.getDestination()));
        }else {
            if (bottomarr.getDestination().equals("search golf course")){
                viewpager.setCurrentItem(1);
                startActivity(new Intent(getContext(), ActSearch.class));
            }else if(bottomarr.getDestination().equals("weekend")){
                startActivity(new Intent(getContext(), ActWeekend.class).putExtra("date", bottomarr.getRequest().getData().getDate()));
            }
        }
    }


}
