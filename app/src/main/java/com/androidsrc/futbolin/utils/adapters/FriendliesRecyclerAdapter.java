
package com.androidsrc.futbolin.utils.adapters;

        import android.content.Context;
        import android.graphics.Color;
        import android.os.Build;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.androidsrc.futbolin.R;
        import com.androidsrc.futbolin.activities.MainActivity;
        import com.androidsrc.futbolin.activities.fragments.FragmentFriendlies;
        import com.androidsrc.futbolin.communications.http.auth.get.FriendlyTeam;
        import com.androidsrc.futbolin.utils.Defaultdata;
        import com.devs.vectorchildfinder.VectorChildFinder;
        import com.devs.vectorchildfinder.VectorDrawableCompat;

        import java.util.ArrayList;
        import java.util.List;

        import androidx.recyclerview.widget.RecyclerView;

public class FriendliesRecyclerAdapter extends RecyclerView.Adapter<FriendliesRecyclerAdapter.ViewHolder> {

    private List<FriendlyTeam> teams = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;
    long user_id;
    FragmentFriendlies fragmentFriendlies;
    boolean areTeams;

    public FriendliesRecyclerAdapter(FragmentFriendlies fragmentFriendlies, ItemClickListener mClickListener, Context context, List<FriendlyTeam> teams, long user_id, boolean areTeams) {
        this.mInflater = LayoutInflater.from(context);
        this.teams = teams;
        this.context = context;
        this.user_id = user_id;
        this.fragmentFriendlies = fragmentFriendlies;
        this.mClickListener = mClickListener;
        this.areTeams = areTeams;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            view = mInflater.inflate(R.layout.adapter_friendlies_element, parent, false);
        } else{
            view = mInflater.inflate(R.layout.adapter_friendlies_element_old_versions, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final FriendlyTeam friendlyTeam = teams.get(position);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            if(friendlyTeam.getId() == user_id){
                Log.e("adapter", "friendlyTeam id " + friendlyTeam.getId() + " = id myTeam = " + user_id + " | name team " + friendlyTeam.getName());
                holder.eyeButton.setVisibility(View.INVISIBLE);
                holder.chartButton.setVisibility(View.INVISIBLE);
                holder.handshakeButton.setVisibility(View.INVISIBLE);
                holder.remainingText.setVisibility(View.INVISIBLE);
            }else{
                holder.eyeButton.setVisibility(View.VISIBLE);
                holder.chartButton.setVisibility(View.VISIBLE);
                holder.handshakeButton.setVisibility(View.VISIBLE);
                if (friendlyTeam.getPlayed() != null) {
                    holder.remainingText.setText(friendlyTeam.getPlayed());
                    holder.remainingText.setVisibility(View.VISIBLE);
                    holder.handshakeButton.setVisibility(View.INVISIBLE);
                }else{
                    holder.remainingText.setVisibility(View.INVISIBLE);
                }
            }
            holder.eyeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) fragmentFriendlies.getActivity()).changeToShowTeamFragment(friendlyTeam.getId(), friendlyTeam.getShort_name(), "FragmentFriendlies");
                }
            });

            holder.chartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentFriendlies.getgetTeam(friendlyTeam.getId());
                }
            });

            holder.handshakeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentFriendlies.postMatch(friendlyTeam);
                    if(areTeams){
                        holder.remainingText.setText("23h");
                        holder.remainingText.setVisibility(View.VISIBLE);
                        holder.handshakeButton.setVisibility(View.INVISIBLE);
                    }
                }
            });
            if (friendlyTeam.getPlayed() != null) {
                holder.remainingText.setText(friendlyTeam.getPlayed());
                holder.remainingText.setVisibility(View.VISIBLE);
                holder.handshakeButton.setVisibility(View.INVISIBLE);
            }
            holder.averageText.setText(teams.get(position).getAverage() + "");
            holder.averageText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentFriendlies.updateRecycleFriendliesByAverage();
                    fragmentFriendlies.hideKeyboard(holder.averageText);
                    if(areTeams){
                        fragmentFriendlies.deleteSearchText();
                    }

                }
            });


            holder.teamNameText.setText(teams.get(position).getName());
            holder.trainerNameText.setText(teams.get(position).getUser_name());

            VectorChildFinder shieldLocalVector = new VectorChildFinder(context,
                    Defaultdata.shields.get(teams.get(position).getShield()), holder.shieldImage);
            VectorDrawableCompat.VFullPath path21Local_ = shieldLocalVector.findPathByName("secundary_color1");
            path21Local_.setFillColor(Color.parseColor(teams.get(position).getSecondary_color()));
            VectorDrawableCompat.VFullPath path22Local_ = shieldLocalVector.findPathByName("secundary_color2");
            path22Local_.setFillColor(Color.parseColor(teams.get(position).getSecondary_color()));
            VectorDrawableCompat.VFullPath path1Local_ = shieldLocalVector.findPathByName("primary_color");
            path1Local_.setFillColor(Color.parseColor(teams.get(position).getPrimary_color()));
            holder.shieldImage.invalidate();
        } else{
            if(friendlyTeam.getId() == user_id){
                holder.eyeButtonI.setVisibility(View.INVISIBLE);
                holder.chartButtonI.setVisibility(View.INVISIBLE);
                holder.handshakeButtonI.setVisibility(View.INVISIBLE);
            }
            holder.eyeButtonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) fragmentFriendlies.getActivity()).changeToShowTeamFragment(friendlyTeam.getId(), friendlyTeam.getShort_name(), "FragmentFriendlies");
                }
            });

            holder.chartButtonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentFriendlies.getgetTeam(friendlyTeam.getId());
                }
            });

            holder.handshakeButtonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentFriendlies.postMatch(friendlyTeam);
                    if(areTeams){
                        holder.remainingText.setText("23h");
                        holder.remainingText.setVisibility(View.VISIBLE);
                        holder.handshakeButtonI.setVisibility(View.INVISIBLE);
                    }
                }
            });
            if (friendlyTeam.getPlayed() != null) {
                holder.remainingText.setText(friendlyTeam.getPlayed());
                holder.remainingText.setVisibility(View.VISIBLE);
                holder.handshakeButtonI.setVisibility(View.INVISIBLE);
            }
            holder.averageText.setText(teams.get(position).getAverage() + "");
            holder.teamNameText.setText(teams.get(position).getName());
            holder.trainerNameText.setText(teams.get(position).getUser_name());

            VectorChildFinder shieldLocalVector = new VectorChildFinder(context,
                    Defaultdata.shields.get(teams.get(position).getShield()), holder.shieldImage);
            VectorDrawableCompat.VFullPath path21Local_ = shieldLocalVector.findPathByName("secundary_color1");
            path21Local_.setFillColor(Color.parseColor(teams.get(position).getSecondary_color()));
            VectorDrawableCompat.VFullPath path22Local_ = shieldLocalVector.findPathByName("secundary_color2");
            path22Local_.setFillColor(Color.parseColor(teams.get(position).getSecondary_color()));
            VectorDrawableCompat.VFullPath path1Local_ = shieldLocalVector.findPathByName("primary_color");
            path1Local_.setFillColor(Color.parseColor(teams.get(position).getPrimary_color()));
            holder.shieldImage.invalidate();
        }


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return teams.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button eyeButton, chartButton, handshakeButton;
        ImageButton eyeButtonI, chartButtonI, handshakeButtonI;
        TextView averageText, teamNameText, trainerNameText, remainingText;
        ImageView shieldImage;

        ViewHolder(View itemView) {
                super(itemView);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                eyeButton = itemView.findViewById(R.id.adapter_friendlies_eye);
                chartButton = itemView.findViewById(R.id.adapter_friendlies_charts);
                handshakeButton = itemView.findViewById(R.id.adapter_friendlies_handshake);
            } else{
                eyeButtonI = itemView.findViewById(R.id.adapter_friendlies_eye);
                chartButtonI = itemView.findViewById(R.id.adapter_friendlies_charts);
                handshakeButtonI = itemView.findViewById(R.id.adapter_friendlies_handshake);
            }



                averageText = itemView.findViewById(R.id.adapter_friendlies_team_average);
                teamNameText = itemView.findViewById(R.id.adapter_friendlies_team_name);
                trainerNameText = itemView.findViewById(R.id.adapter_friendlies_trainer_name);
                shieldImage = itemView.findViewById(R.id.adapter_friendlies_shield);
                remainingText = itemView.findViewById(R.id.adapter_friendlies_remaining);

                itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    FriendlyTeam getItem(int id) {
        return teams.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}