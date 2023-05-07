package com.example.djsu;

public class Set {
    static private String setNumber, Number,unit;

    public Set() {
    }

    public Set(String setNumber, String unit, String number) {
        this.setNumber = setNumber;
        Number = number;
        this.unit = unit;
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

}
