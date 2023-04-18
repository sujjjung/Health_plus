package com.example.djsu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class friend_invite_Adapter extends ArrayAdapter<member> {
    private Context context;
    private List<member> postList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private DatabaseReference databaseReference = database.getReference();

    public friend_invite_Adapter(Context context, List<member> postList) {
        super(context, 0, postList);
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_friend_invite, parent, false);
        }
        TextView nameTextView = convertView.findViewById(R.id.name);
        ImageView imageView = convertView.findViewById(R.id.profile);

        member member = postList.get(position);
        nameTextView.setText(member.getName());
        Glide.with(context)
                .load(member.getProfile())
                .into(imageView);

        return convertView;
    }
}
