package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendatRequest extends StringRequest{
    final static private String URL = "http://119.197.11.177/foodcalendar.php";
    private Map<String, String> map;

    public CalendatRequest(String userId, Date date,int FoodCood) {
        super(Method.POST, URL, null, null);
        map = new HashMap<>();
        map.put("userId",userId);
        map.put("date", String.valueOf(date));
        map.put("FoodCood", FoodCood + "");
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
