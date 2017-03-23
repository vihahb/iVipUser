package com.xtel.ivipu.view.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.xtel.ivipu.R;
import com.xtel.ivipu.view.MyApplication;

public class NotificationAction extends AppCompatActivity {

    Button btn_notification, btn_notification_news, btn_notification_location, btn_notification_update;
    NotificationManager notificationManager;
    int notifyCount = 0, newsCount = 0, locationCount = 0, updateCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_action);
        btn_notification = (Button) findViewById(R.id.btnNotification);
        btn_notification_news = (Button) findViewById(R.id.btn_notification_news);
        btn_notification_location = (Button) findViewById(R.id.btn_notification_location);
        btn_notification_update = (Button) findViewById(R.id.btn_notification_update);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNotification();
            }
        });

        btn_notification_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNews();
            }
        });

        btn_notification_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLocation();
            }
        });

        btn_notification_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleUpdate();
            }
        });
    }

    public void toggleNotification() {
        Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
        intent.putExtra("notification", "notification");

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifyCount++;
        Notification notification = new NotificationCompat.Builder(getBaseContext())
                .setContentTitle("Hello Notification test")
                .setContentText("Content Text Notification")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setNumber(notifyCount)
                .addAction(R.drawable.ic_no_send, "Go to Profile", pendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    public void toggleNews() {
        Intent intent = new Intent(MyApplication.context, HomeActivity.class);
        intent.putExtra("notification", "news");

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        newsCount++;
        Notification notification = new NotificationCompat.Builder(getBaseContext())
                .setContentTitle("News")
                .setContentText("Content Text News")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setNumber(newsCount)
                .addAction(R.drawable.ic_no_send, "Go to News", pendingIntent)
                .build();
        notificationManager.notify(2, notification);
    }

    public void toggleLocation() {
        Intent intent = new Intent(MyApplication.context, HomeActivity.class);
        intent.putExtra("notification", "location");

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        locationCount++;
        Notification notification = new NotificationCompat.Builder(getBaseContext())
                .setContentTitle("Location")
                .setContentText("Content Text Location")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setNumber(locationCount)
                .addAction(R.drawable.ic_no_send, "Go to Location", pendingIntent)
                .build();
        notificationManager.notify(3, notification);
    }

    public void toggleUpdate() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String PkgName = "com.xtel.vparking";
        try {
            intent.setData(Uri.parse("market://details?id=" + PkgName));
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PkgName)));
        } catch (android.content.ActivityNotFoundException anfe) {
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + PkgName)));
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + PkgName));
        }
        Log.e("Pkg_name notify", PkgName);

        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        updateCount++;
        Notification notification = new NotificationCompat.Builder(getBaseContext())
                .setContentTitle("New update for apps")
                .setContentText("Please update from Play Store")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setNumber(updateCount)
                .addAction(R.drawable.ic_no_send, "Go to Update", pendingIntent)
                .build();
        notificationManager.notify(4, notification);
    }
}
