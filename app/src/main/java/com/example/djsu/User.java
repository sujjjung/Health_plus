package com.example.djsu;

public class User {
    public String getId;
    private String profile,Date;
    static private String Id;
    static private String Name;
    int FoodCode;

    public User() {}

    public User(String date, int foodCode) {
        Date = date;
        FoodCode = foodCode;
    }

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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getFoodCode() {
        return FoodCode;
    }

    public void setFoodCode(int foodCode) {
        FoodCode = foodCode;
    }
}
