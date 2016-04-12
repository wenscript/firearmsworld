package com.example.wenscript.myapplication.model;

/**
 * Created by wenscript on 2016/1/21.
 * 首页，listview的item
 */
public class HomePageItem  {
    private String url;
    private String name;

    public HomePageItem(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
