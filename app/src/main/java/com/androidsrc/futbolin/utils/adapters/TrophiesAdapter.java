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
import com.androidsrc.futbolin.database.models.Trophy;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TrophiesAdapter extends RecyclerView.Adapter<TrophiesAdapter.ViewHolder> {

    private List<Trophy> trophies = new ArrayList<>();
    private LayoutInflater mInflater;
    private TrophiesAdapter.ItemClickListener mClickListener;
    Context context;


    public TrophiesAdapter(TrophiesAdapter.ItemClickListener mClickListener, Context context, List<Trophy> trophies) {
        this.mInflater = LayoutInflater.from(context);
        this.trophies = trophies;
        this.context = context;
        this.mClickListener = mClickListener;
        Log.e("TrophiesAdapter","TrophiesAdapter = " + trophies.size() + " " + trophies.toString());
    }


    @Override
    public TrophiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_adapter_trophies_team, parent, false);
        Log.e("onCreateViewHolder","position = ");
        return new TrophiesAdapter.ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(TrophiesAdapter.ViewHolder holder, int position) {

        Log.e("onBindViewHolder","position = " + position);
        VectorChildFinder shield;



        if(trophies.get(position).getPosition() == 1 ){
            shield = new VectorChildFinder(context, R.drawable.ic_trophy, holder.trophyImage);
            VectorDrawableCompat.VFullPath path21 = shield.findPathByName("color");
            path21.setFillColor(Color.parseColor("#ffd800"));

            holder.trophyImage.invalidate();
        }else if(trophies.get(position).getPosition() == 2 ){
            shield = new VectorChildFinder(context, R.drawable.ic_trophy, holder.trophyImage);
            VectorDrawableCompat.VFullPath path21 = shield.findPathByName("color");
            path21.setFillColor(Color.parseColor("#c0c0c0"));

            holder.trophyImage.invalidate();
        }else if(trophies.get(position).getPosition() == 3 ){
            shield = new VectorChildFinder(context, R.drawable.ic_trophy, holder.trophyImage);
            VectorDrawableCompat.VFullPath path21 = shield.findPathByName("color");
            path21.setFillColor(Color.parseColor("#ce7f32"));

            holder.trophyImage.invalidate();

        }else{
            shield = new VectorChildFinder(context, R.drawable.ic_trophy, holder.trophyImage);
            VectorDrawableCompat.VFullPath path21 = shield.findPathByName("color");
            path21.setFillColor(Color.parseColor("#80FFFFFF"));

            holder.trophyImage.invalidate();

        }
        final int positionfinal = position;
        holder.trophyPosition.setText(trophies.get(position).getPosition() + "°");
        holder.categoryButton.setText(trophies.get(position).getTournament_name());
        holder.categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mClickListener.onItemTrophyClick(v, positionfinal);
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return trophies.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView trophyImage;
        TextView trophyPosition;
        Button categoryButton;

        ViewHolder(View itemView) {
            super(itemView);
            trophyImage = itemView.findViewById(R.id.item_adapter_trophies_trophy_image);
            trophyPosition = itemView.findViewById(R.id.item_adapter_trophies_position_text);
            categoryButton = itemView.findViewById(R.id.item_adapter_trophies_category_button);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemTrophyClick(view, getAdapterPosition());
        }
    }


    Trophy getItem(int id) {
        return trophies.get(id);
    }

    void setClickListener(TrophiesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemTrophyClick(View view, int position);
    }
}