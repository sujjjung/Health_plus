package com.example.djsu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Food_List extends AppCompatActivity {

    private List<Food>foodArrayList;
    private FoodAdapter foodAdapter;
    private EditText editText;
    String Date,search;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_FoodCode = "FoodCode";
    private static final String TAG_FoodName = "FoodName";
    private static final String TAG_FoodKcal = "FoodKcal";
    private static final String TAG_FoodCarbohydrate = "FoodCarbohydrate";
    private static final String TAG_FoodProtein = "FoodProtein";
    private static final String TAG_FoodFat = "FoodFat";
    private static final String TAG_FoodSodium = "FoodSodium";
    private static final String TAG_FoodSugar = "FoodSugar";
    private static final String TAG_FoodKg = "FoodKg";
    String myJSON;
    JSONArray peoples = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        ArrayList<Food> search_list = new ArrayList<>();
        foodArrayList = new ArrayList<>();
        editText = findViewById(R.id.searchtext);
        // editText 리스터 작성

        Bundle extras = getIntent().getExtras();

        Date = extras.getString("Date");
        search = extras.getString("searchText");

        foodAdapter = new FoodAdapter(this,foodArrayList,Date);

        ListView foodListView = (ListView) findViewById(R.id.FoodView);
        foodListView.setAdapter(foodAdapter);
        int num = extras.getInt("num");
        switch (num){
            case 1: break;
            case 0:     editText.setText(search);
                String searchText = editText.getText().toString();
                search_list.clear();

                if (searchText.equals("")) {
                    foodAdapter.setItems((ArrayList<Food>) foodArrayList);
                } else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < foodArrayList.size(); a++) {
                        if (foodArrayList.get(a).getFoodName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(foodArrayList.get(a));
                        }
                        foodAdapter.setItems(search_list);
                    }
                };break;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = editText.getText().toString();
                search_list.clear();

                if (searchText.equals("")) {
                    foodAdapter.setItems((ArrayList<Food>) foodArrayList);
                } else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < foodArrayList.size(); a++) {
                        if (foodArrayList.get(a).getFoodName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(foodArrayList.get(a));
                        }
                        foodAdapter.setItems(search_list);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });


        getData("http://enejd0613.dothome.co.kr/foodlist.php");
    }
    protected void showList() {
        foodAdapter.notifyDataSetChanged();
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String FoodCode = c.getString(TAG_FoodCode);
                    String FoodName = c.getString(TAG_FoodName);
                    String FoodKcal = c.getString(TAG_FoodKcal);
                    String FoodCarbohydrate = c.getString(TAG_FoodCarbohydrate);
                    String FoodProtein = c.getString(TAG_FoodProtein);
                    String FoodFat = c.getString(TAG_FoodFat);
                    String FoodSodium = c.getString(TAG_FoodSodium);
                    String FoodSugar = c.getString(TAG_FoodSugar);
                    String FoodKg = c.getString(TAG_FoodKg);
                    Food food = new Food(Integer.parseInt(FoodCode),FoodName,Float.parseFloat(FoodKcal),Float.parseFloat(FoodCarbohydrate),Float.parseFloat(FoodProtein)
                            ,Float.parseFloat(FoodFat),Float.parseFloat(FoodSodium),Float.parseFloat(FoodSugar),Float.parseFloat(FoodKg));
                    foodArrayList.add(food);
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

