package com.infotech.fplcolosseum.features.homepage.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekMatchDetails {
    ArrayList<MatchDetails> matchDetails = new ArrayList<>();

    public ArrayList<MatchDetails> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(ArrayList<MatchDetails> matchDetails) {
        this.matchDetails = matchDetails;
    }
}

class MatchDetails {
    private float code;
    private float event;
    private boolean finished;
    private boolean finished_provisional;
    private float id;
    private String kickoff_time;
    private float minutes;
    private boolean provisional_start_time;
    private boolean started;
    private float team_a;
    private float team_a_score;
    private float team_h;
    private float team_h_score;
    @SerializedName("stats")
    ArrayList < MatchStats > stats = new ArrayList < > ();
    private float team_h_difficulty;
    private float team_a_difficulty;
    private float pulse_id;


    // Getter Methods

    public float getCode() {
        return code;
    }

    public float getEvent() {
        return event;
    }

    public boolean getFinished() {
        return finished;
    }

    public boolean getFinished_provisional() {
        return finished_provisional;
    }

    public float getId() {
        return id;
    }

    public String getKickoff_time() {
        return kickoff_time;
    }

    public float getMinutes() {
        return minutes;
    }

    public boolean getProvisional_start_time() {
        return provisional_start_time;
    }

    public boolean getStarted() {
        return started;
    }

    public float getTeam_a() {
        return team_a;
    }

    public float getTeam_a_score() {
        return team_a_score;
    }

    public float getTeam_h() {
        return team_h;
    }

    public float getTeam_h_score() {
        return team_h_score;
    }

    public float getTeam_h_difficulty() {
        return team_h_difficulty;
    }

    public float getTeam_a_difficulty() {
        return team_a_difficulty;
    }

    public float getPulse_id() {
        return pulse_id;
    }

    // Setter Methods

    public void setCode(float code) {
        this.code = code;
    }

    public void setEvent(float event) {
        this.event = event;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setFinished_provisional(boolean finished_provisional) {
        this.finished_provisional = finished_provisional;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setKickoff_time(String kickoff_time) {
        this.kickoff_time = kickoff_time;
    }

    public void setMinutes(float minutes) {
        this.minutes = minutes;
    }

    public void setProvisional_start_time(boolean provisional_start_time) {
        this.provisional_start_time = provisional_start_time;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setTeam_a(float team_a) {
        this.team_a = team_a;
    }

    public void setTeam_a_score(float team_a_score) {
        this.team_a_score = team_a_score;
    }

    public void setTeam_h(float team_h) {
        this.team_h = team_h;
    }

    public void setTeam_h_score(float team_h_score) {
        this.team_h_score = team_h_score;
    }

    public void setTeam_h_difficulty(float team_h_difficulty) {
        this.team_h_difficulty = team_h_difficulty;
    }

    public void setTeam_a_difficulty(float team_a_difficulty) {
        this.team_a_difficulty = team_a_difficulty;
    }

    public void setPulse_id(float pulse_id) {
        this.pulse_id = pulse_id;
    }
}

class MatchStats {
    private String identifier;
    @SerializedName("a")
    ArrayList < MatchElement > a = new ArrayList < > ();
    @SerializedName("h")
    ArrayList < MatchElement > h = new ArrayList < > ();


    // Getter Methods

    public String getIdentifier() {
        return identifier;
    }

    // Setter Methods

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}

class MatchElement {
    private float value;
    private float element;


    // Getter Methods

    public float getValue() {
        return value;
    }

    public float getElement() {
        return element;
    }

    // Setter Methods

    public void setValue(float value) {
        this.value = value;
    }

    public void setElement(float element) {
        this.element = element;
    }
}