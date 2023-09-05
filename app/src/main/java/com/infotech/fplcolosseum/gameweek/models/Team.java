package com.infotech.fplcolosseum.gameweek.models;

public class Team {
    private String teamName;
    private String managerName;
    private int gameWeekPoints;
    private int totalPoints;

    public Team(String teamName, String managerName, int gameWeekPoints, int totalPoints) {
        this.teamName = teamName;
        this.managerName = managerName;
        this.gameWeekPoints = gameWeekPoints;
        this.totalPoints = totalPoints;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getGameWeekPoints() {
        return gameWeekPoints;
    }

    public void setGameWeekPoints(int gameWeekPoints) {
        this.gameWeekPoints = gameWeekPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}
