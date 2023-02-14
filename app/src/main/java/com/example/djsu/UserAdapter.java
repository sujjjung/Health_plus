package com.example.djsu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<User> userArrayList;


    // creating constructor for our adapter class
    public UserAdapter(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;

    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends_remove,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
//        User user = userArrayList.get(position);
//        holder.userprofile.se(user.getProfile());
        Glide.with(holder.itemView)
                .load(userArrayList.get(position).getProfile())
                .into(holder.userProfile);
        holder.userName.setText(userArrayList.get(position).getName());
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView profile;
//        TextView name;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.profile = itemView.findViewById(R.id.profile);
//            this.name = itemView.findViewById(R.id.name);
//        }
//    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return userArrayList.size();
    }
    public void setItems(ArrayList<User> list){
        userArrayList = list;
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
//        private final ImageView userprofile;
        TextView userName;
        ImageView userProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
//            userprofile = itemView.findViewById(R.id.profile);
            userName = itemView.findViewById(R.id.name);
            userProfile = itemView.findViewById(R.id.profile);
        }
    }



}
