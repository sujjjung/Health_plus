package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class community extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        Button mypage_btn = (Button) findViewById(R.id.mypage_btn);
        mypage_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), community_mypage.class);
                startActivity(intent);
            }
        });

        Button plusBtn = (Button) findViewById(R.id.button2);
        plusBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), community_post.class);
                startActivity(intent);
            }
        });

        // ImageButton commentButton=findViewById(R.id.chat_bubble_btn);
        // findViewById(R.id.button4) : 버튼의 text가 아니라 Id값을 넣어줘야한다.
        // commentButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Intent intent=new Intent(getApplicationContext(),comment.class);
        //        startActivity(intent);
        //    }
        // });
    }
}