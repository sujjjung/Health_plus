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
        mCheckedList = new ArrayList<>(Collections.nCopies(this.setArrayList.size(), false));
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

        holder.checkbox.setChecked(mCheckedList.get(position));

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckedList.set(position, isChecked);
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
    public List<Set> getCheckedItems() {
        List<Set> checkedItems = new ArrayList<>();
        for (int i = 0; i < setArrayList.size(); i++) {
            if (mCheckedList.get(i)) {
                checkedItems.add(setArrayList.get(i));
            }
        }
        return checkedItems;
    }

}
