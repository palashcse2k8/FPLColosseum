package com.infotech.fplcolosseum.features.homepage.models.livepoints;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Element implements Serializable {
    public int id;

    @SerializedName("stats")
    public Stat stats;

    @SerializedName("explain")
    public ArrayList<Explain> explain;
}

