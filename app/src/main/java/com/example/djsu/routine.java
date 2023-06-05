package com.example.djsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class routine extends AppCompatActivity {
    private List<User> routineList;
    private userRoutineAdapter userRoutineAdapter;
    private EditText editText;
    // 햄버거 버튼
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    String Date;
    Button RoutineAddBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        Bundle extras = getIntent().getExtras();
        editText = findViewById(R.id.search_routine);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<User> search_list = new ArrayList<>();
                String searchText = editText.getText().toString();
                search_list.clear();

                if (searchText.equals("")) {
                    userRoutineAdapter.setItems((ArrayList<User>) routineList);
                } else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < routineList.size(); a++) {
                        if (routineList.get(a).getRoutineName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(routineList.get(a));
                        }
                        userRoutineAdapter.setItems(search_list);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        Date = extras.getString("Date");
        Intent intent = getIntent();
        routineList = new ArrayList<>();
        userRoutineAdapter = new userRoutineAdapter(this,routineList,Date);
        ListView exView = (ListView) findViewById(R.id.recycler_routine);
        exView.setAdapter(userRoutineAdapter);
        User user1 = new User();

        try {
            userRoutineAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("UserRoutine"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String RoutineName,UserID,ExerciseCode,ExerciseName,ExercisePart;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);
                RoutineName = object.getString("RoutineName");
                ExerciseCode = object.getString("ExerciseCode");
                ExerciseName = object.getString("ExerciseName");
                ExercisePart = object.getString("ExercisePart");
                User user = new User();
                user.AddRoutine(RoutineName,ExerciseCode,ExerciseName,ExercisePart);
                UserID = object.getString("UserID");
                if(UserID.equals(user1.getId())) {
                    routineList.add(user);//리스트뷰에 값을 추가해줍니다
                }
                count++;
            };



        } catch (Exception e) {
            e.printStackTrace();
        }
        RoutineAddBtn = findViewById(R.id.RoutineAdd);

        RoutineAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(routine.this);
                View view = LayoutInflater.from(routine.this).inflate(R.layout.dialog_routine_name, null, false);
                builder.setView(view);
                final Button SaveButton = (Button) view.findViewById(R.id.saveBtn);
                final Button BackButton = (Button) view.findViewById(R.id.backBtn);
                final EditText RoutineNameText = (EditText) view.findViewById(R.id.RoutineName);
                final AlertDialog dialog = builder.create();
                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(routine.this, HealthAddActivity.class);
                        intent.putExtra("Date", Date);
                        intent.putExtra("RoutineNameText", RoutineNameText.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                });
                BackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
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
                        mainkcalBackgroundTask mainkcalBackgroundTask = new mainkcalBackgroundTask(routine.this);
                        mainkcalBackgroundTask.execute();
                        return true;
                    case R.id.calender:
                        UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(routine.this);
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
                        NoticeBackgroundTask noticeBackgroundTask = new NoticeBackgroundTask(routine.this);
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