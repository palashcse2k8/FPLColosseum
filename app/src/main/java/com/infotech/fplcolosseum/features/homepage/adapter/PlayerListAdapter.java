package com.infotech.fplcolosseum.features.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.views.PlayerSelectionBottomSheetFragment;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> implements Filterable {

    private List<PlayersData> playersList;
    private List<PlayersData> filteredList;
    private final OnPlayerClickListener onPlayerClickListener;
    private final FragmentActivity activity;
    List<PlayersData> currentTeamPlayers;
    PlayersData transferredPlayer;


    public PlayerListAdapter(List<PlayersData> playersList, OnPlayerClickListener listener, PlayersData transferredPlayer, List<PlayersData> currentTeamPlayers, FragmentActivity activity) {
        this.playersList = playersList;
        this.activity = activity;
        this.transferredPlayer = transferredPlayer;
        this.currentTeamPlayers = currentTeamPlayers;

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

        // Disable or enable item interaction based on whether the player is in the current team
        if (isPlayerInList(currentTeamPlayers, player.getId())) {
            holder.itemView.setEnabled(false);  // Disable interaction
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.switch_track_off));
        } else {
            holder.itemView.setEnabled(true);   // Enable interaction
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            if (onPlayerClickListener != null && holder.itemView.isEnabled()) {
                onPlayerClickListener.onPlayerClick(player);
                // Open the bottom sheet
                PlayerSelectionBottomSheetFragment bottomSheet = PlayerSelectionBottomSheetFragment.newInstance(player);
                bottomSheet.show(activity.getSupportFragmentManager(), "PlayerBottomSheet");
            }
        });

    }

    public boolean isPlayerInList(List<PlayersData> playerList, long playerId) {
        if(playerList == null) return false;
        return playerList.stream().anyMatch(player -> player.getId() == playerId);
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
        private final LinearLayout playerSelectionItemView;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerSelectionItemView = itemView.findViewById(R.id.playerSelectionItemView);
            playerImage = itemView.findViewById(R.id.player_image);
            playerName = itemView.findViewById(R.id.player_name);
            playerTypeAndTeam = itemView.findViewById(R.id.player_position);
            playerTotalPoints = itemView.findViewById(R.id.player_tp);
            playerPrice = itemView.findViewById(R.id.player_price);


//            itemView.setOnClickListener(v -> {
//
//                if (onPlayerClickListener != null && !isPlayerInList(currentTeamPlayers, playersList.get(getAdapterPosition()).getId())) {
//                    onPlayerClickListener.onPlayerClick(playersList.get(getAdapterPosition()));
//                }
//            });

//            if (isPlayerInList(currentTeamPlayers, player.getId())) {
//                itemView.setEnabled(false);  // Disable interaction
//            } else {
//                itemView.setEnabled(true);   // Enable interaction
//            }

        }

        public void bind(PlayersData player) {
            String imageUrl = "https://resources.premierleague.com/premierleague/photos/players/110x140/p" + player.getCode() + ".png";

            if(isPlayerInList(currentTeamPlayers, player.getId())){
                playerSelectionItemView.setBackgroundColor(ContextCompat.getColor(activity, R.color.switch_track_off));
            } else {
                playerSelectionItemView.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
            }

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
