package com.example.gamesradar.newsfragment;

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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.gamesradar.R;
import com.example.gamesradar.adapter.SourcesAdapter;
import com.example.gamesradar.model.news.Source;
import com.example.gamesradar.viewmodel.NewsFragmentViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class SourcesFragment extends Fragment implements OnAddButtonClicked, OnSourceItemClicked {


MaterialToolbar toolbar;
    public SourcesFragment() {

    }

    RecyclerView sourcesRv;
    CircularProgressIndicator loadingIndicator;
    NewsFragmentViewModel viewModel;
    SourcesAdapter adapter;
    List<Source> sourceList;



    public static SourcesFragment newInstance() {
        SourcesFragment fragment = new SourcesFragment();
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
        sourceList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(NewsFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("oncreateview","called");
        View v=  inflater.inflate(R.layout.fragment_sources, container, false);

        toolbar  =v.findViewById(R.id.topAppBar);
        sourcesRv = v.findViewById(R.id.sources_rv);
        loadingIndicator = v.findViewById(R.id.loading_sources);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });

        setupRecyclerView();






        return v;
    }

    private void setupRecyclerView() {
        adapter = new SourcesAdapter(sourceList,this,this,getActivity());
        sourcesRv.setLayoutManager(new LinearLayoutManager(getContext()));
        sourcesRv.setAdapter(adapter);
        loadSources();
    }


    private void loadSources() {
        Log.d("sourcesload","called");
       // setLoadingStatus();
        loadingIndicator.setVisibility(View.VISIBLE);
        viewModel.getAllSources().observe(getViewLifecycleOwner(), new Observer<List<Source>>() {
            @Override
            public void onChanged(List<Source> sources) {
                adapter.update(sources);

                loadingIndicator.setVisibility(View.GONE);
            }

        });

    }

    private void setLoadingStatus() {
        viewModel.getLoadingStatus().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    loadingIndicator.setVisibility(View.VISIBLE);
                    Log.d("loadingSources","true");
                }
                else{
                    loadingIndicator.setVisibility(View.INVISIBLE);
                    Log.d("loadingSources","false");
                }
            }
        });
    }

    @Override
    public void Clicked(Source source) {
////        viewModel.checkFollowed(source).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
////            @Override
////            public void onChanged(Boolean aBoolean) {
////                if(aBoolean){
////                    Log.d("deleting","true");
////                   viewModel.deleteSource(source);
////
////                }
////                else{
////                    Log.d("adding","true");
////                    viewModel.addSource(source);
////                }
////            }
////        });
//            if(viewModel.checkFollow(source)==true){
//                Log.d("deleting","true");
//                viewModel.deleteSource(source);
//            }
//            else {
//                Log.d("adding","true");
//                viewModel.addSource(source);
//            }
//
//           setDrawable(imageView,source);

        viewModel.updateSource(source);

    }

//    @Override
//    public void setDrawable(ImageButton imageView,Source source) {
//////        viewModel.checkFollowed(source).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//////            @Override
//////            public void onChanged(Boolean aBoolean) {
//////                if(aBoolean){
//////                    Log.d("followed","true");
//////                   imageView.setImageDrawable(getContext().getDrawable(R.drawable.followed_icon));
//////
//////                }
//////                else{
//////                    Log.d("followed","false");
//////                    imageView.setImageDrawable(getContext().getDrawable(R.drawable.add_icon));
//////                }
//////            }
//////        });
////        Log.d("setDrawable","called");
////
////
////
////        if(viewModel.checkFollow(source)==true){
////            Log.d("followed","true");
////            imageView.setImageDrawable(getContext().getDrawable(R.drawable.followed_icon));
////        }
////        else{
////            Log.d("followed","false");
////            imageView.setImageDrawable(getContext().getDrawable(R.drawable.add_icon));
////        }
//    }

    @Override
    public void onSourceItemClicked(Source source) {

        getParentFragmentManager()
                .beginTransaction()
                .add(R.id.frame, FeedFragment.newInstance(source))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("destroyview","called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("destroy","called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("detach","called");
    }
}