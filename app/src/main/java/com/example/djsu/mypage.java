package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class mypage extends AppCompatActivity {
    //햄버거 선언부
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private TextView name, state;
    private ImageView ivImage;
    private String profile;

    //차트
    private BarChart weightChart;
    private BarChart muscleChart;
    private BarChart fatChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        // User Data 불러오기
        User user = new User();
        name = findViewById(R.id.name_textView);
        name.setText(user.getName());
        state = findViewById(R.id.Status_message_text);
        state.setText(user.getState());

        profile = user.getProfile();

        ivImage = findViewById(R.id.profile);

        // Glide로 이미지 표시하기
        String imageUrl = profile;
        Glide.with(this).load(imageUrl).into(ivImage);

        // 정보 변경 버튼
        Button user_edit = (Button) findViewById(R.id.change_btn);
        user_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), user_data_edit.class);
                startActivity(intent);
            }
        });

        // 회원 탈퇴 버튼
        Button user_delete = (Button) findViewById(R.id.secession_btn);
        user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserID = user.getId();

                dialog_del alert = new dialog_del(mypage.this, UserID);
                alert.callFunction();
                alert.setModifyReturnListener(new dialog_del.ModifyReturnListener() {
                    @Override
                    public void afterModify(String text) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");

                                    if (success.equals("1")) { // 회원 삭제에 성공한 경우
                                        Toast.makeText(getApplicationContext(), "회원 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mypage.this, MainActivity.class);
                                        startActivity(intent);  //intent를 넣어 실행시키게 됩니다.
                                    } else { // 회원 삭제에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "회원 탈퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        // 서버로 Volley를 이용해서 요청을 함.
                        delUserRequest delUserRequest1 = new delUserRequest(UserID, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(mypage.this);
                        queue.add(delUserRequest1);
                    }
                });
            }
        });
        // 몸무게
        // 차트
        weightChart = findViewById(R.id.weight_chart);
        // Disable description
        weightChart.getDescription().setEnabled(false);
        // Enable touch gestures
        weightChart.setTouchEnabled(true);
        // Enable scaling and dragging
        weightChart.setDragEnabled(true);
        weightChart.setScaleEnabled(true);
        // 데이터 초기화
        weightChart.clear();
        // Set data
        setWeightData();

        // 체지방량
        // 차트
        fatChart = findViewById(R.id.fat_chart);
        // Disable description
        fatChart.getDescription().setEnabled(false);
        // Enable touch gestures
        fatChart.setTouchEnabled(true);
        // Enable scaling and dragging
        fatChart.setDragEnabled(true);
        fatChart.setScaleEnabled(true);
        // 데이터 초기화
        fatChart.clear();
        // Set data
        setFatData();

        // 체지방량
        // 차트
        muscleChart = findViewById(R.id.muscles_chart);
        // Disable description
        muscleChart.getDescription().setEnabled(false);
        // Enable touch gestures
        muscleChart.setTouchEnabled(true);
        // Enable scaling and dragging
        muscleChart.setDragEnabled(true);
        muscleChart.setScaleEnabled(true);
        // 데이터 초기화
        muscleChart.clear();
        // Set data
        setMuscleData();


        // 햄버거 버튼 구현부
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent homeintent = new Intent(getApplicationContext(), main_user.class);
                        startActivity(homeintent);
                        return true;
                    case R.id.calender:
                        UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(mypage.this);
                        userFoodListBackgroundTask.execute();
                        return true;
                    case R.id.communety:
                        Intent communetyintent = new Intent(getApplicationContext(), community.class);
                        startActivity(communetyintent);
                        return true;
                    case R.id.mypage:
                        Intent mypageintent = new Intent(getApplicationContext(), mypage.class);
                        startActivity(mypageintent);
                        return true;
                    case R.id.map:
                        Intent mapintent = new Intent(getApplicationContext(), map.class);
                        startActivity(mapintent);
                        return true;
                   /* case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), .class);
                        startActivity(manbogiintent);
                        return true;*/
                    case R.id.annoucement:
                        NoticeBackgroundTask noticeBackgroundTask = new NoticeBackgroundTask(mypage.this);
                        noticeBackgroundTask.execute();
                        return true;
                }
                return false;
            }
        });
    }

    private void setWeightData() {
        String url = "http://enejd0613.dothome.co.kr/getWeight.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<BarEntry> values = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int fatKey = jsonObject.getInt("fatKey");
                        int weight = jsonObject.getInt("weight");
                        values.add(new BarEntry(i, new float[] {fatKey, weight}));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                BarDataSet set1 = new BarDataSet(values, "weight Data");
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                set1.setDrawValues(false);

                BarData data = new BarData(set1);
                data.setBarWidth(0.45f);

                weightChart.setData(data);
                weightChart.setFitBars(true);
                weightChart.invalidate();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mypage.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    private void setFatData() {
        String url = "http://enejd0613.dothome.co.kr/getFat.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<BarEntry> values = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int fatKey = jsonObject.getInt("fatKey");
                        int fat = jsonObject.getInt("fat");
                        values.add(new BarEntry(i, new float[] {fatKey, fat}));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                BarDataSet set1 = new BarDataSet(values, "fat Data");
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                set1.setDrawValues(false);

                BarData data = new BarData(set1);
                data.setBarWidth(0.45f);

                fatChart.setData(data);
                fatChart.setFitBars(true);
                fatChart.invalidate();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mypage.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    private void setMuscleData() {
        String url = "http://enejd0613.dothome.co.kr/getMuscle.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<BarEntry> values = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int fatKey = jsonObject.getInt("fatKey");
                        int muscle = jsonObject.getInt("muscle");
                        values.add(new BarEntry(i, new float[] {fatKey, muscle}));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                BarDataSet set1 = new BarDataSet(values, "muscle Data");
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                set1.setDrawValues(false);

                BarData data = new BarData(set1);
                data.setBarWidth(0.45f);

                muscleChart.setData(data);
                muscleChart.setFitBars(true);
                muscleChart.invalidate();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mypage.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}