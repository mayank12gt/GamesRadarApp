package com.example.gamesradar.adapter;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamesradar.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FeedPostsAdapter extends RecyclerView.Adapter<FeedPostsAdapter.viewholder> {

    Context context;
    List<Pair<Article, Channel>> postList;

    public FeedPostsAdapter(Context context, List<Pair<Article, Channel>> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public FeedPostsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_post_item_2,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeedPostsAdapter.viewholder holder, int position) {
        Article post = postList.get(position).first;
        Channel channel = postList.get(position).second;
        Log.d("bind","working");

        holder.postTitle.setText(post.getTitle());




        holder.postDate.setText(post.getPubDate());
        holder.postSource.setText(channel.getTitle());


        if(post.getDescription()!=null){
            if(post.getDescription()==""){
                holder.postDesc.setVisibility(View.GONE);
            }
            else {
                Log.d("desc", post.getDescription());
                holder.postDesc.setText(post.getDescription());
            }
        }
        else{
            Log.d("desc","null");
            holder.postDesc.setVisibility(View.GONE);
        }


        if(post.getImage()!=null){
            Log.d("image",post.getImage());
            Glide.with(context).load(post.getImage())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(holder.postImage);
        } else if (post.getImage()==null) {
            Log.d("image",post.getDescription());
            holder.postImage.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

public void update(List<Pair<Article,Channel>> postList){

        this.postList = postList;
        Log.d("postList",this.postList.toString());
        notifyDataSetChanged();
}

//    private String formatDate(String pubDate){
//        DateFormat inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
//        val outputFormat = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
//        val date: Date = inputFormat.parse(pubDate)
//        return outputFormat.format(date)
//    }

    public class viewholder extends RecyclerView.ViewHolder {

        RoundedImageView postImage;
        TextView postTitle;
        TextView postDesc;
        TextView postSource;
        TextView postDate;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_Image);
            postTitle = itemView.findViewById(R.id.post_Title);
            postDesc = itemView.findViewById(R.id.post_Desc);
            postSource = itemView.findViewById(R.id.post_Source);
            postDate = itemView.findViewById(R.id.post_Date);
        }
    }
}
