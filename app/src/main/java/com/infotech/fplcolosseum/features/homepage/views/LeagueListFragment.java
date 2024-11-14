package com.infotech.fplcolosseum.features.homepage.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentLeagueListBinding;
import com.infotech.fplcolosseum.databinding.FragmentLeaguesBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.ClassicLeagueListAdapter;
import com.infotech.fplcolosseum.features.homepage.adapter.MostValuableTeamsAdapter;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.LeagueDataModel;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.TeamInformationResponseModel;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LeagueListFragment extends Fragment {

    private FragmentLeagueListBinding binding;
    private HomePageSharedViewModel sharedViewModel;
    private List<LeagueDataModel> classicLeagues = new ArrayList<>();
    private List<LeagueDataModel> headToHeadLeagues = new ArrayList<>();
    private List<LeagueDataModel> generalLeagues = new ArrayList<>();

    private ClassicLeagueListAdapter classicLeagueListAdapter;
    private ClassicLeagueListAdapter h2hListAdapter;
    private ClassicLeagueListAdapter generalLeagueListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLeagueListBinding.inflate(inflater, container, false);

        // Initialize adapters with empty lists first
        setUpAdapter();

        // Observe ViewModel data
        observeViewModelData();

        return binding.getRoot();
    }

    private void observeViewModelData() {
        sharedViewModel.getTeamInformationApiResultLiveData().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse == null) return;

            switch (apiResponse.getStatus()) {
                case LOADING:
//                    showLoading();
                    break;
                case SUCCESS:
                    sharedViewModel.dataLoading.setValue(false);
                    updateUI(apiResponse.getData());
                    break;
                case ERROR:
//                    showFailure(apiResponse.getMessage());
                    break;
            }
        });
    }

    private void updateUI(TeamInformationResponseModel teamInformationResponseModel) {
        if (teamInformationResponseModel == null ||
                teamInformationResponseModel.getLeagues() == null) return;

        // Update lists with new data
        classicLeagues = teamInformationResponseModel.getLeagues().getClassic().stream()
                .filter(model -> model.getLeague_type().equalsIgnoreCase("x"))
                .collect(Collectors.toList());

        headToHeadLeagues = teamInformationResponseModel.getLeagues().getH2h();

        generalLeagues = teamInformationResponseModel.getLeagues().getClassic().stream()
                .filter(model -> model.getLeague_type().equalsIgnoreCase("s"))
                .collect(Collectors.toList());

        // Update adapters
        if (classicLeagueListAdapter != null) {
            classicLeagueListAdapter.updateLeagueList(classicLeagues);
        }
        if (h2hListAdapter != null) {
            h2hListAdapter.updateLeagueList(headToHeadLeagues);
        }
        if (generalLeagueListAdapter != null) {
            generalLeagueListAdapter.updateLeagueList(generalLeagues);
        }

        updateViewVisibility();
    }

    private void updateViewVisibility() {
        if (binding == null) return;

        binding.head2headCV.setVisibility(
                headToHeadLeagues.isEmpty() ? View.GONE : View.VISIBLE
        );

        binding.classicLeagueCV.setVisibility(
                classicLeagues.isEmpty() ? View.GONE : View.VISIBLE
        );

        binding.generalLeagueCV.setVisibility(
                generalLeagues.isEmpty() ? View.GONE : View.VISIBLE
        );
    }

    private void setUpAdapter() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
        );

        // Classic Leagues
        classicLeagueListAdapter = new ClassicLeagueListAdapter(classicLeagues);
        binding.classicLeaguesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
        binding.classicLeaguesRecyclerView.setAdapter(classicLeagueListAdapter);
        binding.classicLeaguesRecyclerView.addItemDecoration(dividerItemDecoration);

        // Head to Head Leagues
        h2hListAdapter = new ClassicLeagueListAdapter(headToHeadLeagues);
        binding.head2HeadLeagueRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
        binding.head2HeadLeagueRecyclerView.setAdapter(h2hListAdapter);
        binding.head2HeadLeagueRecyclerView.addItemDecoration(dividerItemDecoration);

        // General Leagues
        generalLeagueListAdapter = new ClassicLeagueListAdapter(generalLeagues);
        binding.generalLeaguesRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext())
        );
        binding.generalLeaguesRecyclerView.setAdapter(generalLeagueListAdapter);
        binding.generalLeaguesRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
