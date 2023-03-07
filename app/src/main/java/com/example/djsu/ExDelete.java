package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ExDelete extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/ExDelete.php";
    private Map<String, String> parameters;
    public ExDelete(String ExerciseName, Response.Listener<String> listener){
        super(Request.Method.POST, URL, listener, null);//Post방식임
        parameters = new HashMap<>();//해쉬맵 생성후 parameters 변수에 값을 넣어줌
        parameters.put("ExerciseName", ExerciseName);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}