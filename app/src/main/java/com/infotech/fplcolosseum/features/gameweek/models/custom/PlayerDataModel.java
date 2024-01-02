package com.infotech.fplcolosseum.features.gameweek.models.custom;

import androidx.annotation.NonNull;

public class PlayerDataModel {
    private long GameWeek;
    private boolean IsCaptain;
    private boolean IsViceCaptain;
    private long PlayerID;
    private long FixtureID;

    public long getFixtureID() {
        return FixtureID;
    }

    public void setFixtureID(long fixtureID) {
        FixtureID = fixtureID;
    }

    private String PlayerName;
    private String TeamName;
    private String PlayerPositionName;

    public String getPlayerPositionName() {
        return PlayerPositionName;
    }

    public void setPlayerPositionName(String playerPositionName) {
        PlayerPositionName = playerPositionName;
    }

    private long Points;
    private long GoalScored;
    private long GoalConceded;
    private long BonusPoints;
    private long BPSPoints;

    private long Multiplier;

    private boolean IsSub;
    private boolean IsSubIn;
    private boolean IsSubOut;

    public long getMultiplier() {
        return Multiplier;
    }

    public void setMultiplier(long multiplier) {
        Multiplier = multiplier;
    }

    public boolean isSub() {
        return IsSub;
    }

    public void setSub(boolean sub) {
        IsSub = sub;
    }

    public boolean isSubIn() {
        return IsSubIn;
    }

    public void setSubIn(boolean subIn) {
        IsSubIn = subIn;
    }

    public boolean isSubOut() {
        return IsSubOut;
    }

    public void setSubOut(boolean subOut) {
        IsSubOut = subOut;
    }

    public boolean isCaptain() {
        return IsCaptain;
    }

    public void setCaptain(boolean captain) {
        IsCaptain = captain;
    }

    public boolean isViceCaptain() {
        return IsViceCaptain;
    }

    public void setViceCaptain(boolean viceCaptain) {
        IsViceCaptain = viceCaptain;
    }

    public long getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(long playerID) {
        PlayerID = playerID;
    }

    public long getGameWeek() {
        return GameWeek;
    }

    public void setGameWeek(long gameWeek) {
        GameWeek = gameWeek;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public long getPoints() {
        return Points;
    }

    public void setPoints(long points) {
        Points = points;
    }

    public long getGoalScored() {
        return GoalScored;
    }

    public void setGoalScored(long goalScored) {
        GoalScored = goalScored;
    }

    public long getGoalConceded() {
        return GoalConceded;
    }

    public void setGoalConceded(long goalConceded) {
        GoalConceded = goalConceded;
    }

    public long getBonusPoints() {
        return BonusPoints;
    }

    public void setBonusPoints(long bonusPoints) {
        BonusPoints = bonusPoints;
    }

    public long getBPSPoints() {
        return BPSPoints;
    }

    public void setBPSPoints(long BPSPoints) {
        this.BPSPoints = BPSPoints;
    }

    @NonNull
    @Override
    public String toString() {
        return "PlayerDataModel{" +
                "GameWeek=" + GameWeek +
                ", IsCaptain=" + IsCaptain +
                ", IsViceCaptain=" + IsViceCaptain +
                ", PlayerID=" + PlayerID +
                ", PlayerName='" + PlayerName + '\'' +
                ", TeamName='" + TeamName + '\'' +
                ", Points=" + Points +
                ", GoalScored=" + GoalScored +
                ", GoalConceded=" + GoalConceded +
                ", BonusPoints=" + BonusPoints +
                ", BPSPoints=" + BPSPoints +
                ", GoalScored=" + GoalScored +
                ", GoalConceded=" + GoalConceded +
                ", Multiplier=" + Multiplier +
                ", IsSub=" + IsSub +
                ", IsSubIn=" + IsSubIn +
                ", IsSubOut=" + IsSubOut +
                '}';
    }
}
