package com.example.djsu;

public class exerciseLsit {
    private String profile;
    private String ExCode,ExPart,exerciseName;
    private String exerciseExplanation;

    public exerciseLsit(String exCode, String exPart, String exerciseName, String exerciseExplanation) {
        ExCode = exCode;
        ExPart = exPart;
        this.exerciseName = exerciseName;
        this.exerciseExplanation = exerciseExplanation;
    }
    public exerciseLsit(String exerciseName, String exPart, String exerciseExplanation) {
        ExPart = exPart;
        this.exerciseName = exerciseName;
        this.exerciseExplanation = exerciseExplanation;
    }

    public exerciseLsit(String exerciseName, String exerciseExplanation) {
        this.exerciseName = exerciseName;
        this.exerciseExplanation = exerciseExplanation;
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
