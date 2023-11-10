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
    private List<UserRoutine> Rutinelist;
    private TextView name;
    ImageView profile;
    private static final String TAG_RESULTS = "result";
    String myJSON;
    JSONArray peoples = null;
    private ListView listView;

    public communityRutineAdapter(Context context, List<UserRoutine> Rutinelist) {
        this.context = context;
        this.Rutinelist = Rutinelist;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 1;
    }

    public void setItems(ArrayList<UserRoutine> list) {
        Rutinelist = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_communityrutine, null);
        name = v.findViewById(R.id.rutineName);
        name.setText(Rutinelist.get(position).getRoutineName());
        System.out.println(Rutinelist.size());
        System.out.println(Rutinelist.get(position).getRoutineName());
        return v;
    }
}
