package com.example.djsu;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    private EditText et_id, et_pass, et_name, et_age;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        et_id = findViewById(R.id.email_editText);
        et_pass = findViewById(R.id.password_editText);
        et_name = findViewById(R.id.name_editText);
        et_age = findViewById(R.id.birth_editText);

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.signup_btn);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = et_id.getText().toString();
                String UserPass = et_pass.getText().toString();
                String UserName = et_name.getText().toString();
                String UserAge = et_age.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(signup.this, login.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                signupRequest signupRequest1 = new signupRequest(UserID,UserPass,UserName,UserAge,responseListener);
                RequestQueue queue = Volley.newRequestQueue(signup.this);
                queue.add(signupRequest1);
            }
        });
    }
}