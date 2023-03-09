package com.example.djsu;

public class User {
    public String getId, getState;
    private String profile,Date,FoodName;
    static private String Id;
    static private String Name;
    static private String State;
    int FoodCode;

    public User() {}

    public User(String date, String foodName) {
        Date = date;
        FoodName = foodName;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
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

    public static String getState() { return State; }

    public static void setState(String state) { State = state; }

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
