package com.example.djsu;

public class User {
    private String profile;
    static private String Id;
    static private String Name;

    public User() {}

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public static String getId() {
        return Id;
    }

    public static void setId(String id) {
        Id = id;
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }
}
