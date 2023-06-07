package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class waterRequest extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/waterRequest.php";
    private Map<String, String> map;

    public waterRequest(String userID, String water,String date) {
        super(Request.Method.POST, URL, null, null);

        map = new HashMap<>();
        map.put("userId",userID);
        map.put("water",water + "");
        map.put("date", date);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}