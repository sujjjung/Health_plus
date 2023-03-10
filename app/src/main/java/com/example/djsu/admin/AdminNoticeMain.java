package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.djsu.Food;
import com.example.djsu.Notice;
import com.example.djsu.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminNoticeMain extends AppCompatActivity {
    private List<Notice> noticeList;
    private adminNoticeAdapter noticeAdapter;
    ImageButton noticeAddBtn;
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

        ListView foodListView = (ListView) findViewById(R.id.NoticeView);
        foodListView.setAdapter(noticeAdapter);

        try {
            noticeAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("Notice"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String date,title,detail;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);

                //emote = object.getString("emote");
                date = object.getString("date");
                title = object.getString("title");
                detail = object.getString("detail");
                //값들을 User클래스에 묶어줍니다
                Notice notice = new Notice(date,title,detail);
                noticeList.add(notice);//리스트뷰에 값을 추가해줍니다
                count++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}