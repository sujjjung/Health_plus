package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class target extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        Button settingButton=findViewById(R.id.setting_btn);
        //findViewById(R.id.button4) : 버튼의 text가 아니라 Id값을 넣어줘야한다.
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),target_setting.class);
                startActivity(intent);
            }
        });
    }
}