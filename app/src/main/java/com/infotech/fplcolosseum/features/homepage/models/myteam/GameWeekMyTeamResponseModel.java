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

class GameChips {
    private String status_for_entry;
    ArrayList < Object > played_by_entry = new ArrayList < Object > ();
    private String name;
    private long number;
    private long start_event;
    private long stop_event;
    private String chip_type;


    // Getter Methods

    public String getStatus_for_entry() {
        return status_for_entry;
    }

    public String getName() {
        return name;
    }

    public long getNumber() {
        return number;
    }

    public long getStart_event() {
        return start_event;
    }

    public long getStop_event() {
        return stop_event;
    }

    public String getChip_type() {
        return chip_type;
    }

    // Setter Methods

    public void setStatus_for_entry(String status_for_entry) {
        this.status_for_entry = status_for_entry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setStart_event(long start_event) {
        this.start_event = start_event;
    }

    public void setStop_event(long stop_event) {
        this.stop_event = stop_event;
    }

    public void setChip_type(String chip_type) {
        this.chip_type = chip_type;
    }
}