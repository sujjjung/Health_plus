package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.djsu.MainActivity;
import com.example.djsu.R;

import java.util.ArrayList;
import java.util.List;

public class AdminFoodMain extends AppCompatActivity {

    ImageButton foodAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_main);

        ListView listView = findViewById(R.id.adminfoodlistView);

        List<String> list = new ArrayList<>();
        list.add("짜장면");
        list.add("딸기");
        list.add("뿌링클");
        list.add("짬뽕");

        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adpater);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(AdminFoodMain.this, AdminFoodSub.class);
                startActivity(intent);
            }
        });

        foodAddBtn = (ImageButton)findViewById(R.id.foodaddBtn);

        foodAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminFoodMain.this, AdminFoodAdd.class);
                startActivity(intent);
            }
        });
    }
}