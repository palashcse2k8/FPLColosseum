package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.utilities.CustomUtil.updateFixtureData;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentMyteamBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.OnPlayerDragListener;
import com.infotech.fplcolosseum.features.homepage.models.MergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.GameWeekDataResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.MyTeamPicks;
import com.infotech.fplcolosseum.features.homepage.models.picks.Picks;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.features.homepage.viewmodels.viewmodels.MyTeamViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

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

    boolean isRefreshVisible = true;  // Hide refresh button
    boolean isShareVisible = true;    // Show share button
    boolean isSaveVisible = false;
    boolean isClearVisible = false; // Hide save button

    List<PlayersData> initialTeamPlayers = new ArrayList<>();

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
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Add players to the football field (customize positions as needed)
        viewModel.getMergedResponseLiveData().observe(getViewLifecycleOwner(), apiResponse -> {

            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    MergedResponseModel myTeam = apiResponse.getData();
                    setUpToolbar(myTeam.getGameWeekDataResponseModel()); // set up toolbar
                    updateFixtureData(myTeam.getMatchDetails()); //update fixture data
                    updateTeamPlayers(myTeam.getGameWeekMyTeamResponseModel().getPicks()); // update team player
                    updateUI(binding.footballFieldLayout); //finally update the UI

                    break;
                case ERROR:
                    showFailure(apiResponse.getMessage());
                    break;
            }
        });

//        viewModel.getMyTeamData(Constants.LoggedInUser.getPlayer().getEntry());
    }

    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.my_team_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem refreshItem = menu.findItem(R.id.action_refresh);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        MenuItem saveItem = menu.findItem(R.id.action_save);
        MenuItem clearItem = menu.findItem(R.id.action_undo);


        // Set visibility based on your conditions
        refreshItem.setVisible(isRefreshVisible);
        shareItem.setVisible(isShareVisible);
        saveItem.setVisible(isSaveVisible);
        clearItem.setVisible(isClearVisible);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        if (id == R.id.action_undo) {
            handleUndoClick();
            return true;
        } else if (id == R.id.action_refresh) {
            handleRefreshClick();
            return true;
        } else if (id == R.id.action_share) {
            handleShareClick();
            return true;
        } else if (id == R.id.action_save) {
            handleSaveClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleUndoClick() {

        // Logic for undo button

        this.teamPlayers = deepCopyPlayerList(this.initialTeamPlayers);
        updateUI(binding.footballFieldLayout); //update the player list view

        isClearVisible = false;     // Show share button
        isSaveVisible = false;     // Hide save button

        requireActivity().invalidateOptionsMenu();

    }

    private void handleRefreshClick() {
        // Logic for refresh button
        binding.footballFieldLayout.removeAllViews();
        viewModel.getTeamCurrentGameWeekAllData(Constants.LoggedInUser.getPlayer().getEntry());
    }

    private void handleShareClick() {
        // Logic for share button
        Toast.makeText(getActivity(), "Share clicked", Toast.LENGTH_SHORT).show();
    }

    private void handleSaveClick() {
        // Logic for save button
        Toast.makeText(getActivity(), "Save clicked", Toast.LENGTH_SHORT).show();
    }

    private void updateTeamPlayers(ArrayList<MyTeamPicks> picks) {

        this.teamPlayers = new ArrayList<>();
        for (MyTeamPicks myTeamPicks : picks) {
            PlayersData playersData = Constants.playerMap.get(myTeamPicks.getElement());

            if(playersData != null){
                playersData.setIs_captain(myTeamPicks.getIs_captain());
                playersData.setIs_vice_captain(myTeamPicks.getIs_vice_captain());
                playersData.setPosition(myTeamPicks.getPosition());
                playersData.setSingular_name_short((Constants.playerTypeMap.get(playersData.getElement_type()).getSingular_name_short()));
                teamPlayers.add(playersData);
            } else {
                UIUtils.toast(requireContext(), "Player data is null please reload again", ToastLevel.WARNING);
                return;
            }
        }

        this.initialTeamPlayers = deepCopyPlayerList(this.teamPlayers);

        Log.d(Constants.LOG_TAG, "Initial Team Players");
        printTeamPlayers(this.initialTeamPlayers);
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
        binding.footballFieldLayout.removeAllViews();
        addPlayers(footballFieldLayout);
    }

    private void addPlayers(GridLayout footballFieldLayout) {

        List<PlayersData> defenders = new ArrayList<>();
        List<PlayersData> midfielders = new ArrayList<>();
        List<PlayersData> forwards = new ArrayList<>();

        for (int i = 1; i < teamPlayers.size() - 4; i++) {

            PlayersData entry = teamPlayers.get(i);

            long playerType = entry.getElement_type();

            if (entry.getSingular_name_short().equalsIgnoreCase("DEF")) {
                defenders.add(entry);
            } else if (entry.getSingular_name_short().equalsIgnoreCase("MID")) {
                midfielders.add(entry);
            } else if (entry.getSingular_name_short().equalsIgnoreCase("FWD")) {
                forwards.add(entry);
            } else {
                Log.d(Constants.LOG_TAG, "type not defined");
            }
        }


        List<PlayersData> concatenatedPlayerList = new ArrayList<>(defenders);
        concatenatedPlayerList.addAll(midfielders);
        concatenatedPlayerList.addAll(forwards);

        concatenatedPlayerList.add(0, teamPlayers.get(0));

        for (int i = 11; i < teamPlayers.size(); i++) {
            concatenatedPlayerList.add(teamPlayers.get(i));
        }

        teamPlayers = concatenatedPlayerList;

        for (int i = 0; i < teamPlayers.size(); i++) {
            teamPlayers.get(i).setPosition(i + 1);
        }

        Log.d("FPLC", "After Sorting");

        printTeamPlayers(this.teamPlayers);

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
            addPlayerNew(forwards.get(0), 3, 2, footballFieldLayout);

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

        //save player view for later access
        playerViewMap.put(player.getPosition(), playerView);

        //set team name
        String teamName = Constants.teamMap.get(player.getTeam()).getShort_name();
        String playerType = Constants.playerTypeMap.get(player.getElement_type()).getSingular_name_short();
        playerView.setTeamName(teamName + " - (" + playerType + ")");

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
    public void onPlayerDragged(int fromPosition, int toPosition, PlayerView draggedPlayerView, PlayerView dropPlayerView, boolean isSwapData) {

//        Log.d("FPLC", "Before");
//        printTeamPlayers(this.teamPlayers);
//        binding.footballFieldLayout.requestLayout();

        List<PlayersData> tempTeamPlayers = new ArrayList<>(teamPlayers);
        Collections.swap(tempTeamPlayers, fromPosition - 1, toPosition - 1);

        if (!isValidFormation(tempTeamPlayers)) {
            return;
        }

        Collections.swap(teamPlayers, fromPosition - 1, toPosition - 1);

        if (isSwapData) {

            // swap vice captain property
            boolean tempViceCaptainStatus = teamPlayers.get(fromPosition - 1).isIs_vice_captain();
            teamPlayers.get(fromPosition - 1).setIs_vice_captain(teamPlayers.get(toPosition - 1).isIs_vice_captain());
            teamPlayers.get(toPosition - 1).setIs_vice_captain(tempViceCaptainStatus);

            // swap captain property
            boolean tempCaptainStatus = teamPlayers.get(fromPosition - 1).isIs_captain();
            teamPlayers.get(fromPosition - 1).setIs_captain(teamPlayers.get(toPosition - 1).isIs_captain());
            teamPlayers.get(toPosition - 1).setIs_captain(tempCaptainStatus);
        }

//        Log.d("FPLC", "After");
//        printTeamPlayers(this.teamPlayers);

        // Update the GridLayout with the new positions
        updateUI(binding.footballFieldLayout);

        isClearVisible = true;     // Show share button
        isSaveVisible = true;     // Hide save button

        requireActivity().invalidateOptionsMenu();

    }

    private void printTeamPlayers(List<PlayersData> teamPlayers) {
        for (PlayersData player : teamPlayers) {
            Log.d("FPLC", player.getPosition() + " -> " + player.getWeb_name() + (player.isIs_captain() ? " Captain" : "") + (player.isIs_vice_captain() ? " Vice Captain" : ""));
        }
    }

    private boolean isValidFormation(List<PlayersData> teamPlayers) {
        List<PlayersData> defenders = new ArrayList<>();
        List<PlayersData> midfielders = new ArrayList<>();
        List<PlayersData> forwards = new ArrayList<>();

        for (int i = 1; i < teamPlayers.size() - 4; i++) {

            PlayersData entry = teamPlayers.get(i);

            if (entry.getSingular_name_short().equalsIgnoreCase("DEF")) {
                defenders.add(entry);
            } else if (entry.getSingular_name_short().equalsIgnoreCase("MID")) {
                midfielders.add(entry);
            } else if (entry.getSingular_name_short().equalsIgnoreCase("FWD")) {
                forwards.add(entry);
            } else {
                Log.d(Constants.LOG_TAG, "type not defined");
            }
        }

        if (defenders.size() > 5 || defenders.size() < 3) {
            return false;
        }

        if (midfielders.size() > 5 || defenders.size() < 3) {
            return false;
        }

        return forwards.size() <= 3 && !forwards.isEmpty();

    }

    private List<PlayersData> deepCopyPlayerList(List<PlayersData> originalList) {
        List<PlayersData> copyList = new ArrayList<>();
        for (PlayersData player : originalList) {
            copyList.add(new PlayersData(player)); // Assuming PlayersData has a copy constructor
        }
        return copyList;
    }

    private void setUpToolbar(GameWeekDataResponseModel responseModel) {

        Toolbar toolbar = requireActivity().findViewById(R.id.pointToolbar);
        if (toolbar != null) {
            // Access the TextViews in the Toolbar
            TextView teamNameTextView = toolbar.findViewById(R.id.teamName);
            TextView managerNameTextView = toolbar.findViewById(R.id.managerName);

            Constants.teamName = responseModel.getName();
            teamNameTextView.setText(Constants.teamName);

            Constants.managerName = responseModel.getPlayer_first_name() + " " + responseModel.getPlayer_last_name();
            managerNameTextView.setText(Constants.managerName);
        }
    }
}
