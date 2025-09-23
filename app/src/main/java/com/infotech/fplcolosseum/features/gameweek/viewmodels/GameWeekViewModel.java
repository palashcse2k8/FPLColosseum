package com.infotech.fplcolosseum.features.gameweek.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.infotech.fplcolosseum.data.repositories.GameWeekRepository;
import com.infotech.fplcolosseum.features.gameweek.models.custom.CustomGameWeekDataModel;

import java.io.IOException;

public class GameWeekViewModel extends AndroidViewModel {
    private final GameWeekRepository _gameWeekRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(Boolean.FALSE);

    private final MediatorLiveData<CustomGameWeekDataModel> _customGameWeekDataModelLiveData;
    public LiveData<CustomGameWeekDataModel> leagueGameWeekDataModel() {
        return _customGameWeekDataModelLiveData;
    }

    public GameWeekViewModel(Application application) {
        super(application);
        _gameWeekRepository = new GameWeekRepository(application);
        _customGameWeekDataModelLiveData = new MediatorLiveData<>();

    }

    public void deleteDatabase(String leagueID, String currentGameweek) {

        //delete all data from table
        _gameWeekRepository.deleteGameWeekData(leagueID, currentGameweek);
    }

    public void deleteAllGameWeekData() {

        //delete all row data from table
        _gameWeekRepository.deleteAllGameWeekData();
    }

    public void gameWeekDataFromAPI(String leagueID, String currentGameweek)  throws IOException {
        LiveData<CustomGameWeekDataModel> source = _gameWeekRepository.getGameWeekData(leagueID, currentGameweek);

        _customGameWeekDataModelLiveData.addSource(source, data -> {
            _customGameWeekDataModelLiveData.setValue(data);
            _customGameWeekDataModelLiveData.removeSource(source); // ✅ prevent duplicates
        });

    }
}
