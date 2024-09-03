package com.infotech.fplcolosseum.features.homepage.views;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerListAdapter;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectionFragment extends Fragment implements MenuProvider {

    private RecyclerView recyclerView;
    private PlayerListAdapter adapter;
    private List<PlayersData> playersList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_selection, container, false);

        // Initialize Toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar_search);
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle("Search Players");
        }

        // Clear existing MenuProviders and add this fragment as the MenuProvider
        requireActivity().removeMenuProvider(this);
        requireActivity().addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        // If you need to fetch players from an API
        recyclerView = view.findViewById(R.id.player_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PlayerListAdapter(playersList, this::onPlayerSelected);
        fetchPlayersFromApi();
        recyclerView.setAdapter(adapter);

        return view;
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//
//        // Clear the previous menu items
////        menu.clear();
//
//        inflater.inflate(R.menu.menu_player_search, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    private void fetchPlayersFromApi() {
        // Simulating API call
        playersList = new ArrayList<>(Constants.playerMap.values());

        adapter.updatePlayersList(playersList);
    }

    private void onPlayerSelected(PlayersData player) {
        // Handle player selection, for example, pass it back to the bottom sheet
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void sortPlayersByPrice() {
        adapter.sortPlayers((p1, p2) -> Long.compare(p1.getTotal_points(), p2.getTotal_points()));
    }

    @Override
    public void onPrepareMenu(@NonNull Menu menu) {

        //                MenuItem refreshItem = menu.findItem(R.id.action_refresh);
//                MenuItem shareItem = menu.findItem(R.id.action_share);
//                MenuItem saveItem = menu.findItem(R.id.action_save);
//                MenuItem clearItem = menu.findItem(R.id.action_undo);
//
//
//                // Set visibility based on your conditions
//                refreshItem.setVisible(isRefreshVisible);
//                shareItem.setVisible(isShareVisible);
//                saveItem.setVisible(isSaveVisible);
//                clearItem.setVisible(isClearVisible);
//                menu.clear();

        // Instead of clearing the entire menu, selectively show/hide items
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() == R.id.action_search ||
                    item.getItemId() == R.id.action_sort_by_name ||
                    item.getItemId() == R.id.action_sort_by_age) {
                item.setVisible(true);
            } else {
                item.setVisible(false);
            }
        }
        MenuProvider.super.onPrepareMenu(menu);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
//                menuInflater.inflate(R.menu.my_team_menu, menu);
//
//        menu.clear(); // Clear any existing menu items
        menuInflater.inflate(R.menu.menu_player_search, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        }

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    // Restore the main list when the query is cleared
                    adapter.updatePlayersList(new ArrayList<>(Constants.playerMap.values()));
                } else {
                    adapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            // Optional: This is triggered when the search view is closed (if you handle this case)
            adapter.updatePlayersList(new ArrayList<>(Constants.playerMap.values()));
            return false;
        });
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
