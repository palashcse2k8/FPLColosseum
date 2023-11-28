package com.infotech.fplcolosseum.features.login.models;

//public class UserModel {
//    public String userName;
//    public String userFullName;
//    public String managerID;
//    public String sessionToken;
//
//    public UserModel(String userName, String userFullName, String managerID, String sessionToken) {
//        this.userName = userName;
//        this.userFullName = userFullName;
//        this.managerID = managerID;
//        this.sessionToken = sessionToken;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getUserFullName() {
//        return userFullName;
//    }
//
//    public void setUserFullName(String userFullName) {
//        this.userFullName = userFullName;
//    }
//
//    public String getManagerID() {
//        return managerID;
//    }
//
//    public void setManagerID(String managerID) {
//        this.managerID = managerID;
//    }
//
//    public String getSessionToken() {
//        return sessionToken;
//    }
//
//    public void setSessionToken(String sessionToken) {
//        this.sessionToken = sessionToken;
//    }
//}

import java.util.ArrayList;

public class UserResponseModel {
    Player PlayerObject;
    ArrayList< Object > watched = new ArrayList < Object > ();

    // Getter Methods

    public Player getPlayer() {
        return PlayerObject;
    }

    // Setter Methods

    public void setPlayer(Player playerObject) {
        this.PlayerObject = playerObject;
    }
}
class Player {
    private String date_of_birth;
    private boolean dirty;
    private String first_name;
    private String gender;
    private float id;
    private String last_name;
    private float region;
    private String email;
    private float entry;
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

    public float getId() {
        return id;
    }

    public String getLast_name() {
        return last_name;
    }

    public float getRegion() {
        return region;
    }

    public String getEmail() {
        return email;
    }

    public float getEntry() {
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

    public void setId(float id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setRegion(float region) {
        this.region = region;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEntry(float entry) {
        this.entry = entry;
    }

    public void setEntry_email(boolean entry_email) {
        this.entry_email = entry_email;
    }

    public void setEntry_language(String entry_language) {
        this.entry_language = entry_language;
    }
}
