package com.xtel.sdk.fcm;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xtel.ivipuser.R;
import com.xtel.ivipuser.view.activity.HomeActivity;

/**
 * Created by vihahb on 1/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";
    private Intent intent;

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From " + remoteMessage.getFrom());


        //Check if message contains data
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message Data " + remoteMessage.getData());
        }

        //Check if message contains notification
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message body " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }


    }


    /**
     * Display the notification
     *
     * @param body
     */
    private void sendNotification(String body) {

        intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, /* request code */
                intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notification = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID notification */, notification.build());
    }
}
