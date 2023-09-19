package com.infotech.fplcolosseum.gameweek.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.custom.ManagerModel;
import com.infotech.fplcolosseum.repository.GameWeekRepository;

import java.io.IOException;
import java.util.ArrayList;
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

    public void gameWeekDataFromAPI (String leagueID, String entryID, String currentGameweek, String currentPage) throws IOException {
        dataLoading.setValue(true);
        _customGameWeekDataModelLiveData.addSource(_gameWeekRepository.gameMangerListFromAPI(leagueID, currentGameweek, currentPage), managerModels -> {
            CustomGameWeekDataModel customGameWeekDataModel = new CustomGameWeekDataModel();
            customGameWeekDataModel.setTeams(managerModels);
            _customGameWeekDataModelLiveData.postValue(customGameWeekDataModel);
        });
        dataLoading.setValue(false);
    }

//    public void gameMangerListFromAPI(String leagueID, String currentGameweek, String currentPage) throws IOException {
//        dataLoading.setValue(true);
//
//        // Source 1
//        LiveData<List<ManagerModel>> source1 = _gameWeekRepository.getManagerList(leagueID, currentGameweek, currentPage);
//        _managerList.addSource(source1, managerModels -> {
//            if (managerModels != null) {
//                _managerList.postValue(managerModels);
//
//                // Source 1 is completed, now call Source 2
//                callSource2(leagueID, currentGameweek, currentPage);
//            }
//        });
//
//        dataLoading.setValue(false);
//    }
//
//    private void callSource2(String leagueID, String currentGameweek, String currentPage) {
//        // Source 2
//        LiveData<List<ManagerModel>> source2 = _gameWeekRepository.getManagerList(leagueID, currentGameweek, "2");
//        _managerList.addSource(source2, managerModels -> {
//            if (managerModels != null) {
//                // Append data from Source 2 to _managerList
//                List<ManagerModel> currentData = _managerList.getValue();
//                if (currentData != null) {
//                    currentData.addAll(managerModels);
//                    _managerList.setValue(currentData);
//                }
//            }
//        });
//    }

    public interface APIResponseListener {
        public ResponseBody onApiResponse(ResponseBody responseBody) throws IOException;
    }
}
