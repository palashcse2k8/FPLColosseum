package com.infotech.fplcolosseum.features.player_information.views;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentFixturesBinding;
import com.infotech.fplcolosseum.databinding.FragmentHistoryBinding;
import com.infotech.fplcolosseum.features.player_information.adapter.FixtureAdapter;
import com.infotech.fplcolosseum.features.player_information.adapter.HistoryAdapter;
import com.infotech.fplcolosseum.features.player_information.adapter.HistoryAdapterFixed;
import com.infotech.fplcolosseum.features.player_information.adapter.ThisSeasonHistoryAdapter;
import com.infotech.fplcolosseum.features.player_information.models.ElementSummary;
import com.infotech.fplcolosseum.features.player_information.models.History;
import com.infotech.fplcolosseum.features.player_information.viewmodels.PlayerInformationViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                    changeTextViewColors(binding.thisSeasonHeader.thisSeasonItemCardView);

                    // Reverse the list (if not reversed already)
                    List<History> reversedHistory = new ArrayList<>(elementSummary.getHistory());
                    Collections.reverse(reversedHistory);
                    binding.thisSeasonRecyclerHorizontal.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                    binding.thisSeasonRecyclerHorizontal.setAdapter(new ThisSeasonHistoryAdapter(reversedHistory));
                    // Add a divider
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                            DividerItemDecoration.VERTICAL);
                    binding.thisSeasonRecyclerHorizontal.addItemDecoration(dividerItemDecoration);
                } else {
                    binding.thisSeasonLayout.setVisibility(View.GONE);
                }

                // Previous season data
                if (elementSummary.getHistory_past().size() > 1) {

                    changeTextViewColors(binding.itemSeasonFixedHeader.itemSeasonFixedCardView);
                    binding.recyclerFixed.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true));

                    binding.recyclerFixed.setAdapter(new HistoryAdapterFixed(elementSummary.getHistory_past()));
                    // Add a divider
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(requireContext(),
                            DividerItemDecoration.VERTICAL);
                    binding.recyclerFixed.addItemDecoration(dividerItemDecoration);

                    changeTextViewColors(binding.itemSeasonHeader.itemSeasonCardView);
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

    public void changeTextViewColors(ViewGroup headerView) {

        // Set the background color of the CardView
        headerView.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.deepGreen)
        );

        // Find all TextViews in the layout and change their color to white
        changeTextViewColorRecursively(headerView);
    }

    private void changeTextViewColorRecursively(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                changeTextViewColorRecursively(child);
            }
        } else if (view instanceof TextView) {
            ((TextView) view).setTextColor(Color.WHITE);
        }
    }

}
