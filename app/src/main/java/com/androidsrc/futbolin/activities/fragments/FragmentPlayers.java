package com.androidsrc.futbolin.activities.fragments;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.utils.dialogs.PlayerDialog;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPlayers extends Fragment implements PlayerDialog.DialogClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String PLAYER_ID = "player_id";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    getUser user;
    ImageView imageShield;
    TableLayout  table;
    TableRow headerRow;
    private OnFragmentInteractionListener mListener;
    Button buttonShowHide;
    List<Player> players = new ArrayList<>();
    Spinner spinner;

    List<String> spinnerOptions;
    List<Boolean> inverseOnclick = Arrays.asList(false, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
    int spinnerSelected = 0;
    PlayerDialog playerDialog;
    FragmentPlayers fragmentPlayers;

    long player_id;
    boolean playerDialogShown = false;
    public FragmentPlayers() {
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
    public static FragmentPlayers newInstance(getUser user, long player_id) {
        FragmentPlayers fragment = new FragmentPlayers();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        args.putLong(PLAYER_ID, player_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (com.androidsrc.futbolin.communications.http.auth.get.getUser) getArguments().getSerializable(ARG_USER);
            player_id = getArguments().getLong(PLAYER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        spinnerOptions = Arrays.asList(getResources().getString(R.string.all),
                getResources().getString(R.string.goalkeepers), getResources().getString(R.string.defensors),
                getResources().getString(R.string.midfielder), getResources().getString(R.string.forwards));
        fragmentPlayers = this;
        players.addAll(user.getUser().getTeam().getFormation_objects());
        orderPlayersByNumber();

        View v =  inflater.inflate(R.layout.fragment_players, container, false);
        buttonShowHide = v.findViewById(R.id.fragment_players_button_show_hide);
        table = v.findViewById(R.id.fragment_players_table);
        headerRow = v.findViewById(R.id.fragment_players_table_header_row);
        for(int i = 0; i < headerRow.getChildCount(); i++){
            if( ((String)(headerRow.getChildAt(i)).getTag()).contains("header")){
                View view = headerRow.getChildAt(i);
                if(view instanceof TextView){
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((MainActivity) getActivity()).onClickFragmentPlayer(view);
                        }
                    });
                }
            }
        }
        spinner = v.findViewById(R.id.fragment_players_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinnerOptions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                }else{
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                ((MainActivity) getActivity()).setChangingScreenOrientation(true, "FragmentPlayers");
                ShowHideColumns();
            }
        });

        ShowHideColumns();

        if(player_id > 0 && !playerDialogShown){
            Player myPlayer = null;
            for(Player player : players){
                if(player_id == player.getId()){
                    myPlayer = player;
                }
            }
            if(myPlayer !=null){
                playerDialog = new PlayerDialog(getActivity(), myPlayer, user, fragmentPlayers, false, null);
                playerDialogShown = true;
            }
        }

        return v;
    }
    void spinnerFilter(int position){
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

    void ShowHideColumns(){

        List<TextView> columns = new ArrayList<>();
        if(columns.isEmpty()){
            for(int i=0; i < headerRow.getChildCount(); i++){
                TextView textView = (TextView) headerRow.getChildAt(i);

                if(textView.getTag() != null && textView.getTag().equals("hide")){
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
        System.out.println("onClick Fragment view "+ (view.getTag()));
        if(view.getTag().equals("header_number")){
            removeAllRows();
            if(inverseOnclick.get(0)) {
                orderPlayersByNumber();
            }else{
                orderPlayersByNumberInverse();
            }
            inverseOnclick.set(0,!inverseOnclick.get(0));
            ShowHideColumns();
        }else if(view.getTag().equals("header_name")){
            if(inverseOnclick.get(1)) {
                orderPlayersByName();
            }else{
                orderPlayersByNameInverse();
            }
            inverseOnclick.set(1,!inverseOnclick.get(1));
            ShowHideColumns();
        }else if(view.getTag().equals("header_age")){
            removeAllRows();
            if(inverseOnclick.get(2)) {
                orderPlayersByAge();
            }else{
                orderPlayersByAgeInverse();
            }
            inverseOnclick.set(2,!inverseOnclick.get(2));
            ShowHideColumns();
        }else if(view.getTag().equals("header_position")){
            removeAllRows();
            if(inverseOnclick.get(3)) {
                orderPlayersByPosition();
            }else{
                orderPlayersByPositionInverse();
            }
            inverseOnclick.set(3,!inverseOnclick.get(3));
            ShowHideColumns();
        }else if(view.getTag().equals("header_stamina")){
            removeAllRows();
            if(inverseOnclick.get(4)) {
                orderPlayersByEnergy();
            }else{
                orderPlayersByEnergyInverse();
            }
            inverseOnclick.set(4,!inverseOnclick.get(4));
            ShowHideColumns();
        }else if(view.getTag().equals("header_average")){
            removeAllRows();
            if(inverseOnclick.get(5)) {
                orderPlayersByAverage();
            }else{
                orderPlayersByAverageInverse();
            }
            inverseOnclick.set(5,!inverseOnclick.get(5));
            ShowHideColumns();
        }else if(view.getTag().equals("")){

        }else if(view.getTag().equals("")){

        }else if(view.getTag().equals("")){

        }else if(view.getTag().equals("")){

        }
        spinnerFilter(spinnerSelected);
    }

    @Override
    public void onButtonClick() {
        if(playerDialog != null && playerDialog.isShowing()) {
            playerDialog.dismiss();
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
    private void orderPlayersByEnergy(){

        List<Player> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            int minNumber = 1000;
            Player minPlayer  = new Player();

            for (Player player : players) {
                if(minNumber >= player.getStamina()){
                    minNumber = player.getStamina();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByEnergyInverse(){

        List<Player> ordered = new ArrayList<>();
        Log.e("inverse bf pl", players.size() + "");
        while(!players.isEmpty()) {
            int maxNumber = 0;
            Player minPlayer  = new Player();

            for (Player player : players) {
                if(maxNumber <= player.getStamina()){
                    maxNumber = player.getStamina();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }

        players = ordered;
        Log.e("inverse after pl", players.size() + "");
        Log.e("inverse ordn", ordered.size() + "");
    }
    private void orderPlayersByAverage(){

        List<Player> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            int minNumber = 1000;
            Player minPlayer  = new Player();

            for (Player player : players) {
                if(minNumber >= player.getAverage()){
                    minNumber = player.getAverage();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByAverageInverse(){

        List<Player> ordered = new ArrayList<>();
        Log.e("inverse bf pl", players.size() + "");
        while(!players.isEmpty()) {
            int maxNumber = 0;
            Player minPlayer  = new Player();

            for (Player player : players) {
                if(maxNumber <= player.getAverage()){
                    maxNumber = player.getAverage();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }

        players = ordered;
        Log.e("inverse after pl", players.size() + "");
        Log.e("inverse ordn", ordered.size() + "");
    }
    private void orderPlayersByNumber(){

        List<Player> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            int minNumber = 1000;
            Player minPlayer  = new Player();

            for (Player player : players) {
                if(minNumber >= player.getNumber()){
                    minNumber = player.getNumber();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByNumberInverse(){

        List<Player> ordered = new ArrayList<>();
        Log.e("inverse bf pl", players.size() + "");
        while(!players.isEmpty()) {
            int maxNumber = 0;
            Player minPlayer  = new Player();

            for (Player player : players) {
                if(maxNumber <= player.getNumber()){
                    maxNumber = player.getNumber();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }

        players = ordered;
        Log.e("inverse after pl", players.size() + "");
        Log.e("inverse ordn", ordered.size() + "");
    }
    private void orderPlayersByName(){
            Log.e("order", "Name");
            Collections.sort(players, new Comparator<Player>() {
                @Override
                public int compare(final Player object1, final Player object2) {
                    return object2.getShort_name().compareTo(object1.getShort_name());
                }
            });

    }
    private void orderPlayersByNameInverse(){

        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(final Player object1, final Player object2) {

                return object1.getShort_name().compareTo(object2.getShort_name());
            }
        });
    }
    private void orderPlayersByPosition(){
        Log.e("order", "Position");
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(final Player object1, final Player object2) {
                return object2.getPosition().compareTo(object1.getPosition());
            }
        });

    }
    private void orderPlayersByPositionInverse(){

        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(final Player object1, final Player object2) {

                return object1.getPosition().compareTo(object2.getPosition());
            }
        });
    }
    private void orderPlayersByAge(){

        List<Player> ordered = new ArrayList<>();
        while(!players.isEmpty()) {
            int minNumber = 1000;
            Player minPlayer  = new Player();

            for (Player player : players) {
                if(minNumber >= player.getAge()){
                    minNumber = player.getAge();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }
        players = ordered;
    }
    private void orderPlayersByAgeInverse(){

        List<Player> ordered = new ArrayList<>();
        Log.e("inverse bf pl", players.size() + "");
        while(!players.isEmpty()) {
            int maxNumber = 0;
            Player minPlayer  = new Player();

            for (Player player : players) {
                if(maxNumber <= player.getAge()){
                    maxNumber = player.getAge();
                    minPlayer = player;
                }
            }
            ordered.add(minPlayer);
            players.remove(minPlayer);
        }

        players = ordered;
        Log.e("inverse after pl", players.size() + "");
        Log.e("inverse ordn", ordered.size() + "");
    }
    private void buildRows(){

        String last = "";

        for(final Player player : players){

            TableRow tableRow = new TableRow(getActivity());

            tableRow.setTag(player.getPosition());
            tableRow.setPadding(1,30,1,30);

            switch (player.getPosition()){
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


            TextView textView = new TextView(getActivity());
            textView.setText(player.getNumber() + "");
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(12);
            tableRow.addView(textView);

            TextView textVieww = new TextView(getActivity());
            textVieww.setText(player.getFirst_name() + " " + player.getLast_name());
            textVieww.setTextSize(12);

            LinearLayout constraintLayout = new LinearLayout(getActivity());
            constraintLayout.addView(textVieww);

            if(player.getLast_upgrade() == null){

        }else{
          ImageView up = new ImageView(getActivity());
          LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
          paramsImage.height = 25;
          paramsImage.width = 25;
          paramsImage.setMargins(5,5,2,0);
          up.setLayoutParams(paramsImage);
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_circle_up, up);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#02ac01"));
            constraintLayout.addView(up);
        }

            if(player.getStamina() < 40){
                ImageView down = new ImageView(getActivity());
                LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                paramsImage.height = 25;
                paramsImage.width = 25;
                paramsImage.setMargins(5,5,2,0);
                down.setLayoutParams(paramsImage);
                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_down, down);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#f00000"));
                constraintLayout.addView(down);
            }
            if(player.getCards_count() == 2){
                ImageView yellowCard = new ImageView(getActivity());
                LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                paramsImage.height = 25;
                paramsImage.width = 25;
                paramsImage.setMargins(5,5,2,0);
                yellowCard.setLayoutParams(paramsImage);
                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, yellowCard);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#F8F119"));
                constraintLayout.addView(yellowCard);
            }
            if(player.isSuspended()){
                ImageView redCard = new ImageView(getActivity());
                LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                paramsImage.height = 25;
                paramsImage.width = 25;
                paramsImage.setMargins(5,5,2,0);
                redCard.setLayoutParams(paramsImage);
                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, redCard);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#f00000"));
                constraintLayout.addView(redCard);
            }
            System.out.println(player.toString());
            if(player.getInjury_id() != 0){
                ImageView medkit = new ImageView(getActivity());
                LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                paramsImage.height = 25;
                paramsImage.width = 25;
                paramsImage.setMargins(5,5,2,0);
                medkit.setLayoutParams(paramsImage);

                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_medkit, medkit);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#f00000"));

                TextView injuredTime = new TextView(getContext());
                injuredTime.setText("" + player.getRecovery());
                injuredTime.setTextSize(10);
                injuredTime.setTextColor(Color.parseColor("#f00000"));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                params.setMargins(5,0,2,0);
                injuredTime.setLayoutParams(params);

                constraintLayout.addView(medkit);
                constraintLayout.addView(injuredTime);

            }
            if(player.isRetiring()){
                ImageView retired = new ImageView(getActivity());
                LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                paramsImage.height = 25;
                paramsImage.width = 25;
                paramsImage.setMargins(5,5,2,0);
                retired.setLayoutParams(paramsImage);
                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_user_times, retired);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#f00000"));
                constraintLayout.addView(retired);
            }
            if(player.isHealed()){
                ImageView healted = new ImageView(getActivity());
                LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                paramsImage.height = 25;
                paramsImage.width = 25;
                paramsImage.setMargins(5,5,2,0);
                healted.setLayoutParams(paramsImage);
                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_plus_circle, healted);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#02ac01"));
                constraintLayout.addView(healted);
            }
            if(player.isTransferable()){
                ImageView healted = new ImageView(getActivity());
                LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
                paramsImage.height = 25;
                paramsImage.width = 25;
                paramsImage.setMargins(5,5,2,0);
                healted.setLayoutParams(paramsImage);
                VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_share_square_solid, healted);
                VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
                path.setFillColor(Color.parseColor("#02ac01"));
                constraintLayout.addView(healted);
            }

            tableRow.addView(constraintLayout);
            TextView textView1 = new TextView(getActivity());
            textView1.setText(player.getAge() + "");
            textView1.setTextSize(12);
            textView1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(textView1);

            TextView textView2 = new TextView(getActivity());
            textView2.setText(player.getPosition());
            textView2.setTextSize(12);
            textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(textView2);

            TextView textView3 = new TextView(getActivity());
            textView3.setText(player.getStamina()+ "");
            textView3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView3.setTextSize(12);
            tableRow.addView(textView3);

            TextView textView4 = new TextView(getActivity());
            textView4.setText(player.getAverage()+ "");
            textView4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView4.setTextSize(12);
            tableRow.addView(textView4);

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                TextView textView5 = new TextView(getActivity());
                textView5.setText(player.getGoalkeeping()+ "");
                textView5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView5.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getGoalkeeping() != 0){
                    textView5.setTextColor(Color.parseColor("#3c763d"));
                    textView5.setText(Html.fromHtml(player.getGoalkeeping() + "<sub><small>+" + player.getLast_upgrade().getGoalkeeping() + "</small></sub>"));
                }
                tableRow.addView(textView5);



                TextView textView6 = new TextView(getActivity());
                textView6.setText(player.getDefending()+ "");
                textView6.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView6.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getDefending() != 0){
                    textView6.setTextColor(Color.parseColor("#3c763d"));
                    textView6.setText(Html.fromHtml(player.getDefending() + "<sub><small>+" + player.getLast_upgrade().getDefending() + "</small></sub>"));
                }
                tableRow.addView(textView6);

                TextView textView7 = new TextView(getActivity());
                textView7.setText(player.getDribbling()+ "");
                textView7.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView7.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getDribbling() != 0){
                    textView7.setTextColor(Color.parseColor("#3c763d"));
                    textView7.setText(Html.fromHtml(player.getDribbling() + "<sub><small>+" + player.getLast_upgrade().getDribbling() + "</small></sub>"));
                }
                tableRow.addView(textView7);

                TextView textView8 = new TextView(getActivity());
                textView8.setText(player.getHeading()+ "");
                textView8.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView8.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getHeading() != 0){
                    textView8.setTextColor(Color.parseColor("#3c763d"));
                    textView8.setText(Html.fromHtml(player.getHeading() + "<sub><small>+" + player.getLast_upgrade().getHeading() + "</small></sub>"));
                }
                tableRow.addView(textView8);

                TextView textView9 = new TextView(getActivity());
                textView9.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView9.setText(player.getJumping()+ "");
                textView9.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getJumping() != 0){
                    textView9.setTextColor(Color.parseColor("#3c763d"));
                    textView9.setText(Html.fromHtml(player.getJumping() + "<sub><small>+" + player.getLast_upgrade().getJumping() + "</small></sub>"));
                }
                tableRow.addView(textView9);

                TextView textView10 = new TextView(getActivity());
                textView10.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView10.setText(player.getPassing()+ "");
                textView10.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getPassing() != 0){
                    textView10.setTextColor(Color.parseColor("#3c763d"));
                    textView10.setText(Html.fromHtml(player.getPassing() + "<sub><small>+" + player.getLast_upgrade().getPassing() + "</small></sub>"));
                }
                tableRow.addView(textView10);

                TextView textView11 = new TextView(getActivity());
                textView11.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView11.setText(player.getPrecision()+ "");
                textView11.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getPrecision() != 0){
                    textView11.setTextColor(Color.parseColor("#3c763d"));
                    textView11.setText(Html.fromHtml(player.getPrecision() + "<sub><small>+" + player.getLast_upgrade().getPrecision() + "</small></sub>"));
                }
                tableRow.addView(textView11);

                TextView textView12 = new TextView(getActivity());
                textView12.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView12.setText(player.getSpeed()+ "");
                textView12.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getSpeed() != 0){
                    textView12.setTextColor(Color.parseColor("#3c763d"));
                    textView12.setText(Html.fromHtml(player.getSpeed() + "<sub><small>+" + player.getLast_upgrade().getSpeed() + "</small></sub>"));
                }
                tableRow.addView(textView12);

                TextView textView13 = new TextView(getActivity());
                textView13.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView13.setText(player.getStrength()+ "");
                textView13.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getStrength() != 0){
                    textView13.setTextColor(Color.parseColor("#3c763d"));
                    textView13.setText(Html.fromHtml(player.getStrength() + "<sub><small>+" + player.getLast_upgrade().getStrength() + "</small></sub>"));
                }
                tableRow.addView(textView13);

                TextView textView14 = new TextView(getActivity());
                textView14.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView14.setText(player.getTackling()+ "");
                textView14.setTextSize(12);
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getTackling() != 0){
                    textView14.setTextColor(Color.parseColor("#3c763d"));
                    textView14.setText(Html.fromHtml(player.getTackling() + "<sub><small>+" + player.getLast_upgrade().getTackling() + "</small></sub>"));
                }
                tableRow.addView(textView14);

                TextView textView15 = new TextView(getActivity());
                textView15.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView15.setText(player.getExperience()+ "");
                if(player.getLast_upgrade() != null && player.getLast_upgrade().getExperience() != 0){
                    textView15.setTextColor(Color.parseColor("#3c763d"));
                    textView15.setText(Html.fromHtml(player.getExperience() + "<sub><small>+" + player.getLast_upgrade().getExperience() + "</small></sub>"));
                }
                textView15.setTextSize(12);
                tableRow.addView(textView15);

            }
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  ((MainActivity)                    getActivity()).changeToFragmentPlayer(player);
                    playerDialog = new PlayerDialog(getActivity(), player, user, fragmentPlayers, false, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    playerDialog.show();
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

}