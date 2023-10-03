package com.infotech.fplcolosseum.database.dataconverter;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.models.web.GameWeekLiveData;

import java.lang.reflect.Type;
import java.util.List;

public class ManagerModelConverter {

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

    @TypeConverter
    public static List<ManagerModel> fromString(String value) {
        if (value == null) {
            return null;
        }
        Type listType = new TypeToken<List<ManagerModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<ManagerModel> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}