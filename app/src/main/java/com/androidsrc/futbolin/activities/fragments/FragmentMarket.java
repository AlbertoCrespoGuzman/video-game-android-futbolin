package com.androidsrc.futbolin.activities.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.getPlayer;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.get.market.MarketObject;
import com.androidsrc.futbolin.communications.http.auth.get.market.getMarket;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.TeamDB;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.adapters.AdapterMarketPlayer;
import com.androidsrc.futbolin.utils.adapters.FriendliesRecyclerAdapter;
import com.androidsrc.futbolin.utils.dialogs.PlayerDialog;
import com.androidsrc.futbolin.utils.dialogs.PlayerDialog2;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
public class FragmentMarket extends Fragment implements PlayerDialog.DialogClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_PLAYER_ID = "player_id";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    getUser user;
    ImageView imageShield;
    TableLayout table;
    TableRow headerRow;
    private OnFragmentInteractionListener mListener;
    Button buttonShowHide;
    Spinner spinner;
    List<String> spinnerOptions ;
    List<Boolean> inverseOnclick = Arrays.asList(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
    int spinnerSelected = 0;
    PlayerDialog playerDialog;
    FragmentMarket fragmentPlayers;
    AppCompatDialog dialog;
    getMarket market;
    List<MarketObject> players = new ArrayList<>();
    String pattern;
    DecimalFormat decimalFormat;
    DatabaseManager db;
    TextView spendingMargin;
    Button buttonMarketTransactions, buttonMarketFollowing, buttonMarketOffers;
    Guideline guideline;
    RecyclerView recyclerMarket;
    AdapterMarketPlayer adapterMarketPlayer;
    List<MarketObject> currentPlayers;
    boolean averageAscendant;
    List<Integer> lastDyScrollRecyclerController;
    boolean scrollMakesHide;
    LinearLayout threButtons;
    boolean notificationPlayerDialogShown = false;
    long player_id;

    public FragmentMarket() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @param player_id Parameter 2.
     * @return A new instance of fragment FragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMarket newInstance(getUser user, long player_id) {
        FragmentMarket fragment = new FragmentMarket();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        args.putLong(ARG_PLAYER_ID, player_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (com.androidsrc.futbolin.communications.http.auth.get.getUser) getArguments().getSerializable(ARG_USER);
            player_id = getArguments().getLong(ARG_PLAYER_ID);
            db = new DatabaseManager(getActivity());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        spinnerOptions = Arrays.asList(getActivity().getResources().getString(R.string.all),
                getResources().getString(R.string.goalkeepers), getResources().getString(R.string.defensors),
                getResources().getString(R.string.midfielder), getResources().getString(R.string.forwards));
        scrollMakesHide = false;
        lastDyScrollRecyclerController = new ArrayList<>();
        averageAscendant = true;
        fragmentPlayers = this;
        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);
        Log.e("onCreate", "onCreate FragmentMarket");
        View v =  inflater.inflate(R.layout.fragment_market, container, false);

        threButtons = v.findViewById(R.id.fragment_market_buttons_layout);
        recyclerMarket = v.findViewById(R.id.fragment_market_recycler);
        buttonMarketTransactions = v.findViewById(R.id.fragment_market_transactions);
        buttonMarketTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).changeToMarketTransactions(1);
            }
        });
        buttonMarketFollowing = v.findViewById(R.id.fragment_market_following);
        buttonMarketFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).changeToMarketFollowing();
            }
        });
        buttonMarketOffers = v.findViewById(R.id.fragment_market_offers);
        buttonMarketOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).changeToMarketOffers();
            }
        });

        buttonShowHide = v.findViewById(R.id.fragment_players_button_show_hide);
        spendingMargin = v.findViewById(R.id.fragment_market_spending_margin);
       /* guideline = v.findViewById(R.id.fragment_players_guideline);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            guideline.setGuidelinePercent(0.15f);
        }else{
            guideline.setGuidelinePercent(0.22f);
        }
        */
        spendingMargin.setText(getResources().getString(R.string.max_offer) + " " + decimalFormat.format(user.getUser().getTeam().getSpending_margin()) + " $");
        table = v.findViewById(R.id.fragment_players_table);
        headerRow = v.findViewById(R.id.fragment_players_table_header_row);
        for(int i = 0; i < headerRow.getChildCount(); i++){
            if( ((String)(headerRow.getChildAt(i)).getTag()).contains("header")){
                View view = headerRow.getChildAt(i);
                if(view instanceof TextView){
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((MainActivity) getActivity()).onClickFragmentMarket(view);
                        }
                    });
                }
            }
        }
        spinner = v.findViewById(R.id.fragment_players_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_market_positions, spinnerOptions);
        dataAdapter.setDropDownViewResource(R.layout.spinner_market_item_positions);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerFilter(i);
                spinnerSelected = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    ShowHideColumns();
                }else{
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    loadRecyclerMarket(currentPlayers);
                }
                ((MainActivity) getActivity()).setChangingScreenOrientation(true, "FragmentMarket");

            }
        });

        getMarket();

        return v;
    }
    void spinnerFilter(int position){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            switch (position){
                case 0:
                    currentPlayers = players;
                    loadRecyclerMarket(players);
                    break;
                case 1:
                    List<MarketObject> marketObjects = new ArrayList<>();
                    for(MarketObject marketObject : players){
                        if(marketObject.getPlayer().getPosition().equals("ARQ")){
                            marketObjects.add(marketObject);
                        }
                    }
                    currentPlayers = marketObjects;
                    loadRecyclerMarket(marketObjects);

                    break;
                case 2:
                    List<MarketObject> marketObjects2 = new ArrayList<>();
                    for(MarketObject marketObject : players){
                        if(marketObject.getPlayer().getPosition().equals("DEF")){
                            marketObjects2.add(marketObject);
                        }
                    }
                    currentPlayers = marketObjects2;
                    loadRecyclerMarket(marketObjects2);

                    break;
                case 3:
                    List<MarketObject> marketObjects3 = new ArrayList<>();
                    for(MarketObject marketObject : players){
                        if(marketObject.getPlayer().getPosition().equals("MED")){
                            marketObjects3.add(marketObject);
                        }
                    }
                    currentPlayers = marketObjects3;
                    loadRecyclerMarket(marketObjects3);

                    break;
                case 4:
                    List<MarketObject> marketObjects4 = new ArrayList<>();
                    for(MarketObject marketObject : players){
                        if(marketObject.getPlayer().getPosition().equals("ATA")){
                            marketObjects4.add(marketObject);
                        }
                    }
                    currentPlayers = marketObjects4;
                    loadRecyclerMarket(marketObjects4);

                    break;

            }
        }else{
            switch (position){
                case 0:
                    for(int i=0; i< table.getChildCount(); i++){
                        table.getChildAt(i).setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    for(int i=0; i< table.getChildCount(); i++){
                        if(table.getChildAt(i).getTag().equals("ARQ") || table.getChildAt(i).getTag().equals("header")){
                            table.getChildAt(i).setVisibility(View.VISIBLE);
                        }else{
                            table.getChildAt(i).setVisibility(View.GONE);
                        }
                    }
                    break;
                case 2:
                    for(int i=0; i< table.getChildCount(); i++){
                        if(table.getChildAt(i).getTag().equals("DEF")|| table.getChildAt(i).getTag().equals("header")){
                            table.getChildAt(i).setVisibility(View.VISIBLE);
                        }else{
                            table.getChildAt(i).setVisibility(View.GONE);
                        }
                    }
                    break;
                case 3:
                    for(int i=0; i< table.getChildCount(); i++){
                        if(table.getChildAt(i).getTag().equals("MED") || table.getChildAt(i).getTag().equals("header")){
                            table.getChildAt(i).setVisibility(View.VISIBLE);
                        }else{
                            table.getChildAt(i).setVisibility(View.GONE);
                        }
                    }
                    break;
                case 4:
                    for(int i=0; i< table.getChildCount(); i++){
                        if(table.getChildAt(i).getTag().equals("ATA") || table.getChildAt(i).getTag().equals("header")){
                            table.getChildAt(i).setVisibility(View.VISIBLE);
                        }else{
                            table.getChildAt(i).setVisibility(View.GONE);
                        }
                    }
                    break;

            }
        }

    }

    void ShowHideColumns(){
        recyclerMarket.setVisibility(View.GONE);
        table.setVisibility(View.VISIBLE);
        List<TextView> columns = new ArrayList<>();
        if(columns.isEmpty()){
            for(int i=0; i < headerRow.getChildCount(); i++){
                TextView textView = (TextView) headerRow.getChildAt(i);

                if(textView.getTag() != null && ((String)textView.getTag()).contains("hide")){
                    columns.add(textView);
                }
            }
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            for(TextView tv : columns){
                headerRow.removeView(tv);
            }

        }else{
            for(TextView textView : columns){
                headerRow.removeView(textView);
                textView.setPadding(2,2,2,2);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) getActivity()).onClickFragmentMarket(view);
                    }
                });
                headerRow.addView(textView);
            }
        }
        for(int i = 0; i < table.getChildCount(); i++){
            if( !((TableRow)table.getChildAt(i)).getTag().equals("header")){
                table.removeView(table.getChildAt(i));
            }
        }

        buildRows();

    }
    public void updateRecyclerMarketByAverage(){
        List<MarketObject> marketObjects = currentPlayers;
        Collections.sort(marketObjects, new Comparator<MarketObject>() {
            public int compare(MarketObject i1, MarketObject i2) {
                if(averageAscendant){
                    return i2.getPlayer().getAverage() - i1.getPlayer().getAverage();
                }else{
                    return i1.getPlayer().getAverage() - i2.getPlayer().getAverage();
                }

            }
        });

        loadRecyclerMarket(marketObjects);
        averageAscendant = !averageAscendant;

    }
    public void updatePlayer(MarketObject player){
        int position = 0;

        for(int i=0; i < players.size(); i++){
            if(players.get(i).getPlayer().getId() == player.getPlayer().getId()){
                position = i;
                break;
            }
        }
        players.set(position, player);

        loadRecyclerMarket(currentPlayers);
    }
    public void loadRecyclerMarket(List<MarketObject> players){
        recyclerMarket.setVisibility(View.VISIBLE);
        table.setVisibility(View.GONE);
        adapterMarketPlayer = new AdapterMarketPlayer(this, null, getActivity(),
                players, user.getUser().getTeam().getId());
        recyclerMarket.setAdapter(adapterMarketPlayer);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerMarket.setLayoutManager(linearLayoutManager);

        TransitionManager.beginDelayedTransition(recyclerMarket);

        if(player_id > 0 && !notificationPlayerDialogShown){
            MarketObject myPlayer = null;
            for(MarketObject marketObject : players){
                if(marketObject.getPlayer().getId() == player_id){
                    myPlayer = marketObject;
                }
            }
            if(myPlayer != null){
                getPlayer(myPlayer);
                notificationPlayerDialogShown = true;
            }
        }
        recyclerMarket.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("sadfsdfsdf", "ssfsdfsdfsdf = " + newState);
                //direction integers: -1 for up, 1 for down, 0 will always return false.
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
                                spinner.setVisibility(View.GONE);
                                buttonShowHide.setVisibility(View.GONE);
                                spendingMargin.setVisibility(View.GONE);
                                threButtons.setVisibility(View.GONE);
                                scrollMakesHide = !scrollMakesHide;
                                //        TransitionManager.beginDelayedTransition(tabLayout);
                                //        TransitionManager.beginDelayedTransition(searchLayout);
                            } else if (dy < -5 && scrollMakesHide) {
                                spinner.setVisibility(View.VISIBLE);
                                buttonShowHide.setVisibility(View.VISIBLE);
                                spendingMargin.setVisibility(View.VISIBLE);
                                threButtons.setVisibility(View.VISIBLE);
                                scrollMakesHide = !scrollMakesHide;
                                TransitionManager.beginDelayedTransition(spinner);
                                TransitionManager.beginDelayedTransition(threButtons);
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

    public void onClick(View view) {
        Log.e("onClick","onClick Fragment view "+ (view.getTag()));
        if(view.getTag().equals("header_name")){
            removeAllRows();
            if(inverseOnclick.get(0)) {
                orderPlayersByName();
            }else{
                orderPlayersByNameInverse();
            }
            inverseOnclick.set(0,!inverseOnclick.get(0));
            ShowHideColumns();
        }else if(view.getTag().equals("header_position")){
            removeAllRows();
            if(inverseOnclick.get(1)) {
                orderPlayersByPosition();
            }else{
                orderPlayersByPositionInverse();
            }
            inverseOnclick.set(1,!inverseOnclick.get(1));
            ShowHideColumns();
        }else if(view.getTag().equals("header_average")){
            removeAllRows();
            if(inverseOnclick.get(2)) {
                orderPlayersByAverage();
            }else{
                orderPlayersByAverageInverse();
            }
            inverseOnclick.set(2,!inverseOnclick.get(2));
            ShowHideColumns();
        }else if(view.getTag().equals("header_best_offer")){
            removeAllRows();
            if(inverseOnclick.get(3)) {
                orderPlayersByBestOffer();
            }else{
                orderPlayersByBestOfferInverse();
            }
            inverseOnclick.set(3,!inverseOnclick.get(3));
            ShowHideColumns();
        }else if(view.getTag().equals("header_team_hide")){
            removeAllRows();
            if(inverseOnclick.get(4)) {
                orderPlayersByTeam();
            }else{
                orderPlayersByTeamInverse();
            }
            inverseOnclick.set(4,!inverseOnclick.get(4));
            ShowHideColumns();

        }else if(view.getTag().equals("header_closesat_hide")){
            removeAllRows();
            if(inverseOnclick.get(5)) {
                orderPlayersByClosesAt();
            }else{
                orderPlayersByClosesAtInverse();
            }
            inverseOnclick.set(5,!inverseOnclick.get(5));
            ShowHideColumns();
        }else if(view.getTag().equals("header_value_hide")){
            removeAllRows();
            if(inverseOnclick.get(6)) {
                orderPlayersByValue();
            }else{
                orderPlayersByValueInverse();
            }
            inverseOnclick.set(6,!inverseOnclick.get(6));
            ShowHideColumns();
        }
        spinnerFilter(spinnerSelected);
    }

    @Override
    public void onButtonClick() {
        if(playerDialog != null /* && playerDialog.isShowing() */) {
            playerDialog.dismiss();
        }
    }

    private void orderPlayersByAverage(){

        List<MarketObject> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            int minNumber = 1000;
            MarketObject minPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(minNumber >= player.getPlayer().getAverage()){
                    minNumber = player.getPlayer().getAverage();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByAverageInverse(){

        List<MarketObject> ordered = new ArrayList<>();
        Log.e("inverse bf pl", players.size() + "");
        while(!players.isEmpty()) {
            int maxNumber = 0;
            MarketObject minPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(maxNumber <= player.getPlayer().getAverage()){
                    maxNumber = player.getPlayer().getAverage();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }

        players = ordered;

    }

    private void orderPlayersByName(){
        Log.e("order", "Name");
        Collections.sort(players, new Comparator<MarketObject>() {
            @Override
            public int compare(final MarketObject object1, final MarketObject object2) {
                return object2.getPlayer().getName().compareTo(object1.getPlayer().getName());
            }
        });

    }
    private void orderPlayersByNameInverse(){

        Collections.sort(players, new Comparator<MarketObject>() {
            @Override
            public int compare(final MarketObject object1, final MarketObject object2) {

                return object1.getPlayer().getName().compareTo(object2.getPlayer().getName());
            }
        });
    }
    private void orderPlayersByPosition(){
        Log.e("order", "Position");
        Collections.sort(players, new Comparator<MarketObject>() {
            @Override
            public int compare(final MarketObject object1, final MarketObject object2) {
                return object2.getPlayer().getPosition().compareTo(object1.getPlayer().getPosition());
            }
        });

    }
    private void orderPlayersByPositionInverse(){

        Collections.sort(players, new Comparator<MarketObject>() {
            @Override
            public int compare(final MarketObject object1, final MarketObject object2) {

                return object1.getPlayer().getPosition().compareTo(object2.getPlayer().getPosition());
            }
        });
    }
    private void orderPlayersByBestOffer(){

        List<MarketObject> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            long minNumber = 1000000000000000L;
            MarketObject minPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(minNumber >= player.getOffer_value()){
                    minNumber = player.getOffer_value();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByBestOfferInverse(){

        List<MarketObject> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            long minNumber = -1;
            MarketObject minPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(minNumber <= player.getOffer_value()){
                    minNumber = player.getOffer_value();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByTeam(){

        Collections.sort(players, new Comparator<MarketObject>() {
            @Override
            public int compare(final MarketObject object1, final MarketObject object2) {
                return object2.getPlayer().getTeam().compareTo(object1.getPlayer().getTeam());
            }
        });

    }
    private void orderPlayersByTeamInverse(){

        Collections.sort(players, new Comparator<MarketObject>() {
            @Override
            public int compare(final MarketObject object1, final MarketObject object2) {

                return object1.getPlayer().getTeam().compareTo(object2.getPlayer().getTeam());
            }
        });
    }
    private void orderPlayersByClosesAt(){

        List<MarketObject> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            long minNumber = 1000000000000000000L;
            MarketObject minPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(minNumber >= player.getCloses_at()){
                    minNumber = player.getCloses_at();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByClosesAtInverse(){

        List<MarketObject> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            long minNumber = -1;
            MarketObject minPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(minNumber <= player.getCloses_at()){
                    minNumber = player.getCloses_at();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByValue(){

        List<MarketObject> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            long minNumber = 100000000000L;
            MarketObject minPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(minNumber >= player.getValue()){
                    minNumber = player.getValue();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByValueInverse(){

        List<MarketObject> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            long minNumber = -1;
            MarketObject minPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(minNumber <= player.getValue()){
                    minNumber = player.getValue();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
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



    private void buildRows(){

        String last = "";

        for(final MarketObject player : players){

            TableRow tableRow = new TableRow(getActivity());

            tableRow.setTag(player.getPlayer().getId());
            tableRow.setPadding(3,30,1,30);

            switch (player.getPlayer().getPosition()){
                case "ARQ":
                    if(last.equals("ARQ")){
                        tableRow.setBackgroundColor(Color.parseColor("#ffffb1"));
                        last = "";
                    }else{
                        tableRow.setBackgroundColor(Color.parseColor("#ffffb6"));
                        last = "ARQ";
                    }

                    break;
                case "DEF":
                    if(last.equals("DEF")){
                        last = "";
                        tableRow.setBackgroundColor(Color.parseColor("#a0ef9f"));
                    }else{
                        tableRow.setBackgroundColor(Color.parseColor("#b1ffb1"));
                        last = "DEF";
                    }

                    break;
                case "MED":
                    if(last.equals("MED")){
                        last = "";
                        tableRow.setBackgroundColor(Color.parseColor("#b1b0ff"));
                    }else{
                        tableRow.setBackgroundColor(Color.parseColor("#a09fef"));
                        last = "MED";
                    }

                    break;
                case "ATA":
                    if(last.equals("ATA")){
                        last = "";
                        tableRow.setBackgroundColor(Color.parseColor("#ee9f9f"));
                    }else{
                        tableRow.setBackgroundColor(Color.parseColor("#ffb1b0"));
                        last = "ATA";
                    }
                    break;


            }



            TextView textVieww = new TextView(getActivity());
            textVieww.setText(player.getPlayer().getName());
            textVieww.setTextSize(12);

            LinearLayout constraintLayout = new LinearLayout(getActivity());
            constraintLayout.setPadding(5,0,0,0);
            constraintLayout.addView(textVieww);
            for(String icon : player.getPlayer().getIcons()){
                if(icon.equals("transferable")){
                    ImageView down = new ImageView(getActivity());
                    LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    paramsImage.height = 25;
                    paramsImage.width = 25;
                    paramsImage.setMargins(5,5,2,0);
                    down.setLayoutParams(paramsImage);
                    VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_share_square_solid, down);
                    VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                    path.setFillColor(Color.parseColor("#05af02"));
                    constraintLayout.addView(down);
                }
                else if(icon.equals("retiring")){
                    ImageView down = new ImageView(getActivity());
                    LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    paramsImage.height = 25;
                    paramsImage.width = 25;
                    paramsImage.setMargins(5,5,2,0);
                    down.setLayoutParams(paramsImage);
                    VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_user_times, down);
                    VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                    path.setFillColor(Color.parseColor("#ff0100"));
                    constraintLayout.addView(down);
                }
                else if(icon.equals("yellow_cards")){
                    ImageView down = new ImageView(getActivity());
                    LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    paramsImage.height = 25;
                    paramsImage.width = 25;
                    paramsImage.setMargins(5,5,2,0);
                    down.setLayoutParams(paramsImage);
                    VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, down);
                    VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                    path.setFillColor(Color.parseColor("#ffff07"));
                    constraintLayout.addView(down);
                }else if(icon.equals("red_card")){
                    ImageView down = new ImageView(getActivity());
                    LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    paramsImage.height = 25;
                    paramsImage.width = 25;
                    paramsImage.setMargins(5,5,2,0);
                    down.setLayoutParams(paramsImage);
                    VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, down);
                    VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                    path.setFillColor(Color.parseColor("#ff0100"));
                    constraintLayout.addView(down);
                }else if(icon.equals("injured")){
                    ImageView down = new ImageView(getActivity());
                    LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    paramsImage.height = 25;
                    paramsImage.width = 25;
                    paramsImage.setMargins(5,5,2,0);
                    down.setLayoutParams(paramsImage);
                    VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_medkit, down);
                    VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                    path.setFillColor(Color.parseColor("#ff0100"));
                    constraintLayout.addView(down);
/*
                    TextView injuredTime = new TextView(getContext());
                    injuredTime.setText("" + player.getRecovery());
                    injuredTime.setTextSize(10);
                    injuredTime.setTextColor(Color.parseColor("#f00000"));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    params.setMargins(5,0,2,0);
                    injuredTime.setLayoutParams(params);


                    constraintLayout.addView(injuredTime); */

                }else if(icon.equals("healed")){
                    ImageView down = new ImageView(getActivity());
                    LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    paramsImage.height = 25;
                    paramsImage.width = 25;
                    paramsImage.setMargins(5,5,2,0);
                    down.setLayoutParams(paramsImage);
                    VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_plus_circle, down);
                    VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                    path.setFillColor(Color.parseColor("#05af02"));
                    constraintLayout.addView(down);
                }else if(icon.equals("upgraded")){
                    ImageView down = new ImageView(getActivity());
                    LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    paramsImage.height = 25;
                    paramsImage.width = 25;
                    paramsImage.setMargins(5,5,2,0);
                    down.setLayoutParams(paramsImage);
                    VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_circle_up, down);
                    VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                    path.setFillColor(Color.parseColor("#05af02"));
                    constraintLayout.addView(down);
                }else if(icon.equals("tired")){
                    ImageView down = new ImageView(getActivity());
                    LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                    paramsImage.height = 25;
                    paramsImage.width = 25;
                    paramsImage.setMargins(5,5,2,0);
                    down.setLayoutParams(paramsImage);
                    VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_down, down);
                    VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                    path.setFillColor(Color.parseColor("#ff0100"));
                    constraintLayout.addView(down);
                }




            }

            tableRow.setTag(player.getPlayer().getPosition());
            tableRow.addView(constraintLayout);
            TextView textView2 = new TextView(getActivity());
            textView2.setText(player.getPlayer().getPosition());
            textView2.setTextSize(12);
            textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(textView2);


            TextView textView4 = new TextView(getActivity());
            textView4.setText(player.getPlayer().getAverage()+ "");
            textView4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView4.setTextSize(12);
            tableRow.addView(textView4);





            TextView textView43 = new TextView(getActivity());
            if(player.getOffer_team() != 0) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    textView43.setText(decimalFormat.format(player.getOffer_value()) + " $ (" + getTeamName(player.getOffer_team()) + ")");
                }else{
                    textView43.setText(decimalFormat.format(player.getOffer_value()) + " $ (" + getTeamName(player.getOffer_team()) + ")");
                }

            }else{
                textView43.setText(getResources().getString(R.string.no_offer));
            }
            textView43.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView43.setTextSize(12);
            tableRow.addView(textView43);


            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){



                TextView textView5 = new TextView(getActivity());
                if(player.getPlayer().getTeam().equals("")){
                    textView5.setText(getResources().getString(R.string.free_player));
                }else{
                    textView5.setText(player.getPlayer().getTeam()+ "");
                }

                textView5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView5.setTextSize(12);
                tableRow.addView(textView5);

                SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String strDate = sm.format(player.getCloses_at() * 1000);

                TextView textView42 = new TextView(getActivity());
                textView42.setText(strDate+ "");
                textView42.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView42.setTextSize(12);
                tableRow.addView(textView42);

                TextView textView41 = new TextView(getActivity());
                textView41.setText(decimalFormat.format(player.getValue())+ " $");
                textView41.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView41.setTextSize(12);
                tableRow.addView(textView41);



/*
                TextView textView6 = new TextView(getActivity());
                textView6.setText(player.getDefending()+ "");
                textView6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView6.setTextSize(11);
                tableRow.addView(textView6);

                TextView textView7 = new TextView(getActivity());
                textView7.setText(player.getDribbling()+ "");
                textView7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView7.setTextSize(11);
                tableRow.addView(textView7);

                TextView textView8 = new TextView(getActivity());
                textView8.setText(player.getHeading()+ "");
                textView8.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView8.setTextSize(11);
                tableRow.addView(textView8);

                TextView textView9 = new TextView(getActivity());
                textView9.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView9.setText(player.getJumping()+ "");
                textView9.setTextSize(11);
                tableRow.addView(textView9);

                TextView textView10 = new TextView(getActivity());
                textView10.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView10.setText(player.getPassing()+ "");
                textView10.setTextSize(11);
                tableRow.addView(textView10);

                TextView textView11 = new TextView(getActivity());
                textView11.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView11.setText(player.getPrecision()+ "");
                textView11.setTextSize(11);
                tableRow.addView(textView11);

                TextView textView12 = new TextView(getActivity());
                textView12.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView12.setText(player.getSpeed()+ "");
                textView12.setTextSize(11);
                tableRow.addView(textView12);

                TextView textView13 = new TextView(getActivity());
                textView13.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView13.setText(player.getStrength()+ "");
                textView13.setTextSize(11);
                tableRow.addView(textView13);

                TextView textView14 = new TextView(getActivity());
                textView14.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView14.setText(player.getTackling()+ "");
                textView14.setTextSize(11);
                tableRow.addView(textView14);

                TextView textView15 = new TextView(getActivity());
                textView15.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView15.setText(player.getExperience()+ "");
                textView15.setTextSize(11);
                tableRow.addView(textView15);
*/
            }
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  ((MainActivity)                    getActivity()).changeToFragmentPlayer(player);
                    getPlayer(player);
                }
            });
            table.addView(tableRow);

        }

    }
    private void removeAllRows(){
        List<View> views = new ArrayList<>();

        for(int i=0; i< table.getChildCount(); i++){
            if(!table.getChildAt(i).getTag().equals("header")){
                views.add(table.getChildAt(i));
            }
        }
        for(View view : views){
            table.removeView(view);
        }

    }
    public void getMarket(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
     //   if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_market_players) , getActivity());
        dialog.show();

       // }
        SvcApi svc;
        svc = Svc.initAuth(getActivity());
        Call<getMarket> call = svc.getMarket(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getMarket>() {
            @Override
            public void onResponse(Call<getMarket> call, Response<getMarket> response) {
                final getMarket user = response.body();
                final Response<getMarket> responsefinal = response;

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
                                    market = responsefinal.body();
                                    players.addAll(market.getMarket());
                                  //      orderPlayersByClosesOffer();
                                  //  ShowHideColumns();
                                    currentPlayers = players;
                                    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                                        loadRecyclerMarket(currentPlayers);
                                    }else{
                                        ShowHideColumns();
                                    }

                                }
                            }
                        });
                    }
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
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getMarket> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    public void getPlayer(final MarketObject player){

        final long player_id_f = player.getPlayer().getId();

        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_data_from)+ " " + player.getPlayer().getName() , getActivity());
        dialog.show();

        SvcApi svc;
        svc = Svc.initAuth(getActivity());
        Call<getPlayer> call = svc.getPlayer(player_id_f,Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getPlayer>() {
            @Override
            public void onResponse(Call<getPlayer> call, Response<getPlayer> response) {

                final Response<getPlayer> responsefinal = response;

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
                                Player player1 = responsefinal.body().getPlayer();
                                Log.e("Response player", player1.toString());
                                if(player1.getTeam_id() != user.getUser().getTeam().getId()) {
                                    Log.e("GET PLAYER", "!!!!!!!  DIFERNTE");
                                    FragmentTransaction ft =
                                            getFragmentManager().beginTransaction();
                                    playerDialog = new PlayerDialog(getActivity(), player1, user, FragmentMarket.this,
                                            true, player);
                                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    playerDialog.show();
                                }else{
                                    FragmentTransaction ft =
                                            getFragmentManager().beginTransaction();
                                    playerDialog = new PlayerDialog(getActivity(), player1, user, FragmentMarket.this, false, player);
                                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    playerDialog.show();
                                }
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
                        Toast.makeText(getActivity(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getPlayer> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void orderPlayersByClosesOffer(){

        List<MarketObject> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            long maxNumber = 0;
            MarketObject maxPlayer  = new MarketObject();

            for (MarketObject player : players) {
                if(maxNumber <= player.getCloses_at()){
                    maxNumber = player.getCloses_at();
                    maxPlayer = player;
                }
            }
            ordered.add(maxPlayer);
            players.remove(maxPlayer);
        }
        players = ordered;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
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

}