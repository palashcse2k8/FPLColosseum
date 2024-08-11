package com.infotech.fplcolosseum.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.infotech.fplcolosseum.data.sources.network.APIHandler;
import com.infotech.fplcolosseum.data.sources.network.APIServices;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.data.sources.network.RetroClass;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekStaticDataModel;
import com.infotech.fplcolosseum.features.login.models.SessionManager;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class GameWeekStaticDataRepository {

    APIServices apiServices;

    SessionManager sessionManager;

    Application application;

    public GameWeekStaticDataRepository(Application application) {
        this.application = application;
        apiServices = RetroClass.getAPIService(application); // set API
        sessionManager = new SessionManager(application);
    }

    public LiveData<ApiResponse<GameWeekStaticDataModel>> getGameWeekStaticData() {

        MediatorLiveData<ApiResponse<GameWeekStaticDataModel>> apiData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.getGameWeeKStaticData();

        apiData.addSource(APIHandler.makeApiCall(callAPI, GameWeekStaticDataModel.class), tApiResponse -> {
//            Log.d("Data ", tApiResponse.getData().toString());
            apiData.postValue(tApiResponse);
        });

        return apiData;
    }


    public void getSomeThing() {
    }
}

