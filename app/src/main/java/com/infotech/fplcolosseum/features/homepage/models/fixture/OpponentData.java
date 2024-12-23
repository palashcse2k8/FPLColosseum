package com.infotech.fplcolosseum.features.homepage.models.fixture;

import java.io.Serializable;
import java.util.ArrayList;

public class OpponentData implements Serializable {

    private long teamID;
    private long opponentTeamId;
    private long difficulty;
    private String kickOffTime;
    private long minutesPlayed;
    private boolean isFinished;
    private long goalConceded;
    private long goalScored;
    private boolean isHome;
    private boolean isStarted;
    private ArrayList<MatchStats> stats = new ArrayList<>();

    public ArrayList<MatchStats> getStats() {
        return stats;
    }

    public void setStats(ArrayList<MatchStats> stats) {
        this.stats = stats;
    }

    public long getTeamID() {
        return teamID;
    }

    public void setTeamID(long teamID) {
        this.teamID = teamID;
    }

    public long getOpponentTeamId() {
        return opponentTeamId;
    }

    public void setOpponentTeamId(long opponentTeamId) {
        this.opponentTeamId = opponentTeamId;
    }

    public long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public String getKickOffTime() {
        return kickOffTime;
    }

    public void setKickOffTime(String kickOffTime) {
        this.kickOffTime = kickOffTime;
    }

    public long getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(long minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public long getGoalConceded() {
        return goalConceded;
    }

    public void setGoalConceded(long goalConceded) {
        this.goalConceded = goalConceded;
    }

    public long getGoalScored() {
        return goalScored;
    }

    public void setGoalScored(long goalScored) {
        this.goalScored = goalScored;
    }

    public boolean isHome() {
        return isHome;
    }

    public void setHome(boolean home) {
        isHome = home;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
