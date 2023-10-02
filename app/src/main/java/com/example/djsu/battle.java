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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
    JSONArray peoples = null;
    String roomId;
    int weight;
    User user = new User();
    private ArrayList<User> battleList = new ArrayList<>();
    battleAdapter battleAdapter;

    ListView cal_chartlist,time_chart,weight_chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        customDialog = new Dialog(battle.this);       // Dialog 초기화
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        customDialog.setContentView(R.layout.dialog_vs_goal_kg);             // xml 레이아웃 파일과 연결

        roomId = getIntent().getStringExtra("chatRoomId");
        // 버튼: 커스텀 다이얼로그 띄우기
        getData("http://enejd0613.dothome.co.kr/chttingList.php");

        battleAdapter =  new battleAdapter(battle.this, battleList);
        cal_chartlist = (ListView) findViewById(R.id.cal_chart);
        time_chart = (ListView) findViewById(R.id.time_chart);
        weight_chart = (ListView) findViewById(R.id.weight_chart);

        cal_chartlist.setAdapter(battleAdapter);
        time_chart.setAdapter(battleAdapter);
        weight_chart.setAdapter(battleAdapter);

        battleAdapter.notifyDataSetChanged();

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
                    String userId = c.getString(TAG_ID);
                    String weight = c.getString(TAG_weight);
                    User user1 = new User(userId,weight);
                    battleList.add(user1);
                    if(chttingName.equals(roomId) && userId.equals(user.getName())) {
                        String Addweight = c.getString(TAG_weight);
                        this.weight = Integer.parseInt(Addweight);
                    }
                }
                System.out.println(weight);
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
        private ArrayList<User> battleList;

        public battleAdapter(Context context, ArrayList<User> battleList) {
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
        public void setItems(ArrayList<User> list) {
            battleList = list;
            notifyDataSetChanged();
        }
        //리스트뷰에서 실질적으로 뿌려주는 부분임
        @Override
        public View getView (final int position, View convertView, ViewGroup parent){
            View v = View.inflate(context, R.layout.item_prog, null);
            // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);


            return v;

        }
    }
}