package com.infotech.fplcolosseum.login.viewmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.infotech.fplcolosseum.remote.APIServices;
import com.infotech.fplcolosseum.remote.RetroClass;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    public APIServices apiServices;

    public Application application;

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>(false);
    private MutableLiveData<String> userName = new MutableLiveData<>("palashcse2k8@gmail.com");
    private MutableLiveData<String> password = new MutableLiveData<>("Fantasy@2023");


    public LoginViewModel(@NonNull Application application) {
        super(application);
        apiServices = RetroClass.getAPIService(application);
        this.application = application;
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<Boolean> getDataLoading() {
        return dataLoading;
    }

    public void onLoginClick(){

        //return if not validate
        if(!isValidate())
            return;

        dataLoading.setValue(true);

        try {
            Call<ResponseBody> callAPI = apiServices.userLogin(getUserName().getValue(), getPassword().getValue(), Constants.APP_NAME, Constants.LOGIN_REDIRECT_URL);


            callAPI.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    dataLoading.setValue(false);

                    if(response.code() != 200) {
                        UIUtils.toast( application, "Login Unsuccessful! Please check your credential.", ToastLevel.WARNING);
                        return;
                    }

                    SharedPreferences sharedPreferences = application.getSharedPreferences(Constants.COOKIES, Context.MODE_PRIVATE);

                    if(response.code() == 200 && sharedPreferences.getString(Constants.PL_PROFILE, null) != null) {
                        isLoggedIn.setValue(true);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dataLoading.setValue(false);
                    UIUtils.toast( application, "Something went wrong!", ToastLevel.WARNING);
                }
            });

        } catch (Exception e) {
            dataLoading.setValue(false);
            UIUtils.toast( application, "Something went wrong, exception occurred!", ToastLevel.WARNING);
            e.printStackTrace();
        }
    }

    public boolean isValidate() {
        return CustomUtil.isValidEmail(getUserName().getValue());
    }
}
