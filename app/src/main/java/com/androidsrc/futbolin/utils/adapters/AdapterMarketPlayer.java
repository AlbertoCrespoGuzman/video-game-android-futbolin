

package com.androidsrc.futbolin.utils.adapters;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.Build;
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
        import com.androidsrc.futbolin.activities.fragments.FragmentFriendlies;
        import com.androidsrc.futbolin.activities.fragments.FragmentMarket;
        import com.androidsrc.futbolin.communications.Svc;
        import com.androidsrc.futbolin.communications.SvcApi;
        import com.androidsrc.futbolin.communications.http.auth.get.FriendlyTeam;
        import com.androidsrc.futbolin.communications.http.auth.get.ResponsePostFollow;
        import com.androidsrc.futbolin.communications.http.auth.get.getPlayer;
        import com.androidsrc.futbolin.communications.http.auth.get.getUser;
        import com.androidsrc.futbolin.communications.http.auth.get.market.MarketObject;
        import com.androidsrc.futbolin.communications.http.auth.post.PostMarketFollow;
        import com.androidsrc.futbolin.database.manager.DatabaseManager;
        import com.androidsrc.futbolin.database.models.Player;
        import com.androidsrc.futbolin.database.models.TeamDB;
        import com.androidsrc.futbolin.utils.Defaultdata;
        import com.androidsrc.futbolin.utils.dialogs.ImproveDialog;
        import com.androidsrc.futbolin.utils.dialogs.PlayerDialog;
        import com.devs.vectorchildfinder.VectorChildFinder;
        import com.devs.vectorchildfinder.VectorDrawableCompat;
        import com.github.mikephil.charting.animation.Easing;
        import com.github.mikephil.charting.charts.RadarChart;
        import com.github.mikephil.charting.components.AxisBase;
        import com.github.mikephil.charting.components.XAxis;
        import com.github.mikephil.charting.components.YAxis;
        import com.github.mikephil.charting.data.RadarData;
        import com.github.mikephil.charting.data.RadarEntry;
        import com.github.mikephil.charting.data.RadarDataSet;
        import com.github.mikephil.charting.formatter.IAxisValueFormatter;

        import java.text.DecimalFormat;
        import java.util.ArrayList;
        import java.util.List;

        import androidx.appcompat.app.AppCompatDialog;
        import androidx.recyclerview.widget.RecyclerView;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class AdapterMarketPlayer extends RecyclerView.Adapter<AdapterMarketPlayer.ViewHolder> implements ImproveDialog.DialogImproveClickListener {

    private List<MarketObject> players = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    FragmentMarket fragmentMarket;
    DecimalFormat decimalFormat;
    String pattern;
    DatabaseManager db;
    ImproveDialog improveDialog;
    AppCompatDialog dialog;
    long team_id;
    MarketObject currentPlayer;
    AdapterMarketPlayer adapterMarketPlayer;

    public AdapterMarketPlayer(FragmentMarket fragmentMarket, ItemClickListener mClickListener, Context context, List<MarketObject> players, long team_id) {
        this.mInflater = LayoutInflater.from(context);
        this.players = players;
        this.context = context;
        this.fragmentMarket = fragmentMarket;
        this.mClickListener = mClickListener;
        this.team_id = team_id;
        this.adapterMarketPlayer = this;
        pattern = "###,###,###,###";
        decimalFormat = new DecimalFormat(pattern);
        db = new DatabaseManager(context);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view = mInflater.inflate(R.layout.adapter_recycler_market_player_element, parent, false);
        }else{
            view = mInflater.inflate(R.layout.adapter_recycler_market_player_element_old_versions, parent, false);
        }

        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final MarketObject player = players.get(position);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.eyeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentMarket.getPlayer(player);
                }
            });
            if (player.getPlayer().getTeam_id() != team_id) {
                if (player.isFollowing()) {
                    holder.followingButton.setVisibility(View.VISIBLE);
                    holder.notFollowingButton.setVisibility(View.INVISIBLE);
                } else {
                    holder.followingButton.setVisibility(View.INVISIBLE);
                    holder.notFollowingButton.setVisibility(View.VISIBLE);

                }
                holder.followingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postMarketUnfollow(player, holder.followingButton, holder.notFollowingButton);
                    }
                });
                holder.notFollowingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("Not folloing button", "eeee");
                        postMarketFollow(player, holder.followingButton, holder.notFollowingButton);
                    }
                });
                holder.offerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPlayer(player);
                    }
                });
            } else {
                holder.offerButton.setVisibility(View.INVISIBLE);
                holder.followingButton.setVisibility(View.INVISIBLE);
                holder.notFollowingButton.setVisibility(View.INVISIBLE);
            }
        }else{
            holder.eyeButtonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentMarket.getPlayer(player);
                }
            });
            if (player.getPlayer().getTeam_id() != team_id) {
                if (player.isFollowing()) {
                    holder.followingButton.setVisibility(View.VISIBLE);
                    holder.notFollowingButtonI.setVisibility(View.INVISIBLE);
                } else {
                    holder.followingButton.setVisibility(View.INVISIBLE);
                    holder.notFollowingButtonI.setVisibility(View.VISIBLE);

                }
                holder.followingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postMarketUnfollow(player, holder.followingButton, holder.notFollowingButtonI);
                    }
                });
                holder.notFollowingButtonI.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postMarketFollow(player, holder.followingButton, holder.notFollowingButtonI);
                    }
                });
                holder.offerButtonI.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getPlayer(player);
                    }
                });
            } else {
                holder.offerButton.setVisibility(View.INVISIBLE);
                holder.followingButton.setVisibility(View.INVISIBLE);
                holder.notFollowingButton.setVisibility(View.INVISIBLE);
            }
        }
       // DatabaseManager db = new DatabaseManager(context);
        if(db.findPlayerById(player.getPlayer().getId()) == null){
            getPlayerBackground(player, holder.chart);
        }else{
            Defaultdata.loadRadarPlayer(db.findPlayerById(player.getPlayer().getId()), holder.chart, context);
        }
        holder.averageText.setText(players.get(position).getPlayer().getAverage() + "");
        holder.averageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMarket.updateRecyclerMarketByAverage();
            }
        });
        holder.playerNameText.setText(players.get(position).getPlayer().getName());
        if(players.get(position).getOffer_team() != 0){
            holder.bestOfferText.setText(decimalFormat.format(players.get(position).getOffer_value())
                    + " $ (" + getTeamName(players.get(position).getOffer_team()) + ")");
        }else{
            holder.bestOfferText.setText(context.getResources().getString(R.string.no_offer));
        }
        switch (player.getPlayer().getPosition()){
            case "ARQ":
                holder.arqImage.setVisibility(View.VISIBLE);
                holder.defImage.setVisibility(View.INVISIBLE);
                holder.medImage.setVisibility(View.INVISIBLE);
                holder.ataImage.setVisibility(View.INVISIBLE);
                holder.positionText.setText(players.get(position).getPlayer().getPosition());
                holder.positionText.setTextColor(context.getResources().getColor(R.color.trophy_first_color));
                break;
            case "DEF":
                holder.arqImage.setVisibility(View.INVISIBLE);
                holder.defImage.setVisibility(View.VISIBLE);
                holder.medImage.setVisibility(View.INVISIBLE);
                holder.ataImage.setVisibility(View.INVISIBLE);
                holder.positionText.setText(players.get(position).getPlayer().getPosition());
                holder.positionText.setTextColor(context.getResources().getColor(R.color.def_text_color));
                break;
            case "MED":
                holder.arqImage.setVisibility(View.INVISIBLE);
                holder.defImage.setVisibility(View.INVISIBLE);
                holder.medImage.setVisibility(View.VISIBLE);
                holder.ataImage.setVisibility(View.INVISIBLE);
                holder.positionText.setText(players.get(position).getPlayer().getPosition());
                holder.positionText.setTextColor(context.getResources().getColor(R.color.med_text_color));
                break;
            case "ATA":
                holder.arqImage.setVisibility(View.INVISIBLE);
                holder.defImage.setVisibility(View.INVISIBLE);
                holder.medImage.setVisibility(View.INVISIBLE);
                holder.ataImage.setVisibility(View.VISIBLE);
                holder.positionText.setText(players.get(position).getPlayer().getPosition());
                holder.positionText.setTextColor(context.getResources().getColor(R.color.ata_text_color));
                break;
        }
        for(String icon : players.get(position).getPlayer().getIcons()){
            switch (icon) {
                case "transferable":
                    holder.transferableImage.setVisibility(View.VISIBLE);
                    break;
                case "retiring":
                    holder.retiringImage.setVisibility(View.VISIBLE);
                    break;
                case "yellow_cards":
                    holder.yellow_cardsImage.setVisibility(View.VISIBLE);
                    break;
                case "red_card":
                    holder.red_cardImage.setVisibility(View.VISIBLE);
                    break;
                case "injured":
                    holder.injuredImage.setVisibility(View.VISIBLE);
                    break;
                case "healed":
                    holder.healedImage.setVisibility(View.VISIBLE);
                    break;
                case "upgraded":
                    holder.upgradedImage.setVisibility(View.VISIBLE);
                    break;
                case "tired":
                    holder.tiredImage.setVisibility(View.VISIBLE);
                    break;
            }

        }

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return players.size();
    }

    @Override
    public void onDialogImproveButtonClick(boolean success, long value, boolean follow) {

        if(!follow){
            if (improveDialog != null && improveDialog.isShowing()) {
                improveDialog.dismiss();
            }

                if (success) {
                    currentPlayer.setValue((int) value);
                    fragmentMarket.updatePlayer(currentPlayer);
                    getUser user = ((MainActivity)context).getUser();
                    user.getUser().getTeam().setSpending_margin((user.getUser().getTeam().getSpending_margin() - value));
                    ((MainActivity) context).setUser(user);
                    ((MainActivity) context).changeToMarketFragment();

                }

        }else{
            if(improveDialog != null && improveDialog.isShowing()){
                improveDialog.dismiss();
            }
            currentPlayer.setFollowing(!currentPlayer.isFollowing());
            fragmentMarket.updatePlayer(currentPlayer);
        }



    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button eyeButton, notFollowingButton, offerButton;
        ImageView eyeButtonI, notFollowingButtonI, offerButtonI;
        TextView averageText, playerNameText, bestOfferText, positionText;
        ImageView arqImage, defImage, medImage, ataImage, transferableImage, retiringImage, yellow_cardsImage, followingButton;
        ImageView red_cardImage, injuredImage, healedImage, upgradedImage, tiredImage ;
        RadarChart chart;

        ViewHolder(View itemView) {
            super(itemView);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                eyeButton = itemView.findViewById(R.id.adapter_market_player_eye);
                notFollowingButton = itemView.findViewById(R.id.adapter_market_player_not_following);
                offerButton = itemView.findViewById(R.id.adapter_market_player_offer);
            }else{
                eyeButtonI = itemView.findViewById(R.id.adapter_market_player_eye);
                notFollowingButtonI = itemView.findViewById(R.id.adapter_market_player_not_following);
                offerButtonI = itemView.findViewById(R.id.adapter_market_player_offer);
            }
            followingButton = itemView.findViewById(R.id.adapter_market_player_following);

            averageText = itemView.findViewById(R.id.adapter_market_player_average);
            playerNameText = itemView.findViewById(R.id.adapter_market_player_name);

            bestOfferText = itemView.findViewById(R.id.adapter_market_player_best_offer);
            arqImage = itemView.findViewById(R.id.adapter_market_position_image_arq);
            defImage = itemView.findViewById(R.id.adapter_market_position_image_def);
            medImage = itemView.findViewById(R.id.adapter_market_position_image_med);
            ataImage = itemView.findViewById(R.id.adapter_market_position_image_ata);
            transferableImage = itemView.findViewById(R.id.adapter_market_player_transferable);
            retiringImage = itemView.findViewById(R.id.adapter_market_player_retiring);
            yellow_cardsImage = itemView.findViewById(R.id.adapter_market_player_yellow_cards);
            red_cardImage = itemView.findViewById(R.id.adapter_market_player_red_card);
            injuredImage = itemView.findViewById(R.id.adapter_market_player_injured);
            healedImage = itemView.findViewById(R.id.adapter_market_player_healed);
            upgradedImage = itemView.findViewById(R.id.adapter_market_player_upgraded);
            tiredImage = itemView.findViewById(R.id.adapter_market_player_tired);
            positionText = itemView.findViewById(R.id.adapter_market_position_text);
            chart = itemView.findViewById(R.id.adapter_market_player_chart);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    MarketObject getItem(int id) {
        return players.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public String getTeamName(long id){
        String text = "";

        if(db == null){
            db = new DatabaseManager(context);
        }

        TeamDB teamDB = db.findTeamDBById(id);
        if(teamDB != null){
            text = teamDB.getShort_name();
        }
        return text;
    }


    public void postMarketFollow(MarketObject marketObject, final View followingButton, final View notFollowingButton){

        final MarketObject marketObject1 = marketObject;
        SvcApi svc;

        svc = Svc.initAuth(context);

        PostMarketFollow postMarketFollow = new PostMarketFollow();
        postMarketFollow.setDevice_id(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        postMarketFollow.setId(marketObject.getId());
        Log.e("device id",postMarketFollow.getDevice_id());
        Log.e("transfer id",postMarketFollow.getId() + "");
        Call<ResponsePostFollow> call = svc.postMarketFollow(postMarketFollow);

        call.enqueue(new Callback<ResponsePostFollow>() {
            @Override
            public void onResponse(Call<ResponsePostFollow> call, Response<ResponsePostFollow> response) {

                final Response<ResponsePostFollow> responsefinal = response;

                if (response.isSuccessful()) {
                    ((MainActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, Follow player = " + responsefinal);
                            if(responsefinal.code() == 400){
                                Toast.makeText(context, context.getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200|| responsefinal.code() == 204){
                                Toast.makeText(context, context.getResources().getString(R.string.following_to) +" " + marketObject1.getPlayer().getName(), Toast.LENGTH_SHORT).show();
                                marketObject1.setFollowing(!marketObject1.isFollowing());
                                followingButton.setVisibility(View.VISIBLE);
                                notFollowingButton.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(context, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            context.startActivity(mainActivity);
                            ((MainActivity)context).finish();
                        }else{
                            Toast.makeText(context, context.getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(context, context.getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostFollow> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }


    public void postMarketUnfollow(MarketObject marketObject, final View followingButton, final View notFollowingButton){

        final MarketObject marketObject1 = marketObject;
        SvcApi svc;

        svc = Svc.initAuth(context);

        PostMarketFollow postMarketFollow = new PostMarketFollow();
        postMarketFollow.setDevice_id(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        postMarketFollow.setId(marketObject.getId());
        Log.e("device id",postMarketFollow.getDevice_id());
        Log.e("transfer id",postMarketFollow.getId() + "");
        Call<ResponsePostFollow> call = svc.postMarketUnfollow(postMarketFollow);

        call.enqueue(new Callback<ResponsePostFollow>() {
            @Override
            public void onResponse(Call<ResponsePostFollow> call, Response<ResponsePostFollow> response) {

                final Response<ResponsePostFollow> responsefinal = response;

                if (response.isSuccessful()) {
                    ((MainActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, Follow player = " + responsefinal);
                            if(responsefinal.code() == 400){
                                Toast.makeText(context, context.getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200|| responsefinal.code() == 204){
                                Toast.makeText(context, context.getResources().getString(R.string.no_following) + " " + marketObject1.getPlayer().getName(), Toast.LENGTH_SHORT).show();
                                marketObject1.setFollowing(!marketObject1.isFollowing());
                                notFollowingButton.setVisibility(View.VISIBLE);
                                followingButton.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(context, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            context.startActivity(mainActivity);
                            ((MainActivity)context).finish();
                        }else{
                            Toast.makeText(context, context.getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(context, context.getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponsePostFollow> call, Throwable t) {
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
        dialog = Defaultdata.loadDialog(dialog, context.getResources().getString(R.string.wait),
                context.getResources().getString(R.string.loading_data_from) + player.getPlayer().getName() , context);
        dialog.show();

        SvcApi svc;
        svc = Svc.initAuth(context);
        Call<getPlayer> call = svc.getPlayer(player_id_f,Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getPlayer>() {
            @Override
            public void onResponse(Call<getPlayer> call, Response<getPlayer> response) {

                final Response<getPlayer> responsefinal = response;

                if(dialog != null && dialog.isShowing()){
                    dialog.dismiss();
                    dialog = null;
                }

                if (response.isSuccessful()) {
                    ((MainActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMe = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                Player player1 = responsefinal.body().getPlayer();
                                Log.e("Response player", player1.toString());
                                currentPlayer = player;
                                db.savePlayer(player1);
                                improveDialog = new ImproveDialog((MainActivity)context, player1, adapterMarketPlayer, true, player);
                                improveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                improveDialog.show();

                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(context, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            ((MainActivity)context).startActivity(mainActivity);
                            ((MainActivity)context).finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(context,
                                context.getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getPlayer> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public void getPlayerBackground(final MarketObject player, final RadarChart radarChart){

        final long player_id_f = player.getPlayer().getId();

        SvcApi svc;
        svc = Svc.initAuth(context);
        Call<getPlayer> call = svc.getPlayer(player_id_f,Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        call.enqueue(new Callback<getPlayer>() {
            @Override
            public void onResponse(Call<getPlayer> call, Response<getPlayer> response) {

                final Response<getPlayer> responsefinal = response;

                if (response.isSuccessful()) {
                    ((MainActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Success, getMe = " + responsefinal);
                            if(responsefinal.code() == 400){

                            }else if(responsefinal.code() == 201 || responsefinal.code() == 200){
                                Player player1 = responsefinal.body().getPlayer();
                                Log.e("Response player", player1.toString());
                                currentPlayer = player;
                                db.savePlayer(player1);
                                Defaultdata.loadRadarPlayer(db.findPlayerById(currentPlayer.getPlayer().getId()), radarChart, context);
                            }
                        }
                    });
                } else {
                    try {
                        String error = response.errorBody().string();

                        if(error.contains("live_match") && error.contains("Broadcasting live match")){
                            Intent mainActivity = new Intent(context, MainActivity.class);
                            //   mainActivity.putExtra("user", null);
                            ((MainActivity)context).startActivity(mainActivity);
                            ((MainActivity)context).finish();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    if(responsefinal.code() == 500|| responsefinal.code() == 503){
                        Toast.makeText(context,
                                context.getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<getPlayer> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}