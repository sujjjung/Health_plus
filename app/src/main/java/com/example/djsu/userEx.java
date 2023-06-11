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

public class userEx extends AppCompatActivity {
    private List<User> userList;
    private UserExAdapter userExAdapter;
    private TextView datetext;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_Date = "Date";
    private static final String TAG_UserId  = "UserId";
    private static final String TAG_ExName = "ExerciseName";
    private static final String TAG_ExPart = "ExercisePart";
    private static final String TAG_ExerciseSetNumber = "ExerciseSetNumber";
    private static final String TAG_ExerciseNumber = "ExerciseNumber";
    private static final String TAG_ExerciseUnit= "ExerciseUnit";
    private static final String TAG_Time = "Time";
    private static final String TAG_EcCode = "EcCode";
    String myJSON;
    JSONArray peoples = null;
    String date;
    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ex);
        userList = new ArrayList<>();
        userExAdapter = new UserExAdapter(this,userList);
        ListView exView = (ListView) findViewById(R.id.exView);
        exView.setAdapter(userExAdapter);
        Bundle extras = getIntent().getExtras();
        datetext = findViewById(R.id.date_textView);
        date = extras.getString("Date");
        datetext.setText(date);

        getData("http://enejd0613.dothome.co.kr/excalendarlist.php");
    }
    protected void showList() {
        userExAdapter.notifyDataSetChanged();
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);
                    String Date = c.getString(TAG_Date);
                    if(UserId.equals(user.getId())) {
                        if(date.equals(Date)) {;
                            String ExerciseName = c.getString(TAG_ExName);
                            String ExPart = c.getString(TAG_ExPart);
                            String ExerciseSetNumber = c.getString(TAG_ExerciseSetNumber);
                            String ExerciseNumber = c.getString(TAG_ExerciseNumber);
                            String ExerciseUnit = c.getString(TAG_ExerciseUnit);
                            String Time = c.getString(TAG_Time);
                            String EcCode = c.getString(TAG_EcCode);
                            User user = new User(Date,ExerciseName,ExPart,ExerciseSetNumber,ExerciseNumber,ExerciseUnit,Time,Integer.parseInt(EcCode));
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

