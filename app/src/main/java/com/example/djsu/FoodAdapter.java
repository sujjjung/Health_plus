package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class FoodAdapter extends BaseAdapter {
    private Context context;
    private List<Food> foodList;
    private Activity parentActivity;
    private String Date;
    float FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
    int FoodCood;
    TextView FoodName;
    public FoodAdapter(Context context, List<Food> foodList,String Date) {
        this.context = context;
        this.foodList = foodList;
        this.Date = Date;
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
        View v = View.inflate(context, R.layout.item_food_list, null);
        // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        FoodName = (TextView) v.findViewById(R.id.FoodName);
        FoodName.setText(foodList.get(position).getFoodName());
        FoodCood = foodList.get(position).getFoodCood();
        FoodKg = foodList.get(position).getFoodKg();
        FoodKcal = foodList.get(position).getFoodKcal();
        FoodCarbohydrate = foodList.get(position).getFoodCarbohydrate();
        FoodProtein = foodList.get(position).getFoodProtein();
        FoodFat = foodList.get(position).getFoodFat();
        FoodSodium = foodList.get(position).getFoodSodium();
        FoodSugar = foodList.get(position).getFoodSugar();

        v.setTag(foodList.get(position).getFoodName());
        Button selectBtn = (Button) v.findViewById(R.id.select);
        selectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, FoodAddActivity.class);
                intent.putExtra("FoodName", foodList.get(position).getFoodName());
                intent.putExtra("FoodKcal", String.valueOf(foodList.get(position).getFoodKcal()));
                intent.putExtra("FoodCarbohydrate", String.valueOf(foodList.get(position).getFoodCarbohydrate()));
                intent.putExtra("FoodProtein", String.valueOf(foodList.get(position).getFoodProtein()));
                intent.putExtra("FoodFat", String.valueOf(foodList.get(position).getFoodFat()));
                intent.putExtra("FoodSodium", String.valueOf(foodList.get(position).getFoodSodium()));
                intent.putExtra("FoodSugar", String.valueOf(foodList.get(position).getFoodSugar()));
                intent.putExtra("FoodKg", String.valueOf(foodList.get(position).getFoodKg()));
                intent.putExtra("FoodCood", foodList.get(position).getFoodCood());
                intent.putExtra("Date", Date);
                intent.putExtra("set", 1);
                context.startActivity(intent);
            };
        });
        Button DetailBtn = (Button) v.findViewById(R.id.Detail);
        DetailBtn.setOnClickListener(new View.OnClickListener(){
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

        return v;

    }
}