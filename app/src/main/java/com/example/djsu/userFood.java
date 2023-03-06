package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

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
        userList = new ArrayList<User>();
        userFoodAdapter = new UserFoodAdapter(this,userList);
        ListView userfoodListView = (ListView) findViewById(R.id.FoodView);
        userfoodListView.setAdapter(userFoodAdapter);
        try {
            userFoodAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("UserFood"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String Date;
            int FoodCode;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);
                    FoodCode = Integer.parseInt(object.getString("FoodCode"));
                    Date = object.getString("Date");
                    //값들을 User클래스에 묶어줍니다
                    User user = new User(Date,FoodCode);
                    userList.add(user);//리스트뷰에 값을 추가해줍니다
                    count++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

