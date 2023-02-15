package com.example.djsu.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.djsu.R;
import com.example.djsu.exButtonAdapter;
import com.example.djsu.exerciseAdapter;
import com.example.djsu.exerciseLsit;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Fragment6 extends Fragment {
    private FirebaseFirestore db;
    private RecyclerView recyclerView,recyclerView1;
    private RecyclerView.Adapter adapter,buttonAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList arrayList;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment6, container, false);
        recyclerView = view.findViewById(R.id.shoulder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<exerciseLsit>();
        recyclerView1 = view.findViewById(R.id.shoulder2);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager);

        buttonAdapter = new exButtonAdapter(arrayList,getActivity(),getActivity());
        recyclerView1.setAdapter(buttonAdapter);
        db = FirebaseFirestore.getInstance();

        adapter = new exerciseAdapter(arrayList,getActivity());
        recyclerView.setAdapter(adapter);

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
        return view;
    }
}
