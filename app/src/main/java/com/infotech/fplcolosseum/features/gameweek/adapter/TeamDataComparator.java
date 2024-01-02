package com.infotech.fplcolosseum.features.gameweek.adapter;

import com.infotech.fplcolosseum.features.gameweek.models.custom.ManagerModel;

import java.util.Comparator;

public class TeamDataComparator implements Comparator<ManagerModel> {
    @Override
    public int compare(ManagerModel team1, ManagerModel team2) {
        // Compare by total points (descending order)
        int pointsComparison = Long.compare(team2.getGameWeekPointsWithoutTransferCost(), team1.getGameWeekPointsWithoutTransferCost());

        if (pointsComparison != 0) {
            return pointsComparison; // If not tied, return the result
        }

        // If tied on total points, compare by caption points difference (descending order)
        int captionPointComparison = Long.compare(team2.getCaptainGameWeekPoints(), team1.getCaptainGameWeekPoints());

        if (captionPointComparison != 0) {
            return captionPointComparison; // If not tied, return the result
        }

        // If tied on total points, compare by caption points difference (descending order)
        int viceCaptionPointComparison = Long.compare(team2.getViceCaptainGameWeekPoints(), team1.getViceCaptainGameWeekPoints());

        if (viceCaptionPointComparison != 0) {
            return viceCaptionPointComparison; // If not tied, return the result
        }

        // If tied on total points, compare by caption points difference (descending order)
        int bonusPointComparison = Long.compare(team2.getGameWeekBonusPointsXI(), team1.getGameWeekBonusPointsXI());

        if (bonusPointComparison != 0) {
            return bonusPointComparison; // If not tied, return the result
        }

        // If tied on total points, compare by caption points difference (descending order)
        int benchPointComparison = Long.compare(team2.getGameWeekBenchPoints(), team1.getGameWeekBenchPoints());

        if (benchPointComparison != 0) {
            return benchPointComparison; // If not tied, return the result
        }

        // If tied on total points, compare by caption points difference (descending order)
        int goalScoredComparison = Long.compare(team2.getGoalScored(), team1.getGoalScored());

        if (goalScoredComparison != 0) {
            return goalScoredComparison; // If not tied, return the result
        }

        // If tied on total points, compare by caption points difference (descending order)
        int goalConcededComparison = Long.compare(team2.getGoalConceded(), team1.getGoalConceded());

        if (goalConcededComparison != 0) {
            return goalConcededComparison; // If not tied, return the result
        }

        // Compare BPS points at last
        return Long.compare(team2.getGameWeekBPSPointsXI(), team1.getGameWeekBPSPointsXI());
    }
}