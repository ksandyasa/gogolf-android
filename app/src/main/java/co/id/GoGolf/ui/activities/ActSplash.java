package co.id.GoGolf.ui.activities;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import co.id.GoGolf.R;
import co.id.GoGolf.dagger.DaggerInjection;
import co.id.GoGolf.events.ErrorEvent;
import co.id.GoGolf.events.VersionEvent;
import co.id.GoGolf.models.response.DataVersion;
import co.id.GoGolf.ui.presenters.MainPresenter;
import co.id.GoGolf.util.PreferenceUtility;

import org.greenrobot.eventbus.Subscribe;
import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by apridosandyasa on 10/21/16.
 */

public class ActSplash extends BaseAct {

    @Inject
    MainPresenter mainPresenter;

    @InjectView(R.id.pbSplash)
    ProgressBar pbSplash;

    DataVersion dataVersion;

    private String appPackageName;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);

        context = getApplicationContext();

        ButterKnife.inject(this);
        DaggerInjection.get().inject(this);

        mainPresenter.getVersionApps("android");

        setProgressWithAnimation(0, 100);

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().get("destination") != null)
                    PreferenceUtility.getInstance().saveData(context, PreferenceUtility.DESTINATION, getIntent().getExtras().get("destination").toString());
                if (getIntent().getExtras().get("event") != null)
                    PreferenceUtility.getInstance().saveData(context, PreferenceUtility.EVENT, getIntent().getExtras().get("event").toString());
            }
            Log.d("TAG", intentToString(getIntent()));
        }

    }

    @Subscribe
    public void onEventThread(ErrorEvent event) {
        dismissLoadingDialog();
    }

    @Subscribe
    public void onEventThread(VersionEvent event) {
        dataVersion = event.getData();
        dismissLoadingDialog();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkFirstRun();
            }
        }, 500);
    }

    private void saveCurrentVersion(String currentVersionCode) {
        String savedVersionCode = PreferenceUtility.getInstance().loadDataString(ActSplash.this, PreferenceUtility.VERSION_CODE_KEY);
        Log.d("TAG", "first install " + savedVersionCode);

        // Update the shared preferences with the current version code
        PreferenceUtility.getInstance().saveData(ActSplash.this, PreferenceUtility.VERSION_CODE_KEY, currentVersionCode);

        // Check for first run or upgrade
        if (savedVersionCode.equals("")) {
            // TODO This is a new install (or the user cleared the shared preferences)
            startActivity(new Intent(ActSplash.this, ActTutorial.class).putExtra("first_install", savedVersionCode));
            ActSplash.this.finish();
        } else {
            startActivity(new Intent(ActSplash.this, ActLogin.class));
            ActSplash.this.finish();
        }
    }

    private void showUpdateDialog(final String currentVersionCode) {
        new AlertDialog.Builder(ActSplash.this)
                .setCancelable(false)
                .setMessage("GoGolf need to be updated!")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=jp.naver.line.android")));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=jp.naver.line.android")));
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        ActSplash.this.finish();
//                        SharedPreferences prefs = PreferenceUtility.getInstance().sharedPreferences(ActSplash.this);
//                        int savedVersionCode = prefs.getInt(PreferenceUtility.VERSION_CODE_KEY, -1);
//
//                        // Update the shared preferences with the current version code
//                        prefs.edit().putInt(PreferenceUtility.VERSION_CODE_KEY, currentVersionCode).commit();
//
//                        // Check for first run or upgrade
//                        if (currentVersionCode == savedVersionCode) {
//                            // This is just a normal run
//                            startActivity(new Intent(ActSplash.this, ActLogin.class));
//                            ActSplash.this.finish();
//                        } else if (savedVersionCode == -1) {
//                            // TODO This is a new install (or the user cleared the shared preferences)
//                            startActivity(new Intent(ActSplash.this, ActTutorial.class).putExtra("first_install", savedVersionCode));
//                            ActSplash.this.finish();
//                        } else if (currentVersionCode > savedVersionCode) {
//                            // TODO This is an upgrade
//                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void checkFirstRun() {

        // Get current version code
        String currentVersionCode = "";
        try {
            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appPackageName = getPackageName();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            // handle exception
            e.printStackTrace();
            return;
        }

        Log.d("TAG", "currentVersion " + currentVersionCode);
        Log.d("TAG", "serverVersion " + dataVersion.getMin_version());

        String[] currVerArray = currentVersionCode.split("\\.");
        String[] servVerArray = dataVersion.getMin_version().split("\\.");

        Log.d("TAG", "currVerArray count " + currVerArray.length);
        Log.d("TAG", "servVerArray count " + servVerArray.length);

        if (Integer.parseInt(currVerArray[0]) < Integer.parseInt(servVerArray[0])) {
            showUpdateDialog(currentVersionCode);
        }else{
            if (Integer.parseInt(currVerArray[1]) < Integer.parseInt(servVerArray[1])) {
                showUpdateDialog(currentVersionCode);
            }else{
                if (Integer.parseInt(currVerArray[2]) < Integer.parseInt(servVerArray[2])) {
                    showUpdateDialog(currentVersionCode);
                }else{
                    saveCurrentVersion(currentVersionCode);
                }
            }
        }
    }

    private void setProgressWithAnimation(int value1, int value2) {
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(this.pbSplash, "progress", value1, value2);
        progressAnimator.setDuration(1000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }

    public String intentToString(Intent intent) {
        if (intent == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder("action: ")
                .append(intent.getAction())
                .append(" data: ")
                .append(intent.getDataString())
                .append(" extras: ")
                ;

        if (intent.getExtras() != null) {
            if (intent.getExtras().get("destination") != null)
                PreferenceUtility.getInstance().saveData(context, PreferenceUtility.DESTINATION, intent.getExtras().get("destination").toString());
            if (intent.getExtras().get("event") != null)
                PreferenceUtility.getInstance().saveData(context, PreferenceUtility.EVENT, intent.getExtras().get("event").toString());
            for (String key : intent.getExtras().keySet())
                stringBuilder.append(key).append("=").append(intent.getExtras().get(key)).append(" ");
        }

        return stringBuilder.toString();

    }
}
