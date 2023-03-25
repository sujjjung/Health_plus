package com.example.djsu;

public class Food {
    private int FoodCood;
    private String profile;
    private String FoodName;
    private float FoodKcal;
    private float FoodCarbohydrate;
    private float FoodProtein;
    private float FoodFat;
    private float FoodSodium;
    private float FoodSugar;
    private float FoodKg;
    private String writeDate;

    public Food(int foodCood, String foodName, float foodKcal, float foodCarbohydrate, float foodProtein, float foodFat, float foodSodium, float foodSugar, float foodKg) {
        FoodCood = foodCood;
        FoodName = foodName;
        FoodKcal = foodKcal;
        FoodCarbohydrate = foodCarbohydrate;
        FoodProtein = foodProtein;
        FoodFat = foodFat;
        FoodSodium = foodSodium;
        FoodSugar = foodSugar;
        FoodKg = foodKg;
    }


    public Food(String foodName) {
        FoodName = foodName;
    }
    public Food() {

    }

    public int getFoodCood() {
        return FoodCood;
    }

    public void setFoodCood(int foodCood) {
        FoodCood = foodCood;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public float getFoodKcal() {
        return FoodKcal;
    }

    public void setFoodKcal(float foodKcal) {
        FoodKcal = foodKcal;
    }

    public float getFoodCarbohydrate() {
        return FoodCarbohydrate;
    }

    public void setFoodCarbohydrate(float foodCarbohydrate) {
        FoodCarbohydrate = foodCarbohydrate;
    }

    public float getFoodProtein() {
        return FoodProtein;
    }

    public void setFoodProtein(float foodProtein) {
        FoodProtein = foodProtein;
    }

    public float getFoodFat() {
        return FoodFat;
    }

    public void setFoodFat(float foodFat) {
        FoodFat = foodFat;
    }

    public float getFoodSodium() {
        return FoodSodium;
    }

    public void setFoodSodium(float foodSodium) {
        FoodSodium = foodSodium;
    }

    public float getFoodSugar() {
        return FoodSugar;
    }

    public void setFoodSugar(float foodSugar) {
        FoodSugar = foodSugar;
    }

    public float getFoodKg() {
        return FoodKg;
    }

    public void setFoodKg(float foodKg) {
        FoodKg = foodKg;
    }

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

}

