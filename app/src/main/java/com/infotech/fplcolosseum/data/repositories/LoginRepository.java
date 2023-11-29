package com.infotech.fplcolosseum.data.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.sources.remote.APIHandler;
import com.infotech.fplcolosseum.data.sources.remote.APIServices;
import com.infotech.fplcolosseum.data.sources.remote.ApiResponse;
import com.infotech.fplcolosseum.data.sources.remote.RetroClass;
import com.infotech.fplcolosseum.features.login.models.SessionManager;
import com.infotech.fplcolosseum.utilities.Constants;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    APIServices apiServices;

    SessionManager sessionManager;

    Application application;

    public LoginRepository(Application application) {
        this.application = application;
        apiServices = RetroClass.getAPIService(application); // set API
        sessionManager = new SessionManager(application);
    }

    public <T> LiveData<ApiResponse<T>> userLogIn(String userName, String passWord, Class<T> responseClass) {

        MutableLiveData<ApiResponse<T>> resultLiveData = new MutableLiveData<>();

        Call<ResponseBody> callAPI = apiServices.userLogin(userName, passWord, Constants.APP_NAME, Constants.LOGIN_REDIRECT_URL);

        callAPI.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() != 200) {
                    resultLiveData.postValue(ApiResponse.error("API call failed", null));
                    return;
                }

                if (sessionManager.getSessionID() == null) {
                    resultLiveData.postValue(ApiResponse.error("Session ID Not Found!", null));
                    return;
                }

                Call<ResponseBody> callAPI = apiServices.getManagerProfileData();

                resultLiveData.postValue(APIHandler.makeApiCall( callAPI, responseClass).getValue());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                resultLiveData.postValue(ApiResponse.error("API call failed", null));
            }
        });
        return resultLiveData;
    }

    public void getSomeThing(){}
}

