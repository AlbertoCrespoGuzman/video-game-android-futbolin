package com.androidsrc.futbolin.utils.adapters;


        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

        import com.androidsrc.futbolin.R;
        import com.androidsrc.futbolin.communications.http.auth.get.CategoryPositions;
        import com.androidsrc.futbolin.database.models.Trophy;
        import com.devs.vectorchildfinder.VectorChildFinder;
        import com.devs.vectorchildfinder.VectorDrawableCompat;

        import java.util.ArrayList;
        import java.util.List;

        import androidx.recyclerview.widget.RecyclerView;

public class AdapterClassificationTable extends RecyclerView.Adapter<AdapterClassificationTable.ViewHolder> {

    private List<CategoryPositions> categoryPositions = new ArrayList<>();
    private LayoutInflater mInflater;
    Context context;
    long team_id;

    public AdapterClassificationTable(Context context, List<CategoryPositions> categoryPositions, long team_id) {
        this.mInflater = LayoutInflater.from(context);
        this.categoryPositions = categoryPositions;
        this.context = context;
        this.team_id = team_id;
    }


    @Override
    public AdapterClassificationTable.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_classification_table_element, parent, false);
        return new AdapterClassificationTable.ViewHolder(view);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(AdapterClassificationTable.ViewHolder holder, int position) {

        VectorChildFinder shield;

        if(position !=0){
            if(position % 2 != 0){
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.azul_suave));
            }
            if(categoryPositions.get(position - 1).getTeam_id() == team_id){
                holder.layout.setBackgroundColor(context.getResources().getColor(R.color.futbolinAzul));
                holder.position.setTextColor(context.getResources().getColor(R.color.white));
                holder.teamName.setTextColor(context.getResources().getColor(R.color.white));
                holder.points.setTextColor(context.getResources().getColor(R.color.white));
            }
            holder.position.setText(position + "");

            String color = "#FFFFFF";
            int drawable = 0;

            if(categoryPositions.get(position - 1).getLast_position() == position){
                color= "#a79899";
                drawable = R.drawable.ic_angle_right;
            }else if(categoryPositions.get(position - 1).getLast_position() > position){
                color = "#219800";
                drawable =R.drawable.ic_angle_up;
            }else if(categoryPositions.get(position - 1).getLast_position() < position){
                color = "#9e0913";
                drawable =R.drawable.ic_angle_down;
            }

            shield = new VectorChildFinder(context, drawable, holder.updownImage);
            VectorDrawableCompat.VFullPath path21 = shield.findPathByName("color");
            path21.setFillColor(Color.parseColor(color));

            holder.updownImage.invalidate();

            holder.teamName.setText(categoryPositions.get(position - 1).getTeam_name());
            holder.points.setText(categoryPositions.get(position - 1).getPoints() + "");
            if(holder.position.getText().length() != 2) holder.position.setText(holder.position.getText().toString() + "  ");

        }else{
            holder.position.setText(context.getResources().getString(R.string.position));
            holder.position.setTypeface(null, Typeface.BOLD);
            holder.teamName.setTypeface(null, Typeface.BOLD);
            holder.points.setTypeface(null, Typeface.BOLD);
            holder.teamName.setText(context.getResources().getString(R.string.team));
            holder.teamName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            holder.points.setText(context.getResources().getString(R.string.points));
            holder.updownImage.setVisibility(View.INVISIBLE);
        }


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return categoryPositions.size() + 1;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView updownImage;
        TextView position, teamName, points;
        RelativeLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            updownImage = itemView.findViewById(R.id.adapter_classification_table_icon_updown);
            position = itemView.findViewById(R.id.adapter_classification_table_position);
            teamName = itemView.findViewById(R.id.adapter_classification_table_team_name);
            points = itemView.findViewById(R.id.adapter_classification_table_points);
            layout = itemView.findViewById(R.id.adapter_classification_table_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }


    CategoryPositions getItem(int id) {
        return categoryPositions.get(id);
    }


    public interface ItemClickListener {
        void onItemTrophyClick(View view, int position);
    }
}