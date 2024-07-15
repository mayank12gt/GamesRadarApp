package com.example.gamesradar.adapter;



import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.gamesradar.R;
import com.example.gamesradar.model.Radar.YTParser.Entry;

import com.example.gamesradar.radarfragments.OnVideoItemClick;
import com.example.gamesradar.util.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class YTFeedItemsAdapter extends RecyclerView.Adapter<YTFeedItemsAdapter.viewholder> {
    List<Entry> videos;
    OnVideoItemClick videoItemClickListener;

    public YTFeedItemsAdapter(List<Entry> videos, Context context, OnVideoItemClick videoItemClickListener) {
        this.videos = videos;
        this.context = context;
        this.videoItemClickListener = videoItemClickListener;
    }

    Context context;



    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ytfeed_post_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Entry video = videos.get(position);

        Glide.with(context).load(video.getMediaGroup()
                .getMediaThumbnail().getUrl())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(holder.videoThumbnail);
        holder.videoTitle.setText(video.getMediaGroup().getMediaTitle());
        holder.videoDesc.setText(video.getMediaGroup().getMediaDescription());
        holder.videoDesc.setVisibility(View.GONE);
        holder.videoChannel.setText(video.getAuthor().getName());
        holder.videoPubDate.setText(Utils.formatYTFeedDate(video.getPublished()));
        //holder.videoPubDate.setVisibility(View.GONE);
        holder.videoViews.setText
                (String.valueOf(video.getMediaGroup().getMediaCommunity().getStatistics().getViews())+" views");
        holder.itemView.setOnClickListener(view -> {
            videoItemClickListener.onVideoItemClick(video);
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

        RoundedImageView videoThumbnail;
        TextView videoTitle;
        TextView videoDesc;
        TextView videoPubDate;
        TextView videoViews;
        TextView videoChannel;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            videoTitle = itemView.findViewById(R.id.video_title);
            videoDesc = itemView.findViewById(R.id.video_desc);
            videoPubDate = itemView.findViewById(R.id.video_date);
            videoViews = itemView.findViewById(R.id.video_views);
            videoChannel = itemView.findViewById(R.id.video_channel);

        }
    }
}
