package com.infotech.fplcolosseum.features.player_information.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;

public class PlayerInformationViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);

    private final MediatorLiveData<ApiResponse<?>> elementSummaryApiResultLiveData;


    public PlayerInformationViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.elementSummaryApiResultLiveData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<?>> getElementSummary() {
        return elementSummaryApiResultLiveData;
    }

    public void getElementSummary(long entry_id) {

        dataLoading.setValue(true);
        // Make API call through the repository
        elementSummaryApiResultLiveData.addSource(dataRepository.getElementSummaryData(entry_id), userResponseModelApiResponse -> {
            dataLoading.setValue(false);
            elementSummaryApiResultLiveData.setValue(userResponseModelApiResponse);
        });
    }
}
