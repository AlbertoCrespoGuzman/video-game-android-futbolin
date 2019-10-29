package com.androidsrc.futbolin.utils.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidsrc.futbolin.R;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ShieldsAdapter extends RecyclerView.Adapter<ShieldsAdapter.ViewHolder> {

    private List<Integer> shields = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    int primaryColor;
    int secundaryColor;
    Context context;


    public ShieldsAdapter(ItemClickListener mClickListener, Context context, List<Integer> shields, int primaryColor, int secundaryColor) {
        this.mInflater = LayoutInflater.from(context);
        this.shields = shields;
        this.primaryColor = primaryColor;
        this.secundaryColor = secundaryColor;
        this.context = context;
        this.mClickListener = mClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_adapter_create_team_shields_picker, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        VectorChildFinder shield;

        shield = new VectorChildFinder(context, shields.get(position + 1), holder.shieldImage);

        VectorDrawableCompat.VFullPath path21 = shield.findPathByName("secundary_color1");
        path21.setFillColor(secundaryColor);
        VectorDrawableCompat.VFullPath path22 = shield.findPathByName("secundary_color2");
        path22.setFillColor(secundaryColor);
        VectorDrawableCompat.VFullPath path1 = shield.findPathByName("primary_color");
        path1.setFillColor(primaryColor);
        if((position + 1) == 8){
            VectorDrawableCompat.VFullPath path12 = shield.findPathByName("primary_color2");
            path12.setFillColor(primaryColor);
        }
        holder.shieldImage.invalidate();
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return shields.size() -1;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView shieldImage;


        ViewHolder(View itemView) {
            super(itemView);
            shieldImage = (ImageView) itemView.findViewById(R.id.item_adapter_create_team_shields_picker_shield_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    Integer getItem(int id) {
        return shields.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}