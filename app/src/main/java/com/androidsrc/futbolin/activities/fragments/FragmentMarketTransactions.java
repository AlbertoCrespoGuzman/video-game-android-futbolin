package com.androidsrc.futbolin.activities.fragments;




        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
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
        import com.androidsrc.futbolin.communications.http.auth.get.getUser;
        import com.androidsrc.futbolin.database.models.DataMarketTransaction;
        import com.androidsrc.futbolin.database.models.MarketTransactions;
        import com.androidsrc.futbolin.utils.Defaultdata;
        import com.androidsrc.futbolin.utils.dialogs.PlayerDialog;

        import java.text.DateFormat;
        import java.text.DecimalFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;

        import androidx.appcompat.app.AppCompatDialog;
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
public class FragmentMarketTransactions extends Fragment implements PlayerDialog.DialogClickListener{
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
    MarketTransactions transactions;
    LinearLayout buttonsLayout;
    Button previousPage, firstButtonPage, secondButtonPage, thirdButtonPage, fourthButtonPage, fifthButtonPage, nextPage;
    PlayerDialog playerDialog;
    FragmentMarketTransactions fragmentMarketTransactions;
    int current_page = 1;
    DecimalFormat decimalFormat;
    String pattern;

    public FragmentMarketTransactions() {
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
    public static FragmentMarketTransactions newInstance(getUser user, String param2) {
        FragmentMarketTransactions fragment = new FragmentMarketTransactions();
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
        v = inflater.inflate(R.layout.fragment_market_transactions, container, false);
        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);

        fragmentMarketTransactions = this;
        if(mParam2 != null && !mParam2.contains("")){
            current_page = Integer.parseInt(mParam2);
        }
        getMarketTransactions(current_page);
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

    public void getMarketTransactions(int current_page) {
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
        Call<MarketTransactions> call = svc.getMarketTransactions(current_page, Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<MarketTransactions>() {
            @Override
            public void onResponse(Call<MarketTransactions> call, Response<MarketTransactions> response) {
                final MarketTransactions user = response.body();
                final Response<MarketTransactions> responsefinal = response;

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
                                transactions = responsefinal.body();
                                loadFragment();

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
            public void onFailure(Call<MarketTransactions> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void loadFragment() {
        table = v.findViewById(R.id.fragment_me_transactions_table);
        buttonsLayout = v.findViewById(R.id.fragment_pages_layout);
        previousPage = v.findViewById(R.id.fragment_me_transactions_previous_button);
        firstButtonPage = v.findViewById(R.id.fragment_me_transactions_first_button);
        secondButtonPage = v.findViewById(R.id.fragment_me_transactions_second_button);
        thirdButtonPage = v.findViewById(R.id.fragment_me_transactions_third_button);
        fourthButtonPage = v.findViewById(R.id.fragment_me_transactions_fourth_button);
        fifthButtonPage = v.findViewById(R.id.fragment_me_transactions_fifth_button);
        nextPage = v.findViewById(R.id.fragment_me_transactions_next_button);


        List<TableRow> rows = new ArrayList<>();
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row1 = (TableRow) table.getChildAt(i);
            if (row1 != null && !((String) row1.getTag()).equals("header")) {
                rows.add(row1);
            }
        }
        for (TableRow row : rows) {
            table.removeView(row);
        }
        String last = "";
        for (DataMarketTransaction data : transactions.getData()) {
            TableRow row = new TableRow(getActivity());
            row.setPadding(5, 10, 5, 10);
            row.setTag("body");
            switch (data.getPlayer().getPosition()){
                case "ARQ":
                    if(last.equals("ARQ")){
                        row.setBackgroundColor(Color.parseColor("#ffffb1"));
                        last = "";
                    }else{
                        row.setBackgroundColor(Color.parseColor("#ffffb6"));
                        last = "ARQ";
                    }

                    break;
                case "DEF":
                    if(last.equals("DEF")){
                        last = "";
                        row.setBackgroundColor(Color.parseColor("#a0ef9f"));
                    }else{
                        row.setBackgroundColor(Color.parseColor("#b1ffb1"));
                        last = "DEF";
                    }

                    break;
                case "MED":
                    if(last.equals("MED")){
                        last = "";
                        row.setBackgroundColor(Color.parseColor("#b1b0ff"));
                    }else{
                        row.setBackgroundColor(Color.parseColor("#a09fef"));
                        last = "MED";
                    }

                    break;
                case "ATA":
                    if(last.equals("ATA")){
                        last = "";
                        row.setBackgroundColor(Color.parseColor("#ee9f9f"));
                    }else{
                        row.setBackgroundColor(Color.parseColor("#ffb1b0"));
                        last = "ATA";
                    }
                    break;
            }


            DateFormat sdfFinal = new SimpleDateFormat("dd/MM/yyyy HH:mm");


            TextView date = new TextView(getActivity());

            date.setTextSize(9);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = null;
            try {
                date1 = sdf.parse(data.getCreated_at());
                date.setText(sdfFinal.format(date1));
            } catch (Exception e) {

            }

            final DataMarketTransaction dataF = data;
            TextView playerName = new TextView(getActivity());
            playerName.setText(data.getPlayer().getShort_name());
            playerName.setTextSize(11);
            playerName.setTextColor(Color.parseColor("#396c8f"));
            playerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("player", "EEEE");
                    playerDialog = new PlayerDialog(getActivity(), dataF.getPlayer(), user, fragmentMarketTransactions, true, null);
                    playerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    playerDialog.show();
                }
            });

            TextView position = new TextView(getActivity());
            position.setText(data.getPlayer().getPosition());
            position.setTextSize(11);

            TextView seller = new TextView(getActivity());
            if(data.getSeller() != null){
                seller.setText(data.getSeller().getShort_name());
                seller.setTextColor(Color.parseColor("#396c8f"));
                seller.setTextSize(11);
                seller.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) getActivity()).changeToShowTeamFragment(dataF.getSeller().getId(), dataF.getSeller().getShort_name(), "FragmentMarketTransactions_" + current_page);
                    }
                });
            }else{
                seller.setText("Jug. libre");
                seller.setTextSize(11);
            }
            seller.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView buyer = new TextView(getActivity());
            buyer.setText(data.getBuyer().getShort_name());
            buyer.setTextColor(Color.parseColor("#396c8f"));
            buyer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            buyer.setTextSize(11);
            buyer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).changeToShowTeamFragment(dataF.getBuyer().getId(), dataF.getBuyer().getShort_name(), "FragmentMarketTransactions_" + current_page);
                }
            });

            TextView value = new TextView(getActivity());
            value.setText(decimalFormat.format((data.getValue())));

            value.setTextSize(10);

            row.addView(date);
            row.addView(playerName);
            row.addView(position);
            row.addView(seller);
            row.addView(buyer);
            row.addView(value);
            table.addView(row);

        }

        updateRoundButtons();
    }

    void updateRoundButtons() {
        if (transactions.getCurrent_page() - 3 > 0) {
            previousPage.setVisibility(View.VISIBLE);
            previousPage.setText(" <<");
            previousPage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            previousPage.setTextColor(Color.parseColor("#3b6c8e"));
            previousPage.setTag(transactions.getCurrent_page() - 3);
            previousPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMarketTransactions((int) view.getTag());
                }
            });
        } else {
            previousPage.setVisibility(View.INVISIBLE);
            previousPage.setText(" <<");
            previousPage.setBackgroundColor(Color.parseColor("#CCCCCC"));
            previousPage.setTextColor(Color.parseColor("#3b6c8e"));
            previousPage.setTag(transactions.getCurrent_page() - 3);
            previousPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                }
            });
        }
        if (transactions.getCurrent_page() - 2 > 0) {
            firstButtonPage.setVisibility(View.VISIBLE);
            firstButtonPage.setText(transactions.getCurrent_page() - 2 + "");
            firstButtonPage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            firstButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            firstButtonPage.setTag(transactions.getCurrent_page() - 2);
            firstButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMarketTransactions((int) view.getTag());
                }
            });
        } else {
            firstButtonPage.setVisibility(View.INVISIBLE);
            firstButtonPage.setText(" ");
            firstButtonPage.setBackgroundColor(Color.parseColor("#CCCCCC"));
            firstButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            firstButtonPage.setTag(transactions.getCurrent_page() - 2);
            firstButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                }
            });
        }
        if (transactions.getCurrent_page() - 1 > 0) {
            secondButtonPage.setVisibility(View.VISIBLE);
            secondButtonPage.setText(transactions.getCurrent_page() - 1 + " ");
            secondButtonPage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            secondButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            secondButtonPage.setTag(transactions.getCurrent_page() - 1);
            secondButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMarketTransactions((int) view.getTag());
                }
            });
        } else {
            secondButtonPage.setVisibility(View.INVISIBLE);
            secondButtonPage.setText(" ");
            secondButtonPage.setBackgroundColor(Color.parseColor("#CCCCCC"));
            secondButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            secondButtonPage.setTag(transactions.getCurrent_page() - 1);
            secondButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                }
            });
        }
        if (transactions.getCurrent_page() > 0) {

            thirdButtonPage.setText(transactions.getCurrent_page() + " ");
            thirdButtonPage.setBackgroundColor(Color.parseColor("#3b6c8e"));
            thirdButtonPage.setTextColor(Color.parseColor("#FFFFFF"));
            thirdButtonPage.setTag(transactions.getCurrent_page());
            thirdButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMarketTransactions((int) view.getTag());
                }
            });
        } else {
            thirdButtonPage.setText(" ");
            thirdButtonPage.setBackgroundColor(Color.parseColor("#CCCCCC"));
            thirdButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            thirdButtonPage.setTag(transactions.getCurrent_page());
            thirdButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                }
            });
        }
        if (transactions.getCurrent_page() + 1 <= transactions.getLast_page()) {
            fourthButtonPage.setVisibility(View.VISIBLE);
            fourthButtonPage.setText(transactions.getCurrent_page() + 1 + " ");
            fourthButtonPage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            fourthButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            fourthButtonPage.setTag(transactions.getCurrent_page() + 1);
            fourthButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMarketTransactions((int) view.getTag());
                }
            });
        } else {
            fourthButtonPage.setVisibility(View.INVISIBLE);
            fourthButtonPage.setText(" ");
            fourthButtonPage.setBackgroundColor(Color.parseColor("#CCCCCC"));
            fourthButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            fourthButtonPage.setTag(transactions.getCurrent_page() + 1);
            fourthButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                }
            });
        }
        if (transactions.getCurrent_page() + 2 <= transactions.getLast_page()) {
            fifthButtonPage.setVisibility(View.VISIBLE);
            fifthButtonPage.setText(transactions.getCurrent_page() + 2 + " ");
            fifthButtonPage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            fifthButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            fifthButtonPage.setTag(transactions.getCurrent_page() + 2);
            fifthButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMarketTransactions((int) view.getTag());
                }
            });
        } else {
            fifthButtonPage.setVisibility(View.INVISIBLE);
            fifthButtonPage.setText(" ");
            fifthButtonPage.setBackgroundColor(Color.parseColor("#CCCCCC"));
            fifthButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            fifthButtonPage.setTag(transactions.getCurrent_page() + 2);
            fifthButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                }
            });
        }
        if (transactions.getCurrent_page() + 3 <= transactions.getLast_page()) {
            nextPage.setVisibility(View.VISIBLE);
            nextPage.setText(">>");
            nextPage.setBackgroundColor(Color.parseColor("#FFFFFF"));
            nextPage.setTextColor(Color.parseColor("#3b6c8e"));
            nextPage.setTag(transactions.getCurrent_page() + 2);
            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMarketTransactions((int) view.getTag());
                }
            });
        } else {
            nextPage.setVisibility(View.INVISIBLE);
            nextPage.setText(" ");
            nextPage.setBackgroundColor(Color.parseColor("#CCCCCC"));
            nextPage.setTextColor(Color.parseColor("#3b6c8e"));
            nextPage.setTag(transactions.getCurrent_page() + 2);
            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("onClick RoundButton", "disabled -> tag " + view.getTag());
                }
            });
        }
    }
}