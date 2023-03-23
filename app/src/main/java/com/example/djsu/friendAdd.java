package com.example.djsu;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;
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
import java.util.List;

public class friendAdd extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //뒤로가기버튼 이미지 적용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        ListView listView = findViewById(R.id.UserList);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<member> postList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    member member = snapshot.getValue(member.class);
                    postList.add(member);
                }
                // ListView에 데이터를 표시하는 코드 작성
                friendAddAdapter friendAddAdapter = new friendAddAdapter(friendAdd.this, postList);
                listView.setAdapter(friendAddAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 읽기에 실패한 경우 호출되는 콜백 메서드
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
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
                }
                return false;
            }
        });
    } //onCreate

}