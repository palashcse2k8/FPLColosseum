package com.infotech.fplcolosseum.features.homepage.models;

import com.infotech.fplcolosseum.features.homepage.models.entryinformation.GameWeekDataResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.GameWeekMatchDetailsResponse;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.GameWeekLivePointsResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.picks.GameWeekPicksModel;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.GameWeekStaticDataModel;

import java.util.List;

public class MyTeamMergedResponseModel {
    private GameWeekDataResponseModel gameWeekDataResponseModel;

    public List<MatchDetails> getMatchDetails() {
        return matchDetails;
    }

    public GameWeekStaticDataModel gameWeekStaticDataModel;

    public GameWeekStaticDataModel getGameWeekStaticDataModel() {
        return gameWeekStaticDataModel;
    }

    public void setGameWeekStaticDataModel(GameWeekStaticDataModel gameWeekStaticDataModel) {
        this.gameWeekStaticDataModel = gameWeekStaticDataModel;
    }

    public void setMatchDetails(List<MatchDetails> matchDetails) {
        this.matchDetails = matchDetails;
    }

    private GameWeekMatchDetailsResponse gameWeekMatchDetailsResponse;
    List<MatchDetails> matchDetails;

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


    private GameWeekMyTeamResponseModel gameWeekMyTeamResponseModel;

    public GameWeekMatchDetailsResponse getGameWeekMatchDetailsResponse() {
        return gameWeekMatchDetailsResponse;
    }

    public void setGameWeekMatchDetailsResponse(GameWeekMatchDetailsResponse gameWeekMatchDetailsResponse) {
        this.gameWeekMatchDetailsResponse = gameWeekMatchDetailsResponse;
    }

    public GameWeekMyTeamResponseModel getGameWeekMyTeamResponseModel() {
        return gameWeekMyTeamResponseModel;
    }

    public void setGameWeekMyTeamResponseModel(GameWeekMyTeamResponseModel gameWeekMyTeamResponseModel) {
        this.gameWeekMyTeamResponseModel = gameWeekMyTeamResponseModel;
    }
}
