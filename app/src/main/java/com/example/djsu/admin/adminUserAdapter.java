package com.example.djsu.admin;

        import android.annotation.SuppressLint;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.Volley;
        import com.bumptech.glide.Glide;
        import com.example.djsu.R;
        import com.example.djsu.UserFoodDelete;
        import com.example.djsu.declarationRequest;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;
        import com.squareup.picasso.Picasso;

        import org.json.JSONObject;

        import java.util.ArrayList;

public class adminUserAdapter extends RecyclerView.Adapter<com.example.djsu.admin.adminUserAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<adminUser> userArrayList;
    private Context context;
    DatabaseReference databaseReference, userName;


    // creating constructor for our adapter class
    public adminUserAdapter(ArrayList<adminUser> userArrayList, Context context) {
        this.userArrayList = userArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public com.example.djsu.admin.adminUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_useritem,parent,false);

        return new com.example.djsu.admin.adminUserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.djsu.admin.adminUserAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.userId.setText(userArrayList.get(position).getUserID());

        String imageUrl = userArrayList.get(position).getProfile(); // Get the image URL from User object
        Picasso.get().load(imageUrl).into(holder.userProfile);
        holder.Name =  userArrayList.get(position).getName();
        holder.Age =  userArrayList.get(position).getUserAge();
        final String name = holder.Name;
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                view = LayoutInflater.from(context).inflate(R.layout.dialog_value, null, false);
                builder.setView(view);
                final TextView userName = (TextView) view.findViewById(R.id.userName);
                final TextView userAge = (TextView) view.findViewById(R.id.userAge);
                userName.setText("유저 이름: " + holder.Name);
                userAge.setText("유저 나이: " +  holder.Age);
                final Button conformBtn = (Button) view.findViewById(R.id.conformBtn);
                final AlertDialog dialog = builder.create();
                conformBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference();
                userName = databaseReference.child("User");

                String targetName = name;
                System.out.println(targetName);

                Query query = userName.orderByChild("name").equalTo(targetName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // onDataChange 메서드에서 조회 결과를 처리
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String key = snapshot.getKey();
                            // User 노드에서 해당 데이터를 삭제
                            DatabaseReference targetUserRef = userName.child(key);
                            targetUserRef.removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // onCancelled 메서드에서 오류 처리
                    }
                });


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            //받아온 값이 success면 정상적으로 서버로부터 값을 받은 것을 의미함
                            if (success) {
                                Toast.makeText(context.getApplicationContext(), "삭제 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                userArrayList.remove(position);//리스트에서 해당부분을 지워줌
                                notifyDataSetChanged();//데이터가 변경된 것을 어댑터에 알려줌
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                adminUserDelete deleteRequest = new adminUserDelete(userArrayList.get(position).getUserID(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(deleteRequest);
            }
        });
    }


    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return userArrayList.size();
    }
    public void setItems(ArrayList<adminUser> list){
        userArrayList = list;
        notifyDataSetChanged();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our text views.
//        private final ImageView userprofile;
        String Name,Age;
        TextView userId;
        ImageView userProfile;
        Button detailBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
//            userprofile = itemView.findViewById(R.id.profile);
            userId = itemView.findViewById(R.id.name);
            userProfile = itemView.findViewById(R.id.profile);
            detailBtn = itemView.findViewById(R.id.detail);
            deleteBtn = itemView.findViewById(R.id.delete);
        }
    }



}

