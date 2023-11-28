package com.infotech.fplcolosseum.features.login.viewmodel;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.LoginRepository;
import com.infotech.fplcolosseum.data.sources.remote.ApiResponse;
import com.infotech.fplcolosseum.data.sources.remote.ApiResponseCallback;
import com.infotech.fplcolosseum.utilities.CustomUtil;
import com.infotech.fplcolosseum.utilities.ToastLevel;
import com.infotech.fplcolosseum.utilities.UIUtils;
import com.infotech.fplcolosseum.utilities.Utility;

public class LoginViewModel extends AndroidViewModel {
    public Application application;

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>(false);
    public MutableLiveData<String> userName = new MutableLiveData<>("palashcse2k8@gmail.com");
    public MutableLiveData<String> password = new MutableLiveData<>("Fantasy@2023");

    private MutableLiveData<ApiResponse<Object>> apiResultLiveData = new MutableLiveData<ApiResponse<Object>>();

    LoginRepository loginRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.loginRepository = new LoginRepository(application);
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public LiveData<ApiResponse<Object>> getApiResultLiveData() {
        return apiResultLiveData;
    }

    public MutableLiveData<Boolean> getDataLoading() {
        return dataLoading;
    }

    public void onLoginClick(){

        //return if not validate
        if(!isValidate())
            return;

        dataLoading.setValue(true);
        // Make API call through the repository
        loginRepository.userLogIn(userName.getValue(), password.getValue(), String.class).observeForever(apiResponse -> {
            // Update the LiveData with the result from the repository
            if (apiResponse != null) {
                switch (apiResponse.getStatus()) {
                    case SUCCESS:
                        // Handle success
                        dataLoading.setValue(false);
                        isLoggedIn.setValue(true);
                        // ...
                        break;
                    case ERROR:
                        // Handle error
                        isLoggedIn.setValue(false);
                        String errorMessage = apiResponse.getMessage();
                        dataLoading.setValue(false);
                        // ...
                        break;
                    case LOADING:
                        // Handle loading
                        dataLoading.setValue(true);
                        // ...
                        break;
                }
            }
            apiResultLiveData.setValue(apiResponse);
        });
    }

    public boolean isValidate() {
        return CustomUtil.isValidEmail(getUserName().getValue());
    }

}
