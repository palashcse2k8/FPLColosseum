package com.infotech.fplcolosseum.features.homepage.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentTransfersBinding;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.MyTeamPicks;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.viewmodels.MyTeamViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.Duration;
import java.time.LocalDateTime;

public class TransferFragment extends Fragment {

    FragmentTransfersBinding binding;

    MyTeamViewModel viewModel;

    private static final String ARG_ITEM_DATA = "entry_id";

    private long entry_id;

    private LocalDateTime endTime;

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
        viewModel = new ViewModelProvider(requireActivity()).get(MyTeamViewModel.class);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTransfersBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        binding.setMyTeamViewModel(viewModel);
        viewModel.dataLoading.setValue(true);
        binding.setLifecycleOwner(this);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if (item.getItemId() == R.id.logout) {
            logOutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logOutUser(){
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

        // Find the GridLayout in your fragment layout
        GridLayout footballFieldLayout = view.findViewById(R.id.footballFieldLayout);

        endTime = LocalDateTime.of(2024, 12, 31, 23, 59, 59);

        // Observe changes in DataState
        viewModel.getMyTeamApiResultLiveData().observe(getViewLifecycleOwner(), apiResponse -> {

            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);
                    handler.post(countdownRunnable);
                    if(apiResponse.getData() instanceof GameWeekMyTeamResponseModel){
                        GameWeekMyTeamResponseModel data = (GameWeekMyTeamResponseModel) apiResponse.getData();
                        updateUI(footballFieldLayout, data);
                    }

                    break;
                case ERROR:
                    showFailure(apiResponse.getMessage());
                    break;
            }
        });

        // Trigger data fetching
        viewModel.getMyTeamDataIfNeeded(entry_id);
    }

    private void addPlayers(GridLayout footballFieldLayout, GameWeekMyTeamResponseModel myTeam) {

        List<PlayersData> teamPlayers = new ArrayList<>();

        for (MyTeamPicks myTeamPicks : myTeam.getPicks()) {
            PlayersData playersData = Constants.playerMap.get(myTeamPicks.getElement());
            assert playersData != null;
            playersData.setIs_captain(myTeamPicks.getIs_captain());
            playersData.setIs_vice_captain(myTeamPicks.getIs_vice_captain());
            playersData.setSelling_price(myTeamPicks.getSelling_price());
            playersData.setPurchase_price(myTeamPicks.getPurchase_price());
            playersData.setMultiplier(myTeamPicks.getMultiplier());
            playersData.setPosition(myTeamPicks.getMultiplier());
            teamPlayers.add(playersData);
        }

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

        PlayerView playerView = new PlayerView(requireContext(), player, false);
        playerView.setPlayerName(player.getWeb_name());

        String amount = "€" + (float)player.getSelling_price()/10 + "m";
        playerView.setTeamName(amount);

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

    private void updateUI(GridLayout footballFieldLayout, GameWeekMyTeamResponseModel data) {

        binding.progressCircular.setVisibility(View.GONE);
        binding.progressCircular.setVisibility(View.GONE);
        addPlayers(footballFieldLayout, data);
    }

    private void showFailure(String error) {
        viewModel.dataLoading.setValue(false);
        binding.progressCircular.setVisibility(View.GONE);
        binding.footballFieldLayout.setVisibility(View.GONE);
    }
}
