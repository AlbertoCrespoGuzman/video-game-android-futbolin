package com.androidsrc.futbolin.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.database.models.Player;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.LinearLayoutCompat;

/**
 * Created by alberto on 2017/12/05.
 */

public class Defaultdata {

     public static final String DEFAULT_IP ="http://xxxxx";
     public static final String DEFAULT_PORT ="5500";
    
    public static final List<Integer> shields = Arrays.asList(R.drawable.ic_shield_01, R.drawable.ic_shield_01,R.drawable.ic_shield_02,R.drawable.ic_shield_03,R.drawable.ic_shield_04,
    R.drawable.ic_shield_05,R.drawable.ic_shield_06,R.drawable.ic_shield_07,R.drawable.ic_shield_08,
    R.drawable.ic_shield_09,R.drawable.ic_shield_10,R.drawable.ic_shield_11,R.drawable.ic_shield_12,
    R.drawable.ic_shield_13, R.drawable.ic_shield_14, R.drawable.ic_shield_15, R.drawable.ic_shield_16);

    public static final Map<String, Integer> playerColors =createMap();
public static final List<RelativeLayout.LayoutParams> detailsLayoutparams = createPlayersDetails();
    public static final int CREDITS_FOR_HEALTH = 2;
    public static final String URL_PAYPAL = "xxxxx";
    public static final List<String> injuries = Arrays.asList("","Rotura de ligamentos cruzados","Fractura de tibia y peroné","Rotura de meniscos","Pubalgia","Rotura de falange del pie", "Esguince de tobillo", "Microdesgarro");
    public static final int TRAINNING_NOTIFICATION = 2;


    public static int TRAINNING_SERVICE = 1;
    public static int NEXT_MATCH_SERVICE = 2;
    public static int MARKET_SERVICE = 3;
    public static int MARKET_NOTIFICATION_FOLLOWING = 1;
    public static int MARKET_NOTIFICATION_OFFER = 2;


    public static int MAINACTIVITY_CHANGE_TO_MARKET_OFFER = 1;
    public static int MAINACTIVITY_CHANGE_TO_MARKET_FOLLOWING = 2;
    public static int MAINACTIVITY_CHANGE_TO_MESSAGES_DIALOG = 3;
    public static int MAINACTIVITY_CHANGE_TO_MARKET_DEFAULT = 4;
    public static int MAINACTIVITY_CHANGE_TOPLAYERS_DEFAULT = 5;
    public static int MAINACTIVITY_CHANGE_TO_MARKET_DEFAULT_PLAYER_DIALOG = 6;
    public static int MAINACTIVITY_CHANGE_TOPLAYERS_DEFAULT_PLAYER_DIALOG = 7;



    private static Map<String, Integer> createMap()
    {
        Map<String,Integer> myMap = new HashMap<String,Integer>();
        myMap.put("ARQ", R.drawable.player_circle_arq);
        myMap.put("DEF", R.drawable.player_circle_def);
        myMap.put("MED", R.drawable.player_circle_med);
        myMap.put("ATA", R.drawable.player_circle_atac);

        return myMap;
    }




    static private  List<RelativeLayout.LayoutParams> createPlayersDetails(){
        List<RelativeLayout.LayoutParams> params = new ArrayList<>();

        RelativeLayout.LayoutParams paramsImage1 = new RelativeLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        paramsImage1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsImage1.addRule(RelativeLayout.CENTER_HORIZONTAL);


        params.add(paramsImage1);

        RelativeLayout.LayoutParams paramsImage2 = new RelativeLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        paramsImage2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsImage2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        paramsImage2.setMarginStart(10);
        params.add(paramsImage2);

        RelativeLayout.LayoutParams paramsImage3 = new RelativeLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        paramsImage3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        paramsImage3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsImage3.setMarginEnd(10);
        params.add(paramsImage3);

        RelativeLayout.LayoutParams paramsImage4 = new RelativeLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        paramsImage4.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        paramsImage4.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.add(paramsImage4);

        RelativeLayout.LayoutParams paramsImage5 = new RelativeLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        paramsImage5.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        paramsImage5.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        paramsImage5.setMarginStart(10);
        params.add(paramsImage5);

        RelativeLayout.LayoutParams paramsImage6 = new RelativeLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        paramsImage6.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        paramsImage6.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsImage6.setMarginEnd(10);
        params.add(paramsImage6);

        return params;
    }

    public static List<Integer> images = Arrays.asList(R.drawable.background_0,R.drawable.background_1, R.drawable.background_2, R.drawable.background_3, R.drawable.background_4, R.drawable.background_5, R.drawable.background_6, R.drawable.background_7, R.drawable.background_8);

    public static final int DIALOG_NOT_ENOUGH_CREDITS = 0;
    public static final int DIALOG_CONFIRM_BUYING_CREDITS = 1;

    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }

    public static AppCompatDialog loadDialog(AppCompatDialog appCompatDialog, String title, String message, Context context){

        appCompatDialog = new AppCompatDialog(context);
        appCompatDialog.setCancelable(false);
        appCompatDialog.setTitle(title);
      //  appCompatDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        appCompatDialog.setContentView(R.layout.dialog_process);

        final ImageView img_loading_frame = (ImageView) appCompatDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = (TextView) appCompatDialog.findViewById(R.id.tv_progress_message);
        if(message.length() > 25){
            int position = 12;
            for( int i= position; i < message.length(); i++){
                if(message.substring(i, i + 1).equals(" ")){
                    position = i;
                    break;
                }
            }
            message = " " + message.substring(0, position) + "\n" + message.substring(position);
            if(message.length() > 50){
                int position2 = 40;
                for( int i= position2; i < message.length(); i++){
                    if(message.substring(i, i + 1).equals(" ")){
                        position2 = i;
                        break;
                    }
                }
                message = message.substring(0, position2) + "\n" + message.substring(position2);
            }
        }
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }
        final AppCompatDialog appCompatDialog1 = appCompatDialog;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (appCompatDialog1 != null && appCompatDialog1.isShowing()) {
                        appCompatDialog1.dismiss();
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        }, 15000);


        return appCompatDialog;
    }
    public static void loadRadarPlayer(Player player, RadarChart radarChart, Context context){
        List<RadarEntry> entries = new ArrayList<>();
        final List<String> labels = new ArrayList<>();

        switch (player.getPosition()){
            case "ARQ":
                entries.add(new RadarEntry(player.getGoalkeeping()));
                entries.add(new RadarEntry(player.getStrength()));
                entries.add(new RadarEntry( player.getDefending()));
                entries.add(new RadarEntry(player.getJumping()));
                entries.add(new RadarEntry(player.getPassing()));

                labels.add(context.getResources().getString(R.string.goal_keeping));
                labels.add(context.getResources().getString(R.string.strength));
                labels.add(context.getResources().getString(R.string.defending));
                labels.add(context.getResources().getString(R.string.jumping));
                labels.add(context.getResources().getString(R.string.passing));
                break;
            case "DEF":
                entries.add(new RadarEntry(player.getDefending()));
                entries.add(new RadarEntry(player.getJumping()));
                entries.add(new RadarEntry(player.getSpeed()));
                entries.add(new RadarEntry(player.getPassing()));
                entries.add(new RadarEntry(player.getTackling()));

                labels.add(context.getResources().getString(R.string.defending));
                labels.add(context.getResources().getString(R.string.jumping));
                labels.add(context.getResources().getString(R.string.speed));
                labels.add(context.getResources().getString(R.string.passing));
                labels.add(context.getResources().getString(R.string.tackling));
                break;
            case "MED":
                entries.add(new RadarEntry(player.getDribbling()));
                entries.add(new RadarEntry(player.getTackling()));
                entries.add(new RadarEntry(player.getPrecision()));
                entries.add(new RadarEntry(player.getSpeed()));
                entries.add(new RadarEntry(player.getPassing()));

                labels.add(context.getResources().getString(R.string.dribbling));
                labels.add(context.getResources().getString(R.string.tackling));
                labels.add(context.getResources().getString(R.string.precision));
                labels.add(context.getResources().getString(R.string.speed));
                labels.add(context.getResources().getString(R.string.passing));
                break;
            case "ATA":
                entries.add(new RadarEntry(player.getPrecision()));
                entries.add(new RadarEntry(player.getJumping()));
                entries.add(new RadarEntry(player.getDribbling()));
                entries.add(new RadarEntry(player.getHeading()));
                entries.add(new RadarEntry(player.getStrength()));

                labels.add(context.getResources().getString(R.string.precision));
                labels.add(context.getResources().getString(R.string.jumping));
                labels.add(context.getResources().getString(R.string.dribbling));
                labels.add(context.getResources().getString(R.string.heading));
                labels.add(context.getResources().getString(R.string.strength));
                break;

        }

        RadarDataSet dataSet = new RadarDataSet(entries, "");
        dataSet.setColor(context.getResources().getColor(R.color.futbolinAzul));
        dataSet.setFillColor(context.getResources().getColor(R.color.futbolinAzul));
        dataSet.setDrawFilled(true);
        dataSet.setFillAlpha(80);
        dataSet.setLineWidth(2f);
        dataSet.setDrawHighlightCircleEnabled(true);
        dataSet.setDrawHighlightIndicators(false);

        dataSet.setDrawValues(false);
        RadarData data = new RadarData(dataSet);
        //  data.setLabels(labels);
        radarChart.setData(data);
        radarChart.getLegend().setEnabled(false);
        XAxis xAxis = radarChart.getXAxis();
        xAxis.setAxisMaximum(100);
        xAxis.setAxisMinimum(0);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(1f);
        xAxis.setXOffset(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return labels.get((int) value % labels.size());
            }
        });

        YAxis yAxis = radarChart.getYAxis();
        yAxis.setAxisMaximum(100);
        yAxis.setAxisMinimum(0);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(false);

        radarChart.animateXY(1400, 1400, Easing.EaseInOutQuad);
        radarChart.getDescription().setEnabled(false);
        radarChart.invalidate();
        radarChart.animate();

    }
}
