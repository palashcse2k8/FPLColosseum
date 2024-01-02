package com.infotech.fplcolosseum.features.login.models;

public class Player {
    private String date_of_birth;
    private boolean dirty;
    private String first_name;
    private String gender;
    private long id;
    private String last_name;
    private long region;
    private String email;
    private long entry;
    private boolean entry_email;
    private String entry_language = null;


    // Getter Methods

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public boolean getDirty() {
        return dirty;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getGender() {
        return gender;
    }

    public long getId() {
        return id;
    }

    public String getLast_name() {
        return last_name;
    }

    public long getRegion() {
        return region;
    }

    public String getEmail() {
        return email;
    }

    public long getEntry() {
        return entry;
    }

    public boolean getEntry_email() {
        return entry_email;
    }

    public String getEntry_language() {
        return entry_language;
    }

    // Setter Methods

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setRegion(long region) {
        this.region = region;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEntry(long entry) {
        this.entry = (long) entry;
    }

    public void setEntry_email(boolean entry_email) {
        this.entry_email = entry_email;
    }

    public void setEntry_language(String entry_language) {
        this.entry_language = entry_language;
    }
}
