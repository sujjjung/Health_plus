package com.example.djsu;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class signupRequest extends StringRequest{
    final static private String URL = "http://enejd0613.ivyro.net/register.php";
    private Map<String, String> map;

    public signupRequest(String userID, String userPassword, String userName, String userAge, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("id",userID);
        map.put("pw", userPassword);
        map.put("name", userName);
        map.put("age", userAge);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}