package com.example.gamesradar.radarfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gamesradar.R;
import com.example.gamesradar.adapter.FeedPostsAdapter;
import com.example.gamesradar.viewmodel.RadarFragmentViewModel;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;


import java.util.ArrayList;
import java.util.List;


public class NewReleasesFragment extends Fragment {


//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//
//    private String mParam1;
//    private String mParam2;
FeedPostsAdapter adapter;
RadarFragmentViewModel viewModel;
List<Pair<Article, Channel>> postList;
List<String> sources;

RecyclerView newReleasesRv;
CircularProgressIndicator loadingNewReleases;


    public NewReleasesFragment() {
        // Required empty public constructor
    }


    public static NewReleasesFragment newInstance() {
        NewReleasesFragment fragment = new NewReleasesFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        postList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(RadarFragmentViewModel.class);
        sources = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_new_releases, container, false);

        newReleasesRv = v.findViewById(R.id.new_releases_rv);
        loadingNewReleases = v.findViewById(R.id.loading_new_releases);

        newReleasesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FeedPostsAdapter(getContext(),postList);
        newReleasesRv.setAdapter(adapter);
        sources.add("https://www.youtube.com/feeds/videos.xml?channel_id=UCbu2SsF-Or3Rsn3NxqODImw");
        Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();

        viewModel.getNewReleases(sources).observe(getViewLifecycleOwner(), new Observer<List<Pair<Article, Channel>>>() {
            @Override
            public void onChanged(List<Pair<Article, Channel>> pairs) {
                Log.d("update","ok");
                Log.d("data",pairs==null?"null":pairs.toString());
                adapter.update(pairs);

            }
        });


        viewModel.getNewReleasesLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    loadingNewReleases.setVisibility(View.VISIBLE);
                    Log.d("loading","true");
                }
                else{
                    loadingNewReleases.setVisibility(View.INVISIBLE);
                    Log.d("loading","false");
                }
            }
        });









    return  v;
    }
}