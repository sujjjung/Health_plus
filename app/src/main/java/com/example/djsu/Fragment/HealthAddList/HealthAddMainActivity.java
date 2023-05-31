package com.example.djsu.Fragment.HealthAddList;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.djsu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HealthAddMainActivity extends AppCompatActivity {

    ExpandableListViewAdater listViewAdater;
    ExpandableListView expandableListView;
    List<String> chapterList;
    HashMap<String, List<String>> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_add_main);

        expandableListView = findViewById(R.id.eListView);

        showList();

        listViewAdater = new ExpandableListViewAdater(this, chapterList, topicList);
        expandableListView.setAdapter(listViewAdater);
    }

    private void showList() {
        chapterList = new ArrayList<String>();
        topicList = new HashMap<String, List<String>>();

        chapterList.add("런지");

        List<String> topicl1 = new ArrayList<>();
        topicl1.add("이게 바로 런지 운동");

        topicList.put(chapterList.get(0), topicl1);
    }
}