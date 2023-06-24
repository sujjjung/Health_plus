package com.example.djsu;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class chatListAdapter extends ArrayAdapter<ChatRoom> {

    private Context context;

    private List<ChatRoom> postList;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

        public chatListAdapter(Context context, List<ChatRoom> postList) {
            super(context, 0, postList);
            this.context = context;
            this.postList = postList;
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_chat_list, parent, false);
        }

        TextView roomName = convertView.findViewById(R.id.roomName);
        TextView roomMsg = convertView.findViewById(R.id.message);
        TextView msgDate = convertView.findViewById(R.id.date);

        final ChatRoom chatRoom = getItem(position);

        // Get the reference to the member node of the current ChatRoom
        DatabaseReference memberRef = databaseReference.child("ChatRoom").child(chatRoom.getChatRoomId()).child("member");

        View finalConvertView = convertView;
        memberRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    StringBuilder roomNameBuilder = new StringBuilder();
                    for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                        String memberName = memberSnapshot.child("name").getValue(String.class);
                        if (memberName != null) {
                            // Append the member name to the roomNameBuilder
                            roomNameBuilder.append(memberName).append(", ");
                        }
                    }

                    // Remove the trailing comma and space
                    String roomNameText = roomNameBuilder.toString().trim();
                    if (roomNameText.endsWith(",")) {
                        roomNameText = roomNameText.substring(0, roomNameText.length() - 1);
                    }

                    roomName.setText(roomNameText);

                    String finalRoomNameText = roomNameText;
                    finalConvertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String chatRoomId = chatRoom.getChatRoomId();
                            String roomName = finalRoomNameText; // 수정된 부분

                            moveToChatRoom(chatRoomId, roomName);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        roomName.setText(chatRoom.getChatRoomId());

        // Get the reference to the messages node of the current ChatRoom
        DatabaseReference messagesRef = databaseReference.child("ChatRoom").child(chatRoom.getChatRoomId()).child("Message");

        messagesRef.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        ChatRoom recentChat = messageSnapshot.getValue(ChatRoom.class);
                        if (recentChat != null) {
                            // Update the roomMsg and msgDate TextViews with the recent message content
                            roomMsg.setText(recentChat.getMsg());
                            msgDate.setText(recentChat.getDate());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        return convertView;
    }


    private void moveToChatRoom(String chatRoomId, String roomName) {
        Intent intent = new Intent(context, chat_room.class);
        intent.putExtra("chatRoomId", chatRoomId);
        intent.putExtra("roomName", roomName);
        context.startActivity(intent);
    }


    public void update(ChatRoom chatRoom) {
    }
}

