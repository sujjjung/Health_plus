package com.example.djsu;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class community extends AppCompatActivity {
    // 플로팅버튼 상태
    private boolean fabMain_status = false;
    private FloatingActionButton fabMain;
    private FloatingActionButton fabHealth;
    private FloatingActionButton fabFood;
    private FloatingActionButton fabPhoto;

    private int i  = 0;

    private ScrollView scrollView;
    // 햄버거
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private communityAdapter communityAdapter;
    private User user1;
    private ArrayList<User> Friendlist = new ArrayList<>();
    private ArrayList<User> Communitylist = new ArrayList<>();
    // 게시글 출력
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_ID = "id";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_DATE = "date";
    private static final String TAG_FriendCode = "FriendCode";
    private static final String TAG_postId = "postId";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_following_id = "following_id";
    private static final String TAG_UserProfile = "UserProfile";
    JSONArray peoples = null;
    JSONArray peoples1 = null;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        // 마이페이지
        Button mypage_btn = (Button) findViewById(R.id.mypage_btn);
        mypage_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), community_mypage.class);
                startActivity(intent);
            }
        });
        // 플로팅 버튼 선언
        fabMain = findViewById(R.id.floatingMain);
        fabHealth = findViewById(R.id.floatingHealth);
        fabFood = findViewById(R.id.floatingFood);
        fabPhoto = findViewById(R.id.floatingPhoto);
        scrollView = findViewById(R.id.bio_scroll);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    // 스크롤 위치에 따라 버튼의 위치를 업데이트합니다.
                    fabMain.setTranslationY(scrollY);
                    fabMain.setTranslationX(scrollX);
                    fabHealth.setTranslationY(scrollY);
                    fabHealth.setTranslationX(scrollX);
                    fabFood.setTranslationY(scrollY);
                    fabFood.setTranslationX(scrollX);
                    fabPhoto.setTranslationY(scrollY);
                    fabPhoto.setTranslationX(scrollX);
                }
            });
        }

        // 메인플로팅 버튼 클릭
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });

        // 식단 플로팅 버튼 클릭
        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(community.this, community_post.class);
                i = 1;
                intent.putExtra("number", i);
                startActivity(intent);
            }
        });

        // 운동 플로팅 버튼 클릭
        fabHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(community.this, community_post.class);
                i = 1;
                intent.putExtra("number", i);
                startActivity(intent);
            }
        });

        // 사진 플로팅 버튼 클릭
        fabPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(community.this, community_post.class);
                i = 0;
                intent.putExtra("number", i);
                startActivity(intent);
            }
        });
        // 포스트 추가

        // 댓글
        // ImageButton commentButton=findViewById(R.id.chat_bubble_btn);
        // findViewById(R.id.button4) : 버튼의 text가 아니라 Id값을 넣어줘야한다.
        // commentButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Intent intent=new Intent(getApplicationContext(),comment.class);
        //        startActivity(intent);
        //    }

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
                switch(menuItem.getItemId()){
                    case R.id.home:
                        Intent homeintent = new Intent(getApplicationContext(), main_user.class);
                        startActivity(homeintent);
                        return true;
                    case R.id.calender:
                        Intent calenderintent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(calenderintent);
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
                        Intent annoucementintent = new Intent(getApplicationContext(), annoucement.class);
                        startActivity(annoucementintent);
                        return true;
                    case R.id.friend:
                        Intent friend = new Intent(getApplicationContext(), chatList.class);
                        startActivity(friend);
                        return true;
                }
                return false;
            }
        });

        // 게시글
        list = (ListView) findViewById(R.id.community);
        communityAdapter = new communityAdapter(this,Communitylist);

        list.setAdapter(communityAdapter);

        getFriendData("http://enejd0613.dothome.co.kr/FriendList.php");
        getData("http://enejd0613.dothome.co.kr/filedownload.php");
    }
    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    public void toggleFab() {
        if (fabMain_status) {
            // 플로팅 액션 버튼 닫기
            // 애니메이션 추가
            ObjectAnimator fk_animation = ObjectAnimator.ofFloat(fabPhoto, "translationY", fabMain.getTranslationY()-0f);
            fk_animation.start();
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabFood, "translationY", fabMain.getTranslationY()-0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabHealth, "translationY", fabMain.getTranslationY()-0f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_action_plus);

        } else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabFood, "translationY", fabMain.getTranslationY()-200f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabHealth, "translationY", fabMain.getTranslationY()-400f);
            fe_animation.start();
            ObjectAnimator fk_animation = ObjectAnimator.ofFloat(fabPhoto, "translationY", fabMain.getTranslationY()-600f);
            fk_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_action_plus);
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
    }
    // 로그인한 유저의 친구리스트를 얻기위한 코드
    protected void FriendList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples1 = jsonObj.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < peoples1.length(); i++) {
                    JSONObject c = peoples1.getJSONObject(i);
                    String id = c.getString(TAG_USER_ID);
                    if (user1.getId().equals(id)) {
                        String following_id = c.getString(TAG_following_id);
                        user1 = new User(following_id);
                        Friendlist.add(user1);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getFriendData(String url) {
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
                FriendList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);

    }
    // 로그인한 유저의 커뮤티니 목록(본인, 친구)
    protected void showList() {
        communityAdapter.notifyDataSetChanged();
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int j = 0;j < Friendlist.size(); j++) {
                    String FriendName = Friendlist.get(j).getFriend();
                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        if (id.equals(FriendName)) {
                            String image = c.getString(TAG_IMAGE);
                            String date = c.getString(TAG_DATE);
                            String content = c.getString(TAG_CONTENT);
                            String postId = c.getString(TAG_postId);
                            String userProfile = c.getString(TAG_UserProfile);
                            User user = new User(Integer.parseInt(postId),id,content,image,date,userProfile);
                            Communitylist.add(user);
                        }
                    }
                }

                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String id = c.getString(TAG_ID);
                    if (user1.getId().equals(id)) {
                        String image = c.getString(TAG_IMAGE);
                        String date = c.getString(TAG_DATE);
                        String content = c.getString(TAG_CONTENT);
                        String postId = c.getString(TAG_postId);
                        String userProfile = c.getString(TAG_UserProfile); // 추가된 부분
                        User user = new User(Integer.parseInt(postId),id,content,image,date,userProfile);
                        Communitylist.add(user);
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

    // 날짜
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}