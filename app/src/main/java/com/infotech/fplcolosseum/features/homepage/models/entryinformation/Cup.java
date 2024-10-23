package com.infotech.fplcolosseum.features.homepage.models.entryinformation;

import java.util.ArrayList;

public class Cup {
    ArrayList < Object > matches = new ArrayList < Object > ();
    Status StatusObject;
    private String cup_league = null;


    // Getter Methods

    public Status getStatus() {
        return StatusObject;
    }

    public String getCup_league() {
        return cup_league;
    }

    // Setter Methods

    public void setStatus(Status statusObject) {
        this.StatusObject = statusObject;
    }

    public void setCup_league(String cup_league) {
        this.cup_league = cup_league;
    }
}
