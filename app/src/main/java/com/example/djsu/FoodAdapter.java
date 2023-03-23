package com.example.djsu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class FoodAdapter extends BaseAdapter {
    private Context context;
    private List<Food> foodList;
    private Activity parentActivity;
    private String Date;
    public FoodAdapter(Context context, List<Food> foodList,String Date) {
        this.context = context;
        this.foodList = foodList;
        this.Date = Date;
        this.parentActivity = parentActivity;
    }
    @Override
    public int getCount () {
        return foodList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return foodList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<Food> list) {
        foodList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        int FoodKcal,FoodCarbohydrate,FoodProtein,FoodFat,FoodSodium,FoodSugar,FoodKg;
        int FoodCood;
        View v = View.inflate(context, R.layout.item_food_list, null);
       // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        TextView FoodName = (TextView) v.findViewById(R.id.FoodName);
        FoodName.setText(foodList.get(position).getFoodName());
        FoodCood = foodList.get(position).getFoodCood();
        FoodKg = Integer.parseInt(foodList.get(position).getFoodKg());
        FoodKcal = Integer.parseInt(foodList.get(position).getFoodKcal());
        FoodCarbohydrate = Integer.parseInt(foodList.get(position).getFoodCarbohydrate());
        FoodProtein = Integer.parseInt(foodList.get(position).getFoodProtein());
        FoodFat = Integer.parseInt(foodList.get(position).getFoodFat());
        FoodSodium = Integer.parseInt(foodList.get(position).getFoodSodium());
        FoodSugar = Integer.parseInt(foodList.get(position).getFoodSugar());

        v.setTag(foodList.get(position).getFoodName());
        Button selectBtn = (Button) v.findViewById(R.id.select);
        selectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, FoodAddActivity.class);
                intent.putExtra("FoodName", FoodName.getText());
                intent.putExtra("FoodKcal", String.valueOf(FoodKcal));
                intent.putExtra("FoodCarbohydrate", String.valueOf(FoodCarbohydrate));
                intent.putExtra("FoodProtein", String.valueOf(FoodProtein));
                intent.putExtra("FoodFat", String.valueOf(FoodFat));
                intent.putExtra("FoodSodium", String.valueOf(FoodSodium));
                intent.putExtra("FoodSugar", String.valueOf(FoodSugar));
                intent.putExtra("FoodKg", String.valueOf(FoodKg));
                intent.putExtra("FoodCood", FoodCood);
                intent.putExtra("Date", Date);
                context.startActivity(intent);
                };
        });
        Button DetailBtn = (Button) v.findViewById(R.id.Detail);
        DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle( String.valueOf(FoodName.getText()) + "상세성분"); //제목
                dlg.setMessage("칼로리:"+FoodKcal*FoodKg+"\n탄수화물:"+FoodCarbohydrate*FoodKg+"\n단백질:"+FoodProtein*FoodKg+"\n지방:"+FoodFat*FoodKg
                +"\n나트륨:"+FoodSodium*FoodKg+"\n당:"+FoodSugar*FoodKg+"\n무게:"+FoodKg);
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(context,"확인을 눌르셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }

        });

        return v;

    }
}