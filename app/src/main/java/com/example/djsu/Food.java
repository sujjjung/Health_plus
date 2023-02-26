package com.example.djsu;

public class Food {
    private String id;
    private String profile;
    private String FoodName;
    private String FoodKcal;
    private String FoodCarbohydrate;
    private String FoodProtein;
    private String FoodFat;
    private String FoodSodium;
    private String FoodSugar;
    private String FoodKg;
    private String writeDate;

    public Food(String foodName) {
        FoodName = foodName;
    }
    public Food() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(String writeDate) {
        this.writeDate = writeDate;
    }

}

