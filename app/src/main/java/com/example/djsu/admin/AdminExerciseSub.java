package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.djsu.R;

public class AdminExerciseSub extends AppCompatActivity {

    ImageButton exerciseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_sub);

        exerciseBtn = (ImageButton)findViewById(R.id.addBtn);

        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminExerciseSub.this, AdminExerciseSubInput.class);
                startActivity(intent);
            }
        });
    }


}