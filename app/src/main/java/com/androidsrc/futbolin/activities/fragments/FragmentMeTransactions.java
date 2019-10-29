
package com.androidsrc.futbolin.activities.fragments;


        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.PorterDuff;
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
        import com.androidsrc.futbolin.database.models.DataMeTransaction;
        import com.androidsrc.futbolin.database.models.MeTransactions;
        import com.androidsrc.futbolin.utils.Defaultdata;
        import com.google.android.material.button.MaterialButton;

        import java.text.DateFormat;
        import java.text.DecimalFormat;
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
public class FragmentMeTransactions extends Fragment {
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
    MeTransactions transactions;
    LinearLayout buttonsLayout;
    Button previousPage, firstButtonPage, secondButtonPage, thirdButtonPage, fourthButtonPage, fifthButtonPage, nextPage;
    DecimalFormat decimalFormat;
    String pattern;

    public FragmentMeTransactions() {
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
    public static FragmentMeTransactions newInstance(getUser user, String param2) {
        FragmentMeTransactions fragment = new FragmentMeTransactions();
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
        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);
        v = inflater.inflate(R.layout.fragment_fragment_me_transactions, container, false);
        getMeTransactions(1);
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

    public void getMeTransactions(int current_page) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = Defaultdata.loadDialog(dialog, getResources().getString(R.string.wait),
                getResources().getString(R.string.loading_finances_from_your_team) , getActivity());
        dialog.show();

        SvcApi svc;
        svc = Svc.initAuth(getActivity());
        Call<MeTransactions> call = svc.getMeTransactions(current_page, Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<MeTransactions>() {
            @Override
            public void onResponse(Call<MeTransactions> call, Response<MeTransactions> response) {
                final MeTransactions user = response.body();
                final Response<MeTransactions> responsefinal = response;

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
            public void onFailure(Call<MeTransactions> call, Throwable t) {
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

        int position = 0;
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

        for (DataMeTransaction data : transactions.getData()) {
            TableRow row = new TableRow(getActivity());
            row.setPadding(3, 3, 3, 3);
            row.setTag("body");
            if (position % 2 == 0) {
                row.setBackgroundColor(Color.parseColor("#cccccc"));
            }

            DateFormat sdfFinal = new SimpleDateFormat("dd/MM/yyyy");


            TextView date = new TextView(getActivity());

            date.setTextSize(10);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = null;
            try {
                date1 = sdf.parse(data.getCreated_at());
                date.setText(sdfFinal.format(date1));
            } catch (Exception e) {

            }


            TextView description = new TextView(getActivity());
            description.setText(data.getDescription());
            description.setTextSize(10);

            TextView amount = new TextView(getActivity());
            amount.setText(decimalFormat.format(data.getAmount()) + " $");
            if (data.getAmount() < 0) {
                amount.setTextColor(Color.parseColor("#ff0000"));
            }
            amount.setTextSize(10);

            TextView balance = new TextView(getActivity());
            if (data.getBalance() < 0) {
                balance.setTextColor(Color.parseColor("#ff0000"));
            }
            balance.setText(decimalFormat.format(data.getBalance()) + " $");
            balance.setTextSize(10);

            row.addView(date);
            row.addView(description);
            row.addView(amount);
            row.addView(balance);
            table.addView(row);

            position++;
        }

        updateRoundButtons();
    }

    void updateRoundButtons() {
        if (transactions.getCurrent_page() - 3 > 0) {
            previousPage.setVisibility(View.VISIBLE);
            previousPage.setText(" <<");
            previousPage.setBackgroundResource(R.color.white);
            previousPage.setTextColor(Color.parseColor("#3b6c8e"));
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                previousPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
            }
            previousPage.getBackground().setColorFilter(getActivity().getResources().getColor(R.color.white),  PorterDuff.Mode.MULTIPLY);
            previousPage.setTag(transactions.getCurrent_page() - 3);
            previousPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMeTransactions((int) view.getTag());
                }
            });
        } else {
            previousPage.setVisibility(View.INVISIBLE);
            previousPage.setText(" <<");
            previousPage.setBackgroundResource(R.color.gray);
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                previousPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
            }
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
            firstButtonPage.setBackgroundResource(R.color.white);
            firstButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            firstButtonPage.getBackground().setColorFilter(getActivity().getResources().getColor(R.color.white),  PorterDuff.Mode.MULTIPLY);
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                firstButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
            }
            firstButtonPage.setTag(transactions.getCurrent_page() - 2);
            firstButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMeTransactions((int) view.getTag());
                }
            });
        } else {
            firstButtonPage.setVisibility(View.INVISIBLE);
            firstButtonPage.setText(" ");
            firstButtonPage.setBackgroundResource(R.color.gray);
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                firstButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
            }
            firstButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            firstButtonPage.getBackground().setColorFilter(getActivity().getResources().getColor(R.color.gray),  PorterDuff.Mode.MULTIPLY);

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
            secondButtonPage.setBackgroundResource(R.color.white);
            secondButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                secondButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
            }
            secondButtonPage.setTag(transactions.getCurrent_page() - 1);
            secondButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMeTransactions((int) view.getTag());
                }
            });
        } else {
            secondButtonPage.setVisibility(View.INVISIBLE);
            secondButtonPage.setText(" ");
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                secondButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
            }
            secondButtonPage.setBackgroundResource(R.color.gray);
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
            thirdButtonPage.setBackgroundResource(R.color.futbolinAzul);
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                thirdButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
            }
            thirdButtonPage.setTextColor(Color.parseColor("#FFFFFF"));
            thirdButtonPage.setTag(transactions.getCurrent_page());
            thirdButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMeTransactions((int) view.getTag());
                }
            });
        } else {
            thirdButtonPage.setText(" ");
            thirdButtonPage.setBackgroundResource(R.color.gray);
            thirdButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            thirdButtonPage.setTag(transactions.getCurrent_page());
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                thirdButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
            }
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
            fourthButtonPage.setBackgroundResource(R.color.white);
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                fourthButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
            }
            fourthButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            fourthButtonPage.setTag(transactions.getCurrent_page() + 1);
            fourthButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMeTransactions((int) view.getTag());
                }
            });
        } else {
            fourthButtonPage.setVisibility(View.INVISIBLE);
            fourthButtonPage.setText(" ");
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                fourthButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
            }
            fourthButtonPage.setBackgroundResource(R.color.gray);
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
            fifthButtonPage.setBackgroundResource(R.color.white);
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                fifthButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
            }
            fifthButtonPage.setTextColor(Color.parseColor("#3b6c8e"));
            fifthButtonPage.setTag(transactions.getCurrent_page() + 2);
            fifthButtonPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMeTransactions((int) view.getTag());
                }
            });
        } else {
            fifthButtonPage.setVisibility(View.INVISIBLE);
            fifthButtonPage.setText(" ");
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                fifthButtonPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
            }
            fifthButtonPage.setBackgroundResource(R.color.gray);
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
            if(android.os.Build.VERSION.SDK_INT >= 21) {
                nextPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.white));
            }
            nextPage.setBackgroundResource(R.color.white);
            nextPage.setTextColor(Color.parseColor("#3b6c8e"));
            nextPage.setTag(transactions.getCurrent_page() + 2);
            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMeTransactions((int) view.getTag());
                }
            });
        } else {
            nextPage.setVisibility(View.INVISIBLE);

            if(android.os.Build.VERSION.SDK_INT >= 21) {
                nextPage.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
            }
            nextPage.setBackgroundResource(R.color.gray);
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