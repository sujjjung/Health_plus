package com.example.djsu;

public class Post {
    String content, id, image, date;

    public Post(String content, String id, String image, String date) {
        this.content = content;
        this.id = id;
        this.image = image;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
