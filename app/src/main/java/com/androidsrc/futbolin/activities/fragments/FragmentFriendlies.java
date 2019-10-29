package com.androidsrc.futbolin.activities.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.FriendlyTeam;
import com.androidsrc.futbolin.communications.http.auth.get.TeamWithFormationId;
import com.androidsrc.futbolin.communications.http.auth.get.getMatch;
import com.androidsrc.futbolin.communications.http.auth.get.getTeamWithFormationId;
import com.androidsrc.futbolin.communications.http.auth.get.getTeams;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.get.market.MarketObject;
import com.androidsrc.futbolin.communications.http.auth.post.PostMatch;
import com.androidsrc.futbolin.communications.http.auth.post.PostMatchResponse;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.TeamDB;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.adapters.FriendliesRecyclerAdapter;
import com.androidsrc.futbolin.utils.adapters.TrophiesAdapter;
import com.androidsrc.futbolin.utils.dialogs.MatchDialog;
import com.androidsrc.futbolin.utils.dialogs.StatsDialog;
import com.androidsrc.futbolin.utils.itemsDecoration.SpacesItemDecoration;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
public class FragmentFriendlies extends Fragment implements  MatchDialog.DialogClickListener,
                            StatsDialog.DialogClickListener, FriendliesRecyclerAdapter.ItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    getUser user;
    ImageView imageShield;
    SvcApi svc;
    getTeams teams;
    View v;
    TableLayout table, sparringsTable;
    AppCompatDialog postingMatchDialog;
    private  OnFragmentInteractionListener mListener;
    FragmentFriendlies fragmentFriendlies;
    MatchDialog matchDialog;
    StatsDialog statsDialog;
    DatabaseManager db;
    ImageButton sparringsButton;
    boolean sparringHidden = true;
  //  ConstraintLayout layout;
    AppCompatDialog dialog;
    RecyclerView sparringsRecycler, teamsRecycler;
    FriendliesRecyclerAdapter adapterSparrings;
    FriendliesRecyclerAdapter adapterFriendlies;
    TabLayout tabLayout;
    EditText searchInput;
    TextInputLayout searchLayout;
    boolean averageAscendant;
    boolean scrollMakesHide;
    List<Integer> lastDyScrollRecyclerController;
    boolean teamsShown = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lastDyScrollRecyclerController = new ArrayList<>();
        scrollMakesHide = false;
        averageAscendant = true;
        if (getArguments() != null) {
            user = (com.androidsrc.futbolin.communications.http.auth.get.getUser) getArguments().getSerializable(ARG_USER);
            mParam2 = getArguments().getString(ARG_PARAM2);
            this.fragmentFriendlies = this;
            db =  new DatabaseManager(getContext());
        }
        // Inflate the layout for this fragment
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            v = inflater.inflate(R.layout.fragment_friendlies, container, false);
        } else{
            v = inflater.inflate(R.layout.fragment_friendlies_old_versions, container, false);
        }

        teamsRecycler = v.findViewById(R.id.fragment_friendlies_table);
        sparringsRecycler = v.findViewById(R.id.fragment_friendlies_sparrings_table);
        searchLayout = v.findViewById(R.id.fragment_friendlies_search_layout);
        searchInput = v.findViewById(R.id.fragment_friendlies_search);

     //   layout = v.findViewById(R.id.fragment_friendlies_layout);

        tabLayout = (TabLayout) v.findViewById(R.id.fragment_friendlies_tablayout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.teams)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.sparrings)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null && s.length() > 0){
                    filterAndLoad(s.toString());
                }else if(s.length() == 0){
                    loadTeams();
                }
            }
        });
        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                Log.e("onEditorAction", "actionId " + actionId);
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    Log.e("onEditorAction", "IME_ACTION_SEND");
                    hideKeyboard(v);
                    handled = true;
                }
                return handled;
            }
        });
        searchInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("onTabSelected", "tab position "+ tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        hideSparrings();
                        loadTeams();
                        teamsShown = true;
                        break;
                    case 1:
                        hideTeams();
                        loadSparrings();
                        teamsShown = false;
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_team_data) , getActivity());
        dialog.show();
        getTeams();

        return v;
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.e("onResume", "onResume fragment !!!!!!");
    }

    public void hideKeyboard(View view) {
        Log.e("hideKeyboard","hideKeyboard");
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
     //   inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
    }
    public void filterAndLoad(String text){


        List<FriendlyTeam> teams1 = new ArrayList<>();

        if(text.equals("")){
            teams1 = teams.getTeams();
        }else{
            for(FriendlyTeam friendlyTeam : teams.getTeams()){
                if(friendlyTeam.getName().toLowerCase().contains(text.toLowerCase())){
                    teams1.add(friendlyTeam);
                }
            }
        }

        adapterFriendlies = new FriendliesRecyclerAdapter(this, this,
                getActivity(),  teams1, user.getUser().getTeam().getId(), true);
        teamsRecycler.setAdapter(adapterFriendlies);
//        shieldsRV.addItemDecoration(new ItemOffsetDecoration(10));shieldsRV
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        teamsRecycler.setLayoutManager(linearLayoutManager);
//        teamsRecycler.addItemDecoration(new SpacesItemDecoration(6));

        TransitionManager.beginDelayedTransition(teamsRecycler);
        searchLayout.setVisibility(View.VISIBLE);
        TransitionManager.beginDelayedTransition(searchLayout);
    }

    public void updateRecycleFriendliesByAverage(){
        if(teamsShown){
            List<FriendlyTeam> friendlyTeams = teams.getTeams();
            Collections.sort(friendlyTeams, new Comparator<FriendlyTeam>() {
                public int compare(FriendlyTeam i1, FriendlyTeam i2) {
                    if(averageAscendant){
                        return i2.getAverage() - i1.getAverage();
                    }else{
                        return i1.getAverage() - i2.getAverage();
                    }

                }
            });

            loadTeams();
            hideSparrings();
            averageAscendant = !averageAscendant;
        }else{

            List<FriendlyTeam> friendlyTeams = teams.getSparrings();
            Collections.sort(friendlyTeams, new Comparator<FriendlyTeam>() {
                public int compare(FriendlyTeam i1, FriendlyTeam i2) {
                    if(averageAscendant){
                        return i2.getAverage() - i1.getAverage();
                    }else{
                        return i1.getAverage() - i2.getAverage();
                    }

                }
            });

            loadSparrings();
            hideTeams();
            searchLayout.setVisibility(View.GONE);
            averageAscendant = !averageAscendant;
        }

    }
    @Override
    public void onButtonClick() {
        if(matchDialog != null && matchDialog.isShowing())
            matchDialog.dismiss();

        if(statsDialog != null && statsDialog.isShowing())
            statsDialog.dismiss();
    }

    @Override
    public void onItemClick(View view, int position) {

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
    public void getTeams(){

        svc = Svc.initAuth(getContext());
        Call<getTeams> call = svc.getTeams(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getTeams>() {
            @Override
            public void onResponse(Call<getTeams> call, Response<getTeams> response) {
                final getTeams teams = response.body();
                final Response<getTeams> responsefinal = response;
                if (response.isSuccessful()) {
                    if(getActivity()!=null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Success, getMe = " + responsefinal);
                                if (responsefinal.code() == 400) {

                                } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {
                                    setTeams(responsefinal.body());
                                    if(dialog != null && dialog.isShowing()){
                                        dialog.dismiss();
                                        dialog = null;
                                    }
                                    loadTeams();
                                    hideSparrings();

                                }
                            }
                        });
                    }
                } else {
                    try {
                        if(response.errorBody().string().contains("live_match") && response.errorBody().string().contains("Broadcasting live match")){
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
            public void onFailure(Call<getTeams> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    public void deleteSearchText(){
        if(searchInput != null){
            searchInput.setText("");
        }

    }
    void setTeams(getTeams teams){
        this.teams = teams;
        updateTeamDBs();
    }
    void loadSparrings(){

    //    tabLayout.setVisibility(View.VISIBLE);
        searchLayout.setVisibility(View.GONE);
        scrollMakesHide = false;
        adapterSparrings = new FriendliesRecyclerAdapter(this, this,
                getActivity(),
                teams.getSparrings(), user.getUser().getTeam().getId(), false);
        sparringsRecycler.setAdapter(adapterSparrings);
//        shieldsRV.addItemDecoration(new ItemOffsetDecoration(10));shieldsRV
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        sparringsRecycler.setLayoutManager(linearLayoutManager);
  //      sparringsRecycler.addItemDecoration(new SpacesItemDecoration(3));
        TransitionManager.beginDelayedTransition(sparringsRecycler);
        searchLayout.setVisibility(View.GONE);
        TransitionManager.beginDelayedTransition(searchLayout);

    }
    void hideSparrings(){
        adapterSparrings = new FriendliesRecyclerAdapter(this, this,
                getActivity(),
                new ArrayList<FriendlyTeam>(), user.getUser().getTeam().getId(), false);
        sparringsRecycler.setAdapter(adapterSparrings);
//        shieldsRV.addItemDecoration(new ItemOffsetDecoration(10));shieldsRV
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        sparringsRecycler.setLayoutManager(linearLayoutManager);

        TransitionManager.beginDelayedTransition(sparringsRecycler);
    }


    void loadTeams(){

        if(teams == null || teams.getTeams() == null){
            getTeams();
        }else {
            tabLayout.setVisibility(View.VISIBLE);
            searchLayout.setVisibility(View.VISIBLE);
            scrollMakesHide = false;
            adapterFriendlies = new FriendliesRecyclerAdapter(this, this, getActivity(),
                    teams.getTeams(), user.getUser().getTeam().getId(), true);
            teamsRecycler.setAdapter(adapterFriendlies);
//        shieldsRV.addItemDecoration(new ItemOffsetDecoration(10));shieldsRV
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            teamsRecycler.setLayoutManager(linearLayoutManager);
//        teamsRecycler.addItemDecoration(new SpacesItemDecoration(6));

            TransitionManager.beginDelayedTransition(teamsRecycler);
            searchLayout.setVisibility(View.VISIBLE);
            TransitionManager.beginDelayedTransition(searchLayout);
            teamsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!recyclerView.canScrollVertically(1)) {

                    }
                }
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if(dy != 0) {
                        if (lastDyScrollRecyclerController != null) {
                            if (lastDyScrollRecyclerController.size() == 6) {
                                if (dy > 5 && !scrollMakesHide) {
                                    //bajando
                                    tabLayout.setVisibility(View.GONE);
                                    searchLayout.setVisibility(View.GONE);
                                    scrollMakesHide = !scrollMakesHide;
                                    TransitionManager.beginDelayedTransition(tabLayout);
                                    TransitionManager.beginDelayedTransition(searchLayout);
                                } else if (dy < -5 && scrollMakesHide) {
                                    tabLayout.setVisibility(View.VISIBLE);
                                    searchLayout.setVisibility(View.VISIBLE);
                                    scrollMakesHide = !scrollMakesHide;
                                    TransitionManager.beginDelayedTransition(tabLayout);
                                    TransitionManager.beginDelayedTransition(searchLayout);
                                }
                                lastDyScrollRecyclerController = new ArrayList<>();
                            } else {
                                if (lastDyScrollRecyclerController.isEmpty()) {
                                    lastDyScrollRecyclerController.add(dy);
                                } else if (lastDyScrollRecyclerController.size() == 1) {
                                    if ((lastDyScrollRecyclerController.get(0) > 0 && dy > 0)
                                            || (lastDyScrollRecyclerController.get(0) < 0 && dy < 0)) {
                                        lastDyScrollRecyclerController.add(dy);
                                    } else {
                                        lastDyScrollRecyclerController = new ArrayList<>();
                                    }
                                } else if (lastDyScrollRecyclerController.size() == 2) {
                                    if ((lastDyScrollRecyclerController.get(0) > 0 && dy > 0)
                                            || (lastDyScrollRecyclerController.get(0) < 0 && dy < 0)) {
                                        lastDyScrollRecyclerController.add(dy);
                                    } else {
                                        lastDyScrollRecyclerController = new ArrayList<>();
                                    }
                                }else if (lastDyScrollRecyclerController.size() == 3) {
                                    if ((lastDyScrollRecyclerController.get(0) > 0 && dy > 0)
                                            || (lastDyScrollRecyclerController.get(0) < 0 && dy < 0)) {
                                        lastDyScrollRecyclerController.add(dy);
                                    } else {
                                        lastDyScrollRecyclerController = new ArrayList<>();
                                    }
                                }else if (lastDyScrollRecyclerController.size() == 4) {
                                    if ((lastDyScrollRecyclerController.get(0) > 0 && dy > 0)
                                            || (lastDyScrollRecyclerController.get(0) < 0 && dy < 0)) {
                                        lastDyScrollRecyclerController.add(dy);
                                    } else {
                                        lastDyScrollRecyclerController = new ArrayList<>();
                                    }
                                }else if (lastDyScrollRecyclerController.size() == 5) {
                                    if ((lastDyScrollRecyclerController.get(0) > 0 && dy > 0)
                                            || (lastDyScrollRecyclerController.get(0) < 0 && dy < 0)) {
                                        lastDyScrollRecyclerController.add(dy);
                                    } else {
                                        lastDyScrollRecyclerController = new ArrayList<>();
                                    }
                                }
                            }
                        }
                    }

                }
            });


        }
    }
    void hideTeams(){
        adapterFriendlies = new FriendliesRecyclerAdapter(this, this, getActivity(),
                new ArrayList<FriendlyTeam>(), user.getUser().getTeam().getId(), true);
        teamsRecycler.setAdapter(adapterFriendlies);
//        shieldsRV.addItemDecoration(new ItemOffsetDecoration(10));shieldsRV
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        teamsRecycler.setLayoutManager(linearLayoutManager);
   //     teamsRecycler.addItemDecoration(new SpacesItemDecoration(6));
        TransitionManager.beginDelayedTransition(teamsRecycler);
    }
    public void postMatch(FriendlyTeam friendlyTeam){

        final FriendlyTeam rival = friendlyTeam;
        postingMatchDialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.match_time),
                getResources().getString(R.string.match_time_2) , getActivity());

        postingMatchDialog.show();

        PostMatch postMatch = new PostMatch();
        postMatch.setDevice_id(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        postMatch.setRival(friendlyTeam.getId());

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
                                getMatch(responsefinal.body(), rival);


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
    public void getMatch(PostMatchResponse match, FriendlyTeam friendlyTeam){
        Log.e("PostMatchResponse", match.toString());
        final FriendlyTeam rival = friendlyTeam;

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


    private void loadMatchDialog(getMatch match){
        Log.e("loadMatchDialog ", match.toString());
        matchDialog = new MatchDialog(getActivity(), match, fragmentFriendlies);
        matchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        matchDialog.show();
    }
    public void getgetTeam(long team_id){
        if(postingMatchDialog != null && postingMatchDialog.isShowing()){
            postingMatchDialog.dismiss();
            postingMatchDialog = null;
        }
        postingMatchDialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_team_data) , getActivity());
        postingMatchDialog.show();

        SvcApi svc;
        svc = Svc.initAuth(getContext());
        Call<getTeamWithFormationId> call = svc.getgetTeam(team_id, Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getTeamWithFormationId>() {
            @Override
            public void onResponse(Call<getTeamWithFormationId> call, Response<getTeamWithFormationId> response) {

                final Response<getTeamWithFormationId> responsefinal = response;
                final getTeamWithFormationId getgetTeam = response.body();

                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){

                                getTeam(getgetTeam.getTeam());
                            }
                        }
                    });
                } else {
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
            public void onFailure(Call<getTeamWithFormationId> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    private void getTeam(TeamWithFormationId team){
        final TeamWithFormationId team_final = team;
        if(postingMatchDialog != null && postingMatchDialog.isShowing()){
            postingMatchDialog.dismiss();
            postingMatchDialog = null;
        }
        postingMatchDialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_strategies) , getActivity());
        postingMatchDialog.show();

        svc = Svc.initAuth(getContext());
        Call<TeamWithFormationId> call = svc.getTeam(team.getId(), Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<TeamWithFormationId>() {
            @Override
            public void onResponse(Call<TeamWithFormationId> call, Response<TeamWithFormationId> response) {

                final Response<TeamWithFormationId> responsefinal = response;
                final TeamWithFormationId getTeam = response.body();
                Log.e("Team", getTeam.toString());

                if(postingMatchDialog != null && postingMatchDialog.isShowing()){
                    postingMatchDialog.dismiss();
                    postingMatchDialog = null;
                }

                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                team_final.setStrategy(getTeam.getStrategy());
                                team_final.setMatches(getTeam.getMatches());
                                team_final.setLast_matches(getTeam.getLast_matches());
                                team_final.setLast_matches_versus(getTeam.getLast_matches_versus());
                                team_final.setMatches_versus(getTeam.getMatches_versus());
                                loadStatsDialog(team_final);
                            }
                        }
                    });
                } else {
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
            public void onFailure(Call<TeamWithFormationId> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    void loadStatsDialog(TeamWithFormationId team){
        statsDialog  = new StatsDialog(getActivity(), team, fragmentFriendlies);
        statsDialog.show();
    }
    /*
    public void updateImageFriendly(long team_id){
        int position = 0;
        TextView remainingTime = new TextView(getContext());
        remainingTime.setText("23h");
        remainingTime.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TableRow rowCopied = null;
        for(int i=0; i< table.getChildCount(); i++){
            TableRow row = (TableRow) table.getChildAt(i);
            if(row.getTag().equals("row_" + team_id)){
                for(int j=0; j<row.getChildCount(); j++){
                    View view = row.getChildAt(j);
                    if(view instanceof ImageView){
                        position = j;
                        if(view.getTag().equals("play_image_" + team_id)){
                            row.removeView(view);
                            rowCopied = row;
                            break;
                        }
                    }
                }
            }

        }
        if(rowCopied != null) {

            rowCopied.addView(remainingTime, position);
        }

    }
    */

    public void updateTeamDBs(){
        if(db == null){
            db = new DatabaseManager(getActivity());
        }
        for(FriendlyTeam friendlyTeam : teams.getTeams()){
            TeamDB teamDB = null;
            if(db.findTeamDBById(friendlyTeam.getId()) == null){
                teamDB = new TeamDB();
            }else{
                teamDB = db.findTeamDBById(friendlyTeam.getId());
            }
            teamDB.setId(friendlyTeam.getId());
            teamDB.setName(friendlyTeam.getName());
            teamDB.setShort_name(friendlyTeam.getShort_name());
            db.saveTeamDB(teamDB);
        }
    }


}