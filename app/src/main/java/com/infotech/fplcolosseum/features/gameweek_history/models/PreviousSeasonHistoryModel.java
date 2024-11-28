package com.infotech.fplcolosseum.features.gameweek_history.models;

public class PreviousSeasonHistoryModel {
    private String season_name;
    private float total_points;
    private float rank;


    // Getter Methods

    public String getSeason_name() {
        return season_name;
    }

    public float getTotal_points() {
        return total_points;
    }

    public float getRank() {
        return rank;
    }

    // Setter Methods

    public void setSeason_name(String season_name) {
        this.season_name = season_name;
    }

    public void setTotal_points(float total_points) {
        this.total_points = total_points;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }
}