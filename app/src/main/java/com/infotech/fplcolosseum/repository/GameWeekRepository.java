package com.infotech.fplcolosseum.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.infotech.fplcolosseum.remote.APIServices;
import com.infotech.fplcolosseum.remote.RetroClass;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameWeekRepository {
    APIServices apiServices;

    public GameWeekRepository() {
        apiServices = RetroClass.getAPIService(); // set API
    }

    public <T> LiveData<T> gameWeekDataFromAPI(String leagueID, String entryID, String currentGameweek, String currentPage, Class<T> responseTypeClass) {

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        queryParams.put("entry", entryID);
        queryParams.put("currentweek", currentGameweek);
        queryParams.put("currentPage", currentPage);
        Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);

        return callAPI(callAPI, responseTypeClass);
    }


    public <T> LiveData<T> callAPI(Call<ResponseBody> callingAPI, Class<T> classofT) {

        final MutableLiveData<T> apiData = new MutableLiveData<>();

        try {
            callingAPI.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    if (response.isSuccessful()) {

                        try (ResponseBody responseBody = response.body()) {
                            if (responseBody != null) {
                                apiData.setValue(convertResponse(responseBody, classofT));
                            } else {
                                apiData.setValue(null);
                            }

                        } catch (JsonSyntaxException | IOException e) {
                            apiData.setValue(null);
                            e.printStackTrace();
                        }

                    } else {
                        apiData.setValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                    UIUtils.toast("API Calling fail", WARNING);
                    apiData.setValue(null);
                }
            });
        } catch (Exception e) {
            apiData.setValue(null);
        }
        return apiData;
    }

    public <T> T convertResponse(ResponseBody responseBody, Class<T> classofT) throws IOException {

        Gson gson = new Gson();
        String json = responseBody.string();
        Log.d("apiResponse=>>", json);
        return gson.fromJson(json, classofT);
    }


}
