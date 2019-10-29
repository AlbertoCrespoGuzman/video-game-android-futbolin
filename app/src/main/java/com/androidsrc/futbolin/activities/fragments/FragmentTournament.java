package com.androidsrc.futbolin.activities.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.CategoryPositions;
import com.androidsrc.futbolin.communications.http.auth.get.Round;
import com.androidsrc.futbolin.communications.http.auth.get.RoundMatch;
import com.androidsrc.futbolin.communications.http.auth.get.RoundScorers;
import com.androidsrc.futbolin.communications.http.auth.get.getMatch;
import com.androidsrc.futbolin.communications.http.auth.get.getTournament;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.dialogs.CategoriesDialog;
import com.androidsrc.futbolin.utils.dialogs.MatchDialog;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
public class FragmentTournament extends Fragment implements  MatchDialog.DialogClickListener, CategoriesDialog.DialogClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    getUser user;
    getTournament tournament;
    private OnFragmentInteractionListener mListener;
    View v;
    TextView tournamentNameLabel, roundNameLabel, roundDateLabel, scorersLabel;
    Button categoriesButton, previousRoundButton, firstRoundButton,
            secondRoundButton, thirdRoundButton, fourthRoundButton,
            fifthRoundButton, AfterRoundButton, classPointsButton, classMatchsButton, classGoalsButton;
    TableLayout classTable, scorersTable, roundTable;
    AppCompatDialog dialog;
    MatchDialog matchDialog;
    FragmentTournament fragmentTournament;
    Round currentRound;
    CategoriesDialog categoriesDialog;

    public FragmentTournament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user   Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTournament newInstance(getUser user, String param2) {
        FragmentTournament fragment = new FragmentTournament();
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

        }
        if(mParam2 != null && !mParam2.equals("")){
            getTournamentByCategoryId(Long.parseLong(mParam2));
        }else if (tournament == null) {
            getTournament();
        }
        fragmentTournament = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tournament, container, false);
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
        if (matchDialog != null && matchDialog.isShowing()) {
            matchDialog.dismiss();
        }
    }

    @Override
    public void onButtonCategoriesClick(long category_id) {
        if(category_id != -1) {
            getTournamentByCategoryId(category_id);
            if(categoriesDialog != null && categoriesDialog.isShowing()) {
                categoriesDialog.dismiss();
            }
        }else{
            if(categoriesDialog != null && categoriesDialog.isShowing()) {
                categoriesDialog.dismiss();
            }
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

    private void getTournament() {
        if (dialog != null && dialog.isShowing()) {
     //       dialog.dismiss();
            dialog = null;
        }
        SvcApi svc;
        dialog = Defaultdata.loadDialog(dialog,getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_tournament_data) , getActivity());
        dialog.show();
        svc = Svc.initAuth(getContext());
        Call<getTournament> call = svc.getTournament(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getTournament>() {
            @Override
            public void onResponse(Call<getTournament> call, Response<getTournament> response) {

                final Response<getTournament> responsefinal = response;
                Log.e("Strategies", responsefinal.body().toString());
                final getTournament getTournament = response.body();
                Log.e("getTournament", getTournament.toString());
                if (dialog != null && dialog.isShowing()) {
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
                                    loadFragment();
                                }
                            }
                        });
                    }
                } else {
                    try {
                        //    Log.e("error code :",response.toString() + "");
                        //    Log.e("error code :",response.code() + "");
                        //    Log.e("error:",response.message());
                        //   Log.v("Error ", response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (responsefinal.code() == 500 || responsefinal.code() == 503) {
                        Toast.makeText(getContext(),
                                getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getTournament> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    private void getTournamentByCategoryId(long category_id) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        SvcApi svc;
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_tournament_data) , getActivity());
        dialog.show();
        svc = Svc.initAuth(getContext());
        Call<getTournament> call = svc.getTournamentByCategoryId(category_id, Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getTournament>() {
            @Override
            public void onResponse(Call<getTournament> call, Response<getTournament> response) {

                final Response<getTournament> responsefinal = response;
                Log.e("Strategies", responsefinal.body().toString());
                final getTournament getTournament = response.body();
                Log.e("getTournament", getTournament.toString());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (responsefinal.code() == 400) {

                            } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {
                                tournament = getTournament;
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (responsefinal.code() == 500 || responsefinal.code() == 503) {
                        Toast.makeText(getContext(),
                                getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getTournament> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    void loadFragment() {
        tournamentNameLabel = v.findViewById(R.id.fragment_tournament_name_label);
        roundNameLabel = v.findViewById(R.id.fragment_tournament_round_label);
        roundDateLabel = v.findViewById(R.id.fragment_tournament_round_date);
        roundTable = v.findViewById(R.id.fragment_tournament_round_table);
        scorersLabel = v.findViewById(R.id.fragment_tournament_scorers_label);
        categoriesButton = v.findViewById(R.id.fragment_tournament_categories_button);
        previousRoundButton = v.findViewById(R.id.fragment_tournament_previous_button);
        firstRoundButton = v.findViewById(R.id.fragment_tournament_first_button);
        secondRoundButton = v.findViewById(R.id.fragment_tournament_second_button);
        thirdRoundButton = v.findViewById(R.id.fragment_tournament_third_button);
        fourthRoundButton = v.findViewById(R.id.fragment_tournament_fourth_button);
        fifthRoundButton = v.findViewById(R.id.fragment_tournament_fifth_button);
        AfterRoundButton = v.findViewById(R.id.fragment_tournament_next_button);
        classPointsButton = v.findViewById(R.id.fragment_tournament_classif_points_button);
        classMatchsButton = v.findViewById(R.id.fragment_tournament__classif_matchs_button);
        classGoalsButton = v.findViewById(R.id.fragment_tournament_classif_goals_button);
        classTable = v.findViewById(R.id.fragment_tournament_classif_table);
        scorersTable = v.findViewById(R.id.fragment_tournament_scorers_table);

        List<View> viewsClass = new ArrayList<>();

        for(int i=0; i< classTable.getChildCount(); i++){
            View view = classTable.getChildAt(i);
            if(view instanceof TableRow){
                viewsClass.add(view);
            }
        }
        for(View view : viewsClass){
            classTable.removeView(view);
        }

        List<View> viewsScorers = new ArrayList<>();

        for(int i=0; i< scorersTable.getChildCount(); i++){
            View view = scorersTable.getChildAt(i);
            if(view instanceof TableRow){
                viewsScorers.add(view);
            }
        }
        for(View view : viewsScorers){
            scorersTable.removeView(view);
        }



        tournamentNameLabel.setText(tournament.getTournament().getName() +
                getResources().getString(R.string.zone) +" " +
                tournament.getCategory().getZone_name() + " " +
                getResources().getString(R.string.category_avb)+ " " +
                tournament.getCategory().getCategory_name() + ")");
        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoriesDialog = new CategoriesDialog(getActivity(), tournament, fragmentTournament);
                categoriesDialog.show();
            }
        });
        Round currentRound = null;
        if (tournament.getCategory().getRounds().size() > 0) {
            currentRound = tournament.getCategory().getRounds().get(0);
        }
        boolean roundSet = false;
        for (Round round : tournament.getCategory().getRounds()) {
            Log.e("round time", "round" + round.getDatetime() * 1000 + " | now: " + new Date().getTime());
            if (round.getDatetime() * 1000 > new Date().getTime()) {
                Log.e("E caborn!!", "caadsas");
                currentRound = round;
                roundSet = true;
                break;
            }
            roundSet = false;
        }
        if (!roundSet && tournament.getCategory().getRounds().size() > 0) {
            currentRound = tournament.getCategory().getRounds().get(tournament.getCategory().getRounds().size() - 1);
        }
       // currentRound = null; // hola
        if(currentRound != null){
            thirdRoundButton.setTag(currentRound.getNumber());
            updateRoundButtons(thirdRoundButton);
        }
        int position = 1;
        for (CategoryPositions categoryPositions : tournament.getCategory().getPositions()) {
            TableRow row = new TableRow(getContext());
            row.setPadding(20, 20, 20, 20);
            if (categoryPositions.getTeam_id() == user.getUser().getTeam().getId()) {
                row.setBackgroundColor(getActivity().getResources().getColor(R.color.azul_suave));
            } else {
                row.setBackgroundResource(R.drawable.borders_table_grey);
            }
            TextView positionT = new TextView(getContext());
            positionT.setTextSize(11);
            positionT.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            positionT.setText(position + "");
            positionT.setTag("fix");
            row.addView(positionT);

            String color = "#FFFFFF";
            int drawable = 0;

            if (categoryPositions.getLast_position() == position) {
                color = "#a79899";
                drawable = R.drawable.ic_angle_right;
            } else if (categoryPositions.getLast_position() > position) {
                color = "#219800";
                drawable = R.drawable.ic_angle_up;
            } else if (categoryPositions.getLast_position() < position) {
                color = "#9e0913";
                drawable = R.drawable.ic_angle_down;
            }
            ImageView seeImage = new ImageView(getContext());

            VectorChildFinder vector = new VectorChildFinder(getContext(), drawable, seeImage);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor(color));
            seeImage.setTag("fix");
            row.addView(seeImage);


            TextView teamName = new TextView(getContext());
            teamName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            teamName.setTextSize(11);
            teamName.setTextColor(getActivity().getResources().getColor(R.color.futbolinAzul));
            if (categoryPositions.getTeam_name().length() > 20) {
                categoryPositions.setTeam_name(categoryPositions.getTeam_name().substring(0, 20) + "...");
            }
            teamName.setText(categoryPositions.getTeam_name());
            teamName.setTag("fix");
            row.addView(teamName);

            TextView points = new TextView(getContext());
            points.setTextSize(11);
            points.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            points.setText(categoryPositions.getPoints() + "");
            points.setTag("points");
            row.addView(points);

            TextView pj = new TextView(getContext());
            pj.setTextSize(11);
            pj.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            pj.setText(categoryPositions.getPlayed() + "");
            pj.setTag("matchs");
            row.addView(pj);

            TextView pg = new TextView(getContext());
            pg.setTextSize(11);
            pg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            pg.setText(categoryPositions.getWon() + "");
            pg.setTag("matchs");
            row.addView(pg);

            TextView pe = new TextView(getContext());
            pe.setTextSize(11);
            pe.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            pe.setText(categoryPositions.getTied() + "");
            pe.setTag("matchs");
            row.addView(pe);

            TextView pp = new TextView(getContext());
            pp.setTextSize(11);
            pp.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            pp.setText(categoryPositions.getLost() + "");
            pp.setTag("matchs");
            row.addView(pp);

            TextView gf = new TextView(getContext());
            gf.setTextSize(11);
            gf.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            gf.setText(categoryPositions.getGoals_favor() + "");
            gf.setTag("goals");
            row.addView(gf);

            TextView gc = new TextView(getContext());
            gc.setTextSize(11);
            gc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            gc.setText(categoryPositions.getGoals_against() + "");
            gc.setTag("goals");
            row.addView(gc);

            TextView dg = new TextView(getContext());
            dg.setTextSize(11);
            dg.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dg.setText(categoryPositions.getGoals_difference() + "");
            dg.setTag("goals");
            row.addView(dg);

            position++;

            final CategoryPositions categoryPositionsf = categoryPositions;


            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).changeToShowTeamFragment(categoryPositionsf.getTeam_id(), categoryPositionsf.getTeam_short_name(), "FragmentTournament");
                }
            });

            classTable.addView(row);
        }
        classPointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHideClassTableRows("points");
                activeButtonClass(view);
            }
        });

        classMatchsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHideClassTableRows("matchs");
                activeButtonClass(view);
            }
        });
        classGoalsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowHideClassTableRows("goals");
                activeButtonClass(view);
            }
        });
        for (int i = 0; i < classTable.getChildCount(); i++) {
            TableRow row = (TableRow) classTable.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                View view = row.getChildAt(j);
                if (view instanceof ImageView) {
                    view.getLayoutParams().height = 42;
                    view.getLayoutParams().width = 42;
                }
            }
        }


        ShowHideClassTableRows("points");
        activeButtonClass(classPointsButton);

        for (RoundScorers roundScorers : tournament.getCategory().getScorers()) {
            TableRow row = new TableRow(getContext());
            row.setPadding(20, 20, 20, 20);
            if (roundScorers.getPlayer().getTeam_id() == user.getUser().getTeam().getId()) {
                row.setBackgroundColor(getActivity().getResources().getColor(R.color.azul_suave));
            } else {
                row.setBackgroundResource(R.drawable.borders_table_grey);
            }

            TextView nameScorer = new TextView(getContext());
            nameScorer.setTextSize(11);
            nameScorer.setTextColor(getActivity().getResources().getColor(R.color.futbolinAzul));
            nameScorer.setText(roundScorers.getPlayer().getShort_name());
            nameScorer.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            row.addView(nameScorer);

            TextView teamScorer = new TextView(getContext());
            teamScorer.setTextSize(11);
            teamScorer.setTextColor(getActivity().getResources().getColor(R.color.futbolinAzul));
            teamScorer.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            String team = "";
            for (CategoryPositions categoryPositions : tournament.getCategory().getPositions()) {
                if (categoryPositions.getTeam_id() == roundScorers.getPlayer().getTeam_id()) {
                    team = categoryPositions.getTeam_name();
                    break;
                }
            }
            teamScorer.setText(team);
            row.addView(teamScorer);

            TextView goalsScorer = new TextView(getContext());
            goalsScorer.setTextSize(11);
            goalsScorer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            Log.e("goals", roundScorers.getGoals() + "");
            goalsScorer.setText(roundScorers.getGoals() + "");
            row.addView(goalsScorer);

            scorersTable.addView(row);
        }

    }

    public void getMatch(String file_log) {

        SvcApi svc;
        svc = Svc.initAuth(getContext());
        Call<getMatch> call = svc.getMatch(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID), file_log);
        call.enqueue(new Callback<getMatch>() {
            @Override
            public void onResponse(Call<getMatch> call, Response<getMatch> response) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                final getMatch teams = response.body();
                final Response<getMatch> responsefinal = response;
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMatch = " + responsefinal);
                            if (responsefinal.code() == 400) {

                            } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {

                                loadMatchDialog(responsefinal.body());


                            }
                        }
                    });
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
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
                }
            }

            @Override
            public void onFailure(Call<getMatch> call, Throwable t) {
                t.printStackTrace();
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

    private void loadMatchDialog(getMatch match) {
        Log.e("postMachResponse", match.toString());
        matchDialog = new MatchDialog(getActivity(), match, fragmentTournament);
        matchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        matchDialog.show();
    }

    void ShowHideClassTableRows(String tag) {

        for (int i = 0; i < classTable.getChildCount(); i++) {
            TableRow row = (TableRow) classTable.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                View view = row.getVirtualChildAt(j);
                if (((String) view.getTag()).equals(tag) || ((String) view.getTag()).equals("fix")) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        }

    }

    void activeButtonClass(View view) {
        if(android.os.Build.VERSION.SDK_INT >= 21) {
            classPointsButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
        }
        classPointsButton.setTextColor(Color.parseColor("#3b6c8e"));

        if(android.os.Build.VERSION.SDK_INT >= 21) {
            classMatchsButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
        }
        classMatchsButton.setTextColor(Color.parseColor("#3b6c8e"));

        if(android.os.Build.VERSION.SDK_INT >= 21) {
            classGoalsButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
        }
        classGoalsButton.setTextColor(Color.parseColor("#3b6c8e"));


        if(android.os.Build.VERSION.SDK_INT >= 21) {
            view.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.futbolinAzul));
        }
        ((Button) view).setTextColor(Color.parseColor("#FFFFFF"));
    }

    void updateRoundButtons(Button button) {
        int roundNumber = (int) button.getTag();
        for (Round round : tournament.getCategory().getRounds()) {
            if (roundNumber == round.getNumber()) {
                currentRound = round;
                break;
            }
        }

        if (currentRound != null) {

            roundNameLabel.setText(getResources().getString(R.string.date) + " " + currentRound.getNumber());
            SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            roundDateLabel.setText(parser.format(new Date(currentRound.getDatetime() * 1000)));

            List<View> rows = new ArrayList<>();

            for(int i =0; i< roundTable.getChildCount(); i++){
                rows.add(roundTable.getChildAt(i));
            }
            for(int i =0; i< rows.size(); i++){
                roundTable.removeView(rows.get(i));
            }
            for (RoundMatch roundMatch : currentRound.getMatches()) {
                TableRow row = new TableRow(getContext());
                row.setPadding(20, 20, 20, 20);
                if (roundMatch.getLocal_id() == user.getUser().getTeam().getId() || roundMatch.getVisit_id() == user.getUser().getTeam().getId()) {
                    row.setBackgroundColor(getActivity().getResources().getColor(R.color.azul_suave));
                } else {
                    row.setBackgroundResource(R.drawable.borders_table_grey);
                }


                TextView local = new TextView(getContext());
                local.setTextSize(11);
                if(roundMatch.getLocal_name().length() > 20){
                    roundMatch.setLocal_name(roundMatch.getLocal_name().substring(0,20));
                }
                local.setText(roundMatch.getLocal_name());
                local.setTextColor(Color.parseColor("#3b6c8e"));
                local.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                row.addView(local);

                TextView localGoal = new TextView(getContext());
                localGoal.setTextSize(11);
                if(roundMatch.getMatch() != null){
                    localGoal.setText(roundMatch.getMatch().getLocal_goals() + "");
                }else{
                    localGoal.setText("-");
                }
                localGoal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(localGoal);

                TextView visitGoal = new TextView(getContext());
                visitGoal.setTextSize(11);
                if(roundMatch.getMatch() != null) {
                    visitGoal.setText(roundMatch.getMatch().getVisit_goals() + "");
                }else{
                    visitGoal.setText("-");
                }

                visitGoal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                row.addView(visitGoal);

                TextView visit = new TextView(getContext());
                visit.setTextSize(11);
                if(roundMatch.getVisit_name().length() > 20){
                    roundMatch.setVisit_name(roundMatch.getVisit_name().substring(0,20));
                }
                visit.setText(roundMatch.getVisit_name());
                visit.setTextColor(getActivity().getResources().getColor(R.color.futbolinAzul));
                visit.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                row.addView(visit);

                if(roundMatch.getMatch() != null) {
                ImageView seeImage = new ImageView(getActivity());
                seeImage.requestLayout();

                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_search, seeImage);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(getActivity().getResources().getColor(R.color.futbolinAzul));
                seeImage.setPadding(0,0,30,0);

                    seeImage.setTag(roundMatch.getMatch().getLogfile());
                    seeImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("Look the match!" + view.getTag(), "onclick");
                            getMatch(view.getTag() + "");
                        }
                    });

                    row.addView(seeImage);
                }
                roundTable.addView(row);
            }
            for (int i = 0; i < roundTable.getChildCount(); i++) {
                TableRow row = (TableRow) roundTable.getChildAt(i);
                for (int j = 0; j < row.getChildCount(); j++) {
                    View view = row.getChildAt(j);
                    if (view instanceof ImageView) {
                        view.getLayoutParams().height = 42;
                        view.getLayoutParams().width = 42;
                    }
                }
            }

            if (currentRound.getNumber() - 3 > 0) {
                previousRoundButton.setVisibility(View.VISIBLE);
                previousRoundButton.setText(" <<");
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    previousRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
                }
                previousRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                previousRoundButton.setTag(currentRound.getNumber() - 3);
                previousRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRoundButtons((Button) view);
                    }
                });
            } else {
                previousRoundButton.setVisibility(View.INVISIBLE);
                previousRoundButton.setText(" <<");
                previousRoundButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    previousRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
                }
                previousRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                previousRoundButton.setTag(currentRound.getNumber() - 3);
                previousRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                    }
                });
            }
            if (currentRound.getNumber() - 2 > 0) {
                firstRoundButton.setVisibility(View.VISIBLE);
                firstRoundButton.setText(currentRound.getNumber() - 2 + "");
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    firstRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
                }
                firstRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                firstRoundButton.setTag(currentRound.getNumber() - 2);
                firstRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRoundButtons((Button) view);
                    }
                });
            } else {
                firstRoundButton.setVisibility(View.INVISIBLE);
                firstRoundButton.setText(" ");
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    firstRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
                }
                firstRoundButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                firstRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                firstRoundButton.setTag(currentRound.getNumber() - 2);
                firstRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                    }
                });
            }
            if (currentRound.getNumber() - 1 > 0) {
                secondRoundButton.setVisibility(View.VISIBLE);
                secondRoundButton.setText(currentRound.getNumber() - 1 + " ");
                secondRoundButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    secondRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
                }
                secondRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                secondRoundButton.setTag(currentRound.getNumber() - 1);
                secondRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRoundButtons((Button) view);
                    }
                });
            } else {
                secondRoundButton.setVisibility(View.INVISIBLE);
                secondRoundButton.setText(" ");
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    secondRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
                }
                secondRoundButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                secondRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                secondRoundButton.setTag(currentRound.getNumber() - 1);
                secondRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                    }
                });
            }
            if (currentRound.getNumber() > 0) {

                thirdRoundButton.setText(currentRound.getNumber() + " ");
                thirdRoundButton.setBackgroundColor(Color.parseColor("#3b6c8e"));
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    thirdRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.futbolinAzul));
                }
                thirdRoundButton.setTextColor(Color.parseColor("#FFFFFF"));
                thirdRoundButton.setTag(currentRound.getNumber());
                thirdRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRoundButtons((Button) view);
                    }
                });
            } else {
                thirdRoundButton.setText(" ");
                thirdRoundButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    thirdRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
                }
                thirdRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                thirdRoundButton.setTag(currentRound.getNumber());
                thirdRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                    }
                });
            }
            if (currentRound.getNumber() + 1 <= tournament.getCategory().getRounds().get(tournament.getCategory().getRounds().size() -1).getNumber()) {
                fourthRoundButton.setVisibility(View.VISIBLE);
                fourthRoundButton.setText(currentRound.getNumber() + 1 + " ");
                fourthRoundButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    fourthRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
                }
                fourthRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                fourthRoundButton.setTag(currentRound.getNumber() + 1);
                fourthRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRoundButtons((Button) view);
                    }
                });
            } else {
                fourthRoundButton.setVisibility(View.INVISIBLE);
                fourthRoundButton.setText(" ");
                fourthRoundButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    fourthRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
                }
                fourthRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                fourthRoundButton.setTag(currentRound.getNumber() + 1);
                fourthRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                    }
                });
            }
            if (currentRound.getNumber() + 2 <= tournament.getCategory().getRounds().get(tournament.getCategory().getRounds().size() -1).getNumber()) {
                fifthRoundButton.setVisibility(View.VISIBLE);
                fifthRoundButton.setText(currentRound.getNumber() + 2 + " ");
                fifthRoundButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    fifthRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
                }
                fifthRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                fifthRoundButton.setTag(currentRound.getNumber() + 2);
                fifthRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRoundButtons((Button) view);
                    }
                });
            } else {
                fifthRoundButton.setVisibility(View.INVISIBLE);
                fifthRoundButton.setText(" ");
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    fifthRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
                }
                fifthRoundButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                fifthRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                fifthRoundButton.setTag(currentRound.getNumber() + 2);
                fifthRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                    }
                });
            }
            if (currentRound.getNumber() + 3 <= tournament.getCategory().getRounds().get(tournament.getCategory().getRounds().size() -1).getNumber()) {
                AfterRoundButton.setVisibility(View.VISIBLE);
                AfterRoundButton.setText(">>");
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    AfterRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
                }
                AfterRoundButton.setBackgroundColor(Color.parseColor("#FFFFFF"));
                AfterRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                AfterRoundButton.setTag(currentRound.getNumber() + 2);
                AfterRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateRoundButtons((Button) view);
                    }
                });
            } else {
                AfterRoundButton.setVisibility(View.INVISIBLE);
                AfterRoundButton.setText(" ");
                if(android.os.Build.VERSION.SDK_INT >= 21) {
                    AfterRoundButton.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
                }
                AfterRoundButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
                AfterRoundButton.setTextColor(Color.parseColor("#3b6c8e"));
                AfterRoundButton.setTag(currentRound.getNumber() + 2);
                AfterRoundButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                    }
                });
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}

