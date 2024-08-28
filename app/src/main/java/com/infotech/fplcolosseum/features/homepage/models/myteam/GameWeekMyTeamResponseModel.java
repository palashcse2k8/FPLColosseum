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
class Transfers {
    private long cost;
    private String status;
    private long limit;
    private long made;
    private long bank;
    private long value;


    // Getter Methods

    public long getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public long getLimit() {
        return limit;
    }

    public long getMade() {
        return made;
    }

    public long getBank() {
        return bank;
    }

    public long getValue() {
        return value;
    }

    // Setter Methods

    public void setCost(long cost) {
        this.cost = cost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public void setMade(long made) {
        this.made = made;
    }

    public void setBank(long bank) {
        this.bank = bank;
    }

    public void setValue(long value) {
        this.value = value;
    }
}

