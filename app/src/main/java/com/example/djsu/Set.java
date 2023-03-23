package com.example.djsu;

public class Set {
    static int SetNumber,Calorie,Weight;

    public Set(int setNumber, int calorie, int weight) {
        SetNumber = setNumber;
        Calorie = calorie;
        Weight = weight;
    }

    public Set() {
    }

    public int getSetNumber() {
        return SetNumber;
    }

    public void setSetNumber(int setNumber) {
        SetNumber = setNumber;
    }

    public int getCalorie() {
        return Calorie;
    }

    public void setCalorie(int calorie) {
        Calorie = calorie;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }
}
