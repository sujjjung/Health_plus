package com.example.djsu;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class main_user extends AppCompatActivity {
    // 햄버거 버튼 선언
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    // 프로필
    private TextView name, state,kcalText,ExkcalSum,ExTime;
    private String profile, ID;
    private Bitmap bitmap;
    private ImageView ivImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String URL_UPLOAD = "http://enejd0613.dothome.co.kr/upload_profile.php";
    private static final String TAG = "main_user";

    // 유저 정보 출력UserId
    private static final String TAG_RESULTS = "result";
    private static final String TAG_FoodDate = "Date";
    private static final String TAG_FoodUserId  = "UserId";
    private static final String TAG_FoodKcal = "FoodKcal";
    private static final String TAG_UserId  = "UserID";
    private static final String TAG_Date = "date";
    private static final String TAG_water = "water";
    // 운동
    // 운동시간(분) * (6 X 0.0175 X 몸무게)
    private static final String TAG_weight = "weight";
    private static final String TAG_FatId  = "userId";
    private static final String TAG_Time  = "Time";
    private float weight, minute,second,secondSum,ExKcalNum;
    User user = new User();
    String myJSON;
    JSONArray peoples = null;
    // 물
    private TextView water;
    waterRequest waterRequest;
    RequestQueue queue;
    String date;

    // 걸음수
    private int count,KcalNum = 0, watercount = 0;

    // 공지
//    String title;
//    String detail;

    String url = "http://enejd0613.dothome.co.kr/get_fat_main.php";

    // 프로그래스바 테스트

    private ProgressBar progressBar, progressBar1;
    private int progressStatus = 0;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        // 물 선언
        water = findViewById(R.id.water_tv);

        // 종하오빠가 뭐 해놓은거
        kcalText = findViewById(R.id.kcalText);
        ExkcalSum = findViewById(R.id.ExkcalSum);
        ExTime = findViewById(R.id.ExTime);
        date = getTime();
        // 물 + 100
        getData("http://enejd0613.dothome.co.kr/Waterlist.php");
        getFoodData("http://enejd0613.dothome.co.kr/foodcalendarlist.php");
        getFatData("http://enejd0613.dothome.co.kr/getWeight.php");
        getExData("http://enejd0613.dothome.co.kr/excalendarlist.php");
        ImageButton plus = (ImageButton) findViewById(R.id.plusBtn);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(water.getText().toString());
                count = count+100;
                water.setText(count+"");
                watercount++;
            }
        });

        // 물 - 100
        ImageButton minus = (ImageButton) findViewById(R.id.minusBtn);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = Integer.parseInt(water.getText().toString());
                count = count-100;
                water.setText(count+"");
                watercount++;
            }
        });

        // 음식 >
        ImageButton view_food = (ImageButton) findViewById(R.id.view_food);
        view_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (watercount != 0) {
                    waterRequest = new waterRequest(ID,water.getText().toString(),date);
                    queue = Volley.newRequestQueue(main_user.this);
                    queue.add(waterRequest);
                    Intent intent = new Intent(getApplicationContext(), FoodAddActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), FoodAddActivity.class);
                    startActivity(intent);
                }
            }
        });

        // 공지
//        Button announcement_btn = (Button) findViewById(R.id.announcement_btn);
//        announcement_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (watercount != 0) {
//                    waterRequest = new waterRequest(ID,water.getText().toString(),date);
//                    queue = Volley.newRequestQueue(main_user.this);
//                    queue.add(waterRequest);
//                    Intent Noticeintent = new Intent(getApplicationContext(), annoucement.class);
//                    startActivity(Noticeintent);
//                }else{
//                    Intent Noticeintent = new Intent(getApplicationContext(), annoucement.class);
//                    startActivity(Noticeintent);
//                }
//            }
//        });

        // 프로필 선언
        User user = new User();

        name = findViewById(R.id.username); // 이름
        state = findViewById(R.id.Status_message_text); // 상태메시지
        ivImage = findViewById(R.id.imageView2); // 프로필 사진

        name.setText(user.getName()); // 이름
        state.setText(user.getState()); // 상태메시지
        ID = user.getId(); // 아이디
        profile = user.getProfile(); // 프로필 사진

//        String imageUrl = profile; // Glide로 이미지 표시하기
//        Glide.with(this).load(imageUrl).into(ivImage);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
        String UserProfile = user.getUserProfile();
        String UserId = user.getId();

        int eatTarget = Integer.parseInt(user.getEatTarget());
        int burnTarget = Integer.parseInt(user.getBurnTarget());
        // Firebase Realtime Database 설정
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(UserId).child("profile");

        // ValueEventListener를 사용하여 데이터베이스에서 프로필 이미지 URL 가져오기
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageUrl = dataSnapshot.getValue(String.class);
                // Glide로 이미지 표시하기
                Glide.with(main_user.this).load(imageUrl).into(ivImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 처리
            }
        };

// ValueEventListener를 등록하여 데이터 변경 시 동작하도록 설정
        databaseReference.addValueEventListener(valueEventListener);

        // 햄버거
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //뒤로가기버튼 이미지 적용
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        if (watercount != 0) {
                            waterRequest = new waterRequest(ID,water.getText().toString(),date);
                            queue = Volley.newRequestQueue(main_user.this);
                            queue.add(waterRequest);
                            Intent intent = new Intent(getApplicationContext(), main_user.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), main_user.class);
                            startActivity(intent);
                        }
                        return true;
                    case R.id.calender:
                        if (watercount != 0) {
                            waterRequest = new waterRequest(ID,water.getText().toString(),date);
                            queue = Volley.newRequestQueue(main_user.this);
                            queue.add(waterRequest);
                            Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    case R.id.communety:
                        if (watercount != 0) {
                            waterRequest = new waterRequest(ID,water.getText().toString(),date);
                            queue = Volley.newRequestQueue(main_user.this);
                            queue.add(waterRequest);
                            Intent communetyintent = new Intent(getApplicationContext(), community.class);
                            startActivity(communetyintent);
                        }else{     Intent communetyintent = new Intent(getApplicationContext(), community.class);
                            startActivity(communetyintent);
                        }
                        return true;
                    case R.id.mypage:
                        if (watercount != 0) {
                            waterRequest = new waterRequest(ID,water.getText().toString(),date);
                            queue = Volley.newRequestQueue(main_user.this);
                            queue.add(waterRequest);
                            Intent mypageintent = new Intent(getApplicationContext(), mypage.class);
                            startActivity(mypageintent);
                        }else{
                            Intent mypageintent = new Intent(getApplicationContext(), mypage.class);
                            startActivity(mypageintent);
                        }
                        return true;
                    case R.id.annoucement:
                        if (watercount != 0) {
                            waterRequest = new waterRequest(ID,water.getText().toString(),date);
                            queue = Volley.newRequestQueue(main_user.this);
                            queue.add(waterRequest);
                            Intent Noticeintent = new Intent(getApplicationContext(), annoucement.class);
                            startActivity(Noticeintent);
                        }else{
                            Intent Noticeintent = new Intent(getApplicationContext(), annoucement.class);
                            startActivity(Noticeintent);
                        }
                        return true;
                    case R.id.friend:
                        if (watercount != 0) {
                            waterRequest = new waterRequest(ID,water.getText().toString(),date);
                            queue = Volley.newRequestQueue(main_user.this);
                            queue.add(waterRequest);
                            Intent friend = new Intent(getApplicationContext(), chatList.class);
                            startActivity(friend);
                        }else{
                            Intent friend = new Intent(getApplicationContext(), chatList.class);
                            startActivity(friend);
                        }
                        return true;
                }
                return false;
            }
        });
        // 상태메시지 변경
        TextView text1 = findViewById(R.id.Status_message_text);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = user.getId();
                String text = text1.getText().toString();

                dialog_status alert = new dialog_status(main_user.this,text);
                alert.callFunction();
                alert.setModifyReturnListener(new dialog_status.ModifyReturnListener() {
                    @Override
                    public void afterModify(String text) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");

                                    if (success.equals("1")) { // 회원등록에 성공한 경우
                                        text1.setText(text);
                                        System.out.println("!!!!!!!!!!!!!: " +text);
                                        user.setState(text);
                                        Toast.makeText(getApplicationContext(), "한줄소개가 변경되었습니다.", Toast.LENGTH_SHORT).show();
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
                        StatusRequest stateRequest1 = new StatusRequest(UserID, text, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(main_user.this);
                        queue.add(stateRequest1);
                    }
                });
            }
        });

        // 태운 칼로리
        progressBar = findViewById(R.id.progressView1);
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = user.getId();
                String text = String.valueOf(burnTarget);

                dialog_burnkcal alert = new dialog_burnkcal(main_user.this, text);
                alert.callFunction();
                alert.setModifyReturnListener(new dialog_burnkcal.ModifyReturnListener() {
                    @Override
                    public void afterModify(String text) {

                        int burnT = Integer.parseInt(text);
                        progressBar.setMax(burnT);

                        // 다이얼로그로부터 새로운 목표값을 받아와서 progressPercent를 다시 계산합니다.
                        int currentProgress = (int) ExKcalNum;
                        int progressPercent = (int) ((currentProgress * 100.0f) / burnT);


                        TextView progressPercentTextView = findViewById(R.id.eat_per);

                        // progressPercentTextView를 업데이트합니다.
                        progressPercentTextView.setText(progressPercent + "%");


                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");

                                    if (success.equals("1")) { // 회원등록에 성공한 경우
                                        progressBar.setMax(Integer.parseInt(text));
                                        Toast.makeText(getApplicationContext(), "목표값이 변경되었습니다", Toast.LENGTH_SHORT).show();
                                    } else { // 회원등록에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "목표값 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        // 서버로 Volley를 이용해서 요청을 함.
                        BurnTargetRequest stateRequest1 = new BurnTargetRequest(UserID, text, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(main_user.this);
                        queue.add(stateRequest1);
                    }
                });
            }
        });

        // 섭취 칼로리
        progressBar1 = findViewById(R.id.progressView2);
      
        progressBar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String UserID = user.getId();
                String text = String.valueOf(eatTarget);

                dialog_eatkcal alert = new dialog_eatkcal(main_user.this, text);
                alert.callFunction();
                alert.setModifyReturnListener(new dialog_eatkcal.ModifyReturnListener() {
                    @Override
                    public void afterModify(String text) {
                        int eatT = Integer.parseInt(text);
                        progressBar.setMax(eatT);

                        // 다이얼로그로부터 새로운 목표값을 받아와서 progressPercent를 다시 계산합니다.
                        int currentProgress = (int) KcalNum;
                        int progressPercent = (int) ((currentProgress * 100.0f) / eatT);


                        TextView progressPercentTextView = findViewById(R.id.eat_per);

                        // progressPercentTextView를 업데이트합니다.
                        progressPercentTextView.setText(progressPercent + "%");

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");

                                    if (success.equals("1")) { // 회원등록에 성공한 경우
                                        progressBar1.setMax(Integer.parseInt(text));
                                        Toast.makeText(getApplicationContext(), "목표값이 변경되었습니다", Toast.LENGTH_SHORT).show();
                                    } else { // 회원등록에 실패한 경우
                                        Toast.makeText(getApplicationContext(), "목표값 변경에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    // 에러 처리 추가: 서버 응답을 파싱하는 동안 오류 발생
                                    Toast.makeText(getApplicationContext(), "서버 응답 파싱 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        // 서버로 Volley를 이용해서 요청을 함.
                        EatTargetRequest stateRequest1 = new EatTargetRequest(UserID, text, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(main_user.this);
                        queue.add(stateRequest1);
                    }
                });
            }
        });

        // Volley 요청을 통해 PHP 스크립트 호출
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://enejd0613.dothome.co.kr/randomEx.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String exerciseCode = jsonObject.getString("exerciseCode");
                            String exerciseName = jsonObject.getString("exerciseName");

                            // ExerciseName을 TextView에 설정
                            TextView textView = findViewById(R.id.randomEx);
                            textView.setText(exerciseName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 처리
                        Toast.makeText(getApplicationContext(), "네트워크 오류", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        // Volley 요청을 통해 PHP 스크립트 호출
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "http://enejd0613.dothome.co.kr/randomF.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String foodCode = jsonObject.getString("foodCode");
                            String foodName = jsonObject.getString("foodName");

                            // FoodName을 TextView2에 설정
                            TextView textView2 = findViewById(R.id.randomF);
                            textView2.setText(foodName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 처리
                        Toast.makeText(getApplicationContext(), "네트워크 오류", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest1);

    }


    private ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ivImage.setImageBitmap(bitmap);
                        uploadPicture(ID, getStringImage(bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    private void chooseFile()
    {
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
                ivImage.setImageBitmap(bitmap);

//                String userId = user.getId();
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
//
//                DatabaseReference userRef = databaseReference.child(userId);
//                userRef.child("profile").setValue(getStringImage(bitmap), new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                        if (error == null) {
//                            // 변경 성공
//                            Toast.makeText(main_user.this, "프로필 사진을 성공적으로 변경(firebase).", Toast.LENGTH_SHORT).show();
//                        } else {
//                            // 변경 실패
//                            Toast.makeText(main_user.this, "프로필 사진 변경에 실패(firebase).", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            uploadPicture(ID, getStringImage(bitmap));

        }
    }

    private void uploadPicture(final String UserID, final String UserProfile)
    {
        // final ProgressDialog progressDialog = new ProgressDialog(this);
        // progressDialog.setMessage("Uploading...");
        // progressDialog.show();
        String userID = user.getId();

        // 파이어베이스 스토리지에 이미지 업로드
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID + ".jpg");

        byte[] imageBytes = Base64.decode(UserProfile, Base64.DEFAULT);
        UploadTask uploadTask = storageReference.putBytes(imageBytes);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // 업로드 성공 후 이미지의 다운로드 URL 획득
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadURL = uri.toString();

                        // User 노드의 profile 값을 변경
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                        DatabaseReference userRef = databaseReference.child(userID).child("profile");

//                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User");
//
//                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                                    String childUserID = childSnapshot.getKey();
//                                    DatabaseReference friendRef = databaseReference.child(childUserID).child("friend").child(UserID);
//                                    friendRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.hasChild(UserID)) {
//                                                DatabaseReference profileRef = friendRef.child("profile");
//                                                profileRef.setValue(downloadURL, new DatabaseReference.CompletionListener() {
//                                                    @Override
//                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                                        if (error == null) {
//                                                            // 변경 성공
//                                                            Toast.makeText(main_user.this, "프로필 사진을 성공적으로 변경(친구).", Toast.LENGTH_SHORT).show();
//                                                        } else {
//                                                            // 변경 실패
//                                                            Toast.makeText(main_user.this, "프로필 사진 변경에 실패(친구).", Toast.LENGTH_SHORT).show();
//                                                        }
//                                                    }
//                                                });
//                                            }
//                                        }
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                                            // 취소 처리 시 동작할 내용
//                                        }
//                                    });
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });

                        userRef.setValue(downloadURL, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error == null) {
                                    // 변경 성공
                                    //Toast.makeText(main_user.this, "프로필 사진을 성공적으로 변경(firebase).", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 변경 실패
                                    //Toast.makeText(main_user.this, "프로필 사진 변경에 실패(firebase).", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // 업로드 실패
                Toast.makeText(main_user.this, "이미지 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

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
                        // progressDialog.dismiss();
                        Toast.makeText(main_user.this, "프로필 사진을 성공적으로 변경했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // progressDialog.dismiss();
                    Toast.makeText(main_user.this, "프로필 사진 변경에 실패했습니다. 다시 시도해주세요." + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // progressDialog.dismiss();
                Toast.makeText(main_user.this, "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        )
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
        stringRequest.setRetryPolicy(new com.android.volley.DefaultRetryPolicy(20000 ,
                com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
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

    // 햄버거
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void showFoodList() {

        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_FoodUserId);
                    String Date = c.getString(TAG_FoodDate);
                    if(user.getId().equals(UserId)) {
                        if(date.equals(Date)) {
                            String FoodKcal = c.getString(TAG_FoodKcal);
                            KcalNum += Integer.parseInt(FoodKcal);
                            kcalText.setText(String.valueOf(KcalNum));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 프로그래스바의 최대 값 설정
        int eatT = Integer.parseInt(user.getEatTarget());

// 프로그래스바와 TextView 초기화
        progressBar = findViewById(R.id.progressView2);
        TextView progressPercentTextView = findViewById(R.id.eat_per);
        progressBar.setMax(eatT);

// 어떤 활동을 수행할 때 진행률을 업데이트하려면 아래와 같이 호출
// 예를 들어, 진행률을 ExKcalNum로 업데이트하려면
        int currentProgress = (int) KcalNum;
        progressBar.setProgress(currentProgress);

// 진행률을 퍼센트로 계산하여 TextView에 업데이트
        int progressPercent = (int) ((currentProgress * 100.0f) / eatT);
        progressPercentTextView.setText(progressPercent + "%");

    }

    private void updateProgressBar(int newProgress) {
        progressBar.setProgress(newProgress);
    }

    public void getFoodData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showFoodList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    protected void showList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);
                    String Date = c.getString(TAG_Date);
                    if(UserId.equals(user.getId())) {
                        if(date.equals(Date)) {
                            String Water = c.getString(TAG_water);
                            water.setText(Water);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    // 운동 불러오기
    protected void showExList() {
        ExKcalNum = 0;
        ExkcalSum.setText("");
        minute = 0;
        ExTime.setText("");
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_FoodUserId);
                    String Date1 = c.getString(TAG_FoodDate);
                    if(UserId.equals(user.getId())) {
                        if(date.equals(Date1)) {
                            String Time = c.getString(TAG_Time);
                            minute += Float.parseFloat(Time.substring(0, 2));
                            second += Float.parseFloat(Time.substring(3, 5));
                        }
                    }
                }
                secondSum = second / 60;
                minute += secondSum;
                ExKcalNum = (float) (minute * (6 * 0.0175 * weight));
                ExkcalSum.setText(String.valueOf(ExKcalNum));

                ExTime.setText(String.valueOf(Math.round(minute)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // 프로그래스바의 최대 값 설정
        int burnT = Integer.parseInt(user.getBurnTarget());

// 프로그래스바와 TextView 초기화
        progressBar = findViewById(R.id.progressView1);
        TextView progressPercentTextView = findViewById(R.id.ex_per);
        progressBar.setMax(burnT);

// 어떤 활동을 수행할 때 진행률을 업데이트하려면 아래와 같이 호출
// 예를 들어, 진행률을 ExKcalNum로 업데이트하려면
        int currentProgress = (int) ExKcalNum;
        progressBar.setProgress(currentProgress);

// 진행률을 퍼센트로 계산하여 TextView에 업데이트
        int progressPercent = (int) ((currentProgress * 100.0f) / burnT);
        progressPercentTextView.setText(progressPercent + "%");

    }

    public void getExData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showExList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    // 몸무게 불러오기
    protected void showFatList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String userId = c.getString(TAG_FatId);
                    if(userId.equals(user.getId())) {
                        String weight = c.getString(TAG_weight);
                        this.weight =  Float.parseFloat(weight);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getFatData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showFatList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    // 시간
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        String getTime = dateFormat.format(date);

        return getTime;
    }

    // 공지 불러오기
//    private void fetchLatestAnnoncementTitle() {
//        String url = "http://enejd0613.dothome.co.kr/fetch_announcement.php";  // PHP 파일의 URL을 여기에 입력하세요.
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            title = response.getString("title");
//                            detail = response.getString("detail");
//
//                            TextView textView = findViewById(R.id.ann_txt);
//                            textView.setText(title);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });
//
//        // Volley 요청을 큐에 추가합니다.
//        Volley.newRequestQueue(this).add(request);
//    }

    // 공지 커스텀다이얼로그
//    private void showDialog() {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(main_user.this);
//
//        // 커스텀 다이얼로그 레이아웃을 inflate하여 설정
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_announcement, null);
//        dialogBuilder.setView(dialogView);
//
//        // 커스텀 다이얼로그 내부의 뷰 요소를 찾아서 설정
//        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
//        TextView contentTextView = dialogView.findViewById(R.id.dialog_content);
//
//        // TextView에 받아온 데이터를 설정
//        titleTextView.setText(title);
//        contentTextView.setText(detail);
//
//        // 커스텀 다이얼로그를 생성하고 보여주기
//        AlertDialog customDialog = dialogBuilder.create();
//        customDialog.show();
//    }

}