package com.infotech.fplcolosseum.features.homepage.models.status;

public class ValuableTeamDataModel {
    private float entry;
    private String name;
    private String player_name;
    private float value_with_bank;
    private float total_transfers;


    // Getter Methods

    public float getEntry() {
        return entry;
    }

    public String getName() {
        return name;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public float getValue_with_bank() {
        return value_with_bank;
    }

    public float getTotal_transfers() {
        return total_transfers;
    }

    // Setter Methods

    public void setEntry(float entry) {
        this.entry = entry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public void setValue_with_bank(float value_with_bank) {
        this.value_with_bank = value_with_bank;
    }

    public void setTotal_transfers(float total_transfers) {
        this.total_transfers = total_transfers;
    }
}