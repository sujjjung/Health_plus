package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class communityAdapter extends BaseAdapter {
    private Context context;
    private List<User> Communitylist;
    UserRoutine userRoutine;
    private List<UserRoutine> UserRoutinelist = new ArrayList<>();
    private List<String> Rutinelist = new ArrayList<>();
    private List<String> MyRutinelist = new ArrayList<>();

    private static final String TAG_RESULTS = "result";
    private static final String TAG_UserId  = "userId";
    private static final String TAG_ExName = "ExerciseName";
    private static final String TAG_RoutineName = "RoutineName";
    private static final String TAG_ExPart = "ExercisePart";
    private static final String TAG_ExerciseCode = "ExerciseCode";
    String myJSON;
    JSONArray peoples = null;
    private communityRutineAdapter communityRutineAdapter;
    private TextView name_textView,date,content;
    private ImageView photo, profile;
    private String Date = "";

    int a;
    User user = new User();
    private ImageButton heart_btn,chat_bubble_btn,siren_btn,rutine_btn;
    public communityAdapter(Context context, List<User> Communitylist) {
        this.context = context;
        this.Communitylist = Communitylist;
    }
    @Override
    public int getCount () {
        return Communitylist.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return Communitylist.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<User> list) {
        Communitylist = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        View v = View.inflate(context, R.layout.item_community, null);
        Date = getTime();
        name_textView = v.findViewById(R.id.name_textView);
        name_textView.setText(Communitylist.get(position).getPostid());

        photo = v.findViewById(R.id.photo);
        String imageUrl = Communitylist.get(position).getImage(); // Get the image URL from User object
        Picasso.get().load(imageUrl).into(photo);

        date = v.findViewById(R.id.date);
        date.setText(Communitylist.get(position).getPostdate());

        content = v.findViewById(R.id.content);
        content.setText(Communitylist.get(position).getContent());

        profile = v.findViewById(R.id.profile);
        String profileImageUrl = Communitylist.get(position).getProfileImageUrl();
        Picasso.get().load(profileImageUrl).into(profile);
        // 추가된 부분 끝
        rutine_btn = v.findViewById(R.id.rutine_btn);

        if(Communitylist.get(position).getRoutineName().equals("")){
            rutine_btn.setVisibility(View.GONE);
        }
        rutine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData("http://enejd0613.dothome.co.kr/Routinelist.php");
                a = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = LayoutInflater.from(context).inflate(R.layout.dialog_comunityrutine, null, false);
                builder.setView(view);

                final Button conformBtn = (Button) view.findViewById(R.id.conformBtn);
                final Button backBtn = (Button) view.findViewById(R.id.backBtn);
                final Button okBtn = (Button) view.findViewById(R.id.okBtn);
                final ListView rutineView = (ListView) view.findViewById(R.id.ListView);
                final AlertDialog dialog = builder.create();
                communityRutineAdapter = new communityRutineAdapter(context,Rutinelist);
                communityRutineAdapter.setListView(rutineView);
                rutineView.setAdapter(communityRutineAdapter);
                if(user.getId().equals(Communitylist.get(position).getPostid())){
                    backBtn.setVisibility(View.GONE);
                    conformBtn.setVisibility(View.GONE);
                    okBtn.setVisibility(View.VISIBLE);
                }

                Rutinelist.clear();
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                conformBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        view = LayoutInflater.from(context).inflate(R.layout.dialog_routine_name, null, false);
                        builder.setView(view);
                        final Button SaveButton = (Button) view.findViewById(R.id.saveBtn);
                        final Button BackButton = (Button) view.findViewById(R.id.backBtn);
                        final EditText RoutineNameText = (EditText) view.findViewById(R.id.RoutineName);
                        final AlertDialog dialog = builder.create();
                        RoutineNameText.setText(Communitylist.get(position).getRoutineName());
                        SaveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for (int i = 0; i < MyRutinelist.size(); i++) {
                                    if (MyRutinelist.get(i).equals(RoutineNameText.getText().toString())) {
                                        Toast.makeText(context, "동일한 이름의 루틴이 있습니다!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        return;
                                    }
                                }
                                    User user = new User();
                                    for (int i = 0; i < UserRoutinelist.size(); i++){
                                        RoutineRequest routineRequest = new RoutineRequest(user.getId(),RoutineNameText.getText().toString(),
                                                UserRoutinelist.get(i).getExCode(),UserRoutinelist.get(i).getExerciseName(),UserRoutinelist.get(i).getExPart());
                                        RequestQueue queue = Volley.newRequestQueue(context);
                                        queue.add(routineRequest);
                                    }
                                    Toast.makeText(context, "등록 완료되었습니다!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, routine.class);
                                    intent.putExtra("Date", Date);
                                    context.startActivity(intent);

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
                dialog.show();

            }
        });

        chat_bubble_btn =  v.findViewById(R.id.chat_bubble_btn);
        chat_bubble_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, comment.class);
                intent.putExtra("postId",Communitylist.get(position).getPostKey());
                context.startActivity(intent);
            }
        });

        siren_btn =  v.findViewById(R.id.siren_btn);
        if(user.getId().equals(Communitylist.get(position).getPostid())){
            siren_btn.setVisibility(View.GONE);
        }
        siren_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = LayoutInflater.from(context).inflate(R.layout.dialog_declaration, null, false);
                builder.setView(view);
                final Button SaveButton = (Button) view.findViewById(R.id.saveBtn);
                final Button BackButton = (Button) view.findViewById(R.id.backBtn);
                final EditText declarationText = (EditText) view.findViewById(R.id.declaration);
                final AlertDialog dialog = builder.create();
                SaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        declarationRequest declarationRequest = new declarationRequest(user.getId(),String.valueOf(Communitylist.get(position).getPostKey()),
                                Communitylist.get(position).getPostid(),declarationText.getText().toString(),Date,"게시글");
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(declarationRequest);
                        Toast.makeText(context, "신고되었습니다.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
        return v;
    }

    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = dateFormat.format(date);

        return getTime;
    }
    protected void showList() {

        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);
                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String UserId = c.getString(TAG_UserId);
                    String RoutineName = c.getString(TAG_RoutineName);
                   if(Communitylist.get(a).getRoutineName().equals(RoutineName)){
                        String ExercisePart = c.getString(TAG_ExPart);
                        String ExerciseName = c.getString(TAG_ExName);
                        String ExerciseCode = c.getString(TAG_ExerciseCode);
                        userRoutine = new UserRoutine(RoutineName,ExerciseCode,ExercisePart,ExerciseName);
                        UserRoutinelist.add(userRoutine);
                        Rutinelist.add(ExerciseName);
                    }

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
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