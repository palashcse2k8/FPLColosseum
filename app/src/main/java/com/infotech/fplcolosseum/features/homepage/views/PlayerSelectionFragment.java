package com.infotech.fplcolosseum.features.homepage.views;

import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.CURRENT_BALANCE;
import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.CURRENT_TEAM_PLAYERS;
import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.TRANSFERRED_PLAYER_DATA;
import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.TRANSFER_REQUEST_KEY;

import android.content.res.ColorStateList;
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

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentPlayerSelectionBinding;
import com.infotech.fplcolosseum.features.homepage.adapter.PlayerListAdapter;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.TeamData;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;

public class PlayerSelectionFragment extends Fragment implements MenuProvider {
    FragmentPlayerSelectionBinding binding;
    private PlayerListAdapter adapter;
    private List<PlayersData> playersList;
    private List<PlayersData> filteredPlayerList;
    private PlayersData transferredPlayerData;
    private ArrayList<PlayersData> teamPlayers;

    public static String SELECTED_PLAYER_DATA = "selected_player_data";
    public static String REQUEST_KEY = "requestKey";

    public String playerSearchText = null;
    public long availableBalance;
    // Example data for the dropdown
    ArrayList<String> playerTypes = new ArrayList<>();
    ArrayList<String> playerTeams = new ArrayList<>();
    String[] playerCriterionItems = new String[]{"Total Points", "Round Points", "Team Selected By", "Minutes Played", "Goal Scored", "Assist", "Clean Sheet", "Goal Conceded", "Own Goals", "Penalty Saved", "Penalty Missed", "Yellow Cards", "Red Cards", "Saves", "Bonus", "Bonus Points System", "Influence", "Creativity", "Threat", "ICT Index", "Games Started", "Form", "Times in Team of the Week", "Value(form)", "Value(season)", "Points Per Match", "Transfers In", "Transfers Out", "Transfers In(round)", "Transfers Out(round)", "Net Transfers In(round)", "Net Transfers Out(round)", "Price Rise", "Price Fall", "Price Rice(round)", "Price Fall(round)", "Expected Goals(xG)", "Expected Assists(xA)", "Expected Goals Involvement(xGI)", "Expected Goal Conceded(xGC)"};

    public static PlayerSelectionFragment newInstance(PlayersData playerData, ArrayList<PlayersData> teamPlayers, long balance) {
        PlayerSelectionFragment fragment = new PlayerSelectionFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRANSFERRED_PLAYER_DATA, playerData);
        args.putLong(CURRENT_BALANCE, balance);
        args.putSerializable(CURRENT_TEAM_PLAYERS, teamPlayers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchPlayersFromApi();
        // Set the listener on the child fragmentManager.

        getParentFragmentManager()
                .setFragmentResultListener(REQUEST_KEY, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                        PlayersData selectedPlayer = (PlayersData) bundle.getSerializable(SELECTED_PLAYER_DATA);

                        if(selectedPlayer != null && selectedPlayer.getElement_type() != transferredPlayerData.getElement_type()){ // Check if correct player type is selected
                            Toasty.warning(requireContext(), "Please Select a " + transferredPlayerData.getElement_type_full()).show();
                            return;
                        }

                        bundle.putSerializable(TRANSFERRED_PLAYER_DATA, transferredPlayerData);
                        // The child fragment needs to still set the result on its parent fragment manager.
                        getParentFragmentManager().setFragmentResult(TRANSFER_REQUEST_KEY, bundle);

                        requireActivity().getSupportFragmentManager().popBackStack();
                    }
                });
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

        toolbar.setNavigationOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed(); //apply default button back press action
        });


        // Clear existing MenuProviders and add this fragment as the MenuProvider
        requireActivity().removeMenuProvider(this);
        requireActivity().addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        if (getArguments() != null) {
            transferredPlayerData = (PlayersData) getArguments().getSerializable(TRANSFERRED_PLAYER_DATA);
            teamPlayers = (ArrayList<PlayersData>) getArguments().getSerializable(CURRENT_TEAM_PLAYERS);
            availableBalance = getArguments().getLong(CURRENT_BALANCE);
        }

        // If you need to fetch players from an API
        binding.playerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PlayerListAdapter(playersList, this::onPlayerSelected, transferredPlayerData, teamPlayers, requireActivity());
        binding.playerRecyclerView.setAdapter(adapter);
        String availableBalanceText = "Available Balance : " + CustomUtil.convertedPrice(availableBalance);
        binding.tvBalance.setText(availableBalanceText);
//        adapter.updatePlayersList(playersList);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpDropdowns();
        setUpRangeSlider();
        updateInitialAdapterData(transferredPlayerData.getElement_type_full());
        updateRangeSlider();
    }

    private void setUpRangeSlider() {
        binding.rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(RangeSlider slider, float value, boolean fromUser) {
                // Get the current values of the RangeSlider
                float minPrice = binding.rangeSlider.getValues().get(0) * 10;
                float maxPrice = binding.rangeSlider.getValues().get(1) * 10;
                filteredPlayerList = playersList.stream().filter(playersData -> playersData.getNow_cost() >= minPrice && playersData.getNow_cost() <= maxPrice).collect(Collectors.toList());

                filterSortAndUpdatePlayer(filteredPlayerList);
            }
        });

        // Set a LabelFormatter for formatting slider values
        binding.rangeSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                // Format the value as currency with 1 decimal place and the Euro symbol
                return String.format("€%.1f", value);
            }
        });
    }
    private void updateRangeSlider() {
        Optional<PlayersData> minPlayer = filteredPlayerList.stream()
                .min(Comparator.comparing(PlayersData::getNow_cost));

        float minPrice = minPlayer.map(data -> (float) data.getNow_cost() / 10).orElse(0f);

        Optional<PlayersData> maxPlayer = filteredPlayerList.stream()
                .max(Comparator.comparing(PlayersData::getNow_cost));

        float maxPrice = maxPlayer.map(playersData -> (float) playersData.getNow_cost() / 10).orElse(0f);

        if(minPrice >= maxPrice) return; // validate from and to data before set

        binding.rangeSlider.setValueFrom(minPrice);
        binding.rangeSlider.setValueTo(maxPrice);
        binding.rangeSlider.setValues(minPrice, maxPrice);
        binding.tvMin.setText(CustomUtil.convertedPrice((long) (minPrice*10)));
        binding.tvMax.setText(CustomUtil.convertedPrice((long) (maxPrice*10)));

    }

    private void updateInitialAdapterData(String playerType) {
        int position = playerTypes.indexOf(playerType);
        binding.playerTypeDropdown.setSelection(position);
        filterSortAndUpdatePlayer(playersList);
    }

    private void setUpDropdowns() {
        // Adapter for dropdowns
        playerTypes.add("All Player");
        ArrayList<String> playerTypes = (ArrayList<String>) Constants.playerTypeMap.values()
                .stream()
                .map(Player_Type::getSingular_name)
                .collect(Collectors.toList());
        this.playerTypes.addAll(playerTypes);

        ArrayAdapter<String> playerTypeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, this.playerTypes);
        playerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.playerTypeDropdown.setAdapter(playerTypeAdapter);
        binding.playerTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterSortAndUpdatePlayer(playersList);
//                updateRangeSlider();
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
                filterSortAndUpdatePlayer(playersList);
//                updateRangeSlider();
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
                sortAndUpdatePlayer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fetchPlayersFromApi() {
        // Simulating API call
        playersList = new ArrayList<>(Constants.playerMap.values());
    }

    private void onPlayerSelected(PlayersData player) {
        // Handle player selection, for example, pass it back to the bottom sheet
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

                playerSearchText = newText;
                filterSortAndUpdatePlayer(playersList);
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            // Optional: This is triggered when the search view is closed (if you handle this case)
            filterSortAndUpdatePlayer(playersList);
            return false;
        });
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private void filterSortAndUpdatePlayer(List<PlayersData> initialPlayerList) {
        String selectedTeam = binding.playerTeamDropdown.getSelectedItem().toString(); //get selected player team
        String selectedPlayerType = binding.playerTypeDropdown.getSelectedItem().toString(); // get selected player type
        String sortingCriteria = binding.playerCriterion.getSelectedItem().toString(); // get selected sorting criterion


        filteredPlayerList = new ArrayList<>(initialPlayerList);

        //apply player team filter
        if (!selectedTeam.equals("All Club")) {
            filteredPlayerList = filteredPlayerList.stream().filter(playersData -> playersData.getTeam_name_full().equalsIgnoreCase(selectedTeam)).collect(Collectors.toList());
        }

        //apply player team filter
        if (!selectedPlayerType.equals("All Player")) {
            filteredPlayerList = filteredPlayerList.stream().filter(playersData -> playersData.getElement_type_full().equalsIgnoreCase(selectedPlayerType)).collect(Collectors.toList());
        }

        updateRangeSlider();

        //apply search filter
        if (playerSearchText != null || (playerSearchText != null && !playerSearchText.isEmpty()))
            filteredPlayerList = filteredPlayerList.stream().filter(playersData -> playersData.getWeb_name().toLowerCase().contains(playerSearchText.toLowerCase())).collect(Collectors.toList());

        sortFilteredData(sortingCriteria);

        adapter.updatePlayersList(filteredPlayerList);
    }

    private void sortAndUpdatePlayer() {

        String sortingCriteria = binding.playerCriterion.getSelectedItem().toString(); // get selected sorting criterion

        sortFilteredData(sortingCriteria);

        adapter.updatePlayersList(filteredPlayerList);
    }

    public void sortFilteredData(String item) {
        switch (item) {
            case "Total Points" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTotal_points).reversed());
            case "Round Points" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getEvent_points).reversed());
            case "Team Selected By" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getSelected_by_percent).reversed());
            case "Minutes Played" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getMinutes).reversed());
            case "Goal Scored" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getGoals_scored).reversed());
            case "Assist" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getAssists).reversed());
            case "Clean Sheet" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getClean_sheets).reversed());
            case "Goal Conceded" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getGoals_conceded));
            case "Own Goals" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getOwn_goals));
            case "Penalty Saved" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getPenalties_saved).reversed());
            case "Penalty Missed" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getPenalties_missed));
            case "Yellow Cards" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getYellow_cards));
            case "Red Cards" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getRed_cards));
            case "Saves" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getSaves).reversed());
            case "Bonus" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getBonus).reversed());
            case "Bonus Points System" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getBps).reversed());
            case "Influence" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getInfluence).reversed());
            case "Creativity" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCreativity).reversed());
            case "Threat" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getThreat).reversed());
            case "ICT Index" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getIct_index).reversed());
            case "Games Started" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getStarts).reversed());
            case "Form" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getForm).reversed());
            case "Times in Team of the Week" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getDreamteam_count).reversed());
            case "Value(form)" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getValue_form).reversed());
            case "Value(season)" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getValue_season).reversed());
            case "Points Per Match" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getPoints_per_game).reversed());
            case "Transfers In" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_in).reversed());
            case "Transfers Out" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_out).reversed());
            case "Transfers In(round)" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_in_event).reversed());
            case "Transfers Out(round)" ->
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_out_event).reversed());
            case "Net Transfers In(round)" ->
                    filteredPlayerList.sort(Comparator.comparingLong((PlayersData player) ->
                            player.getTransfers_in_event() - player.getTransfers_out_event()).reversed());
            case "Net Transfers Out(round)" ->
                    filteredPlayerList.sort(Comparator.comparingLong((PlayersData player) ->
                            player.getTransfers_out_event() - player.getTransfers_in_event()).reversed());
            case "Price Rise" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_start).reversed());
            case "Price Fall" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_start));
            case "Price Rise(round)" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_event).reversed());
            case "Price Fall(round)" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_event));
            case "Expected Goals(xG)" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goals).reversed());
            case "Expected Assists(xA)" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_assists).reversed());
            case "Expected Goals Involvement(xGI)" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goal_involvements).reversed());
            case "Expected Goal Conceded(xGC)" ->
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goals_conceded));
            default -> Log.d(Constants.LOG_TAG, "No item selected or unhandled item: " + item);
        }
    }
}
