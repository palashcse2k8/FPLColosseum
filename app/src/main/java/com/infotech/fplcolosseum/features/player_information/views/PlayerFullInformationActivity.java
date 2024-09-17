package com.infotech.fplcolosseum.features.player_information.views;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.ActivityPlayerFullInformationBinding;
import com.infotech.fplcolosseum.databinding.AppbarLayoutBinding;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.player_information.adapter.PlayerInformationPageAdapter;

public class PlayerFullInformationActivity extends AppCompatActivity {

    ActivityPlayerFullInformationBinding binding;
    PlayersData playersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityPlayerFullInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inflate the toolbar
//        toolbarBinding = AppbarLayoutBinding.bind(binding.playerInformationToolbar.getRoot());
        setSupportActionBar(binding.toolbar);

        // Enable the Up button (back key)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // Retrieve player name from the Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.playersData = (PlayersData) extras.getSerializable("playerData");
            binding.setPlayer(playersData); // Assuming "playerData" is the key
        }

        // Set the title based on the received name (optional)
        if (playersData != null) {
            binding.toolbar.setTitle(playersData.getFirst_name() + " " + playersData.getSecond_name());
            String subtitle = "€" + playersData.getNow_cost() / 10 + "m";
            binding.toolbar.setSubtitle(subtitle);
        }

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
}