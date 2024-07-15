package com.example.gamesradar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamesradar.adapter.VideosViewPagerAdapter;
import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class VideosFragment extends Fragment {


//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    TabLayout videosTabLayout;
    ViewPager2 videosViewPager;

    public VideosFragment() {
        // Required empty public constructor
    }


    public static VideosFragment newInstance() {
        VideosFragment fragment = new VideosFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_videos, container, false);

        videosTabLayout = v.findViewById(R.id.videos_tab_layout);
        videosViewPager = v.findViewById(R.id.videos_view_pager);
        String titles[]=new String[]{"Manage","Inbox", "Sources"};

        VideosViewPagerAdapter adapter = new VideosViewPagerAdapter(getActivity(),titles);
        videosViewPager.setAdapter(adapter);


        new TabLayoutMediator(videosTabLayout,videosViewPager,((tab, position) -> {
            switch (position){
                case 0:
                   tab.setIcon(R.drawable.video_icon);

//
                  // tab.setText("Manage");
//                    tab.setCustomView(LayoutInflater.from(getContext()).inflate(R.layout.single_chip_layout, tab.parent,false));
//                   Chip c= tab.view.findViewById(R.id.chip);
//                   c.setText("Manage");
                    //Log.d("tab", String.valueOf(videosTabLayout.getSelectedTabPosition()));
                    //tab.view.setGravity(Gravity.START);

                    break;
                case 2:
                    tab.setIcon(R.drawable.explore_icon);
                    //tab.setText("Sources");

                    break;
                case 1:
                    tab.setText("Inbox");


                    break;
                default:
            }
        })).attach();


        return v;
    }
}