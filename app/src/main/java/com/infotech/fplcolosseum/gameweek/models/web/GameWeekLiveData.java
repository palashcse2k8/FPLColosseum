package com.infotech.fplcolosseum.gameweek.models.web;

import java.util.ArrayList;

public class GameWeekLiveData {
    private float TransferCost;
    private float PastSeasonOverallRank;
    private float PastSeasonOverallPoints;
    private float Transfers;
//    ArrayList<Object> Players = new ArrayList<Object>();
    ArrayList<PlayerModel> Players;

    public ArrayList<PlayerModel> getPlayers() {
        return Players;
    }

    public void setPlayers(ArrayList<PlayerModel> players) {
        Players = players;
    }

    private String ActiveChip;
    private float Gameweek;
    private String CaptainWebName;
    private String ViceCaptainWebName;
    private String BonusPlayerWebName;
    private boolean IsFinished;
    private boolean ReturnAllData;
    private boolean CaptainIsDonePlaying;
    private boolean CaptainIsPlaying;
    private boolean CaptainIsLeft;
    private boolean EffectiveCaptainIsDonePlaying;
    private boolean EffectiveCaptainIsPlaying;
    private boolean EffectiveCaptainIsLeft;
    private boolean BonusIsDonePlaying;
    private boolean BonusIsPlaying;
    private boolean BonusIsLeft;
    private float LivePointsTotal;
    private float BonusPlayerPoints;
    private float LivePointsTotalIncTransferCost;
    private float TotalFinishedPoints;
    private float TotalFinishedBonusPlayerPoints;
    private float TotalPlayers;
    private float PlayersDonePlaying;
    private float PlayersIsPlaying;
    private float PlayersLeft;
    private float EffectivePlayersLeft;
    private float EffectivePlayersLeftWithMultiplier;
    private float SeasonBasePointsUntilGameWeek;
    private float SeasonBasePointsUntilGameWeekOverall;
    private float PhaseBasePointsUntilGameWeek;
    private float SeasonTotalPoints;
    private float PhaseTotalPoints;
    private String GwNetPointsOverallRank = null;
    private String OverallRank = null;
    private String OverallRankDirection = null;
    private float ImpactSummary;
    private String OverallRankAfterFinished;
    private String GameweekRankAfterFinished;


    // Getter Methods

    public float getTransferCost() {
        return TransferCost;
    }

    public float getPastSeasonOverallRank() {
        return PastSeasonOverallRank;
    }

    public float getPastSeasonOverallPoints() {
        return PastSeasonOverallPoints;
    }

    public float getTransfers() {
        return Transfers;
    }

    public String getActiveChip() {
        return ActiveChip;
    }

    public float getGameweek() {
        return Gameweek;
    }

    public String getCaptainWebName() {
        return CaptainWebName;
    }

    public String getViceCaptainWebName() {
        return ViceCaptainWebName;
    }

    public String getBonusPlayerWebName() {
        return BonusPlayerWebName;
    }

    public boolean getIsFinished() {
        return IsFinished;
    }

    public boolean getReturnAllData() {
        return ReturnAllData;
    }

    public boolean getCaptainIsDonePlaying() {
        return CaptainIsDonePlaying;
    }

    public boolean getCaptainIsPlaying() {
        return CaptainIsPlaying;
    }

    public boolean getCaptainIsLeft() {
        return CaptainIsLeft;
    }

    public boolean getEffectiveCaptainIsDonePlaying() {
        return EffectiveCaptainIsDonePlaying;
    }

    public boolean getEffectiveCaptainIsPlaying() {
        return EffectiveCaptainIsPlaying;
    }

    public boolean getEffectiveCaptainIsLeft() {
        return EffectiveCaptainIsLeft;
    }

    public boolean getBonusIsDonePlaying() {
        return BonusIsDonePlaying;
    }

    public boolean getBonusIsPlaying() {
        return BonusIsPlaying;
    }

    public boolean getBonusIsLeft() {
        return BonusIsLeft;
    }

    public float getLivePointsTotal() {
        return LivePointsTotal;
    }

    public float getBonusPlayerPoints() {
        return BonusPlayerPoints;
    }

    public float getLivePointsTotalIncTransferCost() {
        return LivePointsTotalIncTransferCost;
    }

    public float getTotalFinishedPoints() {
        return TotalFinishedPoints;
    }

    public float getTotalFinishedBonusPlayerPoints() {
        return TotalFinishedBonusPlayerPoints;
    }

    public float getTotalPlayers() {
        return TotalPlayers;
    }

    public float getPlayersDonePlaying() {
        return PlayersDonePlaying;
    }

    public float getPlayersIsPlaying() {
        return PlayersIsPlaying;
    }

    public float getPlayersLeft() {
        return PlayersLeft;
    }

    public float getEffectivePlayersLeft() {
        return EffectivePlayersLeft;
    }

    public float getEffectivePlayersLeftWithMultiplier() {
        return EffectivePlayersLeftWithMultiplier;
    }

    public float getSeasonBasePointsUntilGameWeek() {
        return SeasonBasePointsUntilGameWeek;
    }

    public float getSeasonBasePointsUntilGameWeekOverall() {
        return SeasonBasePointsUntilGameWeekOverall;
    }

    public float getPhaseBasePointsUntilGameWeek() {
        return PhaseBasePointsUntilGameWeek;
    }

    public float getSeasonTotalPoints() {
        return SeasonTotalPoints;
    }

    public float getPhaseTotalPoints() {
        return PhaseTotalPoints;
    }

    public String getGwNetPointsOverallRank() {
        return GwNetPointsOverallRank;
    }

    public String getOverallRank() {
        return OverallRank;
    }

    public String getOverallRankDirection() {
        return OverallRankDirection;
    }

    public float getImpactSummary() {
        return ImpactSummary;
    }

    public String getOverallRankAfterFinished() {
        return OverallRankAfterFinished;
    }

    public String getGameweekRankAfterFinished() {
        return GameweekRankAfterFinished;
    }

    // Setter Methods

    public void setTransferCost(float TransferCost) {
        this.TransferCost = TransferCost;
    }

    public void setPastSeasonOverallRank(float PastSeasonOverallRank) {
        this.PastSeasonOverallRank = PastSeasonOverallRank;
    }

    public void setPastSeasonOverallPoints(float PastSeasonOverallPoints) {
        this.PastSeasonOverallPoints = PastSeasonOverallPoints;
    }

    public void setTransfers(float Transfers) {
        this.Transfers = Transfers;
    }

    public void setActiveChip(String ActiveChip) {
        this.ActiveChip = ActiveChip;
    }

    public void setGameweek(float Gameweek) {
        this.Gameweek = Gameweek;
    }

    public void setCaptainWebName(String CaptainWebName) {
        this.CaptainWebName = CaptainWebName;
    }

    public void setViceCaptainWebName(String ViceCaptainWebName) {
        this.ViceCaptainWebName = ViceCaptainWebName;
    }

    public void setBonusPlayerWebName(String BonusPlayerWebName) {
        this.BonusPlayerWebName = BonusPlayerWebName;
    }

    public void setIsFinished(boolean IsFinished) {
        this.IsFinished = IsFinished;
    }

    public void setReturnAllData(boolean ReturnAllData) {
        this.ReturnAllData = ReturnAllData;
    }

    public void setCaptainIsDonePlaying(boolean CaptainIsDonePlaying) {
        this.CaptainIsDonePlaying = CaptainIsDonePlaying;
    }

    public void setCaptainIsPlaying(boolean CaptainIsPlaying) {
        this.CaptainIsPlaying = CaptainIsPlaying;
    }

    public void setCaptainIsLeft(boolean CaptainIsLeft) {
        this.CaptainIsLeft = CaptainIsLeft;
    }

    public void setEffectiveCaptainIsDonePlaying(boolean EffectiveCaptainIsDonePlaying) {
        this.EffectiveCaptainIsDonePlaying = EffectiveCaptainIsDonePlaying;
    }

    public void setEffectiveCaptainIsPlaying(boolean EffectiveCaptainIsPlaying) {
        this.EffectiveCaptainIsPlaying = EffectiveCaptainIsPlaying;
    }

    public void setEffectiveCaptainIsLeft(boolean EffectiveCaptainIsLeft) {
        this.EffectiveCaptainIsLeft = EffectiveCaptainIsLeft;
    }

    public void setBonusIsDonePlaying(boolean BonusIsDonePlaying) {
        this.BonusIsDonePlaying = BonusIsDonePlaying;
    }

    public void setBonusIsPlaying(boolean BonusIsPlaying) {
        this.BonusIsPlaying = BonusIsPlaying;
    }

    public void setBonusIsLeft(boolean BonusIsLeft) {
        this.BonusIsLeft = BonusIsLeft;
    }

    public void setLivePointsTotal(float LivePointsTotal) {
        this.LivePointsTotal = LivePointsTotal;
    }

    public void setBonusPlayerPoints(float BonusPlayerPoints) {
        this.BonusPlayerPoints = BonusPlayerPoints;
    }

    public void setLivePointsTotalIncTransferCost(float LivePointsTotalIncTransferCost) {
        this.LivePointsTotalIncTransferCost = LivePointsTotalIncTransferCost;
    }

    public void setTotalFinishedPoints(float TotalFinishedPoints) {
        this.TotalFinishedPoints = TotalFinishedPoints;
    }

    public void setTotalFinishedBonusPlayerPoints(float TotalFinishedBonusPlayerPoints) {
        this.TotalFinishedBonusPlayerPoints = TotalFinishedBonusPlayerPoints;
    }

    public void setTotalPlayers(float TotalPlayers) {
        this.TotalPlayers = TotalPlayers;
    }

    public void setPlayersDonePlaying(float PlayersDonePlaying) {
        this.PlayersDonePlaying = PlayersDonePlaying;
    }

    public void setPlayersIsPlaying(float PlayersIsPlaying) {
        this.PlayersIsPlaying = PlayersIsPlaying;
    }

    public void setPlayersLeft(float PlayersLeft) {
        this.PlayersLeft = PlayersLeft;
    }

    public void setEffectivePlayersLeft(float EffectivePlayersLeft) {
        this.EffectivePlayersLeft = EffectivePlayersLeft;
    }

    public void setEffectivePlayersLeftWithMultiplier(float EffectivePlayersLeftWithMultiplier) {
        this.EffectivePlayersLeftWithMultiplier = EffectivePlayersLeftWithMultiplier;
    }

    public void setSeasonBasePointsUntilGameWeek(float SeasonBasePointsUntilGameWeek) {
        this.SeasonBasePointsUntilGameWeek = SeasonBasePointsUntilGameWeek;
    }

    public void setSeasonBasePointsUntilGameWeekOverall(float SeasonBasePointsUntilGameWeekOverall) {
        this.SeasonBasePointsUntilGameWeekOverall = SeasonBasePointsUntilGameWeekOverall;
    }

    public void setPhaseBasePointsUntilGameWeek(float PhaseBasePointsUntilGameWeek) {
        this.PhaseBasePointsUntilGameWeek = PhaseBasePointsUntilGameWeek;
    }

    public void setSeasonTotalPoints(float SeasonTotalPoints) {
        this.SeasonTotalPoints = SeasonTotalPoints;
    }

    public void setPhaseTotalPoints(float PhaseTotalPoints) {
        this.PhaseTotalPoints = PhaseTotalPoints;
    }

    public void setGwNetPointsOverallRank(String GwNetPointsOverallRank) {
        this.GwNetPointsOverallRank = GwNetPointsOverallRank;
    }

    public void setOverallRank(String OverallRank) {
        this.OverallRank = OverallRank;
    }

    public void setOverallRankDirection(String OverallRankDirection) {
        this.OverallRankDirection = OverallRankDirection;
    }

    public void setImpactSummary(float ImpactSummary) {
        this.ImpactSummary = ImpactSummary;
    }

    public void setOverallRankAfterFinished(String OverallRankAfterFinished) {
        this.OverallRankAfterFinished = OverallRankAfterFinished;
    }

    public void setGameweekRankAfterFinished(String GameweekRankAfterFinished) {
        this.GameweekRankAfterFinished = GameweekRankAfterFinished;
    }
}