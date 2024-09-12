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
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamUpdateModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekTransferUpdateModel;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.concurrent.CountDownLatch;

public class HomePageSharedViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;

    public MutableLiveData<Long> managerID;

    private final MediatorLiveData<ApiResponse<?>> myTeamApiResultLiveData;
    private final MediatorLiveData<ApiResponse<?>> transferApiResultLiveData;

    private final MediatorLiveData<ApiResponse<?>> fixtureApiResultLiveData;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> dataLoadingDone;

    private final MediatorLiveData<ApiResponse<MyTeamMergedResponseModel>> myTeamMergedMediatorLiveData;
    private final MediatorLiveData<ApiResponse<PointsMergedResponseModel>> pointsMergedMediatorLiveData;
    CountDownLatch latch;
    Application application;

    public HomePageSharedViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.myTeamApiResultLiveData = new MediatorLiveData<>();
        this.transferApiResultLiveData = new MediatorLiveData<>();
        this.fixtureApiResultLiveData = new MediatorLiveData<>();
        myTeamMergedMediatorLiveData = new MediatorLiveData<>();
        pointsMergedMediatorLiveData = new MediatorLiveData<>();

    }

    public void getMyTeamMergedData(long managerID) {

        MyTeamMergedResponseModel myTeamMergedResponseModel = new MyTeamMergedResponseModel();
        dataLoading.setValue(true);

        myTeamMergedMediatorLiveData.addSource(dataRepository.getGameWeekStaticData(), gameWeekStaticDataModelApiResponse -> {
            myTeamMergedResponseModel.setGameWeekStaticDataModel(gameWeekStaticDataModelApiResponse.getData());

            prepareData(gameWeekStaticDataModelApiResponse.getData()); //update static data

            //call manager information with league data
            myTeamMergedMediatorLiveData.addSource(dataRepository.getTeamInformation(managerID),
                    gameWeekDataResponseModelApiResponse -> {
                        long currentGameWeek = gameWeekDataResponseModelApiResponse.getData().getCurrent_event();
                        Constants.currentGameWeek = currentGameWeek;
                        Constants.previousGameWeek = currentGameWeek - 1;
                        Constants.nextGameWeek = currentGameWeek + 1;
                        Constants.teamName = gameWeekDataResponseModelApiResponse.getData().getName();
                        Constants.managerName = gameWeekDataResponseModelApiResponse.getData().getPlayer_first_name() + " " + gameWeekDataResponseModelApiResponse.getData().getPlayer_last_name();

                        myTeamMergedResponseModel.setGameWeekDataResponseModel(gameWeekDataResponseModelApiResponse.getData());

                        //call game week live points api
                        myTeamMergedMediatorLiveData.addSource(dataRepository.getMyTeamData(managerID),
                                gameWeekMyTeamResponseModelApiResponse -> {
                                    myTeamMergedResponseModel.setGameWeekMyTeamResponseModel(gameWeekMyTeamResponseModelApiResponse.getData());

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

            //get give game week players live data
            pointsMergedMediatorLiveData.addSource(dataRepository.getPlayerGameWeekLivePoints(gameWeek),
                    gameWeekLivePointsResponseModelApiResponse -> {

                        pointsMergedResponseModel.setGameWeekLivePointsResponseModel(gameWeekLivePointsResponseModelApiResponse.getData());

                        //call game week live points api

                        pointsMergedMediatorLiveData.addSource(dataRepository.getFixtureData(gameWeek),
                                            gameWeekMatchDetailsApiResponse -> {

                                                pointsMergedResponseModel.setMatchDetails(gameWeekMatchDetailsApiResponse.getData());

                                                dataLoading.setValue(false); // make progress bar vanish when all api results are combined
                                                pointsMergedMediatorLiveData.setValue(ApiResponse.success(pointsMergedResponseModel));
                                            });

//                            });

                    }
            );

        });
    }

//    public void getTeamSpecificGameWeekAllData(long managerID, long gameWeek) {
//
//        MyTeamMergedResponseModel myTeamMergedResponseModel = new MyTeamMergedResponseModel();
//        dataLoading.setValue(true);
//
//        //call manager information with league data
//        mergedResponseModelMediatorLiveData.addSource(dataRepository.getTeamInformation(managerID),
//                gameWeekDataResponseModelApiResponse -> {
//                    myTeamMergedResponseModel.setGameWeekDataResponseModel(gameWeekDataResponseModelApiResponse.getData());
//
//                    // call game week players picked api
//                    mergedResponseModelMediatorLiveData.addSource(dataRepository.getGameWeekPicks(managerID, gameWeek),
//                            gameWeekPicksModelApiResponse -> {
//                                myTeamMergedResponseModel.setGameWeekPicksModel(gameWeekPicksModelApiResponse.getData());
//
//                                //call game week live points api
//                                mergedResponseModelMediatorLiveData.addSource(dataRepository.getPlayerGameWeekLivePoints(gameWeek),
//                                        gameWeekLivePointsResponseModelApiResponse -> {
//                                            myTeamMergedResponseModel.setGameWeekLivePointsResponseModel(gameWeekLivePointsResponseModelApiResponse.getData());
//
//                                            mergedResponseModelMediatorLiveData.addSource(dataRepository.getFixtureData(gameWeek),
//                                                    gameWeekMatchDetailsApiResponse -> {
//                                                        myTeamMergedResponseModel.setMatchDetails(gameWeekMatchDetailsApiResponse.getData());
//                                                        dataLoading.setValue(false); // make progress bar vanish when all api results are combined
//                                                        mergedResponseModelMediatorLiveData.setValue(ApiResponse.success(myTeamMergedResponseModel));
//                                                    });
//                                        });
//
//                            });
//
//                }
//        );
//
//    }


    public LiveData<Boolean> getMyTeamAllData(long entry_id) throws InterruptedException {

        dataLoadingDone.setValue(false);

        latch = new CountDownLatch(2);

        dataLoading.setValue(true);
        // Make API call through the repository
        myTeamApiResultLiveData.addSource(dataRepository.getTeamInformation(entry_id), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            long gameWeekNumber = userResponseModelApiResponse.getData().getCurrent_event();
            latch.countDown();
            fixtureApiResultLiveData.addSource(
                    dataRepository.getFixtureData(gameWeekNumber),
                    gameWeekMatchDetailsApiResponse -> {
                        latch.countDown();
                        fixtureApiResultLiveData.setValue(gameWeekMatchDetailsApiResponse);
                    }
            );
            dataRepository.getGameWeekPicks(entry_id, gameWeekNumber);
            myTeamApiResultLiveData.setValue(userResponseModelApiResponse);
        });

        getMyTeamData(entry_id);
        getTeamData(entry_id);
        getMyTeamData(entry_id);
        latch.await();

        dataLoadingDone.setValue(true);

        return dataLoading;
    }

    public LiveData<ApiResponse<?>> getMyTeamApiResultLiveData() {
        return myTeamApiResultLiveData;
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

    public LiveData<Boolean> getDataLoadingDoneLiveData() {
        return dataLoadingDone;
    }

    public void getMyTeamDataIfNeeded(long entry_id) {
        if (myTeamApiResultLiveData.getValue() == null || myTeamApiResultLiveData.getValue().getData() == null) {
            getMyTeamData(entry_id);
        }
    }

    public void resetApiResponse() {
        myTeamApiResultLiveData.setValue(null);
    }

    public void getMyTeamData(long entry_id) {

        dataLoading.setValue(true);
        // Make API call through the repository
        myTeamApiResultLiveData.addSource(dataRepository.getMyTeamData(entry_id), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            latch.countDown();
            myTeamApiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }

    public void getFixtureData(int gameWeekNumber) {

        dataLoading.setValue(true);
        // Make API call through the repository
        fixtureApiResultLiveData.addSource(dataRepository.getFixtureData(gameWeekNumber), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            fixtureApiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }

    public void getTeamData(long entry_id) {

        dataLoading.setValue(true);
        // Make API call through the repository
        myTeamApiResultLiveData.addSource(dataRepository.getMyTeamData(entry_id), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            myTeamApiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }

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
}
