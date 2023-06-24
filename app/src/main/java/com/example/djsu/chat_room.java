package com.example.djsu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.djsu.admin.AdminMainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class chat_room extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatRoom> chatList;
    private EditText EditText_chat;
    private Button Button_send, Button_out;
    private DatabaseReference myRef, backRoom;
    private TextView roomName;

    private FirebaseDatabase firebase;

    private DatabaseReference database, sourceRef, destinationRef;

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        return currentTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button_out = findViewById(R.id.backBtn);
        Button_send = findViewById(R.id.btn_submit);
        EditText_chat = findViewById(R.id.edt_message);
        roomName = findViewById(R.id.txt_TItle);

        User user = new User();
        String userId = user.getId();
        String userName = user.getName();

        mRecyclerView = findViewById(R.id.recycler_messages);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = EditText_chat.getText().toString();
                if(msg != null) {
                    ChatRoom chat = new ChatRoom();
                    chat.setUserName(userName);
                    chat.setMsg(msg);

                    // 현재 시간을 가져와서 ChatData 객체에 설정
                    String currentTime = getCurrentTime();
                    chat.setDate(currentTime);

                    myRef.push().setValue(chat);
                }
            }
        });


        chatList = new ArrayList<>();
        mAdapter = new ChatRoomAdapter(chatList, chat_room.this, userName, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        //chatList 에서 클릭된 아이템의 텍스트를 가져온다
        Intent intent = getIntent();
        String roomId = getIntent().getStringExtra("chatRoomId");
        String RoomName = getIntent().getStringExtra("roomName");

        roomName.setText(RoomName);

        ChatRoom chatRoom = new ChatRoom();
        String chatId = chatRoom.getChatRoomId();

        //나가기 버튼 클릭 시
        Button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference("ChatRoom").child(roomId);
                DatabaseReference memberRef = roomRef.child("member");

                // 현재 회원의 이름을 가져옵니다.
                String currentUserName = user.getName();

                // member 노드에 대한 ValueEventListener를 추가합니다.
                memberRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            boolean shouldDeleteRoom = false;

                            for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                                String memberName = memberSnapshot.child("name").getValue(String.class);

                                if (memberName != null && memberName.equals(currentUserName)) {
                                    // 현재 회원의 이름과 동일한 값을 가진 노드를 삭제합니다.
                                    memberSnapshot.getRef().removeValue();
                                }
                            }

                            // 자식 노드가 1개 이하인지 확인합니다.
                            if (dataSnapshot.getChildrenCount() <= 2) {
                                shouldDeleteRoom = true;
                            }

                            if (shouldDeleteRoom) {
                                // 채팅방 노드를 전체 삭제합니다.
                                roomRef.removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle error
                    }
                });

                Intent intent1 = new Intent(chat_room.this, chatList.class);
                startActivity(intent1);
            }
        });



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ChatRoom").child(roomId).child("Message");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatRoom chat = snapshot.getValue(ChatRoom.class);
                ((ChatRoomAdapter)mAdapter).addChat(chat);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}