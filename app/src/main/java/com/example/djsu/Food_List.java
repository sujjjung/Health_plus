package com.example.djsu;

import static android.content.ContentValues.TAG;
import static android.media.CamcorderProfile.get;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;


public class Food_List extends AppCompatActivity {

    // creating variables for our recycler view,
    // array list, adapter, firebase firestore
    // and our progress bar.
    private RecyclerView recyclerView,recyclerView1;
    private ArrayList<Food> foodArrayList;
    private FoodAdapter foodAdapter;
    private ButtonAdapter buttonAdapter;
    private FirebaseFirestore db;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        db = FirebaseFirestore.getInstance();
        ArrayList<Food> search_list = new ArrayList<>();
        editText = findViewById(R.id.searchtext);
        // editText 리스터 작성
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editText.getText().toString();
                search_list.clear();

                if(searchText.equals("")){
                    foodAdapter.setItems(foodArrayList);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < foodArrayList.size(); a++) {
                        if (foodArrayList.get(a).getFoodName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(foodArrayList.get(a));
                        }
                        foodAdapter.setItems(search_list);
                    }
                }
            }

        });

       // recyclerView1.bringToFront();
        foodArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(foodArrayList);
        buttonAdapter = new ButtonAdapter(foodArrayList,this);
        recyclerView1.setAdapter(buttonAdapter);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Food food = foodArrayList.get(position);
                Toast.makeText(getApplicationContext(), food.getFoodName()+' '+food.getFoodKcal()+' '+food.getFoodCarbohydrate()+' '+food.getFoodProtein()+' '+food.getFoodFat()
                        +' '+ food.getFoodSodium()+' '+food.getFoodSugar()+' '+food.getFoodKg(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), FoodAddActivity.class);

                intent.putExtra("FoodName", food.getFoodName());
                intent.putExtra( "FoodKcal", food.getFoodKcal());
                intent.putExtra("FoodCarbohydrate", food.getFoodCarbohydrate());
                intent.putExtra("FoodProtein", food.getFoodProtein());
                intent.putExtra("FoodFat", food.getFoodFat());
                intent.putExtra( "FoodSodium", food.getFoodSodium());
                intent.putExtra("FoodSugar", food.getFoodSugar());
                intent.putExtra( "FoodKg", food.getFoodKg());

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        db.collection("Food")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            Food object=d.toObject(Food.class);
                            foodArrayList.add(object);

                        }
                        foodAdapter.notifyDataSetChanged();

                    }
                });

    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Food_List.ClickListener clickListener;

        public RecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

}