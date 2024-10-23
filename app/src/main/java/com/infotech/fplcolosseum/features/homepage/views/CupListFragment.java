package com.infotech.fplcolosseum.features.homepage.views;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentCupListBinding;
import com.infotech.fplcolosseum.databinding.FragmentLeagueListBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.ClassicLeagueListAdapter;
import com.infotech.fplcolosseum.features.homepage.adapter.CupListAdapter;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.LeagueDataModel;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class CupListFragment extends Fragment {


    FragmentCupListBinding binding;
    private HomePageSharedViewModel sharedViewModel;
    List<LeagueDataModel> classicLeagues;
    List<LeagueDataModel> generalLeagues;

    public CupListFragment() {
        // Required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
        classicLeagues = sharedViewModel.getTeamInformationApiResultLiveData().getValue().getData().getLeagues().getClassic().stream().filter(model -> model.getLeague_type().equalsIgnoreCase("x") && model.getHas_cup()).collect(Collectors.toList());
        generalLeagues = sharedViewModel.getTeamInformationApiResultLiveData().getValue().getData().getLeagues().getClassic().stream().filter(model -> model.getLeague_type().equalsIgnoreCase("s") && model.getHas_cup()).collect(Collectors.toList());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCupListBinding.inflate(inflater, container, false);
        setUpAdapter();

        if (classicLeagues.isEmpty()) {
            binding.leagueCupCV.setVisibility(View.GONE);
        }

        if (generalLeagues.isEmpty()) {
            binding.generalCupCV.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    private void setUpAdapter() {

        CupListAdapter classicLeagueCupListAdapter = new CupListAdapter(classicLeagues);
        binding.classicLeagueCupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.classicLeagueCupRecyclerView.setAdapter(classicLeagueCupListAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);
        binding.classicLeagueCupRecyclerView.addItemDecoration(dividerItemDecoration);

        CupListAdapter generalCupListAdapter = new CupListAdapter(generalLeagues);
        binding.generalCupRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.generalCupRecyclerView.setAdapter(generalCupListAdapter);
        binding.generalCupRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
