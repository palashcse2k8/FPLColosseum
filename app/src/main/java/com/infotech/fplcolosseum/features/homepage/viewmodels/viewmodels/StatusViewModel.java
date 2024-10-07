package com.infotech.fplcolosseum.features.homepage.viewmodels.viewmodels;

import android.view.View;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.homepage.models.picks.GameWeekPicksModel;

public class StatusViewModel extends ViewModel {

    private final MediatorLiveData<ApiResponse<GameWeekPicksModel>> transferApiResultLiveData;

    public StatusViewModel() {
        this.transferApiResultLiveData = new MediatorLiveData<>();
    }
}
