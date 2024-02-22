package com.infotech.fplcolosseum.features.homepage.models.staticdata;

import java.util.ArrayList;

public class Player_Type {
    private long id;
    private String plural_name;
    private String plural_name_short;
    private String singular_name;
    private String singular_name_short;
    private long squad_select;
    private long squad_min_play;
    private long squad_max_play;
    private boolean ui_shirt_specific;
    ArrayList<Object> sub_positions_locked = new ArrayList<Object>();
    private long element_count;


    // Getter Methods

    public long getId() {
        return id;
    }

    public String getPlural_name() {
        return plural_name;
    }

    public String getPlural_name_short() {
        return plural_name_short;
    }

    public String getSingular_name() {
        return singular_name;
    }

    public String getSingular_name_short() {
        return singular_name_short;
    }

    public long getSquad_select() {
        return squad_select;
    }

    public long getSquad_min_play() {
        return squad_min_play;
    }

    public long getSquad_max_play() {
        return squad_max_play;
    }

    public boolean getUi_shirt_specific() {
        return ui_shirt_specific;
    }

    public long getElement_count() {
        return element_count;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setPlural_name(String plural_name) {
        this.plural_name = plural_name;
    }

    public void setPlural_name_short(String plural_name_short) {
        this.plural_name_short = plural_name_short;
    }

    public void setSingular_name(String singular_name) {
        this.singular_name = singular_name;
    }

    public void setSingular_name_short(String singular_name_short) {
        this.singular_name_short = singular_name_short;
    }

    public void setSquad_select(long squad_select) {
        this.squad_select = squad_select;
    }

    public void setSquad_min_play(long squad_min_play) {
        this.squad_min_play = squad_min_play;
    }

    public void setSquad_max_play(long squad_max_play) {
        this.squad_max_play = squad_max_play;
    }

    public void setUi_shirt_specific(boolean ui_shirt_specific) {
        this.ui_shirt_specific = ui_shirt_specific;
    }

    public void setElement_count(long element_count) {
        this.element_count = element_count;
    }
}
