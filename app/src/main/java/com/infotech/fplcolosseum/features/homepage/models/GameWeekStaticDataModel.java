package com.infotech.fplcolosseum.features.homepage.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameWeekStaticDataModel {
    @SerializedName("events")
    ArrayList< GameWeekEvent > events = new ArrayList <> ();
    Game_settings Game_settingsObject;
    ArrayList < Phase > phases = new ArrayList < > ();
    ArrayList < Teams > teams = new ArrayList <> ();
    private float total_players;
    ArrayList < Elements > elements = new ArrayList < > ();
    @SerializedName("element_stats")
    ArrayList < Element_Stats > element_stats = new ArrayList <> ();
    ArrayList < Element_Type > element_types = new ArrayList <> ();


    // Getter Methods

    public Game_settings getGame_settings() {
        return Game_settingsObject;
    }

    public float getTotal_players() {
        return total_players;
    }

    // Setter Methods

    public void setGame_settings(Game_settings game_settingsObject) {
        this.Game_settingsObject = game_settingsObject;
    }

    public void setTotal_players(float total_players) {
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
    ArrayList < Object > featured_entries = new ArrayList < Object > ();
    private float squad_squadplay;
    private float squad_squadsize;
    private float squad_team_limit;
    private float squad_total_spend;
    private float ui_currency_multiplier;
    private boolean ui_use_special_shirts;
    ArrayList < Object > ui_special_shirt_exclusions = new ArrayList < Object > ();
    private float stats_form_days;
    private boolean sys_vice_captain_enabled;
    private float transfers_cap;
    private float transfers_sell_on_fee;
    ArrayList < Object > league_h2h_tiebreak_stats = new ArrayList < Object > ();
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

    public void setLeague_join_private_max(float league_join_private_max) {
        this.league_join_private_max = league_join_private_max;
    }

    public void setLeague_join_public_max(float league_join_public_max) {
        this.league_join_public_max = league_join_public_max;
    }

    public void setLeague_max_size_public_classic(float league_max_size_public_classic) {
        this.league_max_size_public_classic = league_max_size_public_classic;
    }

    public void setLeague_max_size_public_h2h(float league_max_size_public_h2h) {
        this.league_max_size_public_h2h = league_max_size_public_h2h;
    }

    public void setLeague_max_size_private_h2h(float league_max_size_private_h2h) {
        this.league_max_size_private_h2h = league_max_size_private_h2h;
    }

    public void setLeague_max_ko_rounds_private_h2h(float league_max_ko_rounds_private_h2h) {
        this.league_max_ko_rounds_private_h2h = league_max_ko_rounds_private_h2h;
    }

    public void setLeague_prefix_public(String league_prefix_public) {
        this.league_prefix_public = league_prefix_public;
    }

    public void setLeague_points_h2h_win(float league_points_h2h_win) {
        this.league_points_h2h_win = league_points_h2h_win;
    }

    public void setLeague_points_h2h_lose(float league_points_h2h_lose) {
        this.league_points_h2h_lose = league_points_h2h_lose;
    }

    public void setLeague_points_h2h_draw(float league_points_h2h_draw) {
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

    public void setSquad_squadplay(float squad_squadplay) {
        this.squad_squadplay = squad_squadplay;
    }

    public void setSquad_squadsize(float squad_squadsize) {
        this.squad_squadsize = squad_squadsize;
    }

    public void setSquad_team_limit(float squad_team_limit) {
        this.squad_team_limit = squad_team_limit;
    }

    public void setSquad_total_spend(float squad_total_spend) {
        this.squad_total_spend = squad_total_spend;
    }

    public void setUi_currency_multiplier(float ui_currency_multiplier) {
        this.ui_currency_multiplier = ui_currency_multiplier;
    }

    public void setUi_use_special_shirts(boolean ui_use_special_shirts) {
        this.ui_use_special_shirts = ui_use_special_shirts;
    }

    public void setStats_form_days(float stats_form_days) {
        this.stats_form_days = stats_form_days;
    }

    public void setSys_vice_captain_enabled(boolean sys_vice_captain_enabled) {
        this.sys_vice_captain_enabled = sys_vice_captain_enabled;
    }

    public void setTransfers_cap(float transfers_cap) {
        this.transfers_cap = transfers_cap;
    }

    public void setTransfers_sell_on_fee(float transfers_sell_on_fee) {
        this.transfers_sell_on_fee = transfers_sell_on_fee;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}

class GameWeekEvent {
    private float id;
    private String name;
    private String deadline_time;
    private float average_entry_score;
    private boolean finished;
    private boolean data_checked;
    private float highest_scoring_entry;
    private float deadline_time_epoch;
    private float deadline_time_game_offset;
    private float highest_score;
    private boolean is_previous;
    private boolean is_current;
    private boolean is_next;
    private boolean cup_leagues_created;
    private boolean h2h_ko_matches_created;
    ArrayList < Object > chip_plays = new ArrayList < Object > ();
    private float most_selected;
    private float most_transferred_in;
    private float top_element;
    Top_element_info Top_element_infoObject;
    private float transfers_made;
    private float most_captained;
    private float most_vice_captained;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeadline_time() {
        return deadline_time;
    }

    public float getAverage_entry_score() {
        return average_entry_score;
    }

    public boolean getFinished() {
        return finished;
    }

    public boolean getData_checked() {
        return data_checked;
    }

    public float getHighest_scoring_entry() {
        return highest_scoring_entry;
    }

    public float getDeadline_time_epoch() {
        return deadline_time_epoch;
    }

    public float getDeadline_time_game_offset() {
        return deadline_time_game_offset;
    }

    public float getHighest_score() {
        return highest_score;
    }

    public boolean getIs_previous() {
        return is_previous;
    }

    public boolean getIs_current() {
        return is_current;
    }

    public boolean getIs_next() {
        return is_next;
    }

    public boolean getCup_leagues_created() {
        return cup_leagues_created;
    }

    public boolean getH2h_ko_matches_created() {
        return h2h_ko_matches_created;
    }

    public float getMost_selected() {
        return most_selected;
    }

    public float getMost_transferred_in() {
        return most_transferred_in;
    }

    public float getTop_element() {
        return top_element;
    }

    public Top_element_info getTop_element_info() {
        return Top_element_infoObject;
    }

    public float getTransfers_made() {
        return transfers_made;
    }

    public float getMost_captained() {
        return most_captained;
    }

    public float getMost_vice_captained() {
        return most_vice_captained;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline_time(String deadline_time) {
        this.deadline_time = deadline_time;
    }

    public void setAverage_entry_score(float average_entry_score) {
        this.average_entry_score = average_entry_score;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setData_checked(boolean data_checked) {
        this.data_checked = data_checked;
    }

    public void setHighest_scoring_entry(float highest_scoring_entry) {
        this.highest_scoring_entry = highest_scoring_entry;
    }

    public void setDeadline_time_epoch(float deadline_time_epoch) {
        this.deadline_time_epoch = deadline_time_epoch;
    }

    public void setDeadline_time_game_offset(float deadline_time_game_offset) {
        this.deadline_time_game_offset = deadline_time_game_offset;
    }

    public void setHighest_score(float highest_score) {
        this.highest_score = highest_score;
    }

    public void setIs_previous(boolean is_previous) {
        this.is_previous = is_previous;
    }

    public void setIs_current(boolean is_current) {
        this.is_current = is_current;
    }

    public void setIs_next(boolean is_next) {
        this.is_next = is_next;
    }

    public void setCup_leagues_created(boolean cup_leagues_created) {
        this.cup_leagues_created = cup_leagues_created;
    }

    public void setH2h_ko_matches_created(boolean h2h_ko_matches_created) {
        this.h2h_ko_matches_created = h2h_ko_matches_created;
    }

    public void setMost_selected(float most_selected) {
        this.most_selected = most_selected;
    }

    public void setMost_transferred_in(float most_transferred_in) {
        this.most_transferred_in = most_transferred_in;
    }

    public void setTop_element(float top_element) {
        this.top_element = top_element;
    }

    public void setTop_element_info(Top_element_info top_element_infoObject) {
        this.Top_element_infoObject = top_element_infoObject;
    }

    public void setTransfers_made(float transfers_made) {
        this.transfers_made = transfers_made;
    }

    public void setMost_captained(float most_captained) {
        this.most_captained = most_captained;
    }

    public void setMost_vice_captained(float most_vice_captained) {
        this.most_vice_captained = most_vice_captained;
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
    private float id;
    private float points;


    // Getter Methods

    public float getId() {
        return id;
    }

    public float getPoints() {
        return points;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setPoints(float points) {
        this.points = points;
    }
}

class Element_Type {
    private float id;
    private String plural_name;
    private String plural_name_short;
    private String singular_name;
    private String singular_name_short;
    private float squad_select;
    private float squad_min_play;
    private float squad_max_play;
    private boolean ui_shirt_specific;
    ArrayList < Object > sub_positions_locked = new ArrayList < Object > ();
    private float element_count;


    // Getter Methods

    public float getId() {
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

    public float getSquad_select() {
        return squad_select;
    }

    public float getSquad_min_play() {
        return squad_min_play;
    }

    public float getSquad_max_play() {
        return squad_max_play;
    }

    public boolean getUi_shirt_specific() {
        return ui_shirt_specific;
    }

    public float getElement_count() {
        return element_count;
    }

    // Setter Methods

    public void setId(float id) {
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

    public void setSquad_select(float squad_select) {
        this.squad_select = squad_select;
    }

    public void setSquad_min_play(float squad_min_play) {
        this.squad_min_play = squad_min_play;
    }

    public void setSquad_max_play(float squad_max_play) {
        this.squad_max_play = squad_max_play;
    }

    public void setUi_shirt_specific(boolean ui_shirt_specific) {
        this.ui_shirt_specific = ui_shirt_specific;
    }

    public void setElement_count(float element_count) {
        this.element_count = element_count;
    }
}

class Elements {
    private float chance_of_playing_next_round;
    private float chance_of_playing_this_round;
    private float code;
    private float cost_change_event;
    private float cost_change_event_fall;
    private float cost_change_start;
    private float cost_change_start_fall;
    private float dreamteam_count;
    private float element_type;
    private String ep_next;
    private String ep_this;
    private float event_points;
    private String first_name;
    private String form;
    private float id;
    private boolean in_dreamteam;
    private String news;
    private String news_added;
    private float now_cost;
    private String photo;
    private String points_per_game;
    private String second_name;
    private String selected_by_percent;
    private boolean special;
    private String squad_number = null;
    private String status;
    private float team;
    private float team_code;
    private float total_points;
    private float transfers_in;
    private float transfers_in_event;
    private float transfers_out;
    private float transfers_out_event;
    private String value_form;
    private String value_season;
    private String web_name;
    private float minutes;
    private float goals_scored;
    private float assists;
    private float clean_sheets;
    private float goals_conceded;
    private float own_goals;
    private float penalties_saved;
    private float penalties_missed;
    private float yellow_cards;
    private float red_cards;
    private float saves;
    private float bonus;
    private float bps;
    private String influence;
    private String creativity;
    private String threat;
    private String ict_index;
    private float starts;
    private String expected_goals;
    private String expected_assists;
    private String expected_goal_involvements;
    private String expected_goals_conceded;
    private float influence_rank;
    private float influence_rank_type;
    private float creativity_rank;
    private float creativity_rank_type;
    private float threat_rank;
    private float threat_rank_type;
    private float ict_index_rank;
    private float ict_index_rank_type;
    private String corners_and_indirect_freekicks_order = null;
    private String corners_and_indirect_freekicks_text;
    private String direct_freekicks_order = null;
    private String direct_freekicks_text;
    private String penalties_order = null;
    private String penalties_text;
    private float expected_goals_per_90;
    private float saves_per_90;
    private float expected_assists_per_90;
    private float expected_goal_involvements_per_90;
    private float expected_goals_conceded_per_90;
    private float goals_conceded_per_90;
    private float now_cost_rank;
    private float now_cost_rank_type;
    private float form_rank;
    private float form_rank_type;
    private float points_per_game_rank;
    private float points_per_game_rank_type;
    private float selected_rank;
    private float selected_rank_type;
    private float starts_per_90;
    private float clean_sheets_per_90;


    // Getter Methods

    public float getChance_of_playing_next_round() {
        return chance_of_playing_next_round;
    }

    public float getChance_of_playing_this_round() {
        return chance_of_playing_this_round;
    }

    public float getCode() {
        return code;
    }

    public float getCost_change_event() {
        return cost_change_event;
    }

    public float getCost_change_event_fall() {
        return cost_change_event_fall;
    }

    public float getCost_change_start() {
        return cost_change_start;
    }

    public float getCost_change_start_fall() {
        return cost_change_start_fall;
    }

    public float getDreamteam_count() {
        return dreamteam_count;
    }

    public float getElement_type() {
        return element_type;
    }

    public String getEp_next() {
        return ep_next;
    }

    public String getEp_this() {
        return ep_this;
    }

    public float getEvent_points() {
        return event_points;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getForm() {
        return form;
    }

    public float getId() {
        return id;
    }

    public boolean getIn_dreamteam() {
        return in_dreamteam;
    }

    public String getNews() {
        return news;
    }

    public String getNews_added() {
        return news_added;
    }

    public float getNow_cost() {
        return now_cost;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPoints_per_game() {
        return points_per_game;
    }

    public String getSecond_name() {
        return second_name;
    }

    public String getSelected_by_percent() {
        return selected_by_percent;
    }

    public boolean getSpecial() {
        return special;
    }

    public String getSquad_number() {
        return squad_number;
    }

    public String getStatus() {
        return status;
    }

    public float getTeam() {
        return team;
    }

    public float getTeam_code() {
        return team_code;
    }

    public float getTotal_points() {
        return total_points;
    }

    public float getTransfers_in() {
        return transfers_in;
    }

    public float getTransfers_in_event() {
        return transfers_in_event;
    }

    public float getTransfers_out() {
        return transfers_out;
    }

    public float getTransfers_out_event() {
        return transfers_out_event;
    }

    public String getValue_form() {
        return value_form;
    }

    public String getValue_season() {
        return value_season;
    }

    public String getWeb_name() {
        return web_name;
    }

    public float getMinutes() {
        return minutes;
    }

    public float getGoals_scored() {
        return goals_scored;
    }

    public float getAssists() {
        return assists;
    }

    public float getClean_sheets() {
        return clean_sheets;
    }

    public float getGoals_conceded() {
        return goals_conceded;
    }

    public float getOwn_goals() {
        return own_goals;
    }

    public float getPenalties_saved() {
        return penalties_saved;
    }

    public float getPenalties_missed() {
        return penalties_missed;
    }

    public float getYellow_cards() {
        return yellow_cards;
    }

    public float getRed_cards() {
        return red_cards;
    }

    public float getSaves() {
        return saves;
    }

    public float getBonus() {
        return bonus;
    }

    public float getBps() {
        return bps;
    }

    public String getInfluence() {
        return influence;
    }

    public String getCreativity() {
        return creativity;
    }

    public String getThreat() {
        return threat;
    }

    public String getIct_index() {
        return ict_index;
    }

    public float getStarts() {
        return starts;
    }

    public String getExpected_goals() {
        return expected_goals;
    }

    public String getExpected_assists() {
        return expected_assists;
    }

    public String getExpected_goal_involvements() {
        return expected_goal_involvements;
    }

    public String getExpected_goals_conceded() {
        return expected_goals_conceded;
    }

    public float getInfluence_rank() {
        return influence_rank;
    }

    public float getInfluence_rank_type() {
        return influence_rank_type;
    }

    public float getCreativity_rank() {
        return creativity_rank;
    }

    public float getCreativity_rank_type() {
        return creativity_rank_type;
    }

    public float getThreat_rank() {
        return threat_rank;
    }

    public float getThreat_rank_type() {
        return threat_rank_type;
    }

    public float getIct_index_rank() {
        return ict_index_rank;
    }

    public float getIct_index_rank_type() {
        return ict_index_rank_type;
    }

    public String getCorners_and_indirect_freekicks_order() {
        return corners_and_indirect_freekicks_order;
    }

    public String getCorners_and_indirect_freekicks_text() {
        return corners_and_indirect_freekicks_text;
    }

    public String getDirect_freekicks_order() {
        return direct_freekicks_order;
    }

    public String getDirect_freekicks_text() {
        return direct_freekicks_text;
    }

    public String getPenalties_order() {
        return penalties_order;
    }

    public String getPenalties_text() {
        return penalties_text;
    }

    public float getExpected_goals_per_90() {
        return expected_goals_per_90;
    }

    public float getSaves_per_90() {
        return saves_per_90;
    }

    public float getExpected_assists_per_90() {
        return expected_assists_per_90;
    }

    public float getExpected_goal_involvements_per_90() {
        return expected_goal_involvements_per_90;
    }

    public float getExpected_goals_conceded_per_90() {
        return expected_goals_conceded_per_90;
    }

    public float getGoals_conceded_per_90() {
        return goals_conceded_per_90;
    }

    public float getNow_cost_rank() {
        return now_cost_rank;
    }

    public float getNow_cost_rank_type() {
        return now_cost_rank_type;
    }

    public float getForm_rank() {
        return form_rank;
    }

    public float getForm_rank_type() {
        return form_rank_type;
    }

    public float getPoints_per_game_rank() {
        return points_per_game_rank;
    }

    public float getPoints_per_game_rank_type() {
        return points_per_game_rank_type;
    }

    public float getSelected_rank() {
        return selected_rank;
    }

    public float getSelected_rank_type() {
        return selected_rank_type;
    }

    public float getStarts_per_90() {
        return starts_per_90;
    }

    public float getClean_sheets_per_90() {
        return clean_sheets_per_90;
    }

    // Setter Methods

    public void setChance_of_playing_next_round(float chance_of_playing_next_round) {
        this.chance_of_playing_next_round = chance_of_playing_next_round;
    }

    public void setChance_of_playing_this_round(float chance_of_playing_this_round) {
        this.chance_of_playing_this_round = chance_of_playing_this_round;
    }

    public void setCode(float code) {
        this.code = code;
    }

    public void setCost_change_event(float cost_change_event) {
        this.cost_change_event = cost_change_event;
    }

    public void setCost_change_event_fall(float cost_change_event_fall) {
        this.cost_change_event_fall = cost_change_event_fall;
    }

    public void setCost_change_start(float cost_change_start) {
        this.cost_change_start = cost_change_start;
    }

    public void setCost_change_start_fall(float cost_change_start_fall) {
        this.cost_change_start_fall = cost_change_start_fall;
    }

    public void setDreamteam_count(float dreamteam_count) {
        this.dreamteam_count = dreamteam_count;
    }

    public void setElement_type(float element_type) {
        this.element_type = element_type;
    }

    public void setEp_next(String ep_next) {
        this.ep_next = ep_next;
    }

    public void setEp_this(String ep_this) {
        this.ep_this = ep_this;
    }

    public void setEvent_points(float event_points) {
        this.event_points = event_points;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setIn_dreamteam(boolean in_dreamteam) {
        this.in_dreamteam = in_dreamteam;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public void setNews_added(String news_added) {
        this.news_added = news_added;
    }

    public void setNow_cost(float now_cost) {
        this.now_cost = now_cost;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setPoints_per_game(String points_per_game) {
        this.points_per_game = points_per_game;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setSelected_by_percent(String selected_by_percent) {
        this.selected_by_percent = selected_by_percent;
    }

    public void setSpecial(boolean special) {
        this.special = special;
    }

    public void setSquad_number(String squad_number) {
        this.squad_number = squad_number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTeam(float team) {
        this.team = team;
    }

    public void setTeam_code(float team_code) {
        this.team_code = team_code;
    }

    public void setTotal_points(float total_points) {
        this.total_points = total_points;
    }

    public void setTransfers_in(float transfers_in) {
        this.transfers_in = transfers_in;
    }

    public void setTransfers_in_event(float transfers_in_event) {
        this.transfers_in_event = transfers_in_event;
    }

    public void setTransfers_out(float transfers_out) {
        this.transfers_out = transfers_out;
    }

    public void setTransfers_out_event(float transfers_out_event) {
        this.transfers_out_event = transfers_out_event;
    }

    public void setValue_form(String value_form) {
        this.value_form = value_form;
    }

    public void setValue_season(String value_season) {
        this.value_season = value_season;
    }

    public void setWeb_name(String web_name) {
        this.web_name = web_name;
    }

    public void setMinutes(float minutes) {
        this.minutes = minutes;
    }

    public void setGoals_scored(float goals_scored) {
        this.goals_scored = goals_scored;
    }

    public void setAssists(float assists) {
        this.assists = assists;
    }

    public void setClean_sheets(float clean_sheets) {
        this.clean_sheets = clean_sheets;
    }

    public void setGoals_conceded(float goals_conceded) {
        this.goals_conceded = goals_conceded;
    }

    public void setOwn_goals(float own_goals) {
        this.own_goals = own_goals;
    }

    public void setPenalties_saved(float penalties_saved) {
        this.penalties_saved = penalties_saved;
    }

    public void setPenalties_missed(float penalties_missed) {
        this.penalties_missed = penalties_missed;
    }

    public void setYellow_cards(float yellow_cards) {
        this.yellow_cards = yellow_cards;
    }

    public void setRed_cards(float red_cards) {
        this.red_cards = red_cards;
    }

    public void setSaves(float saves) {
        this.saves = saves;
    }

    public void setBonus(float bonus) {
        this.bonus = bonus;
    }

    public void setBps(float bps) {
        this.bps = bps;
    }

    public void setInfluence(String influence) {
        this.influence = influence;
    }

    public void setCreativity(String creativity) {
        this.creativity = creativity;
    }

    public void setThreat(String threat) {
        this.threat = threat;
    }

    public void setIct_index(String ict_index) {
        this.ict_index = ict_index;
    }

    public void setStarts(float starts) {
        this.starts = starts;
    }

    public void setExpected_goals(String expected_goals) {
        this.expected_goals = expected_goals;
    }

    public void setExpected_assists(String expected_assists) {
        this.expected_assists = expected_assists;
    }

    public void setExpected_goal_involvements(String expected_goal_involvements) {
        this.expected_goal_involvements = expected_goal_involvements;
    }

    public void setExpected_goals_conceded(String expected_goals_conceded) {
        this.expected_goals_conceded = expected_goals_conceded;
    }

    public void setInfluence_rank(float influence_rank) {
        this.influence_rank = influence_rank;
    }

    public void setInfluence_rank_type(float influence_rank_type) {
        this.influence_rank_type = influence_rank_type;
    }

    public void setCreativity_rank(float creativity_rank) {
        this.creativity_rank = creativity_rank;
    }

    public void setCreativity_rank_type(float creativity_rank_type) {
        this.creativity_rank_type = creativity_rank_type;
    }

    public void setThreat_rank(float threat_rank) {
        this.threat_rank = threat_rank;
    }

    public void setThreat_rank_type(float threat_rank_type) {
        this.threat_rank_type = threat_rank_type;
    }

    public void setIct_index_rank(float ict_index_rank) {
        this.ict_index_rank = ict_index_rank;
    }

    public void setIct_index_rank_type(float ict_index_rank_type) {
        this.ict_index_rank_type = ict_index_rank_type;
    }

    public void setCorners_and_indirect_freekicks_order(String corners_and_indirect_freekicks_order) {
        this.corners_and_indirect_freekicks_order = corners_and_indirect_freekicks_order;
    }

    public void setCorners_and_indirect_freekicks_text(String corners_and_indirect_freekicks_text) {
        this.corners_and_indirect_freekicks_text = corners_and_indirect_freekicks_text;
    }

    public void setDirect_freekicks_order(String direct_freekicks_order) {
        this.direct_freekicks_order = direct_freekicks_order;
    }

    public void setDirect_freekicks_text(String direct_freekicks_text) {
        this.direct_freekicks_text = direct_freekicks_text;
    }

    public void setPenalties_order(String penalties_order) {
        this.penalties_order = penalties_order;
    }

    public void setPenalties_text(String penalties_text) {
        this.penalties_text = penalties_text;
    }

    public void setExpected_goals_per_90(float expected_goals_per_90) {
        this.expected_goals_per_90 = expected_goals_per_90;
    }

    public void setSaves_per_90(float saves_per_90) {
        this.saves_per_90 = saves_per_90;
    }

    public void setExpected_assists_per_90(float expected_assists_per_90) {
        this.expected_assists_per_90 = expected_assists_per_90;
    }

    public void setExpected_goal_involvements_per_90(float expected_goal_involvements_per_90) {
        this.expected_goal_involvements_per_90 = expected_goal_involvements_per_90;
    }

    public void setExpected_goals_conceded_per_90(float expected_goals_conceded_per_90) {
        this.expected_goals_conceded_per_90 = expected_goals_conceded_per_90;
    }

    public void setGoals_conceded_per_90(float goals_conceded_per_90) {
        this.goals_conceded_per_90 = goals_conceded_per_90;
    }

    public void setNow_cost_rank(float now_cost_rank) {
        this.now_cost_rank = now_cost_rank;
    }

    public void setNow_cost_rank_type(float now_cost_rank_type) {
        this.now_cost_rank_type = now_cost_rank_type;
    }

    public void setForm_rank(float form_rank) {
        this.form_rank = form_rank;
    }

    public void setForm_rank_type(float form_rank_type) {
        this.form_rank_type = form_rank_type;
    }

    public void setPoints_per_game_rank(float points_per_game_rank) {
        this.points_per_game_rank = points_per_game_rank;
    }

    public void setPoints_per_game_rank_type(float points_per_game_rank_type) {
        this.points_per_game_rank_type = points_per_game_rank_type;
    }

    public void setSelected_rank(float selected_rank) {
        this.selected_rank = selected_rank;
    }

    public void setSelected_rank_type(float selected_rank_type) {
        this.selected_rank_type = selected_rank_type;
    }

    public void setStarts_per_90(float starts_per_90) {
        this.starts_per_90 = starts_per_90;
    }

    public void setClean_sheets_per_90(float clean_sheets_per_90) {
        this.clean_sheets_per_90 = clean_sheets_per_90;
    }
}
class Teams {
    private float code;
    private float draw;
    private String form = null;
    private float id;
    private float loss;
    private String name;
    private float played;
    private float points;
    private float position;
    private String short_name;
    private float strength;
    private String team_division = null;
    private boolean unavailable;
    private float win;
    private float strength_overall_home;
    private float strength_overall_away;
    private float strength_attack_home;
    private float strength_attack_away;
    private float strength_defence_home;
    private float strength_defence_away;
    private float pulse_id;


    // Getter Methods

    public float getCode() {
        return code;
    }

    public float getDraw() {
        return draw;
    }

    public String getForm() {
        return form;
    }

    public float getId() {
        return id;
    }

    public float getLoss() {
        return loss;
    }

    public String getName() {
        return name;
    }

    public float getPlayed() {
        return played;
    }

    public float getPoints() {
        return points;
    }

    public float getPosition() {
        return position;
    }

    public String getShort_name() {
        return short_name;
    }

    public float getStrength() {
        return strength;
    }

    public String getTeam_division() {
        return team_division;
    }

    public boolean getUnavailable() {
        return unavailable;
    }

    public float getWin() {
        return win;
    }

    public float getStrength_overall_home() {
        return strength_overall_home;
    }

    public float getStrength_overall_away() {
        return strength_overall_away;
    }

    public float getStrength_attack_home() {
        return strength_attack_home;
    }

    public float getStrength_attack_away() {
        return strength_attack_away;
    }

    public float getStrength_defence_home() {
        return strength_defence_home;
    }

    public float getStrength_defence_away() {
        return strength_defence_away;
    }

    public float getPulse_id() {
        return pulse_id;
    }

    // Setter Methods

    public void setCode(float code) {
        this.code = code;
    }

    public void setDraw(float draw) {
        this.draw = draw;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setLoss(float loss) {
        this.loss = loss;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayed(float played) {
        this.played = played;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public void setPosition(float position) {
        this.position = position;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public void setTeam_division(String team_division) {
        this.team_division = team_division;
    }

    public void setUnavailable(boolean unavailable) {
        this.unavailable = unavailable;
    }

    public void setWin(float win) {
        this.win = win;
    }

    public void setStrength_overall_home(float strength_overall_home) {
        this.strength_overall_home = strength_overall_home;
    }

    public void setStrength_overall_away(float strength_overall_away) {
        this.strength_overall_away = strength_overall_away;
    }

    public void setStrength_attack_home(float strength_attack_home) {
        this.strength_attack_home = strength_attack_home;
    }

    public void setStrength_attack_away(float strength_attack_away) {
        this.strength_attack_away = strength_attack_away;
    }

    public void setStrength_defence_home(float strength_defence_home) {
        this.strength_defence_home = strength_defence_home;
    }

    public void setStrength_defence_away(float strength_defence_away) {
        this.strength_defence_away = strength_defence_away;
    }

    public void setPulse_id(float pulse_id) {
        this.pulse_id = pulse_id;
    }
}

class Phase {
    private float id;
    private String name;
    private float start_event;
    private float stop_event;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getStart_event() {
        return start_event;
    }

    public float getStop_event() {
        return stop_event;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart_event(float start_event) {
        this.start_event = start_event;
    }

    public void setStop_event(float stop_event) {
        this.stop_event = stop_event;
    }
}