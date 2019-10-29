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
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
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
        import com.androidsrc.futbolin.utils.dialogs.PlayerDialog;
        import com.devs.vectorchildfinder.VectorChildFinder;
        import com.devs.vectorchildfinder.VectorDrawableCompat;

        import java.text.DecimalFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.List;

        import androidx.appcompat.app.AppCompatDialog;
        import androidx.appcompat.widget.LinearLayoutCompat;
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
public class FragmentMarketFollowing extends Fragment implements PlayerDialog.DialogClickListener{
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
    View v;
    TableLayout table;
    List<MarketObject> players = new ArrayList<>();
    LinearLayout buttonsLayout;
    Button previousPage, firstButtonPage, secondButtonPage, thirdButtonPage, fourthButtonPage, fifthButtonPage, nextPage;
    PlayerDialog playerDialog;
    FragmentMarketFollowing fragmentMarketTransactions;
    int current_page = 1;
    DecimalFormat decimalFormat;
    String pattern;
    DatabaseManager db;
    TableRow headerRow;
    FragmentMarketFollowing fragmentFollowing;
    Button backButton, buttonShowHide;

    public FragmentMarketFollowing() {
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
    public static FragmentMarketFollowing newInstance(getUser user, String param2) {
        FragmentMarketFollowing fragment = new FragmentMarketFollowing();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_market_following, container, false);
        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);

        fragmentFollowing = this;
        if(mParam2 != null && !mParam2.contains("")){
            current_page = Integer.parseInt(mParam2);
        }
        table = v.findViewById(R.id.fragment_market_following_players_table);
        headerRow = v.findViewById(R.id.fragment_players_table_header_row);
        backButton = v.findViewById(R.id.fragment_market_following_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeToMarketFragment();
            }
        });
        buttonShowHide  = v.findViewById(R.id.fragment_market_following_show_hide);
        buttonShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                }else{
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                ((MainActivity) getActivity()).setChangingScreenOrientation(true, "FragmentMarket");
                ShowHideColumns();
            }
        });
        getMarketFollowing();
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
        if(playerDialog != null && playerDialog.isShowing()){
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

    void ShowHideColumns(){

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


    private void buildRows(){

        String last = "";

        for(final MarketObject player : players){

            TableRow tableRow = new TableRow(getActivity());

            tableRow.setTag(player.getPlayer().getId());
            tableRow.setPadding(1,25,1,25);

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
            textVieww.setTextSize(11);

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
            textView2.setTextSize(11);
            textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tableRow.addView(textView2);


            TextView textView4 = new TextView(getActivity());
            textView4.setText(player.getPlayer().getAverage()+ "");
            textView4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView4.setTextSize(11);
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
            textView43.setTextSize(11);
            tableRow.addView(textView43);


            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){



                TextView textView5 = new TextView(getActivity());
                if(player.getPlayer().getTeam().equals("")){
                    textView5.setText(getResources().getString(R.string.free_player));
                }else{
                    textView5.setText(player.getPlayer().getTeam()+ "");
                }

                textView5.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView5.setTextSize(11);
                tableRow.addView(textView5);

                SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String strDate = sm.format(player.getCloses_at() * 1000);

                TextView textView42 = new TextView(getActivity());
                textView42.setText(strDate+ "");
                textView42.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView42.setTextSize(11);
                tableRow.addView(textView42);

                TextView textView41 = new TextView(getActivity());
                textView41.setText(decimalFormat.format(player.getValue())+ " $");
                textView41.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView41.setTextSize(11);
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
    public void getPlayer(final MarketObject player){

        final long player_id_f = player.getPlayer().getId();

        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_data_from) + " " + player.getPlayer().getName(), getActivity());
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
                                    playerDialog = new PlayerDialog(getActivity(), player1, user, fragmentFollowing, true, player);
                                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    playerDialog.show();
                                }else{
                                    Log.e("GET PLAYER", "!!!!!!!  MISMO ID" + player1.getTeam_id());
                                    playerDialog = new PlayerDialog(getActivity(), player1, user, fragmentFollowing, false, player);
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
    public void getMarketFollowing() {
        this.current_page = current_page;
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_finances_from_market) , getActivity());
        dialog.show();

        SvcApi svc;
        svc = Svc.initAuth(getActivity());
        Call<getMarket> call = svc.getMarketFollowing(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getMarket>() {
            @Override
            public void onResponse(Call<getMarket> call, Response<getMarket> response) {
                final getMarket user = response.body();
                final Response<getMarket> responsefinal = response;

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("MeTransActions", "Success, MeTransActions = " + responsefinal.body().toString());
                            if (responsefinal.code() == 400) {

                            } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {


                                players.addAll(responsefinal.body().getMarket());
                                //      orderPlayersByClosesOffer();
                                ShowHideColumns();

                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if (error.contains("live_match") && error.contains("Broadcasting live match")) {
                            Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                            startActivity(mainActivity);
                            getActivity().finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (responsefinal.code() == 500 || responsefinal.code() == 503) {
                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getMarket> call, Throwable t) {
                t.printStackTrace();
            }
        });

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