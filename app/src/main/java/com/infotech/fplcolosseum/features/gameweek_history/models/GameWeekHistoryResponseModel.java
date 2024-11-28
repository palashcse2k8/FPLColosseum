package com.infotech.fplcolosseum.features.gameweek_history.models;

import java.util.ArrayList;

public class GameWeekHistoryResponseModel {
    ArrayList<CurrentSeasonHistoryModel> current = new ArrayList<>();
    ArrayList<PreviousSeasonHistoryModel> past = new ArrayList<>();
    ArrayList<UsedChipsModel> chips = new ArrayList<>();

    // Getter Methods
    public ArrayList<CurrentSeasonHistoryModel> getCurrent() {
        return current;
    }

    public ArrayList<PreviousSeasonHistoryModel> getPast() {
        return past;
    }

    public ArrayList<UsedChipsModel> getChips() {
        return chips;
    }


    // Setter Methods
    public void setCurrent(ArrayList<CurrentSeasonHistoryModel> current) {
        this.current = current;
    }

    public void setPast(ArrayList<PreviousSeasonHistoryModel> past) {
        this.past = past;
    }

    public void setChips(ArrayList<UsedChipsModel> chips) {
        this.chips = chips;
    }
}
