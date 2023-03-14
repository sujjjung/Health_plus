package com.example.djsu;

public class Notice {
    String date,title,content,emote;

    public Notice(String date, String title, String content,String emote) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.emote = emote;
    }

    public String getEmote() {
        return emote;
    }

    public void setEmote(String emote) {
        this.emote = emote;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
