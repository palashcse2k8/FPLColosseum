package com.infotech.fplcolosseum.features.player_information.views;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.infotech.fplcolosseum.databinding.FragmentMatchesBinding;
import com.infotech.fplcolosseum.features.player_information.adapter.MatchesAdapter;
import com.infotech.fplcolosseum.features.player_information.models.ElementSummary;
import com.infotech.fplcolosseum.features.player_information.models.History;
import com.infotech.fplcolosseum.features.player_information.viewmodels.PlayerInformationViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatchesFragment extends Fragment {

    FragmentMatchesBinding binding;
    PlayerInformationViewModel viewModel;
    private MatchesAdapter adapter;
    private List<History> matchList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMatchesBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(PlayerInformationViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getElementSummary().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse != null && apiResponse.getData() != null) {
                ElementSummary elementSummary = (ElementSummary) apiResponse.getData();
                setupBarChart(elementSummary.getHistory());

                binding.matchesView.setLayoutManager(new LinearLayoutManager(getContext()));
                // Add more items...

                adapter = new MatchesAdapter(elementSummary.getHistory());
                binding.matchesView.setAdapter(adapter);
            } else {
                // Handle the case where the data is null, e.g., show an error message
                // or retry logic can be implemented here.
                // Example: binding.errorMessage.setVisibility(View.VISIBLE);
            }
        });

    }

    private void setupBarChart(ArrayList<History> history) {
        List<BarEntry> entries = new ArrayList<>();

        // Generate random colors for the first 4 weeks
        List<Integer> colors = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < history.size(); i++) {
            entries.add(new BarEntry(i+1, history.get(i).getTotal_points()));
            int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            colors.add(randomColor);
        }

        // Fill remaining weeks (5–38) with 0 points
        for (int i = 5; i <= 38; i++) {
            entries.add(new BarEntry(i, 0));  // Future weeks with 0 points
            colors.add(Color.LTGRAY);  // Future weeks with 0 points
        }

        // Create a BarDataSet for the weeks' data
        BarDataSet dataSet = new BarDataSet(entries, "Points Per Week");

        // Customize the dataset (optional)
        dataSet.setColors(colors);
        dataSet.setValueTextSize(10f);

        // Set ValueFormatter to display integers
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                // Format the value as an integer
                return String.valueOf((int) barEntry.getY());
            }
        });

        // Create BarData and set it to the chart
        BarData barData = new BarData(dataSet);
        binding.barChart.setData(barData);

        // Customize the X-axis to show 38 weeks
        XAxis xAxis = binding.barChart.getXAxis();
        xAxis.setLabelCount(38, true);   // Set 38 weeks on the X axis
        xAxis.setGranularity(1f);        // Each label represents one week
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // X axis at the bottom
        xAxis.setDrawGridLines(false);

        // Customize Y axis (optional)
        YAxis leftAxis = binding.barChart.getAxisLeft();
        YAxis rightAxis = binding.barChart.getAxisRight();
        rightAxis.setEnabled(false); // Disable the right Y-axis if not needed

        // Set animations for chart loading
        binding.barChart.animateY(1500);
        binding.barChart.invalidate();  // Refresh the chart to display data
    }
}
