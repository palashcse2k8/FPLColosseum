package com.infotech.fplcolosseum.repository;

import static com.infotech.fplcolosseum.remote.APIHandler.callAPI;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.gameweek.models.custom.PlayerDataModel;
import com.infotech.fplcolosseum.gameweek.models.web.LeagueGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.web.PlayerPointsDatas;
import com.infotech.fplcolosseum.gameweek.models.web.PlayerResponseModel;
import com.infotech.fplcolosseum.gameweek.models.web.PlayerStatsResponseModel;
import com.infotech.fplcolosseum.gameweek.models.web.TeamDataResponseModel;
import com.infotech.fplcolosseum.remote.APIServices;
import com.infotech.fplcolosseum.remote.RetroClass;
import com.infotech.fplcolosseum.utilities.StaticConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class GameWeekRepository {
    APIServices apiServices;

    private MediatorLiveData<List<ManagerModel>> _managerList;
    private LiveData<List<PlayerDataModel>> playersLiveData;

    public GameWeekRepository() {
        apiServices = RetroClass.getAPIService(); // set API
        _managerList = new MediatorLiveData<>();
        playersLiveData = new MutableLiveData<>();
    }

    public LiveData<List<ManagerModel>> getManagerList(String leagueID, String currentGameweek, String currentPage) {

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        queryParams.put("currentweek", currentGameweek);
        queryParams.put("currentPage", currentPage);
        Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);

        LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel = callAPI(callAPI, LeagueGameWeekDataModel.class);

        return Transformations.switchMap(leagueGameWeekDataModel, complexData -> {
            if (complexData != null) {
                return filterMangers(complexData);
            }
            return null; // Handle the case where complexData is null
        });
    }

    //    public LiveData<List<ManagerModel>> filterMangers(LeagueGameWeekDataModel leagueGameWeekDataModel) {
//
//        MutableLiveData<List<ManagerModel>> managerModelsLiveData = new MutableLiveData<>();
//        List<ManagerModel> managerModels = new ArrayList<>();
//
//        for (TeamDataResponseModel teamDataModel : leagueGameWeekDataModel.getTeamDatas()) {
//            ManagerModel managerModel = new ManagerModel();
//            managerModel.setId(teamDataModel.getEntryId());
//            managerModel.setManagerName(teamDataModel.getPlayerName());
//            managerModel.setTeamName(teamDataModel.getName());
//            managerModel.setGameWeekPoints(teamDataModel.getLiveData().getLivePointsTotal());
//            managerModel.setGameWeekPointsWithoutTransferCost(teamDataModel.getLiveData().getLivePointsTotalIncTransferCost());
//            managerModel.setSeasonTotalPoints(teamDataModel.getLiveData().getSeasonTotalPoints());
//
//
//            String managerID = String.valueOf((int)managerModel.getId());
//            String currentGameweek = String.valueOf((int)leagueGameWeekDataModel.getCurrentGameweek());
//
//            //get gameweek player list for this manager
//            LiveData<List<PlayerDataModel>> playerListLiveData = getManagerData( managerID, currentGameweek);
//
//            // Observe the player data LiveData and update the manager's player list when data is received
//            playerListLiveData.observeForever(playerDataModels -> {
//                managerModel.setPlayersAll(playerDataModels);
//                // Notify that the manager data has changed
//                managerModelsLiveData.setValue(managerModels);
//            });
//            managerModel.setPlayersAll(playerListLiveData.getValue());
//            managerModels.add(managerModel);
//        }
//
//        managerModelsLiveData.setValue(managerModels);
//        return managerModelsLiveData;
//    }
    public LiveData<List<ManagerModel>> filterMangers(LeagueGameWeekDataModel leagueGameWeekDataModel) {
        MediatorLiveData<List<ManagerModel>> managerModelsLiveData = new MediatorLiveData<>();
        List<ManagerModel> managerModels = new ArrayList<>();

        AtomicInteger apiCallCount = new AtomicInteger(leagueGameWeekDataModel.getTeamDatas().size());
        for (TeamDataResponseModel teamDataResponseModel : leagueGameWeekDataModel.getTeamDatas()) {
            ManagerModel managerModel = new ManagerModel();
            managerModel.setId(teamDataResponseModel.getEntryId());
            managerModel.setManagerName(teamDataResponseModel.getPlayerName());
            managerModel.setTeamName(teamDataResponseModel.getName());
            managerModel.setGameWeekPoints(teamDataResponseModel.getLiveData().getLivePointsTotal());
            managerModel.setGameWeekPointsWithoutTransferCost(teamDataResponseModel.getLiveData().getLivePointsTotalIncTransferCost());
            managerModel.setSeasonTotalPoints(teamDataResponseModel.getLiveData().getSeasonTotalPoints());

            // Set the manager's player list to an empty list initially
            managerModel.setPlayersAll(new ArrayList<>());

            // Fetch the player data for the current manager
            String managerID = String.valueOf((int) managerModel.getId());
            String currentGameweek = String.valueOf((int) leagueGameWeekDataModel.getCurrentGameweek());

            LiveData<List<PlayerDataModel>> playerListLiveData = getManagerData(managerID, currentGameweek);

            managerModelsLiveData.addSource(playerListLiveData, result -> {
                if (result != null) {
                    managerModel.setPlayersAll(result);
                    managerModels.add(managerModel);
                    apiCallCount.getAndDecrement();

                    // Check if all API calls have completed
                    if (apiCallCount.get() == 0) {
                        // All API calls have completed, combine results and set value
                        managerModelsLiveData.setValue(managerModels);
                    }
                }
            });
        }

        return managerModelsLiveData;
    }

    public LiveData<List<PlayerDataModel>> getManagerData(String managerId, String currentGameweek) {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", "entry_" + managerId);
        queryParams.put("currentweek", currentGameweek);
        Call<ResponseBody> callAPI = apiServices.getManagerData(queryParams);

        Log.d(StaticConstants.LOG_TAG, "Getting Player List");
        LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel = callAPI(callAPI, LeagueGameWeekDataModel.class);

        return Transformations.switchMap(leagueGameWeekDataModel, complexData -> {
            if (complexData != null) {
                return filterPlayers(complexData);
            }
            return null; // Handle the case where complexData is null
        });
    }

    public LiveData<List<PlayerDataModel>> filterPlayers(LeagueGameWeekDataModel leagueGameWeekDataModel) {

        MediatorLiveData<List<PlayerDataModel>> playerListMediatorLiveData = new MediatorLiveData<>();
        List<PlayerDataModel> playerList = new ArrayList<>();
        AtomicInteger apiCallCount = new AtomicInteger(leagueGameWeekDataModel.getTeamDatas().get(0).getLiveData().getPlayers().size());
        for (PlayerResponseModel playerResponseModel : leagueGameWeekDataModel.getTeamDatas().get(0).getLiveData().getPlayers()) {

            PlayerDataModel playerDataModel = new PlayerDataModel();
            playerDataModel.setPlayerName(playerResponseModel.getPlayerWebName());
            playerDataModel.setPlayerID(playerResponseModel.getId());
            playerDataModel.setTeamName(playerResponseModel.getTeamName());
            playerDataModel.setPoints(playerResponseModel.getTotalPoints());
            playerDataModel.setCaptain(playerResponseModel.getIsCaptain());
            playerDataModel.setViceCaptain(playerResponseModel.getIsViceCaptain());

            Log.d(StaticConstants.LOG_TAG, "Getting Player Data");
            LiveData<PlayerStatsResponseModel> playerStatsResponseModelLiveData = getPlayerData(String.valueOf((int)playerDataModel.getPlayerID()), String.valueOf((int)leagueGameWeekDataModel.getGameweek()));

            playerListMediatorLiveData.addSource(playerStatsResponseModelLiveData, result -> {
                if (result != null) {
                    List<PlayerPointsDatas> list = result.getPlayerPointsDatas();
                    for(PlayerPointsDatas playerPointsDatas: list) {
                        if(playerPointsDatas.getKey().equalsIgnoreCase("bonus")) {
                            playerDataModel.setBonusPoints(playerPointsDatas.getPoints());
                        }
                    }
                    playerList.add(playerDataModel);

                    // Check if all API calls have completed
                    apiCallCount.getAndDecrement();
                    if (apiCallCount.get() == 0) {
                        // All API calls have completed, combine results and set value
                        playerListMediatorLiveData.setValue(playerList);
                    }
                }
            });
        }

        return playerListMediatorLiveData;
    }

    public LiveData<PlayerStatsResponseModel> getPlayerData(String playerId, String currentGameweek) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("playerId", playerId);
        queryParams.put("gameweek", currentGameweek);
        Call<ResponseBody> callAPI = apiServices.getPlayerData(queryParams);

        return callAPI(callAPI, PlayerStatsResponseModel.class);
    }

//    public LiveData<CustomGameWeekDataModel> gameWeekDataFromAPI(String leagueID, String entryID, String currentGameweek, String currentPage) throws IOException {
//
//        // Create a Map to hold the query parameters
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("leagueId", leagueID);
//        queryParams.put("entry", entryID);
//        queryParams.put("currentweek", currentGameweek);
//        queryParams.put("currentPage", currentPage);
//        Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);
//
//        playersLiveData = Transformations.switchMap(_managerList, managers-> {
//            if (managers != null && !managers.isEmpty()) {
//            // Fetch player data for each manager
//            List<LiveData<List<PlayerDataModel>>> playerLiveDataList = new ArrayList<>();
//            for (ManagerModel manager : managers) {
//                LiveData<List<PlayerDataModel>> playerData = getManagerData(String.valueOf(manager.getId()), "1");
//                playerLiveDataList.add(playerData);
//            }
//
//            // Combine the LiveData objects into a single LiveData
////            return LiveDataMerger.merge(playerLiveDataList);
//        } else {
//            // Handle the case where managers list is null or empty
//            return new MutableLiveData<>();
//        }
//    });
//
//        LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel = callAPI(callAPI, LeagueGameWeekDataModel.class);
//
//        MutableLiveData<CustomGameWeekDataModel> customGameWeekDataModelMutableLiveData = new MutableLiveData<>();
//        customGameWeekDataModelMutableLiveData =
//
//        return customDataLiveData;
//    }

    public LiveData<List<ManagerModel>> gameMangerListFromAPI(String leagueID, String currentGameweek, String currentPage) throws IOException {

        // Source 1
        LiveData<List<ManagerModel>> source1 = getManagerList(leagueID, currentGameweek, currentPage);
        _managerList.addSource(source1, managerModels -> {
            if (managerModels != null) {
                _managerList.postValue(managerModels);

                // Source 1 is completed, now call Source 2
                callSource2(leagueID, currentGameweek, currentPage);
            }
        });

        return _managerList;
    }

    private void callSource2(String leagueID, String currentGameweek, String currentPage) {
        // Source 2
        LiveData<List<ManagerModel>> source2 = getManagerList(leagueID, currentGameweek, "2");
        _managerList.addSource(source2, managerModels -> {
            if (managerModels != null) {
                // Append data from Source 2 to _managerList
                List<ManagerModel> currentData = _managerList.getValue();
                if (currentData != null) {
                    currentData.addAll(managerModels);
                    _managerList.postValue(currentData);
                }
            }
        });
    }

    public CustomGameWeekDataModel convertToCustomModel(LeagueGameWeekDataModel leagueGameWeekDataModel) {
        CustomGameWeekDataModel customGameWeekDataModel = new CustomGameWeekDataModel();
        //set league informations
        customGameWeekDataModel.setLeagueId(leagueGameWeekDataModel.getLeagueId());
        customGameWeekDataModel.setLeagueName(leagueGameWeekDataModel.getLeagueName());
        customGameWeekDataModel.setCurrentGameweek(leagueGameWeekDataModel.getCurrentGameweek());
        customGameWeekDataModel.setTeams(_managerList.getValue());
//        gameMangerListFromAPI(leagueID, currentGameweek, currentPage);

        //set teams data
        return customGameWeekDataModel;
    }

}
