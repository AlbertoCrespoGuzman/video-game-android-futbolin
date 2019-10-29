package com.androidsrc.futbolin.activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.ResponseError;
import com.androidsrc.futbolin.communications.http.auth.ResponseErrors;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;

import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Notification;
import com.androidsrc.futbolin.database.models.User;
import com.androidsrc.futbolin.communications.http.auth.AndroiduserRegistrationResponse;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.LocaleLanguageHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Locale;
import java.util.Random;

/**
 * Created by alberto on 2017/12/05.
 */

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText firstnameInput, lastnameInput, emailInput, passwordInput, passwordconfirmInput;
    Button submitButton;
    SvcApi svc, svcAth;
    ProgressDialog dialog;
    DatabaseManager db;
    ConstraintLayout layout;
    AppCompatSpinner languageSpinner;
    String languageChanged = "";
    int languageHasBeenSelected = 0;
    boolean langChanged = false;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        layout =  findViewById(R.id.activity_register_layout);
        layout.setBackgroundResource(Defaultdata.images.get((new Random()).nextInt((8 - 0) + 1) + 0));

        firstnameInput = findViewById(R.id.activity_register_input_first_name);
        lastnameInput = findViewById(R.id.activity_register_input_last_name);
        emailInput = findViewById(R.id.activity_register_input_email);
        passwordInput = findViewById(R.id.activity_register_input_password);
        passwordconfirmInput = findViewById(R.id.activity_register_input_password_confirm);
        languageSpinner = findViewById(R.id.activity_register_input_language_spinner);


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//To do//
                            return;
                        }

// Get the Instance ID token//
                        token = task.getResult().getToken();
                        String msg = getString(R.string.fcm_token, token);
                        Log.e("MAINACTIVITY", "TOKEN !!!!v    --->> "+ token);
                        Log.e("MAINACTIVITY", msg);

                    }
                });


        submitButton = findViewById(R.id.activity_register_input_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filter()) {
                    // get device_id && device_name
                    registerUser(buildRegistrationModel());
                    // connect Register
                }
            }
        });
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lang = "es";
                switch (position){
                    case 0:
                        lang = "es";
                        break;
                    case 1:
                        lang = "en";
                        break;
                    case 2:
                        lang = "pt";
                        Log.e("switch","lang = " + lang);
                        break;
                    case 3:
                        lang = "ca";
                        Log.e("switch","lang = " + lang);
                        break;
                }
                if(languageHasBeenSelected >= 1){

                    setLanguage(lang);
                }else{
                    Log.e("ei ma", "ey ma");
                    languageHasBeenSelected ++;
                    if(Locale.getDefault().getDisplayLanguage().startsWith("espa")){
                        languageSpinner.setSelection(0);
                        langChanged = true;
                    }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("english")){
                        languageSpinner.setSelection(1);
                    }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("por")){
                        languageSpinner.setSelection(2);
                    }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("cat")){
                        languageSpinner.setSelection(3);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if(languageChanged != null){
            savedInstanceState.putString("language", languageChanged);
        }
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null &&  savedInstanceState.getString("language") != null){
            LocaleLanguageHelper.setLocale(getApplicationContext(), savedInstanceState.getString("language"));
        }

    }
    public void setLanguage(String lang){
        languageChanged = lang;
        LocaleLanguageHelper.setLocale(getApplicationContext(), lang);
        if(langChanged){
            startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
            finish();
        }
        if(languageHasBeenSelected >= 1){
            Log.e("Register", "languageHasBeenSelected = " + languageHasBeenSelected + " | langChanged = " + langChanged);
            langChanged = true;

        }
        //         recreate();
    }

    @Override
    public void onResume(){
        super.onResume();
        svc = Svc.initRegisterLogin();
        db = new DatabaseManager(getApplicationContext());
    }

    private User buildRegistrationModel() {

        User au = new User();

        au.setFirst_name(firstnameInput.getText().toString());
        au.setLast_name(lastnameInput.getText().toString());
        au.setEmail(emailInput.getText().toString());
        au.setPassword(passwordInput.getText().toString());
        au.setPassword_confirmation(passwordconfirmInput.getText().toString());
        au.setDevice_id(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        au.setDevice_name(android.os.Build.MODEL);
        au.setLanguage(languageChanged);
        au.setPush_notification_token(token);

        return au;
    }

    private boolean filter() {
        String firstname = firstnameInput.getText().toString();
        String lastname = lastnameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String passwordconfirm = passwordconfirmInput.getText().toString();

        if (firstname.length() == 0) {
            firstnameInput.setError(getResources().getString(R.string.error_name_2));
            return false;
        } else if (lastname.length() == 0) {
            lastnameInput.setError(getResources().getString(R.string.error_surname));
            return false;
        }else if (!email.contains("@") || !email.contains(".")) {
            emailInput.setError(getResources().getString(R.string.error_email));
            return false;
        } else if (!password.equals(passwordconfirm)) {
            passwordInput.setError(getResources().getString(R.string.error_password_do_not_match));
            return false;
        } else if (password.length() < 6) {
            passwordInput.setError(getResources().getString(R.string.error_password));
            return false;
        }
        return true;
    }


    private void registerUser(User au){
        if(dialog == null){
            dialog = new ProgressDialog(RegisterActivity.this);
            dialog.setTitle(getResources().getString(R.string.wait));
            dialog.show();
        }
        final User au_final = au;
        Log.e("Register", au_final.toString());
        Call<AndroiduserRegistrationResponse> call = svc.registerAndroiduser(au);
        call.enqueue(new Callback<AndroiduserRegistrationResponse>() {
            @Override
            public void onResponse(Call<AndroiduserRegistrationResponse> call, Response<AndroiduserRegistrationResponse> response) {
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }
                final AndroiduserRegistrationResponse aur = response.body();
                final Response<AndroiduserRegistrationResponse> responsefinal = response;
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, register Response body = " + aur.toString());
                            if(responsefinal.code() == 400){
                                showErrorsRegistrationForm(aur.getErrors());
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                updateAndSaveAndroiduser(au_final, aur);
                                changeActivity();
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
            public void onFailure(Call<AndroiduserRegistrationResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void showErrorsRegistrationForm(ResponseErrors errors){

        for(ResponseError re : errors.getErrors()){
            Toast.makeText(getApplicationContext(), re.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void updateAndSaveAndroiduser(User au, AndroiduserRegistrationResponse aur){

        au.setToken(aur.getToken());
        au.setFirst_name(aur.getFirst_name());
        au.setLast_name(aur.getLast_name());
        au.setEmail(aur.getEmail());
        au.setLogged(true);
        au.setLanguage(aur.getLanguage());
        db.saveUser(au);
        Notification nt = db.findNotification();
        nt.setLanguage(aur.getLanguage());
        db.saveNotification(nt);

    }
    private void changeActivity(){

        svcAth = Svc.initAuth(getApplicationContext());
        Call<getUser> call = svcAth.getMe(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getUser>() {
            @Override
            public void onResponse(Call<getUser> call, Response<getUser> response) {
                final getUser user = response.body();
                final Response<com.androidsrc.futbolin.communications.http.auth.get.getUser> responsefinal = response;
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, code = " + responsefinal.code());
                            System.out.println("Success, body = " + responsefinal.body());
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                               if(user.getUser().getTeam() != null){
                                   Intent mainActivity = new Intent(RegisterActivity.this, MainActivity.class);
                                   mainActivity.putExtra("user", user);
                                   startActivity(mainActivity);
                                   finish();
                               }else{
                                   startActivity(new Intent(RegisterActivity.this, CreateTeamActivity.class));
                                   finish();
                               }
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
            public void onFailure(Call<getUser> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

}

