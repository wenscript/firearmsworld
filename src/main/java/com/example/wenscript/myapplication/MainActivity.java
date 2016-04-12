package com.example.wenscript.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Activity> collector;
    private BaseApplication baseApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        baseApplication= (BaseApplication) getApplication();
        if (baseApplication.isDayMode()){
            setTheme(R.style.AppTheme);
        }else {
            setTheme(R.style.AppTheme_night);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        collector=new ArrayList<>();
        collector.add(this);


    }
    public void changeToDay(){
        baseApplication.setIsDayMode(true);
    }
    public void changeToNight(){
        baseApplication.setIsDayMode(false);
    }
    public void removeAll(){
        for (Activity activity:collector){
            activity.finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        collector.remove(this);
    }
    public void recreateOnResume() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                recreate();
            }
        }, 1000);
    }
}
