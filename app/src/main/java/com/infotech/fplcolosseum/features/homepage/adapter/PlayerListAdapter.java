package com.infotech.fplcolosseum.features.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.PlayerViewHolder> {

    private List<PlayersData> playersList;
    private OnPlayerClickListener onPlayerClickListener;

    public PlayerListAdapter(List<PlayersData> playersList, OnPlayerClickListener listener) {
        this.playersList = playersList;
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
        PlayersData player = playersList.get(position);
        holder.bind(player);
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    public void updatePlayersList(List<PlayersData> newPlayersList) {
        this.playersList = newPlayersList;
        notifyDataSetChanged();
    }

    public void sortPlayers(Comparator<PlayersData> comparator) {
        Collections.sort(playersList, comparator);
        notifyDataSetChanged();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {

        private ImageView playerImage;
        private TextView playerName;
        private TextView playerPosition;
        private TextView playerTotalPoints;
        private TextView playerPrice;
        private TextView playerSelectedBy;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerImage = itemView.findViewById(R.id.player_image);
            playerName = itemView.findViewById(R.id.player_name);
            playerPosition = itemView.findViewById(R.id.player_position);
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
//            playerPosition.setText(player.getPosition());
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
