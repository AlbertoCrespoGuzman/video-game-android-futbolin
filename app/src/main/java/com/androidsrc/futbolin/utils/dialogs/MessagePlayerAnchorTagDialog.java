package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.androidsrc.futbolin.R;

import android.app.Activity;
        import android.app.Dialog;
        import android.os.Bundle;
        import android.view.View;
        import android.view.Window;
        import android.widget.Button;
        import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.getPlayer;
import com.androidsrc.futbolin.communications.http.auth.get.market.MarketObject;
import com.androidsrc.futbolin.database.models.Player;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MessagePlayerAnchorTagDialog extends Dialog implements PlayerDialog.DialogClickListener{

    TextView title;
    TextView message1, message2;
    Button closeButton, playerButton;
    DialogPlayerAnchorTagClickListener dialogClickListener;
    String titleTxt;
    String messageTxt;
    ProgressDialog dialog;
    Activity a;
    PlayerDialog playerDialog;
    MessagePlayerAnchorTagDialog dialogPlayerAnchorTag;

    public MessagePlayerAnchorTagDialog(Activity a, String title, String text, DialogPlayerAnchorTagClickListener dialogClickListener){
        super(a);
        this.a = a;
        this.titleTxt = title;
        this.messageTxt = text;
        this.dialogClickListener = dialogClickListener;
        dialogPlayerAnchorTag = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Log.e("MESSAGE", messageTxt);
        if(messageTxt.contains("<a href=\"/jugador/")){
            setContentView(R.layout.dialog_player_tag_message);
            title = findViewById(R.id.dialog_message_title);
            message1 = findViewById(R.id.dialog_message_text_1);
            message2 = findViewById(R.id.dialog_message_text_2);
            playerButton = findViewById(R.id.dialog_player_anchor_tag_player_button);
            closeButton = findViewById(R.id.dialog_message_button);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogClickListener.onButtonPlayerAnchorTagClick();
                }
            });
            title.setText(titleTxt);

            message1.setText(Html.fromHtml(getMessagePart1()));
            message2.setText(Html.fromHtml(getMessagePart2()));
            playerButton.setText(getPlayerName());
            playerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPlayer(getPlayerId());
                }
            });

        }else
        {
            setContentView(R.layout.dialog_message);

            title = findViewById(R.id.dialog_message_title);
            message1 = findViewById(R.id.dialog_message_text);
            closeButton = findViewById(R.id.dialog_message_button);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogClickListener.onButtonPlayerAnchorTagClick();
                }
            });
            title.setText(titleTxt);
            if(messageTxt.contains("<") && messageTxt.contains(">")){
                message1.setText(Html.fromHtml(messageTxt));
            }else{
                message1.setText(messageTxt);
            }

        }


    }

    @Override
    public void onButtonClick() {
        if(playerDialog != null && playerDialog.isShowing()){
            playerDialog.dismiss();
        }
    }


    public interface DialogPlayerAnchorTagClickListener {
        void onButtonPlayerAnchorTagClick();
    }
    public void getPlayer(final long player_id){



        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = ProgressDialog.show(a, "",
                getContext().getResources().getString(R.string.loading_data_from)
                + " " + getPlayerName(), true);
        SvcApi svc;
        svc = Svc.initAuth(a);
        Call<getPlayer> call = svc.getPlayer(player_id, Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getPlayer>() {
            @Override
            public void onResponse(Call<getPlayer> call, Response<getPlayer> response) {

                final Response<getPlayer> responsefinal = response;

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
                                Player player1 = responsefinal.body().getPlayer();
                                Log.e("Response player", player1.toString());
                                if(player1.getTeam_id() != ((MainActivity)a).getUser().getUser().getTeam().getId()) {
                                    playerDialog = new PlayerDialog(a, player1, ((MainActivity)a).getUser(), dialogPlayerAnchorTag, true, null);
                                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    playerDialog.show();
                                }else{
                                    playerDialog = new PlayerDialog(a, player1, ((MainActivity)a).getUser(), dialogPlayerAnchorTag, false, null);
                                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    playerDialog.show();
                                }
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(a, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            a.startActivity(mainActivity);
                            a.finish();
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
            public void onFailure(Call<getPlayer> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    String getMessagePart1(){
        String text = "";
        text = messageTxt.split("<a")[0];
        return text;
    }
    String getMessagePart2(){
        String text = "";
        text = messageTxt.split("</a>")[1];
        return text;
    }
    String getPlayerName(){
        String text = "";

        text = messageTxt.split("</a>")[0];
        text = text.split("\">")[1];
        return text;
    }
    long getPlayerId(){
        long id = 0;
        String text= "";
        text = messageTxt.split("/jugador/")[1];
        text = text.split("/")[0];
        id = Long.parseLong(text);
        return id;
    }
}
