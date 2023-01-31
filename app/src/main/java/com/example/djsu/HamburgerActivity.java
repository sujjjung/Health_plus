package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HamburgerActivity extends AppCompatActivity {

    Button homeBtn, calenBtn, snsBtn, mypageBtn, mapBtn, runBtn, noticeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamburger);

        homeBtn = (Button)findViewById(R.id.homeBtn);
        calenBtn = (Button)findViewById(R.id.calenBtn);
        snsBtn = (Button)findViewById(R.id.snsBtn);
        mypageBtn = (Button)findViewById(R.id.mypageBtn);
        mapBtn = (Button)findViewById(R.id.mapBtn);
        runBtn = (Button)findViewById(R.id.runBtn);
        noticeBtn = (Button)findViewById(R.id.noticeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HamburgerActivity.this, main_user.class);
                startActivity(intent);
            }
        });

        calenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HamburgerActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        snsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HamburgerActivity.this, main_user.class);
                startActivity(intent);
            }
        });

        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HamburgerActivity.this, main_user.class);
                startActivity(intent);
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HamburgerActivity.this, main_user.class);
                startActivity(intent);
            }
        });

        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HamburgerActivity.this, main_user.class);
                startActivity(intent);
            }
        });

        noticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HamburgerActivity.this, main_user.class);
                startActivity(intent);
            }
        });
    }
}