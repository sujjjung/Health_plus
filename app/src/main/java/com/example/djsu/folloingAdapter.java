package com.example.djsu;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class folloingAdapter extends BaseAdapter {
    private Context context;
    private List<String> followingList;
    private TextView name;
    public folloingAdapter(Context context, List<String> followingList) {
        this.context = context;
        this.followingList = followingList;
    }
    @Override
    public int getCount () {
        return followingList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return followingList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<String> list) {
        followingList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        View v = View.inflate(context, R.layout.item_friend, null);
        name = v.findViewById(R.id.name);
        name.setText(followingList.get(position));

        return v;
    }

}

