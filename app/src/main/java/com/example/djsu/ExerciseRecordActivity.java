package com.example.djsu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExerciseRecordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private ArrayList<exrecode> exrecodeList;
    private exerciserecodeAdapter exAdapter;
    private ArrayList<UserRoutine> RoutineListResult = new ArrayList<>();
    private UserRoutine userRoutine;
    private  ArrayList <User> RoutineList;
    private Thread timeThread = null;
    TextView mTimeTextView;
    private Boolean isRunning = true;
    String number, unitnum,Date,ExCode,RoutineNameText;
    int count = 1, RoutineCount = 0,num,setcount=1,index = 0,Exnum = 0 , excount = 0, setNum = 0;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_Date = "Date";
    private static final String TAG_UserId  = "UserId";
    private static final String TAG_ExName = "ExerciseName";
    private static final String TAG_ExerciseSetNumber = "ExerciseSetNumber";
    private static final String TAG_ExerciseNumber = "ExerciseNumber";
    private static final String TAG_ExerciseUnit= "ExerciseUnit";
    private static final String TAG_EcCode = "EcCode";
    String myJSON;
    JSONArray peoples = null;

    User user = new User();

    TextView NameText;

    private ArrayList<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_record);
        Bundle extras = getIntent().getExtras();
        Date = extras.getString("Date");
        index = extras.getInt("index");
        Exnum = extras.getInt("num");
        RoutineCount = extras.getInt("RoutineCount");
        RoutineNameText = extras.getString("RoutineNameText");
        RoutineList = (ArrayList <User>) getIntent(). getSerializableExtra("routineList");

        NameText = findViewById(R.id.exname);
        TextView PartText = (TextView) findViewById(R.id.expart);
        TextView monthText = (TextView) findViewById(R.id.month);
        int dashIndex = Date.indexOf("-");
        String extractedDate = Date.substring(dashIndex+1);
        monthText.setText( extractedDate.replace("-","월 ")+"일");
        mTimeTextView = findViewById(R.id.timeView);
        ImageButton TimerStartBtn = (ImageButton) findViewById(R.id.TimerStart);
        ImageButton TimerPauseBtn = (ImageButton) findViewById(R.id.TimerPause);
        ImageButton TimerStopBtn = (ImageButton) findViewById(R.id.TimerStop);
        TimerStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                isRunning = true;
                TimerStopBtn.setVisibility(View.VISIBLE);
                TimerPauseBtn.setVisibility(View.VISIBLE);

                if (timeThread == null || !timeThread.isAlive()) {
                    timeThread = new Thread(new timeThread());
                    timeThread.start();
                }
            }
        });

        TimerStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerStartBtn.setVisibility(View.VISIBLE);
                TimerPauseBtn.setVisibility(View.GONE);
                isRunning = false;

                if (timeThread != null) {
                    timeThread.interrupt();
                    timeThread = null;
                }
            }
        });

        TimerPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                isRunning = false;
                TimerStartBtn.setVisibility(View.VISIBLE);
                updateAdapterTimerValue( mTimeTextView.getText().toString());
            }
        });

        for(int i =0; i < RoutineList.size(); i++){
            if(RoutineList.get(i).getRoutineName().equals(RoutineNameText)){
                userRoutine = new UserRoutine(RoutineList.get(i).getRoutineName(),RoutineList.get(i).getExerciseCode(), RoutineList.get(i).getExercisePart(), RoutineList.get(i).getExerciseName());
                RoutineListResult.add(userRoutine);
            }
        }

        if(RoutineCount == 0){
            RoutineCount += RoutineListResult.size();
        }

        NameText.setText(RoutineListResult.get(index).getExerciseName());
        PartText.setText(RoutineListResult.get(index).getExPart());
        ExCode = RoutineListResult.get(index).getExCode();
        TextView unit = findViewById(R.id.textView53);
        exrecodeList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exAdapter = new exerciserecodeAdapter(exrecodeList,ExerciseRecordActivity.this);
        recyclerView.setAdapter(exAdapter);
        ImageButton unitbtn = (ImageButton)findViewById(R.id.unitBtn);
        if(Exnum == 1){
            TextView textView53 =(TextView) findViewById(R.id.textView53);
            textView53.setVisibility(View.GONE);
        }
        unitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Exnum == 0) {
                    Toast.makeText(ExerciseRecordActivity.this, "단위가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                    if (unit.getText().toString().equals("kg")) {
                        unit.setText("ld");
                    } else if (unit.getText().toString().equals("ld")) {
                        unit.setText("kg");
                    }
                }
            }
        });

        Button buttonInsert = (Button)findViewById(R.id.add);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            // 1. 화면 아래쪽에 있는 데이터 추가 버튼을 클릭하면
            @Override
            public void onClick(View v) {
                if(setcount == 1) {
                    // 2. 레이아웃 파일 edit_box.xml 을 불러와서 화면에 다이얼로그를 보여줍니다.
                    AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseRecordActivity.this, R.style.CustomDialog);
                    View view = LayoutInflater.from(ExerciseRecordActivity.this).inflate(R.layout.dialog_edit_box, null, false);
                    builder.setView(view);
                    final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                    final EditText editTextID = (EditText) view.findViewById(R.id.num);
                    final AlertDialog dialog = builder.create();
                    ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(editTextID.getText().toString().equals("")){Toast.makeText(ExerciseRecordActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();}
                                unitnum = editTextID.getText().toString();
                                AlertDialog.Builder countbuilder = new AlertDialog.Builder(ExerciseRecordActivity.this);
                                View viewCount = LayoutInflater.from(ExerciseRecordActivity.this).inflate(R.layout.dialog_setcount_box, null, false);
                                countbuilder.setView(viewCount);
                                final Button ButtonSubmit1 = (Button) viewCount.findViewById(R.id.button_dialog_submit);
                                final EditText editTextID1 = (EditText) viewCount.findViewById(R.id.num);
                                final AlertDialog dialog1 = countbuilder.create();
                                if(Exnum == 0) {
                                    ButtonSubmit1.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            if (editTextID1.getText().toString().equals("")) {
                                                Toast.makeText(ExerciseRecordActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // 4. 사용자가 입력한 내용을 가져와서
                                                String setnumber = String.valueOf(setcount);
                                                number = editTextID1.getText().toString();
                                                // 5. ArrayList에 추가하고
                                                exrecode dict = new exrecode(setnumber, unitnum, number);
                                                exrecodeList.add(dict); //첫번째 줄에 삽입됨
                                                // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                                                exAdapter.notifyItemInserted(0);
                                                //mAdapter.notifyDataSetChanged();
                                                dialog1.dismiss();
                                                dialog.dismiss();
                                                setcount++;
                                                setNum = setcount;
                                                num = Integer.parseInt(unitnum);

                                            }
                                        }
                                    });
                                    dialog1.show();
                                }else if(Exnum == 1){
                                    String setnumber = String.valueOf(setcount);
                                    number = "0";
                                    // 5. ArrayList에 추가하고
                                    exrecode dict = new exrecode(setnumber, unitnum, number);
                                    exrecodeList.add(dict); //첫번째 줄에 삽입됨
                                    // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                                    exAdapter.notifyItemInserted(0);
                                    //mAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                    setcount++;
                                    num = Integer.parseInt(unitnum);
                                }


                        }
                    });

                    dialog.show();

                }
                else {
                    String unit = String.valueOf(num);
                    String setnumber = String.valueOf(setcount + 1);

                    // 5. ArrayList에 추가하고
                    exrecode dict = new exrecode(setnumber, unit,number);
                    exrecodeList.add(dict); //첫번째 줄에 삽입됨
                    //mArrayList.add(dict); //마지막 줄에 삽입됨
                    // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                    exAdapter.notifyItemInserted(count);
                    count++;
                    setcount++;
                }
            }
        });
        User user = new User();

        Button SaveBtn = (Button) findViewById(R.id.Save);
        SaveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Set set = new Set();
                set.getSetArrayList();
                ArrayList<Set> setList = set.getSetArrayList();
                for (int i = 0; i < setList.size(); i++){
                    ExSelectRequest exSelectRequest = new ExSelectRequest(user.getId(),Date,ExCode,NameText.getText().toString(),PartText.getText().toString(),
                            setList.get(i).getSetNumber(),setList.get(i).getNumber(),setList.get(i).getUnit()+unit.getText(),setList.get(i).getTime());
                    RequestQueue queue = Volley.newRequestQueue(ExerciseRecordActivity.this);
                    queue.add(exSelectRequest);
                }
                Toast.makeText(ExerciseRecordActivity.this, "등록 완료되었습니다!", Toast.LENGTH_SHORT).show();

                if(RoutineCount == 1){
                    Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(ExerciseRecordActivity.this, ExerciseRecordActivity.class);
                    intent.putExtra("routineList", (Serializable) RoutineList);
                    intent.putExtra("Date", Date);
                    intent.putExtra("RoutineCount", --RoutineCount);
                    intent.putExtra("index", index + 1);
                    if(RoutineListResult.get(index + 1).getExPart().equals("유산소")){
                        Exnum =1;
                    }
                    intent.putExtra("num", Exnum);
                    intent.putExtra("RoutineNameText", RoutineNameText);
                    startActivity(intent);
                }
            }
        });

        Button LoadBtn = (Button)findViewById(R.id.Load);

        LoadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(excount == 0) {
                    getData("http://enejd0613.dothome.co.kr/excalendarlist.php");
                }else{
                    Toast.makeText(ExerciseRecordActivity.this, "이미 불러왔습니다!", Toast.LENGTH_SHORT).show();
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
                switch(menuItem.getItemId()){
                    case R.id.home:
                        Intent homeintent = new Intent(getApplicationContext(), main_user.class);
                        startActivity(homeintent);
                        return true;
                    case R.id.calender:
                        Intent calenderintent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(calenderintent);
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
                }
                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //둘다 타이머 관려된건데 뭘 의미하는지는 모르겠네
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int mSec = msg.arg1 % 100;
            int sec = (msg.arg1 / 100) % 60;
            int min = (msg.arg1 / 100) / 60;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d",  min, sec, mSec);

            mTimeTextView.setText(result);
        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {
            int i = 0;

            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = i++;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                mTimeTextView.setText("");
                                mTimeTextView.setText("00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                }
            }
        }
    }

    private void updateAdapterTimerValue(String value) {
        exAdapter.setTimerValue(value);
    }

    protected void showList() {
        String SubDate= "";
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);
                    String ExerciseName = c.getString(TAG_ExName);
                    if(UserId.equals(user.getId()) && ExerciseName.equals(NameText.getText().toString())) {
                        String Date = c.getString(TAG_Date);
                        if(excount == 0) {
                            SubDate = Date;
                            excount++;
                        }
                        if(SubDate.equals(Date)) {
                            String ExerciseSetNumber = c.getString(TAG_ExerciseSetNumber);
                            String ExerciseNumber = c.getString(TAG_ExerciseNumber);
                            String ExerciseUnit = c.getString(TAG_ExerciseUnit);
                            String result = ExerciseUnit.substring(0, ExerciseUnit.length() - 2);
                            setcount = Integer.parseInt(ExerciseSetNumber);
                            setcount += setNum;
                            count = Integer.parseInt(ExerciseSetNumber);
                            count += setNum;
                            num = Integer.parseInt(result);
                            number = ExerciseNumber;
                            exrecode dict = new exrecode(ExerciseSetNumber, result,ExerciseNumber);
                            exrecodeList.add(dict); //첫번째 줄에 삽입됨
                        }
                    }
                }
                Collections.reverse(exrecodeList);
                exAdapter.notifyDataSetChanged();
                setcount++;
                count++;
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