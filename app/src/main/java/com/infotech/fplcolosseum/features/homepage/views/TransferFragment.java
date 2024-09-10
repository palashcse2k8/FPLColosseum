package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.utilities.ButtonStateManager.getButtonState;
import static com.infotech.fplcolosseum.utilities.ButtonStateManager.updateButtonState;
import static com.infotech.fplcolosseum.utilities.CustomUtil.convertUtcToLocalDateTime;
import static com.infotech.fplcolosseum.utilities.CustomUtil.convertUtcToLocalTime;
import static com.infotech.fplcolosseum.utilities.CustomUtil.deepCopyPlayer;
import static com.infotech.fplcolosseum.utilities.CustomUtil.deepCopyPlayerList;
import static com.infotech.fplcolosseum.utilities.CustomUtil.findPlayerById;
import static com.infotech.fplcolosseum.utilities.CustomUtil.hasMoreThanThreePlayersFromSameTeam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.FragmentUtils;
import com.google.android.material.button.MaterialButton;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentTransfersBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.OnPlayerClickOrDragListener;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerTransferListener;
import com.infotech.fplcolosseum.features.homepage.models.MyTeamMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameChips;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekTransferUpdateModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.MyTeamPicks;
import com.infotech.fplcolosseum.features.homepage.models.myteam.TransferUpdate;
import com.infotech.fplcolosseum.features.homepage.models.myteam.Transfers;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.ButtonStateManager;
import com.infotech.fplcolosseum.utilities.Chips;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import es.dmoral.toasty.Toasty;

public class TransferFragment extends Fragment implements OnPlayerClickOrDragListener, PlayerTransferListener {

    FragmentTransfersBinding binding;

    HomePageSharedViewModel viewModel;

    private static final String ARG_ITEM_DATA = "entry_id";
    public static String PLAYER_DATA = "selected_player_data";
    public static String TRANSFERRED_PLAYER_DATA = "transferred_player_data";
    public static String TRANSFER_REQUEST_KEY = "transfer_request_key";

    private long entry_id;

    private List<PlayersData> teamPlayers;
    private List<PlayersData> initialTeamPlayers;
    private List<PlayerView> playerViewList;

    private LocalDateTime endTime;
    boolean isRefreshVisible = true;  // Hide refresh button
    boolean isShareVisible = true;    // Show share button
    boolean isSaveVisible = false;
    boolean isClearVisible = false;

    String activeChip;
    int activeChipCount = 0;
    GameWeekMyTeamResponseModel initialResponse;
    Transfers currentTransfer;

    Map<Long, TransferUpdate> transferredPlayerList = new HashMap<>();

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable countdownRunnable = new Runnable() {
        @Override
        public void run() {
            updateCountdown();
            handler.postDelayed(this, 1000); // Update every 1 second
        }
    };


    public TransferFragment newInstance(long index) {
        Bundle args = new Bundle();
        args.putLong(ARG_ITEM_DATA, index);

        TransferFragment fragment = new TransferFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            entry_id = args.getLong(ARG_ITEM_DATA);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);

        // Set the listener on the child fragmentManager.
        getParentFragmentManager()
                .setFragmentResultListener(TRANSFER_REQUEST_KEY, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                        PlayersData selectedPlayer = (PlayersData) bundle.getSerializable(PLAYER_DATA);
                        PlayersData transferredPlayer = (PlayersData) bundle.getSerializable(TRANSFERRED_PLAYER_DATA);

                        // trigger ui change with the new selected player
                        selectedPlayer.setPurchase_price(selectedPlayer.getNow_cost()); // update the price with purchase price
                        selectedPlayer.setSelling_price(selectedPlayer.getNow_cost()); // update the price with selling price
                        int transferredPlayerIndex = (int) transferredPlayer.getPosition() - 1;
                        selectedPlayer.setSubstitute_number(transferredPlayerIndex + 1);
                        selectedPlayer.setPosition(transferredPlayer.getPosition());

                        teamPlayers.set(transferredPlayerIndex, selectedPlayer);

                        long updatedBankBalance = transferredPlayer.getPurchase_price() + currentTransfer.getBank() - selectedPlayer.getPurchase_price();
                        currentTransfer.setBank(updatedBankBalance);
                        long transferCount = currentTransfer.getMade();
                        currentTransfer.setMade(++transferCount);

                        updateTransferInfo(currentTransfer);
                        updateFieldUI(binding.footballFieldLayout);

                        enableEditToolBar();

                        //store transferred player list
                        TransferUpdate previousData = transferredPlayerList.get(transferredPlayer.getPosition());
                        if (previousData != null) {
                            previousData.setElement_in(selectedPlayer.getId());
                            previousData.setPurchase_price(selectedPlayer.getPurchase_price() + "");
                        } else {
                            TransferUpdate transferUpdate = new TransferUpdate();
                            transferUpdate.setElement_in(selectedPlayer.getId());
                            transferUpdate.setElement_out(transferredPlayer.getId());
                            transferUpdate.setSelling_price(transferredPlayer.getSelling_price() + "");
                            transferUpdate.setPurchase_price(selectedPlayer.getPurchase_price() + "");
                            transferredPlayerList.put(transferredPlayer.getPosition(), transferUpdate);
                        }
                    }
                });
//        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransfersBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        binding.setMyTeamViewModel(viewModel);
        viewModel.dataLoading.setValue(true);
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

    private void handleUndoClick() {

        // Logic for undo button
        binding.footballFieldLayout.removeAllViews();
        updateUI(initialResponse);
        resetToolBar(); // reset the toolbar
        transferredPlayerList = new HashMap<>(); // reset transferred player list
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

    private boolean isValid() {

        //check the current balance is sufficient enough
        if (currentTransfer.getBank() < 0) {
            Toasty.warning(requireContext(), "You don't have enough money to make this transfer").show();
            return false;
        }

        //check if more than 3 player from same team is selected
        if (hasMoreThanThreePlayersFromSameTeam(this.teamPlayers)) {
            Toasty.warning(requireContext(), "You can't select more than 3 player from same team").show();
            return false;
        }

        return true;
    }

    private void handleSaveClick() {

        if (!isValid()) {
            return;
        }

        // Logic for save button
        GameWeekTransferUpdateModel updateModel = new GameWeekTransferUpdateModel();

        updateModel.setChip(activeChip); // add chips data
        updateModel.setEntry(Constants.LoggedInUser.getPlayer().getEntry() + "");
        updateModel.setEvent(Constants.nextGameWeek);

        //add player data
        ArrayList<TransferUpdate> transferUpdateList = new ArrayList<>(this.transferredPlayerList.values());
        updateModel.setTransfers(transferUpdateList);

        //add chips data
        //TODO

        viewModel.transferMyTeam(updateModel);
        viewModel.getTransferApiResultLiveData().observe(getViewLifecycleOwner(), apiResponse -> {

            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);
                    transferredPlayerList = new HashMap<>();// reset transferred player list
                    binding.footballFieldLayout.removeAllViews(); // remove all existing ui
                    viewModel.getMyTeamMergedData(Constants.LoggedInUser.getPlayer().getEntry());
                    resetToolBar(); // reset toolbar action

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

    public void logOutUser() {
        Constants.LoggedInUser = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Stop the countdown when the fragment is destroyed
        handler.removeCallbacks(countdownRunnable);
    }

    private void updateCountdown() {
        // Calculate the duration between the current time and the end time
        Duration duration = Duration.between(LocalDateTime.now(), endTime);

        // Extract days, hours, minutes, and seconds
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        // Display the countdown in the TextView
        @SuppressLint("DefaultLocale") String countdownText = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
        binding.deadlineCounter.setText(countdownText);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //set up swipe refresh layout
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {

                    // This method performs the actual data-refresh operation and calls
                    handleRefreshClick();

                    // Stop the refreshing animation after the refresh operation is completed
                    binding.swipeRefreshLayout.setRefreshing(false);
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
                    initialResponse = myTeam.getGameWeekMyTeamResponseModel();
                    updateUI(initialResponse);
                    updateGameWeekDeadline();

                    break;
                case ERROR:
                    showFailure(apiResponse.getMessage());
                    break;
            }
        });
    }

    private void updateUI(GameWeekMyTeamResponseModel response) {
        updateChipsStatus(requireContext(), response.getChips()); //update chips
        updateTeamPlayers(response.getPicks()); // update team player
        updateFieldUI(binding.footballFieldLayout);
        currentTransfer = new Transfers(response.getTransfers()); // hold the data for next update
        updateTransferInfo(currentTransfer);
    }

    private void updateGameWeekDeadline() {
        GameWeekEvent gameWeekEvent = Constants.gameWeekMap.get(Constants.nextGameWeek);
        String gameWeekText = "GW" + Constants.nextGameWeek + " Deadline";
        binding.gameWeekDeadline.setText(gameWeekText);

        String localTime = convertUtcToLocalTime(gameWeekEvent.getDeadline_time());
        binding.deadlineText.setText(localTime);

        // Parse the local time string to LocalDateTime
        endTime = convertUtcToLocalDateTime(gameWeekEvent.getDeadline_time());

        // Start the countdown
        handler.post(countdownRunnable);
    }

    private void updateTeamPlayers(ArrayList<MyTeamPicks> picks) {

        this.teamPlayers = new ArrayList<>();
        for (int i = 0; i < picks.size(); i++) {
            PlayersData playersData = deepCopyPlayer(Constants.playerMap.get(picks.get(i).getElement()));

            if (playersData != null) {
                playersData.setIs_captain(picks.get(i).getIs_captain());
                playersData.setIs_vice_captain(picks.get(i).getIs_vice_captain());
                playersData.setPosition(i + 1);
                playersData.setSelling_price(picks.get(i).getSelling_price());
                playersData.setPurchase_price(picks.get(i).getPurchase_price());
                playersData.setMultiplier(picks.get(i).getMultiplier());
                playersData.setSingular_name_short(Objects.requireNonNull(Constants.playerTypeMap.get(playersData.getElement_type())).getSingular_name_short());
                playersData.setTeam_name_short(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getShort_name());
                playersData.setTeam_name_full(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getName());
                teamPlayers.add(playersData);
            } else {
                UIUtils.toast(requireContext(), "Player data is null please reload again", ToastLevel.WARNING);
                return;
            }
        }
//        for (MyTeamPicks myTeamPicks : picks) {
//            PlayersData playersData = deepCopyPlayer(Constants.playerMap.get(myTeamPicks.getElement()));
//
//            if (playersData != null) {
//                playersData.setIs_captain(myTeamPicks.getIs_captain());
//                playersData.setIs_vice_captain(myTeamPicks.getIs_vice_captain());
//                playersData.setPosition(myTeamPicks.getPosition());
//                playersData.setSelling_price(myTeamPicks.getSelling_price());
//                playersData.setPurchase_price(myTeamPicks.getPurchase_price());
//                playersData.setMultiplier(myTeamPicks.getMultiplier());
//                playersData.setSingular_name_short(Objects.requireNonNull(Constants.playerTypeMap.get(playersData.getElement_type())).getSingular_name_short());
//                playersData.setTeam_name_short(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getShort_name());
//                playersData.setTeam_name_full(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getName());
//                teamPlayers.add(playersData);
//            } else {
//                UIUtils.toast(requireContext(), "Player data is null please reload again", ToastLevel.WARNING);
//                return;
//            }
//        }

        this.initialTeamPlayers = deepCopyPlayerList(this.teamPlayers);

//        Log.d(Constants.LOG_TAG, "Initial Team Players");
//        printTeamPlayers(this.initialTeamPlayers);
    }

    private void addPlayers(GridLayout footballFieldLayout) {

        this.playerViewList = new ArrayList<>();

        List<PlayersData> goalkeepers = new ArrayList<>();
        List<PlayersData> defenders = new ArrayList<>();
        List<PlayersData> midfielders = new ArrayList<>();
        List<PlayersData> forwards = new ArrayList<>();

        for (int i = 0; i < teamPlayers.size(); i++) {

            PlayersData entry = teamPlayers.get(i);

            long playerType = entry.getElement_type();

            if ((Objects.requireNonNull(Constants.playerTypeMap.get(playerType)).getSingular_name_short().equalsIgnoreCase("DEF"))) {
                defenders.add(entry);
            } else if (Objects.requireNonNull(Constants.playerTypeMap.get(playerType)).getSingular_name_short().equalsIgnoreCase("MID")) {
                midfielders.add(entry);
            } else if (Objects.requireNonNull(Constants.playerTypeMap.get(playerType)).getSingular_name_short().equalsIgnoreCase("FWD")) {
                forwards.add(entry);

            } else if (Objects.requireNonNull(Constants.playerTypeMap.get(playerType)).getSingular_name_short().equalsIgnoreCase("GKP")) {
                goalkeepers.add(entry);
            } else {
                Log.d(Constants.LOG_TAG, "type not defined");
            }
        }

        //Adding players to the ui
        addPlayerNew(goalkeepers.get(0), 0, 1, footballFieldLayout); // playing goalkeeper
        addPlayerNew(goalkeepers.get(1), 0, 3, footballFieldLayout); // playing goalkeeper


        // formation for mid defenders
        if (defenders.size() == 5) { // Adding players for a 3-x-x formation (adjust positions based on your layout)
            addPlayerNew(defenders.get(0), 1, 0, footballFieldLayout);
            addPlayerNew(defenders.get(1), 1, 1, footballFieldLayout);
            addPlayerNew(defenders.get(2), 1, 2, footballFieldLayout);
            addPlayerNew(defenders.get(3), 1, 3, footballFieldLayout);
            addPlayerNew(defenders.get(4), 1, 4, footballFieldLayout);
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }

        // formation for mid fielders
        if (midfielders.size() == 5) {
            addPlayerNew(midfielders.get(0), 2, 0, footballFieldLayout);
            addPlayerNew(midfielders.get(1), 2, 1, footballFieldLayout);
            addPlayerNew(midfielders.get(2), 2, 2, footballFieldLayout);
            addPlayerNew(midfielders.get(3), 2, 3, footballFieldLayout);
            addPlayerNew(midfielders.get(4), 2, 4, footballFieldLayout);
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }

        // formation for forwards
        if (forwards.size() == 3) {
            addPlayerNew(forwards.get(0), 3, 1, footballFieldLayout);
            addPlayerNew(forwards.get(1), 3, 2, footballFieldLayout);
            addPlayerNew(forwards.get(2), 3, 3, footballFieldLayout);
        } else {
            Log.d(Constants.LOG_TAG, "Unknown Formation");
        }
    }

    public void addPlayerNew(PlayersData player, int row, int column, GridLayout footballFieldLayout) {

        PlayerView playerView = new PlayerView(requireContext(), player, false, null);
        playerView.setPlayerName(player.getWeb_name());
        playerViewList.add(playerView); // add reference of the player view for later modification

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

        String amount = "€" + (float) player.getSelling_price() / 10 + "m";
        playerView.setOpponentTeamName(amount);

        //https://resources.premierleague.com/premierleague/badges/rb/t14.svg team logo
        //https://resources.premierleague.com/premierleague/photos/players/250x250/p441164.png player photo

        String imgURL = "";
        if ((row == 0 && column == 1) || (row == 0 && column == 3)) // for two goal keeper shirt will be changed
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

        // highlight transfer player
        if (player.getSubstitute_number() > 0) {
            playerView.setHighlight("#e9ff32");
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

    private void showLoading() {
        viewModel.dataLoading.setValue(true);
        binding.progressCircular.setVisibility(View.VISIBLE);
        binding.footballFieldLayout.setVisibility(View.GONE);

    }

    private void showFailure(String error) {
        viewModel.dataLoading.setValue(false);
        binding.progressCircular.setVisibility(View.GONE);
//        binding.footballFieldLayout.setVisibility(View.GONE);
        Toasty.warning(requireContext(), error).show();
    }

    private void updateChipsStatus(Context context, ArrayList<GameChips> chips) {

//        setupButton(binding.buttonAutoPick, Chips.AP.getDisplayName());
        setupButton(binding.buttonWildCard, Chips.WC.getDisplayName());
        setupButton(binding.buttonFH, Chips.FH.getDisplayName());

        Map<String, MaterialButton> chipButtons = new HashMap<>();
//        chipButtons.put(Chips.AP.getShortName(), binding.buttonAutoPick);
        chipButtons.put(Chips.WC.getShortName(), binding.buttonWildCard);
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

//        updateButtonState(context, binding.buttonAutoPick, ButtonStateManager.ButtonState.NOT_AVAILABLE); //
    }

    private void updateFieldUI(GridLayout footballFieldLayout) {

        binding.progressCircular.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.GONE);
        addPlayers(footballFieldLayout);
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

                        if (newState == ButtonStateManager.ButtonState.ACTIVE) {
                            if (buttonName.equalsIgnoreCase(Chips.WC.getDisplayName())) {
                                activeChip = Chips.WC.getShortName();
                                activeChipCount++;
                            } else if (buttonName.equalsIgnoreCase(Chips.FH.getDisplayName())) {
                                activeChip = Chips.FH.getShortName();
                                activeChipCount--;
                            } else {
                                activeChip = Chips.FH.getShortName();
                                activeChipCount--;
                            }

                            //check if any transfer happens
                            if (activeChipCount == 0 && transferredPlayerList.isEmpty()) {
                                resetToolBar();
                            } else {
                                enableEditToolBar();
                            }

                        } else if (newState == ButtonStateManager.ButtonState.AVAILABLE) {
                            activeChip = null;
                            activeChipCount--;

                            //check if any transfer happens
                            if (activeChipCount == 0 && transferredPlayerList.isEmpty()) {
                                resetToolBar();
                            } else {
                                enableEditToolBar();
                            }

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

    public void updateTransferInfo(Transfers transfers) {
        updateTransferMade(transfers.getMade(), transfers.getLimit());
        updateCostAndFT(transfers.getMade(), transfers.getLimit());
        updateBankBalance(transfers.getBank(), transfers.getValue());
    }

    private void updateTransferMade(long transferMade, long limit) {

        SpannableStringBuilder builder = new SpannableStringBuilder();

        String blackText = "Transfer Made : ";
        SpannableString blackSpannable = new SpannableString(blackText);
        builder.append(blackSpannable);

        if (activeChip != null) {
            transferMade = 0;
        }
        String redOrGreenText = String.valueOf(transferMade);
        SpannableString redSpannable = new SpannableString(redOrGreenText);
        if (transferMade > limit) {
            redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, redOrGreenText.length(), 0);
        } else {
            redSpannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, redOrGreenText.length(), 0);
        }
        builder.append(redSpannable);

        binding.transferMade.setText(builder);
    }

    private void updateCostAndFT(long transferMade, long limit) {

        SpannableStringBuilder builder = new SpannableStringBuilder();

        String blackText = "Cost : ";
        SpannableString blackSpannable = new SpannableString(blackText);
        builder.append(blackSpannable);

        long costCalculation = limit - transferMade;

        if (costCalculation < 0) {
            costCalculation = costCalculation * 4;
        } else {
            costCalculation = 0;
        }

        if (activeChip != null) {
            costCalculation = 0;
        }
        String redOrGreenText = String.valueOf(costCalculation);
        SpannableString redSpannable = new SpannableString(redOrGreenText);
        if (costCalculation < 0) {
            redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, redOrGreenText.length(), 0);
        } else {
            redSpannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, redOrGreenText.length(), 0);
        }
        builder.append(redSpannable).append(", ");

        String nextBlackText = "FT : ";
        SpannableString nextBlackSpannable = new SpannableString(nextBlackText);
        builder.append(nextBlackSpannable);

        long ftCalculation = limit - transferMade;

        if (ftCalculation < 0) {
            ftCalculation = 0;
        }
        String greenText = String.valueOf(ftCalculation);

        if (activeChip != null) {
            greenText = "∞";
        }
        SpannableString greenSpannable = new SpannableString(greenText);
        greenSpannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, greenText.length(), 0);

        builder.append(greenSpannable);

        binding.transferCalculation.setText(builder);
    }

    private void updateBankBalance(long bank, long value) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        String blackText = "In Bank : ";
        SpannableString blackSpannable = new SpannableString(blackText);
        builder.append(blackSpannable);

        String redOrGreenText = String.valueOf("€" + (float) bank / 10 + "m");
        SpannableString redSpannable = new SpannableString(redOrGreenText);
        if (bank < 0) {
            redSpannable.setSpan(new ForegroundColorSpan(Color.RED), 0, redOrGreenText.length(), 0);
        } else {
            redSpannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, redOrGreenText.length(), 0);
        }
        builder.append(redSpannable);

//        String nextBlackText = "Squad Value : ";
//        SpannableString nextBlackSpannable = new SpannableString(nextBlackText);
//        builder.append(nextBlackSpannable);
//
//        String greenText = String.valueOf(value);
//        SpannableString greenSpannable = new SpannableString(greenText);
//        greenSpannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, greenText.length(), 0);

//        builder.append(greenSpannable);

        binding.bankValue.setText(builder);
    }

    @Override
    public void onPlayerDragged(int fromPosition, int toPosition, PlayerView draggedPlayerView, PlayerView dropPlayerView, boolean isSwapData) {

    }

    @Override
    public void onClickPlayer(PlayerView view) {
        showBottomSheetDialogue(view.getPlayerData());
    }

    private void showBottomSheetDialogue(PlayersData playersData) {
        TransferPlayerInfoBottomSheetFragment bottomSheet = TransferPlayerInfoBottomSheetFragment.newInstance(playersData);
        bottomSheet.setPlayerTransferListener(this);
        bottomSheet.show(requireActivity().getSupportFragmentManager(), bottomSheet.getTag());
    }

    @Override
    public void onTransferPlayer(PlayersData player) {


        PlayerSelectionFragment playerSelectionFragment = PlayerSelectionFragment.newInstance(player);
        // go to player selection
        FragmentUtils.replace(
                requireActivity().getSupportFragmentManager(),
                playerSelectionFragment,
                R.id.contentFrame,
                true,
                R.anim.enter_from_right, // enter
                R.anim.exit_to_left,      // exit
                R.anim.enter_from_right,   // popEnter
                R.anim.exit_to_left      // popExit
        );
    }

    @Override
    public void onCancelTransfer(PlayersData player) {
        TransferUpdate transferUpdate = transferredPlayerList.get(player.getPosition());

        Optional<PlayersData> playerOpt = findPlayerById(this.initialTeamPlayers, transferUpdate.getElement_out());

        PlayersData playerOrException = playerOpt.orElseThrow(() -> new RuntimeException("Player not found"));

        teamPlayers.set((int) player.getPosition() - 1, playerOrException);
        binding.footballFieldLayout.removeAllViews();
        updateFieldUI(binding.footballFieldLayout);

        long updatedBankBalance = currentTransfer.getBank() - playerOrException.getSelling_price() + player.getPurchase_price();
        currentTransfer.setBank(updatedBankBalance);
        long transferCount = currentTransfer.getMade();
        currentTransfer.setMade(++transferCount);

        updateTransferInfo(currentTransfer);

        transferredPlayerList.remove(player.getPosition());

        updateToolbar();
    }

    private void updateToolbar() {
        //check if any transfer happens
        if (activeChipCount == 0 && transferredPlayerList.isEmpty()) {
            resetToolBar();
        } else {
            enableEditToolBar();
        }
    }
}
