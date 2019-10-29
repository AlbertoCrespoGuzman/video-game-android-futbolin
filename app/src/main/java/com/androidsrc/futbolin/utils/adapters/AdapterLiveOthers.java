
package com.androidsrc.futbolin.utils.adapters;

        import android.content.Context;
        import android.graphics.Color;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.androidsrc.futbolin.R;
        import com.androidsrc.futbolin.communications.http.auth.get.MatchLive;
        import com.androidsrc.futbolin.database.models.Trophy;
        import com.androidsrc.futbolin.utils.Defaultdata;
        import com.devs.vectorchildfinder.VectorChildFinder;
        import com.devs.vectorchildfinder.VectorDrawableCompat;

        import java.util.ArrayList;
        import java.util.List;

        import androidx.core.content.ContextCompat;
        import androidx.recyclerview.widget.RecyclerView;

public class AdapterLiveOthers extends RecyclerView.Adapter<AdapterLiveOthers.ViewHolder> {

    private List<MatchLive> matchLives = new ArrayList<>();
    private LayoutInflater mInflater;
    Context context;


    public AdapterLiveOthers( Context context, List<MatchLive> matchLives) {
        this.mInflater = LayoutInflater.from(context);
        this.matchLives = matchLives;
        this.context = context;

    }


    @Override
    public AdapterLiveOthers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_adapter_live_others, parent, false);
        return new AdapterLiveOthers.ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(AdapterLiveOthers.ViewHolder holder, int position) {


        VectorChildFinder shieldLocalVector = new VectorChildFinder(context,
                Defaultdata.shields.get(matchLives.get(position).getLocal().getShield()), holder.localImage);
        VectorDrawableCompat.VFullPath path21Local_ = shieldLocalVector.findPathByName("secundary_color1");
        path21Local_.setFillColor(Color.parseColor(matchLives.get(position).getLocal().getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Local_ = shieldLocalVector.findPathByName("secundary_color2");
        path22Local_.setFillColor(Color.parseColor(matchLives.get(position).getLocal().getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Local_ = shieldLocalVector.findPathByName("primary_color");
        path1Local_.setFillColor(Color.parseColor(matchLives.get(position).getLocal().getPrimary_color()));
        holder.localImage.invalidate();


        VectorChildFinder shieldVisitVector = new VectorChildFinder(context
                , Defaultdata.shields.get(matchLives.get(position).getVisit().getShield()), holder.visitImage);
        VectorDrawableCompat.VFullPath path21Visit_ = shieldVisitVector.findPathByName("secundary_color1");
        path21Visit_.setFillColor(Color.parseColor(matchLives.get(position).getVisit().getSecondary_color()));
        VectorDrawableCompat.VFullPath path22Visit_ = shieldVisitVector.findPathByName("secundary_color2");
        path22Visit_.setFillColor(Color.parseColor(matchLives.get(position).getVisit().getSecondary_color()));
        VectorDrawableCompat.VFullPath path1Visit_ = shieldVisitVector.findPathByName("primary_color");
        path1Visit_.setFillColor(Color.parseColor(matchLives.get(position).getVisit().getPrimary_color()));
        holder.visitImage.invalidate();

        holder.scoreText.setText(matchLives.get(position).getScorersAccount().getResultGoal());
        holder.localName.setText(matchLives.get(position).getLocal().getName());
        holder.visitName.setText(matchLives.get(position).getVisit().getName());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return matchLives.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView localImage, visitImage;
        TextView scoreText, localName, visitName;

        ViewHolder(View itemView) {
            super(itemView);
            localImage = itemView.findViewById(R.id.adapter_live_others_local_shield);
            visitImage = itemView.findViewById(R.id.adapter_live_others_visit_shield);
            scoreText = itemView.findViewById(R.id.adapter_live_others_score);
            localName = itemView.findViewById(R.id.adapter_live_local_name);
            visitName = itemView.findViewById(R.id.adapter_live_visit_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }


    MatchLive getItem(int id) {
        return matchLives.get(id);
    }

    void setClickListener(TrophiesAdapter.ItemClickListener itemClickListener) {
    }

    public interface ItemClickListener {
        void onItemTrophyClick(View view, int position);
    }
}