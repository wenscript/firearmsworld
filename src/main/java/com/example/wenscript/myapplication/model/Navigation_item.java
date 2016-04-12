package com.example.wenscript.myapplication.model;

/**
 * Created by wenscript on 2016/1/18.
 */
public class Navigation_item {
    private int image;
    private String name;

    public Navigation_item(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
