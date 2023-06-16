package com.example.djsu;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.djsu.admin.AdminFoodAdapter;
import com.example.djsu.admin.adminNoticeAdapter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class annoucement extends AppCompatActivity {
    String date, title, detail, emote;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private NoticeAdapter noticeAdapter;
    private List<Notice> noticeList;
    // 게시글 출력
    private static final String TAG_RESULTS = "result";
    private static final String TAG_emote = "emote";
    private static final String TAG_title = "title";
    private static final String TAG_detail = "detail";
    private static final String TAG_DATE = "date";
    JSONArray peoples = null;
    String myJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annoucement);
        Intent intent = getIntent();
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
                    //case R.id.map:
                        //Intent mapintent = new Intent(getApplicationContext(), map.class);
                        //startActivity(mapintent);
                        //return true;
                    //case R.id.manbogi:
                        //Intent manbogiintent = new Intent(getApplicationContext(), pedometer.class);
                        //startActivity(manbogiintent);
                        //return true;
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
        noticeList = new ArrayList<>();

        noticeAdapter = new NoticeAdapter(this, noticeList);

        ListView NoticeListView = (ListView) findViewById(R.id.NoticeView);
        NoticeListView.setAdapter(noticeAdapter);

        NoticeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                noticeAdapter.getItem(position);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(annoucement.this);

                // 커스텀 다이얼로그 레이아웃을 inflate하여 설정
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_announcement, null);
                dialogBuilder.setView(dialogView);

                // 커스텀 다이얼로그 내부의 뷰 요소를 찾아서 설정
                TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
                TextView contentTextView = dialogView.findViewById(R.id.dialog_content);

                titleTextView.setText(noticeList.get(position).getTitle());
                contentTextView.setText(noticeList.get(position).getContent());

                // 커스텀 다이얼로그를 생성하고 보여주기
                AlertDialog customDialog = dialogBuilder.create();
                customDialog.show();
            }
        });
        getData("http://enejd0613.dothome.co.kr/Announcementlist.php");
    }
    protected void showList() {

        try {

            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String date = c.getString(TAG_DATE);
                    String title = c.getString(TAG_title);
                    String detail = c.getString(TAG_detail);
                    String emote = c.getString(TAG_emote);
                    Notice notice = new Notice(date,title,detail,emote);
                    noticeList.add(notice);//리스트뷰에 값을 추가해줍니다
                }

            }
            noticeAdapter.notifyDataSetChanged();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}