package com.infotech.fplcolosseum.features.homepage.views;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentLeaguesBinding;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.TeamInformationResponseModel;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

public class LeaguesFragment extends Fragment {
    private FragmentLeaguesBinding binding;
    private HomePageSharedViewModel viewModel;
    private MenuProvider menuProvider;
    private Fragment activeFragment;
    private Button btnTab1, btnTab2;
    private LeagueListFragment leagueListFragment;
    private CupListFragment cupListFragment;
    private static final String KEY_ACTIVE_TAB = "active_tab";
    private boolean isDataLoaded = false;


    private static final String ARG_ENTRY_ID = "entry_id";
    private static final String ARG_IS_NEW_MANAGER = "is_new_manager";
    private long entry_id;
    private boolean isNewManager;


    public LeaguesFragment newInstance(long index, boolean isNewManager) {
        Bundle args = new Bundle();
        args.putLong(ARG_ENTRY_ID, index);
        args.putBoolean(ARG_IS_NEW_MANAGER, isNewManager);

        LeaguesFragment fragment = new LeaguesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            entry_id = args.getLong(ARG_ENTRY_ID);
            isNewManager = args.getBoolean(ARG_IS_NEW_MANAGER);
        }
        // Initialize fragments early
        leagueListFragment = new LeagueListFragment();
        cupListFragment = new CupListFragment();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLeaguesBinding.inflate(inflater, container, false);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
        binding.setHomePageViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        initializeViews();
        setupObservers();

        // Restore saved state
        if (savedInstanceState != null) {
            int activeTab = savedInstanceState.getInt(KEY_ACTIVE_TAB, 0);
            switchToTab(activeTab);
        } else {
            // Default to first tab
            switchToTab(0);
        }

        return binding.getRoot();
    }

    private void initializeViews() {
        btnTab1 = binding.btnTab1;
        btnTab2 = binding.btnTab2;

        // Set up tab click listeners
        btnTab1.setOnClickListener(v -> switchToTab(0));
        btnTab2.setOnClickListener(v -> switchToTab(1));

        // Setup SwipeRefreshLayout
        binding.leagueSwipeRefresh.setOnRefreshListener(() -> {
            handleRefreshClick();
            binding.leagueSwipeRefresh.setRefreshing(false);
        });
    }

    private void setupObservers() {
        viewModel.getTeamInformationApiResultLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse == null) return;

            switch (apiResponse.getStatus()) {
                case LOADING:
                    binding.progressCircular.setVisibility(View.VISIBLE);
                    break;

                case SUCCESS:
                    binding.progressCircular.setVisibility(View.GONE);
                    viewModel.dataLoading.setValue(false);
                    if (apiResponse.getData() != null) {
                        updateUI(apiResponse.getData());
                        isDataLoaded = true;
                    }
                    break;

                case ERROR:
                    binding.progressCircular.setVisibility(View.GONE);
                    viewModel.dataLoading.setValue(false);
                    // Handle error case
                    break;
            }
        });
    }

    private void switchToTab(int tabIndex) {
        Fragment targetFragment = tabIndex == 0 ? leagueListFragment : cupListFragment;
        Button targetButton = tabIndex == 0 ? btnTab1 : btnTab2;

        if (activeFragment != targetFragment || !isDataLoaded) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

            // Add animations
            transaction.setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
            );

            if (!targetFragment.isAdded()) {
                transaction.replace(R.id.leagues_fragment_container, targetFragment);
            } else {
                transaction.show(targetFragment);
                if (activeFragment != null) {
                    transaction.hide(activeFragment);
                }
            }

            transaction.commit();
            activeFragment = targetFragment;
            updateButtonStates(targetButton);
        }
    }

    private void updateUI(TeamInformationResponseModel teamInformationResponseModel) {
        if (teamInformationResponseModel != null) {
            setUpToolbar(Constants.currentGameWeek);

            // If no active fragment, default to league list
            if (activeFragment == null) {
                switchToTab(0);
            }
        }
    }

    private void updateButtonStates(Button selectedButton) {
        // Update button backgrounds
        btnTab1.setBackgroundColor(ContextCompat.getColor(requireContext(),
                selectedButton == btnTab1 ? android.R.color.darker_gray : android.R.color.transparent));
        btnTab2.setBackgroundColor(ContextCompat.getColor(requireContext(),
                selectedButton == btnTab2 ? android.R.color.darker_gray : android.R.color.transparent));

        // Update text colors
        btnTab1.setTextColor(ContextCompat.getColor(requireContext(),
                selectedButton == btnTab1 ? android.R.color.white : android.R.color.black));
        btnTab2.setTextColor(ContextCompat.getColor(requireContext(),
                selectedButton == btnTab2 ? android.R.color.white : android.R.color.black));
    }

    private void setUpToolbar(long gameWeekNumber) {
        Toolbar toolbar = requireActivity().findViewById(R.id.pointToolbar);

        if (toolbar != null && viewModel.getTeamInformationApiResultLiveData().getValue() != null) {

            TeamInformationResponseModel data = viewModel.getTeamInformationApiResultLiveData().getValue().getData();
            if (data != null) {

                String concatenatedName = data.getName() + " (GW " + gameWeekNumber + ")";
                viewModel.setToolbarTitle(concatenatedName);

                String fullName = data.getPlayer_first_name() + " " + data.getPlayer_last_name();
                viewModel.setToolbarSubTitle(fullName);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        addMenuProvider();
        if (isDataLoaded) {
            setUpToolbar(Constants.currentGameWeek);
        }
    }

    private void addMenuProvider() {
        menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                if(isNewManager){
                    menuInflater.inflate(R.menu.new_manager_menu_leagues, menu);
                } else {
                    menuInflater.inflate(R.menu.menu_leagues, menu);
                }

                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    item.setIconTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(requireContext(), R.color.white)));
                }
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.reload) {
                    handleRefreshClick();
                    return true;
                }
                return false;
            }
        };
        requireActivity().addMenuProvider(menuProvider, getViewLifecycleOwner());
    }

    private void handleRefreshClick() {
        viewModel.getTeamInformation(entry_id);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_ACTIVE_TAB, activeFragment == leagueListFragment ? 0 : 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(menuProvider != null)
            requireActivity().removeMenuProvider(menuProvider);
        binding = null;
    }
}