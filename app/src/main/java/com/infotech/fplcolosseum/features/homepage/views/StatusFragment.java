package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.utilities.CustomUtil.convertDateToStringFormat;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentStatusBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.MostValuableTeamsAdapter;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.ChipsPlayedInfo;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.status.GameWeekStatus;
import com.infotech.fplcolosseum.features.homepage.models.status.Status;
import com.infotech.fplcolosseum.features.homepage.models.status.StatusMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.status.ValuableTeamDataModel;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.Chips;
import com.infotech.fplcolosseum.utilities.Constants;

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
        updateMostValuableTeamRecyclerView(statusMergedResponseModel.getValuableTeamDataModels());

    }

    private void updateMostValuableTeamRecyclerView(List<ValuableTeamDataModel> valuableTeamDataModels) {

        binding.mostValuableTableHeader.layoutBestLeagueItem.setBackgroundColor(getResources().getColor(R.color.accent));
        MostValuableTeamsAdapter valuableTeamsAdapter= new MostValuableTeamsAdapter(valuableTeamDataModels.subList(0,5));
        binding.mostValuableTeamRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.mostValuableTeamRecyclerView.setAdapter(valuableTeamsAdapter);

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
                    // Handle home search action
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
