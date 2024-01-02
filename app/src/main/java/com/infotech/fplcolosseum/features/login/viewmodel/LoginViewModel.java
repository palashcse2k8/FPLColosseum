package com.infotech.fplcolosseum.features.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.login.models.UserResponseModel;
import com.infotech.fplcolosseum.utilities.CustomUtil;

public class LoginViewModel extends AndroidViewModel {
    public Application application;

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isLoggedIn = new MutableLiveData<>(false);
    public MutableLiveData<String> userName = new MutableLiveData<>("fantasy.palash@gmail.com");
    public MutableLiveData<String> password = new MutableLiveData<>("Fantasy@2024");

    private final MediatorLiveData<ApiResponse<?>> apiResultLiveData;

    UserGameWeekDataRepository userGameWeekDataRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.userGameWeekDataRepository = new UserGameWeekDataRepository(application);
        apiResultLiveData = new MediatorLiveData<>();
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public LiveData<ApiResponse<?>> getApiResultLiveData() {
        return apiResultLiveData;
    }

    public MutableLiveData<Boolean> getDataLoading() {
        return dataLoading;
    }

    public void onLoginClick() {

        //return if not validate
        if (!isValidate())
            return;

        dataLoading.setValue(true);
        // Make API call through the repository
        apiResultLiveData.addSource(userGameWeekDataRepository.userLogIn(userName.getValue(), password.getValue(), UserResponseModel.class), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            apiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }

    public boolean isValidate() {
        return CustomUtil.isValidEmail(getUserName().getValue());
    }

}
