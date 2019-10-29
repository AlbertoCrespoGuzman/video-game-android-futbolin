package com.androidsrc.futbolin.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.communications.http.auth.post.PostTeam;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Team;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.LocaleLanguageHelper;
import com.androidsrc.futbolin.utils.dialogs.ShieldsPickerDialog;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateTeamActivity extends AppCompatActivity implements ShieldsPickerDialog.DialogClickListener{

    SvcApi svc;
    DatabaseManager db;
    int colorPrimary = 0xffffff00;
    int colorSecundary = 0xffffff00;
    EditText teamName, shortTeamName, stadiumName;
    ImageButton primaryColor, secundaryColor;
    List<Integer> shields;
    VectorChildFinder shield;
    ImageView shieldImage;
    LinearLayout bordersShield;
    ShieldsPickerDialog cdd;
    int positionShield = 1;
    Button confirmButton;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        db = new DatabaseManager(getApplicationContext());

        if(db!= null && db.findNotification() != null && db.findNotification().getLanguage() != null && db.findNotification().getLanguage().length() >=2){
            LocaleLanguageHelper.setLocale(getApplicationContext(), db.findNotification().getLanguage());
        }




        setContentView(R.layout.activity_create_team);

        layout =  findViewById(R.id.activity_create_team_layout);
        layout.setBackgroundResource(Defaultdata.images.get((new Random()).nextInt((8 - 0) + 1) + 0));

        shields = Defaultdata.shields;

        bordersShield = findViewById(R.id.activity_create_team_borders_shield);
        teamName = findViewById(R.id.activity_create_team_name);
        shortTeamName = findViewById(R.id.activity_create_team_short_name);
        stadiumName = findViewById(R.id.activity_create_team_stadim_name);
        primaryColor = findViewById(R.id.activity_create_team_primary_color);
        secundaryColor = findViewById(R.id.activity_create_team_secundary_color);
        confirmButton = findViewById(R.id.activity_create_team_confirm_button);

        primaryColor.setTag(colorPrimary);
        secundaryColor.setTag(colorSecundary);

        GradientDrawable drawable = (GradientDrawable) primaryColor.getDrawable();
        drawable.setColor(colorPrimary);
        GradientDrawable drawable2 = (GradientDrawable) secundaryColor.getDrawable();
        drawable2.setColor(colorSecundary);

        shieldImage = findViewById(R.id.activity_create_team_selected_shield);

        shield = new VectorChildFinder(this, shields.get(positionShield), shieldImage);

        VectorDrawableCompat.VFullPath path21 = shield.findPathByName("secundary_color1");
        path21.setFillColor(colorSecundary);
        VectorDrawableCompat.VFullPath path22 = shield.findPathByName("secundary_color2");
        path22.setFillColor(colorSecundary);
        VectorDrawableCompat.VFullPath path1 = shield.findPathByName("primary_color");
        path1.setFillColor(colorPrimary);
        if(positionShield == 8){
            VectorDrawableCompat.VFullPath path12 = shield.findPathByName("primary_color2");
            path12.setFillColor(colorPrimary);
        }
        shieldImage.invalidate();



        primaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerDialog(false, (ImageButton) view, true);
            }
        });
        secundaryColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerDialog(false, (ImageButton) view, false);
            }
        });

        bordersShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShieldPickerDialog();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filtering()){
                    postTeam();
                }
            }
        });
    }




    @Override
    public void onResume(){
        super.onResume();
        svc = Svc.initAuth(getApplicationContext());
        db = new DatabaseManager(getApplicationContext());
    }

    void openColorPickerDialog(boolean supportsAlpha, ImageButton tvColor, boolean isPrimary) {
        final ImageButton tvColorF = tvColor;
        final boolean isPrimaryf = isPrimary;
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(CreateTeamActivity.this, (int)tvColor.getTag(), supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                displayColor(tvColorF, color, isPrimaryf);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
        });
        dialog.show();
    }

    void displayColor(ImageButton squareColor, int color, boolean isPrimary) {
        GradientDrawable drawable = (GradientDrawable) squareColor.getDrawable();
        drawable.setColor(color);

        if(isPrimary){
            VectorDrawableCompat.VFullPath path1 = shield.findPathByName("primary_color");
            path1.setFillColor(color);
            if(positionShield == 8){
                VectorDrawableCompat.VFullPath path12 = shield.findPathByName("primary_color2");
                path12.setFillColor(color);
            }
            colorPrimary = color;

        }else{
            VectorDrawableCompat.VFullPath path21 = shield.findPathByName("secundary_color1");
            path21.setFillColor(color);
            VectorDrawableCompat.VFullPath path22 = shield.findPathByName("secundary_color2");
            path22.setFillColor(color);
            colorSecundary = color;
        }

        squareColor.setTag(color);
        shieldImage.invalidate();

    }
    void openShieldPickerDialog(){
        cdd = new ShieldsPickerDialog(this, CreateTeamActivity.this, shields, colorPrimary, colorSecundary);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }


    @Override
    public void onItemClick(int position) {
        System.out.println("position final: " + position);
        positionShield = position;
        if(cdd != null && cdd.isShowing()){
            cdd.dismiss();
        }
        shield = new VectorChildFinder(this, shields.get(positionShield), shieldImage);

        VectorDrawableCompat.VFullPath path21 = shield.findPathByName("secundary_color1");
        path21.setFillColor(colorSecundary);
        VectorDrawableCompat.VFullPath path22 = shield.findPathByName("secundary_color2");
        path22.setFillColor(colorSecundary);
        VectorDrawableCompat.VFullPath path1 = shield.findPathByName("primary_color");
        path1.setFillColor(colorPrimary);
        if(positionShield == 8){
            VectorDrawableCompat.VFullPath path12 = shield.findPathByName("primary_color2");
            path12.setFillColor(colorPrimary);
        }

        shieldImage.invalidate();
    }
    boolean filtering(){
        boolean ok = true;

        if(teamName.getText().toString().length() < 3){
            ok = false;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_name), Toast.LENGTH_SHORT).show();
        }
        if(shortTeamName.getText().toString().length() < 3){
            ok = false;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_short_name_min), Toast.LENGTH_SHORT).show();
        }
        if(shortTeamName.getText().toString().length() > 4){
            ok = false;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_short_name_max), Toast.LENGTH_SHORT).show();
        }
        if(stadiumName.getText().toString().length() < 3){
            ok = false;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_stadium_name), Toast.LENGTH_SHORT).show();
        }
        if(positionShield < 1){
            ok = false;
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_shield), Toast.LENGTH_SHORT).show();
        }
        return ok;
    }
    void postTeam(){

        Call<Team> call = svc.postTeam(buildPostTeamClass());
        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                final Team team = response.body();
                final Response<Team> responsefinal = response;
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, code = " + responsefinal.code());
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){

                                    Intent mainActivity = new Intent(CreateTeamActivity.this, MainActivity.class);
                                    mainActivity.putExtra("team", team);
                                    startActivity(mainActivity);
                                    finish();
                                }else{
                                    startActivity(new Intent(CreateTeamActivity.this, CreateTeamActivity.class));
                                    finish();
                                }
                            }

                    });
                } else {
                    try {
                        Log.e("error code :",response.toString() + "");
                        Log.e("error code :",response.code() + "");
                        Log.e("error:",response.message());
                        Log.v("Error ", response.errorBody().string());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    PostTeam buildPostTeamClass(){
        PostTeam team = new PostTeam();

        team.setName(teamName.getText().toString());
        team.setShort_name(shortTeamName.getText().toString());
        team.setStadium_name(stadiumName.getText().toString());
        team.setDevice_id(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        team.setShield(positionShield);
        team.setPrimary_color(String.format("#%06X", (0xFFFFFF & colorPrimary)));
        team.setSecondary_color(String.format("#%06X", (0xFFFFFF & colorSecundary)));

        return team;
    }

}
