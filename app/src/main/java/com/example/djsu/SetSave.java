package com.example.djsu;

import java.util.ArrayList;

public class SetSave {
    static private String setNumber, Number,unit;
    int count = 0;
    static private ArrayList<Set> setArrayList = new ArrayList<>();
    public SetSave() {
    }
    public SetSave( ArrayList<Set> setArrayList) {
        this.setArrayList = setArrayList;
    }
    public SetSave(String setNumber, String number, String unit) {
        this.setNumber = setNumber;
        Number = number;
        this.unit = unit;
    }

    public void setList(Set set){
        this.setArrayList.add(count,set);
        count++;
    }

    public String getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(String setNumber) {
        this.setNumber = setNumber;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ArrayList<Set> getSetArrayList() {
        return setArrayList;
    }

    public void setSetArrayList(ArrayList<Set> setArrayList) {
        this.setArrayList = setArrayList;
    }
}

