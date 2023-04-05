package com.example.djsu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class friendRemoveAdapter extends ArrayAdapter<member> {
    private Context context;
    private List<member> postList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference();

    public friendRemoveAdapter(Context context, List<member> postList) {
        super(context, 0, postList);
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend_add, parent, false);
        }
        TextView nameTextView = convertView.findViewById(R.id.name);
        ImageView imageView = convertView.findViewById(R.id.profile);
        Button delBtn = convertView.findViewById(R.id.delete);

        final member memberItem = postList.get(position);

        User user = new User();
        String userid = user.getId();

        //DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User").child(userid).child("friend").equalTo(memberItem.getName());

        //버튼 클릭 이벤트

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,  memberItem.getName() + " 님을 삭제하셨습니다.", Toast.LENGTH_SHORT).show();
                // 아이템 데이터 삭제
                postList.remove(memberItem);
                notifyDataSetChanged();
                // 해당 아이템의 리얼타임 데이터 삭제
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User").child(userid).child("friend").child(memberItem.getId());

                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("TAG", "Failed to delete value.", databaseError.toException());
                    }
                });
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