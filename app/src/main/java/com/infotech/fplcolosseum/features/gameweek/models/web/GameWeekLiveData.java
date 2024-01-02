package com.infotech.fplcolosseum.features.gameweek.models.web;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekLiveData {
    private long TransferCost;
    private long PastSeasonOverallRank;
    private long PastSeasonOverallPoints;
    private long Transfers;
//    ArrayList<Object> Players = new ArrayList<Object>();
    @SerializedName("Players")
    ArrayList<PlayerResponseModel> Players;

    public ArrayList<PlayerResponseModel> getPlayers() {
        return Players;
    }

    public void setPlayers(ArrayList<PlayerResponseModel> players) {
        Players = players;
    }

    private String ActiveChip;
    private long Gameweek;
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
    private long LivePointsTotal;
    private long BonusPlayerPoints;
    private long LivePointsTotalIncTransferCost;
    private long TotalFinishedPoints;
    private long TotalFinishedBonusPlayerPoints;
    private long TotalPlayers;
    private long PlayersDonePlaying;
    private long PlayersIsPlaying;
    private long PlayersLeft;
    private long EffectivePlayersLeft;
    private long EffectivePlayersLeftWithMultiplier;
    private long SeasonBasePointsUntilGameWeek;
    private long SeasonBasePointsUntilGameWeekOverall;
    private long PhaseBasePointsUntilGameWeek;
    private long SeasonTotalPoints;
    private long PhaseTotalPoints;
    private String GwNetPointsOverallRank = null;
    private String OverallRank = null;
    private String OverallRankDirection = null;
    private long ImpactSummary;
    private String OverallRankAfterFinished;
    private String GameweekRankAfterFinished;


    // Getter Methods

    public long getTransferCost() {
        return TransferCost;
    }

    public long getPastSeasonOverallRank() {
        return PastSeasonOverallRank;
    }

    public long getPastSeasonOverallPoints() {
        return PastSeasonOverallPoints;
    }

    public long getTransfers() {
        return Transfers;
    }

    public String getActiveChip() {
        return ActiveChip;
    }

    public long getGameweek() {
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

    public long getLivePointsTotal() {
        return LivePointsTotal;
    }

    public long getBonusPlayerPoints() {
        return BonusPlayerPoints;
    }

    public long getLivePointsTotalIncTransferCost() {
        return LivePointsTotalIncTransferCost;
    }

    public long getTotalFinishedPoints() {
        return TotalFinishedPoints;
    }

    public long getTotalFinishedBonusPlayerPoints() {
        return TotalFinishedBonusPlayerPoints;
    }

    public long getTotalPlayers() {
        return TotalPlayers;
    }

    public long getPlayersDonePlaying() {
        return PlayersDonePlaying;
    }

    public long getPlayersIsPlaying() {
        return PlayersIsPlaying;
    }

    public long getPlayersLeft() {
        return PlayersLeft;
    }

    public long getEffectivePlayersLeft() {
        return EffectivePlayersLeft;
    }

    public long getEffectivePlayersLeftWithMultiplier() {
        return EffectivePlayersLeftWithMultiplier;
    }

    public long getSeasonBasePointsUntilGameWeek() {
        return SeasonBasePointsUntilGameWeek;
    }

    public long getSeasonBasePointsUntilGameWeekOverall() {
        return SeasonBasePointsUntilGameWeekOverall;
    }

    public long getPhaseBasePointsUntilGameWeek() {
        return PhaseBasePointsUntilGameWeek;
    }

    public long getSeasonTotalPoints() {
        return SeasonTotalPoints;
    }

    public long getPhaseTotalPoints() {
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

    public long getImpactSummary() {
        return ImpactSummary;
    }

    public String getOverallRankAfterFinished() {
        return OverallRankAfterFinished;
    }

    public String getGameweekRankAfterFinished() {
        return GameweekRankAfterFinished;
    }

    // Setter Methods

    public void setTransferCost(long TransferCost) {
        this.TransferCost = TransferCost;
    }

    public void setPastSeasonOverallRank(long PastSeasonOverallRank) {
        this.PastSeasonOverallRank = PastSeasonOverallRank;
    }

    public void setPastSeasonOverallPoints(long PastSeasonOverallPoints) {
        this.PastSeasonOverallPoints = PastSeasonOverallPoints;
    }

    public void setTransfers(long Transfers) {
        this.Transfers = Transfers;
    }

    public void setActiveChip(String ActiveChip) {
        this.ActiveChip = ActiveChip;
    }

    public void setGameweek(long Gameweek) {
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

    public void setLivePointsTotal(long LivePointsTotal) {
        this.LivePointsTotal = LivePointsTotal;
    }

    public void setBonusPlayerPoints(long BonusPlayerPoints) {
        this.BonusPlayerPoints = BonusPlayerPoints;
    }

    public void setLivePointsTotalIncTransferCost(long LivePointsTotalIncTransferCost) {
        this.LivePointsTotalIncTransferCost = LivePointsTotalIncTransferCost;
    }

    public void setTotalFinishedPoints(long TotalFinishedPoints) {
        this.TotalFinishedPoints = TotalFinishedPoints;
    }

    public void setTotalFinishedBonusPlayerPoints(long TotalFinishedBonusPlayerPoints) {
        this.TotalFinishedBonusPlayerPoints = TotalFinishedBonusPlayerPoints;
    }

    public void setTotalPlayers(long TotalPlayers) {
        this.TotalPlayers = TotalPlayers;
    }

    public void setPlayersDonePlaying(long PlayersDonePlaying) {
        this.PlayersDonePlaying = PlayersDonePlaying;
    }

    public void setPlayersIsPlaying(long PlayersIsPlaying) {
        this.PlayersIsPlaying = PlayersIsPlaying;
    }

    public void setPlayersLeft(long PlayersLeft) {
        this.PlayersLeft = PlayersLeft;
    }

    public void setEffectivePlayersLeft(long EffectivePlayersLeft) {
        this.EffectivePlayersLeft = EffectivePlayersLeft;
    }

    public void setEffectivePlayersLeftWithMultiplier(long EffectivePlayersLeftWithMultiplier) {
        this.EffectivePlayersLeftWithMultiplier = EffectivePlayersLeftWithMultiplier;
    }

    public void setSeasonBasePointsUntilGameWeek(long SeasonBasePointsUntilGameWeek) {
        this.SeasonBasePointsUntilGameWeek = SeasonBasePointsUntilGameWeek;
    }

    public void setSeasonBasePointsUntilGameWeekOverall(long SeasonBasePointsUntilGameWeekOverall) {
        this.SeasonBasePointsUntilGameWeekOverall = SeasonBasePointsUntilGameWeekOverall;
    }

    public void setPhaseBasePointsUntilGameWeek(long PhaseBasePointsUntilGameWeek) {
        this.PhaseBasePointsUntilGameWeek = PhaseBasePointsUntilGameWeek;
    }

    public void setSeasonTotalPoints(long SeasonTotalPoints) {
        this.SeasonTotalPoints = SeasonTotalPoints;
    }

    public void setPhaseTotalPoints(long PhaseTotalPoints) {
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

    public void setImpactSummary(long ImpactSummary) {
        this.ImpactSummary = ImpactSummary;
    }

    public void setOverallRankAfterFinished(String OverallRankAfterFinished) {
        this.OverallRankAfterFinished = OverallRankAfterFinished;
    }

    public void setGameweekRankAfterFinished(String GameweekRankAfterFinished) {
        this.GameweekRankAfterFinished = GameweekRankAfterFinished;
    }
}