package com.infotech.fplcolosseum.features.player_information.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.infotech.fplcolosseum.features.player_information.views.FixtureFragment;
import com.infotech.fplcolosseum.features.player_information.views.HistoryFragment;
import com.infotech.fplcolosseum.features.player_information.views.MatchesFragment;

public class PlayerInformationPageAdapter extends FragmentStateAdapter {
    public PlayerInformationPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MatchesFragment();
            case 1:
                return new FixtureFragment();
            case 2:
                return new HistoryFragment();
            default:
                throw new IllegalArgumentException("Invalid tab position");
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of tabs
    }
}
