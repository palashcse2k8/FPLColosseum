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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentFixtureBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.DateWiseFixtureListAdapter;
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

public class FixturesFragment extends Fragment {
    FragmentFixtureBinding binding;
    private HomePageSharedViewModel sharedViewModel;

    private MenuProvider menuProvider;

    private DateWiseFixtureListAdapter dateWiseFixtureListAdapter;
    private List<DateWiseFixtures> dateWiseFixturesList;

    long fragmentCurrentGameWeek;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentCurrentGameWeek = Constants.currentGameWeek;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFixtureBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
        sharedViewModel.getStatusMergedData(Constants.LoggedInUser.getPlayer().getEntry(), Constants.currentGameWeek);
        binding.setGameWeekEventIndex(Constants.currentGameWeek);
        binding.setHomePageViewModel(sharedViewModel);
        binding.setLifecycleOwner(this);
        setupToolbar();
        setUpAdapter();
        setUpViews();
        return binding.getRoot();
    }

    private void setUpViews() {

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCurrentGameWeek<38)
                    fragmentCurrentGameWeek++;
                if(Constants.fixtures.get(fragmentCurrentGameWeek) != null){
                    updateUI(Constants.fixtures.get(fragmentCurrentGameWeek));
                } else {
                    sharedViewModel.getFixtureData(fragmentCurrentGameWeek);
                }
            }
        });

        binding.buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentCurrentGameWeek>1)
                    fragmentCurrentGameWeek--;
                if(Constants.fixtures.get(fragmentCurrentGameWeek) != null){
                    updateUI(Constants.fixtures.get(fragmentCurrentGameWeek));
                } else {
                    sharedViewModel.getFixtureData(fragmentCurrentGameWeek);
                }
            }
        });
    }

    private void setUpAdapter() {

        binding.gameWeekFixturesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if(Constants.fixtures.get(fragmentCurrentGameWeek) == null){
            sharedViewModel.getFixtureData(fragmentCurrentGameWeek);
        }

        // Initialize an empty list for the adapter
        dateWiseFixtureListAdapter = new DateWiseFixtureListAdapter(new ArrayList<>(), requireActivity(), binding.gameWeekFixturesRecyclerView);
        binding.gameWeekFixturesRecyclerView.setAdapter(dateWiseFixtureListAdapter);
    }

    // Group matches by the kickoff date and return a List of DateWiseFixtures
    public static List<DateWiseFixtures> prepareDateWiseFixtures(List<MatchDetails> matches) {
        Map<String, List<MatchDetails>> groupedMatches = new HashMap<>();
        // Define input date format (with UTC timezone)
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));  // Ensure UTC is used for parsing

        // Define output date format (also with UTC timezone)
        final SimpleDateFormat dateOnlyFormat = new SimpleDateFormat("EEEE dd MMMM", Locale.getDefault());
        dateOnlyFormat.setTimeZone(TimeZone.getTimeZone("UTC"));  // Use UTC for formatting

        for (MatchDetails match : matches) {
            String kickoffTime = match.getKickoff_time();
            try {
                // Parse the kickoff_time to a Date object using UTC timezone
                Date kickoffDate = dateFormat.parse(kickoffTime);
                // Extract only the date part in a string format (still using UTC)
                String dateString = dateOnlyFormat.format(kickoffDate);

                // Group matches by the extracted date
                groupedMatches.computeIfAbsent(dateString, k -> new ArrayList<>()).add(match);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Prepare DateWiseFixtures list
        List<DateWiseFixtures> dateWiseFixturesList = new ArrayList<>();
        for (String dateString : groupedMatches.keySet()) {
            try {
                // Parse dateString back to Date (still using UTC)
//                Date date = dateOnlyFormat.parse(dateString);
                List<MatchDetails> fixturesForDate = groupedMatches.get(dateString);
                Date date = dateFormat.parse(groupedMatches.get(dateString).get(0).getKickoff_time());
                // Create a new DateWiseFixtures object
                DateWiseFixtures dateWiseFixtures = new DateWiseFixtures(date, fixturesForDate);
                dateWiseFixturesList.add(dateWiseFixtures);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // Sort the list by date
        dateWiseFixturesList.sort(Comparator.comparing(DateWiseFixtures::date));

        return dateWiseFixturesList;
    }


    public void setupToolbar() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.fragmentToolbar);

        binding.fragmentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.LOG_TAG, "Icon Clicked!");
                ((DashboardActivity) requireActivity()).toggleDrawer();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        addMenuProvider();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel.getFixtureLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse == null) return;
            switch (apiResponse.getStatus()) {
                case LOADING:
                    showLoading();
                    break;
                case SUCCESS:
                    sharedViewModel.dataLoading.setValue(false);
                    List<MatchDetails> matchDetails = (List<MatchDetails>) apiResponse.getData();
                    updateUI(matchDetails);
                    break;
                case ERROR:
                    showFailure(apiResponse.getMessage());
                    break;
            }
        });

        //set up swipe refresh layout
        binding.fixtureSwipeRefresh.setOnRefreshListener(() -> {

                    // This method performs the actual data-refresh operation and calls
                    handleRefreshClick();

                    // Stop the refreshing animation after the refresh operation is completed
                    binding.fixtureSwipeRefresh.setRefreshing(false);
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

    private void updateUI(List<MatchDetails> matchDetailsList) {
        String gameWeekText = "Gameweek " + fragmentCurrentGameWeek;
        binding.gameWeekTV.setText(gameWeekText);
        String deadline_time = Constants.gameWeekMap.get(fragmentCurrentGameWeek).getDeadline_time();
        String gameWeekDeadlineText = CustomUtil.convertDateToDeadLine(deadline_time);
        binding.gameWeekDeadlineTV.setText(gameWeekDeadlineText);

        if (matchDetailsList != null) {
            dateWiseFixturesList = prepareDateWiseFixtures(matchDetailsList);
            // Update the adapter with the new data
            dateWiseFixtureListAdapter.updateDateList(dateWiseFixturesList);
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
                    item.setIconTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.accent)));
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
        sharedViewModel.getFixtureData(fragmentCurrentGameWeek);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Remove the menu provider when the fragment's view is destroyed
        requireActivity().removeMenuProvider(menuProvider);
    }
}
