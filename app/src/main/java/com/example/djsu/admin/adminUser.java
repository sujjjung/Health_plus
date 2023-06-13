package com.example.djsu.admin;

public class adminUser {
    private String profile;
    private String Name,UserID,UserAge;

    public adminUser() {}

    public adminUser(String userID, String profile, String name, String userAge) {
        UserID = userID;
        this.profile = profile;
        Name = name;
        UserAge = userAge;
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
}
