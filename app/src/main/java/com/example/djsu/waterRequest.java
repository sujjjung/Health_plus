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

    public waterRequest(String userID, int water, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserID",userID);
        map.put("UserPassword", String.valueOf(water));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}