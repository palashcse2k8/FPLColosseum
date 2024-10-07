package com.infotech.fplcolosseum.features.homepage.models.status;

import com.infotech.fplcolosseum.features.homepage.models.entryinformation.GameWeekDataResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.fixture.MatchDetails;
import com.infotech.fplcolosseum.features.homepage.models.myteam.GameWeekMyTeamResponseModel;
import com.infotech.fplcolosseum.features.homepage.models.picks.GameWeekPicksModel;

import java.util.List;

public class StatusMergedResponseModel {
    private GameWeekMyTeamResponseModel gameWeekMyTeamResponseModel;
    private GameWeekPicksModel gameWeekPicksModel;
    private GameWeekStatus gameWeekStatus;
    private List<BestTeamDataModel> bestTeamDataModels;
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

    public List<BestTeamDataModel> getBestTeamDataModels() {
        return bestTeamDataModels;
    }

    public void setBestTeamDataModels(List<BestTeamDataModel> bestTeamDataModels) {
        this.bestTeamDataModels = bestTeamDataModels;
    }

    public List<ValuableTeamDataModel> getValuableTeamDataModels() {
        return valuableTeamDataModels;
    }

    public void setValuableTeamDataModels(List<ValuableTeamDataModel> valuableTeamDataModels) {
        this.valuableTeamDataModels = valuableTeamDataModels;
    }
}
