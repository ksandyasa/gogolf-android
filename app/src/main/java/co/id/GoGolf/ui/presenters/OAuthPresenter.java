package co.id.GoGolf.ui.presenters;

import android.util.Log;

import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.ForgotPasswordEvent;
import co.id.GoGolf.events.LoginEvent;
import co.id.GoGolf.events.RegisterEvent;
import co.id.GoGolf.models.RestAPI;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dedepradana on 6/5/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class OAuthPresenter {
    RestAPI restAPI;
    String TAG = OAuthPresenter.class.getSimpleName();

    @Inject
    public OAuthPresenter(RestAPI restAPI) {
        this.restAPI = restAPI;
    }

    public void postLogin(String email, String password, String device_id) {
        restAPI.postLogin(email, password, device_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(LoginEvent event) {
                        Log.d(TAG, event.getMessage());
                        Log.d(TAG, event.getData().toString());
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postSocialLogin(String access_token, String social_provider, String device_id, String lang) {
        restAPI.postSocialLogin(access_token, social_provider, device_id, lang).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(LoginEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postRegister(String fname, String lname, String email, String password, String country_id, String gender, String phone, String device_id, String lang, String country_code) {
        restAPI.postRegister(fname, lname, email, password, country_id, gender, phone, device_id, lang, country_code).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(RegisterEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

    public void postForgotPassword(String emailAddr) {
        restAPI.postForgotPassword(emailAddr).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ForgotPasswordEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent(e.getMessage()));
                    }

                    @Override
                    public void onNext(ForgotPasswordEvent event) {
                        if (event.getCode() == 200) {
                            EventBus.getDefault().post(event);
                        } else {
                            EventBus.getDefault().post(new ErrorEvent(event.getMessage()));
                        }
                    }
                });
    }

}
