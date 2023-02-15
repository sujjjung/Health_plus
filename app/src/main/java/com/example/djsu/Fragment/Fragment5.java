package com.example.djsu.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.djsu.ButtonAdapter;
import com.example.djsu.Food;
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

public class Fragment5 extends Fragment {
    private FirebaseFirestore db;
    private RecyclerView recyclerView,recyclerView1;
    private RecyclerView.Adapter buttonAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<exerciseLsit> arrayList;
    private ArrayList<exerciseLsit> search_list;
    private View view;
    private EditText editText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment5, container, false);
        recyclerView = view.findViewById(R.id.Abs);
        search_list = new ArrayList<>();
        editText = view.findViewById(R.id.searchtext);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        arrayList = new ArrayList<>();
        exerciseAdapter adapter = new exerciseAdapter(arrayList,getActivity());
        recyclerView1 = view.findViewById(R.id.Abs2);
        recyclerView1.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager);

        buttonAdapter = new exButtonAdapter(arrayList,getActivity(),getActivity());
        recyclerView1.setAdapter(buttonAdapter);


        db = FirebaseFirestore.getInstance();

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
        return view;
    }
}