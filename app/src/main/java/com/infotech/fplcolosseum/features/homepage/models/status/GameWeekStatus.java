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

    public ArrayList<Status> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<Status> status) {
        this.status = status;
    }
}

