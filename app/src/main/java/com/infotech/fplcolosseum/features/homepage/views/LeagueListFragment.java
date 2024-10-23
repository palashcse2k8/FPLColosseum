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
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class LeagueListFragment extends Fragment {

    FragmentLeagueListBinding binding;
    private HomePageSharedViewModel sharedViewModel;
    List<LeagueDataModel> classicLeagues;
    List<LeagueDataModel> headToHeadLeagues;
    List<LeagueDataModel> generalLeagues;

    public LeagueListFragment() {
        // Required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
        classicLeagues = sharedViewModel.getTeamInformationApiResultLiveData().getValue().getData().getLeagues().getClassic().stream().filter(model -> model.getLeague_type().equalsIgnoreCase("x")).collect(Collectors.toList());
        headToHeadLeagues = sharedViewModel.getTeamInformationApiResultLiveData().getValue().getData().getLeagues().getH2h();
        generalLeagues = sharedViewModel.getTeamInformationApiResultLiveData().getValue().getData().getLeagues().getClassic().stream().filter(model -> model.getLeague_type().equalsIgnoreCase("s")).collect(Collectors.toList());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLeagueListBinding.inflate(inflater, container, false);

        setUpAdapter();

        if (headToHeadLeagues.isEmpty()) {
            binding.head2headCV.setVisibility(View.GONE);
        }

        if (classicLeagues.isEmpty()) {
            binding.classicLeagueCV.setVisibility(View.GONE);
        }

        if (generalLeagues.isEmpty()) {
            binding.generalLeagueCV.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    private void setUpAdapter() {

        ClassicLeagueListAdapter classicLeagueListAdapter = new ClassicLeagueListAdapter(classicLeagues);
        binding.classicLeaguesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.classicLeaguesRecyclerView.setAdapter(classicLeagueListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        binding.classicLeaguesRecyclerView.addItemDecoration(dividerItemDecoration);

        ClassicLeagueListAdapter h2hListAdapter = new ClassicLeagueListAdapter(headToHeadLeagues);
        binding.head2HeadLeagueRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.head2HeadLeagueRecyclerView.setAdapter(h2hListAdapter);
        binding.head2HeadLeagueRecyclerView.addItemDecoration(dividerItemDecoration);

        ClassicLeagueListAdapter generalLeagueListAdapter = new ClassicLeagueListAdapter(generalLeagues);
        binding.generalLeaguesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.generalLeaguesRecyclerView.setAdapter(generalLeagueListAdapter);
        binding.generalLeaguesRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
