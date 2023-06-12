package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.djsu.Food;
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

public class Admindeclaration extends AppCompatActivity {
    private List<AdminManagement> DeclarationArrayList;
    private adminDeclarationAdapter DeclarationAdapter;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_declarationid = "declarationid";
    private static final String TAG_UserId = "UserId";
    private static final String TAG_postid = "postid";
    private static final String TAG_postname = "postname";
    private static final String TAG_cause = "cause";
    private static final String TAG_Date = "Date";
    private static final String TAG_type = "type";
    String myJSON;
    JSONArray peoples = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindeclaration);

        DeclarationArrayList = new ArrayList<>();

        DeclarationAdapter = new adminDeclarationAdapter(this, DeclarationArrayList);

        ListView DeclarationListView = (ListView) findViewById(R.id.ListView);
        DeclarationListView.setAdapter(DeclarationAdapter);

        getData("http://enejd0613.dothome.co.kr/declarationlist.php");
    }
        protected void showList() {
            DeclarationAdapter.notifyDataSetChanged();
            try {
                if (myJSON != null && !myJSON.isEmpty()) {
                    JSONObject jsonObj = new JSONObject(myJSON);
                    peoples = jsonObj.getJSONArray(TAG_RESULTS);

                    for(int i = 0;i < peoples.length(); i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        String declarationid = c.getString(TAG_declarationid);
                        String UserId = c.getString(TAG_UserId);
                        String postid = c.getString(TAG_postid);
                        String postname = c.getString(TAG_postname);
                        String cause = c.getString(TAG_cause);
                        String Date = c.getString(TAG_Date);
                        String type = c.getString(TAG_type);
                        AdminManagement adminManagement = new AdminManagement(Integer.parseInt(declarationid),Integer.parseInt(postid),UserId,postname,cause,Date,type);
                        DeclarationArrayList.add(adminManagement);
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
