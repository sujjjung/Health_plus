package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    public exerciseAdapter(Context context, List<exerciseLsit> exList) {
        this.context = context;
        this.exList = exList;
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
        String ExExplanation,ExCalorie,ExUnit;
        View v = View.inflate(context, R.layout.item_frag, null);
        // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        TextView ExName = (TextView) v.findViewById(R.id.ExName);
        ExName.setText(exList.get(position).getExerciseName());
        ExExplanation = exList.get(position).getExerciseExplanation();
        ExCalorie = exList.get(position).getExerciseCalorie();
        ExUnit = exList.get(position).getExerciseUnit();
        v.setTag(exList.get(position).getExerciseName());
        Button selectBtn = (Button) v.findViewById(R.id.select);
        selectBtn.setOnClickListener(new View.OnClickListener(){
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
                                Toast.makeText(context.getApplicationContext(), "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                exList.remove(position);//리스트에서 해당부분을 지워줌
                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                // DeleteRequest deleteRequest = new DeleteRequest(noticeText.getText().toString(), responseListener);
                //  RequestQueue queue = Volley.newRequestQueue(parentActivity);
                // queue.add(deleteRequest);

            }
        });

        Button DetailBtn = (Button) v.findViewById(R.id.Detail);
        DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle( String.valueOf(ExName.getText()) + "설명"); //제목
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