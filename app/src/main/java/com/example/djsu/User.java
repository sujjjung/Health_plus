package com.example.djsu;

import java.io.Serializable;

public class User implements Serializable {
    // 음식관련 변수
    private String Date,FoodName,EatingTime,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;

    int FcCode,EcCode,quantity;
    // 운동관련 변수

    static private String Nondisclosure;

    private String ExerciseName,ExercisePart,ExercisesetNumber, ExerciseNumber,Exerciseunit,Time,ExerciseCode;
    // 루틴관련 변수
    private String RoutineName, friend;
    // 사용자관련 변수
    static private String Id, Name, State, Profile, Age, Password;

    static private int CurrentSteps;
    // 물관련 변수
    static private String Water;
    // 만보기관련 변수
    static private String WalkGoal;
    // 커뮤니티관련 변수
    private String postid,content,image , postdate;

    public User(String postid,String content,String image,String postdate) {
        this.postid = postid;
        this.content = content;
        this.image = image;
        this.postdate = postdate;
    }


    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getExerciseCode() {
        return ExerciseCode;
    }

    public void setExerciseCode(String exerciseCode) {
        ExerciseCode = exerciseCode;
    }

    public User() {}

    public User(String Friend) {this.friend = Friend; }

    public void AddRoutine(String RoutineName,String ExerciseCode,String ExerciseName,String exercisePart) {
        this.RoutineName = RoutineName;
        this.ExerciseCode = ExerciseCode;
        this.ExerciseName = ExerciseName;
        ExercisePart = exercisePart;
    }

    public User(String date, String exerciseName, String exercisePart, String exercisesetNumber, String exerciseNumber, String exerciseunit, String time,int EcCode) {
        Date = date;
        ExerciseName = exerciseName;
        ExercisePart = exercisePart;
        ExercisesetNumber = exercisesetNumber;
        ExerciseNumber = exerciseNumber;
        Exerciseunit = exerciseunit;
        Time = time;
        this.EcCode = EcCode;
    }

    public String getRoutineName() {
        return RoutineName;
    }

    public void setRoutineName(String routineName) {
        RoutineName = routineName;
    }

    public String getFoodKcal() {
        return FoodKcal;
    }

    public void setFoodKcal(String foodKcal) {
        FoodKcal = foodKcal;
    }

    public String getFoodCarbohydrate() {
        return FoodCarbohydrate;
    }

    public void setFoodCarbohydrate(String foodCarbohydrate) {
        FoodCarbohydrate = foodCarbohydrate;
    }

    public String getFoodProtein() {
        return FoodProtein;
    }

    public void setFoodProtein(String foodProtein) {
        FoodProtein = foodProtein;
    }

    public String getFoodFat() {
        return FoodFat;
    }

    public void setFoodFat(String foodFat) {
        FoodFat = foodFat;
    }

    public String getFoodSodium() {
        return FoodSodium;
    }

    public void setFoodSodium(String foodSodium) {
        FoodSodium = foodSodium;
    }

    public String getFoodSugar() {
        return FoodSugar;
    }

    public void setFoodSugar(String foodSugar) {
        FoodSugar = foodSugar;
    }

    public String getFoodKg() {
        return FoodKg;
    }

    public void setFoodKg(String foodKg) {
        FoodKg = foodKg;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User(String date, String foodName, String eatingTime, String foodKcal, String foodCarbohydrate, String foodProtein, String foodFat, String foodSodium, String foodSugar, String foodKg, int fccode, int quantity) {
        Date = date;
        FoodName = foodName;
        EatingTime = eatingTime;
        FoodKcal = foodKcal;
        FoodCarbohydrate = foodCarbohydrate;
        FoodProtein = foodProtein;
        FoodFat = foodFat;
        FoodSodium = foodSodium;
        FoodSugar = foodSugar;
        FoodKg = foodKg;
        FcCode = fccode;
        this.quantity = quantity;
    }
    public User(String date, String foodName, String eatingTime, String foodKcal, String foodCarbohydrate, String foodProtein, String foodFat, String foodSodium, String foodSugar, String foodKg, int fccode) {
        Date = date;
        FoodName = foodName;
        EatingTime = eatingTime;
        FoodKcal = foodKcal;
        FoodCarbohydrate = foodCarbohydrate;
        FoodProtein = foodProtein;
        FoodFat = foodFat;
        FoodSodium = foodSodium;
        FoodSugar = foodSugar;
        FoodKg = foodKg;
        FcCode = fccode;
    }
    //

    public User(String id, String name, String profile, String age, String state, String Nondisclosure) {
        Id = id;
        Name = name;
        Profile = profile;
        Age = age;
        State = state;
        Nondisclosure = Nondisclosure;
    }

    public User(String date, String foodName, String eatingTime) {
        Date = date;
        FoodName = foodName;
        EatingTime = eatingTime;
    }

    public String getEatingTime() {
        return EatingTime;
    }

    public void setEatingTime(String eatingTime) {
        this.EatingTime = eatingTime;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public static String getId() {
        return Id;
    }

    public static void setId(String id) {
        Id = id;
    }

    public static String getName() {
        return Name;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getState() { return State; }

    public static void setState(String state) { State = state; }

    public static String getProfile() { return Profile; }

    public static void setProfile(String profile) { Profile = profile; }

    public static String getAge() { return Age; }

    public static void setAge(String age) { Age = age; }

    public static String getPassword() { return Password; }

    public static void setPassword(String password) { Password = password; }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCurrentSteps() {
        return CurrentSteps;
    }

    public void setCurrentSteps(int currentSteps) {
        CurrentSteps = currentSteps;
    }

    public int getFcCode() {
        return FcCode;
    }

    public void setFcCode(int fcCode) {
        FcCode = fcCode;
    }

    public static String getWater() { return Water; }

    public static void setWater(String water) { Water = water; }

    public static String getwalkGoal() { return WalkGoal; }

    public static void setwalk_goal(String walkGoal) { WalkGoal = walkGoal; }


    public String getExerciseName() {
        return ExerciseName;
    }

    public void setExerciseName(String exerciseName) {
        ExerciseName = exerciseName;
    }

    public String getExercisePart() {
        return ExercisePart;
    }

    public void setExercisePart(String exercisePart) {
        ExercisePart = exercisePart;
    }

    public String getExercisesetNumber() {
        return ExercisesetNumber;
    }

    public void setExercisesetNumber(String exercisesetNumber) {
        ExercisesetNumber = exercisesetNumber;
    }

    public String getExerciseNumber() {
        return ExerciseNumber;
    }

    public void setExerciseNumber(String exerciseNumber) {
        ExerciseNumber = exerciseNumber;
    }

    public String getExerciseunit() {
        return Exerciseunit;
    }

    public void setExerciseunit(String exerciseunit) {
        Exerciseunit = exerciseunit;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public static String getWalkGoal() {
        return WalkGoal;
    }

    public static void setWalkGoal(String walkGoal) {
        WalkGoal = walkGoal;
    }

    public int getEcCode() {
        return EcCode;
    }

    public void setEcCode(int ecCode) {
        EcCode = ecCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;

    public static String getNondisclosure() {
        return Nondisclosure;
    }

    public static void setNondisclosure(String nondisclosure) {
        Nondisclosure = nondisclosure;

    }
}
