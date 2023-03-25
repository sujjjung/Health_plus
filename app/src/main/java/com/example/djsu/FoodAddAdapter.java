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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class FoodAddAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private Activity parentActivity;


    public FoodAddAdapter(Context context, List<User> userList) {
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
        String UserId,date;
        int FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg,FcCode;
        View v = View.inflate(context, R.layout.item_food_add, null);
        TextView Time = (TextView) v.findViewById(R.id.foodTime);
        TextView FoodName = (TextView) v.findViewById(R.id.FoodName);
        Time.setText(userList.get(position).getEatingTime());
        FoodName.setText(userList.get(position).getFoodName());
        FoodKg = Integer.parseInt(userList.get(position).getFoodKg());
        FoodKcal = Integer.parseInt(userList.get(position).getFoodKcal());
        FoodCarbohydrate = Integer.parseInt(userList.get(position).getFoodCarbohydrate());
        FoodProtein = Integer.parseInt(userList.get(position).getFoodProtein());
        FoodFat = Integer.parseInt(userList.get(position).getFoodFat());
        FoodSodium = Integer.parseInt(userList.get(position).getFoodSodium());
        FoodSugar = Integer.parseInt(userList.get(position).getFoodSugar());
        FcCode = userList.get(position).getFcCode();
        date = userList.get(position).getDate();
        //UserId = userList.get(position).getId();

        v.setTag(userList.get(position).getId());

        return v;

    }
}