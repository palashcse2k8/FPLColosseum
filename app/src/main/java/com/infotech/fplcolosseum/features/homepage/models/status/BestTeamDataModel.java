package com.infotech.fplcolosseum.features.homepage.models.status;

public class BestTeamDataModel {
    private float league;
    private float entries;
    private String average_score;
    private String name;


    // Getter Methods

    public float getLeague() {
        return league;
    }

    public float getEntries() {
        return entries;
    }

    public String getAverage_score() {
        return average_score;
    }

    public String getName() {
        return name;
    }

    // Setter Methods

    public void setLeague(float league) {
        this.league = league;
    }

    public void setEntries(float entries) {
        this.entries = entries;
    }

    public void setAverage_score(String average_score) {
        this.average_score = average_score;
    }

    public void setName(String name) {
        this.name = name;
    }
}
