package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EatTargetRequest extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/eatTarget.php";
    private Map<String, String> map;

    public EatTargetRequest(String userID, String eatTarget, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserID",userID);
        map.put("eatTarget", eatTarget);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}