package com.infotech.fplcolosseum.features.league_information.models;

public class LeagueNewEntryResultDataModel {
    private long entry;
    private String entry_name;
    private String joined_time;
    private String player_first_name;
    private String player_last_name;


    // Getter Methods

    public long getEntry() {
        return entry;
    }

    public String getEntry_name() {
        return entry_name;
    }

    public String getJoined_time() {
        return joined_time;
    }

    public String getPlayer_first_name() {
        return player_first_name;
    }

    public String getPlayer_last_name() {
        return player_last_name;
    }

    // Setter Methods

    public void setEntry(long entry) {
        this.entry = entry;
    }

    public void setEntry_name(String entry_name) {
        this.entry_name = entry_name;
    }

    public void setJoined_time(String joined_time) {
        this.joined_time = joined_time;
    }

    public void setPlayer_first_name(String player_first_name) {
        this.player_first_name = player_first_name;
    }

    public void setPlayer_last_name(String player_last_name) {
        this.player_last_name = player_last_name;
    }
}