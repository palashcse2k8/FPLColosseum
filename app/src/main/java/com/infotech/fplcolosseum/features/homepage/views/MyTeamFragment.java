package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
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

public class MyTeamFragment extends Fragment implements View.OnDragListener{

    FragmentMyteamBinding binding;

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLUMNS = 5;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyteamBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the GridLayout in your fragment layout
        GridLayout footballFieldLayout = view.findViewById(R.id.footballFieldLayout);

        // Add players to the football field (customize positions as needed)
        addPlayers(footballFieldLayout);
    }

    private void addPlayers(GridLayout footballFieldLayout) {
        // Adding players for a 4-4-2 formation (adjust positions based on your layout)
        addPlayerNew("Player 1", "Team A", R.mipmap.no_image, 0, 2, footballFieldLayout);

        addPlayerNew("Player 1", "Team A", R.mipmap.no_image, 1, 0, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 1, 1, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 1, 3, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 1, 4, footballFieldLayout);

        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 2, 0, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 2, 1, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 2, 3, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 2, 4, footballFieldLayout);

        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 3, 1, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 3, 3, footballFieldLayout);


        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 4, 0, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 4, 2, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 4, 3, footballFieldLayout);
        addPlayerNew("Player 2", "Team A", R.mipmap.no_image, 4, 4, footballFieldLayout);
//        // Add more players as needed...
    }

    public void addPlayerNew(String playerName, String teamName, int imageResId, int row, int column, GridLayout footballFieldLayout) {
        PlayerView playerView = new PlayerView(requireContext());
        playerView.setPlayerName(playerName);
        playerView.setTeamName(teamName);
        playerView.setPlayerImage(imageResId);

        // Set the position of the player in the GridLayout
        playerView.setRow(row);
        playerView.setColumn(column);

        // Create layout parameters for the GridLayout
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.rowSpec = GridLayout.spec(row, 1, 1f);
        params.columnSpec = GridLayout.spec(column, 1, 1f);

        // Set the width and height to 0dp to let weight take effect
        params.width = 0;
        params.height = 0;

        // Apply layout parameters to the PlayerView
        playerView.setLayoutParams(params);

        // Add the PlayerView to the GridLayout
        footballFieldLayout.addView(playerView);
    }

    // Implement the OnDragListener interface
    @Override
    public boolean onDrag(View v, DragEvent event) {
        // Handle the drag event here
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                // Handle the drop event
                View draggedView = (View) event.getLocalState();
                GridLayout owner = (GridLayout) v.getParent();

                int draggedPosition = owner.indexOfChild(draggedView);
                int targetPosition = owner.indexOfChild(v);

                owner.addView(draggedView, targetPosition);
                owner.addView(v, draggedPosition);
                break;
        }
        return true;
    }

}
