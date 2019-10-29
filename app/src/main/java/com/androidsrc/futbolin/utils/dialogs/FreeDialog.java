package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.SellingPlayer;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.post.PostPlayerTransferible;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuy;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuyResponse;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.ShoppingItem;
import com.androidsrc.futbolin.database.models.Team;
import com.androidsrc.futbolin.utils.Defaultdata;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeDialog extends Dialog implements TwoButtonsDialog.DialogTwoButtonsClickListener{

    TextView title;
    TextView message;
    Button acceptButton,fulboButton,  closeButton;
    DialogFreeClickListener dialogClickListener;
    Player player;
    ProgressDialog dialog;
    Activity a;
    getUser user;
    TwoButtonsDialog dialogNotEnoughtCredits;
    FreeDialog freeDialog;
    String pattern;
    DecimalFormat decimalFormat;

    public FreeDialog(Activity a, Player player, DialogFreeClickListener dialogClickListener, getUser user){
        super(a);
        this.a = a;
        this.player = player;
        this.user = user;
        this.dialogClickListener = dialogClickListener;
        this.freeDialog = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_three_buttons);

        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);

        title = findViewById(R.id.dialog_threebuttons_title);
        message = findViewById(R.id.dialog_threebuttons_text);
        acceptButton = findViewById(R.id.dialog_threebuttons_accept_button);
        fulboButton = findViewById(R.id.dialog_threebuttons_fulbo_button);
        closeButton = findViewById(R.id.dialog_threebuttons_cancel_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onFreeButtonClick(false, user);
            }
        });
        title.setText(a.getResources().getString(R.string.dialog_free_1)+ " " + player.getFirst_name() + " " +
                player.getLast_name() + " " + a.getResources().getString(R.string.dialog_free_2));
        message.setText(a.getResources().getString(R.string.dialog_free_3) + " " + player.getShort_name() + " "
                + a.getResources().getString(R.string.dialog_free_4)+ " " + decimalFormat.format(player.getValue())
                + " " + a.getResources().getString(R.string.dialog_free_5)+ " "+
                "\n" + a.getResources().getString(R.string.dialog_free_6) +
                " " + player.getShort_name() + "?\n" +
                "\n" + a.getResources().getString(R.string.dialog_free_7) +
                " " + decimalFormat.format(user.getUser().getTeam().getFunds()) + " $\n" +
                "\n" + a.getResources().getString(R.string.dialog_free_8) +
                " " + user.getUser().getCredits());

        acceptButton.setText( a.getResources().getString(R.string.to_pay)+ " " + decimalFormat.format(player.getValue()) + "$");
        acceptButton.setTextSize(10);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getUser().getTeam().getFunds() >= player.getValue()){
                    postPlayerFree();
                }else{
                    Toast.makeText(a, a.getResources().getString(R.string.not_enough_funds)
                            + " " + player.getShort_name(), Toast.LENGTH_SHORT).show();
                }


            }
        });
        fulboButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getUser().getCredits() >= 1){
                    postShoppingBuy(player.getId());
                }else{
                    dialogNotEnoughtCredits = new TwoButtonsDialog(a, a.getResources().getString(R.string.buy_fulbos)
                            , a.getResources().getString(R.string.not_enough_fulbos_2_version) + "\n" +
                            "\n" + a.getResources().getString(R.string.buy_more_version_2) +
                            "", freeDialog,  Defaultdata.DIALOG_NOT_ENOUGH_CREDITS, null);
                    dialogNotEnoughtCredits.show();
                }
            }
        });
    }

    @Override
    public void onButtonClick(boolean confirm, int type, Object extra) {
        if(confirm){
            buyCreditsViaPayPal();
        }else{
            dialogNotEnoughtCredits.dismiss();
        }

    }


    public interface DialogFreeClickListener {
        void onFreeButtonClick(boolean free, getUser user);
    }

    public void postPlayerFree(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(a, "",
                a.getResources().getString(R.string.making_free_to) + " " +
                        player.getShort_name() + "...", true);
        SvcApi svc;

        svc = Svc.initAuth(a);

        PostPlayerTransferible postPlayerTransferible = new PostPlayerTransferible();
        postPlayerTransferible.setDevice_id(Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));


        Call<Player> call = svc.postPlayerFree(player.getId(), postPlayerTransferible);

        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                final Player u = response.body();
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
                                Toast.makeText(a, player.getShort_name() + " "  +
                                                a.getResources().getString(R.string.made_free_successfully) ,
                                        Toast.LENGTH_SHORT).show();
                                dialogClickListener.onFreeButtonClick(true, user);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            dialogClickListener.onFreeButtonClick(false, user);
                            Intent mainActivity = new Intent(a, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            a.startActivity(mainActivity);
                            a.finish();
                        }else{
                            Toast.makeText(a, error.split("\"message\":\"")[1], Toast.LENGTH_SHORT).show();
                            dialogClickListener.onFreeButtonClick(false, user);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(a,
                                a.getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    void postShoppingBuy(long player_id){

        SvcApi svcAth;
        svcAth = Svc.initAuth(getContext());
        PostShoppingBuy postShoppingBuy = new PostShoppingBuy();
        postShoppingBuy.setDevice_id(Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));
        postShoppingBuy.setId(5);
        postShoppingBuy.setPlayer_id(player_id);

        Call<PostShoppingBuyResponse> call = svcAth.postShoppingBuy(postShoppingBuy);
        call.enqueue(new Callback<PostShoppingBuyResponse>() {
            @Override
            public void onResponse(Call<PostShoppingBuyResponse> call, Response<PostShoppingBuyResponse> response) {

                final Response<PostShoppingBuyResponse> responsefinal = response;
                Log.e("getShoppingItems", responsefinal.toString());
                final PostShoppingBuyResponse getShoppingItems = response.body();
                if (response.isSuccessful()) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(responsefinal.code() == 400){
                                try {
                                    String error = responsefinal.errorBody().string();
                                    Toast.makeText(a, error.split("\"message\":\"")[1], Toast.LENGTH_SHORT).show();
                                }catch (Exception e){e.printStackTrace();}
                                // SUPONIENDO QUE ANTERIORMENTE HA HECHO LOGOUT, NO TIENE TOKEN !
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                user.getUser().setCredits(responsefinal.body().getCredits());
                                ((MainActivity)a).setUser(user);
                                if(dialogNotEnoughtCredits != null && dialogNotEnoughtCredits.isShowing()) {
                                    dialogNotEnoughtCredits.dismiss();
                                }
                                Toast.makeText(a, player.getShort_name() +
                                        " "+
                                        a.getResources().getString(R.string.made_free_successfully) , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    try {
                        String error = responsefinal.errorBody().string();
                        Toast.makeText(a, error.split("\"message\":\"")[1], Toast.LENGTH_SHORT).show();
                    }catch (Exception e){e.printStackTrace();}
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(),
                                a.getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostShoppingBuyResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    public void buyCreditsViaPayPal(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Defaultdata.URL_PAYPAL));
        a.startActivity(browserIntent);
    }
}
