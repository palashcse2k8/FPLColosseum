package com.infotech.fplcolosseum.features.gameweek.models.web;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlayerStatsResponseModel {
    private String PlayerDisplayName;
    private String PlayerWebName;
    private String TeamFullName;
    private String TeamName;
    private String News;
    private String PlayerPositionName;
    private String ManualSubData = null;
    private String ManualSubNotes;
    @SerializedName("PlayerPointsDatas")
    ArrayList<PlayerPointsDatas > PlayerPointsDatas;

    public ArrayList<PlayerPointsDatas> getPlayerPointsDatas() {
        return PlayerPointsDatas;
    }

    public void setPlayerPointsDatas(ArrayList<PlayerPointsDatas> playerPointsDatas) {
        PlayerPointsDatas = playerPointsDatas;
    }

    ArrayList < Object > PreviousFixtures = new ArrayList < Object > ();
    ArrayList < Object > UpcommingFixtures = new ArrayList < Object > ();
    ArrayList < Object > Fixtures = new ArrayList < Object > ();
    private long Points;
    private long Cost;
    private long SelectedByPercent;


    // Getter Methods

    public String getPlayerDisplayName() {
        return PlayerDisplayName;
    }

    public String getPlayerWebName() {
        return PlayerWebName;
    }

    public String getTeamFullName() {
        return TeamFullName;
    }

    public String getTeamName() {
        return TeamName;
    }

    public String getNews() {
        return News;
    }

    public String getPlayerPositionName() {
        return PlayerPositionName;
    }

    public String getManualSubData() {
        return ManualSubData;
    }

    public String getManualSubNotes() {
        return ManualSubNotes;
    }

    public long getPoints() {
        return Points;
    }

    public long getCost() {
        return Cost;
    }

    public long getSelectedByPercent() {
        return SelectedByPercent;
    }

    // Setter Methods

    public void setPlayerDisplayName(String PlayerDisplayName) {
        this.PlayerDisplayName = PlayerDisplayName;
    }

    public void setPlayerWebName(String PlayerWebName) {
        this.PlayerWebName = PlayerWebName;
    }

    public void setTeamFullName(String TeamFullName) {
        this.TeamFullName = TeamFullName;
    }

    public void setTeamName(String TeamName) {
        this.TeamName = TeamName;
    }

    public void setNews(String News) {
        this.News = News;
    }

    public void setPlayerPositionName(String PlayerPositionName) {
        this.PlayerPositionName = PlayerPositionName;
    }

    public void setManualSubData(String ManualSubData) {
        this.ManualSubData = ManualSubData;
    }

    public void setManualSubNotes(String ManualSubNotes) {
        this.ManualSubNotes = ManualSubNotes;
    }

    public void setPoints(long Points) {
        this.Points = Points;
    }

    public void setCost(long Cost) {
        this.Cost = Cost;
    }

    public void setSelectedByPercent(long SelectedByPercent) {
        this.SelectedByPercent = SelectedByPercent;
    }
}
