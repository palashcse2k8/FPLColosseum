package com.infotech.fplcolosseum.features.gameweek.models.web;

public class PlayerPointsDatas {
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
