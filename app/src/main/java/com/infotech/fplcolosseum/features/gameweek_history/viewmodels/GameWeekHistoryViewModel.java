package com.infotech.fplcolosseum.features.gameweek_history.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.gameweek_history.models.GameWeekHistoryResponseModel;

public class GameWeekHistoryViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);

    private final MediatorLiveData<ApiResponse<GameWeekHistoryResponseModel>> gameWeekHistoryApiResultLiveData;


    public GameWeekHistoryViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.gameWeekHistoryApiResultLiveData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<GameWeekHistoryResponseModel>> getGameWeekHistoryLiveData() {
        return gameWeekHistoryApiResultLiveData;
    }

    public void getGameWeekHistory(long entry_id) {
        dataLoading.setValue(true);

        // Make API call through the repository
        gameWeekHistoryApiResultLiveData.addSource(dataRepository.getGameWeekHistory(entry_id), gameWeekHistoryResponseModelApiResponse -> {

            dataLoading.setValue(false);
            gameWeekHistoryApiResultLiveData.setValue(gameWeekHistoryResponseModelApiResponse);
        });
    }
}
