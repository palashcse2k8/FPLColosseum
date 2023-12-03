package com.infotech.fplcolosseum.features.homepage.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekMyTeamResponseModel {
    @SerializedName("picks")
    ArrayList<GameWeekPicks> picks = new ArrayList <> ();
    @SerializedName("chips")
    ArrayList <GameChips> chips = new ArrayList <> ();
    @SerializedName("transfers")
    Transfers TransfersObject;


    // Getter Methods

    public Transfers getTransfers() {
        return TransfersObject;
    }

    // Setter Methods

    public void setTransfers(Transfers transfersObject) {
        this.TransfersObject = transfersObject;
    }
}
class Transfers {
    private float cost;
    private String status;
    private float limit;
    private float made;
    private float bank;
    private float value;


    // Getter Methods

    public float getCost() {
        return cost;
    }

    public String getStatus() {
        return status;
    }

    public float getLimit() {
        return limit;
    }

    public float getMade() {
        return made;
    }

    public float getBank() {
        return bank;
    }

    public float getValue() {
        return value;
    }

    // Setter Methods

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

    public void setMade(float made) {
        this.made = made;
    }

    public void setBank(float bank) {
        this.bank = bank;
    }

    public void setValue(float value) {
        this.value = value;
    }
}

class GameWeekPicks {
    private float element;
    private float position;
    private float selling_price;
    private float multiplier;
    private float purchase_price;
    private boolean is_captain;
    private boolean is_vice_captain;


    // Getter Methods

    public float getElement() {
        return element;
    }

    public float getPosition() {
        return position;
    }

    public float getSelling_price() {
        return selling_price;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public float getPurchase_price() {
        return purchase_price;
    }

    public boolean getIs_captain() {
        return is_captain;
    }

    public boolean getIs_vice_captain() {
        return is_vice_captain;
    }

    // Setter Methods

    public void setElement(float element) {
        this.element = element;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public void setSelling_price(float selling_price) {
        this.selling_price = selling_price;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }

    public void setPurchase_price(float purchase_price) {
        this.purchase_price = purchase_price;
    }

    public void setIs_captain(boolean is_captain) {
        this.is_captain = is_captain;
    }

    public void setIs_vice_captain(boolean is_vice_captain) {
        this.is_vice_captain = is_vice_captain;
    }
}

class GameChips {
    private String status_for_entry;
    ArrayList < Object > played_by_entry = new ArrayList < Object > ();
    private String name;
    private float number;
    private float start_event;
    private float stop_event;
    private String chip_type;


    // Getter Methods

    public String getStatus_for_entry() {
        return status_for_entry;
    }

    public String getName() {
        return name;
    }

    public float getNumber() {
        return number;
    }

    public float getStart_event() {
        return start_event;
    }

    public float getStop_event() {
        return stop_event;
    }

    public String getChip_type() {
        return chip_type;
    }

    // Setter Methods

    public void setStatus_for_entry(String status_for_entry) {
        this.status_for_entry = status_for_entry;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public void setStart_event(float start_event) {
        this.start_event = start_event;
    }

    public void setStop_event(float stop_event) {
        this.stop_event = stop_event;
    }

    public void setChip_type(String chip_type) {
        this.chip_type = chip_type;
    }
}