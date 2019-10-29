package com.androidsrc.futbolin.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.core.app.NotificationCompat;

import java.util.Iterator;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e("onMessageReceived", "onMessageReceived -----> " + remoteMessage.toString());

        Iterator<String> iterator = remoteMessage.getData().keySet().iterator();
        Intent intent = null;
        PendingIntent pendingIntent = null;

        while(iterator.hasNext()) {
            String setElement = iterator.next();
            Log.e("setElement","setElement: " + setElement);
            if(setElement.equals("screen")){


            }
        }
        if(remoteMessage.getData() != null && remoteMessage.getData().containsKey("screen")){
            String screenCode = remoteMessage.getData().get("screen");
            Log.e("NOTIFICATION","get Data -> screen !!!  " + screenCode);
            String player_idString = remoteMessage.getData().get("player_id");
            Log.e("NOTIFICATION","get Data -> player_idString !!!  " + player_idString);
            if(screenCode.equals("1")){
                String channelId = "Default";

                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try{
                    long player_id = Long.parseLong(player_idString);
                    if(player_id > 0){
                        intent.putExtra("changeactivity", Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_DEFAULT_PLAYER_DIALOG);
                        intent.putExtra("player_id", player_id);
                    }else{
                        intent.putExtra("changeactivity", Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_DEFAULT);
                    }
                }catch (Exception e){
                    intent.putExtra("changeactivity", Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_DEFAULT);
                }


                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


                Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationManager notif=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                android.app.Notification notify=new android.app.Notification.Builder  (getApplicationContext())
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setSmallIcon(R.drawable.ic_ball)
                        .setSound(notificationSound)
                        .setLights(Color.BLUE, 2000, 2000)
                        .setContentIntent(pendingIntent).build();

/*

                NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_ball)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setLights(Color.BLUE, 2000, 2000)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setAutoCancel(true).setContentIntent(pendingIntent);

*/
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                    notif.createNotificationChannel(channel);
                }
                notify.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
                notif.notify(4, notify);
            }
            if(screenCode.equals("2")){
                String channelId = "Default";

                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try{
                    long player_id = Long.parseLong(player_idString);
                    if(player_id > 0){
                        intent.putExtra("changeactivity", Defaultdata.MAINACTIVITY_CHANGE_TOPLAYERS_DEFAULT_PLAYER_DIALOG);
                        intent.putExtra("player_id", player_id);
                    }else{
                        intent.putExtra("changeactivity", Defaultdata.MAINACTIVITY_CHANGE_TOPLAYERS_DEFAULT);
                    }
                }catch (Exception e){
                    intent.putExtra("changeactivity", Defaultdata.MAINACTIVITY_CHANGE_TOPLAYERS_DEFAULT);
                }
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
/*
                NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_ball)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setLights(Color.BLUE, 2000, 2000)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setAutoCancel(true).setContentIntent(pendingIntent);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                */
                Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationManager notif=(NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                android.app.Notification notify=new android.app.Notification.Builder  (getApplicationContext())
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setSmallIcon(R.drawable.ic_ball)
                        .setSound(notificationSound)
                        .setLights(Color.BLUE, 2000, 2000)
                        .setContentIntent(pendingIntent).build();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                    notif.createNotificationChannel(channel);
                }
                notify.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
                notif.notify(4, notify);
            }

        }


/*
        Iterator<String> iterator = remoteMessage.getData().keySet().iterator();
        while(iterator.hasNext()) {
            String setElement = iterator.next();
            Log.e("setElement","setElement: " + setElement);
        }
        Log.d("MENSAJE", "Message data payload: " + remoteMessage.getData());
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.ic_ball)
                .setLights(Color.BLUE, 2000, 2000)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());

        */
    }

    @Override
    public void onNewToken(String token) {
        Log.e("CUIDADOOOO", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(token);

    }

}
