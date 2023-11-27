package com.infotech.fplcolosseum.login.models;

public class UserModel {
    public String userName;
    public String userFullName;
    public String managerID;
    public String sessionToken;

    public UserModel(String userName, String userFullName, String managerID, String sessionToken) {
        this.userName = userName;
        this.userFullName = userFullName;
        this.managerID = managerID;
        this.sessionToken = sessionToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
