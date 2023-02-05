package com.example.djsu.admin;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.djsu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AdminExerciseSubAdd extends AppCompatActivity {
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button saveBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exercise_sub_add);
        saveBtn = findViewById(R.id.SaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminExerciseSubAdd.this, "운동등록 성공.", Toast.LENGTH_SHORT).show();
                String hName=((EditText)findViewById(R.id.HealthNameEdit)).getText().toString();
                String hExplanation=((EditText)findViewById(R.id.HealthExplanationEdit)).getText().toString();
                String hKcal=((EditText)findViewById(R.id.FHealthKcalEdit)).getText().toString();
                String hunit=((EditText)findViewById(R.id.HealthunitEdit)).getText().toString();
                Map<String, Object> Exercise = new HashMap<>();
                Exercise.put("1.이름", hName);
                Exercise.put("2.운동설명", hExplanation);
                Exercise.put("3.소비칼로리", hKcal);
                Exercise.put("4.단위", hunit);

                // Add a new document with a generated ID
                db.collection("Exercise")
                        .add(Exercise)
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
                db.collection("Exercise").get()
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