package com.infotech.fplcolosseum.features.gameweek.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.GameweekDashboardFragmentBinding;
import com.infotech.fplcolosseum.features.gameweek.adapter.TeamAdapter;
import com.infotech.fplcolosseum.features.gameweek.adapter.TeamDataComparator;
import com.infotech.fplcolosseum.features.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.features.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.features.gameweek.viewmodels.GameWeekViewModel;
import com.infotech.fplcolosseum.utilities.AppLogger;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.ProgressDialogHelper;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@EFragment(resName = "gameweek_dashboard_fragment")
public class GameWeekDashboardFragment extends Fragment {

    GameweekDashboardFragmentBinding binding;
    private TeamAdapter adapter;

    private CustomGameWeekDataModel weekDataModel;
    private List<ManagerModel> teams;

    private GameWeekViewModel viewModel;


    private int currentSelectedGameWeek = -1; // Track current selection

    private ProgressDialogHelper progressHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressHelper = new ProgressDialogHelper(getContext());
        // Initialize ViewModel and other components here
        viewModel = new ViewModelProvider(requireActivity()).get(GameWeekViewModel.class);
        viewModel.deleteAllGameWeekData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GameweekDashboardFragmentBinding.inflate(inflater, container, false);
        binding.setGameWeekViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressHelper != null) {
            progressHelper.dismissProgressDialog();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setUpLiveDataObserver();

        binding.tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    viewModel.deleteAllGameWeekData();
                    UIUtils.toast(requireContext(), "Local Data Deleted", ToastLevel.INFO);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


        binding.gameWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                AppLogger.d("item selected " + i);
                if (i > 0){
                    currentSelectedGameWeek = i;
                    getGameWeekData(Constants.leagues[0], String.valueOf(i));
                }
                else {
                    AppLogger.d("Game Week Not Selected");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getGameWeekData(String leagueID, String gameWeek) {

        try {
            AppLogger.d("Getting Game Week Data for leagueID-> " + leagueID + ", gameWeek-> " + gameWeek);
            showIndeterminateProgress(gameWeek);
            viewModel.gameWeekDataFromAPI(leagueID, gameWeek);
        } catch (IOException e) {
            progressHelper.dismissProgressDialog();
            throw new RuntimeException(e);
        }
    }

    public void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView1;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        teams = new ArrayList<>();
        adapter = new TeamAdapter(requireContext(), teams);
        recyclerView.setAdapter(adapter);
    }

    public void setUpLiveDataObserver() {

        viewModel.leagueGameWeekDataModel().observe(getViewLifecycleOwner(), customGameWeekDataModel -> {

//            if (customGameWeekDataModel == null) { return;}

            AppLogger.d("GameWeek Data changed");
            if (customGameWeekDataModel != null) {

                // Verify data matches current selection
                if (customGameWeekDataModel.getGameWeek() == currentSelectedGameWeek) {
                    AppLogger.d("Updating UI with latest data");
                    updateUI(customGameWeekDataModel);
                } else {
                    AppLogger.d("Ignoring stale data for GW " + customGameWeekDataModel.getGameWeek() + " ,current is GW " + currentSelectedGameWeek);
                }

            } else {
                Toast.makeText(getContext(), "Failed to get data!", Toast.LENGTH_SHORT).show();
            }
            if (progressHelper.isShowing())
                progressHelper.dismissProgressDialog();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUI(CustomGameWeekDataModel weekDataModel) {

        AppLogger.d("Updating UI");
        if (weekDataModel != null && !weekDataModel.getTeams().isEmpty()) {
            // Update your RecyclerView and other UI components here using the data
            String gameWeek = " (GW " + (int) weekDataModel.getGameWeek() + ")";
            binding.textviewGameWeek.setText(gameWeek);
            String leagueName = " " + weekDataModel.getLeagueName();
            binding.leagueName.setText(leagueName);
            teams.clear(); // Clear the existing data
            weekDataModel.getTeams().sort(new TeamDataComparator());
            teams.addAll(weekDataModel.getTeams());
            adapter.notifyDataSetChanged();
        } else {
            Logger.d("GameWeek Model is Empty");
        }
    }

    private void showIndeterminateProgress(String gameWeek) {
        progressHelper.showProgressDialog( "Please Wait...", "Fetching GW " + gameWeek + " Data");
    }
}
