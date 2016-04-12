package com.example.wenscript.myapplication.model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wenscript on 2016/2/24.
 * 带有url属性的textview
 */
public class MyTextView extends TextView {
    private String url;
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setUrl(String url){
        this.url=url;
    }
    public String getUrl(){
        return url;
    }
}
