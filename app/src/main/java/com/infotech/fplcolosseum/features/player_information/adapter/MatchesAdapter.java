package com.infotech.fplcolosseum.features.player_information.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.player_information.models.History;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.List;
import java.util.Objects;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchViewHolder> {

    private List<History> matchList;

    public MatchesAdapter(List<History> matchList) {
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gameweek_result_list_item, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        History matchItem = matchList.get(position);
        String gameWeek = matchItem.getRound() + "";
        holder.tvNumber.setText(gameWeek);

        String teamName = Objects.requireNonNull(Constants.teamMap.get(matchItem.getOpponent_team())).getShort_name() + " " + (matchItem.getWas_home() ? "(H)" : "(A)");
        holder.tvOpponent.setText(teamName);
        String score = matchItem.getTeam_h_score() + " - " + matchItem.getTeam_a_score();
        String result;
        if(matchItem.getTeam_h_score() == matchItem.getTeam_a_score())
        {
            result = " (D)";
        } else if (matchItem.getWas_home() && matchItem.getTeam_h_score() > matchItem.getTeam_a_score()) {
            result = " (W)";
        } else if (!matchItem.getWas_home() && matchItem.getTeam_h_score() < matchItem.getTeam_a_score()) {
            result = " (W)";
        } else {
            result = " (L)";
        }

        score = score + result;

        holder.tvResult.setText(score);
        String points = matchItem.getTotal_points() + " Pts";
        holder.tvPoints.setText(points);
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvOpponent, tvResult, tvPoints;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvOpponent = itemView.findViewById(R.id.tvOpponent);
            tvResult = itemView.findViewById(R.id.tvResult);
            tvPoints = itemView.findViewById(R.id.tvPoints);
        }
    }
}
