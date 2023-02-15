package com.example.djsu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class exerciserecodeAdapter extends RecyclerView.Adapter<exerciserecodeAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<exrecode> exArrayList;



    // creating constructor for our adapter class

    public exerciserecodeAdapter(ArrayList<exrecode> exArrayList) {
        this.exArrayList = exArrayList;

    }

    @NonNull
    @Override
    public exerciserecodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exerciserecord,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull exerciserecodeAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
        exrecode exeLsit = exArrayList.get(position);
        holder.setnumber.setText(exeLsit.getSetNumber());
        holder.number.setText(exeLsit.getNumber());
        holder.Unit.setText(exeLsit.getUnit());

        holder.checkbox.setOnCheckedChangeListener(null);

        // 모델 클래스의 getter로 체크 상태값을 가져온 다음, setter를 통해 이 값을 아이템 안의 체크박스에 set한다
        holder.checkbox.setChecked(exeLsit.getSelected());

        // 체크박스의 상태값을 알기 위해 리스너 부착
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // 여기의 item은 final 키워드를 붙인 모델 클래스의 객체와 동일하다
                exeLsit.setSelected(isChecked);
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
        private final TextView setnumber,number,Unit;
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
