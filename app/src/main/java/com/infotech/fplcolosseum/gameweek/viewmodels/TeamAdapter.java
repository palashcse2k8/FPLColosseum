package com.infotech.fplcolosseum.gameweek.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.gameweek.models.LiveData;
import com.infotech.fplcolosseum.gameweek.models.Team;
import com.infotech.fplcolosseum.gameweek.models.TeamDataModel;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<TeamDataModel> teams;

    public TeamAdapter(List<TeamDataModel> teams) {
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        TeamDataModel team = teams.get(position);
        LiveData liveData = team.getLiveData();
        holder.teamNameTextView.setText(team.getName());
        holder.managerNameTextView.setText(team.getPlayerName());
        holder.gameWeekPointsTextView.setText(String.valueOf(liveData.getPhaseTotalPoints()));
        holder.totalPointsTextView.setText(String.valueOf( liveData.getSeasonTotalPoints()));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        TextView teamNameTextView;
        TextView managerNameTextView;
        TextView gameWeekPointsTextView;
        TextView totalPointsTextView;


        public TeamViewHolder(View itemView) {
            super(itemView);
            teamNameTextView = itemView.findViewById(R.id.tvTeamName);
            managerNameTextView = itemView.findViewById(R.id.tvManagerName);
            gameWeekPointsTextView = itemView.findViewById(R.id.tvGameWeekPoints);
            totalPointsTextView = itemView.findViewById(R.id.tvTotalPoints);
        }
    }
}
