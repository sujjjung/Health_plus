package com.example.djsu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class friendlistAdapter extends ArrayAdapter<member> {
    private Context context;
    private List<member> postList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference();

    public friendlistAdapter(Context context, List<member> postList) {
        super(context, 0, postList);
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);
        }
        TextView nameTextView = convertView.findViewById(R.id.name);
        ImageView imageView = convertView.findViewById(R.id.profile);
        Button checkBtn = convertView.findViewById(R.id.delete);

        final member memberItem = postList.get(position);

//        checkBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Button clicked for " + memberItem.getName(), Toast.LENGTH_SHORT).show();
//            }
//        });

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(memberItem);
            }

            private void showDialog(member memberItem) {
                // 커스텀 다이얼로그 보이기
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.activity_dialog_friend_list, null, false);
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
                Button backButton = view.findViewById(R.id.backBtn);
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
