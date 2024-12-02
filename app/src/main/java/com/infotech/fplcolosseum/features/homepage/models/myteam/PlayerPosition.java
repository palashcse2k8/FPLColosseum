package com.infotech.fplcolosseum.features.homepage.models.myteam;

import com.infotech.fplcolosseum.features.homepage.models.staticdata.PlayersData;

public class PlayerPosition {
    PlayersData player;
    int row;
    int column;

    public PlayerPosition(PlayersData player, int row, int column) {
        this.player = player;
        this.row = row;
        this.column = column;
    }

    // Getters and setters can be added here
    public PlayersData getPlayer() {
        return player;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
