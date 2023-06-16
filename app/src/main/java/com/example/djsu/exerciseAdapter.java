package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
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
    static private ArrayList<String> selectedPositions = new ArrayList<>();
    String Date,RoutineNameText;
    static private ArrayList<UserRoutine> selectedItems = new ArrayList<>();
    static int count = 0;
    public exerciseAdapter(Context context, List<exerciseLsit> exList,String Date,String RoutineNameText) {
        this.context = context;
        this.exList = exList;
        this.Date = Date;
        this.RoutineNameText = RoutineNameText;
     }
    public exerciseAdapter() {
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
        UserRoutine userRoutine = new UserRoutine();
        TextView ExName = (TextView) v.findViewById(R.id.ExName);
        ExCode = exList.get(position).getExCode();
        ExPart = exList.get(position).getExPart();
        ExName.setText(exList.get(position).getExerciseName());
        ExExplanation = exList.get(position).getExerciseExplanation();

        v.setTag(exList.get(position).getExerciseName());
        Button selectBtn = (Button) v.findViewById(R.id.select);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedItems.contains(userRoutine)) {
                    // Already selected item, deselect it
                    Toast.makeText(context, "이미 선택한 항목입니다.", Toast.LENGTH_SHORT).show();
                    selectedItems.remove(count - 1); // Remove at the correct index
                } else {
                    // Not selected item, select it
                    userRoutine.AddUserRoutine(RoutineNameText, ExCode, ExPart, ExName.getText().toString());
                    selectedItems.add(count, userRoutine); // Add at the correct index
                    userRoutine.setRoutineArrayList(selectedItems);
                }

                if (selectedPositions.contains(ExName.getText().toString())) {
                    selectedPositions.remove(ExName.getText().toString());
                } else {
                    selectedPositions.add(ExName.getText().toString());
                }

                count++; // Increment count after adding the item

                notifyDataSetChanged(); // Update the ListView
            }
        });

        if (selectedPositions.contains(ExName.getText().toString())) {
            // 선택된 아이템인 경우 배경 색상 변경
            selectBtn.setText("해제");
            v.setBackgroundColor(context.getResources().getColor(R.color.color_yellow));
        } else {
            // 선택되지 않은 아이템인 경우 기본 배경 색상
            selectBtn.setText("선택");
            v.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

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
    public void resetSelectedPositions() {
        selectedPositions.clear();
        selectedItems.clear();
        count = 0;
        notifyDataSetChanged();
    }
}