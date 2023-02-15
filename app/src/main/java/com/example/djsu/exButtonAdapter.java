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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.djsu.Fragment.Fragment2;

import java.util.ArrayList;

public class exButtonAdapter extends RecyclerView.Adapter<exButtonAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<exerciseLsit> exArrayList;
    Context mContext;

    // creating constructor for our adapter class
    public exButtonAdapter(ArrayList<exerciseLsit> exerciseLsit, Context mContext, FragmentActivity activity) {
        this.exArrayList = exerciseLsit;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public exButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exbutton, parent, false);
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_item, parent, false);
        return new ViewHolder(v,v1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        exerciseLsit exeLsit = exArrayList.get(position);
        holder.Name.setText(exeLsit.getExerciseName());
        holder.Explanation.setText(exeLsit.getExerciseExplanation());
        holder.Calorie.setText(exeLsit.getExerciseCalorie());
        holder.Unit.setText(exeLsit.getExerciseUnit());
        holder.explanationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                new AlertDialog.Builder(mContext)
                        .setTitle(exeLsit.getExerciseName() + " 설명")
                        .setMessage("설명 : " + exeLsit.getExerciseExplanation())
                        .setNeutralButton("닫기", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }
                        }).show();
            }

        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return exArrayList.size();
    }

    public void setItems(ArrayList<exerciseLsit> list1) {
        exArrayList = list1;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        private final TextView Name,Explanation,Calorie,Unit;
        public Button explanationBtn;
        public ViewHolder(@NonNull View itemView, View v1) {
            super(itemView);
            // initializing our text views.
            Name = v1.findViewById(R.id.exName);
            Explanation = v1.findViewById(R.id.exExplanation);
            Calorie = v1.findViewById(R.id.exCalorie);
            Unit = v1.findViewById(R.id.exUnit);
            explanationBtn = (Button)itemView.findViewById(R.id.explanation);
        }
    }
    public void show(){

    }
}