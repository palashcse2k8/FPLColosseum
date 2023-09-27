package com.infotech.fplcolosseum.gameweek.models.web;

import java.util.ArrayList;
import java.util.List;

public class FixtureDatas {
    private float FixtureId;
    private String HomeTeamName;
    private String AwayTeamName;
    private String HomeTeamFullName;
    private String AwayTeamFullName;
    private String KickOffTime;
    private float PlayedMinutes;
    private boolean Started;
    private boolean Finished;
    private boolean FinishedProvisional;
    private float TeamHScore;
    private float TeamAScore;
    private float TeamA;
    private float TeamH;
    Object AllStatsDataObject;
    private String FixtureStatusText;

    public Object getAllStatsDataObject() {
        return AllStatsDataObject;
    }

    public void setAllStatsDataObject(Object allStatsDataObject) {
        AllStatsDataObject = allStatsDataObject;
    }
// Getter Methods

    public float getFixtureId() {
        return FixtureId;
    }

    public String getHomeTeamName() {
        return HomeTeamName;
    }

    public String getAwayTeamName() {
        return AwayTeamName;
    }

    public String getHomeTeamFullName() {
        return HomeTeamFullName;
    }

    public String getAwayTeamFullName() {
        return AwayTeamFullName;
    }

    public String getKickOffTime() {
        return KickOffTime;
    }

    public float getPlayedMinutes() {
        return PlayedMinutes;
    }

    public boolean getStarted() {
        return Started;
    }

    public boolean getFinished() {
        return Finished;
    }

    public boolean getFinishedProvisional() {
        return FinishedProvisional;
    }

    public float getTeamHScore() {
        return TeamHScore;
    }

    public float getTeamAScore() {
        return TeamAScore;
    }

    public float getTeamA() {
        return TeamA;
    }

    public float getTeamH() {
        return TeamH;
    }

    public String getFixtureStatusText() {
        return FixtureStatusText;
    }

// Setter Methods

    public void setFixtureId(float FixtureId) {
        this.FixtureId = FixtureId;
    }

    public void setHomeTeamName(String HomeTeamName) {
        this.HomeTeamName = HomeTeamName;
    }

    public void setAwayTeamName(String AwayTeamName) {
        this.AwayTeamName = AwayTeamName;
    }

    public void setHomeTeamFullName(String HomeTeamFullName) {
        this.HomeTeamFullName = HomeTeamFullName;
    }

    public void setAwayTeamFullName(String AwayTeamFullName) {
        this.AwayTeamFullName = AwayTeamFullName;
    }

    public void setKickOffTime(String KickOffTime) {
        this.KickOffTime = KickOffTime;
    }

    public void setPlayedMinutes(float PlayedMinutes) {
        this.PlayedMinutes = PlayedMinutes;
    }

    public void setStarted(boolean Started) {
        this.Started = Started;
    }

    public void setFinished(boolean Finished) {
        this.Finished = Finished;
    }

    public void setFinishedProvisional(boolean FinishedProvisional) {
        this.FinishedProvisional = FinishedProvisional;
    }

    public void setTeamHScore(float TeamHScore) {
        this.TeamHScore = TeamHScore;
    }

    public void setTeamAScore(float TeamAScore) {
        this.TeamAScore = TeamAScore;
    }

    public void setTeamA(float TeamA) {
        this.TeamA = TeamA;
    }

    public void setTeamH(float TeamH) {
        this.TeamH = TeamH;
    }

    public void setFixtureStatusText(String FixtureStatusText) {
        this.FixtureStatusText = FixtureStatusText;
    }
}