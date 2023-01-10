package com.guruprasad.notesuplaoder.ui.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.guruprasad.notesuplaoder.MainActivity;
import com.guruprasad.notesuplaoder.R;

import java.util.Random;

public class Message_Listener extends FirebaseMessagingService {

    private final  String CHANNEL_ID = "guru";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Intent intent = new Intent(this, MainActivity.class);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int notification_id = new Random().nextInt();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CreateNotificationChannel(manager);
        }


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent intent1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            intent1 = PendingIntent.getActivities(this ,0, new Intent[]{intent}, PendingIntent.FLAG_MUTABLE);
        }

        Notification notification ;

            notification = new NotificationCompat.Builder(this , CHANNEL_ID )
                    .setContentTitle(message.getData().get("title"))
                    .setContentText(message.getData().get("message"))
                    .setSmallIcon(R.drawable.logo)
                    .setAutoCancel(true)
                    .setContentIntent(intent1)
                    .build();


        manager.notify(notification_id , notification);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void CreateNotificationChannel(NotificationManager manager) {

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID , "Pithos " , NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("This is the Notification from the Pithos Community ");
        channel.enableLights(true);
        channel.setLightColor(Color.rgb(3,155,229));

        manager.createNotificationChannel(channel);


    }
}
