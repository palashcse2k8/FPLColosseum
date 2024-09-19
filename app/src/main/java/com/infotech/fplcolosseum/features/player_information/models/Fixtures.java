package com.infotech.fplcolosseum.features.player_information.models;

public class Fixtures {
    private float id;
    private float code;
    private long team_h;
    private String team_h_score = null;
    private long team_a;
    private String team_a_score = null;
    private long event;
    private boolean finished;
    private float minutes;
    private boolean provisional_start_time;
    private String kickoff_time;
    private String event_name;
    private boolean is_home;
    private long difficulty;


    // Getter Methods 

    public float getId() {
        return id;
    }

    public float getCode() {
        return code;
    }

    public long getTeam_h() {
        return team_h;
    }

    public String getTeam_h_score() {
        return team_h_score;
    }

    public long getTeam_a() {
        return team_a;
    }

    public String getTeam_a_score() {
        return team_a_score;
    }

    public long getEvent() {
        return event;
    }

    public boolean getFinished() {
        return finished;
    }

    public float getMinutes() {
        return minutes;
    }

    public boolean getProvisional_start_time() {
        return provisional_start_time;
    }

    public String getKickoff_time() {
        return kickoff_time;
    }

    public String getEvent_name() {
        return event_name;
    }

    public boolean getIs_home() {
        return is_home;
    }

    public long getDifficulty() {
        return difficulty;
    }

    // Setter Methods 

    public void setId(float id) {
        this.id = id;
    }

    public void setCode(float code) {
        this.code = code;
    }

    public void setTeam_h(long team_h) {
        this.team_h = team_h;
    }

    public void setTeam_h_score(String team_h_score) {
        this.team_h_score = team_h_score;
    }

    public void setTeam_a(long team_a) {
        this.team_a = team_a;
    }

    public void setTeam_a_score(String team_a_score) {
        this.team_a_score = team_a_score;
    }

    public void setEvent(long event) {
        this.event = event;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setMinutes(float minutes) {
        this.minutes = minutes;
    }

    public void setProvisional_start_time(boolean provisional_start_time) {
        this.provisional_start_time = provisional_start_time;
    }

    public void setKickoff_time(String kickoff_time) {
        this.kickoff_time = kickoff_time;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public void setIs_home(boolean is_home) {
        this.is_home = is_home;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }
}

