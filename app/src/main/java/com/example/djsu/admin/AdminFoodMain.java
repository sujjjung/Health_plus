package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.djsu.Food;
import com.example.djsu.FoodAdapter;
import com.example.djsu.MainActivity;
import com.example.djsu.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminFoodMain extends AppCompatActivity {
    private List<Food>foodArrayList;
    private AdminFoodAdapter foodAdapter;
    private EditText editText;
    ImageButton foodAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_main);
        Intent intent = getIntent();
        foodAddBtn = (ImageButton)findViewById(R.id.foodaddBtn);

        foodAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminFoodMain.this, AdminFoodAdd.class);
                startActivity(intent);
            }
        });
        foodArrayList =new ArrayList<>();

        foodAdapter = new AdminFoodAdapter(this,foodArrayList);

        ListView foodListView = (ListView) findViewById(R.id.FoodView);
        foodListView.setAdapter(foodAdapter);

        try {
            foodAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("food"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String FoodName;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);

                FoodName = object.getString("FoodName");

                //값들을 User클래스에 묶어줍니다
                Food food = new Food(FoodName);
                foodArrayList.add(food);//리스트뷰에 값을 추가해줍니다
                count++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
