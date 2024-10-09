package com.infotech.fplcolosseum.features.homepage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.homepage.models.status.ValuableTeamDataModel;
import com.infotech.fplcolosseum.utilities.CustomUtil;

import java.util.List;

public class MostValuableTeamsAdapter extends RecyclerView.Adapter<MostValuableTeamsAdapter.MostValuableTeamsHolder> {
    private List<ValuableTeamDataModel> itemList;

    public MostValuableTeamsAdapter(List<ValuableTeamDataModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MostValuableTeamsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_best_league_item, parent, false);
        return new MostValuableTeamsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostValuableTeamsHolder holder, int position) {
        holder.rank.setText(String.valueOf(position + 1));
        holder.name.setText(itemList.get(position).getName());
        holder.value.setText(CustomUtil.convertedPrice(itemList.get(position).getValue_with_bank()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<ValuableTeamDataModel> newList) {
        this.itemList = newList;
        notifyDataSetChanged();
    }

    public static class MostValuableTeamsHolder extends RecyclerView.ViewHolder {
        TextView rank;
        TextView name;
        TextView value;

        public MostValuableTeamsHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            name = itemView.findViewById(R.id.name);
            value = itemView.findViewById(R.id.value);
        }
    }
}
