package com.example.djsu;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.signup_btn).setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.signup_btn:
                    signUp();
                    break;
            }
        }
    };


    private void signUp(){
        String id=((EditText)findViewById(R.id.email_editText)).getText().toString();
        String password=((EditText)findViewById(R.id.password_editText)).getText().toString();
        String name=((EditText)findViewById(R.id.name_editText)).getText().toString();
        String birth=((EditText)findViewById(R.id.birth_editText)).getText().toString();
        String passwordCheck=((EditText)findViewById(R.id.password_editText2)).getText().toString();

        if(id.length()>0 && password.length()>0 && passwordCheck.length()>0 && name.length()>0 && birth.length()>0){
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(id, password)
                        .addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    move();
                                    Toast.makeText(signup.this, "회원가입에 성공했습니다." ,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(signup.this, login.class);
                                    startActivity(intent);
                                } else {
                                    if(task.getException().toString() !=null){
                                        Toast.makeText(signup.this, "회원가입에 실패했습니다." ,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
            else{
                Toast.makeText(signup.this, "비밀번호가 일치하지 않습니다." ,Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(signup.this, "입력을 안한 정보가 있습니다." ,Toast.LENGTH_SHORT).show();
        }
    }

    private void move() {
        String id=((EditText)findViewById(R.id.email_editText)).getText().toString();
        String password=((EditText)findViewById(R.id.password_editText)).getText().toString();
        String name=((EditText)findViewById(R.id.name_editText)).getText().toString();
        String birth=((EditText)findViewById(R.id.birth_editText)).getText().toString();
        // Create a new user with a first and last name
        Map<String, Object> member = new HashMap<>();
        member.put("Email", id);
        member.put("Password", password);
        member.put("Name", name);
        member.put("Birth", birth);


        // Add a new document with a generated ID
        db.collection("member")
                .add(member)
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
        db.collection("member").get()
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
}