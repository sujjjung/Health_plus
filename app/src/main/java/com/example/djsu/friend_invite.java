package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class friend_invite extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_invite);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //뒤로가기버튼 이미지 적용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        Button addBtn = findViewById(R.id.button3);
        Button backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ListView listView = findViewById(R.id.recycler_messages);

        User user = new User();
        String userName = user.getId();

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
                friend_invite_Adapter friend_invite_Adapter = new friend_invite_Adapter(friend_invite.this, postList);
                listView.setAdapter(friend_invite_Adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터 읽기에 실패한 경우 호출되는 콜백 메서드
            }
        });

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // 선택된 회원들을 담을 ArrayList
                ArrayList<String> selectedMembers = new ArrayList<>();

                // ListView에서 체크박스에 체크된 아이템들을 찾아서 ArrayList에 담기
                for (int i = 0; i < listView.getCount(); i++) {
                    View view = listView.getChildAt(i);
                    CheckBox checkBox = view.findViewById(R.id.checkbox);

                    if (checkBox.isChecked()) {
                        // 체크된 아이템에서 회원 이름을 가져와서 ArrayList에 추가
                        TextView textView = view.findViewById(R.id.name);
                        selectedMembers.add(textView.getText().toString());
                    }
                }

                // 선택된 회원이 없으면 메시지를 띄우고 함수를 종료
                if (selectedMembers.isEmpty()) {
                    Toast.makeText(friend_invite.this, "선택된 회원이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } //여기 부분 돌아감

                // 채팅방 생성
                String chatRoomId = database.child("User").child(userName).child("chatRooms").push().getKey();
                ChatRoom chatRoom = new ChatRoom(chatRoomId);
                database.child("User").child(userName).child("chatRooms").child(chatRoomId).setValue(chatRoom);

                // 선택된 회원들을 채팅방에 참여시킴
                for (String member : selectedMembers) {
                    // 회원 이름으로 회원의 UID를 가져옴
                    DatabaseReference memberRef = database.child("User").orderByChild("name").equalTo(member).getRef();
                    memberRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String memberId = dataSnapshot.getChildren().iterator().next().getKey();
                                database.child("User").child(userName).child("chatRooms").child(chatRoomId).child("users").child(memberId).setValue(true);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("friend_invite", "Failed to get member ID", databaseError.toException());
                        }
                    });
                }

                // 채팅방 화면으로 이동
                Intent intent = new Intent(friend_invite.this, chatList.class);
                intent.putExtra("chatRooms", chatRoomId);
                startActivity(intent);
            }
        });



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