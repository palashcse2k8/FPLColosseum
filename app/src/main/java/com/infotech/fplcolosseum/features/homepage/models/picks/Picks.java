package com.infotech.fplcolosseum.features.homepage.models.picks;

public class Picks {
    private long element;
    private long position;
    private long multiplier;
    private boolean is_captain;
    private boolean is_vice_captain;


    // Getter Methods

    public long getElement() {
        return element;
    }

    public long getPosition() {
        return position;
    }

    public long getMultiplier() {
        return multiplier;
    }

    public boolean getIs_captain() {
        return is_captain;
    }

    public boolean getIs_vice_captain() {
        return is_vice_captain;
    }

    // Setter Methods

    public void setElement(long element) {
        this.element = element;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
    }

    public void setIs_captain(boolean is_captain) {
        this.is_captain = is_captain;
    }

    public void setIs_vice_captain(boolean is_vice_captain) {
        this.is_vice_captain = is_vice_captain;
    }
}
