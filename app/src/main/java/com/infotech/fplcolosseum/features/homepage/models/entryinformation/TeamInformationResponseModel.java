package com.infotech.fplcolosseum.features.homepage.models.entryinformation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TeamInformationResponseModel {
    private long id;
    private String joined_time;
    private long started_event;
    private long favourite_team;
    private String player_first_name;
    private String player_last_name;
    private long player_region_id;
    private String player_region_name;
    private String player_region_iso_code_short;
    private String player_region_iso_code_long;
    private long summary_overall_points;
    private long summary_overall_rank;
    private long summary_event_points;
    private long summary_event_rank;
    private long current_event;

    public Leagues getLeaguesObject() {
        return LeaguesObject;
    }

    public void setLeaguesObject(Leagues leaguesObject) {
        LeaguesObject = leaguesObject;
    }

    public boolean isName_change_blocked() {
        return name_change_blocked;
    }

    @SerializedName("leagues")
    Leagues LeaguesObject;

    private String name;
    private boolean name_change_blocked;
    private String kit = null;
    private long last_deadline_bank;
    private long last_deadline_value;
    private long last_deadline_total_transfers;


    // Getter Methods

    public long getId() {
        return id;
    }

    public String getJoined_time() {
        return joined_time;
    }

    public long getStarted_event() {
        return started_event;
    }

    public long getFavourite_team() {
        return favourite_team;
    }

    public String getPlayer_first_name() {
        return player_first_name;
    }

    public String getPlayer_last_name() {
        return player_last_name;
    }

    public long getPlayer_region_id() {
        return player_region_id;
    }

    public String getPlayer_region_name() {
        return player_region_name;
    }

    public String getPlayer_region_iso_code_short() {
        return player_region_iso_code_short;
    }

    public String getPlayer_region_iso_code_long() {
        return player_region_iso_code_long;
    }

    public long getSummary_overall_points() {
        return summary_overall_points;
    }

    public long getSummary_overall_rank() {
        return summary_overall_rank;
    }

    public long getSummary_event_points() {
        return summary_event_points;
    }

    public long getSummary_event_rank() {
        return summary_event_rank;
    }

    public long getCurrent_event() {
        return current_event;
    }

    public Leagues getLeagues() {
        return LeaguesObject;
    }

    public String getName() {
        return name;
    }

    public boolean getName_change_blocked() {
        return name_change_blocked;
    }

    public String getKit() {
        return kit;
    }

    public long getLast_deadline_bank() {
        return last_deadline_bank;
    }

    public long getLast_deadline_value() {
        return last_deadline_value;
    }

    public long getLast_deadline_total_transfers() {
        return last_deadline_total_transfers;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setJoined_time(String joined_time) {
        this.joined_time = joined_time;
    }

    public void setStarted_event(long started_event) {
        this.started_event = started_event;
    }

    public void setFavourite_team(long favourite_team) {
        this.favourite_team = favourite_team;
    }

    public void setPlayer_first_name(String player_first_name) {
        this.player_first_name = player_first_name;
    }

    public void setPlayer_last_name(String player_last_name) {
        this.player_last_name = player_last_name;
    }

    public void setPlayer_region_id(long player_region_id) {
        this.player_region_id = player_region_id;
    }

    public void setPlayer_region_name(String player_region_name) {
        this.player_region_name = player_region_name;
    }

    public void setPlayer_region_iso_code_short(String player_region_iso_code_short) {
        this.player_region_iso_code_short = player_region_iso_code_short;
    }

    public void setPlayer_region_iso_code_long(String player_region_iso_code_long) {
        this.player_region_iso_code_long = player_region_iso_code_long;
    }

    public void setSummary_overall_points(long summary_overall_points) {
        this.summary_overall_points = summary_overall_points;
    }

    public void setSummary_overall_rank(long summary_overall_rank) {
        this.summary_overall_rank = summary_overall_rank;
    }

    public void setSummary_event_points(long summary_event_points) {
        this.summary_event_points = summary_event_points;
    }

    public void setSummary_event_rank(long summary_event_rank) {
        this.summary_event_rank = summary_event_rank;
    }

    public void setCurrent_event(long current_event) {
        this.current_event = current_event;
    }

    public void setLeagues(Leagues leaguesObject) {
        this.LeaguesObject = leaguesObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setName_change_blocked(boolean name_change_blocked) {
        this.name_change_blocked = name_change_blocked;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public void setLast_deadline_bank(long last_deadline_bank) {
        this.last_deadline_bank = last_deadline_bank;
    }

    public void setLast_deadline_value(long last_deadline_value) {
        this.last_deadline_value = last_deadline_value;
    }

    public void setLast_deadline_total_transfers(long last_deadline_total_transfers) {
        this.last_deadline_total_transfers = last_deadline_total_transfers;
    }
}

