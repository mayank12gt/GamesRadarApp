package com.example.gamesradar.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gamesradar.gamesfragments.FreeGamesFragment;
import com.example.gamesradar.gamesfragments.GiveawaysFragment;

import com.example.gamesradar.radarfragments.VideoReviewsFragment;

public class ReviewsViewPagerAdapter extends FragmentStateAdapter {
    String titles[];
    public ReviewsViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String titles[]) {
        super(fragmentActivity);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new VideoReviewsFragment();


        }
        return new VideoReviewsFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
