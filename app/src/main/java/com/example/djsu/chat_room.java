package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class chat_room extends AppCompatActivity {

    public chat_room(String chatRoomId) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button BattleButton=findViewById(R.id.battle_btn);
        //findViewById(R.id.button4) : 버튼의 text가 아니라 Id값을 넣어줘야한다.
        BattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),target.class);
                startActivity(intent);
            }
        });
    }
}