package com.example.djsu;

public class exrecode {
    String setNumber, Number,unit;
    static boolean isSelected;

    public exrecode(String setNumber, String unit,String number) {
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
