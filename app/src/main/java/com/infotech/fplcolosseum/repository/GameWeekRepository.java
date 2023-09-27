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
import com.infotech.fplcolosseum.gameweek.models.web.FixtureDatas;
import com.infotech.fplcolosseum.gameweek.models.web.LeagueGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.web.PlayerPointsDatas;
import com.infotech.fplcolosseum.gameweek.models.web.PlayerResponseModel;
import com.infotech.fplcolosseum.gameweek.models.web.PlayerStatsResponseModel;
import com.infotech.fplcolosseum.gameweek.models.web.TeamDataResponseModel;
import com.infotech.fplcolosseum.remote.APIServices;
import com.infotech.fplcolosseum.remote.RetroClass;
import com.infotech.fplcolosseum.utilities.Constants;

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
//    private MediatorLiveData<List<ManagerModel>> _managerList;

    private LiveData<List<PlayerDataModel>> playersLiveData;

    private List<PlayerDataModel> gameWeekPlayerList;
    private List<PlayerDataModel> gameWeekPlayerListWithData;

    public GameWeekRepository() {
        apiServices = RetroClass.getAPIService(); // set API
//        _managerList = new MediatorLiveData<>();
        playersLiveData = new MutableLiveData<>();
        gameWeekPlayerList = new ArrayList<>();
        gameWeekPlayerListWithData = new ArrayList<>();
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
            managerModel.setGameWeek(leagueGameWeekDataModel.getGameweek());
            managerModel.setCaptainName(teamDataResponseModel.getLiveData().getCaptainWebName());
            managerModel.setViceCaptainName(teamDataResponseModel.getLiveData().getViceCaptainWebName());

            // Set the manager's player list to an empty list initially
            managerModel.setPlayersAll(new ArrayList<>());

            // Fetch the player data for the current manager
            String managerID = String.valueOf((int) managerModel.getId());
            String currentGameweek = String.valueOf((int) leagueGameWeekDataModel.getGameweek());

            LiveData<List<PlayerDataModel>> playerListLiveData = getManagerData(managerID, currentGameweek);

            managerModelsLiveData.addSource(playerListLiveData, result -> {
                if (result != null) {
                    managerModel.setPlayersAll(result);

                    Log.d(Constants.LOG_TAG, "Manager Info -> " + managerModel.getManagerName() + ", gameweek : " + managerModel.getGameWeek());

                    float bonusPoints = 0;
                    float benchPoints = 0;
                    float goalScored = 0;
                    float goalConceded = 0;
                    float bpsPoints = 0;
                    PlayerDataModel model;

                    //update captain and vice captain points
                    for (int i=0; i<result.size(); i++) {
                        model = result.get(i);
                        Log.d(Constants.LOG_TAG, "Player Info-> " + model.toString());

                        //set first rules
                        if (model.getPlayerName().equalsIgnoreCase(managerModel.getCaptainName())) {
                            managerModel.setCaptainGameWeekPoints(model.getPoints());
//                            managerModel.setCaptainName(model.getPlayerName());
                        }

                        //set 2nd rules
                        if (model.getPlayerName().equalsIgnoreCase(managerModel.getViceCaptainName())) {
                            managerModel.setViceCaptainGameWeekPoints(model.getPoints());
//                            managerModel.setViceCaptainName(model.getPlayerName());
                        }

                        //set bench point and bonus point
                        if (i<11) {
                            bonusPoints += model.getBonusPoints();
                            goalScored += model.getGoalScored();
                            bpsPoints += model.getBPSPoints();

                            //get goal conceded information
                            for (FixtureDatas fixtureDatas: leagueGameWeekDataModel.getFixtureDatas()) {
                                if(fixtureDatas.getFixtureId() == model.getFixtureID()){
                                    if(fixtureDatas.getAwayTeamName().equalsIgnoreCase(model.getTeamName()))
                                        goalConceded += fixtureDatas.getTeamHScore();
                                    else if(fixtureDatas.getHomeTeamName().equalsIgnoreCase(model.getTeamName()))
                                        goalConceded += fixtureDatas.getTeamAScore();
                                    else Log.d(Constants.LOG_TAG, "No team found");
                                }
                            }

                        } else {
                            Log.d(Constants.LOG_TAG, "adding bench point");
                            benchPoints += model.getPoints();
                        }
                    }

                    //setting data for the manager
                    managerModel.setGameWeekBonusPointsXI(bonusPoints);
                    managerModel.setGameWeekBenchPoints(benchPoints);
                    managerModel.setGoalScored(goalScored);
                    managerModel.setGoalConceded(goalConceded);
                    managerModel.setGameWeekBPSPointsXI(bpsPoints);

//                    Log.d(Constants.LOG_TAG, "Manager Info -> bonusPoints : " + bonusPoints + ", benchepoint : " + benchPoints);
                    managerModels.add(managerModel);
                    apiCallCount.getAndDecrement();

                    // Check if all API calls have completed
                    if (apiCallCount.get() == 0) {
                        // All API calls have completed, combine results and set value
                        managerModelsLiveData.postValue(managerModels);
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

//        Log.d(Constants.LOG_TAG, "Getting Player List");
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

        List<Float> currentPlayerList = new ArrayList<>();

        AtomicInteger apiCallCount = new AtomicInteger(leagueGameWeekDataModel.getTeamDatas().get(0).getLiveData().getPlayers().size());

        for (PlayerResponseModel playerResponseModel : leagueGameWeekDataModel.getTeamDatas().get(0).getLiveData().getPlayers()) {

            currentPlayerList.add(playerResponseModel.getId());
            if (checkPlayerMatch(gameWeekPlayerList, playerResponseModel)) {
                apiCallCount.getAndDecrement();
            } else {
                PlayerDataModel playerDataModel = new PlayerDataModel();
                playerDataModel.setPlayerName(playerResponseModel.getPlayerWebName());
                playerDataModel.setPlayerID(playerResponseModel.getId());
                playerDataModel.setTeamName(playerResponseModel.getTeamName());
                playerDataModel.setPoints(playerResponseModel.getTotalPoints());
                playerDataModel.setCaptain(playerResponseModel.getIsCaptain());
                playerDataModel.setViceCaptain(playerResponseModel.getIsViceCaptain());
                playerDataModel.setSub(playerResponseModel.getIsSub());
                playerDataModel.setSubIn(playerResponseModel.getIsSubIn());
                playerDataModel.setSubOut(playerResponseModel.getIsSubOut());
                playerDataModel.setMultiplier(playerResponseModel.getMultiplier());
                playerDataModel.setTeamName(playerResponseModel.getTeamName());

                gameWeekPlayerList.add(playerDataModel);
//                Log.d(Constants.LOG_TAG, "Getting Player Data for " + playerDataModel.getPlayerName());

                LiveData<PlayerStatsResponseModel> playerStatsResponseModelLiveData = getPlayerData(String.valueOf((int) playerDataModel.getPlayerID()), String.valueOf((int) leagueGameWeekDataModel.getGameweek()));

                playerListMediatorLiveData.addSource(playerStatsResponseModelLiveData, result -> {
                    if (result != null) {
                        playerDataModel.setPlayerPositionName(result.getPlayerPositionName());
                        List<PlayerPointsDatas> list = result.getPlayerPointsDatas();
                        for (PlayerPointsDatas playerPointsDatas : list) {
                            playerDataModel.setFixtureID(playerPointsDatas.getFixtureId());
                            if (playerPointsDatas.getKey().equalsIgnoreCase("bonus")) {
                                playerDataModel.setBonusPoints(playerPointsDatas.getPoints());
                                playerDataModel.setBPSPoints(playerPointsDatas.getAmount());
                            }
                            if (playerPointsDatas.getKey().equalsIgnoreCase("goals_scored")) {
                                playerDataModel.setGoalScored(playerPointsDatas.getAmount());
                            }
                        }
                        gameWeekPlayerListWithData.add(playerDataModel);

                        // Check if all API calls have completed
                        apiCallCount.getAndDecrement();
                        if (apiCallCount.get() == 0) {

                            List<PlayerDataModel> currentPlayerListWithData = new ArrayList<>();

                            for (float id : currentPlayerList) {
                                for (PlayerDataModel model : gameWeekPlayerListWithData) {
                                    if (id == model.getPlayerID()) {
                                        currentPlayerListWithData.add(model);
                                    }
                                }
                            }
                            // All API calls have completed, combine results and set value
                            playerListMediatorLiveData.postValue(currentPlayerListWithData);
                        }
                    }
                });
            }
        }

        return playerListMediatorLiveData;
    }

    public static boolean checkPlayerMatch(List<PlayerDataModel> playerList, PlayerResponseModel playerDataModel) {
        if (playerList.size() != 0) {
            for (PlayerDataModel model : playerList) {
                if (model.getPlayerID() == playerDataModel.getId()) {
//                    Log.d(Constants.LOG_TAG, "Player Found " + playerDataModel.getPlayerWebName());
                    return true;
                }
            }
        }
        return false;
    }

    public LiveData<PlayerStatsResponseModel> getPlayerData(String playerId, String currentGameweek) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("playerId", playerId);
        queryParams.put("gameweek", currentGameweek);
        Call<ResponseBody> callAPI = apiServices.getPlayerData(queryParams);

        return callAPI(callAPI, PlayerStatsResponseModel.class);
    }

    public LiveData<List<ManagerModel>> getMangersData(String leagueID, String currentGameweek) throws IOException {

        MediatorLiveData<List<ManagerModel>> _managerList = new MediatorLiveData<>();
        // Source 1
        LiveData<List<ManagerModel>> source1 = getManagerList(leagueID, currentGameweek, "1");
        _managerList.addSource(source1, managerModels -> {
            if (managerModels != null) {
                _managerList.postValue(managerModels);

                // Source 1 is completed, now call Source 2
//                if (leagueID.equalsIgnoreCase("671887"))
//                    callSource2(leagueID, currentGameweek, "2");
            }
        });

        return _managerList;
    }

//    private void callSource2(String leagueID, String currentGameweek, String currentPage) {
//        // Source 2
//        LiveData<List<ManagerModel>> source2 = getManagerList(leagueID, currentGameweek, currentPage);
//        _managerList.addSource(source2, managerModels -> {
//            if (managerModels != null) {
//                // Append data from Source 2 to _managerList
//                List<ManagerModel> currentData = _managerList.getValue();
//                if (currentData != null) {
//                    currentData.addAll(managerModels);
//                    _managerList.postValue(currentData);
//                }
//            }
//        });
//    }

    public LiveData<CustomGameWeekDataModel> getGameWeekDataFromAPI(String leagueID, String currentGameweek) throws IOException {

        gameWeekPlayerList = new ArrayList<>();
        gameWeekPlayerListWithData = new ArrayList<>();
        MediatorLiveData<CustomGameWeekDataModel> gameWeekDataModelMediatorLiveData = new MediatorLiveData<>();
        CustomGameWeekDataModel customGameWeekDataModel = new CustomGameWeekDataModel();

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        queryParams.put("currentweek", currentGameweek);
        Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);

        LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel = callAPI(callAPI, LeagueGameWeekDataModel.class);

        //set league informations
        gameWeekDataModelMediatorLiveData.addSource(leagueGameWeekDataModel, leagueGameWeekDataModel1 -> {
            customGameWeekDataModel.setLeagueId(leagueGameWeekDataModel1.getLeagueId());
            customGameWeekDataModel.setLeagueName(leagueGameWeekDataModel1.getLeagueName());
            customGameWeekDataModel.setCurrentGameweek(leagueGameWeekDataModel1.getCurrentGameweek());
        });

        LiveData<List<ManagerModel>> listManagersLiveData = getMangersData(leagueID, currentGameweek);

        gameWeekDataModelMediatorLiveData.addSource(listManagersLiveData, modelList -> {
            customGameWeekDataModel.setTeams(modelList);
//            Log.d(Constants.LOG_TAG, "Unique Players are " + gameWeekPlayerList.size());
//            for (PlayerDataModel model : gameWeekPlayerList) {
//                Log.d(Constants.LOG_TAG, model.getPlayerName());
//            }
            gameWeekDataModelMediatorLiveData.postValue(customGameWeekDataModel);
        });

        //set teams data
        return gameWeekDataModelMediatorLiveData;
    }

}