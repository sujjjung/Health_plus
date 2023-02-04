package com.example.djsu.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.djsu.R;

public class AdminMainActivity extends AppCompatActivity {

    ImageButton food_list, user_list;

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
    }
}