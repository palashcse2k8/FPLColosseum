package com.infotech.fplcolosseum.gameweek.viewmodels;

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

public class GameWeekViewModel extends ViewModel {
    private GameWeekRepository _gameWeekRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(Boolean.FALSE);
    private LiveData<ResponseBody> _data;

    private MediatorLiveData<List<ManagerModel>> _managerList;
    private MediatorLiveData<CustomGameWeekDataModel> _customGameWeekDataModelLiveData;
    public LiveData<CustomGameWeekDataModel> leagueGameWeekDataModel() {
        return _customGameWeekDataModelLiveData;
    }


//    public LiveData<List<ManagerModel>> getManagerList() {
//        return _managerList;
//    }

    public GameWeekViewModel() throws IOException {
        _gameWeekRepository = new GameWeekRepository();
        _data = new MutableLiveData<>();
        _customGameWeekDataModelLiveData = new MediatorLiveData<>();
        _managerList = new MediatorLiveData<>();

    }

    public void gameWeekDataFromAPI (String leagueID, String currentGameweek) throws IOException {
//        dataLoading.setValue(true);
        _customGameWeekDataModelLiveData.addSource(_gameWeekRepository.getGameWeekDataFromAPI(leagueID, currentGameweek), customGameWeekDataModel -> {
//            dataLoading.setValue(false);
            _customGameWeekDataModelLiveData.setValue(customGameWeekDataModel);
        });
    }

    public interface APIResponseListener {
        public ResponseBody onApiResponse(ResponseBody responseBody) throws IOException;
    }
}
