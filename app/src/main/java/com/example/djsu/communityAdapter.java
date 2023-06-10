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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class communityAdapter extends BaseAdapter {
    private Context context;
    private List<User> Communitylist;
    private TextView name_textView,date,content;
    private ImageView photo;
    private ImageButton heart_btn,chat_bubble_btn,siren_btn;
    public communityAdapter(Context context, List<User> Communitylist) {
        this.context = context;
        this.Communitylist = Communitylist;
    }
    @Override
    public int getCount () {
        return Communitylist.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return Communitylist.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<User> list) {
        Communitylist = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        View v = View.inflate(context, R.layout.item_community, null);
        System.out.println("Dffds " + Communitylist.size());
        name_textView = v.findViewById(R.id.name_textView);
        name_textView.setText(Communitylist.get(position).getPostid());

        photo = v.findViewById(R.id.photo);
        String imageUrl = Communitylist.get(position).getImage(); // Get the image URL from User object
        Picasso.get().load(imageUrl).into(photo);

        date = v.findViewById(R.id.date);
        date.setText(Communitylist.get(position).getPostdate());

        content = v.findViewById(R.id.content);
        content.setText(Communitylist.get(position).getContent());

        chat_bubble_btn =  v.findViewById(R.id.chat_bubble_btn);
        chat_bubble_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, comment.class);
                context.startActivity(intent);
            }
        });
        return v;
    }
}