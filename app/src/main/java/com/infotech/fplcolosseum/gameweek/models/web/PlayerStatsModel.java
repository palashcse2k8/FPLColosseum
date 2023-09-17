package com.infotech.fplcolosseum.gameweek.models.web;

import java.util.ArrayList;

public class PlayerStatsModel {
    private String PlayerDisplayName;
    private String PlayerWebName;
    private String TeamFullName;
    private String TeamName;
    private String News;
    private String PlayerPositionName;
    private String ManualSubData = null;
    private String ManualSubNotes;
    ArrayList< PlayerPointsDatas > PlayerPointsDatas;
    ArrayList < Object > PreviousFixtures = new ArrayList < Object > ();
    ArrayList < Object > UpcommingFixtures = new ArrayList < Object > ();
    ArrayList < Object > Fixtures = new ArrayList < Object > ();
    private float Points;
    private float Cost;
    private float SelectedByPercent;


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

    public float getPoints() {
        return Points;
    }

    public float getCost() {
        return Cost;
    }

    public float getSelectedByPercent() {
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

    public void setPoints(float Points) {
        this.Points = Points;
    }

    public void setCost(float Cost) {
        this.Cost = Cost;
    }

    public void setSelectedByPercent(float SelectedByPercent) {
        this.SelectedByPercent = SelectedByPercent;
    }
}

class PlayerPointsDatas {
    private float FixtureId;
    private String Key;
    private float Amount;
    private float Points;
    private boolean IsTemporaryBonusPoints;
    private float TemporaryBonusPoints;
    private String PointsText;
    private boolean ShowAmount;


    // Getter Methods

    public float getFixtureId() {
        return FixtureId;
    }

    public String getKey() {
        return Key;
    }

    public float getAmount() {
        return Amount;
    }

    public float getPoints() {
        return Points;
    }

    public boolean getIsTemporaryBonusPoints() {
        return IsTemporaryBonusPoints;
    }

    public float getTemporaryBonusPoints() {
        return TemporaryBonusPoints;
    }

    public String getPointsText() {
        return PointsText;
    }

    public boolean getShowAmount() {
        return ShowAmount;
    }

    // Setter Methods

    public void setFixtureId(float FixtureId) {
        this.FixtureId = FixtureId;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public void setAmount(float Amount) {
        this.Amount = Amount;
    }

    public void setPoints(float Points) {
        this.Points = Points;
    }

    public void setIsTemporaryBonusPoints(boolean IsTemporaryBonusPoints) {
        this.IsTemporaryBonusPoints = IsTemporaryBonusPoints;
    }

    public void setTemporaryBonusPoints(float TemporaryBonusPoints) {
        this.TemporaryBonusPoints = TemporaryBonusPoints;
    }

    public void setPointsText(String PointsText) {
        this.PointsText = PointsText;
    }

    public void setShowAmount(boolean ShowAmount) {
        this.ShowAmount = ShowAmount;
    }
}