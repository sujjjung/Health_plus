package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class chat_room_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);
        Button friendButton=findViewById(R.id.friend_lsit_btn);
        //findViewById(R.id.button4) : 버튼의 text가 아니라 Id값을 넣어줘야한다.
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),friend_list.class);
                startActivity(intent);
            }
        });
    }
}