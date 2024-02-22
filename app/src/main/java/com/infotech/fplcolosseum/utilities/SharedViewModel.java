package com.infotech.fplcolosseum.utilities;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.infotech.fplcolosseum.data.repositories.GameWeekStaticDataRepository;
import com.infotech.fplcolosseum.data.sources.network.APIServices;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekStaticDataModel;

public class SharedViewModel extends AndroidViewModel {

    GameWeekStaticDataRepository dataRepository;
    APIServices apiServices;
    private MediatorLiveData<ApiResponse<GameWeekStaticDataModel>> apiData;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new GameWeekStaticDataRepository(application);
        this.apiData = new MediatorLiveData<>();
    }

    public void getGameWeekStaticData() {
        apiData.addSource(dataRepository.getGameWeekStaticData(), gameWeekStaticDataModelApiResponse -> {
            apiData.setValue(gameWeekStaticDataModelApiResponse);
        });
    }

    public LiveData<ApiResponse<GameWeekStaticDataModel>> getApiData(){
        return apiData;
    }
}