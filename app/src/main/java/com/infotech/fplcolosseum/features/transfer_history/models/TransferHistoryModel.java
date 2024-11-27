package com.infotech.fplcolosseum.features.transfer_history.models;

public class TransferHistoryModel {
    private long element_in;
    private long element_in_cost;
    private long element_out;
    private long element_out_cost;
    private long entry;
    private int event;
    private String time;


    // Getter Methods

    public long getElement_in() {
        return element_in;
    }

    public long getElement_in_cost() {
        return element_in_cost;
    }

    public long getElement_out() {
        return element_out;
    }

    public long getElement_out_cost() {
        return element_out_cost;
    }

    public long getEntry() {
        return entry;
    }

    public int getEvent() {
        return event;
    }

    public String getTime() {
        return time;
    }

    // Setter Methods

    public void setElement_in(long element_in) {
        this.element_in = element_in;
    }

    public void setElement_in_cost(long element_in_cost) {
        this.element_in_cost = element_in_cost;
    }

    public void setElement_out(long element_out) {
        this.element_out = element_out;
    }

    public void setElement_out_cost(long element_out_cost) {
        this.element_out_cost = element_out_cost;
    }

    public void setEntry(long entry) {
        this.entry = entry;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
