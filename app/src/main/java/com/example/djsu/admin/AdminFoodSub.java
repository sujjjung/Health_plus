package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.djsu.R;

public class AdminFoodSub extends AppCompatActivity {

    ImageButton food_changeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_sub);

        food_changeBtn = (ImageButton)findViewById(R.id.changeBtn);

        food_changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminFoodSub.this, AdminFoodSubInput.class);
                startActivity(intent);
            }
        });
    }
}