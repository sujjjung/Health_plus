package com.example.djsu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class exerciserecodeAdapter extends RecyclerView.Adapter<exerciserecodeAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<exrecode> exArrayList;
    private ArrayList<Set> setArrayList = new ArrayList<>();
    private Context context;
    int count = 0;

    // creating constructor for our adapter class
    public exerciserecodeAdapter(ArrayList<exrecode> exArrayList, Context context) {
        this.exArrayList = exArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public exerciserecodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exerciserecord,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull exerciserecodeAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our text views from our modal class.
        exrecode exeLsit = exArrayList.get(position);
        holder.setnumber.setText(exeLsit.getSetNumber());
        holder.number.setText(exeLsit.getNumber());
        holder.Unit.setText(exeLsit.getUnit());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // CheckBox의 상태가 변경되었을 때 호출되는 콜백
                if (isChecked) {
                    String setNumber = holder.setnumber.getText().toString();
                    String number = holder.number.getText().toString();
                    String unit = holder.Unit.getText().toString();
                    Set set = new Set(setNumber, number, unit);
                    setArrayList.add(set); // 해당 포지션에 값을 추가합니다.
                    set.setSetArrayList(setArrayList);
                    Toast.makeText(buttonView.getContext(), "선택된 데이터: " + number, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < setArrayList.size(); i++) {
                        System.out.println("hongchul" + setArrayList.get(i).getNumber());
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return exArrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        public CompoundButton checkbox;
        public TextView setnumber, number, Unit;
        public ArrayList<Set> setArrayList = new ArrayList<>();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            setnumber = itemView.findViewById(R.id.setnumber);
            number = itemView.findViewById(R.id.number);
            Unit = itemView.findViewById(R.id.unit);
            checkbox = itemView.findViewById(R.id.checkbox);


        }
    }
}