package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.TRANSFERRED_PLAYER_DATA;
import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.TRANSFER_REQUEST_KEY;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentPlayerSelectionBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerListAdapter;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectionFragment extends Fragment implements MenuProvider {
    FragmentPlayerSelectionBinding binding;
    private PlayerListAdapter adapter;
    private List<PlayersData> playersList;
    private PlayersData transferredPlayerData;

    public static String SELECTED_PLAYER_DATA = "selected_player_data";
    public static String REQUEST_KEY = "requestKey";


    // Example data for the dropdown
    String[] items = new String[]{"All Player", "Goalkeeper", "Defender", "Midfielder", "Forward", "Watchlist", "Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton", "Chelsea", "Crystal Palace", "Everton", "Fulham", "Ipswich", "Leicester City", "Liverpool", "Man City", "Man Utd", "Newcastle", "Nott'm Forest", "Southamton", "Spurs", "West Ham", "Wolves"};


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            transferredPlayerData = (PlayersData) getArguments().getSerializable(TRANSFERRED_PLAYER_DATA);
        }
        // Adapter for dropdowns
        ArrayAdapter<String> playerTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);

        binding.playerTypeDropdown.setAdapter(playerTypeAdapter);
        binding.playerCriterion.setAdapter(playerTypeAdapter);
    }

    public static PlayerSelectionFragment newInstance(PlayersData playerData) {
        PlayerSelectionFragment fragment = new PlayerSelectionFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRANSFERRED_PLAYER_DATA, playerData);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentPlayerSelectionBinding.inflate(getLayoutInflater(), container, false);
        // Initialize Toolbar
        Toolbar toolbar = binding.toolbarSearch;
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
        binding.playerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PlayerListAdapter(playersList, this::onPlayerSelected, requireActivity());
        fetchPlayersFromApi();
        binding.playerRecyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

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
        // Set the listener on the child fragmentManager.
        getParentFragmentManager()
                .setFragmentResultListener(REQUEST_KEY, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
//
                        bundle.putSerializable(TRANSFERRED_PLAYER_DATA, transferredPlayerData);
                        // The child fragment needs to still set the result on its parent fragment manager.
                        getParentFragmentManager().setFragmentResult(TRANSFER_REQUEST_KEY, bundle);

                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                });
    }

    public void sortPlayersByPrice() {
        adapter.sortPlayers((p1, p2) -> Long.compare(p1.getTotal_points(), p2.getTotal_points()));
    }

    @Override
    public void onPrepareMenu(@NonNull Menu menu) {

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
