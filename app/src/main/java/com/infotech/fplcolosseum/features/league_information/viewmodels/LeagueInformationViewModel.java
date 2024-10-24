package com.infotech.fplcolosseum.features.league_information.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.UserGameWeekDataRepository;
import com.infotech.fplcolosseum.data.sources.network.ApiResponse;
import com.infotech.fplcolosseum.features.league_information.models.LeagueInformationDataModel;

public class LeagueInformationViewModel extends AndroidViewModel {

    UserGameWeekDataRepository dataRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);

    private final MediatorLiveData<ApiResponse<LeagueInformationDataModel>> leagueStandingApiResultLiveData;


    public LeagueInformationViewModel(@NonNull Application application) {
        super(application);
        this.dataRepository = new UserGameWeekDataRepository(application);
        this.leagueStandingApiResultLiveData = new MediatorLiveData<>();
    }

    public LiveData<ApiResponse<LeagueInformationDataModel>> getLeagueStandingApiLiveData() {
        return leagueStandingApiResultLiveData;
    }

    public void getLeagueInformation(long leagueId, int phase, int pageStanding, int pageNewEntries) {

        dataLoading.setValue(true);
        // Make API call through the repository
        leagueStandingApiResultLiveData.addSource(dataRepository.getLeagueInformation(leagueId, phase, pageStanding, pageNewEntries), leagueInformationDataModelApiResponse -> {
            dataLoading.setValue(false);
            leagueStandingApiResultLiveData.setValue(leagueInformationDataModelApiResponse);
        });
    }
}
