package com.example.djsu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class friendAdapter extends RecyclerView.Adapter<friendAdapter.ViewHolder> {
    Context context;

    ArrayList<member> list;

    public friendAdapter(Context context, ArrayList<member> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_friend_add, parent, false);
        return new friendAdapter.ViewHolder(v);
    }

//    public View getView(int position, View v, ViewGroup viewgroup) {
//        Button btn = (Button) v.findViewById(R.id.delete);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull friendAdapter.ViewHolder holder, int position) {
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
