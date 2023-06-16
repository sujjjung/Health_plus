package com.example.djsu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FoodUpdate extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    EditText NameText,KcalText,CarbohydratText,ProteinText,FatText,SodiumText,SugarText,KgText,DateText,setText,searchText;
    String Namestr,Kcalstr,Carbohydratestr,Proteinstr,Fatstr,Sodiumstr,Sugarstr,Kgstr,Datestr;
    String s = "0",Time;
    int setSum;
    User user = new User();
    ImageButton searchBtn;
    Double KcalSum,CarbohydratSum, ProteinSum, FatSum ,SodiumSum,SugarSum,KgSum,kgSum;
    private List<User> userList;
    private FoodAddAdapter userFoodAdapter;
    DatePickerDialog datePickerDialog;
    Bundle extras;
    Double KcalNum,CarbohydratNum, ProteinNum, FatNum ,SodiumNum,SugarNum,KgNum;
    int Cood,FcCode;
    private Button addButton,backButton;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_Date = "Date";
    private static final String TAG_UserId  = "UserId";
    private static final String TAG_FoodName = "FoodName";
    private static final String TAG_FoodKcal = "FoodKcal";
    private static final String TAG_FoodCarbohydrate = "FoodCarbohydrate";
    private static final String TAG_FoodProtein = "FoodProtein";
    private static final String TAG_FoodFat = "FoodFat";
    private static final String TAG_FoodSodium = "FoodSodium";
    private static final String TAG_FoodSugar = "FoodSugar";
    private static final String TAG_FoodKg = "FoodKg";
    private static final String TAG_FcCode  = "FcCode";
    private static final String TAG_eatingTime	 = "eatingTime";
    private static final String TAG_quantity = "quantity";
    String myJSON;
    JSONArray peoples = null;
    String Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_update);
        extras = getIntent().getExtras();
        Date = extras.getString("Date");
        Datestr = Date;
        userList = new ArrayList<>();
        userFoodAdapter = new FoodAddAdapter(FoodUpdate.this, userList);
        ListView userfoodListView = (ListView) findViewById(R.id.FoodView);
        userfoodListView.setAdapter(userFoodAdapter);

        searchBtn = (ImageButton) findViewById(R.id.SearchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodUpdate.this, Food_List.class);
                intent.putExtra("Date",Datestr);
                intent.putExtra("searchText",searchText.getText().toString());
                intent.putExtra("num", 0);
                startActivity(intent);
                finish();
            }
        });
        searchText = (EditText) findViewById(R.id.searchText);
        ImageButton calendarBtn = findViewById(R.id.calendarbtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //오늘 날짜(년,월,일) 변수에 담기
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR); //년
                int pMonth = calendar.get(Calendar.MONTH);//월
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);//일

                datePickerDialog = new DatePickerDialog(FoodUpdate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                //1월은 0부터 시작하기 때문에 +1을 해준다.
                                month = month + 1;
                                Date = year + "-" + month + "-" + day;
                                DateText.setText(Date);
                            }
                        }, pYear, pMonth, pDay);
                datePickerDialog.show();
            } //onClick
        });

        addButton = findViewById(R.id.addBtn);
        backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodUpdate.this, userFood.class);
                intent.putExtra("Date", Date);
                startActivity(intent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                UserFoodUpdate UpdateRequest = new UserFoodUpdate(Date, NameText.getText().toString(), KcalText.getText().toString(), CarbohydratText.getText().toString(), ProteinText.getText().toString()
                        , FatText.getText().toString(), SodiumText.getText().toString(), SugarText.getText().toString(), KgText.getText().toString(), Time, FcCode,setText.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(FoodUpdate.this);
                queue.add(UpdateRequest);
                Toast.makeText(getApplicationContext(), "음식수정이 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FoodUpdate.this, userFood.class);
                intent.putExtra("Date", Date);
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
        int quantity;

        quantity = extras.getInt("quantity");
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
        setText = (EditText) findViewById(R.id.settext);
        setText.setText(String.valueOf(quantity));
        Namestr = Name;
        Kcalstr = Kcal;
        Carbohydratestr = Carbohydrate;
        Proteinstr = Protein;
        Fatstr = Fat;
        Sodiumstr = Sodium;
        Sugarstr = Sugar;
        Kgstr = Kg;
        kgSum =  Double.valueOf(Kgstr);
        NameText.setText(Namestr);
        KcalText.setText(Kcalstr);
        CarbohydratText.setText(Carbohydratestr);
        ProteinText.setText(Proteinstr);
        FatText.setText(Fatstr);
        SodiumText.setText(Sodiumstr);
        SugarText.setText(Sugarstr);
        KgText.setText(Kgstr);
        DateText.setText(Datestr);
        setSum =  quantity;
        KcalNum = Double.valueOf(Kcalstr) /quantity;
        CarbohydratNum = Double.valueOf(Carbohydratestr) /quantity;
        ProteinNum = Double.valueOf(Proteinstr) /quantity;
        FatNum = Double.valueOf(Fatstr) /quantity;
        SodiumNum = Double.valueOf(Sodiumstr) /quantity;
        SugarNum = Double.valueOf(Sugarstr) /quantity;
        KgNum = Double.valueOf(Kgstr) /quantity;
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.BreakFast) {
                    s = "아침";
                } else if (id == R.id.BreakFastTo) {
                    s = "아점";
                } else if (id == R.id.Lunch) {
                    s = "점심";
                } else if (id == R.id.LunchTo) {
                    s = "점저";
                } else if (id == R.id.Dinner) {
                    s = "저녁";
                } else if (id == R.id.DinnerTo) {
                    s = "야식";
                }
            }
        });
        ImageButton plus = (ImageButton) findViewById(R.id.plusBtn);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSum = setSum + 1;
                setText.setText(String.valueOf(setSum));
                KcalSum = Double.valueOf(KcalNum) * setSum;
                KcalText.setText(String.valueOf(KcalSum));
                CarbohydratSum = Double.valueOf(CarbohydratNum) * setSum;
                CarbohydratText.setText(String.valueOf(CarbohydratSum));
                ProteinSum = Double.valueOf(ProteinNum) * setSum;
                ProteinText.setText(String.valueOf(ProteinSum));
                FatSum = Double.valueOf(FatNum) * setSum;
                FatText.setText(String.valueOf(FatSum));
                SodiumSum = Double.valueOf(SodiumNum) * setSum;
                SodiumText.setText(String.valueOf(SodiumSum));
                SugarSum = Double.valueOf(SugarNum) * setSum;
                SugarText.setText(String.valueOf(SugarSum));
                KgSum = Double.valueOf(KgNum) * setSum;
                KgText.setText(String.valueOf(KgSum));
            }
        });

        ImageButton minus = (ImageButton) findViewById(R.id.minusBtn);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setText.getText().toString().equals("1")){
                    Toast.makeText(getApplicationContext(),"수량 1밑으로는 내릴수 없습니다.",Toast.LENGTH_SHORT).show();
                }else {
                    setSum = setSum - 1;
                    setText.setText(String.valueOf(setSum));
                    KcalSum = Double.valueOf(KcalText.getText().toString()) - Double.valueOf(KcalNum);
                    KcalText.setText(String.valueOf(KcalSum));
                    CarbohydratSum = Double.valueOf(CarbohydratText.getText().toString()) - Double.valueOf(CarbohydratNum);
                    CarbohydratText.setText(String.valueOf(CarbohydratSum));
                    ProteinSum = Double.valueOf(ProteinText.getText().toString()) - Double.valueOf(ProteinNum);
                    ProteinText.setText(String.valueOf(ProteinSum));
                    FatSum = Double.valueOf(FatText.getText().toString()) - Double.valueOf(FatNum);
                    FatText.setText(String.valueOf(FatSum));
                    SodiumSum = Double.valueOf(SodiumText.getText().toString()) - Double.valueOf(SodiumNum);
                    SodiumText.setText(String.valueOf(SodiumSum));
                    SugarSum = Double.valueOf(SugarText.getText().toString()) - Double.valueOf(SugarNum);
                    SugarText.setText(String.valueOf(SugarSum));
                    KgSum = Double.valueOf(KgText.getText().toString()) - Double.valueOf(KgNum);
                    KgText.setText(String.valueOf(KgSum));
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
                        Intent intent = new Intent(FoodUpdate.this, CalendarActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.communety:
                        Intent communetyintent = new Intent(getApplicationContext(), community.class);
                        startActivity(communetyintent);
                        return true;
                    case R.id.mypage:
                        Intent mypageintent = new Intent(getApplicationContext(), mypage.class);
                        startActivity(mypageintent);
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


        getData("http://enejd0613.dothome.co.kr/foodcalendarlist.php");
    }
    protected void showList() {
        userFoodAdapter.notifyDataSetChanged();
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);
                    String Date = c.getString(TAG_Date);
                    if(UserId.equals(user.getId())) {
                        if(Datestr.equals(Date)) {
                            String FoodName = c.getString(TAG_FoodName);
                            String eatingTime = c.getString(TAG_eatingTime);
                            String FoodKcal = c.getString(TAG_FoodKcal);
                            String FoodCarbohydrate = c.getString(TAG_FoodCarbohydrate);
                            String FoodProtein = c.getString(TAG_FoodProtein);
                            String FoodFat = c.getString(TAG_FoodFat);
                            String FoodSodium = c.getString(TAG_FoodSodium);
                            String FoodSugar = c.getString(TAG_FoodSugar);
                            String FoodKg = c.getString(TAG_FoodKg);
                            String FcCode = c.getString(TAG_FcCode);
                            String quantity = c.getString(TAG_quantity);
                            User user = new User(Date,FoodName,eatingTime,FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg,Integer.parseInt(FcCode),Integer.parseInt(quantity));
                            userList.add(user);//리스트뷰에 값을 추가해줍니다
                        }
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