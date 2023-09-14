package com.infotech.fplcolosseum.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonSyntaxException;
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

    public LiveData<ResponseBody> gameWeekDataFromAPI(String leagueID, String entryID, String currentGameweek, String currentPage) {

        // Create a Map to hold the query parameters
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("leagueId", leagueID);
        queryParams.put("entry", entryID);
        queryParams.put("currentweek", currentGameweek);
        queryParams.put("currentPage", currentPage);
        Call<ResponseBody> callAPI = apiServices.getLeagueData(queryParams);

        return callAPI(callAPI);
    }


    public LiveData<ResponseBody> callAPI (Call<ResponseBody> callingAPI) {

        final MutableLiveData<ResponseBody> data = new MutableLiveData<>();

        try {
            callingAPI.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();

                        if (responseBody != null) {
                            try {
                                String json = responseBody.string();
                                Log.d("apiresponse=>", json);
                                data.setValue(responseBody);
                            } catch (JsonSyntaxException | IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            data.setValue(null);
                            Log.d("apiresponse=>", "null");
                        }

                    } else {
                        data.setValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
//                    UIUtils.toast("API Calling fail", WARNING);
                    data.setValue(null);
                }
            });
        } catch (Exception e) {
            data.setValue(null);
        }
        return data;
    }
}
