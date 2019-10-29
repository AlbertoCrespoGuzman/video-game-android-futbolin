
package com.androidsrc.futbolin.activities.fragments;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.Typeface;
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
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.androidsrc.futbolin.R;
        import com.androidsrc.futbolin.activities.MainActivity;
        import com.androidsrc.futbolin.communications.Svc;
        import com.androidsrc.futbolin.communications.SvcApi;
        import com.androidsrc.futbolin.communications.http.auth.get.FriendlyTeam;
        import com.androidsrc.futbolin.communications.http.auth.get.LastMatch;
        import com.androidsrc.futbolin.communications.http.auth.get.TeamWithFormationId;
        import com.androidsrc.futbolin.communications.http.auth.get.getMatch;
        import com.androidsrc.futbolin.communications.http.auth.get.getTeamWithFormationId;
        import com.androidsrc.futbolin.communications.http.auth.get.getUser;
        import com.androidsrc.futbolin.communications.http.auth.post.PostMatch;
        import com.androidsrc.futbolin.communications.http.auth.post.PostMatchResponse;
        import com.androidsrc.futbolin.database.manager.DatabaseManager;
        import com.androidsrc.futbolin.database.models.Strategy;
        import com.androidsrc.futbolin.utils.Defaultdata;
        import com.androidsrc.futbolin.utils.adapters.ShieldsAdapter;
        import com.androidsrc.futbolin.utils.adapters.TrophiesAdapter;
        import com.androidsrc.futbolin.utils.dialogs.MatchDialog;
        import com.androidsrc.futbolin.utils.itemsDecoration.SpacesItemDecoration;
        import com.devs.vectorchildfinder.VectorChildFinder;
        import com.devs.vectorchildfinder.VectorDrawableCompat;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import androidx.appcompat.app.AppCompatDialog;
        import androidx.appcompat.widget.AppCompatImageButton;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.constraintlayout.widget.ConstraintSet;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.GridLayoutManager;
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
public class FragmentShowTeam extends Fragment implements MatchDialog.DialogClickListener, TrophiesAdapter.ItemClickListener{
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
    long team_id;
    AppCompatDialog dialog;
    View v;
    TeamWithFormationId team;
    ImageView shield;
    TextView teamNameLabel,versusLabel;
    RecyclerView trophiesTable;
    TableLayout  statsTable, statsLastMatchesTable, versusStatsTable, versusLastMatchesTable,infoTable;
    ConstraintLayout fieldLayout, totalLayout;
    RelativeLayout player_1, zone_1,player_2, zone_2,player_3, zone_3,player_4, zone_4,player_5, zone_5,player_6, zone_6,player_7, zone_7,player_8, zone_8,player_9, zone_9,
            player_10, zone_10,player_11, zone_11;
    List<RelativeLayout> zones = new ArrayList<>();
    List<RelativeLayout> players= new ArrayList<>();
    List<Boolean> detailsAdded = Arrays.asList(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false);
    List<Strategy> strategies = new ArrayList<>();
    DatabaseManager db;
    MatchDialog matchDialog;
    TrophiesAdapter adapter;
    TextView titleVersusLastMatches, nextFriendlyText;
    AppCompatImageButton playMatchButton;
    AppCompatDialog postingMatchDialog;

    public FragmentShowTeam() {
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
    public static FragmentShowTeam newInstance(getUser user, String param2) {
        FragmentShowTeam fragment = new FragmentShowTeam();
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
            team_id = Long.parseLong(mParam2);

            if(team == null){
                getgetTeam();
                //
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_team_show, container, false);
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

    @Override
    public void onButtonClick() {
        if(matchDialog != null && matchDialog.isShowing()){
            matchDialog.dismiss();
        }
    }

    @Override
    public void onItemTrophyClick(View view, int position) {
       Log.e("Trophy click", "category_id " +team.getTrophies().get(position).getTournament_id());
        ((MainActivity) getActivity()).changeToTournamentFragmentById(team.getTrophies().get(position).getTournament_id());
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
    private void getTeam(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_team_data) , getActivity());
        dialog.show();

        SvcApi svc;
        svc = Svc.initAuth(getContext());
        Log.e("ID TEAM","id team" + team_id);
        Call<TeamWithFormationId> call = svc.getTeam(team_id, Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<TeamWithFormationId>() {
            @Override
            public void onResponse(Call<TeamWithFormationId> call, Response<TeamWithFormationId> response) {

                final Response<TeamWithFormationId> responsefinal = response;
                final TeamWithFormationId getTeam = response.body();
                Log.e("getTeam USER ->", responsefinal.body().toString());

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
                                Log.e("team is null", "team is null? " + (team == null));
                                Log.e("SHOW TEAM", getTeam.toString());
                                team.setStrategy(getTeam.getStrategy());
                                team.setMatches(getTeam.getMatches());
                                team.setLast_matches(getTeam.getLast_matches());
                                team.setLast_matches_versus(getTeam.getLast_matches_versus());
                                team.setMatches_versus(getTeam.getMatches_versus());
                                team.setTrophies(getTeam.getTrophies());
                                team.setPlayable(getTeam.isPlayable());
                                team.setNext_friendly(getTeam.getNext_friendly());
                                loadFragment();
                            }
                        }
                    });
                } else {
                    try {
                        //    Log.e("error code :",response.toString() + "");
                        //    Log.e("error code :",response.code() + "");
                        //    Log.e("error:",response.message());
                        //   Log.v("Error ", response.errorBody().string());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(),
                                getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeamWithFormationId> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    private void getgetTeam(){
        if(dialog != null && dialog.isShowing()){
       //     dialog.dismiss();
            dialog = null;
        }
  //      dialog = ProgressDialog.show(getActivity(), "","Descargando datos del vestuario...", true);
        SvcApi svc;
        svc = Svc.initAuth(getContext());
        Call<getTeamWithFormationId> call = svc.getgetTeam(team_id, Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getTeamWithFormationId>() {
            @Override
            public void onResponse(Call<getTeamWithFormationId> call, Response<getTeamWithFormationId> response) {

                final Response<getTeamWithFormationId> responsefinal = response;
                final getTeamWithFormationId getgetTeam = response.body();
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
                                Log.e("getgetTeam", getgetTeam.getTeam().toString());
                                team  = getgetTeam.getTeam();
                                getTeam();
                            }
                        }
                    });
                } else {
                    try {
                        //    Log.e("error code :",response.toString() + "");
                        //    Log.e("error code :",response.code() + "");
                        //    Log.e("error:",response.message());
                        //   Log.v("Error ", response.errorBody().string());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(),
                                getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getTeamWithFormationId> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    void loadFragment(){

        shield = v.findViewById(R.id.fragment_team_show_shield);
        teamNameLabel = v.findViewById(R.id.fragment_team_show_team_label);
        trophiesTable  = v.findViewById(R.id.fragment_team_show_trophies_table);
        statsTable = v.findViewById(R.id.fragment_team_show_stats_table);
        statsLastMatchesTable = v.findViewById(R.id.fragment_team_show_last_matches_table);
        versusStatsTable = v.findViewById(R.id.fragment_team_show_versus_stats_table);
        titleVersusLastMatches = v.findViewById(R.id.fragment_team_show_versus_last_matches_label);
        versusLastMatchesTable = v.findViewById(R.id.fragment_team_show_versus_last_matches_table);
        fieldLayout = v.findViewById(R.id.fragment_team_show_formation_layout);
        versusLabel = v.findViewById(R.id.fragment_team_show_versus_stats_label);
        playMatchButton = v.findViewById(R.id.fragment_team_show_playable_play_button);
        nextFriendlyText = v.findViewById(R.id.fragment_team_show_playable_next_friendly_text);

        if(team.isPlayable()){
            playMatchButton.setVisibility(View.VISIBLE);
            playMatchButton.setEnabled(true);

        }else{
            playMatchButton.setVisibility(View.VISIBLE);
            playMatchButton.setVisibility(View.GONE);
        }
        if(team.getNext_friendly() != null && team.getNext_friendly().length() > 0){
            nextFriendlyText.setText(getActivity().getResources().getString(R.string.next_match_playable) + " " +
                    team.getNext_friendly());
            nextFriendlyText.setTextColor(getResources().getColor(R.color.gray_progress_bar));
        }else{
            nextFriendlyText.setText(getActivity().getResources().getString(R.string.next_match_playable_now));
            nextFriendlyText.setTextColor(getResources().getColor(R.color.futbolinAzul));
            nextFriendlyText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postMatch(team.getId());
                }
            });
            playMatchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    postMatch(team.getId());
                }
            });
        }


        adapter = new TrophiesAdapter(this, getActivity(), team.getTrophies());
        trophiesTable.setAdapter(adapter);
//        shieldsRV.addItemDecoration(new ItemOffsetDecoration(10));shieldsRV
        final GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),3);
        trophiesTable.setLayoutManager(linearLayoutManager);
        trophiesTable.addItemDecoration(new SpacesItemDecoration(3));


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

        infoTable = v.findViewById(R.id.fragment_team_show_info_table);

        TableRow roww = new TableRow(getContext());
        TextView nameLabel = new TextView(getContext());
        nameLabel.setTypeface(Typeface.DEFAULT_BOLD);
        nameLabel.setText("Nombre corto");
        nameLabel.setTextSize(11);
        nameLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        nameLabel.setPadding(5,5,5,5);
        roww.addView(nameLabel);


        TextView nameText = new TextView(getContext());
        nameText.setText(team.getShort_name());
        nameText.setPadding(5,5,5,5);
        nameText.setTextSize(11);
        roww.addView(nameText);

        infoTable.addView(roww);

        TableRow row1 = new TableRow(getContext());
        TextView trainerLabel = new TextView(getContext());
        trainerLabel.setTypeface(Typeface.DEFAULT_BOLD);
        trainerLabel.setPadding(5,5,5,5);
        trainerLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        trainerLabel.setText("Entrenador");
        trainerLabel.setTextSize(11);
        row1.addView(trainerLabel);

        TextView trainerText = new TextView(getContext());
        trainerText.setText(team.getUser_name());
        trainerText.setTextSize(11);
        row1.addView(trainerText);

        infoTable.addView(row1);

        TableRow row2 = new TableRow(getContext());
        TextView stadiumLabel = new TextView(getContext());
        stadiumLabel.setTypeface(Typeface.DEFAULT_BOLD);
        stadiumLabel.setPadding(5,5,5,5);
        stadiumLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        stadiumLabel.setText("Estadio");
        stadiumLabel.setTextSize(11);
        row2.addView(stadiumLabel);

        TextView stadiumText = new TextView(getContext());
        stadiumText.setText(team.getStadium_name());
        stadiumText.setPadding(5,5,5,5);
        stadiumText.setTextSize(11);
        row2.addView(stadiumText);

        infoTable.addView(row2);

        zones.addAll(Arrays.asList(zone_1, zone_2, zone_3, zone_4, zone_5, zone_6, zone_7, zone_8, zone_9,zone_10,zone_11));
        players.addAll(Arrays.asList(player_1, player_2, player_3, player_4, player_5, player_6, player_7, player_8, player_9,player_10,player_11));



        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(fieldLayout);

        for(int j = 0; j < zones.size(); j++){

            for(int i = 0; i < ((RelativeLayout) zones.get(j).getChildAt(0)).getChildCount(); i++) {
                if (zones.get(j).getChildAt(0) instanceof RelativeLayout) {
                    View view = ((RelativeLayout) zones.get(j).getChildAt(0)).getChildAt(i);
                    if (view instanceof ImageView) {
                        ((ImageView) view).setBackgroundResource(Defaultdata.playerColors.get(team.getStrategy().get(j).getPosition()));


                    } else if (view instanceof TextView) {
                        ((TextView) view).setText(team.getStrategy().get(j).getNumber() + "");
                        ((TextView) view).setTextColor(Color.parseColor("#000000"));

                    }


                }
            }

        }




        for(int i=0; i< 11; i++){
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.TOP, fieldLayout.getId(), ConstraintSet.TOP, 0);
            constraintSet.connect(zones.get(i).getId(), ConstraintSet.LEFT, fieldLayout.getId(), ConstraintSet.LEFT, 0);

             constraintSet.setVerticalBias(zones.get(i).getId(), (float)team.getStrategy().get(i).getTop()/100);
            constraintSet.setHorizontalBias(zones.get(i).getId(),  (float)team.getStrategy().get(i).getLeft()/100);
            constraintSet.applyTo(fieldLayout);
            TransitionManager.beginDelayedTransition(fieldLayout);
        }


        VectorChildFinder vectorLocal = new VectorChildFinder(getActivity(), Defaultdata.shields.get(team.getShield()), shield);
        VectorDrawableCompat.VFullPath path21Local = vectorLocal.findPathByName("secundary_color1");
        path21Local.setFillColor(Color.parseColor(team.getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Local = vectorLocal.findPathByName("secundary_color2");
        path22Local.setFillColor(Color.parseColor(team.getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Local = vectorLocal.findPathByName("primary_color");
        path1Local.setFillColor(Color.parseColor(team.getPrimary_color()));
        shield.invalidate();

        teamNameLabel.setText(team.getName());
        //TROPHIES


        TableRow rowLocal = new TableRow(getContext());
        rowLocal.setBackgroundResource(R.drawable.borders_table_grey);
        rowLocal.setPadding(15,15,15,15);


        TextView localLabel = new TextView(getContext());
        localLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        localLabel.setText("Local");
        localLabel.setTypeface(Typeface.DEFAULT_BOLD);
        localLabel.setTextSize(11);
        rowLocal.addView(localLabel);

        TextView localJug = new TextView(getContext());
        localJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localJug.setText(String.valueOf(team.getMatches().getLocal().getTotal()));
        localJug.setTextSize(11);
        rowLocal.addView(localJug);

        TextView localG = new TextView(getContext());
        localG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localG.setText(String.valueOf(team.getMatches().getLocal().getWon()));
        localG.setTextSize(11);
        rowLocal.addView(localG);

        TextView localE = new TextView(getContext());
        localE.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localE.setText(String.valueOf(team.getMatches().getLocal().getTied()));
        localE.setTextSize(11);
        rowLocal.addView(localE);

        TextView localP = new TextView(getContext());
        localP.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localP.setText(String.valueOf(team.getMatches().getLocal().getLost()));
        localP.setTextSize(11);
        rowLocal.addView(localP);

        TextView localDF = new TextView(getContext());
        localDF.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localDF.setText(String.valueOf(team.getMatches().getLocal().getGoals_favor()));
        localDF.setTextSize(11);
        rowLocal.addView(localDF);

        TextView localDC = new TextView(getContext());
        localDC.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localDC.setText(String.valueOf(team.getMatches().getLocal().getGoals_against()));
        localDC.setTextSize(11);
        rowLocal.addView(localDC);

        TextView localDG = new TextView(getContext());
        localDG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localDG.setText(String.valueOf(team.getMatches().getLocal().getGoals_difference()));
        localDG.setTextSize(11);
        rowLocal.addView(localDG);
        statsTable.addView(rowLocal);


        TableRow rowvisit = new TableRow(getContext());
        rowvisit.setBackgroundResource(R.drawable.borders_table_grey);
        rowvisit.setPadding(15,15,15,15);


        TextView visitLabel = new TextView(getContext());
        visitLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        visitLabel.setText("Visitante");
        visitLabel.setTypeface(Typeface.DEFAULT_BOLD);
        visitLabel.setTextSize(11);
        rowvisit.addView(visitLabel);

        TextView visitJug = new TextView(getContext());
        visitJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitJug.setText(String.valueOf(team.getMatches().getVisit().getTotal()));
        visitJug.setTextSize(11);
        rowvisit.addView(visitJug);

        TextView visitG = new TextView(getContext());
        visitG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitG.setText(String.valueOf(team.getMatches().getVisit().getWon()));
        visitG.setTextSize(11);
        rowvisit.addView(visitG);

        TextView visitE = new TextView(getContext());
        visitE.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitE.setText(String.valueOf(team.getMatches().getVisit().getTied()));
        visitE.setTextSize(11);
        rowvisit.addView(visitE);

        TextView visitP = new TextView(getContext());
        visitP.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitP.setText(String.valueOf(team.getMatches().getVisit().getLost()));
        visitP.setTextSize(11);
        rowvisit.addView(visitP);

        TextView visitDF = new TextView(getContext());
        visitDF.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitDF.setText(String.valueOf(team.getMatches().getVisit().getGoals_favor()));
        visitDF.setTextSize(11);
        rowvisit.addView(visitDF);

        TextView visitDC = new TextView(getContext());
        visitDC.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitDC.setText(String.valueOf(team.getMatches().getVisit().getGoals_against()));
        visitDC.setTextSize(11);
        rowvisit.addView(visitDC);

        TextView visitDG = new TextView(getContext());
        visitDG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitDG.setText(String.valueOf(team.getMatches().getVisit().getGoals_difference()));
        visitDG.setTextSize(11);
        rowvisit.addView(visitDG);

        statsTable.addView(rowvisit);


        TableRow rowtotal = new TableRow(getContext());
        rowtotal.setBackgroundResource(R.drawable.borders_table_grey);
        rowtotal.setPadding(15,15,15,15);


        TextView totalLabel = new TextView(getContext());
        totalLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        totalLabel.setText("Total");
        totalLabel.setTypeface(Typeface.DEFAULT_BOLD);
        totalLabel.setTextSize(11);
        rowtotal.addView(totalLabel);

        TextView totalJug = new TextView(getContext());
        totalJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalJug.setText(String.valueOf(team.getMatches().getLocal().getTotal() + team.getMatches().getVisit().getTotal()));
        totalJug.setTextSize(11);
        rowtotal.addView(totalJug);

        TextView totalG = new TextView(getContext());
        totalG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalG.setText(String.valueOf(team.getMatches().getLocal().getWon() + team.getMatches().getVisit().getWon()));
        totalG.setTextSize(11);
        rowtotal.addView(totalG);

        TextView totalE = new TextView(getContext());
        totalE.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalE.setText(String.valueOf(team.getMatches().getLocal().getTied() + team.getMatches().getVisit().getTied()));
        totalE.setTextSize(11);
        rowtotal.addView(totalE);

        TextView totalP = new TextView(getContext());
        totalP.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalP.setText(String.valueOf(team.getMatches().getLocal().getLost() + team.getMatches().getVisit().getLost()));
        totalP.setTextSize(11);
        rowtotal.addView(totalP);

        TextView totalDF = new TextView(getContext());
        totalDF.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalDF.setText(String.valueOf(team.getMatches().getLocal().getGoals_favor() + team.getMatches().getVisit().getGoals_favor()));
        totalDF.setTextSize(11);
        rowtotal.addView(totalDF);

        TextView totalDC = new TextView(getContext());
        totalDC.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalDC.setText(String.valueOf(team.getMatches().getLocal().getGoals_against() + team.getMatches().getVisit().getGoals_against()));
        totalDC.setTextSize(11);
        rowtotal.addView(totalDC);

        TextView totalDG = new TextView(getContext());
        totalDG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalDG.setText(String.valueOf(team.getMatches().getLocal().getGoals_difference() + team.getMatches().getVisit().getGoals_difference()));
        totalDG.setTextSize(11);
        rowtotal.addView(totalDG);

        statsTable.addView(rowtotal);



        boolean odd = true;
        for(LastMatch lm : team.getLast_matches()){
            TableRow row = new TableRow(getContext());
            row.setPadding(15,15,15,15);
            if(odd){
                row.setBackgroundColor(getActivity().getResources().getColor(R.color.azul_suave));
            }
            odd =!odd;

            TextView date = new TextView(getContext());
            date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            date.setText(lm.getDate());
            date.setTextSize(11);
            row.addView(date);

            TextView cond = new TextView(getContext());
            cond.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            cond.setTextSize(11);
            cond.setText(lm.getLocal() + " " + lm.getLocal_goals() + " ");
            row.addView(cond);
            TextView score = new TextView(getContext());
            score.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            score.setText(lm.getVisit() + " " + lm.getVisit_goals() + " ");
            score.setTextSize(11);
            if(lm.isWon()){
                score.setTypeface(Typeface.DEFAULT_BOLD);
            }
            row.addView(score);

            ImageView seeImage =  new ImageView(getContext());
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
            row.addView(seeImage);
            statsLastMatchesTable.addView(row);
        }

        TableRow rowlocalVersus = new TableRow(getContext());
        rowlocalVersus.setBackgroundResource(R.drawable.borders_table_grey);
        rowlocalVersus.setPadding(15,15,15,15);



        TextView localVersusLabel = new TextView(getContext());
        localVersusLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        localVersusLabel.setText("Local");
        localVersusLabel.setTypeface(Typeface.DEFAULT_BOLD);
        localVersusLabel.setTextSize(11);
        rowlocalVersus.addView(localVersusLabel);

        TextView localVersusJug = new TextView(getContext());
        localVersusJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localVersusJug.setText(String.valueOf(team.getMatches_versus().getLocal().getTotal()));
        localVersusJug.setTextSize(11);
        rowlocalVersus.addView(localVersusJug);

        TextView localVersusG = new TextView(getContext());
        localVersusG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localVersusG.setText(String.valueOf(team.getMatches_versus().getLocal().getWon()));
        localVersusG.setTextSize(11);
        rowlocalVersus.addView(localVersusG);

        TextView localVersusE = new TextView(getContext());
        localVersusE.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localVersusE.setText(String.valueOf(team.getMatches_versus().getLocal().getTied()));
        localVersusE.setTextSize(11);
        rowlocalVersus.addView(localVersusE);

        TextView localVersusP = new TextView(getContext());
        localVersusP.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localVersusP.setText(String.valueOf(team.getMatches_versus().getLocal().getLost()));
        localVersusP.setTextSize(11);
        rowlocalVersus.addView(localVersusP);

        TextView localVersusDF = new TextView(getContext());
        localVersusDF.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localVersusDF.setText(String.valueOf(team.getMatches_versus().getLocal().getGoals_favor()));
        localVersusDF.setTextSize(11);
        rowlocalVersus.addView(localVersusDF);

        TextView localVersusDC = new TextView(getContext());
        localVersusDC.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localVersusDC.setText(String.valueOf(team.getMatches_versus().getLocal().getGoals_against()));
        localVersusDC.setTextSize(11);
        rowlocalVersus.addView(localVersusDC);

        TextView localVersusDG = new TextView(getContext());
        localVersusDG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        localVersusDG.setText(String.valueOf(team.getMatches_versus().getLocal().getGoals_difference()));
        localVersusDG.setTextSize(11);
        rowlocalVersus.addView(localVersusDG);
        versusStatsTable.addView(rowlocalVersus);

        TableRow rowvisitVersus = new TableRow(getContext());
        rowvisitVersus.setBackgroundResource(R.drawable.borders_table_grey);
        rowvisitVersus.setPadding(15,15,15,15);

        TextView visitVersusLabel = new TextView(getContext());
        visitVersusLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        visitVersusLabel.setText(getResources().getString(R.string.visiting_team));
        visitVersusLabel.setTypeface(Typeface.DEFAULT_BOLD);
        visitVersusLabel.setTextSize(11);
        rowvisitVersus.addView(visitVersusLabel);

        TextView visitVersusJug = new TextView(getContext());
        visitVersusJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitVersusJug.setText(String.valueOf(team.getMatches_versus().getVisit().getTotal()));
        visitVersusJug.setTextSize(11);
        rowvisitVersus.addView(visitVersusJug);

        TextView visitVersusG = new TextView(getContext());
        visitVersusG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitVersusG.setText(String.valueOf(team.getMatches_versus().getVisit().getWon()));
        visitVersusG.setTextSize(11);
        rowvisitVersus.addView(visitVersusG);

        TextView visitVersusE = new TextView(getContext());
        visitVersusE.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitVersusE.setText(String.valueOf(team.getMatches_versus().getVisit().getTied()));
        visitVersusE.setTextSize(11);
        rowvisitVersus.addView(visitVersusE);

        TextView visitVersusP = new TextView(getContext());
        visitVersusP.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitVersusP.setText(String.valueOf(team.getMatches_versus().getVisit().getLost()));
        visitVersusP.setTextSize(11);
        rowvisitVersus.addView(visitVersusP);

        TextView visitVersusDF = new TextView(getContext());
        visitVersusDF.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitVersusDF.setText(String.valueOf(team.getMatches_versus().getVisit().getGoals_favor()));
        visitVersusDF.setTextSize(11);
        rowvisitVersus.addView(visitVersusDF);

        TextView visitVersusDC = new TextView(getContext());
        visitVersusDC.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitVersusDC.setText(String.valueOf(team.getMatches_versus().getVisit().getGoals_against()));
        visitVersusDC.setTextSize(11);
        rowvisitVersus.addView(visitVersusDC);

        TextView visitVersusDG = new TextView(getContext());
        visitVersusDG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        visitVersusDG.setText(String.valueOf(team.getMatches_versus().getVisit().getGoals_difference()));
        visitVersusDG.setTextSize(11);
        rowvisitVersus.addView(visitVersusDG);

        versusStatsTable.addView(rowvisitVersus);


        TableRow rowtotalVersus = new TableRow(getContext());
        rowtotalVersus.setBackgroundResource(R.drawable.borders_table_grey);
        rowtotalVersus.setPadding(15,15,15,15);


        TextView totalVersusLabel = new TextView(getContext());
        totalVersusLabel.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        totalVersusLabel.setText("Total");
        totalVersusLabel.setTypeface(Typeface.DEFAULT_BOLD);
        totalVersusLabel.setTextSize(11);
        rowtotalVersus.addView(totalVersusLabel);

        TextView totalVersusJug = new TextView(getContext());
        totalVersusJug.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalVersusJug.setText(String.valueOf(team.getMatches_versus().getLocal().getTotal() + team.getMatches_versus().getVisit().getTotal()));
        totalVersusJug.setTextSize(11);
        rowtotalVersus.addView(totalVersusJug);

        TextView totalVersusG = new TextView(getContext());
        totalVersusG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalVersusG.setText(String.valueOf(team.getMatches_versus().getLocal().getWon() + team.getMatches_versus().getVisit().getWon()));
        totalVersusG.setTextSize(11);
        rowtotalVersus.addView(totalVersusG);

        TextView totalVersusE = new TextView(getContext());
        totalVersusE.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalVersusE.setText(String.valueOf(team.getMatches_versus().getLocal().getTied() + team.getMatches_versus().getVisit().getTied()));
        totalVersusE.setTextSize(11);
        rowtotalVersus.addView(totalVersusE);

        TextView totalVersusP = new TextView(getContext());
        totalVersusP.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalVersusP.setText(String.valueOf(team.getMatches_versus().getLocal().getLost() + team.getMatches_versus().getVisit().getLost()));
        totalVersusP.setTextSize(11);
        rowtotalVersus.addView(totalVersusP);

        TextView totalVersusDF = new TextView(getContext());
        totalVersusDF.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalVersusDF.setText(String.valueOf(team.getMatches_versus().getLocal().getGoals_favor() + team.getMatches_versus().getVisit().getGoals_favor()));
        totalVersusDF.setTextSize(11);
        rowtotalVersus.addView(totalVersusDF);

        TextView totalVersusDC = new TextView(getContext());
        totalVersusDC.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalVersusDC.setText(String.valueOf(team.getMatches_versus().getLocal().getGoals_against() + team.getMatches_versus().getVisit().getGoals_against()));
        totalVersusDC.setTextSize(11);
        rowtotalVersus.addView(totalVersusDC);

        TextView totalVersusDG = new TextView(getContext());
        totalVersusDG.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        totalVersusDG.setText(String.valueOf(team.getMatches_versus().getLocal().getGoals_difference() + team.getMatches_versus().getVisit().getGoals_difference()));
        totalVersusDG.setTextSize(11);
        rowtotalVersus.addView(totalVersusDG);

        versusStatsTable.addView(rowtotalVersus);

        odd = true;
        if(team.getLast_matches_versus().isEmpty()){
            versusLastMatchesTable.setVisibility(View.INVISIBLE);
        }
        for(LastMatch lm : team.getLast_matches_versus()){
            TableRow row = new TableRow(getContext());
            row.setPadding(15,15,15,15);
            if(odd){
                row.setBackgroundColor(getActivity().getResources().getColor(R.color.azul_suave));
            }
            odd =!odd;

            TextView date = new TextView(getContext());
            date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            date.setText(lm.getDate());
            date.setTextSize(11);
            row.addView(date);

            TextView cond = new TextView(getContext());
            cond.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            cond.setTextSize(11);
            cond.setText(lm.getLocal() + " " + lm.getLocal_goals() + " ");

            row.addView(cond);

            TextView score = new TextView(getContext());
            score.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            score.setText(lm.getVisit() + " " + lm.getVisit_goals() + " ");
            score.setTextSize(11);
            if(!lm.isWon()){
                score.setTypeface(Typeface.DEFAULT_BOLD);
            }
            row.addView(score);

            ImageView seeImage =  new ImageView(getContext());
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
            row.addView(seeImage);
            versusLastMatchesTable.addView(row);
        }

        versusLabel.setText("Contra " + team.getName());

        for(int i=0; i < versusLastMatchesTable.getChildCount(); i++){
            TableRow view = (TableRow) versusLastMatchesTable.getChildAt(i);
            for(int j=0; j< ((TableRow)view).getChildCount(); j++){
                View view1 = ((TableRow)view).getChildAt(j);
                if(view1 instanceof ImageView){
                    view1.getLayoutParams().height = 30;
                    view1.getLayoutParams().width = 30;
                }
            }
        }
        for(int i=0; i < statsLastMatchesTable.getChildCount(); i++){
            TableRow view = (TableRow) statsLastMatchesTable.getChildAt(i);
            for(int j=0; j< ((TableRow)view).getChildCount(); j++){
                View view1 = ((TableRow)view).getChildAt(j);
                if(view1 instanceof ImageView){
                    view1.getLayoutParams().height = 30;
                    view1.getLayoutParams().width = 30;
                }
            }
        }

    }

    public void getMatch(String file_log){

        SvcApi svc;
        svc = Svc.initAuth(getContext());
        Call<getMatch> call = svc.getMatch(Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID), file_log);
        call.enqueue(new Callback<getMatch>() {
            @Override
            public void onResponse(Call<getMatch> call, Response<getMatch> response) {
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
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
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
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
                }
            }

            @Override
            public void onFailure(Call<getMatch> call, Throwable t) {
                t.printStackTrace();
                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });

    }
    private void loadMatchDialog(getMatch match){
        Log.e("postMachResponse", match.toString());
        matchDialog = new MatchDialog(getActivity(), match, this);
        matchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        matchDialog.show();
    }
    public void postMatch(long team_id){

        postingMatchDialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.match_time),
                getResources().getString(R.string.match_time_2) , getActivity());

        postingMatchDialog.show();

        PostMatch postMatch = new PostMatch();
        postMatch.setDevice_id(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        postMatch.setRival(team_id);

        SvcApi svc;
        svc = Svc.initAuth(getContext());
        Call<PostMatchResponse> call = svc.postMatch(postMatch);
        call.enqueue(new Callback<PostMatchResponse>() {
            @Override
            public void onResponse(Call<PostMatchResponse> call, Response<PostMatchResponse> response) {


                final Response<PostMatchResponse> responsefinal = response;
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMe = " + responsefinal);
                            if(responsefinal.code() == 400){
                                postingMatchDialog.dismiss();
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                //     updateImageFriendly(rival.getId());
                                getMatchPlayed(responsefinal.body());

                            }
                        }
                    });
                } else {
                    postingMatchDialog.dismiss();
                    try {
                        String error = response.errorBody().string();
                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            startActivity(mainActivity);
                            getActivity().finish();
                        }
                        Log.e("error!!","error " + error);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostMatchResponse> call, Throwable t) {
                t.printStackTrace();
                postingMatchDialog.dismiss();
            }
        });

    }
    public void getMatchPlayed(PostMatchResponse match){
        Log.e("PostMatchResponse", match.toString());

        SvcApi svc;
        svc = Svc.initAuth(getContext());
        Call<getMatch> call = svc.getMatch(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID), match.getLog_file());
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

                                playMatchButton.setVisibility(View.VISIBLE);
                                playMatchButton.setEnabled(false);
                                nextFriendlyText.setText(getActivity().getResources().getString(R.string.next_match_playable) + " 23h");
                                nextFriendlyText.setTextColor(getResources().getColor(R.color.gray_progress_bar));
                                nextFriendlyText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                    if(postingMatchDialog != null && postingMatchDialog.isShowing()){
                        postingMatchDialog.dismiss();
                    }
                    try {
                        String error = response.errorBody().string();
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


}