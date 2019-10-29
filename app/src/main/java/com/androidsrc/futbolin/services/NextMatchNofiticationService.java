package com.androidsrc.futbolin.services;

import android.app.AlarmManager;
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
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Notification;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class NextMatchNofiticationService  extends IntentService {
    public static int TRAINNING_SERVICE = 1;
    public static int NEXT_MATCH_SERVICE = 2;
    DatabaseManager db;
    long dateToTrain = 0;
    long dateToNextMatch = 0;

    public NextMatchNofiticationService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        db = new DatabaseManager(getApplicationContext());
        Log.e("SERVICE", "NextMatchNofiticationService " + db.findNotification().toString());
        if(db.ExistsNotification()){

            }if(db.findNotification().isMatchActive() /*&& !db.findNotification().isMatchNotification() */){
                checkNotificationNextMatch();
            }
        }



    void checkNotificationNextMatch(){
        if(db.ExistsNotification()){
            TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");

            Calendar calendar = new GregorianCalendar();
            calendar.setTimeZone(timeZone);
            long timeNow = calendar.getTimeInMillis();
            dateToNextMatch = db.findNotification().getNextMatch();

            Log.e("dateToNextMatch", dateToNextMatch + "");
            Log.e("timeNow", timeNow + "");
            Log.e("menor de 15", dateToNextMatch + 1000 * 60 * 15 + "");
            Log.e("checking", "dateToNextMatch <= timeNow  ==  " + (dateToNextMatch <= timeNow) );
            Log.e("checking", "timeNow < (dateToNextMatch + 1000 * 60 * 15)  ==  " + (timeNow < (dateToNextMatch + 1000 * 60 * 15)) );

            if(dateToNextMatch <= timeNow && timeNow < (dateToNextMatch + 1000 * 60 * 15)){
                Log.e("CHECK MATCH", "SI creatingMatchNotification");
                createNextMatchNotification();
            }else{
                Log.e("CHECK MATCH", "NOOOO creatingMAtchNotification");
                Notification notification = db.findNotification();
                notification.setMatchNotificationAccount(notification.getMatchNotificationAccount() + 1);
                db.saveNotification(notification);

                if(notification.getMatchNotificationAccount() > 2){
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent myIntent = new Intent(getApplicationContext(), NextMatchNofiticationService.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            getApplicationContext(), NextMatchNofiticationService.NEXT_MATCH_SERVICE, myIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.cancel(pendingIntent);
                    Log.e("ALARm", "Cancelling NExtMatchAlarm");
                    notification.setMatchNotificationAccount(0);
                    db.saveNotification(notification);
                }

            }
        }
    }
    void createNextMatchNotification(){
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 1,
                notificationIntent, 0);

        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        android.app.Notification notify=new android.app.Notification.Builder  (getApplicationContext()).setContentText("Comienza el encuentro: "
                + db.findNotification().getNextMatchTeam()).
                setContentTitle("\uD83C\uDFC6 " + getResources().getString(R.string.tournament_match)+ " \uD83C\uDFDF")
                .setSmallIcon(R.drawable.ic_ball).setContentIntent(intent)
                .setSound(notificationSound)
                .setLights(Color.BLUE, 2000, 2000)
                .build();

        notify.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);

        Notification not = db.findNotification();
        not.setMatchNotification(true);
        not.setMatchNotificationAccount(0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), NextMatchNofiticationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), NextMatchNofiticationService.NEXT_MATCH_SERVICE, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
        Log.e("ALARm", "Cancelling NExtMatchAlarm");

        db.saveNotification(not);

    }



}