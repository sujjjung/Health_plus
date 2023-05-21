package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.djsu.Notice;
import com.example.djsu.R;
import com.example.djsu.exerciseLsit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminExerciseMain extends AppCompatActivity {
    private List<exerciseLsit> exerciselsit;
    private AdminExerciseAdapter exerciseAdapter;
    ImageButton ExerciseAddBtn;

    private AdminExerciseAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_main);

        ExerciseAddBtn = (ImageButton)findViewById(R.id.exerciseaddBtn);

        ExerciseAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminExerciseMain.this, AdminExerciseSubAdd.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        exerciselsit = new ArrayList<>();

        exerciseAdapter = new AdminExerciseAdapter(this, exerciselsit);

        ListView exListView = (ListView) findViewById(R.id.listView);
        exListView.setAdapter(exerciseAdapter);

        try {
            exerciseAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("Ex"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String ExName;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);

                ExName = object.getString("ExName");
                //값들을 User클래스에 묶어줍니다
                exerciseLsit exlsit = new exerciseLsit(ExName);
                exerciselsit.add(exlsit);//리스트뷰에 값을 추가해줍니다
                count++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}