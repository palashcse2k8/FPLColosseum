package com.infotech.fplcolosseum.features.player_search.views;

import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.CURRENT_BALANCE;
import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.CURRENT_TEAM_PLAYERS;
import static com.infotech.fplcolosseum.features.homepage.views.TransferFragment.TRANSFERRED_PLAYER_DATA;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.FragmentPlayerSelectionBinding;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.TeamData;
import com.infotech.fplcolosseum.features.player_search.adapter.PlayerListAdapter;
import com.infotech.fplcolosseum.features.player_search.viewmodel.PlayerSelectionViewModel;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.PlayerSortingCriterion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;

public class PlayerSelectionActivity extends AppCompatActivity {
    FragmentPlayerSelectionBinding binding;
    private PlayerListAdapter adapter;
    private List<PlayersData> playersList;
    private List<PlayersData> filteredPlayerList;
    private PlayersData transferredPlayerData;
    private ArrayList<PlayersData> teamPlayers;

    public static String SELECTED_PLAYER_DATA = "selected_player_data";
    public static String BOTTOM_SHEET_REQUEST_KEY = "requestKey";

    public static String playerSearchText = null;
    public long availableBalance;

    ArrayList<String> playerTypes = new ArrayList<>();
    ArrayList<String> playerTeams = new ArrayList<>();
    String[] playerCriterionItems;

    boolean isPriceAscending = false;
    boolean isCriterionAscending = false;

    String sortingCriteria;

    PlayerSelectionViewModel viewModel;
    SearchView searchView;
    MenuItem searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentPlayerSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fetchPlayersFromApi();

        Intent intent = getIntent();
        if (intent != null) {
            transferredPlayerData = (PlayersData) intent.getSerializableExtra(TRANSFERRED_PLAYER_DATA);
            teamPlayers = (ArrayList<PlayersData>) intent.getSerializableExtra(CURRENT_TEAM_PLAYERS);
            availableBalance = intent.getLongExtra(CURRENT_BALANCE, 0);
        }

        setResultListener();
        setupToolbar();
        setupRecyclerView();
        setUpDropdowns();
        setUpRangeSlider();
        updateInitialAdapterData(transferredPlayerData.getElement_type_full());
//        updateRangeSlider();
        updateTextViewProperty();

        String availableBalanceText = "Available Balance : " + CustomUtil.convertedPrice(availableBalance);
        binding.tvBalance.setText(availableBalanceText);
        viewModel = new ViewModelProvider(this).get(PlayerSelectionViewModel.class);

    }

    public void setResultListener() {
        getSupportFragmentManager().setFragmentResultListener(BOTTOM_SHEET_REQUEST_KEY, this, (requestKey, bundle) -> {
            if (BOTTOM_SHEET_REQUEST_KEY.equals(requestKey)) {
                PlayersData selectedPlayer = (PlayersData) bundle.getSerializable(SELECTED_PLAYER_DATA);

                if (selectedPlayer != null && selectedPlayer.getElement_type() != transferredPlayerData.getElement_type()) { // Check if correct player type is selected
                    Toasty.warning(this, "Please Select a " + transferredPlayerData.getElement_type_full()).show();
                    return;
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra(TRANSFERRED_PLAYER_DATA, transferredPlayerData);
                resultIntent.putExtra(SELECTED_PLAYER_DATA, selectedPlayer);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = binding.toolbarSearch;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Players");

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecyclerView() {
        binding.playerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.playerRecyclerView.setHasFixedSize(true);
        adapter = new PlayerListAdapter(playersList, this::onPlayerSelected, transferredPlayerData, teamPlayers, this);
        binding.playerRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_player_search, menu);

        searchItem = menu.findItem(R.id.action_search);
        searchItem.setIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        searchView = (SearchView) searchItem.getActionView();
//        searchView.setIconified(false);

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
            filterSortAndUpdatePlayer(playersList);
            return false;
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchPlayersFromApi() {
        // Simulating API call
        playersList = new ArrayList<>(Constants.playerMap.values());
    }

    private void onPlayerSelected(PlayersData player) {
        if (player.getElement_type() != transferredPlayerData.getElement_type()) {
            Toasty.warning(this, "Please Select a " + transferredPlayerData.getElement_type_full()).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra(SELECTED_PLAYER_DATA, player);
        resultIntent.putExtra(TRANSFERRED_PLAYER_DATA, transferredPlayerData);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    // ... [Rest of the methods remain mostly the same, just change 'requireContext()' to 'this'
    //     and 'requireActivity()' to 'this' where applicable]

    private void setUpRangeSlider() {
        binding.rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                float minPrice = binding.rangeSlider.getValues().get(0) * 10;
                float maxPrice = binding.rangeSlider.getValues().get(1) * 10;
                filteredPlayerList = playersList.stream()
                        .filter(playersData -> playersData.getNow_cost() >= minPrice && playersData.getNow_cost() <= maxPrice)
                        .collect(Collectors.toList());

                filterSortAndUpdatePlayer(filteredPlayerList);
            }
        });

        binding.rangeSlider.setLabelFormatter(new LabelFormatter() {
            @SuppressLint("DefaultLocale")
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return String.format("€%.1f", value);
            }
        });
    }

    private void updateTextViewProperty() {
        // Initialize the drawable programmatically
        Drawable dscIcon = ContextCompat.getDrawable(this, R.drawable.ic_sort_dsc);

        // Make sure the drawable is not null and set its tint color
        if (dscIcon != null) {
            dscIcon.setTint(ContextCompat.getColor(this, android.R.color.white));  // Set tint color
            binding.recyclerHeader.tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, dscIcon, null);  // Set the drawable at the end (right)
            binding.recyclerHeader.playerCriterion.setCompoundDrawablesWithIntrinsicBounds(null, null, dscIcon, null);  // Set the drawable at the end (right)
        }

        binding.recyclerHeader.tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isPriceAscending) {
                    // Perform ascending sort on your list
                    filteredPlayerList.sort(Comparator.comparing(PlayersData::getNow_cost));  // Ascending sort
                    Drawable icon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_sort_asc);
                    binding.recyclerHeader.tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);  // Setting drawableEnd

                } else {
                    // Perform descending sort on your list
                    filteredPlayerList.sort(Comparator.comparing(PlayersData::getNow_cost).reversed());  // Descending sort
                    Drawable icon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_sort_dsc);
                    binding.recyclerHeader.tvPrice.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);  // Setting drawableEnd
                }

                // Update your adapter with the sorted list
                adapter.updatePlayersList(filteredPlayerList, sortingCriteria);

                // Toggle the flag for the next click
                isPriceAscending = !isPriceAscending;
            }
        });

        binding.recyclerHeader.playerCriterion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isCriterionAscending) {
                    Drawable icon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_sort_dsc);
                    binding.recyclerHeader.playerCriterion.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);  // Setting drawableEnd

                } else {
                    Drawable icon = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_sort_asc);
                    binding.recyclerHeader.playerCriterion.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);  // Setting drawableEnd
                }
                // Toggle the flag for the next click
                isCriterionAscending = !isCriterionAscending;

                sortFilteredData(sortingCriteria); // sort the data accordingly

                // Update your adapter with the sorted list
                adapter.updatePlayersList(filteredPlayerList, sortingCriteria);

            }
        });
    }

    private void setUpDropdowns() {
        // Adapter for dropdowns
        playerTypes.add("All Player");
        ArrayList<String> playerTypes = (ArrayList<String>) Constants.playerTypeMap.values()
                .stream()
                .map(Player_Type::getSingular_name)
                .collect(Collectors.toList());
        this.playerTypes.addAll(playerTypes);

        ArrayAdapter<String> playerTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, this.playerTypes);
        playerTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.playerTypeDropdown.setAdapter(playerTypeAdapter);
        binding.playerTypeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterSortAndUpdatePlayer(playersList);
                updateRangeSlider();
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
        ArrayAdapter<String> playerTeamsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, playerTeams);
        playerTeamsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.playerTeamDropdown.setAdapter(playerTeamsAdapter);
        binding.playerTeamDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterSortAndUpdatePlayer(playersList);
                updateRangeSlider();
//                updateRangeSlider();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Adapter for dropdowns
        playerCriterionItems = Arrays.stream(PlayerSortingCriterion.values())
                .map(PlayerSortingCriterion::getDisplayName)
                .toArray(String[]::new);
        ArrayAdapter<String> playerCriterionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, playerCriterionItems);
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

    private void updateInitialAdapterData(String playerType) {
        int position = playerTypes.indexOf(playerType);
        binding.playerTypeDropdown.setSelection(position);
    }

    private void updateRangeSlider() {
        Optional<PlayersData> minPlayer = filteredPlayerList.stream()
                .min(Comparator.comparing(PlayersData::getNow_cost));

        float minPrice = minPlayer.map(data -> (float) data.getNow_cost() / 10).orElse(0f);

        Optional<PlayersData> maxPlayer = filteredPlayerList.stream()
                .max(Comparator.comparing(PlayersData::getNow_cost));

        float maxPrice = maxPlayer.map(playersData -> (float) playersData.getNow_cost() / 10).orElse(0f);

        if (minPrice >= maxPrice) return; // validate from and to data before set

        binding.rangeSlider.setValueFrom(minPrice);
        binding.rangeSlider.setValueTo(maxPrice);
        binding.rangeSlider.setValues(minPrice, maxPrice);
        binding.tvMin.setText(CustomUtil.convertedPrice((long) (minPrice * 10)));
        binding.tvMax.setText(CustomUtil.convertedPrice((long) (maxPrice * 10)));

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

        //apply search filter
        if (playerSearchText != null)
            filteredPlayerList = filteredPlayerList.stream().filter(playersData -> playersData.getWeb_name().toLowerCase().contains(playerSearchText.toLowerCase())).collect(Collectors.toList());

        sortFilteredData(sortingCriteria);

        adapter.updatePlayersList(filteredPlayerList, sortingCriteria);
    }

    public void sortFilteredData(String item) {
        sortingCriteria = item;
        // Find the corresponding PlayerSortingCriterion enum
        PlayerSortingCriterion selectedCriterion = Arrays.stream(PlayerSortingCriterion.values())
                .filter(criterion -> criterion.getDisplayName().equals(item))
                .findFirst()
                .orElse(null);

        if (selectedCriterion == null) {
            Log.d(Constants.LOG_TAG, "Invalid sorting criterion: " + item);
            return;
        }

        switch (selectedCriterion) {
            case TOTAL_POINTS -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTotal_points).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTotal_points));
            }
            case ROUND_POINTS -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getEvent_points).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getEvent_points));
            }
            case TEAM_SELECTED_BY -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getSelected_by_percent).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getSelected_by_percent));
            }
            case MINUTES_PLAYED -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getMinutes).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getMinutes));
            }
            case GOALS_SCORED -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getGoals_scored).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getGoals_scored));
            }
            case ASSIST -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getAssists).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getAssists));
            }
            case CLEAN_SHEET -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getClean_sheets).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getClean_sheets));
            }
            case GOALS_CONCEDED -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getGoals_conceded).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getGoals_conceded));
            }
            case OWN_GOALS -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getOwn_goals).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getOwn_goals));
            }
            case PENALTY_SAVED -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getPenalties_saved).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getPenalties_saved));
            }
            case PENALTY_MISSED -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getPenalties_missed).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getPenalties_missed));
            }
            case YELLOW_CARDS -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getYellow_cards).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getYellow_cards));
            }
            case RED_CARDS -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getRed_cards).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getRed_cards));
            }
            case SAVES -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getSaves).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getSaves));
            }
            case BONUS -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getBonus).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getBonus));
            }
            case BONUS_POINTS_SYSTEM -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getBps).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getBps));
            }
            case INFLUENCE -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getInfluence).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getInfluence));
            }
            case CREATIVITY -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCreativity).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCreativity));
            }
            case THREAT -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getThreat).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getThreat));
            }
            case ICT_INDEX -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getIct_index).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getIct_index));
            }
            case GAMES_STARTED -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getStarts).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getStarts));
            }
            case FORM -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getForm).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getForm));
            }
            case TIMES_IN_TEAM_OF_THE_WEEK -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getDreamteam_count).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getDreamteam_count));
            }
            case VALUE_FORM -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getValue_form).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getValue_form));
            }
            case VALUE_SEASON -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getValue_season).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getValue_season));
            }
            case POINTS_PER_MATCH -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getPoints_per_game).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getPoints_per_game));
            }
            case TRANSFERS_IN -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_in).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_in));
            }
            case TRANSFERS_OUT -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_out).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_out));
            }
            case TRANSFERS_IN_ROUND -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_in_event).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_in_event));
            }
            case TRANSFERS_OUT_ROUND -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_out_event).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong(PlayersData::getTransfers_out_event));
            }
            case NET_TRANSFERS_IN_ROUND -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong((PlayersData player) ->
                            player.getTransfers_in_event() - player.getTransfers_out_event()).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong((PlayersData player) ->
                            player.getTransfers_in_event() - player.getTransfers_out_event()));
            }
            case NET_TRANSFERS_OUT_ROUND -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingLong((PlayersData player) ->
                            player.getTransfers_out_event() - player.getTransfers_in_event()).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingLong((PlayersData player) ->
                            player.getTransfers_out_event() - player.getTransfers_in_event()));
            }
            case PRICE -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getNow_cost).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getNow_cost));
            }
            case PRICE_RISE -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_start).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_start));
            }
            case PRICE_FALL -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_start).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_start));
            }
            case PRICE_RISE_ROUND -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_event).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_event));
            }
            case PRICE_FALL_ROUND -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_event).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getCost_change_event));
            }
            case EXPECTED_GOALS -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goals).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goals));
            }
            case EXPECTED_ASSISTS -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_assists).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_assists));
            }
            case EXPECTED_GOALS_INVOLVEMENT -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goal_involvements).reversed());
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goal_involvements));
            }
            case EXPECTED_GOALS_CONCEDED -> {
                binding.recyclerHeader.playerCriterion.setText(selectedCriterion.getShortName());
                if (!isCriterionAscending)
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goals_conceded));
                else
                    filteredPlayerList.sort(Comparator.comparingDouble(PlayersData::getExpected_goals_conceded).reversed());
            }
            default -> Log.d(Constants.LOG_TAG, "No item selected or unhandled item: " + item);
        }
    }

    private void sortAndUpdatePlayer() {

        String sortingCriteria = binding.playerCriterion.getSelectedItem().toString(); // get selected sorting criterion

        sortFilteredData(sortingCriteria);

        adapter.updatePlayersList(filteredPlayerList, sortingCriteria);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("searchQuery", playerSearchText);
        outState.putString("playerTeamFilter", binding.playerTeamDropdown.getSelectedItem().toString());
        outState.putString("playerTypeFilter", binding.playerTypeDropdown.getSelectedItem().toString());
        outState.putString("sortingCriterion", binding.playerCriterion.getSelectedItem().toString());
    }

    public void restoreUiState(Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "restoreUiState Called");

        playerSearchText = savedInstanceState.getString("searchQuery");

        binding.playerTeamDropdown.setSelection(playerTeams.indexOf(savedInstanceState.getString("playerTeamFilter")));

        binding.playerTypeDropdown.setSelection(playerTypes.indexOf(savedInstanceState.getString("playerTypeFilter")));

        String sortingCriterion = savedInstanceState.getString("sortingCriterion");

        binding.playerCriterion.setSelection(getItemPosition(playerCriterionItems, sortingCriterion));

        filterSortAndUpdatePlayer(filteredPlayerList);
    }

    public int getItemPosition(String[] strings, String item) {
        for (int i = 0; i < strings.length; i++) {
            if (item.equalsIgnoreCase(playerCriterionItems[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getSearchQuery().observe(this, s -> {
            if (searchView != null && s!= null && !s.isEmpty()) {
                searchItem.expandActionView();
                searchView.setQuery(s, false);  // Restore the query
                searchView.clearFocus();
            }
            filterSortAndUpdatePlayer(playersList);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setSearchQuery(playerSearchText);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
