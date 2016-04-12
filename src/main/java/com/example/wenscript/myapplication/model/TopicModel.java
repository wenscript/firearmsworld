package com.example.wenscript.myapplication.model;

/**
 * Created by wenscript on 2016/3/5.
 * 公司，国家系列的模型
 */
public class TopicModel {
    private String name;
    private String url;

    public TopicModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
