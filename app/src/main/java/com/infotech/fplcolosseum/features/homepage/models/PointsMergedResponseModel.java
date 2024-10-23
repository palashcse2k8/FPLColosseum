package com.infotech.fplcolosseum.features.homepage.models;

import com.infotech.fplcolosseum.features.homepage.models.entryinformation.TeamInformationResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.GameWeekMatchDetailsResponse;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.livepoints.GameWeekLivePointsResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.picks.GameWeekPicksModel;

import java.util.List;

public class PointsMergedResponseModel {
    private TeamInformationResponseModel teamInformationResponseModel;

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

    public TeamInformationResponseModel getGameWeekDataResponseModel() {
        return teamInformationResponseModel;
    }

    public void setGameWeekDataResponseModel(TeamInformationResponseModel teamInformationResponseModel) {
        this.teamInformationResponseModel = teamInformationResponseModel;
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
