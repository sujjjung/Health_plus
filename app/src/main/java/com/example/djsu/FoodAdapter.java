package com.example.djsu;


import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<Food> FoodArrayList;

    // creating constructor for our adapter class
    public FoodAdapter(ArrayList<Food> FoodArrayList) {
        this.FoodArrayList = FoodArrayList;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_item, parent, false);
        return new ViewHolder(v);
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
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return FoodArrayList.size();
    }

    public void setItems(ArrayList<Food> list) {
        FoodArrayList = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView fname, fkcal, fcarbohydrate, fprotein, ffat, fsodium, fsugar, fkg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            fname = itemView.findViewById(R.id.name);
            fkcal = itemView.findViewById(R.id.Kcal);
            fcarbohydrate = itemView.findViewById(R.id.Carbohydrate);
            fprotein = itemView.findViewById(R.id.Protein);
            ffat = itemView.findViewById(R.id.Fat);
            fsodium = itemView.findViewById(R.id.Sodium);
            fsugar = itemView.findViewById(R.id.Sugar);
            fkg = itemView.findViewById(R.id.Kg);

        }
    }
}