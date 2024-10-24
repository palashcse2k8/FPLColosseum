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

import com.infotech.fplcolosseum.databinding.FragmentLeagueStandingBinding;
import com.infotech.fplcolosseum.features.league_information.adapters.LeagueStandingAdapter;
import com.infotech.fplcolosseum.features.league_information.viewmodels.LeagueInformationViewModel;

public class LeagueStandingFragment extends Fragment {

    FragmentLeagueStandingBinding binding;
    LeagueInformationViewModel viewModel;

    int currentPage;
    boolean hasNext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(LeagueInformationViewModel.class);
        hasNext = viewModel.getLeagueStandingApiLiveData().getValue().getData().getStandings().getHas_next();
        currentPage = (int) viewModel.getLeagueStandingApiLiveData().getValue().getData().getStandings().getPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLeagueStandingBinding.inflate(inflater, container, false);

        LeagueStandingAdapter leagueStandingAdapter = new LeagueStandingAdapter(viewModel.getLeagueStandingApiLiveData().getValue().getData().getStandings().getResults());
        binding.leagueStandingRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.leagueStandingRecyclerView.setAdapter(leagueStandingAdapter);

        setupPagination();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupPagination() {
        updatePageButtons();

        binding.btnPreviousPage.setOnClickListener(v -> {
            if (currentPage > 1) {
                currentPage--;
                loadPage(currentPage);
                updatePageButtons();
            }
        });

        binding.btnNextPage.setOnClickListener(v -> {
            if (hasNext) {
                currentPage++;
                loadPage(currentPage);
                updatePageButtons();
            }
        });
    }

    private void loadPage(int page) {
        binding.tvPageNumber.setText("Page " + page);
        long leagueID = viewModel.getLeagueStandingApiLiveData().getValue().getData().getLeague().getId();
        viewModel.getLeagueInformation(leagueID, 1, currentPage, 1);
    }

    private void updatePageButtons() {
        binding.btnPreviousPage.setEnabled(currentPage > 1);
        binding.btnNextPage.setEnabled(hasNext);
    }
}
