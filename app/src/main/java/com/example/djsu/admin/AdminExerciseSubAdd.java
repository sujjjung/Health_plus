package com.example.djsu.admin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.djsu.EerciseRequest;
import com.example.djsu.FoodRequest;
import com.example.djsu.R;
import com.example.djsu.login;
import com.example.djsu.signup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminExerciseSubAdd extends AppCompatActivity {
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button saveBtn;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_sub_add);
        saveBtn = findViewById(R.id.SaveBtn);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if(id == R.id.lower_body)
                {        s = "하체";
                }else if(id == R.id.chest)
                {        s = "가슴";
                }else if(id == R.id.etc)
                {        s = "등";
                }else if(id == R.id.Abs)
                {        s = "복근";
                } else if(id == R.id.shoulder)
                {        s = "어깨";
                }else if(id == R.id.eight)
                {        s = "팔";
                }else if(id == R.id.aerobic)
                {        s = "유산소";
                }
            }
        });
        // 클릭이벤트
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hName=((EditText)findViewById(R.id.HealthNameEdit)).getText().toString();
                String hExplanation=((EditText)findViewById(R.id.HealthExplanationEdit)).getText().toString();
                String hKcal=((EditText)findViewById(R.id.FHealthKcalEdit)).getText().toString();
                String hunit=((EditText)findViewById(R.id.HealthunitEdit)).getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"운동 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminExerciseSubAdd.this, AdminExerciseSubAdd.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"운동 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                EerciseRequest eerciseRequest = new EerciseRequest(s,hName,hExplanation,hKcal,hunit,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdminExerciseSubAdd.this);
                queue.add(eerciseRequest);

            }
        });

    }
}