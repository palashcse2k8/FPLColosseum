package com.infotech.fplcolosseum.gameweek.models;

import androidx.room.Entity;

import java.util.ArrayList;

@Entity(tableName = "GAMEWEEKDATA")
public class LeagueGameWeekDataModel {
    private float Gameweek;
    private float CurrentGameweek;
    private String LeagueId;
    private float LeagueType;
    private float EntryId;
    private float FeatureLevel;
    private float CompareEntryId;
    private float SpecialLeagueId;
    private float OverallRankTopLeague;
    private String LeagueName;
    private boolean HaveMoreThan50Teams;
    private float StartGameweek;
    private boolean LeagueIsNotCached;
    private float TeamsToCache;
    private float PagesFetched;
    private boolean CanFetchMoreTeams;
    private boolean DoKnowAllRankings;
    private float FilterTo;
    private float LastLiveFeedEventTimeStamp;
    private String LeagueOverallLivedata = null;
    private ArrayList < TeamDataModel > TeamDatas;
    private String LeagueCountryInfos = null;
    ArrayList < Object > FixtureDatas = new ArrayList < Object > ();
    ArrayList < Object > FavoriteEntryIds = new ArrayList < Object > ();
    ArrayList< Object > AllPlayerDataStats = new ArrayList < Object > ();
    private String AllPlayerDataStatsStatsViewExtra = null;
    private String LeagueAvgStatsEntry = null;
    private String LeagueAvgStatsExtra = null;
    private float CurrentImpact;
    private float CurrentPlayersProgressVsLeague;
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
    private float FailedType;
    private String ServerStatus;
    private String OverallStatsStatus = null;
    private String ErrorMessage = null;
    private boolean Succeeded;


    // Getter Methods

    public float getGameweek() {
        return Gameweek;
    }

    public float getCurrentGameweek() {
        return CurrentGameweek;
    }

    public String getLeagueId() {
        return LeagueId;
    }

    public float getLeagueType() {
        return LeagueType;
    }

    public float getEntryId() {
        return EntryId;
    }

    public float getFeatureLevel() {
        return FeatureLevel;
    }

    public float getCompareEntryId() {
        return CompareEntryId;
    }

    public float getSpecialLeagueId() {
        return SpecialLeagueId;
    }

    public float getOverallRankTopLeague() {
        return OverallRankTopLeague;
    }

    public String getLeagueName() {
        return LeagueName;
    }

    public boolean getHaveMoreThan50Teams() {
        return HaveMoreThan50Teams;
    }

    public float getStartGameweek() {
        return StartGameweek;
    }

    public boolean getLeagueIsNotCached() {
        return LeagueIsNotCached;
    }

    public float getTeamsToCache() {
        return TeamsToCache;
    }

    public float getPagesFetched() {
        return PagesFetched;
    }

    public boolean getCanFetchMoreTeams() {
        return CanFetchMoreTeams;
    }

    public boolean getDoKnowAllRankings() {
        return DoKnowAllRankings;
    }

    public float getFilterTo() {
        return FilterTo;
    }

    public float getLastLiveFeedEventTimeStamp() {
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

    public float getCurrentImpact() {
        return CurrentImpact;
    }

    public float getCurrentPlayersProgressVsLeague() {
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

    public float getFailedType() {
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

    public ArrayList<TeamDataModel> getTeamDatas() {
        return TeamDatas;
    }
    // Setter Methods

    public void setGameweek(float Gameweek) {
        this.Gameweek = Gameweek;
    }

    public void setCurrentGameweek(float CurrentGameweek) {
        this.CurrentGameweek = CurrentGameweek;
    }

    public void setLeagueId(String LeagueId) {
        this.LeagueId = LeagueId;
    }

    public void setLeagueType(float LeagueType) {
        this.LeagueType = LeagueType;
    }

    public void setEntryId(float EntryId) {
        this.EntryId = EntryId;
    }

    public void setFeatureLevel(float FeatureLevel) {
        this.FeatureLevel = FeatureLevel;
    }

    public void setCompareEntryId(float CompareEntryId) {
        this.CompareEntryId = CompareEntryId;
    }

    public void setSpecialLeagueId(float SpecialLeagueId) {
        this.SpecialLeagueId = SpecialLeagueId;
    }

    public void setOverallRankTopLeague(float OverallRankTopLeague) {
        this.OverallRankTopLeague = OverallRankTopLeague;
    }

    public void setLeagueName(String LeagueName) {
        this.LeagueName = LeagueName;
    }

    public void setHaveMoreThan50Teams(boolean HaveMoreThan50Teams) {
        this.HaveMoreThan50Teams = HaveMoreThan50Teams;
    }

    public void setStartGameweek(float StartGameweek) {
        this.StartGameweek = StartGameweek;
    }

    public void setLeagueIsNotCached(boolean LeagueIsNotCached) {
        this.LeagueIsNotCached = LeagueIsNotCached;
    }

    public void setTeamsToCache(float TeamsToCache) {
        this.TeamsToCache = TeamsToCache;
    }

    public void setPagesFetched(float PagesFetched) {
        this.PagesFetched = PagesFetched;
    }

    public void setCanFetchMoreTeams(boolean CanFetchMoreTeams) {
        this.CanFetchMoreTeams = CanFetchMoreTeams;
    }

    public void setDoKnowAllRankings(boolean DoKnowAllRankings) {
        this.DoKnowAllRankings = DoKnowAllRankings;
    }

    public void setFilterTo(float FilterTo) {
        this.FilterTo = FilterTo;
    }

    public void setLastLiveFeedEventTimeStamp(float LastLiveFeedEventTimeStamp) {
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

    public void setCurrentImpact(float CurrentImpact) {
        this.CurrentImpact = CurrentImpact;
    }

    public void setCurrentPlayersProgressVsLeague(float CurrentPlayersProgressVsLeague) {
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

    public void setFailedType(float FailedType) {
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

    public void setTeamDatas(ArrayList<TeamDataModel> teamDatas) {
        this.TeamDatas = teamDatas;
    }
}