package com.example.djsu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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
    private static final String TAG_postId = "postId";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    // 수정된 부분: 댓글 작성에 필요한 변수 추가
    private EditText et;
    private Button post;
    User user = new User();
    private String userId = user.getId();
    private int postId = 0; // 게시물 ID 불러오기

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_comment);

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
                    // navigation item 처리
                }
                return false;
            }
        });
        Bundle extras = getIntent().getExtras();

        postId = extras.getInt("postId");

        // 수정된 부분: 댓글 작성에 필요한 뷰 초기화
        et = findViewById(R.id.et);
        post = findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et.getText().toString();
                saveComment(content);
            }
        });

        // 댓글 출력
        list = findViewById(R.id.comment_list);
        personList = new ArrayList<>();
        getData("http://enejd0613.dothome.co.kr/get_comments.php");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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
                int postId = Integer.parseInt(c.getString(TAG_postId));
                if(this.postId == postId) {
                    String content = c.getString(TAG_CONTENT);
                    String created_at = c.getString(TAG_CREATED_AT);
                    String userId = c.getString(TAG_USERID);
                    HashMap<String, String> persons = new HashMap<>();
                    persons.put(TAG_CONTENT, content);
                    persons.put(TAG_CREATED_AT, created_at);
                    persons.put(TAG_USERID, userId);

                    personList.add(persons);
                }

            }



            ListAdapter adapter = new SimpleAdapter(
                    comment.this, personList, R.layout.item_comment,
                    new String[]{TAG_CONTENT, TAG_CREATED_AT, TAG_USERID},
                    new int[]{R.id.userComment, R.id.date, R.id.userName}
            );

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

                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
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

    // 수정된 부분: 댓글 저장을 위한 메소드 추가
    private void saveComment(String content) {
        class SaveCommentTask extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    String url = "http://enejd0613.dothome.co.kr/save_comment.php";
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // 댓글 데이터를 POST 요청으로 전송
                    String postData = "content=" + URLEncoder.encode(content, "UTF-8");
                    postData += "&userId=" + URLEncoder.encode(userId, "UTF-8");
                    postData += "&postId=" + postId;

                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(postData.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();

                    // 응답 받기
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    return response.toString().trim();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    // 댓글 저장 성공
                    // 댓글 리스트를 다시 가져와서 업데이트
                    getData("http://enejd0613.dothome.co.kr/get_comments.php");
                } else {
                    // 댓글 저장 실패
                }
            }
        }

        SaveCommentTask saveTask = new SaveCommentTask();
        saveTask.execute();
    }
}
