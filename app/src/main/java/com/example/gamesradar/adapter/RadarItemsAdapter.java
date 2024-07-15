package com.example.gamesradar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamesradar.R;
import com.example.gamesradar.model.Radar.RadarItem;

import java.util.List;

public class RadarItemsAdapter extends RecyclerView.Adapter<RadarItemsAdapter.viewholder> {

    List<RadarItem> radarItemList;
    Context context;

    public RadarItemsAdapter(Context context,List<RadarItem> radarItemList) {
        this.radarItemList = radarItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RadarItemsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.radar_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RadarItemsAdapter.viewholder holder, int position) {
        RadarItem item = radarItemList.get(position);
        holder.title.setText(item.getTitle());
        holder.desc.setText(item.getDesc());
       // holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.green_1));

    }

    @Override
    public int getItemCount() {
        return radarItemList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView title;
        TextView desc;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            desc = itemView.findViewById(R.id.item_desc);
        }
    }
}
