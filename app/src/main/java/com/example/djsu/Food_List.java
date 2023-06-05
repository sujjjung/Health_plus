package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Food_List extends AppCompatActivity {

    private List<Food>foodArrayList;
    private FoodAdapter foodAdapter;
    private EditText editText;
    String Date,search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        Intent intent = getIntent();
        ArrayList<Food> search_list = new ArrayList<>();
        foodArrayList = new ArrayList<>();
        editText = findViewById(R.id.searchtext);
        // editText 리스터 작성

        Bundle extras = getIntent().getExtras();

        Date = extras.getString("Date");
        search = extras.getString("searchText");

        foodAdapter = new FoodAdapter(this,foodArrayList,Date);

        ListView foodListView = (ListView) findViewById(R.id.FoodView);
        foodListView.setAdapter(foodAdapter);

        try {
            foodAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("Food"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String FoodName;
            float FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
            int FoodCood;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);

                FoodCood = Integer.parseInt(object.getString("FoodCood"));
                FoodName = object.getString("FoodName");
                FoodKcal = object.getInt("FoodKcal");
                FoodCarbohydrate = object.getInt("FoodCarbohydrate");
                FoodProtein = object.getInt("FoodProtein");
                FoodFat = object.getInt("FoodFat");
                FoodSodium = object.getInt("FoodSodium");
                FoodSugar = object.getInt("FoodSugar");
                FoodKg = object.getInt("FoodKg");

                //값들을 User클래스에 묶어줍니다
                Food food = new Food(FoodCood,FoodName,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg);
                foodArrayList.add(food);//리스트뷰에 값을 추가해줍니다
                count++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        int num = extras.getInt("num");
        switch (num){
            case 1: break;
            case 0:     editText.setText(search);
                String searchText = editText.getText().toString();
                search_list.clear();

                if (searchText.equals("")) {
                    foodAdapter.setItems((ArrayList<Food>) foodArrayList);
                } else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < foodArrayList.size(); a++) {
                        if (foodArrayList.get(a).getFoodName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(foodArrayList.get(a));
                        }
                        foodAdapter.setItems(search_list);
                    }
                };break;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = editText.getText().toString();
                search_list.clear();

                if (searchText.equals("")) {
                    foodAdapter.setItems((ArrayList<Food>) foodArrayList);
                } else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < foodArrayList.size(); a++) {
                        if (foodArrayList.get(a).getFoodName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(foodArrayList.get(a));
                        }
                        foodAdapter.setItems(search_list);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
    }

}

