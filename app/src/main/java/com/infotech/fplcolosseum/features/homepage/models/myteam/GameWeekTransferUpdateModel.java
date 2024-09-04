package com.infotech.fplcolosseum.features.homepage.models.myteam;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekTransferUpdateModel {
    private String entry;
    private float event;
    private String chip;

    @SerializedName("transfers")
    ArrayList <TransferUpdate> transfers = new ArrayList<>();

    // Getter Methods
    public String getEntry() {
        return entry;
    }

    public float getEvent() {
        return event;
    }

    public ArrayList<TransferUpdate> getTransfers() {
        return transfers;
    }

    public String getChip() {
        return chip;
    }

    // Setter Methods
    public void setEntry(String entry) {
        this.entry = entry;
    }

    public void setEvent(float event) {
        this.event = event;
    }

    public void setChip(String chip) {
        this.chip = chip;
    }

    public void setTransfers(ArrayList<TransferUpdate> transfers) {
        this.transfers = transfers;
    }
}
