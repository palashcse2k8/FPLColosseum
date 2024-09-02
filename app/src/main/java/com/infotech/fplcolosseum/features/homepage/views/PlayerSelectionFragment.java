package com.infotech.fplcolosseum.features.homepage.views;

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
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerListAdapter;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectionFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlayerListAdapter adapter;
    private List<PlayersData> playersList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_selection, container, false);

        recyclerView = view.findViewById(R.id.player_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PlayerListAdapter(playersList, this::onPlayerSelected);
        recyclerView.setAdapter(adapter);

        // If you need to fetch players from an API
        fetchPlayersFromApi();

        return view;
    }

    private void fetchPlayersFromApi() {
        // Simulating API call
        playersList = new ArrayList<>(Constants.playerMap.values());

        adapter.updatePlayersList(playersList);
    }

    private void onPlayerSelected(PlayersData player) {
        // Handle player selection, for example, pass it back to the bottom sheet
    }

    public void sortPlayersByAttribute() {
        adapter.sortPlayers((p1, p2) -> Integer.compare(p1.getSomeAttribute(), p2.getSomeAttribute()));
    }
}
