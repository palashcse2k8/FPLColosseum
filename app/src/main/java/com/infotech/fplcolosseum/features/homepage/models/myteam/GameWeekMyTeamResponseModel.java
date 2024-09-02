package com.infotech.fplcolosseum.features.homepage.models.myteam;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekMyTeamResponseModel {
    @SerializedName("picks")
    ArrayList<MyTeamPicks> picks = new ArrayList <> ();
    @SerializedName("chips")
    ArrayList <GameChips> chips = new ArrayList <> ();
    @SerializedName("transfers")
    Transfers TransfersObject;


    // Getter Methods

    public ArrayList<MyTeamPicks> getPicks() {
        return picks;
    }

    public ArrayList<GameChips> getChips() {
        return chips;
    }

    public Transfers getTransfersObject() {
        return TransfersObject;
    }

    public Transfers getTransfers() {
        return TransfersObject;
    }

    // Setter Methods

    public void setTransfers(Transfers transfersObject) {
        this.TransfersObject = transfersObject;
    }
}

