package com.example.djsu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
    private Button Button_send, Button_out, Button_back;
    private ImageButton btn_Battle;
    private DatabaseReference myRef;
    private TextView roomName;


    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        return currentTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        btn_Battle = findViewById(R.id.btn_plus);
        Button_out = findViewById(R.id.backBtn);
        Button_send = findViewById(R.id.btn_submit);
        EditText_chat = findViewById(R.id.edt_message);
        roomName = findViewById(R.id.txt_Title);
        Button_back = findViewById(R.id.backButton);

        User user = new User();
        String userName = user.getName();

        mRecyclerView = findViewById(R.id.recycler_messages);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(chat_room.this, chatList.class);
                startActivity(intent2);
            }
        });

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
                    EditText_chat.setText(null);
                }
            }
        });

        chatList = new ArrayList<>();
        mAdapter = new ChatRoomAdapter(chatList, chat_room.this, userName, mRecyclerView);

        mRecyclerView.setAdapter(mAdapter);

        //chatList 에서 클릭된 아이템의 텍스트를 가져온다
        String roomId = getIntent().getStringExtra("chatRoomId");
        String RoomName = getIntent().getStringExtra("roomName");

//        if(RoomName.length() >= 8) {
//            RoomName = RoomName.substring(0, 13) + "...";
//        }

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

                                    // 채팅방에 나갔음을 메시지로 보냅니다.
                                    String messageText = currentUserName + "님이 채팅방을 나갔습니다.";
                                    sendMessageToChatRoom(roomId, "더하기톡 알림", messageText);
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

        btn_Battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(chat_room.this, battle.class);
                intent1.putExtra("chatRoomId", roomId);
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

    // 채팅방에 메시지를 보내는 함수 -> 나가기 버튼을 클릭하면 해당 회원이 나갔다는 메시지가 자동으로 보내짐
    private void sendMessageToChatRoom(String roomId, String senderName, String messageText) {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference("ChatRoom").child(roomId);
        DatabaseReference messagesRef = roomRef.child("Message");

//        long timestamp = System.currentTimeMillis();
        ChatRoom chatMessage = new ChatRoom();
        chatMessage.setUserName(senderName);
        chatMessage.setMsg(messageText);
        chatMessage.setDate(getCurrentTime());

        // Firebase Realtime Database에 메시지 저장
        messagesRef.push().setValue(chatMessage);
    }

}