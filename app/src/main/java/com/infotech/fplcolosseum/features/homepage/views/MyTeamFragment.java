package com.infotech.fplcolosseum.features.homepage.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.FragmentMyteamBinding;
import com.infotech.fplcolosseum.features.gameweek.models.custom.PlayerDataModel;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyTeamFragment extends Fragment {

    FragmentMyteamBinding binding;

    private String tabTitle;
    private List<PlayerDataModel> playingElevenGoalKeeper;
    private List<PlayerDataModel> playingElevenDefenders;
    private List<PlayerDataModel> playingElevenMidfielders;
    private List<PlayerDataModel> playingElevenForwards;
    private List<PlayerDataModel> benchPlayers;

    private PlayerAdapter playingElevenGoalKeeperAdapter;
    private PlayerAdapter playingElevenDefendersAdapter;
    private PlayerAdapter playingElevenMidfieldersAdapter;
    private PlayerAdapter playingElevenForwardsAdapter;
    private PlayerAdapter benchAdapter;
    RecyclerView playingElevenRecyclerView;
    RecyclerView benchRecyclerView;


    public MyTeamFragment(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initializeList() {
        // Initialize player lists
        playingElevenGoalKeeper = new ArrayList<>();
        benchPlayers = new ArrayList<>();

        // Populate player lists (Replace with your actual player data)

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyteamBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("MyTeamFragment", "onViewCreated: RecyclerView setup");

//        initializeList();

        playingElevenGoalKeeper = new ArrayList<>();
        playingElevenDefenders = new ArrayList<>();
        playingElevenMidfielders = new ArrayList<>();
        playingElevenForwards = new ArrayList<>();


        // Populate player lists (Replace with your actual player data)

        PlayerDataModel playerDataModel = new PlayerDataModel();
        playerDataModel.setPlayerName("Martinez ");
        playerDataModel.setTeamName("Aston Villa");
        playingElevenGoalKeeper.add(playerDataModel);


        for (int i = 0; i < 5; i++) {
            playerDataModel = new PlayerDataModel();
            playerDataModel.setPlayerName("Martinez " + i);
            playerDataModel.setTeamName("Aston Villa");
            playingElevenDefenders.add(playerDataModel);
        }

        for (int i = 0; i < 5; i++) {
            playerDataModel = new PlayerDataModel();
            playerDataModel.setPlayerName("Martinez " + i);
            playerDataModel.setTeamName("Aston Villa");
            playingElevenMidfielders.add(playerDataModel);
        }
        for (int i = 0; i < 5; i++) {
            playerDataModel = new PlayerDataModel();
            playerDataModel.setPlayerName("Martinez " + i);
            playerDataModel.setTeamName("Aston Villa");
            playingElevenForwards.add(playerDataModel);
        }

        playingElevenGoalKeeperAdapter = new PlayerAdapter(playingElevenGoalKeeper);
        playingElevenDefendersAdapter = new PlayerAdapter(playingElevenDefenders);
        playingElevenMidfieldersAdapter= new PlayerAdapter(playingElevenMidfielders);
        playingElevenForwardsAdapter = new PlayerAdapter(playingElevenForwards);

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 5);
        binding.playingElevenGoalKeepRecyclerView.setLayoutManager(layoutManager);
        binding.playingElevenGoalKeepRecyclerView.setAdapter(playingElevenGoalKeeperAdapter);
        binding.playingElevenDefenderRecyclerView.setAdapter(playingElevenDefendersAdapter);
        binding.playingElevenMidfielderRecyclerView.setAdapter(playingElevenMidfieldersAdapter);
        binding.playingElevenForwardRecyclerView.setAdapter(playingElevenForwardsAdapter);

        // Add data to adapters after setting up RecyclerView
//        playingElevenAdapter.setData(playingElevenPlayers);
//        benchAdapter.setData(benchPlayers);

//        // Enable drag-and-drop for the bench players
//        ItemTouchHelper.Callback callback = new PlayerItemTouchHelperCallback(benchAdapter);
//        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
//        touchHelper.attachToRecyclerView(binding.playingElevenGoalKeepRecyclerView);
//
//        Log.d("MyTeamFragment", "Playing Eleven Size: " + playingElevenPlayers.size());
//        Log.d("MyTeamFragment", "Bench Size: " + benchPlayers.size());
    }
}
