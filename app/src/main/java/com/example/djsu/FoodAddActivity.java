package com.example.djsu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FoodAddActivity extends AppCompatActivity {

    ImageButton food_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);

        food_input = (ImageButton)findViewById(R.id.food_input_btn);
        food_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodAddActivity.this, FoodAddInputActivity.class);
                startActivity(intent);
            }
        });
    }
}