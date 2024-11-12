package com.infotech.fplcolosseum.features.league_information.views;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.ActivityLeagueInformationBinding;
import com.infotech.fplcolosseum.features.league_information.adapters.LeagueInformationPageAdapter;
import com.infotech.fplcolosseum.features.league_information.models.League;
import com.infotech.fplcolosseum.features.league_information.models.LeagueInformationDataModel;
import com.infotech.fplcolosseum.features.league_information.viewmodels.LeagueInformationViewModel;

public class LeagueInformationActivity extends AppCompatActivity {

    public static String LEAGUE_ID = "league_id";

    ActivityLeagueInformationBinding binding;
    long leagueId;
    LeagueInformationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityLeagueInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Retrieve player name from the Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.leagueId = extras.getLong(LEAGUE_ID);

            viewModel = new ViewModelProvider(this).get(LeagueInformationViewModel.class);
            binding.setLeagueInfoViewModel(viewModel);
            binding.setLifecycleOwner(this);
            viewModel.getLeagueInformation(leagueId, 1, 1, 1);
        }


        // Observe the loading state to hide/show progress and content
//        viewModel.dataLoading.observe(this, isLoading -> {
//            if (isLoading != null) {
//                binding.progressCircular.setVisibility(isLoading ? View.VISIBLE : View.GONE);
//                binding.playerDataLayout.setVisibility(isLoading ? View.GONE : View.VISIBLE);
//            }
//        });
        // wait for data loading
        viewModel.getLeagueStandingApiLiveData().observe(this, apiResponse -> {

            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
//                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    LeagueInformationDataModel leagueInformationDataModel = (LeagueInformationDataModel) apiResponse.getData();

                    setUpTabLayout(leagueInformationDataModel);

                    updateUI(leagueInformationDataModel);

                    break;
                case ERROR:
//                    showFailure(apiResponse.getMessage());
                    break;
            }
        });

    }

    private void updateUI(LeagueInformationDataModel dataModel) {
        setUpToolbar(dataModel.getLeague());

    }

    public void setUpTabLayout(LeagueInformationDataModel leagueInformationDataModel) {
        // Set up the adapter


        LeagueInformationPageAdapter adapter = new LeagueInformationPageAdapter(this);
        binding.viewPager.setAdapter(adapter);

        // Link the TabLayout and the ViewPager2
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Standing");
                                break;
                            case 1:
                                tab.setText("New Entries");
                                break;
                        }
                    }
                }
        ).attach();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    public void setUpToolbar(League league) {
        // Set the title based on the received name (optional)
        if (league != null) {
            binding.leagueStandingToolbar.setTitle(league.getName());
            binding.leagueStandingToolbar.setSubtitle(String.valueOf(league.getId()));
            binding.leagueStandingToolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.white));
        }

        setSupportActionBar(binding.leagueStandingToolbar);

        // Enable the Up button (back key)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //change the back button color to white
        Drawable navigationIcon = binding.leagueStandingToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }
}