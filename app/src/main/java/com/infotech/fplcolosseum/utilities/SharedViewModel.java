package com.infotech.fplcolosseum.utilities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.GameWeekStaticDataRepository;
import com.infotech.fplcolosseum.data.sources.network.APIServices;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.homepage.models.GameWeekStaticDataModel;

public class SharedViewModel extends AndroidViewModel {

    GameWeekStaticDataRepository dataRepository;
    APIServices apiServices;
    private MutableLiveData<ApiResponse<GameWeekStaticDataModel>> apiData;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new GameWeekStaticDataRepository(application);
        this.apiData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<GameWeekStaticDataModel>> getApiData() {

        apiData.setValue(dataRepository.getGameWeekStaticData().getValue());

        return apiData;
    }
}