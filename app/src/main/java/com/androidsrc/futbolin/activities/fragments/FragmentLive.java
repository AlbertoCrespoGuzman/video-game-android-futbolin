package com.androidsrc.futbolin.activities.fragments;


        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.graphics.drawable.GradientDrawable;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ProgressBar;
        import android.widget.RelativeLayout;
        import android.widget.ScrollView;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.androidsrc.futbolin.R;
        import com.androidsrc.futbolin.activities.MainActivity;
        import com.androidsrc.futbolin.communications.Svc;
        import com.androidsrc.futbolin.communications.SvcApi;
        import com.androidsrc.futbolin.communications.http.auth.get.Category;
        import com.androidsrc.futbolin.communications.http.auth.get.CategoryPositions;
        import com.androidsrc.futbolin.communications.http.auth.get.FriendlyTeam;
        import com.androidsrc.futbolin.communications.http.auth.get.Highlight;
        import com.androidsrc.futbolin.communications.http.auth.get.Live;
        import com.androidsrc.futbolin.communications.http.auth.get.MatchLive;
        import com.androidsrc.futbolin.communications.http.auth.get.getUser;
        import com.androidsrc.futbolin.database.manager.DatabaseManager;
        import com.androidsrc.futbolin.database.models.TeamDB;
        import com.androidsrc.futbolin.utils.Defaultdata;
        import com.androidsrc.futbolin.utils.ScorersAccount;
        import com.androidsrc.futbolin.utils.adapters.AdapterClassificationLive;
        import com.androidsrc.futbolin.utils.adapters.AdapterClassificationTable;
        import com.androidsrc.futbolin.utils.adapters.AdapterLiveOthers;
        import com.androidsrc.futbolin.utils.adapters.FriendliesRecyclerAdapter;
        import com.devs.vectorchildfinder.VectorChildFinder;
        import com.devs.vectorchildfinder.VectorDrawableCompat;
        import com.google.android.material.button.MaterialButton;

        import java.text.DecimalFormat;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Calendar;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.Date;
        import java.util.GregorianCalendar;
        import java.util.List;
        import java.util.TimeZone;

        import androidx.appcompat.app.AppCompatDialog;
        import androidx.appcompat.widget.LinearLayoutCompat;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.constraintlayout.widget.ConstraintSet;
        import androidx.constraintlayout.widget.Guideline;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.transition.TransitionManager;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLive extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    getUser user;
    ImageView imageShield;
    private OnFragmentInteractionListener mListener;
    AppCompatDialog dialog;
    Live live;
    View v;
    TextView tournamentLabel, tournamentRoundLabel, matchAssistance, scoreLabel, clockLabel, localTeamLabel, visitTeamLabel;
    ProgressBar progressBar;
    ImageView localShield, visitShield;
    TableLayout highlights, localLineupTable, visitLineupTable;
    RecyclerView scoreOthersTable, classificationRecycler1, classificationRecycler2;

    TextView statsLocalTeam, statsVisitTeam, statsLocalGoals, statsVisitGoals, statsLocalShoots, statsVisitShoots, statsLocalFouls, statsVisitFouls, statsLocalCards, statsVisitCards, statsLocalSubs, statsVisitSubs;
    RelativeLayout player_1, zone_1,player_2, zone_2,player_3, zone_3,player_4, zone_4,player_5, zone_5,player_6, zone_6,player_7, zone_7,player_8, zone_8,player_9, zone_9,
            player_10, zone_10,player_11, zone_11,player_12, zone_12,player_13, zone_13,player_14, zone_14,player_15, zone_15,player_16, zone_16,player_17, zone_17,player_18, zone_18,
            player_19, zone_19,player_20, zone_20,player_21, zone_21,player_22, zone_22;
    List<RelativeLayout> zones = new ArrayList<>();
    MatchLive myLive;
    List<MatchLive> othersLive = new ArrayList<>();
    ConstraintLayout field, iconsImages;
    List<ScorersAccount> othersScores = new ArrayList<>();
    List<TextView> othersScoresTextView = new ArrayList<>();
    Thread liveUpdater;
    long finishTime = 15 * 60 * 1000;
    //long finishTime = 60 * 1000 * 60 * 2;
    long currentTime = 0;
    ScorersAccount myScorerAccount;
    int halfTime = 10;
    int minutes = 0;
    int seconds = 0;
    int endingTime = 10;
    MediaPlayer mpGoal, mpWhistle, mpWhistleFinish;
    DatabaseManager db;
    String pattern;
    DecimalFormat decimalFormat;
    ScrollView mainScroll;
    MaterialButton othersScorersButton;
    Guideline othersScorersGuideline;
    AdapterLiveOthers adapterLiveOthers;
    AdapterClassificationLive adapterClassificationTable;

    public FragmentLive() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLive newInstance(getUser user, String param2) {
        FragmentLive fragment = new FragmentLive();
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
            if(live == null){
                getLive();
            }
            ((MainActivity)getActivity()).hideBarLayout(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_live, container, false);
        return v;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void getLive(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_live_match) , getActivity());
        dialog.show();

        SvcApi svc;

        svc = Svc.initAuthTimeout(getContext());
        Call<Live> call = svc.getMatchLive(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<Live>() {
            @Override
            public void onResponse(Call<Live> call, final Response<Live> response) {
                final Response<Live> responsefinal = response;

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMe = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                live = responsefinal.body();
                                Log.e("live", live.toString());
                                loadFragment();
                            }
                        }
                    });
                } else {

                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Live> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    void loadFragment(){

        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);


        mpGoal = MediaPlayer.create(getActivity(), R.raw.goal);
        mpWhistle = MediaPlayer.create(getActivity(), R.raw.whistle);
        mpWhistleFinish = MediaPlayer.create(getActivity(), R.raw.final_whistle);
        player_1 = (RelativeLayout) v.findViewById(R.id.player_1);
        player_2 = (RelativeLayout) v.findViewById(R.id.player_2);
        player_3 = (RelativeLayout) v.findViewById(R.id.player_3);
        player_4 = (RelativeLayout) v.findViewById(R.id.player_4);
        player_5 = (RelativeLayout) v.findViewById(R.id.player_5);
        player_6 = (RelativeLayout) v.findViewById(R.id.player_6);
        player_7 = (RelativeLayout) v.findViewById(R.id.player_7);
        player_8 = (RelativeLayout) v.findViewById(R.id.player_8);
        player_9 = (RelativeLayout) v.findViewById(R.id.player_9);
        player_10 = (RelativeLayout) v.findViewById(R.id.player_10);
        player_11 = (RelativeLayout) v.findViewById(R.id.player_11);
        player_12 = (RelativeLayout) v.findViewById(R.id.player_12);
        player_13 = (RelativeLayout) v.findViewById(R.id.player_13);
        player_14 = (RelativeLayout) v.findViewById(R.id.player_14);
        player_15 = (RelativeLayout) v.findViewById(R.id.player_15);
        player_16 = (RelativeLayout) v.findViewById(R.id.player_16);
        player_17 = (RelativeLayout) v.findViewById(R.id.player_17);
        player_18 = (RelativeLayout) v.findViewById(R.id.player_18);
        player_19 = (RelativeLayout) v.findViewById(R.id.player_19);
        player_20 = (RelativeLayout) v.findViewById(R.id.player_20);
        player_21 = (RelativeLayout) v.findViewById(R.id.player_21);
        player_22 = (RelativeLayout) v.findViewById(R.id.player_22);



        //set ondrag listener for right and left parent views
        zone_1 = (RelativeLayout) v.findViewById(R.id.zone_1);
        zone_2 = (RelativeLayout) v.findViewById(R.id.zone_2);
        zone_3 = (RelativeLayout) v.findViewById(R.id.zone_3);
        zone_4 = (RelativeLayout) v.findViewById(R.id.zone_4);
        zone_5 = (RelativeLayout) v.findViewById(R.id.zone_5);
        zone_6 = (RelativeLayout) v.findViewById(R.id.zone_6);
        zone_7 = (RelativeLayout) v.findViewById(R.id.zone_7);
        zone_8 = (RelativeLayout) v.findViewById(R.id.zone_8);
        zone_9 = (RelativeLayout) v.findViewById(R.id.zone_9);
        zone_10 = (RelativeLayout) v.findViewById(R.id.zone_10);
        zone_11 = (RelativeLayout) v.findViewById(R.id.zone_11);
        zone_12 = (RelativeLayout) v.findViewById(R.id.zone_12);
        zone_13 = (RelativeLayout) v.findViewById(R.id.zone_13);
        zone_14 = (RelativeLayout) v.findViewById(R.id.zone_14);
        zone_15 = (RelativeLayout) v.findViewById(R.id.zone_15);
        zone_16 = (RelativeLayout) v.findViewById(R.id.zone_16);
        zone_17 = (RelativeLayout) v.findViewById(R.id.zone_17);
        zone_18 = (RelativeLayout) v.findViewById(R.id.zone_18);
        zone_19 = (RelativeLayout) v.findViewById(R.id.zone_19);
        zone_20 = (RelativeLayout) v.findViewById(R.id.zone_20);
        zone_21 = (RelativeLayout) v.findViewById(R.id.zone_21);
        zone_22 = v.findViewById(R.id.zone_22);

        zones.addAll(Arrays.asList(zone_1, zone_2, zone_3, zone_4, zone_5, zone_6, zone_7, zone_8, zone_9,zone_10,zone_11,zone_12,zone_13, zone_14, zone_15, zone_16, zone_17, zone_18, zone_19,zone_20,zone_21, zone_22));

        tournamentLabel  = v.findViewById(R.id.fragment_live_tournament_label );
        tournamentRoundLabel = v.findViewById(R.id.fragment_live_round_label );
        matchAssistance = v.findViewById(R.id.fragment_live_assistance_label );
        scoreLabel = v.findViewById(R.id.fragment_live_score_label );
        clockLabel = v.findViewById(R.id.fragment_live_clock );
        localTeamLabel = v.findViewById(R.id.fragment_live_local_team_label );
        visitTeamLabel = v.findViewById(R.id.fragment_live_visit_team_label );
        progressBar = v.findViewById(R.id.fragment_live_progress_bar_time );
        localShield = v.findViewById(R.id.fragment_live_local_team_shield );
        visitShield = v.findViewById(R.id.fragment_live_visit_team_shield );
        highlights = v.findViewById(R.id.fragment_live_highlights_table );
        localLineupTable = v.findViewById(R.id.fragment_live_formation_local );
        visitLineupTable = v.findViewById(R.id.fragment_live_formation_visit );
        statsLocalTeam = v.findViewById(R.id.fragment_live_stats_local_team_label );
        statsVisitTeam = v.findViewById(R.id.fragment_live_stats_visit_team_label );
        statsLocalGoals = v.findViewById(R.id.fragment_live_stats_goals_local_label );
        statsVisitGoals = v.findViewById(R.id.fragment_live_stats_goals_visit_label );
        statsLocalShoots = v.findViewById(R.id.fragment_live_stats_shoots_local_label );
        statsVisitShoots = v.findViewById(R.id.fragment_live_stats_shoots_visit_label );
        statsLocalFouls = v.findViewById(R.id.fragment_live_stats_fouls_local_label );
        statsVisitFouls = v.findViewById(R.id.fragment_live_stats_fouls_visit_label );
        statsLocalCards = v.findViewById(R.id.fragment_live_stats_cards_local_label );
        statsVisitCards = v.findViewById(R.id.fragment_live_stats_cards_visit_label );
        statsLocalSubs = v.findViewById(R.id.fragment_live_stats_substitutions_local_label );
        statsVisitSubs = v.findViewById(R.id.fragment_live_stats_substitutions_visit_label );
        field = v.findViewById(R.id.fragment_live_field);
        iconsImages = v.findViewById(R.id.fragment_live_icons_layout);
        scoreOthersTable = v.findViewById(R.id. fragment_live_score_others_table);
        classificationRecycler1 = v.findViewById(R.id.fragment_live_classification1);
        classificationRecycler2 = v.findViewById(R.id.fragment_live_classification2);
        mainScroll = v.findViewById(R.id.fragment_live_scrollview);
        othersScorersGuideline = v.findViewById(R.id.fragment_live_horizontal_guideline);
        othersScorersButton = v.findViewById(R.id.fragment_live_scorers_others_button);

        othersScorersButton.setTag("down");
        othersScorersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getTag().equals("up")){

                    ((MaterialButton) v).setIcon(getActivity().getResources().getDrawable(R.drawable.ic_angle_down));
                    v.setBackgroundResource(R.drawable.ic_angle_up);
                    v.setTag("down");
                    adapterLiveOthers = new AdapterLiveOthers(getActivity(), othersLive);
                    scoreOthersTable.setAdapter(adapterLiveOthers);
                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    scoreOthersTable.setLayoutManager(linearLayoutManager);

                    TransitionManager.beginDelayedTransition(scoreOthersTable);
                 //   othersScorersGuideline.setGuidelinePercent(0.85f);

                    ConstraintLayout constraintLayout = (ConstraintLayout) FragmentLive.this.v.findViewById(R.id.fragment_live_main_layout);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.setGuidelinePercent(R.id.fragment_live_horizontal_guideline, 0.85f); // 7% // range: 0 <-> 1
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    constraintSet.applyTo(constraintLayout);

                }else{
                    ((MaterialButton) v).setIcon(getActivity().getResources().getDrawable(R.drawable.ic_angle_up));
                    v.setBackgroundResource(R.drawable.ic_angle_down);
                    v.setTag("up");
                    adapterLiveOthers = new AdapterLiveOthers(getActivity(), new ArrayList<MatchLive>());
                    scoreOthersTable.setAdapter(adapterLiveOthers);
                    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    scoreOthersTable.setLayoutManager(linearLayoutManager);

                    TransitionManager.beginDelayedTransition(scoreOthersTable);

                    ConstraintLayout constraintLayout = (ConstraintLayout) FragmentLive.this.v.findViewById(R.id.fragment_live_main_layout);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);
                    constraintSet.setGuidelinePercent(R.id.fragment_live_horizontal_guideline, 1.50f); // 7% // range: 0 <-> 1
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    constraintSet.applyTo(constraintLayout);

                //    othersScorersGuideline.setGuidelinePercent(0.65f);
                }

            }
        });


        TransitionManager.beginDelayedTransition(highlights);

        myLive = live.getMatches().get(0);
        myScorerAccount= new ScorersAccount(myLive.getLocal().getName(), myLive.getVisit().getName());

        for(CategoryPositions categoryPositions : live.getPositions()){
            if(categoryPositions.getTeam_name().equals(myScorerAccount.getLocal_id())){
                    myScorerAccount.setPreviousLocalPoints(categoryPositions.getPoints());
                    myScorerAccount.setPreviousLocalDFs(categoryPositions.getGoals_difference());
             }else if(categoryPositions.getTeam_name().equals(myScorerAccount.getVisit_id())){
                    myScorerAccount.setPreviousVisitPoints(categoryPositions.getPoints());
                    myScorerAccount.setPreviousVIsitDFs(categoryPositions.getGoals_difference());
             }

        }
        tournamentLabel.setText(live.getCategory_name());

        tournamentRoundLabel.setText(getResources().getString(R.string.date) + " " + live.getRound_number() );

        matchAssistance.setText( decimalFormat.format( myLive.getAssistance()) + " " +
                getResources().getString(R.string.match_suporters) + " " +  decimalFormat.format(myLive.getIncomes()) + " $");

        VectorChildFinder vectorLocal = new VectorChildFinder(getActivity(), Defaultdata.shields.get((int)myLive.getLocal().getShield()), localShield);
        VectorDrawableCompat.VFullPath path21Local = vectorLocal.findPathByName("secundary_color1");
        path21Local.setFillColor(Color.parseColor(myLive.getLocal().getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Local = vectorLocal.findPathByName("secundary_color2");
        path22Local.setFillColor(Color.parseColor(myLive.getLocal().getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Local = vectorLocal.findPathByName("primary_color");
        path1Local.setFillColor(Color.parseColor(myLive.getLocal().getPrimary_color()));
        localShield.invalidate();

        VectorChildFinder vectorVisit = new VectorChildFinder(getActivity(), Defaultdata.shields.get(myLive.getVisit().getShield()), visitShield);
        VectorDrawableCompat.VFullPath path21Visit = vectorVisit.findPathByName("secundary_color1");
        path21Visit.setFillColor(Color.parseColor(myLive.getVisit().getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Visit = vectorVisit.findPathByName("secundary_color2");
        path22Visit.setFillColor(Color.parseColor(myLive.getVisit().getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Visit = vectorVisit.findPathByName("primary_color");
        path1Visit.setFillColor(Color.parseColor(myLive.getVisit().getPrimary_color()));
        visitShield.invalidate();

        scoreLabel.setText("0 - 0");
        localTeamLabel.setText(myLive.getLocal().getName());
        visitTeamLabel.setText(myLive.getVisit().getName());
        if(myLive.getLocal().getName().length() > 15){
            statsLocalTeam.setText(myLive.getLocal().getName().substring(0,15) + "...");
        }else{
            statsLocalTeam.setText(myLive.getLocal().getName());
        }
        if(myLive.getVisit().getName().length() > 15){
            statsVisitTeam.setText(myLive.getVisit().getName().substring(0,15) + "...");
        }else{
            statsVisitTeam.setText(myLive.getVisit().getName());
        }
        progressBar.setProgress(0);



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
                        shape.setColor(Color.parseColor(myLive.getLocal().getPrimary_color()));
                        shape.setStroke(2, Color.parseColor(myLive.getLocal().getSecondary_color()));
                        ((ImageView) view).setBackground(shape);
                        view.invalidate();


                    } else if (view instanceof TextView) {
                        ((TextView) view).setText(myLive.getLocal().getFormation().get(j).getNumber() + "");
                        if(myLive.getLocal().getPrimary_color().equals(myLive.getLocal().getSecondary_color())){
                            System.out.println("----------------------- " + myLive.getLocal().getSecondary_color().substring(0,4) + "FFF");
                            ((TextView) view).setTextColor(Color.parseColor(reverseStringColor(myLive.getLocal().getSecondary_color())));
                        }else{
                            ((TextView) view).setTextColor(Color.parseColor(myLive.getLocal().getSecondary_color()));
                        }

                        ((TextView) view).setTextSize(7);
                    }
                }
            }

        }


        for(int i=0; i< 11; i++){
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.TOP, field.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.LEFT, field.getId(), ConstraintSet.LEFT, 0);

            constraintSet.setVerticalBias(zones.get(i).getId(), myLive.getLocal().getFormation().get(i).getTop()/100);
            constraintSet.setHorizontalBias(zones.get(i).getId(), myLive.getLocal().getFormation().get(i).getLeft()/100);
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
                        shape.setColor(Color.parseColor(myLive.getVisit().getPrimary_color()));
                        shape.setStroke(2, Color.parseColor(myLive.getVisit().getSecondary_color()));
                        ((ImageView) view).setBackground(shape);
                        view.invalidate();


                    } else if (view instanceof TextView) {
                        ((TextView) view).setText(myLive.getVisit().getFormation().get(j -11).getNumber() + "");
                        if(myLive.getVisit().getPrimary_color().equals(myLive.getVisit().getSecondary_color())){

                            ((TextView) view).setTextColor(Color.parseColor(reverseStringColor(myLive.getVisit().getSecondary_color())));
                        }else{
                            ((TextView) view).setTextColor(Color.parseColor(myLive.getVisit().getSecondary_color()));
                        }

                        ((TextView) view).setTextSize(7);
                    }
                }
            }

        }
        for(int i=11; i< 22; i++){
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.TOP, field.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.LEFT, field.getId(), ConstraintSet.LEFT, 0);

            constraintSet.setVerticalBias(zones.get(i).getId(), (float)( myLive.getVisit().getFormation().get(i - 11).getTop()/100));
            constraintSet.setHorizontalBias(zones.get(i).getId(), (1 - (float)myLive.getVisit().getFormation().get(i - 11).getLeft()/100));
            constraintSet.applyTo(field);
            TransitionManager.beginDelayedTransition(field);
        }



        for(int i=0; i< myLive.getLocal().getFormation().size(); i++){
            TableRow row = new TableRow(getActivity());

            TextView localLineup = new TextView(getActivity());
            localLineup.setText(myLive.getLocal().getFormation().get(i).getShort_name() + "(" + myLive.getLocal().getFormation().get(i).getNumber()+")");
            if(i < 11) {
                localLineup.setTextColor(Color.parseColor("#333333"));
            }else{
                localLineup.setTextColor(Color.parseColor("#afafae"));
            }
            localLineup.setGravity(Gravity.RIGHT);
            localLineup.setTextSize(10);
            localLineup.setPadding(5,0,5,0);
            row.addView(localLineup);

            localLineupTable.addView(row);
        }
        for(int i=0; i< myLive.getVisit().getFormation().size(); i++){
            TableRow row = new TableRow(getActivity());

            TextView visitLineup = new TextView(getActivity());
            visitLineup.setText(myLive.getVisit().getFormation().get(i).getShort_name() + "(" + myLive.getVisit().getFormation().get(i).getNumber()+")");
            if(i < 11) {
                visitLineup.setTextColor(Color.parseColor("#333333"));
            }else{
                visitLineup.setTextColor(Color.parseColor("#afafae"));
            }
            visitLineup.setTextSize(10);
            visitLineup.setGravity(Gravity.LEFT);
            visitLineup.setPadding(5,0,5,0);
            row.addView(visitLineup);

            visitLineupTable.addView(row);
        }

        for(int i=1; i<live.getMatches().size(); i++){
            othersLive.add(live.getMatches().get(i));
        }

        TransitionManager.beginDelayedTransition(classificationRecycler2);

        for(MatchLive matchLive : othersLive){
            ScorersAccount scoreClass = new ScorersAccount(matchLive.getLocal().getName(),
                    matchLive.getVisit().getName());
            othersScores.add(scoreClass);
        }

        /*
                    SUPER IMPORTANTE HACER UN FOR BUSCANDO ID DE TEAMS ANOTAR PUNTOS Y GOLES
         */
        for(CategoryPositions categoryPositions : live.getPositions()){
            for(ScorersAccount scorersAccount :othersScores ){
                if(categoryPositions.getTeam_name().equals(scorersAccount.getLocal_id())){
                    scorersAccount.setPreviousLocalPoints(categoryPositions.getPoints());
                    scorersAccount.setPreviousLocalDFs(categoryPositions.getGoals_difference());
                }else if(categoryPositions.getTeam_name().equals(scorersAccount.getVisit_id())){
                    scorersAccount.setPreviousVisitPoints(categoryPositions.getPoints());
                    scorersAccount.setPreviousVIsitDFs(categoryPositions.getGoals_difference());
                }
            }
        }
        Log.e("otherScorers", "otherScorers = " + othersScores.toString());
        updateAdapters();
/*
        for(MatchLive matchLive : othersLive){
            ImageView shieldLocal = new ImageView(getActivity());
            VectorChildFinder shieldLocalVector = new VectorChildFinder(getActivity(), Defaultdata.shields.get(matchLive.getLocal().getShield()), shieldLocal);
            VectorDrawableCompat.VFullPath path21Local_ = shieldLocalVector.findPathByName("secundary_color1");
            path21Local_.setFillColor(Color.parseColor(matchLive.getLocal().getSecondary_color()));
            VectorDrawableCompat.VFullPath path22Local_ = shieldLocalVector.findPathByName("secundary_color2");
            path22Local_.setFillColor(Color.parseColor(matchLive.getLocal().getSecondary_color()));
            VectorDrawableCompat.VFullPath path1Local_ = shieldLocalVector.findPathByName("primary_color");
            path1Local_.setFillColor(Color.parseColor(matchLive.getLocal().getPrimary_color()));
            shieldLocal.invalidate();

            ImageView shieldVisit = new ImageView(getActivity());
            VectorChildFinder shieldVisitVector = new VectorChildFinder(getActivity(), Defaultdata.shields.get(matchLive.getVisit().getShield()), shieldVisit);
            VectorDrawableCompat.VFullPath path21Visit_ = shieldVisitVector.findPathByName("secundary_color1");
            path21Visit_.setFillColor(Color.parseColor(matchLive.getVisit().getSecondary_color()));
            VectorDrawableCompat.VFullPath path22Visit_ = shieldVisitVector.findPathByName("secundary_color2");
            path22Visit_.setFillColor(Color.parseColor(matchLive.getVisit().getSecondary_color()));
            VectorDrawableCompat.VFullPath path1Visit_ = shieldVisitVector.findPathByName("primary_color");
            path1Visit_.setFillColor(Color.parseColor(matchLive.getVisit().getPrimary_color()));
            shieldVisit.invalidate();

            ScorersAccount scoreClass = new ScorersAccount();
            othersScores.add(scoreClass);
            TextView score = new TextView(getActivity());
            score.setText(scoreClass.getResultGoal());
            score.setTextColor(Color.parseColor("#000000"));
            score.setTypeface(null, Typeface.BOLD);

            TextView localName = new TextView(getActivity());
            if(matchLive.getLocal().getName().length() > 15){
                localName.setText(matchLive.getLocal().getName().substring(0,15) + "...");
            }else{
                localName.setText(matchLive.getLocal().getName());
            }

            localName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            TextView visitName = new TextView(getActivity());
            if(matchLive.getVisit().getName().length() > 15){
                visitName.setText(matchLive.getVisit().getName().substring(0,15) + "...");
            }else{
                visitName.setText(matchLive.getVisit().getName());
            }

            TableRow row = new TableRow(getActivity());
            row.setPadding(0,5,0,5);
            row.addView(localName);
            row.addView(shieldLocal);
            row.addView(score);
            row.addView(shieldVisit);
            row.addView(visitName);

            scoreOthersTable.addView(row);
            othersScoresTextView.add(score);
        }
        for(int i=0; i< scoreOthersTable.getChildCount(); i++){
            TableRow row = (TableRow) scoreOthersTable.getChildAt(i);
            for(int j=0; j < row.getChildCount(); j++){
                View view = row.getChildAt(j);
                if(view instanceof ImageView){
                    if(getResources().getDisplayMetrics().density == 2.0) {
                        ((ImageView) view).getLayoutParams().width = 50;
                        ((ImageView) view).getLayoutParams().height = 50;
                        ((ImageView) view).setPadding(5, 5, 5, 5);
                    }else{
                        ((ImageView) view).getLayoutParams().width = 70;
                        ((ImageView) view).getLayoutParams().height = 70;
                        ((ImageView) view).setPadding(5, 5, 5, 5);
                    }
                }
            }
        }
        */


        TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(timeZone);
        long timeBA = calendar.getTimeInMillis();

        // SUPER IMPORTANTE !!!!
        currentTime = timeBA - live.getMatches().get(0).getDatetime() * 1000;//hola
        //    currentTime = timeBA - 1529556180000L;// hola
        Log.e("hora", "hora = " + new Date(timeBA) );
        Log.e("matches time", "matches time = " + new Date(live.getMatches().get(0).getDatetime() * 1000) );
        Log.e("hora", "currentTime = " + currentTime );
        Runnable runnable = new CountDownRunner();
        liveUpdater = new Thread(runnable);
        liveUpdater.start();
    }
    public String reverseStringColor(String word){
        String reverse = "#";

        for(int i = word.length() -1; i >= 1; i--){
            reverse = reverse + word.charAt(i);
        }

        return reverse;
    }
    public class CountDownRunner implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                //    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                //    e.printStackTrace();
                }
            }
        }
    }
    public void doWork() {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {

                try {

                    if(minutes != 45 && (minutes <= 90) || (minutes == 45 && halfTime == 0)){
                    currentTime += 1000;
                    if (currentTime <= finishTime) {

                        long liveTime = (long) ((double) currentTime * ((double) 90 / ((double) finishTime / (double) (60 * 1000))));

                        seconds = (int) (liveTime / 1000) % 60;
                        minutes = (int) ((liveTime / (1000 * 60)));
                        String secondsOk = (seconds < 10 ? "0" + seconds : seconds + "");
                        String minutesOk = (minutes < 10 ? "0" + minutes : minutes + "");
                        clockLabel.setText(minutesOk + ":" + (seconds < 10 ? "0" + seconds : seconds + ""));
                        boolean clearForNow = false;
                        boolean removeLast = false;
                        while (!clearForNow && myLive.getPlays().size() > 0) {
                            for (int i=0; i< myLive.getPlays().size() ; i++) {
                                Highlight h = myLive.getPlays().get(i);
                                if (Integer.parseInt(h.getMinutes().split(":")[0]) <
                                        Integer.parseInt(minutesOk) ||
                                        Integer.parseInt(h.getMinutes().split(":")[0])
                                                <= Integer.parseInt(minutesOk) &&
                                                Integer.parseInt(h.getMinutes().split(":")[1])
                                                        <= Integer.parseInt(secondsOk)) {

                                    TableRow row = new TableRow(getActivity());
                                    row.setBackgroundColor(Color.parseColor(h.getBackground()));

                                    TextView highLightTime = new TextView(getActivity());
                                    highLightTime.setText(h.getMinutes() + "'  ");
                                    highLightTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                                    String color = h.getColor();
                                    if (h.getColor().length() < 7) {
                                        color = "#000000";

                                    }

                                    highLightTime.setTextColor(Color.parseColor(color));
                                    highLightTime.setTextSize(12);
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(5, 3, 1, 3);
                                    // highLightTime.setLayoutParams(params);

                                    TextView highLightText = new TextView(getActivity());
                                    highLightText.setText(h.getHighlight());
                                    highLightText.setTextColor(Color.parseColor(color));
                                    highLightText.setTextSize(12);

                                    row.addView(highLightTime);
                                    row.addView(highLightText);
                                    TransitionManager.beginDelayedTransition(row);
                                    highlights.addView(row, 0);
                                    progressBar.setProgress(minutes);

                                    switch (h.getType()) {
                                        case 1:

                                            break;
                                        case 2:

                                            break;
                                        case 3:

                                            break;
                                        case 4:
                                            if (h.getTeam() == 0) {
                                                myScorerAccount.increaseFoul(1);
                                            } else {
                                                myScorerAccount.increaseFoul(0);
                                            }
                                            break;
                                        case 5:

                                            break;
                                        case 6:
                                            myScorerAccount.increaseGoal(h.getTeam());
                                            updateAdapters();
                                            if(Integer.parseInt(h.getMinutes().split(":")[0]) + 5 < minutes){
                                                playAudio("gol");
                                            }

                                            addImage("gol", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            break;
                                        case 7:

                                            break;
                                        case 8:
                                            break;
                                        case 9:
                                            break;
                                        case 10:

                                            break;
                                        case 11:
                                            myScorerAccount.increaseShootOut(h.getTeam());
                                            break;
                                        case 12:
                                            myScorerAccount.increaseShootOnTarget(h.getTeam());
                                            break;
                                        case 13:

                                            break;
                                        case 14:

                                            break;
                                        case 15:

                                            break;
                                        case 16:

                                            break;
                                        case 17:
                                            myScorerAccount.increaseShootOut(h.getTeam());
                                            break;
                                        case 18:
                                            myScorerAccount.increaseShootOnTarget(h.getTeam());
                                            break;
                                        case 19:
                                            myScorerAccount.increaseGoal(h.getTeam());
                                            updateAdapters();
                                            if(Integer.parseInt(h.getMinutes().split(":")[0]) + 5 < minutes){
                                                playAudio("gol");
                                            }
                                            addImage("gol", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            break;
                                        case 20:

                                            break;
                                        case 21:

                                            break;
                                        case 22:
                                            addImage("substitution", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            myScorerAccount.increaseSubs(h.getTeam());
                                            break;
                                        case 23:
                                            addImage("injure", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            if (myScorerAccount.getSubs(h.getTeam()) < 3) {
                                                myScorerAccount.increaseSubs(h.getTeam());
                                            }
                                            break;
                                        case 24:
                                            addImage("yellow_card", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            playAudio("foul");
                                            myScorerAccount.increaseYellowCard(h.getTeam());

                                            break;
                                        case 25:
                                            addImage("red_card", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            playAudio("foul");
                                            myScorerAccount.increaseRedCard(h.getTeam());

                                            break;
                                        case 26:
                                            addImage("red_card", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            playAudio("foul");
                                            myScorerAccount.increaseRedCard(h.getTeam());
                                            break;
                                        case 27:
                                            myScorerAccount.increaseGoal(h.getTeam());
                                            updateAdapters();
                                            if(Integer.parseInt(h.getMinutes().split(":")[0]) + 5 < minutes){
                                                playAudio("gol");
                                            }
                                            addImage("gol", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            break;
                                        case 28:
                                            break;
                                        case 29:
                                            break;
                                        case 30:
                                            break;
                                        case 31:
                                            playAudio("foul");
                                            if (h.getTeam() == 0) {
                                                myScorerAccount.increaseFoul(1);
                                            } else {
                                                myScorerAccount.increaseFoul(0);
                                            }
                                            addImage("penalty", Integer.parseInt(h.getMinutes().split(":")[0]));
                                            break;
                                        case 32:
                                            myScorerAccount.increaseShootOut(h.getTeam());
                                            break;
                                        case 33:
                                            myScorerAccount.increaseShootOnTarget(h.getTeam());
                                            break;
                                        case 35:
                                            myScorerAccount.increaseYellowCard(h.getTeam());
                                            break;

                                    }
                                    if (!scoreLabel.getText().toString().equals(myScorerAccount.getResultGoal())) {
                                        scoreLabel.setText(myScorerAccount.getResultGoal());
                                    }
                                    if (!statsLocalGoals.getText().toString().equals(myScorerAccount.getLocalGoal())) {
                                        statsLocalGoals.setText(String.valueOf(myScorerAccount.getLocalGoal()));
                                    }
                                    if (!statsVisitGoals.getText().toString().equals(myScorerAccount.getVisitGoal())) {
                                        statsVisitGoals.setText(String.valueOf(myScorerAccount.getVisitGoal()));
                                    }
                                    if (!statsLocalShoots.getText().toString().equals(myScorerAccount.getResultLocalShoots())) {
                                        statsLocalShoots.setText(String.valueOf(myScorerAccount.getResultLocalShoots()));
                                    }
                                    if (!statsVisitShoots.getText().toString().equals(myScorerAccount.getResultVisitShoots())) {
                                        statsVisitShoots.setText(String.valueOf(myScorerAccount.getResultVisitShoots()));
                                    }
                                    if (!statsLocalFouls.getText().toString().equals(myScorerAccount.getResultLocalFouls())) {
                                        statsLocalFouls.setText(String.valueOf(myScorerAccount.getResultLocalFouls()));
                                    }
                                    if (!statsVisitFouls.getText().toString().equals(myScorerAccount.getResultVisitFouls())) {
                                        statsVisitFouls.setText(String.valueOf(myScorerAccount.getResultVisitFouls()));
                                    }
                                    if (!statsLocalCards.getText().toString().equals(myScorerAccount.getResultLocalCards())) {
                                        statsLocalCards.setText(String.valueOf(myScorerAccount.getResultLocalCards()));
                                    }
                                    if (!statsVisitCards.getText().toString().equals(myScorerAccount.getResultVisitCards())) {
                                        statsVisitCards.setText(String.valueOf(myScorerAccount.getResultVisitCards()));
                                    }
                                    if (!statsLocalSubs.getText().toString().equals(myScorerAccount.getResultLocalSubs())) {
                                        statsLocalSubs.setText(String.valueOf(myScorerAccount.getResultLocalSubs()));
                                    }
                                    if (!statsVisitSubs.getText().toString().equals(myScorerAccount.getResultVisitSubs())) {
                                        statsVisitSubs.setText(String.valueOf(myScorerAccount.getResultVisitSubs()));
                                    }
                                    Log.e("myLive", h.getMinutes() + "|" + h.getHighlight());
                                    if(myLive.getPlays().size() > i+1) {
                                        myLive.getPlays().remove(i);
                                    }else{
                                        clearForNow = true;
                                        removeLast = true;
                                        break;
                                    }

                                    break;
                                } else {

                                    clearForNow = true;
                                    break;
                                }
                            }


                        }
                        if(removeLast){
                            myLive.getPlays().remove(0);
                        }

                            for (int i = 0; i < othersLive.size(); i++) {
                                boolean othersScorers = false;
                                boolean removeLastOthers = false;
                                while(!othersScorers && othersLive.get(i).getPlays().size() > 0){
                                for (int j=0; j<othersLive.get(i).getPlays().size(); j++) {
                                        Highlight h = othersLive.get(i).getPlays().get(j);
                                        if (Integer.parseInt(h.getMinutes().split(":")[0]) < Integer.parseInt(minutesOk) ||
                                            Integer.parseInt(h.getMinutes().split(":")[0]) <= Integer.parseInt(minutesOk)
                                                    && Integer.parseInt(h.getMinutes().split(":")[1]) <= Integer.parseInt(secondsOk)) {
                                        switch (h.getType()) {
                                            case 6: {
                                                othersScores.get(i).increaseGoal(h.getTeam());
                                                updateAdapters();
                                                break;
                                            }
                                            case 19: {
                                                othersScores.get(i).increaseGoal(h.getTeam());
                                                updateAdapters();
                                                break;
                                            }
                                            case 27: {
                                                othersScores.get(i).increaseGoal(h.getTeam());
                                                updateAdapters();
                                                break;
                                            }
                                            default:
                                                break;
                                        }
                                        /*if (!othersScoresTextView.get(i).getText().toString().equals(othersScores.get(i).getResultGoal())) {
                                            othersScoresTextView.get(i).setText(othersScores.get(i).getResultGoal());
                                        } */
                                        if(othersLive.get(i).getPlays().size() > j+1) {
                                            othersLive.get(i).getPlays().remove(j);
                                        }else{
                                            othersScorers = true;
                                            removeLastOthers = true;
                                            break;
                                        }
                                    } else {
                                        othersScorers = true;
                                        break;
                                    }
                                }
                            }if(removeLastOthers){
                                    othersLive.get(i).getPlays().remove(0);
                                }

                            }

                    }else{
                        // ENTRE TARDE !!

                    //    Intent intent= new Intent(((MainActivity)getActivity()), MainActivity.class);
                    //    ((MainActivity)getActivity()).startActivity(intent);
                   //     ((MainActivity)getActivity()).finish();
                        if(minutes <= 90 && endingTime > 0){
                            if(endingTime == 10){
                                playAudio("final");
                                clockLabel.setText("90:00");
                                progressBar.setProgress(90);
                            }
                            endingTime --;
                        }else if(minutes <= 90 && endingTime == 0){
                            if(liveUpdater != null && liveUpdater.isAlive()){
                                liveUpdater.interrupt();
                            }
                            Intent intent= new Intent(((MainActivity)getActivity()), MainActivity.class);
                            ((MainActivity)getActivity()).startActivity(intent);
                            ((MainActivity)getActivity()).finish();

                        }
                    }
                }else if(minutes == 45 && halfTime > 0){
                        if(halfTime == 10){
                            playAudio("final");
                        }
                        halfTime --;
                    }
                    else if(minutes <= 90){
                        playAudio("final");
                        if(liveUpdater != null && liveUpdater.isAlive()){
                            liveUpdater.interrupt();
                        }
                        Intent intent= new Intent(((MainActivity)getActivity()), MainActivity.class);
                        ((MainActivity)getActivity()).startActivity(intent);
                        ((MainActivity)getActivity()).finish();
                        endingTime --;
                    }else if(minutes <= 90 && endingTime == 0){
                        if(liveUpdater != null && liveUpdater.isAlive()){
                            liveUpdater.interrupt();
                        }
                        Intent intent= new Intent(((MainActivity)getActivity()), MainActivity.class);
                        ((MainActivity)getActivity()).startActivity(intent);
                        ((MainActivity)getActivity()).finish();

                    }
                }catch (Exception e) {
                }
            }
        });
    }
    void playAudio(String audio){
        DatabaseManager db = new DatabaseManager(getActivity());
        if(db.findNotification()!= null && db.findNotification().isLiveSoundsActive()) {
            if (mpGoal!= null && mpGoal.isPlaying()) {
                mpGoal.stop();
                mpGoal.reset();
        //        mpGoal.release();
            }
            if (mpWhistleFinish!= null && mpWhistleFinish.isPlaying()) {
                mpWhistleFinish.stop();
                mpWhistleFinish.reset();
          //      mpWhistleFinish.release();
            }
            if ( mpWhistle!= null && mpWhistle.isPlaying()) {
                Log.e("esoty", "1111");
                mpWhistle.stop();
                Log.e("esoty", "2222");
                mpWhistle.reset();
                Log.e("esoty", "333");
            //    mpWhistle.release();
                Log.e("esoty", "4444");
            }
            try {
                switch (audio) {
                    case "gol":
                        mpGoal.start();
                        break;
                    case "foul":
                        Log.e("esoty", "555");
                        mpWhistle.start();
                        Log.e("esoty", "6666");
                        break;
                    case "final":
                        mpWhistleFinish.start();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    void addImage(String image, long minutes){





        ImageView imageView =  new ImageView(getContext());
        imageView.setId(View.generateViewId());
        switch (image){
            case "penalty":
                VectorChildFinder vector6 = new VectorChildFinder(getContext(), R.drawable.ic_dot_circle_regular, imageView);
                VectorDrawableCompat.VFullPath path6 = vector6.findPathByName("color");
                path6.setFillColor(Color.parseColor("#000000"));
                break;
            case "gol":
                VectorChildFinder vector1 = new VectorChildFinder(getContext(), R.drawable.ic_ball, imageView);
                VectorDrawableCompat.VFullPath path1 = vector1.findPathByName("color");
                path1.setFillColor(Color.parseColor("#000000"));
                break;
            case "substitution":
                VectorChildFinder vector2 = new VectorChildFinder(getContext(), R.drawable.ic_substitution, imageView);
                VectorDrawableCompat.VFullPath path2 = vector2.findPathByName("color");
                path2.setFillColor(Color.parseColor("#000000"));
                break;
            case "injure":
                VectorChildFinder vector3 = new VectorChildFinder(getContext(), R.drawable.ic_injure, imageView);
                VectorDrawableCompat.VFullPath path3 = vector3.findPathByName("color");
                path3.setFillColor(Color.parseColor("#ff0000"));
                break;
            case "yellow_card":
                VectorChildFinder vector4 = new VectorChildFinder(getContext(), R.drawable.ic_square, imageView);
                VectorDrawableCompat.VFullPath path4 = vector4.findPathByName("color");
                path4.setFillColor(Color.parseColor("#F8F119"));
                break;
            case "red_card":
                VectorChildFinder vector5 = new VectorChildFinder(getContext(), R.drawable.ic_square, imageView);
                VectorDrawableCompat.VFullPath path5 = vector5.findPathByName("color");
                path5.setFillColor(Color.parseColor("#ff0000"));
                break;
        }
        LinearLayoutCompat.LayoutParams paramsImage = new LinearLayoutCompat.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        paramsImage.height = 10;
        paramsImage.width = 10;
  //      imageView.setLayoutParams(paramsImage);
        iconsImages.addView(imageView);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(iconsImages);
        constraintSet.connect(imageView.getId(), ConstraintSet.TOP, iconsImages.getId(), ConstraintSet.TOP, 0);
        constraintSet.connect(imageView.getId(), ConstraintSet.LEFT, iconsImages.getId(), ConstraintSet.LEFT, 0);
        constraintSet.connect(imageView.getId(), ConstraintSet.RIGHT, iconsImages.getId(), ConstraintSet.RIGHT, 0);
        constraintSet.connect(imageView.getId(), ConstraintSet.BOTTOM, iconsImages.getId(), ConstraintSet.BOTTOM, 0);
     //   constraintSet.setVerticalBias(imageView.getId(),  0.5f);
        constraintSet.setHorizontalBias(imageView.getId(),  (float)minutes/90);

        constraintSet.applyTo(iconsImages);
        TransitionManager.beginDelayedTransition(iconsImages);

    }

    public String getTeamName(long id){
        String text = "";
        if(db == null){
            db = new DatabaseManager(getActivity());
        }

        TeamDB teamDB = db.findTeamDBById(id);
        if(teamDB != null){
            text = teamDB.getShort_name();
        }
        return text;
    }
    public void updateAdapters(){
        for(ScorersAccount scorersAccount : othersScores){
            for(MatchLive matchLive : othersLive){
                if(matchLive.getLocal().getName().equals(scorersAccount.getLocal_id())){
                    matchLive.setScorersAccount(scorersAccount);
                }else if(matchLive.getVisit().getName().equals(scorersAccount.getVisit_id())){
                    matchLive.setScorersAccount(scorersAccount);
                }
            }
        }

        adapterLiveOthers = new AdapterLiveOthers(getActivity(), othersLive);
        scoreOthersTable.setAdapter(adapterLiveOthers);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        scoreOthersTable.setLayoutManager(linearLayoutManager);

        TransitionManager.beginDelayedTransition(scoreOthersTable);





        List<CategoryPositions> categoryPositions = live.getPositions();
        for(CategoryPositions ca : categoryPositions){
            if(ca.getTeam_name().equals(myScorerAccount.getLocal_id())){
                ca.setPoints(myScorerAccount.getLocalPoints());
                ca.setGoals_difference(myScorerAccount.getLocalGoalsDifference());
            }else if(ca.getTeam_name().equals(myScorerAccount.getVisit_id())){
                ca.setPoints(myScorerAccount.getVisitPoints());
                ca.setGoals_difference(myScorerAccount.getVisitGoalsDifference());
            }
        }

        for(CategoryPositions ca : categoryPositions){
            for(ScorersAccount scorersAccount : othersScores){
                if(scorersAccount.getLocal_id().equals(ca.getTeam_name())){
                    ca.setPoints(scorersAccount.getLocalPoints());
                    ca.setGoals_difference(scorersAccount.getLocalGoalsDifference());
                }else if(scorersAccount.getVisit_id().equals(ca.getTeam_name())){
                    ca.setPoints(scorersAccount.getVisitPoints());
                    ca.setGoals_difference(scorersAccount.getVisitGoalsDifference());
                }
            }
        }
        Collections.sort(categoryPositions, new Comparator<CategoryPositions>() {

            public int compare(CategoryPositions o1, CategoryPositions o2) {

                int sComp = o2.getPoints() - o1.getPoints();

                if (sComp != 0) {
                    return sComp;
                }

                return o2.getGoals_difference() - o1.getGoals_difference();
            }});
        Collections.sort(categoryPositions, new Comparator<CategoryPositions>() {
            public int compare(CategoryPositions i1, CategoryPositions i2) {
                    return i2.getPoints() - i1.getPoints();

            }
        });

        adapterClassificationTable = new AdapterClassificationLive(getActivity(), categoryPositions, 1);
        classificationRecycler1.setAdapter(adapterClassificationTable);
        final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        classificationRecycler1.setLayoutManager(linearLayoutManager2);

        TransitionManager.beginDelayedTransition(classificationRecycler1);

        adapterClassificationTable = new AdapterClassificationLive(getActivity(), categoryPositions, 1);
        classificationRecycler2.setAdapter(adapterClassificationTable);
        final LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getActivity());
        classificationRecycler2.setLayoutManager(linearLayoutManager3);

        TransitionManager.beginDelayedTransition(classificationRecycler2);

    }
}