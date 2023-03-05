package com.example.djsu;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class weightRequest extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/fat.php";
    private Map<String, String> map;

    public weightRequest(String id, String date, String fat, String muscle, String weight, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userId",id);
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
