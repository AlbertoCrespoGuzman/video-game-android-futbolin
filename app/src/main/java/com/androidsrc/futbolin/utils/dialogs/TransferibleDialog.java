package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.androidsrc.futbolin.communications.http.auth.patch.PatchPlayerValue;
import com.androidsrc.futbolin.communications.http.auth.post.PostPlayerTransferible;
import com.androidsrc.futbolin.database.models.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransferibleDialog extends Dialog {

    TextView title;
    TextView message;
    Button acceptButton, closeButton;
    DialogTransferibleClickListener dialogClickListener;
    String date;
    String messageTxt;
    Player player;
    ProgressDialog dialog;
    Activity a;

    public TransferibleDialog(Activity a, Player player, DialogTransferibleClickListener dialogClickListener){
        super(a);
        this.a = a;
        this.player = player;
        this.dialogClickListener = dialogClickListener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_two_buttons);
        long date = new Date().getTime() +  1000 * 60 * 60 * 24 * 7;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String datefinal =  sdf.format(date);

        title = findViewById(R.id.dialog_twobuttons_title);
        message = findViewById(R.id.dialog_twobuttons_text);
        acceptButton = findViewById(R.id.dialog_twobuttons_confirm_button);
        closeButton = findViewById(R.id.dialog_twobuttons_cancel_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onTransferibleButtonClick(false);
            }
        });
        title.setText(getContext().getResources().getString(R.string.transfarable_1) + " " + player.getFirst_name() + " " + player.getLast_name()
                + " " + getContext().getResources().getString(R.string.transfarable_2));
        message.setText(getContext().getResources().getString(R.string.transfarable_3) +
                " " + player.getShort_name() + " " + getContext().getResources().getString(R.string.transfarable_4)
                +" " + datefinal + "?\n" +
                "\n" + getContext().getResources().getString(R.string.transfarable_5) +
                " " + player.getValue() +" $.");
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postPlayerTransferible();
            }
        });
    }



    public interface DialogTransferibleClickListener {
        void onTransferibleButtonClick(boolean transfered);
    }

    public void postPlayerTransferible(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(a, "",
                getContext().getResources().getString(R.string.making_player_transfarable)
                , true);
        SvcApi svc;

        svc = Svc.initAuth(a);

        PostPlayerTransferible postPlayerTransferible = new PostPlayerTransferible();
        postPlayerTransferible.setDevice_id(Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));


        Call<SellingPlayer> call = svc.postPlayerTransferible(player.getId(), postPlayerTransferible);

        call.enqueue(new Callback<SellingPlayer>() {
            @Override
            public void onResponse(Call<SellingPlayer> call, Response<SellingPlayer> response) {
                final SellingPlayer user = response.body();
                final Response<SellingPlayer> responsefinal = response;

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, transsferible = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                Toast.makeText(a, player.getShort_name() + " "
                                        + getContext().getResources().getString(R.string.is_transfarable), Toast.LENGTH_SHORT).show();
                                dialogClickListener.onTransferibleButtonClick(true);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            dialogClickListener.onTransferibleButtonClick(false);
                            Intent mainActivity = new Intent(a, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            a.startActivity(mainActivity);
                            a.finish();
                        }else{
                            Toast.makeText(a, error.split("\"message\":\"")[1], Toast.LENGTH_SHORT).show();
                            dialogClickListener.onTransferibleButtonClick(false);
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
            public void onFailure(Call<SellingPlayer> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
