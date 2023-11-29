package com.infotech.fplcolosseum.utilities;

import com.infotech.fplcolosseum.features.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.features.gameweek.models.custom.ManagerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    public static final String FUNCTION_KEY = "db059d47-8b44-476a-9dfc-509bceb87bee";
    public static Map<String, CustomGameWeekDataModel> customGameWeekDataModels = new HashMap<>();
    public static List<ManagerModel> managerList = new ArrayList<>();

    public static String LOG_TAG = "FPlColosseum";
    public static String [] leagues = new String [] {
            "671887", // FPLC
            "118990" // srbd premier league
    };

    public static final String LOGIN_URL = "https://users.premierleague.com/accounts/login/";
    public static final String LOGIN_REDIRECT_URL = "https://fantasy.premierleague.com/";
    public static final String APP_NAME = "plfpl-web";

    public static int counter;

    //image link

}
