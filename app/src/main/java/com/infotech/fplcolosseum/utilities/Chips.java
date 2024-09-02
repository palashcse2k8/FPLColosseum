package com.infotech.fplcolosseum.utilities;

public enum Chips {
    BB("Bench Boost", -1, "bboost"),
    FH("Free Hit", -1, "freehit"),
    TC("Triple Captain", -1, "3xc"),
    WC("WildCard", -1, "wildcard"),
    AP("AutoPick", -1, "ap");

    private final String displayName;
    private final int iconResourceId;
    private final String shortName;

    Chips(String displayName, int iconResourceId, String shortName) {
        this.displayName = displayName;
        this.iconResourceId = iconResourceId;
        this.shortName = shortName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public String getShortName() {
        return shortName;
    }
}