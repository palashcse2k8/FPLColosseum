package com.infotech.fplcolosseum.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.infotech.fplcolosseum.database.entities.CustomGameWeekDataEntity;
import com.infotech.fplcolosseum.gameweek.models.web.LeagueGameWeekDataModel;

import java.util.List;

@Dao
public interface GameWeekDBDao {

    @Query("SELECT * FROM GAMEWEEKDATA")
    List<CustomGameWeekDataEntity> loadAllGameWeekData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGameWeekData(CustomGameWeekDataEntity gameWeekDataEntity);

    @Update
    void updateGameWeekData(CustomGameWeekDataEntity gameWeekDataEntity);

    @Delete
    void deleteGameWeekData(CustomGameWeekDataEntity gameWeekDataEntity);

    @Query("SELECT * FROM GAMEWEEKDATA WHERE leagueId = :leagueId AND Gameweek = :id")
    LiveData<CustomGameWeekDataEntity> loadGameWeekDataById(String leagueId, String id);

    @Query("DELETE FROM GAMEWEEKDATA WHERE leagueId = :leagueId AND Gameweek = :id")
    public void deleteGameWeekDataById(String leagueId, String id);
    @Query("DELETE FROM GAMEWEEKDATA")
    public void deleteAllGameData();
}