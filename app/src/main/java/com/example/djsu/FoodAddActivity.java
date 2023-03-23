package com.example.djsu;

import static java.lang.Double.sum;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.util.Calendar;

public class FoodAddActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Button addButton,SumBtn,backButton,addtoButton;
    ImageButton food_input, searchBtn;
    EditText NameText,KcalText,CarbohydratText,ProteinText,FatText,SodiumText,SugarText,KgText,DateText;
    String Namestr,Kcalstr,Carbohydratestr,Proteinstr,Fatstr,Sodiumstr,Sugarstr,Kgstr,Datestr;
    String Date,s,Time;
    Bundle extras;
    int a,Cood,FcCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);
        addButton = findViewById(R.id.addBtn);
        backButton = findViewById(R.id.backBtn);
        addtoButton = findViewById(R.id.addtoBtn);
        searchBtn = (ImageButton) findViewById(R.id.SearchBtn);
        food_input = (ImageButton) findViewById(R.id.food_input_btn);
        food_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodAddActivity.this, FoodAddInputActivity.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(FoodAddActivity.this);
                userFoodListBackgroundTask.execute();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(FoodAddActivity.this);
                userFoodListBackgroundTask.execute();
            }
        });
        extras = getIntent().getExtras();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                CalendatRequest calendatRequest = new CalendatRequest(user.getId(), Date, Cood,NameText.getText().toString(),KcalText.getText().toString(),CarbohydratText.getText().toString(),ProteinText.getText().toString()
                        ,FatText.getText().toString(),SodiumText.getText().toString(),SugarText.getText().toString(),KgText.getText().toString(),s);
                RequestQueue queue = Volley.newRequestQueue(FoodAddActivity.this);
                queue.add(calendatRequest);
                Intent intent = new Intent(FoodAddActivity.this, CalendarActivity.class);
                intent.putExtra("num", 1);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"음식등록이 되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        addtoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                CalendatRequest calendatRequest = new CalendatRequest(user.getId(), Date, Cood,NameText.getText().toString(),KcalText.getText().toString(),CarbohydratText.getText().toString(),ProteinText.getText().toString()
                        ,FatText.getText().toString(),SodiumText.getText().toString(),SugarText.getText().toString(),KgText.getText().toString(),s);
                RequestQueue queue = Volley.newRequestQueue(FoodAddActivity.this);
                queue.add(calendatRequest);
                Intent intent = new Intent(getApplicationContext(), FoodAddActivity.class);
                intent.putExtra("FoodName", String.valueOf(NameText.getText()));
                intent.putExtra("FoodKcal", Kcalstr);
                intent.putExtra("FoodCarbohydrate", Carbohydratestr);
                intent.putExtra("FoodProtein", Proteinstr);
                intent.putExtra("FoodFat", Fatstr);
                intent.putExtra("FoodSodium", Sodiumstr);
                intent.putExtra("FoodSugar", Sugarstr);
                intent.putExtra("FoodKg", Kgstr);
                intent.putExtra("FoodCood", Cood);
                intent.putExtra("Date", Date);
                intent.putExtra("FcCode", FcCode);
                intent.putExtra("Time", Time);
                Toast.makeText(getApplicationContext(),"음식등록이 되었습니다.",Toast.LENGTH_SHORT).show();
                UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(FoodAddActivity.this);
                userFoodListBackgroundTask.execute();
            }
        });
        s = getDT();
        String Name = "";
        String Kcal = "";
        String Carbohydrate = "";
        String Protein = "";
        String Fat = "";
        String Sodium = "";
        String Sugar = "";
        String Kg = "";


        Name = extras.getString("FoodName");
        Kcal = extras.getString("FoodKcal");
        Carbohydrate = extras.getString("FoodCarbohydrate");
        Protein = extras.getString("FoodProtein");
        Fat = extras.getString("FoodFat");
        Sodium = extras.getString("FoodSodium");
        Sugar = extras.getString("FoodSugar");
        Kg = extras.getString("FoodKg");
        Cood = extras.getInt("FoodCood");
        Date = extras.getString("Date");
        Time = extras.getString("Time");
        FcCode = extras.getInt("FcCode");
        NameText = (EditText) findViewById(R.id.nametext);
        KcalText = (EditText) findViewById(R.id.kcaltext);
        CarbohydratText = (EditText) findViewById(R.id.Carbohydratetext);
        ProteinText = (EditText) findViewById(R.id.Protintext);
        FatText = (EditText) findViewById(R.id.Fattext);
        SodiumText = (EditText) findViewById(R.id.Sodiumtext);
        SugarText = (EditText) findViewById(R.id.Sugartext);
        KgText = (EditText) findViewById(R.id.Kgtext);
        DateText = (EditText) findViewById(R.id.DateText);

        Namestr = Name;
        Kcalstr = Kcal;
        Carbohydratestr = Carbohydrate;
        Proteinstr = Protein;
        Fatstr = Fat;
        Sodiumstr = Sodium;
        Sugarstr = Sugar;
        Kgstr = Kg;
        Datestr = Date;

        NameText.setText(Namestr);
        KcalText.setText(Kcalstr);
        CarbohydratText.setText(Carbohydratestr);
        ProteinText.setText(Proteinstr);
        FatText.setText(Fatstr);
        SodiumText.setText(Sodiumstr);
        SugarText.setText(Sugarstr);
        KgText.setText(Kgstr);
        DateText.setText(Datestr);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if(id == R.id.BreakFast)
                {        s = "아침";
                }else if(id == R.id.BreakFastTo)
                {        s = "아점";
                }else if(id == R.id.Lunch)
                {        s = "점심";
                }else if(id == R.id.LunchTo)
                {        s = "점저";
                } else if(id == R.id.Dinner)
                {        s = "저녁";
                }else if(id == R.id.DinnerTo)
                {        s = "야식";
                }
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
                        UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(FoodAddActivity.this);
                        userFoodListBackgroundTask.execute();
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
                    case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), pedometer.class);
                        startActivity(manbogiintent);
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
    public String getDT() {
        Calendar cal = Calendar.getInstance();
        String h, mi, s;

        h = String.valueOf(cal.get(Calendar.HOUR));
        mi = String.valueOf(cal.get(Calendar.MINUTE));
        s = String.valueOf(cal.get(Calendar.SECOND));
        String Time = h +"시" + mi +"분" + s+"초";
        return Time;
    }

    public void Sum(int kg, int s){
        Intent intent = new Intent(getApplicationContext(), FoodAddActivity.class);
        intent.putExtra("FoodName", String.valueOf(NameText.getText()));
        intent.putExtra("FoodKcal",  String.valueOf(Integer.parseInt(Kcalstr) * kg));
        intent.putExtra("FoodCarbohydrate", String.valueOf(Integer.parseInt(Carbohydratestr)* kg));
        intent.putExtra("FoodProtein", String.valueOf(Integer.parseInt(Proteinstr)* kg));
        intent.putExtra("FoodFat", String.valueOf(Integer.parseInt(Fatstr)* kg));
        intent.putExtra("FoodSodium", String.valueOf(Integer.parseInt(Sodiumstr)* kg));
        intent.putExtra("FoodSugar", String.valueOf(Integer.parseInt(Sugarstr)* kg));
        intent.putExtra("FoodKg", String.valueOf(kg));
        intent.putExtra("FoodCood", Cood);
        intent.putExtra("Date", Date);
        intent.putExtra("num", s);
        Toast.makeText(getApplicationContext(),"음식등록이 되었습니다.",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    public void Update() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if (success) {
                                Toast.makeText(FoodAddActivity.this, "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                UserFoodUpdate UpdateRequest = new UserFoodUpdate(Date, NameText.getText().toString(), KcalText.getText().toString(), CarbohydratText.getText().toString(), ProteinText.getText().toString()
                        , FatText.getText().toString(), SodiumText.getText().toString(), SugarText.getText().toString(), KgText.getText().toString(), Time, FcCode, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FoodAddActivity.this);
                queue.add(UpdateRequest);
                Intent intent = new Intent(getApplicationContext(), FoodAddActivity.class);
                intent.putExtra("FoodName", String.valueOf(NameText.getText()));
                intent.putExtra("FoodKcal", Kcalstr);
                intent.putExtra("FoodCarbohydrate", Carbohydratestr);
                intent.putExtra("FoodProtein", Proteinstr);
                intent.putExtra("FoodFat", Fatstr);
                intent.putExtra("FoodSodium", Sodiumstr);
                intent.putExtra("FoodSugar", Sugarstr);
                intent.putExtra("FoodKg", Kgstr);
                intent.putExtra("FoodCood", Cood);
                intent.putExtra("Date", Date);

                intent.putExtra("FcCode", FcCode);
                intent.putExtra("Time", Time);
                Toast.makeText(getApplicationContext(), "음식수정이 되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

        });
    }
}