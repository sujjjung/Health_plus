package com.example.djsu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class userRoutineAdapter extends BaseAdapter {
    private Context context;
    private List<User> routineList;
    TextView name;
    Button shape_yes_btn,detailBtn;
    int num = 0,Exnum = 0,communitynum;
    String RoutineName ="",Date,ExPart,ExName,ExCode,BtnText = "선택";
    public userRoutineAdapter(Context context, List<User> routineList, String Date,int communitynum) {
        this.context = context;
        this.routineList = routineList;
        this.Date = Date;
        this.communitynum = communitynum;
    }
    @Override
    public int getCount () {
        return routineList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return routineList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<User> list) {
        routineList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        View v = View.inflate(context, R.layout.item_routine, null);
        if (routineList.get(position).getRoutineName().equals(RoutineName)) {
            return new View(context);
        }else {
            name = v.findViewById(R.id.name);
            name.setText(routineList.get(position).getRoutineName());
            ExCode = routineList.get(position).getExerciseCode();
            ExName = routineList.get(position).getExerciseName();
            ExPart = routineList.get(position).getExercisePart();
            RoutineName = name.getText().toString();
        }
        shape_yes_btn = v.findViewById(R.id.shape_yes_btn);
        detailBtn = v.findViewById(R.id.detail_btn);
        shape_yes_btn.setText(BtnText);
        if(BtnText.equals("선택")){
        }else if(BtnText.equals("삭제")){
            detailBtn.setVisibility(View.VISIBLE);
        }
        shape_yes_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(communitynum == 1){
                    Intent intent = new Intent(context, community_post.class);
                    intent.putExtra("RoutineNameText", routineList.get(position).getRoutineName());
                    context.startActivity(intent);
                } else if(communitynum != 1){
                    if(BtnText.equals("선택")){
                        Intent intent = new Intent(context, ExerciseRecordActivity.class);
                        intent.putExtra("routineList", (Serializable) routineList);
                        intent.putExtra("Date", Date);
                        intent.putExtra("RoutineCount", 0);
                        intent.putExtra("index", 0);
                        if(routineList.get(position).getExercisePart().equals("유산소")){
                            Exnum =1;
                        }
                        intent.putExtra("num", Exnum);
                        intent.putExtra("RoutineNameText", routineList.get(position).getRoutineName());
                        context.startActivity(intent);
                    } else if(BtnText.equals("삭제")){
                        for(int i =0; i < routineList.size(); i++) {
                            if(routineList.get(i).getRoutineName().equals(routineList.get(position).getRoutineName())) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            boolean success = jsonResponse.getBoolean("success");
                                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                                            if (success) {
                                                Toast.makeText(context.getApplicationContext(), "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                                routineList.remove(position);//리스트에서 해당부분을 지워줌
                                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                UserRoutineDelete deleteRequest = new UserRoutineDelete(routineList.get(i).getRoutineName(), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(context);
                                queue.add(deleteRequest);
                            }
                        }
                    }
                }

            }
        });

        detailBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle(routineList.get(position).getRoutineName() + "상세정보"); // Set title

                StringBuilder messageBuilder = new StringBuilder();
                for (int i = 0; i < routineList.size(); i++) {
                    if (routineList.get(position).getRoutineName().equals(routineList.get(i).getRoutineName())) {
                        messageBuilder.append("설정운동 : " + routineList.get(i).getExerciseName());
                        messageBuilder.append("\n"); // Add a new line for each entry
                    }
                }
                dlg.setMessage(messageBuilder.toString()); // Set the message

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Display a toast message
                        Toast.makeText(context, "확인을 눌르셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                dlg.show();
            }
        });
        return v;

    }

    public void ButtonChange(int num) {
        this.num += num;

        if (this.num % 2 == 0 || this.num == 0) {
            BtnText = "선택";
        } else {
            BtnText = "삭제";
        }
        notifyDataSetChanged();
    }

}