package com.infotech.fplcolosseum.features.homepage.models.staticdata;

import com.google.gson.annotations.SerializedName;
import com.infotech.fplcolosseum.features.homepage.models.staticdata.Top_element_info;

import java.util.ArrayList;

public class GameWeekEvent {
    private long id;
    private String name;
    private String deadline_time;
    private long average_entry_score;
    private boolean finished;
    private boolean data_checked;
    private long highest_scoring_entry;
    private long deadline_time_epoch;
    private long deadline_time_game_offset;
    private long highest_score;
    private boolean is_previous;
    private boolean is_current;
    private boolean is_next;
    private boolean cup_leagues_created;
    private boolean h2h_ko_matches_created;
    @SerializedName("chip_plays")
    ArrayList<ChipsPlayedInfo> chip_plays = new ArrayList<ChipsPlayedInfo>();
    private long most_selected;
    private long most_transferred_in;
    private long top_element;
    Top_element_info Top_element_infoObject;
    private long transfers_made;
    private long most_captained;
    private long most_vice_captained;


    // Getter Methods

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeadline_time() {
        return deadline_time;
    }

    public long getAverage_entry_score() {
        return average_entry_score;
    }

    public boolean getFinished() {
        return finished;
    }

    public boolean getData_checked() {
        return data_checked;
    }

    public long getHighest_scoring_entry() {
        return highest_scoring_entry;
    }

    public long getDeadline_time_epoch() {
        return deadline_time_epoch;
    }

    public long getDeadline_time_game_offset() {
        return deadline_time_game_offset;
    }

    public long getHighest_score() {
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

    public long getMost_selected() {
        return most_selected;
    }

    public long getMost_transferred_in() {
        return most_transferred_in;
    }

    public long getTop_element() {
        return top_element;
    }

    public Top_element_info getTop_element_info() {
        return Top_element_infoObject;
    }

    public long getTransfers_made() {
        return transfers_made;
    }

    public long getMost_captained() {
        return most_captained;
    }

    public long getMost_vice_captained() {
        return most_vice_captained;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeadline_time(String deadline_time) {
        this.deadline_time = deadline_time;
    }

    public void setAverage_entry_score(long average_entry_score) {
        this.average_entry_score = average_entry_score;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setData_checked(boolean data_checked) {
        this.data_checked = data_checked;
    }

    public void setHighest_scoring_entry(long highest_scoring_entry) {
        this.highest_scoring_entry = highest_scoring_entry;
    }

    public void setDeadline_time_epoch(long deadline_time_epoch) {
        this.deadline_time_epoch = deadline_time_epoch;
    }

    public void setDeadline_time_game_offset(long deadline_time_game_offset) {
        this.deadline_time_game_offset = deadline_time_game_offset;
    }

    public void setHighest_score(long highest_score) {
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

    public void setMost_selected(long most_selected) {
        this.most_selected = most_selected;
    }

    public void setMost_transferred_in(long most_transferred_in) {
        this.most_transferred_in = most_transferred_in;
    }

    public void setTop_element(long top_element) {
        this.top_element = top_element;
    }

    public void setTop_element_info(Top_element_info top_element_infoObject) {
        this.Top_element_infoObject = top_element_infoObject;
    }

    public void setTransfers_made(long transfers_made) {
        this.transfers_made = transfers_made;
    }

    public void setMost_captained(long most_captained) {
        this.most_captained = most_captained;
    }

    public void setMost_vice_captained(long most_vice_captained) {
        this.most_vice_captained = most_vice_captained;
    }
}
