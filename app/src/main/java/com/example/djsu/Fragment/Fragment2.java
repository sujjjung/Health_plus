package com.example.djsu.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.djsu.User;
import com.example.djsu.exerciseAdapter;
import com.example.djsu.exerciseLsit;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    private List<exerciseLsit>exerciseLsits;
    private exerciseAdapter exerciseAdapter;
    private ArrayList<exerciseLsit> search_list;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ExCode  = "ExerciseCode";
    private static final String TAG_ExName = "ExerciseName";
    private static final String TAG_ExPart = "ExercisePart";
    private static final String TAG_ExExplanation = "ExerciseExplanation";
    String myJSON;
    JSONArray peoples = null;
    private EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        search_list = new ArrayList<>();
        editText = view.findViewById(R.id.searchtext);
        String Date = getArguments().getString("Date");
        String RoutineNameText = getArguments().getString("RoutineNameText");
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

        exerciseAdapter = new exerciseAdapter(getActivity(),exerciseLsits,Date,RoutineNameText);

        ListView exListView = (ListView) view.findViewById(R.id.ExView);
        exListView.setAdapter(exerciseAdapter);

        getData("http://enejd0613.dothome.co.kr/rowexlist.php");
        return view;

    }
    protected void showList() {
        exerciseAdapter.notifyDataSetChanged();
        try {
            if (myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonObj = new JSONObject(myJSON);
                peoples = jsonObj.getJSONArray(TAG_RESULTS);

                for(int i = 0;i < peoples.length(); i++) {
                    JSONObject c = peoples.getJSONObject(i);
                    String ExCode = c.getString(TAG_ExCode);
                    String ExerciseName = c.getString(TAG_ExName);
                    String ExPart = c.getString(TAG_ExPart);
                    String ExExplanation = c.getString(TAG_ExExplanation);
                    exerciseLsit exlist = new exerciseLsit(ExCode,ExPart,ExerciseName,ExExplanation);
                    exerciseLsits.add(exlist);//리스트뷰에 값을 추가해줍니다
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);

    }

}


