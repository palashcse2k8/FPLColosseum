package com.infotech.fplcolosseum.features.homepage.models.staticdata;

import java.io.Serializable;

public class PlayersData implements Serializable {
    private Long chance_of_playing_next_round;
    private Long chance_of_playing_this_round;
    private long code;
    private long cost_change_event;
    private long cost_change_event_fall;
    private long cost_change_start;
    private long cost_change_start_fall;
    private long dreamteam_count;
    private long element_type;
    private String ep_next;
    private String ep_this;
    private long event_points;
    private String first_name;
    private String form;
    private long id;
    private boolean in_dreamteam;
    private String news;
    private String news_added;
    private long now_cost;
    private String photo;
    private String points_per_game;
    private String second_name;
    private String selected_by_percent;
    private boolean special;
    private String squad_number = null;
    private String status;
    private long team;
    private long team_code;
    private long total_points;
    private long transfers_in;
    private long transfers_in_event;
    private long transfers_out;
    private long transfers_out_event;
    private String value_form;
    private String value_season;
    private String web_name;
    private long minutes;
    private long goals_scored;
    private long assists;
    private long clean_sheets;
    private long goals_conceded;
    private long own_goals;
    private long penalties_saved;
    private long penalties_missed;
    private long yellow_cards;
    private long red_cards;
    private long saves;
    private long bonus;
    private long bps;
    private String influence;
    private String creativity;
    private String threat;
    private String ict_index;
    private long starts;
    private String expected_goals;
    private String expected_assists;
    private String expected_goal_involvements;
    private String expected_goals_conceded;
    private long influence_rank;
    private long influence_rank_type;
    private long creativity_rank;
    private long creativity_rank_type;
    private long threat_rank;
    private long threat_rank_type;
    private long ict_index_rank;
    private long ict_index_rank_type;
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

    private String team_name_full;
    private String team_name_short;
    private String singular_name;
    private String singular_name_short;

    private long position;
    private long selling_price;
    private long multiplier;
    private long purchase_price;
    private boolean is_captain;
    private boolean is_vice_captain;
    private long substitute_number;

    public long getSubstitute_number() {
        return substitute_number;
    }

    public void setSubstitute_number(long substitute_number) {
        this.substitute_number = substitute_number;
    }

    public PlayersData(PlayersData player) {
        this.chance_of_playing_next_round = player.chance_of_playing_next_round;
        this.chance_of_playing_this_round = player.chance_of_playing_this_round;
        this.code = player.code;
        this.cost_change_event = player.cost_change_event;
        this.cost_change_event_fall = player.cost_change_event_fall;
        this.cost_change_start = player.cost_change_start;
        this.cost_change_start_fall = player.cost_change_start_fall;
        this.dreamteam_count = player.dreamteam_count;
        this.element_type = player.element_type;
        this.ep_next = player.ep_next;
        this.ep_this = player.ep_this;
        this.event_points = player.event_points;
        this.first_name = player.first_name;
        this.form = player.form;
        this.id = player.id;
        this.in_dreamteam = player.in_dreamteam;
        this.news = player.news;
        this.news_added = player.news_added;
        this.now_cost = player.now_cost;
        this.photo = player.photo;
        this.points_per_game = player.points_per_game;
        this.second_name = player.second_name;
        this.selected_by_percent = player.selected_by_percent;
        this.special = player.special;
        this.squad_number = player.squad_number;
        this.status = player.status;
        this.team = player.team;
        this.team_code = player.team_code;
        this.total_points = player.total_points;
        this.transfers_in = player.transfers_in;
        this.transfers_in_event = player.transfers_in_event;
        this.transfers_out = player.transfers_out;
        this.transfers_out_event = player.transfers_out_event;
        this.value_form = player.value_form;
        this.value_season = player.value_season;
        this.web_name = player.web_name;
        this.minutes = player.minutes;
        this.goals_scored = player.goals_scored;
        this.assists = player.assists;
        this.clean_sheets = player.clean_sheets;
        this.goals_conceded = player.goals_conceded;
        this.own_goals = player.own_goals;
        this.penalties_saved = player.penalties_saved;
        this.penalties_missed = player.penalties_missed;
        this.yellow_cards = player.yellow_cards;
        this.red_cards = player.red_cards;
        this.saves = player.saves;
        this.bonus = player.bonus;
        this.bps = player.bps;
        this.influence = player.influence;
        this.creativity = player.creativity;
        this.threat = player.threat;
        this.ict_index = player.ict_index;
        this.starts = player.starts;
        this.expected_goals = player.expected_goals;
        this.expected_assists = player.expected_assists;
        this.expected_goal_involvements = player.expected_goal_involvements;
        this.expected_goals_conceded = player.expected_goals_conceded;
        this.influence_rank = player.influence_rank;
        this.influence_rank_type = player.influence_rank_type;
        this.creativity_rank = player.creativity_rank;
        this.creativity_rank_type = player.creativity_rank_type;
        this.threat_rank = player.threat_rank;
        this.threat_rank_type = player.threat_rank_type;
        this.ict_index_rank = player.ict_index_rank;
        this.ict_index_rank_type = player.ict_index_rank_type;
        this.corners_and_indirect_freekicks_order = player.corners_and_indirect_freekicks_order;
        this.corners_and_indirect_freekicks_text = player.corners_and_indirect_freekicks_text;
        this.direct_freekicks_order = player.direct_freekicks_order;
        this.direct_freekicks_text = player.direct_freekicks_text;
        this.penalties_order = player.penalties_order;
        this.penalties_text = player.penalties_text;
        this.expected_goals_per_90 = player.expected_goals_per_90;
        this.saves_per_90 = player.saves_per_90;
        this.expected_assists_per_90 = player.expected_assists_per_90;
        this.expected_goal_involvements_per_90 = player.expected_goal_involvements_per_90;
        this.expected_goals_conceded_per_90 = player.expected_goals_conceded_per_90;
        this.goals_conceded_per_90 = player.goals_conceded_per_90;
        this.now_cost_rank = player.now_cost_rank;
        this.now_cost_rank_type = player.now_cost_rank_type;
        this.form_rank = player.form_rank;
        this.form_rank_type = player.form_rank_type;
        this.points_per_game_rank = player.points_per_game_rank;
        this.points_per_game_rank_type = player.points_per_game_rank_type;
        this.selected_rank = player.selected_rank;
        this.selected_rank_type = player.selected_rank_type;
        this.starts_per_90 = player.starts_per_90;
        this.clean_sheets_per_90 = player.clean_sheets_per_90;
        this.team_name_full = player.team_name_full;
        this.team_name_short = player.team_name_short;
        this.singular_name = player.singular_name;
        this.singular_name_short = player.singular_name_short;
        this.position = player.position;
        this.selling_price = player.selling_price;
        this.multiplier = player.multiplier;
        this.purchase_price = player.purchase_price;
        this.is_captain = player.is_captain;
        this.is_vice_captain = player.is_vice_captain;
    }

    public PlayersData() {
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(long selling_price) {
        this.selling_price = selling_price;
    }

    public long getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(long multiplier) {
        this.multiplier = multiplier;
    }

    public long getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(long purchase_price) {
        this.purchase_price = purchase_price;
    }

    public boolean isIs_captain() {
        return is_captain;
    }

    public void setIs_captain(boolean is_captain) {
        this.is_captain = is_captain;
    }

    public boolean isIs_vice_captain() {
        return is_vice_captain;
    }

    public void setIs_vice_captain(boolean is_vice_captain) {
        this.is_vice_captain = is_vice_captain;
    }

    public boolean isIn_dreamteam() {
        return in_dreamteam;
    }

    public boolean isSpecial() {
        return special;
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

    public String getTeam_name_full() {
        return team_name_full;
    }

    public void setTeam_name_full(String team_name_full) {
        this.team_name_full = team_name_full;
    }

    public String getTeam_name_short() {
        return team_name_short;
    }

    public void setTeam_name_short(String team_name_short) {
        this.team_name_short = team_name_short;
    }

    public String getSingular_name() {
        return singular_name;
    }

    public void setSingular_name(String singular_name) {
        this.singular_name = singular_name;
    }

    public String getSingular_name_short() {
        return singular_name_short;
    }

    public void setSingular_name_short(String singular_name_short) {
        this.singular_name_short = singular_name_short;
    }
// Getter Methods

    public Long getChance_of_playing_next_round() {
        return chance_of_playing_next_round;
    }

    public Long getChance_of_playing_this_round() {
        return chance_of_playing_this_round;
    }

    public long getCode() {
        return code;
    }

    public long getCost_change_event() {
        return cost_change_event;
    }

    public long getCost_change_event_fall() {
        return cost_change_event_fall;
    }

    public long getCost_change_start() {
        return cost_change_start;
    }

    public long getCost_change_start_fall() {
        return cost_change_start_fall;
    }

    public long getDreamteam_count() {
        return dreamteam_count;
    }

    public long getElement_type() {
        return element_type;
    }

    public String getEp_next() {
        return ep_next;
    }

    public String getEp_this() {
        return ep_this;
    }

    public long getEvent_points() {
        return event_points;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getForm() {
        return form;
    }

    public long getId() {
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

    public long getNow_cost() {
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

    public long getTeam() {
        return team;
    }

    public long getTeam_code() {
        return team_code;
    }

    public long getTotal_points() {
        return total_points;
    }

    public long getTransfers_in() {
        return transfers_in;
    }

    public long getTransfers_in_event() {
        return transfers_in_event;
    }

    public long getTransfers_out() {
        return transfers_out;
    }

    public long getTransfers_out_event() {
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

    public long getMinutes() {
        return minutes;
    }

    public long getGoals_scored() {
        return goals_scored;
    }

    public long getAssists() {
        return assists;
    }

    public long getClean_sheets() {
        return clean_sheets;
    }

    public long getGoals_conceded() {
        return goals_conceded;
    }

    public long getOwn_goals() {
        return own_goals;
    }

    public long getPenalties_saved() {
        return penalties_saved;
    }

    public long getPenalties_missed() {
        return penalties_missed;
    }

    public long getYellow_cards() {
        return yellow_cards;
    }

    public long getRed_cards() {
        return red_cards;
    }

    public long getSaves() {
        return saves;
    }

    public long getBonus() {
        return bonus;
    }

    public long getBps() {
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

    public long getStarts() {
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

    public long getInfluence_rank() {
        return influence_rank;
    }

    public long getInfluence_rank_type() {
        return influence_rank_type;
    }

    public long getCreativity_rank() {
        return creativity_rank;
    }

    public long getCreativity_rank_type() {
        return creativity_rank_type;
    }

    public long getThreat_rank() {
        return threat_rank;
    }

    public long getThreat_rank_type() {
        return threat_rank_type;
    }

    public long getIct_index_rank() {
        return ict_index_rank;
    }

    public long getIct_index_rank_type() {
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

    public void setChance_of_playing_next_round(long chance_of_playing_next_round) {
        this.chance_of_playing_next_round = chance_of_playing_next_round;
    }

    public void setChance_of_playing_this_round(Long chance_of_playing_this_round) {
        this.chance_of_playing_this_round = chance_of_playing_this_round;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setCost_change_event(long cost_change_event) {
        this.cost_change_event = cost_change_event;
    }

    public void setCost_change_event_fall(long cost_change_event_fall) {
        this.cost_change_event_fall = cost_change_event_fall;
    }

    public void setCost_change_start(long cost_change_start) {
        this.cost_change_start = cost_change_start;
    }

    public void setCost_change_start_fall(long cost_change_start_fall) {
        this.cost_change_start_fall = cost_change_start_fall;
    }

    public void setDreamteam_count(long dreamteam_count) {
        this.dreamteam_count = dreamteam_count;
    }

    public void setElement_type(long element_type) {
        this.element_type = element_type;
    }

    public void setEp_next(String ep_next) {
        this.ep_next = ep_next;
    }

    public void setEp_this(String ep_this) {
        this.ep_this = ep_this;
    }

    public void setEvent_points(long event_points) {
        this.event_points = event_points;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setId(long id) {
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

    public void setNow_cost(long now_cost) {
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

    public void setTeam(long team) {
        this.team = team;
    }

    public void setTeam_code(long team_code) {
        this.team_code = team_code;
    }

    public void setTotal_points(long total_points) {
        this.total_points = total_points;
    }

    public void setTransfers_in(long transfers_in) {
        this.transfers_in = transfers_in;
    }

    public void setTransfers_in_event(long transfers_in_event) {
        this.transfers_in_event = transfers_in_event;
    }

    public void setTransfers_out(long transfers_out) {
        this.transfers_out = transfers_out;
    }

    public void setTransfers_out_event(long transfers_out_event) {
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

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public void setGoals_scored(long goals_scored) {
        this.goals_scored = goals_scored;
    }

    public void setAssists(long assists) {
        this.assists = assists;
    }

    public void setClean_sheets(long clean_sheets) {
        this.clean_sheets = clean_sheets;
    }

    public void setGoals_conceded(long goals_conceded) {
        this.goals_conceded = goals_conceded;
    }

    public void setOwn_goals(long own_goals) {
        this.own_goals = own_goals;
    }

    public void setPenalties_saved(long penalties_saved) {
        this.penalties_saved = penalties_saved;
    }

    public void setPenalties_missed(long penalties_missed) {
        this.penalties_missed = penalties_missed;
    }

    public void setYellow_cards(long yellow_cards) {
        this.yellow_cards = yellow_cards;
    }

    public void setRed_cards(long red_cards) {
        this.red_cards = red_cards;
    }

    public void setSaves(long saves) {
        this.saves = saves;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
    }

    public void setBps(long bps) {
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

    public void setStarts(long starts) {
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

    public void setInfluence_rank(long influence_rank) {
        this.influence_rank = influence_rank;
    }

    public void setInfluence_rank_type(long influence_rank_type) {
        this.influence_rank_type = influence_rank_type;
    }

    public void setCreativity_rank(long creativity_rank) {
        this.creativity_rank = creativity_rank;
    }

    public void setCreativity_rank_type(long creativity_rank_type) {
        this.creativity_rank_type = creativity_rank_type;
    }

    public void setThreat_rank(long threat_rank) {
        this.threat_rank = threat_rank;
    }

    public void setThreat_rank_type(long threat_rank_type) {
        this.threat_rank_type = threat_rank_type;
    }

    public void setIct_index_rank(long ict_index_rank) {
        this.ict_index_rank = ict_index_rank;
    }

    public void setIct_index_rank_type(long ict_index_rank_type) {
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

    public void setExpected_goals_per_90(long expected_goals_per_90) {
        this.expected_goals_per_90 = expected_goals_per_90;
    }

    public void setSaves_per_90(long saves_per_90) {
        this.saves_per_90 = saves_per_90;
    }

    public void setExpected_assists_per_90(long expected_assists_per_90) {
        this.expected_assists_per_90 = expected_assists_per_90;
    }

    public void setExpected_goal_involvements_per_90(long expected_goal_involvements_per_90) {
        this.expected_goal_involvements_per_90 = expected_goal_involvements_per_90;
    }

    public void setExpected_goals_conceded_per_90(long expected_goals_conceded_per_90) {
        this.expected_goals_conceded_per_90 = expected_goals_conceded_per_90;
    }

    public void setGoals_conceded_per_90(long goals_conceded_per_90) {
        this.goals_conceded_per_90 = goals_conceded_per_90;
    }

    public void setNow_cost_rank(long now_cost_rank) {
        this.now_cost_rank = now_cost_rank;
    }

    public void setNow_cost_rank_type(long now_cost_rank_type) {
        this.now_cost_rank_type = now_cost_rank_type;
    }

    public void setForm_rank(long form_rank) {
        this.form_rank = form_rank;
    }

    public void setForm_rank_type(long form_rank_type) {
        this.form_rank_type = form_rank_type;
    }

    public void setPoints_per_game_rank(long points_per_game_rank) {
        this.points_per_game_rank = points_per_game_rank;
    }

    public void setPoints_per_game_rank_type(long points_per_game_rank_type) {
        this.points_per_game_rank_type = points_per_game_rank_type;
    }

    public void setSelected_rank(long selected_rank) {
        this.selected_rank = selected_rank;
    }

    public void setSelected_rank_type(long selected_rank_type) {
        this.selected_rank_type = selected_rank_type;
    }

    public void setStarts_per_90(long starts_per_90) {
        this.starts_per_90 = starts_per_90;
    }

    public void setClean_sheets_per_90(long clean_sheets_per_90) {
        this.clean_sheets_per_90 = clean_sheets_per_90;
    }

    @Override
    public String toString() {
        return "PlayersData{" +
                "chance_of_playing_next_round=" + chance_of_playing_next_round +
                ", chance_of_playing_this_round=" + chance_of_playing_this_round +
                ", code=" + code +
                ", cost_change_event=" + cost_change_event +
                ", cost_change_event_fall=" + cost_change_event_fall +
                ", cost_change_start=" + cost_change_start +
                ", cost_change_start_fall=" + cost_change_start_fall +
                ", dreamteam_count=" + dreamteam_count +
                ", element_type=" + element_type +
                ", ep_next='" + ep_next + '\'' +
                ", ep_this='" + ep_this + '\'' +
                ", event_points=" + event_points +
                ", first_name='" + first_name + '\'' +
                ", form='" + form + '\'' +
                ", id=" + id +
                ", in_dreamteam=" + in_dreamteam +
                ", news='" + news + '\'' +
                ", news_added='" + news_added + '\'' +
                ", now_cost=" + now_cost +
                ", photo='" + photo + '\'' +
                ", points_per_game='" + points_per_game + '\'' +
                ", second_name='" + second_name + '\'' +
                ", selected_by_percent='" + selected_by_percent + '\'' +
                ", special=" + special +
                ", squad_number='" + squad_number + '\'' +
                ", status='" + status + '\'' +
                ", team=" + team +
                ", team_code=" + team_code +
                ", total_points=" + total_points +
                ", transfers_in=" + transfers_in +
                ", transfers_in_event=" + transfers_in_event +
                ", transfers_out=" + transfers_out +
                ", transfers_out_event=" + transfers_out_event +
                ", value_form='" + value_form + '\'' +
                ", value_season='" + value_season + '\'' +
                ", web_name='" + web_name + '\'' +
                ", minutes=" + minutes +
                ", goals_scored=" + goals_scored +
                ", assists=" + assists +
                ", clean_sheets=" + clean_sheets +
                ", goals_conceded=" + goals_conceded +
                ", own_goals=" + own_goals +
                ", penalties_saved=" + penalties_saved +
                ", penalties_missed=" + penalties_missed +
                ", yellow_cards=" + yellow_cards +
                ", red_cards=" + red_cards +
                ", saves=" + saves +
                ", bonus=" + bonus +
                ", bps=" + bps +
                ", influence='" + influence + '\'' +
                ", creativity='" + creativity + '\'' +
                ", threat='" + threat + '\'' +
                ", ict_index='" + ict_index + '\'' +
                ", starts=" + starts +
                ", expected_goals='" + expected_goals + '\'' +
                ", expected_assists='" + expected_assists + '\'' +
                ", expected_goal_involvements='" + expected_goal_involvements + '\'' +
                ", expected_goals_conceded='" + expected_goals_conceded + '\'' +
                ", influence_rank=" + influence_rank +
                ", influence_rank_type=" + influence_rank_type +
                ", creativity_rank=" + creativity_rank +
                ", creativity_rank_type=" + creativity_rank_type +
                ", threat_rank=" + threat_rank +
                ", threat_rank_type=" + threat_rank_type +
                ", ict_index_rank=" + ict_index_rank +
                ", ict_index_rank_type=" + ict_index_rank_type +
                ", corners_and_indirect_freekicks_order='" + corners_and_indirect_freekicks_order + '\'' +
                ", corners_and_indirect_freekicks_text='" + corners_and_indirect_freekicks_text + '\'' +
                ", direct_freekicks_order='" + direct_freekicks_order + '\'' +
                ", direct_freekicks_text='" + direct_freekicks_text + '\'' +
                ", penalties_order='" + penalties_order + '\'' +
                ", penalties_text='" + penalties_text + '\'' +
                ", expected_goals_per_90=" + expected_goals_per_90 +
                ", saves_per_90=" + saves_per_90 +
                ", expected_assists_per_90=" + expected_assists_per_90 +
                ", expected_goal_involvements_per_90=" + expected_goal_involvements_per_90 +
                ", expected_goals_conceded_per_90=" + expected_goals_conceded_per_90 +
                ", goals_conceded_per_90=" + goals_conceded_per_90 +
                ", now_cost_rank=" + now_cost_rank +
                ", now_cost_rank_type=" + now_cost_rank_type +
                ", form_rank=" + form_rank +
                ", form_rank_type=" + form_rank_type +
                ", points_per_game_rank=" + points_per_game_rank +
                ", points_per_game_rank_type=" + points_per_game_rank_type +
                ", selected_rank=" + selected_rank +
                ", selected_rank_type=" + selected_rank_type +
                ", starts_per_90=" + starts_per_90 +
                ", clean_sheets_per_90=" + clean_sheets_per_90 +
                ", team_name_full='" + team_name_full + '\'' +
                ", team_name_short='" + team_name_short + '\'' +
                ", singular_name='" + singular_name + '\'' +
                ", singular_name_short='" + singular_name_short + '\'' +
                ", position=" + position +
                ", selling_price=" + selling_price +
                ", multiplier=" + multiplier +
                ", purchase_price=" + purchase_price +
                ", is_captain=" + is_captain +
                ", is_vice_captain=" + is_vice_captain +
                '}';
    }
}
