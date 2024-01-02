package com.infotech.fplcolosseum.features.gameweek.models.web;

import java.util.ArrayList;
import java.util.List;

public class FixtureDatas {
    private long FixtureId;
    private String HomeTeamName;
    private String AwayTeamName;
    private String HomeTeamFullName;
    private String AwayTeamFullName;
    private String KickOffTime;
    private long PlayedMinutes;
    private boolean Started;
    private boolean Finished;
    private boolean FinishedProvisional;
    private long TeamHScore;
    private long TeamAScore;
    private long TeamA;
    private long TeamH;
    Object AllStatsDataObject;
    private String FixtureStatusText;

    public Object getAllStatsDataObject() {
        return AllStatsDataObject;
    }

    public void setAllStatsDataObject(Object allStatsDataObject) {
        AllStatsDataObject = allStatsDataObject;
    }
// Getter Methods

    public long getFixtureId() {
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

    public long getPlayedMinutes() {
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

    public long getTeamHScore() {
        return TeamHScore;
    }

    public long getTeamAScore() {
        return TeamAScore;
    }

    public long getTeamA() {
        return TeamA;
    }

    public long getTeamH() {
        return TeamH;
    }

    public String getFixtureStatusText() {
        return FixtureStatusText;
    }

// Setter Methods

    public void setFixtureId(long FixtureId) {
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

    public void setPlayedMinutes(long PlayedMinutes) {
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

    public void setTeamHScore(long TeamHScore) {
        this.TeamHScore = TeamHScore;
    }

    public void setTeamAScore(long TeamAScore) {
        this.TeamAScore = TeamAScore;
    }

    public void setTeamA(long TeamA) {
        this.TeamA = TeamA;
    }

    public void setTeamH(long TeamH) {
        this.TeamH = TeamH;
    }

    public void setFixtureStatusText(String FixtureStatusText) {
        this.FixtureStatusText = FixtureStatusText;
    }
}