package com.infotech.fplcolosseum.features.player_information.views;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.ActivityPlayerFullInformationBinding;
import com.infotech.fplcolosseum.databinding.AppbarLayoutBinding;
import com.infotech.fplcolosseum.features.homepage.models.MyTeamMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.features.player_information.adapter.PlayerInformationPageAdapter;
import com.infotech.fplcolosseum.features.player_information.models.ElementSummary;
import com.infotech.fplcolosseum.features.player_information.viewmodels.PlayerInformationViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PlayerFullInformationActivity extends AppCompatActivity {

    ActivityPlayerFullInformationBinding binding;
    PlayersData playersData;
    PlayerInformationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityPlayerFullInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve player name from the Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.playersData = (PlayersData) extras.getSerializable("playerData");
            binding.setPlayer(playersData); // Assuming "playerData" is the key
            viewModel = new ViewModelProvider(this).get(PlayerInformationViewModel.class);
            binding.setPlayerInfoViewModel(viewModel);
            viewModel.getElementSummary(playersData.getId());

            String imageUrl = "https://resources.premierleague.com/premierleague/photos/players/110x140/p" + playersData.getCode() + ".png";

            Picasso.get()
                    .load(imageUrl)
                    .error(R.mipmap.no_image)
                    .into(binding.playerImageView);
        }


        // Observe the loading state to hide/show progress and content
        viewModel.dataLoading.observe(this, isLoading -> {
            if (isLoading != null) {
                binding.progressCircular.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                binding.playerDataLayout.setVisibility(isLoading ? View.GONE : View.VISIBLE);
            }
        });
        // wait for data loading
        viewModel.getElementSummary().observe(this, apiResponse -> {

            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
//                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    ElementSummary myTeam = (ElementSummary) apiResponse.getData();
//                    setUpToolbar(myTeam.getGameWeekDataResponseModel()); // set up toolbar
                    setUpToolbar(playersData);
                    setUpTabLayout();

                    break;
                case ERROR:
//                    showFailure(apiResponse.getMessage());
                    break;
            }
        });

    }

    public void setUpTabLayout(){
        // Set up the adapter
        PlayerInformationPageAdapter adapter = new PlayerInformationPageAdapter(this);
        binding.viewPager.setAdapter(adapter);

        // Link the TabLayout and the ViewPager2
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText("Matches");
                                break;
                            case 1:
                                tab.setText("Fixture");
                                break;
                            case 2:
                                tab.setText("History");
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

    public void setUpToolbar(PlayersData playersData){
        // Set the title based on the received name (optional)
        if (playersData != null) {
            binding.toolbar.setTitle(playersData.getFirst_name() + " " + playersData.getSecond_name());
            String price = "€" + (float) playersData.getNow_cost() / 10 + "m";
            String teamName = Objects.requireNonNull(Constants.teamMap.get(playersData.getTeam())).getShort_name();
            String playerType = Objects.requireNonNull(Constants.playerTypeMap.get(playersData.getElement_type())).getSingular_name_short();
            String subtitle = price + " | " + playerType + " | " + teamName;
            binding.toolbar.setSubtitle(subtitle);
        }

        setSupportActionBar(binding.toolbar);

        // Enable the Up button (back key)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

}