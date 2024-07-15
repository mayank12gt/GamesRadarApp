package com.example.gamesradar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamesradar.R;
import com.example.gamesradar.gamesfragments.listeners.GiveawayOnClickListener;
import com.example.gamesradar.model.game.Giveaway;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class GiveawaysAdapter extends RecyclerView.Adapter<GiveawaysAdapter.viewholder> {
    List<Giveaway> giveaway;
    Context context;
    GiveawayOnClickListener listener;

    public GiveawaysAdapter(List<Giveaway> giveaway, Context context, GiveawayOnClickListener listener) {
        this.giveaway = giveaway;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GiveawaysAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(context).
                inflate(R.layout.giveaway_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GiveawaysAdapter.viewholder holder, int position) {
        Giveaway giveaway = this.giveaway.get(position);
        Glide.with(context)
                .load(giveaway.getImage())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.giveaway_image);

        holder.giveaway_title.setText(giveaway.getTitle());
        if(giveaway.getWorth().equals("N/A")){
            holder.giveaway_worth.setVisibility(View.GONE);
        }
        else{
            holder.giveaway_worth.setVisibility(View.VISIBLE);
            holder.giveaway_worth.setText(giveaway.getWorth());
        }

        holder.giveaway_desc.setText(giveaway.getDescription());
        setupChips(holder, giveaway);
        holder.itemView.setOnClickListener(view -> {
        listener.onCick(giveaway);
        });

    }

    private void setupChips(viewholder holder, Giveaway giveaway) {
        holder.giveawayInfoChipGroup.removeAllViews();
        Chip giveawayTypeChip = (Chip) LayoutInflater.from(context).
                inflate(R.layout.single_chip_layout, holder.giveawayInfoChipGroup, false);

        giveawayTypeChip.setText(giveaway.getType());
        Chip platformChip = (Chip) LayoutInflater.from(context).
                inflate(R.layout.single_chip_layout, holder.giveawayInfoChipGroup, false);

        platformChip.setText(giveaway.getPlatforms());


        holder.giveawayInfoChipGroup.addView(giveawayTypeChip);
        holder.giveawayInfoChipGroup.addView(platformChip);
    }

    @Override
    public int getItemCount() {
        return giveaway.size();
    }

    public void updateGiveaways(List<Giveaway> giveaways){
        this.giveaway = giveaways;
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        RoundedImageView giveaway_image;
        TextView giveaway_title;
        TextView giveaway_worth;
        TextView giveaway_desc;

        ChipGroup giveawayInfoChipGroup;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            giveaway_image = itemView.findViewById(R.id.giveaway_image);
            giveaway_title = itemView.findViewById(R.id.giveaway_title);
            giveaway_worth = itemView.findViewById(R.id.giveaway_worth);
            giveaway_desc = itemView.findViewById(R.id.giveaway_desc);
            giveawayInfoChipGroup = itemView.findViewById(R.id.giveaway_info_chipgroup);

        }
    }
}
