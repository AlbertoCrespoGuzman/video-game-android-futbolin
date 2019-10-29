package com.androidsrc.futbolin.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.http.auth.get.market.MarketObject;
import com.androidsrc.futbolin.utils.Defaultdata;

public class NotificationCreatorService extends IntentService{
    final static String CASHBACK_INFO = "cashback_info";
    public NotificationCreatorService() {
        super("Cashback IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String title = intent.getStringExtra("title");
        String player = intent.getStringExtra("player_name");
        int type = intent.getIntExtra("marketnotification", 1);
Log.e("EEEEEE", "CREEATORRRR NOTIFICATION   NNIOSNDNIODS");
        if(type == Defaultdata.MARKET_NOTIFICATION_FOLLOWING){
       //     createFollowingPlayerNotification(getApplicationContext(), title, player);
        }else{
       //     createOfferedPlayerNotification(getApplicationContext(), title, player);
        }
    }


    public static void createFollowingPlayerNotification(Context context, String title, String player){
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("changeactivity", Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_FOLLOWING);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        NotificationManager notif=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        android.app.Notification notify=new android.app.Notification.Builder  (context)
                .setContentText(context.getResources().getString(R.string.in_15_mins) +" " + player + " "
                        + context.getResources().getString(R.string.in_15_mins_2))
                        .setContentTitle("\uD83D\uDCB5 " + title + " ❗️")
                .setSmallIcon(R.drawable.ic_ball)
                .setSound(notificationSound)
                .setLights(Color.BLUE, 2000, 2000)
                .setContentIntent(intent).build();

        notify.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
        notif.notify(2, notify);


    }
    public static void createOfferedPlayerNotification(Context context, String title, String player){
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("changeactivity", Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_OFFER);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        NotificationManager notif=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        android.app.Notification notify=new android.app.Notification.Builder  (context)
                .setContentText(context.getResources().getString(R.string.in_15_mins) +" " + player + " "
                        + context.getResources().getString(R.string.in_15_mins_2)).
                        setContentTitle("\uD83D\uDCB5 " + title + " ❗️")
                .setSmallIcon(R.drawable.ic_ball)
                .setSound(notificationSound)
                .setLights(Color.BLUE, 2000, 2000)
                .setContentIntent(intent).build();

        notify.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
        notif.notify(3, notify);
    }

}