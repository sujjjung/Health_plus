package com.example.djsu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class pedometer extends AppCompatActivity {
    //햄버거 선언부
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    SensorManager sensorManager;
    Sensor stepCountSensor;
    TextView stepCountView;
    Button resetButton;

    public int getCurrentSteps() {
        return currentSteps;
    }

    public void setCurrentSteps(int currentSteps) {
        this.currentSteps = currentSteps;
    }

    // 현재 걸음 수
    int currentSteps = 0;

    // 차트 선언
    private BarChart walkChart, goalChart;

    @RequiresApi(api = Build.VERSION_CODES.Q)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer);

        // 걷는 움짤
        ImageView walk = (ImageView)findViewById(R.id.walk_lion);
        Glide.with(this).load(R.raw.rununscreen).into(walk);

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
                switch(menuItem.getItemId()){
                    case R.id.home:
                        Intent homeintent = new Intent(getApplicationContext(), main_user.class);
                        startActivity(homeintent);
                        return true;
                    case R.id.calender:
                        Intent calenderintent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(calenderintent);
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
                        Intent annoucementintent = new Intent(getApplicationContext(), annoucement.class);
                        startActivity(annoucementintent);
                        return true;
                    case R.id.friend:
                        Intent friend = new Intent(getApplicationContext(), friend_list.class);
                        startActivity(friend);
                        return true;
                }
                return false;
            }
        });

        User user = new User();

        // 목표 설정
        Button goal = findViewById(R.id.setGoal);
        goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = user.getId();

                dialog_set_goal alert = new dialog_set_goal(pedometer.this);
                alert.callFunction();
                alert.setModifyReturnListener(new dialog_set_goal.ModifyReturnListener() {
                    @Override
                    public void afterModify(String text) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("!!!!!!!!!!!!!: " +text);
                                String goal = text.toString();
                                User user = new User();
                                user.setwalk_goal(goal);
                                Toast.makeText(getApplicationContext(), "한줄소개가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        };
                    }
                });
            }
        });

        // 걸음수 선언
        stepCountView = findViewById(R.id.stepCountView);
        // 초기화 버튼 선언
        resetButton = findViewById(R.id.resetButton);


        // 활동 퍼미션 체크
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        // 걸음 센서 연결
        // * 옵션
        // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        //
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

        // 리셋 버튼 추가 - 리셋 기능
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 걸음수 초기화
                currentSteps = 0;
                stepCountView.setText(String.valueOf(currentSteps));

            }
        });

        // 차트
        walkChart = findViewById(R.id.walk_chart);
        // Disable description
        walkChart.getDescription().setEnabled(false);
        // Enable touch gestures
        walkChart.setTouchEnabled(true);
        // Enable scaling and dragging
        walkChart.setDragEnabled(true);
        walkChart.setScaleEnabled(true);
        // 데이터 초기화
        walkChart.clear();
        // Set data
        setWalkData();


        // 당일 목표 차트
        goalChart = (BarChart) findViewById(R.id.goalChart);
        // Disable description
        goalChart.getDescription().setEnabled(false);
        // Enable touch gestures
        goalChart.setTouchEnabled(true);
        // Enable scaling and dragging
        goalChart.setDragEnabled(true);
        goalChart.setScaleEnabled(true);
        // 데이터 초기화
        goalChart.clear();
        // Set data
        setGoalData();
    }

    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener((SensorEventListener) this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){

            if(event.values[0]==1.0f){
                // 센서 이벤트가 발생할때 마다 걸음수 증가
                currentSteps++;
                stepCountView.setText(String.valueOf(currentSteps));
                // User user = new User();
                // user.setCurrentSteps(currentSteps);
            }
        }
    }

    public static void resetAlarm(Context context){
        AlarmManager resetAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent resetIntent = new Intent(context, pedometer.class);
        PendingIntent resetSender = PendingIntent.getBroadcast(context, 0, resetIntent, 0);

        // 자정 시간
        Calendar resetCal = Calendar.getInstance();
        resetCal.setTimeInMillis(System.currentTimeMillis());
        resetCal.set(Calendar.HOUR_OF_DAY, 0);
        resetCal.set(Calendar.MINUTE,0);
        resetCal.set(Calendar.SECOND, 0);

        //다음날 0시에 맞추기 위해 24시간을 뜻하는 상수인 AlarmManager.INTERVAL_DAY를 더해줌.
        resetAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, resetCal.getTimeInMillis()
                +AlarmManager.INTERVAL_DAY, AlarmManager.INTERVAL_DAY, resetSender);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd kk:mm:ss");
        String setResetTime = format.format(new Date(resetCal.getTimeInMillis()+AlarmManager.INTERVAL_DAY));

        Log.d("resetAlarm", "ResetHour : " + setResetTime);
    }

    private void setGoalData() {
        ArrayList<BarEntry> entry_chart = new ArrayList<>();

        entry_chart.add(new BarEntry(1,currentSteps));
    }

    private void setWalkData() {
        String url = "http://enejd0613.dothome.co.kr/getWalk.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<BarEntry> values = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int walk = jsonObject.getInt("walk");
                        values.add(new BarEntry(i, new float[] {walk}));
                        String date = jsonObject.getString("date");
                        labels.add(date);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                BarDataSet set1 = new BarDataSet(values, "walk Data");
                set1.setColors(ColorTemplate.MATERIAL_COLORS);
                set1.setDrawValues(false);

                BarData data = new BarData(set1);
                data.setBarWidth(0.45f);

                walkChart.setData(data);
                walkChart.setFitBars(true);
                walkChart.invalidate();
                walkChart.setTouchEnabled(false); // 터치 막기
                walkChart.setMaxVisibleValueCount(7); // 그래프 최대 갯수
                walkChart.getXAxis().setDrawGridLines(false); // X축 및 Y축 격자선 없애기
                walkChart.getAxisLeft().setDrawGridLines(false);
                walkChart.getAxisRight().setDrawGridLines(false);
                walkChart.getAxisLeft().setDrawLabels(false); // 왼쪽 값 없애기
                // weightChart.getXAxis().setDrawLabels(false); // 위쪽 라벨 없애기
                XAxis xAxis = walkChart.getXAxis(); // x축 설정
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels)); // 라벨 붙이기
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 라벨 위치 설정
                walkChart.getLegend().setEnabled(false); // 레전드 제거
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(pedometer.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}