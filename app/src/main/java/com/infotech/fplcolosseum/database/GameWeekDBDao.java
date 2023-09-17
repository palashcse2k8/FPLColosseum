package com.infotech.fplcolosseum.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.infotech.fplcolosseum.gameweek.models.web.LeagueGameWeekDataModel;

import java.util.List;

@Dao
public interface GameWeekDBDao {

    @Query("SELECT * FROM GAMEWEEKDATA")
    List<LeagueGameWeekDataModel> loadAllGameWeekData();

    @Insert
    void insertGameWeekData(LeagueGameWeekDataModel gameWeekDataModel);

    @Update
    void updateGameWeekData(LeagueGameWeekDataModel gameWeekDataModel);

    @Delete
    void deleteGameWeekData(LeagueGameWeekDataModel gameWeekDataModel);

    @Query("SELECT * FROM GAMEWEEKDATA WHERE Gameweek = :id")
    LeagueGameWeekDataModel loadGameWeekDataById(float id);
}