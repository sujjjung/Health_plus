package com.example.djsu.admin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.djsu.R;
import com.example.djsu.signup;
import com.example.djsu.target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class AdminFoodAdd extends AppCompatActivity {
    private Button saveBtn;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_add);
        saveBtn = findViewById(R.id.SaveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminFoodAdd.this, "음식등록 성공.", Toast.LENGTH_SHORT).show();
                String fName=((EditText)findViewById(R.id.FoodNameEdit)).getText().toString();
                String fKcal=((EditText)findViewById(R.id.FoodKcalEdit)).getText().toString();
                String fCarbohydrate=((EditText)findViewById(R.id.FoodCarbohydrateEdit)).getText().toString();
                String fProtein=((EditText)findViewById(R.id.FoodProteinEdit)).getText().toString();
                String fFat=((EditText)findViewById(R.id.FoodFatEdit)).getText().toString();
                String fSodium=((EditText)findViewById(R.id.FoodSodiumEdit)).getText().toString();
                String fSugar=((EditText)findViewById(R.id.FoodSugarEdit)).getText().toString();
                String fKg=((EditText)findViewById(R.id.FoodgEdit)).getText().toString();
                Map<String, Object> Food = new HashMap<>();
                Food.put("1.이름", fName);
                Food.put("2.칼로리", fKcal);
                Food.put("3.탄수화물", fCarbohydrate);
                Food.put("4.단백질", fProtein);
                Food.put("5.지방", fFat);
                Food.put("6.나트륨", fSodium);
                Food.put("7.당", fSugar);
                Food.put("8.무게", fKg);



                // Add a new document with a generated ID
                db.collection("Food")
                        .add(Food)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                db.collection("Food").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });
            }
        });
    }

}