package com.androidsrc.futbolin.activities.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuy;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuyResponse;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.dialogs.MessageDialog;
import com.androidsrc.futbolin.utils.dialogs.TwoButtonsDialog;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentPlayer extends Fragment implements TwoButtonsDialog.DialogTwoButtonsClickListener, MessageDialog.DialogClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_PLAYER= "player";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private Player player;
    getUser user;
    ImageView imageShield;
    private OnFragmentInteractionListener mListener;

    TextView injuredTime, playerName, playerInfo, playerEnergia, playerArquero, playerDefensa, playerGambeta, playerCabeceo, playerSalto, playerPase, playerPrecision, playerVelocidad, playerFuerza, playerQuite, playerExp;
    ImageView up, down, medkit, healted, retired, yellowCard, redCard;
    Button healthButton;
    TwoButtonsDialog dialogConfirmBuying;
    FragmentPlayer fragmentPlayer;
    SvcApi svcAth;
    MessageDialog messageDialog;

    public FragmentPlayer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @param player Parameter 2.
     * @return A new instance of fragment FragmentTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPlayer newInstance(getUser user, Player player) {
        FragmentPlayer fragment = new FragmentPlayer();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        args.putSerializable(ARG_PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (com.androidsrc.futbolin.communications.http.auth.get.getUser) getArguments().getSerializable(ARG_USER);
            player =(Player) getArguments().getSerializable(ARG_PLAYER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        fragmentPlayer = this;
        playerName = v.findViewById(R.id.fragment_player_name);
        playerInfo = v.findViewById(R.id.fragment_player_info);
        playerEnergia = v.findViewById(R.id.fragment_player_stamina);
         playerArquero = v.findViewById(R.id.fragment_player_goalkeeping);
         playerDefensa = v.findViewById(R.id.fragment_player_defense);
         playerGambeta= v.findViewById(R.id.fragment_player_dribbling);
         playerCabeceo= v.findViewById(R.id.fragment_player_cab);
         playerSalto= v.findViewById(R.id.fragment_player_jump);
         playerPase= v.findViewById(R.id.fragment_player_pass);
         playerPrecision= v.findViewById(R.id.fragment_player_pres);
         playerVelocidad= v.findViewById(R.id.fragment_player_speed);
         playerFuerza= v.findViewById(R.id.fragment_player_strength);
         playerQuite= v.findViewById(R.id.fragment_player_quite);
         playerExp= v.findViewById(R.id.fragment_player_exp);
        up = v.findViewById(R.id.fragment_player_up_image);
        down = v.findViewById(R.id.fragment_player_down_image);
        medkit = v.findViewById(R.id.fragment_player_medkit_image);
        retired = v.findViewById(R.id.fragment_player_retired_image);
        healted = v.findViewById(R.id.fragment_player_healted_image);
        yellowCard = v.findViewById(R.id.fragment_player_yellowcard_image);
        redCard = v.findViewById(R.id.fragment_player_redcard_image);
        injuredTime = v.findViewById(R.id.fragment_player_medkit_text);
        healthButton = v.findViewById(R.id.fragment_player_health_button);

        buildLayout();

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
    public void onButtonClick(boolean confirm, int type, Object extra) {
        if(confirm){
            postShoppingBuyHealth();
        }else{
            dialogConfirmBuying.dismiss();
        }
    }

    @Override
    public void onMessageDialogButtonClick() {
        if(messageDialog != null && messageDialog.isShowing()){
            messageDialog.dismiss();
        }
        ((MainActivity)getActivity()).changeToSplashScreen();
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
    void postShoppingBuyHealth(){

        svcAth = Svc.initAuth(getContext());
        PostShoppingBuy postShoppingBuy = new PostShoppingBuy();
        postShoppingBuy.setDevice_id(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        postShoppingBuy.setId(4);
        postShoppingBuy.setPlayer_id(player.getId());

        Call<PostShoppingBuyResponse> call = svcAth.postShoppingBuy(postShoppingBuy);
        call.enqueue(new Callback<PostShoppingBuyResponse>() {
            @Override
            public void onResponse(Call<PostShoppingBuyResponse> call, Response<PostShoppingBuyResponse> response) {

                final Response<PostShoppingBuyResponse> responsefinal = response;
                Log.e("getShoppingItems", responsefinal.toString());
                final PostShoppingBuyResponse getShoppingItems = response.body();
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(responsefinal.code() == 400){
                                Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                                // SUPONIENDO QUE ANTERIORMENTE HA HECHO LOGOUT, NO TIENE TOKEN !
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                user.getUser().setCredits(responsefinal.body().getCredits());
                                ((MainActivity)getActivity()).setUser(user);
                                dialogConfirmBuying.dismiss();
                                ((MainActivity)getActivity()).getMe();
                                messageDialog = new MessageDialog(getActivity(),
                                        getResources().getString(R.string.heal_player), getResources().getString(R.string.player_healed_sucessfully), fragmentPlayer);
                                messageDialog.show();
                            }
                        }
                    });
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
                        Toast.makeText(getContext(),
                                getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PostShoppingBuyResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    public void buildLayout(){

       /* if(player.getLast_upgrade() == null){
            up.setVisibility(View.GONE);
        }else{
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_circle_up, up);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#080000"));
        } */
        up.setVisibility(View.GONE);
        if(player.getStamina() < 40){
            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_arrow_down, down);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
        }else{
            down.setVisibility(View.GONE);
        }
        if(player.getCards_count() == 2){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, yellowCard);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#ff0000"));
        }else{
            yellowCard.setVisibility(View.GONE);
        }
        if(player.isSuspended()){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_square, redCard);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
        }else{
            redCard.setVisibility(View.GONE);
        }
        if(player.getInjury_id() != 0){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_medkit, medkit);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
            injuredTime.setText("" + player.getRecovery());
            if(!player.isHealed()) {
                healthButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogConfirmBuying = new TwoButtonsDialog(getActivity(),
                                getResources().getString(R.string.heal_player), getResources().getString(R.string.healing_player_1) + ""
                                + player.getFirst_name() + " " + player.getLast_name() + " " + getResources().getString(R.string.healing_player_2)
                                + " " + (player.getRecovery() % 2 > 0 ? player.getRecovery() / 2 + 1 : player.getRecovery() / 2) +
                                " " + getResources().getString(R.string.healing_player_3) + " " +

                                " " + player.getFirst_name() + " "
                                + player.getLast_name() + " " + getResources().getString(R.string.healing_player_4), fragmentPlayer, Defaultdata.DIALOG_CONFIRM_BUYING_CREDITS, null);
                        dialogConfirmBuying.show();
                    }
                });
            }else{
                healthButton.setVisibility(View.GONE);
            }
        }else{
            medkit.setVisibility(View.GONE);
            injuredTime.setVisibility(View.GONE);
            healthButton.setVisibility(View.GONE);
        }
        if(player.isRetiring()){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_user_times, retired);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#f00000"));
        }else{
            retired.setVisibility(View.GONE);
        }
        if(player.isHealed()){

            VectorChildFinder vector = new VectorChildFinder(getContext(), R.drawable.ic_plus_circle, healted);
            VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
            path.setFillColor(Color.parseColor("#3c763d"));
        }else{
            healted.setVisibility(View.GONE);
        }
        playerName.setText(player.getNumber() + " - " + player.getFirst_name() + player.getLast_name());
        playerInfo.setText(player.getPosition() + " - " + player.getAge() + " " +
                getResources().getString(R.string.years));
        playerEnergia.setText( player.getStamina() + "");
        playerArquero.setText( player.getGoalkeeping() + "");
        playerDefensa.setText( player.getDefending() + "");
        playerGambeta.setText( player.getDribbling() + "");
        playerCabeceo.setText( player.getHeading() + "");
        playerSalto.setText( player.getJumping() + "");
        playerPase.setText( player.getPassing() + "");
        playerPrecision.setText( player.getPrecision() + "");
        playerVelocidad.setText( player.getSpeed() + "");
        playerFuerza.setText( player.getStrength() + "");
        playerQuite.setText( player.getTackling() + "");
        playerExp.setText( player.getExperience() + "");

        /*
        if(player.getLast_upgrade()!= null){
            if (player.getLast_upgrade().getStamina() > 0){
                playerEnergia.setText( player.getStamina() + " (" + player.getLast_upgrade().getStamina() + ")");
                playerEnergia.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getGoalkeeping() > 0){
                playerArquero.setText( player.getGoalkeeping() + " (" + player.getLast_upgrade().getGoalkeeping() + ")");
                playerArquero.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getDefending() > 0){
                playerDefensa.setText( player.getDefending() + " (" + player.getLast_upgrade().getDefending() + ")");
                playerDefensa.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getDribbling() > 0){
                playerGambeta.setText( player.getDribbling() + " (" + player.getLast_upgrade().getDribbling() + ")");
                playerGambeta.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getHeading() > 0){
                playerCabeceo.setText( player.getHeading() + " (" + player.getLast_upgrade().getHeading() + ")");
                playerCabeceo.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getJumping() > 0){
                playerSalto.setText( player.getJumping() + " (" + player.getLast_upgrade().getJumping() + ")");
                playerSalto.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getPassing() > 0){
                playerPase.setText( player.getPassing() + " (" + player.getLast_upgrade().getPassing() + ")");
                playerPase.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getPrecision() > 0){
                playerPrecision.setText( player.getPrecision() + " (" + player.getLast_upgrade().getPrecision() + ")");
                playerPrecision.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getSpeed() > 0){
                playerVelocidad.setText( player.getSpeed() + " (" + player.getLast_upgrade().getSpeed() + ")");
                playerVelocidad.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getStrength() > 0){
                playerFuerza.setText( player.getStrength() + " (" + player.getLast_upgrade().getStrength() + ")");
                playerFuerza.setTextColor(Color.parseColor("#080000"));
            }

            if (player.getLast_upgrade().getTackling() > 0){
                playerQuite.setText( player.getTackling() + " (" + player.getLast_upgrade().getTackling() + ")");
                playerQuite.setTextColor(Color.parseColor("#080000"));
            }
            if (player.getLast_upgrade().getExperience() > 0){
                playerExp.setText( player.getExperience() + " (" + player.getLast_upgrade().getExperience() + ")");
                playerExp.setTextColor(Color.parseColor("#080000"));
            }
        } */
    }

}