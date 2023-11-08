package com.infotech.fplcolosseum.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.infotech.fplcolosseum.utilities.Constants;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIHandler {
    APIHandler(){}

    public static int apiCallCount = 0;
    public static int apiResponseCount = 0;

    public static <T> LiveData<T> callAPI(Call<ResponseBody> callingAPI, Class<T> apiResponseType) {

        final MutableLiveData<T> apiData = new MutableLiveData<>();
        Log.d("api call => ", "Call request" + (++apiCallCount));

        try {
            callingAPI.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    Logger.d("Request URL => " + call.request().url().uri());
                    Log.d("api response => ", "Response received" + (++apiResponseCount));
                    if (response.isSuccessful()) {

                        try (ResponseBody responseBody = response.body()) {
                            if (responseBody != null) {
                                T convertedResponse = convertResponse(responseBody, apiResponseType);
                                apiData.setValue(convertedResponse);
                            } else {
                                apiData.setValue(null);
                            }
                        } catch (JsonSyntaxException | IOException e) {
                            Log.d("response", "exception occured");
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
                    Log.d("response", "data receive failure");
                    apiData.setValue(null);
                }
            });
        } catch (Exception e) {
            apiData.setValue(null);
        }
        return apiData;
    }

    public static <T> T convertResponse(ResponseBody responseBody, Class<T> classofT) throws IOException {

        Gson gson = new Gson();
        String json = responseBody.string();
//        Logger.d("apiResponse=>> " + json);
        try {
            return gson.fromJson(json, classofT);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
