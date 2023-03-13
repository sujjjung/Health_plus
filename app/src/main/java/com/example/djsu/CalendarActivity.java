package com.example.djsu;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.checkerframework.checker.units.qual.C;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private TextView kcalSum, carbohydrateSum, proteinSum, fatSum, SodiumSum, sugarSum;
    // 플로팅버튼 상태
    private boolean fabMain_status = false;
    private FloatingActionButton fabMain;
    private FloatingActionButton fabHealth;
    private FloatingActionButton fabFood;

    private FloatingActionButton fabKg;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public CalendarView calendarView;
    public TextView diaryTextView;
    public int year1, month1, dayOfMonth1;
    String Year,Month,DayOfMonth,Date = "",date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Button imageButton = (Button) findViewById(R.id.btn_exercise);
        Button UserFoodBtn = (Button) findViewById(R.id.FoodGoBtn);
        UserFoodBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new UserFoodBackgroundTask().execute();
            }

        });
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseRecordActivity.class);
                startActivity(intent);
            }
        });
        FoodSum foodSum = new FoodSum();

        calendarView = findViewById(R.id.calendarView);
        diaryTextView = findViewById(R.id.diaryTextView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                Year = String.valueOf(year);
                Month = String.valueOf(month + 1);
                DayOfMonth = String.valueOf(dayOfMonth);
                Date = Year + "-" + Month + "-" + DayOfMonth;
                diaryTextView.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), String.format("%d - %d - %d", year, month + 1, dayOfMonth), Toast.LENGTH_SHORT).show();
                diaryTextView.setText(Date);
            }
        });

        date = getTime();

        if(Date.equals("")){
            Date = date;
        }
        diaryTextView.setText(Date);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//뒤로가기버튼 이미지 적용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        Intent intent = getIntent();
        User user1 = new User();
        try {
            System.out.println(intent.getStringExtra("UserFood"));
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("UserFood"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String Date1,UserID,FoodName,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);
                System.out.println(object);
                Date1 = object.getString("Date");
                FoodName = object.getString("FoodName");
                FoodKcal = object.getString("FoodKcal");
                FoodCarbohydrate = object.getString("FoodCarbohydrate");
                FoodProtein = object.getString("FoodProtein");
                FoodFat = object.getString("FoodFat");
                FoodSodium = object.getString("FoodSodium");
                FoodSugar = object.getString("FoodSugar");
                FoodKg = object.getString("FoodKg");
                //값들을 User클래스에 묶어줍니다
                UserID = object.getString("UserID");
                if(UserID.equals(user1.getId())) {
                    if(Date.equals(Date1)) {
                        foodSum.setSumKcal(foodSum.sumKcal(Integer.parseInt(FoodKcal)));
                        foodSum.setSumCarbohydrate(foodSum.sumCarbohydrate(Integer.parseInt(FoodCarbohydrate)));
                        foodSum.setSumProtein(foodSum.sumProtein(Integer.parseInt(FoodProtein)));
                        foodSum.setSumFat(foodSum.sumFat(Integer.parseInt(FoodFat)));
                        foodSum.setSumSodium(foodSum.sumSodium(Integer.parseInt(FoodSodium)));
                        foodSum.setSumSugar(foodSum.sumSugar(Integer.parseInt(FoodSugar)));
                        foodSum.setSumKg(foodSum.sumKg(Integer.parseInt(FoodKg)));
                    }
                }
                count++;
            };

            foodSum.getSumKcal();
            foodSum.getSumCarbohydrate();
            foodSum.getSumProtein();
            foodSum.getSumFat();
            foodSum.getSumSodium();
            foodSum.getSumSugar();
            foodSum.getSumKg();

            kcalSum = findViewById(R.id.kcalSum);
            kcalSum.setText(String.valueOf(foodSum.getSumKcal()));

            carbohydrateSum = findViewById(R.id.carbohydrateSum);
            carbohydrateSum.setText(String.valueOf(foodSum.getSumCarbohydrate()));

            proteinSum = findViewById(R.id.ProteinSum);
            proteinSum.setText(String.valueOf(foodSum.getSumProtein()));

            fatSum = findViewById(R.id.fatSum);
            fatSum.setText(String.valueOf(foodSum.getSumFat()));

            SodiumSum = findViewById(R.id.SodiumSum);
            SodiumSum.setText(String.valueOf(foodSum.getSumSodium()));

            sugarSum = findViewById(R.id.sugarSum);
            sugarSum.setText(String.valueOf(foodSum.getSumSugar()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent homeintent = new Intent(getApplicationContext(), main_user.class);
                        startActivity(homeintent);
                        return true;
                    case R.id.calender:
                        UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(CalendarActivity.this);
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
                        NoticeBackgroundTask noticeBackgroundTask = new NoticeBackgroundTask(CalendarActivity.this);
                        noticeBackgroundTask.execute();
                        return true;
                    case R.id.friend:
                        Intent friend = new Intent(getApplicationContext(), friend_list.class);
                        startActivity(friend);
                        return true;
                }
                return false;
            }
        });
        fabMain = findViewById(R.id.floatingMain);
        fabHealth = findViewById(R.id.floatingHealth);
        fabFood = findViewById(R.id.floatingFood);
        fabKg = findViewById(R.id.floatingKg);

        // 메인플로팅 버튼 클릭
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });
        // 식단 플로팅 버튼 클릭
        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FoodBackgroundTask(Date).execute();
            }
        });

        // 운동 플로팅 버튼 클릭
        fabHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, HealthAddActivity.class);
                startActivity(intent);
            }
        });

        fabKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, WeightActivity.class);
                startActivity(intent);
            }
        });
    }

    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    public void toggleFab() {
        if (fabMain_status) {
            // 플로팅 액션 버튼 닫기
            // 애니메이션 추가
            ObjectAnimator fk_animation = ObjectAnimator.ofFloat(fabKg, "translationY", 0f);
            fk_animation.start();
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabFood, "translationY", 0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabHealth, "translationY", 0f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_action_plus);

        } else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabFood, "translationY", -200f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabHealth, "translationY", -400f);
            fe_animation.start();
            ObjectAnimator fk_animation = ObjectAnimator.ofFloat(fabKg, "translationY", -600f);
            fk_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_action_plus);
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
    }

    class FoodBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String Date;

        public FoodBackgroundTask(String date) {
            this.Date = date;
        }

        @Override
        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://enejd0613.dothome.co.kr/foodlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);//URL 객체 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(CalendarActivity.this, Food_List.class);
            intent.putExtra("Food",result);
            intent.putExtra("Date", Date);
            startActivity(intent);
            CalendarActivity.this.startActivity(intent);
        }
    }

    class UserFoodBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://enejd0613.dothome.co.kr/foodcalendarlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);//URL 객체 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(CalendarActivity.this, userFood.class);
            intent.putExtra("UserFood", result);
            intent.putExtra("Date", Date);
            CalendarActivity.this.startActivity(intent);

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}