package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.renderscript.Script;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.activities.fragments.FragmentPlayer;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.get.market.MarketObject;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuy;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuyResponse;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.TeamDB;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;
import com.github.mikephil.charting.charts.RadarChart;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidsrc.futbolin.utils.Defaultdata.DIALOG_CONFIRM_BUYING_CREDITS;
import static com.androidsrc.futbolin.utils.Defaultdata.DIALOG_NOT_ENOUGH_CREDITS;

public class PlayerDialog extends Dialog  implements TwoButtonsDialog.DialogTwoButtonsClickListener, MessageDialog.DialogClickListener, ImproveDialog.DialogImproveClickListener, TransferibleDialog.DialogTransferibleClickListener, FreeDialog.DialogFreeClickListener{

    private Player player;
    getUser user;
    ImageView imageShield;
    private FragmentPlayer.OnFragmentInteractionListener mListener;

    TextView injuredTime, playerName, playerInfo, playerEnergia, playerArquero, playerDefensa, playerGambeta, playerCabeceo, playerSalto, playerPase, playerPrecision, playerVelocidad, playerFuerza, playerQuite, playerExp;
    ImageView transferable, up, down, medkit, healted, retired, yellowCard, redCard;
    Button healthButton;
    TwoButtonsDialog dialogConfirmBuying, dialogNotEnoughtCredits;
    PlayerDialog playerDialog;
    SvcApi svcAth;
    MessageDialog messageDialog;
    Activity a;
    TextView valueInfo;
    Button closeButton, improveButton, transferibleButton, freeButton;
    DialogClickListener dialogClickListener;
    ImproveDialog improveDialog;
    TransferibleDialog transferibleDialog;
    FreeDialog freeDialog;
    boolean isMarket;
    MarketObject marketObject;
    TextView offerValue, offerClosesAt;
    String pattern;
    DecimalFormat decimalFormat;
    DatabaseManager db;
    RadarChart radarChart;

    public PlayerDialog(Activity a, Player player, getUser user, DialogClickListener dialogClickListener, boolean isMarket, MarketObject marketObject){
        super(a);
        this.a = a;
        this.player = player;
        this.user = user;
        this.dialogClickListener = dialogClickListener;
        this.isMarket = isMarket;
        this.marketObject = marketObject;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if(!isMarket){
            if (a.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.fragment_player_landscape);

            }else{
                Log.e("fragment_player","fragment_player!!");
                setContentView(R.layout.fragment_player);
            }
        }else{
            if (a.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setContentView(R.layout.dialog_player_market_landscape);
            }else {
                setContentView(R.layout.dialog_player_market);
            }

        }

        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);



        closeButton = findViewById(R.id.fragment_player_dialog_button);
        playerDialog = this;
        playerName = findViewById(R.id.fragment_player_name);
        playerInfo = findViewById(R.id.fragment_player_info);
        playerEnergia = findViewById(R.id.fragment_player_stamina);
        playerArquero = findViewById(R.id.fragment_player_goalkeeping);
        playerDefensa = findViewById(R.id.fragment_player_defense);
        playerGambeta= findViewById(R.id.fragment_player_dribbling);
        playerCabeceo= findViewById(R.id.fragment_player_cab);
        playerSalto= findViewById(R.id.fragment_player_jump);
        playerPase= findViewById(R.id.fragment_player_pass);
        playerPrecision= findViewById(R.id.fragment_player_pres);
        playerVelocidad= findViewById(R.id.fragment_player_speed);
        playerFuerza= findViewById(R.id.fragment_player_strength);
        playerQuite= findViewById(R.id.fragment_player_quite);
        playerExp= findViewById(R.id.fragment_player_exp);
        up = findViewById(R.id.fragment_player_up_image);
        down = findViewById(R.id.fragment_player_down_image);
        medkit = findViewById(R.id.fragment_player_medkit_image);
        retired = findViewById(R.id.fragment_player_retired_image);
        healted = findViewById(R.id.fragment_player_healted_image);
        transferable = findViewById(R.id.fragment_player_transferable_image);
        yellowCard = findViewById(R.id.fragment_player_yellowcard_image);
        redCard = findViewById(R.id.fragment_player_redcard_image);
        injuredTime = findViewById(R.id.fragment_player_medkit_text);
        healthButton = findViewById(R.id.fragment_player_health_button);
        improveButton = findViewById(R.id.fragment_player_improve_button);
        transferibleButton = findViewById(R.id.fragment_player_tranferible_button);
        freeButton = findViewById(R.id.fragment_player_free_button);
        valueInfo = findViewById(R.id.fragment_player_value_info);
        radarChart = findViewById(R.id.adapter_market_player_chart);
        if(!isMarket) {
            if(player.getSelling() == null){
                valueInfo.setText(getContext().getResources().getString(R.string.value)+ " " +
                        decimalFormat.format(player.getValue()) + " $");
            }else{
                if(player.getSelling().getBest_offer_team() == 0){
                    valueInfo.setText(getContext().getResources().getString(R.string.value)+
                            " " + decimalFormat.format(player.getValue()) + " $ \n"
                    + getContext().getResources().getString(R.string.no_offer));
                }else{
                    valueInfo.setText(getContext().getResources().getString(R.string.value) +
                            " " + decimalFormat.format(player.getValue()) + " $ \n"
                            + getContext().getResources().getString(R.string.best_offer)
                            + " " + decimalFormat.format(player.getSelling().getBest_offer_value())
                            + "  (" + getTeamName(player.getSelling().getBest_offer_team())+ ")");
                }
            }
            if(!player.isTransferable()) {
                improveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        improveDialog = new ImproveDialog(a, player, playerDialog, isMarket, marketObject);
                        improveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        improveDialog.show();
                    }
                });

                transferibleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        transferibleDialog = new TransferibleDialog(a, player, playerDialog);
                        transferibleDialog.show();
                    }
                });
                freeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        freeDialog = new FreeDialog(a, player, playerDialog, user);
                        freeDialog.show();
                    }
                });
            }else{
                improveButton.setVisibility(View.INVISIBLE);
                transferibleButton.setVisibility(View.INVISIBLE);
                freeButton.setVisibility(View.INVISIBLE);
            }
        }else{
            Log.e("player", "1 -");
            improveButton = findViewById(R.id.fragment_player_offer_button);
            offerValue = findViewById(R.id.fragment_player_offer_value);
            offerClosesAt = findViewById(R.id.fragment_player_offer_closesat);
            if(marketObject != null) {
                Log.e("player", "2 -");
                if (marketObject.getOffer_team() == 0) {
                    offerValue.setText(getContext().getResources().getString(R.string.best_offer_no_offer));
                } else {
                    offerValue.setText(getContext().getResources().getString(R.string.best_offer) +
                            " " + decimalFormat.format(marketObject.getOffer_value())
                            + " $ (" + getTeamName(marketObject.getOffer_team())+ ")");
                    offerValue.setTextSize(11);
                }

                SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String strDate = sm.format(marketObject.getCloses_at() * 1000);
                if(player.isTransferable()) {
                    offerClosesAt.setText(getContext().getResources().getString(R.string.transfarable_untill)+ " " + strDate);
                    improveButton.setText(getContext().getResources().getString(R.string.make_an_offer));
                    improveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            improveDialog = new ImproveDialog(a, player, playerDialog, isMarket, marketObject);
                            improveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            improveDialog.show();
                        }
                    });
                    if(player.getTeam_id() != user.getUser().getTeam().getId()){
                        healthButton.setVisibility(View.INVISIBLE);
                    }
                }else{
                    improveButton.setVisibility(View.INVISIBLE);
                    offerValue.setVisibility(View.INVISIBLE);
                    if(player.getTeam_id() != user.getUser().getTeam().getId()){
                        healthButton.setVisibility(View.INVISIBLE);
                    }
                }
            }else if(player.getSelling() != null){
                Log.e("player", "3 -");
                if (player.getSelling().getBest_offer_team() == 0 || player.getSelling().getBest_offer_team() == 0) {
                    offerValue.setText(getContext().getResources().getString(R.string.best_offer_no_offer));
                } else {
                    offerValue.setText(getContext().getResources().getString(R.string.best_offer)
                            +" " + decimalFormat.format(player.getSelling().getBest_offer_value())
                            + " $ (" + getTeamName(player.getSelling().getBest_offer_team())+ ")");
                    offerValue.setTextSize(11);
                }
                if(player.getTeam_id() != user.getUser().getTeam().getId()){
                    healthButton.setVisibility(View.INVISIBLE);
                }
                if(player.isTransferable()) {
                    offerClosesAt.setText(getContext().getResources().getString(R.string.transfarable_untill)+
                            " " + player.getSelling().getCloses_at());
                    improveButton.setText(getContext().getResources().getString(R.string.make_an_offer));
                    improveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            improveDialog = new ImproveDialog(a, player, playerDialog, isMarket, marketObject);
                            improveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            improveDialog.show();
                        }
                    });
                }else{
                    improveButton.setVisibility(View.INVISIBLE);
                    offerValue.setVisibility(View.INVISIBLE);
                }

            }else{
                Log.e("player", "4 -");
                improveButton.setVisibility(View.INVISIBLE);
                offerValue.setVisibility(View.INVISIBLE);
                if(player.getTeam_id() != user.getUser().getTeam().getId()){
                    healthButton.setVisibility(View.INVISIBLE);
                }
            }

        }
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClickListener.onButtonClick();
            }
        });
        closeButton.setVisibility(View.VISIBLE);

        buildLayout();

    }

    @Override
    public void onDialogImproveButtonClick(boolean success, long value, boolean follow) {

        if(!follow){
            if (improveDialog != null && improveDialog.isShowing()) {
                improveDialog.dismiss();
            }
            if (!isMarket) {
                if (success && player.getValue() < value) {
                    valueInfo.setText(getContext().getResources().getString(R.string.value) +
                            " " + decimalFormat.format(value) + " $");
                }
            } else {
                if (success) {
                    offerValue.setText(getContext().getResources().getString(R.string.best_offer)+
                            " " + decimalFormat.format(value) + " $ ");
                    player.setValue((int) value);
                    user.getUser().getTeam().setSpending_margin(user.getUser().getTeam().getSpending_margin() - value);
                    ((MainActivity) a).setUser(user);
                    ((MainActivity) a).changeToMarketFragment();
                }
            }
        }else{
            if(improveDialog != null && improveDialog.isShowing()){
                improveDialog.dismiss();
            }
            marketObject.setFollowing(!marketObject.isFollowing());
            improveDialog = new ImproveDialog(a, player, playerDialog, isMarket, marketObject);
            improveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            improveDialog.show();
            ((MainActivity) a).changeToMarketFollowing();
        }
    }

    @Override
    public void onTransferibleButtonClick(boolean transfered) {
        if(transferibleDialog != null && transferibleDialog.isShowing()){
            transferibleDialog.dismiss();
        }
        if(transfered){
            player.setTransferable(true);
        }
    }

    @Override
    public void onFreeButtonClick(boolean free, getUser user) {
        if(freeDialog != null && freeDialog.isShowing()){
            freeDialog.dismiss();
        }
        if(free){
            this.user = user;
            // Reiniciar MainAcitivy e ir a PlayersFragment
        }else{

        }

    }


    public interface DialogClickListener {
        void onButtonClick();
    }

    public void buildLayout(){

        Defaultdata.loadRadarPlayer(player, radarChart, getContext());
        if(player.getLast_upgrade() == null){
            up.setVisibility(View.GONE);
        }else{
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_circle_up, up);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#02ac01"));

            up.getLayoutParams().width = (int)(15 * a.getResources().getDisplayMetrics().density);
            up.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
            up.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
        }

        if(player.getStamina() < 40){
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_down, down);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
            down.getLayoutParams().width = (int)(15 * a.getResources().getDisplayMetrics().density);
            down.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
            down.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
        }else{
            down.setVisibility(View.GONE);
        }
        if(player.getCards_count() == 2){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, yellowCard);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#fff700"));
            yellowCard.getLayoutParams().width = (int)(15 * a.getResources().getDisplayMetrics().density);
            yellowCard.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
            yellowCard.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
        }else{
            yellowCard.setVisibility(View.GONE);
        }
        if(player.isSuspended()){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, redCard);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
            redCard.getLayoutParams().width = (int)(15 * a.getResources().getDisplayMetrics().density);
            redCard.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
            redCard.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
        }else{
            redCard.setVisibility(View.GONE);
        }
        if(player.getInjury_id() != 0){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_medkit, medkit);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
            injuredTime.setText("" + player.getRecovery());
            if(!player.isHealed()) {
                healthButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogConfirmBuying = new TwoButtonsDialog(a, getContext().getResources().getString(R.string.heal_player),
                                getContext().getResources().getString(R.string.healing_player_1) + " "
                                + player.getFirst_name() + " " + player.getLast_name() + " " +
                                        getContext().getResources().getString(R.string.healing_player_2) + " " + (player.getRecovery() % 2 > 0 ? player.getRecovery() / 2 + 1 : player.getRecovery() / 2) + " fechas Cada jugador puede " +
                                        getContext().getResources().getString(R.string.healing_player_3) +" " + player.getFirst_name() + " "
                                + player.getLast_name() + " " + getContext().getResources().getString(R.string.healing_player_4)
                                , playerDialog, Defaultdata.DIALOG_CONFIRM_BUYING_CREDITS, null);
                        dialogConfirmBuying.show();
                    }
                });
                medkit.getLayoutParams().width = (int)(15 * a.getResources().getDisplayMetrics().density);
                medkit.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
                medkit.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
            }else{
                healthButton.setVisibility(View.GONE);
            }
        }else{
            medkit.setVisibility(View.GONE);
            injuredTime.setVisibility(View.GONE);
            healthButton.setVisibility(View.GONE);
        }
        if(player.isRetiring()){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_user_times, retired);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));

            retired.getLayoutParams().width = (int)(15 * a.getResources().getDisplayMetrics().density);
            retired.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
            retired.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);

        }else{
            retired.setVisibility(View.GONE);
        }
        if(player.isHealed()){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_plus_circle, healted);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#02ac01"));
            healted.getLayoutParams().width = (int)(15 * a.getResources().getDisplayMetrics().density);
            healted.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
            healted.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
        }else{
            healted.setVisibility(View.GONE);
        }
        if(player.isTransferable()){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_share_square_solid, transferable);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#02ac01"));
            Log.e("DialogPlayer","is transferable null? " + (transferable == null));
            transferable.getLayoutParams().width = (int)(15 * a.getResources().getDisplayMetrics().density);
            transferable.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
            transferable.getLayoutParams().height =  (int)(15 * a.getResources().getDisplayMetrics().density);
        }else{
            transferable.setVisibility(View.GONE);
        }
        playerName.setText(player.getNumber() + " - " + player.getFirst_name() + " " + player.getLast_name());
        playerInfo.setText(player.getPosition() + " - " + player.getAge() + " años");
        playerEnergia.setText( player.getStamina() + "");
        playerArquero.setText( player.getGoalkeeping() + "");
        playerDefensa.setText( player.getDefending() + "");
        playerGambeta.setText( player.getDribbling() + "");
        playerCabeceo.setText( player.getHeading() + "");
        playerSalto.setText( player.getJumping() + "");
        playerPase.setText( player.getPassing() + "");
        playerPrecision.setText( player.getPrecision() + "");
        playerVelocidad.setText( player.getSpeed() + "");
        playerFuerza.setText( player.getStrength() + "");
        playerQuite.setText( player.getTackling() + "");
        playerExp.setText( player.getExperience() + "");
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getGoalkeeping() > 0){
            playerArquero.setTextColor(Color.parseColor("#3c763d"));
            playerArquero.setText(Html.fromHtml(player.getGoalkeeping() + "<sub><small>+" + player.getLast_upgrade().getGoalkeeping() + "</small></sub>"));
        }else{
            playerArquero.setText(player.getGoalkeeping() + "");
            playerArquero.setTextColor(Color.parseColor("#000000"));
        }

        if(player.getLast_upgrade() != null && player.getLast_upgrade().getDefending() > 0){
            playerDefensa.setTextColor(Color.parseColor("#3c763d"));
            playerDefensa.setText(Html.fromHtml(player.getDefending() + "<sub><small>+" + player.getLast_upgrade().getDefending() + "</small></sub>"));
        }else{
            playerDefensa.setText(player.getDefending() + "");
            playerDefensa.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getDribbling() > 0){
            playerGambeta.setTextColor(Color.parseColor("#3c763d"));
            playerGambeta.setText(Html.fromHtml(player.getDribbling() + "<sub><small>+" + player.getLast_upgrade().getDribbling() + "</small></sub>"));
        }else{
            playerGambeta.setText(player.getDribbling() + "");
            playerGambeta.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getHeading() > 0){
            playerCabeceo.setTextColor(Color.parseColor("#3c763d"));
            playerCabeceo.setText(Html.fromHtml(player.getHeading() + "<sub><small>+" + player.getLast_upgrade().getHeading() + "</small></sub>"));
        }else{
            playerCabeceo.setText(player.getHeading() + "");
            playerCabeceo.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getJumping() > 0){
            playerSalto.setTextColor(Color.parseColor("#3c763d"));
            playerSalto.setText(Html.fromHtml(player.getHeading() + "<sub><small>+" + player.getLast_upgrade().getJumping() + "</small></sub>"));
        }else{
            playerSalto.setText(player.getJumping() + "");
            playerSalto.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getPassing() > 0){
            playerPase.setTextColor(Color.parseColor("#3c763d"));
            playerPase.setText(Html.fromHtml(player.getPassing() + "<sub><small>+" + player.getLast_upgrade().getPassing() + "</small></sub>"));
        }else{
            playerPase.setText(player.getPassing() + "");
            playerPase.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getPrecision() > 0){
            playerPrecision.setTextColor(Color.parseColor("#3c763d"));
            playerPrecision.setText(Html.fromHtml(player.getPrecision() + "<sub><small>+" + player.getLast_upgrade().getPrecision() + "</small></sub>"));
        }else{
            playerPrecision.setText(player.getPrecision() + "");
            playerPrecision.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getSpeed() > 0){
            playerVelocidad.setTextColor(Color.parseColor("#3c763d"));
            playerVelocidad.setText(Html.fromHtml(player.getSpeed() + "<sub><small>+" + player.getLast_upgrade().getSpeed() + "</small></sub>"));
        }else{
            playerVelocidad.setText(player.getSpeed() + "");
            playerVelocidad.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getStrength() > 0){
            playerFuerza.setTextColor(Color.parseColor("#3c763d"));
            playerFuerza.setText(Html.fromHtml(player.getStrength() + "<sub><small>+" + player.getLast_upgrade().getStrength() + "</small></sub>"));
        }else{
            playerFuerza.setText(player.getStrength() + "");
            playerFuerza.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getTackling() > 0){
            playerQuite.setTextColor(Color.parseColor("#3c763d"));
            playerQuite.setText(Html.fromHtml(player.getTackling() + "<sub><small>+" + player.getLast_upgrade().getTackling() + "</small></sub>"));
        }else{
            playerQuite.setText(player.getTackling() + "");
            playerQuite.setTextColor(Color.parseColor("#000000"));
        }
        if(player.getLast_upgrade() != null && player.getLast_upgrade().getExperience() > 0){
            playerExp.setTextColor(Color.parseColor("#3c763d"));
            playerExp.setText(Html.fromHtml(player.getExperience() + "<sub><small>+" + player.getLast_upgrade().getExperience() + "</small></sub>"));
        }else{
            playerExp.setText(player.getExperience() + "");
            playerExp.setTextColor(Color.parseColor("#000000"));
        }
        playerEnergia.setTextColor(Color.parseColor("#000000"));
        /*
        if(player.getLast_upgrade()!= null){
            if (player.getLast_upgrade().getStamina() > 0){
                playerEnergia.setText( player.getStamina() + " (" + player.getLast_upgrade().getStamina() + ")");
                playerEnergia.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getGoalkeeping() > 0){
                playerArquero.setText( player.getGoalkeeping() + " (" + player.getLast_upgrade().getGoalkeeping() + ")");
                playerArquero.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getDefending() > 0){
                playerDefensa.setText( player.getDefending() + " (" + player.getLast_upgrade().getDefending() + ")");
                playerDefensa.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getDribbling() > 0){
                playerGambeta.setText( player.getDribbling() + " (" + player.getLast_upgrade().getDribbling() + ")");
                playerGambeta.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getHeading() > 0){
                playerCabeceo.setText( player.getHeading() + " (" + player.getLast_upgrade().getHeading() + ")");
                playerCabeceo.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getJumping() > 0){
                playerSalto.setText( player.getJumping() + " (" + player.getLast_upgrade().getJumping() + ")");
                playerSalto.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getPassing() > 0){
                playerPase.setText( player.getPassing() + " (" + player.getLast_upgrade().getPassing() + ")");
                playerPase.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getPrecision() > 0){
                playerPrecision.setText( player.getPrecision() + " (" + player.getLast_upgrade().getPrecision() + ")");
                playerPrecision.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getSpeed() > 0){
                playerVelocidad.setText( player.getSpeed() + " (" + player.getLast_upgrade().getSpeed() + ")");
                playerVelocidad.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getStrength() > 0){
                playerFuerza.setText( player.getStrength() + " (" + player.getLast_upgrade().getStrength() + ")");
                playerFuerza.setTextColor(Color.parseColor("#080000"));
            }

            if (player.getLast_upgrade().getTackling() > 0){
                playerQuite.setText( player.getTackling() + " (" + player.getLast_upgrade().getTackling() + ")");
                playerQuite.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getExperience() > 0){
                playerExp.setText( player.getExperience() + " (" + player.getLast_upgrade().getExperience() + ")");
                playerExp.setTextColor(Color.parseColor("#080000"));
            }
        } */
    }
    void postShoppingBuyHealth(){

        svcAth = Svc.initAuth(getContext());
        PostShoppingBuy postShoppingBuy = new PostShoppingBuy();
        postShoppingBuy.setDevice_id(Settings.Secure.getString(a.getContentResolver(), Settings.Secure.ANDROID_ID));
        postShoppingBuy.setId(4);
        postShoppingBuy.setPlayer_id(player.getId());

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
                                Toast.makeText(getContext(),
                                        getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                                // SUPONIENDO QUE ANTERIORMENTE HA HECHO LOGOUT, NO TIENE TOKEN !
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                user.getUser().setCredits(responsefinal.body().getCredits());

                                for(Player player1 :user.getUser().getTeam().getFormation_objects()){
                                    if(player1.getId() == player.getId()){
                                        player.setHeading((player.getRecovery() % 2 > 0 ? player.getRecovery() / 2 + 1 : player.getRecovery() / 2));
                                    }
                                }
                                ((MainActivity)a).setUser(user);
                                dialogConfirmBuying.dismiss();
                                ((MainActivity)a).changeToFragmentPlayers();
                                Toast.makeText(a, player.getShort_name()  + " " +
                                        getContext().getResources().getString(R.string.player_healed_sucessfully), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    try {
                        if(response.errorBody().string().contains("live_match") && response.errorBody().string().contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(a, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            a.startActivity(mainActivity);
                            a.finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostShoppingBuyResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    @Override
    public void onButtonClick(boolean confirm, int type, Object extra) {
        if(confirm){
            if(user.getUser().getCredits() >= Defaultdata.CREDITS_FOR_HEALTH && type == DIALOG_CONFIRM_BUYING_CREDITS){
                postShoppingBuyHealth();
            }else if(user.getUser().getCredits() < Defaultdata.CREDITS_FOR_HEALTH && type == DIALOG_CONFIRM_BUYING_CREDITS){
                dialogNotEnoughtCredits = new TwoButtonsDialog(a, getContext().getResources().getString(R.string.buy_fulbos),
                        getContext().getResources().getString(R.string.not_enough_fulbos_second_phrase_1) +".\n" +
                        "\n" +
                                getContext().getResources().getString(R.string.not_enough_fulbos_second_phrase_2),
                        playerDialog,  Defaultdata.DIALOG_NOT_ENOUGH_CREDITS, null);
                dialogNotEnoughtCredits.show();
            }else if(type == DIALOG_NOT_ENOUGH_CREDITS){
                buyCreditsViaPayPal();
            }

        }else{
            dialogConfirmBuying.dismiss();
        }
    }

    @Override
    public void onMessageDialogButtonClick() {
        if(messageDialog != null && messageDialog.isShowing()){
            messageDialog.dismiss();
        }
        ((MainActivity)a).changeToSplashScreen();
    }
    public void buyCreditsViaPayPal(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Defaultdata.URL_PAYPAL));
        a.startActivity(browserIntent);
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
}
