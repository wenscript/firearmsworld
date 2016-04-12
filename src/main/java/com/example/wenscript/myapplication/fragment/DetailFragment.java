package com.example.wenscript.myapplication.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.data.HtmlCacheHelper;
import com.example.wenscript.myapplication.util.CharsetStringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by wenscript on 2016/2/24.
 * 用于显示某种枪械的详细信息。。。。
 */
public class DetailFragment extends Fragment {
    private FragmentActivity activity;
    private String url1;//某种枪械的url网址
    private RequestQueue rq;
    private CharsetStringRequest charsetStringRequest;
    private String request;
    private String content;
    private ProgressBar pro;
    private WebView wv;
    private String name;
    private String nowUrl;//当前页面的url;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x1008) {
                Toast.makeText(getContext(), "该页面已经缓存", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 0x2323) {
                Bundle bundle = msg.getData();
                Toast.makeText(getContext(), "全部" + bundle.getInt("data") + "张图片," + "图片已下载" + bundle.getString("progress") + "请耐心等待", Toast.LENGTH_SHORT).show();
            }

        }
    };
    private SQLiteDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq = Volley.newRequestQueue(getActivity());
        HtmlCacheHelper helper = new HtmlCacheHelper(getContext(), "html.db", null, 1);
        database = helper.getWritableDatabase();
        activity = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    Toast.makeText(getContext(), "hehe", Toast.LENGTH_SHORT).show();
//                    getActivity().getSupportFragmentManager().popBackStack("homepagefragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        wv = (WebView) view.findViewById(R.id.web);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDefaultTextEncodingName("gb2312");
        pro = (ProgressBar) view.findViewById(R.id.pro);
        pro.setVisibility(View.VISIBLE);
        if (url1 != null) {
            if (isNetworkConnected()) {
                wv.loadUrl(url1);
                network(url1);
                rq.add(charsetStringRequest);
            } else {
                Cursor cursor = database.rawQuery("select * from htmlcache where url=?", new String[]{url1});
                if (cursor.getCount() == 1) {
                    cursor.moveToNext();
                    String sdurl = cursor.getString(cursor.getColumnIndex("sdurl"));
                    wv.loadUrl("file:///" + sdurl);
                } else {
                    Toast.makeText(getContext(), "不好意思，当前页面无内容", Toast.LENGTH_SHORT).show();
                }
            }
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (isNetworkConnected()) {
                        nowUrl = url;
                        view.loadUrl(url);
                        network(url);
                        rq.add(charsetStringRequest);
                    } else {
                        String[] s = url1.split("/");
                        String[] p = url.split("sywsyw/");
                        String ss = url1.replace(s[s.length - 1], p[p.length - 1]);
                        Cursor cursor = database.rawQuery("select * from htmlcache where url=?", new String[]{ss});
                        if (cursor.getCount() == 1) {
                            DetailFragment df = new DetailFragment();
                            String[] ww = ss.split("http://www.firearmsworld.net/");
                            df.setUrl(ww[1]);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, df).addToBackStack(null).commit();
//                            String sdurl=cursor.getString(cursor.getColumnIndex("sdurl"));
//                            view.loadUrl("file:///" + sdurl);
                        } else {
                            Toast.makeText(getContext(), "不好意思，当前页面无内容", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                }
            });
        }
        pro.setVisibility(View.INVISIBLE);
//        Log.d("syw1", "1" + url);

        return view;
    }

    private void network(final String url) {
        charsetStringRequest = new CharsetStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                content = response;
//                parseHtml();
                if (response != null) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Cursor cursor = database.rawQuery("select * from htmlcache where url=?", new String[]{url});
                                if (cursor.getCount() == 0) {
                                    String filePath = Environment.getExternalStorageDirectory() + "/sywsyw/";
                                    File file = new File(filePath);
                                    if (!file.exists()) {
                                        file.mkdir();
                                    }
                                    File file1 = new File(filePath + split(url) + ".html");
                                    FileWriter writer = new FileWriter(file1);
                                    String newContent = content.replace("charset=gb2312", "charset=utf-8");
                                    writer.write(newContent);
                                    writer.close();
                                    parseHtml();
                                    database.execSQL("insert into htmlcache values(null,?,?)", new String[]{url, file1.getAbsolutePath()});
                                    handler.sendEmptyMessage(0x1008);
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void parseHtml() {//将网页中的图片解析出来，并把图片保存到sd卡上
        Document doc = Jsoup.parse(content);
        Elements roots = doc.getElementsByTag("img");
        int i=0;
        for (final Element e : roots) {

            if (e.attr("src") != null) {
                String src = e.attr("src");
                if (src.contains("/images/")) {
                    final String[] w = src.split("/images/");
                    src = "http://www.firearmsworld.net/images/" + w[w.length - 1];
                    ImageRequest imageRequest = new ImageRequest(src, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            if (response != null) {
                                String filePath = Environment.getExternalStorageDirectory() + "/sywsyw/";
                                File file = new File(filePath);
                                if (!file.exists()) {
                                    file.mkdir();
                                }
                                File f = new File(filePath + w[w.length-1]);
                                FileOutputStream out = null;
                                try {
                                    out = new FileOutputStream(f);
                                    response.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                    out.flush();
                                    out.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
//                                Map<String,Object> map=new HashMap<>();
//                                map.put("name",w[w.length-1]);
//                                map.put("bit",response);
//                                bitMapList.add(map);
                            }
                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    rq.add(imageRequest);
                } else {
                    final String[] a = nowUrl.split("/");
                    String aa = nowUrl.replace(a[a.length - 1], src);

                    ImageRequest imageRequest = new ImageRequest(aa, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            if (response != null) {
                                String filePath = Environment.getExternalStorageDirectory() + "/sywsyw/";
                                File file = new File(filePath);
                                if (!file.exists()) {
                                    file.mkdir();
                                }
                                File f = new File(filePath + e.attr("src"));
                                FileOutputStream out = null;
                                try {
                                    out = new FileOutputStream(f);
                                    response.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                    out.flush();
                                    out.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
//                                Map<String,Object> map=new HashMap<>();
//                                map.put("name",e.attr("src"));
//                                map.put("bit",response);
//                                bitMapList.add(map);
//                                Log.d("new1", "11" + bitMapList);
                            }
                        }
                    }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    rq.add(imageRequest);
                }
                i++;
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("progress", (i * 100) /roots.size() + "%");
                bundle.putInt("data", roots.size());
                msg.setData(bundle);
                msg.what = 0x2323;
                handler.sendMessage(msg);
            }
        }
    }

    public void setUrl(String url) {
        StringBuilder sb = new StringBuilder("http://www.firearmsworld.net/");
        sb.append(url);
        this.url1 = sb.toString();
        this.nowUrl = sb.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNetworkConnected() {//检测网络连接
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public String split(String s) {//将网址字符串以/分开
        String[] qq = s.split("/");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < qq.length; i++) {
            builder.append(qq[i]);
        }
        return builder.toString();
    }

//    public void saveBitmap(List<Map<String, Object>> list) {
//        int i = 0;
//        for (Map<String, Object> map : list) {
//            String name = (String) map.get("name");
//            Bitmap bitmap = (Bitmap) map.get("bit");
//            String filePath = Environment.getExternalStorageDirectory() + "/sywsyw/";
//            File file = new File(filePath);
//            if (!file.exists()) {
//                file.mkdir();
//            }
//            File f = new File(filePath + name);
//            FileOutputStream out = null;
//            try {
//                out = new FileOutputStream(f);
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//                out.flush();
//                out.close();
//                i++;
//                Message msg = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("progress", (i * 100) / list.size() + "%");
//                bundle.putInt("data", list.size());
//                msg.setData(bundle);
//                msg.what = 0x2323;
//                handler.sendMessage(msg);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
