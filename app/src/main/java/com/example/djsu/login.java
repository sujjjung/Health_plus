package com.example.djsu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.djsu.admin.AdminMainActivity;
import com.example.djsu.admin.AdminNoticeMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class login extends AppCompatActivity {
    private EditText et_id, et_pass;
    private Button login_btn, btn_register;

    public static Context context_email;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.email_editText);
        et_pass = findViewById(R.id.password_editText);
        login_btn = findViewById(R.id.login_btn);

        mDatabase = FirebaseDatabase.getInstance().getReference("User");

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = et_id.getText().toString();
                String UserPassword = et_pass.getText().toString();

                mDatabase.child(UserID).child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if(UserID.length() == 0 || UserPassword.length() == 0){
                            Toast.makeText(login.this, "모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else if(value == null) {
                            Toast.makeText(login.this, "없는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                            et_pass.setText(null);
                        }else if(!value.equals(UserPassword)){
                            Toast.makeText(login.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            et_pass.setText(null);
                        }else{
                            Toast.makeText(login.this, et_id.getText().toString() + " 님 환영합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(login.this, "인터넷 연결 실패", Toast.LENGTH_SHORT).show();
                    }
                });

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success && UserID.equals("admin")) { // 로그인에 성공한 경우
                                Toast.makeText(getApplicationContext(), "관리자로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, AdminMainActivity.class);
                                startActivity(intent);
                            }
                            else  if (success) {
                                String name = jsonObject.getString("UserName");
                                String state = jsonObject.getString("State");
                                String profile = jsonObject.getString("UserProfile");
                                String age = jsonObject.getString("UserAge");
                                String password = jsonObject.getString("UserPassword");
                                //Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                User user = new User();
                                user.setName(name);
                                user.setId(UserID);
                                user.setState(state);
                                user.setProfile(profile);
                                user.setAge(age);
                                user.setPassword(password);
                                Intent Mainintent = new Intent(getApplicationContext(), main_user.class);
                                startActivity(Mainintent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginrequest = new LoginRequest(UserID, UserPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(login.this);
                queue.add(loginrequest);
            }
        });
    }
}