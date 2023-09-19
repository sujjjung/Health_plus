package com.example.djsu;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class communityRutineAdapter extends BaseAdapter {
    private Context context;
    private List<String> Rutinelist;
    private TextView name;
    ImageView profile;
    private static final String TAG_RESULTS = "result";
    String myJSON;
    JSONArray peoples = null;
    private ListView listView;

    public communityRutineAdapter(Context context, List<String> Rutinelist) {
        this.context = context;
        this.Rutinelist = Rutinelist;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return Rutinelist.size();
    }

    @Override
    public Object getItem(int position) {
        return Rutinelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(ArrayList<String> list) {
        Rutinelist = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_friend, null);
        name = v.findViewById(R.id.name);
        name.setText(Rutinelist.get(position));

        ImageView imageView = v.findViewById(R.id.profile);
        imageView.setVisibility(View.GONE);
        return v;
    }
}
