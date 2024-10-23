package com.infotech.fplcolosseum.features.homepage.models.entryinformation;

public class LeagueDataModel {
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
