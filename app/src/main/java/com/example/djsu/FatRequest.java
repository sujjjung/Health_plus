package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FatRequest extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/fat_add.php";
    private Map<String, String> map;

    public FatRequest(String UserID, String date, String weight, String muscle, String fat, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userId",UserID);
        map.put("date", date);
        map.put("fat", fat);
        map.put("muscle", muscle);
        map.put("weight", weight);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}