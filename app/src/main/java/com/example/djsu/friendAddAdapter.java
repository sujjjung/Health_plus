package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.IOException;
import java.net.URL;
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
        String userState = memberItem.getState();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(memberItem);
            }

            private void showDialog(member memberItem) {
                // 커스텀 다이얼로그 보이기
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.activity_dialog_friend_user, null, false);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

                // 다이얼로그에 데이터 설정
                TextView name = view.findViewById(R.id.userName);
                name.setText(memberItem.getName());
                TextView state = view.findViewById(R.id.state);
                state.setText(memberItem.getState());
                // 이미지 나오게 했음 -> 근데 사이즈 다 깨짐 ㅅㅂ ;;;
                ImageView profile = view.findViewById(R.id.profile);
                Glide.with(context)
                        .load(memberItem.getProfile())
                        .into(profile);

                // 다이얼로그 버튼 이벤트 처리
                Button addButton = view.findViewById(R.id.conformBtn);
                Button backButton = view.findViewById(R.id.backBtn);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        // 확인 버튼을 눌렀을 때 처리할 코드
                        databaseReference.child("User").child(userID).child("friend").child(userItem).child("id").setValue(userItem);
                        databaseReference.child("User").child(userID).child("friend").child(userItem).child("name").setValue(userName);
                        databaseReference.child("User").child(userID).child("friend").child(userItem).child("profile").setValue(userPro);
                        databaseReference.child("User").child(userID).child("friend").child(userItem).child("state").setValue(userState);
//                        Toast.makeText(context, "Button clicked for " + memberItem.getName(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Intent intent = new Intent(v.getContext(), friends_list.class);
                        context.startActivity(intent);
                    }
                });

                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
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