package com.infotech.fplcolosseum.features.homepage.models.myteam;

import java.util.ArrayList;

public class GameChips {
    private String status_for_entry;
    ArrayList < Object > played_by_entry = new ArrayList < Object > ();
    private String name;
    private long number;
    private long start_event;
    private long stop_event;
    private String chip_type;


    // Getter Methods

    public String getStatus_for_entry() {
        return status_for_entry;
    }

    public String getName() {
        return name;
    }

    public long getNumber() {
        return number;
    }

    public long getStart_event() {
        return start_event;
    }

    public long getStop_event() {
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

    public void setNumber(long number) {
        this.number = number;
    }

    public void setStart_event(long start_event) {
        this.start_event = start_event;
    }

    public void setStop_event(long stop_event) {
        this.stop_event = stop_event;
    }

    public void setChip_type(String chip_type) {
        this.chip_type = chip_type;
    }
}
