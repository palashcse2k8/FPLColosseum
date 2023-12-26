package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentMyteamBinding;
import com.infotech.fplcolosseum.features.gameweek.models.custom.PlayerDataModel;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTeamFragment extends Fragment {

    FragmentMyteamBinding binding;
    private FootballFieldLayout footballFieldLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyteamBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Create FootballFieldLayout
        FootballFieldLayout footballFieldLayout = new FootballFieldLayout(requireContext());

        // Add FootballFieldLayout to the parent layout (assuming you have a parent layout in fragment_myteam.xml)
        LinearLayout parentLayout = rootView.findViewById(R.id.footballFieldLayout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        footballFieldLayout.setLayoutParams(layoutParams);
        parentLayout.addView(footballFieldLayout);

        // Rest of your code...

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the FootballFieldLayout in your fragment layout
        footballFieldLayout = view.findViewById(R.id.footballFieldLayout);

        // Add players to the football field (customize positions as needed)
        addPlayers();
    }

    private void addPlayers() {
        // Adding players for a 4-4-2 formation (adjust positions based on your layout)
        footballFieldLayout.addPlayer("Player 1", "Team A", R.mipmap.no_image, 1, 1);
        footballFieldLayout.addPlayer("Player 2", "Team A", R.mipmap.no_image, 1, 2);
        footballFieldLayout.addPlayer("Player 3", "Team A", R.mipmap.no_image, 1, 3);
        footballFieldLayout.addPlayer("Player 4", "Team A", R.mipmap.no_image, 1, 4);
        footballFieldLayout.addPlayer("Player 5", "Team A", R.mipmap.no_image, 1, 5);

        footballFieldLayout.addPlayer("Player 6", "Team A", R.mipmap.no_image, 2, 1);
        footballFieldLayout.addPlayer("Player 7", "Team A", R.mipmap.no_image, 2, 2);
        footballFieldLayout.addPlayer("Player 8", "Team A", R.mipmap.no_image, 2, 3);
        footballFieldLayout.addPlayer("Player 9", "Team A", R.mipmap.no_image, 2, 4);
        footballFieldLayout.addPlayer("Player 10", "Team A", R.mipmap.no_image, 2, 5);
    }
}
