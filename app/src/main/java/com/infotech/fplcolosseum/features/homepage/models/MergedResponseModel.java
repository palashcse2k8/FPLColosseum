package com.infotech.fplcolosseum.features.homepage.models;

import com.infotech.fplcolosseum.features.homepage.models.entryinformation.GameWeekDataResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.GameWeekMatchDetailsResponse;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.GameWeekLivePointsResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.picks.GameWeekPicksModel;

import java.util.ArrayList;
import java.util.List;

public class MergedResponseModel {
    private GameWeekDataResponseModel gameWeekDataResponseModel;

    public List<MatchDetails> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(List<MatchDetails> matchDetails) {
        this.matchDetails = matchDetails;
    }

    private GameWeekMatchDetailsResponse gameWeekMatchDetailsResponse;
    List<MatchDetails> matchDetails;
    private GameWeekPicksModel gameWeekPicksModel;
    private GameWeekLivePointsResponseModel gameWeekLivePointsResponseModel;

    public GameWeekDataResponseModel getGameWeekDataResponseModel() {
        return gameWeekDataResponseModel;
    }

    public void setGameWeekDataResponseModel(GameWeekDataResponseModel gameWeekDataResponseModel) {
        this.gameWeekDataResponseModel = gameWeekDataResponseModel;
    }

    public GameWeekMatchDetailsResponse getGameWeekMatchDetails() {
        return gameWeekMatchDetailsResponse;
    }

    public void setGameWeekMatchDetails(GameWeekMatchDetailsResponse gameWeekMatchDetailsResponse) {
        this.gameWeekMatchDetailsResponse = gameWeekMatchDetailsResponse;
    }

    public GameWeekPicksModel getGameWeekPicksModel() {
        return gameWeekPicksModel;
    }

    public void setGameWeekPicksModel(GameWeekPicksModel gameWeekPicksModel) {
        this.gameWeekPicksModel = gameWeekPicksModel;
    }

    public GameWeekLivePointsResponseModel getGameWeekLivePointsResponseModel() {
        return gameWeekLivePointsResponseModel;
    }

    public void setGameWeekLivePointsResponseModel(GameWeekLivePointsResponseModel gameWeekLivePointsResponseModel) {
        this.gameWeekLivePointsResponseModel = gameWeekLivePointsResponseModel;
    }
}
