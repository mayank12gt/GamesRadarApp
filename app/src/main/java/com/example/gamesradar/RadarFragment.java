package com.example.gamesradar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamesradar.adapter.RadarItemsAdapter;
import com.example.gamesradar.adapter.RadarViewPagerAdapter;
import com.example.gamesradar.model.Radar.RadarItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RadarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RadarFragment extends Fragment {

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    RecyclerView radarItemsRV;
    List<RadarItem> radarItems;
    RadarItemsAdapter adapter;
    TabLayout radarTabLayout;
    ViewPager2 radarViewPager;
    RadarViewPagerAdapter viewPagerAdapter;
    String titles[];

    public RadarFragment() {
        // Required empty public constructor
    }



    public static RadarFragment newInstance() {
        RadarFragment fragment = new RadarFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        radarItems = new ArrayList<>();
        titles = new String[]{"Trailers"};


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_radar, container, false);
//        radarItemsRV = v.findViewById(R.id.radar_items_rv);
//        radarItems.add(new RadarItem("Game Reviews- Articles","Latest video game review articles from top sources"));
//        radarItems.add(new RadarItem("Game Reviews- Videos","Latest video game review videos from top sources"));
//        radarItems.add(new RadarItem("Trailers","Watch trailers of the latest video games releasing"));
//        radarItems.add(new RadarItem("New Releases","The newest games releasing"));
//
//
//        adapter = new RadarItemsAdapter(getContext(),radarItems);
//
//        radarItemsRV.setAdapter(adapter);
//        radarItemsRV.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        radarTabLayout = v.findViewById(R.id.radartablayout);
        radarViewPager = v.findViewById(R.id.radar_view_pager);
        viewPagerAdapter = new RadarViewPagerAdapter(getActivity(),titles);

        radarViewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(radarTabLayout,radarViewPager,((tab, position) -> {
            tab.setText(titles[position]);
        })).attach();





        return v;
    }
}