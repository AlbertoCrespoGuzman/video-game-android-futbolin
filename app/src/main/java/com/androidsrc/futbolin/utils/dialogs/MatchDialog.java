package com.androidsrc.futbolin.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.communications.http.auth.get.Highlight;
import com.androidsrc.futbolin.communications.http.auth.get.getMatch;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Strategy;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.transition.TransitionManager;

public class MatchDialog  extends Dialog {

    DatabaseManager db;
    List<Strategy> strategies = new ArrayList<>();
    List<RelativeLayout> zones = new ArrayList<>();
    MatchDialog.DialogClickListener dialogClickListener;
    getMatch match;
    TextView alertMessage, stadiumDate, scoreResult, assistanceLabel, localNameLabel, visitNameLabel;
    ImageView shieldLocal, shieldVisit;
    TableLayout tableScorers, formationLocal, formationVisit, highLights, stats;
    ConstraintLayout field;
    Button closeButton;
    Activity a;
    List<String> scorers = new ArrayList<>();
    ScrollView scroller;
    Guideline fieldGuidelineLocal, fieldGuidelineVisit;
    RelativeLayout player_1, zone_1,player_2, zone_2,player_3, zone_3,player_4, zone_4,player_5, zone_5,player_6, zone_6,player_7, zone_7,player_8, zone_8,player_9, zone_9,
            player_10, zone_10,player_11, zone_11,player_12, zone_12,player_13, zone_13,player_14, zone_14,player_15, zone_15,player_16, zone_16,player_17, zone_17,player_18, zone_18,
            player_19, zone_19,player_20, zone_20,player_21, zone_21,player_22, zone_22;
    String pattern;
    DecimalFormat decimalFormat;

    public MatchDialog(Activity a, getMatch match, MatchDialog.DialogClickListener dialogClickListener){
        super(a);
        this.dialogClickListener = dialogClickListener;
        this.match = match;
        this.a = a;

        scorers = loadScorersTable();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_match);

        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);

        db = new DatabaseManager(a);
        strategies = db.findAllStrategies();

        assistanceLabel= findViewById(R.id.dialog_match_assistance_label);
        player_1 = (RelativeLayout) findViewById(R.id.player_1);
        player_2 = (RelativeLayout) findViewById(R.id.player_2);
        player_3 = (RelativeLayout) findViewById(R.id.player_3);
        player_4 = (RelativeLayout) findViewById(R.id.player_4);
        player_5 = (RelativeLayout) findViewById(R.id.player_5);
        player_6 = (RelativeLayout) findViewById(R.id.player_6);
        player_7 = (RelativeLayout) findViewById(R.id.player_7);
        player_8 = (RelativeLayout) findViewById(R.id.player_8);
        player_9 = (RelativeLayout) findViewById(R.id.player_9);
        player_10 = (RelativeLayout) findViewById(R.id.player_10);
        player_11 = (RelativeLayout) findViewById(R.id.player_11);
        player_12 = (RelativeLayout) findViewById(R.id.player_12);
        player_13 = (RelativeLayout) findViewById(R.id.player_13);
        player_14 = (RelativeLayout) findViewById(R.id.player_14);
        player_15 = (RelativeLayout) findViewById(R.id.player_15);
        player_16 = (RelativeLayout) findViewById(R.id.player_16);
        player_17 = (RelativeLayout) findViewById(R.id.player_17);
        player_18 = (RelativeLayout) findViewById(R.id.player_18);
        player_19 = (RelativeLayout) findViewById(R.id.player_19);
        player_20 = (RelativeLayout) findViewById(R.id.player_20);
        player_21 = (RelativeLayout) findViewById(R.id.player_21);
        player_22 = (RelativeLayout) findViewById(R.id.player_22);



        //set ondrag listener for right and left parent views
        zone_1 = (RelativeLayout) findViewById(R.id.zone_1);
        zone_2 = (RelativeLayout) findViewById(R.id.zone_2);
        zone_3 = (RelativeLayout) findViewById(R.id.zone_3);
        zone_4 = (RelativeLayout) findViewById(R.id.zone_4);
        zone_5 = (RelativeLayout) findViewById(R.id.zone_5);
        zone_6 = (RelativeLayout) findViewById(R.id.zone_6);
        zone_7 = (RelativeLayout) findViewById(R.id.zone_7);
        zone_8 = (RelativeLayout) findViewById(R.id.zone_8);
        zone_9 = (RelativeLayout) findViewById(R.id.zone_9);
        zone_10 = (RelativeLayout) findViewById(R.id.zone_10);
        zone_11 = (RelativeLayout) findViewById(R.id.zone_11);
        zone_12 = (RelativeLayout) findViewById(R.id.zone_12);
        zone_13 = (RelativeLayout) findViewById(R.id.zone_13);
        zone_14 = (RelativeLayout) findViewById(R.id.zone_14);
        zone_15 = (RelativeLayout) findViewById(R.id.zone_15);
        zone_16 = (RelativeLayout) findViewById(R.id.zone_16);
        zone_17 = (RelativeLayout) findViewById(R.id.zone_17);
        zone_18 = (RelativeLayout) findViewById(R.id.zone_18);
        zone_19 = (RelativeLayout) findViewById(R.id.zone_19);
        zone_20 = (RelativeLayout) findViewById(R.id.zone_20);
        zone_21 = (RelativeLayout) findViewById(R.id.zone_21);
        zone_22 = findViewById(R.id.zone_22);

        zones.addAll(Arrays.asList(zone_1, zone_2, zone_3, zone_4, zone_5, zone_6, zone_7, zone_8, zone_9,zone_10,zone_11,zone_12,zone_13, zone_14, zone_15, zone_16, zone_17, zone_18, zone_19,zone_20,zone_21, zone_22));

        visitNameLabel = findViewById(R.id.dialog_match_visit_team_name_label);
        localNameLabel =  findViewById(R.id.dialog_match_local_team_name_label);

        alertMessage = findViewById(R.id.dialog_match_alert_message);
        stadiumDate = findViewById(R.id.dialog_match_stadium_date);
        shieldLocal = findViewById(R.id.dialog_match_shield_local);
        scoreResult = findViewById(R.id.dialog_match_result);
        shieldVisit = findViewById(R.id.dialog_match_shield_visit);
        tableScorers = findViewById(R.id.dialog_match_scorers);
        formationLocal = findViewById(R.id.dialog_match_formation_local);
        formationVisit = findViewById(R.id.dialog_match_formation_visit);
        highLights = findViewById(R.id.dialog_match_highlights);
        stats = findViewById(R.id.dialog_match_stats);
        field = findViewById(R.id.dialog_match_field);
        closeButton = findViewById(R.id.dialog_match_button_close);
        scroller = findViewById(R.id.dialog_match_scroll_field);
        fieldGuidelineLocal = findViewById(R.id.dialog_match_formation_local_guideline);
        fieldGuidelineVisit = findViewById(R.id.dialog_match_formation_visit_guideline);

        localNameLabel.setText(match.getLocal().getName());
        visitNameLabel.setText(match.getVisit().getName());

        if(match.getAssistance() > 0){
            assistanceLabel.setText(match.getAssistance() + " " +
                    getContext().getResources().getString(R.string.spectators)+ " - " + decimalFormat.format(match.getIncomes()) + " $");
        }else{
            assistanceLabel.setVisibility(View.INVISIBLE);
        }

        SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if(match.isShow_remaining()) {
            alertMessage.setText(getContext().getResources().getString(R.string.in) + " " +
                    match.getRemaining_readable() + " " + getContext().getResources().getString(R.string.you_can_play_again));
        }else{
            alertMessage.setVisibility(View.GONE);
        }
        stadiumDate.setText(match.getStadium() + " (" + parser.format(new Date(Long.parseLong(match.getDatetime()+ "000"))) + ")");


        VectorChildFinder vectorLocal = new VectorChildFinder(a, Defaultdata.shields.get((int)match.getLocal().getShield()), shieldLocal);
        VectorDrawableCompat.VFullPath path21Local = vectorLocal.findPathByName("secundary_color1");
        path21Local.setFillColor(Color.parseColor(match.getLocal().getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Local = vectorLocal.findPathByName("secundary_color2");
        path22Local.setFillColor(Color.parseColor(match.getLocal().getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Local = vectorLocal.findPathByName("primary_color");
        path1Local.setFillColor(Color.parseColor(match.getLocal().getPrimary_color()));
        shieldLocal.invalidate();

        VectorChildFinder vectorVisit = new VectorChildFinder(a, Defaultdata.shields.get(match.getVisit().getShield()), shieldVisit);
        VectorDrawableCompat.VFullPath path21Visit = vectorVisit.findPathByName("secundary_color1");
        path21Visit.setFillColor(Color.parseColor(match.getVisit().getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Visit = vectorVisit.findPathByName("secundary_color2");
        path22Visit.setFillColor(Color.parseColor(match.getVisit().getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Visit = vectorVisit.findPathByName("primary_color");
        path1Visit.setFillColor(Color.parseColor(match.getVisit().getPrimary_color()));
        shieldVisit.invalidate();



        scoreResult.setText(match.getScorers().getLocal().size() + " - " + match.getScorers().getVisit().size());

        for(int i=0; i< scorers.size(); i=i+2){
            TableRow row = new TableRow(a);

            TextView localScorer = new TextView(a);
            localScorer.setText(loadScorersTable().get(i));
            if(loadScorersTable().get(i).equals(".................")){
                localScorer.setTextColor(Color.parseColor("#FFFFFF"));
            }else {
                localScorer.setTextColor(Color.parseColor("#333333"));
            }
            localScorer.setTextSize(10);
            localScorer.setPadding(0,0,10,0);
            localScorer.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);


            TextView visitScorer = new TextView(a);
            visitScorer.setText(loadScorersTable().get(i + 1));
            if(loadScorersTable().get(i + 1).equals(".................")){
                visitScorer.setTextColor(Color.parseColor("#FFFFFF"));
            }else {
                visitScorer.setTextColor(Color.parseColor("#333333"));
            }
            visitScorer.setTextSize(10);
            visitScorer.setPadding(10,0,0,0);
            visitScorer.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            row.addView(localScorer);
            row.addView(visitScorer);

            tableScorers.addView(row);
        }


        Display display = a.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        overrideGetSize(display, size);
        ConstraintLayout.LayoutParams constraintLayout = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        constraintLayout.width = 0;


        ConstraintSet constraintSet2 = new ConstraintSet();
        constraintSet2.clone(field);

        constraintSet2.connect(field.getId(), ConstraintSet.LEFT, fieldGuidelineLocal.getId(), ConstraintSet.RIGHT, 0);
        constraintSet2.connect(field.getId(), ConstraintSet.RIGHT, fieldGuidelineVisit.getId(), ConstraintSet.LEFT, 0);

        constraintSet2.constrainMinHeight(field.getId(), size.y * 50 / 4);
        constraintSet2.connect(field.getId(), ConstraintSet.TOP, tableScorers.getId(), ConstraintSet.BOTTOM, 0);
        constraintSet2.connect(field.getId(), ConstraintSet.BOTTOM, highLights.getId(), ConstraintSet.TOP, 0);
        constraintSet2.applyTo(field);





        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(field);

        for(int j = 0; j < 11; j++){

            for(int i = 0; i < ((RelativeLayout) zones.get(j).getChildAt(0)).getChildCount(); i++) {
                if (zones.get(j).getChildAt(0) instanceof RelativeLayout) {
                    View view = ((RelativeLayout) zones.get(j).getChildAt(0)).getChildAt(i);
                    if (view instanceof ImageView) {
                        GradientDrawable shape = new GradientDrawable();
                        shape.setShape(GradientDrawable.RECTANGLE);
                        shape.setCornerRadii(new float[] { 30, 30, 30, 30, 30, 30, 30, 30 });
                        shape.setColor(Color.parseColor(match.getLocal().getPrimary_color()));
                        shape.setStroke(2, Color.parseColor(match.getLocal().getSecondary_color()));
                        ((ImageView) view).setBackground(shape);
                        view.invalidate();


                    } else if (view instanceof TextView) {
                        ((TextView) view).setText(match.getLocal().getFormation().get(j).getNumber() + "");
                        if(match.getLocal().getPrimary_color().equals(match.getLocal().getSecondary_color())){
                            System.out.println("----------------------- " + match.getLocal().getSecondary_color().substring(0,4) + "FFF");
                            ((TextView) view).setTextColor(Color.parseColor(reverseStringColor(match.getLocal().getSecondary_color())));
                        }else{
                            ((TextView) view).setTextColor(Color.parseColor(match.getLocal().getSecondary_color()));
                        }

                        ((TextView) view).setTextSize(7);
                    }
                }
            }

        }


        for(int i=0; i< 11; i++){
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.TOP, field.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.LEFT, field.getId(), ConstraintSet.LEFT, 0);

            constraintSet.setVerticalBias(zones.get(i).getId(), match.getLocal().getFormation().get(i).getTop()/100);
            constraintSet.setHorizontalBias(zones.get(i).getId(), match.getLocal().getFormation().get(i).getLeft()/100);
            constraintSet.applyTo(field);
            TransitionManager.beginDelayedTransition(field);
        }

        for(int j = 11; j < 22; j++){

            for(int i = 0; i < ((RelativeLayout) zones.get(j).getChildAt(0)).getChildCount(); i++) {
                if (zones.get(j).getChildAt(0) instanceof RelativeLayout) {
                    View view = ((RelativeLayout) zones.get(j).getChildAt(0)).getChildAt(i);
                    if (view instanceof ImageView) {
                        GradientDrawable shape = new GradientDrawable();
                        shape.setShape(GradientDrawable.RECTANGLE);
                        shape.setCornerRadii(new float[] { 30, 30, 30, 30, 30, 30, 30, 30 });
                        shape.setColor(Color.parseColor(match.getVisit().getPrimary_color()));
                        shape.setStroke(2, Color.parseColor(match.getVisit().getSecondary_color()));
                        ((ImageView) view).setBackground(shape);
                        view.invalidate();


                    } else if (view instanceof TextView) {
                        ((TextView) view).setText(match.getVisit().getFormation().get(j -11).getNumber() + "");
                        if(match.getVisit().getPrimary_color().equals(match.getVisit().getSecondary_color())){

                            ((TextView) view).setTextColor(Color.parseColor(reverseStringColor(match.getVisit().getSecondary_color())));
                        }else{
                            ((TextView) view).setTextColor(Color.parseColor(match.getVisit().getSecondary_color()));
                        }

                        ((TextView) view).setTextSize(7);
                    }
                }
            }

        }
        for(int i=11; i< 22; i++){
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.TOP, field.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.LEFT, field.getId(), ConstraintSet.LEFT, 0);

            constraintSet.setVerticalBias(zones.get(i).getId(), (float)( match.getVisit().getFormation().get(i - 11).getTop()/100));
            constraintSet.setHorizontalBias(zones.get(i).getId(), (1 - (float)match.getVisit().getFormation().get(i - 11).getLeft()/100));
            constraintSet.applyTo(field);
            TransitionManager.beginDelayedTransition(field);
        }



        for(int i=0; i< match.getLocal().getFormation().size(); i++){
            TableRow row = new TableRow(a);

            TextView localLineup = new TextView(a);
            localLineup.setText(match.getLocal().getFormation().get(i).getShort_name() + "(" + match.getLocal().getFormation().get(i).getNumber()+")");
            if(i < 11) {
                localLineup.setTextColor(Color.parseColor("#333333"));
            }else{
                localLineup.setTextColor(Color.parseColor("#afafae"));
            }
            localLineup.setGravity(Gravity.RIGHT);
            localLineup.setTextSize(8);
            localLineup.setPadding(5,0,5,0);
            row.addView(localLineup);

            formationLocal.addView(row);
        }
        for(int i=0; i< match.getVisit().getFormation().size(); i++){
            TableRow row = new TableRow(a);

            TextView visitLineup = new TextView(a);
            visitLineup.setText(match.getVisit().getFormation().get(i).getShort_name() + "(" + match.getVisit().getFormation().get(i).getNumber()+")");
            if(i < 11) {
                visitLineup.setTextColor(Color.parseColor("#333333"));
            }else{
                visitLineup.setTextColor(Color.parseColor("#afafae"));
            }
            visitLineup.setTextSize(8);
            visitLineup.setGravity(Gravity.LEFT);
            visitLineup.setPadding(5,0,5,0);
            row.addView(visitLineup);

            formationVisit.addView(row);
        }

        for(Highlight h : match.getHighlights()){
            TableRow row = new TableRow(a);
            row.setBackgroundColor(Color.parseColor(h.getBackground()));

            TextView highLightTime = new TextView(a.getApplicationContext());
            highLightTime.setText(h.getMinutes() + "'  ");
            highLightTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            if(h.getColor().length() < 7){
                while(h.getColor().length() < 7){
                    h.setColor(h.getColor() + "0");
                }
            }

            highLightTime.setTextColor(Color.parseColor(h.getColor()));
            highLightTime.setTextSize(10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,1,1,1);
           // highLightTime.setLayoutParams(params);

            TextView highLightText = new TextView(a);
            highLightText.setText(h.getHighlight());
            highLightText.setTextColor(Color.parseColor(h.getColor()));
            if(h.getType() == 10 || h.getType() == 19){
                highLightText.setTypeface(highLightText.getTypeface(), Typeface.BOLD);
            }
            highLightText.setTextSize(10);

            row.addView(highLightTime);
            row.addView(highLightText);

            highLights.addView(row);
        }

        TableRow statsRow1 = new TableRow(getContext());
        statsRow1.setBackgroundColor(a.getResources().getColor(R.color.azul_suave));

        TextView localGoals =  new TextView(getContext());
        localGoals.setText(match.getScorers().getLocal().size() + "");
        localGoals.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        localGoals.setTextSize(11);
        if(match.getScorers().getLocal().size() > match.getScorers().getVisit().size()){
            localGoals.setTypeface(Typeface.DEFAULT_BOLD);
        }

        TextView goalsLabel = new TextView(getContext());
        goalsLabel.setText(getContext().getResources().getString(R.string.goals));
        goalsLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        goalsLabel.setTextSize(11);

        TextView visitGoals =  new TextView(getContext());
        visitGoals.setText(match.getScorers().getVisit().size() + "");
        visitGoals.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        visitGoals.setTextSize(11);
        if(match.getScorers().getLocal().size() < match.getScorers().getVisit().size()){
            visitGoals.setTypeface(Typeface.DEFAULT_BOLD);
        }

        statsRow1.addView(localGoals);
        statsRow1.addView(goalsLabel);
        statsRow1.addView(visitGoals);

        stats.addView(statsRow1);

        TableRow statsRow2 = new TableRow(getContext());
        statsRow2.setBackgroundColor(Color.parseColor("#FFFFFF"));

        TextView localPossession =  new TextView(getContext());

        localPossession.setText(match.getLocal().getPosession() + "");
        localPossession.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        localPossession.setTextSize(11);
        if(Float.parseFloat(match.getLocal().getPosession().substring(0, match.getLocal().getPosession().length() -2))  > Float.parseFloat(match.getVisit().getPosession().substring(0, match.getVisit().getPosession().length() -2))){
            localPossession.setTypeface(Typeface.DEFAULT_BOLD);
        }
        TextView possessionLabel = new TextView(getContext());
        possessionLabel.setText(getContext().getResources().getString(R.string.possession));
        possessionLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        possessionLabel.setTextSize(11);

        TextView visitPossession =  new TextView(getContext());

        visitPossession.setText(match.getVisit().getPosession() + "");
        visitPossession.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        visitPossession.setTextSize(11);
        if(Float.parseFloat(match.getLocal().getPosession().substring(0, match.getLocal().getPosession().length() -2))  < Float.parseFloat(match.getVisit().getPosession().substring(0, match.getVisit().getPosession().length() -2))){
            visitPossession.setTypeface(Typeface.DEFAULT_BOLD);
        }
        statsRow2.addView(localPossession);
        statsRow2.addView(possessionLabel);
        statsRow2.addView(visitPossession);

        stats.addView(statsRow2);

        TableRow statsRow3 = new TableRow(getContext());
        statsRow3.setBackgroundColor(a.getResources().getColor(R.color.azul_suave));

        TextView localShoots =  new TextView(getContext());
        localShoots.setText(match.getLocal().getShots() + " (" + match.getLocal().getShots_goal() + ")");
        localShoots.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        localShoots.setTextSize(11);
        if(match.getLocal().getShots() > match.getVisit().getShots()){
            localShoots.setTypeface(Typeface.DEFAULT_BOLD);
        }

        TextView shootsLabel = new TextView(getContext());
        shootsLabel.setText(getContext().getResources().getString(R.string.shots));
        shootsLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        shootsLabel.setTextSize(11);

        TextView visitShoots =  new TextView(getContext());
        visitShoots.setText(match.getVisit().getShots() + " (" + match.getVisit().getShots_goal() + ")");
        visitShoots.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        visitShoots.setTextSize(11);
        if(match.getLocal().getShots() < match.getVisit().getShots()){
            visitShoots.setTypeface(Typeface.DEFAULT_BOLD);
        }

        statsRow3.addView(localShoots);
        statsRow3.addView(shootsLabel);
        statsRow3.addView(visitShoots);

        stats.addView(statsRow3);

        TableRow statsRow4 = new TableRow(getContext());
        statsRow4.setBackgroundColor(Color.parseColor("#FFFFFF"));

        TextView localSubtitutions =  new TextView(getContext());
        localSubtitutions.setText(match.getLocal().getSubstitutions() + "");
        localSubtitutions.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        localSubtitutions.setTextSize(11);

        TextView substitutionLabel = new TextView(getContext());
            substitutionLabel.setText(getContext().getResources().getString(R.string.substitutions));
        substitutionLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        substitutionLabel.setTextSize(11);

        TextView visitSubstitution =  new TextView(getContext());
        visitSubstitution.setText(match.getVisit().getSubstitutions() + "");
        visitSubstitution.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        visitSubstitution.setTextSize(10);

        statsRow4.addView(localSubtitutions);
        statsRow4.addView(substitutionLabel);
        statsRow4.addView(visitSubstitution);

        stats.addView(statsRow4);


        TableRow statsRow5 = new TableRow(getContext());
        statsRow5.setBackgroundColor(a.getResources().getColor(R.color.azul_suave));

        TextView localCards=  new TextView(getContext());
        localCards.setText(match.getLocal().getYellow_cards() + " / " + match.getLocal().getRed_cards());
        localCards.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        localCards.setTextSize(11);


        TextView cardsLabel = new TextView(getContext());
        cardsLabel.setText(getContext().getResources().getString(R.string.cards));
        cardsLabel.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        cardsLabel.setTextSize(11);

        TextView visitCards =  new TextView(getContext());
        visitCards.setText(match.getVisit().getYellow_cards() + " / " + match.getVisit().getRed_cards());
        visitCards.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        visitCards.setTextSize(11);


        statsRow5.addView(localCards);
        statsRow5.addView(cardsLabel);
        statsRow5.addView(visitCards);

        stats.addView(statsRow5);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogClickListener.onButtonClick();
            }
        });
    }



    public interface DialogClickListener {
        void onButtonClick();
    }
    List<String> loadScorersTable(){
        List<String> scorers = new ArrayList<>();

        int maxLocal = match.getScorers().getLocal().size();
        int maxVisit = match.getScorers().getVisit().size();
        int max = 0;

        if(maxLocal>= maxVisit){
            max = maxLocal;
        }else{
            max = maxVisit;
        }

        for(int i = 0; i < max ; i++){
            if(maxLocal > i){
                scorers.add(match.getScorers().getLocal().get(i).getPlayer() + "(" + match.getScorers().getLocal().get(i).getMinute() + "')");
            }else{
                scorers.add(".................");
            }
            if(maxVisit > i){
                scorers.add(match.getScorers().getVisit().get(i).getPlayer() + "(" + match.getScorers().getVisit().get(i).getMinute() + "')");
            }else{
                scorers.add(".................");
            }
        }

        return scorers;
    }
    void overrideGetSize(Display display, Point outSize) {
        try {
            // test for new method to trigger exception
            Class pointClass = Class.forName("android.graphics.Point");
            Method newGetSize = Display.class.getMethod("getSize", new Class[]{ pointClass });

            // no exception, so new method is available, just use it
            newGetSize.invoke(display, outSize);
        } catch(Exception ex) {
            // new method is not available, use the old ones
            outSize.x = display.getWidth();
            outSize.y = display.getHeight();
        }
    }
    public String reverseStringColor(String word){
        String reverse = "#";

        for(int i = word.length() -1; i >= 1; i--){
            reverse = reverse + word.charAt(i);
        }

        return reverse;
    }



}

