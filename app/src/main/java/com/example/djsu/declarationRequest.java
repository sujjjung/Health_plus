package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class declarationRequest  extends StringRequest {
        final static private String URL = "http://enejd0613.dothome.co.kr/declaration.php";
        private Map<String, String> map;

        public declarationRequest(String userId ,String postid,String postname,String cause, String Date) {
            super(Request.Method.POST, URL, null, null);
            map = new HashMap<>();
            map.put("UserId",userId);
            map.put("postid",postid + "");
            map.put("postname", postname);
            map.put("cause", cause);
            map.put("Date", Date);
        }
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return map;
        }
}
