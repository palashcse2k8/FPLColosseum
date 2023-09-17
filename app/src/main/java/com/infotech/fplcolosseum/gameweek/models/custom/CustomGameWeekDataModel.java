package com.infotech.fplcolosseum.gameweek.models.custom;

import java.util.ArrayList;
import java.util.List;

public class CustomGameWeekDataModel {
    private String leagueId;
    private String leagueName;
    private float currentGameweek;
    private float numberOfTeams;
    List<ManagerModel> teams = new ArrayList<>();

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public float getCurrentGameweek() {
        return currentGameweek;
    }

    public void setCurrentGameweek(float currentGameweek) {
        this.currentGameweek = currentGameweek;
    }

    public float getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(float numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public List<ManagerModel> getTeams() {
        return teams;
    }

    public void setTeams(List<ManagerModel> teams) {
        this.teams = teams;
    }
}
