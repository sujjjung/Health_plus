package com.example.djsu;

public class Battle_Results {
    private String userId,weight;
    private float Kcal,Time;

    private int FoodKcal;

    public Battle_Results() {
    }

    public Battle_Results(String userId, float kcal, float time) {
        this.userId = userId;
        Kcal = kcal;
        Time = time;
    }

    public Battle_Results(String userId, String weight) {
        this.userId = userId;
        this.weight = weight;
    }

    public Battle_Results(String userId, int FoodKcal) {
        this.userId = userId;
        this.FoodKcal = FoodKcal;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public float getKcal() {
        return Kcal;
    }

    public void setKcal(int kcal) {
        Kcal = kcal;
    }

    public float getTime() {
        return Time;
    }

    public void setTime(int time) {
        Time = time;
    }

    public int getFoodKcal() {
        return FoodKcal;
    }

    public void setFoodKcal(int foodKcal) {
        FoodKcal = foodKcal;
    }
}
