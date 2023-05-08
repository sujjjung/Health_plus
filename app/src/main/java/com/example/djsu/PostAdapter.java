package com.example.djsu;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends BaseAdapter {
    private Context context;
    private List<Post> PostList;
    private Activity parentActivity;

    public PostAdapter(Context context, List<Post> PostList) {
        this.context = context;
        this.PostList = PostList;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return PostList.size();
    }

    @Override
    public Object getItem(int position) {
        return PostList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(ArrayList<Post> list) {
        PostList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_community, null);

        TextView content = (TextView) v.findViewById(R.id.content);
        TextView id = (TextView) v.findViewById(R.id.name_textView);
        TextView image = (TextView) v.findViewById(R.id.photo);
        TextView date = (TextView) v.findViewById(R.id.date);

        content.setText(PostList.get(position).getContent());
        id.setText(PostList.get(position).getId());
        image.setText(PostList.get(position).getImage());
        date.setText(PostList.get(position).getDate());
        v.setTag(PostList.get(position).getDate());

        return v;
    }
}
