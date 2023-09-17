package com.infotech.fplcolosseum.repository;

import static com.infotech.fplcolosseum.remote.APIHandler.callAPI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.models.web.LeagueGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.web.TeamDataModel;
import com.infotech.fplcolosseum.remote.APIServices;
import com.infotech.fplcolosseum.remote.RetroClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class GameWeekRepository {
    APIServices apiServices;

    public GameWeekRepository() {
        apiServices = RetroClass.getAPIService(); // set API
    }

    public LiveData<List<ManagerModel>> getManagerList(String leagueID) {

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);

        LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel = callAPI(callAPI, LeagueGameWeekDataModel.class);

        return Transformations.map(leagueGameWeekDataModel, complexData -> {
            if (complexData != null) {
                return filterMangers(complexData);
            }
            return null; // Handle the case where complexData is null
        });
    }

    public List<ManagerModel> filterMangers(LeagueGameWeekDataModel leagueGameWeekDataModel) {
        List<ManagerModel> managerModels = new ArrayList<>();

        List<TeamDataModel> teamDataModels = leagueGameWeekDataModel.getTeamDatas();

        for (TeamDataModel teamDataModel : leagueGameWeekDataModel.getTeamDatas()) {
            ManagerModel managerModel = new ManagerModel();
            managerModel.setId(teamDataModel.getEntryId());
            managerModel.setManagerName(teamDataModel.getPlayerName());
            managerModel.setTeamName(teamDataModel.getName());
            managerModels.add(managerModel);
        }
        return managerModels;
    }

    public LiveData<CustomGameWeekDataModel> gameWeekDataFromAPI(String leagueID, String entryID, String currentGameweek, String currentPage) {

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        queryParams.put("entry", entryID);
        queryParams.put("currentweek", currentGameweek);
        queryParams.put("currentPage", currentPage);
        Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);

        LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel = callAPI(callAPI, LeagueGameWeekDataModel.class);

        LiveData<CustomGameWeekDataModel> customDataLiveData = Transformations.map(leagueGameWeekDataModel, complexData -> {
            if (complexData != null) {
                return convertToCustomModel(complexData);
            }
            return null; // Handle the case where complexData is null
        });
        return customDataLiveData;
    }

    public CustomGameWeekDataModel convertToCustomModel(LeagueGameWeekDataModel leagueGameWeekDataModel) {
        CustomGameWeekDataModel customGameWeekDataModel = new CustomGameWeekDataModel();
        //set league informations
        customGameWeekDataModel.setLeagueId(leagueGameWeekDataModel.getLeagueId());
        customGameWeekDataModel.setLeagueName(leagueGameWeekDataModel.getLeagueName());
        customGameWeekDataModel.setCurrentGameweek(leagueGameWeekDataModel.getCurrentGameweek());

        //set teams data
        customGameWeekDataModel.setLeagueId(leagueGameWeekDataModel.getLeagueId());
        return customGameWeekDataModel;
    }
}
