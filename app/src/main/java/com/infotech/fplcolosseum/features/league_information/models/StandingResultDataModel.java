package com.infotech.fplcolosseum.features.league_information.models;

public class StandingResultDataModel {
    private long id;
    private long event_total;
    private String player_name;
    private long rank;
    private long last_rank;
    private long rank_sort;
    private long total;
    private long entry;
    private String entry_name;


    // Getter Methods

    public long getId() {
        return id;
    }

    public long getEvent_total() {
        return event_total;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public long getRank() {
        return rank;
    }

    public long getLast_rank() {
        return last_rank;
    }

    public long getRank_sort() {
        return rank_sort;
    }

    public long getTotal() {
        return total;
    }

    public long getEntry() {
        return entry;
    }

    public String getEntry_name() {
        return entry_name;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setEvent_total(long event_total) {
        this.event_total = event_total;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public void setLast_rank(long last_rank) {
        this.last_rank = last_rank;
    }

    public void setRank_sort(long rank_sort) {
        this.rank_sort = rank_sort;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setEntry(long entry) {
        this.entry = entry;
    }

    public void setEntry_name(String entry_name) {
        this.entry_name = entry_name;
    }
}