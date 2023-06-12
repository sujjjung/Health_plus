package com.example.djsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class routine extends AppCompatActivity {
    private List<User> routineList;
    private userRoutineAdapter userRoutineAdapter;
    private ArrayList<User> search_list;
    private EditText editText;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_UserId  = "userId";
    private static final String TAG_ExName = "ExerciseName";
    private static final String TAG_ExPart = "ExercisePart";
    private static final String TAG_RoutineName = "RoutineName";
    private static final String TAG_ExerciseCode = "ExerciseCode";
    String myJSON;
    JSONArray peoples = null;
    User user;
    // 햄버거 버튼
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    String Date, searchName = "";
    Button RoutineAddBtn, editBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        Bundle extras = getIntent().getExtras();
        search_list = new ArrayList<>();
        editText = findViewById(R.id.search_routine);


        Date = extras.getString("Date");
        routineList = new ArrayList<>();
        userRoutineAdapter = new userRoutineAdapter(this,routineList,Date);
        ListView exView = (ListView) findViewById(R.id.recycler_routine);
        exView.setAdapter(userRoutineAdapter);
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

                if(searchText.equals("")){
                    userRoutineAdapter.setItems((ArrayList<User>) routineList);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < routineList.size(); a++) {
                        if (routineList.get(a).getRoutineName().toLowerCase().contains(searchText.toLowerCase())) {
                            if(searchName.equals(routineList.get(a).getRoutineName()) == false){
                                search_list.add(routineList.get(a));
                                searchName = routineList.get(a).getRoutineName();
                            }
                        }
                        userRoutineAdapter.setItems(search_list);
                    }
                }
            }

        });
        RoutineAddBtn = findViewById(R.id.RoutineAdd);
        editBtn = findViewById(R.id.edit);
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
                        for (int i = 0; i < routineList.size(); i++) {
                            if (routineList.get(i).getRoutineName().equals(RoutineNameText.getText().toString())) {
                                Toast.makeText(routine.this, "동일한 이름의 루틴이 있습니다!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;
                            }
                        }
                        Intent intent = new Intent(routine.this, HealthAddActivity.class);
                        intent.putExtra("Date", Date);
                        intent.putExtra("RoutineNameText", RoutineNameText.getText().toString());
                        startActivity(intent);
                        dialog.dismiss();
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
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRoutineAdapter.ButtonChange(1);
                userRoutineAdapter.notifyDataSetChanged();
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
                    case R.id.map:
                        Intent mapintent = new Intent(getApplicationContext(), map.class);
                        startActivity(mapintent);
                        return true;
                    case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), pedometer.class);
                        startActivity(manbogiintent);
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
        getData("http://enejd0613.dothome.co.kr/Routinelist.php");
    }
    protected void showList() {

        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);

                    if(UserId.equals(user.getId())) {
                        String ExerciseName = c.getString(TAG_ExName);
                        String ExercisePart = c.getString(TAG_ExPart);
                        String RoutineName = c.getString(TAG_RoutineName);
                        String ExerciseCode = c.getString(TAG_ExerciseCode);
                        user = new User(RoutineName,ExerciseCode,ExercisePart,ExerciseName);
                        routineList.add(user);//리스트뷰에 값을 추가해줍니다
                    }
                }

            }
            userRoutineAdapter.notifyDataSetChanged();
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