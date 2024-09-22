package com.infotech.fplcolosseum.features.player_information.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.features.player_information.models.HistoryPast;

import java.util.List;

// CustomAdapter.java
public class HistoryAdapterFixed extends RecyclerView.Adapter<HistoryAdapterFixed.ViewHolder> {
    private List<HistoryPast> mData;

    public HistoryAdapterFixed(List<HistoryPast> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_season_fixed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HistoryPast data = mData.get(position);
        holder.name.setText(data.getSeason_name());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.seasonName);
        }
    }
}
