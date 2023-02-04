package com.example.djsu.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.djsu.R;

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