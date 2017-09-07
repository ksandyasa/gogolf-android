package co.id.GoGolf.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;

import co.id.GoGolf.fcm.MyFirebaseMessagingService;

/**
 * Created by apridosandyasa on 8/23/16.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmMessageHandler will handle the intent.

        ComponentName comp = new ComponentName(context.getPackageName(),
                MyFirebaseMessagingService.class.getName());

        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(AppCompatActivity.RESULT_OK);
    }

}
