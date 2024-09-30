package com.infotech.fplcolosseum.features.homepage.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.views.PlayerSelectionBottomSheetFragment;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> implements Filterable {

    private List<PlayersData> playersList;
    private List<PlayersData> filteredList;
    private final OnPlayerClickListener onPlayerClickListener;
    private final FragmentActivity activity;
    String selectedSortingCriterion = null;
    String selectedPlayerType = null;
    String selectedTeamType = null;


    public PlayerListAdapter(List<PlayersData> playersList, OnPlayerClickListener listener, FragmentActivity activity) {
        this.playersList = playersList;
        this.activity = activity;

        if (this.playersList != null) {
            this.filteredList = new ArrayList<>(playersList);
        }
        sortPlayersByPoints();
        this.onPlayerClickListener = listener;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_selection_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        PlayersData player = filteredList.get(position);
        holder.bind(player);
        holder.itemView.setOnClickListener(v -> {
            // Open the bottom sheet
            PlayerSelectionBottomSheetFragment bottomSheet = PlayerSelectionBottomSheetFragment.newInstance(player);
            bottomSheet.show(activity.getSupportFragmentManager(), "PlayerBottomSheet");
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                List<PlayersData> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = new ArrayList<>(filteredList); // Return the original list if the query is empty
                } else {
                    for (PlayersData item : filteredList) {
                        if (item.getWeb_name().toLowerCase().contains(query)) {
                            filtered.add(item); // Add player to the filtered list if the name contains the query
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<PlayersData>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void updatePlayersList(List<PlayersData> newPlayersList) {
        this.playersList = newPlayersList;
        this.filteredList = new ArrayList<>(playersList);

        if (selectedPlayerType == null) {
            filterPlayersByType("All Player");
        } else {
            filterPlayersByType(selectedSortingCriterion);
        }
        if (selectedSortingCriterion == null) {
            sortPlayersByPoints();
        } else {
            sortAdapterData(selectedSortingCriterion);
        }

        // Sort players by total points after updating the list
        notifyDataSetChanged();
    }

    public void filterPlayersByType(String selectedType) {
        selectedPlayerType = selectedType;
        if (playersList == null || selectedType.equals("All Player")) {
            // No filtering needed, return the entire list
            updatePlayersList(new ArrayList<>(Constants.playerMap.values()));
        } else {
            List<PlayersData> filteredList = new ArrayList<>();
            for (PlayersData player : playersList) {
                if (selectedType.equals(player.getElement_type_full())) {
                    filteredList.add(player);
                }
            }

            updatePlayersList(filteredList);
        }
    }

    public void filterPlayersByTeam(String selectedType) {

        selectedTeamType = selectedType;
        if (playersList == null || selectedType.equals("All Club")) {
            // No filtering needed, return the entire list
            updatePlayersList(new ArrayList<>(Constants.playerMap.values()));
        } else {
            List<PlayersData> filteredList = new ArrayList<>();
            for (PlayersData player : playersList) {
                if (selectedType.equals(player.getTeam_name_full())) {
                    filteredList.add(player);
                }
            }

            updatePlayersList(filteredList);
        }
    }

    public void sortAdapterData(String item) {
        switch (item) {
            case "Total Points" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getTotal_points).reversed(), item);
            case "Round Points" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getEvent_points).reversed(), item);
            case "Team Selected By" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getSelected_by_percent).reversed(), item);
            case "Minutes Played" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getMinutes).reversed(), item);
            case "Goal Scored" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getGoals_scored).reversed(), item);
            case "Assist" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getAssists).reversed(), item);
            case "Clean Sheet" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getClean_sheets).reversed(), item);
            case "Goal Conceded" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getGoals_conceded), item);
            case "Own Goals" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getOwn_goals), item);
            case "Penalty Saved" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getPenalties_saved).reversed(), item);
            case "Penalty Missed" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getPenalties_missed), item);
            case "Yellow Cards" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getYellow_cards), item);
            case "Red Cards" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getRed_cards), item);
            case "Saves" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getSaves).reversed(), item);
            case "Bonus" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getBonus).reversed(), item);
            case "Bonus Points System" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getBps).reversed(), item);
            case "Influence" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getInfluence).reversed(), item);
            case "Creativity" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getCreativity).reversed(), item);
            case "Threat" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getThreat).reversed(), item);
            case "ICT Index" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getIct_index).reversed(), item);
            case "Games Started" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getStarts).reversed(), item);
            case "Form" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getForm).reversed(), item);
            case "Times in Team of the Week" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getDreamteam_count).reversed(), item);
            case "Value(form)" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getValue_form).reversed(), item);
            case "Value(season)" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getValue_season).reversed(), item);
            case "Points Per Match" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getPoints_per_game).reversed(), item);
            case "Transfers In" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getTransfers_in).reversed(), item);
            case "Transfers Out" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getTransfers_out).reversed(), item);
            case "Transfers In(round)" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getTransfers_in_event).reversed(), item);
            case "Transfers Out(round)" ->
                    sortPlayers(Comparator.comparingLong(PlayersData::getTransfers_out_event).reversed(), item);
            case "Net Transfers In(round)" ->
                    sortPlayers(Comparator.comparingLong((PlayersData player) ->
                            player.getTransfers_in_event() - player.getTransfers_out_event()).reversed(), item);
            case "Net Transfers Out(round)" ->
                    sortPlayers(Comparator.comparingLong((PlayersData player) ->
                            player.getTransfers_out_event() - player.getTransfers_in_event()).reversed(), item);
            case "Price Rise" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getCost_change_start).reversed(), item);
            case "Price Fall" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getCost_change_start), item);
            case "Price Rise(round)" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getCost_change_event).reversed(), item);
            case "Price Fall(round)" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getCost_change_event), item);
            case "Expected Goals(xG)" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getExpected_goals).reversed(), item);
            case "Expected Assists(xA)" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getExpected_assists).reversed(), item);
            case "Expected Goals Involvement(xGI)" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getExpected_goal_involvements).reversed(), item);
            case "Expected Goal Conceded(xGC)" ->
                    sortPlayers(Comparator.comparingDouble(PlayersData::getExpected_goals_conceded), item);
            default -> Log.d(Constants.LOG_TAG, "No item selected or unhandled item: " + item);
        }
    }

    public void sortPlayers(Comparator<PlayersData> comparator, String sortingItem) {
        selectedSortingCriterion = sortingItem;
        ;
        if (filteredList == null || filteredList.isEmpty()) {
            return;  // No need to sort an empty or null list
        }

        // Create a new list to avoid modifying the original data source
        List<PlayersData> sortedList = new ArrayList<>(filteredList);
        sortedList.sort(comparator);

        // Clear and add all to ensure the order is updated
        filteredList.clear();
        filteredList.addAll(sortedList);

        // Notify of potentially large changes
        notifyDataSetChanged();
    }

    private void sortPlayersByPoints() {
        if (filteredList != null) {
            filteredList.sort((p1, p2) -> Long.compare(p2.getTotal_points(), p1.getTotal_points()));
        }
    }


    class PlayerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView playerImage;
        private final TextView playerName;
        private final TextView playerTypeAndTeam;
        private final TextView playerTotalPoints;
        private final TextView playerPrice;
        private TextView playerSelectedBy;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerImage = itemView.findViewById(R.id.player_image);
            playerName = itemView.findViewById(R.id.player_name);
            playerTypeAndTeam = itemView.findViewById(R.id.player_position);
            playerTotalPoints = itemView.findViewById(R.id.player_tp);
            playerPrice = itemView.findViewById(R.id.player_price);

            itemView.setOnClickListener(v -> {
                if (onPlayerClickListener != null) {
                    onPlayerClickListener.onPlayerClick(playersList.get(getAdapterPosition()));
                }
            });


        }

        public void bind(PlayersData player) {
            String imageUrl = "https://resources.premierleague.com/premierleague/photos/players/110x140/p" + player.getCode() + ".png";

            // Placeholder animation: Fade in the image after it has loaded
            AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
            fadeInAnimation.setDuration(1000); // 1 second fade-in duration

            Picasso.get()
                    .load(imageUrl)
                    .error(R.mipmap.no_image)
                    .into(playerImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            // Start the fade-in animation when the image is successfully loaded
                            playerImage.startAnimation(fadeInAnimation);
                        }

                        @Override
                        public void onError(Exception e) {
//                            Log.e("Image Load Error", "Failed to load image: " + e.getMessage());
                        }
                    });

            playerName.setText(player.getWeb_name());
            String teamName = Objects.requireNonNull(Constants.teamMap.get(player.getTeam())).getShort_name();
            String playerType = Objects.requireNonNull(Constants.playerTypeMap.get(player.getElement_type())).getSingular_name_short();
            String playerTypeAndTeamString = playerType + " | " + teamName;
            playerTypeAndTeam.setText(playerTypeAndTeamString);

            playerTotalPoints.setText(String.valueOf(player.getTotal_points()));
            playerPrice.setText(CustomUtil.convertedPrice(player.getNow_cost()));
        }
    }

    public interface OnPlayerClickListener {
        void onPlayerClick(PlayersData player);
    }
}
