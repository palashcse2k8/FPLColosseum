package com.infotech.fplcolosseum.features.player_information.adapter;

import static com.infotech.fplcolosseum.utilities.CustomUtil.convertedPrice;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.LayoutItemSeasonHeaderBinding;
import com.infotech.fplcolosseum.databinding.LayoutItemThisSeasonHeaderBinding;
import com.infotech.fplcolosseum.features.player_information.models.History;
import com.infotech.fplcolosseum.features.player_information.models.HistoryPast;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.List;
import java.util.Objects;

// CustomAdapter.java
public class ThisSeasonHistoryAdapter extends RecyclerView.Adapter<ThisSeasonHistoryAdapter.ViewHolder> {
    private List<History> mData;

    public ThisSeasonHistoryAdapter(List<History> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutItemThisSeasonHeaderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        History data = mData.get(position);
        holder.binding.gameWeek.setText((int) data.getRound() + "");

        String teamName = Objects.requireNonNull(Constants.teamMap.get(data.getOpponent_team())).getShort_name() + " " + (data.getWas_home() ? "(H)" : "(A)");

        String score = (int) data.getTeam_h_score() + " - " + (int) data.getTeam_a_score();
        String result;
        if(data.getTeam_h_score() == data.getTeam_a_score())
        {
            result = " (D)";
        } else if (data.getWas_home() && data.getTeam_h_score() > data.getTeam_a_score()) {
            result = " (W)";
        } else {
            result = " (L)";
        }

        score = teamName + " " + score + result;
        holder.binding.opponent.setText(score);
        holder.binding.points.setText((int) data.getTotal_points() + "");
        holder.binding.minutesPlayed.setText((int) data.getMinutes() + "");
        holder.binding.goalScored.setText((int) data.getGoals_scored() + "");
        holder.binding.assist.setText((int) data.getAssists() + "");
        holder.binding.starts.setText( (int) data.getStarts() + "");
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
        holder.binding.netTransfer.setText(data.getTransfers_in() - data.getTransfers_out() + "");
        holder.binding.selectedBy.setText(data.getSelected() + "");
        holder.binding.currentPrice.setText(convertedPrice(data.getValue()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        LayoutItemThisSeasonHeaderBinding binding;//Name of the test_list_item.xml in camel case + "Binding"

        public ViewHolder(LayoutItemThisSeasonHeaderBinding b) {
            super(b.getRoot());
            binding = b;
        }
    }
}
