package com.example.djsu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.djsu.Food;
import com.example.djsu.FoodAdapter;
import com.example.djsu.HealthAddActivity;
import com.example.djsu.R;
import com.example.djsu.exerciseAdapter;
import com.example.djsu.exerciseLsit;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends Fragment {
    private List<exerciseLsit>exerciseLsits;
    private exerciseAdapter exerciseAdapter;
    private ArrayList<exerciseLsit> search_list;

    private View view;
    private EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3, container, false);
        search_list = new ArrayList<>();
        editText = view.findViewById(R.id.searchtext);

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
                    exerciseAdapter.setItems((ArrayList<exerciseLsit>) exerciseLsits);
                }
                else {
                    // 검색 단어를 포함하는지 확인
                    for (int a = 0; a < exerciseLsits.size(); a++) {
                        if (exerciseLsits.get(a).getExerciseName().toLowerCase().contains(searchText.toLowerCase())) {
                            search_list.add(exerciseLsits.get(a));
                        }
                        exerciseAdapter.setItems(search_list);
                    }
                }
            }

        });
        exerciseLsits =new ArrayList<>();

        exerciseAdapter = new exerciseAdapter(getActivity(),exerciseLsits);

        ListView exListView = (ListView) view.findViewById(R.id.ExView);
        exListView.setAdapter(exerciseAdapter);

        try {
            exerciseAdapter.notifyDataSetChanged();
            JSONObject jsonObject = new JSONObject(this.getArguments().getString("exercise"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String ExName;
            //JSON 배열 길이만큼 반복문을 실행
            while (count < jsonArray.length()) {
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);

                ExName = object.getString("ExName");

                //값들을 User클래스에 묶어줍니다
                exerciseLsit exlist = new exerciseLsit(ExName);
                exerciseLsits.add(exlist);//리스트뷰에 값을 추가해줍니다
                count++;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;

    }

}


