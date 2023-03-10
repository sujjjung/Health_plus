package com.example.djsu;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ExerciseRecordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private ArrayList<exrecode> exrecodeList;
    private exerciserecodeAdapter exAdapter;
    int setcount = 1;
    int num;
    private int count = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_record);
        TextView unit = findViewById(R.id.unittext);
        exrecodeList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exAdapter = new exerciserecodeAdapter(exrecodeList);
        recyclerView.setAdapter(exAdapter);
        ImageButton unitbtn = (ImageButton)findViewById(R.id.unitBtn);
        unitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExerciseRecordActivity.this, "단위가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                if(unit.getText().toString().equals("kg")) {unit.setText("ld");}
                else if (unit.getText().toString().equals("ld")){ unit.setText("kg");}
            }
        });

        ImageButton buttonInsert = (ImageButton)findViewById(R.id.add);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            // 1. 화면 아래쪽에 있는 데이터 추가 버튼을 클릭하면
            @Override
            public void onClick(View v) {
                if(setcount == 1) {
                    // 2. 레이아웃 파일 edit_box.xml 을 불러와서 화면에 다이얼로그를 보여줍니다.
                    AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseRecordActivity.this);
                    View view = LayoutInflater.from(ExerciseRecordActivity.this).inflate(R.layout.dialog_edit_box, null, false);
                    builder.setView(view);
                    final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                    final EditText editTextID = (EditText) view.findViewById(R.id.num);
                    final AlertDialog dialog = builder.create();

                    ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if(editTextID.getText().toString().equals("")){dialog.show(); Toast.makeText(ExerciseRecordActivity.this, "값을 입력해주세요", Toast.LENGTH_SHORT).show();}
                                else {
                                // 4. 사용자가 입력한 내용을 가져와서
                                String unit = editTextID.getText().toString();
                                String number = "0";
                                String setnumber = String.valueOf(setcount);

                                // 5. ArrayList에 추가하고
                                exrecode dict = new exrecode(setnumber, unit, number);
                                exrecodeList.add(0, dict); //첫번째 줄에 삽입됨
                                //mArrayList.add(dict); //마지막 줄에 삽입됨
                                // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                                exAdapter.notifyItemInserted(0);
                                //mAdapter.notifyDataSetChanged();

                                dialog.dismiss();
                                setcount++;
                                num = Integer.parseInt(unit);
                            }
                            }
                        });


                    dialog.show();

                }
                else {
                    String unit = String.valueOf(num);
                    String number = "0";
                    String setnumber = String.valueOf(setcount);

                    // 5. ArrayList에 추가하고
                    exrecode dict = new exrecode(setnumber, unit, number);
                    exrecodeList.add(0, dict); //첫번째 줄에 삽입됨
                    //mArrayList.add(dict); //마지막 줄에 삽입됨
                    // 6. 어댑터에서 RecyclerView에 반영하도록 합니다.
                    exAdapter.notifyItemInserted(0);
                    setcount++;}
            }
        });

        Bundle extras = getIntent().getExtras();
        String Name = "";
        Name = extras.getString("exName");
        TextView NameText = (TextView) findViewById(R.id.exname);
        String Namestr = Name;
        NameText.setText(Namestr);

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
                    case R.id.map:
                        Intent mapintent = new Intent(getApplicationContext(), map.class);
                        startActivity(mapintent);
                        return true;
                   /* case R.id.manbogi:
                        Intent manbogiintent = new Intent(getApplicationContext(), .class);
                        startActivity(manbogiintent);
                        return true;*/
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

}