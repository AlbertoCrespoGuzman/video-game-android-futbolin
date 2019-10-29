package com.androidsrc.futbolin.activities.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.MainActivity;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTeam extends Fragment {
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

    TextView nameTv, shortNameTv, stadiumTv;
    ImageView primaryColorIv, secondaryColorIv, shieldIv;
    View v;
    LayoutInflater inflater;
    ViewGroup container;
    Button editTeamButton;

    public FragmentTeam() {
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
    public static FragmentTeam newInstance(getUser user, String param2) {
        FragmentTeam fragment = new FragmentTeam();
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
        v = inflater.inflate(R.layout.fragment_team, container, false);

        nameTv = v.findViewById(R.id.fragment_team_name_text);
        shortNameTv = v.findViewById(R.id.fragment_team_short_name_text);
        stadiumTv = v.findViewById(R.id.fragment_team_stadium_text);
        primaryColorIv = v.findViewById(R.id.fragment_team_primary_color);
        secondaryColorIv = v.findViewById(R.id.fragment_team_secondary_color);
        shieldIv = v.findViewById(R.id.fragment_team_shield);
        editTeamButton = v.findViewById(R.id.fragment_team_edit_button);

        nameTv.setText(user.getUser().getTeam().getName());
        shortNameTv.setText(user.getUser().getTeam().getShort_name());
        stadiumTv.setText(user.getUser().getTeam().getStadium_name());

        GradientDrawable shapePrimary = new GradientDrawable();
        shapePrimary.setShape(GradientDrawable.RECTANGLE);
        shapePrimary.setCornerRadii(new float[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        shapePrimary.setColor(Color.parseColor(user.getUser().getTeam().getPrimary_color()));
        primaryColorIv.setBackground(shapePrimary);

        GradientDrawable shapeSecondary = new GradientDrawable();
        shapeSecondary.setShape(GradientDrawable.RECTANGLE);
        shapeSecondary.setCornerRadii(new float[] { 0, 0, 0, 0, 0, 0, 0, 0 });
        shapeSecondary.setColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
        secondaryColorIv.setBackground(shapeSecondary);

        VectorChildFinder vectorHeader = new VectorChildFinder(getContext(), Defaultdata.shields.get(user.getUser().getTeam().getShield()), shieldIv);

        VectorDrawableCompat.VFullPath path21Header = vectorHeader.findPathByName("secundary_color1");
        path21Header.setFillColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Header = vectorHeader.findPathByName("secundary_color2");
        path22Header.setFillColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Header = vectorHeader.findPathByName("primary_color");
        path1Header.setFillColor(Color.parseColor(user.getUser().getTeam().getPrimary_color()));
        if(user.getUser().getTeam().getStrategy_id() == 8){
            VectorDrawableCompat.VFullPath path12 = vectorHeader.findPathByName("primary_color2");
            if(path12 != null) {
                path12.setFillColor(Color.parseColor(user.getUser().getTeam().getPrimary_color()));
            }
        }
        shieldIv.invalidate();

        editTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildEditTeamLayout();
            }
        });

    }
    void buildEditTeamLayout(){
        ((MainActivity)getActivity()).changeToFragmentTeamEdit();
    }


}