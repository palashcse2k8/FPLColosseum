package com.infotech.fplcolosseum.features.homepage.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekStaticDataModel {
    @SerializedName("events")
    ArrayList<GameWeekEvent> events = new ArrayList<>();

    @SerializedName("game_settings")
    Game_settings Game_settingsObject;
    @SerializedName("phases")
    ArrayList<Phase> phases = new ArrayList<>();

    @SerializedName("teams")
    ArrayList<TeamData> teams = new ArrayList<>();
    private long total_players;
    @SerializedName("elements")
    ArrayList<PlayersData> elements = new ArrayList<>();
    @SerializedName("element_stats")
    ArrayList<Element_Stats> element_stats = new ArrayList<>();
    @SerializedName("element_types")
    ArrayList<Player_Type> player_types = new ArrayList<>();


    // Getter Methods

    public Game_settings getGame_settings() {
        return Game_settingsObject;
    }

    public long getTotal_players() {
        return total_players;
    }

    public ArrayList<PlayersData> getElements() {
        return elements;
    }

    public ArrayList<GameWeekEvent> getEvents() {
        return events;
    }

    public ArrayList<TeamData> getTeams() {
        return teams;
    }

    public Game_settings getGame_settingsObject() {
        return Game_settingsObject;
    }

    public ArrayList<Phase> getPhases() {
        return phases;
    }

    public ArrayList<Element_Stats> getElement_stats() {
        return element_stats;
    }

    public ArrayList<Player_Type> getPlayer_types() {
        return player_types;
    }

    // Setter Methods

    public void setGame_settings(Game_settings game_settingsObject) {
        this.Game_settingsObject = game_settingsObject;
    }

    public void setTotal_players(long total_players) {
        this.total_players = total_players;
    }
}

class Game_settings {
    private float league_join_private_max;
    private float league_join_public_max;
    private float league_max_size_public_classic;
    private float league_max_size_public_h2h;
    private float league_max_size_private_h2h;
    private float league_max_ko_rounds_private_h2h;
    private String league_prefix_public;
    private float league_points_h2h_win;
    private float league_points_h2h_lose;
    private float league_points_h2h_draw;
    private boolean league_ko_first_instead_of_random;
    private String cup_start_event_id = null;
    private String cup_stop_event_id = null;
    private String cup_qualifying_method = null;
    private String cup_type = null;
    ArrayList<Object> featured_entries = new ArrayList<Object>();
    private float squad_squadplay;
    private float squad_squadsize;
    private float squad_team_limit;
    private float squad_total_spend;
    private float ui_currency_multiplier;
    private boolean ui_use_special_shirts;
    ArrayList<Object> ui_special_shirt_exclusions = new ArrayList<Object>();
    private float stats_form_days;
    private boolean sys_vice_captain_enabled;
    private float transfers_cap;
    private float transfers_sell_on_fee;
    ArrayList<Object> league_h2h_tiebreak_stats = new ArrayList<Object>();
    private String timezone;


    // Getter Methods

    public float getLeague_join_private_max() {
        return league_join_private_max;
    }

    public float getLeague_join_public_max() {
        return league_join_public_max;
    }

    public float getLeague_max_size_public_classic() {
        return league_max_size_public_classic;
    }

    public float getLeague_max_size_public_h2h() {
        return league_max_size_public_h2h;
    }

    public float getLeague_max_size_private_h2h() {
        return league_max_size_private_h2h;
    }

    public float getLeague_max_ko_rounds_private_h2h() {
        return league_max_ko_rounds_private_h2h;
    }

    public String getLeague_prefix_public() {
        return league_prefix_public;
    }

    public float getLeague_points_h2h_win() {
        return league_points_h2h_win;
    }

    public float getLeague_points_h2h_lose() {
        return league_points_h2h_lose;
    }

    public float getLeague_points_h2h_draw() {
        return league_points_h2h_draw;
    }

    public boolean getLeague_ko_first_instead_of_random() {
        return league_ko_first_instead_of_random;
    }

    public String getCup_start_event_id() {
        return cup_start_event_id;
    }

    public String getCup_stop_event_id() {
        return cup_stop_event_id;
    }

    public String getCup_qualifying_method() {
        return cup_qualifying_method;
    }

    public String getCup_type() {
        return cup_type;
    }

    public float getSquad_squadplay() {
        return squad_squadplay;
    }

    public float getSquad_squadsize() {
        return squad_squadsize;
    }

    public float getSquad_team_limit() {
        return squad_team_limit;
    }

    public float getSquad_total_spend() {
        return squad_total_spend;
    }

    public float getUi_currency_multiplier() {
        return ui_currency_multiplier;
    }

    public boolean getUi_use_special_shirts() {
        return ui_use_special_shirts;
    }

    public float getStats_form_days() {
        return stats_form_days;
    }

    public boolean getSys_vice_captain_enabled() {
        return sys_vice_captain_enabled;
    }

    public float getTransfers_cap() {
        return transfers_cap;
    }

    public float getTransfers_sell_on_fee() {
        return transfers_sell_on_fee;
    }

    public String getTimezone() {
        return timezone;
    }

    // Setter Methods

    public void setLeague_join_private_max(long league_join_private_max) {
        this.league_join_private_max = league_join_private_max;
    }

    public void setLeague_join_public_max(long league_join_public_max) {
        this.league_join_public_max = league_join_public_max;
    }

    public void setLeague_max_size_public_classic(long league_max_size_public_classic) {
        this.league_max_size_public_classic = league_max_size_public_classic;
    }

    public void setLeague_max_size_public_h2h(long league_max_size_public_h2h) {
        this.league_max_size_public_h2h = league_max_size_public_h2h;
    }

    public void setLeague_max_size_private_h2h(long league_max_size_private_h2h) {
        this.league_max_size_private_h2h = league_max_size_private_h2h;
    }

    public void setLeague_max_ko_rounds_private_h2h(long league_max_ko_rounds_private_h2h) {
        this.league_max_ko_rounds_private_h2h = league_max_ko_rounds_private_h2h;
    }

    public void setLeague_prefix_public(String league_prefix_public) {
        this.league_prefix_public = league_prefix_public;
    }

    public void setLeague_points_h2h_win(long league_points_h2h_win) {
        this.league_points_h2h_win = league_points_h2h_win;
    }

    public void setLeague_points_h2h_lose(long league_points_h2h_lose) {
        this.league_points_h2h_lose = league_points_h2h_lose;
    }

    public void setLeague_points_h2h_draw(long league_points_h2h_draw) {
        this.league_points_h2h_draw = league_points_h2h_draw;
    }

    public void setLeague_ko_first_instead_of_random(boolean league_ko_first_instead_of_random) {
        this.league_ko_first_instead_of_random = league_ko_first_instead_of_random;
    }

    public void setCup_start_event_id(String cup_start_event_id) {
        this.cup_start_event_id = cup_start_event_id;
    }

    public void setCup_stop_event_id(String cup_stop_event_id) {
        this.cup_stop_event_id = cup_stop_event_id;
    }

    public void setCup_qualifying_method(String cup_qualifying_method) {
        this.cup_qualifying_method = cup_qualifying_method;
    }

    public void setCup_type(String cup_type) {
        this.cup_type = cup_type;
    }

    public void setSquad_squadplay(long squad_squadplay) {
        this.squad_squadplay = squad_squadplay;
    }

    public void setSquad_squadsize(long squad_squadsize) {
        this.squad_squadsize = squad_squadsize;
    }

    public void setSquad_team_limit(long squad_team_limit) {
        this.squad_team_limit = squad_team_limit;
    }

    public void setSquad_total_spend(long squad_total_spend) {
        this.squad_total_spend = squad_total_spend;
    }

    public void setUi_currency_multiplier(long ui_currency_multiplier) {
        this.ui_currency_multiplier = ui_currency_multiplier;
    }

    public void setUi_use_special_shirts(boolean ui_use_special_shirts) {
        this.ui_use_special_shirts = ui_use_special_shirts;
    }

    public void setStats_form_days(long stats_form_days) {
        this.stats_form_days = stats_form_days;
    }

    public void setSys_vice_captain_enabled(boolean sys_vice_captain_enabled) {
        this.sys_vice_captain_enabled = sys_vice_captain_enabled;
    }

    public void setTransfers_cap(long transfers_cap) {
        this.transfers_cap = transfers_cap;
    }

    public void setTransfers_sell_on_fee(long transfers_sell_on_fee) {
        this.transfers_sell_on_fee = transfers_sell_on_fee;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}

class Element_Stats {
    private String label;
    private String name;


// Getter Methods

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

// Setter Methods

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Top_element_info {
    private long id;
    private long points;


    // Getter Methods

    public long getId() {
        return id;
    }

    public long getPoints() {
        return points;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}

class Phase {
    private long id;
    private String name;
    private long start_event;
    private long stop_event;


    // Getter Methods

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getStart_event() {
        return start_event;
    }

    public long getStop_event() {
        return stop_event;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart_event(long start_event) {
        this.start_event = start_event;
    }

    public void setStop_event(long stop_event) {
        this.stop_event = stop_event;
    }
}