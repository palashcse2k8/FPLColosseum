package com.infotech.fplcolosseum.features.cup_status.model;

public class CupStatusResponseModel {
    private long qualifying_league;
    private String qualifying_method;
    private String draw_type;
    private String league = null;
    private long qualification_event;
    private long qualification_numbers;
    private String name;
    private boolean has_byes;
    private boolean is_large;
    private String pulse_article_id = null;


    // Getter Methods

    public long getQualifying_league() {
        return qualifying_league;
    }

    public String getQualifying_method() {
        return qualifying_method;
    }

    public String getDraw_type() {
        return draw_type;
    }

    public String getLeague() {
        return league;
    }

    public long getQualification_event() {
        return qualification_event;
    }

    public long getQualification_numbers() {
        return qualification_numbers;
    }

    public String getName() {
        return name;
    }

    public boolean getHas_byes() {
        return has_byes;
    }

    public boolean getIs_large() {
        return is_large;
    }

    public String getPulse_article_id() {
        return pulse_article_id;
    }

    // Setter Methods

    public void setQualifying_league(long qualifying_league) {
        this.qualifying_league = qualifying_league;
    }

    public void setQualifying_method(String qualifying_method) {
        this.qualifying_method = qualifying_method;
    }

    public void setDraw_type(String draw_type) {
        this.draw_type = draw_type;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setQualification_event(long qualification_event) {
        this.qualification_event = qualification_event;
    }

    public void setQualification_numbers(long qualification_numbers) {
        this.qualification_numbers = qualification_numbers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHas_byes(boolean has_byes) {
        this.has_byes = has_byes;
    }

    public void setIs_large(boolean is_large) {
        this.is_large = is_large;
    }

    public void setPulse_article_id(String pulse_article_id) {
        this.pulse_article_id = pulse_article_id;
    }
}
