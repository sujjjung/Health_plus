package com.example.djsu;

public class User {
    public String getId, getState;
    private String profile,Date,FoodName,EatingTime;
    static private String Id;
    static private String Name;
    static private String State;
    static private String Profile;
    static private int CurrentSteps;
    int FoodCode;

    public User() {}

    public User(String date, String foodName,String eatingTime) {
        Date = date;
        FoodName = foodName;
        EatingTime = eatingTime;
    }

    public String getEatingTime() {
        return EatingTime;
    }

    public void setEatingTime(String eatingTime) {
        this.EatingTime = eatingTime;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
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

    public static String getProfile() { return Profile; }

    public static void setProfile(String profile) { Profile = profile; }

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

    public int getCurrentSteps() {
        return CurrentSteps;
    }

    public void setCurrentSteps(int currentSteps) {
        CurrentSteps = currentSteps;
    }

}
