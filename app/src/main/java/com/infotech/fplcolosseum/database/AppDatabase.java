package com.infotech.fplcolosseum.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.infotech.fplcolosseum.database.dao.GameWeekDBDao;
import com.infotech.fplcolosseum.database.dataconverter.ManagerModelConverter;
import com.infotech.fplcolosseum.database.dataconverter.PlayerModelConverter;
import com.infotech.fplcolosseum.database.entities.CustomGameWeekDataEntity;
import com.orhanobut.logger.Logger;

@Database(entities = {CustomGameWeekDataEntity.class}, version = 1, exportSchema = false)
@TypeConverters({ManagerModelConverter.class, PlayerModelConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "GameWeekDataDB";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
//                Logger.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
//        Logger.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract GameWeekDBDao dbDao();
}