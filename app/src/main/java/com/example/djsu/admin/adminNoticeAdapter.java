package com.example.djsu.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.djsu.Notice;
import com.example.djsu.NoticeDelete;
import com.example.djsu.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class adminNoticeAdapter extends BaseAdapter {
    private Context context;
    private List<Notice> NoticeList;
    private Activity parentActivity;

    public adminNoticeAdapter(Context context, List<Notice> NoticeList) {
        this.context = context;
        this.NoticeList = NoticeList;
        this.parentActivity = parentActivity;
    }
    @Override
    public int getCount () {
        return NoticeList.size();//리스트뷰의 총 갯수
    }

    @Override
    public Object getItem (int position){
        return NoticeList.get(position);//해당 위치의 값을 리스트뷰에 뿌려줌
    }
    @Override
    public long getItemId (int position){
        return position;
    }
    public void setItems(ArrayList<Notice> list) {
        NoticeList = list;
        notifyDataSetChanged();
    }
    //리스트뷰에서 실질적으로 뿌려주는 부분임
    @Override
    public View getView (final int position, View convertView, ViewGroup parent){

        View v = View.inflate(context, R.layout.item_notice, null);
        // final TextView noticeText = (TextView) v.findViewById(R.id.userContent);
        TextView date = (TextView) v.findViewById(R.id.date);
        TextView title = (TextView) v.findViewById(R.id.title);
        date.setText(NoticeList.get(position).getDate());
        title.setText(NoticeList.get(position).getTitle());
        String content = NoticeList.get(position).getContent();
        v.setTag(NoticeList.get(position).getDate());
        Button deleteBtn = (Button) v.findViewById(R.id.delete);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if (success) {
                                Toast.makeText(context.getApplicationContext(), "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                NoticeList.remove(position);//리스트에서 해당부분을 지워줌
                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                NoticeDelete deleteRequest = new NoticeDelete(content, responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteRequest);

            }
        });
        Button DetailBtn = (Button) v.findViewById(R.id.Detail);
        DetailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle( String.valueOf(title.getText())); //제목
                dlg.setMessage(content);
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        //토스트 메시지
                        Toast.makeText(context,"확인을 눌르셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }

        });
        return v;

    }
}