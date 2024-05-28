package com.infotech.fplcolosseum.features.homepage.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.homepage.models.MergedResponseModel;

import java.util.concurrent.CountDownLatch;

public class HomePageSharedViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;

    public MutableLiveData<Long> managerID;

    private final MediatorLiveData<ApiResponse<?>> myTeamApiResultLiveData;
    private final MediatorLiveData<ApiResponse<?>> fixtureApiResultLiveData;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> dataLoadingDone;

    private final MediatorLiveData<ApiResponse<MergedResponseModel>> mergedResponseModelMediatorLiveData;
    CountDownLatch latch;
    Application application;

    public HomePageSharedViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.myTeamApiResultLiveData = new MediatorLiveData<>();
        this.fixtureApiResultLiveData = new MediatorLiveData<>();
        mergedResponseModelMediatorLiveData = new MediatorLiveData<>();
    }

    public void getTeamCurrentGameWeekAllData(long managerID) {

        MergedResponseModel mergedResponseModel = new MergedResponseModel();
        dataLoading.setValue(true);

        //call manager information with league data
        mergedResponseModelMediatorLiveData.addSource(dataRepository.getTeamInformation(managerID),
                gameWeekDataResponseModelApiResponse -> {
                    long currentGameWeek = gameWeekDataResponseModelApiResponse.getData().getCurrent_event();
                    mergedResponseModel.setGameWeekDataResponseModel(gameWeekDataResponseModelApiResponse.getData());

                    // call game week players picked api
                    mergedResponseModelMediatorLiveData.addSource(dataRepository.getGameWeekPicks(managerID, currentGameWeek),
                            gameWeekPicksModelApiResponse -> {
                                mergedResponseModel.setGameWeekPicksModel(gameWeekPicksModelApiResponse.getData());

                                //call game week live points api
                                mergedResponseModelMediatorLiveData.addSource(dataRepository.getPlayerGameWeekLivePoints(currentGameWeek),
                                        gameWeekLivePointsResponseModelApiResponse -> {
                                            mergedResponseModel.setGameWeekLivePointsResponseModel(gameWeekLivePointsResponseModelApiResponse.getData());

                                            mergedResponseModelMediatorLiveData.addSource(dataRepository.getFixtureData(currentGameWeek),
                                                    gameWeekMatchDetailsApiResponse -> {
                                                        mergedResponseModel.setGameWeekMatchDetails(gameWeekMatchDetailsApiResponse.getData());
                                                        dataLoading.setValue(false); // make progress bar vanish when all api results are combined
                                                        mergedResponseModelMediatorLiveData.setValue(ApiResponse.success(mergedResponseModel));
//                                                        dataLoading.setValue(false);
                                            });
                                        });

                            });

                }
        );

    }

    public void getTeamSpecificGameWeekAllData(long managerID, long gameWeek) {

        MergedResponseModel mergedResponseModel = new MergedResponseModel();
        dataLoading.setValue(true);

        //call manager information with league data
        mergedResponseModelMediatorLiveData.addSource(dataRepository.getTeamInformation(managerID),
                gameWeekDataResponseModelApiResponse -> {
                    mergedResponseModel.setGameWeekDataResponseModel(gameWeekDataResponseModelApiResponse.getData());

                    // call game week players picked api
                    mergedResponseModelMediatorLiveData.addSource(dataRepository.getGameWeekPicks(managerID, gameWeek),
                            gameWeekPicksModelApiResponse -> {
                                mergedResponseModel.setGameWeekPicksModel(gameWeekPicksModelApiResponse.getData());

                                //call game week live points api
                                mergedResponseModelMediatorLiveData.addSource(dataRepository.getPlayerGameWeekLivePoints(gameWeek),
                                        gameWeekLivePointsResponseModelApiResponse -> {
                                            mergedResponseModel.setGameWeekLivePointsResponseModel(gameWeekLivePointsResponseModelApiResponse.getData());

                                            mergedResponseModelMediatorLiveData.addSource(dataRepository.getFixtureData(gameWeek),
                                                    gameWeekMatchDetailsApiResponse -> {
                                                        mergedResponseModel.setGameWeekMatchDetails(gameWeekMatchDetailsApiResponse.getData());
                                                        dataLoading.setValue(false); // make progress bar vanish when all api results are combined
                                                        mergedResponseModelMediatorLiveData.setValue(ApiResponse.success(mergedResponseModel));
                                                    });
                                        });

                            });

                }
        );

    }


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

    public LiveData<ApiResponse<MergedResponseModel>> getMergedResponseLiveData() {
        return mergedResponseModelMediatorLiveData;
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
}
