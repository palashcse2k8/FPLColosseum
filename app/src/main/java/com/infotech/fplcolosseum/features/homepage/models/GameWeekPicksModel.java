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
    private float event;
    private float points;
    private float total_points;
    private float rank;
    private float rank_sort;
    private float overall_rank;
    private float bank;
    private float value;
    private float event_transfers;
    private float event_transfers_cost;
    private float points_on_bench;


    // Getter Methods

    public float getEvent() {
        return event;
    }

    public float getPoints() {
        return points;
    }

    public float getTotal_points() {
        return total_points;
    }

    public float getRank() {
        return rank;
    }

    public float getRank_sort() {
        return rank_sort;
    }

    public float getOverall_rank() {
        return overall_rank;
    }

    public float getBank() {
        return bank;
    }

    public float getValue() {
        return value;
    }

    public float getEvent_transfers() {
        return event_transfers;
    }

    public float getEvent_transfers_cost() {
        return event_transfers_cost;
    }

    public float getPoints_on_bench() {
        return points_on_bench;
    }

    // Setter Methods

    public void setEvent(float event) {
        this.event = event;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public void setTotal_points(float total_points) {
        this.total_points = total_points;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public void setRank_sort(float rank_sort) {
        this.rank_sort = rank_sort;
    }

    public void setOverall_rank(float overall_rank) {
        this.overall_rank = overall_rank;
    }

    public void setBank(float bank) {
        this.bank = bank;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setEvent_transfers(float event_transfers) {
        this.event_transfers = event_transfers;
    }

    public void setEvent_transfers_cost(float event_transfers_cost) {
        this.event_transfers_cost = event_transfers_cost;
    }

    public void setPoints_on_bench(float points_on_bench) {
        this.points_on_bench = points_on_bench;
    }
}

class Picks {
    private float element;
    private float position;
    private float multiplier;
    private boolean is_captain;
    private boolean is_vice_captain;


    // Getter Methods

    public float getElement() {
        return element;
    }

    public float getPosition() {
        return position;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public boolean getIs_captain() {
        return is_captain;
    }

    public boolean getIs_vice_captain() {
        return is_vice_captain;
    }

    // Setter Methods

    public void setElement(float element) {
        this.element = element;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public void setIs_captain(boolean is_captain) {
        this.is_captain = is_captain;
    }

    public void setIs_vice_captain(boolean is_vice_captain) {
        this.is_vice_captain = is_vice_captain;
    }
}