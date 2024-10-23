package com.infotech.fplcolosseum.features.homepage.viewmodels;

import static com.infotech.fplcolosseum.utilities.CustomUtil.prepareData;
import static com.infotech.fplcolosseum.utilities.CustomUtil.updateFixtureData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.homepage.models.MyTeamMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.PointsMergedResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.TeamInformationResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamUpdateModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekTransferUpdateModel;
import com.infotech.fplcolosseum.features.homepage.models.picks.GameWeekPicksModel;
import com.infotech.fplcolosseum.features.homepage.models.status.StatusMergedResponseModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.concurrent.CountDownLatch;

public class HomePageSharedViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;

    private final MediatorLiveData<ApiResponse<GameWeekMyTeamResponseModel>> myTeamApiResultLiveData;
    private final MediatorLiveData<ApiResponse<TeamInformationResponseModel>> teamInformationApiResultLiveData;
    private final MediatorLiveData<ApiResponse<?>> transferApiResultLiveData;

    private final MutableLiveData<ApiResponse<GameWeekPicksModel>> gameWeekPicksApiResultLiveData;

    private final MediatorLiveData<ApiResponse<?>> fixtureApiResultLiveData;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> dataLoadingDone;

    private final MediatorLiveData<ApiResponse<MyTeamMergedResponseModel>> myTeamMergedMediatorLiveData;
    private final MediatorLiveData<ApiResponse<PointsMergedResponseModel>> pointsMergedMediatorLiveData;
    private final MediatorLiveData<ApiResponse<StatusMergedResponseModel>> statusMergedMediatorLiveData;
    CountDownLatch latch;

    private MutableLiveData<String> toolbarTitle;
    private MutableLiveData<String> toolbarSubTitle;
    private MutableLiveData<String> previousFragment;

    public HomePageSharedViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.myTeamApiResultLiveData = new MediatorLiveData<>();
        this.transferApiResultLiveData = new MediatorLiveData<>();
        gameWeekPicksApiResultLiveData = new MediatorLiveData<>();
        this.fixtureApiResultLiveData = new MediatorLiveData<>();
        myTeamMergedMediatorLiveData = new MediatorLiveData<>();
        teamInformationApiResultLiveData = new MediatorLiveData<>();
        pointsMergedMediatorLiveData = new MediatorLiveData<>();
        statusMergedMediatorLiveData = new MediatorLiveData<>();
        toolbarTitle = new MutableLiveData<>();
        toolbarSubTitle = new MutableLiveData<>();
        previousFragment = new MutableLiveData<>();
    }

    public void getMyTeamMergedData(long managerID) {

        MyTeamMergedResponseModel myTeamMergedResponseModel = new MyTeamMergedResponseModel();
        dataLoading.setValue(true);

        myTeamMergedMediatorLiveData.addSource(dataRepository.getGameWeekStaticData(), gameWeekStaticDataModelApiResponse -> {
            myTeamMergedResponseModel.setGameWeekStaticDataModel(gameWeekStaticDataModelApiResponse.getData());

            prepareData(gameWeekStaticDataModelApiResponse.getData()); //update static data

            //call manager information with league data
            myTeamMergedMediatorLiveData.addSource(dataRepository.getTeamInformation(managerID),
                    teamInformationResponseModelApiResponse -> {
                        long currentGameWeek = teamInformationResponseModelApiResponse.getData().getCurrent_event();
                        Constants.currentGameWeek = currentGameWeek;
                        Constants.previousGameWeek = currentGameWeek - 1;
                        Constants.nextGameWeek = currentGameWeek + 1;
                        Constants.teamName = teamInformationResponseModelApiResponse.getData().getName();
                        Constants.managerName = teamInformationResponseModelApiResponse.getData().getPlayer_first_name() + " " + teamInformationResponseModelApiResponse.getData().getPlayer_last_name();

                        myTeamMergedResponseModel.setTeamInformationResponseModel(teamInformationResponseModelApiResponse.getData());
                        teamInformationApiResultLiveData.setValue(teamInformationResponseModelApiResponse); //save game week data

                        //call game week live points api
                        myTeamMergedMediatorLiveData.addSource(dataRepository.getMyTeamData(managerID),
                                gameWeekMyTeamResponseModelApiResponse -> {
                                    myTeamMergedResponseModel.setGameWeekMyTeamResponseModel(gameWeekMyTeamResponseModelApiResponse.getData());
                                    myTeamApiResultLiveData.setValue(gameWeekMyTeamResponseModelApiResponse);

                                    myTeamMergedMediatorLiveData.addSource(dataRepository.getAllFixtureData(1),
                                            gameWeekMatchDetailsApiResponse -> {

                                                myTeamMergedResponseModel.setMatchDetails(gameWeekMatchDetailsApiResponse.getData());

                                                updateFixtureData(myTeamMergedResponseModel.getMatchDetails()); //update future fixture data

                                                dataLoading.setValue(false); // make progress bar vanish when all api results are combined
                                                myTeamMergedMediatorLiveData.setValue(ApiResponse.success(myTeamMergedResponseModel));

//                                                if (Constants.currentGameWeek > 0) {
//                                                    // call game week players picked api
//                                                    mergedResponseModelMediatorLiveData.addSource(dataRepository.getGameWeekPicks(managerID, Constants.currentGameWeek),
//                                                            gameWeekPicksModelApiResponse -> {
//
//                                                                dataLoading.setValue(false); // make progress bar vanish when all api results are combined
//                                                                myTeamMergedResponseModel.setGameWeekPicksModel(gameWeekPicksModelApiResponse.getData());
//                                                                mergedResponseModelMediatorLiveData.setValue(ApiResponse.success(myTeamMergedResponseModel));
//                                                            });
//                                                } else {
//                                                    dataLoading.setValue(false); // make progress bar vanish when all api results are combined
//                                                    mergedResponseModelMediatorLiveData.setValue(ApiResponse.success(myTeamMergedResponseModel));
//                                                }
//                                                        dataLoading.setValue(false);
                                            });
                                });

//                            });

                    }
            );

        });
    }

    public void getPointsMergedData(long managerID, long gameWeek) {

        PointsMergedResponseModel pointsMergedResponseModel = new PointsMergedResponseModel();
        dataLoading.setValue(true);

        // get given game week picks player list
        pointsMergedMediatorLiveData.addSource(dataRepository.getGameWeekPicks(managerID, gameWeek), gameWeekPicksModelApiResponse -> {
            pointsMergedResponseModel.setGameWeekPicksModel(gameWeekPicksModelApiResponse.getData());
            gameWeekPicksApiResultLiveData.setValue(gameWeekPicksModelApiResponse);

            //get give game week players live data
            pointsMergedMediatorLiveData.addSource(dataRepository.getPlayerGameWeekLivePoints(gameWeek),
                    gameWeekLivePointsResponseModelApiResponse -> {

                        pointsMergedResponseModel.setGameWeekLivePointsResponseModel(gameWeekLivePointsResponseModelApiResponse.getData());

                        //call game week live points api

                        pointsMergedMediatorLiveData.addSource(dataRepository.getFixtureData(gameWeek),
                                gameWeekMatchDetailsApiResponse -> {

                                    pointsMergedResponseModel.setMatchDetails(gameWeekMatchDetailsApiResponse.getData());

                                    updateFixtureData(gameWeekMatchDetailsApiResponse.getData());

                                    dataLoading.setValue(false); // make progress bar vanish when all api results are combined
                                    pointsMergedMediatorLiveData.setValue(ApiResponse.success(pointsMergedResponseModel));
                                });

                    }
            );

        });
    }


    public void getStatusMergedData(long managerID, long gameWeek) {

        StatusMergedResponseModel statusMergedResponseModel = new StatusMergedResponseModel();
        dataLoading.setValue(true);

        // get given game week picks player list

        if (myTeamApiResultLiveData.getValue() != null && myTeamApiResultLiveData.getValue().getData() != null) {
            statusMergedResponseModel.setGameWeekMyTeamResponseModel(myTeamApiResultLiveData.getValue().getData());
        }

        if (gameWeekPicksApiResultLiveData.getValue() != null && gameWeekPicksApiResultLiveData.getValue().getData() != null) {
            statusMergedResponseModel.setGameWeekPicksModel(gameWeekPicksApiResultLiveData.getValue().getData());

            //get game week status
            statusMergedMediatorLiveData.addSource(dataRepository.getGameWeekStatus(),
                    gameWeekStatusApiResponse -> {

                        statusMergedResponseModel.setGameWeekStatus(gameWeekStatusApiResponse.getData());

                        //get best classic leagues data
                        statusMergedMediatorLiveData.addSource(dataRepository.getBestClassicPrivateLeaguesData(),
                                listApiResponse -> {

                                    statusMergedResponseModel.setBestTeamDataModels(listApiResponse.getData());

                                    //get most valuable teams data
                                    statusMergedMediatorLiveData.addSource(dataRepository.getMostValuableTeamsData(), listApiResponse1 -> {
                                        statusMergedResponseModel.setValuableTeamDataModels(listApiResponse1.getData());
                                        dataLoading.setValue(false); // make progress bar vanish when all api results are combined
                                        statusMergedMediatorLiveData.setValue(ApiResponse.success(statusMergedResponseModel));
                                    });

                                });
                    }
            );
        } else {
            //get game week status
            statusMergedMediatorLiveData.addSource(dataRepository.getGameWeekPicks(managerID, gameWeek), gameWeekPicksModelApiResponse -> {
                statusMergedResponseModel.setGameWeekPicksModel(gameWeekPicksModelApiResponse.getData());

                //
                statusMergedMediatorLiveData.addSource(dataRepository.getGameWeekStatus(), gameWeekStatusApiResponse ->
                {
                    statusMergedResponseModel.setGameWeekStatus(gameWeekStatusApiResponse.getData());
                    //get best classic leagues data
                    statusMergedMediatorLiveData.addSource(dataRepository.getBestClassicPrivateLeaguesData(),
                            listApiResponse -> {

                                statusMergedResponseModel.setBestTeamDataModels(listApiResponse.getData());

                                //get most valuable teams data
                                statusMergedMediatorLiveData.addSource(dataRepository.getMostValuableTeamsData(),
                                        listApiResponse1 -> {

                                            statusMergedResponseModel.setValuableTeamDataModels(listApiResponse1.getData());

                                            dataLoading.setValue(false); // make progress bar vanish when all api results are combined
                                            statusMergedMediatorLiveData.setValue(ApiResponse.success(statusMergedResponseModel));
                                        });

                            }
                    );
                })

                ;

            });
        }
    }


//    public LiveData<Boolean> getMyTeamAllData(long entry_id) throws InterruptedException {
//
//        dataLoadingDone.setValue(false);
//
//        latch = new CountDownLatch(2);
//
//        dataLoading.setValue(true);
//        // Make API call through the repository
//        myTeamApiResultLiveData.addSource(dataRepository.getTeamInformation(entry_id), userResponseModelApiResponse -> {
//            dataLoading.setValue(false);
//            long gameWeekNumber = userResponseModelApiResponse.getData().getCurrent_event();
//            latch.countDown();
//            fixtureApiResultLiveData.addSource(
//                    dataRepository.getFixtureData(gameWeekNumber),
//                    gameWeekMatchDetailsApiResponse -> {
//                        latch.countDown();
//                        fixtureApiResultLiveData.setValue(gameWeekMatchDetailsApiResponse);
//                    }
//            );
//            dataRepository.getGameWeekPicks(entry_id, gameWeekNumber);
//            myTeamApiResultLiveData.setValue(userResponseModelApiResponse);
//        });
//
//        getMyTeamData(entry_id);
//        getTeamData(entry_id);
//        getMyTeamData(entry_id);
//        latch.await();
//
//        dataLoadingDone.setValue(true);
//
//        return dataLoading;
//    }

    public LiveData<ApiResponse<GameWeekMyTeamResponseModel>> getMyTeamApiResultLiveData() {
        return myTeamApiResultLiveData;
    }

    public LiveData<ApiResponse<TeamInformationResponseModel>> getTeamInformationApiResultLiveData() {
        return teamInformationApiResultLiveData;
    }

    public MediatorLiveData<ApiResponse<?>> getFixtureLiveData() {
        return fixtureApiResultLiveData;
    }

    public LiveData<ApiResponse<GameWeekPicksModel>> getGameWeekPicksLiveData() {
        return gameWeekPicksApiResultLiveData;
    }

    public LiveData<ApiResponse<?>> getTransferApiResultLiveData() {
        return transferApiResultLiveData;
    }

    public LiveData<ApiResponse<MyTeamMergedResponseModel>> getMyTeamMergedResponseLiveData() {
        return myTeamMergedMediatorLiveData;
    }

    public LiveData<ApiResponse<PointsMergedResponseModel>> getPointsMergedResponseLiveData() {
        return pointsMergedMediatorLiveData;
    }

    public LiveData<ApiResponse<StatusMergedResponseModel>> getStatusMergedResponseLiveData() {
        return statusMergedMediatorLiveData;
    }

    public LiveData<Boolean> getDataLoadingDoneLiveData() {
        return dataLoadingDone;
    }

//    public void getMyTeamDataIfNeeded(long entry_id) {
//        if (myTeamApiResultLiveData.getValue() == null || myTeamApiResultLiveData.getValue().getData() == null) {
//            getMyTeamData(entry_id);
//        }
//    }

//    public void resetApiResponse() {
//        myTeamApiResultLiveData.setValue(null);
//    }
//
//    public void getMyTeamData(long entry_id) {
//
//        dataLoading.setValue(true);
//        // Make API call through the repository
//        myTeamApiResultLiveData.addSource(dataRepository.getMyTeamData(entry_id), userResponseModelApiResponse -> {
//            dataLoading.setValue(false);
//            latch.countDown();
//            myTeamApiResultLiveData.setValue(userResponseModelApiResponse);
//        });
//    }

    //get fixture data
    public void getFixtureData(long gameWeekNumber) {

        dataLoading.setValue(true);
        // Make API call through the repository
        fixtureApiResultLiveData.addSource(dataRepository.getFixtureData(gameWeekNumber), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            updateFixtureData(userResponseModelApiResponse.getData());
            fixtureApiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }

    // get team information like league rank etc
    public void getTeamInformation(long managerId) {

        dataLoading.setValue(true);
        // Make API call through the repository
        teamInformationApiResultLiveData.addSource(dataRepository.getTeamInformation(managerId), gameWeekDataResponseModelApiResponse -> {
            dataLoading.setValue(false);
            teamInformationApiResultLiveData.setValue(gameWeekDataResponseModelApiResponse);
        });
    }

//    public void getTeamData(long entry_id) {
//
//        dataLoading.setValue(true);
//        // Make API call through the repository
//        myTeamApiResultLiveData.addSource(dataRepository.getMyTeamData(entry_id), userResponseModelApiResponse -> {
//            dataLoading.setValue(false);
//            myTeamApiResultLiveData.setValue(userResponseModelApiResponse);
//        });
//    }

    public void updateMyTeam(long entry_id, GameWeekMyTeamUpdateModel updateModel) {

        dataLoading.setValue(true);
        // Make API call through the repository
        myTeamApiResultLiveData.addSource(dataRepository.updateMyTeam(entry_id, updateModel), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            myTeamApiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }

    public void transferMyTeam(GameWeekTransferUpdateModel updateModel) {

        dataLoading.setValue(true);
        // Make API call through the repository
        transferApiResultLiveData.addSource(dataRepository.transferMyTeam(updateModel), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            transferApiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }

    public LiveData<String> getToolbarTitle() {
        return toolbarTitle;
    }

    public LiveData<String> getToolbarSubTitle() {
        return toolbarSubTitle;
    }

    public void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle.setValue(toolbarTitle);
    }

    public void setToolbarSubTitle(String toolbarSubTitle) {
        this.toolbarSubTitle.setValue(toolbarSubTitle);
    }

    public MutableLiveData<String> getPreviousFragment() {
        return previousFragment;
    }

    public void setPreviousFragment(String fragment) {
        this.previousFragment.setValue(fragment);
    }

    public LiveData<Long> geGameWeekAvgScore(long gameWeekEventIndex) {
        return new MutableLiveData<>(Constants.GameWeekStaticData.getEvents().get((int) gameWeekEventIndex).getAverage_entry_score());
    }

    public LiveData<Long> geGameWeekMaxScore(long gameWeekEventIndex) {
        return new MutableLiveData<>(Constants.GameWeekStaticData.getEvents().get((int) gameWeekEventIndex).getAverage_entry_score());
    }
}
