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
    Bundle extras;
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
    int FcCode;
    int KcalNum,CarbohydrateNum,proteinNum,FatNum,sodiumNum,SugarNum;
    User user1 = new User();
    String Year,Month,DayOfMonth,Date = "",date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Intent intent = getIntent();
        TextView KcalSum = findViewById(R.id.kcalSum);
        TextView CarbohydrateSum = findViewById(R.id.carbohydrateSum);
        TextView proteinSum = findViewById(R.id.ProteinSum);
        TextView FatSum = findViewById(R.id.fatSum);
        TextView sodiumSum = findViewById(R.id.SodiumSum);
        TextView SugarSum = findViewById(R.id.sugarSum);
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
                KcalSum.setText("");
                CarbohydrateSum.setText("");
                proteinSum.setText("");
                FatSum.setText("");
                sodiumSum.setText("");
                SugarSum.setText("");
                Year = String.valueOf(year);
                Month = String.valueOf(month + 1);
                DayOfMonth = String.valueOf(dayOfMonth);
                Date = Year + "-" + Month + "-" + DayOfMonth;
                String eatingTime,Date1,UserID,FoodName,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
                KcalNum = 0;
                int count = 0;
                CarbohydrateNum = 0;proteinNum = 0;FatNum = 0;sodiumNum = 0;SugarNum = 0;
                try {
                    JSONObject jsonObject = new JSONObject(intent.getStringExtra("UserFood"));
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    int a = jsonArray.length();
                    int b = a + 3;
                    //JSON 배열 길이만큼 반복문을 실행
                    while (count < b) {
                        //count는 배열의 인덱스를 의미
                        JSONObject object = jsonArray.getJSONObject(count);
                        Date1 = object.getString("Date");
                        FoodName = object.getString("FoodName");
                        FoodKcal = object.getString("FoodKcal");
                        FoodCarbohydrate = object.getString("FoodCarbohydrate");
                        FoodProtein = object.getString("FoodProtein");
                        FoodFat = object.getString("FoodFat");
                        FoodSodium = object.getString("FoodSodium");
                        FoodSugar = object.getString("FoodSugar");
                        FoodKg = object.getString("FoodKg");
                        eatingTime = object.getString("eatingTime");
                        FcCode = object.getInt("FcCode");
                        //값들을 User클래스에 묶어줍니다
                        UserID = object.getString("UserID");
                        if(UserID.equals(user1.getId())) {
                            if(Date.equals(Date1)) {
                                KcalNum +=  Integer.parseInt(FoodKcal);
                                CarbohydrateNum += Integer.parseInt(FoodCarbohydrate);
                                proteinNum += Integer.parseInt(FoodProtein);
                                FatNum += Integer.parseInt(FoodFat);
                                sodiumNum += Integer.parseInt(FoodSodium);
                                SugarNum += Integer.parseInt(FoodSugar);
                                KcalSum.setText(String.valueOf(KcalNum));
                                CarbohydrateSum.setText(String.valueOf(CarbohydrateNum));
                                proteinSum.setText(String.valueOf(proteinNum));
                                FatSum.setText(String.valueOf(FatNum));
                                sodiumSum.setText(String.valueOf(sodiumNum));
                                SugarSum.setText(String.valueOf(SugarNum));
                            }
                        }
                        count++;
                    };

                } catch (Exception e) {
                    e.printStackTrace();
                }
                KcalSum.setVisibility(View.VISIBLE);
                CarbohydrateSum.setVisibility(View.VISIBLE);
                proteinSum.setVisibility(View.VISIBLE);
                FatSum.setVisibility(View.VISIBLE);
                sodiumSum.setVisibility(View.VISIBLE);
                SugarSum.setVisibility(View.VISIBLE);
                // UserFoodBtn.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), String.format("%d - %d - %d", year, month + 1, dayOfMonth), Toast.LENGTH_SHORT).show();

            }
        });
        Button UserFoodBtn = (Button) findViewById(R.id.FoodGoBtn);
        extras = getIntent().getExtras();

        date = getTime();

        if(Date.equals("")){
            Date = date;
        }

        UserFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserFoodBackgroundTask(Date).execute();
            }

        });
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
//뒤로가기버튼 이미지 적용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        mainkcalBackgroundTask mainkcalBackgroundTask = new mainkcalBackgroundTask(CalendarActivity.this);
                        mainkcalBackgroundTask.execute();
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
                    case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), pedometer.class);
                        startActivity(manbogiintent);
                        return true;
                    case R.id.annoucement:
                        NoticeBackgroundTask noticeBackgroundTask = new NoticeBackgroundTask(CalendarActivity.this);
                        noticeBackgroundTask.execute();
                        return true;
                    case R.id.friend:
                        Intent friend = new Intent(getApplicationContext(), chatList.class);
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
                finish();
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
            intent.putExtra("num", 1);
            startActivity(intent);
            finish();
        }
    }

    class UserFoodBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String Date;
        public UserFoodBackgroundTask(String date) {
            this.Date = date;
        }
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
            startActivity(intent);
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