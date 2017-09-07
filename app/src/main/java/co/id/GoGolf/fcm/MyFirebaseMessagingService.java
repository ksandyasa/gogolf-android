package co.id.GoGolf.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import co.id.GoGolf.R;
import co.id.GoGolf.ui.activities.ActLogin;
import co.id.GoGolf.ui.activities.MainActivity;
import co.id.GoGolf.util.GcmBroadcastReceiver;
import co.id.GoGolf.util.PreferenceUtility;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by dedepradana on 5/26/16.
 * Copyright (C) 2016 Dede Pradana <me@dedepradana.org>
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData().get("body"));
        Log.d(TAG, "Notification DATA " + remoteMessage.getData());
        Log.d(TAG, "Notification FROM " + remoteMessage.getFrom());
        Log.d(TAG, "Notification TO " + remoteMessage.getTo());
        Log.d(TAG, "Notification MESSAGE TYPE " + remoteMessage.getMessageType());
        Log.d(TAG, "Notification COLLAPSE KEY " + remoteMessage.getCollapseKey());
        Log.d(TAG, "Notification MESSAGE ID " + remoteMessage.getMessageId());

        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), remoteMessage.getData().get("destination"), remoteMessage.getData().get("event"));
        Intent intent = new Intent("push_notifications");
        intent.putExtra("title", remoteMessage.getData().get("title"));
        intent.putExtra("content", remoteMessage.getData().get("body"));
        intent.putExtra("destination", remoteMessage.getData().get("destination"));
        intent.putExtra("event", remoteMessage.getData().get("event"));

        updateListNotifications(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), remoteMessage.getData().get("destination"), remoteMessage.getData().get("event"));

        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String messageBody, String destination, String event) {
        Intent intent;
        if (PreferenceUtility.getInstance().loadDataString(getApplicationContext(), PreferenceUtility.ACCESS_TOKEN).length() > 0) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }else {
            intent = new Intent(getApplicationContext(), ActLogin.class);
        }

        intent.putExtra("title", title);
        intent.putExtra("content", messageBody);
        intent.putExtra("destination", destination);
        intent.putExtra("event", event);

        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder;
        notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) System.currentTimeMillis() /* ID of notification */, notificationBuilder.build());
    }

    private void updateListNotifications(String title, String message, String destination, String event) {
        Intent intent = new Intent("push_notifications");
        intent.putExtra("title", title);
        intent.putExtra("content", message);
        intent.putExtra("destination", destination);
        intent.putExtra("event", event);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
}
