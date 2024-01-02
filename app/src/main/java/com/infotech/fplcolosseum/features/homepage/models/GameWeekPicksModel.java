package com.infotech.fplcolosseum.features.homepage.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekPicksModel {
    private String active_chip = null;
    ArrayList<Object> automatic_subs = new ArrayList<Object>();
    @SerializedName("entry_history")
    Entry_history Entry_historyObject;

    @SerializedName("picks")
    ArrayList<Picks> picks = new ArrayList<>();


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

class Entry_history {
    private long event;
    private long points;
    private long total_points;
    private long rank;
    private long rank_sort;
    private long overall_rank;
    private long bank;
    private long value;
    private long event_transfers;
    private long event_transfers_cost;
    private long points_on_bench;


    // Getter Methods

    public long getEvent() {
        return event;
    }

    public long getPoints() {
        return points;
    }

    public long getTotal_points() {
        return total_points;
    }

    public long getRank() {
        return rank;
    }

    public long getRank_sort() {
        return rank_sort;
    }

    public long getOverall_rank() {
        return overall_rank;
    }

    public long getBank() {
        return bank;
    }

    public long getValue() {
        return value;
    }

    public long getEvent_transfers() {
        return event_transfers;
    }

    public long getEvent_transfers_cost() {
        return event_transfers_cost;
    }

    public long getPoints_on_bench() {
        return points_on_bench;
    }

    // Setter Methods

    public void setEvent(long event) {
        this.event = event;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public void setTotal_points(long total_points) {
        this.total_points = total_points;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public void setRank_sort(long rank_sort) {
        this.rank_sort = rank_sort;
    }

    public void setOverall_rank(long overall_rank) {
        this.overall_rank = overall_rank;
    }

    public void setBank(long bank) {
        this.bank = bank;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setEvent_transfers(long event_transfers) {
        this.event_transfers = event_transfers;
    }

    public void setEvent_transfers_cost(long event_transfers_cost) {
        this.event_transfers_cost = event_transfers_cost;
    }

    public void setPoints_on_bench(long points_on_bench) {
        this.points_on_bench = points_on_bench;
    }
}

class Picks {
    private long element;
    private long position;
    private long multiplier;
    private boolean is_captain;
    private boolean is_vice_captain;


    // Getter Methods

    public long getElement() {
        return element;
    }

    public long getPosition() {
        return position;
    }

    public long getMultiplier() {
        return multiplier;
    }

    public boolean getIs_captain() {
        return is_captain;
    }

    public boolean getIs_vice_captain() {
        return is_vice_captain;
    }

    // Setter Methods

    public void setElement(long element) {
        this.element = element;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
    }

    public void setIs_captain(boolean is_captain) {
        this.is_captain = is_captain;
    }

    public void setIs_vice_captain(boolean is_vice_captain) {
        this.is_vice_captain = is_vice_captain;
    }
}