package com.infotech.fplcolosseum.gameweek.models.custom;

public class PlayerDataModel {
    private float GameWeek;

    private boolean IsCaptain;
    private boolean IsViceCaptain;
    private float PlayerID;
    private String PlayerName;
    private String TeamName;
    private float Points;
    private float GoalScored;
    private float GoalConceded;
    private float BonusPoints;
    private float BPSPoints;

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

    public float getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(float playerID) {
        PlayerID = playerID;
    }

    public float getGameWeek() {
        return GameWeek;
    }

    public void setGameWeek(float gameWeek) {
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

    public float getPoints() {
        return Points;
    }

    public void setPoints(float points) {
        Points = points;
    }

    public float getGoalScored() {
        return GoalScored;
    }

    public void setGoalScored(float goalScored) {
        GoalScored = goalScored;
    }

    public float getGoalConceded() {
        return GoalConceded;
    }

    public void setGoalConceded(float goalConceded) {
        GoalConceded = goalConceded;
    }

    public float getBonusPoints() {
        return BonusPoints;
    }

    public void setBonusPoints(float bonusPoints) {
        BonusPoints = bonusPoints;
    }

    public float getBPSPoints() {
        return BPSPoints;
    }

    public void setBPSPoints(float BPSPoints) {
        this.BPSPoints = BPSPoints;
    }
}
