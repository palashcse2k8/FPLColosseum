package com.infotech.fplcolosseum.features.homepage.models.fixture;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MatchStats {
    private String identifier;
    @SerializedName("a")
    ArrayList<MatchElement> a = new ArrayList<>();
    @SerializedName("h")
    ArrayList<MatchElement> h = new ArrayList<>();


    // Getter Methods

    public String getIdentifier() {
        return identifier;
    }

    // Setter Methods

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
