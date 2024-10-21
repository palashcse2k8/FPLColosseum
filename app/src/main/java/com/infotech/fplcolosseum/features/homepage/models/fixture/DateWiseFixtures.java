package com.infotech.fplcolosseum.features.homepage.models.fixture;


import java.util.Date;
import java.util.List;

public class DateWiseFixtures {
    private final Date date;
    private final List<MatchDetails> fixtures;

    public DateWiseFixtures(Date date, List<MatchDetails> fixtures) {
        this.date = date;
        this.fixtures = fixtures;
    }

    public Date getDate() {
        return date;
    }

    public List<MatchDetails> getFixtures() {
        return fixtures;
    }
}
