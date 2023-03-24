package com.example.djsu;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class friendAddAdapter extends ArrayAdapter<member> {
    private Context context;
    private List<member> postList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference();


    public friendAddAdapter(Context context, List<member> postList) {
        super(context, 0, postList);
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friends_remove, parent, false);
        }
        TextView nameTextView = convertView.findViewById(R.id.name);
        ImageView imageView = convertView.findViewById(R.id.profile);
        Button addBtn = convertView.findViewById(R.id.shape_yes_btn);

        final member memberItem = postList.get(position);

        User user = new User();
        String userID = user.getId();

        String userItem = memberItem.getId();
        String userName = memberItem.getName();
        String userPro = memberItem.getProfile();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context context = v.getContext();
                // 버튼 클릭 이벤트 처리
                databaseReference.child("User").child(userID).child("friend").child(userItem).child("id").setValue(userItem);
                databaseReference.child("User").child(userID).child("friend").child(userItem).child("name").setValue(userName);
                databaseReference.child("User").child(userID).child("friend").child(userItem).child("profile").setValue(userPro);
                Toast.makeText(context, "Button clicked for " + memberItem.getName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), friends_remove.class);
//                context.startActivity(intent);
            }
        });

        member member = postList.get(position);
        nameTextView.setText(member.getName());
        Glide.with(context)
                .load(member.getProfile())
                .into(imageView);

        return convertView;
    }
}