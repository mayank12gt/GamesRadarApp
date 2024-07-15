package com.example.gamesradar.gamesfragments;

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
import com.example.gamesradar.adapter.FreeGamesAdapter;
import com.example.gamesradar.gamesfragments.listeners.FreeGameOnClickListener;
import com.example.gamesradar.gamesfragments.listeners.GetGamesFilterValues;
import com.example.gamesradar.model.game.FreeGame;
import com.example.gamesradar.viewmodel.FreeGamesFragmentViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;


public class FreeGamesFragment extends Fragment implements GetGamesFilterValues, FreeGameOnClickListener {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    RecyclerView freeGamesRV;
    CircularProgressIndicator loading_indicator;
    FreeGamesAdapter adapter;
    ExtendedFloatingActionButton filter_btn;
List<FreeGame> freeGame;
FreeGamesFragmentViewModel viewModel;

String platform_filter,genre_filter,sortBy_filter;




    public FreeGamesFragment() {
        // Required empty public constructor
    }



    public static FreeGamesFragment newInstance() {
        FreeGamesFragment fragment = new FreeGamesFragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        getChildFragmentManager().setFragmentResultListener("requestKey", this,
                (requestKey, result) -> {
                    platform_filter = result.getString("platform");
                    genre_filter = result.getString("genre");
                    sortBy_filter = result.getString("sortBy");
                    Log.d("plat1",(platform_filter==null)?"null":platform_filter);
                    Log.d("genre1",genre_filter==null?"null":genre_filter);
                    Log.d("sort1",sortBy_filter==null?"null":sortBy_filter);
                    loadfreeGames(platform_filter,genre_filter,sortBy_filter);
                });

        freeGame = new ArrayList<>();
        viewModel = new ViewModelProvider(FreeGamesFragment.this).get(FreeGamesFragmentViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_free_games, container, false);
        freeGamesRV = v.findViewById(R.id.free_games_rv);
        loading_indicator = v.findViewById(R.id.loading_games);
        filter_btn = v.findViewById(R.id.free_games_filter_btn);


        setupRecyclerView();
        //setLoadingStatus();
        loadFilterFragment();






        return v;
    }

    private void loadFilterFragment() {
        BadgeDrawable filterBadge =  BadgeDrawable.create(getContext());
        filterBadge.setNumber(2);
        filterBadge.setVisible(true);
        //BadgeUtils.attachBadgeDrawable(filterBadge,filter_btn);


        filter_btn.setOnClickListener(view -> {
            FreeGamesFilterFragment filterDialog = FreeGamesFilterFragment.
                    newInstance(platform_filter,genre_filter,sortBy_filter);
            filterDialog.show(getChildFragmentManager(),filterDialog.getTag());
        });
    }

    private void setLoadingStatus() {
        viewModel.getLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    loading_indicator.setVisibility(View.VISIBLE);
                    Log.d("loading","true");
                }
                else{
                    loading_indicator.setVisibility(View.INVISIBLE);
                    Log.d("loading","false");
                }

            }
        });
    }

    private void setupRecyclerView(){
        adapter = new FreeGamesAdapter(freeGame,getContext(),this);
        freeGamesRV.setAdapter(adapter);
        freeGamesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        loadfreeGames(platform_filter,genre_filter,sortBy_filter);
    }

    private void loadfreeGames(String platform,String genre,String sortBy) {
        freeGame.clear();
        adapter.updateGames(freeGame);

        setLoadingStatus();
        viewModel.getFreeGames(platform,genre,sortBy).observe(getViewLifecycleOwner(), new Observer<List<FreeGame>>() {
            @Override
            public void onChanged(List<FreeGame> freeGames) {
                freeGame = freeGames;
                adapter.updateGames(freeGame);

            }
        });

    }

    @Override
    public void getFilterValues(String platform, String genre, String sortBy) {
        Log.d("plat1",(platform==null)?"null":platform);
       Log.d("genre1",genre==null?"null":genre);
        Log.d("sort1",sortBy==null?"null":sortBy);
       platform_filter=platform;
        genre_filter=platform;
        sortBy_filter=platform;
        loadfreeGames(platform_filter,genre_filter,sortBy_filter);

    }

    @Override
    public void onClick(FreeGame game) {
        Log.d("click","ok");
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame,FreeGameDetailsFragment.newInstance(game.getId()))
                .addToBackStack(null)
                .commit();
    }
}