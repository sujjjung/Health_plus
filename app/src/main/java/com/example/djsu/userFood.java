package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class userFood extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<Food>  mFoodArrayList;
    private DBHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food);

        setInit();

    }

    private void setInit() {
        mDbHelper = new DBHelper(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mFoodArrayList = new ArrayList<>();

    }
}