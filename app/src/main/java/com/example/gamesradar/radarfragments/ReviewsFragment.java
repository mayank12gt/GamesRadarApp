package com.example.gamesradar.radarfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamesradar.OnFeedItemClicked;
import com.example.gamesradar.R;
import com.example.gamesradar.WebViewFragment;
import com.example.gamesradar.adapter.FeedPostsAdapter;
import com.example.gamesradar.adapter.ReviewPostsAdapter;
import com.example.gamesradar.adapter.ReviewsViewPagerAdapter;
import com.example.gamesradar.viewmodel.RadarFragmentViewModel;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;

import java.util.ArrayList;
import java.util.List;


public class ReviewsFragment extends Fragment  {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

//    ReviewPostsAdapter adapter;
//    RadarFragmentViewModel viewModel;
//    List<Pair<Article, Channel>> reviewPostsList;
//    List<String> sources;
//
//    RecyclerView reviewsRv;
//    CircularProgressIndicator loadingReviews;


    TabLayout reviewsTabLayout;
    ViewPager2 reviewsViewPager;
    String titles[];

    public ReviewsFragment() {
        // Required empty public constructor
    }



    public static ReviewsFragment newInstance() {
        ReviewsFragment fragment = new ReviewsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        reviewPostsList = new ArrayList<>();
//        viewModel = new ViewModelProvider(this).get(RadarFragmentViewModel.class);
//        sources = new ArrayList<>();

        titles = new String[]{"Videos","Articles"};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_reviews, container, false);
//        reviewsRv = v.findViewById(R.id.reviews_rv);
//        loadingReviews = v.findViewById(R.id.loading_reviews);
//
//
//
//
//        sources.add("https://www.gamespot.com/feeds/reviews");
//        sources.add("https://indiegamereviewer.com/feed/");
//        sources.add("https://www.theguardian.com/games+tone/reviews/rss");
//        sources.add("https://www.forbes.com/sites/games/feed/");
//        Log.d("sources3",sources.toString());
//
//        setupRecyclerView();

      reviewsTabLayout = v.findViewById(R.id.reviews_tablayout);
      reviewsViewPager = v.findViewById(R.id.reviews_view_pager);

        ReviewsViewPagerAdapter adapter = new ReviewsViewPagerAdapter(getActivity(),titles);

        reviewsViewPager.setAdapter(adapter);

        new TabLayoutMediator(reviewsTabLayout,reviewsViewPager,((tab, position) -> {
                                                                        tab.setText(titles[position]);
                                                                    })).attach();


        return v;
    }

//    private void setupRecyclerView() {
//        reviewsRv.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new ReviewPostsAdapter(getContext(),reviewPostsList,this::FeedItemClicked);
//        reviewsRv.setAdapter(adapter);
//        loadReviews();
//    }
//
//    private void loadReviews() {
//            setLoadingStatus();
//            Log.d("sources2",sources.toString());
//
//        viewModel.getReviews(sources).observe(getViewLifecycleOwner(), new Observer<List<Pair<Article, Channel>>>() {
//            @Override
//            public void onChanged(List<Pair<Article, Channel>> pairs) {
//
//                Log.d("update","ok");
//                Log.d("data",pairs==null?"null":pairs.toString());
//                adapter.update(pairs);
//
//            }
//        });
//
//
//
//    }
//
//    private void setLoadingStatus() {
//
//        viewModel.getReviewsLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if(aBoolean){
//                    loadingReviews.setVisibility(View.VISIBLE);
//                    Log.d("loading","true");
//                }
//                else{
//                    loadingReviews.setVisibility(View.GONE);
//                    Log.d("loading","false");
//                }
//            }
//        });
//    }

//    @Override
//    public void FeedItemClicked(String url) {
//        getParentFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame, WebViewFragment.newInstance(url)).commit();
//    }
}