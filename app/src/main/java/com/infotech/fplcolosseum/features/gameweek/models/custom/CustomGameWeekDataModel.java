package com.infotech.fplcolosseum.features.gameweek.models.custom;

import com.infotech.fplcolosseum.data.sources.database.entities.CustomGameWeekDataEntity;

import java.util.ArrayList;
import java.util.List;

public class CustomGameWeekDataModel {
    private String leagueId;
    private String leagueName;
    private long gameWeek;
    private long currentGameweek;
    private long numberOfTeams;

    public CustomGameWeekDataModel() {}

    public CustomGameWeekDataModel(String leagueId, String leagueName, long gameWeek, long currentGameweek, long numberOfTeams, List<ManagerModel> teams) {
        this.leagueId = leagueId;
        this.leagueName = leagueName;
        this.gameWeek = gameWeek;
        this.currentGameweek = currentGameweek;
        this.numberOfTeams = numberOfTeams;
        this.teams = teams;
    }

    public CustomGameWeekDataModel(CustomGameWeekDataEntity gameWeekDataEntity) {
        this.leagueId = gameWeekDataEntity.getLeagueId();
        this.leagueName = gameWeekDataEntity.getLeagueName();
        this.gameWeek = gameWeekDataEntity.getGameWeek();
        this.currentGameweek = gameWeekDataEntity.getCurrentGameweek();
        this.numberOfTeams = gameWeekDataEntity.getNumberOfTeams();
        this.teams = gameWeekDataEntity.getTeams();
    }

    List<ManagerModel> teams = new ArrayList<>();

    public long getGameWeek() {
        return gameWeek;
    }

    public void setGameWeek(long gameWeek) {
        this.gameWeek = gameWeek;
    }

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

    public long getCurrentGameweek() {
        return currentGameweek;
    }

    public void setCurrentGameweek(long currentGameweek) {
        this.currentGameweek = currentGameweek;
    }

    public long getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(long numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public List<ManagerModel> getTeams() {
        return teams;
    }

    public void setTeams(List<ManagerModel> teams) {
        this.teams = teams;
    }
}
