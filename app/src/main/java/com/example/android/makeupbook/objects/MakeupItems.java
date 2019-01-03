package com.example.android.makeupbook.objects;

import android.graphics.drawable.Drawable;

public class MakeupItems {
    private String name;
    private Drawable image;

    public MakeupItems(){

    }

    public MakeupItems(String name, Drawable image){
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
