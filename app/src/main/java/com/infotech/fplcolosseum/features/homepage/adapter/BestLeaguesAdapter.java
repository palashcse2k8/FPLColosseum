package com.infotech.fplcolosseum.features.homepage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.status.BestLeagueDataModel;
import com.infotech.fplcolosseum.features.homepage.models.status.ValuableTeamDataModel;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;

public class BestLeaguesAdapter extends RecyclerView.Adapter<BestLeaguesAdapter.BestLeaguesHolder> {
    private List<BestLeagueDataModel> itemList;

    public BestLeaguesAdapter(List<BestLeagueDataModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public BestLeaguesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_best_league_item, parent, false);
        return new BestLeaguesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestLeaguesHolder holder, int position) {
        holder.rank.setText(String.valueOf(position + 1));
        holder.name.setText(itemList.get(position).getName());
        holder.value.setText(String.valueOf(itemList.get(position).getAverage_score()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<BestLeagueDataModel> newList) {
        this.itemList = newList;
        notifyDataSetChanged();
    }

    public static class BestLeaguesHolder extends RecyclerView.ViewHolder {
        TextView rank;
        TextView name;
        TextView value;

        public BestLeaguesHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            name = itemView.findViewById(R.id.name);
            value = itemView.findViewById(R.id.value);
        }
    }
}
