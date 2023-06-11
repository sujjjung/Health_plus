package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class userFood extends AppCompatActivity {
    private List<User> userList;
    private UserFoodAdapter userFoodAdapter;
    private TextView datetext;
    String date;
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
    User user = new User();
    String myJSON;
    JSONArray peoples = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food);
        userList = new ArrayList<>();
        userFoodAdapter = new UserFoodAdapter(this,userList);
        ListView userfoodListView = (ListView) findViewById(R.id.FoodView);
        userfoodListView.setAdapter(userFoodAdapter);

        Bundle extras = getIntent().getExtras();
        datetext = findViewById(R.id.date_textView);
        date = extras.getString("Date");
        datetext.setText(date);
        userList.clear();
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
                        if(date.equals(Date)) {
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
}

