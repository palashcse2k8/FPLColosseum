package com.infotech.fplcolosseum.features.homepage.models.myteam;

import com.google.gson.annotations.SerializedName;
import com.infotech.fplcolosseum.features.homepage.models.picks.Picks;

import java.util.ArrayList;

public class GameWeekMyTeamUpdateModel {
    @SerializedName("picks")
    ArrayList<Picks> picks = new ArrayList <> ();
    @SerializedName("chip")
    String chip;

    // Getter Methods
    public ArrayList<Picks> getPicks() {
        return picks;
    }
    public String getChip() {
        return chip;
    }

    // Setter Methods
    public void setChip(String chip) {
        this.chip = chip;
    }

    public void setPicks(ArrayList<Picks> picks) {
        this.picks = picks;
    }
}

