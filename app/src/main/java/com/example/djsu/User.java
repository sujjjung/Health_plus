package com.example.djsu;

import java.net.IDN;

public class User {
    private String Date,FoodName,EatingTime,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
    static private String Id, Name, State, Profile, Age, Password;
    static private int CurrentSteps;
    int FcCode,quantity;
    static private String Water;
    static private String WalkGoal;

    public User() {}

    public String getFoodKcal() {
        return FoodKcal;
    }

    public void setFoodKcal(String foodKcal) {
        FoodKcal = foodKcal;
    }

    public String getFoodCarbohydrate() {
        return FoodCarbohydrate;
    }

    public void setFoodCarbohydrate(String foodCarbohydrate) {
        FoodCarbohydrate = foodCarbohydrate;
    }

    public String getFoodProtein() {
        return FoodProtein;
    }

    public void setFoodProtein(String foodProtein) {
        FoodProtein = foodProtein;
    }

    public String getFoodFat() {
        return FoodFat;
    }

    public void setFoodFat(String foodFat) {
        FoodFat = foodFat;
    }

    public String getFoodSodium() {
        return FoodSodium;
    }

    public void setFoodSodium(String foodSodium) {
        FoodSodium = foodSodium;
    }

    public String getFoodSugar() {
        return FoodSugar;
    }

    public void setFoodSugar(String foodSugar) {
        FoodSugar = foodSugar;
    }

    public String getFoodKg() {
        return FoodKg;
    }

    public void setFoodKg(String foodKg) {
        FoodKg = foodKg;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User(String date, String foodName, String eatingTime, String foodKcal, String foodCarbohydrate, String foodProtein, String foodFat, String foodSodium, String foodSugar, String foodKg, int fccode, int quantity) {
        Date = date;
        FoodName = foodName;
        EatingTime = eatingTime;
        FoodKcal = foodKcal;
        FoodCarbohydrate = foodCarbohydrate;
        FoodProtein = foodProtein;
        FoodFat = foodFat;
        FoodSodium = foodSodium;
        FoodSugar = foodSugar;
        FoodKg = foodKg;
        FcCode = fccode;
        this.quantity = quantity;
    }
    public User(String date, String foodName, String eatingTime, String foodKcal, String foodCarbohydrate, String foodProtein, String foodFat, String foodSodium, String foodSugar, String foodKg, int fccode) {
        Date = date;
        FoodName = foodName;
        EatingTime = eatingTime;
        FoodKcal = foodKcal;
        FoodCarbohydrate = foodCarbohydrate;
        FoodProtein = foodProtein;
        FoodFat = foodFat;
        FoodSodium = foodSodium;
        FoodSugar = foodSugar;
        FoodKg = foodKg;
        FcCode = fccode;
    }

    public User(String id, String name, String profile, String age) {
        Id = id;
        Name = name;
        Profile = profile;
        Age = age;
    }

    public User(String date, String foodName, String eatingTime) {
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

    public static String getAge() { return Age; }

    public static void setAge(String age) { Age = age; }

    public static String getPassword() { return Password; }

    public static void setPassword(String password) { Password = password; }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCurrentSteps() {
        return CurrentSteps;
    }

    public void setCurrentSteps(int currentSteps) {
        CurrentSteps = currentSteps;
    }

    public int getFcCode() {
        return FcCode;
    }

    public void setFcCode(int fcCode) {
        FcCode = fcCode;
    }

    public static String getWater() { return Water; }

    public static void setWater(String water) { Water = water; }

    public static String getwalkGoal() { return WalkGoal; }

    public static void setwalk_goal(String walkGoal) { WalkGoal = walkGoal; }

}
