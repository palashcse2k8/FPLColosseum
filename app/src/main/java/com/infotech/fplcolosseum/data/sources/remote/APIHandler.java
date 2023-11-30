package com.infotech.fplcolosseum.data.sources.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIHandler {
    APIHandler() {
    }

    public static <T> LiveData<T> callAPI(Call<ResponseBody> callingAPI, Class<T> apiResponseType) {

        final MutableLiveData<T> apiData = new MutableLiveData<>();
//        Logger.d("api call => ", "Call request" + (++apiCallCount));

        try {
            callingAPI.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

//                    Logger.d("api response => ", "Response received" + (++apiResponseCount));
                    if (response.isSuccessful()) {

                        try (ResponseBody responseBody = response.body()) {
                            if (responseBody != null) {
                                T convertedResponse = convertResponse(responseBody, apiResponseType);
                                apiData.setValue(convertedResponse);
                            } else {
                                apiData.setValue(null);
                            }
                        } catch (JsonSyntaxException | IOException e) {
//                            Logger.d("response", "exception occurred");
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

    public static <T> T convertResponse(ResponseBody responseBody, Class<T> classofT) throws IOException {

        Gson gson = new Gson();
        String json = responseBody.string();
        Logger.d("apiResponse=>> " + json);
        try {
            return gson.fromJson(json, classofT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //special case for login api
    public static <T> LiveData<ApiResponse<T>> makeApiCall(Call<ResponseBody> callingAPI, Class<T> apiResponseType) {

        MutableLiveData<ApiResponse<T>> resultLiveData = new MutableLiveData<>();
        // Enqueue the call and handle the response
        callingAPI.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                // API call successful
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                try {
                    T convertedResponse = convertResponse(responseBody, apiResponseType);
                    resultLiveData.postValue(ApiResponse.success(convertedResponse));
                } catch (IOException e) {
                    resultLiveData.postValue(ApiResponse.error("API call failed", null));
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // API call failed
                resultLiveData.postValue(ApiResponse.error("API call failed", null));
            }
        });

        return resultLiveData;
    }
}
