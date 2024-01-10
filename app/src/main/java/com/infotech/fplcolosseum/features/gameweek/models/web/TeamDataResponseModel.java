package com.infotech.fplcolosseum.features.gameweek.models.web;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.infotech.fplcolosseum.data.sources.database.dataconverter.ManagerModelConverter;

import java.util.List;

@Entity(tableName = "team_data")
@TypeConverters(ManagerModelConverter.class)
public class TeamDataResponseModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("LiveData")
    GameWeekLiveData GameWeekLiveData;
    private long EntryId;
    private String Name;
    private String PlayerName;
    private String OfficialName = null;
    private String Description = null;
    private boolean NotInLeague;
    private String OverAllRankInfo = null;
    private String TeamH2HData = null;
    List< Object > UsedChips ;
    private float TeamValue;
    private double BankValue;
    private boolean IsCupOpponent;
    private String CupLeague = null;
    private boolean IsExcludedFromStats;
    private long CupOpponentGameweek;
    private long RealTotalPosition;
    private long TotalTransfersSeason;
    private long DisplayRank;
    private long DisplayRankTotal;
    private long AvgRankLast3;
    private long SeasonsPlayed;
    private long BestRank;
    private long FavouriteTeam;
    private String CountryIso;
    private long LastGameweekLeagueRank;


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

    public long getEntryId() {
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

    public double getBankValue() {
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

    public long getCupOpponentGameweek() {
        return CupOpponentGameweek;
    }

    public long getRealTotalPosition() {
        return RealTotalPosition;
    }

    public long getTotalTransfersSeason() {
        return TotalTransfersSeason;
    }

    public long getDisplayRank() {
        return DisplayRank;
    }

    public long getDisplayRankTotal() {
        return DisplayRankTotal;
    }

    public long getAvgRankLast3() {
        return AvgRankLast3;
    }

    public long getSeasonsPlayed() {
        return SeasonsPlayed;
    }

    public long getBestRank() {
        return BestRank;
    }

    public long getFavouriteTeam() {
        return FavouriteTeam;
    }

    public String getCountryIso() {
        return CountryIso;
    }

    public long getLastGameweekLeagueRank() {
        return LastGameweekLeagueRank;
    }

    // Setter Methods

    public void setLiveData(GameWeekLiveData gameWeekLiveDataObject) {
        this.GameWeekLiveData = gameWeekLiveDataObject;
    }

    public void setEntryId(long EntryId) {
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

    public void setBankValue(double BankValue) {
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

    public void setCupOpponentGameweek(long CupOpponentGameweek) {
        this.CupOpponentGameweek = CupOpponentGameweek;
    }

    public void setRealTotalPosition(long RealTotalPosition) {
        this.RealTotalPosition = RealTotalPosition;
    }

    public void setTotalTransfersSeason(long TotalTransfersSeason) {
        this.TotalTransfersSeason = TotalTransfersSeason;
    }

    public void setDisplayRank(long DisplayRank) {
        this.DisplayRank = DisplayRank;
    }

    public void setDisplayRankTotal(long DisplayRankTotal) {
        this.DisplayRankTotal = DisplayRankTotal;
    }

    public void setAvgRankLast3(long AvgRankLast3) {
        this.AvgRankLast3 = AvgRankLast3;
    }

    public void setSeasonsPlayed(long SeasonsPlayed) {
        this.SeasonsPlayed = SeasonsPlayed;
    }

    public void setBestRank(long BestRank) {
        this.BestRank = BestRank;
    }

    public void setFavouriteTeam(long FavouriteTeam) {
        this.FavouriteTeam = FavouriteTeam;
    }

    public void setCountryIso(String CountryIso) {
        this.CountryIso = CountryIso;
    }

    public void setLastGameweekLeagueRank(long LastGameweekLeagueRank) {
        this.LastGameweekLeagueRank = LastGameweekLeagueRank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
