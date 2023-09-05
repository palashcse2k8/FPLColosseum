package com.infotech.fplcolosseum.gameweek.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.GameweekDashboardFragmentBinding;
import com.infotech.fplcolosseum.gameweek.models.Team;
import com.infotech.fplcolosseum.gameweek.viewmodels.TeamAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameWeekDashboard extends Fragment {

    GameweekDashboardFragmentBinding binding;
    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private List<Team> teams;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GameweekDashboardFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.recyclerView1;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Initialize your data (replace with your actual data)
        teams = new ArrayList<>();
        teams.add(new Team("Team A", "Md. Mosiur Rahman", 50, 150));
        teams.add(new Team("Team A", "Md. Mosiur Rahman", 50, 151));
        teams.add(new Team("Team A", "Md. Mosiur Rahman", 50, 152));
        teams.add(new Team("Team A", "Md. Mosiur Rahman", 50, 153));


        // Sort the data based on your predefined rules
        Collections.sort(teams, (team1, team2) -> {
            // Your sorting logic here, e.g., based on points
            return Integer.compare(team2.getTotalPoints(), team1.getTotalPoints());
        });

        adapter = new TeamAdapter(teams);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
