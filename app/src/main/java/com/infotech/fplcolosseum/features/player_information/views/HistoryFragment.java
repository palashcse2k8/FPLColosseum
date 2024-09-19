package com.infotech.fplcolosseum.features.player_information.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.databinding.FragmentFixturesBinding;
import com.infotech.fplcolosseum.databinding.FragmentHistoryBinding;
import com.infotech.fplcolosseum.features.player_information.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;
    private HistoryAdapter adapter;

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

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        adapter = new HistoryAdapter(generateData());
        binding.recyclerView.setAdapter(adapter);
    }

    private List<List<String>> generateData() {
        List<List<String>> data = new ArrayList<>();

        // Header row
        data.add(Arrays.asList("Name", "Age", "City", "Occupation", "Salary", "Years of Experience"));

        // Sample data rows
        data.add(Arrays.asList("John Doe", "30", "New York", "Software Engineer", "$95,000", "5"));
        data.add(Arrays.asList("Jane Smith", "28", "San Francisco", "Data Scientist", "$105,000", "3"));
        data.add(Arrays.asList("Mike Johnson", "35", "Chicago", "Product Manager", "$110,000", "8"));
        data.add(Arrays.asList("Emily Brown", "32", "Boston", "UX Designer", "$85,000", "6"));
        data.add(Arrays.asList("David Lee", "40", "Seattle", "Senior Developer", "$120,000", "12"));
        data.add(Arrays.asList("Sarah Wilson", "27", "Austin", "Marketing Specialist", "$75,000", "4"));
        data.add(Arrays.asList("Tom Anderson", "38", "Los Angeles", "Sales Manager", "$100,000", "10"));
        data.add(Arrays.asList("Lisa Taylor", "33", "Denver", "HR Manager", "$90,000", "7"));
        data.add(Arrays.asList("Chris Martin", "29", "Portland", "Graphic Designer", "$80,000", "5"));
        data.add(Arrays.asList("Anna Garcia", "36", "Miami", "Financial Analyst", "$95,000", "9"));

        return data;
    }
}
