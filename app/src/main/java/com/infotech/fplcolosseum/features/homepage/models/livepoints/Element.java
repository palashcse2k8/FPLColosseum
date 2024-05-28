package com.infotech.fplcolosseum.features.homepage.models.livepoints;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Element {
    public int id;

    @SerializedName("stats")
    public Stat stats;

    @SerializedName("explain")
    public ArrayList<Explain> explain;
}

