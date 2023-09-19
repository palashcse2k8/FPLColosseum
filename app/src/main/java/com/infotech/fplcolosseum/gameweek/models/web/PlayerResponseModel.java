package com.infotech.fplcolosseum.gameweek.models.web;

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
    private float TeamPosition;
    private float Multiplier;
    private float TotalPoints;
    private float BonusPlayerPoints;
    private boolean OnBenchInPlayerTeam;
    private boolean ComeOnFromBenchInPlayerTeam;
    private boolean ShowAsStartPlayerInPlayerTeam;
    private String MultiplierVsRestOfLeague;
    private String LeagueImportance;
    private boolean CanEffect;
    private boolean CanEffectLeft;
    private float Id;
    private float TeamId;
    private float TeamCode;
    private float NumFixtures;
    private String PlayerWebName;
    private String TeamName;
    private String TeamFullName = null;
    private String News;
    private String ManualSubNotes;
    private float DisplaySortOrder;
    private String KickOffTime;
    private float PlayerPosition;
    private float PlayerMinutes;
    private boolean FixtureStarted;
    private boolean FixtureFinished;
    private boolean FixtureFinishedProvisional;
    private float AllFixturesTotalMinutesPlayed;
    private float AllFixturesMinutesLeft;
    private boolean GameWeekIsFinished;
    private boolean PlayerHasNotStarted;
    private boolean PlayerIsPlaying;
    private boolean PlayerHasPlayedProvisional;
    private boolean PlayerHasPlayedFinal;
    private boolean PlayerDidNotPlay;
    private String PlayerPointsStatusText;
    private String OpponentDisplayName;
    private String OpponentShortName;
    private float FplReportedPoints;
    private float LiveBonusPoints;
    private float Difficulty;
    private float ChanceOfPlaying;
    private float SelectedByPercent;
    private float Cost;
    private float ExpectedPoints;
    private float FormCalculated;
    private float FormCalculatedTransfers;
    private String PlayerDisplayName;
    private float Points;
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

    public float getTeamPosition() {
        return TeamPosition;
    }

    public float getMultiplier() {
        return Multiplier;
    }

    public float getTotalPoints() {
        return TotalPoints;
    }

    public float getBonusPlayerPoints() {
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

    public float getId() {
        return Id;
    }

    public float getTeamId() {
        return TeamId;
    }

    public float getTeamCode() {
        return TeamCode;
    }

    public float getNumFixtures() {
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

    public float getDisplaySortOrder() {
        return DisplaySortOrder;
    }

    public String getKickOffTime() {
        return KickOffTime;
    }

    public float getPlayerPosition() {
        return PlayerPosition;
    }

    public float getPlayerMinutes() {
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

    public float getAllFixturesTotalMinutesPlayed() {
        return AllFixturesTotalMinutesPlayed;
    }

    public float getAllFixturesMinutesLeft() {
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

    public float getFplReportedPoints() {
        return FplReportedPoints;
    }

    public float getLiveBonusPoints() {
        return LiveBonusPoints;
    }

    public float getDifficulty() {
        return Difficulty;
    }

    public float getChanceOfPlaying() {
        return ChanceOfPlaying;
    }

    public float getSelectedByPercent() {
        return SelectedByPercent;
    }

    public float getCost() {
        return Cost;
    }

    public float getExpectedPoints() {
        return ExpectedPoints;
    }

    public float getFormCalculated() {
        return FormCalculated;
    }

    public float getFormCalculatedTransfers() {
        return FormCalculatedTransfers;
    }

    public String getPlayerDisplayName() {
        return PlayerDisplayName;
    }

    public float getPoints() {
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

    public void setTeamPosition(float TeamPosition) {
        this.TeamPosition = TeamPosition;
    }

    public void setMultiplier(float Multiplier) {
        this.Multiplier = Multiplier;
    }

    public void setTotalPoints(float TotalPoints) {
        this.TotalPoints = TotalPoints;
    }

    public void setBonusPlayerPoints(float BonusPlayerPoints) {
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

    public void setId(float Id) {
        this.Id = Id;
    }

    public void setTeamId(float TeamId) {
        this.TeamId = TeamId;
    }

    public void setTeamCode(float TeamCode) {
        this.TeamCode = TeamCode;
    }

    public void setNumFixtures(float NumFixtures) {
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

    public void setDisplaySortOrder(float DisplaySortOrder) {
        this.DisplaySortOrder = DisplaySortOrder;
    }

    public void setKickOffTime(String KickOffTime) {
        this.KickOffTime = KickOffTime;
    }

    public void setPlayerPosition(float PlayerPosition) {
        this.PlayerPosition = PlayerPosition;
    }

    public void setPlayerMinutes(float PlayerMinutes) {
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

    public void setAllFixturesTotalMinutesPlayed(float AllFixturesTotalMinutesPlayed) {
        this.AllFixturesTotalMinutesPlayed = AllFixturesTotalMinutesPlayed;
    }

    public void setAllFixturesMinutesLeft(float AllFixturesMinutesLeft) {
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

    public void setFplReportedPoints(float FplReportedPoints) {
        this.FplReportedPoints = FplReportedPoints;
    }

    public void setLiveBonusPoints(float LiveBonusPoints) {
        this.LiveBonusPoints = LiveBonusPoints;
    }

    public void setDifficulty(float Difficulty) {
        this.Difficulty = Difficulty;
    }

    public void setChanceOfPlaying(float ChanceOfPlaying) {
        this.ChanceOfPlaying = ChanceOfPlaying;
    }

    public void setSelectedByPercent(float SelectedByPercent) {
        this.SelectedByPercent = SelectedByPercent;
    }

    public void setCost(float Cost) {
        this.Cost = Cost;
    }

    public void setExpectedPoints(float ExpectedPoints) {
        this.ExpectedPoints = ExpectedPoints;
    }

    public void setFormCalculated(float FormCalculated) {
        this.FormCalculated = FormCalculated;
    }

    public void setFormCalculatedTransfers(float FormCalculatedTransfers) {
        this.FormCalculatedTransfers = FormCalculatedTransfers;
    }

    public void setPlayerDisplayName(String PlayerDisplayName) {
        this.PlayerDisplayName = PlayerDisplayName;
    }

    public void setPoints(float Points) {
        this.Points = Points;
    }

    public void setReturnPlayerStatsData(boolean ReturnPlayerStatsData) {
        this.ReturnPlayerStatsData = ReturnPlayerStatsData;
    }
}