package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyTeamFragment extends Fragment{

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
            if(apiResponse == null) return;
            if(apiResponse.getStatus() == ApiResponse.Status.SUCCESS){
                viewModel.dataLoading.setValue(false);

                GameWeekMyTeamResponseModel myTeam = (GameWeekMyTeamResponseModel) apiResponse.getData();
                addPlayers(footballFieldLayout, myTeam);
            }
        });
    }

    private void addPlayers(GridLayout footballFieldLayout, GameWeekMyTeamResponseModel myTeam) {

        Map<Long, PlayersData> teamPlayers = new HashMap<>();


        for(GameWeekPicks gameWeekPicks : myTeam.getPicks()) {
            teamPlayers.put(gameWeekPicks.getElement(), Constants.playerMap.get(gameWeekPicks.getElement()));
        }

        Map<Long, PlayersData> defenders = new HashMap<>();
        Map<Long, PlayersData> midfielders = new HashMap<>();
        Map<Long, PlayersData> forwards = new HashMap<>();

        for (Map.Entry<Long, PlayersData> entry: teamPlayers.entrySet()) {
            if(Constants.playerTypeMap.get(entry.getKey()).getSingular_name_short().equalsIgnoreCase("DEF")) {
                defenders.put(entry.getKey(), entry.getValue());
            }

            if(Constants.playerTypeMap.get(entry.getKey()).getSingular_name_short().equalsIgnoreCase("MID")) {
                midfielders.put(entry.getKey(), entry.getValue());
            }

            if(Constants.playerTypeMap.get(entry.getKey()).getSingular_name_short().equalsIgnoreCase("FWD")) {
                forwards.put(entry.getKey(), entry.getValue());
            }
        }

        // Adding players for a 4-4-2 formation (adjust positions based on your layout)

        //Adding goal keeper

        addPlayerNew(playerFullName, "Team A", R.mipmap.no_image, 0, 2, footballFieldLayout);

        addPlayerNew(teamPlayers.get(1).getElement() + "", "Team A", R.mipmap.no_image, 1, 0, footballFieldLayout);
        addPlayerNew(teamPlayers.get(2).getElement() + "", "Team A", R.mipmap.no_image, 1, 1, footballFieldLayout);
        addPlayerNew(teamPlayers.get(3).getElement() + "", "Team A", R.mipmap.no_image, 1, 3, footballFieldLayout);
        addPlayerNew(teamPlayers.get(4).getElement() + "", "Team A", R.mipmap.no_image, 1, 4, footballFieldLayout);

        addPlayerNew(teamPlayers.get(5).getElement() + "", "Team A", R.mipmap.no_image, 2, 0, footballFieldLayout);
        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 2, 1, footballFieldLayout);
        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 2, 3, footballFieldLayout);
        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 2, 4, footballFieldLayout);

        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 3, 1, footballFieldLayout);
        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 3, 3, footballFieldLayout);

        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 4, 0, footballFieldLayout);
        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 4, 2, footballFieldLayout);
        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 4, 3, footballFieldLayout);
        addPlayerNew(teamPlayers.get(0).getElement() + "", "Team A", R.mipmap.no_image, 4, 4, footballFieldLayout);
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
}
