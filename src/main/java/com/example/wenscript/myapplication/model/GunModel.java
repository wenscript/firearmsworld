package com.example.wenscript.myapplication.model;

/**
 * Created by wenscript on 2016/1/23.
 */
public class GunModel {
    private String name;
    private String productor;
    private String url;

    public GunModel(String name, String productor, String url) {
        this.name = name;
        this.productor = productor;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
