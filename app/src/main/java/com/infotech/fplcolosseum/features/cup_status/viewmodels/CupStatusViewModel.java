package com.infotech.fplcolosseum.features.cup_status.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.cup_status.model.CupStatusResponseModel;
import com.infotech.fplcolosseum.features.dream_team.models.DreamTeamResponseModel;

public class CupStatusViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);

    private final MediatorLiveData<ApiResponse<CupStatusResponseModel>> cupStatusApiLiveData;

    public CupStatusViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.cupStatusApiLiveData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<CupStatusResponseModel>> getCupStatusLiveData() {
        return cupStatusApiLiveData;
    }


    public void getCupStatus(long gameWeek) {
        dataLoading.setValue(true);
        // Make API call through the repository
        cupStatusApiLiveData.addSource(dataRepository.getCupStatus(gameWeek), cupStatusResponseModelApiResponse -> {
            dataLoading.setValue(false);
            cupStatusApiLiveData.setValue(cupStatusResponseModelApiResponse);
        });
    }
}
