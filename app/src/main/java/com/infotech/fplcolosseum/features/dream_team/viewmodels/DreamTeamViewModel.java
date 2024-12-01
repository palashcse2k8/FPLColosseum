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


    public DreamTeamViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.dreamTeamApiLiveData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<DreamTeamResponseModel>> getDreamTeamLiveData() {
        return dreamTeamApiLiveData;
    }

    public void getDreamTeam(long gameWeek) {
        dataLoading.setValue(true);
        // Make API call through the repository
        dreamTeamApiLiveData.addSource(dataRepository.getDreamTeam(gameWeek), dreamTeamResponseModelApiResponse -> {
            dataLoading.setValue(false);
            dreamTeamApiLiveData.setValue(dreamTeamResponseModelApiResponse);
        });
    }
}
