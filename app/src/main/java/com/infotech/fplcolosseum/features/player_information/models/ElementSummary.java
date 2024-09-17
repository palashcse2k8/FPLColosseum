package com.infotech.fplcolosseum.features.player_information.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ElementSummary {

    @SerializedName("fixtures")
    ArrayList< Object > fixtures = new ArrayList < Object > ();

    @SerializedName("history")
    ArrayList < Object > history = new ArrayList < Object > ();

    @SerializedName("history_past")
    ArrayList < Object > history_past = new ArrayList < Object > ();


    // Getter Methods

    public ArrayList<Object> getFixtures() {
        return fixtures;
    }

    public ArrayList<Object> getHistory() {
        return history;
    }

    public ArrayList<Object> getHistory_past() {
        return history_past;
    }

    // Setter Methods

    public void setFixtures(ArrayList<Object> fixtures) {
        this.fixtures = fixtures;
    }

    public void setHistory(ArrayList<Object> history) {
        this.history = history;
    }

    public void setHistory_past(ArrayList<Object> history_past) {
        this.history_past = history_past;
    }
}

