package com.infotech.fplcolosseum.database;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infotech.fplcolosseum.gameweek.models.web.GameWeekLiveData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataConverter {
    @TypeConverter
    public static String fromList(List<?> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<?>>() {}.getType();
        return gson.toJson(list, type);
    }

    @TypeConverter
    public static List<Object> toList(String json) {
        if (json == null) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Object>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String fromGameWeekLiveData(GameWeekLiveData gameWeekLiveData) {
        if (gameWeekLiveData == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(gameWeekLiveData);
    }

    @TypeConverter
    public static GameWeekLiveData toGameWeekLiveData(String json) {
        if (json == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, GameWeekLiveData.class);
    }
}