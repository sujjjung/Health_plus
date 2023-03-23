package com.example.djsu;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeightActivity extends AppCompatActivity {
    private ImageButton savebtn;
    private EditText et_weight, et_muscle, et_fat;
    private String userId, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        // EditText 선언
        et_weight = findViewById(R.id.weight_text);
        et_fat = findViewById(R.id.muscles_text);
        et_muscle = findViewById(R.id.fat_text);

        // 저장 버튼 실행
        savebtn = findViewById(R.id.SaveBtn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();

                userId = user.getId();
                date = getTime();

                String fat = et_fat.getText().toString();
                String muscle = et_muscle.getText().toString();
                String weight = et_weight.getText().toString();


                // 서버로 Volley를 이용해서 요청을 함.
                weightRequest weightRequest1 = new weightRequest(userId, date, fat, muscle, weight);
                RequestQueue queue = Volley.newRequestQueue(WeightActivity.this);
                queue.add(weightRequest1);
                Toast.makeText(getApplicationContext(),"오늘의 체중이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WeightActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
    // 날짜 형태 변환
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}