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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class communityAdapter extends BaseAdapter {
    private Context context;
    private List<User> Communitylist;
    private TextView name_textView,date,content;
    private ImageView photo, profile;
    private String Date = "";
    User user = new User();
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
        Date = getTime();
        name_textView = v.findViewById(R.id.name_textView);
        name_textView.setText(Communitylist.get(position).getPostid());

        photo = v.findViewById(R.id.photo);
        String imageUrl = Communitylist.get(position).getImage(); // Get the image URL from User object
        Picasso.get().load(imageUrl).into(photo);

        date = v.findViewById(R.id.date);
        date.setText(Communitylist.get(position).getPostdate());

        content = v.findViewById(R.id.content);
        content.setText(Communitylist.get(position).getContent());

        profile = v.findViewById(R.id.profile);
        String profileImageUrl = Communitylist.get(position).getProfileImageUrl();
        Picasso.get().load(profileImageUrl).into(profile);
        // 추가된 부분 끝

        chat_bubble_btn =  v.findViewById(R.id.chat_bubble_btn);
        chat_bubble_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, comment.class);
                intent.putExtra("postId",Communitylist.get(position).getPostKey());
                context.startActivity(intent);
            }
        });

        siren_btn =  v.findViewById(R.id.siren_btn);
        if(user.getId().equals(Communitylist.get(position).getPostid())){
            siren_btn.setVisibility(View.GONE);
        }
        siren_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = LayoutInflater.from(context).inflate(R.layout.dialog_declaration, null, false);
                builder.setView(view);
                final Button SaveButton = (Button) view.findViewById(R.id.saveBtn);
                final Button BackButton = (Button) view.findViewById(R.id.backBtn);
                final EditText declarationText = (EditText) view.findViewById(R.id.declaration);
                final AlertDialog dialog = builder.create();
                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        declarationRequest declarationRequest = new declarationRequest(user.getId(),String.valueOf(Communitylist.get(position).getPostKey()),
                                Communitylist.get(position).getPostid(),declarationText.getText().toString(),Date,"게시글");
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(declarationRequest);
                        Toast.makeText(context, "신고되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                BackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return v;
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);

        return getTime;
    }
}