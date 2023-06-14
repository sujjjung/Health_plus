package com.example.djsu.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.djsu.R;
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

public class adminDeclarationAdapter extends BaseAdapter {
    private Context context;
    private List<AdminManagement> DeclarationArrayList;
    private TextView ReporterName, Date;
    private Button detailBtn, deleteBtn;
    String myJSON, content, date, photo,type = "",userId;
    // 커뮤니티
    private static final String TAG_RESULTS = "result";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_DATE = "date";
    private static final String TAG_postId = "postId";
    private static final String TAG_type = "type";
    //댓글
    private static final String TAG_commentContent = "content";
    private static final String TAG_CREATED_AT = "created_at";
    private static final String TAG_USERID = "userId";
    private static final String TAG_id = "id";
    JSONArray peoples = null;
    private int count;

    public adminDeclarationAdapter(Context context, List<AdminManagement> DeclarationArrayList) {
        this.context = context;
        this.DeclarationArrayList = DeclarationArrayList;
    }

    @Override
    public int getCount() {
        return DeclarationArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return DeclarationArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(ArrayList<AdminManagement> list) {
        DeclarationArrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_admin_declaration, null);
        ReporterName = v.findViewById(R.id.ReporterName);
        ReporterName.setText(DeclarationArrayList.get(position).getUserId());

        Date = v.findViewById(R.id.Date);
        Date.setText(DeclarationArrayList.get(position).getDate());

        detailBtn = v.findViewById(R.id.detail);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = LayoutInflater.from(context).inflate(R.layout.dialog_admin_dec, null, false);
                count = position;
                builder.setView(view);
                final Button conformBtn = (Button) view.findViewById(R.id.conformBtn);
                final Button BackButton = (Button) view.findViewById(R.id.backBtn);
                final TextView matchUser = (TextView) view.findViewById(R.id.matchUser);
                final TextView reason = (TextView) view.findViewById(R.id.reason);
                final TextView contentText = (TextView) view.findViewById(R.id.content);
                final TextView dateText = (TextView) view.findViewById(R.id.date);
                final ImageView photoText = (ImageView) view.findViewById(R.id.photo);
                getCommentData("http://enejd0613.dothome.co.kr/get_comments.php");
                getData("http://enejd0613.dothome.co.kr/filedownload.php", new DataCallback() {
                    @Override
                    public void onDataFetched(String content, String date, String photo) {
                        if(type.equals("게시글")) {
                            contentText.setText("해당 글:" +content);
                            dateText.setText("게시 날짜:" +date);
                            String imageUrl = photo;
                            Picasso.get().load(imageUrl).into(photoText);
                        } else if(type.equals("댓글")) {
                            contentText.setText("해당 댓글:" +content);
                            dateText.setText("게시 날짜:" +date);
                            photoText.setVisibility(View.GONE);
                        }
                    }
                });

                matchUser.setText("신고당한유저: " + DeclarationArrayList.get(position).getPostname());
                reason.setText("신고사유: " + DeclarationArrayList.get(position).getCause());

                final AlertDialog dialog = builder.create();
                conformBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                                    if (success) {
                                        Toast.makeText(context.getApplicationContext(), "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                        DeclarationArrayList.remove(position);//리스트에서 해당부분을 지워줌
                                        notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        adminUserDelete deleteRequest = new adminUserDelete(DeclarationArrayList.get(position).getPostname(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(deleteRequest);
                    }
                });

                BackButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        deleteBtn = v.findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if (success) {
                                Toast.makeText(context.getApplicationContext(), "반려 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                DeclarationArrayList.remove(position);//리스트에서 해당부분을 지워줌
                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                adminDeclarationDelete deleteRequest = new adminDeclarationDelete(String.valueOf(DeclarationArrayList.get(position).getDeclarationid()), responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteRequest);

            }
        });

        return v;
    }

    protected void showList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String postId = c.getString(TAG_postId);
                    if (Integer.parseInt(postId) == DeclarationArrayList.get(count).getPostid()&& DeclarationArrayList.get(count).getType().equals("게시글")) {
                        String imageText = c.getString(TAG_IMAGE);
                        String dateText = c.getString(TAG_DATE);
                        String contentText = c.getString(TAG_CONTENT);
                        content = contentText;
                        date = dateText;
                        photo = imageText;
                        type = DeclarationArrayList.get(count).getType();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url, DataCallback callback) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            private DataCallback callback;

            public GetDataJSON(DataCallback callback) {
                this.callback = callback;
            }

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
                        sb.append(json + "\n");
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

                if (callback != null) {
                    callback.onDataFetched(content, date, photo);
                }
            }
        }

        GetDataJSON g = new GetDataJSON(callback);
        g.execute(url);
    }

    protected void showCommentList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);
            for (int i = 0; i < peoples.length(); i++) {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_id);

                if (Integer.parseInt(id) == DeclarationArrayList.get(count).getPostid() && DeclarationArrayList.get(count).getType().equals("댓글")) {
                    String created_at = c.getString(TAG_CREATED_AT);
                    String contentText = c.getString(TAG_commentContent);
                    String userIdText = c.getString(TAG_USERID);
                    type = DeclarationArrayList.get(count).getType();
                    content = contentText;
                    date = created_at;
                    userId = userIdText;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getCommentData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];

                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }

                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showCommentList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    public interface DataCallback {
        void onDataFetched(String content, String date, String photo);
    }
}