package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class main_user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        ImageButton view_exercise = (ImageButton) findViewById(R.id.view_exercise);
        view_exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HealthAddActivity.class);
                startActivity(intent);
            }
        });

        ImageButton view_food = (ImageButton) findViewById(R.id.view_food);
        view_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodAddActivity.class);
                startActivity(intent);
            }
        });

        Button announcement_btn = (Button) findViewById(R.id.announcement_btn);
        announcement_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), annoucement.class);
                startActivity(intent);
            }
        });

        ImageButton hamburger = (ImageButton) findViewById(R.id.menu);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HamburgerActivity.class);
                startActivity(intent);
            }
        });
    }
}