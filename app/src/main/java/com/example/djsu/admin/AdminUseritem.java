package com.example.djsu.admin;

import android.graphics.drawable.Drawable;

public class AdminUseritem {
    private Drawable iconDrawable;
    private String title;
    private String desc;

    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }

    public Drawable getIcon() {
        return this.iconDrawable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
