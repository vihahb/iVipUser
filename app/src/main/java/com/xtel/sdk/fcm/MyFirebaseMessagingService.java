package com.xtel.sdk.fcm;

import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xtel.ivipu.model.entity.MessageObj;
import com.xtel.ivipu.view.MyApplication;
import com.xtel.ivipu.view.activity.NotificationAction;
import com.xtel.ivipu.view.widget.NotificationHelper;
import com.xtel.nipservicesdk.utils.JsonHelper;

/**
 * Created by vihahb on 1/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyAndroidFCMService";
    private static final String PkgName = MyApplication.context.getPackageName();
    private static final Context CONTEXT = MyApplication.context;
    NotificationAction notificationAction = new NotificationAction();
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        }
        Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            MessageObj message = JsonHelper.getObjectNoException(remoteMessage.getNotification().getBody(), MessageObj.class);
            Log.e(TAG, String.valueOf(message));
            if (message != null) {
                toggleNotifications(message, 1);
            }
//                sendNotification(message, 1);
        }
        Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
    }

    public void toggleNotifications(MessageObj messageObj, int action) {
        if (action == 1) {
            NotificationHelper.getIntance().toNotification(messageObj.getTitle(), messageObj.getContent());
        } else if (action == 2) {
            NotificationHelper.getIntance().toNotification(messageObj.getTitle(), messageObj.getContent());
        }
    }

//    /**
//     * Display the notification
//     *
//     * @param mes_notification
//     */
//    private void sendNotification(MessageObj mes_notification, int action) {
//        notificationAction.toggleNotification();
////        if (action == 1){
//////            NotificationHelper.getIntance().toNotification(mes_notification.getTitle(), mes_notification.getContent());
////            notificationAction.toggleNotification();
////        } else if (action == 2){
////            NotificationHelper.getIntance().toNews(mes_notification.getTitle(), mes_notification.getContent());
////        } else if (action == 3){
////            NotificationHelper.getIntance().toNewsForLocation(mes_notification.getTitle(), mes_notification.getContent());
////        } else if (action == 4){
////            NotificationHelper.getIntance().toUpdate(mes_notification.getTitle(), mes_notification.getContent());
////        } else if (action == 5){
////
////        }
//    }


//    String push_data = "notification";
//
//        // Invoking the default notification service
//        notification = new NotificationCompat.Builder(CONTEXT);
//        notification.setContentTitle(mes_notification.getTitle());
//        notification.setContentText(mes_notification.getContent());
//        notification.setTicker("OnReceived!");
//        notification.setSmallIcon(R.mipmap.ic_launcher);
//        notification.setSound(defaultSoundUri);
//        notification.setVibrate(vibrate);
//        notification.setColor(color);
//
//
//        // Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, ProfileActivity.class);
//        resultIntent.putExtra("notification", push_data);
//        //This ensures that navigating backward from the Activity leads out of the app to Home page
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(CONTEXT);
//        //Add stack builder for the intent
//        stackBuilder.addParentStack(ProfileActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(CONTEXT, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
////        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
////                PendingIntent.FLAG_ONE_SHOT //can only be used once
////        );
//        //Start Activity when user click the notification text
//        notification.setContentIntent(pendingIntent);
//        NotificationManager notificationManager = (NotificationManager)
//                getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notification.build());

    private void actionNotification() {
//        if (action == 1) {
//            Intent intent = new Intent(this, ProfileActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            String push_data = "1";
//            intent.putExtra("notification", push_data);
//            PendingIntent pendingIntent = PendingIntent.getActivity(CONTEXT, 0, intent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            long[] vibrate = {500,1000};
//            notification = new Notification.Builder(this)
//                    .setContentTitle(mes_notification.getTitle())
//                    .setContentText(mes_notification.getContent())
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setAutoCancel(false)
//                    .setColor(color)
//                    .setSound(defaultSoundUri)
//                    .setContentIntent(pendingIntent)
//                    .addAction(R.drawable.ic_no_send, "Notify", pendingIntent).build();
////                    .setSmallIcon(R.mipmap.ic_launcher)
////                    .setContentTitle(mes_notification.getTitle())
////                    .setContentText(mes_notification.getContent())
////                    .setAutoCancel(true)
////                    .setColor(color)
////                    .setSound(defaultSoundUri)
////                    .setVibrate(vibrate)
////                    .addAction(R.drawable.ic_no_send, "Notify", pendingIntent)
////                    .setContentIntent(pendingIntent);
//        }

//        else if (action == 2) {
////            notification.addAction(R.drawable.ic_no_send, "News", pendingIntent);
//        }
//
//        else if (action == 3) {
////            notification.addAction(R.drawable.ic_no_send, "News for Location", pendingIntent);
//        }
//
//        else if (action == 4) {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            try {
//                intent.setData(Uri.parse("market://details?id=" + PkgName));
////                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PkgName)));
//            } catch (android.content.ActivityNotFoundException anfe) {
////                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + PkgName)));
//                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + PkgName));
//            }
//            PendingIntent pendingIntent = PendingIntent.getActivity(CONTEXT, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            notification.addAction(R.drawable.ic_no_send, "Update", pendingIntent);
//
//        }
//
//        else if (action == 5) {
////            notification.addAction(R.drawable.ic_no_send, "Play Store", pendingIntent);
//        }
////                .addAction(R.drawable.ic_send_comment, "Action", pendingIntent);
////                .setContentIntent(pendingIntent);
    }
}
