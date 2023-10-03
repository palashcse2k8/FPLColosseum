package com.infotech.fplcolosseum.gameweek.models.web;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.infotech.fplcolosseum.database.dataconverter.ManagerModelConverter;

import java.util.List;

@Entity(tableName = "team_data")
@TypeConverters(ManagerModelConverter.class)
public class TeamDataResponseModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("LiveData")
    GameWeekLiveData GameWeekLiveData;
    private float EntryId;
    private String Name;
    private String PlayerName;
    private String OfficialName = null;
    private String Description = null;
    private boolean NotInLeague;
    private String OverAllRankInfo = null;
    private String TeamH2HData = null;
    List< Object > UsedChips ;
    private float TeamValue;
    private float BankValue;
    private boolean IsCupOpponent;
    private String CupLeague = null;
    private boolean IsExcludedFromStats;
    private float CupOpponentGameweek;
    private float RealTotalPosition;
    private float TotalTransfersSeason;
    private float DisplayRank;
    private float DisplayRankTotal;
    private float AvgRankLast3;
    private float SeasonsPlayed;
    private float BestRank;
    private float FavouriteTeam;
    private String CountryIso;
    private float LastGameweekLeagueRank;


    // Getter Methods

    public List<Object> getUsedChips() {
        return UsedChips;
    }

    public void setUsedChips(List<Object> usedChips) {
        UsedChips = usedChips;
    }

    public GameWeekLiveData getLiveData() {
        return GameWeekLiveData;
    }

    public float getEntryId() {
        return EntryId;
    }

    public String getName() {
        return Name;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public String getOfficialName() {
        return OfficialName;
    }

    public String getDescription() {
        return Description;
    }

    public boolean getNotInLeague() {
        return NotInLeague;
    }

    public String getOverAllRankInfo() {
        return OverAllRankInfo;
    }

    public String getTeamH2HData() {
        return TeamH2HData;
    }

    public float getTeamValue() {
        return TeamValue;
    }

    public float getBankValue() {
        return BankValue;
    }

    public boolean getIsCupOpponent() {
        return IsCupOpponent;
    }

    public String getCupLeague() {
        return CupLeague;
    }

    public boolean getIsExcludedFromStats() {
        return IsExcludedFromStats;
    }

    public float getCupOpponentGameweek() {
        return CupOpponentGameweek;
    }

    public float getRealTotalPosition() {
        return RealTotalPosition;
    }

    public float getTotalTransfersSeason() {
        return TotalTransfersSeason;
    }

    public float getDisplayRank() {
        return DisplayRank;
    }

    public float getDisplayRankTotal() {
        return DisplayRankTotal;
    }

    public float getAvgRankLast3() {
        return AvgRankLast3;
    }

    public float getSeasonsPlayed() {
        return SeasonsPlayed;
    }

    public float getBestRank() {
        return BestRank;
    }

    public float getFavouriteTeam() {
        return FavouriteTeam;
    }

    public String getCountryIso() {
        return CountryIso;
    }

    public float getLastGameweekLeagueRank() {
        return LastGameweekLeagueRank;
    }

    // Setter Methods

    public void setLiveData(GameWeekLiveData gameWeekLiveDataObject) {
        this.GameWeekLiveData = gameWeekLiveDataObject;
    }

    public void setEntryId(float EntryId) {
        this.EntryId = EntryId;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPlayerName(String PlayerName) {
        this.PlayerName = PlayerName;
    }

    public void setOfficialName(String OfficialName) {
        this.OfficialName = OfficialName;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setNotInLeague(boolean NotInLeague) {
        this.NotInLeague = NotInLeague;
    }

    public void setOverAllRankInfo(String OverAllRankInfo) {
        this.OverAllRankInfo = OverAllRankInfo;
    }

    public void setTeamH2HData(String TeamH2HData) {
        this.TeamH2HData = TeamH2HData;
    }

    public void setTeamValue(float TeamValue) {
        this.TeamValue = TeamValue;
    }

    public void setBankValue(float BankValue) {
        this.BankValue = BankValue;
    }

    public void setIsCupOpponent(boolean IsCupOpponent) {
        this.IsCupOpponent = IsCupOpponent;
    }

    public void setCupLeague(String CupLeague) {
        this.CupLeague = CupLeague;
    }

    public void setIsExcludedFromStats(boolean IsExcludedFromStats) {
        this.IsExcludedFromStats = IsExcludedFromStats;
    }

    public void setCupOpponentGameweek(float CupOpponentGameweek) {
        this.CupOpponentGameweek = CupOpponentGameweek;
    }

    public void setRealTotalPosition(float RealTotalPosition) {
        this.RealTotalPosition = RealTotalPosition;
    }

    public void setTotalTransfersSeason(float TotalTransfersSeason) {
        this.TotalTransfersSeason = TotalTransfersSeason;
    }

    public void setDisplayRank(float DisplayRank) {
        this.DisplayRank = DisplayRank;
    }

    public void setDisplayRankTotal(float DisplayRankTotal) {
        this.DisplayRankTotal = DisplayRankTotal;
    }

    public void setAvgRankLast3(float AvgRankLast3) {
        this.AvgRankLast3 = AvgRankLast3;
    }

    public void setSeasonsPlayed(float SeasonsPlayed) {
        this.SeasonsPlayed = SeasonsPlayed;
    }

    public void setBestRank(float BestRank) {
        this.BestRank = BestRank;
    }

    public void setFavouriteTeam(float FavouriteTeam) {
        this.FavouriteTeam = FavouriteTeam;
    }

    public void setCountryIso(String CountryIso) {
        this.CountryIso = CountryIso;
    }

    public void setLastGameweekLeagueRank(float LastGameweekLeagueRank) {
        this.LastGameweekLeagueRank = LastGameweekLeagueRank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
