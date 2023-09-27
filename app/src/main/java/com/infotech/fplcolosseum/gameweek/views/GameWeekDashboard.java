package com.infotech.fplcolosseum.gameweek.views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.GameweekDashboardFragmentBinding;
import com.infotech.fplcolosseum.gameweek.adapter.TeamAdapter;
import com.infotech.fplcolosseum.gameweek.adapter.TeamDataComparator;
import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.viewmodels.GameWeekViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class GameWeekDashboard extends Fragment {

    GameweekDashboardFragmentBinding binding;
    private TeamAdapter adapter;

    private CustomGameWeekDataModel weekDataModel;
    private List<ManagerModel> teams;
    private ProgressDialog progressDialog;

    private GameWeekViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait.");
        progressDialog.setCancelable(false);

        // Initialize ViewModel and other components here
        viewModel = new ViewModelProvider(requireActivity()).get(GameWeekViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GameweekDashboardFragmentBinding.inflate(inflater, container, false);
        binding.setGameWeekViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setUpLiveDataObserver();

        binding.gameWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("selected=> ", "item selected" + i);
                getGameWeekData(Constants.leagues[1], String.valueOf(i+1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getGameWeekData(String leagueID, String gameWeek){
        try {
            Log.d(Constants.LOG_TAG, "Getting Game Week Data for leagueID->" + leagueID + ", gameWeek->" + gameWeek );
            progressDialog.show();
            viewModel.gameWeekDataFromAPI(leagueID, gameWeek);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView1;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize your data (replace with your actual data)
        teams = Constants.managerList;

        // Sort the data based on your predefined rules
//        Collections.sort(teams, (team1, team2) -> {
//            // Your sorting logic here, e.g., based on points
//            return Integer.compare(team2.getTotalPoints(), team1.getTotalPoints());
//        });

        adapter = new TeamAdapter(teams);
        recyclerView.setAdapter(adapter);
    }

    public void setUpLiveDataObserver() {

        viewModel.leagueGameWeekDataModel().observe(getViewLifecycleOwner(), customGameWeekDataModel -> {
            Log.d(Constants.LOG_TAG, "leagueGameWeekDataModel changed");
            if (customGameWeekDataModel != null) {
                updateUI(customGameWeekDataModel );
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            } else {
                // Handle API call errors here
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUI( CustomGameWeekDataModel weekDataModel) {
        Log.d(Constants.LOG_TAG, "Updating UI");
        if (weekDataModel != null && !weekDataModel.getTeams().isEmpty()) {
            // Update your RecyclerView and other UI components here using the data
            binding.leagueName.setText(weekDataModel.getLeagueName());
            teams.clear(); // Clear the existing data
            weekDataModel.getTeams().sort(new TeamDataComparator());
            teams.addAll(weekDataModel.getTeams());
            adapter.notifyDataSetChanged();
        } else {
            Log.d(Constants.LOG_TAG, "GameWeek Model is Empty");
            // Handle the case where gameWeekDataModel is null or the data is not as expected
        }
    }

}
