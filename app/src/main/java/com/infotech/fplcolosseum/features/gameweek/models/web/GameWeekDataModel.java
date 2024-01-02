package com.infotech.fplcolosseum.features.gameweek.models.web;

import java.util.ArrayList;

public class GameWeekDataModel {
    private long Gameweek;
    private long CurrentGameweek;
    private String LeagueId;
    private long LeagueType;
    private long EntryId;
    private long FeatureLevel;
    private long CompareEntryId;
    private long SpecialLeagueId;
    private long OverallRankTopLeague;
    private String LeagueName;
    private boolean HaveMoreThan50Teams;
    private long StartGameweek;
    private boolean LeagueIsNotCached;
    private long TeamsToCache;
    private long PagesFetched;
    private boolean CanFetchMoreTeams;
    private boolean DoKnowAllRankings;
    private long FilterTo;
    private long LastLiveFeedEventTimeStamp;
    private String LeagueOverallLivedata = null;
    ArrayList< Object > TeamDatas = new ArrayList < Object > ();
    private String LeagueCountryInfos = null;
    ArrayList < Object > FixtureDatas = new ArrayList < Object > ();
    ArrayList < Object > FavoriteEntryIds = new ArrayList < Object > ();
    ArrayList < Object > AllPlayerDataStats = new ArrayList < Object > ();
    private String AllPlayerDataStatsStatsViewExtra = null;
    private String LeagueAvgStatsEntry = null;
    private String LeagueAvgStatsExtra = null;
    private long CurrentImpact;
    private long CurrentPlayersProgressVsLeague;
    private String SeasonHistoryGameweeks = null;
    ArrayList < Object > GameWeeks = new ArrayList < Object > ();
    private String LogText;
    private String InfoTextAboveLeague;
    private boolean HideAds;
    private boolean IsOverallRankLeague;
    private boolean IsCompareLeague;
    private boolean IsSpecialLeague;
    private boolean IsFavoriteLeague;
    private boolean IsFutureGameweek;
    private boolean IsCurrentGameweek;
    private boolean IsNextGameweek;
    private long FailedType;
    private String ServerStatus;
    private String OverallStatsStatus = null;
    private String ErrorMessage = null;
    private boolean Succeeded;


    // Getter Methods

    public long getGameweek() {
        return Gameweek;
    }

    public long getCurrentGameweek() {
        return CurrentGameweek;
    }

    public String getLeagueId() {
        return LeagueId;
    }

    public long getLeagueType() {
        return LeagueType;
    }

    public long getEntryId() {
        return EntryId;
    }

    public long getFeatureLevel() {
        return FeatureLevel;
    }

    public long getCompareEntryId() {
        return CompareEntryId;
    }

    public long getSpecialLeagueId() {
        return SpecialLeagueId;
    }

    public long getOverallRankTopLeague() {
        return OverallRankTopLeague;
    }

    public String getLeagueName() {
        return LeagueName;
    }

    public boolean getHaveMoreThan50Teams() {
        return HaveMoreThan50Teams;
    }

    public long getStartGameweek() {
        return StartGameweek;
    }

    public boolean getLeagueIsNotCached() {
        return LeagueIsNotCached;
    }

    public long getTeamsToCache() {
        return TeamsToCache;
    }

    public long getPagesFetched() {
        return PagesFetched;
    }

    public boolean getCanFetchMoreTeams() {
        return CanFetchMoreTeams;
    }

    public boolean getDoKnowAllRankings() {
        return DoKnowAllRankings;
    }

    public long getFilterTo() {
        return FilterTo;
    }

    public long getLastLiveFeedEventTimeStamp() {
        return LastLiveFeedEventTimeStamp;
    }

    public String getLeagueOverallLivedata() {
        return LeagueOverallLivedata;
    }

    public String getLeagueCountryInfos() {
        return LeagueCountryInfos;
    }

    public String getAllPlayerDataStatsStatsViewExtra() {
        return AllPlayerDataStatsStatsViewExtra;
    }

    public String getLeagueAvgStatsEntry() {
        return LeagueAvgStatsEntry;
    }

    public String getLeagueAvgStatsExtra() {
        return LeagueAvgStatsExtra;
    }

    public long getCurrentImpact() {
        return CurrentImpact;
    }

    public long getCurrentPlayersProgressVsLeague() {
        return CurrentPlayersProgressVsLeague;
    }

    public String getSeasonHistoryGameweeks() {
        return SeasonHistoryGameweeks;
    }

    public String getLogText() {
        return LogText;
    }

    public String getInfoTextAboveLeague() {
        return InfoTextAboveLeague;
    }

    public boolean getHideAds() {
        return HideAds;
    }

    public boolean getIsOverallRankLeague() {
        return IsOverallRankLeague;
    }

    public boolean getIsCompareLeague() {
        return IsCompareLeague;
    }

    public boolean getIsSpecialLeague() {
        return IsSpecialLeague;
    }

    public boolean getIsFavoriteLeague() {
        return IsFavoriteLeague;
    }

    public boolean getIsFutureGameweek() {
        return IsFutureGameweek;
    }

    public boolean getIsCurrentGameweek() {
        return IsCurrentGameweek;
    }

    public boolean getIsNextGameweek() {
        return IsNextGameweek;
    }

    public long getFailedType() {
        return FailedType;
    }

    public String getServerStatus() {
        return ServerStatus;
    }

    public String getOverallStatsStatus() {
        return OverallStatsStatus;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public boolean getSucceeded() {
        return Succeeded;
    }

    // Setter Methods

    public void setGameweek(long Gameweek) {
        this.Gameweek = Gameweek;
    }

    public void setCurrentGameweek(long CurrentGameweek) {
        this.CurrentGameweek = CurrentGameweek;
    }

    public void setLeagueId(String LeagueId) {
        this.LeagueId = LeagueId;
    }

    public void setLeagueType(long LeagueType) {
        this.LeagueType = LeagueType;
    }

    public void setEntryId(long EntryId) {
        this.EntryId = EntryId;
    }

    public void setFeatureLevel(long FeatureLevel) {
        this.FeatureLevel = FeatureLevel;
    }

    public void setCompareEntryId(long CompareEntryId) {
        this.CompareEntryId = CompareEntryId;
    }

    public void setSpecialLeagueId(long SpecialLeagueId) {
        this.SpecialLeagueId = SpecialLeagueId;
    }

    public void setOverallRankTopLeague(long OverallRankTopLeague) {
        this.OverallRankTopLeague = OverallRankTopLeague;
    }

    public void setLeagueName(String LeagueName) {
        this.LeagueName = LeagueName;
    }

    public void setHaveMoreThan50Teams(boolean HaveMoreThan50Teams) {
        this.HaveMoreThan50Teams = HaveMoreThan50Teams;
    }

    public void setStartGameweek(long StartGameweek) {
        this.StartGameweek = StartGameweek;
    }

    public void setLeagueIsNotCached(boolean LeagueIsNotCached) {
        this.LeagueIsNotCached = LeagueIsNotCached;
    }

    public void setTeamsToCache(long TeamsToCache) {
        this.TeamsToCache = TeamsToCache;
    }

    public void setPagesFetched(long PagesFetched) {
        this.PagesFetched = PagesFetched;
    }

    public void setCanFetchMoreTeams(boolean CanFetchMoreTeams) {
        this.CanFetchMoreTeams = CanFetchMoreTeams;
    }

    public void setDoKnowAllRankings(boolean DoKnowAllRankings) {
        this.DoKnowAllRankings = DoKnowAllRankings;
    }

    public void setFilterTo(long FilterTo) {
        this.FilterTo = FilterTo;
    }

    public void setLastLiveFeedEventTimeStamp(long LastLiveFeedEventTimeStamp) {
        this.LastLiveFeedEventTimeStamp = LastLiveFeedEventTimeStamp;
    }

    public void setLeagueOverallLivedata(String LeagueOverallLivedata) {
        this.LeagueOverallLivedata = LeagueOverallLivedata;
    }

    public void setLeagueCountryInfos(String LeagueCountryInfos) {
        this.LeagueCountryInfos = LeagueCountryInfos;
    }

    public void setAllPlayerDataStatsStatsViewExtra(String AllPlayerDataStatsStatsViewExtra) {
        this.AllPlayerDataStatsStatsViewExtra = AllPlayerDataStatsStatsViewExtra;
    }

    public void setLeagueAvgStatsEntry(String LeagueAvgStatsEntry) {
        this.LeagueAvgStatsEntry = LeagueAvgStatsEntry;
    }

    public void setLeagueAvgStatsExtra(String LeagueAvgStatsExtra) {
        this.LeagueAvgStatsExtra = LeagueAvgStatsExtra;
    }

    public void setCurrentImpact(long CurrentImpact) {
        this.CurrentImpact = CurrentImpact;
    }

    public void setCurrentPlayersProgressVsLeague(long CurrentPlayersProgressVsLeague) {
        this.CurrentPlayersProgressVsLeague = CurrentPlayersProgressVsLeague;
    }

    public void setSeasonHistoryGameweeks(String SeasonHistoryGameweeks) {
        this.SeasonHistoryGameweeks = SeasonHistoryGameweeks;
    }

    public void setLogText(String LogText) {
        this.LogText = LogText;
    }

    public void setInfoTextAboveLeague(String InfoTextAboveLeague) {
        this.InfoTextAboveLeague = InfoTextAboveLeague;
    }

    public void setHideAds(boolean HideAds) {
        this.HideAds = HideAds;
    }

    public void setIsOverallRankLeague(boolean IsOverallRankLeague) {
        this.IsOverallRankLeague = IsOverallRankLeague;
    }

    public void setIsCompareLeague(boolean IsCompareLeague) {
        this.IsCompareLeague = IsCompareLeague;
    }

    public void setIsSpecialLeague(boolean IsSpecialLeague) {
        this.IsSpecialLeague = IsSpecialLeague;
    }

    public void setIsFavoriteLeague(boolean IsFavoriteLeague) {
        this.IsFavoriteLeague = IsFavoriteLeague;
    }

    public void setIsFutureGameweek(boolean IsFutureGameweek) {
        this.IsFutureGameweek = IsFutureGameweek;
    }

    public void setIsCurrentGameweek(boolean IsCurrentGameweek) {
        this.IsCurrentGameweek = IsCurrentGameweek;
    }

    public void setIsNextGameweek(boolean IsNextGameweek) {
        this.IsNextGameweek = IsNextGameweek;
    }

    public void setFailedType(long FailedType) {
        this.FailedType = FailedType;
    }

    public void setServerStatus(String ServerStatus) {
        this.ServerStatus = ServerStatus;
    }

    public void setOverallStatsStatus(String OverallStatsStatus) {
        this.OverallStatsStatus = OverallStatsStatus;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public void setSucceeded(boolean Succeeded) {
        this.Succeeded = Succeeded;
    }
}
