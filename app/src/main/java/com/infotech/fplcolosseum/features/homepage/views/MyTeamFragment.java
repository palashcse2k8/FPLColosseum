package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.utilities.ButtonStateManager.getButtonState;
import static com.infotech.fplcolosseum.utilities.ButtonStateManager.updateButtonState;
import static com.infotech.fplcolosseum.utilities.CustomUtil.deepCopyPlayer;
import static com.infotech.fplcolosseum.utilities.CustomUtil.deepCopyPlayerList;
import static com.infotech.fplcolosseum.utilities.CustomUtil.prepareData;
import static com.infotech.fplcolosseum.utilities.CustomUtil.updateFixtureData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentMyteamBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.OnPlayerClickOrDragListener;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerInfoUpdateListener;
import com.infotech.fplcolosseum.features.homepage.models.MyTeamMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.GameWeekDataResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameChips;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamUpdateModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.MyTeamPicks;
import com.infotech.fplcolosseum.features.homepage.models.picks.Picks;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.ButtonStateManager;
import com.infotech.fplcolosseum.utilities.Chips;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyTeamFragment extends Fragment implements OnPlayerClickOrDragListener, PlayerInfoUpdateListener {

    FragmentMyteamBinding binding;

    HomePageSharedViewModel viewModel;
    List<PlayersData> teamPlayers = new ArrayList<>();
    Map<Long, PlayerView> playerViewMap = new HashMap<>();
    List<PlayerView> playerViewList = new ArrayList<>();
    PlayerView captain;
    PlayerView viceCaptain;

    boolean isRefreshVisible = true;  // Hide refresh button
    boolean isShareVisible = true;    // Show share button
    boolean isSaveVisible = false;
    boolean isClearVisible = false;  // Hide save button

    List<PlayersData> initialTeamPlayers = new ArrayList<>();
    String activeChip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyteamBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        binding.setMyTeamViewModel(viewModel);
        binding.setLifecycleOwner(this);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

                // Inflate the menu; this adds items to the action bar if it is present.
                menuInflater.inflate(R.menu.my_team_menu, menu);

                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    item.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                // Handle option Menu Here
                // Handle action bar item clicks here
                int id = menuItem.getItemId();

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

                return false;
            }

            @Override
            public void onPrepareMenu(@NonNull Menu menu) {

                MenuItem refreshItem = menu.findItem(R.id.action_refresh);
                MenuItem shareItem = menu.findItem(R.id.action_share);
                MenuItem saveItem = menu.findItem(R.id.action_save);
                MenuItem clearItem = menu.findItem(R.id.action_undo);


                // Set visibility based on your conditions
                refreshItem.setVisible(isRefreshVisible);
                shareItem.setVisible(isShareVisible);
                saveItem.setVisible(isSaveVisible);
                clearItem.setVisible(isClearVisible);

                MenuProvider.super.onPrepareMenu(menu);
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

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

        //set up swipe refresh layout
        binding.swiperefresh.setOnRefreshListener(() -> {

                    // This method performs the actual data-refresh operation and calls
                    handleRefreshClick();

                    // Stop the refreshing animation after the refresh operation is completed
                    binding.swiperefresh.setRefreshing(false);
                }
        );

        // Add players to the football field (customize positions as needed)
        viewModel.getMyTeamMergedResponseLiveData().observe(getViewLifecycleOwner(), apiResponse -> {

            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    MyTeamMergedResponseModel myTeam = apiResponse.getData();
//                    setUpToolbar(myTeam.getGameWeekDataResponseModel()); // set up toolbar
                    setUpToolbar(Constants.nextGameWeek);
                    updateTeamPlayers(myTeam.getGameWeekMyTeamResponseModel().getPicks()); // update team player
                    updateChipsStatus(requireContext(), myTeam.getGameWeekMyTeamResponseModel().getChips()); //update chips
                    updateFieldUI(binding.footballFieldLayout); //finally update the UI

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
        setUpToolbar(Constants.nextGameWeek);
    }

    private void handleUndoClick() {

        // Logic for undo button
        updateChipsStatus(requireContext(), viewModel.getMyTeamMergedResponseLiveData().getValue().getData().getGameWeekMyTeamResponseModel().getChips());
        this.teamPlayers = deepCopyPlayerList(this.initialTeamPlayers);
        updateFieldUI(binding.footballFieldLayout); //update the player list view

        resetToolBar();
    }

    private void handleRefreshClick() {
        // Logic for refresh button
        binding.footballFieldLayout.removeAllViews();
        viewModel.getMyTeamMergedData(Constants.LoggedInUser.getPlayer().getEntry());
        resetToolBar();
    }

    private void handleShareClick() {
        // Logic for share button
        Toast.makeText(getActivity(), "Share clicked", Toast.LENGTH_SHORT).show();
    }

    private void handleSaveClick() {

        // Logic for save button
        GameWeekMyTeamUpdateModel updateModel = new GameWeekMyTeamUpdateModel();

        ArrayList<Picks> picks = new ArrayList<>();

        //add player data
        for (PlayersData player : this.teamPlayers) {
            Picks pick = new Picks();
            pick.setElement(player.getId());
            pick.setPosition(player.getPosition());
            pick.setIs_captain(player.isIs_captain());
            pick.setIs_vice_captain(player.isIs_vice_captain());
            picks.add(pick);
        }
        updateModel.setPicks(picks);

        //add chips data
        //TODO

        viewModel.updateMyTeam(Constants.LoggedInUser.getPlayer().getEntry(), updateModel);
        viewModel.getMyTeamApiResultLiveData().observe(getViewLifecycleOwner(), apiResponse -> {

            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    viewModel.getMyTeamMergedData(Constants.LoggedInUser.getPlayer().getEntry());
                    resetToolBar();

                    break;
                case ERROR:
                    showFailure(apiResponse.getMessage());
                    break;
            }
        });
    }

    private void resetToolBar() {
        isClearVisible = false;     // Hide undo button
        isSaveVisible = false;     // Hide save button

        requireActivity().invalidateOptionsMenu();
    }

    private void enableEditToolBar() {
        isClearVisible = true;     // Hide undo button
        isSaveVisible = true;     // Hide save button

        requireActivity().invalidateOptionsMenu();
    }

    private void updateTeamPlayers(ArrayList<MyTeamPicks> picks) {

        this.teamPlayers = new ArrayList<>();
        for (MyTeamPicks myTeamPicks : picks) {
            PlayersData playersData = deepCopyPlayer(Constants.playerMap.get(myTeamPicks.getElement()));

            if (playersData != null) {
                playersData.setIs_captain(myTeamPicks.getIs_captain());
                playersData.setIs_vice_captain(myTeamPicks.getIs_vice_captain());
                playersData.setPosition(myTeamPicks.getPosition());
                playersData.setSingular_name_short(Objects.requireNonNull(Constants.playerTypeMap.get(playersData.getElement_type())).getSingular_name_short());
                playersData.setTeam_name_short(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getShort_name());
                playersData.setTeam_name_full(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getName());
                teamPlayers.add(playersData);
            } else {
                UIUtils.toast(requireContext(), "Player data is null please reload again", ToastLevel.WARNING);
                return;
            }
        }

        this.initialTeamPlayers = deepCopyPlayerList(this.teamPlayers);

//        Log.d(Constants.LOG_TAG, "Initial Team Players");
//        printTeamPlayers(this.initialTeamPlayers);
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

    private void updateFieldUI(GridLayout footballFieldLayout) {

        binding.progressCircular.setVisibility(View.GONE);
        binding.footballFieldLayout.removeAllViews();
        addPlayers(footballFieldLayout);
    }

    private void addPlayers(GridLayout footballFieldLayout) {

        this.playerViewList = new ArrayList<>();
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

//        printTeamPlayers(this.teamPlayers);

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
        playerViewList.add(playerView);

        //set team name
        String teamName = Objects.requireNonNull(Constants.teamMap.get(player.getTeam())).getShort_name();
        String playerType = Objects.requireNonNull(Constants.playerTypeMap.get(player.getElement_type())).getSingular_name_short();
        playerView.setTeamName(teamName + " - (" + playerType + ")");

        //update opponent team name
        HashMap<Long, OpponentData> fixtures = (HashMap<Long, OpponentData>) Constants.fixtureData.get(Constants.nextGameWeek);
        assert fixtures != null;
        OpponentData opponentData = fixtures.get(player.getTeam());
        assert opponentData != null;
        String homeOrAway = opponentData.isHome() ? "H" : "A";
        String opponentTeamName = Objects.requireNonNull(Constants.teamMap.get(opponentData.getTeamID())).getShort_name();
        playerView.setOpponentTeamName("v " + opponentTeamName + " (" + homeOrAway + ")");

        // https://resources.premierleague.com/premierleague/badges/rb/t14.svg team logo
        // https://resources.premierleague.com/premierleague/photos/players/250x250/p441164.png player photo

        String imgURL;
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
        if (player.getChance_of_playing_next_round() != null && player.getChance_of_playing_next_round() < 100) {
            playerView.setAvailability(player.getChance_of_playing_next_round());
        }

        //set difficulty color
        playerView.setDifficulty1BackgroundColor(CustomUtil.getDifficultyLevelColor(Constants.fixtureData.get(Constants.nextGameWeek).get(player.getTeam()).getDifficulty()));
        playerView.setDifficulty2BackgroundColor(CustomUtil.getDifficultyLevelColor(Constants.fixtureData.get(Constants.nextGameWeek + 1).get(player.getTeam()).getDifficulty()));
        playerView.setDifficulty3BackgroundColor(CustomUtil.getDifficultyLevelColor(Constants.fixtureData.get(Constants.nextGameWeek + 2).get(player.getTeam()).getDifficulty()));


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

    // Method to handle the click event
    private void handleCustomViewClick(PlayerView playerView) {
        // Implement your logic for handling the click event here
        // For example:
        Toast.makeText(requireContext(), "Clicked on position: " + playerView.getPlayerData().getPosition(), Toast.LENGTH_SHORT).show();
        // Perform other actions as needed
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
        updateFieldUI(binding.footballFieldLayout);

        isClearVisible = true;     // Show share button
        isSaveVisible = true;     // Hide save button

        requireActivity().invalidateOptionsMenu();

    }

    @Override
    public void onClickPlayer(PlayerView view) {
        Log.d(Constants.LOG_TAG, "Player Clicked! " + view.player.getPosition());
        showBottomSheetDialogue(view.getPlayerData());
    }


    private void showBottomSheetDialogue(PlayersData playersData) {
        MyTeamPlayerInfoBottomSheetFragment bottomSheet = MyTeamPlayerInfoBottomSheetFragment.newInstance(playersData);
        bottomSheet.setPlayerInfoUpdateListener(this);
        bottomSheet.show(requireActivity().getSupportFragmentManager(), bottomSheet.getTag());
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

        if (midfielders.size() > 5 || midfielders.size() < 3) {
            return false;
        }

        return forwards.size() <= 3 && !forwards.isEmpty();

    }


//    private void setUpToolbar(GameWeekDataResponseModel responseModel) {
//
//        Toolbar toolbar = requireActivity().findViewById(R.id.pointToolbar);
//        if (toolbar != null) {
//            // Access the TextViews in the Toolbar
//            TextView teamNameTextView = toolbar.findViewById(R.id.teamName);
//            TextView managerNameTextView = toolbar.findViewById(R.id.managerName);
//
//            Constants.teamName = responseModel.getName();
//            String concatenatedName = Constants.teamName + " (GW " + Constants.nextGameWeek + ")";
//            teamNameTextView.setText(concatenatedName);
//
//            Constants.managerName = responseModel.getPlayer_first_name() + " " + responseModel.getPlayer_last_name();
//            managerNameTextView.setText(Constants.managerName);
//        }
//    }

    private void setUpToolbar( long gameWeekNumber) {

        Toolbar toolbar = requireActivity().findViewById(R.id.pointToolbar);
        if (toolbar != null) {
            // Access the TextViews in the Toolbar
            TextView teamNameTextView = toolbar.findViewById(R.id.teamName);
            TextView managerNameTextView = toolbar.findViewById(R.id.managerName);

            String concatenatedName = Constants.teamName + " (GW " + gameWeekNumber + ")";
            teamNameTextView.setText(concatenatedName);

            managerNameTextView.setText(Constants.managerName);
        }
    }

    @Override
    public void onSetCaptain(PlayersData player) {
        PlayersData currentCaptain = null;

        // Loop through the list to find the current captain
        for (PlayersData p : this.teamPlayers) {
            if (p.isIs_captain()) {
                currentCaptain = p;
                break; // No need to continue once we find the current captain
            }
        }

        // Check if the selected player is the current vice-captain
        if (player.isIs_vice_captain()) {
            // If the player is vice-captain, make the current captain the vice-captain
            if (currentCaptain != null) {
                currentCaptain.setIs_captain(false);
                currentCaptain.setIs_vice_captain(true);
            }
            // Set the selected player as the new captain
            player.setIs_vice_captain(false);
            player.setIs_captain(true);
        } else {
            // If the selected player is not vice-captain, simply update the captain
            if (currentCaptain != null) {
                currentCaptain.setIs_captain(false);
            }
            player.setIs_captain(true);
        }

        updateFieldUI(binding.footballFieldLayout);

        isClearVisible = true;     // Show share button
        isSaveVisible = true;     // Hide save button

        requireActivity().invalidateOptionsMenu();
    }


    @Override
    public void onSetViceCaptain(PlayersData player) {
        PlayersData currentViceCaptain = null;

        // Loop through the list to find the current captain
        for (PlayersData p : this.teamPlayers) {
            if (p.isIs_vice_captain()) {
                currentViceCaptain = p;
                break; // No need to continue once we find the current captain
            }
        }

        // Check if the selected player is the current vice-captain
        if (player.isIs_captain()) {
            // If the player is captain, make the current captain the vice-captain
            if (currentViceCaptain != null) {
                currentViceCaptain.setIs_captain(true);
                currentViceCaptain.setIs_vice_captain(false);
            }
            // Set the selected player as the new captain
            player.setIs_vice_captain(true);
            player.setIs_captain(false);
        } else {
            // If the selected player is not captain, simply update the vice captain
            if (currentViceCaptain != null) {
                currentViceCaptain.setIs_vice_captain(false);
            }
            player.setIs_vice_captain(true);
        }

        updateFieldUI(binding.footballFieldLayout);

        isClearVisible = true;     // Show share button
        isSaveVisible = true;     // Hide save button

        requireActivity().invalidateOptionsMenu();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onSwitchPlayer(PlayersData player) {
        // Highlight switchable players in the grid

//        for (PlayerView playerView: this.playerViewList) {
//            if(playerView.getPlayerData().getPosition() != player.getPosition()){
//                playerView.setHighlight("#e9ff32");
//            } else {
//                Log.d(Constants.LOG_TAG, "Same Player");
//            }
//        }

        //check if the player is goal keeper

        if (player.getPosition() == 12) {
            for (PlayerView playerView : this.playerViewList) {

                playerView.setOnDragListener(null);
                playerView.setOnTouchListener(null);

                if (playerView.getPlayerData().getPosition() == 1) {
                    playerView.setHighlight("#e9ff32");// Implement setHighlight in PlayerView to visually indicate the player can be swapped
                    playerView.setOnClickListener(v -> {
                        onPlayerDragged(12, 1, playerView, playerView, true);
                    });
                } else {
                    playerView.setOnClickListener(
                            v -> updateFieldUI(binding.footballFieldLayout)
                    );
                }
            }
        }

        // check if the player is goal keeper
        if (player.getPosition() == 1) {
            for (PlayerView playerView : this.playerViewList) {

                playerView.setOnDragListener(null);
                playerView.setOnTouchListener(null);

                if (playerView.getPlayerData().getPosition() == 12) {
                    playerView.setHighlight("#e9ff32");// Implement setHighlight in PlayerView to visually indicate the player can be swapped
                    playerView.setOnClickListener(v -> {
                        onPlayerDragged(1, 12, playerView, playerView, true);
                    });
                } else {
                    playerView.setOnClickListener(
                            v -> updateFieldUI(binding.footballFieldLayout)
                    );
                }
            }
        }

        if (player.getPosition() > 12) {
            for (PlayerView playerView : this.playerViewList) {

                playerView.setOnDragListener(null);
                playerView.setOnTouchListener(null);

                if (playerView.getPlayerData().getPosition() != player.getPosition() && playerView.getPlayerData().getPosition() != 12 && playerView.getPlayerData().getPosition() != 1) {
                    playerView.setHighlight("#e9ff32");// Implement setHighlight in PlayerView to visually indicate the player can be swapped
                    playerView.setOnClickListener(v -> {
                        onPlayerDragged((int) player.getPosition(), (int) playerView.getPlayerData().getPosition(), playerView, playerView, true);
                    });
                } else {
                    playerView.setOnClickListener(
                            v -> updateFieldUI(binding.footballFieldLayout)
                    );
                }
            }
        }

        if (player.getPosition() > 1 && player.getPosition() < 12) {
            for (PlayerView playerView : this.playerViewList) {

                playerView.setOnDragListener(null);
                playerView.setOnTouchListener(null);

                if (playerView.getPlayerData().getPosition() > 12) {
                    playerView.setHighlight("#e9ff32");// Implement setHighlight in PlayerView to visually indicate the player can be swapped
                    playerView.setOnClickListener(v -> {
                        onPlayerDragged((int) player.getPosition(), (int) playerView.getPlayerData().getPosition(), playerView, playerView, true);
                    });
                } else {
                    playerView.setOnClickListener(
                            v -> updateFieldUI(binding.footballFieldLayout)
                    );
                }
            }
        }

    }

    private void updateChipsStatus(Context context, ArrayList<GameChips> chips) {

        setupButton(binding.buttonBB, Chips.BB.getDisplayName());
        setupButton(binding.buttonTC, Chips.TC.getDisplayName());
        setupButton(binding.buttonFH, Chips.FH.getDisplayName());

        Map<String, MaterialButton> chipButtons = new HashMap<>();
        chipButtons.put(Chips.BB.getShortName(), binding.buttonBB);
        chipButtons.put(Chips.TC.getShortName(), binding.buttonTC);
        chipButtons.put(Chips.FH.getShortName(), binding.buttonFH);

        for (GameChips chip : chips) {
            MaterialButton button = chipButtons.get(chip.getName());
            if (button != null) {
                ButtonStateManager.ButtonState state;
                switch (chip.getStatus_for_entry().toLowerCase()) {
                    case "available":
                        state = ButtonStateManager.ButtonState.AVAILABLE;
                        break;
                    case "active":
                        state = ButtonStateManager.ButtonState.ACTIVE;
                        activeChip = chip.getName();
                        break;
                    case "unavailable":
                        state = ButtonStateManager.ButtonState.NOT_AVAILABLE;
                        break;
                    default:
                        continue; // Skip unknown statuses
                }
                updateButtonState(context, button, state);
            }
        }
    }

    private void setupButton(final MaterialButton button, final String buttonName) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonStateManager.ButtonState currentState = getButtonState(button);
                switch (currentState) {
                    case ACTIVE:
                        showConfirmationDialog(button, buttonName, "Deactivate " + buttonName + "?", ButtonStateManager.ButtonState.AVAILABLE);
                        break;
                    case AVAILABLE:
                        showConfirmationDialog(button, buttonName, "Activate " + buttonName + "?", ButtonStateManager.ButtonState.ACTIVE);
                        break;
                    case NOT_AVAILABLE:
                        showPopup(buttonName + " is not available!");
                        break;
                }
            }
        });
    }

    private void showPopup(String message) {
        new AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showConfirmationDialog(final MaterialButton button, final String buttonName, String message, final ButtonStateManager.ButtonState newState) {
        new AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateButtonState(requireContext(), button, newState);
//                        showPopup(buttonName + " state updated to " + newState);
                        enableEditToolBar();
                        if (newState == ButtonStateManager.ButtonState.ACTIVE) {
                            if (buttonName.equalsIgnoreCase(Chips.BB.getDisplayName())) {
                                activeChip = Chips.BB.getShortName();
                            } else if (buttonName.equalsIgnoreCase(Chips.TC.getDisplayName())) {
                                activeChip = Chips.TC.getShortName();
                            } else {
                                activeChip = Chips.FH.getShortName();
                            }
                        } else if (newState == ButtonStateManager.ButtonState.AVAILABLE) {
                            activeChip = null;
                        } else {
                            if (buttonName.equalsIgnoreCase(Chips.BB.getDisplayName())) {
                                activeChip = Chips.BB.getShortName();
                            } else if (buttonName.equalsIgnoreCase(Chips.TC.getDisplayName())) {
                                activeChip = Chips.TC.getShortName();
                            } else {
                                activeChip = Chips.FH.getShortName();
                            }
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    //    private boolean isPlayerSwitchable(PlayersData data) {
//        // Implement your logic to determine if a player is switchable
//        return data.getPosition() < 12 && !data.isIs_captain() && !data.isIs_vice_captain();
//    }
//
//    private void swapPlayers(PlayersData player1, PlayersData player2) {
//        // Swap the players in the list
//        int index1 = playersList.indexOf(player1);
//        int index2 = playersList.indexOf(player2);
//
//        playersList.set(index1, player2);
//        playersList.set(index2, player1);
//    }
}
