package com.infotech.fplcolosseum.features.homepage.views;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentFixtureBinding;
import com.infotech.fplcolosseum.databinding.FragmentLeaguesBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.DateWiseFixtureListAdapter;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.TeamInformationResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.DateWiseFixtures;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class LeaguesFragment extends Fragment {
    FragmentLeaguesBinding binding;
    private HomePageSharedViewModel sharedViewModel;

    private MenuProvider menuProvider;

    private Fragment activeFragment;
    private Button btnTab1, btnTab2;
    private LeagueListFragment leagueListFragment;
    private CupListFragment cupListFragment;

    private static final String KEY_ACTIVE_TAB = "active_tab";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLeaguesBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
        sharedViewModel.getStatusMergedData(Constants.LoggedInUser.getPlayer().getEntry(), Constants.currentGameWeek);
        binding.setHomePageViewModel(sharedViewModel);
        binding.setLifecycleOwner(this);
        setupToolbar();
        setUpViews();

//        if (savedInstanceState != null) {
//            int activeTab = savedInstanceState.getInt(KEY_ACTIVE_TAB, 0);
//            if (activeTab == 0) {
//                setFragment(leagueListFragment);
//                updateButtonStates(btnTab1);
//            } else {
//                setFragment(cupListFragment);
//                updateButtonStates(btnTab2);
//            }
//        }
        return binding.getRoot();
    }

    private void setUpViews() {

    }

    public void setupToolbar() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_ACTIVE_TAB, activeFragment == leagueListFragment ? 0 : 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        addMenuProvider();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addMenuProvider();
        // Initialize fragments
        leagueListFragment = new LeagueListFragment();
        cupListFragment = new CupListFragment();

        btnTab1 = binding.btnTab1;
        btnTab2 = binding.btnTab2;

        setFragment(leagueListFragment);
        updateButtonStates(btnTab1);

        // Set click listeners
        binding.btnTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(leagueListFragment);
                updateButtonStates(btnTab1);
            }
        });

        binding.btnTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(cupListFragment);
                updateButtonStates(btnTab2);
            }
        });

        sharedViewModel.getTeamInformationApiResultLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    sharedViewModel.dataLoading.setValue(false);
                    TeamInformationResponseModel matchDetails = apiResponse.getData();
                    updateUI(matchDetails);
                    break;
                case ERROR:
                    showFailure(apiResponse.getMessage());
                    break;
            }
        });

        //set up swipe refresh layout
        binding.leagueSwipeRefresh.setOnRefreshListener(() -> {

                    // This method performs the actual data-refresh operation and calls
                    handleRefreshClick();

                    // Stop the refreshing animation after the refresh operation is completed
                    binding.leagueSwipeRefresh.setRefreshing(false);
                }
        );
    }

    private void showLoading() {
        sharedViewModel.dataLoading.setValue(false);
        binding.progressCircular.setVisibility(View.GONE);
    }

    private void showFailure(String ignoredError) {
        sharedViewModel.dataLoading.setValue(false);
        binding.progressCircular.setVisibility(View.GONE);
    }

    private void updateUI( TeamInformationResponseModel teamInformationResponseModel) {

    }

    private void addMenuProvider() {
        // Add a menu provider to manage the menu lifecycle

        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear(); // Clear previous menu
                menuInflater.inflate(R.menu.menu_leagues, menu); // Inflate home-specific menu
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    item.setIconTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Handle menu item selection
                if (menuItem.getItemId() == R.id.reload) {
                    handleRefreshClick();
                    return true;
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
    }

    public void handleRefreshClick(){
        sharedViewModel.getTeamInformation(Constants.LoggedInUser.getPlayer().getEntry());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove the menu provider when the fragment's view is destroyed
        requireActivity().removeMenuProvider(menuProvider);
    }

    private void setFragment(Fragment fragment) {
        if (activeFragment == fragment) return;

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

        if (activeFragment != null) {
            // Add animation if desired
            transaction.setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
            );
        }

        transaction.replace(R.id.leagues_fragment_container, fragment);
        transaction.commit();

        activeFragment = fragment;
    }

    private void updateButtonStates(Button selectedButton) {
        // Reset all buttons
        binding.btnTab1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        binding.btnTab2.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        // Highlight selected button
        selectedButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // Optional: Update text colors
        binding.btnTab1.setTextColor(getResources().getColor(selectedButton == btnTab1 ?
                android.R.color.white : android.R.color.black));
        binding.btnTab2.setTextColor(getResources().getColor(selectedButton == btnTab2 ?
                android.R.color.white : android.R.color.black));
    }
}
