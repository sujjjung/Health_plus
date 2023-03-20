package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class user_data_editRequest extends StringRequest {

    final static private String URL = "http://enejd0613.dothome.co.kr/user_info_edit.php";
    private Map<String, String> map;

    public user_data_editRequest(String UserID, String UserPassword, String UserName, String UserAge, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("UserID",UserID);
        map.put("UserPassword", UserPassword);
        map.put("UserName", UserName);
        map.put("UserAge", UserAge + "");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}