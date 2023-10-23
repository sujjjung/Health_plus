package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.djsu.Food;
import com.example.djsu.Notice;
import com.example.djsu.R;
import com.example.djsu.exerciseLsit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminNoticeMain extends AppCompatActivity {
    private List<Notice> noticeList;
    private adminNoticeAdapter noticeAdapter;
    ImageButton noticeAddBtn;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_date = "anndate";
    private static final String TAG_title = "title";
    private static final String TAG_detail = "detail";
    private static final String TAG_emote = "emote";
    String myJSON;
    JSONArray peoples = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice_main);
        Intent intent = getIntent();
        noticeAddBtn = (ImageButton)findViewById(R.id.noticeAddBtn);

        noticeAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminNoticeMain.this, AdminNoticeAdd.class);
                startActivity(intent);
            }
        });
        noticeList =new ArrayList<>();

        noticeAdapter = new adminNoticeAdapter(this,noticeList);

        ListView  noticetView = (ListView) findViewById(R.id.NoticeView);
        noticetView.setAdapter(noticeAdapter);


        getData("http://enejd0613.dothome.co.kr/Announcementlist.php");
    }
    protected void showList() {
        noticeAdapter.notifyDataSetChanged();
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String date = c.getString(TAG_date);
                    String title = c.getString(TAG_title);
                    String detail = c.getString(TAG_detail);
                    String emote = c.getString(TAG_emote);
                    Notice notice = new Notice(date,title,detail,emote);
                    noticeList.add(notice);
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