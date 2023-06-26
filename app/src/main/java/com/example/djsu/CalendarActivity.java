package com.example.djsu;

import static android.content.ContentValues.TAG;

import static com.example.djsu.R.style.CalendarWidgetHeader;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
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
import org.json.JSONException;
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

    private ScrollView scrollView;

    // 햄버거 버튼
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    // 캘린더
    public MaterialCalendarView calendarView;
    private final String TAG = this.getClass().getSimpleName();

    // 섭취, 운동
    int KcalNum = 0,CarbohydrateNum = 0,proteinNum = 0,FatNum = 0,sodiumNum = 0 ,SugarNum = 0,UnitNum = 0;
    String Date = "",date;

    // 유저 정보 출력
    private static final String TAG_RESULTS = "result";
    private static final String TAG_Date = "Date";
    private static final String TAG_UserId  = "UserId";
    private static final String TAG_FoodKcal = "FoodKcal";
    private static final String TAG_FoodCarbohydrate = "FoodCarbohydrate";
    private static final String TAG_FoodProtein = "FoodProtein";
    private static final String TAG_FoodFat = "FoodFat";
    private static final String TAG_FoodSodium = "FoodSodium";
    private static final String TAG_FoodSugar = "FoodSugar";
    private static final String TAG_FoodKg = "FoodKg";
    // 뭏
    private static final String TAG_WaterUserid  = "UserID";
    private static final String TAG_WaterDate = "date";
    private static final String TAG_water = "water";
    // 운동
    // 운동시간(분) * (6 X 0.0175 X 몸무게)
    private static final String TAG_ExerciseUnit = "ExerciseUnit";
    private static final String TAG_weight = "weight";
    private static final String TAG_FatId  = "userId";
    private static final String TAG_Time  = "Time";
    private float weight;
    User user = new User();
    String myJSON;
    JSONArray peoples = null;
    float minute,second,secondSum,ExKcalNum;

    // 섭취, 운동 선언
    TextView KcalSum,CarbohydrateSum,proteinSum,FatSum,sodiumSum,SugarSum,WaterSum,UnitSum,ExkcalSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 캘린더, 버튼 선언
        setContentView(R.layout.activity_calendar);
        getData("http://enejd0613.dothome.co.kr/foodcalendarlist.php");
        getFatData("http://enejd0613.dothome.co.kr/getWeight.php");
        getWaterData("http://enejd0613.dothome.co.kr/Waterlist.php");
        getExData("http://enejd0613.dothome.co.kr/excalendarlist.php");
        // 섭취, 운동 선언 초기화
        KcalSum = findViewById(R.id.kcalSum);
        CarbohydrateSum = findViewById(R.id.carbohydrateSum);
        proteinSum = findViewById(R.id.ProteinSum);
        FatSum = findViewById(R.id.fatSum);
        sodiumSum = findViewById(R.id.SodiumSum);
        SugarSum = findViewById(R.id.sugarSum);
        WaterSum = findViewById(R.id.WaterSum);
        UnitSum = findViewById(R.id.UnitSum);
        ExkcalSum = findViewById(R.id.ExkcalSum);
        // 캘린더 UnitSum
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                WaterSum.setText("");
                UnitSum.setText("");
                ExkcalSum.setText("");
                int year = date.getYear(); // 선택된 날짜의 연도 정보 추출
                int month = date.getMonth() + 1; // 선택된 날짜의 월 정보 추출 (0부터 시작하므로 +1 필요)
                int dayOfMonth = date.getDay(); // 선택된 날짜의 일 정보 추출

                String Year = String.valueOf(year); // 연도 정보를 문자열로 변환
                String Month = String.valueOf(month); // 월 정보를 문자열로 변환
                String DayOfMonth = String.valueOf(dayOfMonth); // 일 정보를 문자열로 변환
                Date = Year + "-" + Month + "-" + DayOfMonth;
                getData("http://enejd0613.dothome.co.kr/foodcalendarlist.php");
                getFatData("http://enejd0613.dothome.co.kr/getMuscle.php");
                getWaterData("http://enejd0613.dothome.co.kr/Waterlist.php");
                getExData("http://enejd0613.dothome.co.kr/excalendarlist.php");
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

        calendarView.setDateTextAppearance(R.style.CalendarDateTextAppearance);

        // 날짜 가져오기
        date = getTime();

        if(Date.equals("")){
            Date = date;
        }

        // 당일 음식 목록
        UserFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, userFood.class);
                intent.putExtra("Date", Date);
                startActivity(intent);
            }
        });

        // 당일 운동 목록
        UserExBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, userEx.class);
                intent.putExtra("Date", Date);
                startActivity(intent);
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
                        Intent Mainintent = new Intent(getApplicationContext(), main_user.class);
                        startActivity(Mainintent);
                        return true;
                    case R.id.calender:
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.communety:
                        Intent communetyintent = new Intent(getApplicationContext(), community.class);
                        startActivity(communetyintent);
                        return true;
                    case R.id.mypage:
                        Intent mypageintent = new Intent(getApplicationContext(), mypage.class);
                        startActivity(mypageintent);
                        return true;
                    case R.id.annoucement:
                        Intent Noticeintent = new Intent(getApplicationContext(), annoucement.class);
                        startActivity(Noticeintent);
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
        scrollView = findViewById(R.id.bio_scroll);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    // 스크롤 위치에 따라 버튼의 위치를 업데이트합니다.
                    fabMain.setTranslationY(scrollY);
                    fabMain.setTranslationX(scrollX);
                    fabHealth.setTranslationY(scrollY);
                    fabHealth.setTranslationX(scrollX);
                    fabFood.setTranslationY(scrollY);
                    fabFood.setTranslationX(scrollX);
                    fabKg.setTranslationY(scrollY);
                    fabKg.setTranslationX(scrollX);
                }
            });
        }

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
                Intent intent = new Intent(CalendarActivity.this, FoodAddActivity.class);
                intent.putExtra("Date", Date);
                startActivity(intent);
            }
        });

        // 운동 플로팅 버튼 클릭
        fabHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, routine.class);
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
            ObjectAnimator fk_animation = ObjectAnimator.ofFloat(fabKg, "translationY", fabMain.getTranslationY()-0f);
            fk_animation.start();
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabFood, "translationY", fabMain.getTranslationY()-0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabHealth, "translationY", fabMain.getTranslationY()-0f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_action_plus);

        } else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabFood, "translationY", fabMain.getTranslationY()-200f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabHealth, "translationY", fabMain.getTranslationY()-400f);
            fe_animation.start();
            ObjectAnimator fk_animation = ObjectAnimator.ofFloat(fabKg, "translationY", fabMain.getTranslationY()-600f);
            fk_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_action_plus);
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
    }

    // 음식 불러오기
    protected void showList() {
        KcalNum = 0;
        CarbohydrateNum = 0;
        proteinNum = 0;
        FatNum = 0;
        sodiumNum = 0;
        SugarNum = 0;
        KcalSum.setText("");
        CarbohydrateSum.setText("");
        proteinSum.setText("");
        FatSum.setText("");
        sodiumSum.setText("");
        SugarSum.setText("");
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);
                    String Date1 = c.getString(TAG_Date);
                    if(UserId.equals(user.getId())) {
                        if(Date.equals(Date1)) {
                            String FoodKcal = c.getString(TAG_FoodKcal);
                            String FoodCarbohydrate = c.getString(TAG_FoodCarbohydrate);
                            String FoodProtein = c.getString(TAG_FoodProtein);
                            String FoodFat = c.getString(TAG_FoodFat);
                            String FoodSodium = c.getString(TAG_FoodSodium);
                            String FoodSugar = c.getString(TAG_FoodSugar);
                            String FoodKg = c.getString(TAG_FoodKg);

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
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    // 물 불러오기
    protected void showWaterList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_WaterUserid);
                    String WaterDate = c.getString(TAG_WaterDate);
                    if(UserId.equals(user.getId())) {
                        if(Date.equals(WaterDate)) {
                            String Water = c.getString(TAG_water);
                            WaterSum.setText(Water);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getWaterData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showWaterList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    // 운동 불러오기
    protected void showExList() {
        UnitNum = 0;
        UnitSum.setText("");
        ExKcalNum = 0;
        ExkcalSum.setText("");
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);
                    String Date1 = c.getString(TAG_Date);
                    if(UserId.equals(user.getId())) {
                        if(Date.equals(Date1)) {
                            String ExerciseUnit = c.getString(TAG_ExerciseUnit);
                            String numberString = ExerciseUnit.replace("kg", "");
                            int number = Integer.parseInt(numberString);
                            UnitNum += number;
                            UnitSum.setText(String.valueOf(UnitNum));
                        }
                    }
                }

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);
                    String Date1 = c.getString(TAG_Date);
                    if(UserId.equals(user.getId())) {
                        if(Date.equals(Date1)) {
                            String Time = c.getString(TAG_Time);
                            minute += Float.parseFloat(Time.substring(0, 2));
                            second += Float.parseFloat(Time.substring(3, 5));
                        }
                    }
                }
                secondSum = second / 60;
                minute += secondSum;
                ExKcalNum = (float) (minute * (6 * 0.0175 * weight));
                ExkcalSum.setText(String.valueOf(ExKcalNum));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getExData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showExList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    // 몸무게 불러오기
    protected void showFatList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String userId = c.getString(TAG_FatId);
                    if(userId.equals(user.getId())) {
                        String weight = c.getString(TAG_weight);
                        this.weight =  Float.parseFloat(weight);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getFatData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showFatList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
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