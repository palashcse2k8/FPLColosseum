package com.infotech.fplcolosseum.features.player_information.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.player_information.models.Fixtures;
import com.infotech.fplcolosseum.features.player_information.models.History;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;
import java.util.Objects;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder> {

    private List<Fixtures> matchList;

    public FixtureAdapter(List<Fixtures> matchList) {
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gameweek_fixture_list_item, parent, false);
        return new FixtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FixtureViewHolder holder, int position) {
        Fixtures matchItem = matchList.get(position);
        String gameWeek = (int) matchItem.getEvent() + "";
        holder.tvNumber.setText(gameWeek);

        long opponentTeamId = (matchItem.getIs_home() ? matchItem.getTeam_a() : matchItem.getTeam_h());
        String teamName = Objects.requireNonNull(Constants.teamMap.get(opponentTeamId)).getShort_name() + " " + (matchItem.getIs_home() ? "(H)" : "(A)");
        holder.tvOpponent.setText(teamName);

        holder.tvDate.setText(CustomUtil.convertUtcToLocalTime(matchItem.getKickoff_time()));

        String difficulty = (int) matchItem.getDifficulty() + "";
        holder.tvFDR.setText(difficulty);
        holder.tvFDR.setTextColor(CustomUtil.getDifficultyLeveTextColor(matchItem.getDifficulty()));
        holder.tvFDR.setBackgroundColor(CustomUtil.getDifficultyLevelColor(matchItem.getDifficulty()));
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    static class FixtureViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvOpponent, tvDate, tvFDR;

        public FixtureViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvOpponent = itemView.findViewById(R.id.tvOpponent);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvFDR = itemView.findViewById(R.id.tvFDR);
        }
    }
}
