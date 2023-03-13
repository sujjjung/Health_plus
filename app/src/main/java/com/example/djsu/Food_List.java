package com.example.djsu;

import static android.content.ContentValues.TAG;
import static android.media.CamcorderProfile.get;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;


public class Food_List extends AppCompatActivity {

    private List<Food>foodArrayList;
    private FoodAdapter foodAdapter;
    private EditText editText;
    String Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        Intent intent = getIntent();
        ArrayList<Food> search_list = new ArrayList<>();
        foodArrayList = new ArrayList<Food>();
        editText = findViewById(R.id.searchtext);
        // editText 리스터 작성
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
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

        });

        // recyclerView1.bringToFront();
        foodArrayList =new ArrayList<>();
        Bundle extras = getIntent().getExtras();

        Date = extras.getString("Date");
        foodAdapter = new FoodAdapter(this,foodArrayList,Date);

        ListView foodListView = (ListView) findViewById(R.id.FoodView);
        foodListView.setAdapter(foodAdapter);

        try {
            foodAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("Food"));
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

