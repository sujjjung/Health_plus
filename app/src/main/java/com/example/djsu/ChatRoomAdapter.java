package com.example.djsu;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kotlin.jvm.Volatile;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MyViewHolder> {
    private final RecyclerView mRecyclerView;
    private List<ChatData> mDataSet;
    private String myNickName;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView TextView_msg;
        public TextView TextView_nickname;

        public TextView TextView_date;

        public LinearLayout msgLinear, test2;

        public View rootView;
        public MyViewHolder(View v) {
            super(v);
            TextView_nickname = v.findViewById(R.id.TextView_nickname);
            TextView_msg = v.findViewById(R.id.TextView_msg);
            TextView_date = v.findViewById(R.id.TextView_date);
            msgLinear = itemView.findViewById(R.id.itemId);
            test2 = itemView.findViewById(R.id.itemId2);
            rootView = v;
        }
    }

    public ChatRoomAdapter(List<ChatData> myDataset, Context context, String myNickName, RecyclerView recyclerView) {
        mDataSet = myDataset;
        this.myNickName = myNickName;
        this.mRecyclerView = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatRoomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_talk_mine, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatData chat = mDataSet.get(position);


        holder.TextView_nickname.setText(chat.getUserName());
        holder.TextView_msg.setText(chat.getMsg());
        holder.TextView_date.setText(chat.getDate());

        if(chat.getUserName().equals(this.myNickName)) {
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.TextView_date.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.msgLinear.setGravity(Gravity.RIGHT);
            holder.test2.setGravity(Gravity.RIGHT);
        }
        else {
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.TextView_date.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            holder.msgLinear.setGravity(Gravity.LEFT);
            holder.test2.setGravity(Gravity.LEFT);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public ChatData getChat(int position) {
        return mDataSet != null ? mDataSet.get(position) : null;
    }

    public void addChat(ChatData chat) {
        mDataSet.add(chat);
        notifyItemInserted(mDataSet.size()-1);
        mRecyclerView.smoothScrollToPosition(mDataSet.size() - 1);
    }

}
