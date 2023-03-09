package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

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

public class userFood extends AppCompatActivity {
    private List<User> userList;
    private UserFoodAdapter userFoodAdapter;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food);
        Intent intent = getIntent();
        userList = new ArrayList<>();
        userFoodAdapter = new UserFoodAdapter(this,userList);
        ListView userfoodListView = (ListView) findViewById(R.id.FoodView);
        userfoodListView.setAdapter(userFoodAdapter);
        User user1 = new User();
        try {
            userFoodAdapter.notifyDataSetChanged();
            System.out.println(intent.getStringExtra("UserFood"));
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("UserFood"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String Date,UserID,FoodName;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);
                System.out.println(object);
                Date = object.getString("Date");
                FoodName = object.getString("FoodName");
                //값들을 User클래스에 묶어줍니다
                User user = new User(Date,FoodName);
                    UserID = object.getString("UserID");
                    if(UserID.equals(user1.getId())) {
                        userList.add(user);//리스트뷰에 값을 추가해줍니다
                    }
                count++;
                };



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

