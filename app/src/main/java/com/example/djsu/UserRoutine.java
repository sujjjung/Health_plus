package com.example.djsu;

import java.util.ArrayList;

public class UserRoutine {

    static private ArrayList<UserRoutine> routineArrayList = new ArrayList<>();
    private String RoutineName;
    private String ExCode,ExPart,exerciseName;


    public UserRoutine() {

    }
    public void AddUserRoutine (String routineName, String exCode,String exPart, String exerciseName) {
        RoutineName = routineName;
        ExCode = exCode;
        ExPart = exPart;
        this.exerciseName = exerciseName;
    }
    public UserRoutine(String routineName, String exCode,String exPart, String exerciseName) {
        RoutineName = routineName;
        ExCode = exCode;
        ExPart = exPart;
        this.exerciseName = exerciseName;
    }

    public static ArrayList<UserRoutine> getRoutineArrayList() {
        return routineArrayList;
    }

    public static void setRoutineArrayList(ArrayList<UserRoutine> routineArrayList) {
        UserRoutine.routineArrayList = routineArrayList;
    }

    public String getRoutineName() {
        return RoutineName;
    }

    public void setRoutineName(String routineName) {
        RoutineName = routineName;
    }

    public String getExCode() {
        return ExCode;
    }

    public void setExCode(String exCode) {
        ExCode = exCode;
    }

    public String getExPart() {
        return ExPart;
    }

    public void setExPart(String exPart) {
        ExPart = exPart;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

}

