package com.example.gamesradar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gamesradar.adapter.ViewPagerFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class GamesFragment extends Fragment {

TabLayout games_tablayout;
ViewPager2 gamesViewPager;
String titles[];
    public GamesFragment() {

    }



    public static GamesFragment newInstance() {
        GamesFragment fragment = new GamesFragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
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
        titles = new String[]{"Giveaways","Free Games"};


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_games, container, false);

        games_tablayout=v.findViewById(R.id.gamestablayout);
        gamesViewPager = v.findViewById(R.id.games_view_pager);
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getActivity(),titles);
        gamesViewPager.setAdapter(adapter);
        new TabLayoutMediator(games_tablayout,gamesViewPager,(tab, position) ->
                                                {tab.setText(titles[position]);
                                                }).attach();





        return v;
    }
}