package com.example.gamesradar.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gamesradar.gamesfragments.FreeGamesFragment;
import com.example.gamesradar.gamesfragments.GiveawaysFragment;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {
    String titles[];
    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity, String titles[]) {
        super(fragmentActivity);
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new GiveawaysFragment();
            case 1:
                return new FreeGamesFragment();
        }

        return new GiveawaysFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
