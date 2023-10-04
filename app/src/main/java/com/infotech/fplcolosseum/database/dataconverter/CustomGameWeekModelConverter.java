package com.infotech.fplcolosseum.database.dataconverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.infotech.fplcolosseum.database.entities.CustomGameWeekDataEntity;

public class CustomGameWeekModelConverter {
    @TypeConverter
    public static String fromCustomModel(CustomGameWeekDataEntity customModel) {
        // Convert CustomModel to JSON string using Gson
        Gson gson = new Gson();
        return gson.toJson(customModel);
    }

    @TypeConverter
    public static CustomGameWeekDataEntity toCustomModel(String json) {
        // Convert JSON string back to CustomModel using Gson
        Gson gson = new Gson();
        return gson.fromJson(json, CustomGameWeekDataEntity.class);
    }
}
