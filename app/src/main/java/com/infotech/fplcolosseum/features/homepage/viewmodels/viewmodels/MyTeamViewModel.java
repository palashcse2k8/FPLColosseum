package com.infotech.fplcolosseum.features.homepage.viewmodels.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;

import java.util.concurrent.CountDownLatch;

public class MyTeamViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;

    private final MediatorLiveData<ApiResponse<?>> myTeamApiResultLiveData;
    private final MediatorLiveData<ApiResponse<?>> fixtureApiResultLiveData;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> dataLoadingDone;
    CountDownLatch latch;
    Application application;

    public MyTeamViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.myTeamApiResultLiveData = new MediatorLiveData<>();
        this.fixtureApiResultLiveData = new MediatorLiveData<>();
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
                    gameWeekMatchDetailsApiResponse ->
                    {
                        latch.countDown();
                        fixtureApiResultLiveData.setValue(gameWeekMatchDetailsApiResponse);
                    }
            );
            dataRepository.getTeamPicks(entry_id, gameWeekNumber);
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
