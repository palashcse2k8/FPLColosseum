package com.infotech.fplcolosseum.database.dataconverter;

import android.util.Log;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.models.web.GameWeekLiveData;
import com.infotech.fplcolosseum.utilities.Constants;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class ManagerModelConverter {

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