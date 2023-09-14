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

    private MutableLiveData<ResponseBody> _data;
    private MutableLiveData<LeagueGameWeekDataModel> _leagueGameWeekDataModel;

    public LiveData<LeagueGameWeekDataModel> leagueGameWeekDataModel() {
        return _leagueGameWeekDataModel;
    }

    public LiveData<ResponseBody> data() {
        return _data;
    }

    public GameWeekViewModel() throws IOException {
        _gameWeekRepository = new GameWeekRepository();
        _leagueGameWeekDataModel = new MutableLiveData<>();
        _data = new MutableLiveData<>();
    }

    public void gameWeekDataFromAPI (String leagueID, String entryID, String currentGameweek, String currentPage) throws IOException {
        _data.setValue( _gameWeekRepository.gameWeekDataFromAPI("671887", "116074", "1", "1").getValue());
        LeagueGameWeekDataModel leagueGameWeekData = convertResponse(_data.getValue(), LeagueGameWeekDataModel.class);
        this._leagueGameWeekDataModel.setValue(leagueGameWeekData);
    }

    public <T> T convertResponse(ResponseBody responseBody, Class T) throws IOException {

        String json = responseBody.string();
        Log.d("apiresponse=>", json);
        T convertedResponse = new Gson().fromJson(json, new TypeToken<T>() {
        }.getType());

        return convertedResponse;
    }
}
