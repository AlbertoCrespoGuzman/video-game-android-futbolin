package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.ResponsePostFollow;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.get.market.MarketObject;
import com.androidsrc.futbolin.communications.http.auth.patch.PatchPlayerValue;
import com.androidsrc.futbolin.communications.http.auth.post.PostMarketFollow;
import com.androidsrc.futbolin.communications.http.auth.post.PostPlayerOffer;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.TeamDB;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImproveDialog extends Dialog {

    TextView title;
    TextView message, salary;
    EditText valueInput;
    Button acceptButton, closeButton;
    DialogImproveClickListener dialogClickListener;
    Player player;
    Activity a;
    ProgressDialog dialog;
    boolean isMarket;
    MarketObject marketObject;
    String pattern;
    DecimalFormat decimalFormat;
    DatabaseManager db;
    long paymentLimit;
    Button followButton;

    public ImproveDialog(Activity a, Player player, DialogImproveClickListener dialogClickListener, boolean isMarket, MarketObject marketObject){
        super(a);
        this.dialogClickListener = dialogClickListener;
        this.a = a;
        this.player = player;
        this.isMarket = isMarket;
        this.marketObject = marketObject;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(a.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.dialog_improve_player);
        }else{
            setContentView(R.layout.dialog_improve_player_landscape);
        }


        pattern = "###,###,###,###.##";
        decimalFormat = new DecimalFormat(pattern);
        paymentLimit = 15000000;
        if(((MainActivity)a).getUser().getUser().getTeam().getSpending_margin() < paymentLimit && ((MainActivity)a).getUser().getUser().getTeam().getSpending_margin() > 0){
            paymentLimit =((MainActivity)a).getUser().getUser().getTeam().getSpending_margin();
        }

        title = findViewById(R.id.dialog_improve_title);
        message = findViewById(R.id.dialog_improve_text);
        valueInput = findViewById(R.id.dialog_improve_value_input);
        salary = findViewById(R.id.dialog_improve_salary_value);
        acceptButton = findViewById(R.id.dialog_improve_accept_button);

        if(!isMarket){
                title.setText(getContext().getResources().getString(R.string.improve_contract_1) + " " + player.getFirst_name() + " " + player.getLast_name());
                message.setText(getContext().getResources().getString(R.string.improve_contract_2)  + " " + player.getShort_name() +
                        getContext().getResources().getString(R.string.improve_contract_3)  + "  " +
                        "\n" + getContext().getResources().getString(R.string.improve_contract_4) +
                        "");


                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (filterMaxNoMarket()) {
                            patchPlayerValue();
                        } else {
                            Toast.makeText(a, getContext().getResources().getString(R.string.error_improve_contract)
                                    + " " + decimalFormat.format(paymentLimit) + " $)", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }else {
            followButton = findViewById(R.id.dialog_improve_follow_button);

            if (marketObject != null){
                followButton.setVisibility(View.VISIBLE);
                if(marketObject.isFollowing()){
                    followButton.setText(getContext().getResources().getString(R.string.stop_following));
                }else{
                    followButton.setText(getContext().getResources().getString(R.string.start_following));
                }

                followButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(marketObject.isFollowing()) {
                            postMarketUnfollow();
                        }else{
                            postMarketFollow();
                        }
                    }
                });

                title.setText(getContext().getResources().getString(R.string.make_offer) + " " + player.getFirst_name() + " " + player.getLast_name());
                if(marketObject.getOffer_team() == 0){
                    message.setText(getContext().getResources().getString(R.string.make_offer_1) +
                            " " + player.getShort_name() + " \n" +
                            "\n" + getContext().getResources().getString(R.string.make_offer_2) +
                            " " + decimalFormat.format(marketObject.getValue()) + " $");

                }else {
                    message.setText(getContext().getResources().getString(R.string.make_offer_1) +
                            " " + player.getShort_name() + " \n" +
                            "\n" + getContext().getResources().getString(R.string.make_offer_2) +
                            " " + decimalFormat.format(marketObject.getOffer_value()) + " $ ("+getTeamName(marketObject.getOffer_team()) +")");
                }



            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (filterMax()) {
                        postPlayerOffer();
                    } else {
                        Toast.makeText(a, getContext().getResources().getString(R.string.error_improve_contract_1) +
                                " " + decimalFormat.format(marketObject.getOffer_value()) + " " + getContext().getResources().getString(R.string.and) + " "  + decimalFormat.format(paymentLimit) + "$", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if (marketObject.getOffer_value() == 0) {
                valueInput.setText(String.valueOf(Math.round(marketObject.getValue() * 1.05)));
            } else {
                valueInput.setText(String.valueOf(Math.round(marketObject.getOffer_value() * 1.05)));
            }
        }else{
                title.setText(getContext().getResources().getString(R.string.make_offer_1) +
                        " " + player.getFirst_name() + " " + player.getLast_name());
                message.setText(getContext().getResources().getString(R.string.make_offer_2) + " " + player.getShort_name() + " \n" +
                        "\n" + getContext().getResources().getString(R.string.error_improve_contract_1) +
                        " " + decimalFormat.format(player.getSelling().getBest_offer_value()) + " $ ("+getTeamName(player.getSelling().getBest_offer_team()) +")");


                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (filterMax()) {
                            postPlayerOffer();
                        } else {
                            Toast.makeText(a, getContext().getResources().getString(R.string.value_between) +
                                    " " + decimalFormat.format(player.getSelling().getBest_offer_value())
                                    + " " + getContext().getResources().getString(R.string.and) + " " + decimalFormat.format(paymentLimit) + "$", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                if (player.getSelling().getBest_offer_value() == 0) {
                    valueInput.setText(String.valueOf(Math.round(player.getValue() * 1.05)));
                } else {
                    valueInput.setText(String.valueOf(Math.round(player.getSelling().getBest_offer_value() * 1.05)));
                }
            }

        }

        salary.setText(  decimalFormat.format( Integer.parseInt(valueInput.getText().toString()) * 1.5 / 100) + " $");
        closeButton = findViewById(R.id.dialog_improve_cancel_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onDialogImproveButtonClick(false, 0, false);
            }
        });

        valueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(valueInput.getText().toString().length() > 0 && Long.parseLong(valueInput.getText().toString()) > 0) {
                    salary.setText( decimalFormat.format(Integer.parseInt(valueInput.getText().toString()) * 1.5 / 100));
                }else if(valueInput.getText().toString().length() > 1 && valueInput.getText().toString().startsWith("0")){
                    valueInput.setText(valueInput.getText().toString().substring(1,valueInput.getText().toString().length()-1));
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });


    }



    public interface DialogImproveClickListener {
        void onDialogImproveButtonClick(boolean success, long value, boolean follow);
    }
    boolean filterMaxNoMarket(){
        boolean ok = true;

        if(Integer.parseInt(valueInput.getText().toString()) > 1500000 || Integer.parseInt(valueInput.getText().toString()) < 100000){
            ok = false;
        }
        return ok;
    }
    boolean filterMax(){
        boolean ok = true;

        if(Integer.parseInt(valueInput.getText().toString()) > paymentLimit || Integer.parseInt(valueInput.getText().toString()) < 100000){
            ok = false;
        }
        return ok;
    }
    public void patchPlayerValue(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(a, "",
                getContext().getResources().getString(R.string.making_offer) +
                        "", true);
        SvcApi svc;

        svc = Svc.initAuth(a);

        PatchPlayerValue patchPlayerValue = new PatchPlayerValue();
        patchPlayerValue.setDevice_id(Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));
        patchPlayerValue.setValue(Long.parseLong(valueInput.getText().toString()));

        Call<Player> call = svc.pathPlayerValue(player.getId(), patchPlayerValue);

        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                final Player user = response.body();
                final Response<Player> responsefinal = response;

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMe = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                Toast.makeText(a,
                                        getContext().getResources().getString(R.string.salary_improved), Toast.LENGTH_SHORT).show();
                                dialogClickListener.onDialogImproveButtonClick(true, Long.parseLong(valueInput.getText().toString()), false);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            dialogClickListener.onDialogImproveButtonClick(false, 0, false);
                            Intent mainActivity = new Intent(a, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            a.startActivity(mainActivity);
                            a.finish();
                        }else{
                            Log.e("ERROR","ERROR -> " + error);
                            if(error.split(Pattern.quote("message")).length > 1){
                                Toast.makeText(a, error.split(Pattern.quote("message"))[1], Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(a,
                                        getContext().getResources().getString(R.string.error_verify_values) , Toast.LENGTH_SHORT).show();
                            }
                            dialogClickListener.onDialogImproveButtonClick(false, 0, false);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(a,
                                getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void postPlayerOffer(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(a, "",
                getContext().getResources().getString(R.string.making_offer), true);
        SvcApi svc;

        svc = Svc.initAuth(a);

        PostPlayerOffer postPlayerOffer = new PostPlayerOffer();
        postPlayerOffer.setDevice_id(Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));
        postPlayerOffer.setOffer(Long.parseLong(valueInput.getText().toString()));
        postPlayerOffer.setPlayer_id(player.getId());

        Call<Void> call = svc.postPlayerOffer(postPlayerOffer);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                final Response<Void> responsefinal = response;

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMe = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200|| responsefinal.code() == 204){
                                Toast.makeText(a, getContext().getResources().getString(R.string.offer_made), Toast.LENGTH_SHORT).show();
                                dialogClickListener.onDialogImproveButtonClick(true, Long.parseLong(valueInput.getText().toString()), false);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            dialogClickListener.onDialogImproveButtonClick(false, 0, false);
                            Intent mainActivity = new Intent(a, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            a.startActivity(mainActivity);
                            a.finish();
                        }else{
                            Log.e("ERROR","ERROR -> " + error);
                            if(error.split(Pattern.quote("message")).length > 1){
                                Toast.makeText(a, error.split(Pattern.quote("message"))[1], Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(a, getContext().getResources().getString(R.string.error_verify_values), Toast.LENGTH_SHORT).show();
                            }
                            dialogClickListener.onDialogImproveButtonClick(false, 0, false);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(a, getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    public String getTeamName(long id){
        String text = "";
        if(db == null){
            db = new DatabaseManager(getContext());
        }

        TeamDB teamDB = db.findTeamDBById(id);
        if(teamDB != null){
            text = teamDB.getShort_name();
        }
        return text;
    }

    public void postMarketFollow(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(a, "",
                getContext().getResources().getString(R.string.finishing_to_follow), true);
        SvcApi svc;

        svc = Svc.initAuth(a);

        PostMarketFollow postMarketFollow = new PostMarketFollow();
        postMarketFollow.setDevice_id(Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));
        postMarketFollow.setId(marketObject.getId());
        Log.e("device id",postMarketFollow.getDevice_id());
        Log.e("transfer id",postMarketFollow.getId() + "");
        Call<ResponsePostFollow> call = svc.postMarketFollow(postMarketFollow);

        call.enqueue(new Callback<ResponsePostFollow>() {
            @Override
            public void onResponse(Call<ResponsePostFollow> call, Response<ResponsePostFollow> response) {

                final Response<ResponsePostFollow> responsefinal = response;

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, Follow player = " + responsefinal);
                            if(responsefinal.code() == 400){
                                Toast.makeText(a,
                                        getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200|| responsefinal.code() == 204){
                                Toast.makeText(a, getContext().getResources().getString(R.string.started_following_to) +
                                        " " + marketObject.getPlayer().getName(), Toast.LENGTH_SHORT).show();
                                dialogClickListener.onDialogImproveButtonClick(true, responsefinal.body().getId(), true);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            dialogClickListener.onDialogImproveButtonClick(false, 0, true);
                            Intent mainActivity = new Intent(a, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            a.startActivity(mainActivity);
                            a.finish();
                        }else{
                            Log.e("ERROR","ERROR -> " + error);
                            if(error.split(Pattern.quote("message")).length > 1){
                                Toast.makeText(a, error.split(Pattern.quote("message"))[1], Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(a, getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                            }
                            dialogClickListener.onDialogImproveButtonClick(false, 0, true);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(a, getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostFollow> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }


    public void postMarketUnfollow(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(a, "",
                getContext().getResources().getString(R.string.stoping_follow_player)
                , true);
        SvcApi svc;

        svc = Svc.initAuth(a);

        PostMarketFollow postMarketFollow = new PostMarketFollow();
        postMarketFollow.setDevice_id(Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));
        postMarketFollow.setId(marketObject.getId());
        Call<ResponsePostFollow> call = svc.postMarketUnfollow(postMarketFollow);

        call.enqueue(new Callback<ResponsePostFollow>() {
            @Override
            public void onResponse(Call<ResponsePostFollow> call, Response<ResponsePostFollow> response) {

                final Response<ResponsePostFollow> responsefinal = response;

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, Unfollow player = " + responsefinal);
                            if(responsefinal.code() == 400){
                                Toast.makeText(a, getContext().getResources().getString(R.string.error_try_again)
                                        , Toast.LENGTH_SHORT).show();
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200|| responsefinal.code() == 204){
                                Toast.makeText(a, getContext().getResources().getString(R.string.not_following_more_to)
                                        + " " + marketObject.getPlayer().getName(), Toast.LENGTH_SHORT).show();
                                dialogClickListener.onDialogImproveButtonClick(true, responsefinal.body().getId(), true);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            dialogClickListener.onDialogImproveButtonClick(false, 0, true);
                            Intent mainActivity = new Intent(a, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            a.startActivity(mainActivity);
                            a.finish();
                        }else{
                            Log.e("ERROR","ERROR -> " + error);
                            if(error.split(Pattern.quote("message")).length > 1){
                                Toast.makeText(a, error.split(Pattern.quote("message"))[1], Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(a, getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                            }

                            dialogClickListener.onDialogImproveButtonClick(false, 0, true);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(a, getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostFollow> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

}
