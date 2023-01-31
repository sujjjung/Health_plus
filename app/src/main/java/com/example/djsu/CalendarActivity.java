package com.example.djsu;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CalendarActivity extends AppCompatActivity {

    // 플로팅버튼 상태
    private boolean fabMain_status = false;

    private FloatingActionButton fabMain;
    private FloatingActionButton fabHealth;
    private FloatingActionButton fabFood;

    private FloatingActionButton fabKg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        fabMain = findViewById(R.id.floatingMain);
        fabHealth = findViewById(R.id.floatingHealth);
        fabFood = findViewById(R.id.floatingFood);
        fabKg = findViewById(R.id.floatingKg);

        // 메인플로팅 버튼 클릭
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });
        // 식단 플로팅 버튼 클릭
        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, FoodAddActivity.class);
                startActivity(intent);
            }
        });

        // 운동 플로팅 버튼 클릭
        fabHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, HealthAddActivity.class);
                startActivity(intent);
            }
        });

        fabKg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, WeightActivity.class);
                startActivity(intent);
            }
        });
    }

    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    public void toggleFab() {
        if(fabMain_status) {
            // 플로팅 액션 버튼 닫기
            // 애니메이션 추가
            ObjectAnimator fk_animation = ObjectAnimator.ofFloat(fabKg, "translationY", 0f);
            fk_animation.start();
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabFood, "translationY", 0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabHealth, "translationY", 0f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_action_plus);

        }else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabFood, "translationY", -200f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabHealth, "translationY", -400f);
            fe_animation.start();
            ObjectAnimator fk_animation = ObjectAnimator.ofFloat(fabKg, "translationY", -600f);
            fk_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_action_plus);
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
    }


}