package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ExSelectRequest  extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/excalendar.php";
    private Map<String, String> map;

    public ExSelectRequest(String userId, String date,String ExerciseCode, String ExerciseName, String ExercisePart, String ExerciseSetNumber,String ExerciseNumber, String ExerciseUnit,String Time) {
        super(Method.POST, URL, null, null);
        map = new HashMap<>();
        map.put("userId",userId);
        map.put("Date", String.valueOf(date));
        map.put("ExerciseCode", ExerciseCode+ "");
        map.put("ExerciseName", ExerciseName);
        map.put("ExercisePart", ExercisePart);
        map.put("ExerciseSetNumber", ExerciseSetNumber+ "");
        map.put("ExerciseNumber", ExerciseNumber+ "");
        map.put("ExerciseUnit",ExerciseUnit);
        map.put("Time",Time );
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}