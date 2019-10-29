
package com.androidsrc.futbolin.activities.fragments;


        import android.app.AlarmManager;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.provider.Settings;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.CompoundButton;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.ToggleButton;

        import com.androidsrc.futbolin.R;
        import com.androidsrc.futbolin.activities.MainActivity;
        import com.androidsrc.futbolin.activities.RegisterActivity;
        import com.androidsrc.futbolin.communications.Svc;
        import com.androidsrc.futbolin.communications.SvcApi;
        import com.androidsrc.futbolin.communications.http.auth.get.getMatch;
        import com.androidsrc.futbolin.communications.http.auth.get.getUser;
        import com.androidsrc.futbolin.communications.http.auth.patch.PatchMe;
        import com.androidsrc.futbolin.database.manager.DatabaseManager;
        import com.androidsrc.futbolin.database.models.Notification;
        import com.androidsrc.futbolin.utils.LocaleLanguageHelper;

        import java.util.Date;
        import java.util.Locale;

        import androidx.appcompat.widget.AppCompatSpinner;
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
public class FragmentSettings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    getUser user;
    ImageView imageShield;
    View v;
    DatabaseManager db;
    private OnFragmentInteractionListener mListener;
    ToggleButton notificationTrain, notificationNextMatch, liveSounds;
    TextView notificationTrainText;
    LayoutInflater inflater;
    ViewGroup container;
    FragmentSettings fragmentSettings;
    AppCompatSpinner languageSpinner;
    String languageChanged = "";
    int languageHasBeenSelected = 0;
    boolean langChanged = false;
    SvcApi svc;
    ProgressDialog languageDialog;

    public FragmentSettings() {
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
    public static FragmentSettings newInstance(getUser user, String param2) {
        FragmentSettings fragment = new FragmentSettings();
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
        this.fragmentSettings = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        this.container = container;

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

    public void buildLayout(){
        db = new DatabaseManager(getActivity());
        v = inflater.inflate(R.layout.fragment_fragment_settings, container, false);

        notificationTrain = v.findViewById(R.id.fragment_settings_notification_trainment_toggle);
        notificationNextMatch = v.findViewById(R.id.fragment_settings_notification_next_match_toggle);
        liveSounds = v.findViewById(R.id.fragment_settings_notification_live_sounds_toggle);
        notificationTrainText = v.findViewById(R.id.notification_train_text);
        languageSpinner = v.findViewById(R.id.fragment_settings_input_language_spinner);


        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String lang = "es";
                switch (position){
                    case 0:
                        lang = "es";
                        break;
                    case 1:
                        lang = "en";
                        break;
                    case 2:
                        lang = "pt";
                        Log.e("switch","lang = " + lang);
                        break;
                    case 3:
                        lang = "ca";
                        Log.e("switch","lang = " + lang);
                        break;
                }
                if(languageHasBeenSelected >= 1){

                    setLanguage(lang);
                }else{
                    Log.e("ei ma", "ey ma");
                    languageHasBeenSelected ++;
                    if(Locale.getDefault().getDisplayLanguage().startsWith("espa")){
                        languageSpinner.setSelection(0);
                        langChanged = true;
                    }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("english")){
                        languageSpinner.setSelection(1);
                    }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("por")){
                        languageSpinner.setSelection(2);
                    }else if(Locale.getDefault().getDisplayLanguage().toLowerCase().startsWith("cat")){
                        languageSpinner.setSelection(3);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        Date date = new Date();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AlarmManager am = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                if(am != null && am.getNextAlarmClock() != null){
                    date.setTime(am.getNextAlarmClock().getTriggerTime());
                 //   notificationTrainText.setText("Prox notif: " + date.toString() + " | " +am.getNextAlarmClock().getShowIntent());
                }else{
                    Log.e("Settings", "NULL -> am != null  " + (am == null) + " | am.getNextAlarmClock() != null "+ (am.getNextAlarmClock() == null));
                }

        }
        notificationTrain.setChecked(db.findNotification().isTrainActive());
        notificationNextMatch.setChecked(db.findNotification().isMatchActive());
        liveSounds.setChecked(db.findNotification().isLiveSoundsActive());
        Log.e("notif",db.findNotification().toString());

        notificationTrain.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if(db == null){
                    db = new DatabaseManager(getActivity());
                }
                Notification notification = db.findNotification();
                notification.setTrainActive(isChecked);
                db.saveNotification(notification);
            }
        }) ;
        notificationNextMatch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if(db == null){
                    db = new DatabaseManager(getActivity());
                }
                Notification notification = db.findNotification();
                notification.setMatchActive(isChecked);
                db.saveNotification(notification);
            }
        }) ;
        liveSounds.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean isChecked) {
                if(db == null){
                    db = new DatabaseManager(getActivity());
                }
                Notification notification = db.findNotification();
                notification.setLiveSoundsActive(isChecked);
                db.saveNotification(notification);
            }
        }) ;

    }
    public void setLanguage(String lang){
        languageChanged = lang;
        LocaleLanguageHelper.setLocale(getActivity(), lang);
        db = new DatabaseManager(getActivity());
        Notification nt = db.findNotification();
        nt.setLanguage(lang);
        db.saveNotification(nt);
        if(langChanged){
            patchMe(lang);

        }
        if(languageHasBeenSelected >= 1){
            Log.e("Register", "languageHasBeenSelected = " + languageHasBeenSelected + " | langChanged = " + langChanged);
            langChanged = true;

        }
        //         recreate();
    }

    public void patchMe(String lang){

        PatchMe patchMe =new PatchMe();


        patchMe.setDevice_id(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        patchMe.setFirst_name(((MainActivity)getActivity()).getUser().getUser().getFirstName());
        patchMe.setLast_name(((MainActivity)getActivity()).getUser().getUser().getLastName());
        patchMe.setLanguage(lang);

        if(languageDialog != null && languageDialog.isShowing()){
            languageDialog.dismiss();
            languageDialog = null;
        }
        languageDialog = new ProgressDialog(getActivity());
        languageDialog.setTitle(getResources().getString(R.string.wait));
        languageDialog.setMessage(getResources().getString(R.string.updating_language));
        languageDialog.show();

        svc = Svc.initAuth(getContext());
        Call<getUser> call = svc.patchMe(patchMe);
        call.enqueue(new Callback<getUser>() {
            @Override
            public void onResponse(Call<getUser> call, Response<getUser> response) {
                if(languageDialog != null && languageDialog.isShowing()){
                    languageDialog.dismiss();
                }

                final getUser user = response.body();
                final Response<getUser> responsefinal = response;
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, patchMe = " + user);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){

                                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();


                            }
                        }
                    });
                } else {

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
            public void onFailure(Call<getUser> call, Throwable t) {
                t.printStackTrace();
                if(languageDialog != null && languageDialog.isShowing()){
                    languageDialog.dismiss();
                }
            }
        });

    }

}