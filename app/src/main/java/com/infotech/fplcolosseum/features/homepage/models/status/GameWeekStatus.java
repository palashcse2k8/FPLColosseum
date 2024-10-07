package com.infotech.fplcolosseum.features.homepage.models.status;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekStatus {
    @SerializedName("status")
    ArrayList<Status> status = new ArrayList<>();
    private String leagues;


    // Getter Methods

    public String getLeagues() {
        return leagues;
    }

    // Setter Methods

    public void setLeagues(String leagues) {
        this.leagues = leagues;
    }
}

class Status {
    private boolean bonus_added;
    private String date;
    private float event;
    private String points;


    // Getter Methods

    public boolean getBonus_added() {
        return bonus_added;
    }

    public String getDate() {
        return date;
    }

    public float getEvent() {
        return event;
    }

    public String getPoints() {
        return points;
    }

    // Setter Methods

    public void setBonus_added(boolean bonus_added) {
        this.bonus_added = bonus_added;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEvent(float event) {
        this.event = event;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}