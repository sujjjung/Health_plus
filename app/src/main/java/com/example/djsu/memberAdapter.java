package com.example.djsu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class memberAdapter extends RecyclerView.Adapter<memberAdapter.ViewHolder> {
    Context context;

    ArrayList<member> list;

    public memberAdapter(Context context, ArrayList<member> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_friends_remove, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
        member member = list.get(position);
        holder.name.setText(member.getName());
        Glide.with(holder.itemView)
                .load(list.get(position).getProfile())
                .into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(ArrayList<member> memberList){
        list = memberList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView profile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            profile = itemView.findViewById(R.id.profile);
        }
    }
}