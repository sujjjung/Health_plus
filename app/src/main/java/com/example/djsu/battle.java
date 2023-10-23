package com.example.djsu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.djsu.admin.adminUser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class battle extends AppCompatActivity {
    Dialog customDialog; // 커스텀 다이얼로그
    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_chttingName = "chttingName";
    private static final String TAG_ID = "userId";
    private static final String TAG_weight = "weight";
    private static final String TAG_UserId  = "UserId";
    private static final String TAG_Time  = "Time";
    private static final String TAG_FatId  = "userId";
    private static final String TAG_UserID  = "UserID";
    private static final String TAG_UserName  = "UserName";
    private static final String TAG_UserProfile = "UserProfile";
    private static final String TAG_FoodDate = "Date";
    private static final String TAG_FoodUserId  = "UserId";
    private static final String TAG_FoodKcal = "FoodKcal";
    private ImageView burnkcal,eatKcal,kg;
    private float Exweight, minute,second,secondSum,ExKcalNum;
    JSONArray peoples = null;
    String roomId, date;
    int ExTime,weight,KcalNum = 0,max = 0,foodMax = 0,exMax = 0 , foodNum = 0, num = 0,ExNum = 0;
    User user = new User(), user1;
    private ProgressBar progressBar;
    private ArrayList<String> UserNameList = new ArrayList<>(),UserIdList = new ArrayList<>(),UserProfileList = new ArrayList<>(), ExList = new ArrayList<>();
    private ArrayList<User> battleList = new ArrayList<>();
    private ArrayList<Battle_Results> battleExList = new ArrayList<>(), battleWeightList = new ArrayList<>(),FoodKcalList = new ArrayList<>();
    battleAdapter battleAdapter;
    timeBattleAdapter timebattleAdapter;
    weightBattleAdapter weightbattleAdapter;
    Battle_Results battle_results = new Battle_Results();
    TextView burkcal_username1,eatkcal_username,kg_username;
    ListView cal_chartlist,time_chart,weight_chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        customDialog = new Dialog(battle.this);       // Dialog 초기화
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        customDialog.setContentView(R.layout.dialog_vs_goal_kg);             // xml 레이아웃 파일과 연결
        date = getTime();
        roomId = getIntent().getStringExtra("chatRoomId");

        burkcal_username1 = findViewById(R.id.burkcal_username);
        eatkcal_username = findViewById(R.id.eatkcal_username);
        kg_username = findViewById(R.id.kg_username);

        burnkcal = findViewById(R.id.burnkcal);
        eatKcal = findViewById(R.id.eatKcal);
        kg = findViewById(R.id.imageView2);
        // 버튼: 커스텀 다이얼로그 띄우기
        getData("http://enejd0613.dothome.co.kr/chttingList.php");
        getUserData("http://enejd0613.dothome.co.kr/getUser.php");
        getFatData("http://enejd0613.dothome.co.kr/getWeight.php");
        getExData("http://enejd0613.dothome.co.kr/excalendarlist.php");
        getFoodData("http://enejd0613.dothome.co.kr/foodcalendarlist.php");
        battleAdapter =  new battleAdapter(battle.this, battleExList);
        timebattleAdapter = new timeBattleAdapter(battle.this, FoodKcalList);
        weightbattleAdapter = new weightBattleAdapter(battle.this, battleWeightList);

        cal_chartlist = (ListView) findViewById(R.id.cal_chart);
        time_chart = (ListView) findViewById(R.id.time_chart);
        weight_chart = (ListView) findViewById(R.id.weight_chart);

        cal_chartlist.setAdapter(battleAdapter);
        time_chart.setAdapter(timebattleAdapter);
        weight_chart.setAdapter(weightbattleAdapter);

        battleAdapter.notifyDataSetChanged();
        timebattleAdapter.notifyDataSetChanged();
        weightbattleAdapter.notifyDataSetChanged();
    }

    // dialog01을 디자인하는 함수
    public void showDialog01(){
        customDialog.show(); // 다이얼로그 띄우기
        EditText txt_contents = customDialog.findViewById(R.id.txt_contents);
        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // 아니오 버튼
        Button noBtn = customDialog.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                Intent intent1 = new Intent(battle.this, chat_room.class);
                intent1.putExtra("chatRoomId", roomId);
                startActivity(intent1);
                finish();
                customDialog.dismiss(); // 다이얼로그 닫기
            }
        });
        // 네 버튼
        customDialog.findViewById(R.id.yesBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                if(txt_contents.equals("00") || txt_contents.equals("")){
                    Toast.makeText(battle.this, "몸무게를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (txt_contents.equals("") == false || txt_contents.equals("00") == false) {
                    chttingUpdate chttingRequest = new chttingUpdate(roomId,user.getName(),Integer.parseInt(txt_contents.getText().toString()));
                    RequestQueue queue = Volley.newRequestQueue(battle.this);
                    queue.add(chttingRequest);
                    customDialog.dismiss();
                }
            }
        });
    }

    public class chttingUpdate extends StringRequest {
        final static private String URL = "http://enejd0613.dothome.co.kr/chttingUpdate.php";
        private Map<String, String> map;

        public chttingUpdate(String chttingName ,String userId,int weight) {
            super(Method.POST, URL, null, null);
            map = new HashMap<>();
            map.put("chttingName",chttingName);
            map.put("userId",userId);
            map.put("weight",weight + "");
        }
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return map;
        }
    }
    protected void showList() {
        try {

            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String chttingName = c.getString(TAG_chttingName);
                    if(chttingName.equals(roomId) ) {
                        String userId = c.getString(TAG_ID);
                        String weight = c.getString(TAG_weight);
                        user1 = new User(userId, weight);
                        battleList.add(user1);
                        if (userId.equals(user.getName())) {
                            String Addweight = c.getString(TAG_weight);
                            this.weight = Integer.parseInt(Addweight);
                        }
                    }
                }
                if(weight == 0){
                    showDialog01();
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

    public class battleAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Battle_Results> battleList;

        public battleAdapter(Context context, ArrayList<Battle_Results> battleList) {
            this.context = context;
            this.battleList = battleList;
        }
        @Override
        public int getCount () {
            return battleList.size();//리스트뷰의 총 갯수
        }

        @Override
        public Object getItem (int position){
            return battleList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
        }
        @Override
        public long getItemId (int position){
            return position;
        }
        public void setItems(ArrayList<Battle_Results> list) {
            battleList = list;
            notifyDataSetChanged();
        }
        //리스트뷰에서 실질적으로 뿌려주는 부분임
        @Override
        public View getView (final int position, View convertView, ViewGroup parent){
            View v = View.inflate(context, R.layout.item_prog, null);
            TextView burkcal_username = v.findViewById(R.id.burkcal_username);
            burkcal_username.setText(UserNameList.get(position));

// 프로그래스바와 TextView 초기화
            progressBar = v.findViewById(R.id.progressView1);
            progressBar.setMax(exMax);

// 어떤 활동을 수행할 때 진행률을 업데이트하려면 아래와 같이 호출
// 예를 들어, 진행률을 ExKcalNum로 업데이트하려면
            int currentProgress = (int)battleExList.get(position).getKcal();
            progressBar.setProgress(currentProgress);

            if(exMax < currentProgress){
                exMax = currentProgress;
                ExNum = position;
            }
            burkcal_username1.setText(UserNameList.get(ExNum));

            String profileImageUrl = UserProfileList.get(ExNum);
            Picasso.get().load(profileImageUrl).into(burnkcal);
            return v;

        }
    }
    public class timeBattleAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Battle_Results> battleList;

        public timeBattleAdapter(Context context, ArrayList<Battle_Results> battleList) {
            this.context = context;
            this.battleList = battleList;
        }
        @Override
        public int getCount () {
            return battleList.size();//리스트뷰의 총 갯수
        }

        @Override
        public Object getItem (int position){
            return battleList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
        }
        @Override
        public long getItemId (int position){
            return position;
        }
        public void setItems(ArrayList<Battle_Results> list) {
            battleList = list;
            notifyDataSetChanged();
        }
        //리스트뷰에서 실질적으로 뿌려주는 부분임
        @Override
        public View getView (final int position, View convertView, ViewGroup parent){
            View v = View.inflate(context, R.layout.item_prog, null);
            TextView burkcal_username = v.findViewById(R.id.burkcal_username);
            burkcal_username.setText(UserNameList.get(position));

// 프로그래스바와 TextView 초기화
            progressBar = v.findViewById(R.id.progressView1);
            progressBar.setMax(1500);

// 어떤 활동을 수행할 때 진행률을 업데이트하려면 아래와 같이 호출
// 예를 들어, 진행률을 ExKcalNum로 업데이트하려면
            int currentProgress = FoodKcalList.get(position).getFoodKcal();
            progressBar.setProgress(currentProgress);

            if(foodMax < currentProgress){
                foodMax = currentProgress;
                foodNum = position;
            }
            eatkcal_username.setText(UserNameList.get(foodNum));

            String profileImageUrl = UserProfileList.get(foodNum);
            Picasso.get().load(profileImageUrl).into(eatKcal);
            return v;

        }
    }

    public class weightBattleAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Battle_Results> battleList;

        public weightBattleAdapter(Context context, ArrayList<Battle_Results> battleList) {
            this.context = context;
            this.battleList = battleList;
        }
        @Override
        public int getCount () {
            return battleList.size();//리스트뷰의 총 갯수
        }

        @Override
        public Object getItem (int position){
            return battleList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
        }
        @Override
        public long getItemId (int position){
            return position;
        }
        public void setItems(ArrayList<Battle_Results> list) {
            battleList = list;
            notifyDataSetChanged();
        }
        //리스트뷰에서 실질적으로 뿌려주는 부분임
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = View.inflate(context, R.layout.item_prog, null);
            TextView burkcal_username = v.findViewById(R.id.burkcal_username);
            burkcal_username.setText(UserNameList.get(position));
            int a;
            a = weight - Integer.parseInt(battleWeightList.get(position).getWeight());


// 프로그래스바와 TextView 초기화
            progressBar = v.findViewById(R.id.progressView1);
            progressBar.setMax(100);

// 어떤 활동을 수행할 때 진행률을 업데이트하려면 아래와 같이 호출
// 예를 들어, 진행률을 ExKcalNum로 업데이트하려면
            int currentProgress = a;
            progressBar.setProgress(currentProgress);
            if(max < a){
                max = a;
                num = position;
            }
            kg_username.setText(UserNameList.get(num));

            String profileImageUrl = UserProfileList.get(num);
            Picasso.get().load(profileImageUrl).into(kg);
            return v;
        }

    }

    protected void showExList() {
        try {
            String userid = "";
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int j = 0;j < UserIdList.size(); j++) {
                    minute = 0;
                    ExKcalNum = 0 ;
                    for(int i = 0;i < peoples.length(); i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        String UserId = c.getString(TAG_UserId);
                        String Date1 = c.getString(TAG_FoodDate);
                        if (UserId.equals(UserIdList.get(j))) {
                            if (date.equals(Date1)) {
                                String Time = c.getString(TAG_Time);
                                minute += Float.parseFloat(Time.substring(0, 2));
                                second += Float.parseFloat(Time.substring(3, 5));
                                secondSum = second / 60;
                                minute += secondSum;
                                ExKcalNum += (float) (minute * (6 * 0.0175 * Exweight));
                                ExTime += Math.round(minute);
                            }
                        }
                    }
                    battle_results = new Battle_Results(UserIdList.get(j),ExKcalNum,ExTime);
                    battleExList.add(battle_results);
                }
            }
            battleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getExData(String url) {
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
                showExList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    protected void showFatList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String userId = c.getString(TAG_FatId);
                    for(int j = 0;j < UserIdList.size(); j++) {
                        if (userId.equals(UserIdList.get(j))) {
                            String weight = c.getString(TAG_weight);
                            this.Exweight =  Float.parseFloat(weight);
                            Battle_Results battle_results1 = new Battle_Results(UserIdList.get(j),weight);
                            battleWeightList.add(battle_results1);
                        }
                    }

                }
            }
            weightbattleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getFatData(String url) {
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
                showFatList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    protected void showUserList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserName = c.getString(TAG_UserName);
                    for(int j = 0;j < battleList.size(); j++) {
                        if (UserName.equals(battleList.get(j).getBattleUserName())) {
                            String UserID = c.getString(TAG_UserID);
                            String UserProfile = c.getString(TAG_UserProfile);
                            UserIdList.add(UserID);
                            UserNameList.add(battleList.get(j).getBattleUserName());
                            UserProfileList.add(UserProfile);
                        }
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getUserData(String url) {
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
                showUserList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);

    }

    protected void showFoodList() {

        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_FoodUserId);
                    String Date = c.getString(TAG_FoodDate);
                    for(int j = 0;j < UserIdList.size(); j++) {
                        if (UserId.equals(UserIdList.get(j))) {
                            if(date.equals(Date)) {
                                String FoodKcal = c.getString(TAG_FoodKcal);
                                KcalNum += Integer.parseInt(FoodKcal);
                                Battle_Results battle_results1 = new Battle_Results(UserIdList.get(j),KcalNum);
                                FoodKcalList.add(battle_results1);
                            }
                        }
                    }
                }
            }
            timebattleAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateProgressBar(int newProgress) {
        progressBar.setProgress(newProgress);
    }

    public void getFoodData(String url) {
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
                showFoodList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}