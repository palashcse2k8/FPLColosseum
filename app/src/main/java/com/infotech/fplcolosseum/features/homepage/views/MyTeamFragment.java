package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentHomepageBinding;
import com.infotech.fplcolosseum.databinding.FragmentMyteamBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerAdapter;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class MyTeamFragment extends Fragment {

    FragmentMyteamBinding binding;

    private String tabTitle;
    private List<String> playingElevenPlayers;
    private List<String> benchPlayers;

    private PlayerAdapter playingElevenAdapter;
    private PlayerAdapter benchAdapter;


    public MyTeamFragment(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize player lists
        playingElevenPlayers = new ArrayList<>();
        benchPlayers = new ArrayList<>();

        // Populate player lists (Replace with your actual player data)
        for (int i = 1; i <= 5; i++) {
            playingElevenPlayers.add("Player " + i);
        }

        for (int i = 12; i <= 15; i++) {
            benchPlayers.add("Player " + i);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyteamBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        View view = inflater.inflate(R.layout.fragment_myteam, container, false);
////        TextView textView = view.findViewById(R.id.textView);
////        textView.setText(tabTitle);
//        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView playingElevenRecyclerView = binding.playingElevenGoalKeepRecyclerView;
        RecyclerView benchRecyclerView = binding.benchRecyclerView;

        playingElevenAdapter = new PlayerAdapter(playingElevenPlayers);
        benchAdapter = new PlayerAdapter(benchPlayers);

        playingElevenRecyclerView.setAdapter(playingElevenAdapter);
        benchRecyclerView.setAdapter(benchAdapter);

        playingElevenRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 5));
        benchRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 5));

        // Enable drag-and-drop for the bench players
        ItemTouchHelper.Callback callback = new PlayerItemTouchHelperCallback(benchAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(benchRecyclerView);
    }
}
