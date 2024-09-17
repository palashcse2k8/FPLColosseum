package com.infotech.fplcolosseum.features.player_information.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentMatchesBinding;
import com.infotech.fplcolosseum.databinding.FragmentPointsBinding;
import com.infotech.fplcolosseum.features.homepage.viewmodels.HomePageSharedViewModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class MatchesFragment extends Fragment {

    FragmentMatchesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMatchesBinding.inflate(inflater, container, false);
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

        setupLineChart();

        setupBarChart();
    }

    private void setupLineChart() {
        List<Entry> entries = new ArrayList<>();

        // Add data points for the line chart (x, y)
        entries.add(new Entry(1, 10));
        entries.add(new Entry(2, 15));
        entries.add(new Entry(3, 5));
        entries.add(new Entry(4, 17));

        for (int i = 5; i <= 38; i++) {
            entries.add(new Entry(i, 0));  // Future weeks with 0 points
        }

        // Create a dataset with the entries
        LineDataSet lineDataSet = new LineDataSet(entries, "Player Performance");

        // Customize the dataset
        lineDataSet.setLineWidth(2f);
        lineDataSet.setColor(getResources().getColor(R.color.black));
        lineDataSet.setCircleColor(getResources().getColor(R.color.black));
        lineDataSet.setValueTextSize(12f);

        // Add the dataset to a list of ILineDataSet (multiple datasets can be used)
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        // Create LineData from the datasets
        LineData lineData = new LineData(dataSets);

        // Set data to the chart
        binding.lineChart.setData(lineData);
        binding.lineChart.animateX(1500);

        // Customize the X axis to display 38 weeks (marking the future weeks empty)
        XAxis xAxis = binding.lineChart.getXAxis();
        xAxis.setLabelCount(38, true);  // 38 weeks
        xAxis.setGranularity(1f);       // Each label represents one week
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // X axis at the bottom
        xAxis.setDrawGridLines(false);

        // Customize Y axis if needed (optional)
        YAxis leftAxis = binding.lineChart.getAxisLeft();
        YAxis rightAxis = binding.lineChart.getAxisRight();
        rightAxis.setEnabled(false);  // Disable the right Y axis

        // Refresh the chart
        binding.lineChart.invalidate(); // Refresh the chart to show the data
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();

        // Add entries for completed weeks (week number, points gathered)
        entries.add(new BarEntry(1, 50));  // Week 1
        entries.add(new BarEntry(2, 60));  // Week 2
        entries.add(new BarEntry(3, 55));  // Week 3
        entries.add(new BarEntry(4, 70));  // Week 4

        // Fill remaining weeks (5–38) with 0 points
        for (int i = 5; i <= 38; i++) {
            entries.add(new BarEntry(i, 0));  // Future weeks with 0 points
        }

        // Create a BarDataSet for the weeks' data
        BarDataSet dataSet = new BarDataSet(entries, "Points Per Week");

        // Customize the dataset (optional)
        dataSet.setColor(getResources().getColor(R.color.black));
        dataSet.setValueTextSize(10f);

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
