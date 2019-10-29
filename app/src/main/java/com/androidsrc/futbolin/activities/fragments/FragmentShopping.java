package com.androidsrc.futbolin.activities.fragments;

import android.content.Context;

import android.content.Intent;
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
import com.androidsrc.futbolin.communications.http.auth.get.getShoppingItems;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuy;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuyResponse;
import com.androidsrc.futbolin.database.models.ShoppingItem;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.adapters.ShoppingItemsAdapter;
import com.androidsrc.futbolin.utils.dialogs.MessageDialog;
import com.androidsrc.futbolin.utils.dialogs.TwoButtonsDialog;
import com.androidsrc.futbolin.utils.itemsDecoration.ItemOffsetDecoration;
import com.androidsrc.futbolin.utils.itemsDecoration.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
public class FragmentShopping extends Fragment implements MessageDialog.DialogClickListener, ShoppingItemsAdapter.ItemClickListener, TwoButtonsDialog.DialogTwoButtonsClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    getUser user;
    ImageView imageShield;
    SvcApi svcAth;
    List<ShoppingItem> items = new ArrayList<>();
    TextView fubtosTv;
    Button buyFutbosButton;
    RecyclerView itemsListRV;
    View v;
    ShoppingItemsAdapter adapter;
    TwoButtonsDialog dialogNotEnoughtCredits, dialogConfirmBuying;
    FragmentShopping fragmentShopping;
    MessageDialog messageDialog;

    private OnFragmentInteractionListener mListener;

    public FragmentShopping() {
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
    public static FragmentShopping newInstance(getUser user, String param2) {
        FragmentShopping fragment = new FragmentShopping();
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
            if(items ==  null || items.isEmpty()){
                getShoppingItems();
            }
            this.fragmentShopping = this;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shopping, container, false);


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
    public void onItemClick(View view, int position) {
        Log.e("onItemClick", items.get(position).toString());
        if(items.get(position).getPrice() > user.getUser().getCredits()){
            dialogNotEnoughtCredits = new TwoButtonsDialog(getActivity(), getResources().getString(R.string.buy_fulbos),
                    getResources().getString(R.string.not_enough_fulbos_second_phrase_1) +
                    "\n" +
                            getResources().getString(R.string.not_enough_fulbos_second_phrase_2), fragmentShopping,  Defaultdata.DIALOG_NOT_ENOUGH_CREDITS, null);
            dialogNotEnoughtCredits.show();
        }else{
            dialogConfirmBuying = new TwoButtonsDialog(getActivity(), items.get(position).getName(), getResources().getString(R.string.buy_more_1) + " " + items.get(position).getName() +
                    " " + getResources().getString(R.string.buy_more_2) + " " + items.get(position).getPrice(), fragmentShopping,  Defaultdata.DIALOG_CONFIRM_BUYING_CREDITS, items.get(position));
            dialogConfirmBuying.show();
        }
    }

    @Override
    public void onButtonClick(boolean confirm, int type, Object extra) {
        switch (type){
            case Defaultdata.DIALOG_CONFIRM_BUYING_CREDITS:
                if(confirm){
                    if(((ShoppingItem) extra).getId() != 4){
                        postShoppingBuy((ShoppingItem) extra, 0);
                    }else{
                        selectPlayerToBeHealth();
                    }

                }else{
                    dialogConfirmBuying.dismiss();
                }
                break;
            case Defaultdata.DIALOG_NOT_ENOUGH_CREDITS:
                if(confirm){
                    buyCreditsViaPayPal();
                }else{
                    dialogNotEnoughtCredits.dismiss();
                }
                break;
        }
    }
    public void buyCreditsViaPayPal(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Defaultdata.URL_PAYPAL));
        getActivity().startActivity(browserIntent);
    }
    @Override
    public void onMessageDialogButtonClick() {
        messageDialog.dismiss();
        buildLayout();
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
    private void getShoppingItems(){

        svcAth = Svc.initAuth(getContext());
        Call<getShoppingItems> call = svcAth.getShoppingItems(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getShoppingItems>() {
            @Override
            public void onResponse(Call<getShoppingItems> call, Response<getShoppingItems> response) {

                final Response<getShoppingItems> responsefinal = response;
                Log.e("getShoppingItems", responsefinal.toString());
                final getShoppingItems getShoppingItems = response.body();
                if (response.isSuccessful()) {
                    if(getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (responsefinal.code() == 400) {
                                    Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                                    // SUPONIENDO QUE ANTERIORMENTE HA HECHO LOGOUT, NO TIENE TOKEN !
                                } else if (responsefinal.code() == 201 || responsefinal.code() == 200) {
                                    items = getShoppingItems.getItems();
                                    Log.e("items", items.toString());
                                    buildLayout();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getShoppingItems> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
    void buildLayout(){
        fubtosTv = v.findViewById(R.id.fragment_shopping_futbos_tv);
        fubtosTv.setText("Fúlbos: " + user.getUser().getCredits());
        buyFutbosButton = v.findViewById(R.id.fragment_shopping_buy_futbos_tv);
        buyFutbosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCreditsViaPayPal();
            }
        });
        itemsListRV = v.findViewById(R.id.fragment_shopping_items_list_rv);
        adapter = new ShoppingItemsAdapter(this, getContext(), items);
        itemsListRV.setAdapter(adapter);
        itemsListRV.addItemDecoration(new ItemOffsetDecoration(10));
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());



        itemsListRV.setLayoutManager(linearLayoutManager);
        itemsListRV.addItemDecoration(new SpacesItemDecoration(3));
        Log.e("buildLayout", "buildLayout");
    }
    void postShoppingBuy(ShoppingItem item, long player_id){

        svcAth = Svc.initAuth(getContext());
        PostShoppingBuy postShoppingBuy = new PostShoppingBuy();
        postShoppingBuy.setDevice_id(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
        postShoppingBuy.setId(item.getId());
        if(item.getId() == 4){
            postShoppingBuy.setPlayer_id(player_id);
        }
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
                                messageDialog = new MessageDialog(getActivity(),getResources().getString(R.string.shopping)
                                        , getResources().getString(R.string.shopping_successfully), fragmentShopping);
                                messageDialog.show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
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
    void selectPlayerToBeHealth(){
        Log.e("selectPalyerToBeHealth","selectPalyerToBeHealth" );
    }

}