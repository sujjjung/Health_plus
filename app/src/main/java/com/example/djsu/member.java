package com.example.djsu;

public class member {
    private String Id;
    private String Password;
    private String Age;
    private String Name;

    private String Profile;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    private String State;

    public member() {
    }

    public member(String id, String password, String age, String name, String profile, String state) {
        Id = id;
        Password = password;
        Age = age;
        Name = name;
        Profile = profile;
        State = state;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }
}