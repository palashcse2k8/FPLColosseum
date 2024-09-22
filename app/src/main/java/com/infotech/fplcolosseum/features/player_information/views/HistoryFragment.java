package com.infotech.fplcolosseum.features.player_information.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.databinding.FragmentFixturesBinding;
import com.infotech.fplcolosseum.databinding.FragmentHistoryBinding;
import com.infotech.fplcolosseum.features.player_information.adapter.FixtureAdapter;
import com.infotech.fplcolosseum.features.player_information.adapter.HistoryAdapter;
import com.infotech.fplcolosseum.features.player_information.adapter.HistoryAdapterFixed;
import com.infotech.fplcolosseum.features.player_information.adapter.ThisSeasonHistoryAdapter;
import com.infotech.fplcolosseum.features.player_information.models.ElementSummary;
import com.infotech.fplcolosseum.features.player_information.viewmodels.PlayerInformationViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;
    private HistoryAdapter adapter;
    PlayerInformationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
//        Toolbar pointToolBar = requireActivity().findViewById(R.id.pointToolbar);
////        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
//        ((AppCompatActivity) requireActivity()).setSupportActionBar(pointToolBar);

//        binding.setViewModel(viewModel);
//        binding.setLifecycleOwner(this);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(PlayerInformationViewModel.class);
//        viewModel = new ViewModelProvider(requireActivity()).get(HomePageSharedViewModel.class);
//        viewModel.getPointsMergedData(Constants.LoggedInUser.getPlayer().getEntry(), Constants.currentGameWeek);
//        selectedChip = (int) Constants.currentGameWeek;
//        viewModel.getTeamCurrentGameWeekAllData(10359552);
//        setRetainInstance(true);
//        setHasOptionsMenu(true);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewModel.getElementSummary().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse != null && apiResponse.getData() != null) {
                ElementSummary elementSummary = (ElementSummary) apiResponse.getData();
//                setupBarChart(elementSummary.getHistory());

                //Populate this season data
                if (elementSummary.getHistory().size() > 1) {
                    binding.thisSeasonRecyclerHorizontal.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true));
                    binding.thisSeasonRecyclerHorizontal.setAdapter(new ThisSeasonHistoryAdapter(elementSummary.getHistory()));
                    // Add a divider
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                            DividerItemDecoration.VERTICAL);
                    binding.thisSeasonRecyclerHorizontal.addItemDecoration(dividerItemDecoration);
                } else {
                    binding.thisSeasonLayout.setVisibility(View.GONE);
                }

                // Previous season data
                if (elementSummary.getHistory_past().size() > 1) {
                    binding.recyclerFixed.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true));

                    binding.recyclerFixed.setAdapter(new HistoryAdapterFixed(elementSummary.getHistory_past()));
// Add a divider
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                            DividerItemDecoration.VERTICAL);
                    binding.recyclerFixed.addItemDecoration(dividerItemDecoration);

                    binding.recyclerHorizontal.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true));

                    adapter = new HistoryAdapter(elementSummary.getHistory_past());
                    binding.recyclerHorizontal.setAdapter(adapter);
;
                    binding.recyclerHorizontal.addItemDecoration(dividerItemDecoration);
                } else {
                    binding.previousSeasonLayout.setVisibility(View.GONE);
                }

            } else {
                // Handle the case where the data is null, e.g., show an error message
                // or retry logic can be implemented here.
                // Example: binding.errorMessage.setVisibility(View.VISIBLE);
            }
        });


    }

    private List<List<String>> generateData() {
        List<List<String>> data = new ArrayList<>();

        // Header row
        data.add(Arrays.asList("Name", "Age", "City", "Occ", "Sal", "Exp"));

        // Sample data rows
        data.add(Arrays.asList("John", "30", "New", "Software", "$95", "5"));
        data.add(Arrays.asList("Jane", "28", "San", "Data", "$105", "3"));

        return data;
    }
}
