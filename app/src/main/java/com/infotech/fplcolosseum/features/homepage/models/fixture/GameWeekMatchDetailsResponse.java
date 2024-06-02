package com.infotech.fplcolosseum.features.homepage.models.fixture;

import java.util.ArrayList;
import java.util.List;

public class GameWeekMatchDetailsResponse {
    ArrayList<MatchDetails> matchDetails;

    public ArrayList<MatchDetails> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(ArrayList<MatchDetails> matchDetails) {
        this.matchDetails = matchDetails;
    }
}

