package co.id.GoGolf.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.UserDetailEvent;
import co.id.GoGolf.models.response.login.User;
import co.id.GoGolf.ui.CustomTextView;
import co.id.GoGolf.ui.activities.ActPointHistory;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by dedepradana on 7/15/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class FragPoint extends Fragment {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.tvCurrentPoint)
    CustomTextView tvCurrentPoint;

    @InjectView(R.id.tvLastUpdate)
    CustomTextView tvLastUpdate;

    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_point, container, false);

        ButterKnife.inject(this, rootView);
        DaggerInjection.get().inject(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        user = new Gson().fromJson(PreferenceUtility.getInstance().loadDataString(getContext(), PreferenceUtility.SF_USER_DATA), User.class);

        mainPresenter.postGetUserDetail(PreferenceUtility.getInstance().loadDataString(getContext(), PreferenceUtility.ACCESS_TOKEN), "en", user.getUid());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void refreshPointFromServer() {
        mainPresenter.postGetUserDetail(PreferenceUtility.getInstance().loadDataString(getContext(), PreferenceUtility.ACCESS_TOKEN), "id", user.getUid());
    }

    @OnClick(R.id.btCheckPoint)
    public void onCheckPoint() {
        startActivity(new Intent(getContext(), ActPointHistory.class));
    }

    @Subscribe
    public void onEventThread(ErrorEvent errorEvent) {
        Toast.makeText(getActivity(), "Failed! " + errorEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(UserDetailEvent event) {
        user = event.getData();
        tvLastUpdate.setText(getResources().getString(R.string.pp_last_update) + " : " + user.getLdate());
        tvCurrentPoint.setText(user.getPoint());
        PreferenceUtility.getInstance().saveData(getContext(), PreferenceUtility.SF_USER_DATA, new Gson().toJson(event.getData()));
    }

}
