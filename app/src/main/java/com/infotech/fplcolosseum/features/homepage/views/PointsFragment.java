package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.utilities.CustomUtil.deepCopyPlayer;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.databinding.FragmentPointsBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.OnPlayerClickOrDragListener;
import com.infotech.fplcolosseum.features.homepage.models.PointsMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.TeamInformationResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.Element;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.GameWeekLivePointsResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.PlayerPosition;
import com.infotech.fplcolosseum.features.homepage.models.picks.AutomaticSubs;
import com.infotech.fplcolosseum.features.homepage.models.picks.Picks;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.PlayerPositioningStrategy;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PointsFragment extends Fragment implements OnPlayerClickOrDragListener {

    FragmentPointsBinding binding;

    HomePageSharedViewModel viewModel;

    @Nullable
    private List<PlayersData> teamPlayers; // Initialize only when needed

    boolean isRefreshVisible = true;  // Hide refresh button
    boolean isShareVisible = true;    // Show share button

    int selectedChip;
    long selectedGameWeek;
    MenuProvider menuProvider;

    private static final String ARG_ITEM_DATA = "entry_id";
    private long entry_id;
    private final Map<Long, Integer> playerPositionMap = new HashMap<>();


    public PointsFragment newInstance(long index) {
        Bundle args = new Bundle();
        args.putLong(ARG_ITEM_DATA, index);

        PointsFragment fragment = new PointsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPointsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
//        Toolbar pointToolBar = requireActivity().findViewById(R.id.pointToolbar);
////        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(pointToolBar);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        return rootView;
    }

    public void addMenuProvider() {

        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

                menu.clear();
                // Inflate the menu; this adds items to the action bar if it is present.
                menuInflater.inflate(R.menu.points_menu, menu);

                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    item.setIconTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(requireContext(), R.color.white)));
                }

            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {

                // Handle option Menu Here
                // Handle action bar item clicks here
                int id = menuItem.getItemId();

                if (id == R.id.action_refresh) {
                    handleRefreshClick();
                    return true;
                } else if (id == R.id.action_share) {
                    handleShareClick();
                    return true;
                } else if (id == R.id.action_team_of_the_week) {
                    gotoDreamTeam();
                    return true;
                } else if (id == R.id.action_gameWeek_history) {
                    gotoGameWeekHistory();
                    return true;
                } else if (id == R.id.action_transfer_history) {
                    gotoTransferHistory();
                    return true;
                } else if(id == R.id.action_manager_of_the_week) {
                    long topMangerOfTheWeek = Objects.requireNonNull(Constants.gameWeekMap.get(selectedGameWeek)).getHighest_scoring_entry();
                    CustomUtil.startManagerDashboardActivity(requireContext(), topMangerOfTheWeek);
                    return true;
                }

                return false;
            }

            @Override
            public void onPrepareMenu(@NonNull Menu menu) {

                MenuItem refreshItem = menu.findItem(R.id.action_refresh);
                MenuItem shareItem = menu.findItem(R.id.action_share);

                // Set visibility based on your conditions
                if (refreshItem != null) refreshItem.setVisible(isRefreshVisible);
                if (shareItem != null) shareItem.setVisible(isShareVisible);

                MenuProvider.super.onPrepareMenu(menu);
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            entry_id = args.getLong(ARG_ITEM_DATA);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);

        viewModel.getPointsMergedData(entry_id, Constants.currentGameWeek);
        selectedChip = (int) Constants.currentGameWeek;

        setRetainInstance(true);
    }

    private void handleRefreshClick() {
        // Logic for refresh button
        resetChipSelection((int) Constants.currentGameWeek);
        setUpToolbar(Constants.currentGameWeek);
        binding.footballFieldLayout.removeAllViews();
        viewModel.getPointsMergedData(entry_id, Constants.currentGameWeek);
        resetToolBar();
    }

    private void handleShareClick() {
        // Logic for share button
        Toast.makeText(getActivity(), "Share clicked", Toast.LENGTH_SHORT).show();
    }

    private void gotoDreamTeam() {
        // Logic for dream team action
        CustomUtil.startDreamTeamActivity(requireActivity(), selectedGameWeek);
    }
    private void gotoTransferHistory() {
        // Logic for transfer history action
        CustomUtil.startTransferHistoryActivity(requireActivity(), entry_id);
    }

    private void gotoGameWeekHistory() {
        // Logic for share button
        CustomUtil.startGameWeekHistoryActivity(requireActivity(), entry_id);
    }

    private void resetToolBar() {
//        isClearVisible = false;     // Hide undo button
//        isSaveVisible = false;     // Hide save button
//        requireActivity().invalidateOptionsMenu();
    }

    @Override
    public void onResume() {
        super.onResume();

        addMenuProvider();
        // Check if a chip is already selected; if so, don't reset the toolbar
//        if (selectedChip == (int) Constants.currentGameWeek) {
//            setUpToolbar(selectedChip);
//        }
        setUpToolbar(selectedChip);
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

        // Add chips for game week selection
        setupChips();
        setupNavigationButtons();

        // Add players to the football field (customize positions as needed)
        viewModel.getPointsMergedResponseLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
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

                PointsMergedResponseModel myTeam = apiResponse.getData();
//
//
//                setUpToolbar(selectedGameWeek);
//
//                addPlayers(footballFieldLayout, myTeam);

                updateUI(myTeam);

//                addLeftOverLayView(myTeam);
//                addRightOverLayView(myTeam);
            }
        });

        selectedGameWeek = Constants.currentGameWeek;
    }

    // Batch UI updates
    private void updateUI(PointsMergedResponseModel data) {
        // ... rest of the method
        updateTeamPlayers(data.getGameWeekPicksModel().getPicks(), data.getGameWeekLivePointsResponseModel());
        setUpToolbar(selectedGameWeek);
        binding.footballFieldLayout.post(() -> {
            binding.footballFieldLayout.removeAllViews();
            addPlayers(binding.footballFieldLayout, data);
        });
    }


    private void setupChips() {

        for (int i = 1; i <= Constants.currentGameWeek; i++) {
            final Chip chip = new Chip(requireContext());
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
                    setUpToolbar(finalI);
                    binding.footballFieldLayout.removeAllViews();
                    viewModel.getPointsMergedData(entry_id, finalI);
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

        Toolbar toolbar = requireActivity().findViewById(R.id.pointToolbar);

        if (toolbar != null && viewModel.getTeamInformationApiResultLiveData().getValue() != null) {

            TeamInformationResponseModel data = viewModel.getTeamInformationApiResultLiveData().getValue().getData();

            if (data != null) {

                String concatenatedName = data.getName() + " (GW " + gameWeekNumber + ")";
                viewModel.setToolbarTitle(concatenatedName);

                String fullName = data.getPlayer_first_name() + " " + data.getPlayer_last_name();
                viewModel.setToolbarSubTitle(fullName);
            }
        }
    }

    private void addRightOverLayView(PointsMergedResponseModel myTeam) {
        OverlayView overlayView = new OverlayView(requireContext(), false);

        // Add overlay view initially to measure its size
        binding.frameLayout.addView(overlayView);

        // Measure the view
        overlayView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        overlayView.setLabel1TextView("Transfers");
        overlayView.setInfo1TextView(myTeam.getGameWeekPicksModel().getEntry_history().getEvent_transfers() + "(-" + myTeam.getGameWeekPicksModel().getEntry_history().getEvent_transfers_cost() + ")");

        overlayView.setLabel2TextView("Squad Val");
        overlayView.setInfo2TextView((double) myTeam.getGameWeekPicksModel().getEntry_history().getValue() / 10 + "m");

        overlayView.setLabel3TextView("In Bank");
        overlayView.setInfo3TextView((double) myTeam.getGameWeekPicksModel().getEntry_history().getBank() / 10 + "m");

        // Get device width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;

        // Calculate grid width (device width / 5)
        int gridWidth = deviceWidth / 5;

        // Calculate left margin to center the view in the middle of the 1st and 2nd grid
        int overlayViewWidth = overlayView.getMeasuredWidth();
        int leftMargin = (gridWidth * 4) - gridWidth / 4 - (overlayViewWidth / 2);

        int topMargin = 20;

        FrameLayout.LayoutParams overlayParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        overlayParams.gravity = Gravity.CENTER;
        overlayParams.leftMargin = leftMargin;
        overlayParams.topMargin = topMargin;
        overlayView.setLayoutParams(overlayParams);
//        binding.frameLayout.addView(overlayView, overlayParams);
    }

    private void addLeftOverLayView(PointsMergedResponseModel myTeam) {

        OverlayView overlayView = new OverlayView(requireContext(), false);

        // Add overlay view initially to measure its size
        binding.frameLayout.addView(overlayView);

        // Measure the view
        overlayView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);


        int gameWeekNumber = (int) myTeam.getGameWeekPicksModel().getEntry_history().getEvent();
        int gameWeekEventIndex = gameWeekNumber - 1;

        overlayView.setLabel1TextView("Final Points");
        overlayView.setInfo1TextView(myTeam.getGameWeekPicksModel().getEntry_history().getPoints() + "");

        overlayView.setLabel2TextView("Avg Points");
        overlayView.setInfo2TextView(String.valueOf(Constants.GameWeekStaticData.getEvents().get(gameWeekEventIndex).getAverage_entry_score()));

        overlayView.setLabel3TextView("Max Points");
        overlayView.setInfo3TextView(String.valueOf(Constants.GameWeekStaticData.getEvents().get(gameWeekEventIndex).getHighest_score()));

        // Get device width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;
        int deviceHeight = displayMetrics.heightPixels;

        // Calculate grid width (device width / 5)
        int gridWidth = deviceWidth / 5;

        // Calculate left margin to center the view in the middle of the 1st and 2nd grid
        int overlayViewWidth = overlayView.getMeasuredWidth();
        int leftMargin = gridWidth - gridWidth / 4 - (overlayViewWidth / 2);

        int topMargin = 20;

        FrameLayout.LayoutParams overlayParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        overlayParams.gravity = Gravity.CENTER;
        overlayParams.leftMargin = leftMargin;
        overlayParams.topMargin = topMargin;
        overlayView.setLayoutParams(overlayParams);
//        binding.frameLayout.addView(overlayView, overlayParams);
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }


    private void updateTeamPlayers(ArrayList<Picks> picks, GameWeekLivePointsResponseModel gameWeekLivePointsResponseModel) {

        Objects.requireNonNull(picks, "Picks cannot be null");
        Objects.requireNonNull(gameWeekLivePointsResponseModel, "LivePoints cannot be null");

        this.teamPlayers = new ArrayList<>();
        for (Picks myTeamPicks : picks) {
            PlayersData playersData = deepCopyPlayer(Constants.playerMap.get(myTeamPicks.getElement()));

            if (playersData != null) {
                playersData.setIs_captain(myTeamPicks.getIs_captain());
                playersData.setIs_vice_captain(myTeamPicks.getIs_vice_captain());
                playersData.setMultiplier(myTeamPicks.getMultiplier());
                playersData.setPosition(myTeamPicks.getPosition());
                playersData.setSingular_name_short(Objects.requireNonNull(Constants.playerTypeMap.get(playersData.getElement_type())).getSingular_name_short());
                playersData.setTeam_name_short(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getShort_name());
                playersData.setTeam_name_full(Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getName());

                playersData.setEvent_points(
                        gameWeekLivePointsResponseModel.elements.stream()
                                .filter(element -> element.id == playersData.getId())
                                .findFirst()
                                .map(element -> element.stats.total_points)
                                .orElse(0L) // Provide a default value if no matching element is found
                );

                playersData.setStarts(
                        gameWeekLivePointsResponseModel.elements.stream()
                                .filter(element -> element.id == playersData.getId())
                                .findFirst()
                                .map(element -> element.stats.starts)
                                .orElse(0) // Provide a default value if no matching element is found
                );

                teamPlayers.add(playersData);
            } else {
                UIUtils.toast(requireContext(), "Player data is null please reload again", ToastLevel.WARNING);
                return;
            }
        }
    }

    private void addPlayers(GridLayout footballFieldLayout, PointsMergedResponseModel pointsMergedResponseModel) {

        try {
            if (pointsMergedResponseModel == null || footballFieldLayout == null) {
                Log.e(Constants.LOG_TAG, "Null parameters in addPlayers");
                return;
            }

            initializePlayerPositionMap(); // initialize the teamPlayer map for later look up

            substitutePlayer(pointsMergedResponseModel.getGameWeekPicksModel().getAutomatic_subs());
//            // Extract main players (first 11 players) for positioning
//            List<PlayersData> mainPlayers = new ArrayList<>(teamPlayers);
//            List<PlayerPosition> playerPositions = PlayerPositioningStrategy.getPositionsForFormation(mainPlayers);
//
//            // Loop through player positions and add them to the football field layout
//            for (PlayerPosition position : playerPositions) {
//                addPlayerNew(position.getPlayer(), position.getRow(), position.getColumn(), footballFieldLayout);
//            }

            List<PlayersData> defenders = new ArrayList<>();
            List<PlayersData> midfielders = new ArrayList<>();
            List<PlayersData> forwards = new ArrayList<>();

            for (int i = 1; i < Objects.requireNonNull(teamPlayers).size() - 4; i++) {

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

            addPlayerNew(teamPlayers.get(11), 4, 0, footballFieldLayout); // bench goal keeper
            addPlayerNew(teamPlayers.get(12), 4, 2, footballFieldLayout); // first bench
            addPlayerNew(teamPlayers.get(13), 4, 3, footballFieldLayout); // second bench
            addPlayerNew(teamPlayers.get(14), 4, 4, footballFieldLayout); // third bench
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error adding players", e);
            UIUtils.toast(requireContext(), "Error setting up team", ToastLevel.ERROR);
        }
    }

    private void substitutePlayer(ArrayList<AutomaticSubs> automaticSubs) {
//        printTeamPlayers(this.teamPlayers);
        for (AutomaticSubs sub : automaticSubs) {
            int fromPosition = Objects.requireNonNull(playerPositionMap.get(sub.getElement_in()));
            int toPosition = Objects.requireNonNull(playerPositionMap.get(sub.getElement_out()));
            assert this.teamPlayers != null;
            this.teamPlayers.get(fromPosition).setSubstitute_number(1);
            this.teamPlayers.get(toPosition).setSubstitute_number(1);
        }
//        printTeamPlayers(this.teamPlayers);
    }


    private void initializePlayerPositionMap() {
        for (int i = 0; i < Objects.requireNonNull(teamPlayers).size(); i++) {
            playerPositionMap.put(teamPlayers.get(i).getId(), i);
        }
    }

    public void addPlayerNew(PlayersData player, int row, int column, GridLayout footballFieldLayout) {

        PlayerView playerView = new PlayerView(requireContext(), player, false, null);
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

        HashMap<Long, OpponentData> fixtures = (HashMap<Long, OpponentData>) Constants.fixtureData.get(Constants.nextGameWeek);
        assert fixtures != null;
        OpponentData opponentData = fixtures.get(player.getTeam());
        assert opponentData != null;

        if (player.getStarts() != 0) {
            playerView.setOpponentTeamName((player.getEvent_points() * player.getMultiplier()) + "");
        } else {
            playerView.setOpponentTeamName("-");
        }


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

        //set captaincy
        if (player.isIs_captain()) {
            playerView.setCaptain();
        }

        //set vice captain icon
        if (player.isIs_vice_captain()) {
            playerView.setViceCaptain();
        }

        // set substitute player icon
        if (player.getSubstitute_number() > 0) {
            playerView.setSubstitutePlayer();
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

        OpponentData matchDetails = Objects.requireNonNull(Constants.fixtureData.get(selectedGameWeek)).get(playersData.getTeam());

        Element playerPointExplain = Objects.requireNonNull(viewModel.getPointsMergedResponseLiveData().getValue()).getData().getGameWeekLivePointsResponseModel()
                .elements.stream()
                .filter(element -> element.id == playersData.getId())
                .findFirst()
                .orElse(null); // Provide a default value if no matching element is found

        PointsPlayerInfoBottomSheetFragment bottomSheet = PointsPlayerInfoBottomSheetFragment.newInstance(playersData, matchDetails, playerPointExplain);
        bottomSheet.show(requireActivity().getSupportFragmentManager(), bottomSheet.getTag());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (menuProvider != null) {
            requireActivity().removeMenuProvider(menuProvider);
        }

        binding = null;
        cleanupResources();
    }

    private void cleanupResources() {
        teamPlayers = null;
        playerPositionMap.clear();
    }

}
