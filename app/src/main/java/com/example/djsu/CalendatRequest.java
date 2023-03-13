package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CalendatRequest extends StringRequest{
    final static private String URL = "http://enejd0613.dothome.co.kr/foodcalendar.php";
    private Map<String, String> map;

    public CalendatRequest(String userId, String date, int FoodCood,String FoodName, String FoodKcal, String FoodCarbohydrate, String FoodProtein, String FoodFat
            , String FoodSodium, String FoodSugar, String FoodKg) {
        super(Method.POST, URL, null, null);
        map = new HashMap<>();
        map.put("userId",userId);
        map.put("date", String.valueOf(date));
        map.put("FoodCood", FoodCood + "");
        map.put("FoodName",FoodName);
        map.put("FoodKcal", FoodKcal+ "");
        map.put("FoodCarbohydrate", FoodCarbohydrate + "");
        map.put("FoodProtein", FoodProtein + "");
        map.put("FoodFat",FoodFat + "");
        map.put("FoodSodium", FoodSodium + "");
        map.put("FoodSugar", FoodSugar + "");
        map.put("FoodKg", FoodKg + "");
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
