package com.example.wenscript.myapplication.util;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by wenscript on 2016/2/22.
 */
public class CharsetStringRequest extends StringRequest {
    public CharsetStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public CharsetStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String str=null;
        try {
            str=new String(response.data,"gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(str,HttpHeaderParser.parseCacheHeaders(response));
    }
}
