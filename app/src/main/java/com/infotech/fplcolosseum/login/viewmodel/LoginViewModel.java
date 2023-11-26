package com.infotech.fplcolosseum.login.viewmodel;

import android.app.Application;
import android.content.ComponentName;


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

                    List<String> Cookielist = response.headers().values("Set-Cookie");
                    UIUtils.toast( application, "Login Successful", ToastLevel.SUCCESS);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    dataLoading.setValue(false);
                }
            });

        } catch (Exception e) {
            dataLoading.setValue(false);
            e.printStackTrace();
        }
    }

    public boolean isValidate() {
        return CustomUtil.isValidEmail(getUserName().getValue());
    }
}
