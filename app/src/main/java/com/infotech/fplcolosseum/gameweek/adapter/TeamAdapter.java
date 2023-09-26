package com.infotech.fplcolosseum.gameweek.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.RowLayoutNewBinding;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {
    private List<ManagerModel> teams;

    public TeamAdapter(List<ManagerModel> teams) {
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutNewBinding binding = RowLayoutNewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.row_layout_new, parent, false);
        return new TeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        ManagerModel team = teams.get(position);
        holder.binding.teamPosition.setText(String.valueOf(position+1));
        holder.binding.teamName.setText(team.getTeamName());
        holder.binding.managerName.setText(team.getManagerName());
        holder.binding.gameWeekPoint.setText(String.valueOf(team.getGameWeekPoints()));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }


    public static class TeamViewHolder extends RecyclerView.ViewHolder {
        RowLayoutNewBinding binding;
        public TeamViewHolder(RowLayoutNewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
