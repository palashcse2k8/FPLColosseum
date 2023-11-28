package com.infotech.fplcolosseum.data.repositories;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.infotech.fplcolosseum.data.sources.remote.APIHandler;
import com.infotech.fplcolosseum.data.sources.remote.APIServices;
import com.infotech.fplcolosseum.data.sources.remote.ApiResponseCallback;
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

    public void userLogIn(String userName, String passWord) {

        Call<ResponseBody> callAPI = apiServices.userLogin(userName, passWord, Constants.APP_NAME, Constants.LOGIN_REDIRECT_URL);
        APIHandler.callLoginAPI(callAPI, new ApiResponseCallback() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {

                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.COOKIES, Context.MODE_PRIVATE);

            }

            @Override
            public void onFailure(String message) {

            }
        });

        try {



            callAPI.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.code() != 200) {
                        callback.onFailure("Login Unsuccessful! Please check your credential.");
                        return;
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callback.onFailure("Something went wrong!");
                }
            });

        } catch (Exception e) {
            callback.onFailure("Something went wrong, exception occurred!");
            e.printStackTrace();
        }
    }
}

