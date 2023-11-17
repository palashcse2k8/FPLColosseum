package com.infotech.fplcolosseum.repository;

import static com.infotech.fplcolosseum.remote.APIHandler.callAPI;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.infotech.fplcolosseum.database.AppDatabase;
import com.infotech.fplcolosseum.database.AppExecutors;
import com.infotech.fplcolosseum.database.dao.GameWeekDBDao;
import com.infotech.fplcolosseum.database.entities.CustomGameWeekDataEntity;
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
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class GameWeekRepository {
    GameWeekDBDao gameWeekDBDao;
    APIServices apiServices;
    AppExecutors appExecutors;
//    private MediatorLiveData<List<ManagerModel>> _managerList;

    private List<PlayerDataModel> gameWeekPlayerList;
    private List<PlayerDataModel> gameWeekPlayerListWithData;

    private Map<Float, List<Float>> managerWithPlayerList;

    // Map to associate manager IDs with their LiveData
    private Map<Float, MediatorLiveData<List<PlayerDataModel>>> managerLiveDataMap;

    private int totalPlayerAPICount;

    public GameWeekRepository(Application application) {
        apiServices = RetroClass.getAPIService(application); // set API
//        _managerList = new MediatorLiveData<>();

        gameWeekDBDao = AppDatabase.getInstance(application).dbDao();
        appExecutors = AppExecutors.getInstance();
        initAllGameWeekTempData();
    }

    public void initAllGameWeekTempData() {
        gameWeekPlayerList = new ArrayList<>();
        gameWeekPlayerListWithData = new ArrayList<>();
        managerWithPlayerList = new LinkedHashMap<>();
        managerLiveDataMap = new LinkedHashMap<>();
        totalPlayerAPICount = 0;
    }

    public LiveData<List<ManagerModel>> getManagerList(String leagueID, String currentGameweek, String currentPage) {

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        queryParams.put("currentweek", currentGameweek);
        queryParams.put("currentPage", currentPage);
        queryParams.put("sortOrder", "orderByNet");
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

        List<TeamDataResponseModel> customModelList = leagueGameWeekDataModel.getTeamDatas();

        if (leagueGameWeekDataModel.getTeamDatas().size() > 5) {
            customModelList = leagueGameWeekDataModel.getTeamDatas().subList(0, 10);
        }

        AtomicInteger managerApiCallCount = new AtomicInteger(customModelList.size());

//        for (TeamDataResponseModel teamDataResponseModel : leagueGameWeekDataModel.getTeamDatas()) {
        for (TeamDataResponseModel teamDataResponseModel : customModelList) {

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

//                    if (managerModel.getManagerName().contains("Sami")) {
//                        Log.d(Constants.LOG_TAG, "Manager Info -> " + managerModel.getManagerName() + ", gameweek : " + managerModel.getGameWeek());
//                    }
                    managerModel.setPlayersAll(result);

//                    Log.d(Constants.LOG_TAG, "Manager Info -> " + managerModel.getManagerName() + ", gameweek : " + managerModel.getGameWeek());

                    float bonusPoints = 0;
                    float benchPoints = 0;
                    float goalScored = 0;
                    float goalConceded = 0;
                    float bpsPoints = 0;
                    PlayerDataModel model;

                    //update captain and vice captain points
                    for (int i = 0; i < result.size(); i++) {
                        model = result.get(i);
//                        Log.d(Constants.LOG_TAG, "Player Info-> " + model.toString());

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
                        if (i < 11) {
                            bonusPoints += model.getBonusPoints();
                            goalScored += model.getGoalScored();
                            bpsPoints += model.getBPSPoints();

                            //get goal conceded information
                            for (FixtureDatas fixtureDatas : leagueGameWeekDataModel.getFixtureDatas()) {
                                if (fixtureDatas.getFixtureId() == model.getFixtureID()) {
                                    if (fixtureDatas.getAwayTeamName().equalsIgnoreCase(model.getTeamName()))
                                        goalConceded += fixtureDatas.getTeamHScore();
                                    else if (fixtureDatas.getHomeTeamName().equalsIgnoreCase(model.getTeamName()))
                                        goalConceded += fixtureDatas.getTeamAScore();
//                                    else Log.d(Constants.LOG_TAG, "No team found");
                                }
                            }

                        } else {
//                            Log.d(Constants.LOG_TAG, "adding bench point");
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
                    managerApiCallCount.decrementAndGet();

                    // Check if all API calls have completed
                    if (managerApiCallCount.get() == 0) {
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

//        Logger.d("Getting Player List");
        LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel = callAPI(callAPI, LeagueGameWeekDataModel.class);

        return Transformations.switchMap(leagueGameWeekDataModel, complexData -> {
            if (complexData != null) {
                return filterPlayers(complexData);
            }
            return null; // Handle the case where complexData is null
        });
    }

    public LiveData<List<PlayerDataModel>> filterPlayers(LeagueGameWeekDataModel leagueGameWeekDataModel) {

        // Retrieve or create LiveData for the manager
        Map<Float, String> currentManagerPlayerList = new LinkedHashMap<>();

        MediatorLiveData<List<PlayerDataModel>> playerListMediatorLiveData = new MediatorLiveData<>();
        managerLiveDataMap.put(leagueGameWeekDataModel.getCompareEntryId(), playerListMediatorLiveData);

        Logger.d("Getting Player Data for manager " + leagueGameWeekDataModel.getLeagueName());

        for (PlayerResponseModel playerResponseModel : leagueGameWeekDataModel.getTeamDatas().get(0).getLiveData().getPlayers()) {

            currentManagerPlayerList.put(playerResponseModel.getId(), playerResponseModel.getPlayerWebName());

            if (!checkPlayerMatch(gameWeekPlayerList, playerResponseModel)) {

                PlayerDataModel playerDataModel = new PlayerDataModel();
                playerDataModel.setPlayerName(playerResponseModel.getPlayerWebName());
                playerDataModel.setPlayerID(playerResponseModel.getId());
                playerDataModel.setTeamName(playerResponseModel.getTeamName());
//                playerDataModel.setPoints(playerResponseModel.getTotalPoints());
                playerDataModel.setCaptain(playerResponseModel.getIsCaptain());
                playerDataModel.setViceCaptain(playerResponseModel.getIsViceCaptain());
                playerDataModel.setSub(playerResponseModel.getIsSub());
                playerDataModel.setSubIn(playerResponseModel.getIsSubIn());
                playerDataModel.setSubOut(playerResponseModel.getIsSubOut());
                playerDataModel.setMultiplier(playerResponseModel.getMultiplier());
                playerDataModel.setTeamName(playerResponseModel.getTeamName());

                gameWeekPlayerList.add(playerDataModel);
//            Logger.d("Getting Player Data for player " + playerDataModel.getPlayerName());

                totalPlayerAPICount++;

                LiveData<PlayerStatsResponseModel> playerStatsResponseModelLiveData = getPlayerData(String.valueOf((int) playerDataModel.getPlayerID()), String.valueOf((int) leagueGameWeekDataModel.getGameweek()));

                playerListMediatorLiveData.addSource(playerStatsResponseModelLiveData, result -> {
                    if (result != null) {
                        totalPlayerAPICount--;
                        playerDataModel.setPoints(result.getPoints());
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

//                        playerSemaphore.release();
                        if (totalPlayerAPICount == 0) {
//                            Log.d(Constants.LOG_TAG, "Unique PlayerCount " + "gameWeekPlayerList.size() : " + gameWeekPlayerList.size() + " totalPlayerAPICount : " + totalPlayerAPICount + "gameWeekPlayerListWithData.size() " + gameWeekPlayerListWithData.size());
                            MediatorLiveData<List<PlayerDataModel>> currentManagerPlayerListLiveData;
                            for (Float managerID : managerWithPlayerList.keySet()) {
                                currentManagerPlayerListLiveData = managerLiveDataMap.get(managerID);
                                List<PlayerDataModel> currentManagerPlayerListWithData = preparePlayersList(Objects.requireNonNull(managerWithPlayerList.get(managerID)));
                                if (currentManagerPlayerListWithData != null && currentManagerPlayerListWithData.size() == 15) {

                                    assert currentManagerPlayerListLiveData != null;
                                    currentManagerPlayerListLiveData.postValue(currentManagerPlayerListWithData);
//                                    List<PlayerDataModel> clonedList = clonePlayerDataList(currentManagerPlayerListWithData);
//                                    playerListMediatorLiveData.postValue(currentManagerPlayerListWithData);

                                } else {
                                    Log.d(Constants.LOG_TAG, "Data fetching issue");
                                }
                            }
                        }
                    }
                });
            }
        }
//        Log.d(Constants.LOG_TAG, "Adding player list for " + leagueGameWeekDataModel.getCompareEntryId());
//        printPlayerList(new ArrayList<>(currentManagerPlayerList.values()));
        managerWithPlayerList.put(leagueGameWeekDataModel.getCompareEntryId(), new ArrayList<>(currentManagerPlayerList.keySet()));
        return playerListMediatorLiveData;
    }

    private List<PlayerDataModel> clonePlayerDataList(List<PlayerDataModel> originalList) {
        List<PlayerDataModel> clonedList = new ArrayList<>();
        for (PlayerDataModel originalPlayer : originalList) {
            PlayerDataModel clonedPlayer = new PlayerDataModel();
            // Copy properties from originalPlayer to clonedPlayer
            clonedPlayer.setGameWeek(originalPlayer.getGameWeek());
//            clonedPlayer.setIsCaptain(originalPlayer.isCaptain());
//            clonedPlayer.setIsViceCaptain(originalPlayer.isViceCaptain());
            clonedPlayer.setPlayerID(originalPlayer.getPlayerID());
            clonedPlayer.setFixtureID(originalPlayer.getFixtureID());
            clonedPlayer.setPlayerName(originalPlayer.getPlayerName());
            clonedPlayer.setTeamName(originalPlayer.getTeamName());
            clonedPlayer.setPlayerPositionName(originalPlayer.getPlayerPositionName());
            clonedPlayer.setPoints(originalPlayer.getPoints());
            clonedPlayer.setGoalScored(originalPlayer.getGoalScored());
            clonedPlayer.setGoalConceded(originalPlayer.getGoalConceded());
            clonedPlayer.setBonusPoints(originalPlayer.getBonusPoints());
            clonedPlayer.setBPSPoints(originalPlayer.getBPSPoints());
            clonedPlayer.setMultiplier(originalPlayer.getMultiplier());
            clonedPlayer.setSub(originalPlayer.isSub());
            clonedPlayer.setSubIn(originalPlayer.isSubIn());
            clonedPlayer.setSubOut(originalPlayer.isSubOut());
            // Copy other properties...

            clonedList.add(clonedPlayer);
        }
        return clonedList;
    }

    public void printPlayerList(List<String> list) {
        for (String player : list) {
            Log.d(Constants.LOG_TAG, "Player Name:" + player);
        }
    }

    public List<PlayerDataModel> preparePlayersList(List<Float> currentManagerPlayerList) {

        if (currentManagerPlayerList.size() != 15) {
            return null;
        }

        List<PlayerDataModel> currentPlayerListWithData = new ArrayList<>();

        for (float id : currentManagerPlayerList) {
            for (PlayerDataModel model : gameWeekPlayerListWithData) {
                if (id == model.getPlayerID()) {
                    currentPlayerListWithData.add(model);
                    break;
                }
            }
        }
        if (currentPlayerListWithData.size() != 15) {
            Logger.d("Player count is mismatched!");
        }

        return currentPlayerListWithData;
    }


    public static boolean checkPlayerMatch
            (List<PlayerDataModel> playerList, PlayerResponseModel playerDataModel) {

        for (PlayerDataModel model : playerList) {
            if (model.getPlayerID() == playerDataModel.getId()) {
//                    Logger.d("Player Found " + playerDataModel.getPlayerWebName());
                return true;
            }
        }
        return false;
    }

    public static boolean checkPlayerAdded
            (List<PlayerDataModel> playerList, PlayerStatsResponseModel playerDataModel) {

        for (PlayerDataModel model : playerList) {
            if (model.getPlayerName().equalsIgnoreCase(playerDataModel.getPlayerWebName())) {
                Logger.d("Player Found " + playerDataModel.getPlayerWebName());
                return true;
            }
        }
        return false;
    }

    public LiveData<PlayerStatsResponseModel> getPlayerData(String playerId, String
            currentGameweek) {

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("playerId", playerId);
        queryParams.put("gameweek", currentGameweek);
        Call<ResponseBody> callAPI = apiServices.getPlayerData(queryParams);

        return callAPI(callAPI, PlayerStatsResponseModel.class);
    }

    public LiveData<List<ManagerModel>> getMangersData(String leagueID, String currentGameweek) {

        MediatorLiveData<List<ManagerModel>> _managerList = new MediatorLiveData<>();

        // Source 1
        LiveData<List<ManagerModel>> source1 = getManagerList(leagueID, currentGameweek, "1");

        _managerList.addSource(source1, managerModels -> {
            if (managerModels != null) {

                _managerList.postValue(managerModels);

//                 Source 1 is completed, now call Source 2
//                if (leagueID.equalsIgnoreCase("671887"))
//                    callSource2(_managerList, leagueID, currentGameweek);
            }
        });

        return _managerList;
    }

    private void callSource2(MediatorLiveData<List<ManagerModel>> _managerList, String
            leagueID, String currentGameweek) {
        // Source 2

        Logger.d("Getting 2nd List");
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

    public LiveData<CustomGameWeekDataModel> getGameWeekData(String leagueID, String
            currentGameweek) {

        // Create LiveData for the custom model
        LiveData<CustomGameWeekDataEntity> customGameWeekDataEntityLiveData = gameWeekDBDao.loadGameWeekDataById(leagueID, currentGameweek);

        // MediatorLiveData to combine data from API and database
        MediatorLiveData<CustomGameWeekDataModel> gameWeekDataModelMediatorLiveData = new MediatorLiveData<>();

        // Add a source for database data
        gameWeekDataModelMediatorLiveData.addSource(customGameWeekDataEntityLiveData, customGameWeekDataEntity -> {
            if (customGameWeekDataEntity != null) {
                // Convert the entity to the custom model
                CustomGameWeekDataModel customGameWeekDataModel = new CustomGameWeekDataModel(customGameWeekDataEntity);
                Logger.d("Getting Data From ROOM DB");
                gameWeekDataModelMediatorLiveData.postValue(customGameWeekDataModel);
            } else {
                // Data not available in the database, fetch it from the API
                try {
                    Logger.d("Getting Data From API");
                    getGameWeekDataFromAPI(leagueID, currentGameweek, gameWeekDataModelMediatorLiveData);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return gameWeekDataModelMediatorLiveData;
    }

    public void getGameWeekDataFromAPI(String leagueID, String
            currentGameweek, MediatorLiveData<CustomGameWeekDataModel> gameWeekDataModelMediatorLiveData) throws
            IOException {


        initAllGameWeekTempData();
//        MediatorLiveData<CustomGameWeekDataModel> gameWeekDataModelMediatorLiveData = new MediatorLiveData<>();
        CustomGameWeekDataModel customGameWeekDataModel = new CustomGameWeekDataModel();

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        queryParams.put("currentweek", currentGameweek);
        Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);

        LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel = callAPI(callAPI, LeagueGameWeekDataModel.class);

        //set league information
        gameWeekDataModelMediatorLiveData.addSource(leagueGameWeekDataModel, leagueGameWeekDataModel1 -> {
            customGameWeekDataModel.setLeagueId(leagueGameWeekDataModel1.getLeagueId());
            customGameWeekDataModel.setLeagueName(leagueGameWeekDataModel1.getLeagueName());
            customGameWeekDataModel.setGameWeek(leagueGameWeekDataModel1.getGameweek());
            customGameWeekDataModel.setCurrentGameweek(leagueGameWeekDataModel1.getCurrentGameweek());
        });

        LiveData<List<ManagerModel>> listManagersLiveData = getMangersData(leagueID, currentGameweek);

        gameWeekDataModelMediatorLiveData.addSource(listManagersLiveData, modelList -> {
            customGameWeekDataModel.setTeams(modelList);
//            Log.d(Constants.LOG_TAG, "Unique Players are " + gameWeekPlayerList.size());
//            for (PlayerDataModel model : gameWeekPlayerList) {
//                Log.d(Constants.LOG_TAG, model.getPlayerName());
//            }

//            gameWeekDataModelMediatorLiveData.postValue(customGameWeekDataModel);
            CustomGameWeekDataEntity gameWeekDataEntity = new CustomGameWeekDataEntity(customGameWeekDataModel);
            insertGameWeekDataToDB(gameWeekDataEntity);
            gameWeekDataModelMediatorLiveData.postValue(customGameWeekDataModel);
        });
    }

    public void insertGameWeekDataToDB(CustomGameWeekDataEntity gameWeekDataEntity) {

//        Logger.d("Inserting data to room db");
        appExecutors.diskIO().submit(() -> gameWeekDBDao.insertGameWeekData(gameWeekDataEntity));
    }

    public void deleteGameWeekData(String leagueID, String currentGameweek) {

        //delete row data from table
        appExecutors.diskIO().submit(() -> gameWeekDBDao.deleteGameWeekDataById(leagueID, currentGameweek));
    }

    public void deleteAllGameWeekData() {

        //delete all row data from table
        appExecutors.diskIO().submit(() -> gameWeekDBDao.deleteAllGameData());
    }

}
