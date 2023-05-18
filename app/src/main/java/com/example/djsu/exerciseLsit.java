package com.example.djsu;

public class exerciseLsit {
    private String profile;
    private String ExCode,ExPart,exerciseName;
    private String exerciseExplanation;
    private String exerciseCalorie;
    private String exerciseUnit;

    public exerciseLsit(String exCode, String exPart, String exerciseName, String exerciseExplanation, String exerciseCalorie, String exerciseUnit) {
        ExCode = exCode;
        ExPart = exPart;
        this.exerciseName = exerciseName;
        this.exerciseExplanation = exerciseExplanation;
        this.exerciseCalorie = exerciseCalorie;
        this.exerciseUnit = exerciseUnit;
    }

    public exerciseLsit(String exerciseName, String exerciseExplanation, String exerciseCalorie, String exerciseUnit) {
        this.exerciseName = exerciseName;
        this.exerciseExplanation = exerciseExplanation;
        this.exerciseCalorie = exerciseCalorie;
        this.exerciseUnit = exerciseUnit;
    }

    public exerciseLsit(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public  exerciseLsit(){};
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseExplanation() {
        return exerciseExplanation;
    }

    public void setExerciseExplanation(String exerciseExplanation) {
        this.exerciseExplanation = exerciseExplanation;
    }

    public String getExerciseCalorie() {
        return exerciseCalorie;
    }

    public void setExerciseCalorie(String exerciseCalorie) {
        this.exerciseCalorie = exerciseCalorie;
    }

    public String getExerciseUnit() {
        return exerciseUnit;
    }

    public void setExerciseUnit(String exerciseUnit) {
        this.exerciseUnit = exerciseUnit;
    }


    public String getExPart() {
        return ExPart;
    }

    public void setExPart(String exPart) {
        ExPart = exPart;
    }

    public String getExCode() {
        return ExCode;
    }

    public void setExCode(String exCode) {
        ExCode = exCode;
    }
}
