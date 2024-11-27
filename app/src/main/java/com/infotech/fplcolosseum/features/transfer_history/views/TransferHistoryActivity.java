package com.infotech.fplcolosseum.features.transfer_history.views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.infotech.fplcolosseum.MainActivity;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.ActivityTransferHistoryBinding;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.views.FixturesFragment;
import com.infotech.fplcolosseum.features.homepage.views.HomePageFragment;
import com.infotech.fplcolosseum.features.homepage.views.StatusFragment;
import com.infotech.fplcolosseum.features.player_information.models.ElementSummary;
import com.infotech.fplcolosseum.features.transfer_history.adapter.TransferHistoryAdapter;
import com.infotech.fplcolosseum.features.transfer_history.models.TransferHistoryModel;
import com.infotech.fplcolosseum.features.transfer_history.viewmodels.TransferHistoryViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class TransferHistoryActivity extends AppCompatActivity {


    ActivityTransferHistoryBinding binding;
    private long managerId;
    private TransferHistoryViewModel viewModel;
    TransferHistoryAdapter adapter;

    public static final String ARG_MANAGER_ID = "managerID";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTransferHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(ARG_MANAGER_ID)) {
            managerId = intent.getLongExtra(ARG_MANAGER_ID, 0L);
            viewModel = new ViewModelProvider(this).get(TransferHistoryViewModel.class);
            viewModel.getTransferHistory(managerId);
            binding.setViewModel(viewModel);
            binding.setLifecycleOwner(this);
            setUpObserver();
        }

        // Find RecyclerView
        binding.recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));

        // Create and set adapter
        adapter = new TransferHistoryAdapter(this, new ArrayList<>());
        binding.recyclerViewEvents.setAdapter(adapter);

        // Optional: Add item decorations
        binding.recyclerViewEvents.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                outRect.bottom = 16; // Add bottom margin between items
            }
        });

        binding.statusSwipeRefresh.setOnRefreshListener(() -> {
            // This method performs the actual data-refresh operation and calls
            handleRefresh();

            // Stop the refreshing animation after the refresh operation is completed
            binding.statusSwipeRefresh.setRefreshing(false);
        });
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

        viewModel.dataLoading.observe(this, isLoading -> {
            if (isLoading != null) {
                binding.progressCircular.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                binding.dataLayout.setVisibility(isLoading ? View.GONE : View.VISIBLE);
            }
        });

        viewModel.getTransferHistoryLiveData().observe(this, apiResponse -> {
            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
//                    showLoading();
                    break;
                case SUCCESS:
                    viewModel.dataLoading.setValue(false);

                    List<TransferHistoryModel> responseData = apiResponse.getData();
                    setUpToolbar();
                    adapter.updateAdapterData(responseData);
                    break;
                case ERROR:
//                    showFailure(apiResponse.getMessage());
                    break;
            }
        });
    }

    public void setUpToolbar() {
        // Set the title based on the received name (optional)
        setSupportActionBar(binding.transferHistoryToolbar);

        // Enable the Up button (back key)
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        //change the back button color to white
        Drawable navigationIcon = binding.transferHistoryToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void handleRefresh(){
        viewModel.getTransferHistory(managerId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
