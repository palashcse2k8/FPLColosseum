package com.infotech.fplcolosseum.features.player_information.adapter;

import static com.infotech.fplcolosseum.utilities.CustomUtil.convertedPrice;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infotech.fplcolosseum.databinding.LayoutThisSeasonItemBinding;
import com.infotech.fplcolosseum.databinding.LayoutThisSeasonItemFooterBinding;
import com.infotech.fplcolosseum.features.player_information.models.History;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Objects;
import java.util.stream.Stream;

// CustomAdapter.java
public class ThisSeasonHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<History> mData;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public ThisSeasonHistoryAdapter(List<History> data) {
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(LayoutThisSeasonItemFooterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return new ItemViewHolder(LayoutThisSeasonItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bindData(mData.get(position));

        } else if (holder instanceof FooterViewHolder) {

            ((FooterViewHolder) holder).bindData(mData);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        LayoutThisSeasonItemBinding binding;//Name of the test_list_item.xml in camel case + "Binding"

        public ItemViewHolder(LayoutThisSeasonItemBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bindData(History data) {


            binding.gameWeek.setText(String.valueOf(data.getRound()));

            String teamName = Objects.requireNonNull(Constants.teamMap.get(data.getOpponent_team())).getShort_name() + " " + (data.getWas_home() ? "(H)" : "(A)");

            String score = data.getTeam_h_score() + " - " + data.getTeam_a_score();
            String result;
            if (data.getTeam_h_score() == data.getTeam_a_score()) {
                result = " (D)";
            } else if (data.getWas_home() && data.getTeam_h_score() > data.getTeam_a_score()) {
                result = " (W)";
            } else if (!data.getWas_home() && data.getTeam_h_score() < data.getTeam_a_score()) {
                result = " (W)";
            } else {
                result = " (L)";
            }

            score = teamName + " " + score + result;
            binding.opponent.setText(score);
            binding.points.setText(String.valueOf(data.getTotal_points()));
            binding.minutesPlayed.setText(String.valueOf(data.getMinutes()));
            binding.goalScored.setText(String.valueOf(data.getGoals_scored()));
            binding.assist.setText(String.valueOf(data.getAssists()));
            binding.starts.setText(String.valueOf(data.getStarts()));
            binding.expectedGoal.setText(String.valueOf(data.getExpected_goals()));
            binding.expectedAssist.setText(String.valueOf(data.getExpected_assists()));
            binding.expectedGoalInvolvement.setText(String.valueOf(data.getExpected_goal_involvements()));
            binding.cleanSheet.setText(String.valueOf(data.getClean_sheets()));
            binding.goalConceded.setText(String.valueOf(data.getGoals_conceded()));
            binding.expectedGoalConceded.setText(String.valueOf(data.getExpected_goals_conceded()));
            binding.ownGoal.setText(String.valueOf(data.getOwn_goals()));
            binding.penaltySaved.setText(String.valueOf(data.getPenalties_saved()));
            binding.penaltyMissed.setText(String.valueOf(data.getPenalties_missed()));
            binding.yellowCard.setText(String.valueOf(data.getYellow_cards()));
            binding.redCard.setText(String.valueOf(data.getRed_cards()));
            binding.saves.setText(String.valueOf(data.getSaves()));
            binding.bonusPoints.setText(String.valueOf(data.getBonus()));
            binding.bonusPointSystem.setText(String.valueOf(data.getBps()));
            binding.influence.setText(String.valueOf(data.getInfluence()));
            binding.creativity.setText(String.valueOf(data.getCreativity()));
            binding.threat.setText(String.valueOf(data.getThreat()));
            binding.ictIndex.setText(String.valueOf(data.getIct_index()));
            Long netTransfer = data.getTransfers_in() - data.getTransfers_out();
            binding.netTransfer.setText(String.valueOf(netTransfer));
            binding.selectedBy.setText(String.valueOf(data.getSelected()));
            binding.currentPrice.setText(convertedPrice(data.getValue()));
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        LayoutThisSeasonItemFooterBinding binding;

        FooterViewHolder(LayoutThisSeasonItemFooterBinding b) {
            super(b.getRoot());
            binding = b;
        }

        public void bindData(List<History> data) {

            // Initialize variables to store total values
            long totalPoints = 0, totalMinutesPlayed = 0, totalGoalsScored = 0, totalAssists = 0;
            long totalStarts = 0, totalExpectedGoals = 0, totalExpectedAssists = 0, totalExpectedGoalsInvolvement = 0;
            long totalCleanSheets = 0, totalGoalsConceded = 0, totalExpectedGoalsConceded = 0, totalOwnGoals = 0;
            long totalPenaltiesSaved = 0, totalPenaltiesMissed = 0, totalYellowCards = 0, totalRedCards = 0;
            long totalSaves = 0, totalBonus = 0, totalBPS = 0, totalInfluence = 0, totalCreativity = 0, totalThreat = 0;
            long totalICTIndex = 0, totalTransfersIn = 0, totalTransfersOut = 0, totalSelected = 0;

            // Loop through each item in the data list and accumulate totals
            for (History history : data) {
                totalPoints += history.getTotal_points();
                totalMinutesPlayed += history.getMinutes();
                totalGoalsScored += history.getGoals_scored();
                totalAssists += history.getAssists();
                totalStarts += history.getStarts();
                totalExpectedGoals += history.getExpected_goals();
                totalExpectedAssists += history.getExpected_assists();
                totalExpectedGoalsInvolvement += history.getExpected_goal_involvements();
                totalCleanSheets += history.getClean_sheets();
                totalGoalsConceded += history.getGoals_conceded();
                totalExpectedGoalsConceded += history.getExpected_goals_conceded();
                totalOwnGoals += history.getOwn_goals();
                totalPenaltiesSaved += history.getPenalties_saved();
                totalPenaltiesMissed += history.getPenalties_missed();
                totalYellowCards += history.getYellow_cards();
                totalRedCards += history.getRed_cards();
                totalSaves += history.getSaves();
                totalBonus += history.getBonus();
                totalBPS += history.getBps();
                totalInfluence += history.getInfluence();
                totalCreativity += history.getCreativity();
                totalThreat += history.getThreat();
                totalICTIndex += history.getIct_index();
                totalTransfersIn += history.getTransfers_in();
                totalTransfersOut += history.getTransfers_out();
                totalSelected += history.getSelected();
            }

            // Bind the total values to the views in the layout
            binding.points.setText(String.valueOf(totalPoints));
            binding.minutesPlayed.setText(String.valueOf(totalMinutesPlayed));
            binding.goalScored.setText(String.valueOf(totalGoalsScored));
            binding.assist.setText(String.valueOf(totalAssists));
            binding.starts.setText(String.valueOf(totalStarts));
            binding.expectedGoal.setText(String.valueOf(totalExpectedGoals));
            binding.expectedAssist.setText(String.valueOf(totalExpectedAssists));
            binding.expectedGoalInvolvement.setText(String.valueOf(totalExpectedGoalsInvolvement));
            binding.cleanSheet.setText(String.valueOf(totalCleanSheets));
            binding.goalConceded.setText(String.valueOf(totalGoalsConceded));
            binding.expectedGoalConceded.setText(String.valueOf(totalExpectedGoalsConceded));
            binding.ownGoal.setText(String.valueOf(totalOwnGoals));
            binding.penaltySaved.setText(String.valueOf(totalPenaltiesSaved));
            binding.penaltyMissed.setText(String.valueOf(totalPenaltiesMissed));
            binding.yellowCard.setText(String.valueOf(totalYellowCards));
            binding.redCard.setText(String.valueOf(totalRedCards));
            binding.saves.setText(String.valueOf(totalSaves));
            binding.bonusPoints.setText(String.valueOf(totalBonus));
            binding.bonusPointSystem.setText(String.valueOf(totalBPS));
            binding.influence.setText(String.valueOf(totalInfluence));
            binding.creativity.setText(String.valueOf(totalCreativity));
            binding.threat.setText(String.valueOf(totalThreat));
            binding.ictIndex.setText(String.valueOf(totalICTIndex));

        }

    }
}
