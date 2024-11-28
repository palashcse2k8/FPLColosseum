package com.infotech.fplcolosseum.features.gameweek_history.models;

public class PreviousSeasonHistoryModel {
    private String season_name;
    private long total_points;
    private long rank;


    // Getter Methods

    public String getSeason_name() {
        return season_name;
    }

    public long getTotal_points() {
        return total_points;
    }

    public long getRank() {
        return rank;
    }

    // Setter Methods

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public void setTotal_points(long total_points) {
        this.total_points = total_points;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }
}