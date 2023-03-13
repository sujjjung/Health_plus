package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NoticeRequest extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/Announcement.php";
    private Map<String, String> map;

    public NoticeRequest(String date, String title, String detail, String emoji) {
        super(Method.POST, URL, null, null);
        map = new HashMap<>();
        map.put("date",date);
        map.put("title",title);
        map.put("detail", detail);
        map.put("emote", emoji);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}