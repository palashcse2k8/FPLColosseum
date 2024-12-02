package com.infotech.fplcolosseum.features.dream_team.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DreamTeamResponseModel {
    @SerializedName("top_player")
    TopPlayer topPlayer;
    ArrayList< DreamTeamPlayerModel > team = new ArrayList < > ();

    // Setter Getter Methods
    public ArrayList<DreamTeamPlayerModel> getTeam() {
        return team;
    }

    public void setTeam(ArrayList<DreamTeamPlayerModel> team) {
        this.team = team;
    }

    public TopPlayer getTopPlayer() {
        return topPlayer;
    }

    public void setTopPlayer(TopPlayer topPlayer) {
        this.topPlayer = topPlayer;
    }
}
