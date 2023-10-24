package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.djsu.Food;
import com.example.djsu.Notice;
import com.example.djsu.R;
import com.example.djsu.exerciseLsit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminExerciseMain extends AppCompatActivity {
    private List<exerciseLsit> exerciselsit;
    private ArrayList<exerciseLsit> search_list;
    private AdminExerciseAdapter exerciseAdapter;
    ImageButton ExerciseAddBtn;
    private EditText editText;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ExName = "ExerciseName";
    String myJSON;
    JSONArray peoples = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_main);
        search_list = new ArrayList<>();
        editText = findViewById(R.id.searchtext);
        ExerciseAddBtn = (ImageButton)findViewById(R.id.exerciseaddBtn);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editText.getText().toString();
                search_list.clear();

                if(searchText.equals("")){
                    exerciseAdapter.setItems((ArrayList<exerciseLsit>) exerciselsit);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < exerciselsit.size(); a++) {
                        if (exerciselsit.get(a).getExerciseName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(exerciselsit.get(a));
                        }
                        exerciseAdapter.setItems(search_list);
                    }
                }
            }

        });
        ExerciseAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminExerciseMain.this, AdminExerciseSubAdd.class);
                startActivity(intent);
            }
        });

        exerciselsit = new ArrayList<>();

        exerciseAdapter = new AdminExerciseAdapter(this, exerciselsit);

        ListView exListView = (ListView) findViewById(R.id.listView);
        exListView.setAdapter(exerciseAdapter);

        getData("http://enejd0613.dothome.co.kr/exlist.php");
    }
    protected void showList() {
        exerciseAdapter.notifyDataSetChanged();
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String ExName = c.getString(TAG_ExName);
                    exerciseLsit exlsit = new exerciseLsit(ExName);
                    exerciselsit.add(exlsit);
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