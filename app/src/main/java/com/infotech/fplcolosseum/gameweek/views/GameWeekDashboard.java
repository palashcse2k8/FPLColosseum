package com.infotech.fplcolosseum.gameweek.views;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.GameweekDashboardFragmentBinding;
import com.infotech.fplcolosseum.gameweek.adapter.TeamAdapter;
import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.viewmodels.GameWeekViewModel;
import com.infotech.fplcolosseum.utilities.StaticConstants;

import java.io.IOException;
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
//        viewModel = new ViewModelProvider(requireActivity()).get(GameWeekViewModel.class);
        binding.setGameWeekViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        managerListObserver();
        getMangerList();
        binding.gameWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("selected=> ", "item selected" + i);
//                makeApiCall("671887", "116074", String.valueOf(i + 1), "1");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getMangerList(){
        try {
            progressDialog.show();
            viewModel.gameMangerListFromAPI("671887","1", "1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setupRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView1;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize your data (replace with your actual data)
        teams = StaticConstants.managerList;

        // Sort the data based on your predefined rules
//        Collections.sort(teams, (team1, team2) -> {
//            // Your sorting logic here, e.g., based on points
//            return Integer.compare(team2.getTotalPoints(), team1.getTotalPoints());
//        });

        adapter = new TeamAdapter(teams);
        recyclerView.setAdapter(adapter);
    }

    private void makeApiCall(String leagueID, String entryID, String currentGameweek, String currentPage) {
//        try {
//            progressDialog.show();
//            viewModel.gameWeekDataFromAPI(leagueID, entryID, currentGameweek, currentPage);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        // Set up LiveData observer
//        liveDataObserver();
    }

    private void liveDataObserver() {
        viewModel.leagueGameWeekDataModel().observe(getViewLifecycleOwner(), data -> {
//            progressDialog.dismiss();
//            Log.d("oberver=>", "triggers" + data.toString());
            if (data != null) {
                weekDataModel = data;
                updateUI();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            } else {
                // Handle API call errors here
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }

    public void managerListObserver() {
        viewModel.getManagerList().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                StaticConstants.managerList = data;
                updateUI();
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
    public void updateUI() {
        if (StaticConstants.managerList != null) {
            // Update your RecyclerView and other UI components here using the data
            TextView leagueName = binding.leagueName;
//            leagueName.setText(weekDataModel.getLeagueName());
            teams.clear(); // Clear the existing data
            teams.addAll(StaticConstants.managerList);
            adapter.notifyDataSetChanged();
        } else {
            // Handle the case where gameWeekDataModel is null or the data is not as expected
        }
    }
}
