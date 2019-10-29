package com.androidsrc.futbolin.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.getTournament;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.database.manager.DatabaseManager;

import android.app.Notification;
import com.androidsrc.futbolin.utils.Defaultdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import androidx.core.app.NotificationCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainNotificationsService extends IntentService {

    DatabaseManager db;
    long dateToTrain = 0;
    long dateToNextMatch = 0;
    getTournament tournament;

    public TrainNotificationsService() {
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
        Log.e("EEEEEE", "TrainNotificationsService " + db.findNotification().toString());
        createTrainningNotification2();
    }

    void checkNotificationTrainning(){
        if(db.ExistsNotification()){
            TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");

            Calendar calendar = new GregorianCalendar();
            calendar.setTimeZone(timeZone);
            long timeNow = calendar.getTimeInMillis();
            Log.e("CHECK TRAIN","dateToTrain " + dateToTrain);
            Log.e("CHECK TRAIN","timeNow " + timeNow);
            Log.e("CHECK TRAIN","dateToTrain + MainActivity.TrainningInterval <= timeNow ->" + (dateToTrain + MainActivity.TrainningInterval <= timeNow) );
            if(dateToTrain + MainActivity.TrainningInterval <= timeNow){
                Log.e("CHECK TRAIN", "creatingTrainnigNotification");
                createTrainningNotification();

            }else{
                /*
                Log.e("CHECK TRAIN", "NOOOO creatingTrainnigNotification");
                Notification notification = db.findNotification();
                notification.setTrainNotificationAccount(notification.getTrainNotificationAccount() + 1);
                db.saveNotification(notification);

                if(notification.getTrainNotificationAccount() > 2){
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent myIntent = new Intent(getApplicationContext(), TrainNotificationsService.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            getApplicationContext(), TrainNotificationsService.TRAINNING_SERVICE, myIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.cancel(pendingIntent);
                    Log.e("ALARm", "Cancelling TrainAlarm");
                    notification.setTrainNotificationAccount(0);
                    db.saveNotification(notification);
                }
                    */
            }
        }
    }

    public void createTrainningNotification2(){
        NotificationManager notifManager;


        String id = getApplicationContext().getString(R.string.trainning_notification_channel_id); // default_channel_id
        String title = getApplicationContext().getString(R.string.app_name); // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String aMessage = getApplicationContext().getResources().getString(R.string.trainning_notification);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(getApplicationContext(), id);
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            builder.setContentTitle("\uD83D\uDCAA " + getApplicationContext().getResources().getString(R.string.trainment)+ "  \uD83C\uDFCB\u200D♂")                            // required
                    .setSmallIcon(R.drawable.ic_ball)   // required
                    .setContentText(aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(aMessage));
        }
        else {
            builder = new NotificationCompat.Builder(getApplicationContext(), id);
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
            builder.setContentTitle("\uD83D\uDCAA " + getApplicationContext().getResources().getString(R.string.trainment)+ "  \uD83C\uDFCB\u200D♂")                            // required
                    .setSmallIcon(R.drawable.ic_ball)   // required
                    .setContentText( aMessage) // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(aMessage));
        }
        Notification notification = builder.build();
        notifManager.notify(Defaultdata.TRAINNING_NOTIFICATION, notification);

    }
    void createTrainningNotification(){
        /*
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, 0);

        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        android.app.Notification notify=new android.app.Notification.Builder  (getApplicationContext()).setContentText(" Tu equipo te necesita, ya puedes entrenarlo ").
                setContentTitle("\uD83D\uDCAA Entrenamiento \uD83C\uDFCB\u200D♂")
                .setSmallIcon(R.drawable.ic_ball)
                .setSound(notificationSound)
                .setLights(Color.BLUE, 2000, 2000)
                .setContentIntent(intent).build();

        notify.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
        notif.notify(1, notify);

        Notification not = db.findNotification();
        not.setTrainNotification(true);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(getApplicationContext(), TrainNotificationsService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), Defaultdata.TRAINNING_SERVICE, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
        Log.e("ALARm", "Cancelling TrainAlarm");
        not.setTrainNotificationAccount(0);
        db.saveNotification(not);
*/
    }

    public void getMe(){
        SvcApi svc;
        svc = Svc.initAuth(getApplicationContext());
        if(svc != null) {
            Call<getUser> call = svc.getMe(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
            call.enqueue(new Callback<getUser>() {
                @Override
                public void onResponse(Call<com.androidsrc.futbolin.communications.http.auth.get.getUser> call, Response<getUser> response) {
                    final com.androidsrc.futbolin.communications.http.auth.get.getUser user = response.body();
                    final Response<com.androidsrc.futbolin.communications.http.auth.get.getUser> responsefinal = response;


                    if (response.isSuccessful()) {
                        new MainActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("SuccessSERTVICEEEE, getMe = " + responsefinal);
                                if (responsefinal.code() == 400) {

                                } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {
                                    Date date = null;
                                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");
                                    try {
                                        sdf.setTimeZone(timeZone);
                                        date = sdf.parse(user.getUser().getTeam().getLast_trainning());
                                        Log.e("service!", date.getTime() + "");
                                    } catch (Exception e) {

                                    }
                                    dateToTrain = date.getTime();
                                    checkNotificationTrainning();
                                }
                            }
                        });
                    } else {
                        try {
                            String error = response.errorBody().string();

                            if (error.contains("live_match") && error.contains("Broadcasting live match")) {
                                //      checkNotificationNextMatch();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (responsefinal.code() == 500 || responsefinal.code() == 503) {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<com.androidsrc.futbolin.communications.http.auth.get.getUser> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

}