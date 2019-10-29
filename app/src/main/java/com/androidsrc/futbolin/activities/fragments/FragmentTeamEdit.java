package com.androidsrc.futbolin.activities.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.post.PostTeam;
import com.androidsrc.futbolin.database.models.Team;
import com.androidsrc.futbolin.utils.dialogs.ShieldsPickerDialog;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuku.ambilwarna.AmbilWarnaDialog;

import static com.androidsrc.futbolin.utils.Defaultdata.shields;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTeamEdit extends Fragment implements ShieldsPickerDialog.DialogClickListener{
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

    EditText nameTv, shortNameTv, stadiumTv;
    ImageButton primaryColorIv, secondaryColorIv;
    ImageView  shieldIv;
    View v;
    LayoutInflater inflater;
    ViewGroup container;
    Button saveTeamButton;
    int positionShield = 1;
    VectorChildFinder vectorShield;
    int colorPrimary = 0xffffff00;
    int colorSecundary = 0xffffff00;
    LinearLayout bordersShield;
    ShieldsPickerDialog cdd;
    SvcApi svc;

    public FragmentTeamEdit() {
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
    public static FragmentTeamEdit newInstance(getUser user, String param2) {
        FragmentTeamEdit fragment = new FragmentTeamEdit();
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
        this.inflater = inflater;
        this.container = container;
        buildShowTeamLayout();
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
    public void onItemClick(int position) {
        System.out.println("position final: " + position);
        positionShield = position;
        if(cdd != null && cdd.isShowing()){
            cdd.dismiss();
        }
        vectorShield = new VectorChildFinder(getContext(), shields.get(positionShield), shieldIv);

        VectorDrawableCompat.VFullPath path21 = vectorShield.findPathByName("secundary_color1");
        path21.setFillColor(colorSecundary);
        VectorDrawableCompat.VFullPath path22 = vectorShield.findPathByName("secundary_color2");
        path22.setFillColor(colorSecundary);
        VectorDrawableCompat.VFullPath path1 = vectorShield.findPathByName("primary_color");
        path1.setFillColor(colorPrimary);
        if(positionShield == 8){
            VectorDrawableCompat.VFullPath path12 = vectorShield.findPathByName("primary_color2");
            path12.setFillColor(colorPrimary);
        }

        shieldIv.invalidate();
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
    void buildShowTeamLayout(){
        v = inflater.inflate(R.layout.fragment_team_edit, container, false);

        nameTv = v.findViewById(R.id.fragment_team_name_edittext);
        shortNameTv = v.findViewById(R.id.fragment_team_short_name_edittext);
        stadiumTv = v.findViewById(R.id.fragment_team_stadium_edittext);
        primaryColorIv = v.findViewById(R.id.activity_team_edit_primary_color);
        secondaryColorIv = v.findViewById(R.id.activity_team_edit_secundary_color);
        shieldIv = v.findViewById(R.id.fragment_team_edit_shield);
        saveTeamButton = v.findViewById(R.id.fragment_team_save_button);
        bordersShield = v.findViewById(R.id.activity_team_borders_shield);


        nameTv.setText(user.getUser().getTeam().getName());
        shortNameTv.setText(user.getUser().getTeam().getShort_name());
        stadiumTv.setText(user.getUser().getTeam().getStadium_name());



        positionShield = (int)user.getUser().getTeam().getStrategy_id();
        colorPrimary = Color.parseColor(user.getUser().getTeam().getPrimary_color());
        colorSecundary = Color.parseColor(user.getUser().getTeam().getSecondary_color());

        primaryColorIv.setTag(colorPrimary);
        secondaryColorIv.setTag(colorSecundary);

        GradientDrawable drawable = (GradientDrawable) primaryColorIv.getDrawable();
        drawable.setColor(colorPrimary);
        GradientDrawable drawable2 = (GradientDrawable) secondaryColorIv.getDrawable();
        drawable2.setColor(colorSecundary);

        primaryColorIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerDialog(false, (ImageButton) view, true);
            }
        });
        secondaryColorIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPickerDialog(false, (ImageButton) view, false);
            }
        });

        vectorShield = new VectorChildFinder(getContext(), shields.get(user.getUser().getTeam().getShield()), shieldIv);

        VectorDrawableCompat.VFullPath path21Header = vectorShield.findPathByName("secundary_color1");
        path21Header.setFillColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Header = vectorShield.findPathByName("secundary_color2");
        path22Header.setFillColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Header = vectorShield.findPathByName("primary_color");
        path1Header.setFillColor(Color.parseColor(user.getUser().getTeam().getPrimary_color()));
        if(user.getUser().getTeam().getStrategy_id() == 8){
            VectorDrawableCompat.VFullPath path12 = vectorShield.findPathByName("primary_color2");
            path12.setFillColor(Color.parseColor(user.getUser().getTeam().getPrimary_color()));
        }
        shieldIv.invalidate();

        bordersShield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShieldPickerDialog();
            }
        });


        saveTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patchTeam();
            }
        });

    }
    void buildEditTeamLayout(){
        ((MainActivity)getActivity()).changeToFragmentTeamEdit();
    }
    void openColorPickerDialog(boolean supportsAlpha, ImageButton tvColor, boolean isPrimary) {
        final ImageButton tvColorF = tvColor;
        final boolean isPrimaryf = isPrimary;
        Log.e("COLOR", tvColor.getTag() + "");
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(), (int)tvColor.getTag(), supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                displayColor(tvColorF, color, isPrimaryf);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }
        });
        dialog.show();
    }
    void displayColor(ImageButton squareColor, int color, boolean isPrimary) {
        GradientDrawable drawable = (GradientDrawable) squareColor.getDrawable();
        drawable.setColor(color);

        if(isPrimary){
            VectorDrawableCompat.VFullPath path1 = vectorShield.findPathByName("primary_color");
            path1.setFillColor(color);
            if(positionShield == 8){
                VectorDrawableCompat.VFullPath path12 = vectorShield.findPathByName("primary_color2");
                path12.setFillColor(color);
            }
            colorPrimary = color;

        }else{
            VectorDrawableCompat.VFullPath path21 = vectorShield.findPathByName("secundary_color1");
            path21.setFillColor(color);
            VectorDrawableCompat.VFullPath path22 = vectorShield.findPathByName("secundary_color2");
            path22.setFillColor(color);
            colorSecundary = color;
        }

        squareColor.setTag(color);
        shieldIv.invalidate();

    }
    void openShieldPickerDialog(){
        cdd = new ShieldsPickerDialog(this, getActivity(), shields, colorPrimary, colorSecundary);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }
    void patchTeam(){
        svc = Svc.initAuth(getContext());
        Call<Team> call = svc.patchTeam(buildPostTeamClass());
        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Call<Team> call, Response<Team> response) {
                final Team team = response.body();
                final Response<Team> responsefinal = response;
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, code = " + responsefinal.code());
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                Log.e("Repsonse postTeam", team.toString());
                                ((MainActivity)getActivity()).changeToSplashScreen();
                            }else{
                                Log.e("error", "algo raro");
                            }
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
                }
            }

            @Override
            public void onFailure(Call<Team> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    PostTeam buildPostTeamClass(){
        PostTeam team = new PostTeam();

        team.setName(nameTv.getText().toString());
        team.setShort_name(shortNameTv.getText().toString());
        team.setStadium_name(stadiumTv.getText().toString());
        team.setDevice_id(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        team.setShield(positionShield);
        team.setPrimary_color(String.format("#%06X", (0xFFFFFF & colorPrimary)));
        team.setSecondary_color(String.format("#%06X", (0xFFFFFF & colorSecundary)));

        return team;
    }
}