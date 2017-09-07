package co.id.GoGolf.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import co.id.GoGolf.R;

import co.id.GoGolf.events.TokenInvalidEvent;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by dedepradana on 6/5/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class BaseAct extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void showLoadingDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
//                        dialog.dismiss();
                        PreferenceUtility.getInstance().saveData(BaseAct.this, PreferenceUtility.SF_USER_DATA, "");
                        PreferenceUtility.getInstance().saveData(BaseAct.this, PreferenceUtility.ACCESS_TOKEN, "");
                        PreferenceUtility.getInstance().saveData(BaseAct.this, PreferenceUtility.PASSWORD, "");
                        PreferenceUtility.getInstance().saveData(BaseAct.this, PreferenceUtility.COUNTRY_ID, "");
                        startActivity(new Intent(BaseAct.this, ActLogin.class));
                        ActivityCompat.finishAffinity(BaseAct.this);
                    }
                })
                .show();
    }

    public void dismissLoadingDialog() {
        if (null == dialog) return;
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void initToolbar(String title, String subtitle) {
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        if (subtitle != null) {
            toolbar.setSubtitle(subtitle);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void initToolbarWithoutHomeButton(String title, String subtitle) {
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        if (subtitle != null) {
            toolbar.setSubtitle(subtitle);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    protected void quickToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEventThread(TokenInvalidEvent event) {
        showAlertDialog(event.getMessage());
    }


}
