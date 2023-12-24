package com.infotech.fplcolosseum.features.homepage.models;

public class PlayerModel {

    String playerName;
    String teamName;
    int position;
    String imageUrl;
    float points;

    public PlayerModel(String playerName, String teamName, int position, String imageUrl, float points) {
        this.playerName = playerName;
        this.teamName = teamName;
        this.position = position;
        this.imageUrl = imageUrl;
        this.points = points;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }
}
