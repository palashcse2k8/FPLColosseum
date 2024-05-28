package com.infotech.fplcolosseum.features.homepage.models.fixture;

import java.util.List;

public class GameWeekMatchDetailsResponse {
    List<MatchDetails> matchDetails;

    public List<MatchDetails> getMatchDetails() {
        return matchDetails;
    }

    public void setMatchDetails(List<MatchDetails> matchDetails) {
        this.matchDetails = matchDetails;
    }
}

