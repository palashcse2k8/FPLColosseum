package com.infotech.fplcolosseum.features.gameweek_history.views;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.ActivityGameweekHistoryBinding;
import com.infotech.fplcolosseum.features.gameweek_history.adapters.PreviousSeasonHistoryAdapter;
import com.infotech.fplcolosseum.features.gameweek_history.adapters.ThisSeasonGameWeekHistoryAdapter;
import com.infotech.fplcolosseum.features.gameweek_history.adapters.UsedChipsHistoryAdapter;
import com.infotech.fplcolosseum.features.gameweek_history.models.CurrentSeasonHistoryModel;
import com.infotech.fplcolosseum.features.gameweek_history.models.GameWeekHistoryResponseModel;
import com.infotech.fplcolosseum.features.gameweek_history.models.PreviousSeasonHistoryModel;
import com.infotech.fplcolosseum.features.gameweek_history.models.UsedChipsModel;
import com.infotech.fplcolosseum.features.gameweek_history.viewmodels.GameWeekHistoryViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameWeekHistoryActivity extends AppCompatActivity {


    ActivityGameweekHistoryBinding binding;
    private long managerId;
    private GameWeekHistoryViewModel viewModel;
    ThisSeasonGameWeekHistoryAdapter thisSeasonGameWeekHistoryAdapter;
    UsedChipsHistoryAdapter usedChipsHistoryAdapter;

    PreviousSeasonHistoryAdapter previousSeasonHistoryAdapter;

    public static final String ARG_MANAGER_ID = "managerID";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameweekHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(ARG_MANAGER_ID)) {
            managerId = intent.getLongExtra(ARG_MANAGER_ID, 0L);
            viewModel = new ViewModelProvider(this).get(GameWeekHistoryViewModel.class);
            viewModel.getGameWeekHistory(managerId);
            binding.setViewModel(viewModel);
            binding.setLifecycleOwner(this);
            setUpObserver();
        }
        setUpViews();
    }

    public void setUpViews(){
        // Find RecyclerView
        binding.thisSeasonRecyclerHorizontal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Create and set adapter
        thisSeasonGameWeekHistoryAdapter = new ThisSeasonGameWeekHistoryAdapter(new ArrayList<>());
        binding.thisSeasonRecyclerHorizontal.setAdapter(thisSeasonGameWeekHistoryAdapter);

        // Optional: Add item decorations
        binding.thisSeasonRecyclerHorizontal.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                outRect.bottom = 2; // Add bottom margin between items
            }
        });

        // Find RecyclerView
        binding.chipUsedRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Create and set adapter
        usedChipsHistoryAdapter = new UsedChipsHistoryAdapter(new ArrayList<>());
        binding.chipUsedRecyclerView.setAdapter(usedChipsHistoryAdapter);

        // Optional: Add item decorations
        binding.chipUsedRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                outRect.bottom = 2; // Add bottom margin between items
            }
        });

        // Find RecyclerView
        binding.previousSeasonRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Create and set adapter
        previousSeasonHistoryAdapter = new PreviousSeasonHistoryAdapter(new ArrayList<>());
        binding.previousSeasonRecyclerView.setAdapter(previousSeasonHistoryAdapter);

        // Optional: Add item decorations
        binding.previousSeasonRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                outRect.bottom = 2; // Add bottom margin between items
            }
        });


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

//        viewModel.dataLoading.observe(this, isLoading -> {
//            if (isLoading != null) {
//                binding.progressCircular.setVisibility(isLoading ? View.VISIBLE : View.GONE);
//                binding.dataLayout.setVisibility(isLoading ? View.GONE : View.VISIBLE);
//            }
//        });

        viewModel.getGameWeekHistoryLiveData().observe(this, apiResponse -> {
            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
//                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    GameWeekHistoryResponseModel responseData = apiResponse.getData();
                    setUpToolbar();

                    List<CurrentSeasonHistoryModel> currentSeasonHistoryModelList = new ArrayList<>(responseData.getCurrent());
                    Collections.reverse(currentSeasonHistoryModelList);
                    thisSeasonGameWeekHistoryAdapter.updateAdapterData(currentSeasonHistoryModelList);
//                    thisSeasonGameWeekHistoryAdapter.updateAdapterData(new ArrayList<>());

                    List<UsedChipsModel> usedChipsModels = new ArrayList<>(responseData.getChips());
                    Collections.reverse(usedChipsModels);
                    usedChipsHistoryAdapter.updateAdapterData(usedChipsModels);
//                    usedChipsHistoryAdapter.updateAdapterData(new ArrayList<>());

                    List<PreviousSeasonHistoryModel> previousSeasonHistoryModelList = new ArrayList<>(responseData.getPast());
                    Collections.reverse(previousSeasonHistoryModelList);
                    previousSeasonHistoryAdapter.updateAdapterData(previousSeasonHistoryModelList);
//                    thisSeasonGameWeekHistoryAdapter.updateAdapterData(new ArrayList<>());

                    break;
                case ERROR:
//                    showFailure(apiResponse.getMessage());
                    break;
            }
        });
    }

    public void setUpToolbar() {
        // Set the title based on the received name (optional)
        setSupportActionBar(binding.gameWeekHistoryToolbar);

        // Enable the Up button (back key)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //change the back button color to white
        Drawable navigationIcon = binding.gameWeekHistoryToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void handleRefresh(){
        viewModel.getGameWeekHistory(managerId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
