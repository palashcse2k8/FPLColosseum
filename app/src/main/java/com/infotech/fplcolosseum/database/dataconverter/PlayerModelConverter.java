package com.infotech.fplcolosseum.database.dataconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.models.custom.PlayerDataModel;
import com.infotech.fplcolosseum.gameweek.models.web.GameWeekLiveData;

import java.lang.reflect.Type;
import java.util.List;

public class PlayerModelConverter {

    @TypeConverter
    public static List<PlayerDataModel> fromString(String value) {
        if (value == null) {
            return null;
        }
        Type listType = new TypeToken<List<PlayerDataModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<PlayerDataModel> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}