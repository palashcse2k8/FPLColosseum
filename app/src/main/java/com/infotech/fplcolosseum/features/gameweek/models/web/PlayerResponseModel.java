package com.infotech.fplcolosseum.features.gameweek.models.web;

import java.util.ArrayList;

public class PlayerResponseModel {
    private boolean ForcedInTeamNotEnoughReqPlayers;
    private boolean IsCaptain;
    private boolean IsViceCaptain;
    private boolean IsTrippleCaptain;
    private boolean IsBonusPlayer;
    private boolean IsSub;
    private boolean IsSubIn;
    private boolean IsSubOut;
    private boolean IsEffectiveCaptain;
    private boolean IsEffectiveTrippleCaptain;
    private boolean IsUsingBenchBoost;
    private long TeamPosition;
    private long Multiplier;
    private long TotalPoints;
    private long BonusPlayerPoints;
    private boolean OnBenchInPlayerTeam;
    private boolean ComeOnFromBenchInPlayerTeam;
    private boolean ShowAsStartPlayerInPlayerTeam;
    private String MultiplierVsRestOfLeague;
    private String LeagueImportance;
    private boolean CanEffect;
    private boolean CanEffectLeft;
    private long Id;
    private long TeamId;
    private long TeamCode;
    private long NumFixtures;
    private String PlayerWebName;
    private String TeamName;
    private String TeamFullName = null;
    private String News;
    private String ManualSubNotes;
    private long DisplaySortOrder;
    private String KickOffTime;
    private long PlayerPosition;
    private long PlayerMinutes;
    private boolean FixtureStarted;
    private boolean FixtureFinished;
    private boolean FixtureFinishedProvisional;
    private long AllFixturesTotalMinutesPlayed;
    private long AllFixturesMinutesLeft;
    private boolean GameWeekIsFinished;
    private boolean PlayerHasNotStarted;
    private boolean PlayerIsPlaying;
    private boolean PlayerHasPlayedProvisional;
    private boolean PlayerHasPlayedFinal;
    private boolean PlayerDidNotPlay;
    private String PlayerPointsStatusText;
    private String OpponentDisplayName;
    private String OpponentShortName;
    private long FplReportedPoints;
    private long LiveBonusPoints;
    private long Difficulty;
    private long ChanceOfPlaying;
    private double SelectedByPercent;
    private double Cost;
    private double ExpectedPoints;
    private long FormCalculated;
    private long FormCalculatedTransfers;
    private String PlayerDisplayName;
    private long Points;
    private boolean ReturnPlayerStatsData;
    ArrayList< Object > PlayerStatsData = new ArrayList < Object > ();


    // Getter Methods

    public boolean getForcedInTeamNotEnoughReqPlayers() {
        return ForcedInTeamNotEnoughReqPlayers;
    }

    public boolean getIsCaptain() {
        return IsCaptain;
    }

    public boolean getIsViceCaptain() {
        return IsViceCaptain;
    }

    public boolean getIsTrippleCaptain() {
        return IsTrippleCaptain;
    }

    public boolean getIsBonusPlayer() {
        return IsBonusPlayer;
    }

    public boolean getIsSub() {
        return IsSub;
    }

    public boolean getIsSubIn() {
        return IsSubIn;
    }

    public boolean getIsSubOut() {
        return IsSubOut;
    }

    public boolean getIsEffectiveCaptain() {
        return IsEffectiveCaptain;
    }

    public boolean getIsEffectiveTrippleCaptain() {
        return IsEffectiveTrippleCaptain;
    }

    public boolean getIsUsingBenchBoost() {
        return IsUsingBenchBoost;
    }

    public long getTeamPosition() {
        return TeamPosition;
    }

    public long getMultiplier() {
        return Multiplier;
    }

    public long getTotalPoints() {
        return TotalPoints;
    }

    public long getBonusPlayerPoints() {
        return BonusPlayerPoints;
    }

    public boolean getOnBenchInPlayerTeam() {
        return OnBenchInPlayerTeam;
    }

    public boolean getComeOnFromBenchInPlayerTeam() {
        return ComeOnFromBenchInPlayerTeam;
    }

    public boolean getShowAsStartPlayerInPlayerTeam() {
        return ShowAsStartPlayerInPlayerTeam;
    }

    public String getMultiplierVsRestOfLeague() {
        return MultiplierVsRestOfLeague;
    }

    public String getLeagueImportance() {
        return LeagueImportance;
    }

    public boolean getCanEffect() {
        return CanEffect;
    }

    public boolean getCanEffectLeft() {
        return CanEffectLeft;
    }

    public long getId() {
        return Id;
    }

    public long getTeamId() {
        return TeamId;
    }

    public long getTeamCode() {
        return TeamCode;
    }

    public long getNumFixtures() {
        return NumFixtures;
    }

    public String getPlayerWebName() {
        return PlayerWebName;
    }

    public String getTeamName() {
        return TeamName;
    }

    public String getTeamFullName() {
        return TeamFullName;
    }

    public String getNews() {
        return News;
    }

    public String getManualSubNotes() {
        return ManualSubNotes;
    }

    public long getDisplaySortOrder() {
        return DisplaySortOrder;
    }

    public String getKickOffTime() {
        return KickOffTime;
    }

    public long getPlayerPosition() {
        return PlayerPosition;
    }

    public long getPlayerMinutes() {
        return PlayerMinutes;
    }

    public boolean getFixtureStarted() {
        return FixtureStarted;
    }

    public boolean getFixtureFinished() {
        return FixtureFinished;
    }

    public boolean getFixtureFinishedProvisional() {
        return FixtureFinishedProvisional;
    }

    public long getAllFixturesTotalMinutesPlayed() {
        return AllFixturesTotalMinutesPlayed;
    }

    public long getAllFixturesMinutesLeft() {
        return AllFixturesMinutesLeft;
    }

    public boolean getGameWeekIsFinished() {
        return GameWeekIsFinished;
    }

    public boolean getPlayerHasNotStarted() {
        return PlayerHasNotStarted;
    }

    public boolean getPlayerIsPlaying() {
        return PlayerIsPlaying;
    }

    public boolean getPlayerHasPlayedProvisional() {
        return PlayerHasPlayedProvisional;
    }

    public boolean getPlayerHasPlayedFinal() {
        return PlayerHasPlayedFinal;
    }

    public boolean getPlayerDidNotPlay() {
        return PlayerDidNotPlay;
    }

    public String getPlayerPointsStatusText() {
        return PlayerPointsStatusText;
    }

    public String getOpponentDisplayName() {
        return OpponentDisplayName;
    }

    public String getOpponentShortName() {
        return OpponentShortName;
    }

    public long getFplReportedPoints() {
        return FplReportedPoints;
    }

    public long getLiveBonusPoints() {
        return LiveBonusPoints;
    }

    public long getDifficulty() {
        return Difficulty;
    }

    public long getChanceOfPlaying() {
        return ChanceOfPlaying;
    }

    public double getSelectedByPercent() {
        return SelectedByPercent;
    }

    public double getCost() {
        return Cost;
    }

    public double getExpectedPoints() {
        return ExpectedPoints;
    }

    public long getFormCalculated() {
        return FormCalculated;
    }

    public long getFormCalculatedTransfers() {
        return FormCalculatedTransfers;
    }

    public String getPlayerDisplayName() {
        return PlayerDisplayName;
    }

    public long getPoints() {
        return Points;
    }

    public boolean getReturnPlayerStatsData() {
        return ReturnPlayerStatsData;
    }

    // Setter Methods

    public void setForcedInTeamNotEnoughReqPlayers(boolean ForcedInTeamNotEnoughReqPlayers) {
        this.ForcedInTeamNotEnoughReqPlayers = ForcedInTeamNotEnoughReqPlayers;
    }

    public void setIsCaptain(boolean IsCaptain) {
        this.IsCaptain = IsCaptain;
    }

    public void setIsViceCaptain(boolean IsViceCaptain) {
        this.IsViceCaptain = IsViceCaptain;
    }

    public void setIsTrippleCaptain(boolean IsTrippleCaptain) {
        this.IsTrippleCaptain = IsTrippleCaptain;
    }

    public void setIsBonusPlayer(boolean IsBonusPlayer) {
        this.IsBonusPlayer = IsBonusPlayer;
    }

    public void setIsSub(boolean IsSub) {
        this.IsSub = IsSub;
    }

    public void setIsSubIn(boolean IsSubIn) {
        this.IsSubIn = IsSubIn;
    }

    public void setIsSubOut(boolean IsSubOut) {
        this.IsSubOut = IsSubOut;
    }

    public void setIsEffectiveCaptain(boolean IsEffectiveCaptain) {
        this.IsEffectiveCaptain = IsEffectiveCaptain;
    }

    public void setIsEffectiveTrippleCaptain(boolean IsEffectiveTrippleCaptain) {
        this.IsEffectiveTrippleCaptain = IsEffectiveTrippleCaptain;
    }

    public void setIsUsingBenchBoost(boolean IsUsingBenchBoost) {
        this.IsUsingBenchBoost = IsUsingBenchBoost;
    }

    public void setTeamPosition(long TeamPosition) {
        this.TeamPosition = TeamPosition;
    }

    public void setMultiplier(long Multiplier) {
        this.Multiplier = Multiplier;
    }

    public void setTotalPoints(long TotalPoints) {
        this.TotalPoints = TotalPoints;
    }

    public void setBonusPlayerPoints(long BonusPlayerPoints) {
        this.BonusPlayerPoints = BonusPlayerPoints;
    }

    public void setOnBenchInPlayerTeam(boolean OnBenchInPlayerTeam) {
        this.OnBenchInPlayerTeam = OnBenchInPlayerTeam;
    }

    public void setComeOnFromBenchInPlayerTeam(boolean ComeOnFromBenchInPlayerTeam) {
        this.ComeOnFromBenchInPlayerTeam = ComeOnFromBenchInPlayerTeam;
    }

    public void setShowAsStartPlayerInPlayerTeam(boolean ShowAsStartPlayerInPlayerTeam) {
        this.ShowAsStartPlayerInPlayerTeam = ShowAsStartPlayerInPlayerTeam;
    }

    public void setMultiplierVsRestOfLeague(String MultiplierVsRestOfLeague) {
        this.MultiplierVsRestOfLeague = MultiplierVsRestOfLeague;
    }

    public void setLeagueImportance(String LeagueImportance) {
        this.LeagueImportance = LeagueImportance;
    }

    public void setCanEffect(boolean CanEffect) {
        this.CanEffect = CanEffect;
    }

    public void setCanEffectLeft(boolean CanEffectLeft) {
        this.CanEffectLeft = CanEffectLeft;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public void setTeamId(long TeamId) {
        this.TeamId = TeamId;
    }

    public void setTeamCode(long TeamCode) {
        this.TeamCode = TeamCode;
    }

    public void setNumFixtures(long NumFixtures) {
        this.NumFixtures = NumFixtures;
    }

    public void setPlayerWebName(String PlayerWebName) {
        this.PlayerWebName = PlayerWebName;
    }

    public void setTeamName(String TeamName) {
        this.TeamName = TeamName;
    }

    public void setTeamFullName(String TeamFullName) {
        this.TeamFullName = TeamFullName;
    }

    public void setNews(String News) {
        this.News = News;
    }

    public void setManualSubNotes(String ManualSubNotes) {
        this.ManualSubNotes = ManualSubNotes;
    }

    public void setDisplaySortOrder(long DisplaySortOrder) {
        this.DisplaySortOrder = DisplaySortOrder;
    }

    public void setKickOffTime(String KickOffTime) {
        this.KickOffTime = KickOffTime;
    }

    public void setPlayerPosition(long PlayerPosition) {
        this.PlayerPosition = PlayerPosition;
    }

    public void setPlayerMinutes(long PlayerMinutes) {
        this.PlayerMinutes = PlayerMinutes;
    }

    public void setFixtureStarted(boolean FixtureStarted) {
        this.FixtureStarted = FixtureStarted;
    }

    public void setFixtureFinished(boolean FixtureFinished) {
        this.FixtureFinished = FixtureFinished;
    }

    public void setFixtureFinishedProvisional(boolean FixtureFinishedProvisional) {
        this.FixtureFinishedProvisional = FixtureFinishedProvisional;
    }

    public void setAllFixturesTotalMinutesPlayed(long AllFixturesTotalMinutesPlayed) {
        this.AllFixturesTotalMinutesPlayed = AllFixturesTotalMinutesPlayed;
    }

    public void setAllFixturesMinutesLeft(long AllFixturesMinutesLeft) {
        this.AllFixturesMinutesLeft = AllFixturesMinutesLeft;
    }

    public void setGameWeekIsFinished(boolean GameWeekIsFinished) {
        this.GameWeekIsFinished = GameWeekIsFinished;
    }

    public void setPlayerHasNotStarted(boolean PlayerHasNotStarted) {
        this.PlayerHasNotStarted = PlayerHasNotStarted;
    }

    public void setPlayerIsPlaying(boolean PlayerIsPlaying) {
        this.PlayerIsPlaying = PlayerIsPlaying;
    }

    public void setPlayerHasPlayedProvisional(boolean PlayerHasPlayedProvisional) {
        this.PlayerHasPlayedProvisional = PlayerHasPlayedProvisional;
    }

    public void setPlayerHasPlayedFinal(boolean PlayerHasPlayedFinal) {
        this.PlayerHasPlayedFinal = PlayerHasPlayedFinal;
    }

    public void setPlayerDidNotPlay(boolean PlayerDidNotPlay) {
        this.PlayerDidNotPlay = PlayerDidNotPlay;
    }

    public void setPlayerPointsStatusText(String PlayerPointsStatusText) {
        this.PlayerPointsStatusText = PlayerPointsStatusText;
    }

    public void setOpponentDisplayName(String OpponentDisplayName) {
        this.OpponentDisplayName = OpponentDisplayName;
    }

    public void setOpponentShortName(String OpponentShortName) {
        this.OpponentShortName = OpponentShortName;
    }

    public void setFplReportedPoints(long FplReportedPoints) {
        this.FplReportedPoints = FplReportedPoints;
    }

    public void setLiveBonusPoints(long LiveBonusPoints) {
        this.LiveBonusPoints = LiveBonusPoints;
    }

    public void setDifficulty(long Difficulty) {
        this.Difficulty = Difficulty;
    }

    public void setChanceOfPlaying(long ChanceOfPlaying) {
        this.ChanceOfPlaying = ChanceOfPlaying;
    }

    public void setSelectedByPercent(double SelectedByPercent) {
        this.SelectedByPercent = SelectedByPercent;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public void setExpectedPoints(double ExpectedPoints) {
        this.ExpectedPoints = ExpectedPoints;
    }

    public void setFormCalculated(long FormCalculated) {
        this.FormCalculated = FormCalculated;
    }

    public void setFormCalculatedTransfers(long FormCalculatedTransfers) {
        this.FormCalculatedTransfers = FormCalculatedTransfers;
    }

    public void setPlayerDisplayName(String PlayerDisplayName) {
        this.PlayerDisplayName = PlayerDisplayName;
    }

    public void setPoints(long Points) {
        this.Points = Points;
    }

    public void setReturnPlayerStatsData(boolean ReturnPlayerStatsData) {
        this.ReturnPlayerStatsData = ReturnPlayerStatsData;
    }
}