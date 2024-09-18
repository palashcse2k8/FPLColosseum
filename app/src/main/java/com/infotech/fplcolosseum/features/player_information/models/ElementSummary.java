package com.infotech.fplcolosseum.features.player_information.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ElementSummary {

    @SerializedName("fixtures")
    ArrayList< Fixtures > fixtures = new ArrayList < > ();

    @SerializedName("history")
    ArrayList < History > history = new ArrayList < > ();

    @SerializedName("history_past")
    ArrayList < HistoryPast > history_past = new ArrayList < > ();


    // Getter Methods

    public ArrayList<Fixtures> getFixtures() {
        return fixtures;
    }

    public ArrayList<History> getHistory() {
        return history;
    }

    public ArrayList<HistoryPast> getHistory_past() {
        return history_past;
    }

    // Setter Methods

    public void setFixtures(ArrayList<Fixtures> fixtures) {
        this.fixtures = fixtures;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    public void setHistory_past(ArrayList<HistoryPast> history_past) {
        this.history_past = history_past;
    }
}

