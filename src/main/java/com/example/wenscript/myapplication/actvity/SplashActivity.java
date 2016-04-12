package com.example.wenscript.myapplication.actvity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.wenscript.myapplication.MainActivity;
import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.util.ScreenUtil;

/**
 * Created by wenscript on 2016/1/17.
 */
public class SplashActivity extends MainActivity {
    private Bitmap bitmap;
    private Animation animation;
    private int width;
    private int height;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        int[] hw = ScreenUtil.getScreenHW2(this);
        width = hw[0];
        height = hw[1];
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.splash);
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        image = (ImageView) findViewById(R.id.splash_imge);
        image.setImageBitmap(bitmap);
        animation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
        image.startAnimation(animation);

    }

    @Override
    protected void onStart() {
        super.onStart();
        startActivity(new Intent(this, HomeActivity.class));
    }
}
