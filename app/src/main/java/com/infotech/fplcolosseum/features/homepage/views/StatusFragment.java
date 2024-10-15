package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.utilities.CustomUtil.convertDateToStringFormat;
import static com.infotech.fplcolosseum.utilities.CustomUtil.scrollToItem;
import static com.infotech.fplcolosseum.utilities.CustomUtil.sortPlayersByNewsAdded;
import static com.infotech.fplcolosseum.utilities.CustomUtil.startPlayerFullProfile;
import static com.infotech.fplcolosseum.utilities.CustomUtil.updatePlayerImage;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentStatusBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.BestLeaguesAdapter;
import com.infotech.fplcolosseum.features.homepage.adapter.LatestInjuredPlayerListAdapter;
import com.infotech.fplcolosseum.features.homepage.adapter.MostValuableTeamsAdapter;
import com.infotech.fplcolosseum.features.homepage.adapter.TopTransferPlayerListAdapter;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.ChipsPlayedInfo;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.status.BestLeagueDataModel;
import com.infotech.fplcolosseum.features.homepage.models.status.GameWeekStatus;
import com.infotech.fplcolosseum.features.homepage.models.status.Status;
import com.infotech.fplcolosseum.features.homepage.models.status.StatusMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.status.ValuableTeamDataModel;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.features.player_information.views.PlayerFullInformationActivity;
import com.infotech.fplcolosseum.utilities.Chips;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.PlayerSortingCriterion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StatusFragment extends Fragment {
    FragmentStatusBinding binding;
    private HomePageSharedViewModel sharedViewModel;

    private MenuProvider menuProvider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStatusBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
        sharedViewModel.getStatusMergedData(Constants.LoggedInUser.getPlayer().getEntry(), Constants.currentGameWeek);
        binding.setGameWeekEventIndex(Constants.currentGameWeek);
        binding.setHomePageViewModel(sharedViewModel);
        binding.setLifecycleOwner(this);
        setupToolbar();
        return binding.getRoot();
    }

    public void setupToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.statusToolbar);
    }

    @Override
    public void onResume() {
        super.onResume();
        addMenuProvider();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel.getStatusMergedResponseLiveData().observe(getViewLifecycleOwner(), statusMergedResponseModelApiResponse -> {
            if (statusMergedResponseModelApiResponse == null) return;
            switch (statusMergedResponseModelApiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    sharedViewModel.dataLoading.setValue(false);

                    StatusMergedResponseModel responseModel = statusMergedResponseModelApiResponse.getData();

                    updateUI(responseModel);
                    break;
                case ERROR:
                    showFailure(statusMergedResponseModelApiResponse.getMessage());
                    break;
            }
        });
    }

    private void showLoading() {
        sharedViewModel.dataLoading.setValue(false);
        binding.progressCircular.setVisibility(View.GONE);
    }

    private void showFailure(String error) {
        sharedViewModel.dataLoading.setValue(false);
        binding.progressCircular.setVisibility(View.GONE);
    }

    private void updateUI(StatusMergedResponseModel statusMergedResponseModel) {
        addGWStatus(statusMergedResponseModel.getGameWeekStatus());
        updateGameWeekSummary(Constants.currentGameWeek);
        updateTopPlayerList();
        updateTopTransferInData();
        updateLatestInjury();
        updateMostValuableTeamRecyclerView(statusMergedResponseModel.getValuableTeamDataModels());
        updateBestClassicLeagueRecyclerView(statusMergedResponseModel.getBestTeamDataModels());
    }

    private void updateTopPlayerList() {
        for (long i = 1; i <= Constants.currentGameWeek; i++) {
            View itemView = getLayoutInflater().inflate(R.layout.layout_top_player_view, binding.gameWeekTopPlayers, false);
            TextView playerName = itemView.findViewById(R.id.playerName);
            TextView gameWeekNumber = itemView.findViewById(R.id.gameWeekNumber);
            TextView pointInfo = itemView.findViewById(R.id.pointsInfo);
            ImageView playerImageView = itemView.findViewById(R.id.playerImageView);

            PlayersData topPlayer = Constants.playerMap.get(Constants.gameWeekMap.get(i).getTop_element());

            String gwNumber = "GW" + i;
            gameWeekNumber.setText(gwNumber);

            updatePlayerImage(playerImageView, topPlayer);
            playerName.setText(topPlayer.getWeb_name());
            String pointsInformation = String.valueOf(Constants.gameWeekMap.get(i).getTop_element_info().getPoints());

            pointInfo.setText(pointsInformation);

            itemView.setOnClickListener(v -> {
                startPlayerFullProfile(requireActivity(), topPlayer);
            });

            binding.gameWeekTopPlayers.addView(itemView);
        }

        // Scroll to the current game week after a short delay
        binding.gameWeekTopPlayersScrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollToCurrentGameWeek();
            }
        });

//        scrollToItem(binding.gameWeekTopPlayersScrollView, binding.gameWeekTopPlayers, (int) Constants.currentGameWeek);
    }

    private void scrollToCurrentGameWeek() {
        if (binding.gameWeekTopPlayers.getChildCount() > 0) {
            View targetView = binding.gameWeekTopPlayers.getChildAt((int)Constants.currentGameWeek - 1);
            if (targetView != null) {
                int targetX = targetView.getLeft();
                int screenWidth = binding.gameWeekTopPlayersScrollView.getWidth();
                int viewWidth = targetView.getWidth();

                // Center the target view
                targetX -= (screenWidth - viewWidth) / 2;

                // Ensure we don't scroll past the start
                targetX = Math.max(0, targetX);

                // Scroll to the target position
                binding.gameWeekTopPlayersScrollView.smoothScrollTo(targetX, 0);
            }
        }
    }

    private void updateLatestInjury() {
        binding.latestInjuryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<PlayersData> filteredTransferredInPlayerList = new ArrayList<>(Constants.playerMap.values());

        List<PlayersData> sortedPlayers = sortPlayersByNewsAdded(filteredTransferredInPlayerList);

        LatestInjuredPlayerListAdapter latestInjuredPlayerListAdapter = new LatestInjuredPlayerListAdapter(sortedPlayers, PlayerSortingCriterion.NEWS_ADDED.getDisplayName(), requireActivity());
        binding.latestInjuryRecyclerView.setAdapter(latestInjuredPlayerListAdapter);
    }

    private void updateTopTransferInData() {
        binding.topTransferInHeader.playerCriterion.setText("TIR");
        binding.topTransferInPlayerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<PlayersData> filteredTransferredInPlayerList = new ArrayList<>(Constants.playerMap.values());
        filteredTransferredInPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_in_event).reversed());
        TopTransferPlayerListAdapter topTransferInPlayerListAdapter = new TopTransferPlayerListAdapter(filteredTransferredInPlayerList, PlayerSortingCriterion.TRANSFERS_IN_ROUND.getDisplayName(), requireActivity());
        binding.topTransferInPlayerRecyclerView.setAdapter(topTransferInPlayerListAdapter);

        binding.topTransferOutHeader.playerCriterion.setText("TOR");
        binding.topTransferOutPlayerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<PlayersData> filteredTransferredOutPlayerList = new ArrayList<>(Constants.playerMap.values());
        filteredTransferredOutPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_out_event).reversed());
        TopTransferPlayerListAdapter topTransferOutPlayerListAdapter = new TopTransferPlayerListAdapter(filteredTransferredOutPlayerList, PlayerSortingCriterion.TRANSFERS_OUT_ROUND.getDisplayName(), requireActivity());
        binding.topTransferOutPlayerRecyclerView.setAdapter(topTransferOutPlayerListAdapter);
    }

    private void updateBestClassicLeagueRecyclerView(List<BestLeagueDataModel> bestLeagueDataModels) {

        binding.bestLeaguesTableHeader.layoutBestLeagueItem.setBackgroundColor(getResources().getColor(R.color.accent));
        BestLeaguesAdapter bestLeaguesAdapter = new BestLeaguesAdapter(bestLeagueDataModels.subList(0,5));
        binding.bestLeaguesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.bestLeaguesRecyclerView.setAdapter(bestLeaguesAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        binding.bestLeaguesRecyclerView.addItemDecoration(dividerItemDecoration);
        // Show more button click event
        binding.bestLeaguesShowMoreButton.setOnClickListener(v -> {
            // Show all items
            bestLeaguesAdapter.updateList(bestLeagueDataModels);
            binding.bestLeaguesShowMoreButton.setVisibility(View.GONE);  // Hide the button after showing all items
        });
    }

    private void updateMostValuableTeamRecyclerView(List<ValuableTeamDataModel> valuableTeamDataModels) {

        binding.mostValuableTableHeader.layoutBestLeagueItem.setBackgroundColor(getResources().getColor(R.color.accent));
        MostValuableTeamsAdapter valuableTeamsAdapter= new MostValuableTeamsAdapter(valuableTeamDataModels.subList(0,5));
        binding.mostValuableTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.mostValuableTeamRecyclerView.setAdapter(valuableTeamsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        binding.mostValuableTeamRecyclerView.addItemDecoration(dividerItemDecoration);
        // Show more button click event
        binding.mostValuableTeamsShowMoreButton.setOnClickListener(v -> {
            // Show all items
            valuableTeamsAdapter.updateList(valuableTeamDataModels);
            binding.mostValuableTeamsShowMoreButton.setVisibility(View.GONE);  // Hide the button after showing all items
        });
    }

    private void updateGameWeekSummary(long gameWeek) {

       GameWeekEvent gameWeekEvent = Constants.GameWeekStaticData.getEvents().get((int)gameWeek-1);
       binding.gameWeekSummaryMostSelectedTV.setText(Objects.requireNonNull(Constants.playerMap.get(gameWeekEvent.getMost_selected())).getWeb_name());
       binding.gameWeekSummaryMostCaptainTV.setText(Objects.requireNonNull(Constants.playerMap.get(gameWeekEvent.getMost_captained())).getWeb_name());
       binding.gameWeekSummaryMostViceCaptainTV.setText(Objects.requireNonNull(Constants.playerMap.get(gameWeekEvent.getMost_vice_captained())).getWeb_name());
       binding.gameWeekSummaryMostTransferredInTV.setText(Objects.requireNonNull(Constants.playerMap.get(gameWeekEvent.getMost_transferred_in())).getWeb_name());
       binding.gameWeekSummaryTransferMadeTV.setText(String.valueOf(gameWeekEvent.getTransfers_made()));

       Optional<Long> wildcardPlays = gameWeekEvent.getChip_plays().stream()
                .filter(chip -> Chips.WC.getShortName().equalsIgnoreCase(chip.getChip_name()))
                .map(ChipsPlayedInfo::getNum_played)
                .findFirst();

       binding.gameWeekSummaryWCTV.setText(String.valueOf(wildcardPlays.isPresent()? wildcardPlays.get() : "0"));

       Optional<Long> bbPlays = gameWeekEvent.getChip_plays().stream()
                .filter(chip -> Chips.BB.getShortName().equalsIgnoreCase(chip.getChip_name()))
                .map(ChipsPlayedInfo::getNum_played)
                .findFirst();
        binding.gameWeekSummaryBBTV.setText(String.valueOf(bbPlays.isPresent()? bbPlays.get() : "0"));

        Optional<Long> fhPlays = gameWeekEvent.getChip_plays().stream()
                .filter(chip -> Chips.FH.getShortName().equalsIgnoreCase(chip.getChip_name()))
                .map(ChipsPlayedInfo::getNum_played)
                .findFirst();
        binding.gameWeekSummaryFHTV.setText(String.valueOf(fhPlays.isPresent()? fhPlays.get() : "0"));

        Optional<Long> tcPlays = gameWeekEvent.getChip_plays().stream()
                .filter(chip -> Chips.TC.getShortName().equalsIgnoreCase(chip.getChip_name()))
                .map(ChipsPlayedInfo::getNum_played)
                .findFirst();
        binding.gameWeekSummaryTCTV.setText(String.valueOf(tcPlays.isPresent()? tcPlays.get() : "0"));

        binding.gameWeekAveragePointTV.setText(String.valueOf(gameWeekEvent.getAverage_entry_score()));
        binding.gameWeekMaximumPointTV.setText(String.valueOf(gameWeekEvent.getHighest_score()));

    }

    private void addGWStatus(GameWeekStatus gameWeekStatus) {

        binding.layoutMatchDayStatus.removeAllViews();
        for (Status status : gameWeekStatus.getStatus()) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_league_table_status, binding.layoutMatchDayStatus, false);
            ((TextView) itemView.findViewById(R.id.statusDayTV)).setText(convertDateToStringFormat(status.getDate()));
            ((TextView) itemView.findViewById(R.id.statusMatchTV)).setText(status.getPoints().equalsIgnoreCase("r")? "Confirmed" : "Not Confirmed");
            ((TextView) itemView.findViewById(R.id.statusBonusTV)).setText((status.getBonus_added()? "Added": "Not Added"));

            binding.layoutMatchDayStatus.addView(itemView);
        }
    }

    private void addMenuProvider() {
        // Add a menu provider to manage the menu lifecycle

        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear(); // Clear previous menu
                menuInflater.inflate(R.menu.menu_status, menu); // Inflate home-specific menu
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    item.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Handle menu item selection
                if (menuItem.getItemId() == R.id.reload) {
                    sharedViewModel.getStatusMergedData(Constants.LoggedInUser.getPlayer().getEntry(), Constants.currentGameWeek);
                    return true;
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove the menu provider when the fragment's view is destroyed
        requireActivity().removeMenuProvider(menuProvider);
    }
}
