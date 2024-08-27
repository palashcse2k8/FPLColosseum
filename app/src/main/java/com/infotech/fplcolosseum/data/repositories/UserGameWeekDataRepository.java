package com.infotech.fplcolosseum.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.gson.reflect.TypeToken;
import com.infotech.fplcolosseum.data.sources.network.APIHandler;
import com.infotech.fplcolosseum.data.sources.network.APIServices;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.data.sources.network.RetroClass;
import com.infotech.fplcolosseum.features.homepage.models.entryinformation.GameWeekDataResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.GameWeekMatchDetailsResponse;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.GameWeekLivePointsResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamUpdateModel;
import com.infotech.fplcolosseum.features.homepage.models.picks.GameWeekPicksModel;
import com.infotech.fplcolosseum.features.login.models.SessionManager;
import com.infotech.fplcolosseum.utilities.Constants;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
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
                    userProfileLiveData.postValue(ApiResponse.error("Login failed or API call failed", null));
                    return;
                }

                Call<ResponseBody> callAPI = apiServices.getManagerProfileData();

                userProfileLiveData.addSource(APIHandler.makeApiCall(callAPI, responseClass), tApiResponse -> {
//                    Log.d("Data ", tApiResponse.getData().toString());
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

                userProfileLiveData.addSource(APIHandler.makeApiCall(callAPI, responseClass), tApiResponse -> {
//                    Log.d("Data ", tApiResponse.getData().toString());
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
//            Log.d("Data ", tApiResponse.getData().toString());
            apiData.postValue(tApiResponse);
        });

        return apiData;
    }

    public LiveData<ApiResponse<GameWeekMyTeamResponseModel>> updateMyTeam(long entry_id, GameWeekMyTeamUpdateModel updateModel) {

        MediatorLiveData<ApiResponse<GameWeekMyTeamResponseModel>> apiData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.updateMyTeam(entry_id, updateModel);

        apiData.addSource(APIHandler.makeApiCall(callAPI, GameWeekMyTeamResponseModel.class), tApiResponse -> {
//            Log.d("Data ", tApiResponse.getData().toString());
            apiData.postValue(tApiResponse);
        });

        return apiData;
    }


    public LiveData<ApiResponse<GameWeekDataResponseModel>> getTeamInformation(long entry_id) {

        MediatorLiveData<ApiResponse<GameWeekDataResponseModel>> apiData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.gameWeekEntriesInformation(entry_id);

        apiData.addSource(APIHandler.makeApiCall(callAPI, GameWeekDataResponseModel.class), tApiResponse -> {
//            Log.d("Data ", tApiResponse.getData().toString());
            apiData.postValue(tApiResponse);
        });

        return apiData;
    }

    public LiveData<ApiResponse<GameWeekPicksModel>> getGameWeekPicks(long entry_id, long gameWeekNumber) {

        MediatorLiveData<ApiResponse<GameWeekPicksModel>> apiData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.gameWeekPicks(entry_id, gameWeekNumber);

        apiData.addSource(APIHandler.makeApiCall(callAPI, GameWeekPicksModel.class), tApiResponse -> {
//            Log.d("Data ", tApiResponse.getData().toString());
            apiData.postValue(tApiResponse);
        });

        return apiData;
    }


//    public LiveData<ApiResponse<GameWeekMatchDetailsResponse>> getFixtureData(long gameWeekNumber) {
//
//        MediatorLiveData<ApiResponse<GameWeekMatchDetailsResponse>> apiData = new MediatorLiveData<>();
//
//        Call<ResponseBody> callAPI = apiServices.gameWeekFixtureData(gameWeekNumber);
//
//        Type responseType = new TypeToken<GameWeekMatchDetailsResponse>() {}.getType();
//
//        apiData.addSource(APIHandler.makeApiCall2(callAPI, responseType), tApiResponse -> {
////            Log.d("Data ", tApiResponse.getData().toString());
//
////            GameWeekMatchDetailsResponse data = new GameWeekMatchDetailsResponse();
////            data.setMatchDetails(tApiResponse.getData().getMatchDetails());
//
//            apiData.postValue(tApiResponse);
//        });
//
//        return apiData;
//    }

    public LiveData<ApiResponse<List<MatchDetails>>> getFixtureData(long gameWeekNumber) {
        MediatorLiveData<ApiResponse<List<MatchDetails>>> apiData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.gameWeekFixtureData(gameWeekNumber);

        apiData.addSource(APIHandler.makeApiCallForList(callAPI, MatchDetails.class), listApiResponse -> {
            apiData.postValue(listApiResponse);
        });

        return apiData;
    }

    public LiveData<ApiResponse<List<MatchDetails>>> getAllFixtureData(long gameWeekNumber) {
        MediatorLiveData<ApiResponse<List<MatchDetails>>> apiData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.allGameWeekFixtureData(gameWeekNumber);

        apiData.addSource(APIHandler.makeApiCallForList(callAPI, MatchDetails.class), listApiResponse -> {
            apiData.postValue(listApiResponse);
        });

        return apiData;
    }

    public LiveData<ApiResponse<GameWeekLivePointsResponseModel>> getPlayerGameWeekLivePoints(long gameWeekNumber) {

        MediatorLiveData<ApiResponse<GameWeekLivePointsResponseModel>> apiData = new MediatorLiveData<>();

        Call<ResponseBody> callAPI = apiServices.gameWeekLive(gameWeekNumber);

        apiData.addSource(APIHandler.makeApiCall(callAPI, GameWeekLivePointsResponseModel.class), tApiResponse -> {
//            Log.d("Data ", tApiResponse.getData().toString());
            apiData.postValue(tApiResponse);
        });

        return apiData;
    }

}

