package com.example.djsu.admin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.djsu.R;
import com.example.djsu.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminUserListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<adminUser> userArrayList;
    private adminUserAdapter adminUserAdapter;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_UserID  = "UserID";
    private static final String TAG_UserName  = "UserName";
    private static final String TAG_UserAge = "UserAge";
    private static final String TAG_UserProfile = "UserProfile";
    String myJSON;
    JSONArray peoples = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);

        userArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adminUserAdapter = new adminUserAdapter(userArrayList,AdminUserListActivity.this);

        recyclerView.setAdapter(adminUserAdapter);

        getData("http://enejd0613.dothome.co.kr/getUser.php");
    }
    protected void showList() {
        adminUserAdapter.notifyDataSetChanged();
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserID = c.getString(TAG_UserID);
                    if(UserID.equals("admin") == false) {
                        String UserName = c.getString(TAG_UserName);
                        String UserAge = c.getString(TAG_UserAge);
                        String UserProfile = c.getString(TAG_UserProfile);
                        adminUser adminUser = new adminUser(UserID, UserProfile, UserName, UserAge);
                        userArrayList.add(adminUser);
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
}