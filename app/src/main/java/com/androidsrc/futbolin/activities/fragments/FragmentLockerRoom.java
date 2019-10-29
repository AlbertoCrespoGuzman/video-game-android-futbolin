package com.androidsrc.futbolin.activities.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.services.NextMatchNofiticationService;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.LastMatch;
import com.androidsrc.futbolin.communications.http.auth.get.TeamWithFormationId;
import com.androidsrc.futbolin.communications.http.auth.get.getMatch;
import com.androidsrc.futbolin.communications.http.auth.get.getTournament;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Notification;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.Strategy;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.adapters.AdapterClassificationTable;
import com.androidsrc.futbolin.utils.dialogs.MatchDialog;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import androidx.appcompat.app.AppCompatDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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
public class FragmentLockerRoom extends Fragment implements MatchDialog.DialogClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    getUser user;
    private OnFragmentInteractionListener mListener;
    AppCompatDialog dialog;
    SvcApi svc;
    getTournament tournament;
    View v;
    TextView tournamentName, nextMatchDate, stadiumName,localTeamName, visitTeamName, lastMatchesTournamentLabel;
    ImageView localShield, visitShield;
    Button seeAllTournament;
    TableLayout lastMatchesTournament, tournamentPositions, lastGeneralMatches;
    ConstraintLayout fieldLayout, totalLayout;
    RelativeLayout player_1, zone_1,player_2, zone_2,player_3, zone_3,player_4, zone_4,player_5, zone_5,player_6, zone_6,player_7, zone_7,player_8, zone_8,player_9, zone_9,
            player_10, zone_10,player_11, zone_11;
    List<RelativeLayout> zones = new ArrayList<>();
    List<RelativeLayout> players= new ArrayList<>();
    List<Boolean> detailsAdded = Arrays.asList(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false);
    List<Strategy> strategies = new ArrayList<>();
    DatabaseManager db;
    ScrollView scrollView;
    TeamWithFormationId team;
    AppCompatDialog postingMatchDialog;
    MatchDialog matchDialog;
    FragmentLockerRoom fragmentLockerRoom;
    RecyclerView classificationRecycler;
    float density;
    AdapterClassificationTable adapterClassificationTable;

    public FragmentLockerRoom() {
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
    public static FragmentLockerRoom newInstance(getUser user, String param2) {
        FragmentLockerRoom fragment = new FragmentLockerRoom();
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
            density = getResources().getDisplayMetrics().density;
            fragmentLockerRoom = this;
            if(tournament == null){
                getTournament();
            }else{
                loadFragment();
            }
        }
        Log.e("LockerRoom", "onCreate !!!!!!!!!!!!");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.e("onResume", "onResume fragment !!!!!!");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("LockerRoom", "onCreateView !!!!!!!!!!!!");
        v  = inflater.inflate(R.layout.fragment_locker_room, container, false);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Log.e("LockerRoom", " onButtonPressed!!!!!!!!!!!!");
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.e("LockerRoom", "onAttach !!!!!!!!!!!!");
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
        Log.e("LockerRoom", "onDetach !!!!!!!!!!!!");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onButtonClick() {
        if(matchDialog != null && matchDialog.isShowing()) {
            matchDialog.dismiss();
        }
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
    private void getTournament(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_tournament_data), getActivity());
        dialog.show();

        svc = Svc.initAuth(getContext());
        Call<getTournament> call = svc.getTournament(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getTournament>() {
            @Override
            public void onResponse(Call<getTournament> call, Response<getTournament> response) {

                final Response<getTournament> responsefinal = response;

                final getTournament getTournament = response.body();

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    if(getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (responsefinal.code() == 400) {

                                } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {
                                    tournament = getTournament;
                                    getTeam();
                                    updateNotificationNextMatch();
                                }
                            }
                        });
                    }
                } else {
                    try {
                        String error = response.errorBody().string();
                        Log.e("error getTournament", error);
                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            startActivity(mainActivity);
                            getActivity().finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getTournament> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "getTournament error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void getTeam(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_team_data) , getActivity());
        dialog.show();

        svc = Svc.initAuth(getContext());
        Log.e("ID TEAM", "ID TEAM" + user.getUser().getTeam().getId());
        Call<TeamWithFormationId> call = svc.getTeam(user.getUser().getTeam().getId(),
                Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<TeamWithFormationId>() {
            @Override
            public void onResponse(Call<TeamWithFormationId> call, Response<TeamWithFormationId> response) {

                final Response<TeamWithFormationId> responsefinal = response;
                final TeamWithFormationId getTeam = response.body();
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                team = getTeam;
                                Log.e("team", team.toString());
                                loadFragment();
                            }
                        }
                    });
                } else {
                    String error = "";
                    try {
                        error = response.errorBody().string();
                        Log.e("error getTeam", error);
                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            startActivity(mainActivity);
                            getActivity().finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        if(responsefinal.body() != null){
                            Log.e("getTeam ", "responsefinal.body().toString()" + responsefinal.body().toString());
                        }
                        Log.e("getTeam","error body "+ error);
                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamWithFormationId> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "get Team error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void loadFragment(){

        db = new DatabaseManager(getActivity());
        strategies = db.findAllStrategies();

        tournamentName = v.findViewById(R.id.fragment_locker_room_tournament_name);
        nextMatchDate= v.findViewById(R.id.fragment_locker_room_next_match_date);
        stadiumName = v.findViewById(R.id.fragment_locker_room_stadium_name);
        localTeamName= v.findViewById(R.id.fragment_locker_room_local_name);
        visitTeamName= v.findViewById(R.id.fragment_locker_room_visit_name);
        localShield= v.findViewById(R.id.fragment_locker_room_shield_local);
        visitShield = v.findViewById(R.id.fragment_locker_room_shield_visit);
        seeAllTournament = v.findViewById(R.id.fragment_locker_room_see_all_button);
        lastMatchesTournament = v.findViewById(R.id.fragment_locker_room_last_matches_table);
        classificationRecycler = v.findViewById(R.id.fragment_locker_room_tournament_positions_recycler);
        fieldLayout = v.findViewById(R.id.fragment_locker_room_field_constraint_layout);
        lastGeneralMatches = v.findViewById(R.id.fragment_locker_room_last_general_matches_table);
        lastMatchesTournamentLabel = v.findViewById(R.id.fragment_locker_room_last_matches_label);

        seeAllTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).changeToTournamentFragment();
            }
        });
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

        tournamentName.setText(tournament.getTournament().getName() + getResources().getString(R.string.zone) + " " +
                                tournament.getCategory().getZone_name() + " "
                +  getResources().getString(R.string.category_avb)+
                " " +
                                    tournament.getCategory().getCategory_name() + ")");

        SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if(tournament.getNext_match() != null && tournament.getNext_match().getLocal() != null && tournament.getNext_match().getVisit() != null) {
            nextMatchDate.setText(parser.format(new Date(tournament.getNext_match().getDatetime() * 1000)));
            stadiumName.setText(tournament.getNext_match().getStadium());

            VectorChildFinder vectorLocal = new VectorChildFinder(getActivity(), Defaultdata.shields.get(tournament.getNext_match().getLocal().getShield()), localShield);
            VectorDrawableCompat.VFullPath path21Local = vectorLocal.findPathByName("secundary_color1");
            path21Local.setFillColor(Color.parseColor(tournament.getNext_match().getLocal().getSecondary_color()));
            VectorDrawableCompat.VFullPath path22Local = vectorLocal.findPathByName("secundary_color2");
            path22Local.setFillColor(Color.parseColor(tournament.getNext_match().getLocal().getSecondary_color()));
            VectorDrawableCompat.VFullPath path1Local = vectorLocal.findPathByName("primary_color");
            path1Local.setFillColor(Color.parseColor(tournament.getNext_match().getLocal().getPrimary_color()));
            localShield.invalidate();

            VectorChildFinder vectorVisit = new VectorChildFinder(getActivity(), Defaultdata.shields.get(tournament.getNext_match().getVisit().getShield()), visitShield);
            VectorDrawableCompat.VFullPath path21Visit = vectorVisit.findPathByName("secundary_color1");
            path21Visit.setFillColor(Color.parseColor(tournament.getNext_match().getVisit().getSecondary_color()));
            VectorDrawableCompat.VFullPath path22Visit = vectorVisit.findPathByName("secundary_color2");
            path22Visit.setFillColor(Color.parseColor(tournament.getNext_match().getVisit().getSecondary_color()));
            VectorDrawableCompat.VFullPath path1Visit = vectorVisit.findPathByName("primary_color");
            path1Visit.setFillColor(Color.parseColor(tournament.getNext_match().getVisit().getPrimary_color()));
            visitShield.invalidate();

            localTeamName.setText(tournament.getNext_match().getLocal().getName());
            visitTeamName.setText(tournament.getNext_match().getVisit().getName());



            localShield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).changeToShowTeamFragment(tournament.getNext_match().getLocal().getId(), tournament.getNext_match().getLocal().getName(), "FragmentLockerRoom");
                }
            });

            visitShield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).changeToShowTeamFragment(tournament.getNext_match().getVisit().getId(), tournament.getNext_match().getVisit().getName(), "FragmentLockerRoom");
                }
            });

        }else{
            nextMatchDate.getLayoutParams().height = 1;
            localTeamName.getLayoutParams().height = 1;
            stadiumName.getLayoutParams().height = 1;
            visitTeamName.getLayoutParams().height = 1;
            localShield.getLayoutParams().height = 1;
            visitShield.getLayoutParams().height = 1;
        }
        boolean odd = true;
        for(LastMatch lm : tournament.getLast_matches()){
            TableRow tableRow = new TableRow(getContext());
            tableRow.setPadding(15,15,15,15);

            if(odd){
                tableRow.setBackgroundColor(getActivity().getResources().getColor(R.color.azul_suave));
            }

            TextView matchDate = new TextView(getContext());
            matchDate.setText(lm.getDate());
            matchDate.setTextSize(12);
            matchDate.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            TextView localTeam = new TextView(getContext());
            localTeam.setText(lm.getLocal() + " " + lm.getLocal_goals());
            localTeam.setTextSize(12);
            localTeam.setPadding(0,0,10,0);
            localTeam.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            TextView visitTeam = new TextView(getContext());
            visitTeam .setText(lm.getVisit() + " " + lm.getVisit_goals());
            visitTeam .setTextSize(12);
            visitTeam.setPadding(10,0,0,0);
            visitTeam .setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            ImageView seeImage =  new ImageView(getActivity());
            seeImage.requestLayout();

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_search, seeImage);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(getActivity().getResources().getColor(R.color.futbolinAzul));

            seeImage.setTag(lm.getLog_file());
            seeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("Look the match!" + view.getTag(), "onclick");
                    getMatch(view.getTag() + "");
                }
            });
            tableRow.addView(matchDate);
            tableRow.addView(localTeam);
            tableRow.addView(visitTeam);
            tableRow.addView(seeImage);

            lastMatchesTournament.addView(tableRow);
            odd = !odd;

        }

        for(int i=0; i <lastMatchesTournament.getChildCount(); i++){
            for(int j=0;j < ((ViewGroup) lastMatchesTournament.getChildAt(i)).getChildCount() ; j++){
                View view = ((ViewGroup) lastMatchesTournament.getChildAt(i)).getChildAt(j);
                if (view instanceof ImageView) {
                    Log.e("imageView", "LayoutParams null? " + (view.getLayoutParams() == null));
                    view.getLayoutParams().width = 30;
                    view.getLayoutParams().height = 30;
                }
            }
        }
        if(tournament.getLast_matches().size() == 0){
            lastMatchesTournamentLabel.setText(getActivity().getResources().getString(R.string.wait_for_next_tournament_begins));
        }
        adapterClassificationTable = new AdapterClassificationTable(getActivity(), tournament.getCategory().getPositions(), user.getUser().getTeam().getId());
        classificationRecycler.setAdapter(adapterClassificationTable);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        classificationRecycler.setLayoutManager(linearLayoutManager);

        TransitionManager.beginDelayedTransition(classificationRecycler);



        zones.addAll(Arrays.asList(zone_1, zone_2, zone_3, zone_4, zone_5, zone_6, zone_7, zone_8, zone_9,zone_10,zone_11));
        players.addAll(Arrays.asList(player_1, player_2, player_3, player_4, player_5, player_6, player_7, player_8, player_9,player_10,player_11));

        Strategy strategy = null;
        for(Strategy s : strategies){
            if(s.getId() == user.getUser().getTeam().getStrategy_id()){
                strategy = s;
            }
        }

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(fieldLayout);

        for(int j = 0; j < zones.size(); j++){

            for(int i = 0; i < ((RelativeLayout) zones.get(j).getChildAt(0)).getChildCount(); i++) {
                if (zones.get(j).getChildAt(0) instanceof RelativeLayout) {
                    View view = ((RelativeLayout) zones.get(j).getChildAt(0)).getChildAt(i);
                    if (view instanceof ImageView) {
                        if(user.getUser().getTeam().getFormation_objects().get(j) != null && user.getUser().getTeam().getFormation_objects().get(j).getPosition() != null){
                            ((ImageView) view).setBackgroundResource(Defaultdata.playerColors.get(user.getUser().getTeam().getFormation_objects().get(j).getPosition()));
                        }else{
                            Toast.makeText(getActivity(), getResources().getString(R.string.change_strategy_web), Toast.LENGTH_LONG).show();
                            ((ImageView) view).setBackgroundResource(R.color.futbolinAzul);
                        }



                    } else if (view instanceof TextView) {
                        ((TextView) view).setText(user.getUser().getTeam().getFormation_objects().get(j).getNumber() + "");
                        ((TextView) view).setTextColor(Color.parseColor("#000000"));

                    }
                    if(!detailsAdded.get(j)){
                        addDetailsView((RelativeLayout)zones.get(j).getChildAt(0), user.getUser().getTeam().getFormation_objects().get(j));
                        detailsAdded.set(j,true);
                    }

                }
            }

        }
      /*  ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) fieldLayout.getLayoutParams();
        lp.height = ((Double)(lp.width * 0.633)).intValue();
        ConstraintSet cs = new ConstraintSet();
        cs.clone(fieldLayout);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) getActivity().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        Log.e("height",((Double)(displayMetrics.widthPixels * 0.633)).intValue() + "");
        Log.e("width", displayMetrics.widthPixels + "");
        cs.constrainHeight(R.id.fragment_locker_room_field_inside, ((Double)(displayMetrics.widthPixels * 0.633)).intValue());
        cs.applyTo(fieldLayout); */



        for(int i=0; i< 11; i++){
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.TOP, fieldLayout.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.LEFT, fieldLayout.getId(), ConstraintSet.LEFT, 0);

            constraintSet.setVerticalBias(zones.get(i).getId(), 1 - (float)strategy.getPositions().get(i).getTop()/100);
            constraintSet.setHorizontalBias(zones.get(i).getId(),  (float)strategy.getPositions().get(i).getLeft()/100);
            constraintSet.applyTo(fieldLayout);
            TransitionManager.beginDelayedTransition(fieldLayout);
        }

        boolean odd1 = true;
       for(LastMatch lm : team.getLast_matches()) {

           TableRow tableRow = new TableRow(getContext());
           tableRow.setPadding(15,15,15,15);

           if(odd1){
               tableRow.setBackgroundColor(getActivity().getResources().getColor(R.color.azul_suave));

           }

           TextView matchDate = new TextView(getContext());
           matchDate.setText(lm.getDate());
           matchDate.setTextSize(12);
           matchDate.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

           TextView localTeam = new TextView(getContext());
           localTeam.setText(lm.getLocal() + " " + lm.getLocal_goals());
           localTeam.setTextSize(12);
           localTeam.setPadding(0,0,10,0);
           localTeam.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

           TextView visitTeam = new TextView(getContext());
           visitTeam .setText(lm.getVisit() + " " + lm.getVisit_goals());
           visitTeam.setPadding(10,0,0,0);
           visitTeam .setTextSize(12);
           visitTeam .setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

           ImageView seeImage =  new ImageView(getActivity());
           seeImage.requestLayout();

           VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_search, seeImage);
           VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
           path.setFillColor(getActivity().getResources().getColor(R.color.futbolinAzul));

           seeImage.setTag(lm.getLog_file());
           seeImage.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Log.e("Look the match!" + view.getTag(), "onclick");
                   getMatch(view.getTag() + "");
               }
           });
           tableRow.addView(matchDate);
           tableRow.addView(localTeam);
           tableRow.addView(visitTeam);
           tableRow.addView(seeImage);

           lastGeneralMatches.addView(tableRow);
           odd1 = !odd1;
        }
        for(int i=0; i <lastGeneralMatches.getChildCount(); i++){
            for(int j=0;j < ((ViewGroup) lastGeneralMatches.getChildAt(i)).getChildCount() ; j++){
                View view = ((ViewGroup) lastGeneralMatches.getChildAt(i)).getChildAt(j);
                if (view instanceof ImageView) {
                    Log.e("imageView", "LayoutParams null? " + (view.getLayoutParams() == null));
                    view.getLayoutParams().width = 30;
                    view.getLayoutParams().height = 30;
                }
            }
        }

    }
    private void addDetailsView(ViewGroup zone, Player player){


        int detailsAdded = 0;

         if(player.getLast_upgrade() == null){

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
            Log.e("estamina", detailsAdded + "");
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
            Log.e("getCards_count", detailsAdded + "");
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
            Log.e("isSuspended", detailsAdded + "");
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
            Log.e("getInjury_id", detailsAdded + "");
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
            Log.e("isRetiring", detailsAdded + "");
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
            Log.e("isHealed", detailsAdded + "");
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

            ImageView transferable = new ImageView(getActivity());
            RelativeLayout.LayoutParams paramsImage = Defaultdata.detailsLayoutparams.get(detailsAdded);
            paramsImage.height = 15 * (int)density/2;
            paramsImage.width = 15 * (int)density/2;
            transferable.setLayoutParams(paramsImage);
            detailsAdded++;

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_share_square_solid, transferable);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#3c763d"));
            zone.addView(transferable);
        }
    }
    public void getMatch(String file_log){


        svc = Svc.initAuth(getContext());
        Call<getMatch> call = svc.getMatch(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID), file_log);
        call.enqueue(new Callback<getMatch>() {
            @Override
            public void onResponse(Call<getMatch> call, Response<getMatch> response) {
                if(postingMatchDialog != null && postingMatchDialog.isShowing()){
                    postingMatchDialog.dismiss();
                }

                final getMatch teams = response.body();
                final Response<getMatch> responsefinal = response;
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMatch = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){

                                loadMatchDialog(responsefinal.body());


                            }
                        }
                    });
                } else {
                    if(postingMatchDialog != null && postingMatchDialog.isShowing()){
                        postingMatchDialog.dismiss();
                    }
                    try {
                        String error = response.errorBody().string();
                        Log.e("!!error getMatch", error);
                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            startActivity(mainActivity);
                            getActivity().finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getMatch> call, Throwable t) {
                t.printStackTrace();
                if(postingMatchDialog != null && postingMatchDialog.isShowing()){
                    postingMatchDialog.dismiss();
                }
            }
        });

    }


    private void loadMatchDialog(getMatch match){
        Log.e("postMachResponse", match.toString());
        matchDialog = new MatchDialog(getActivity(), match, fragmentLockerRoom);

        matchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        matchDialog.show();
    }
    public void updateNotificationNextMatch(){
        Log.e("updateTrainningNOt", "updateNotificationNextMatch");
        Notification notification;
        TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(timeZone);
        long timeNow = calendar.getTimeInMillis();

        db = new DatabaseManager(getActivity());
        if(db.ExistsNotification()){
            notification = db.findNotification();
        }else{
            notification = new Notification();
            notification.setMatchActive(true);
            notification.setTrainActive(true);
            notification.setLiveSoundsActive(true);
            notification.setTrainNotification(false);
            notification.setMatchNotification(false);
            notification.setNextMatch(timeNow);
            notification.setMatchNotificationAccount(0);
            notification.setTrainNotificationAccount(0);
            db.saveNotification(notification);
        }




        long dateToMatch = (tournament.getNext_match().getDatetime()* 1000) - timeNow;

        if(tournament.getNext_match().getDatetime()* 1000 != db.findNotification().getNextMatch() && tournament.getNext_match().getDatetime() != 0){
            Log.e("alarm", "new match detected!!!!!!!!!!!!!!!");
            notification.setNextMatch(tournament.getNext_match().getDatetime() * 1000);
            notification.setNextMatchTeam(tournament.getNext_match().getLocal().getName() + " - " + tournament.getNext_match().getVisit().getName());
            notification.setMatchNotification(false);
        }


        db.saveNotification(notification);

        if(dateToMatch > 0 ) {
            AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent serviceIntent = new Intent(getActivity(), NextMatchNofiticationService.class);

            PendingIntent servicePendingIntent =
                    PendingIntent.getService(getActivity(),
                            NextMatchNofiticationService.NEXT_MATCH_SERVICE, // integer constant used to identify the service
                            serviceIntent,
                            PendingIntent.FLAG_CANCEL_CURRENT);

            Log.e("dateToTrain", dateToMatch + "");

//			am.setTimeZone("America/Buenos_Aires");

            am.setRepeating(
                    AlarmManager.RTC,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
                    new GregorianCalendar().getTimeInMillis(),
                    dateToMatch,
                    servicePendingIntent
            );
            notification.setMatchNotificationAccount(0);
            notification.setMatchNotification(false);
            db.saveNotification(notification);
            Log.e("NEW ALARM", "!!!!!!!!!!! ---------- MATCH ALARM SET -> " + new Date((new GregorianCalendar().getTimeInMillis()) + dateToMatch));

        }else{
            Log.e("dateToTrain negative", dateToMatch + "");
        }
    }
    @Override
    public void onDestroy() {
        Log.e("LockerRoom", "onDestroy !!!!!!!!!!!!");
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}