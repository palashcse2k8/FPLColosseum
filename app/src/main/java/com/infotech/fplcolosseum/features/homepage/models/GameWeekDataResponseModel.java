package com.infotech.fplcolosseum.features.homepage.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekDataResponseModel {
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
class Leagues {
    @SerializedName("classic")
    ArrayList < LeagueDataModel > classic = new ArrayList < > ();
    @SerializedName("h2h")
    ArrayList< LeagueDataModel > h2h = new ArrayList < > ();
    Cup CupObject;
    ArrayList < Object > cup_matches = new ArrayList < Object > ();


    // Getter Methods

    public Cup getCup() {
        return CupObject;
    }

    // Setter Methods

    public void setCup(Cup cupObject) {
        this.CupObject = cupObject;
    }
}

class LeagueDataModel {
    private long id;
    private String name;
    private String short_name = null;
    private String created;
    private boolean closed;
    private String rank = null;
    private String max_entries = null;
    private String league_type;
    private String scoring;
    private long admin_entry;
    private long start_event;
    private boolean entry_can_leave;
    private boolean entry_can_admin;
    private boolean entry_can_invite;
    private boolean has_cup;
    private String cup_league = null;
    private String cup_qualified = null;
    private long entry_rank;
    private long entry_last_rank;


    // Getter Methods

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShort_name() {
        return short_name;
    }

    public String getCreated() {
        return created;
    }

    public boolean getClosed() {
        return closed;
    }

    public String getRank() {
        return rank;
    }

    public String getMax_entries() {
        return max_entries;
    }

    public String getLeague_type() {
        return league_type;
    }

    public String getScoring() {
        return scoring;
    }

    public long getAdmin_entry() {
        return admin_entry;
    }

    public long getStart_event() {
        return start_event;
    }

    public boolean getEntry_can_leave() {
        return entry_can_leave;
    }

    public boolean getEntry_can_admin() {
        return entry_can_admin;
    }

    public boolean getEntry_can_invite() {
        return entry_can_invite;
    }

    public boolean getHas_cup() {
        return has_cup;
    }

    public String getCup_league() {
        return cup_league;
    }

    public String getCup_qualified() {
        return cup_qualified;
    }

    public long getEntry_rank() {
        return entry_rank;
    }

    public long getEntry_last_rank() {
        return entry_last_rank;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setMax_entries(String max_entries) {
        this.max_entries = max_entries;
    }

    public void setLeague_type(String league_type) {
        this.league_type = league_type;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public void setAdmin_entry(long admin_entry) {
        this.admin_entry = admin_entry;
    }

    public void setStart_event(long start_event) {
        this.start_event = start_event;
    }

    public void setEntry_can_leave(boolean entry_can_leave) {
        this.entry_can_leave = entry_can_leave;
    }

    public void setEntry_can_admin(boolean entry_can_admin) {
        this.entry_can_admin = entry_can_admin;
    }

    public void setEntry_can_invite(boolean entry_can_invite) {
        this.entry_can_invite = entry_can_invite;
    }

    public void setHas_cup(boolean has_cup) {
        this.has_cup = has_cup;
    }

    public void setCup_league(String cup_league) {
        this.cup_league = cup_league;
    }

    public void setCup_qualified(String cup_qualified) {
        this.cup_qualified = cup_qualified;
    }

    public void setEntry_rank(long entry_rank) {
        this.entry_rank = entry_rank;
    }

    public void setEntry_last_rank(long entry_last_rank) {
        this.entry_last_rank = entry_last_rank;
    }
}

class Cup {
    ArrayList < Object > matches = new ArrayList < Object > ();
    Status StatusObject;
    private String cup_league = null;


    // Getter Methods

    public Status getStatus() {
        return StatusObject;
    }

    public String getCup_league() {
        return cup_league;
    }

    // Setter Methods

    public void setStatus(Status statusObject) {
        this.StatusObject = statusObject;
    }

    public void setCup_league(String cup_league) {
        this.cup_league = cup_league;
    }
}
class Status {
    private String qualification_event = null;
    private String qualification_numbers = null;
    private String qualification_rank = null;
    private String qualification_state = null;


    // Getter Methods

    public String getQualification_event() {
        return qualification_event;
    }

    public String getQualification_numbers() {
        return qualification_numbers;
    }

    public String getQualification_rank() {
        return qualification_rank;
    }

    public String getQualification_state() {
        return qualification_state;
    }

    // Setter Methods

    public void setQualification_event(String qualification_event) {
        this.qualification_event = qualification_event;
    }

    public void setQualification_numbers(String qualification_numbers) {
        this.qualification_numbers = qualification_numbers;
    }

    public void setQualification_rank(String qualification_rank) {
        this.qualification_rank = qualification_rank;
    }

    public void setQualification_state(String qualification_state) {
        this.qualification_state = qualification_state;
    }
}
