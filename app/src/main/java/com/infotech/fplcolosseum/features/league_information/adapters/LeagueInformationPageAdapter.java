package com.infotech.fplcolosseum.features.league_information.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.infotech.fplcolosseum.features.league_information.views.LeagueStandingFragment;
import com.infotech.fplcolosseum.features.league_information.views.LeagueNewEntryFragment;

public class LeagueInformationPageAdapter extends FragmentStateAdapter {

    public LeagueInformationPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 0 -> new LeagueStandingFragment();
            case 1 -> new LeagueNewEntryFragment();
            default -> throw new IllegalArgumentException("Invalid tab position");
        };
    }

    @Override
    public int getItemCount() {
        return 2; // Number of tabs
    }
}
