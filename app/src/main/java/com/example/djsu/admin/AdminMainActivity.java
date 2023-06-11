package com.example.djsu.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;;
import com.example.djsu.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminMainActivity extends AppCompatActivity {

    ImageButton food_list, user_list, exercise_list, notice_list,declarationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        food_list = (ImageButton)findViewById(R.id.adminfoodBtn);
        food_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminFoodMain.class);
                startActivity(intent);
            }
        });

        user_list = (ImageButton)findViewById(R.id.adminuserBtn);
        user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminUserListActivity.class);
                startActivity(intent);
            }
        });

        notice_list = (ImageButton)findViewById(R.id.NoticeBtn);
        notice_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminNoticeMain.class);
                startActivity(intent);
            }
        });

        exercise_list = (ImageButton)findViewById(R.id.exerciseBtn);
        exercise_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminExerciseMain.class);
                startActivity(intent);
            }
        });

        declarationBtn = (ImageButton)findViewById(R.id.declarationBtn);

        declarationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMainActivity.this, AdminUserListActivity.class);
                startActivity(intent);
            }
        });
    }
}