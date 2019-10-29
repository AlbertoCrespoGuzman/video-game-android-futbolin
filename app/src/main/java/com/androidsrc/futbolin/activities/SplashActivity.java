package com.androidsrc.futbolin.activities;
// emulator -use-system-libs -avd mini
//emulator -gpu off -use-system-libs -avd mini

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.FriendlyTeam;
import com.androidsrc.futbolin.communications.http.auth.get.getStrategies;
import com.androidsrc.futbolin.communications.http.auth.get.getTeams;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.get.test.Usertest;

import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Notification;
import com.androidsrc.futbolin.database.models.Strategy;
import com.androidsrc.futbolin.database.models.TeamDB;
import com.androidsrc.futbolin.database.models.currentScreen;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.animations.ResizeAnimation;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alberto on 2017/12/05.
 */

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 500;
    SvcApi svcAth;
    DatabaseManager db;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        image = findViewById(R.id.activity_splash_image);
        image.setImageResource(Defaultdata.images.get((new Random()).nextInt((8 - 0) + 1) + 0));
        getSupportActionBar().hide();

        db = new DatabaseManager(getApplicationContext());

        if(getIntent().getExtras() != null){
            int fragment = getIntent().getExtras().getInt("changeactivity", 0);
            Log.e("SPLASH", "SPLASH fragment -> " + fragment);
        }

        ImageView img_loading_frame = findViewById(R.id.iv_frame_loading);
        img_loading_frame.bringToFront();
        img_loading_frame.requestLayout();
        final ResizeAnimation resizeAnimation = new ResizeAnimation(img_loading_frame, 0f, 0f, 500f, 500f);

        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {

                frameAnimation.start();
          //      resizeAnimation.start();
            }
        });

        currentScreen currentScreen;

        if(db.findCurrentScreen() == null){
            currentScreen = new currentScreen();
        }else{
            currentScreen = db.findCurrentScreen();
        }
        currentScreen.setFragmentName("");
        db.saveCurrentScreen(currentScreen);
        if(db.findNotification() == null){
            Notification notification = new Notification();
            notification.setTrainActive(true);
            notification.setLastTrain(0);
            notification.setLiveSoundsActive(true);
            notification.setMatchActive(true);
            notification.setTrainNotification(true);
            if(Locale.getDefault().getDisplayLanguage().startsWith("español")){
                notification.setLanguage("es");
            }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("english")){
                notification.setLanguage("en");
            }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("por")){
                notification.setLanguage("pt");
            }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("cat")){
                notification.setLanguage("ca");
            }
            db.saveNotification(notification);
        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                if(!db.ExistsUser()){
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }else{
                    if(db.findUser().isLogged() && db.findUser().getToken() != null && !db.findUser().getToken().equals("") && db.findUser().getToken().length() == 60){
                        if(db.findAllTeamDBs() == null || db.findAllTeamDBs().isEmpty()) {
                            getTeams();
                        }else{
                            changeActivity();
                        }
                    }else{
                        Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
                    }

                }

            }
        }, SPLASH_DISPLAY_LENGTH);

        svcAth = Svc.initAuth(getApplicationContext());
    }
    private void changeActivity(){

        svcAth = Svc.initAuth(getApplicationContext());
        Log.e("getuser device_id", "change activity" + Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        Call<getUser> call = svcAth.getMe(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getUser>() {
            @Override
            public void onResponse(Call<getUser> call, Response<getUser> response) {

                final Response<com.androidsrc.futbolin.communications.http.auth.get.getUser> responsefinal = response;
                Log.e("changeActivity", "getMe response" + responsefinal.toString());
                final getUser user = response.body();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(responsefinal.code() == 400){
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                // SUPONIENDO QUE ANTERIORMENTE HA HECHO LOGOUT, NO TIENE TOKEN !
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){

                                if(user.getUser().getTeam() != null){
                                    Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
                                    mainActivity.putExtra("user", user);
                                    try{
                                        Notification notification = db.findNotification();
                                        notification.setLanguage(user.getUser().getLanguage());
                                        db.saveNotification(notification);
                                    }catch(Exception e){e.printStackTrace();}

                                    startActivity(mainActivity);
                                    finish();
                                }else{
                                    startActivity(new Intent(SplashActivity.this, CreateTeamActivity.class));
                                    finish();
                                }
                            }
                        }
                    });
                } else {

                   // SUPONIENDO QUE ANTERIORMENTE HA HECHO LOGOUT, NO TIENE TOKEN !
                    try {
                        String error =response.errorBody().string();
                        Log.e("Splash Error", error);
                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            startActivity(mainActivity);
                            finish();
                        }else{
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                    //    Log.e("error code :",response.toString() + "");
                    //    Log.e("error code :",response.code() + "");
                    //    Log.e("error:",response.message());
                     //   Log.v("Error ", response.errorBody().string());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getUser> call, Throwable t) {
                t.printStackTrace();
                Log.e("onFailure", "onFailure");
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
    private void getStrategies(){

        svcAth = Svc.initAuth(getApplicationContext());
        Log.e("device_id", "" + Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        Call<getStrategies> call = svcAth.getStrategies(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getStrategies>() {
            @Override
            public void onResponse(Call<getStrategies> call, Response<getStrategies> response) {

                final Response<getStrategies> responsefinal = response;
//                Log.e("Strategies", responsefinal.body().toString());
                final getStrategies getStrategies = response.body();
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){

                                db.saveAllStrategies(getStrategies.getStrategies());
                                changeActivity();

                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();
                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            startActivity(mainActivity);
                            finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getStrategies> call, Throwable t) {
                t.printStackTrace();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();

            }
        });

    }
    public void getTeams(){
        Log.e("SPLASH", "getTeams()!");
        svcAth = Svc.initAuth(getApplicationContext());
        Call<getTeams> call = svcAth.getTeams(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getTeams>() {
            @Override
            public void onResponse(Call<getTeams> call, Response<getTeams> response) {
                final getTeams teams = response.body();
                final Response<getTeams> responsefinal = response;
                if (response.isSuccessful()) {
                    if(getApplicationContext()!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Success, get team = " + responsefinal);
                                if (responsefinal.code() == 400) {

                                } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {
                                    updateTeamDBs(responsefinal.body());
                                    if(db.findAllStrategies() == null || db.findAllStrategies().isEmpty()){
                                        getStrategies();
                                    }else{
                                        changeActivity();
                                    }

                                }
                            }
                        });
                    }
                } else {
                    try {
                        String error = response.errorBody().string();
                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            startActivity(mainActivity);
                            finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getTeams> call, Throwable t) {
                t.printStackTrace();
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
    public void updateTeamDBs(getTeams teams){
        if(db == null){
            db = new DatabaseManager(getApplicationContext());
        }
        for(FriendlyTeam friendlyTeam : teams.getTeams()){
            TeamDB teamDB = null;
            if(db.findTeamDBById(friendlyTeam.getId()) == null){
                teamDB = new TeamDB();
            }else{
                teamDB = db.findTeamDBById(friendlyTeam.getId());
            }
            teamDB.setId(friendlyTeam.getId());
            teamDB.setName(friendlyTeam.getName());
            teamDB.setShort_name(friendlyTeam.getShort_name());
            db.saveTeamDB(teamDB);
            Log.e("saved team",friendlyTeam.getShort_name());
        }
    }
}