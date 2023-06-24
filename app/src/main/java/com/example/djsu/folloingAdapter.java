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

public class folloingAdapter extends BaseAdapter {
    private Context context;
    private List<String> followingList;
    private TextView name;
    ImageView profile;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_UserID  = "UserID";
    private static final String TAG_UserName  = "UserName";
    private static final String TAG_UserAge = "UserAge";
    private static final String TAG_UserProfile = "UserProfile";
    String myJSON;
    JSONArray peoples = null;
    private ListView listView;

    public folloingAdapter(Context context, List<String> followingList) {
        this.context = context;
        this.followingList = followingList;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    public int getCount() {
        return followingList.size();
    }

    @Override
    public Object getItem(int position) {
        return followingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(ArrayList<String> list) {
        followingList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_friend, null);
        getData("http://enejd0613.dothome.co.kr/getUser.php");
        name = v.findViewById(R.id.name);
        profile = v.findViewById(R.id.profile);
        return v;
    }

    protected void showList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for (int position = 0; position < followingList.size(); position++) {
                    String sid = followingList.get(position);
                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject c = peoples.getJSONObject(i);
                        String UserID = c.getString(TAG_UserID);
                        if (UserID.equals(sid)) {
                            String UserName = c.getString(TAG_UserName);
                            String UserProfile = c.getString(TAG_UserProfile);

                            // Update the views for the current item
                            View itemView = getViewByPosition(position);
                            if (itemView != null) {
                                TextView name = itemView.findViewById(R.id.name);
                                ImageView profile = itemView.findViewById(R.id.profile);
                                name.setText(UserName);
                                String imageUrl = UserProfile;
                                Picasso.get().load(imageUrl).into(profile);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private View getViewByPosition(int position) {
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            return listView.getChildAt(position - firstVisiblePosition);
        }
        return null;
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
