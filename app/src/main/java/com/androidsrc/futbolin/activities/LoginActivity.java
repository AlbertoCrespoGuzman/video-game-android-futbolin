package com.androidsrc.futbolin.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.androidsrc.futbolin.database.models.User;
import com.androidsrc.futbolin.communications.http.auth.AndroiduserLogin;
import com.androidsrc.futbolin.communications.http.auth.AndroiduserLoginResponse;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.LocaleLanguageHelper;
import com.androidsrc.futbolin.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Locale;
import java.util.Random;

/**
 * Created by alberto on 2017/12/05.
 */

public class LoginActivity extends AppCompatActivity {

    Button loginButton,registerButton;
    EditText emailInput, passwordInput;
    DatabaseManager db;
    SvcApi svc, svcAth;
    ProgressDialog dialog;
    ConstraintLayout layout;
    String languageChanged = "";
    AppCompatSpinner languageSpinner;
    int languageHasBeenSelected = 0;
    boolean langChanged = false;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        db = new DatabaseManager(getApplicationContext());
        layout = findViewById(R.id.activity_login_layout);
        loginButton = findViewById(R.id.activity_login_login_button);
        registerButton = findViewById(R.id.activity_login_register_button);
        emailInput = findViewById(R.id.activity_login_input_email);
        passwordInput = findViewById(R.id.activity_login_input_password);
        languageSpinner = findViewById(R.id.activity_login_input_language_spinner);

        //Utils.setLanguage(getApplicationContext(), savedInstanceState);


        layout.setBackgroundResource(Defaultdata.images.get((new Random()).nextInt((8 - 0) + 1) + 0));

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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterInput()){
                    hideKeyboard(LoginActivity.this);
                    loginAndroiduser(createLoginForm());
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
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
                        break;
                }
                if(languageHasBeenSelected >= 1){
                    setLanguage(lang);
                }else{
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
                  /*  if(db.findNotification().getLanguage().equals("es")){
                        languageSpinner.setSelection(0);
                        langChanged = true;
                    }else if(db.findNotification().getLanguage().equals("en")){
                        languageSpinner.setSelection(1);
                    }else if(db.findNotification().getLanguage().equals("pt")){
                        languageSpinner.setSelection(2);
                    }else if(db.findNotification().getLanguage().equals("ca")){
                        languageSpinner.setSelection(3);
                    } */
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fillLoginInputs();
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
                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                finish();
            }
            if(languageHasBeenSelected >= 1){
                langChanged = true;

            }
   //         recreate();
    }
    @Override
    public void onResume(){
        super.onResume();
        svc = Svc.initRegisterLogin();
        svcAth = Svc.initAuth(getApplicationContext());
        db = new DatabaseManager(getApplicationContext());
    }

    /*
        Filling Login Form
     */
    public AndroiduserLogin createLoginForm(){
        AndroiduserLogin aul = new AndroiduserLogin();

        aul.setEmail(emailInput.getText().toString());
        aul.setPassword(passwordInput.getText().toString());
        aul.setDevice_id(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        aul.setDevice_name(android.os.Build.MODEL);
        aul.setPush_notification_token(token);

        return aul;
    }
    /*
        filling Inputs from Login form
     */
    private void fillLoginInputs(){
        if(db.ExistsUser()){
        //    emailInput.setText(db.findUser().getEmail());
        //    passwordInput.setText(db.findUser().getPassword());
        }

    }
    /*
     Filtering login form
     */
    private boolean filterInput(){
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(!email.contains("@") || !email.contains(".")){
            emailInput.setError(getResources().getString(R.string.error_email));
            return false;
        }else{
            if(password.length() < 6){
                passwordInput.setError(getResources().getString(R.string.error_password));
                return false;
            }
        }

        return true;
    }
    /*
        Login Connection to Server
     */
    private void loginAndroiduser(AndroiduserLogin au){
        if(dialog == null){
//            dialog = Defaultdata.loadDialog(dialog, "Espere", "" , getApplicationContext());
//            dialog.show();
        }
        final AndroiduserLogin au_final = au;
        Call<AndroiduserLoginResponse> call = svc.loginAndroiduser(au);
        call.enqueue(new Callback<AndroiduserLoginResponse>() {
            @Override
            public void onResponse(Call<AndroiduserLoginResponse> call, Response<AndroiduserLoginResponse> response) {
                final Response responsefinal =  response;
                final AndroiduserLoginResponse aur = response.body();
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, code = " + aur.
                            toString());
                            if(responsefinal.code()== 400){
                                showErrorsLoginForm(aur.getErrors());
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                updateAndSaveAndroiduser(aur);
                                changeActivity();
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            startActivity(mainActivity);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), error.split("message")[1], Toast.LENGTH_SHORT).show();
                        }
                        //    Log.e("error code :",response.toString() + "");
                        //    Log.e("error code :",response.code() + "");
                        //    Log.e("error:",response.message());
                        //   Log.v("Error ", response.errorBody().string());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(response.code() == 500 || response.code() == 503){
                        System.out.println(response.toString());
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AndroiduserLoginResponse> call, Throwable t) {
                t.printStackTrace();
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void showErrorsLoginForm(ResponseErrors errors){

        for(ResponseError re : errors.getErrors()){
            Toast.makeText(getApplicationContext(), re.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void updateAndSaveAndroiduser(AndroiduserLoginResponse aur){

        User au = db.findUser();
        if(au == null){
            au = new User();
        }
        au.setToken(aur.getToken());
        au.setEmail(emailInput.getText().toString());
        au.setLogged(true);

        db.saveUser(au);
    }
    private void changeActivity(){
        if(dialog == null){
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setTitle(getResources().getString(R.string.wait));
            dialog.setMessage(getResources().getString(R.string.loading_team_data));
            dialog.show();
        }
        svcAth = Svc.initAuth(getApplicationContext());
        Call<getUser> call = svcAth.getMe(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getUser>() {
            @Override
            public void onResponse(Call<getUser> call, Response<getUser> response) {
                final getUser user = response.body();
                final Response<com.androidsrc.futbolin.communications.http.auth.get.getUser> responsefinal = response;
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, user = " + responsefinal.body().getUser().toString());
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                if(user.getUser().getTeam() != null){
                                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                                    mainActivity.putExtra("user", user);
                                    startActivity(mainActivity);
                                    finish();
                                }else{
                                    startActivity(new Intent(LoginActivity.this, CreateTeamActivity.class));
                                    finish();
                                }
                            }
                        }
                    });
                } else {


                    try {
                        String error =response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                            getUser uuser = new getUser();

                            uuser.setUser(new com.androidsrc.futbolin.communications.http.auth.get.getUser.User());
                            uuser.getUser().setFirstName("MATCH_LIVE");
                               mainActivity.putExtra("user", uuser);

                            startActivity(mainActivity);
                            finish();
                        }else{
                            Log.e("error","error " + error);
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
            public void onFailure(Call<getUser> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
