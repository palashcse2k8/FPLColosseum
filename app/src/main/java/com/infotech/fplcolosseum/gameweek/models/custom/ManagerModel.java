package com.infotech.fplcolosseum.gameweek.models.custom;

import java.util.ArrayList;
import java.util.List;

public class ManagerModel {
    private float Id;
    private String ManagerName;
    private String TeamName;
    private float GameWeek;
    List<PlayerDataModel> PlayersXI = new ArrayList<>();
    List<PlayerDataModel> PlayersAll = new ArrayList<>();
    private String CaptainName;
    private float CaptainGameWeekPoints;
    private String ViceCaptainName;
    private float ViceCaptainGameWeekPoints;
    private float GameWeekPoints;
    private float GameWeekPointsWithoutTransferCost;
    private float GameWeekBonusPointsXI;
    private float GameWeekBenchPoints;
    private float GameWeekBPSPointsXI;
    private float SeasonTotalPoints;
    private float GoalConceded;
    private float GoalScored;

    public float getId() {
        return Id;
    }

    public void setId(float id) {
        Id = id;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public float getGameWeek() {
        return GameWeek;
    }

    public void setGameWeek(float gameWeek) {
        GameWeek = gameWeek;
    }

    public List<PlayerDataModel> getPlayersXI() {
        return PlayersXI;
    }

    public void setPlayersXI(ArrayList<PlayerDataModel> playersXI) {
        PlayersXI = playersXI;
    }

    public List<PlayerDataModel> getPlayersAll() {
        return PlayersAll;
    }

    public void setPlayersAll(List<PlayerDataModel> playersAll) {
        PlayersAll = playersAll;
    }

    public String getCaptainName() {
        return CaptainName;
    }

    public void setCaptainName(String captainName) {
        CaptainName = captainName;
    }

    public float getCaptainGameWeekPoints() {
        return CaptainGameWeekPoints;
    }

    public void setCaptainGameWeekPoints(float captainGameWeekPoints) {
        CaptainGameWeekPoints = captainGameWeekPoints;
    }

    public String getViceCaptainName() {
        return ViceCaptainName;
    }

    public void setViceCaptainName(String viceCaptainName) {
        ViceCaptainName = viceCaptainName;
    }

    public float getViceCaptainGameWeekPoints() {
        return ViceCaptainGameWeekPoints;
    }

    public void setViceCaptainGameWeekPoints(float viceCaptainGameWeekPoints) {
        ViceCaptainGameWeekPoints = viceCaptainGameWeekPoints;
    }

    public float getGameWeekPoints() {
        return GameWeekPoints;
    }

    public void setGameWeekPoints(float gameWeekPoints) {
        GameWeekPoints = gameWeekPoints;
    }

    public float getGameWeekPointsWithoutTransferCost() {
        return GameWeekPointsWithoutTransferCost;
    }

    public void setGameWeekPointsWithoutTransferCost(float gameWeekPointsWithoutTransferCost) {
        GameWeekPointsWithoutTransferCost = gameWeekPointsWithoutTransferCost;
    }

    public float getGameWeekBonusPointsXI() {
        return GameWeekBonusPointsXI;
    }

    public void setGameWeekBonusPointsXI(float gameWeekBonusPointsXI) {
        GameWeekBonusPointsXI = gameWeekBonusPointsXI;
    }

    public float getGameWeekBenchPoints() {
        return GameWeekBenchPoints;
    }

    public void setGameWeekBenchPoints(float gameWeekBenchPoints) {
        GameWeekBenchPoints = gameWeekBenchPoints;
    }

    public float getGameWeekBPSPointsXI() {
        return GameWeekBPSPointsXI;
    }

    public void setGameWeekBPSPointsXI(float gameWeekBPSPointsXI) {
        GameWeekBPSPointsXI = gameWeekBPSPointsXI;
    }

    public float getSeasonTotalPoints() {
        return SeasonTotalPoints;
    }

    public void setSeasonTotalPoints(float seasonTotalPoints) {
        SeasonTotalPoints = seasonTotalPoints;
    }

    public float getGoalConceded() {
        return GoalConceded;
    }

    public void setGoalConceded(float goalConceded) {
        GoalConceded = goalConceded;
    }

    public float getGoalScored() {
        return GoalScored;
    }

    public void setGoalScored(float goalScored) {
        GoalScored = goalScored;
    }
}
