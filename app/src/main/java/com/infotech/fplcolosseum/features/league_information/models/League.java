package com.infotech.fplcolosseum.features.league_information.models;

public class League {
    private long id;
    private String name;
    private String created;
    private boolean closed;
    private String max_entries = null;
    private String league_type;
    private String scoring;
    private float admin_entry;
    private float start_event;
    private String code_privacy;
    private boolean has_cup;
    private String cup_league = null;
    private String rank = null;


    // Getter Methods

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreated() {
        return created;
    }

    public boolean getClosed() {
        return closed;
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

    public float getAdmin_entry() {
        return admin_entry;
    }

    public float getStart_event() {
        return start_event;
    }

    public String getCode_privacy() {
        return code_privacy;
    }

    public boolean getHas_cup() {
        return has_cup;
    }

    public String getCup_league() {
        return cup_league;
    }

    public String getRank() {
        return rank;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
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

    public void setAdmin_entry(float admin_entry) {
        this.admin_entry = admin_entry;
    }

    public void setStart_event(float start_event) {
        this.start_event = start_event;
    }

    public void setCode_privacy(String code_privacy) {
        this.code_privacy = code_privacy;
    }

    public void setHas_cup(boolean has_cup) {
        this.has_cup = has_cup;
    }

    public void setCup_league(String cup_league) {
        this.cup_league = cup_league;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
