package com.infotech.fplcolosseum.features.homepage.models.entryinformation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Leagues {
    @SerializedName("classic")
    ArrayList < LeagueDataModel > classic = new ArrayList < > ();
    @SerializedName("h2h")
    ArrayList< LeagueDataModel > h2h = new ArrayList < > ();
    Cup CupObject;
    ArrayList < Object > cup_matches = new ArrayList < Object > ();


    // Getter Methods

    public Cup getCup() {
        return CupObject;
    }

    // Setter Methods

    public void setCup(Cup cupObject) {
        this.CupObject = cupObject;
    }

    public ArrayList<LeagueDataModel> getClassic() {
        return classic;
    }

    public void setClassic(ArrayList<LeagueDataModel> classic) {
        this.classic = classic;
    }

    public ArrayList<LeagueDataModel> getH2h() {
        return h2h;
    }

    public void setH2h(ArrayList<LeagueDataModel> h2h) {
        this.h2h = h2h;
    }

    public Cup getCupObject() {
        return CupObject;
    }

    public void setCupObject(Cup cupObject) {
        CupObject = cupObject;
    }

    public ArrayList<Object> getCup_matches() {
        return cup_matches;
    }

    public void setCup_matches(ArrayList<Object> cup_matches) {
        this.cup_matches = cup_matches;
    }
}
