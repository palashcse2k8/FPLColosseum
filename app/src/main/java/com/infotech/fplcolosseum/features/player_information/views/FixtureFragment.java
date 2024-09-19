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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.databinding.FragmentFixturesBinding;
import com.infotech.fplcolosseum.features.player_information.adapter.FixtureAdapter;

import com.infotech.fplcolosseum.features.player_information.models.ElementSummary;
import com.infotech.fplcolosseum.features.player_information.models.Fixtures;
import com.infotech.fplcolosseum.features.player_information.models.History;
import com.infotech.fplcolosseum.features.player_information.viewmodels.PlayerInformationViewModel;

import java.util.List;

public class FixtureFragment extends Fragment {

    FragmentFixturesBinding binding;
    PlayerInformationViewModel viewModel;
    private FixtureAdapter adapter;
    private List<Fixtures> matchList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFixturesBinding.inflate(inflater, container, false);
        return binding.getRoot();
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
//                setupBarChart(elementSummary.getHistory());

                binding.fixtureView.setLayoutManager(new LinearLayoutManager(getContext()));
                // Add more items...

                adapter = new FixtureAdapter(elementSummary.getFixtures());
                binding.fixtureView.setAdapter(adapter);
                // Add a divider
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                        DividerItemDecoration.VERTICAL);
                binding.fixtureView.addItemDecoration(dividerItemDecoration);
            } else {
                // Handle the case where the data is null, e.g., show an error message
                // or retry logic can be implemented here.
                // Example: binding.errorMessage.setVisibility(View.VISIBLE);
            }
        });

    }

//    private void setupBarChart(ArrayList<History> history) {
//        List<BarEntry> entries = new ArrayList<>();
//
//        // Generate random colors for the first 4 weeks
//        List<Integer> colors = new ArrayList<>();
//        Random random = new Random();
//
//        for (int i = 0; i < history.size(); i++) {
//            entries.add(new BarEntry(i+1, history.get(i).getTotal_points()));
//            int randomColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
//            colors.add(randomColor);
//        }
//
//        // Fill remaining weeks (5–38) with 0 points
//        for (int i = 5; i <= 38; i++) {
//            entries.add(new BarEntry(i, 0));  // Future weeks with 0 points
//            colors.add(Color.LTGRAY);  // Future weeks with 0 points
//        }
//
//        // Create a BarDataSet for the weeks' data
//        BarDataSet dataSet = new BarDataSet(entries, "Points Per Week");
//
//        // Customize the dataset (optional)
//        dataSet.setColors(colors);
//        dataSet.setValueTextSize(10f);
//        dataSet.setValueTextColor(Color.BLACK);
//
//        // Ensure values are drawn on bars
//        dataSet.setDrawValues(true);
//
//
//        // Set ValueFormatter to display integers
//        dataSet.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getBarLabel(BarEntry barEntry) {
//                // Format the value as an integer
//                return String.valueOf((int) barEntry.getY());
//            }
//        });
//
//        // Create BarData and set it to the chart
//        BarData barData = new BarData(dataSet);
//        binding.barChart.setData(barData);
//
//        Description description = new Description();
//        description.setText("Points Per Week");  // Set your custom description text
//        description.setTextSize(12f);  // Set description text size (optional)
//        description.setTextColor(Color.BLACK);  // Set description text color (optional)
//        binding.barChart.setDescription(description);  // Set the description to the chart
//
//        // Customize the X-axis to show 38 weeks
//        XAxis xAxis = binding.barChart.getXAxis();
//        xAxis.setLabelCount(38, true);   // Set 38 weeks on the X axis
//        xAxis.setGranularity(1f);        // Each label represents one week
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // X axis at the bottom
//        xAxis.setDrawGridLines(false);
//
//        // Customize Y axis (optional)
//        YAxis leftAxis = binding.barChart.getAxisLeft();
//        YAxis rightAxis = binding.barChart.getAxisRight();
//        rightAxis.setEnabled(false); // Disable the right Y-axis if not needed
//
//        // Set up the legend to display the dataset label ("Points Per Week")
//        Legend legend = binding.barChart.getLegend();
//        legend.setEnabled(true);  // Enable legend
////        legend.setTextSize(12f);  // Set legend text size
////        legend.setFormSize(10f);  // Set the form size (icon size)
////        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);  // Align legend to the center
////        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);  // Place legend at the top
////        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);  // Make legend horizontal
//
//
//        // Set animations for chart loading
//        binding.barChart.animateY(1500);
//        binding.barChart.invalidate();  // Refresh the chart to display data
//    }
}
