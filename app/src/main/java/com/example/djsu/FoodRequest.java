package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FoodRequest extends StringRequest {
    final static private String URL = "http://enejd0613.ivyro.net/food.php";
    private Map<String, String> map;

    public FoodRequest(String FoodName, String FoodKcal, String FoodCarbohydrate, String FoodProtein, String FoodFat
            , String FoodSodium, String FoodSugar, String FoodKg, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("FoodName",FoodName);
        map.put("FoodKcal", FoodKcal);
        map.put("FoodCarbohydrate", FoodCarbohydrate);
        map.put("FoodProtein", FoodProtein);
        map.put("FoodFat",FoodFat);
        map.put("FoodSodium", FoodSodium);
        map.put("FoodSugar", FoodSugar);
        map.put("FoodKg", FoodKg);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}