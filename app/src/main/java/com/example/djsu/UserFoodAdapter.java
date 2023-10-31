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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class UserFoodAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private Activity parentActivity;
    int FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg,FcCode,quantity;
    TextView FoodName,Time;
    String UserId,date;
    public UserFoodAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        this.parentActivity = parentActivity;
    }
    @Override
    public int getCount () {
        return userList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return userList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<User> list) {
        userList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        View v = View.inflate(context, R.layout.item_userfood_list, null);
        Time = (TextView) v.findViewById(R.id.foodTime);
        FoodName = (TextView) v.findViewById(R.id.foodName);
        Time.setText(userList.get(position).getEatingTime());
        FoodName.setText(userList.get(position).getFoodName());
        FoodKg = Integer.parseInt(userList.get(position).getFoodKg());
        FoodKcal = Integer.parseInt(userList.get(position).getFoodKcal());
        FoodCarbohydrate = Integer.parseInt(userList.get(position).getFoodCarbohydrate());
        FoodProtein = Integer.parseInt(userList.get(position).getFoodProtein());
        FoodFat = Integer.parseInt(userList.get(position).getFoodFat());
        FoodSodium = Integer.parseInt(userList.get(position).getFoodSodium());
        FoodSugar = Integer.parseInt(userList.get(position).getFoodSugar());
        FcCode = userList.get(position).getFcCode();
        quantity =  userList.get(position).getQuantity();
        date = userList.get(position).getDate();

        v.setTag(userList.get(position).getId());
        Button DetailBtn = (Button) v.findViewById(R.id.Detail);
        DetailBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                AlertDialog ad = dlg.create();
                view = LayoutInflater.from(context).inflate(R.layout.dialog_admin_food, null, false);
                ad.setView(view);
                TextView foodName = view.findViewById(R.id.FoodName);
                TextView foodKcal = view.findViewById(R.id.FoodKcal);
                TextView foodCarbohydrate = view.findViewById(R.id.FoodCarbohydrate);
                TextView foodProtein = view.findViewById(R.id.FoodProtein);
                TextView foodFat = view.findViewById(R.id.FoodFat);
                TextView foodSodium = view.findViewById(R.id.FoodSodium);
                TextView foodSugar = view.findViewById(R.id.FoodSugar);
                TextView foodKg = view.findViewById(R.id.FoodKg);
                TextView num = view.findViewById(R.id.num);
                Button backBtn = view.findViewById(R.id.backBtn);
                foodName.setText(userList.get(position).getFoodName().toString()); //제목
                foodKcal.setText(String.valueOf(userList.get(position).getFoodKcal()));
                foodCarbohydrate.setText(String.valueOf(userList.get(position).getFoodCarbohydrate()));
                foodProtein.setText(String.valueOf(userList.get(position).getFoodProtein()));
                foodFat.setText(String.valueOf(userList.get(position).getFoodFat()));
                foodSodium.setText(String.valueOf(userList.get(position).getFoodSodium()));
                foodSugar.setText(String.valueOf(userList.get(position).getFoodSugar()));
                foodKg.setText(String.valueOf(userList.get(position).getFoodKg()));
                num.setText("수량 " + userList.get(position).getQuantity() + "개 기준");
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                    }
                });
                ad.show();
            }
        });
        Button deleteBtn = (Button) v.findViewById(R.id.Delete);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if (success) {
                                Toast.makeText(context.getApplicationContext(), "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                userList.remove(position);//리스트에서 해당부분을 지워줌
                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                UserFoodDelete deleteRequest = new UserFoodDelete(FcCode, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteRequest);

            }
        });
        Button update = (Button) v.findViewById(R.id.Update);
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, FoodUpdate.class);
                intent.putExtra("FoodName", userList.get(position).getFoodName());
                intent.putExtra("FoodKcal", userList.get(position).getFoodKcal());
                intent.putExtra("FoodCarbohydrate", userList.get(position).getFoodCarbohydrate());
                intent.putExtra("FoodProtein", userList.get(position).getFoodProtein());
                intent.putExtra("FoodFat", userList.get(position).getFoodFat());
                intent.putExtra("FoodSodium", userList.get(position).getFoodSodium());
                intent.putExtra("FoodSugar", userList.get(position).getFoodSugar());
                intent.putExtra("FoodKg", userList.get(position).getFoodKg());
                intent.putExtra("Date", date);
                intent.putExtra("FcCode", userList.get(position).getFcCode());
                intent.putExtra("quantity", userList.get(position).getQuantity());
                intent.putExtra("Time", String.valueOf(Time.getText()));
                context.startActivity(intent);
            }
        });
        return v;

    }

}