package com.example.djsu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class comment extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    // 댓글 출력
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_CREATED_AT = "created_at";
    private static final String TAG_USERID = "userId";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_comment);

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
                    case R.id.map:
                        Intent mapintent = new Intent(getApplicationContext(), map.class);
                        startActivity(mapintent);
                        return true;
                    case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), pedometer.class);
                        startActivity(manbogiintent);
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
        Intent intent = getIntent();
        // 댓글
        list = (ListView) findViewById(R.id.comment_list);
        personList = new ArrayList<HashMap<String, String>>();
        getData("http://enejd0613.dothome.co.kr/get_comments.php");
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String content = c.getString(TAG_CONTENT);
                String created_at = c.getString(TAG_CREATED_AT);
                String userId = c.getString(TAG_USERID);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_CONTENT, content);
                persons.put(TAG_CREATED_AT, created_at);
                persons.put(TAG_USERID, userId);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    comment.this, personList, R.layout.item_comment,
                    new String[]{TAG_CONTENT, TAG_CREATED_AT, TAG_USERID},
                    new int[]{R.id.userComment, R.id.date, R.id.userName }
            );

            // 리스트뷰에 어댑터 설정
            list.setAdapter(adapter);

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
}