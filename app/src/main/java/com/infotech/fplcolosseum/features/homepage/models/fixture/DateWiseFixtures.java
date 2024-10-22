package com.infotech.fplcolosseum.features.homepage.models.fixture;


import java.util.Date;
import java.util.List;

public record DateWiseFixtures(Date date, List<MatchDetails> fixtures) {
}
