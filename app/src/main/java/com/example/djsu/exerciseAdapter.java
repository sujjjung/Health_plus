package com.example.djsu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class exerciseAdapter extends RecyclerView.Adapter<exerciseAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<exerciseLsit> exArrayList;


    // creating constructor for our adapter class
    public exerciseAdapter(ArrayList<exerciseLsit> exArrayList, FragmentActivity activity) {
        this.exArrayList = exArrayList;

    }

    @NonNull
    @Override
    public exerciseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull exerciseAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        exerciseLsit exeLsit = exArrayList.get(position);
        holder.Name.setText(exeLsit.getExerciseName());
        holder.Explanation.setText(exeLsit.getExerciseExplanation());
        holder.Calorie.setText(exeLsit.getExerciseCalorie());
        holder.Unit.setText(exeLsit.getExerciseUnit());


    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return exArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView Name,Explanation,Calorie,Unit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            Name = itemView.findViewById(R.id.exName);
            Explanation = itemView.findViewById(R.id.exExplanation);
            Calorie = itemView.findViewById(R.id.exCalorie);
            Unit = itemView.findViewById(R.id.exUnit);
        }
    }
}
