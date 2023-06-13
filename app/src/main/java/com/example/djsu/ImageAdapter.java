package com.example.djsu;

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

import com.example.djsu.admin.adminDeclarationAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imageUrls;
    // 클릭시 해당 게시물 보기
    private static final String TAG_RESULTS = "result";
    private static final String TAG_CONTENT = "content";
    private static final String TAG_IMAGE = "image";
    private static final String TAG_DATE = "date";
    private static final String TAG_postId = "postId";
    private static final String TAG_id = "id";
    JSONArray peoples = null;
    String myJSON,content, date, photo;
    private int count;
    public ImageAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(250, 250)); // 이미지 뷰의 크기를 조정합니다.
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = LayoutInflater.from(context).inflate(R.layout.dialog_commnity, null, false);
                count = position;
                builder.setView(view);
                final Button conformBtn = (Button) view.findViewById(R.id.conformBtn);
                final TextView contentText = (TextView) view.findViewById(R.id.content);
                final TextView dateText = (TextView) view.findViewById(R.id.date);
                final ImageView photoText = (ImageView) view.findViewById(R.id.photo);

                getData("http://enejd0613.dothome.co.kr/filedownload.php", new adminDeclarationAdapter.DataCallback() {
                    @Override
                    public void onDataFetched(String content, String date, String photo) {
                        contentText.setText(content);
                        dateText.setText("게시 날짜:" +date);
                        String imageUrl = photo;
                        Picasso.get().load(imageUrl).into(photoText);
                    }
                });

                final AlertDialog dialog = builder.create();
                conformBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        // 이미지 로딩을 위해 Picasso 라이브러리를 사용합니다. 필요하다면 해당 라이브러리를 추가해야 합니다.
        Picasso.get()
                .load(imageUrls.get(position))
                .placeholder(R.drawable.loading) // 이미지가 로드되기 전에 보여줄 임시 이미지
                .error(R.drawable.error) // 이미지 로드 실패 시 보여줄 이미지
                .into(imageView);

        return imageView;
    }
    protected void showList() {
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String imageText = c.getString(TAG_IMAGE);
                    if(imageUrls.get(count).equals(imageText)) {
                        String dateText = c.getString(TAG_DATE);
                        String contentText = c.getString(TAG_CONTENT);
                        content = contentText;
                        date = dateText;
                        photo = imageText;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url, adminDeclarationAdapter.DataCallback callback) {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            private adminDeclarationAdapter.DataCallback callback;

            public GetDataJSON(adminDeclarationAdapter.DataCallback callback) {
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
}
