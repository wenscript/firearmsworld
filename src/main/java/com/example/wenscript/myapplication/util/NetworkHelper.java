package com.example.wenscript.myapplication.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by wenscript on 2016/1/21.
 * 网络操作的辅助类，考虑到本app数据通信频繁，但是数据量不大，我们考虑使用volley
 */
public class NetworkHelper {

    public static String request(RequestQueue mQueue, String url) {//根据网址，返回html网页
        final String[] ss = new String[2];
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ss[0] = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ss[0] = "fail";
            }
        });
        mQueue.add(stringRequest);
        return ss[0];
    }
    public static Bitmap requestImage(RequestQueue mQueue,String url){//按照指定网址，返回图片
        final Bitmap[] bitmap = new Bitmap[1];
        ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                bitmap[0] =response;
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(imageRequest);

        return bitmap[0];
    }


}
