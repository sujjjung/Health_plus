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
import com.example.djsu.Food;
import com.example.djsu.Notice;
import com.example.djsu.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends BaseAdapter {
    private Context context;
    private List<Notice> NoticeList;
    private Activity parentActivity;

    public NoticeAdapter(Context context, List<Notice> NoticeList) {
        this.context = context;
        this.NoticeList = NoticeList;
        this.parentActivity = parentActivity;
    }
    @Override
    public int getCount () {
        return NoticeList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return NoticeList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<Notice> list) {
        NoticeList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){

        View v = View.inflate(context, R.layout.item_annoucement, null);
        // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        TextView emote = (TextView) v.findViewById(R.id.emote);
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView title = (TextView) v.findViewById(R.id.title);
        emote.setText(NoticeList.get(position).getEmote());
        date.setText(NoticeList.get(position).getDate());
        title.setText(NoticeList.get(position).getTitle());
        //String content = NoticeList.get(position).getContent();
        v.setTag(NoticeList.get(position).getDate());


        return v;

    }
}