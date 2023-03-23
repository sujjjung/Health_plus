package com.example.djsu;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class weightRequest extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/fat_add.php";
    private Map<String, String> map;

    public weightRequest(String userId, String date, String fat, String muscle, String weight) {
        super(Method.POST, URL, null, null);
        map = new HashMap<>();
        map.put("userId", userId);
        map.put("date", date);
        map.put("fat", fat + "");
        map.put("muscle", muscle+ "");
        map.put("weight", weight+ "");
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}