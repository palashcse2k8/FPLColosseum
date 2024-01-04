package com.infotech.fplcolosseum.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.infotech.fplcolosseum.data.sources.network.APIHandler;
import com.infotech.fplcolosseum.data.sources.network.APIServices;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.data.sources.network.RetroClass;
import com.infotech.fplcolosseum.features.homepage.models.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.GameWeekStaticDataModel;
import com.infotech.fplcolosseum.features.login.models.SessionManager;
import com.infotech.fplcolosseum.utilities.Constants;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGameWeekDataRepository {

    APIServices apiServices;

    SessionManager sessionManager;

    Application application;

    public UserGameWeekDataRepository(Application application) {
        this.application = application;
        apiServices = RetroClass.getAPIService(application); // set API
        sessionManager = new SessionManager(application);
    }

    public LiveData<ApiResponse<?>> userLogIn(String userName, String passWord, Class<?> responseClass) {

        MediatorLiveData<ApiResponse<?>> userProfileLiveData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.userLogin(userName, passWord, Constants.APP_NAME, Constants.LOGIN_REDIRECT_URL);

        callAPI.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() != 200 || !response.raw().request().url().toString().contains("success")) {
                    userProfileLiveData.postValue(ApiResponse.error("API call failed", null));
                    return;
                }

                Call<ResponseBody> callAPI = apiServices.getManagerProfileData();

                userProfileLiveData.addSource(APIHandler.makeApiCall( callAPI, responseClass), tApiResponse -> {
                    Log.d("Data ", tApiResponse.getData().toString());
                    userProfileLiveData.postValue(tApiResponse);
                });
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                userProfileLiveData.postValue(ApiResponse.error("API call failed", null));
            }
        });
        return userProfileLiveData;
    }

    public LiveData<ApiResponse<?>> userLogOut(String userName, String passWord, Class<?> responseClass) {

        MediatorLiveData<ApiResponse<?>> userProfileLiveData = new MediatorLiveData<>();

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("app", "plfpl-web");
        queryParams.put("redirect_uri", "https://fantasy.premierleague.com/");


        Call<ResponseBody> callAPI = apiServices.userLogout(queryParams);

        callAPI.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() != 200 || !response.raw().request().url().toString().contains("success")) {
                    userProfileLiveData.postValue(ApiResponse.error("API call failed", null));
                    return;
                }

                Call<ResponseBody> callAPI = apiServices.getManagerProfileData();

                userProfileLiveData.addSource(APIHandler.makeApiCall( callAPI, responseClass), tApiResponse -> {
                    Log.d("Data ", tApiResponse.getData().toString());
                    userProfileLiveData.postValue(tApiResponse);
                });
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                userProfileLiveData.postValue(ApiResponse.error("API call failed", null));
            }
        });
        return userProfileLiveData;
    }

    public LiveData<ApiResponse<GameWeekMyTeamResponseModel>> getMyTeamData(long entry_id) {

        MediatorLiveData<ApiResponse<GameWeekMyTeamResponseModel>> apiData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.gameWeekMyTeam(entry_id);

        apiData.addSource(APIHandler.makeApiCall(callAPI, GameWeekMyTeamResponseModel.class), tApiResponse -> {
            Log.d("Data ", tApiResponse.getData().toString());
            apiData.postValue(tApiResponse);
        });

        return apiData;
    }
}

