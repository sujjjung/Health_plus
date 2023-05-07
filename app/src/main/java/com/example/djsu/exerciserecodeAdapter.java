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
    private ArrayList<Set> setArrayList;
    private Context context;
    private TextView setnumber,number,Unit;
    private List<Boolean> mCheckedList;
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
        setnumber.setText(exeLsit.getSetNumber());
        number.setText(exeLsit.getNumber());
        Unit.setText(exeLsit.getUnit());

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // CheckBox의 상태가 변경되었을 때 호출되는 콜백
                exeLsit.setSelected(isChecked == true);
                Toast.makeText(buttonView.getContext(), "선택된 데이터: " + number.getText().toString(), Toast.LENGTH_SHORT).show();
                Set set = new Set(setnumber.getText().toString(),number.getText().toString(),Unit.getText().toString());
                setArrayList.add(set);

            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return exArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
        public CompoundButton checkbox;

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
