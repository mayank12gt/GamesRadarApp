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

public class TrailersFragment extends Fragment implements OnVideoItemClick {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    RecyclerView YTFeedVideosRv;
    YTFeedItemsAdapter adapter;
    CircularProgressIndicator loadingIndicator;
    RadarFragmentViewModel viewModel;
    List<Entry> videos;


    public TrailersFragment() {
        // Required empty public constructor
    }


    public static TrailersFragment newInstance() {
        TrailersFragment fragment = new TrailersFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        viewModel = new ViewModelProvider(this).get(RadarFragmentViewModel.class);
        videos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_trailers, container, false);
        YTFeedVideosRv = v.findViewById(R.id.trailers_rv);
        loadingIndicator = v.findViewById(R.id.loading_trailers);

        adapter = new YTFeedItemsAdapter(videos,getContext(),this::onVideoItemClick);
        YTFeedVideosRv.setLayoutManager(new LinearLayoutManager(getContext()));
        YTFeedVideosRv.setAdapter(adapter);

        viewModel.getYTFeed("UCUnRn1f78foyP26XGkRfWsA").observe(getViewLifecycleOwner(), new Observer<Feed>() {
            @Override
            public void onChanged(Feed feed) {
                adapter.update(feed.getEntries());
                videos.addAll(feed.getEntries());
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
        moreVideosList.addAll(videos);
        moreVideosList.remove(video);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame,PlayerFragment.newInstance(video, (ArrayList<Entry>) moreVideosList))
                .addToBackStack(null).commit();

    }
}