package com.androidsrc.futbolin.utils.dialogs;


import android.app.Activity;
        import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
        import android.view.Window;
        import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

        import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.SystemNotification;
import com.androidsrc.futbolin.utils.Defaultdata;

import java.text.DecimalFormat;
import java.util.List;


public class PlayersNotificationDialog extends Dialog implements PlayerDialog.DialogClickListener{

    TextView title;
    TextView message;
    Button closeButton;
    DialogPlayersNotificationClickListener dialogClickListener;
    TableLayout table;
    SystemNotification notifications;
    String pattern;
    DecimalFormat decimalFormat;
    PlayerDialog playerDialog;
    Activity a;
    PlayersNotificationDialog playersNotificationDialog;


    public PlayersNotificationDialog(Activity a, SystemNotification notifications, DialogPlayersNotificationClickListener dialogClickListener){
        super(a);
        this.dialogClickListener = dialogClickListener;
        this.notifications = notifications;
        this.a = a;
        playersNotificationDialog  = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notifications_all);

        title = findViewById(R.id.dialog_notifications_title);
        table = findViewById(R.id.dialog_notifications_table);
        closeButton = findViewById(R.id.dialog_notifications_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onButtonPlayersNotificationClick();
            }
        });

        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);


        title.setText(getContext().getResources().getString(R.string.players));

        if(notifications.getTransferables().size() > 0){
            TableRow row = new TableRow(getContext());

            TextView title = new TextView(getContext());
            title.setText(getContext().getResources().getString(R.string.transfarable_players));
            title.setTextSize(12);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(title);
            table.addView(row);
        }
        for(final Player player : notifications.getTransferables()){
            TableRow row1 = new TableRow(getContext());

            TextView name = new TextView(getContext());
            name.setText(player.getNumber() + " " + Html.fromHtml(player.getName()));
            name.setTypeface(name.getTypeface(), Typeface.BOLD);
            name.setTextColor(Color.parseColor("#000000"));
            name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            name.setTextSize(11);

            row1.addView(name);
            row1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    playerDialog.show();
                }
            });


            TableRow row2 = new TableRow(getContext());

            TextView position = new TextView(getContext());
            position.setText(player.getPosition());
            position.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            position.setTextSize(10);

            row2.addView(position);
            row2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });

            TableRow row3 = new TableRow(getContext());

            TextView offers = new TextView(getContext());
            if(player.getSelling() != null){
                if(player.getSelling().getBest_offer_team() != 0 ){
                    offers.setText(getContext().getResources().getString(R.string.best_offer)+
                            "" + decimalFormat.format(player.getSelling().getBest_offer_value()) + " $");
                }else{
                    offers.setText(getContext().getResources().getString(R.string.no_offer));
                }
            }else{
                offers.setText(getContext().getResources().getString(R.string.no_offer));
            }

            offers.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            offers.setTextSize(10);

            row3.addView(offers);
            row3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });

            table.addView(row1);
            table.addView(row2);
            table.addView(row3);
        }
        if(notifications.getInjuries().size() > 0){
            TableRow row = new TableRow(getContext());

            TextView title = new TextView(getContext());
            title.setText(getContext().getResources().getString(R.string.injured_players));
            title.setTextSize(12);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(title);
            table.addView(row);
        }
        for(final Player player : notifications.getInjuries()){
            TableRow row1 = new TableRow(getContext());

            TextView name = new TextView(getContext());
            name.setText(player.getNumber() + " " + Html.fromHtml(player.getName()));
            name.setTypeface(name.getTypeface(), Typeface.BOLD);
            name.setTextColor(Color.parseColor("#000000"));
            name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            name.setTextSize(11);

            row1.addView(name);
            row1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });


            TableRow row2 = new TableRow(getContext());

            TextView position = new TextView(getContext());
            position.setText(player.getPosition());
            position.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            position.setTextSize(10);

            row2.addView(position);
            row2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });

            TableRow row3 = new TableRow(getContext());

            TextView injures = new TextView(getContext());
            injures.setText(Defaultdata.injuries.get(player.getInjury_id()) + " - " + player.getRecovery() + " " +
                    getContext().getResources().getString(R.string.dates));
            injures.setTextColor(Color.parseColor("#ff0000"));
            injures.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            injures.setTextSize(10);

            row3.addView(injures);
            row3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });

            table.addView(row1);
            table.addView(row2);
            table.addView(row3);
        }
        if(notifications.getUpgraded().size() > 0){
            TableRow row = new TableRow(getContext());

            TextView title = new TextView(getContext());
            title.setText(getContext().getResources().getString(R.string.improved_players));
            title.setTextSize(12);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(title);
            table.addView(row);
        }
        for(final Player player : notifications.getUpgraded()){
            TableRow row1 = new TableRow(getContext());

            TextView name = new TextView(getContext());
            name.setText(player.getNumber() + " " + Html.fromHtml(player.getName()));
            name.setTypeface(name.getTypeface(), Typeface.BOLD);
            name.setTextColor(Color.parseColor("#000000"));
            name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            name.setTextSize(11);

            row1.addView(name);
            row1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });


            TableRow row2 = new TableRow(getContext());

            TextView position = new TextView(getContext());
            position.setText(player.getPosition());
            position.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            position.setTextSize(10);

            row2.addView(position);
            row2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });



            table.addView(row1);
            table.addView(row2);
        }
        if(notifications.getRetiring().size() > 0){
            TableRow row = new TableRow(getContext());

            TextView title = new TextView(getContext());
            title.setText(getContext().getResources().getString(R.string.players_to_retire));
            title.setTextSize(12);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(title);
            table.addView(row);
        }
        for(final Player player : notifications.getRetiring()){
            TableRow row1 = new TableRow(getContext());

            TextView name = new TextView(getContext());
            name.setText(player.getNumber() + " " + Html.fromHtml(player.getName()));
            name.setTypeface(name.getTypeface(), Typeface.BOLD);
            name.setTextColor(Color.parseColor("#000000"));
            name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            name.setTextSize(11);

            row1.addView(name);
            row1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });


            TableRow row2 = new TableRow(getContext());

            TextView position = new TextView(getContext());
            position.setText(player.getPosition());
            position.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            position.setTextSize(10);

            row2.addView(position);
            row2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });



            table.addView(row1);
            table.addView(row2);
        }
        if(notifications.getSuspensions().size() > 0){
            TableRow row = new TableRow(getContext());

            TextView title = new TextView(getContext());
            title.setText(getContext().getResources().getString(R.string.suspended_players));
            title.setTextSize(12);
            title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            row.addView(title);
            table.addView(row);
        }
        for(final Player player : notifications.getSuspensions()){
            TableRow row1 = new TableRow(getContext());

            TextView name = new TextView(getContext());
            name.setText(player.getNumber() + " " + Html.fromHtml(player.getName()));
            name.setTypeface(name.getTypeface(), Typeface.BOLD);
            name.setTextColor(Color.parseColor("#000000"));
            name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            name.setTextSize(11);

            row1.addView(name);
            row1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });


            TableRow row2 = new TableRow(getContext());

            TextView position = new TextView(getContext());
            position.setText(player.getPosition());
            position.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            position.setTextSize(10);

            row2.addView(position);
            row2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDialog = new PlayerDialog(a,player, ((MainActivity)a).getUser(), playersNotificationDialog, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    playerDialog.show();
                }
            });



            table.addView(row1);
            table.addView(row2);
        }
    }

    @Override
    public void onButtonClick() {
        if(playerDialog != null && playerDialog.isShowing()){
            playerDialog.dismiss();
        }
    }


    public interface DialogPlayersNotificationClickListener {
        void onButtonPlayersNotificationClick();
    }
}