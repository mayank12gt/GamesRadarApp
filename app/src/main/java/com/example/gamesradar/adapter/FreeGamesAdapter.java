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
import com.example.gamesradar.gamesfragments.listeners.FreeGameOnClickListener;
import com.example.gamesradar.model.game.FreeGame;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class FreeGamesAdapter extends RecyclerView.Adapter<FreeGamesAdapter.viewholder> {

    List<FreeGame> freeGame;
    Context context;
    FreeGameOnClickListener listener;

    public FreeGamesAdapter(List<FreeGame> freeGame, Context context, FreeGameOnClickListener listener) {
        this.freeGame = freeGame;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FreeGamesAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.freegameitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FreeGamesAdapter.viewholder holder, int position) {
        FreeGame game = freeGame.get(position);
        Glide.with(context)
                .load(game.getThumbnail())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder).into(holder.game_image);
        holder.game_title.setText(game.getTitle());
        holder.game_desc.setText(game.getShortDescription());
        holder.itemView.setOnClickListener(view -> {
                listener.onClick(game);
        });


        setupChips(holder,game);



    }

    private void setupChips(viewholder holder, FreeGame game) {
        holder.gameInfoChipGroup.removeAllViews();
        Chip chipGenre = (Chip) LayoutInflater.from(context).
                inflate(R.layout.single_chip_layout, holder.gameInfoChipGroup, false);

        chipGenre.setText(game.getGenre());
        Chip chipPlatform = (Chip) LayoutInflater.from(context).
                inflate(R.layout.single_chip_layout, holder.gameInfoChipGroup, false);

        chipPlatform.setText(game.getPlatform());

        holder.gameInfoChipGroup.addView(chipGenre);
        holder.gameInfoChipGroup.addView(chipPlatform);


    }

    @Override
    public int getItemCount() {
        return freeGame.size();
    }
    public void updateGames(List<FreeGame> gamesList){
        this.freeGame =gamesList;
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        RoundedImageView game_image;
        TextView game_title;
        TextView game_desc;
        ChipGroup gameInfoChipGroup;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            game_image = itemView.findViewById(R.id.game_image);
            game_title = itemView.findViewById(R.id.game_title);
            game_desc = itemView.findViewById(R.id.game_desc);
            gameInfoChipGroup= itemView.findViewById(R.id.game_info_chipgroup);

        }
    }
}
