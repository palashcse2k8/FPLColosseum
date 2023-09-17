package com.infotech.fplcolosseum.gameweek.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.infotech.fplcolosseum.gameweek.models.custom.CustomGameWeekDataModel;
import com.infotech.fplcolosseum.gameweek.models.web.LeagueGameWeekDataModel;
import com.infotech.fplcolosseum.repository.GameWeekRepository;

import java.io.IOException;

import okhttp3.ResponseBody;

public class GameWeekViewModel extends ViewModel {
    private GameWeekRepository _gameWeekRepository;
    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(Boolean.FALSE);
    private LiveData<ResponseBody> _data;
    private LiveData<CustomGameWeekDataModel> _customGameWeekDataModelLiveData;
    public LiveData<CustomGameWeekDataModel> leagueGameWeekDataModel() {
        return _customGameWeekDataModelLiveData;
    }

    public GameWeekViewModel() throws IOException {
        _gameWeekRepository = new GameWeekRepository();
        _data = new MutableLiveData<>();
    }

    public void gameWeekDataFromAPI (String leagueID, String entryID, String currentGameweek, String currentPage) throws IOException {
        dataLoading.setValue(true);
        _customGameWeekDataModelLiveData = _gameWeekRepository.gameWeekDataFromAPI(leagueID, entryID, currentGameweek, currentPage);
        dataLoading.setValue(false);
    }

    public interface APIResponseListener {
        public ResponseBody onApiResponse(ResponseBody responseBody) throws IOException;
    }
}
