package com.example.djsu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chatListAdapter extends ArrayAdapter<ChatRoom> {

        public chatListAdapter(Context context, int chatRooms) {
            super(context, 0, chatRooms);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat_list, parent, false);
            }

            ChatRoom chatRoom = getItem(position);

            User user = new User();
            String userId = user.getId();

            TextView roomNameTextView = convertView.findViewById(R.id.txt_message);
            roomNameTextView.setText(chatRoom.getTitle());

            // Firebase Realtime Database의 데이터 경로 설정
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(userId).child("chatRooms");

            // ValueEventListener 객체 생성 및 이벤트 리스너 등록
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // "chatRooms" 하위 노드의 데이터를 가져옴
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String chatRoomId = snapshot.getKey(); // ChatRoom 객체의 ID
                        String chatRoomTitle = snapshot.child("chatRooms").getValue(String.class); // ChatRoom 객체의 title
                        // 가져온 데이터를 리스트에 추가 또는 업데이트
                        // 예시) mAdapter.add(new ChatRoom(chatRoomId, chatRoomTitle));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // 데이터 읽기가 취소되었을 때 호출됨
                }
            };
            // ValueEventListener 등록
            databaseReference.addValueEventListener(valueEventListener);


            return convertView;
        }


    public void update(ChatRoom chatRoom) {
    }
}

