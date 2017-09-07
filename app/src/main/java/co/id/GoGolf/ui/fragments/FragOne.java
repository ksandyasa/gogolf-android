package co.id.GoGolf.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.HomeEvent;
import co.id.GoGolf.models.response.home.DataHome;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.CustomVP;
import co.id.GoGolf.ui.activities.MainActivity;
import co.id.GoGolf.ui.adapters.SliderAdapter;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by dedepradana on 5/15/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class FragOne extends Fragment {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.lltop)
    RelativeLayout lltop;
    @InjectView(R.id.pager)
    CustomVP pager;
    @InjectView(R.id.circleIndicator)
    CircleIndicator circleIndicator;
    @InjectView(R.id.llbottom)
    LinearLayout llbottom;
    @InjectView(R.id.ll1)
    LinearLayout ll1;
    @InjectView(R.id.ll2)
    LinearLayout ll2;
    @InjectView(R.id.ll3)
    LinearLayout ll3;
    @InjectView(R.id.ll4)
    LinearLayout ll4;
    @InjectView(R.id.iv1)
    ImageView iv1;
    @InjectView(R.id.iv2)
    ImageView iv2;
    @InjectView(R.id.iv3)
    ImageView iv3;
    @InjectView(R.id.iv4)
    ImageView iv4;
    @InjectView(R.id.tv1)
    CustomTextView tv1;
    @InjectView(R.id.tv2)
    CustomTextView tv2;
    @InjectView(R.id.tv3)
    CustomTextView tv3;
    @InjectView(R.id.tv4)
    CustomTextView tv4;

    private DataHome dataHome;
    private SliderAdapter sliderAdapter;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.frag_one, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.inject(this, view);
        DaggerInjection.get().inject(this);

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getContext(), PreferenceUtility.SF_USER_DATA), User.class);

        mainPresenter.postGetPhotoHome(user.getLang());

        ((MainActivity) getActivity()).showLoadingDialog();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe
    public void onEventThread(HomeEvent event) {
        dataHome = event.getData();
        sliderAdapter = new SliderAdapter(getContext(), dataHome.getToparr());

        ll1.setBackgroundColor(Color.parseColor(dataHome.getBottomarr().get(0).getBgcolor()));
        ll2.setBackgroundColor(Color.parseColor(dataHome.getBottomarr().get(1).getBgcolor()));
        ll3.setBackgroundColor(Color.parseColor(dataHome.getBottomarr().get(2).getBgcolor()));
        ll4.setBackgroundColor(Color.parseColor(dataHome.getBottomarr().get(3).getBgcolor()));

        Log.d("TAG", "iv1 url " + dataHome.getBottomarr().get(0).getImage());
        Log.d("TAG", "iv2 url " + dataHome.getBottomarr().get(1).getImage());
        Log.d("TAG", "iv3 url " + dataHome.getBottomarr().get(2).getImage());
        Log.d("TAG", "iv4 url " + dataHome.getBottomarr().get(3).getImage());
        Glide.with(getContext()).load(dataHome.getBottomarr().get(0).getImage()).into(iv1);
        Glide.with(getContext()).load(dataHome.getBottomarr().get(1).getImage()).into(iv2);
        Glide.with(getContext()).load(dataHome.getBottomarr().get(2).getImage()).into(iv3);
        Glide.with(getContext()).load(dataHome.getBottomarr().get(3).getImage()).into(iv4);

        tv1.setText(getResources().getString(R.string.home_weekend));
        tv2.setText(getResources().getString(R.string.home_course));
        tv3.setText(getResources().getString(R.string.home_map));
        tv4.setText(getResources().getString(R.string.home_point));

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(dataHome.getBottomarr().get(0));
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(dataHome.getBottomarr().get(1));
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(dataHome.getBottomarr().get(2));
            }
        });
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(dataHome.getBottomarr().get(3));
            }
        });

        pager.setAdapter(sliderAdapter);
        pager.setOffscreenPageLimit(sliderAdapter.getCount());
        circleIndicator.setViewPager(pager);
        lltop.setVisibility(View.VISIBLE);
        llbottom.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).dismissLoadingDialog();
        if (PreferenceUtility.getInstance().loadDataString(getContext(), PreferenceUtility.DESTINATION).equals("weekend") && PreferenceUtility.getInstance().loadDataString(getContext(), PreferenceUtility.EVENT).equals("page")) {
            PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.DESTINATION, "");
            PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.EVENT, "");
            openWeekendFromHome();
        }
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        ((MainActivity) getActivity()).dismissLoadingDialog();
        llbottom.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void openWeekendFromHome() {
        EventBus.getDefault().post(dataHome.getBottomarr().get(0));
    }

}
