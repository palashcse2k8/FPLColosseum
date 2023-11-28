package com.infotech.fplcolosseum.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() != 200) {
                    resultLiveData.setValue(ApiResponse.error("API call failed", null));
                    return;
                }

                if (sessionManager.getSessionID() == null) {
                    resultLiveData.setValue(ApiResponse.error("API call failed", null));
                    return;
                }

                resultLiveData.setValue(ApiResponse.success(null));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
        return resultLiveData;
    }
}

