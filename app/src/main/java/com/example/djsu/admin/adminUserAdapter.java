package com.example.djsu.admin;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;
        import com.example.djsu.R;

        import java.util.ArrayList;

public class adminUserAdapter extends RecyclerView.Adapter<com.example.djsu.admin.adminUserAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<adminUser> userArrayList;


    // creating constructor for our adapter class
    public adminUserAdapter(ArrayList<adminUser> userArrayList) {
        this.userArrayList = userArrayList;

    }

    @NonNull
    @Override
    public com.example.djsu.admin.adminUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_useritem,parent,false);

        return new com.example.djsu.admin.adminUserAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.djsu.admin.adminUserAdapter.ViewHolder holder, int position) {
        // setting data to our text views from our modal class.
//        User user = userArrayList.get(position);
//        holder.userprofile.se(user.getProfile());
        Glide.with(holder.itemView)
                .load(userArrayList.get(position).getProfile())
                .into(holder.userProfile);
        holder.userName.setText(userArrayList.get(position).getName());
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView profile;
//        TextView name;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.profile = itemView.findViewById(R.id.profile);
//            this.name = itemView.findViewById(R.id.name);
//        }
//    }

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
        TextView userName;
        ImageView userProfile;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
//            userprofile = itemView.findViewById(R.id.profile);
            userName = itemView.findViewById(R.id.name);
            userProfile = itemView.findViewById(R.id.profile);
        }
    }



}

