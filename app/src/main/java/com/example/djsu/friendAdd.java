package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class friendAdd extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private friendAddAdapter adapter;
    private EditText searchEditText;
    private ArrayList<member> itemList;
    private ArrayList<member> filteredList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        ListView listView = findViewById(R.id.UserList);
        searchEditText = findViewById(R.id.searchtext);

        itemList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new friendAddAdapter(this, filteredList);
        listView.setAdapter(adapter);

        User user = new User();
        String userid = user.getId();

        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = charSequence.toString().toLowerCase();
                filterList(searchText);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User").child(userid).child("friend");

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> excludedIds = new ArrayList<>();
                for (DataSnapshot excludedIdSnapshot : snapshot.getChildren()) {
                    String excludedId = excludedIdSnapshot.getKey();
                    excludedIds.add(excludedId);
                }

                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("User");
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<member> postList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            member member = snapshot.getValue(member.class);
                            if (member.getNondisclosure().equals("공개") && !excludedIds.contains(member.getId()) && !member.getId().equals(userid) && !member.getId().equals("admin")) {
                                postList.add(member);
                            }
                        }
                        itemList.clear();
                        itemList.addAll(postList);
                        filterList(""); // 초기 데이터로 검색 기능을 설정합니다.
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 데이터 읽기에 실패한 경우 호출되는 콜백 메서드
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 처리 중 오류 발생 시
            }
        });

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
    }

    private void filterList(String searchText) {
        filteredList.clear();

        if (TextUtils.isEmpty(searchText)) {
            filteredList.addAll(itemList);
        } else {
            for (member item : itemList) {
                if (item.getName().toLowerCase().contains(searchText) || item.getId().toLowerCase().contains(searchText)) {
                    filteredList.add(item);
                }
            }
        }

        adapter.notifyDataSetChanged();
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
