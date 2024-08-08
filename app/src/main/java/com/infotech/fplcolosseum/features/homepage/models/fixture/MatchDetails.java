package com.infotech.fplcolosseum.features.homepage.models.fixture;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MatchDetails {
    private long code;
    private long event;
    private boolean finished;
    private boolean finished_provisional;
    private long id;
    private String kickoff_time;
    private long minutes;
    private boolean provisional_start_time;
    private boolean started;
    private long team_a;
    private long team_a_score;
    private long team_h;
    private long team_h_score;
    @SerializedName("stats")
    ArrayList<MatchStats> stats = new ArrayList<>();
    private long team_h_difficulty;
    private long team_a_difficulty;
    private long pulse_id;

    public ArrayList<MatchStats> getStats() {
        return stats;
    }

    public void setStats(ArrayList<MatchStats> stats) {
        this.stats = stats;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isProvisional_start_time() {
        return provisional_start_time;
    }

    public boolean isFinished_provisional() {
        return finished_provisional;
    }

    public boolean isFinished() {
        return finished;
    }

    // Getter Methods

    public long getCode() {
        return code;
    }

    public long getEvent() {
        return event;
    }

    public boolean getFinished() {
        return finished;
    }

    public boolean getFinished_provisional() {
        return finished_provisional;
    }

    public long getId() {
        return id;
    }

    public String getKickoff_time() {
        return kickoff_time;
    }

    public long getMinutes() {
        return minutes;
    }

    public boolean getProvisional_start_time() {
        return provisional_start_time;
    }

    public boolean getStarted() {
        return started;
    }

    public long getTeam_a() {
        return team_a;
    }

    public long getTeam_a_score() {
        return team_a_score;
    }

    public long getTeam_h() {
        return team_h;
    }

    public long getTeam_h_score() {
        return team_h_score;
    }

    public long getTeam_h_difficulty() {
        return team_h_difficulty;
    }

    public long getTeam_a_difficulty() {
        return team_a_difficulty;
    }

    public long getPulse_id() {
        return pulse_id;
    }

    // Setter Methods

    public void setCode(long code) {
        this.code = code;
    }

    public void setEvent(long event) {
        this.event = event;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setFinished_provisional(boolean finished_provisional) {
        this.finished_provisional = finished_provisional;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setKickoff_time(String kickoff_time) {
        this.kickoff_time = kickoff_time;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public void setProvisional_start_time(boolean provisional_start_time) {
        this.provisional_start_time = provisional_start_time;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setTeam_a(long team_a) {
        this.team_a = team_a;
    }

    public void setTeam_a_score(long team_a_score) {
        this.team_a_score = team_a_score;
    }

    public void setTeam_h(long team_h) {
        this.team_h = team_h;
    }

    public void setTeam_h_score(long team_h_score) {
        this.team_h_score = team_h_score;
    }

    public void setTeam_h_difficulty(long team_h_difficulty) {
        this.team_h_difficulty = team_h_difficulty;
    }

    public void setTeam_a_difficulty(long team_a_difficulty) {
        this.team_a_difficulty = team_a_difficulty;
    }

    public void setPulse_id(long pulse_id) {
        this.pulse_id = pulse_id;
    }
}
