package com.infotech.fplcolosseum.features.player_information.adapter;

import static com.infotech.fplcolosseum.utilities.CustomUtil.convertedPrice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.R;
import com.infotech.fplcolosseum.databinding.LayoutItemSeasonHeaderBinding;
import com.infotech.fplcolosseum.features.player_information.models.HistoryPast;

import java.util.List;

// CustomAdapter.java
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryPast> mData;

    public HistoryAdapter(List<HistoryPast> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutItemSeasonHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HistoryPast data = mData.get(position);
        holder.binding.points.setText((int) data.getTotal_points() + "");
        holder.binding.minutesPlayed.setText((int) data.getMinutes() + "");
        holder.binding.goalScored.setText((int) data.getGoals_scored() + "");
        holder.binding.assist.setText((int) data.getAssists() + "");
        holder.binding.starts.setText( data.getStarts() + "");
        holder.binding.expectedGoal.setText(data.getExpected_goals() + "");
        holder.binding.expectedAssist.setText(data.getExpected_assists() + "");
        holder.binding.expectedGoalInvolvement.setText(data.getExpected_goal_involvements() + "");
        holder.binding.cleanSheet.setText((int) data.getClean_sheets() + "");
        holder.binding.goalConceded.setText((int) data.getGoals_conceded() + "");
        holder.binding.expectedGoalConceded.setText(data.getExpected_goals_conceded() + "");
        holder.binding.ownGoal.setText((int) data.getOwn_goals() + "");
        holder.binding.penaltySaved.setText((int) data.getPenalties_saved() + "");
        holder.binding.penaltyMissed.setText((int) data.getPenalties_missed() + "");
        holder.binding.yellowCard.setText((int) data.getYellow_cards() + "");
        holder.binding.redCard.setText((int) data.getRed_cards() + "");
        holder.binding.saves.setText((int) data.getSaves() + "");
        holder.binding.bonusPoints.setText((int) data.getBonus() + "");
        holder.binding.bonusPointSystem.setText((int) data.getBps() + "");
        holder.binding.influence.setText(data.getInfluence() + "");
        holder.binding.creativity.setText(data.getCreativity() + "");
        holder.binding.threat.setText(data.getThreat() + "");
        holder.binding.ictIndex.setText(data.getIct_index() + "");
        holder.binding.startPrice.setText(convertedPrice((long) data.getStart_cost()));
        holder.binding.entPrice.setText(convertedPrice((long) data.getEnd_cost()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        LayoutItemSeasonHeaderBinding binding;//Name of the test_list_item.xml in camel case + "Binding"

        public ViewHolder(LayoutItemSeasonHeaderBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
