package com.infotech.fplcolosseum.gameweek.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.repository.GameWeekRepository;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;

public class GameWeekViewModel extends AndroidViewModel {
    private GameWeekRepository _gameWeekRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(Boolean.FALSE);
    private LiveData<ResponseBody> _data;

    private MediatorLiveData<List<ManagerModel>> _managerList;
    private MediatorLiveData<CustomGameWeekDataModel> _customGameWeekDataModelLiveData;
    public LiveData<CustomGameWeekDataModel> leagueGameWeekDataModel() {
        return _customGameWeekDataModelLiveData;
    }

    public GameWeekViewModel(Application application) {
        super(application);
        _gameWeekRepository = new GameWeekRepository(application);
        _data = new MutableLiveData<>();
        _customGameWeekDataModelLiveData = new MediatorLiveData<>();
        _managerList = new MediatorLiveData<>();

    }

    public void deleteDatabase(String leagueID, String currentGameweek) {

        //delete all data from table
        _gameWeekRepository.deleteGameWeekData(leagueID, currentGameweek);
    }

    public void deleteAllGameWeekData() {

        //delete all row data from table
        _gameWeekRepository.deleteAllGameWeekData();
    }

    public void gameWeekDataFromAPI (String leagueID, String currentGameweek) throws IOException {
        _customGameWeekDataModelLiveData.addSource(_gameWeekRepository.getGameWeekData(leagueID, currentGameweek), customGameWeekDataModel -> {
            _customGameWeekDataModelLiveData.setValue(customGameWeekDataModel);
        });
    }
}
