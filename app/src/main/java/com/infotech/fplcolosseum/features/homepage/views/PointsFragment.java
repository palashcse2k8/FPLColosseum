package com.infotech.fplcolosseum.features.homepage.views;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.databinding.FragmentPointsBinding;
import com.infotech.fplcolosseum.features.homepage.models.MergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.picks.Picks;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.ToolbarChangeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PointsFragment extends Fragment {

    FragmentPointsBinding binding;

    HomePageSharedViewModel viewModel;

    private ToolbarChangeListener toolbarChangeListener;

    private static final int NUM_ROWS = 5;
    private static final int NUM_COLUMNS = 5;


//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof ToolbarChangeListener) {
//            toolbarChangeListener = (ToolbarChangeListener) context;
//        } else {
//            throw new ClassCastException(context.toString() + " must implement PointsFragment.ToolbarChangeListener");
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPointsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
//        Toolbar pointToolBar = requireActivity().findViewById(R.id.pointToolbar);
////        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(pointToolBar);

//        // Reference the toolbar from the fragment's layout
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.appbarPoint.toolbarTest);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//
//        // If you need to modify the toolbar, do it here
//        if (activity.getSupportActionBar() != null) {
//            activity.getSupportActionBar().setTitle("Custom Toolbar");
//            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        // Reference the toolbar from the fragment's layout
//        Toolbar pointToolbar = rootView.findViewById(R.id.pointToolbar);


//        // Notify the parent to change the toolbar
//        if (toolbarChangeListener != null) {
//            toolbarChangeListener.onToolbarChanged(pointToolbar);
//        }

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
//        viewModel.getMyTeamData(Constants.LoggedInUser.getPlayer().getEntry());
        viewModel.getTeamCurrentGameWeekAllData(10359552);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the GridLayout in your fragment layout
        GridLayout footballFieldLayout = view.findViewById(R.id.footballFieldLayout);

        // Add players to the football field (customize positions as needed)

        viewModel.getMergedResponseLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse == null) return;
            if (apiResponse.getStatus() == ApiResponse.Status.SUCCESS) {
                viewModel.dataLoading.setValue(false);

                MergedResponseModel myTeam = (MergedResponseModel) apiResponse.getData();
                addPlayers(footballFieldLayout, myTeam);
                addLeftOverLayView(myTeam);
                addRightOverLayView(myTeam);
            }
        });
    }

    private void setUpToolBar(@NonNull View view){
        Toolbar pointToolBar = view.findViewById(R.id.pointToolbar);
//        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        ((AppCompatActivity) requireActivity()).setSupportActionBar(pointToolBar);
    }


    private void addRightOverLayView(MergedResponseModel myTeam) {
        OverlayView overlayView = new OverlayView(requireContext(), false);

        // Add overlay view initially to measure its size
        binding.frameLayout.addView(overlayView);

        // Measure the view
        overlayView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        overlayView.setLabel1TextView("Transfers");
        overlayView.setInfo1TextView(myTeam.getGameWeekPicksModel().getEntry_history().getEvent_transfers() + "(-" + myTeam.getGameWeekPicksModel().getEntry_history().getEvent_transfers_cost() + ")");

        overlayView.setLabel2TextView("Squad Val");
        overlayView.setInfo2TextView((double)myTeam.getGameWeekPicksModel().getEntry_history().getValue()/10 + "m");

        overlayView.setLabel3TextView("In Bank");
        overlayView.setInfo3TextView((double)myTeam.getGameWeekPicksModel().getEntry_history().getBank()/10 + "m");

        // Get device width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWidth = displayMetrics.widthPixels;

        // Calculate grid width (device width / 5)
        int gridWidth = deviceWidth / 5;

        // Calculate left margin to center the view in the middle of the 1st and 2nd grid
        int overlayViewWidth = overlayView.getMeasuredWidth();
        int leftMargin = (gridWidth*4) - gridWidth/4 - (overlayViewWidth / 2);

        int topMargin = 20;

        FrameLayout.LayoutParams overlayParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        overlayParams.gravity = Gravity.CENTER;
        overlayParams.leftMargin = leftMargin;
        overlayParams.topMargin = topMargin;
        overlayView.setLayoutParams(overlayParams);
//        binding.frameLayout.addView(overlayView, overlayParams);
    }

    private void addLeftOverLayView(MergedResponseModel myTeam) {

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
        int leftMargin = gridWidth - gridWidth/4 - (overlayViewWidth / 2);

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

    private void addPlayers(GridLayout footballFieldLayout, MergedResponseModel mergedResponseModel) {

        List<PlayersData> teamPlayers = new ArrayList<>();


        for (Picks myTeamPicks : mergedResponseModel.getGameWeekPicksModel().getPicks()) {
            PlayersData data = Constants.playerMap.get(myTeamPicks.getElement());
            assert data != null;
            data.setEvent_points(
                    mergedResponseModel.getGameWeekLivePointsResponseModel().elements.stream()
                            .filter(element -> element.id == myTeamPicks.getElement())
                            .findFirst()
                            .map(element -> element.stats.total_points)
                            .orElse(0) // Provide a default value if no matching element is found
            );
            teamPlayers.add(data);
        }

        List<PlayersData> defenders = new ArrayList<>();
        List<PlayersData> midfielders = new ArrayList<>();
        List<PlayersData> forwards = new ArrayList<>();

        for (int i = 1; i < teamPlayers.size() - 4; i++) {

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
    }

    public void addPlayerNew(PlayersData player, int row, int column, GridLayout footballFieldLayout) {

        PlayerView playerView = new PlayerView(requireContext(), player, false);
        playerView.setPlayerName(player.getWeb_name());
        playerView.setTeamName(player.getEvent_points() + "");

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
        if (player.isIs_captain()) {
            Log.d("Captain's Info", player.toString());
            playerView.setCaptain();
        }

        if (player.isIs_vice_captain()) {
            Log.d("Vice Captain's Info", player.toString());
            playerView.setViceCaptain();
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
}
