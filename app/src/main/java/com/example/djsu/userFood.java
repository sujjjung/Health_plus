package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class userFood extends AppCompatActivity {
    private List<Food> foodArrayList;
    private FoodAdapter foodAdapter;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food);
        Intent intent = getIntent();
        foodArrayList =new ArrayList<>();

        foodAdapter = new FoodAdapter(this,foodArrayList);

        ListView foodListView = (ListView) findViewById(R.id.FoodView);
        foodListView.setAdapter(foodAdapter);

        try {
            foodAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("food"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String FoodName,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
            int FoodCood;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);

                FoodCood = Integer.parseInt(object.getString("FoodCood"));
                FoodName = object.getString("FoodName");
                FoodKcal = object.getString("FoodKcal");
                FoodCarbohydrate = object.getString("FoodCarbohydrate");
                FoodProtein = object.getString("FoodProtein");
                FoodFat = object.getString("FoodFat");
                FoodSodium = object.getString("FoodSodium");
                FoodSugar = object.getString("FoodSugar");
                FoodKg = object.getString("FoodKg");

                //값들을 User클래스에 묶어줍니다
                Food food = new Food(FoodCood,FoodName,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg);
                foodArrayList.add(food);//리스트뷰에 값을 추가해줍니다
                count++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

