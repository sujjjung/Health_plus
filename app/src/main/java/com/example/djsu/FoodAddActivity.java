package com.example.djsu;

import static java.lang.Double.sum;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FoodAddActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Button addButton,SumBtn,backButton,addtoButton;
    private List<User> userList;
    private FoodAddAdapter userFoodAdapter;
    ImageButton food_input, searchBtn;
    EditText NameText,KcalText,CarbohydratText,ProteinText,FatText,SodiumText,SugarText,KgText,DateText;
    String Namestr,Kcalstr,Carbohydratestr,Proteinstr,Fatstr,Sodiumstr,Sugarstr,Kgstr,Datestr;
    String Date,s = "0",Time;
    Bundle extras;
    int a,Cood,FcCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_add);

        extras = getIntent().getExtras();
        Date = extras.getString("Date");
        Datestr = Date;
        userList = new ArrayList<>();
        userFoodAdapter = new FoodAddAdapter(FoodAddActivity.this,userList);
        ListView userfoodListView = (ListView) findViewById(R.id.FoodView);
        userfoodListView.setAdapter(userFoodAdapter);
        Intent intent = getIntent();
        User user1 = new User();
        try {
            userFoodAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("UserFood"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0,FcCode;
            String eatingTime,Date,UserID,FoodName,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미f
                JSONObject object = jsonArray.getJSONObject(count);
                System.out.println(object);
                Date = object.getString("Date");
                FoodName = object.getString("FoodName");
                FoodKcal = object.getString("FoodKcal");
                FoodCarbohydrate = object.getString("FoodCarbohydrate");
                FoodProtein = object.getString("FoodProtein");
                FoodFat = object.getString("FoodFat");
                FoodSodium = object.getString("FoodSodium");
                FoodSugar = object.getString("FoodSugar");
                FoodKg = object.getString("FoodKg");
                eatingTime = object.getString("eatingTime");
                FcCode = object.getInt("FcCode");
                //값들을 User클래스에 묶어줍니다
                User user = new User(Date,FoodName,eatingTime,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg,FcCode);
                UserID = object.getString("UserID");
                if(UserID.equals(user1.getId())) {
                    if(Datestr.equals(Date)) {
                        userList.add(user);//리스트뷰에 값을 추가해줍니다
                    }
                }
                count++;
            };



        } catch (Exception e) {
            e.printStackTrace();
        }
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
                UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask();
                userFoodListBackgroundTask.execute();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s.equals(0)){
                    Toast.makeText(getApplicationContext(),"섭취시기를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = new User();
                    CalendatRequest calendatRequest = new CalendatRequest(user.getId(), Date, Cood, NameText.getText().toString(), KcalText.getText().toString(), CarbohydratText.getText().toString(), ProteinText.getText().toString()
                            , FatText.getText().toString(), SodiumText.getText().toString(), SugarText.getText().toString(), KgText.getText().toString(), s);
                    RequestQueue queue = Volley.newRequestQueue(FoodAddActivity.this);
                    queue.add(calendatRequest);
                    Toast.makeText(getApplicationContext(), "음식등록이 되었습니다.", Toast.LENGTH_SHORT).show();
                    new UserFoodListBackgroundTask().execute();
                }
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


                Toast.makeText(getApplicationContext(),"음식등록이 되었습니다.",Toast.LENGTH_SHORT).show();
                FoodaddListBackgroundTask userFoodListBackgroundTask = new FoodaddListBackgroundTask();
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
        ImageButton plus = (ImageButton) findViewById(R.id.plusBtn);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer.parseInt(Kgstr);
                Kgstr = Kgstr+100;
                KgText.setText(Kgstr+"");
            }
        });

        ImageButton minus = (ImageButton) findViewById(R.id.minusBtn);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Kgstr = String.valueOf(Integer.parseInt(Kgstr)-100);
                KgText.setText(Kgstr+"");
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
                        UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask();
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

    class FoodaddListBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://enejd0613.dothome.co.kr/foodcalendarlist.php";
        }

        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);//URL 객체 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(FoodAddActivity.this, FoodAddActivity.class);
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
            intent.putExtra("Date", Date);
            intent.putExtra("UserFood", result);
            startActivity(intent);
            finish();
        }
    }

    class UserFoodListBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;


        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://enejd0613.dothome.co.kr/foodcalendarlist.php";
        }
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);//URL 객체 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(FoodAddActivity.this, CalendarActivity.class);
            intent.putExtra("UserFood", result);
            startActivity(intent);
        }
    }

}