package com.infotech.fplcolosseum.features.homepage.models.status;

import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.picks.GameWeekPicksModel;

import java.util.List;

public class StatusMergedResponseModel {
    private GameWeekMyTeamResponseModel gameWeekMyTeamResponseModel;
    private GameWeekPicksModel gameWeekPicksModel;
    private GameWeekStatus gameWeekStatus;
    private List<BestLeagueDataModel> bestLeagueDataModels;
    private List<ValuableTeamDataModel> valuableTeamDataModels;

    public GameWeekMyTeamResponseModel getGameWeekMyTeamResponseModel() {
        return gameWeekMyTeamResponseModel;
    }

    public void setGameWeekMyTeamResponseModel(GameWeekMyTeamResponseModel gameWeekMyTeamResponseModel) {
        this.gameWeekMyTeamResponseModel = gameWeekMyTeamResponseModel;
    }

    public GameWeekPicksModel getGameWeekPicksModel() {
        return gameWeekPicksModel;
    }

    public void setGameWeekPicksModel(GameWeekPicksModel gameWeekPicksModel) {
        this.gameWeekPicksModel = gameWeekPicksModel;
    }

    public GameWeekStatus getGameWeekStatus() {
        return gameWeekStatus;
    }

    public void setGameWeekStatus(GameWeekStatus gameWeekStatus) {
        this.gameWeekStatus = gameWeekStatus;
    }

    public List<BestLeagueDataModel> getBestTeamDataModels() {
        return bestLeagueDataModels;
    }

    public void setBestTeamDataModels(List<BestLeagueDataModel> bestLeagueDataModels) {
        this.bestLeagueDataModels = bestLeagueDataModels;
    }

    public List<ValuableTeamDataModel> getValuableTeamDataModels() {
        return valuableTeamDataModels;
    }

    public void setValuableTeamDataModels(List<ValuableTeamDataModel> valuableTeamDataModels) {
        this.valuableTeamDataModels = valuableTeamDataModels;
    }
}
