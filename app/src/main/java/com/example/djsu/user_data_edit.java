package com.example.djsu;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_data_edit extends AppCompatActivity {
    private EditText et_id, et_pass, et_name, et_age, et_pass2, et_status;
    private Button btn_register, btn_photo;
    private Bitmap bitmap;
    ImageView UserProfile;
    private static String URL_UPLOAD = "http://enejd0613.dothome.co.kr/upload_profile.php";

    User user = new User();
    String UserID = user.getId();

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
        et_status = findViewById(R.id.status_editText);
        et_status.setText(user.getState());
        btn_photo = findViewById(R.id.btn_photo);
        UserProfile = findViewById(R.id.profile);
        
        // 회원가입 버튼 클릭 시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserName = et_name.getText().toString();
                String UserAge = et_age.getText().toString();
                String UserPass = et_pass.getText().toString();
                String State = et_status.getText().toString();

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

        // 사진 변경
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                UserProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadPicture(UserID, getStringImage(bitmap));
        }
    }

    private void uploadPicture(final String UserID, final String UserProfile)
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.e(TAG, response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(user_data_edit.this, "Success!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(user_data_edit.this, "Try Again! error : " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(user_data_edit.this, "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("UserID", UserID);
                params.put("UserProfile", UserProfile);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(

                20000 ,

                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,

                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(stringRequest);
    }

    public String getStringImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);
        return encodedImage;
    }
}