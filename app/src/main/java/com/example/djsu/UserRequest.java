package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserRequest extends StringRequest{
    final static private String URL = "http://enejd0613.dothome.co.kr/foodcalendarlist.php";
    private Map<String, String> map;

    public UserRequest(String UserID) {
        super(Method.POST, URL,null, null);
        map = new HashMap<>();
        map.put("UserID",UserID);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}