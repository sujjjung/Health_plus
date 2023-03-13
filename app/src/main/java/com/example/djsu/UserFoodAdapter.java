package com.example.djsu;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class UserFoodAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private Activity parentActivity;

    public UserFoodAdapter(Context context, List<User> userList) {
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
        String UserId;
        View v = View.inflate(context, R.layout.item_userfood_list, null);
        // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        TextView Time = (TextView) v.findViewById(R.id.foodTime);
        TextView FoodName = (TextView) v.findViewById(R.id.foodName);
        Time.setText(userList.get(position).getEatingTime());
        FoodName.setText(userList.get(position).getFoodName());
        //UserId = userList.get(position).getId();
        v.setTag(userList.get(position).getId());
        return v;

    }
}