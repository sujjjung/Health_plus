package com.example.djsu;

import android.content.Context;
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
    String RoutineName ="",Date,ExPart,ExName,ExCode;
    public userRoutineAdapter(Context context, List<User> routineList, String Date) {
        this.context = context;
        this.routineList = routineList;
        this.Date = Date;
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
        Button shape_yes_btn = (Button) v.findViewById(R.id.shape_yes_btn);
        shape_yes_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, ExerciseRecordActivity.class);
                intent.putExtra("routineList", (Serializable) routineList);
                intent.putExtra("Date", Date);
                intent.putExtra("RoutineCount", 0);
                intent.putExtra("index", 0);
                intent.putExtra("RoutineNameText", routineList.get(position).getRoutineName());
                context.startActivity(intent);
            }
        });
        return v;

    }
}