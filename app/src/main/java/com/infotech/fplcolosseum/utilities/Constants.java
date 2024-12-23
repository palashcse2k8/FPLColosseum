package com.infotech.fplcolosseum.utilities;

import com.infotech.fplcolosseum.features.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.features.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.fixture.OpponentData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekEvent;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekStaticDataModel;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Player_Type;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.TeamData;
import com.infotech.fplcolosseum.features.login.models.UserResponseModel;

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
            "893479", // FPLC
            "118990" // srbd premier league
    };

    public static final String LOGIN_URL = "https://users.premierleague.com/accounts/login/";
    public static final String LOGOUT_URL = "https://users.premierleague.com/accounts/logout/";
    public static final String LOGIN_REDIRECT_URL = "https://fantasy.premierleague.com/";
    public static final String APP_NAME = "plfpl-web";

    public static UserResponseModel LoggedInUser = null;
    public static GameWeekStaticDataModel GameWeekStaticData;

    public static Map <Long, PlayersData> playerMap = new HashMap<>();
    public static Map<Long, Player_Type> playerTypeMap = new HashMap<>();
    public static Map <Long, TeamData> teamMap = new HashMap<>();
    public static Map<Long, GameWeekEvent> gameWeekMap = new HashMap<>();
    public static Map<String, String> elementStatMap = new HashMap<>();
    public static long currentGameWeek;
    public static long previousGameWeek;
    public static long nextGameWeek;


    public static Map <Long, Map<Long, OpponentData>> fixtureData = new HashMap<>();
    public static Map <Long, List<MatchDetails>> fixtures = new HashMap<>();

    public static String managerName;
    public static String teamName;

    //image link

}
