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
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.provider.Settings;
        import android.text.Html;
        import android.util.Log;
        import android.widget.Toast;

        import com.androidsrc.futbolin.R;
        import com.androidsrc.futbolin.activities.MainActivity;
        import com.androidsrc.futbolin.communications.Svc;
        import com.androidsrc.futbolin.communications.SvcApi;
        import com.androidsrc.futbolin.communications.http.auth.get.getTournament;
        import com.androidsrc.futbolin.communications.http.auth.get.getUser;
        import com.androidsrc.futbolin.communications.http.auth.get.market.MarketObject;
        import com.androidsrc.futbolin.communications.http.auth.get.market.getMarket;
        import com.androidsrc.futbolin.database.manager.DatabaseManager;
        import com.androidsrc.futbolin.database.models.MarketNotification;
        import com.androidsrc.futbolin.database.models.Notification;
        import com.androidsrc.futbolin.database.models.SystemSubNotifications;
        import com.androidsrc.futbolin.utils.Defaultdata;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.GregorianCalendar;
        import java.util.List;
        import java.util.TimeZone;
        import java.util.regex.Pattern;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        import static com.androidsrc.futbolin.activities.MainActivity.TrainningInterval;


public class MarketNotificationService extends IntentService {
    DatabaseManager db;
    static long timeToNotify = 1000 * 60 * 15;

    public MarketNotificationService() {

        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("EEEEEEEEE", "MarketNotificationService !!!!!!!!!!!!!!!!!!!");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        db = new DatabaseManager(getApplicationContext());

        getMe();
    }




    public static void createMessageNotification(Context context, SystemSubNotifications systemSubNotifications){

        /*
        String message = Html.fromHtml(systemSubNotifications.getMessage()).toString();


        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);

        NotificationManager notif=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        android.app.Notification notify=new android.app.Notification.Builder  (context)
                .setContentText(message).
                        setContentTitle("\uD83D\uDD14 " +systemSubNotifications.getTitle() +  " \uD83D\uDCE9")
                .setSmallIcon(R.drawable.ic_ball)
                .setSound(notificationSound)
                .setLights(Color.BLUE, 2000, 2000)
                .setContentIntent(intent).build();

        notify.flags |= android.app.Notification.FLAG_AUTO_CANCEL;
        notif.notify(4, notify);
        */
    }
    public void updateTrainningNotifications(long nextTime, getUser user){


        long dateToTrain = 0;
        Log.e("updateTrainningNOt", "updateTrainningNotifications - nextTime = " + nextTime);
        Notification notification;
        TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");

        if(db.ExistsNotification()){
            notification = db.findNotification();
        }else{
            notification = new Notification();
            notification.setMatchActive(true);
            notification.setTrainActive(true);
            notification.setLiveSoundsActive(true);
            notification.setTrainNotification(false);
            notification.setMatchNotification(false);
            notification.setNextMatch(0);
            notification.setMatchNotificationAccount(0);
            notification.setTrainNotificationAccount(0);
        }
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            sdf.setTimeZone(timeZone);
            date = sdf.parse(user.getUser().getTeam().getLast_trainning());
        }catch (Exception e){

        }


        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(timeZone);
        long timeNow = calendar.getTimeInMillis();
        if(nextTime > 0){
            dateToTrain = nextTime + (5 * 60 + 1000 );
        }else {
            if(date != null) {
                dateToTrain = date.getTime() + TrainningInterval - timeNow;
            }else{
                dateToTrain = 0;
            }
        }
        notification.setLastTrain(dateToTrain);

        db.saveNotification(notification);
        Log.e("notif Main", db.findNotification().toString());

        if(dateToTrain > 0) {
            AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            Intent serviceIntent = new Intent(getApplicationContext(), TrainNotificationsService.class);

            PendingIntent servicePendingIntent =
                    PendingIntent.getService(getApplicationContext(),
                            Defaultdata.TRAINNING_SERVICE, // integer constant used to identify the service
                            serviceIntent,
                            PendingIntent.FLAG_CANCEL_CURRENT);

            Log.e("dateToTrain", dateToTrain + "");

//			am.setTimeZone("America/Buenos_Aires");

            am.setRepeating(
                    AlarmManager.RTC,
                    new GregorianCalendar().getTimeInMillis(),
                    dateToTrain,
                    servicePendingIntent
            );

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC,new Date().getTime() + dateToTrain, servicePendingIntent);
                am.setAndAllowWhileIdle(AlarmManager.RTC,new Date().getTime() + dateToTrain, servicePendingIntent);
                AlarmManager.AlarmClockInfo ac=
                        new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + dateToTrain,
                                servicePendingIntent);
                am.setAlarmClock(ac, servicePendingIntent);
            }
            notification.setTrainNotificationAccount(0);
            db.saveNotification(notification);
            Log.e("NEW ALARM", "TRAIN ALARM SET -> " + ((new GregorianCalendar().getTimeInMillis()) + dateToTrain));
        }else{
            Log.e("dateToTrain negative", dateToTrain + "");
        }
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
                        new Handler().post(new Runnable() {
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
                                    getUser user = responsefinal.body();
                                    updateTrainningNotifications(0,user);
                              //      checkMarketNotificationGettingMessages(getApplicationContext(), user);

                              //      getMarketFollowing(user.getUser().getTeam().getId());
                              //      getMarketOffers(user.getUser().getTeam().getId());
                                }
                            }
                        });
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<com.androidsrc.futbolin.communications.http.auth.get.getUser> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
    public void getMarketFollowing(final long team_id){

        /*
        SvcApi svc;
        svc = Svc.initAuth(getApplicationContext());
        Call<getMarket> call = svc.getMarketFollowing(Settings.Secure.getString(
                getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getMarket>() {
            @Override
            public void onResponse(Call<getMarket> call, Response<getMarket> response) {
                final getMarket user = response.body();
                final Response<getMarket> responsefinal = response;


                if (response.isSuccessful()) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("MeTransActions", "Success, MeTransActions = " + responsefinal.body().toString());
                            if (responsefinal.code() == 400) {

                            } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {


                                checkMarketNotificationGettingFollowingPlayers(getApplicationContext(), responsefinal.body().getMarket());

                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<getMarket> call, Throwable t) {
                t.printStackTrace();
            }
        });
       */
    }
    public void getMarketOffers(final long team_id){
        /*
        SvcApi svc;
        svc = Svc.initAuth(getApplicationContext());
        Call<getMarket> call = svc.getMarketOffers(Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getMarket>() {
            @Override
            public void onResponse(Call<getMarket> call, Response<getMarket> response) {
                final getMarket user = response.body();
                final Response<getMarket> responsefinal = response;

                if (response.isSuccessful()) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("MeTransActions", "Success, MeTransActions = " + responsefinal.body().toString());
                            if (responsefinal.code() == 400) {

                            } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {


                                checkMarketNotificationGettingOfferedPlayers(getApplicationContext(), responsefinal.body().getMarket());

                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<getMarket> call, Throwable t) {
                t.printStackTrace();
            }
        });
        */
    }
    public static void checkMarketNotificationGettingOfferedPlayers(Context context, List<MarketObject> players){

        DatabaseManager db = new DatabaseManager(context);

        for(MarketObject player : players){
            if(db.findMarketNotificationByOfferPlayerId(player.getId()) == null
                    || !db.findMarketNotificationByOfferPlayerId(player.getId()).isNotified()){
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Calendar closes_at = Calendar.getInstance();
                    Calendar dateNow = Calendar.getInstance();

                    closes_at.setTime(new Date(player.getCloses_at() * 1000));
                    closes_at.setTimeZone(TimeZone.getTimeZone("America/Buenos_Aires"));

                    dateNow.setTime(new Date());
                    dateNow.setTimeZone(TimeZone.getTimeZone("America/Buenos_Aires"));

                    Intent serviceIntent = new Intent(context, NotificationCreatorService.class);


                    serviceIntent.putExtra("title", "Mercado de fichajes");
                    serviceIntent.putExtra("player_name", player.getPlayer().getName());
                    serviceIntent.putExtra("marketnotification",
                            Defaultdata.MARKET_NOTIFICATION_FOLLOWING);
                    PendingIntent servicePendingIntent =
                            PendingIntent.getService(context,
                                    Defaultdata.MARKET_NOTIFICATION_FOLLOWING, // integer constant used to identify the service
                                    serviceIntent,
                                    PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


                    am.setExactAndAllowWhileIdle(AlarmManager.RTC,closes_at.getTimeInMillis() - timeToNotify, servicePendingIntent);
                    am.setAndAllowWhileIdle(AlarmManager.RTC,closes_at.getTimeInMillis() - timeToNotify, servicePendingIntent);
                    AlarmManager.AlarmClockInfo ac=
                            new AlarmManager.AlarmClockInfo( closes_at.getTimeInMillis() - timeToNotify,
                                    servicePendingIntent);
                    am.setAlarmClock(ac, servicePendingIntent);

                    MarketNotification marketNotification = new MarketNotification();
                    marketNotification.setNotified(true);
                    marketNotification.setOfferplayerid(player.getId());
                    db.saveMarketNotification(marketNotification);


                }
            }
        }
    }
    public static void checkMarketNotificationGettingFollowingPlayers(Context context, List<MarketObject> players){

        DatabaseManager db = new DatabaseManager(context);

        for(MarketObject player : players){
            if(db.findMarketNotificationByFollowPlayerId(player.getId()) == null
                    || !db.findMarketNotificationByFollowPlayerId(player.getId()).isNotified()){
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Calendar closes_at = Calendar.getInstance();
                    Calendar dateNow = Calendar.getInstance();

                    closes_at.setTime(new Date(player.getCloses_at() * 1000));
                    closes_at.setTimeZone(TimeZone.getTimeZone("America/Buenos_Aires"));

                    dateNow.setTime(new Date());
                    dateNow.setTimeZone(TimeZone.getTimeZone("America/Buenos_Aires"));

                    Intent serviceIntent = new Intent(context, NotificationCreatorService.class);


                    serviceIntent.putExtra("title", context.getResources().getString(R.string.market_title));
                    serviceIntent.putExtra("player_name", player.getPlayer().getName());
                    serviceIntent.putExtra("marketnotification",
                            Defaultdata.MARKET_NOTIFICATION_OFFER);
                    PendingIntent servicePendingIntent =
                            PendingIntent.getService(context,
                                    Defaultdata.MARKET_NOTIFICATION_OFFER, // integer constant used to identify the service
                                    serviceIntent,
                                    PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    am.setExactAndAllowWhileIdle(AlarmManager.RTC,closes_at.getTimeInMillis() - timeToNotify, servicePendingIntent);
                    am.setAndAllowWhileIdle(AlarmManager.RTC,closes_at.getTimeInMillis() - timeToNotify, servicePendingIntent);
                    AlarmManager.AlarmClockInfo ac=
                            new AlarmManager.AlarmClockInfo( closes_at.getTimeInMillis() - timeToNotify,
                                    servicePendingIntent);
                    am.setAlarmClock(ac, servicePendingIntent);

                    MarketNotification marketNotification = new MarketNotification();
                    marketNotification.setNotified(true);
                    marketNotification.setFollowplayerid(player.getId());
                    db.saveMarketNotification(marketNotification);

                    Calendar test = Calendar.getInstance();
                    test.setTimeZone(TimeZone.getTimeZone("America/Buenos_Aires"));
                    test.setTime(new Date(dateNow.getTimeInMillis() - timeToNotify));

                    Log.e("MarketNotifciation", "CReatorNotificitaion in which time = " + (closes_at.getTimeInMillis() - timeToNotify));


                }
            }
        }
    }
    public static void checkMarketNotificationGettingMessages(Context context, getUser user){

        DatabaseManager db = new DatabaseManager(context);

        for(SystemSubNotifications systemSubNotifications : user.getNotifications().getNotifications()){
            if(systemSubNotifications.isUnread() && (db.findMarketNotificationByMessageId(systemSubNotifications.getId()) == null
            || !db.findMarketNotificationByMessageId(systemSubNotifications.getId()).isNotified())){
                MarketNotification marketNotification = new MarketNotification();
                marketNotification.setMessageid(systemSubNotifications.getId());
                marketNotification.setNotified(true);
                db.saveMarketNotification(marketNotification);
                createMessageNotification(context, systemSubNotifications);
            }
        }



    }
}