package com.infotech.fplcolosseum.features.league_information.models;

import java.util.ArrayList;

public class New_entries {
    private boolean has_next;
    private float page;
    ArrayList < LeagueNewEntryResultDataModel > results = new ArrayList < > ();


    // Getter Methods

    public boolean getHas_next() {
        return has_next;
    }

    public float getPage() {
        return page;
    }

    // Setter Methods

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public void setPage(float page) {
        this.page = page;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public ArrayList<LeagueNewEntryResultDataModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<LeagueNewEntryResultDataModel> results) {
        this.results = results;
    }
}
