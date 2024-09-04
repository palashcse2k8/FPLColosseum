package com.infotech.fplcolosseum.features.homepage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> implements Filterable {

    private List<PlayersData> playersList;
    private List<PlayersData> filteredList;
    private final OnPlayerClickListener onPlayerClickListener;
    private FragmentActivity activity;

    public PlayerListAdapter(List<PlayersData> playersList, OnPlayerClickListener listener, FragmentActivity activity) {
        this.playersList = playersList;
        this.activity = activity;

        if(this.playersList != null) {
            this.filteredList = new ArrayList<>(playersList);
        }
        sortPlayersByPoints();
        this.onPlayerClickListener = listener;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_player, parent, false);
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


    // Optionally implement a method to sort the filtered list
    public void sortData(Comparator<PlayersData> comparator) {
        Collections.sort(filteredList, comparator);
        notifyDataSetChanged();
    }

    public void updatePlayersList(List<PlayersData> newPlayersList) {
        this.playersList = newPlayersList;
        this.filteredList = new ArrayList<>(playersList);
        sortPlayersByPoints(); // Sort players by total points after updating the list
        notifyDataSetChanged();
    }

    public void sortPlayers(Comparator<PlayersData> comparator) {
        Collections.sort(filteredList, comparator);
        notifyDataSetChanged();
    }
    private void sortPlayersByPoints() {
        if(filteredList != null) {
            filteredList.sort((p1, p2) -> Long.compare(p2.getTotal_points(), p1.getTotal_points()));
        }
    }


    class PlayerViewHolder extends RecyclerView.ViewHolder {

        private ImageView playerImage;
        private TextView playerName;
        private TextView playerTypeAndTeam;
        private TextView playerTotalPoints;
        private TextView playerPrice;
        private TextView playerSelectedBy;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerImage = itemView.findViewById(R.id.player_image);
            playerName = itemView.findViewById(R.id.player_name);
            playerTypeAndTeam = itemView.findViewById(R.id.player_position);
            playerTotalPoints = itemView.findViewById(R.id.player_tp);
            playerPrice = itemView.findViewById(R.id.player_price);
            playerSelectedBy = itemView.findViewById(R.id.player_selected_by);

            itemView.setOnClickListener(v -> {
                if (onPlayerClickListener != null) {
                    onPlayerClickListener.onPlayerClick(playersList.get(getAdapterPosition()));
                }
            });


        }

        public void bind(PlayersData player) {
            playerName.setText(player.getWeb_name());
            String teamName = Objects.requireNonNull(Constants.teamMap.get(player.getTeam())).getShort_name();
            String playerType = Objects.requireNonNull(Constants.playerTypeMap.get(player.getElement_type())).getSingular_name_short();
            String playerTypeAndTeamString = playerType + " | " + teamName;
            playerTypeAndTeam.setText(playerTypeAndTeamString);

            playerTotalPoints.setText(String.valueOf(player.getTotal_points()));
            playerPrice.setText(String.valueOf(player.getNow_cost()));
            playerSelectedBy.setText(String.valueOf(player.getSelected_by_percent()));
            // Load player image if available
            // Glide.with(playerImage.getContext()).load(player.getImageUrl()).into(playerImage);
        }
    }

    public interface OnPlayerClickListener {
        void onPlayerClick(PlayersData player);
    }
}
