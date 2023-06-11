package com.example.djsu.admin;

public class AdminManagement {
    private int declarationid, postid;
    private String UserId, postname,cause,Date,type;

    public AdminManagement() {
    }

    public AdminManagement(int declarationid, int postid, String userId, String postname, String cause, String date,String type) {
        this.declarationid = declarationid;
        this.postid = postid;
        UserId = userId;
        this.postname = postname;
        this.cause = cause;
        Date = date;
        this.type = type;
    }

    public int getDeclarationid() {
        return declarationid;
    }

    public void setDeclarationid(int declarationid) {
        this.declarationid = declarationid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
