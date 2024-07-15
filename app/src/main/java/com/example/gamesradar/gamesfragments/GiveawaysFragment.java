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
import com.example.gamesradar.adapter.GiveawaysAdapter;
import com.example.gamesradar.gamesfragments.listeners.GetGiveawayFilterValues;
import com.example.gamesradar.gamesfragments.listeners.GiveawayOnClickListener;
import com.example.gamesradar.model.game.Giveaway;
import com.example.gamesradar.viewmodel.GiveawaysFragmentViewModel;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;


public class GiveawaysFragment extends Fragment implements GetGiveawayFilterValues, GiveawayOnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    RecyclerView giveawaysRV;
    CircularProgressIndicator loadingIndicator;
    ExtendedFloatingActionButton filterBtn;
    GiveawaysAdapter adapter;
    List<Giveaway> giveaway;
    GiveawaysFragmentViewModel viewModel;

    String platform_filter,type_filter,sortBy_filter;


    public GiveawaysFragment() {

    }


    public static GiveawaysFragment newInstance(String param1, String param2) {
        GiveawaysFragment fragment = new GiveawaysFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        getChildFragmentManager().setFragmentResultListener("requestKey", this,
                (requestKey, result) -> {
                    platform_filter = result.getString("platform");
                    type_filter = result.getString("type");
                    sortBy_filter = result.getString("sortBy");
                    Log.d("plat1",(platform_filter==null)?"null":platform_filter);
                    Log.d("genre1",type_filter==null?"null":type_filter);
                    Log.d("sort1",sortBy_filter==null?"null":sortBy_filter);
                    loadGiveaways(platform_filter,type_filter,sortBy_filter);
                });

        viewModel = new ViewModelProvider(this).get(GiveawaysFragmentViewModel.class);
        giveaway = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("oncreateview","called");

        View v = inflater.inflate(R.layout.fragment_giveaways, container, false);
        giveawaysRV = v.findViewById(R.id.giveaways_rv);
        loadingIndicator = v.findViewById(R.id.loading_giveaways);
        filterBtn = v.findViewById(R.id.giveaways_filter_btn);

        setupRecyclerView();
        //setLoadingStatus();
        loadFilterFragment();
        setErrorStatus();


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("destroyview","called");
    }

    private void setErrorStatus() {
        viewModel.getErrorStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    //loadingIndicator.setVisibility(View.VISIBLE);
                    Log.d("error","true");
                }
                else{
                   // loadingIndicator.setVisibility(View.INVISIBLE);
                    Log.d("error","false");
                }

            }
        });
    }

    private void setupRecyclerView() {
        Log.d("giveawayrv","setup");
        adapter = new GiveawaysAdapter(giveaway, getContext(), this);
        giveawaysRV.setAdapter(adapter);
        giveawaysRV.setLayoutManager(new LinearLayoutManager(getContext()));
        loadGiveaways(platform_filter,type_filter,sortBy_filter);
    }

    private void loadGiveaways(String platform,String type,String sortBy) {
        Log.d("giveawayload","called");
       giveaway.clear();
        adapter.updateGiveaways(giveaway);
        setLoadingStatus();
        viewModel.getAllGiveaways(platform, type, sortBy).observe(getViewLifecycleOwner(), new Observer<List<Giveaway>>() {
            @Override
            public void onChanged(List<Giveaway> giveaways) {
                giveaway = giveaways;
                Log.d("Giveaways","updated");
                adapter.updateGiveaways(giveaway);
            }
        });
    }

    private void setLoadingStatus() {
        Log.d("giveawayloadingstatus","called");
        viewModel.getLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    loadingIndicator.setVisibility(View.VISIBLE);
                    Log.d("loading","true");
                }
                else{
                    loadingIndicator.setVisibility(View.INVISIBLE);
                    Log.d("loading","false");
                }

            }
        });
    }

    private void loadFilterFragment() {
        filterBtn.setOnClickListener(view -> {
            GiveawaysFilterFragment filterDialog = GiveawaysFilterFragment.
                    newInstance(platform_filter,type_filter,sortBy_filter);
            filterDialog.show(getChildFragmentManager(),filterDialog.getTag());
        });
    }

    @Override
    public void getFilterValues(String platform, String type, String sortBy) {

        Log.d("plat1",(platform==null)?"null":platform);
        Log.d("type1",type==null?"null":type);
        Log.d("sort1",sortBy==null?"null":sortBy);
        platform_filter=platform;
        type_filter=platform;
        sortBy_filter=platform;
        loadGiveaways(platform_filter,type_filter,sortBy_filter);

    }

    @Override
    public void onCick(Giveaway giveaway) {
        Log.d("click","ok");
        getParentFragmentManager().beginTransaction()
                .replace(R.id.frame,GiveawayDetailsFragment.newInstance(giveaway.getId()))
                .addToBackStack(null)
                .commit();
    }
}