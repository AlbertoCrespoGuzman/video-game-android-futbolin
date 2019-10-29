package com.androidsrc.futbolin.activities.fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.getTeam;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.patch.PatchFormation;
import com.androidsrc.futbolin.communications.http.auth.patch.PatchStrategy;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.Strategy;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;
import com.github.mikephil.charting.charts.RadarChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.transition.TransitionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androidsrc.futbolin.utils.Utils.setStyleForEnergyProgressBar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentStrategy.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentStrategy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentStrategy extends Fragment implements View.OnTouchListener, View.OnDragListener {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_USER = "user";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters

        private String mParam2;

        private OnFragmentInteractionListener mListener;
        LinearLayout statisticsLayout;
        RelativeLayout player_1, zone_1,player_2, zone_2,player_3, zone_3,player_4, zone_4,player_5, zone_5,player_6, zone_6,player_7, zone_7,player_8, zone_8,player_9, zone_9,
                player_10, zone_10,player_11, zone_11,player_12, zone_12,player_13, zone_13,player_14, zone_14,player_15, zone_15,player_16, zone_16,player_17, zone_17,player_18, zone_18,
                player_19, zone_19,player_20, zone_20,player_21, zone_21, zone_22,player_22, zone_23,player_23, zone_24,player_24, zone_25,player_25, zone_26,player_26, zone_27,player_27, zone_28,player_28, zone_29,player_29,zone_30,player_30;
        ImageView player_image_1, player_image_2, player_image_3, player_image_4, player_image_5, player_image_6, player_image_7, player_image_8, player_image_9, player_image_10,
                player_image_11, player_image_12, player_image_13, player_image_14, player_image_15, player_image_16, player_image_17, player_image_18, player_image_19, player_image_20, player_image_21, player_image_22, player_image_23, player_image_24, player_image_25, player_image_26, player_image_27, player_image_28, player_image_29, player_image_30;
        TextView statisticsPlayerName, teamName, ageTv, medT, arqTv, defTv, gamTv, cabTv, salTv, pasTv, preTv, velTv, fueTv, quiTv, expTv, enerTv;
        ImageView iconHeaderImage;
        getUser user;
        DatabaseManager db;
        List<Strategy> strategies = new ArrayList<>();
        ConstraintLayout fieldLayout;
        List<RelativeLayout> zones = new ArrayList<>();
        List<RelativeLayout> players= new ArrayList<>();
        List<ImageView> playersImage= new ArrayList<>();
        Spinner strategySpinner;
        SvcApi svc;
        boolean spinnerSelectionFirstTime = false;
        ImageView up, down, medkit, healted, retired, yellowCard, redCard,transferable;
        TextView injuredTime;
        List<Boolean> detailsAdded = Arrays.asList(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false);
        ConstraintLayout suplentesLayout;
        Guideline guideline2;
        float density;
        RadarChart radarChart;
        ProgressBar energyBar, expBar;

        public FragmentStrategy() {
            // Required empty public constructor

        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param user Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentStrategy.
         */
        // TODO: Rename and change types and number of parameters
        public static FragmentStrategy newInstance(getUser user, String param2) {
            FragmentStrategy fragment = new FragmentStrategy();
            Bundle args = new Bundle();
            args.putSerializable(ARG_USER, user);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                user = (com.androidsrc.futbolin.communications.http.auth.get.getUser) getArguments().getSerializable(ARG_USER);
                mParam2 = getArguments().getString(ARG_PARAM2);
                db = new DatabaseManager(getActivity());
                strategies = db.findAllStrategies();

            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

           View v = inflater.inflate(R.layout.strategy_base, container, false);

            fieldLayout = v.findViewById(R.id.constraintLayout5);

            radarChart = v.findViewById(R.id.strategy_chart);
            player_image_1 = v.findViewById(R.id.player_1_image);
            player_image_2 = v.findViewById(R.id.player_2_image);
            player_image_3 = v.findViewById(R.id.player_3_image);
            player_image_4 = v.findViewById(R.id.player_4_image);
            player_image_5 = v.findViewById(R.id.player_5_image);
            player_image_6 = v.findViewById(R.id.player_6_image);
            player_image_7 = v.findViewById(R.id.player_7_image);
            player_image_8 = v.findViewById(R.id.player_8_image);
            player_image_9 = v.findViewById(R.id.player_9_image);
            player_image_10 = v.findViewById(R.id.player_10_image);
            player_image_11 = v.findViewById(R.id.player_11_image);
            player_image_12 = v.findViewById(R.id.player_12_image);
            player_image_13 = v.findViewById(R.id.player_13_image);
            player_image_14 = v.findViewById(R.id.player_14_image);
            player_image_15 = v.findViewById(R.id.player_15_image);
            player_image_16 = v.findViewById(R.id.player_16_image);
            player_image_17 = v.findViewById(R.id.player_17_image);
            player_image_18 = v.findViewById(R.id.player_18_image);
            player_image_19 = v.findViewById(R.id.player_19_image);
            player_image_20 = v.findViewById(R.id.player_20_image);
            player_image_21 = v.findViewById(R.id.player_21_image);
            player_image_22 = v.findViewById(R.id.player_22_image);
            player_image_23 = v.findViewById(R.id.player_23_image);
            player_image_24 = v.findViewById(R.id.player_24_image);
            player_image_25 = v.findViewById(R.id.player_25_image);
            player_image_26 = v.findViewById(R.id.player_26_image);
            player_image_27 = v.findViewById(R.id.player_27_image);
            player_image_28 = v.findViewById(R.id.player_28_image);
            player_image_29 = v.findViewById(R.id.player_29_image);
            player_image_30 = v.findViewById(R.id.player_30_image);


            player_1 = (RelativeLayout) v.findViewById(R.id.player_1); player_1.setOnTouchListener(this);
            player_2 = (RelativeLayout) v.findViewById(R.id.player_2); player_2.setOnTouchListener(this);
            player_3 = (RelativeLayout) v.findViewById(R.id.player_3); player_3.setOnTouchListener(this);
            player_4 = (RelativeLayout) v.findViewById(R.id.player_4); player_4.setOnTouchListener(this);
            player_5 = (RelativeLayout) v.findViewById(R.id.player_5); player_5.setOnTouchListener(this);
            player_6 = (RelativeLayout) v.findViewById(R.id.player_6); player_6.setOnTouchListener(this);
            player_7 = (RelativeLayout) v.findViewById(R.id.player_7); player_7.setOnTouchListener(this);
            player_8 = (RelativeLayout) v.findViewById(R.id.player_8); player_8.setOnTouchListener(this);
            player_9 = (RelativeLayout) v.findViewById(R.id.player_9); player_9.setOnTouchListener(this);
            player_10 = (RelativeLayout) v.findViewById(R.id.player_10); player_10.setOnTouchListener(this);
            player_11 = (RelativeLayout) v.findViewById(R.id.player_11); player_11.setOnTouchListener(this);
            player_12 = (RelativeLayout) v.findViewById(R.id.player_12); player_12.setOnTouchListener(this);
            player_13 = (RelativeLayout) v.findViewById(R.id.player_13); player_13.setOnTouchListener(this);
            player_14 = (RelativeLayout) v.findViewById(R.id.player_14); player_14.setOnTouchListener(this);
            player_15 = (RelativeLayout) v.findViewById(R.id.player_15); player_15.setOnTouchListener(this);
            player_16 = (RelativeLayout) v.findViewById(R.id.player_16); player_16.setOnTouchListener(this);
            player_17 = (RelativeLayout) v.findViewById(R.id.player_17); player_17.setOnTouchListener(this);
            player_18 = (RelativeLayout) v.findViewById(R.id.player_18); player_18.setOnTouchListener(this);
            player_19 = (RelativeLayout) v.findViewById(R.id.player_19); player_19.setOnTouchListener(this);
            player_20 = (RelativeLayout) v.findViewById(R.id.player_20); player_20.setOnTouchListener(this);
            player_21 = (RelativeLayout) v.findViewById(R.id.player_21); player_21.setOnTouchListener(this);
            player_22 = (RelativeLayout) v.findViewById(R.id.player_22); player_22.setOnTouchListener(this);
            player_23 = (RelativeLayout) v.findViewById(R.id.player_23); player_23.setOnTouchListener(this);
            player_24 = (RelativeLayout) v.findViewById(R.id.player_24); player_24.setOnTouchListener(this);
            player_25 = (RelativeLayout) v.findViewById(R.id.player_25); player_25.setOnTouchListener(this);
            player_26 = (RelativeLayout) v.findViewById(R.id.player_26); player_26.setOnTouchListener(this);
            player_27 = (RelativeLayout) v.findViewById(R.id.player_27); player_27.setOnTouchListener(this);
            player_28 = (RelativeLayout) v.findViewById(R.id.player_28); player_28.setOnTouchListener(this);
            player_29 = (RelativeLayout) v.findViewById(R.id.player_29); player_29.setOnTouchListener(this);
            player_30 = (RelativeLayout) v.findViewById(R.id.player_30); player_30.setOnTouchListener(this);




            //set ondrag listener for right and left parent views
            zone_1 = (RelativeLayout) v.findViewById(R.id.zone_1); zone_1.setOnDragListener(this);
            zone_2 = (RelativeLayout) v.findViewById(R.id.zone_2); zone_2.setOnDragListener(this);
            zone_3 = (RelativeLayout) v.findViewById(R.id.zone_3); zone_3.setOnDragListener(this);
            zone_4 = (RelativeLayout) v.findViewById(R.id.zone_4); zone_4.setOnDragListener(this);
            zone_5 = (RelativeLayout) v.findViewById(R.id.zone_5); zone_5.setOnDragListener(this);
            zone_6 = (RelativeLayout) v.findViewById(R.id.zone_6); zone_6.setOnDragListener(this);
            zone_7 = (RelativeLayout) v.findViewById(R.id.zone_7); zone_7.setOnDragListener(this);
            zone_8 = (RelativeLayout) v.findViewById(R.id.zone_8); zone_8.setOnDragListener(this);
            zone_9 = (RelativeLayout) v.findViewById(R.id.zone_9); zone_9.setOnDragListener(this);
            zone_10 = (RelativeLayout) v.findViewById(R.id.zone_10); zone_10.setOnDragListener(this);
            zone_11 = (RelativeLayout) v.findViewById(R.id.zone_11); zone_11.setOnDragListener(this);
            zone_12 = (RelativeLayout) v.findViewById(R.id.zone_12); zone_12.setOnDragListener(this);
            zone_13 = (RelativeLayout) v.findViewById(R.id.zone_13); zone_13.setOnDragListener(this);
            zone_14 = (RelativeLayout) v.findViewById(R.id.zone_14); zone_14.setOnDragListener(this);
            zone_15 = (RelativeLayout) v.findViewById(R.id.zone_15); zone_15.setOnDragListener(this);
            zone_16 = (RelativeLayout) v.findViewById(R.id.zone_16); zone_16.setOnDragListener(this);
            zone_17 = (RelativeLayout) v.findViewById(R.id.zone_17); zone_17.setOnDragListener(this);
            zone_18 = (RelativeLayout) v.findViewById(R.id.zone_18); zone_18.setOnDragListener(this);
            zone_19 = (RelativeLayout) v.findViewById(R.id.zone_19); zone_19.setOnDragListener(this);
            zone_20 = (RelativeLayout) v.findViewById(R.id.zone_20); zone_20.setOnDragListener(this);
            zone_21 = (RelativeLayout) v.findViewById(R.id.zone_21); zone_21.setOnDragListener(this);
            zone_22 = (RelativeLayout) v.findViewById(R.id.zone_22); zone_22.setOnDragListener(this);
            zone_23 = (RelativeLayout) v.findViewById(R.id.zone_23); zone_23.setOnDragListener(this);
            zone_24 = (RelativeLayout) v.findViewById(R.id.zone_24); zone_24.setOnDragListener(this);
            zone_25 = (RelativeLayout) v.findViewById(R.id.zone_25); zone_25.setOnDragListener(this);
            zone_26 = (RelativeLayout) v.findViewById(R.id.zone_26); zone_26.setOnDragListener(this);
            zone_27 = (RelativeLayout) v.findViewById(R.id.zone_27); zone_27.setOnDragListener(this);
            zone_28 = (RelativeLayout) v.findViewById(R.id.zone_28); zone_28.setOnDragListener(this);
            zone_29 = (RelativeLayout) v.findViewById(R.id.zone_29); zone_29.setOnDragListener(this);
            zone_30 = (RelativeLayout) v.findViewById(R.id.zone_30); zone_30.setOnDragListener(this);



            ageTv = v.findViewById(R.id.age_tv);
            medT = v.findViewById(R.id.average_tv);
            arqTv = v.findViewById(R.id.gk_tv);
            defTv = v.findViewById(R.id.defense_tv);
            gamTv = v.findViewById(R.id.dribble_tv);
            cabTv = v.findViewById(R.id.head_tv);
            salTv = v.findViewById(R.id.jump_tv);
            pasTv = v.findViewById(R.id.pass_tv);
            preTv = v.findViewById(R.id.pressure_tv);
            velTv = v.findViewById(R.id.speed_tv);
            fueTv = v.findViewById(R.id.strength_tv);
            quiTv = v.findViewById(R.id.quim_tv);
            expTv = v.findViewById(R.id.experience_tv);
            enerTv = v.findViewById(R.id.energy_tv);
            energyBar = v.findViewById(R.id.energy_bar);
            expBar = v.findViewById(R.id.exp_bar);

            up = v.findViewById(R.id.statistics_fragment_player_up_image);
            down = v.findViewById(R.id.statistics_fragment_player_down_image);
            yellowCard = v.findViewById(R.id.statistics_fragment_player_yellowcard_image);
            redCard = v.findViewById(R.id.statistics_fragment_player_redcard_image);
            retired = v.findViewById(R.id.statistics_fragment_player_retired_image);
            healted = v.findViewById(R.id.statistics_fragment_player_healted_image);
            transferable = v.findViewById(R.id.statistics_fragment_player_transferable_image);
            medkit = v.findViewById(R.id.statistics_fragment_player_medkit_image);
            injuredTime = v.findViewById(R.id.statistics_fragment_player_medkit_text);
            guideline2 = v.findViewById(R.id.guideline2);

            resizeScreen();

            strategySpinner = v.findViewById(R.id.strategy_spinner);

            List<String> strategiesNames = new ArrayList<>();
            int initialStrategyPosition = 0;
            for(int i=0; i< strategies.size(); i++){
                strategiesNames.add(strategies.get(i).getName());
                if(strategies.get(i).getId() ==  user.getUser().getTeam().getStrategy_id()){
                    initialStrategyPosition = i;
                }
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_fragment_strategy, strategiesNames);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            strategySpinner.setAdapter(dataAdapter);
            strategySpinner.setSelection(initialStrategyPosition);
            strategySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                              @Override
                              public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                  pathStrategy(i);
                              }

                              @Override
                              public void onNothingSelected(AdapterView<?> adapterView) {

                              }
                          });




            statisticsLayout = v.findViewById(R.id.statistics);
            statisticsPlayerName = (TextView) v.findViewById(R.id.statistics_player_name);

            suplentesLayout = v.findViewById(R.id.strategy_layout_suplentes);
         //   ConstraintSet set = new ConstraintSet();
            //   set.clone(suplentesLayout);
            //  set.setDimensionRatio(suplentesLayout.getId(), "w,4:1");
            //  set.applyTo(suplentesLayout);

            initializeIds();
            loadIds();


            return v;
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
                        v.getId() == R.id.zone_20 ||  v.getId() == R.id.zone_21
                        ||  v.getId() == R.id.zone_22||  v.getId() == R.id.zone_23
                        ||  v.getId() == R.id.zone_24||  v.getId() == R.id.zone_25
                        ||  v.getId() == R.id.zone_26||  v.getId() == R.id.zone_27
                        ||  v.getId() == R.id.zone_28||  v.getId() == R.id.zone_29
                        ||  v.getId() == R.id.zone_30
                        ){




                    RelativeLayout target = (RelativeLayout) v;
                    View elOtro = target.getChildAt(0);

                    //updating IDs
                    if(elOtro != null && view != null) {
                        if(elOtro.getTag() != view.getTag()){

                //            view.setTag(elOtro.getTag());
                //            elOtro.setTag(view.getTag());
                        if(view.getTag() != ((View)view.getParent()).getTag()){
                            view.setTag(((View)view.getParent()).getTag());
                        }
                        if(elOtro.getTag() != ((View)elOtro.getParent()).getTag()){
                            elOtro.setTag(((View)elOtro.getParent()).getTag());
                        }
                        ViewGroup source = (ViewGroup) view.getParent();
                        source.removeView(view);


                        target.addView(view);
                        target.removeView(elOtro);
                        source.addView(elOtro);

                            patchFormation();
                        }
                    }else{
                        if(view != null){
                            view.setVisibility(View.VISIBLE);
                        }

                    }

                }
                //make view visible as we set visibility to invisible while starting drag
                if(view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }else if (event.getAction() ==   DragEvent.ACTION_DRAG_ENDED){
                View view = (View)event.getLocalState();
                if(view != null) {
                    view.setVisibility(View.VISIBLE);
                }else{
                    ((MainActivity) getActivity()).getMe();
                    ((MainActivity) getActivity()).changeToStrategyFragment();
                }
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

            Player p = null;

            for(Player player : user.getUser().getTeam().getFormation_objects()){
                if(player.getId() == id) {
                    p = player;
                }
            }
            ageTv.setText(p.getAge() + "");
            medT.setText(p.getAverage() + "");

            if(p.getLast_upgrade() != null && p.getLast_upgrade().getGoalkeeping() > 0){
                arqTv.setTextColor(Color.parseColor("#3c763d"));
                arqTv.setText(Html.fromHtml(p.getGoalkeeping() + "<sub><small>+" + p.getLast_upgrade().getGoalkeeping() + "</small></sub>"));
            }else{
                arqTv.setText(p.getGoalkeeping() + "");
                arqTv.setTextColor(Color.parseColor("#000000"));
            }

            if(p.getLast_upgrade() != null && p.getLast_upgrade().getDefending() > 0){
                defTv.setTextColor(Color.parseColor("#3c763d"));
                defTv.setText(Html.fromHtml(p.getDefending() + "<sub><small>+" + p.getLast_upgrade().getDefending() + "</small></sub>"));
            }else{
                defTv.setText(p.getDefending() + "");
                defTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getDribbling() > 0){
                gamTv.setTextColor(Color.parseColor("#3c763d"));
                gamTv.setText(Html.fromHtml(p.getDribbling() + "<sub><small>+" + p.getLast_upgrade().getDribbling() + "</small></sub>"));
            }else{
                gamTv.setText(p.getDribbling() + "");
                gamTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getHeading() > 0){
                cabTv.setTextColor(Color.parseColor("#3c763d"));
                cabTv.setText(Html.fromHtml(p.getHeading() + "<sub><small>+" + p.getLast_upgrade().getHeading() + "</small></sub>"));
            }else{
                cabTv.setText(p.getHeading() + "");
                cabTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getJumping() > 0){
                salTv.setTextColor(Color.parseColor("#3c763d"));
                salTv.setText(Html.fromHtml(p.getHeading() + "<sub><small>+" + p.getLast_upgrade().getJumping() + "</small></sub>"));
            }else{
                salTv.setText(p.getJumping() + "");
                salTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getPassing() > 0){
                pasTv.setTextColor(Color.parseColor("#3c763d"));
                pasTv.setText(Html.fromHtml(p.getPassing() + "<sub><small>+" + p.getLast_upgrade().getPassing() + "</small></sub>"));
            }else{
                pasTv.setText(p.getPassing() + "");
                pasTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getPrecision() > 0){
                preTv.setTextColor(Color.parseColor("#3c763d"));
                preTv.setText(Html.fromHtml(p.getPrecision() + "<sub><small>+" + p.getLast_upgrade().getPrecision() + "</small></sub>"));
            }else{
                preTv.setText(p.getPrecision() + "");
                preTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getSpeed() > 0){
                velTv.setTextColor(Color.parseColor("#3c763d"));
                velTv.setText(Html.fromHtml(p.getSpeed() + "<sub><small>+" + p.getLast_upgrade().getSpeed() + "</small></sub>"));
            }else{
                velTv.setText(p.getSpeed() + "");
                velTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getStrength() > 0){
                fueTv.setTextColor(Color.parseColor("#3c763d"));
                fueTv.setText(Html.fromHtml(p.getStrength() + "<sub><small>+" + p.getLast_upgrade().getStrength() + "</small></sub>"));
            }else{
                fueTv.setText(p.getStrength() + "");
                fueTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getTackling() > 0){
                quiTv.setTextColor(Color.parseColor("#3c763d"));
                quiTv.setText(Html.fromHtml(p.getTackling() + "<sub><small>+" + p.getLast_upgrade().getTackling() + "</small></sub>"));
            }else{
                quiTv.setText(p.getTackling() + "");
                quiTv.setTextColor(Color.parseColor("#000000"));
            }
            if(p.getLast_upgrade() != null && p.getLast_upgrade().getExperience() > 0){
                expTv.setTextColor(Color.parseColor("#3c763d"));
                expTv.setText(Html.fromHtml(p.getExperience() + "<sub><small>+" + p.getLast_upgrade().getExperience() + "</small></sub>"));
            }else{
                expTv.setText(p.getExperience() + "");
                expTv.setTextColor(Color.parseColor("#000000"));
            }



            enerTv.setText(p.getStamina() + "");
            enerTv.setTextColor(Color.parseColor("#000000"));

            setStyleForEnergyProgressBar(energyBar, p.getStamina(), getActivity());


            expBar.setProgress(0);

            ObjectAnimator animation = ObjectAnimator.ofInt(expBar, "progress", p.getExperience());
            animation.setDuration(1000); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();

            statisticsPlayerName.setText(p.getNumber() + "." + " " + p.getShort_name());
             if(p.getLast_upgrade() == null){
                up.setVisibility(View.GONE);
            }else{
                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_circle_up, up);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#02ac01"));
                up.setVisibility(View.VISIBLE);
            }

            up.invalidate();
            if(p.getStamina() < 40){
                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_down, down);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#f00000"));
                down.setVisibility(View.VISIBLE);
            }else{
                down.setVisibility(View.GONE);
            }
            down.invalidate();
            if(p.getCards_count() == 2){

                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, yellowCard);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#F8F119"));
                yellowCard.setVisibility(View.VISIBLE);
            }else{
                yellowCard.setVisibility(View.GONE);
            }
            yellowCard.invalidate();
            if(p.isSuspended()){

                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, redCard);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#f00000"));
                redCard.setVisibility(View.VISIBLE);
            }else{
                redCard.setVisibility(View.GONE);
            }
            redCard.invalidate();
            if(p.getInjury_id() != 0){

                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_medkit, medkit);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#f00000"));
                injuredTime.setText("" + p.getRecovery());
                injuredTime.setVisibility(View.VISIBLE);
                medkit.setVisibility(View.VISIBLE);

            }else{
                medkit.setVisibility(View.GONE);
                injuredTime.setVisibility(View.GONE);
            }
            medkit.invalidate();
            if(p.isRetiring()){

                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_user_times, retired);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#f00000"));
                retired.setVisibility(View.VISIBLE);
            }else{
                retired.setVisibility(View.GONE);
            }
            retired.invalidate();
            if(p.isHealed()){

                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_plus_circle, healted);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#02ac01"));
                healted.setVisibility(View.VISIBLE);
            }else{
                healted.setVisibility(View.GONE);
            }
            healted.invalidate();
            transferable.invalidate();
            if(p.isTransferable()){

                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_share_square_solid, transferable);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#02ac01"));
                transferable.setVisibility(View.VISIBLE);
            }else{
                transferable.setVisibility(View.GONE);
            }
            transferable.invalidate();
            statisticsLayout.setVisibility(View.VISIBLE);

            Defaultdata.loadRadarPlayer(p, radarChart, getActivity());

        }
        private void initializeIds(){
            Log.e("user is null?", (user == null) + "");
            Log.e("user2 is null?", (user.getUser().getTeam().getFormation_objects().get(0) == null) + "");
            player_1.setTag(user.getUser().getTeam().getFormation_objects().get(0).getId());
            player_2.setTag(user.getUser().getTeam().getFormation_objects().get(1).getId());
            player_3.setTag(user.getUser().getTeam().getFormation_objects().get(2).getId());
            player_4.setTag(user.getUser().getTeam().getFormation_objects().get(3).getId());
            player_5.setTag(user.getUser().getTeam().getFormation_objects().get(4).getId());
            player_6.setTag(user.getUser().getTeam().getFormation_objects().get(5).getId());
            player_7.setTag(user.getUser().getTeam().getFormation_objects().get(6).getId());
            player_8.setTag(user.getUser().getTeam().getFormation_objects().get(7).getId());
            player_9.setTag(user.getUser().getTeam().getFormation_objects().get(8).getId());
            player_10.setTag(user.getUser().getTeam().getFormation_objects().get(9).getId());
            player_11.setTag(user.getUser().getTeam().getFormation_objects().get(10).getId());
            player_12.setTag(user.getUser().getTeam().getFormation_objects().get(11).getId());
            player_13.setTag(user.getUser().getTeam().getFormation_objects().get(12).getId());
            player_14.setTag(user.getUser().getTeam().getFormation_objects().get(13).getId());
            player_15.setTag(user.getUser().getTeam().getFormation_objects().get(14).getId());
            player_16.setTag(user.getUser().getTeam().getFormation_objects().get(15).getId());
            player_17.setTag(user.getUser().getTeam().getFormation_objects().get(16).getId());
            player_18.setTag(user.getUser().getTeam().getFormation_objects().get(17).getId());
            if(user.getUser().getTeam().getFormation_objects().size() >= 19){
                player_19.setTag(user.getUser().getTeam().getFormation_objects().get(18).getId());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 20){
                player_20.setTag(user.getUser().getTeam().getFormation_objects().get(19).getId());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 21){
                player_21.setTag(user.getUser().getTeam().getFormation_objects().get(20).getId());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 22){
                player_22.setTag(user.getUser().getTeam().getFormation_objects().get(21).getId());
                Log.e("setTag", "player_22 ->id " + player_22.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 23){
                player_23.setTag(user.getUser().getTeam().getFormation_objects().get(22).getId());
                Log.e("setTag", "player_23 ->id " + player_23.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 24){
                player_24.setTag(user.getUser().getTeam().getFormation_objects().get(23).getId());
                Log.e("setTag", "player_24 ->id " + player_24.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 25){
                player_25.setTag(user.getUser().getTeam().getFormation_objects().get(24).getId());
                Log.e("setTag", "player_25 ->id " + player_25.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 26){
                player_26.setTag(user.getUser().getTeam().getFormation_objects().get(25).getId());
                Log.e("setTag", "player_26 ->id " + player_26.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 27){
                player_27.setTag(user.getUser().getTeam().getFormation_objects().get(26).getId());
                Log.e("setTag", "player_27 ->id " + player_27.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 28){
                player_28.setTag(user.getUser().getTeam().getFormation_objects().get(27).getId());
                Log.e("setTag", "player_28 ->id " + player_28.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 29){
                player_29.setTag(user.getUser().getTeam().getFormation_objects().get(28).getId());
                Log.e("setTag", "player_29->id " + player_29.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 30){
                player_30.setTag(user.getUser().getTeam().getFormation_objects().get(29).getId());
                Log.e("setTag", "player_30 ->id " + player_30.getTag());
            }
            zone_1.setTag(user.getUser().getTeam().getFormation_objects().get(0).getId());
            zone_2.setTag(user.getUser().getTeam().getFormation_objects().get(1).getId());
            zone_3.setTag(user.getUser().getTeam().getFormation_objects().get(2).getId());
            zone_4.setTag(user.getUser().getTeam().getFormation_objects().get(3).getId());
            zone_5.setTag(user.getUser().getTeam().getFormation_objects().get(4).getId());
            zone_6.setTag(user.getUser().getTeam().getFormation_objects().get(5).getId());
            zone_7.setTag(user.getUser().getTeam().getFormation_objects().get(6).getId());
            zone_8.setTag(user.getUser().getTeam().getFormation_objects().get(7).getId());
            zone_9.setTag(user.getUser().getTeam().getFormation_objects().get(8).getId());
            zone_10.setTag(user.getUser().getTeam().getFormation_objects().get(9).getId());
            zone_11.setTag(user.getUser().getTeam().getFormation_objects().get(10).getId());
            zone_12.setTag(user.getUser().getTeam().getFormation_objects().get(11).getId());
            zone_13.setTag(user.getUser().getTeam().getFormation_objects().get(12).getId());
            zone_14.setTag(user.getUser().getTeam().getFormation_objects().get(13).getId());
            zone_15.setTag(user.getUser().getTeam().getFormation_objects().get(14).getId());
            zone_16.setTag(user.getUser().getTeam().getFormation_objects().get(15).getId());
            zone_17.setTag(user.getUser().getTeam().getFormation_objects().get(16).getId());
            zone_18.setTag(user.getUser().getTeam().getFormation_objects().get(17).getId());
            if(user.getUser().getTeam().getFormation_objects().size() >= 19){
                zone_19.setTag(user.getUser().getTeam().getFormation_objects().get(18).getId());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 20){
                zone_20.setTag(user.getUser().getTeam().getFormation_objects().get(19).getId());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 21){
                zone_21.setTag(user.getUser().getTeam().getFormation_objects().get(20).getId());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 22){
                zone_22.setTag(user.getUser().getTeam().getFormation_objects().get(21).getId());
                Log.e("setTag", "zone_22 ->id " + zone_22.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 23){
                zone_23.setTag(user.getUser().getTeam().getFormation_objects().get(22).getId());
                Log.e("setTag", "zone_23 ->id " + zone_23.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 24){
                zone_24.setTag(user.getUser().getTeam().getFormation_objects().get(23).getId());
                Log.e("setTag", "zone_24 ->id " + zone_24.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 25){
                zone_25.setTag(user.getUser().getTeam().getFormation_objects().get(24).getId());
                Log.e("setTag", "zone_25 ->id " + zone_25.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 26){
                zone_26.setTag(user.getUser().getTeam().getFormation_objects().get(25).getId());
                Log.e("setTag", "zone_26 ->id " + zone_26.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 27){
                zone_27.setTag(user.getUser().getTeam().getFormation_objects().get(26).getId());
                Log.e("setTag", "zone_27 ->id " + zone_27.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 28){
                zone_28.setTag(user.getUser().getTeam().getFormation_objects().get(27).getId());
                Log.e("setTag", "zone_28 ->id " + zone_28.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 29){
                zone_29.setTag(user.getUser().getTeam().getFormation_objects().get(28).getId());
                Log.e("setTag", "zone_29->id " + zone_29.getTag());
            }
            if(user.getUser().getTeam().getFormation_objects().size() >= 30){
                zone_30.setTag(user.getUser().getTeam().getFormation_objects().get(29).getId());
                Log.e("setTag", "zone_30 ->id " + zone_30.getTag());
            }
            zones.removeAll(zones);
            zones.addAll(Arrays.asList(zone_1, zone_2, zone_3, zone_4, zone_5, zone_6, zone_7, zone_8, zone_9,zone_10,zone_11,zone_12,zone_13, zone_14, zone_15, zone_16, zone_17, zone_18, zone_19,zone_20,zone_21,zone_22,zone_23,zone_24,zone_25,zone_26,zone_27,zone_28,zone_29,zone_30));
            players.removeAll(players);
            players.addAll(Arrays.asList(player_1, player_2, player_3, player_4, player_5, player_6, player_7, player_8, player_9,player_10,player_11,player_12,player_13, player_14, player_15, player_16, player_17, player_18, player_19,player_20,player_21,player_22,player_23,player_24,player_25,player_26,player_27,player_28,player_29,player_30));
            playersImage.removeAll(playersImage);
            playersImage.addAll(Arrays.asList(player_image_1, player_image_2, player_image_3, player_image_4, player_image_5, player_image_6, player_image_7, player_image_8, player_image_9,player_image_10,player_image_11,player_image_12,player_image_13, player_image_14, player_image_15, player_image_16, player_image_17, player_image_18, player_image_19,player_image_20,player_image_21,player_image_22,player_image_23,player_image_24,player_image_25,player_image_26,player_image_27,player_image_28,player_image_29,player_image_30));
            for(RelativeLayout zone : zones){
                RelativeLayout player = (RelativeLayout) zone.getChildAt(0);
                if(zone.getTag() != player.getTag()){
                    player.setTag(zone.getTag());
                }
            }

        }

        private void loadIds(){



            Strategy strategy = null;
            for(Strategy s : strategies){
                if(s.getId() == user.getUser().getTeam().getStrategy_id()){
                    strategy = s;
                }
            }



            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(fieldLayout);

            for(int j = 0; j < zones.size(); j++) {
                if (user.getUser().getTeam().getFormation_objects().size() > j){
                    for (int i = 0; i < ((RelativeLayout) zones.get(j).getChildAt(0)).getChildCount(); i++) {
                        zones.get(j).setTag(user.getUser().getTeam().getFormation_objects().get(j).getId());
                        if (zones.get(j).getChildAt(0) instanceof RelativeLayout) {
                            View view = ((RelativeLayout) zones.get(j).getChildAt(0)).getChildAt(i);
                            view.setTag(user.getUser().getTeam().getFormation_objects().get(j).getId());
                            if (view instanceof ImageView) {
                                ((ImageView) view).setBackgroundResource(Defaultdata.playerColors.get(user.getUser().getTeam().getFormation_objects().get(j).getPosition()));


                            } else if (view instanceof TextView) {
                                ((TextView) view).setText(user.getUser().getTeam().getFormation_objects().get(j).getNumber() + "");

                                ((TextView) view).setTextColor(Color.parseColor("#000000"));

                            }
                            if (!detailsAdded.get(j)) {
                                addDetailsView((RelativeLayout) zones.get(j).getChildAt(0), user.getUser().getTeam().getFormation_objects().get(j));
                                detailsAdded.set(j, true);
                            }

                        }
                    }
                }else{
                    Log.e("GONE", j + " zona");
                    zones.get(j).setVisibility(View.INVISIBLE);
                }
            }
            for(int i=0; i< 11; i++){
                constraintSet.connect(zones.get(i).getId(), ConstraintSet.TOP, fieldLayout.getId(), ConstraintSet.TOP, 0);
                constraintSet.connect(zones.get(i).getId(), ConstraintSet.LEFT, fieldLayout.getId(), ConstraintSet.LEFT, 0);

                constraintSet.setVerticalBias(zones.get(i).getId(), 1 - (float)strategy.getPositions().get(i).getLeft()/100);
                constraintSet.setHorizontalBias(zones.get(i).getId(), 1 - (float)strategy.getPositions().get(i).getTop()/100);
                constraintSet.applyTo(fieldLayout);
                TransitionManager.beginDelayedTransition(fieldLayout);
            }


        }

        // TODO: Rename method, update argument and hook method into UI event
        public void onButtonPressed(Uri uri) {
            if (mListener != null) {
                mListener.onFragmentInteraction(uri);
            }
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
            System.out.println("onAttach");
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }
        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }
        private void pathStrategy(int i){

            if(spinnerSelectionFirstTime){

            svc = Svc.initAuth(getContext());
            PatchStrategy patchStrategy =  new PatchStrategy();
            patchStrategy.setDevice_id(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
            patchStrategy.setStrategy(strategies.get(i).getId());

            Call<getTeam> call = svc.pathStrategy(patchStrategy);
            call.enqueue(new Callback<getTeam>() {
                @Override
                public void onResponse(Call<getTeam> call, Response<getTeam> response) {

                    final Response<getTeam> responsefinal = response;
                    final getTeam team = response.body();

                    if (response.isSuccessful()) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(responsefinal.code() == 400){
                                    Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                                }else if(responsefinal.code() == 201 || responsefinal.code() == 204 ||responsefinal.code() == 200){

                                    user.getUser().setTeam(team.getTeam());
                                    ((MainActivity) getActivity()).setUser(user);

                                }
                                initializeIds();
                                loadIds();
                            }
                        });
                    } else {
                        try {
                                Log.e("error code :",response.toString() + "");
                                Log.e("error code :",response.code() + "");
                               Log.e("error:",response.message());
                               Log.v("Error ", response.errorBody().string());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        if(responsefinal.code() == 500|| responsefinal.code() == 503){
                            Toast.makeText(getContext(),
                                    getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                        }
                        loadIds();
                    }
                }

                @Override
                public void onFailure(Call<getTeam> call, Throwable t){
                    t.printStackTrace();
                    Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                    loadIds();
                }
            });

            }else{
                spinnerSelectionFirstTime = true;
            }

        }
        private void patchFormation() {
            List<Long> formation = new ArrayList<>();

            boolean notNull = true;

            for (int i = 0; i < 18; i++) {
                formation.add((Long) zones.get(i).getChildAt(0).getTag());
            }
            for(Long  i: formation){
               if(i == 0) {
                notNull = false;
               }
            }
            Set<Long> set = new HashSet<Long>(formation);
            if(set.size() < formation.size()){
                notNull = false;
            }
            Log.e("formation", formation.toString());
            if(notNull){
                PatchFormation patchFormation = new PatchFormation();
                patchFormation.setDevice_id(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
                patchFormation.setFormation(formation);

                svc = Svc.initAuth(getContext());

                Call<getTeam> call = svc.pathFormation(patchFormation);
                call.enqueue(new Callback<getTeam>() {
                    @Override
                    public void onResponse(Call<getTeam> call, Response<getTeam> response) {

                        final Response<getTeam> responsefinal = response;
                        final getTeam team = response.body();
                        Log.e("team", team.getTeam().toString());
                        if (response.isSuccessful()) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (responsefinal.code() == 400) {
                                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                                    } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {
                                        user.getUser().setTeam(team.getTeam());
                                        ((MainActivity) getActivity()).setUser(user);
                                    }
                                    initializeIds();
                                    loadIds();
                                }
                            });
                        } else {
                            try {
                                Log.e("error code :", response.toString() + "");
                                Log.e("error code :", response.code() + "");
                                Log.e("error:", response.message());
                                Log.v("Error ", response.errorBody().string());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (responsefinal.code() == 500 || responsefinal.code() == 503) {
                                Toast.makeText(getContext(),
                                        getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                            }
                            initializeIds();
                            loadIds();
                        }
                    }

                    @Override
                    public void onFailure(Call<getTeam> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                        loadIds();
                    }
                });
        }else{
                ((MainActivity)getActivity()).getMe();
                ((MainActivity)getActivity()).changeToStrategyFragment();
            }
        }
    private void addDetailsView(ViewGroup zone, Player player){


        int detailsAdded = 0;

         if(player.getLast_upgrade() == null){
            up.setVisibility(View.GONE);
        }else{
            ImageView up = new ImageView(getActivity());
             RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 15 * (int)density/2;
            paramsImage.width = 15 * (int)density/2;
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_circle_up, up);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#02ac01"));
            up.setLayoutParams(paramsImage);
            detailsAdded++;
            zone.addView(up);
        }


        if(player.getStamina() < 40){
            ImageView down = new ImageView(getActivity());
            RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 15 * (int)density/2;
            paramsImage.width = 15 * (int)density/2;

            down.setLayoutParams(paramsImage);
            detailsAdded++;

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_down, down);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
            zone.addView(down);
        }
        if(player.getCards_count() == 2){
            ImageView yellowCard = new ImageView(getActivity());
            RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 14 * (int)density/2;
            paramsImage.width = 12 * (int)density/2;
            yellowCard.setLayoutParams(paramsImage);
            detailsAdded++;
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, yellowCard);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#F8F119"));
            zone.addView(yellowCard);
        }
        if(player.isSuspended()){
            ImageView redCard = new ImageView(getActivity());
            RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 14 * (int)density/2;
            paramsImage.width = 12 * (int)density/2;

            redCard.setLayoutParams(paramsImage);
            detailsAdded++;
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, redCard);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
            zone.addView(redCard);
        }
        if(player.getInjury_id() != 0){
            ImageView medkit = new ImageView(getActivity());
            RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 15 * (int)density/2;
            paramsImage.width = 15 * (int)density/2;
            medkit.setLayoutParams(paramsImage);
            detailsAdded++;

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_medkit, medkit);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));



            zone.addView(medkit);

        }
        if(player.isRetiring()){
            ImageView retired = new ImageView(getActivity());
            RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 15 * (int)density/2;
            paramsImage.width = 15 * (int)density/2;
            retired.setLayoutParams(paramsImage);
            detailsAdded++;
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_user_times, retired);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
            zone.addView(retired);
        }
        if(player.isHealed()){
            ImageView healted = new ImageView(getActivity());
            RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 15 * (int)density/2;
            paramsImage.width = 15 * (int)density/2;
            healted.setLayoutParams(paramsImage);
            detailsAdded++;

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_plus_circle, healted);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#3c763d"));
            zone.addView(healted);
        }
        if(player.isTransferable()){
            ImageView healted = new ImageView(getActivity());
            RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 15 * (int)density/2;
            paramsImage.width = 15 * (int)density/2;
            healted.setLayoutParams(paramsImage);
            detailsAdded++;

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_share_square_solid, healted);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#3c763d"));
            zone.addView(healted);
        }
    }
    private void detailsPosition(){

    }


    public static int getPixelValue(Context context, int dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }
    public void resizeScreen(){
        /*
        0.75 - ldpi
        1.0 - mdpi
        1.5 - hdpi
        2.0 - xhdpi
        3.0 - xxhdpi
        4.0 - xxxhdpi
         */
        boolean resize = false;
        Log.e("DisplayMetrics","" + getResources().getDisplayMetrics().density);
        density = getResources().getDisplayMetrics().density;

        String toastMsg = "" + getResources().getDisplayMetrics().density;
        if(density <= 0.75) {


        }else if(density > 0.75 && density <= 1.0) {

        }else if(density > 1.0  && density <= 1.5) {

            guideline2.setGuidelinePercent((float)0.75);
        }else if(density > 1.5 && density <= 2.0) {
            guideline2.setGuidelinePercent((float)0.78); //Density 1.8 BQ E5

        }else if(density > 2.0 && density <= 3.0) {
            guideline2.setGuidelinePercent((float)0.75);
        }else if(density > 3.0) {

            guideline2.setGuidelinePercent((float)0.75);
        }
    //    Toast.makeText(getActivity(), "Density " + density,Toast.LENGTH_SHORT).show();

        if(resize) {
            for (RelativeLayout player : players) {
                if (player.getLayoutParams() != null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) player.getLayoutParams();
                    params.height = params.height + 200;
                    params.width = params.width + 200;

                    player.setLayoutParams(params);
                }

            }
            for (RelativeLayout player : zones) {
                if (player.getLayoutParams() != null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) player.getLayoutParams();
                    params.height = params.height + 200;
                    params.width = params.width + 200;

                    player.setLayoutParams(params);
                }

            }
            for (ImageView player : playersImage) {
                if (player.getLayoutParams() != null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) player.getLayoutParams();
                    params.height = params.height + 200;
                    params.width = params.width + 200;

                    player.setLayoutParams(params);
                }

            }
        }
    }

}