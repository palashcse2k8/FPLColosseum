package com.infotech.fplcolosseum.features.homepage.models.picks;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekPicksModel {
    private String active_chip = null;

    @SerializedName("automatic_subs")
    ArrayList<AutomaticSubs> automatic_subs = new ArrayList<>();

    @SerializedName("entry_history")
    Entry_history Entry_historyObject;

    @SerializedName("picks")
    ArrayList<Picks> picks = new ArrayList<>();

    public ArrayList<AutomaticSubs> getAutomatic_subs() {
        return automatic_subs;
    }

    public void setAutomatic_subs(ArrayList<AutomaticSubs> automatic_subs) {
        this.automatic_subs = automatic_subs;
    }

    public Entry_history getEntry_historyObject() {
        return Entry_historyObject;
    }

    public void setEntry_historyObject(Entry_history entry_historyObject) {
        Entry_historyObject = entry_historyObject;
    }

    public ArrayList<Picks> getPicks() {
        return picks;
    }

    public void setPicks(ArrayList<Picks> picks) {
        this.picks = picks;
    }
// Getter Methods

    public String getActive_chip() {
        return active_chip;
    }

    public Entry_history getEntry_history() {
        return Entry_historyObject;
    }

    // Setter Methods

    public void setActive_chip(String active_chip) {
        this.active_chip = active_chip;
    }

    public void setEntry_history(Entry_history entry_historyObject) {
        this.Entry_historyObject = entry_historyObject;
    }
}

