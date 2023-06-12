package com.example.djsu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoom {
    private String chatRoomId;
    private String title;
    private List<String> members;

    private String msg;

    private String userName;

    private String Date;

    public ChatRoom() {}

    public ChatRoom(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public ChatRoom(String chatRoomId, String title, List<String> members) {
        this.chatRoomId = chatRoomId;
        this.title = title;
        this.members = members;
    }

    // Getters and Setters

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("chatRoomId", chatRoomId);
        result.put("title", title);
        result.put("members", members);
        return result;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
