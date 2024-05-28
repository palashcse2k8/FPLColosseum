package com.infotech.fplcolosseum.features.homepage.models.livepoints;

public class ExplainStat {
    private String identifier;
    private float points;
    private float value;


    // Getter Methods

    public String getIdentifier() {
        return identifier;
    }

    public float getPoints() {
        return points;
    }

    public float getValue() {
        return value;
    }

    // Setter Methods

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
