package com.infotech.fplcolosseum.features.gameweek_history.models;

public class UsedChipsModel {
    private String name;
    private String time;
    private float event;


    // Getter Methods

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public float getEvent() {
        return event;
    }

    // Setter Methods

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setEvent(float event) {
        this.event = event;
    }
}