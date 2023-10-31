package com.example.djsu.admin;

public class adminUser {
    private String profile;
    private String Name,UserID,userPassword,UserAge,State;

    public adminUser() {}

    public adminUser(String profile, String name, String userID, String userPassword, String userAge, String state) {
        this.profile = profile;
        Name = name;
        UserID = userID;
        this.userPassword = userPassword;
        UserAge = userAge;
        State = state;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserAge() {
        return UserAge;
    }

    public void setUserAge(String userAge) {
        UserAge = userAge;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
