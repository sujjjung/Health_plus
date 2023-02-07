package com.example.djsu;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private SimpleDateFormat defaultDateFormat =new SimpleDateFormat("yyyy-MM-dd");
    DateFormat defaultMonthFormat=new SimpleDateFormat("yyyy-MM");
    public String selectedDate = "";

    private Date startDate = new Date();
    private Date selectedDateTime = null;
    private ImageButton beforeIB;
    private ImageButton afterIB;
    private TextView dateTV;


    public RecyclerView calendarRecyclerView;
    private CalendarAdapter calendarAdapter;

    private Date nowDate = new Date();//주에 오늘 날짜가 포함하는지 보기위해.
    private Calendar nowCal = Calendar.getInstance();
    private int nowMonth = -1;

    // 플로팅버튼 상태
    private boolean fabMain_status = false;
    private FloatingActionButton fabMain;
    private FloatingActionButton fabHealth;
    private FloatingActionButton fabFood;

    private FloatingActionButton fabKg;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Button imageButton = (Button) findViewById(R.id.btn_exercise);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExerciseRecordActivity.class);
                startActivity(intent);
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
                   /* case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), .class);
                        startActivity(manbogiintent);
                        return true;*/
                    case R.id.annoucement:
                        Intent annoucementintent = new Intent(getApplicationContext(), annoucement.class);
                        startActivity(annoucementintent);
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
                Intent intent = new Intent(CalendarActivity.this, Food_List.class);
                startActivity(intent);
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
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        calendarAdapter = new CalendarAdapter(this);
        calendarRecyclerView.setAdapter(calendarAdapter);
        beforeIB = findViewById(R.id.beforeIB);
        afterIB = findViewById(R.id.afterIB);
        dateTV = findViewById(R.id.dateTV);

        beforeIB.setOnClickListener(v -> {
            setDate(-1);
        });
        afterIB.setOnClickListener(v -> {
            setDate(1);
        });

        selectedDateTime = Calendar.getInstance().getTime();

        setCalendarList(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));
    }

    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    public void toggleFab() {
        if(fabMain_status) {
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

        }else {
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


    private void setDate(int addAmount) {
        Calendar calendar = Calendar.getInstance();

        if (selectedDateTime != null) {
            calendar.setTime(selectedDateTime);
        }
        calendar.add(Calendar.MONTH, addAmount);
        startDate =calendar.getTime();

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);


        int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), max);

        selectedDate = defaultDateFormat.format(nowDate);
        selectedDateTime = calendar.getTime();//strStartDate;

        int setMonth = calendar.get(Calendar.MONTH) + 1;
        if (setMonth == nowMonth) {
            selectedDate = defaultDateFormat.format(nowDate);
            selectedDateTime = calendar.getTime();//strStartDate;
        } else {
            Calendar dashboardDateCal = Calendar.getInstance();
            dashboardDateCal.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
            selectedDate = defaultDateFormat.format(dashboardDateCal.getTime());//strStartDate;
            selectedDateTime = calendar.getTime();//strStartDate;
        }

        if (selectedDateTime == null) {
            calendar.setTime(startDate);
            selectedDateTime = startDate;
        } else {
            calendar.setTime(selectedDateTime);
        }
        setCalendarList(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));


    }


    public void setCalendarList(int year, int month) {

        try {

            dateTV.setText(defaultMonthFormat.format(selectedDateTime));

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<DayModel> dayModelList = new ArrayList<>();


        try {


            GregorianCalendar calendar = new GregorianCalendar();
            GregorianCalendar preCalendar = new GregorianCalendar();

            if (year > 0 && month > -1) {
                calendar.set(GregorianCalendar.YEAR, year);
                calendar.set(GregorianCalendar.MONTH, month);
                calendar.set(GregorianCalendar.DATE, 1);

                preCalendar.set(GregorianCalendar.YEAR, year);
                preCalendar.set(GregorianCalendar.MONTH, month);
                preCalendar.set(GregorianCalendar.DATE, 1);

            } else {
                calendar.set(GregorianCalendar.YEAR, calendar.get(Calendar.YEAR));
                calendar.set(GregorianCalendar.MONTH, calendar.get(Calendar.MONTH));
                calendar.set(GregorianCalendar.DATE, 1);

                preCalendar.set(GregorianCalendar.YEAR, calendar.get(Calendar.YEAR));
                preCalendar.set(GregorianCalendar.MONTH, calendar.get(Calendar.MONTH));
                preCalendar.set(GregorianCalendar.DATE, 1);

            }

            preCalendar.add(Calendar.MONTH, -1);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 해당 월에 마지막 요일


            int preMonthMax = preCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            //앞의 EMPTY 생성
            for (int j = 0; j < dayOfWeek; j++) {


                DayModel dayModel = new DayModel();
                dayModel.setType(102031);


                dayModel.setCalendarModel(new GregorianCalendar(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) - 1), preMonthMax - (dayOfWeek - 1 - j)));
                dayModelList.add(dayModel);

            }

            if (dayOfWeek > 0) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date(dayModelList.get(0).getCalendarModel().getTimeInMillis());
            }

            for (int j = 1; j <= max; j++) {

                DayModel dayModel = new DayModel();
                dayModel.setType(1);
                dayModel.setCalendarModel(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), j));
                dayModelList.add(dayModel);
            }

            //뒤의 Empty
            if (dayModelList.size() / 7 > 0) {

                int extra = dayModelList.size() % 7;

                if (extra > 0) {
                    extra = 7 - extra;
                }

                for (int k = 0; k < extra; k++) {
                    DayModel dayModel = new DayModel();
                    dayModel.setType(102031);
                    dayModel.setCalendarModel(new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, k + 1));
                    dayModelList.add(dayModel);
                }

                if (extra > 0) {

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = new Date(dayModelList.get(dayModelList.size() - 1).getCalendarModel().getTimeInMillis());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        calendarAdapter.mCalendarList = dayModelList;
        calendarAdapter.notifyDataSetChanged();
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

}