package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.databinding.FragmentMyteamBinding;
import com.infotech.fplcolosseum.features.homepage.models.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.GameWeekPicks;
import com.infotech.fplcolosseum.features.homepage.models.GameWeekPicksModel;
import com.infotech.fplcolosseum.features.homepage.models.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.viewmodels.MyTeamViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyTeamFragment extends Fragment {

    FragmentMyteamBinding binding;

    MyTeamViewModel viewModel;

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLUMNS = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyteamBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        binding.setMyTeamViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MyTeamViewModel.class);
        viewModel.getMyTeamData(Constants.LoggedInUser.getPlayer().getEntry());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the GridLayout in your fragment layout
        GridLayout footballFieldLayout = view.findViewById(R.id.footballFieldLayout);

        // Add players to the football field (customize positions as needed)

        viewModel.getApiResultLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse == null) return;
            if (apiResponse.getStatus() == ApiResponse.Status.SUCCESS) {
                viewModel.dataLoading.setValue(false);

                GameWeekMyTeamResponseModel myTeam = (GameWeekMyTeamResponseModel) apiResponse.getData();
                addPlayers(footballFieldLayout, myTeam);
            }
        });
    }

    private void addPlayers(GridLayout footballFieldLayout, GameWeekMyTeamResponseModel myTeam) {

        List<PlayersData> teamPlayers = new ArrayList<>();


        for (GameWeekPicks gameWeekPicks : myTeam.getPicks()) {
            teamPlayers.add(Constants.playerMap.get(gameWeekPicks.getElement()));
        }

        List<PlayersData> defenders = new ArrayList<>();
        List<PlayersData> midfielders = new ArrayList<>();
        List<PlayersData> forwards = new ArrayList<>();

        for (int i = 1; i < teamPlayers.size() - 4; i++) {

            PlayersData entry = teamPlayers.get(i);

            long playerType = entry.getElement_type();

            if ((Constants.playerTypeMap.get(playerType).getSingular_name_short().equalsIgnoreCase("DEF"))) {
                defenders.add(entry);
            } else if (Objects.requireNonNull(Constants.playerTypeMap.get(playerType)).getSingular_name_short().equalsIgnoreCase("MID")) {
                midfielders.add(entry);
            } else if (Objects.requireNonNull(Constants.playerTypeMap.get(playerType)).getSingular_name_short().equalsIgnoreCase("FWD")) {
                forwards.add(entry);
            } else {
                Log.d(Constants.LOG_TAG, "type not defined");
            }
        }

        //Adding players to the ui

        addPlayerNew(teamPlayers.get(0), 0, 2, footballFieldLayout); // playing goalkeeper

        // formation for mid defenders
        if (defenders.size() == 3) { // Adding players for a 3-x-x formation (adjust positions based on your layout)
            addPlayerNew(defenders.get(0), 1, 1, footballFieldLayout);
            addPlayerNew(defenders.get(1), 1, 2, footballFieldLayout);
            addPlayerNew(defenders.get(2), 1, 3, footballFieldLayout);

        } else if (defenders.size() == 4) { // Adding players for a 4-x-x formation (adjust positions based on your layout)
            addPlayerNew(defenders.get(0), 1, 0, footballFieldLayout);
            addPlayerNew(defenders.get(1), 1, 1, footballFieldLayout);
            addPlayerNew(defenders.get(2), 1, 3, footballFieldLayout);
            addPlayerNew(defenders.get(3), 1, 4, footballFieldLayout);

        } else if (defenders.size() == 5) { // Adding players for a 5-x-x formation (adjust positions based on your layout)
            addPlayerNew(defenders.get(0), 1, 0, footballFieldLayout);
            addPlayerNew(defenders.get(1), 1, 1, footballFieldLayout);
            addPlayerNew(defenders.get(2), 1, 2, footballFieldLayout);
            addPlayerNew(defenders.get(3), 1, 3, footballFieldLayout);
            addPlayerNew(defenders.get(4), 1, 4, footballFieldLayout);
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }

        // formation for mid fielders
        if (midfielders.size() == 2) {
            addPlayerNew(midfielders.get(0), 2, 1, footballFieldLayout);
            addPlayerNew(midfielders.get(1), 2, 3, footballFieldLayout);

        } else if (midfielders.size() == 3) {
            addPlayerNew(midfielders.get(0), 2, 1, footballFieldLayout);
            addPlayerNew(midfielders.get(1), 2, 2, footballFieldLayout);
            addPlayerNew(midfielders.get(2), 2, 3, footballFieldLayout);

        } else if (midfielders.size() == 4) {
            addPlayerNew(midfielders.get(0), 2, 0, footballFieldLayout);
            addPlayerNew(midfielders.get(1), 2, 1, footballFieldLayout);
            addPlayerNew(midfielders.get(2), 2, 3, footballFieldLayout);
            addPlayerNew(midfielders.get(3), 2, 4, footballFieldLayout);

        } else if (midfielders.size() == 5) {
            addPlayerNew(midfielders.get(0), 2, 0, footballFieldLayout);
            addPlayerNew(midfielders.get(1), 2, 1, footballFieldLayout);
            addPlayerNew(midfielders.get(2), 2, 2, footballFieldLayout);
            addPlayerNew(midfielders.get(3), 2, 3, footballFieldLayout);
            addPlayerNew(midfielders.get(4), 2, 4, footballFieldLayout);
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }


        // formation for forwards
        if (forwards.size() == 1) {
            addPlayerNew(forwards.get(0), 3, 3, footballFieldLayout);

        } else if (forwards.size() == 2) {
            addPlayerNew(forwards.get(0), 3, 1, footballFieldLayout);
            addPlayerNew(forwards.get(1), 3, 3, footballFieldLayout);

        } else if (forwards.size() == 3) {
            addPlayerNew(forwards.get(0), 3, 1, footballFieldLayout);
            addPlayerNew(forwards.get(1), 3, 2, footballFieldLayout);
            addPlayerNew(forwards.get(2), 3, 3, footballFieldLayout);
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }

        addPlayerNew(teamPlayers.get(11), 4, 0, footballFieldLayout); // bench goal keeper
        addPlayerNew(teamPlayers.get(12), 4, 2, footballFieldLayout); // first bench
        addPlayerNew(teamPlayers.get(13), 4, 3, footballFieldLayout); // second bench
        addPlayerNew(teamPlayers.get(14), 4, 4, footballFieldLayout); // third bench
    }

    public void addPlayerNew(PlayersData player, int row, int column, GridLayout footballFieldLayout) {

        PlayerView playerView = new PlayerView(requireContext(), player, true);
        playerView.setPlayerName(player.getWeb_name());
        playerView.setTeamName(Objects.requireNonNull(Constants.teamMap.get(player.getTeam())).getShort_name());

        //https://resources.premierleague.com/premierleague/badges/rb/t14.svg team logo
        //https://resources.premierleague.com/premierleague/photos/players/250x250/p441164.png player photo

        String imgURL = "";
        if ((row == 0 && column == 2) || (row == 4 && column == 0)) // for two goal keeper shirt will be changed
        {
            imgURL = "https://fantasy.premierleague.com/dist/img/shirts/standard/shirt_" + player.getTeam_code() + "_1-66.webp";
        } else {
            imgURL = "https://fantasy.premierleague.com/dist/img/shirts/standard/shirt_" + player.getTeam_code() + "-66.webp";
        }

        // https://fantasy.premierleague.com/dist/img/shirts/standard/shirt_14-66.webp for player shirt
        // https://fantasy.premierleague.com/dist/img/shirts/standard/shirt_14_1-66.webp for goal keeper shirt
        playerView.setPlayerImage(imgURL);

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
}
