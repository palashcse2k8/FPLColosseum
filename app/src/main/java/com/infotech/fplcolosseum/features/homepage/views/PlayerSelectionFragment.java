package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.TRANSFERRED_PLAYER_DATA;
import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.TRANSFER_REQUEST_KEY;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.TeamData;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerSelectionFragment extends Fragment implements MenuProvider {
    FragmentPlayerSelectionBinding binding;
    private PlayerListAdapter adapter;
    private List<PlayersData> playersList;
    private PlayersData transferredPlayerData;

    public static String SELECTED_PLAYER_DATA = "selected_player_data";
    public static String REQUEST_KEY = "requestKey";


    // Example data for the dropdown
    ArrayList<String> playerTypeItems = new ArrayList<>();
    ArrayList<String> playerTeams = new ArrayList<>();
    String[] playerCriterionItems = new String[]{"Total Points", "Round Points", "Team Selected By", "Minutes Played", "Goal Scored", "Assist", "Clean Sheet", "Goal Conceded", "Own Goals", "Penalty Saved", "Penalty Missed", "Yellow Cards", "Red Cards", "Saves", "Bonus", "Bonus Points System", "Influence", "Creativity", "Threat", "ICT Index", "Games Started", "Form", "Times in Team of the Week", "Value(form)", "Value(season)", "Points Per Match", "Transfers In", "Transfers Out", "Transfers In(round)", "Transfers Out(round)", "Net Transfers In(round)", "Net Transfers Out(round)", "Price Rise", "Price Fall", "Price Rice(round)", "Price Fall(round)", "Expected Goals(xG)", "Expected Assists(xA)", "Expected Goals Involvement(xGI)", "Expected Goal Conceded(xGC)"};


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            transferredPlayerData = (PlayersData) getArguments().getSerializable(TRANSFERRED_PLAYER_DATA);
        }

        // Adapter for dropdowns
        playerTypeItems.add("All Player");
        ArrayList<String> playerTypes = (ArrayList<String>) Constants.playerTypeMap.values()
                .stream()
                .map(Player_Type::getSingular_name)
                .collect(Collectors.toList());
        playerTypeItems.addAll(playerTypes);

        ArrayAdapter<String> playerTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, playerTypeItems);
        playerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.playerTypeDropdown.setAdapter(playerTypeAdapter);
        binding.playerTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                adapter.filterPlayersByType(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Adapter for dropdowns
        playerTeams.add("All Club");
        ArrayList<String> teamNames = (ArrayList<String>) Constants.teamMap.values()
                .stream()
                .map(TeamData::getName)
                .collect(Collectors.toList());
        playerTeams.addAll(teamNames);
        ArrayAdapter<String> playerTeamsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, playerTeams);
        playerTeamsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.playerTeamDropdown.setAdapter(playerTeamsAdapter);
        binding.playerTeamDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                adapter.filterPlayersByTeam(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Adapter for dropdowns
        ArrayAdapter<String> playerCriterionAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, playerCriterionItems);
        playerCriterionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.playerCriterion.setAdapter(playerCriterionAdapter);
        binding.playerCriterion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                adapter.sortAdapterData(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
