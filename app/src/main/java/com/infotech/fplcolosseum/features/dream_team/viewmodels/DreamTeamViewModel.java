package com.infotech.fplcolosseum.features.dream_team.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.dream_team.models.DreamTeamResponseModel;
import com.infotech.fplcolosseum.features.gameweek_history.models.GameWeekHistoryResponseModel;
import com.infotech.fplcolosseum.utilities.Constants;

public class DreamTeamViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);

    private final MediatorLiveData<ApiResponse<DreamTeamResponseModel>> dreamTeamApiLiveData;
    private final MediatorLiveData<ApiResponse<DreamTeamResponseModel>> seasonDreamTeamApiLiveData;


    public DreamTeamViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.dreamTeamApiLiveData = new MediatorLiveData<>();
        this.seasonDreamTeamApiLiveData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<DreamTeamResponseModel>> getDreamTeamLiveData() {
        return dreamTeamApiLiveData;
    }

    public LiveData<ApiResponse<DreamTeamResponseModel>> getSeasonDreamTeamLiveData() {
        return seasonDreamTeamApiLiveData;
    }

    public void getDreamTeam(long gameWeek) {
        dataLoading.setValue(true);
        // Make API call through the repository
        dreamTeamApiLiveData.addSource(dataRepository.getDreamTeam(gameWeek), dreamTeamResponseModelApiResponse -> {
            dataLoading.setValue(false);
            dreamTeamApiLiveData.setValue(dreamTeamResponseModelApiResponse);
        });
    }

    public void getSeasonDreamTeam() {
        dataLoading.setValue(true);
        // Make API call through the repository
        seasonDreamTeamApiLiveData.addSource(dataRepository.getSeasonDreamTeam(), dreamTeamResponseModelApiResponse -> {
            dataLoading.setValue(false);
            seasonDreamTeamApiLiveData.setValue(dreamTeamResponseModelApiResponse);
        });
    }
}
