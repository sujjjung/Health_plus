package com.example.djsu;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.djsu.admin.AdminMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class main_user extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private TextView name, state,kcalText;
    private String profile;

    private String ID,date;
    private EditText et_status;

    private ImageView ivImage;

    private TextView water;
    private int count;
    int KcalNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        kcalText = findViewById(R.id.kcalText);
        water = findViewById(R.id.water_tv);
        water.setText(count+"");
        date = getTime();
        ImageButton plus = (ImageButton) findViewById(R.id.plusBtn);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count+100;
                water.setText(count+"");
            }
        });

        ImageButton minus = (ImageButton) findViewById(R.id.minusBtn);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count-100;
                water.setText(count+"");
            }
        });

        ImageButton view_food = (ImageButton) findViewById(R.id.view_food);
        view_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodAddActivity.class);
                startActivity(intent);
            }
        });

        Button announcement_btn = (Button) findViewById(R.id.announcement_btn);
        announcement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoticeBackgroundTask noticeBackgroundTask = new NoticeBackgroundTask(main_user.this);
                noticeBackgroundTask.execute();
            }
        });

        name = findViewById(R.id.username);
        User user = new User();
        name.setText(user.getName());
        state = findViewById(R.id.Status_message_text);
        state.setText(user.getState());

        ID = user.getId();

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
                        mainkcalBackgroundTask mainkcalBackgroundTask = new mainkcalBackgroundTask(main_user.this);
                        mainkcalBackgroundTask.execute();
                        return true;
                    case R.id.calender:
                        UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(main_user.this);
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
                        NoticeBackgroundTask noticeBackgroundTask = new NoticeBackgroundTask(main_user.this);
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
        Intent intent = getIntent();
        User user1 = new User();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("UserFood"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            String Date,UserID,FoodKcal;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);
                Date = object.getString("Date");
                FoodKcal = object.getString("FoodKcal");
                //값들을 User클래스에 묶어줍니다
                UserID = object.getString("UserID");
                if(UserID.equals(user1.getId())) {
                    if(Date.equals(date)) {
                        KcalNum +=  Integer.parseInt(FoodKcal);
                        kcalText.setText(String.valueOf(KcalNum));
                    }
                }
                count++;
            };

        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView text1 = findViewById(R.id.Status_message_text);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = user.getId();
                String text = text1.getText().toString();

                dialog_status alert = new dialog_status(main_user.this,text);
                alert.callFunction();
                alert.setModifyReturnListener(new dialog_status.ModifyReturnListener() {
                    @Override
                    public void afterModify(String text) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");

                                    if (success.equals("1")) { // 회원등록에 성공한 경우
                                        text1.setText(text);
                                        System.out.println("!!!!!!!!!!!!!: " +text);
                                        Toast.makeText(getApplicationContext(), "한줄소개가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                    } else { // 회원등록에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "회원 정보 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        // 서버로 Volley를 이용해서 요청을 함.
                        StatusRequest stateRequest1 = new StatusRequest(UserID, text, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(main_user.this);
                        queue.add(stateRequest1);
                    }
                });

            }
        });

        profile = user.getProfile();

        ivImage = findViewById(R.id.imageView2);

        // Glide로 이미지 표시하기
        String imageUrl = profile;
        Glide.with(this).load(imageUrl).into(ivImage);

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