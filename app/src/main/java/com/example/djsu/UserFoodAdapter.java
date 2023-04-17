package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
        //UserId = userList.get(position).getId();

        v.setTag(userList.get(position).getId());
        Button DetailBtn = (Button) v.findViewById(R.id.Detail);
        DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle( userList.get(position).getFoodName() + "상세성분"); //제목
                dlg.setMessage("수량"+userList.get(position).getQuantity() + "기준" + "\n칼로리:"+userList.get(position).getFoodKcal()+"\n탄수화물:"+userList.get(position).getFoodCarbohydrate()+"\n단백질:"+userList.get(position).getFoodProtein()+"\n지방:"+userList.get(position).getFoodFat()
                        +"\n나트륨:"+userList.get(position).getFoodSodium()+"\n당:"+userList.get(position).getFoodSugar()+"\n무게:"+userList.get(position).getFoodKg());
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(context,"확인을 눌르셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
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
                FoodaddListBackgroundTask foodaddListBackgroundTask = new FoodaddListBackgroundTask(context);
                foodaddListBackgroundTask.execute();
            }
        });
        return v;

    }
    class FoodaddListBackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        Context context;

        FoodaddListBackgroundTask(Context context){
            this.context = context;
        }
        protected void onPreExecute() {
            //List.php은 파싱으로 가져올 웹페이지
            target = "http://enejd0613.dothome.co.kr/foodcalendarlist.php";
        }

        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(target);//URL 객체 생성
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
                }

                //사용했던 것도 다 닫아줌
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(String result) {
            Intent intent = new Intent(context, FoodUpdate.class);
            intent.putExtra("FoodName", String.valueOf(FoodName.getText()));
            intent.putExtra("FoodKcal", String.valueOf(FoodKcal));
            intent.putExtra("FoodCarbohydrate", String.valueOf(FoodCarbohydrate));
            intent.putExtra("FoodProtein", String.valueOf(FoodProtein));
            intent.putExtra("FoodFat", String.valueOf(FoodFat));
            intent.putExtra("FoodSodium", String.valueOf(FoodSodium));
            intent.putExtra("FoodSugar", String.valueOf(FoodSugar));
            intent.putExtra("FoodKg", String.valueOf(FoodKg));
            intent.putExtra("Date", date);
            intent.putExtra("FcCode", FcCode);
            intent.putExtra("quantity", quantity);
            intent.putExtra("Time", String.valueOf(Time.getText()));
            intent.putExtra("UserFood", result);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }
}