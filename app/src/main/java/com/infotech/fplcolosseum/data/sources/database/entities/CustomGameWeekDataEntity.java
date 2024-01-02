package com.infotech.fplcolosseum.data.sources.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.infotech.fplcolosseum.data.sources.database.dataconverter.ManagerModelConverter;
import com.infotech.fplcolosseum.features.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.features.gameweek.models.custom.ManagerModel;

import java.util.List;

@Entity(tableName = "GAMEWEEKDATA")
public class CustomGameWeekDataEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String leagueId;
    private String leagueName;
    private long gameWeek;
    private long currentGameweek;
    private long numberOfTeams;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
