package com.example.djsu;

import android.app.Activity;
import android.content.Context;
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
public class FoodAdapter extends BaseAdapter {
    private Context context;
    private List<Food> foodList;
    private Activity parentActivity;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.parentActivity = parentActivity;
    }
    @Override
    public int getCount () {
        return foodList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return foodList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<Food> list) {
        foodList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){

        View v = View.inflate(context, R.layout.food_list_item, null);
       // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        TextView FoodName = (TextView) v.findViewById(R.id.FoodName);
        FoodName.setText(foodList.get(position).getFoodName());
        v.setTag(foodList.get(position).getFoodName());
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
                                foodList.remove(position);//리스트에서 해당부분을 지워줌
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
        return v;

    }
}