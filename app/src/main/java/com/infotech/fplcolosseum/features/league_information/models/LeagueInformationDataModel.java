package com.infotech.fplcolosseum.features.league_information.models;

import com.google.gson.annotations.SerializedName;

public class LeagueInformationDataModel {
    @SerializedName("new_entries")
    New_entries New_entriesObject;

    private String last_updated_data;
    @SerializedName("league")
    League LeagueObject;

    @SerializedName("standings")
    Standings StandingsObject;


    // Getter Methods

    public New_entries getNew_entries() {
        return New_entriesObject;
    }

    public String getLast_updated_data() {
        return last_updated_data;
    }

    public League getLeague() {
        return LeagueObject;
    }

    public Standings getStandings() {
        return StandingsObject;
    }

    // Setter Methods

    public void setNew_entries(New_entries new_entriesObject) {
        this.New_entriesObject = new_entriesObject;
    }

    public void setLast_updated_data(String last_updated_data) {
        this.last_updated_data = last_updated_data;
    }

    public void setLeague(League leagueObject) {
        this.LeagueObject = leagueObject;
    }

    public void setStandings(Standings standingsObject) {
        this.StandingsObject = standingsObject;
    }
}

