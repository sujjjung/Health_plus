package com.example.djsu;

import android.content.Context;
import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.email_editText);
        et_pass = findViewById(R.id.password_editText);
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = et_id.getText().toString();
                String UserPassword = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO : 인코딩 문제때문에 한글 DB인 경우 로그인 불가
                            System.out.println("hongchul" + response);
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success && UserID.equals("admin")) { // 로그인에 성공한 경우
                                //String userName = jsonObject.getString("username");
                                Toast.makeText(getApplicationContext(), "관리자로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                new BackgroundTask2().execute();
                            }
                            else  if (success) {
                                String name = jsonObject.getString("UserName");
                                String state = jsonObject.getString("State");
                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, main_user.class);
                                User user = new User();
                                user.setName(name);
                                user.setId(UserID);
                                user.setState(state);
                                UserRequest userRequest = new UserRequest(user.getId());
                                RequestQueue queue = Volley.newRequestQueue(login.this);
                                queue.add(userRequest);
                                startActivity(intent);
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
    class BackgroundTask2 extends AsyncTask<Void, Void, String> {
        String target;
        @Override
        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://enejd0613.dothome.co.kr/userlist.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);//URL 객체 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(login.this, AdminMainActivity.class);
            intent.putExtra("userlist",result);
            startActivity(intent);
            login.this.startActivity(intent);
        }
    }
}