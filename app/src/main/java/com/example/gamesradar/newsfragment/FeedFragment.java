package com.example.gamesradar.newsfragment;

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

import com.example.gamesradar.OnFeedItemClicked;
import com.example.gamesradar.R;
import com.example.gamesradar.WebViewFragment;
import com.example.gamesradar.adapter.ReviewPostsAdapter;
import com.example.gamesradar.model.news.Source;
import com.example.gamesradar.viewmodel.NewsFragmentViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends Fragment implements OnFeedItemClicked {

  private static final String ARG_SOURCE = "source";


   private Source source;



    RecyclerView feedPostsRv;
    CircularProgressIndicator loadingPosts;
    NewsFragmentViewModel viewModel;

    List<Pair<Article, Channel>> postList;
    ReviewPostsAdapter adapter;
    MaterialToolbar topAppBar;
    public FeedFragment() {

    }


    public static FeedFragment newInstance(Source source) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();

        args.putSerializable("source",source);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           source = (Source) getArguments().getSerializable(ARG_SOURCE);

        }
        viewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);
        postList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v= inflater.inflate(R.layout.fragment_feed, container, false);
       feedPostsRv = v.findViewById(R.id.feed_posts_rv);
       loadingPosts = v.findViewById(R.id.loading_posts);
       topAppBar = v.findViewById(R.id.topAppBar);
       topAppBar.setTitle(source.getName());

       topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getParentFragmentManager().popBackStack();
           }
       });
       setupRecyclerView();








       return v;
    }

    private void setupRecyclerView() {
        adapter = new ReviewPostsAdapter(getContext(),postList,this::FeedItemClicked);
        feedPostsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        feedPostsRv.setAdapter(adapter);
        loadPosts();

    }

    private void loadPosts() {
        setLoadingStatus();
        viewModel.getFeedPosts(source.getUrl()).observe(getViewLifecycleOwner(), new Observer<List<Pair<Article, Channel>>>() {
            @Override
            public void onChanged(List<Pair<Article, Channel>> pairs) {
                adapter.update(pairs);
            }
        });
    }

    private void setLoadingStatus() {
        viewModel.getFeedPostsLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    loadingPosts.setVisibility(View.VISIBLE);
                    Log.d("loading","true");
                }
                else{
                    loadingPosts.setVisibility(View.GONE);
                    Log.d("loading","false");
                }
            }
        });

    }

    @Override
    public void FeedItemClicked(String url) {
        getParentFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame, WebViewFragment.newInstance(url)).commit();
    }
}