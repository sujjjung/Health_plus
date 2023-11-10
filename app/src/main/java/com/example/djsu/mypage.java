package com.example.djsu;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mypage extends AppCompatActivity {
    //햄버거 선언부
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Bitmap bitmap;
    private TextView name, state, nondisclosure;
    private ImageView ivImage;
    private String profile;
    private static final String URL_UPLOAD = "http://enejd0613.dothome.co.kr/upload_profile.php";
    //차트
    private BarChart weightChart;
    private BarChart muscleChart;
    private BarChart fatChart;

    // 로그인 데이터
    User user = new User();
    String ID = user.getId(); // 아이디

    private static final String TAG_RESULTS = "result";
    private static final String TAG_UserID  = "UserID";
    private static final String TAG_UserProfile = "UserProfile";
    String myJSON;
    JSONArray peoples = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        getData("http://enejd0613.dothome.co.kr/getUser.php");
        // User Data 불러오기
        name = findViewById(R.id.name_textView);
        name.setText(user.getName());
//        state = findViewById(R.id.Status_message_text);
//        state.setText(user.getState());

        User user = new User();
        String UserProfile = user.getUserProfile();
        String UserId = user.getId();
        // Firebase Realtime Database 설정
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(UserId).child("profile");

        ivImage = findViewById(R.id.profile);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });
        // ValueEventListener를 사용하여 데이터베이스에서 프로필 이미지 URL 가져오기
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageUrl = dataSnapshot.getValue(String.class);

                // Glide로 이미지 표시하기
                Glide.with(mypage.this).load(imageUrl).into(ivImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 에러 처리
            }
        };

// ValueEventListener를 등록하여 데이터 변경 시 동작하도록 설정
        databaseReference.addValueEventListener(valueEventListener);

        // 정보 변경 버튼
        Button user_edit = (Button) findViewById(R.id.change_btn);
        user_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), user_data_edit.class);
                startActivity(intent);
            }
        });
        TextView nondisclosure = findViewById(R.id.Nondisclosure);

        String UserID = user.getId();

        DatabaseReference nondisclosureText = FirebaseDatabase.getInstance().getReference("User").child(UserID).child("nondisclosure");

        //텍스트뷰에 데이터 값을 유지시켜주기
        nondisclosureText.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // dataSnapshot을 통해 데이터베이스에서 읽어온 값을 가져올 수 있습니다.
                String value = dataSnapshot.getValue(String.class);

                // 값을 TextView에 설정합니다.
                if (value != null) {
                    nondisclosure.setText(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터베이스 읽기 작업이 취소되었거나 실패한 경우 처리할 내용을 여기에 작성하세요.
            }
        });

        //계정 공개, 비공개
        Button nondisclosureBtn = (Button)findViewById(R.id.Nondisclosure);

        nondisclosureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nondisclosureText.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // dataSnapshot을 통해 데이터베이스에서 읽어온 값을 가져올 수 있습니다.
                        Object value = dataSnapshot.getValue();
                        System.out.println(value);

                        // 값을 확인한 후에 원하는 로직을 수행할 수 있습니다.
                        if (value != null && value.equals("공개")) {
                            nondisclosure.setText("비공개");

                            // "비공개"로 업데이트된 값을 데이터베이스에 저장합니다.
                            nondisclosureText.setValue("비공개");
                        } else {
                            nondisclosure.setText("공개");

                            // "비공개"로 업데이트된 값을 데이터베이스에 저장합니다.
                            nondisclosureText.setValue("공개");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // 데이터베이스 읽기 작업이 취소되었거나 실패한 경우 처리할 내용을 여기에 작성하세요.
                    }
                });

            }
        });



        // 회원 탈퇴 버튼
        Button user_delete = (Button) findViewById(R.id.secession_btn);
        user_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mypage.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mypage.this, login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
//                dialog_del alert = new dialog_del(mypage.this, UserID);
//                alert.callFunction();
//                alert.setModifyReturnListener(new dialog_del.ModifyReturnListener() {
//                    @Override
//                    public void afterModify(String text) {
//                        Response.Listener<String> responseListener = new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    String success = jsonObject.getString("success");
//
//                                    if (success.equals("1")) { // 회원 삭제에 성공한 경우
//                                        Toast.makeText(getApplicationContext(), "회원 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(mypage.this, MainActivity.class);
//                                        startActivity(intent);  //intent를 넣어 실행시키게 됩니다.
//                                    } else { // 회원 삭제에 실패한 경우
//                                        Toast.makeText(getApplicationContext(), "회원 탈퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        };
//                        // 서버로 Volley를 이용해서 요청을 함.
//                        delUserRequest delUserRequest1 = new delUserRequest(UserID, responseListener);
//                        RequestQueue queue = Volley.newRequestQueue(mypage.this);
//                        queue.add(delUserRequest1);
//                    }
//                });
            }
        });

        // 몸무게
        // 차트
        weightChart = findViewById(R.id.weight_chart);
        // Disable description
        weightChart.getDescription().setEnabled(false);
        // Enable touch gestures
        weightChart.setTouchEnabled(true);
        // Enable scaling and dragging
        weightChart.setDragEnabled(true);
        weightChart.setScaleEnabled(true);
        // 데이터 초기화
        weightChart.clear();
        // Set data
        setWeightData();

        // 체지방량
        // 차트
        fatChart = findViewById(R.id.fat_chart);
        // Disable description
        fatChart.getDescription().setEnabled(false);
        // Enable touch gestures
        fatChart.setTouchEnabled(true);
        // Enable scaling and dragging
        fatChart.setDragEnabled(true);
        fatChart.setScaleEnabled(true);
        // 데이터 초기화
        fatChart.clear();
        // Set data
        setFatData();

        // 체지방량
        // 차트
        muscleChart = findViewById(R.id.muscles_chart);
        // Disable description
        muscleChart.getDescription().setEnabled(false);
        // Enable touch gestures
        muscleChart.setTouchEnabled(true);
        // Enable scaling and dragging
        muscleChart.setDragEnabled(true);
        muscleChart.setScaleEnabled(true);
        // 데이터 초기화
        muscleChart.clear();
        // Set data
        setMuscleData();


        // 햄버거 버튼 구현부
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_hamburger);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        Intent homeintent = new Intent(getApplicationContext(), main_user.class);
                        startActivity(homeintent);
                        return true;
                    case R.id.calender:
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.communety:
                        Intent communetyintent = new Intent(getApplicationContext(), community.class);
                        startActivity(communetyintent);
                        return true;
                    case R.id.mypage:
                        Intent mypageintent = new Intent(getApplicationContext(), mypage.class);
                        startActivity(mypageintent);
                        return true;
                    case R.id.annoucement:
                        Intent Noticeintent = new Intent(getApplicationContext(), annoucement.class);
                        startActivity(Noticeintent);
                        return true;
                    case R.id.friend:
                        Intent friend = new Intent(getApplicationContext(), chatList.class);
                        startActivity(friend);
                        return true;
                }
                return false;
            }
        });
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
                Toast.makeText(mypage.this, "이미지 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(mypage.this, "프로필 사진을 성공적으로 변경했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // progressDialog.dismiss();
                    Toast.makeText(mypage.this, "프로필 사진 변경에 실패했습니다. 다시 시도해주세요." + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                // progressDialog.dismiss();
                Toast.makeText(mypage.this, "Error : " + error.toString(), Toast.LENGTH_SHORT).show();
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
    private void setWeightData() {
        String url = "http://enejd0613.dothome.co.kr/getMuscle.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<BarEntry> values = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();

                int index = 0; // Counter for the x-axis index

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String userId = jsonObject.getString("userId");
                        if (user.getId().equals(userId)) {
                            int weight = jsonObject.getInt("weight");
                            values.add(new BarEntry(index, weight));
                            String date = jsonObject.getString("date");
                            // Extract month and day from the date string
                            String[] dateParts = date.split("-");
                            String formattedDate = dateParts[1] + "-" + dateParts[2];
                            labels.add(formattedDate.trim());
                            index++; // Increment the index for the next entry
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (values.size() > 7) {
                    int startIndex = values.size() - 7;
                    values = new ArrayList<>(values.subList(startIndex, values.size()));
                    labels = new ArrayList<>(labels.subList(startIndex, labels.size()));
                }

                BarDataSet set1 = new BarDataSet(values, "weight Data");
                set1.setColors(Color.rgb(255, 222, 0));
                set1.setDrawValues(false);
                set1.setDrawValues(true);
                set1.setValueTextSize(12f);
                set1.setValueTextColor(Color.BLACK);

                BarData data = new BarData(set1);
                data.setBarWidth(0.45f);

                weightChart.setData(data);
                weightChart.setFitBars(true);
                weightChart.invalidate();
                weightChart.setTouchEnabled(false);
                weightChart.setMaxVisibleValueCount(7);
                weightChart.getXAxis().setDrawGridLines(false);
                weightChart.getAxisLeft().setDrawGridLines(false);
                weightChart.getAxisRight().setDrawGridLines(false);
                weightChart.getAxisLeft().setDrawLabels(true);
                weightChart.getAxisRight().setDrawLabels(false);
                weightChart.getAxisLeft().setAxisMinimum(0);
                XAxis xAxis = weightChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(Math.min(7, labels.size())); // Set maximum of 7 labels
                xAxis.setTextSize(12f);
                xAxis.setTextColor(Color.BLACK);
                xAxis.setDrawGridLines(false);
                xAxis.setCenterAxisLabels(true); // Center the labels between the bars
                weightChart.getLegend().setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mypage.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }


    private void setFatData() {
        String url = "http://enejd0613.dothome.co.kr/getMuscle.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<BarEntry> values = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();

                int index = 0; // Counter for the x-axis index

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String userId = jsonObject.getString("userId");
                        if (user.getId().equals(userId)) {
                            int fat = jsonObject.getInt("fat");
                            values.add(new BarEntry(index, fat));
                            String date = jsonObject.getString("date");
                            String[] dateParts = date.split("-");
                            String formattedDate = dateParts[1] + "-" + dateParts[2];
                            labels.add(formattedDate.trim());
                            index++; // Increment the index for the next entry
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (values.size() > 7) {
                    int startIndex = values.size() - 7;
                    values = new ArrayList<>(values.subList(startIndex, values.size()));
                    labels = new ArrayList<>(labels.subList(startIndex, labels.size()));
                }

                BarDataSet set1 = new BarDataSet(values, "fat Data");
                set1.setColors(Color.rgb(255, 222, 0));
                set1.setDrawValues(false);
                set1.setDrawValues(true);
                set1.setValueTextSize(12f);
                set1.setValueTextColor(Color.BLACK);

                BarData data = new BarData(set1);
                data.setBarWidth(0.45f);

                fatChart.setData(data);
                fatChart.setFitBars(true);
                fatChart.invalidate();
                fatChart.setTouchEnabled(false);
                fatChart.setMaxVisibleValueCount(7);
                fatChart.getXAxis().setDrawGridLines(false);
                fatChart.getAxisLeft().setDrawGridLines(false);
                fatChart.getAxisRight().setDrawGridLines(false);
                fatChart.getAxisLeft().setDrawLabels(true);
                fatChart.getAxisRight().setDrawLabels(false);
                fatChart.getAxisLeft().setAxisMinimum(0);
                fatChart.getLegend().setEnabled(false);
                XAxis xAxis = fatChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(Math.min(7, labels.size())); // Set maximum of 7 labels
                xAxis.setTextSize(12f);
                xAxis.setTextColor(Color.BLACK);
                xAxis.setDrawGridLines(false);
                xAxis.setCenterAxisLabels(true); // Center the labels between the bars
                fatChart.getLegend().setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mypage.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }


    private void setMuscleData() {
        String url = "http://enejd0613.dothome.co.kr/getMuscle.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        // POST 파라미터 설정
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", ID);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<BarEntry> values = new ArrayList<>();
                ArrayList<String> labels = new ArrayList<>();

                int index = 0; // Counter for the x-axis index

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String userId = jsonObject.getString("userId");
                        if (user.getId().equals(userId)) {
                            int muscle = jsonObject.getInt("muscle");
                            values.add(new BarEntry(index, muscle));
                            String date = jsonObject.getString("date");
                            String[] dateParts = date.split("-");
                            String formattedDate = dateParts[1] + "-" + dateParts[2];
                            labels.add(formattedDate);
                            index++; // Increment the index for the next entry
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (values.size() > 7) {
                    int startIndex = values.size() - 7;
                    values = new ArrayList<>(values.subList(startIndex, values.size()));
                    labels = new ArrayList<>(labels.subList(startIndex, labels.size()));
                }

                BarDataSet set1 = new BarDataSet(values, "muscle Data");
                set1.setColors(Color.rgb(255, 222, 0));
                set1.setDrawValues(false);
                set1.setDrawValues(true);
                set1.setValueTextSize(12f);
                set1.setValueTextColor(Color.BLACK);

                BarData data = new BarData(set1);
                data.setBarWidth(0.45f);

                muscleChart.setData(data);
                muscleChart.setFitBars(true);
                muscleChart.invalidate();
                muscleChart.setMaxVisibleValueCount(7);
                muscleChart.getXAxis().setDrawGridLines(false);
                muscleChart.getAxisLeft().setDrawGridLines(false);
                muscleChart.getAxisRight().setDrawGridLines(false);
                muscleChart.getAxisLeft().setDrawLabels(true);
                muscleChart.getAxisLeft().setAxisMinimum(0);
                muscleChart.getAxisRight().setDrawLabels(false);
                muscleChart.getLegend().setEnabled(false);
                XAxis xAxis = muscleChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setLabelCount(Math.min(7, labels.size())); // Set maximum of 7 labels
                xAxis.setTextSize(12f);
                xAxis.setTextColor(Color.BLACK);
                xAxis.setDrawGridLines(false);
                xAxis.setCenterAxisLabels(true); // Center the labels between the bars
                muscleChart.getLegend().setEnabled(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mypage.this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
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
                        sb.append(json).append("\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
//                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    // 햄버거 버튼 구현부
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}