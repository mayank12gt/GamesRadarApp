package com.example.gamesradar;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamesradar.adapter.ReviewPostsAdapter;
import com.example.gamesradar.model.news.Source;
import com.example.gamesradar.newsfragment.SourcesFragment;
import com.example.gamesradar.viewmodel.NewsFragmentViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;

import java.util.ArrayList;
import java.util.List;




public class NewsFragment extends Fragment implements OnFeedItemClicked {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    MaterialToolbar toolbar;
    RecyclerView newsRv;
    CircularProgressIndicator loadingNews;
    NewsFragmentViewModel viewModel;
    List<Pair<Article, Channel>> postList;
    ReviewPostsAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }


  
    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
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
        postList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_news, container, false);
            toolbar  =v.findViewById(R.id.topAppBar);
            newsRv = v.findViewById(R.id.news_rv);
            loadingNews = v.findViewById(R.id.loading_news);
            
            setupRecyclerView();
            

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.sources:
                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.frame, SourcesFragment.newInstance())
                                    .addToBackStack(null)
                                    .commit();
                            return true;

                    }
                    return false;
                }
            });
            
            



        return v;
    }

    private void setupRecyclerView() {
        adapter = new ReviewPostsAdapter(getContext(),postList,this::FeedItemClicked);
        newsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRv.setAdapter(adapter);
        loadNewsPosts();
    }

    private void loadNewsPosts() {
        setLoadingStatus();
        viewModel.getFollowedSources().observe(getViewLifecycleOwner(), new Observer<List<Source>>() {
            @Override
            public void onChanged(List<Source> sourceList) {
                Log.d("outet observer","reached");
                if(sourceList==null){
                    Log.d("followedSources","null");
                }
                else{
                    Log.d("followedSources",sourceList.toString());
                }
                for (Source source:sourceList) {
                    Log.d("followedSources",source.getName());
                }
                viewModel.getFollowedFeedPosts(sourceList).observe(getViewLifecycleOwner(), new Observer<List<Pair<Article, Channel>>>() {
                    @Override
                    public void onChanged(List<Pair<Article, Channel>> pairs) {
                        Log.d("inner observer","reached");
                        if(pairs!=null) {
                            adapter.update(pairs);
                        }
                        else {
                            postList.clear();
                            adapter.update(postList);
                        }

                    }
                });

            }
        });
    }

    private void setLoadingStatus() {
        viewModel.getFollowedFeedsPostsLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    loadingNews.setVisibility(View.VISIBLE);
                }
                else{
                    loadingNews.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void FeedItemClicked(String url) {
        getParentFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame,WebViewFragment.newInstance(url)).commit();
    }
}