package com.infotech.fplcolosseum.features.homepage.models.livepoints;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Explain implements Serializable {
    public int fixture;

    @SerializedName("stats")
    public ArrayList<ExplainStat> stats;
}