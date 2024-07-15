package com.example.gamesradar.radarfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamesradar.R;
import com.example.gamesradar.adapter.YTFeedItemsAdapter;
import com.example.gamesradar.model.Radar.YTParser.Entry;
import com.example.gamesradar.model.Radar.YTParser.Feed;
import com.example.gamesradar.viewmodel.RadarFragmentViewModel;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;


public class VideoReviewsFragment extends Fragment implements OnVideoItemClick {


    RecyclerView VideoReviewsRV;
    YTFeedItemsAdapter adapter;
    CircularProgressIndicator loadingIndicator;
    RadarFragmentViewModel viewModel;
    List<Entry> videoReviews;


    public VideoReviewsFragment() {
        // Required empty public constructor
    }


    public static VideoReviewsFragment newInstance() {
        VideoReviewsFragment fragment = new VideoReviewsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        viewModel = new ViewModelProvider(this).get(RadarFragmentViewModel.class);
        videoReviews = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     View v= inflater.inflate(R.layout.fragment_video_reviews, container, false);
       VideoReviewsRV = v.findViewById(R.id.video_reviews_rv);
        loadingIndicator = v.findViewById(R.id.loading_video_reviews);

        adapter = new YTFeedItemsAdapter(videoReviews,getContext(),this::onVideoItemClick);
        VideoReviewsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        VideoReviewsRV.setAdapter(adapter);


        viewModel.getYTFeed("UCUnRn1f78foyP26XGkRfWsA").observe(getViewLifecycleOwner(), new Observer<Feed>() {
            @Override
            public void onChanged(Feed feed) {
                adapter.update(feed.getEntries());
                videoReviews.addAll(feed.getEntries());
            }
        });

        viewModel.getYTFeedLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    loadingIndicator.setVisibility(View.VISIBLE);
                }
                else{
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        });



        return v;
    }

    @Override
    public void onVideoItemClick(Entry video) {
        Log.d("videoURL",video.getMediaGroup().getMediaContent().getUrl());
        List<Entry> moreVideosList = new ArrayList<>();
        moreVideosList.addAll(videoReviews);
        moreVideosList.remove(video);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame,PlayerFragment.newInstance(video, (ArrayList<Entry>) moreVideosList))
                .addToBackStack(null).commit();

    }
}