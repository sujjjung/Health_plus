package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class UserExAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private Activity parentActivity;
    String ExerciseCode,ExerciseSetNumber,ExerciseNumber,ExerciseUnit,Time;
    int ExCode;
    TextView ExName,ExPart;
    String date,name ="";
    public UserExAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity;
    }
    @Override
    public int getCount () {
        return userList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return userList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<User> list) {
        userList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        View v = View.inflate(context, R.layout.item_userex_list, null);
        if (userList.get(position).getExerciseName().equals(name)) {
            return new View(context);
        }else {
            ExName = v.findViewById(R.id.exName);
            ExPart = v.findViewById(R.id.exPart);
            ExName.setText(userList.get(position).getExerciseName());
            ExPart.setText(userList.get(position).getExercisePart());
            name = userList.get(position).getExerciseName();
        }
        date = userList.get(position).getDate();
        ExCode = userList.get(position).getEcCode();
        v.setTag(userList.get(position).getId());
        Button DetailBtn = (Button) v.findViewById(R.id.Detail);
        DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle(userList.get(position).getExerciseName() + "상세정보"); // Set title

                StringBuilder messageBuilder = new StringBuilder();
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(position).getExerciseName().equals(userList.get(i).getExerciseName())) {
                        messageBuilder.append(userList.get(i).getExercisesetNumber() + "세트\n");
                        messageBuilder.append(" 횟수 " + userList.get(i).getExerciseNumber());
                        messageBuilder.append(" 무게 " + userList.get(i).getExerciseunit());
                        messageBuilder.append(" 시간 " + userList.get(i).getTime());
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


        Button deleteBtn = (Button) v.findViewById(R.id.Delete);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if (success) {
                                Toast.makeText(context.getApplicationContext(),  "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                userList.remove(position);//리스트에서 해당부분을 지워줌
                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                UserExDelete deleteRequest = new UserExDelete(name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteRequest);
            }
        });
        Button update = (Button) v.findViewById(R.id.Update);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
            }
        });
        return v;

    }

}