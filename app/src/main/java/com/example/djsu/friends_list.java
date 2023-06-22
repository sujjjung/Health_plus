package com.example.djsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class friends_list extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private EditText editText;

    private ArrayList<member> itemList;
    private ArrayList<member> filteredList;
    private friendlistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        ListView listView = findViewById(R.id.recycler_messages);
        editText = findViewById(R.id.searchtext);

        itemList = new ArrayList<>();
        filteredList = new ArrayList<>();
        adapter = new friendlistAdapter(this, filteredList);
        listView.setAdapter(adapter);

        User user = new User();
        String userName = user.getId();

        editText.addTextChangedListener(new TextWatcher() {
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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userName).child("friend");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<member> postList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    member member = snapshot.getValue(member.class);
                    postList.add(member);
                }
                // ListView에 데이터를 표시하는 코드 작성
//                friendlistAdapter friendlistAdapter = new friendlistAdapter(friends_list.this, postList);
//                listView.setAdapter(friendlistAdapter);

                itemList.clear();
                itemList.addAll(postList);
                filterList("");

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 읽기에 실패한 경우 호출되는 콜백 메서드
            }
        });


        Button btn =findViewById(R.id.delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),friends_remove.class);
                startActivity(intent);
            }
        });

        Button btn2 =findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),friendAdd.class);
                startActivity(intent);
            }
        });

        // 햄버거
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
}