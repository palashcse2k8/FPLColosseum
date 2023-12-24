package com.infotech.fplcolosseum.features.homepage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.gameweek.models.custom.PlayerDataModel;
import com.infotech.fplcolosseum.features.login.models.Player;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<PlayerDataModel> players;

    public PlayerAdapter(List<PlayerDataModel> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_template, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.bind(players.get(position));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    static class PlayerViewHolder extends RecyclerView.ViewHolder {

        private final TextView playerNameTextView;
        private final TextView teamNameTextView;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            playerNameTextView = itemView.findViewById(R.id.playerNameTextView);
            teamNameTextView = itemView.findViewById(R.id.teamNameTV);
        }

        public void bind(PlayerDataModel player) {
            playerNameTextView.setText(player.getPlayerName());
            teamNameTextView.setText(player.getTeamName());
        }
    }

    public void moveItem(int fromPosition, int toPosition) {
        PlayerDataModel movedItem = players.remove(fromPosition);
        players.add(toPosition, movedItem);
        notifyItemMoved(fromPosition, toPosition);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<PlayerDataModel> newData) {
        players.clear();
        players.addAll(newData);
        notifyDataSetChanged();
    }
}
