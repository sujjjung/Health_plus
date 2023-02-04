package com.example.djsu.admin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
import android.widget.ListView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.content.ContextCompat;

import com.example.djsu.R;

public class AdminUserListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);

        ListView listView = findViewById(R.id.listView1);

        AdminUserListViewAdapter adapter = new AdminUserListViewAdapter();
        listView.setAdapter(adapter);

        // 아이템 추가
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.user),"김수빈", "24dp Black");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.user),"김종하", "24dp Black");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.user),"이수정", "24dp Black");
        adapter.addItem(ContextCompat.getDrawable(this,R.drawable.user),"이동준", "24dp Black");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminUserListActivity.this, AdminUserSub.class);
                startActivity(intent);
            }
        });
    }
}
