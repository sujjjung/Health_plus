package com.example.djsu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;

import android.graphics.Color;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class mypage extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    BarChart barChart;

    final static private String URL = "http://enejd0613.dothome.co.kr/Register.php";
    private static String TAG = "djsu";

    private String mJsonString = "";

    private static final String TAG_JSON = "webnautes";
    private static final String TAG_USERID = "userId";
    private static final String TAG_DATE = "date";
    private static final String TAG_FAT = "fat";
    private static final String TAG_MUSCLE = "muscle";
    private static final String TAG_WEIGHT = "wegiht";

    ArrayList<String> dateChart = new ArrayList<>(); // ArrayList 선언
    ArrayList<Integer> weightChart = new ArrayList<>(); // ArrayList 선언
    ArrayList<Integer> fatChart = new ArrayList<>(); // ArrayList 선언
    ArrayList<Integer> muscleChart = new ArrayList<>(); // ArrayList 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
                        UserFoodListBackgroundTask userFoodListBackgroundTask = new UserFoodListBackgroundTask(mypage.this);
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
                   /* case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), .class);
                        startActivity(manbogiintent);
                        return true;*/
                    case R.id.annoucement:
                        NoticeBackgroundTask noticeBackgroundTask = new NoticeBackgroundTask(mypage.this);
                        noticeBackgroundTask.execute();
                        return true;
                }
                return false;
            }
        });

        GetData task = new GetData();
        task.execute("http://enejd0613.dothome.co.kr/fat.php", "");

        barChart = (BarChart) findViewById(R.id.weight_chart);
        graphInitSetting();       //그래프 기본 세팅
    }

    public void graphInitSetting() {
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String userId = item.getString(TAG_USERID);
                String date = item.getString(TAG_DATE);
                int fat = item.getInt(TAG_FAT);
                int muscle = item.getInt(TAG_MUSCLE);
                int weight = item.getInt(TAG_WEIGHT);

                dateChart.add(date);
                weightChart.add(weight);
                fatChart.add(fat);
                muscleChart.add(muscle);
            }
        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }

        BarChartGraph(dateChart, weightChart);
        barChart.setTouchEnabled(false); //확대하지못하게 막아버림! 별로 안좋은 기능인 것 같아~
        //barChart.setRendererLeftYAxis();
//        barChart.setMaxVisibleValueCount(50);
//        barChart.setTop(50);
//        barChart.setBottom(0);
//        barChart.setAutoScaleMinMaxEnabled(true);
        barChart.getAxisRight().setAxisMaxValue(80);
        barChart.getAxisLeft().setAxisMaxValue(80);
    }

    private void BarChartGraph(ArrayList<String> dateChart, ArrayList<Integer> weightChart) {
        // BarChart 메소드

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < weightChart.size(); i++) {
            entries.add(new BarEntry((Integer) weightChart.get(i), i));
        }

        BarDataSet depenses = new BarDataSet(entries, "몸무게 차트"); // 변수로 받아서 넣어줘도 됨
        depenses.setAxisDependency(YAxis.AxisDependency.LEFT);
        barChart.setDescription(" ");

        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < dateChart.size(); i++) {
            labels.add((String) dateChart.get(i));
        }

        BarData data = new BarData(labels, depenses); // 라이브러리 v3.x 사용하면 에러 발생함
        depenses.setColors(ColorTemplate.LIBERTY_COLORS); //

        barChart.setData(data);
        barChart.animateXY(100, 100);
        barChart.invalidate();
    }

    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(mypage.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }
    }
}