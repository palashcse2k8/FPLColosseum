package com.infotech.fplcolosseum.utilities;

public enum PlayerSortingCriterion {
    TOTAL_POINTS("Total Points", "TP"),
    ROUND_POINTS("Round Points","RP"),
    TEAM_SELECTED_BY("Team Selected By", "TSB"),
    MINUTES_PLAYED("Minutes Played","MP"),
    GOALS_SCORED("Goals Scored", "GS"),
    ASSIST("Assist", "A"),
    CLEAN_SHEET("Clean Sheet", "CS"),
    GOALS_CONCEDED("Goals Conceded", "CC"),
    OWN_GOALS("Own Goals", "OG"),
    PENALTY_SAVED("Penalty Saved", "PS"),
    PENALTY_MISSED("Penalty Missed", "PM"),
    YELLOW_CARDS("Yellow Cards", "YC"),
    RED_CARDS("Red Cards", "RC"),
    SAVES("Saves", "S"),
    BONUS("Bonus", "B"),
    BONUS_POINTS_SYSTEM("Bonus Points System", "BPS"),
    INFLUENCE("Influence","Inf"),
    CREATIVITY("Creativity", "C"),
    THREAT("Threat", "T"),
    ICT_INDEX("ICT Index", "ICT"),
    GAMES_STARTED("Games Started", "GSt"),
    FORM("Form", "F"),
    TIMES_IN_TEAM_OF_THE_WEEK("Times in Team of the Week", "TITW"),
    VALUE_FORM("Value(form)", "VF"),
    VALUE_SEASON("Value(season)", "VS"),
    POINTS_PER_MATCH("Points Per Match", "PPM"),
    TRANSFERS_IN("Transfers In", "TI"),
    TRANSFERS_OUT("Transfers Out", "TO"),
    TRANSFERS_IN_ROUND("Transfers In(round)", "TIR"),
    TRANSFERS_OUT_ROUND("Transfers Out(round)", "TOR"),
    NET_TRANSFERS_IN_ROUND("Net Transfers In(round)", "NTI"),
    NET_TRANSFERS_OUT_ROUND("Net Transfers Out(round)", "NTO"),
    PRICE("Price", "P"),
    PRICE_RISE("Price Rise", "PR"),
    PRICE_FALL("Price Fall", "PF"),
    PRICE_RISE_ROUND("Price Rise(round)", "PRR"),
    PRICE_FALL_ROUND("Price Fall(round)", "PFR"),
    EXPECTED_GOALS("Expected Goals(xG)", "xG"),
    EXPECTED_ASSISTS("Expected Assists(xA)", "xA"),
    EXPECTED_GOALS_INVOLVEMENT("Expected Goals Involvement(xGI)", "xGI"),
    EXPECTED_GOALS_CONCEDED("Expected Goal Conceded(xGC)", "xGC"),
    NEWS_ADDED("News Added", "Inj");

    private final String displayName;
    private final String shortName;

    PlayerSortingCriterion(String displayName, String shortName) {
        this.displayName = displayName;
        this.shortName = shortName;
    }

    public String getDisplayName() {
        return displayName;
    }
    public String getShortName() {
        return shortName;
    }
}
