package com.infotech.fplcolosseum.features.dream_team.views;

import static com.infotech.fplcolosseum.utilities.CustomUtil.deepCopyPlayer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.databinding.ActivityDreamTeamBinding;
import com.infotech.fplcolosseum.features.dream_team.models.DreamTeamPlayerModel;
import com.infotech.fplcolosseum.features.dream_team.models.DreamTeamResponseModel;
import com.infotech.fplcolosseum.features.dream_team.viewmodels.DreamTeamViewModel;
import com.infotech.fplcolosseum.features.homepage.adapter.OnPlayerClickOrDragListener;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.views.PlayerView;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DreaTeamActivity extends AppCompatActivity implements OnPlayerClickOrDragListener {

    ActivityDreamTeamBinding binding;

    DreamTeamViewModel viewModel;

    @Nullable
    private List<PlayersData> teamPlayers; // Initialize only when needed

    int selectedChip;
    long selectedGameWeek;
    public static final String ARG_GAME_WEEK = "game_week";
    private long game_week;
    private final Map<Long, Integer> playerPositionMap = new HashMap<>();

    private boolean isTeamOfTheSeason = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDreamTeamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(DreamTeamViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(ARG_GAME_WEEK)) {
            game_week = intent.getLongExtra(ARG_GAME_WEEK, 0L);
            viewModel.getDreamTeam(game_week);
        }
        setUpViews();
        setUpToolbar();

    }

    public void setUpToolbar() {
        // Set the title based on the received name (optional)
        setSupportActionBar(binding.dreamTeamToolbar);

        // Enable the Up button (back key)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //change the back button color to white
        Drawable navigationIcon = binding.dreamTeamToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setUpViews(){
        // Add chips for game week selection
        setupChips();
        setupNavigationButtons();

        binding.teamSelectionButton.setOnClickListener(v -> {
            viewModel.getSeasonDreamTeam();
            isTeamOfTheSeason = !isTeamOfTheSeason;
        });

        // Add players to the football field (customize positions as needed)
        viewModel.getDreamTeamLiveData().observe(this, apiResponse -> {

            if (apiResponse == null) return;
            if (apiResponse.getStatus() == ApiResponse.Status.SUCCESS) {
                viewModel.dataLoading.setValue(false);

                if (Constants.currentGameWeek == 0) {
                    binding.noPointLayout.setVisibility(View.VISIBLE);
                    binding.footballFieldLayout.setVisibility(View.GONE);
                    return;
                } else {
                    binding.noPointLayout.setVisibility(View.GONE);
                    binding.footballFieldLayout.setVisibility(View.VISIBLE);
                }

                DreamTeamResponseModel dreamTeam = apiResponse.getData();

                updateUI(dreamTeam);

            }
        });

        // Add players to the football field (customize positions as needed)
        viewModel.getSeasonDreamTeamLiveData().observe(this, apiResponse -> {

            if (apiResponse == null) return;
            if (apiResponse.getStatus() == ApiResponse.Status.SUCCESS) {
                viewModel.dataLoading.setValue(false);

                if (Constants.currentGameWeek == 0) {
                    binding.noPointLayout.setVisibility(View.VISIBLE);
                    binding.footballFieldLayout.setVisibility(View.GONE);
                    return;
                } else {
                    binding.noPointLayout.setVisibility(View.GONE);
                    binding.footballFieldLayout.setVisibility(View.VISIBLE);
                }

                DreamTeamResponseModel dreamTeam = apiResponse.getData();

                updateUI(dreamTeam);

            }
        });

        selectedGameWeek = Constants.currentGameWeek;
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            // This method performs the actual data-refresh operation and calls
            handleRefresh();
            // Stop the refreshing animation after the refresh operation is completed
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        binding.swipeRefreshLayout.setEnabled(false);
    }
    private void handleRefresh() {
        // Logic for refresh button
        resetChipSelection((int) Constants.currentGameWeek);
        setUpToolbar(Constants.currentGameWeek);
        binding.footballFieldLayout.removeAllViews();
        viewModel.getDreamTeam(game_week);
        resetToolBar();
    }

    private void resetToolBar() {
//        isClearVisible = false;     // Hide undo button
//        isSaveVisible = false;     // Hide save button
//        requireActivity().invalidateOptionsMenu();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Check if a chip is already selected; if so, don't reset the toolbar
//        if (selectedChip == (int) Constants.currentGameWeek) {
//            setUpToolbar(selectedChip);
//        }
        setUpToolbar(selectedChip);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // Batch UI updates
    private void updateUI(DreamTeamResponseModel data) {

        updateTopPlayer(data);
        setUpToolbar(selectedGameWeek);

        binding.footballFieldLayout.post(() -> {
            binding.footballFieldLayout.removeAllViews();
            addPlayers(binding.footballFieldLayout, data);

        });
    }

    public void updateTopPlayer(DreamTeamResponseModel data){
        PlayersData topPlayer = deepCopyPlayer(Constants.playerMap.get(data.getTopPlayer().getId()));
        CustomUtil.updatePlayerImage(binding.topPlayerImg, topPlayer);

        binding.topPlayerName.setText(topPlayer.getWeb_name());
        binding.topPlayerTeamName.setText(topPlayer.getTeam_name_full());
        String topPlayerPointsText = data.getTopPlayer().getPoints() + " Points";
        binding.topPlayerPoints.setText(topPlayerPointsText);

        long teamTotalPoints = data.getTeam().stream()
                .mapToInt(player -> (int) player.getPoints())
                .sum();;
        binding.totalPoints.setText(String.valueOf(teamTotalPoints));

        binding.teamOfTheSeasonText.setPaintFlags(binding.teamOfTheSeasonText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        binding.totalPointsText.setPaintFlags(binding.totalPointsText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void setupChips() {

        for (int i = 1; i <= Constants.currentGameWeek; i++) {
            final Chip chip = new Chip(this);
            String text = "GW " + i;
            chip.setText(text);
            chip.setCheckable(true);
            if (i == Constants.currentGameWeek) {
                chip.setChecked(true);

                // Scroll to the selected chip after layout is complete
                binding.buttonScrollView.post(() -> {
                    int chipLeft = chip.getLeft();
                    binding.buttonScrollView.smoothScrollTo(chipLeft, 0);
                });
            }

            int finalI = i;
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedChip = finalI;
                    isTeamOfTheSeason = false;
                    setUpToolbar(finalI);
                    binding.footballFieldLayout.removeAllViews();
                    viewModel.getDreamTeam(finalI);
                    selectedGameWeek = finalI;

                    // Scroll to the selected chip
                    binding.buttonScrollView.post(() -> {
                        int chipLeft = chip.getLeft();
                        int chipRight = chip.getRight();
                        int scrollViewWidth = binding.buttonScrollView.getWidth();
                        int scrollX = binding.buttonScrollView.getScrollX();

                        if (chipLeft < scrollX) {
                            binding.buttonScrollView.smoothScrollTo(chipLeft, 0);
                        } else if (chipRight > scrollX + scrollViewWidth) {
                            binding.buttonScrollView.smoothScrollTo(chipRight - scrollViewWidth, 0);
                        }
                    });
                }
            });
            binding.buttonGroup.addView(chip);
        }
    }

    private void setupNavigationButtons() {
        binding.buttonPrevious.setOnClickListener(v -> navigateChipSelection(-1));
        binding.buttonNext.setOnClickListener(v -> navigateChipSelection(1));
    }

    private void navigateChipSelection(int direction) {
        ChipGroup chipGroup = binding.buttonGroup;
        int chipCount = chipGroup.getChildCount();
        int currentIndex = -1;

        for (int i = 0; i < chipCount; i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.isChecked()) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex != -1) {
            int newIndex = currentIndex + direction;
            if (newIndex >= 0 && newIndex < chipCount) {
                ((Chip) chipGroup.getChildAt(newIndex)).setChecked(true);
            }
        }
    }


    // Method to reset chip selection to a specific game week
    private void resetChipSelection(int gameWeek) {
        ChipGroup chipGroup = binding.buttonGroup;
        int chipCount = chipGroup.getChildCount();

        // Find the chip corresponding to the given game week
        for (int i = 0; i < chipCount; i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            String chipText = chip.getText().toString();
            if (chipText.equals("GW " + gameWeek)) {
                chip.setChecked(true);
                break;
            }
        }
    }


    private void setUpToolbar(long gameWeekNumber) {

        if(isTeamOfTheSeason) {
            binding.dreamTeamToolbar.setTitle("Team of the Season");
            binding.teamSelectionButton.setText("Team of the Week " + selectedGameWeek);
        } else {
            binding.dreamTeamToolbar.setTitle("Team of the Week " + gameWeekNumber);
            binding.teamSelectionButton.setText("Team of the Season");
        }
    }

    private void updateTeamPlayers(DreamTeamResponseModel dreamTeamResponseModel) {

        Objects.requireNonNull(dreamTeamResponseModel.getTeam(), "Team cannot be null");

        this.teamPlayers = new ArrayList<>();
        for (DreamTeamPlayerModel myTeamPicks : dreamTeamResponseModel.getTeam()) {
            PlayersData playersData = deepCopyPlayer(Constants.playerMap.get(myTeamPicks.getElement()));

            if (playersData != null) {

                playersData.setPosition(myTeamPicks.getPosition());
                playersData.setSingular_name_short(Objects.requireNonNull(Constants.playerTypeMap.get(playersData.getElement_type())).getSingular_name_short());
                playersData.setTeam_name_short(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getShort_name());
                playersData.setTeam_name_full(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getName());

                playersData.setEvent_points(
                        myTeamPicks.getPoints()// Provide a default value if no matching element is found
                );

                teamPlayers.add(playersData);
            } else {
                UIUtils.toast(this, "Player data is null please reload again", ToastLevel.WARNING);
                return;
            }
        }
    }

    private void addPlayers(GridLayout footballFieldLayout, DreamTeamResponseModel dreamTeamResponseModel) {

        try {
            if (dreamTeamResponseModel == null || footballFieldLayout == null) {
                Log.e(Constants.LOG_TAG, "Null parameters in addPlayers");
                return;
            }

            // ... rest of the method
            updateTeamPlayers(dreamTeamResponseModel);

            initializePlayerPositionMap(); // initialize the teamPlayer map for later look up

            List<PlayersData> defenders = new ArrayList<>();
            List<PlayersData> midfielders = new ArrayList<>();
            List<PlayersData> forwards = new ArrayList<>();

            for (int i = 1; i < Objects.requireNonNull(teamPlayers).size(); i++) {

                PlayersData entry = teamPlayers.get(i);

                long playerType = entry.getElement_type();

                if ((Objects.requireNonNull(Constants.playerTypeMap.get(playerType)).getSingular_name_short().equalsIgnoreCase("DEF"))) {
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
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error adding players", e);
            UIUtils.toast(this, "Error setting up team", ToastLevel.ERROR);
        }
    }

    private void initializePlayerPositionMap() {
        for (int i = 0; i < Objects.requireNonNull(teamPlayers).size(); i++) {
            playerPositionMap.put(teamPlayers.get(i).getId(), i);
        }
    }

    public void addPlayerNew(PlayersData player, int row, int column, GridLayout footballFieldLayout) {

        PlayerView playerView = new PlayerView(this, player, false, null);
        playerView.setPlayerName(player.getWeb_name());

        //setting the listener for click operation
        playerView.setPlayerClickOrDragListener(this);
        playerView.setOnClickListener(v -> {
            OnPlayerClickOrDragListener playerClickOrDragListener = playerView.getPlayerClickOrDragListener();

            if (playerClickOrDragListener != null) {
                playerClickOrDragListener.onClickPlayer(playerView);
            }
        });

        //set team name
        String teamName = Objects.requireNonNull(Constants.teamMap.get(player.getTeam())).getShort_name();
        String playerType = Objects.requireNonNull(Constants.playerTypeMap.get(player.getElement_type())).getSingular_name_short();
        playerView.setTeamName(teamName + " - (" + playerType + ")");

        playerView.setOpponentTeamName(player.getEvent_points()+"");

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

        //set dream player icon
        if (player.isIn_dreamteam()) {
            playerView.setDreamTeamPlayer();
        }

        //set availability icon
        if (player.getChance_of_playing_next_round() != null && player.getChance_of_playing_next_round() < 100) {
            playerView.setAvailability(player.getChance_of_playing_next_round());
        }

        //set difficulty color
        playerView.setDifficulty1BackgroundColor(CustomUtil.getDifficultyLevelColor(Objects.requireNonNull(Objects.requireNonNull(Constants.fixtureData.get(Constants.nextGameWeek)).get(player.getTeam())).getDifficulty()));
        playerView.setDifficulty2BackgroundColor(CustomUtil.getDifficultyLevelColor(Objects.requireNonNull(Objects.requireNonNull(Constants.fixtureData.get(Constants.nextGameWeek + 1)).get(player.getTeam())).getDifficulty()));
        playerView.setDifficulty3BackgroundColor(CustomUtil.getDifficultyLevelColor(Objects.requireNonNull(Objects.requireNonNull(Constants.fixtureData.get(Constants.nextGameWeek + 2)).get(player.getTeam())).getDifficulty()));

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

    }

    @Override
    public void onClickPlayer(PlayerView view) {
        showBottomSheetDialogue(view.getPlayerData());
    }

    private void showBottomSheetDialogue(PlayersData playersData) {

//        OpponentData matchDetails = Objects.requireNonNull(Constants.fixtureData.get(selectedGameWeek)).get(playersData.getTeam());
//
//        Element playerPointExplain = Objects.requireNonNull(viewModel.getPointsMergedResponseLiveData().getValue()).getData().getGameWeekLivePointsResponseModel()
//                .elements.stream()
//                .filter(element -> element.id == playersData.getId())
//                .findFirst()
//                .orElse(null); // Provide a default value if no matching element is found
//
//        PointsPlayerInfoBottomSheetFragment bottomSheet = PointsPlayerInfoBottomSheetFragment.newInstance(playersData, matchDetails, playerPointExplain);
//        bottomSheet.show(this.getSupportFragmentManager(), bottomSheet.getTag());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        binding = null;
        cleanupResources();
    }

    private void cleanupResources() {
        teamPlayers = null;
        playerPositionMap.clear();
    }

}
