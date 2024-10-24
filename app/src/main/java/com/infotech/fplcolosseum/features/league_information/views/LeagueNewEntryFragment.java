package com.infotech.fplcolosseum.features.league_information.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.databinding.FragmentLeagueNewEntryBinding;
import com.infotech.fplcolosseum.features.league_information.adapters.LeagueNewEntryAdapter;
import com.infotech.fplcolosseum.features.league_information.viewmodels.LeagueInformationViewModel;

public class LeagueNewEntryFragment extends Fragment {

    FragmentLeagueNewEntryBinding binding;
    LeagueInformationViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LeagueInformationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeagueNewEntryBinding.inflate(inflater, container, false);

        LeagueNewEntryAdapter leagueNewEntryAdapter = new LeagueNewEntryAdapter(viewModel.getLeagueStandingApiLiveData().getValue().getData().getNew_entries().getResults());
        binding.leagueNewEntryRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.leagueNewEntryRecyclerView.setAdapter(leagueNewEntryAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
