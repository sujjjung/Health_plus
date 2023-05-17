package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chat_room extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatData> chatList;
    private EditText EditText_chat;
    private Button Button_send;
    private DatabaseReference myRef;
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        return currentTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button_send = findViewById(R.id.btn_submit);
        EditText_chat = findViewById(R.id.edt_message);

        User user = new User();
        String userId = user.getId();
        String userName = user.getName();

        Button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = EditText_chat.getText().toString();
                if(msg != null) {
                    ChatData chat = new ChatData();
                    chat.setUserName(userName);
                    chat.setMsg(msg);

                    // 현재 시간을 가져와서 ChatData 객체에 설정
                    String currentTime = getCurrentTime();
                    chat.setDate(currentTime);

                    myRef.push().setValue(chat);
                }
            }
        });

        mRecyclerView = findViewById(R.id.recycler_messages);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        chatList = new ArrayList<>();
        mAdapter = new ChatRoomAdapter(chatList, chat_room.this, userName);

        mRecyclerView.setAdapter(mAdapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Message");
        //myRef = database.getReference("User").child(userId).child("Message");
        //DatabaseReference myRef = database.getReference("Message");

//        ChatData chat = new ChatData();
//        chat.setUserName(userName);
//        chat.setMsg("hi");
//        myRef.setValue(chat);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatData chat = snapshot.getValue(ChatData.class);
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