package com.example.djsu;

import static android.content.ContentValues.TAG;

import static com.example.djsu.R.style.CalendarWidgetHeader;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    Bundle extras;
    // 플로팅버튼 상태
    private boolean fabMain_status = false;
    private FloatingActionButton fabMain;
    private FloatingActionButton fabHealth;
    private FloatingActionButton fabFood;
    private FloatingActionButton fabKg;

    // 햄버거 버튼
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    // 캘린더
    public MaterialCalendarView calendarView;
    private final String TAG = this.getClass().getSimpleName();

    // 섭취, 운동
    public int count = 0;
    int FcCode;
    int KcalNum,CarbohydrateNum,proteinNum,FatNum,sodiumNum,SugarNum;
    User user1 = new User();
    String Date = "",date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 캘린더, 버튼 선언
        setContentView(R.layout.activity_calendar);
        Button imageButton = (Button) findViewById(R.id.btn_exercise);

        // 이미지 버튼을 누르면 ?
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseRecordActivity.class);
                startActivity(intent);
            }
        });

        // 이게 뭐지? 
        Intent intent = getIntent();
        
        // 섭취, 운동 선언
        TextView KcalSum = findViewById(R.id.kcalSum);
        TextView CarbohydrateSum = findViewById(R.id.carbohydrateSum);
        TextView proteinSum = findViewById(R.id.ProteinSum);
        TextView FatSum = findViewById(R.id.fatSum);
        TextView sodiumSum = findViewById(R.id.SodiumSum);
        TextView SugarSum = findViewById(R.id.sugarSum);

        // 캘린더 뷰
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                KcalSum.setText("");
                CarbohydrateSum.setText("");
                proteinSum.setText("");
                FatSum.setText("");
                sodiumSum.setText("");
                SugarSum.setText("");
                int KcalNum = 0,CarbohydrateNum = 0,proteinNum = 0,FatNum = 0,sodiumNum = 0,SugarNum = 0;;
                User user1 = new User();
                int year = date.getYear(); // 선택된 날짜의 연도 정보 추출
                int month = date.getMonth() + 1; // 선택된 날짜의 월 정보 추출 (0부터 시작하므로 +1 필요)
                int dayOfMonth = date.getDay(); // 선택된 날짜의 일 정보 추출

                String Year = String.valueOf(year); // 연도 정보를 문자열로 변환
                String Month = String.valueOf(month); // 월 정보를 문자열로 변환
                String DayOfMonth = String.valueOf(dayOfMonth); // 일 정보를 문자열로 변환
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

                // UserFoodBtn.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), String.format("%d - %d - %d", year, month + 1, dayOfMonth), Toast.LENGTH_SHORT).show();

            }
        });
        Button UserFoodBtn = (Button) findViewById(R.id.FoodGoBtn);
        Button UserExBtn = (Button) findViewById(R.id.ExGoBtn);
        extras = getIntent().getExtras();

        // 캘린더 커스텀 (이수정이 만지는 중)
        calendarView.state()
                .edit()
                .commit();

        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));

        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        calendarView.setHeaderTextAppearance(CalendarWidgetHeader);

        // 요일 선택 시 내가 정의한 드로어블이 적용되도록 함
        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                // 아래 로그를 통해 시작일, 종료일이 어떻게 찍히는지 확인하고 본인이 필요한 방식에 따라 바꿔 사용한다
                // UTC 시간을 구하려는 경우 이 라이브러리에서 제공하지 않으니 별도의 로직을 짜서 만들어내 써야 한다
                String startDay = dates.get(0).getDate().toString();
                String endDay = dates.get(dates.size() - 1).getDate().toString();
                Log.e(TAG, "시작일 : " + startDay + ", 종료일 : " + endDay);
            }
        });

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        calendarView.addDecorators(new DayDecorator(this));

        // 주말 색깔 다르게
        calendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator()
        );

        // 오늘 날짜 표시
        OneDayDecorator oneDayDecorator = new OneDayDecorator();
        calendarView.addDecorators(
                oneDayDecorator
        );

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        calendarView.addDecorators(new DayDecorator(this));

        // CustomDayView 객체를 Material CalendarView 에 적용하는 코드
        CustomDayViewDecorator customDayViewDecorator = new CustomDayViewDecorator(10); // 왼쪽 여백 값을 10으로 설정
        calendarView.addDecorators(customDayViewDecorator);

        // 날짜 가져오기
        date = getTime();

        if(Date.equals("")){
            Date = date;
        }

        String eatingTime,Date1,UserID,FoodName,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
        int count = 0;
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
        
        // 당일 음식 목록
        UserFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserFoodBackgroundTask(Date).execute();
            }
        });

        UserExBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserFoodBackgroundTask(Date).execute();
            }
        });

        // 햄버거 버튼
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

        // 플로팅 버튼 선언
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
                intent.putExtra("Date", Date);
                startActivity(intent);
            }
        });

        // 체중 플로팅 버튼 클릭
        fabKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, WeightActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 선택된 요일의 background를 설정하는 Decorator 클래스
    private static class DayDecorator implements DayViewDecorator {

        private final Drawable drawable;

        public DayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
        }

        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
//            view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
        }
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
            target = "http://enejd0613.dothome.co.kr/foodcalendarlist.php";
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
            Intent intent = new Intent(CalendarActivity.this, FoodAddActivity.class);
            intent.putExtra("UserFood", result);
            intent.putExtra("Date", Date);
            startActivity(intent);
            finish();
        }
    }

    //음식리스트 불러오기
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
    
    //운동 리스트 불러오기
    class UserExBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        String Date;
        public UserExBackgroundTask(String date) {
            this.Date = date;
        }
        @Override
        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://enejd0613.dothome.co.kr/excalendarlist.php";
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
            Intent intent = new Intent(CalendarActivity.this, userEx.class);
            intent.putExtra("UserEx", result);
            intent.putExtra("Date", Date);
            startActivity(intent);
        }
    }

    // 햄버거
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 날짜 가져오기
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}