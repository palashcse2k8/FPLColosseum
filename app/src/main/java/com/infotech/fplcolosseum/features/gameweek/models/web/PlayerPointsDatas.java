package com.infotech.fplcolosseum.features.gameweek.models.web;

public class PlayerPointsDatas {
    private long FixtureId;
    private String Key;
    private long Amount;
    private long Points;
    private boolean IsTemporaryBonusPoints;
    private long TemporaryBonusPoints;
    private String PointsText;
    private boolean ShowAmount;

    // Getter Methods

    public long getFixtureId() {
        return FixtureId;
    }

    public String getKey() {
        return Key;
    }

    public long getAmount() {
        return Amount;
    }

    public long getPoints() {
        return Points;
    }

    public boolean getIsTemporaryBonusPoints() {
        return IsTemporaryBonusPoints;
    }

    public long getTemporaryBonusPoints() {
        return TemporaryBonusPoints;
    }

    public String getPointsText() {
        return PointsText;
    }

    public boolean getShowAmount() {
        return ShowAmount;
    }

    // Setter Methods

    public void setFixtureId(long FixtureId) {
        this.FixtureId = FixtureId;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public void setAmount(long Amount) {
        this.Amount = Amount;
    }

    public void setPoints(long Points) {
        this.Points = Points;
    }

    public void setIsTemporaryBonusPoints(boolean IsTemporaryBonusPoints) {
        this.IsTemporaryBonusPoints = IsTemporaryBonusPoints;
    }

    public void setTemporaryBonusPoints(long TemporaryBonusPoints) {
        this.TemporaryBonusPoints = TemporaryBonusPoints;
    }

    public void setPointsText(String PointsText) {
        this.PointsText = PointsText;
    }

    public void setShowAmount(boolean ShowAmount) {
        this.ShowAmount = ShowAmount;
    }
}
