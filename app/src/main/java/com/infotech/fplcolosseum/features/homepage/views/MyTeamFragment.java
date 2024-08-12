package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.utilities.CustomUtil.updateFixtureData;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentMyteamBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.OnPlayerDragListener;
import com.infotech.fplcolosseum.features.homepage.models.MergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.MyTeamPicks;
import com.infotech.fplcolosseum.features.homepage.models.picks.Picks;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.features.homepage.viewmodels.viewmodels.MyTeamViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyTeamFragment extends Fragment implements OnPlayerDragListener {

    FragmentMyteamBinding binding;

    HomePageSharedViewModel viewModel;
    List<PlayersData> teamPlayers = new ArrayList<>();
    Map<Long, PlayerView> playerViewMap = new HashMap<>();
    PlayerView captain;
    PlayerView viceCaptain;

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
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the GridLayout in your fragment layout
        GridLayout footballFieldLayout = view.findViewById(R.id.footballFieldLayout);

        // Add players to the football field (customize positions as needed)
        viewModel.getMergedResponseLiveData().observe(getViewLifecycleOwner(), apiResponse -> {

            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    MergedResponseModel myTeam = (MergedResponseModel) apiResponse.getData();
                    updateFixtureData(myTeam.getMatchDetails());
                    updateTeamPlayers(myTeam.getGameWeekMyTeamResponseModel().getPicks());
                    updateUI(footballFieldLayout);


                    break;
                case ERROR:
                    showFailure(apiResponse.getMessage());
                    break;
            }
        });

//        viewModel.getMyTeamData(Constants.LoggedInUser.getPlayer().getEntry());
    }

    private void updateTeamPlayers(ArrayList<MyTeamPicks> picks) {

        this.teamPlayers = new ArrayList<>();
        for (MyTeamPicks myTeamPicks : picks) {
            PlayersData playersData = Constants.playerMap.get(myTeamPicks.getElement());
            assert playersData != null;
            playersData.setIs_captain(myTeamPicks.getIs_captain());
            playersData.setIs_vice_captain(myTeamPicks.getIs_vice_captain());
            playersData.setPosition(myTeamPicks.getPosition());
            playersData.setSingular_name_short((Constants.playerTypeMap.get(playersData.getElement_type()).getSingular_name_short()));
            teamPlayers.add(playersData);
        }
    }

    private void showLoading() {
        viewModel.dataLoading.setValue(true);
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.footballFieldLayout.setVisibility(View.GONE);

    }

    private void showFailure(String error) {
        viewModel.dataLoading.setValue(false);
        binding.progressCircular.setVisibility(View.GONE);
        binding.footballFieldLayout.setVisibility(View.GONE);
    }

    private void updateUI(GridLayout footballFieldLayout) {

        binding.progressCircular.setVisibility(View.GONE);
        addPlayers(footballFieldLayout);
    }

    private void addPlayers(GridLayout footballFieldLayout) {

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

        PlayerView playerView = new PlayerView(requireContext(), player, true, this);
        playerView.setPlayerName(player.getWeb_name());

        playerView.setTag(player.getPosition());
        playerViewMap.put(player.getPosition(), playerView);

        //set team name
        String teamName = Constants.teamMap.get(player.getTeam()).getShort_name();
        String playerType = Constants.playerTypeMap.get(player.getElement_type()).getSingular_name_short();
        playerView.setTeamName(teamName + " - (" + playerType+ ")");

        //update opponent team name
        HashMap<Long, OpponentData> fixtures = (HashMap<Long, OpponentData>) Constants.fixtureData.get(Constants.nextGameWeek);
        OpponentData opponentData = fixtures.get(player.getTeam());
        assert opponentData != null;
        String homeOrAway = opponentData.isHome() ? "H" : "A";
        String opponentTeamName = Constants.teamMap.get(opponentData.getTeamID()).getShort_name();
        playerView.setOpponentTeamName("v " + opponentTeamName + " (" + homeOrAway + ")");

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

        //set captain icon
        if (player.isIs_captain()) {
            playerView.setCaptain();
            captain = playerView;
        }

        //set vice captain icon
        if (player.isIs_vice_captain()) {
            playerView.setViceCaptain();
            viceCaptain = playerView;
        }

        //set dream player icon
        if (player.isIn_dreamteam()) {
            playerView.setDreamTeamPlayer();
        }

        //set availability icon
        if (player.getChance_of_playing_this_round() != null && player.getChance_of_playing_this_round() < 100) {
            playerView.setAvailability(player.getChance_of_playing_this_round());
        }

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

    @Override
    public void onPlayerDragged(int fromPosition, int toPosition, PlayerView draggedPlayerView, PlayerView dropPlayerView) {

        Log.d("FPLC", "Before");
        printTeamPlayers();

        PlayersData fromPositionPlayer = teamPlayers.get(fromPosition-1);
        PlayersData toPositionPlayer = teamPlayers.get(toPosition-1);
//        fromPositionPlayer.setPosition(toPosition);
//        toPositionPlayer.setPosition(fromPosition);

        Collections.swap(teamPlayers, fromPosition-1, toPosition-1);
        teamPlayers.get(fromPosition-1).setPosition(fromPosition);
        teamPlayers.get(toPosition-1).setPosition(toPosition);
        Log.d("FPLC", "After");
        printTeamPlayers();



        // Update the GridLayout with the new positions
//        updateUI(binding.footballFieldLayout);

    }

    private void printTeamPlayers(){
        for (PlayersData player : this.teamPlayers) {
            Log.d("FPLC", player.getPosition() + " -> " +player.getWeb_name());
        }
    }
}
