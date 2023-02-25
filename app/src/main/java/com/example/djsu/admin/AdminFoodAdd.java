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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.djsu.FoodRequest;
import com.example.djsu.R;
import com.example.djsu.login;
import com.example.djsu.signup;
import com.example.djsu.signupRequest;
import com.example.djsu.target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminFoodAdd extends AppCompatActivity {
    private Button saveBtn;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_add);
        saveBtn = findViewById(R.id.SaveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName=((EditText)findViewById(R.id.FoodNameEdit)).getText().toString();
                String fKcal=((EditText)findViewById(R.id.FoodKcalEdit)).getText().toString();
                String fCarbohydrate=((EditText)findViewById(R.id.FoodCarbohydrateEdit)).getText().toString();
                String fProtein=((EditText)findViewById(R.id.FoodProteinEdit)).getText().toString();
                String fFat=((EditText)findViewById(R.id.FoodFatEdit)).getText().toString();
                String fSodium=((EditText)findViewById(R.id.FoodSodiumEdit)).getText().toString();
                String fSugar=((EditText)findViewById(R.id.FoodSugarEdit)).getText().toString();
                String fKg=((EditText)findViewById(R.id.FoodgEdit)).getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"음식 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminFoodAdd.this, AdminFoodAdd.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"음식 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                FoodRequest foodRequest = new FoodRequest(fName,fKcal,fCarbohydrate,fProtein,fFat,fSodium,fSugar,fKg,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdminFoodAdd.this);
                queue.add(foodRequest);

            }
        });

    }
}