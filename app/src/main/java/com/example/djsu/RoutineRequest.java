package com.example.djsu;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RoutineRequest  extends StringRequest {
    final static private String URL = "http://enejd0613.dothome.co.kr/Routine.php";
    private Map<String, String> map;

    public RoutineRequest(String userId ,String RoutineName,String ExerciseCode,String ExerciseName, String ExercisePart) {
        super(Method.POST, URL, null, null);
        map = new HashMap<>();
        map.put("userId",userId);
        map.put("RoutineName",RoutineName);
        map.put("ExerciseCode", ExerciseCode +"");
        map.put("ExerciseName", ExerciseName);
        map.put("ExercisePart", ExercisePart);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}