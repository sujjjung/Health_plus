package com.example.djsu.Fragment;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.djsu.Food;
import com.example.djsu.R;
import com.example.djsu.exButtonAdapter;
import com.example.djsu.exerciseAdapter;
import com.example.djsu.exerciseLsit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {
    private FirebaseFirestore db;
    private RecyclerView recyclerView,recyclerView1;
    private RecyclerView.Adapter buttonAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<exerciseLsit> arrayList;
    private ArrayList<exerciseLsit> search_list;

    private View view;
    private EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);
        search_list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        editText = view.findViewById(R.id.searchtext);
        recyclerView1 = view.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager);

        buttonAdapter = new exButtonAdapter(arrayList,getActivity(),getActivity());
        recyclerView1.setAdapter(buttonAdapter);
        db = FirebaseFirestore.getInstance();

        exerciseAdapter adapter = new exerciseAdapter(arrayList,getActivity());
        recyclerView.setAdapter(adapter);
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
                    adapter.setItems(arrayList);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < arrayList.size(); a++) {
                        if (arrayList.get(a).getExerciseName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(arrayList.get(a));
                        }
                        adapter.setItems(search_list);
                    }
                }
            }

        });
        db.collection("하체")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            exerciseLsit object=d.toObject(exerciseLsit.class);
                            arrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        db.collection("가슴")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            exerciseLsit object=d.toObject(exerciseLsit.class);
                            arrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        db.collection("등")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            exerciseLsit object=d.toObject(exerciseLsit.class);
                            arrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        db.collection("복근")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            exerciseLsit object=d.toObject(exerciseLsit.class);
                            arrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        db.collection("어깨")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            exerciseLsit object=d.toObject(exerciseLsit.class);
                            arrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        db.collection("팔")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            exerciseLsit object=d.toObject(exerciseLsit.class);
                            arrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
        db.collection("유산소")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:list){
                            exerciseLsit object=d.toObject(exerciseLsit.class);
                            arrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        return view;

    }

}


