package co.id.GoGolf;

import android.app.Application;
import android.content.res.Configuration;

import co.id.GoGolf.ui.UIUtils;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import io.fabric.sdk.android.Fabric;

/**
 * Created by dedepradana on 5/25/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        UIUtils.setLocale(getApplicationContext());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        UIUtils.setLocale(getApplicationContext());
    }

}
