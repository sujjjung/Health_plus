package com.example.djsu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Food> FoodArrayList;
    Context mContext;

    // creating constructor for our adapter class
    public ButtonAdapter(ArrayList<Food> FoodArrayList,Context mContext) {
        this.FoodArrayList = FoodArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.button, parent, false);
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        return new ViewHolder(v,v1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        Food food = FoodArrayList.get(position);
        holder.fname.setText(food.getFoodName());
        holder.fkcal.setText(food.getFoodKcal());
        holder.fcarbohydrate.setText(food.getFoodCarbohydrate());
        holder.fprotein.setText(food.getFoodProtein());
        holder.ffat.setText(food.getFoodFat());
        holder.fsodium.setText(food.getFoodSodium());
        holder.fsugar.setText(food.getFoodSugar());
        holder.fkg.setText(food.getFoodKg());

        holder.DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                show();
                new AlertDialog.Builder(mContext)
                        .setTitle(food.getFoodName() + " 상세성분")
                        .setMessage("칼로리 : " + food.getFoodKcal() + "\n" + "탄수화물 : " + food.getFoodCarbohydrate()
                                + "\n" + "단백질 : " + food.getFoodProtein() + "\n" + "지방 : " + food.getFoodFat()+ "\n" +
                                "나트륨 : " + food.getFoodSodium() + "\n" +"당 : " + food.getFoodSugar()+ "\n" +"무게 : " + food.getFoodKg())
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }
                        })
                        .show(); // 팝업창 보여줌
            }

        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return FoodArrayList.size();
    }

    public void setItems(ArrayList<Food> list1) {
        FoodArrayList = list1;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView fname, fkcal, fcarbohydrate, fprotein, ffat, fsodium, fsugar, fkg;
        public Button DetailBtn;

        public ViewHolder(@NonNull View itemView, View v1){
            super(itemView);
            // initializing our text views.
            fname = v1.findViewById(R.id.name);
            fkcal = v1.findViewById(R.id.Kcal);
            fcarbohydrate = v1.findViewById(R.id.Carbohydrate);
            fprotein = v1.findViewById(R.id.Protein);
            ffat = v1.findViewById(R.id.Fat);
            fsodium = v1.findViewById(R.id.Sodium);
            fsugar = v1.findViewById(R.id.Sugar);
            fkg = itemView.findViewById(R.id.Kg);
            // initializing our text views.
            DetailBtn = (Button)itemView.findViewById(R.id.Detail);
        }
    }
public void show(){

}
}