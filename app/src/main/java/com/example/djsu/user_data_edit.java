package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_data_edit extends AppCompatActivity {
    private Button btn_register;

    private static final String TAG = user_data_edit.class.getSimpleName();
    private EditText et_id, et_pass, et_name, et_age, et_pass2, et_state;
    // private Button btn_logout, btn_photo;
    // SessionManager sessionManager;
    // String getId;
    private static final String URL_EDIT = "http://enejd0613.dothome.co.kr/user_info_edit.php";
    private static final String URL_UPLOAD = "http://enejd0613.dothome.co.kr/upload_profile.php";
    private Bitmap bitmap;
    CircleImageView user;

    DatabaseReference databaseReference;

    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_edit);

        et_id = findViewById(R.id.name_editText);
        et_pass = findViewById(R.id.password_editText);
        et_pass2 = findViewById(R.id.password_editText2);
        et_name = findViewById(R.id.name_editText);
        et_age = findViewById(R.id.birth_editText);
        et_state = findViewById(R.id.state_editText);
        btn_register = findViewById(R.id.signup_btn);

        User user = new User();
        et_id.setText(user.getId());
        et_pass.setText(user.getPassword());
        et_pass2.setText(user.getPassword());
        et_name.setText(user.getName());
        et_age.setText(user.getAge());
        et_state.setText(user.getState());

        // 회원가입 버튼 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = user.getId();
                String UserName = et_name.getText().toString();
                String UserAge = et_age.getText().toString();
                String UserPass = et_pass.getText().toString();
                String State = et_state.getText().toString();

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
                user_data_editRequest user_data_editRequest1 = new user_data_editRequest(UserID, UserPass, UserName, UserAge, State, responseListener);
                RequestQueue queue = Volley.newRequestQueue(user_data_edit.this);
                queue.add(user_data_editRequest1);
            }
        });
    }
}