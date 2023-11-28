package com.infotech.fplcolosseum.features.login.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.LoginRepository;
import com.infotech.fplcolosseum.data.sources.remote.APIServices;
import com.infotech.fplcolosseum.data.sources.remote.ApiResponseCallback;
import com.infotech.fplcolosseum.data.sources.remote.RetroClass;
import com.infotech.fplcolosseum.utilities.Constants;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    public APIServices apiServices;

    public Application application;

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>(false);
    public MutableLiveData<String> userName = new MutableLiveData<>("palashcse2k8@gmail.com");
    public MutableLiveData<String> password = new MutableLiveData<>("Fantasy@2023");

    LoginRepository loginRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        apiServices = RetroClass.getAPIService(application);
        this.application = application;
        this.loginRepository = new LoginRepository(application);
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

        loginRepository.userLogIn(userName.getValue(), password.getValue(), new ApiResponseCallback() {
            @Override
            public void onSuccess(Object responseRoot) {
                dataLoading.setValue(false);
                isLoggedIn.setValue(true);
                UIUtils.toast(application, responseRoot.toString(), ToastLevel.SUCCESS);
            }

            @Override
            public void onFailure(String message) {
                dataLoading.setValue(true);
                isLoggedIn.setValue(false);
                UIUtils.toast(application, message, ToastLevel.SUCCESS);
            }
        });
    }

    public boolean isValidate() {
        return CustomUtil.isValidEmail(getUserName().getValue());
    }

}
