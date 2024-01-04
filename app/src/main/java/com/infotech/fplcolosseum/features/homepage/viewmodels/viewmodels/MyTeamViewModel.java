package com.infotech.fplcolosseum.features.homepage.viewmodels.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.GameWeekStaticDataRepository;
import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.APIServices;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.login.models.SessionManager;

public class MyTeamViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;
    APIServices apiServices;

    SessionManager sessionManager;

    private final MediatorLiveData<ApiResponse<?>> apiResultLiveData;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);

    Application application;
    public MyTeamViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.apiResultLiveData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<?>> getApiResultLiveData() {
        return apiResultLiveData;
    }

    public void getMyTeamData(long entry_id) {

        dataLoading.setValue(true);
        // Make API call through the repository
        apiResultLiveData.addSource(dataRepository.getMyTeamData(entry_id), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            apiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }
}
