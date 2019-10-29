package com.androidsrc.futbolin.utils.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.database.models.ShoppingItem;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class ShoppingItemsAdapter extends RecyclerView.Adapter<ShoppingItemsAdapter.ViewHolder> {

    private List<ShoppingItem> items = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;


    public ShoppingItemsAdapter(ItemClickListener mClickListener, Context context, List<ShoppingItem> items) {
        this.mInflater = LayoutInflater.from(context);
        this.items = items;
        this.context = context;
        this.mClickListener = mClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_adapter_shopping_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        VectorChildFinder icon = null;
        if(position == 0){
            icon = new VectorChildFinder(context, R.drawable.ic_heart_empty, holder.iconImage);
        }else if(position ==1){
            icon = new VectorChildFinder(context, R.drawable.ic_heart_filled, holder.iconImage);
        }else if(position ==2){
            icon = new VectorChildFinder(context, R.drawable.ic_star_empty, holder.iconImage);
        }

        VectorDrawableCompat.VFullPath path = icon.findPathByName("color");
        path.setFillColor(Color.parseColor("#000000"));
        holder.iconImage.setMinimumHeight(60);
        holder.iconImage.setMaxHeight(60);
        holder.iconImage.invalidate();

        holder.title.setText(items.get(position).getName());
        holder.description.setText(items.get(position).getDescription());
        holder.price.setText("Fúlbos: " + items.get(position).getPrice());

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return items.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iconImage;
        TextView title, description, price;

        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.adapter_shopping_item_icon);
            title = itemView.findViewById(R.id.adapter_shopping_item_title);
            description = itemView.findViewById(R.id.adapter_shopping_item_description);
            price = itemView.findViewById(R.id.adapter_shopping_item_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    ShoppingItem getItem(int id) {
        return items.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}