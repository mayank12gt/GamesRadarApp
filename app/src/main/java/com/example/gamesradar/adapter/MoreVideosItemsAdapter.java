package com.example.gamesradar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamesradar.R;
import com.example.gamesradar.model.Radar.YTParser.Entry;
import com.example.gamesradar.radarfragments.OnVideoItemClick;

import com.example.gamesradar.util.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class MoreVideosItemsAdapter extends RecyclerView.Adapter<MoreVideosItemsAdapter.viewholder> {
    List<Entry> videos;
    OnVideoItemClick clickListener;
    Context context;

    public MoreVideosItemsAdapter(List<Entry> videos, Context context) {
        this.videos = videos;
        this.context = context;

    }





    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.more_videos_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Entry video = videos.get(position);

        Glide.with(context).load(video.getMediaGroup().getMediaThumbnail().getUrl()).into(holder.videoImage);
        holder.videoTitle.setText(video.getMediaGroup().getMediaTitle());
        holder.videoDesc.setText(video.getMediaGroup().getMediaDescription());
        holder.videoDesc.setVisibility(View.GONE);
        Log.d("channel",video.getAuthor().getName());
        holder.videoChannel.setText(video.getAuthor().getName());
        holder.videoPubDate.setText(Utils.formatYTFeedDate(video.getPublished()));
        holder.videoPubDate.setVisibility(View.VISIBLE);
        holder.videoViews.setText
                (String.valueOf(video.getMediaGroup().getMediaCommunity().getStatistics().getViews())+" views");

        holder.itemView.setOnClickListener(view -> {
           // clickListener.onVideoItemClick(video);
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void update(List<Entry> videoList){
        this.videos = videoList;
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        RoundedImageView videoImage;
        TextView videoTitle;
        TextView videoDesc;
        TextView videoPubDate;
        TextView videoViews;
        TextView videoChannel;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            videoImage = itemView.findViewById(R.id.video_image);
            videoTitle = itemView.findViewById(R.id.video_title);
            videoDesc = itemView.findViewById(R.id.video_desc);
            videoPubDate = itemView.findViewById(R.id.video_date);
            videoViews = itemView.findViewById(R.id.video_views);
            videoChannel = itemView.findViewById(R.id.video_channel);

        }
    }
}
