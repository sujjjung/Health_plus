package com.example.djsu.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.djsu.Food;
import com.example.djsu.FoodDelete;
import com.example.djsu.R;
import com.example.djsu.routine;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminFoodAdapter extends BaseAdapter {
    private Context context;
    private List<Food> foodList;
    private Activity parentActivity;

    public AdminFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
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

        View v = View.inflate(context, R.layout.item_admin_food, null);
        // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        TextView FoodName = (TextView) v.findViewById(R.id.FoodName);
        FoodName.setText(foodList.get(position).getFoodName());
        v.setTag(foodList.get(position).getFoodName());

        Button detailBtn = (Button) v.findViewById(R.id.detail);
        detailBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                AlertDialog ad = dlg.create();
                view = LayoutInflater.from(context).inflate(R.layout.dialog_admin_food, null, false);
                ad.setView(view);
                TextView foodName = view.findViewById(R.id.FoodName);
                TextView foodKcal = view.findViewById(R.id.FoodKcal);
                TextView foodCarbohydrate = view.findViewById(R.id.FoodCarbohydrate);
                TextView foodProtein = view.findViewById(R.id.FoodProtein);
                TextView foodFat = view.findViewById(R.id.FoodFat);
                TextView foodSodium = view.findViewById(R.id.FoodSodium);
                TextView foodSugar = view.findViewById(R.id.FoodSugar);
                TextView foodKg = view.findViewById(R.id.FoodKg);
                Button backBtn = view.findViewById(R.id.backBtn);
                foodName.setText(foodList.get(position).getFoodName().toString()); //제목
                foodKcal.setText(String.valueOf(foodList.get(position).getFoodKcal()));
                foodCarbohydrate.setText(String.valueOf(foodList.get(position).getFoodCarbohydrate()));
                foodProtein.setText(String.valueOf(foodList.get(position).getFoodProtein()));
                foodFat.setText(String.valueOf(foodList.get(position).getFoodFat()));
                foodSodium.setText(String.valueOf(foodList.get(position).getFoodSodium()));
                foodSugar.setText(String.valueOf(foodList.get(position).getFoodSugar()));
                foodKg.setText(String.valueOf(foodList.get(position).getFoodKg()));
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                    }
                });
                ad.show();
            }
        });

        Button deleteBtn = (Button) v.findViewById(R.id.delete);
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
                                Toast.makeText(context.getApplicationContext(), "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                foodList.remove(position);//리스트에서 해당부분을 지워줌
                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                FoodDelete deleteRequest = new FoodDelete(FoodName.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteRequest);

            }
        });
        return v;

    }
}