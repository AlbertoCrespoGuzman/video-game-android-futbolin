package com.androidsrc.futbolin.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidsrc.futbolin.R;

import java.util.ArrayList;
import java.util.List;


public class StrategyActivity extends Activity implements View.OnTouchListener, View.OnDragListener {

    LinearLayout statisticsLayout;
    RelativeLayout player_1, zone_1,player_2, zone_2,player_3, zone_3,player_4, zone_4,player_5, zone_5,player_6, zone_6,player_7, zone_7,player_8, zone_8,player_9, zone_9,
            player_10, zone_10,player_11, zone_11,player_12, zone_12,player_13, zone_13,player_14, zone_14,player_15, zone_15,player_16, zone_16,player_17, zone_17,player_18, zone_18,
            player_19, zone_19,player_20, zone_20,player_21, zone_21;
    TextView statisticsPlayerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.strategy_base);
        //set ontouch listener for box views
        player_1 = (RelativeLayout) findViewById(R.id.player_1); player_1.setOnTouchListener(this);
        player_2 = (RelativeLayout) findViewById(R.id.player_2); player_2.setOnTouchListener(this);
        player_3 = (RelativeLayout) findViewById(R.id.player_3); player_3.setOnTouchListener(this);
        player_4 = (RelativeLayout) findViewById(R.id.player_4); player_4.setOnTouchListener(this);
        player_5 = (RelativeLayout) findViewById(R.id.player_5); player_5.setOnTouchListener(this);
        player_6 = (RelativeLayout) findViewById(R.id.player_6); player_6.setOnTouchListener(this);
        player_7 = (RelativeLayout) findViewById(R.id.player_7); player_7.setOnTouchListener(this);
        player_8 = (RelativeLayout) findViewById(R.id.player_8); player_8.setOnTouchListener(this);
        player_9 = (RelativeLayout) findViewById(R.id.player_9); player_9.setOnTouchListener(this);
        player_10 = (RelativeLayout) findViewById(R.id.player_10); player_10.setOnTouchListener(this);
        player_11 = (RelativeLayout) findViewById(R.id.player_11); player_11.setOnTouchListener(this);
        player_12 = (RelativeLayout) findViewById(R.id.player_12); player_12.setOnTouchListener(this);
        player_13 = (RelativeLayout) findViewById(R.id.player_13); player_13.setOnTouchListener(this);
        player_14 = (RelativeLayout) findViewById(R.id.player_14); player_14.setOnTouchListener(this);
        player_15 = (RelativeLayout) findViewById(R.id.player_15); player_15.setOnTouchListener(this);
        player_16 = (RelativeLayout) findViewById(R.id.player_16); player_16.setOnTouchListener(this);
        player_17 = (RelativeLayout) findViewById(R.id.player_17); player_17.setOnTouchListener(this);
        player_18 = (RelativeLayout) findViewById(R.id.player_18); player_18.setOnTouchListener(this);
        player_19 = (RelativeLayout) findViewById(R.id.player_19); player_19.setOnTouchListener(this);
        player_20 = (RelativeLayout) findViewById(R.id.player_20); player_20.setOnTouchListener(this);
        player_21 = (RelativeLayout) findViewById(R.id.player_21); player_21.setOnTouchListener(this);



        //set ondrag listener for right and left parent views
        zone_1 = (RelativeLayout) findViewById(R.id.zone_1); zone_1.setOnDragListener(this);
        zone_2 = (RelativeLayout) findViewById(R.id.zone_2); zone_2.setOnDragListener(this);
        zone_3 = (RelativeLayout) findViewById(R.id.zone_3); zone_3.setOnDragListener(this);
        zone_4 = (RelativeLayout) findViewById(R.id.zone_4); zone_4.setOnDragListener(this);
        zone_5 = (RelativeLayout) findViewById(R.id.zone_5); zone_5.setOnDragListener(this);
        zone_6 = (RelativeLayout) findViewById(R.id.zone_6); zone_6.setOnDragListener(this);
        zone_7 = (RelativeLayout) findViewById(R.id.zone_7); zone_7.setOnDragListener(this);
        zone_8 = (RelativeLayout) findViewById(R.id.zone_8); zone_8.setOnDragListener(this);
        zone_9 = (RelativeLayout) findViewById(R.id.zone_9); zone_9.setOnDragListener(this);
        zone_10 = (RelativeLayout) findViewById(R.id.zone_10); zone_10.setOnDragListener(this);
        zone_11 = (RelativeLayout) findViewById(R.id.zone_11); zone_11.setOnDragListener(this);
        zone_12 = (RelativeLayout) findViewById(R.id.zone_12); zone_12.setOnDragListener(this);
        zone_13 = (RelativeLayout) findViewById(R.id.zone_13); zone_13.setOnDragListener(this);
        zone_14 = (RelativeLayout) findViewById(R.id.zone_14); zone_14.setOnDragListener(this);
        zone_15 = (RelativeLayout) findViewById(R.id.zone_15); zone_15.setOnDragListener(this);
        zone_16 = (RelativeLayout) findViewById(R.id.zone_16); zone_16.setOnDragListener(this);
        zone_17 = (RelativeLayout) findViewById(R.id.zone_17); zone_17.setOnDragListener(this);
        zone_18 = (RelativeLayout) findViewById(R.id.zone_18); zone_18.setOnDragListener(this);
        zone_19 = (RelativeLayout) findViewById(R.id.zone_19); zone_19.setOnDragListener(this);
        zone_20 = (RelativeLayout) findViewById(R.id.zone_20); zone_20.setOnDragListener(this);
        zone_21 = (RelativeLayout) findViewById(R.id.zone_21); zone_21.setOnDragListener(this);

        statisticsLayout = (LinearLayout) findViewById(R.id.statistics);
        statisticsPlayerName = (TextView) findViewById(R.id.statistics_player_name);

        loadIds();

    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction()==DragEvent.ACTION_DROP){
            //we want to make sure it is dropped only to left and right parent view
            View view = (View)event.getLocalState();

            if(v.getId() == R.id.zone_1 || v.getId() == R.id.zone_2 ||
                    v.getId() == R.id.zone_3 || v.getId() == R.id.zone_4 ||
                    v.getId() == R.id.zone_5 || v.getId() == R.id.zone_6 ||
                    v.getId() == R.id.zone_7 || v.getId() == R.id.zone_8 ||
                    v.getId() == R.id.zone_9 || v.getId() == R.id.zone_10 ||
                    v.getId() == R.id.zone_11 ||  v.getId() == R.id.zone_12 || v.getId() == R.id.zone_13 ||
                    v.getId() == R.id.zone_14 ||  v.getId() == R.id.zone_15 || v.getId() == R.id.zone_16 ||
                    v.getId() == R.id.zone_17 ||  v.getId() == R.id.zone_18 || v.getId() == R.id.zone_19 ||
                    v.getId() == R.id.zone_20 ||  v.getId() == R.id.zone_21){




                RelativeLayout target = (RelativeLayout) v;
                View elOtro = target.getChildAt(0);

                //updating IDs
                if(elOtro != null) {
                    ViewGroup source = (ViewGroup) view.getParent();
                    source.removeView(view);


                    target.addView(view);
                    target.removeView(elOtro);
                    source.addView(elOtro);
                }else{
                    view.setVisibility(View.VISIBLE);
                }

            }
            //make view visible as we set visibility to invisible while starting drag
            view.setVisibility(View.VISIBLE);
        }else if (event.getAction() ==   DragEvent.ACTION_DRAG_ENDED){
            View view = (View)event.getLocalState();
            view.setVisibility(View.VISIBLE);
        }

        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            updateStatistics((long)view.getTag());
            return true;
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            view.setVisibility(View.VISIBLE);
        }
        return false;
    }
    private void updateStatistics(long id){

        statisticsLayout.setVisibility(View.VISIBLE);
        statisticsPlayerName.setText("Jugador No " + id);
    }

    private void loadIds(){

        player_1.setTag(1L);
        player_2.setTag(2L);
        player_3.setTag(3L);
        player_4.setTag(4L);
        player_5.setTag(5L);
        player_6.setTag(6L);
        player_7.setTag(7L);
        player_8.setTag(8L);
        player_9.setTag(9L);
        player_10.setTag(10L);
        player_11.setTag(11L);
        player_12.setTag(12L);
        player_13.setTag(13L);
        player_14.setTag(14L);
        player_15.setTag(15L);
        player_16.setTag(16L);
        player_17.setTag(17L);
        player_18.setTag(18L);
        player_19.setTag(19L);
        player_20.setTag(20L);
        player_21.setTag(21L);
    }

}
