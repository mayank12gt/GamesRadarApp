package com.example.gamesradar.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gamesradar.gamesfragments.FreeGamesFragment;
import com.example.gamesradar.gamesfragments.GiveawaysFragment;
import com.example.gamesradar.radarfragments.NewReleasesFragment;
import com.example.gamesradar.radarfragments.ReviewsFragment;
import com.example.gamesradar.radarfragments.TrailersFragment;

public class RadarViewPagerAdapter extends FragmentStateAdapter {
    String titles[];
    public RadarViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,String titles[]) {
        super(fragmentActivity);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){

            case 0:
                return TrailersFragment.newInstance();




        }
        return new FreeGamesFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
