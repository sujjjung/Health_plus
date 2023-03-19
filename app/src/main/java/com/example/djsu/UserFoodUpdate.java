package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserFoodUpdate extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/UserFoodUpdate.php";
    private Map<String, String> parameters;
    public UserFoodUpdate(String date,String FoodName, String FoodKcal, String FoodCarbohydrate, String FoodProtein, String FoodFat
            , String FoodSodium, String FoodSugar, String FoodKg,String eatingTime,int FcCode, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);//Post방식임
        parameters = new HashMap<>();//해쉬맵 생성후 parameters 변수에 값을 넣어줌
        parameters.put("date", String.valueOf(date));
        parameters.put("FoodName",FoodName);
        parameters.put("FoodKcal", FoodKcal+ "");
        parameters.put("FoodCarbohydrate", FoodCarbohydrate + "");
        parameters.put("FoodProtein", FoodProtein + "");
        parameters.put("FoodFat",FoodFat + "");
        parameters.put("FoodSodium", FoodSodium + "");
        parameters.put("FoodSugar", FoodSugar + "");
        parameters.put("FoodKg", FoodKg + "");
        parameters.put("eatingTime",eatingTime);
        parameters.put("FcCode", FcCode +"");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}