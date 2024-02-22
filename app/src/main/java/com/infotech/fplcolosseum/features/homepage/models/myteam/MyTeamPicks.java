package com.infotech.fplcolosseum.features.homepage.models.myteam;

public class MyTeamPicks {
    private long element;
    private long position;
    private long selling_price;
    private long multiplier;
    private long purchase_price;
    private boolean is_captain;
    private boolean is_vice_captain;


    // Getter Methods

    public long getElement() {
        return element;
    }

    public long getPosition() {
        return position;
    }

    public long getSelling_price() {
        return selling_price;
    }

    public long getMultiplier() {
        return multiplier;
    }

    public long getPurchase_price() {
        return purchase_price;
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

    public void setSelling_price(long selling_price) {
        this.selling_price = selling_price;
    }

    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
    }

    public void setPurchase_price(long purchase_price) {
        this.purchase_price = purchase_price;
    }

    public void setIs_captain(boolean is_captain) {
        this.is_captain = is_captain;
    }

    public void setIs_vice_captain(boolean is_vice_captain) {
        this.is_vice_captain = is_vice_captain;
    }
}
