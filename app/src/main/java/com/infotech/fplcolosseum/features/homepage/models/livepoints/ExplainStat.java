package com.infotech.fplcolosseum.features.homepage.models.livepoints;

import java.io.Serializable;

public class ExplainStat implements Serializable {
    private String identifier;
    private Long points;
    private Long value;


    // Getter Methods

    public String getIdentifier() {
        return identifier;
    }

    public Long getPoints() {
        return points;
    }

    public Long getValue() {
        return value;
    }

    // Setter Methods

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
