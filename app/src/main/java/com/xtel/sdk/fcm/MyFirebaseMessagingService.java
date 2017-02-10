package com.xtel.sdk.fcm;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xtel.ivipu.R;
import com.xtel.ivipu.view.activity.HomeActivity;

/**
 * Created by vihahb on 1/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From " + remoteMessage.getFrom());

        //Check if message contains data
        Log.d(TAG, "Message Data " + remoteMessage.getData());

        //Check if message contains notification
        Log.d(TAG, "Message body " + remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage.getNotification());
    }


    /**
     * Display the notification
     *
     * @param notifications
     */
    private void sendNotification(RemoteMessage.Notification notifications) {

        int color = getResources().getColor(R.color.notification_color);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notifications.getTitle())
                .setContentText(notifications.getBody())
                .setAutoCancel(true)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notifications.getBody()))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification.build());
    }
}
