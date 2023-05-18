package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class exerciseAdapter extends BaseAdapter {
    private Context context;
    private List<exerciseLsit> exList;
    private Activity parentActivity;
    String Date;
    public exerciseAdapter(Context context, List<exerciseLsit> exList,String Date) {
        this.context = context;
        this.exList = exList;
        this.Date = Date;
        this.parentActivity = parentActivity;
    }
    @Override
    public int getCount () {
        return exList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return exList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<exerciseLsit> list) {
        exList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        String ExCode,ExPart,ExExplanation,ExCalorie,ExUnit;
        View v = View.inflate(context, R.layout.item_frag, null);
        // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        TextView ExName = (TextView) v.findViewById(R.id.ExName);
        ExCode = exList.get(position).getExCode();
        ExPart = exList.get(position).getExPart();
        ExName.setText(exList.get(position).getExerciseName());
        ExExplanation = exList.get(position).getExerciseExplanation();
        ExCalorie = exList.get(position).getExerciseCalorie();
        ExUnit = exList.get(position).getExerciseUnit();
        v.setTag(exList.get(position).getExerciseName());
        Button selectBtn = (Button) v.findViewById(R.id.select);
        selectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ExerciseRecordActivity.class);
                intent.putExtra("exName", ExName.getText().toString());
                intent.putExtra("Date",Date);
                intent.putExtra("ExCode",ExCode);
                intent.putExtra("ExPart",ExPart);
                intent.putExtra("ExCalorie",ExCalorie);
                intent.putExtra("ExUnit",ExUnit);
                view.getContext().startActivity(intent);
            };
        });

        Button DetailBtn = (Button) v.findViewById(R.id.Detail);
        DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle( ExName.getText() + "설명"); //제목
                dlg.setMessage("설명:"+ExExplanation);
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(context,"확인을 눌르셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }

        });

        return v;

    }
}