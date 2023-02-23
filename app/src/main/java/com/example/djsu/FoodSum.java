package com.example.djsu;

public class FoodSum {
    static float sumKcal = 0,sumCarbohydrate= 0,sumProtein= 0,sumFat= 0,sumSodium= 0,sumSugar= 0,sumKg= 0;

    static public float sumKcal(float sumKcal1){
        return sumKcal += sumKcal1;
    }
    public float sumCarbohydrate(float sumCarbohydrate1){
        return this.sumCarbohydrate += sumCarbohydrate1;
    }
    public float sumProtein(float sumProtein1){
        return this.sumProtein += sumProtein1;
    } public float sumFat(float sumFat1){
        return this.sumFat += sumFat1;
    }
    public float sumSodium(float sumSodium1){
        return this.sumSodium += sumSodium1;
    }
    public float sumSugar(float sumSugar1){
        return this.sumSugar += sumSugar1;
    }
    public float sumKg(float sumKg1){
        return this.sumKg += sumKg1;
    }

    public static float getSumKcal() {
        return sumKcal;
    }

    public static void setSumKcal(float sumKcal1) {
        sumKcal = sumKcal1;
    }

    public float getSumCarbohydrate() {
        return sumCarbohydrate;
    }

    public void setSumCarbohydrate(float sumCarbohydrate1) {
        this.sumCarbohydrate = sumCarbohydrate1;
    }

    public float getSumProtein() {
        return sumProtein;
    }

    public void setSumProtein(float sumProtein1) {
        this.sumProtein = sumProtein1;
    }

    public float getSumFat() {
        return sumFat;
    }

    public void setSumFat(float sumFat1) {
        this.sumFat = sumFat1;
    }

    public float getSumSodium() {
        return sumSodium;
    }

    public void setSumSodium(float sumSodium1) {
        this.sumSodium = sumSodium1;
    }

    public float getSumSugar() {
        return sumSugar;
    }

    public void setSumSugar(float sumSugar1) {
        this.sumSugar = sumSugar1;
    }

    public float getSumKg() {
        return sumKg;
    }

    public void setSumKg(float sumKg1) {
        this.sumKg = sumKg1;
    }
}
