package com.infotech.fplcolosseum.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.infotech.fplcolosseum.database.dataconverter.ManagerModelConverter;
import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;

import java.util.List;

@Entity(tableName = "GAMEWEEKDATA")
public class CustomGameWeekDataEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String leagueId;
    private String leagueName;
    private float gameWeek;
    private float currentGameweek;
    private float numberOfTeams;
    //    @Ignore // Mark this field as transient
//    @Relation(parentColumn = "id", entityColumn = "id")
    @TypeConverters(ManagerModelConverter.class)
    List<ManagerModel> teams;

    //Default Constructor
    public CustomGameWeekDataEntity() {

    }

    public CustomGameWeekDataEntity(CustomGameWeekDataModel gameWeekDataModel) {
        this.leagueId = gameWeekDataModel.getLeagueId();
        this.leagueName = gameWeekDataModel.getLeagueName();
        this.gameWeek = gameWeekDataModel.getGameWeek();
        this.currentGameweek = gameWeekDataModel.getCurrentGameweek();
        this.numberOfTeams = gameWeekDataModel.getNumberOfTeams();
        this.teams = gameWeekDataModel.getTeams();
    }

    public float getGameWeek() {
        return gameWeek;
    }

    public void setGameWeek(float gameWeek) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
