package com.example.djsu;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.djsu.admin.AdminFoodAdd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FoodAddActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Button addButton;
    ImageButton food_input, searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);

        addButton = findViewById(R.id.addBtn);
        searchBtn = (ImageButton) findViewById(R.id.SearchBtn);
        food_input = (ImageButton) findViewById(R.id.food_input_btn);
        food_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodAddActivity.this, FoodAddInputActivity.class);
                startActivity(intent);
            }
        });


        String Name = "";
        String Kcal = "";
        String Carbohydrate = "";
        String Protein = "";
        String Fat = "";
        String Sodium = "";
        String Sugar = "";
        String Kg = "";
        int Cood;
        Bundle extras = getIntent().getExtras();

        Name = extras.getString("FoodName");
        Kcal = extras.getString("FoodKcal");
        Carbohydrate = extras.getString("FoodCarbohydrate");
        Protein = extras.getString("FoodProtein");
        Fat = extras.getString("FoodFat");
        Sodium = extras.getString("FoodSodium");
        Sugar = extras.getString("FoodSugar");
        Kg = extras.getString("FoodKg");
        Cood = extras.getInt("FoodCood");

        EditText NameText = (EditText) findViewById(R.id.nametext);
        EditText KcalText = (EditText) findViewById(R.id.kcaltext);
        EditText CarbohydratText = (EditText) findViewById(R.id.Carbohydratetext);
        EditText ProteinText = (EditText) findViewById(R.id.Protintext);
        EditText FatText = (EditText) findViewById(R.id.Fattext);
        EditText SodiumText = (EditText) findViewById(R.id.Sodiumtext);
        EditText SugarText = (EditText) findViewById(R.id.Sugartext);
        EditText KgText = (EditText) findViewById(R.id.Kgtext);

        String Namestr = Name;
        String Kcalstr = Kcal;
        String Carbohydratestr = Carbohydrate;
        String Proteinstr = Protein;
        String Fatstr = Fat;
        String Sodiumstr = Sodium;
        String Sugarstr = Sugar;
        String Kgstr = Kg;

        NameText.setText(Namestr);
        KcalText.setText(Kcalstr);
        CarbohydratText.setText(Carbohydratestr);
        ProteinText.setText(Proteinstr);
        FatText.setText(Fatstr);
        SodiumText.setText(Sodiumstr);
        SugarText.setText(Sugarstr);
        KgText.setText(Kgstr);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodSum foodSum = new FoodSum();

                foodSum.setSumKcal(foodSum.sumKcal(Integer.parseInt(Kcalstr)));
                foodSum.setSumCarbohydrate(foodSum.sumCarbohydrate(Integer.parseInt(Carbohydratestr)));
                foodSum.setSumProtein(foodSum.sumProtein(Integer.parseInt(Proteinstr)));
                foodSum.setSumFat(foodSum.sumFat(Integer.parseInt(Fatstr)));
                foodSum.setSumSodium(foodSum.sumSodium(Integer.parseInt(Sodiumstr)));
                foodSum.setSumSugar(foodSum.sumSugar(Integer.parseInt(Sugarstr)));
                foodSum.setSumKg(foodSum.sumKg(Integer.parseInt(Kgstr)));
                Date currentTime = Calendar.getInstance().getTime();
                User user = new User();
                CalendatRequest calendatRequest = new CalendatRequest(user.getId(), currentTime, extras.getInt("FoodCood"));
                RequestQueue queue = Volley.newRequestQueue(FoodAddActivity.this);
                queue.add(calendatRequest);
                Intent intent = new Intent(FoodAddActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
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
                switch (menuItem.getItemId()) {
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
                   /* case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), .class);
                        startActivity(manbogiintent);
                        return true;*/
                    case R.id.annoucement:
                        Intent annoucementintent = new Intent(getApplicationContext(), annoucement.class);
                        startActivity(annoucementintent);
                        return true;
                    case R.id.friend:
                        Intent friend = new Intent(getApplicationContext(), friend_list.class);
                        startActivity(friend);
                        return true;
                }
                return false;
            }
        });


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
}