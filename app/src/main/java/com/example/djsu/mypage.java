package com.example.djsu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mypage extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private TextView name, state;
    private ImageView ivImage;
    private String profile;

    final static private String URL = "http:enejd0613.dothome.co.kr/Register.php";
    private static String TAG = "djsu";

    private String mJsonString = "";

    private static final String TAG_JSON = "webnautes";
    private static final String TAG_USERID = "userId";
    private static final String TAG_DATE = "date";
    private static final String TAG_FAT = "fat";
    private static final String TAG_MUSCLE = "muscle";
    private static final String TAG_WEIGHT = "wegiht";

    ArrayList<String> dateChart = new ArrayList<>();
    ArrayList<Integer> weightChart = new ArrayList<>();
    ArrayList<Integer> fatChart = new ArrayList<>();
    ArrayList<Integer> muscleChart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        User user = new User();
        name = findViewById(R.id.name_textView);
        name.setText(user.getName());
        state = findViewById(R.id.Status_message_text);
        state.setText(user.getState());

        Button user_edit = (Button) findViewById(R.id.change_btn);
        user_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), user_data_edit.class);
                startActivity(intent);
            }
        });

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

                                    if (success.equals("1")) { // 회원등록에 성공한 경우
                                        Toast.makeText(getApplicationContext(), "회원 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mypage.this, MainActivity.class);
                                        startActivity(intent);  //intent를 넣어 실행시키게 됩니다.
                                    } else { // 회원등록에 실패한 경우
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

        profile = user.getProfile();

        ivImage = findViewById(R.id.profile);

        // Glide로 이미지 표시하기
        String imageUrl = profile;
        Glide.with(this).load(imageUrl).into(ivImage);

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
}