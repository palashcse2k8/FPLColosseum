package com.infotech.fplcolosseum.features.homepage.models.status;

public class BestLeagueDataModel {
    private long league;
    private long entries;
    private double average_score;
    private String name;


    // Getter Methods

    public long getLeague() {
        return league;
    }

    public long getEntries() {
        return entries;
    }

    public Double getAverage_score() {
        return average_score;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setLeague(long league) {
        this.league = league;
    }

    public void setEntries(long entries) {
        this.entries = entries;
    }

    public void setAverage_score(Double average_score) {
        this.average_score = average_score;
    }

    public void setName(String name) {
        this.name = name;
    }
}
