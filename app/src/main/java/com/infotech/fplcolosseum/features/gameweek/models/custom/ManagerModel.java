package com.infotech.fplcolosseum.features.gameweek.models.custom;

import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

public class ManagerModel {
    private long Id;
    private String ManagerName;
    private String TeamName;
    private long GameWeek;
    List<PlayerDataModel> PlayersXI = new ArrayList<>();
    @TypeConverters(PlayerDataModel.class)
    List<PlayerDataModel> PlayersAll = new ArrayList<>();
    private String CaptainName;
    private long CaptainGameWeekPoints;
    private String ViceCaptainName;
    private long ViceCaptainGameWeekPoints;
    private long GameWeekPoints;
    private long GameWeekPointsWithoutTransferCost;
    private long GameWeekBonusPointsXI;
    private long GameWeekBenchPoints;
    private long GameWeekBPSPointsXI;
    private long SeasonTotalPoints;
    private long GoalConceded;
    private long GoalScored;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
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

    public long getGameWeek() {
        return GameWeek;
    }

    public void setGameWeek(long gameWeek) {
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

    public long getCaptainGameWeekPoints() {
        return CaptainGameWeekPoints;
    }

    public void setCaptainGameWeekPoints(long captainGameWeekPoints) {
        CaptainGameWeekPoints = captainGameWeekPoints;
    }

    public String getViceCaptainName() {
        return ViceCaptainName;
    }

    public void setViceCaptainName(String viceCaptainName) {
        ViceCaptainName = viceCaptainName;
    }

    public long getViceCaptainGameWeekPoints() {
        return ViceCaptainGameWeekPoints;
    }

    public void setViceCaptainGameWeekPoints(long viceCaptainGameWeekPoints) {
        ViceCaptainGameWeekPoints = viceCaptainGameWeekPoints;
    }

    public long getGameWeekPoints() {
        return GameWeekPoints;
    }

    public void setGameWeekPoints(long gameWeekPoints) {
        GameWeekPoints = gameWeekPoints;
    }

    public long getGameWeekPointsWithoutTransferCost() {
        return GameWeekPointsWithoutTransferCost;
    }

    public void setGameWeekPointsWithoutTransferCost(long gameWeekPointsWithoutTransferCost) {
        GameWeekPointsWithoutTransferCost = gameWeekPointsWithoutTransferCost;
    }

    public long getGameWeekBonusPointsXI() {
        return GameWeekBonusPointsXI;
    }

    public void setGameWeekBonusPointsXI(long gameWeekBonusPointsXI) {
        GameWeekBonusPointsXI = gameWeekBonusPointsXI;
    }

    public long getGameWeekBenchPoints() {
        return GameWeekBenchPoints;
    }

    public void setGameWeekBenchPoints(long gameWeekBenchPoints) {
        GameWeekBenchPoints = gameWeekBenchPoints;
    }

    public long getGameWeekBPSPointsXI() {
        return GameWeekBPSPointsXI;
    }

    public void setGameWeekBPSPointsXI(long gameWeekBPSPointsXI) {
        GameWeekBPSPointsXI = gameWeekBPSPointsXI;
    }

    public long getSeasonTotalPoints() {
        return SeasonTotalPoints;
    }

    public void setSeasonTotalPoints(long seasonTotalPoints) {
        SeasonTotalPoints = seasonTotalPoints;
    }

    public long getGoalConceded() {
        return GoalConceded;
    }

    public void setGoalConceded(long goalConceded) {
        GoalConceded = goalConceded;
    }

    public long getGoalScored() {
        return GoalScored;
    }

    public void setGoalScored(long goalScored) {
        GoalScored = goalScored;
    }
}
