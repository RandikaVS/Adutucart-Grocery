package com.example.adutucart5.activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;

import androidx.core.app.NotificationCompat;

import com.example.adutucart5.R;
import com.example.adutucart5.fragment.MyOrderFragment;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class pushNotificationService extends FirebaseMessagingService {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;

    NotificationManager notificationManager;


    @SuppressLint("MissingPermission")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title="title",text="body";

        title = remoteMessage.getData().get("title");
        text = remoteMessage.getData().get("message");

        sendNotification(title,pushNotificationService.this,text);
    }

    public void sendNotification(String title, Context context, String text) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.add_icon)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true);

        // Create a pending intent to open the app when notification is clicked
        Intent intent = new Intent(context, MyOrderFragment.class);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        }

        builder.setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;

        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }

        // Show the notification
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(200, builder.build());

    }
}