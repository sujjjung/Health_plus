package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.djsu.CalendarActivity;
import com.example.djsu.EerciseRequest;
import com.example.djsu.NoticeRequest;
import com.example.djsu.R;
import com.example.djsu.WeightActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminNoticeAdd extends AppCompatActivity {
    Button NoticeUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notice_add);

        NoticeUpBtn = findViewById(R.id.NoticeUpBtn);

        NoticeUpBtn.setOnClickListener(new View.OnClickListener() {

            
            @Override
            public void onClick(View v) {
                String Notice_emoji=((EditText)findViewById(R.id.Notice_emoji_et)).getText().toString();
                String Notice_title=((EditText)findViewById(R.id.Notice_title_et)).getText().toString();
                String Notice_content=((EditText)findViewById(R.id.Notice_content_et)).getText().toString();
                String date = getTime();
                // 서버로 Volley를 이용해서 요청을 함.
                NoticeRequest  noticeRequest= new NoticeRequest(date,Notice_title,Notice_content,Notice_emoji);
                RequestQueue queue = Volley.newRequestQueue(AdminNoticeAdd.this);
                queue.add(noticeRequest);
                Toast.makeText(getApplicationContext(),"공지가 등록되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminNoticeAdd.this, AdminNoticeMain.class);
                startActivity(intent);
            }
        });

    }
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}