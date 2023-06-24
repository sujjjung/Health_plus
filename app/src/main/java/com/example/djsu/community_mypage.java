package com.example.djsu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class community_mypage extends AppCompatActivity {
    // 햄버거
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    // 프로필
    private TextView name, state,kcalText;
    private String profile, ID, date;
    private Bitmap bitmap;
    private ImageView ivImage;
    User user = new User();
    // 게시글 출력
    private GridView gridView;
    private ImageAdapter imageAdapter;
    private ArrayList<String> imageUrls;

    //팔로워, 팔로잉
    private static final String TAG_RESULTS = "result";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_following_id = "following_id";
    JSONArray peoples = null;
    String myJSON;
    private List<String> followingList, followersList;
    private Button follower_int,folloing_int;
    private folloingAdapter folloingAdapter;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_mypage);

        // 햄버거 버튼
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
                        Intent Mainintent = new Intent(getApplicationContext(), main_user.class);
                        startActivity(Mainintent);
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

        // 프로필
        User user = new User();

        name = findViewById(R.id.username); // 이름
        ivImage = findViewById(R.id.imageView2); // 프로필 사진

        name.setText(user.getName()); // 이름
        ID = user.getId(); // 아이디
        profile = user.getProfile(); // 프로필 사진

        String imageUrl = profile; // Glide로 이미지 표시하기
        Glide.with(this).load(imageUrl).into(ivImage);

        // 팔로잉 팔로워 follower_int,folloing_int
        followingList = new ArrayList<>();
        followersList = new ArrayList<>();
        getFriendData("http://enejd0613.dothome.co.kr/FriendList.php");
        follower_int = findViewById(R.id.follower_int);
        folloing_int = findViewById(R.id.folloing_int);

        folloing_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(community_mypage.this);
                View view = LayoutInflater.from(community_mypage.this).inflate(R.layout.dialog_following_list, null, false);
                builder.setView(view);

                final Button submitButton = (Button) view.findViewById(R.id.button_dialog_submit);
                final ListView folloingView = (ListView) view.findViewById(R.id.ListView);
                final AlertDialog dialog = builder.create();
                folloingAdapter = new folloingAdapter(context,followingList);
                folloingAdapter.setListView(folloingView);
                folloingView.setAdapter(folloingAdapter);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        follower_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(community_mypage.this);
                View view = LayoutInflater.from(community_mypage.this).inflate(R.layout.dialog_following_list, null, false);
                builder.setView(view);
                final Button submitButton = (Button) view.findViewById(R.id.button_dialog_submit);
                final TextView TextView = (TextView) view.findViewById(R.id.textView2);
                final ListView folloingView = (ListView) view.findViewById(R.id.ListView);
                final AlertDialog dialog = builder.create();
                TextView.setText("팔로워 유저");
                folloingAdapter = new folloingAdapter(context,followersList);
                folloingAdapter.setListView(folloingView);
                folloingView.setAdapter(folloingAdapter);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        // 게시글
        gridView = findViewById(R.id.grid_view);
        imageUrls = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, imageUrls);
        gridView.setAdapter(imageAdapter);
        new FetchImagesTask().execute();
    }
    // 로그인한 유저의 친구리스트를 얻기위한 코드
    protected void FriendList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String id = c.getString(TAG_USER_ID);
                    String following_id = c.getString(TAG_following_id);
                    if (user.getId().equals(id)) {
                        String followingId = c.getString(TAG_following_id);
                        followingList.add(followingId);
                    }
                    if(user.getId().equals(following_id)){
                        String FriendId = c.getString(TAG_USER_ID);
                        followersList.add(FriendId);
                    }
                }
            }
            follower_int.setText(String.valueOf(followersList.size()));
            folloing_int.setText(String.valueOf(followingList.size()));
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
    private class FetchImagesTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String urlString = "http://enejd0613.dothome.co.kr/filedownload.php"; // get_images.php의 URL을 입력하세요.
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }

                    bufferedReader.close();
                    inputStream.close();
                    return response.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.startsWith("Error")) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String id = item.getString("id");
                        if(user.getId().equals(id)) {
                            String imageUrl = item.getString("image");
                            imageUrls.add(imageUrl);
                        }
                    }
                    imageAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(community_mypage.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(community_mypage.this, result, Toast.LENGTH_SHORT).show();
            }
        }
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
}