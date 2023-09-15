package com.infotech.fplcolosseum.gameweek.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infotech.fplcolosseum.gameweek.models.LeagueGameWeekDataModel;
import com.infotech.fplcolosseum.repository.GameWeekRepository;

import java.io.IOException;

import okhttp3.ResponseBody;

public class GameWeekViewModel extends ViewModel {
    private GameWeekRepository _gameWeekRepository;

    public MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(Boolean.FALSE);

    private LiveData<ResponseBody> _data;
    private LiveData<LeagueGameWeekDataModel> _leagueGameWeekDataModel;

    public LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel() {
        return _leagueGameWeekDataModel;
    }

    public GameWeekViewModel() throws IOException {
        _gameWeekRepository = new GameWeekRepository();
        _leagueGameWeekDataModel = new MutableLiveData<>();
        _data = new MutableLiveData<>();
    }

    public void gameWeekDataFromAPI (String leagueID, String entryID, String currentGameweek, String currentPage) throws IOException {
        dataLoading.setValue(true);
        _leagueGameWeekDataModel = _gameWeekRepository.gameWeekDataFromAPI(leagueID, entryID, currentGameweek, currentPage, LeagueGameWeekDataModel.class);
        dataLoading.setValue(false);
    }

    public interface APIResponseListener {
        public ResponseBody onApiResponse(ResponseBody responseBody) throws IOException;
    }
}
