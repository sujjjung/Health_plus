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

            TextView roomNameTextView = convertView.findViewById(R.id.txt_message);
            TextView roomMsg = convertView.findViewById(R.id.message);

            final ChatRoom chatRoom = getItem(position);

            roomNameTextView.setText(chatRoom.getChatRoomId());
            // ChatRoom 객체에서 최근 메시지의 내용을 가져옵니다.
            DatabaseReference chatRoomRef = databaseReference.child("ChatRoom").child(chatRoom.getChatRoomId());
            chatRoomRef.child("Message")
                    .orderByKey()
                    .limitToLast(1)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                    ChatRoom recentChat = messageSnapshot.getValue(ChatRoom.class);
                                    if (recentChat != null) {
                                        // 최근 메시지의 내용을 roomMsgTextView에 설정합니다.
                                        roomMsg.setText(recentChat.getMsg());
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // 오류 처리
                        }
                    });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String chatRoomId = chatRoom.getChatRoomId();

                    moveToChatRoom(chatRoomId);
                }
            });


            return convertView;
        }

    private void moveToChatRoom(String chatRoomId) {
        // 채팅방 액티비티로 이동하는 코드를 작성합니다.
        // 예시: ChatRoomActivity.class는 채팅방 액티비티의 클래스명입니다.
        Intent intent = new Intent(context, chat_room.class);
        intent.putExtra("chatRoomId", chatRoomId);
        context.startActivity(intent);
    }


    public void update(ChatRoom chatRoom) {
    }
}

