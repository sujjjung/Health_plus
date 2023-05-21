package com.example.djsu;

import static com.example.djsu.exrecode.isSelected;

import java.util.ArrayList;

public class Set {
    private String setNumber, Number,unit,time;
    static private ArrayList<Set> setArrayList = new ArrayList<>();
    public Set() {
    }
    public Set( ArrayList<Set> setArrayList) {
        this.setArrayList = setArrayList;
    }
    public Set(String setNumber, String number, String unit, String time) {
        this.setNumber = setNumber;
        Number = number;
        this.unit = unit;
        this.time = time;
    }
    public void setList(Set set) {
        setArrayList.add(set);
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
