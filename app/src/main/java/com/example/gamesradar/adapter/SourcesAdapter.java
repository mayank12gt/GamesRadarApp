package com.example.gamesradar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamesradar.R;
import com.example.gamesradar.newsfragment.OnAddButtonClicked;
import com.example.gamesradar.newsfragment.OnSourceItemClicked;
import com.example.gamesradar.newsfragment.SetDrawableCallback;
import com.example.gamesradar.model.news.Source;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.viewholder> {

    List<Source> sourceList;
    OnAddButtonClicked followBtnlistener;

    OnSourceItemClicked sourceItemClickedCallback;
    Context context;

    public SourcesAdapter(List<Source> sourceList, OnAddButtonClicked listener, OnSourceItemClicked sourceItemClickedCallback, Context context) {
        this.sourceList = sourceList;
        this.context = context;
        this.followBtnlistener = listener;
        this.sourceItemClickedCallback = sourceItemClickedCallback;
    }

    @NonNull
    @Override
    public SourcesAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.source_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SourcesAdapter.viewholder holder, int position) {
        Source source = sourceList.get(position);

        Glide.with(holder.itemView.getContext())
                .load(source.getImageUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.sourceImage);

        holder.sourceName.setText(source.getName());
        holder.sourceUrl.setText(source.getUrl());
        //setDrawable(holder.followBtn,source);
        if(source.getFollowed()){
            holder.followBtn.setImageDrawable(ContextCompat
                    .getDrawable(context, R.drawable.followed_icon));
        }else{
            holder.followBtn.setImageDrawable(ContextCompat
                    .getDrawable(context, R.drawable.add_icon));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sourceItemClickedCallback.onSourceItemClicked(source);
            }
        });



        holder.followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(source.getFollowed()==true){
                    Log.d("source","unfollowing");
                    source.setFollowed(false);
                }
                else{
                    Log.d("source","following");
                    source.setFollowed(true);
                }


                followBtnlistener.Clicked(source);

            }
        });

    }



    @Override
    public int getItemCount() {
        return sourceList.size();
    }

    public void update(List<Source> sourceList){
        this.sourceList = sourceList;
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        RoundedImageView sourceImage;
        ImageButton followBtn;
        TextView sourceName;
        TextView sourceUrl;
        CircularProgressIndicator loadingFollowedStatus;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            sourceImage = itemView.findViewById(R.id.source_Image);
            sourceName = itemView.findViewById(R.id.source_name);
            sourceUrl = itemView.findViewById(R.id.source_url);
            followBtn = itemView.findViewById(R.id.follow_btn);
            loadingFollowedStatus = itemView.findViewById(R.id.loading_followed_status);
        }
    }
}
