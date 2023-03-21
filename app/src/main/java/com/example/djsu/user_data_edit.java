package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class user_data_edit extends AppCompatActivity {
    private EditText et_id, et_pass, et_name, et_age, et_pass2;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_edit);

        User user = new User();
        et_id = findViewById(R.id.name_editText);
        et_id.setText(user.getId());
        et_pass = findViewById(R.id.password_editText);
        et_pass.setText(user.getPassword());
        et_pass2 = findViewById(R.id.password_editText2);
        et_pass2.setText(user.getPassword());
        et_name = findViewById(R.id.name_editText);
        et_name.setText(user.getName());
        et_age = findViewById(R.id.birth_editText);
        et_age.setText(user.getAge());

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.signup_btn);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = user.getId();
                String UserName = et_name.getText().toString();
                String UserAge = et_age.getText().toString();
                String UserPass = et_pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) { // 회원등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "회원 정보 변경에 성공하였습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(user_data_edit.this, MainActivity.class);
                                startActivity(intent);
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "회원 정보 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                user_data_editRequest user_data_editRequest1 = new user_data_editRequest(UserID, UserPass, UserName, UserAge, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_data_edit.this);
                queue.add(user_data_editRequest1);
            }
        });
    }
}