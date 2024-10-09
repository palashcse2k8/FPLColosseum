package com.infotech.fplcolosseum.features.homepage.models.status;

public class ValuableTeamDataModel {
    private long entry;
    private String name;
    private String player_name;
    private long value_with_bank;
    private long total_transfers;


    // Getter Methods

    public long getEntry() {
        return entry;
    }

    public String getName() {
        return name;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public long getValue_with_bank() {
        return value_with_bank;
    }

    public long getTotal_transfers() {
        return total_transfers;
    }

    // Setter Methods

    public void setEntry(long entry) {
        this.entry = entry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public void setValue_with_bank(long value_with_bank) {
        this.value_with_bank = value_with_bank;
    }

    public void setTotal_transfers(long total_transfers) {
        this.total_transfers = total_transfers;
    }
}