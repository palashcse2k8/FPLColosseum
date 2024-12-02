package com.infotech.fplcolosseum.features.cup_status.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.infotech.fplcolosseum.databinding.ActivityCupStatusBinding;
import com.infotech.fplcolosseum.features.cup_status.model.CupStatusResponseModel;
import com.infotech.fplcolosseum.features.cup_status.viewmodels.CupStatusViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.concurrent.ConcurrentHashMap;

public class CupStatusActivity extends AppCompatActivity {


    ActivityCupStatusBinding binding;
    private long leagueId;
    private CupStatusViewModel viewModel;

    public static final String ARG_LEAGUE_ID = "league_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCupStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(CupStatusViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        setUpObserver();
        setUpViews();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(ARG_LEAGUE_ID)) {
            leagueId = intent.getLongExtra(ARG_LEAGUE_ID, 0L);
            viewModel.getCupStatus(leagueId);
        }
    }

    public void setUpViews() {

        binding.swipeRefresh.setOnRefreshListener(() -> {
            // This method performs the actual data-refresh operation and calls
            handleRefresh();

            // Stop the refreshing animation after the refresh operation is completed
            binding.swipeRefresh.setRefreshing(false);
        });

        binding.swipeRefresh.setEnabled(false);
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

    public void setUpObserver() {

        viewModel.getCupStatusLiveData().observe(this, apiResponse -> {
            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
//                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    CupStatusResponseModel responseData = apiResponse.getData();
                    updateToolBar(responseData.getName());
                    updateUI(responseData);
                    break;
                case ERROR:
//                    showFailure(apiResponse.getMessage());
                    break;
            }
        });
    }

    private void updateUI(CupStatusResponseModel responseData) {
        if (Constants.currentGameWeek < responseData.getQualification_event()) {
            String cupStatusTitleText = "The CUP is schedule to start in GW" + responseData.getQualification_event();
            binding.cupStatusTitle.setText(cupStatusTitleText);

            String cupQualificationDescription = "Fixtures will be determined at the end of Gameweek " + responseData.getQualification_event()
                    + ". If there are " + responseData.getQualification_numbers() + " teams in the associated league, then each team will have an opponent in Gameweek " + (responseData.getQualification_event() + 1)
                    + ". If there are between " + (responseData.getQualification_numbers() / 2 + 1) + " and " + (responseData.getQualification_numbers() - 1) + " teams in the league, some teams will receive a bye in Gameweek " + (responseData.getQualification_event()) + " based on their score in Gameweek " + (responseData.getQualification_event() - 1) + "."
                    + "\n\nThe starting round of the cup is determined by the number of the teams in the associated league and the final will be contested in Gameweek 38."
                    + "\n\nYou will not entered into the cup if you have joined the league after the Gameweek prior to the cup starting.";
            binding.cupQualificationDescription.setText(cupQualificationDescription);
        }

    }

    private void updateToolBar(String name) {
        binding.cupStatusToolbar.setTitle(name);
    }

    public void setUpToolbar() {
        // Set the title based on the received name (optional)
        setSupportActionBar(binding.cupStatusToolbar);

        // Enable the Up button (back key)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //change the back button color to white
        Drawable navigationIcon = binding.cupStatusToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void handleRefresh() {
        viewModel.getCupStatus(leagueId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
