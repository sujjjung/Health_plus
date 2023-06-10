package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.djsu.Food;
import com.example.djsu.FoodAdapter;
import com.example.djsu.MainActivity;
import com.example.djsu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminFoodMain extends AppCompatActivity {
    private List<Food>foodArrayList;
    private AdminFoodAdapter foodAdapter;
    private EditText editText;
    ImageButton foodAddBtn;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_FoodName = "FoodName";
    String myJSON;
    JSONArray peoples = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_main);
        foodAddBtn = (ImageButton)findViewById(R.id.foodaddBtn);

        foodAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminFoodMain.this, AdminFoodAdd.class);
                startActivity(intent);
            }
        });
        foodArrayList =new ArrayList<>();

        foodAdapter = new AdminFoodAdapter(this,foodArrayList);

        ListView foodListView = (ListView) findViewById(R.id.FoodView);
        foodListView.setAdapter(foodAdapter);

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
                    String FoodName = c.getString(TAG_FoodName);
                    Food food = new Food(FoodName);
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
