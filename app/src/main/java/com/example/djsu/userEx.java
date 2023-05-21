package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class userEx extends AppCompatActivity {
    private List<User> userList;
    private UserExAdapter userExAdapter;
    private EditText editText;
    private TextView datetext;
    String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ex);
        Intent intent = getIntent();
        userList = new ArrayList<>();
        userExAdapter = new UserExAdapter(this,userList);
        ListView exView = (ListView) findViewById(R.id.exView);
        exView.setAdapter(userExAdapter);
        User user1 = new User();
        Bundle extras = getIntent().getExtras();
        datetext = findViewById(R.id.date_textView);
        date = extras.getString("Date");
        datetext.setText(date);
        try {
            userExAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("UserEx"));
            System.out.println(intent.getStringExtra("UserEx"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String Date,UserID,ExerciseCode,ExerciseName,ExercisePart,Time;
            int ExerciseSetNumber,ExerciseNumber,ExerciseUnit;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);
                System.out.println(object);
                Date = object.getString("Date");
                ExerciseCode = object.getString("ExerciseCode");
                ExerciseName = object.getString("ExerciseName");
                ExercisePart = object.getString("ExercisePart");
                ExerciseSetNumber = Integer.parseInt(object.getString("ExerciseSetNumber"));
                ExerciseNumber = Integer.parseInt(object.getString("ExerciseNumber"));
                ExerciseUnit = Integer.parseInt(object.getString("ExerciseUnit"));
                Time = object.getString("Time");

                //값들을 User클래스에 묶어줍니다
                User user = new User(Date,ExerciseName,ExercisePart,String.valueOf(ExerciseSetNumber),String.valueOf(ExerciseNumber),String.valueOf(ExerciseUnit),Time);
                UserID = object.getString("UserID");
                if(UserID.equals(user1.getId())) {
                    if(date.equals(Date)) {
                        userList.add(user);//리스트뷰에 값을 추가해줍니다

                    }
                }
                count++;
            };



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

