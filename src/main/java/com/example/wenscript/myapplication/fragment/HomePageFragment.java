package com.example.wenscript.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.adapter.HomePage_adapter;
import com.example.wenscript.myapplication.model.GunModel;
import com.example.wenscript.myapplication.util.CharsetStringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenscript on 2016/1/21.
 * 首页的内容
 */
public class HomePageFragment extends Fragment {
    private List<List<GunModel>> listGun;
    private RequestQueue requestQueue;
    private String url;
//    private TextView textView;
    private String request;
//    private StringRequest stringRequest;
    private CharsetStringRequest charsetStringRequest;
    private ProgressBar bar;
    private boolean flag=true;
    private String name="a";
    private ListView listView;
    private HomePage_adapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue= Volley.newRequestQueue(getActivity());
        listGun=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        url=bundle.getString("url");
        View view=inflater.inflate(R.layout.fragment_homepage,container,false);
//        textView= (TextView) view.findViewById(R.id.test);
        listView= (ListView) view.findViewById(R.id.listview);
        bar= (ProgressBar) view.findViewById(R.id.pro);
        bar.setVisibility(View.VISIBLE);
        network();
//        requestQueue.add(stringRequest);
        requestQueue.add(charsetStringRequest);
        return view;
    }

    private void network() {
//        stringRequest=new StringRequest(url, new Response.Listener<String>() {
        charsetStringRequest=new CharsetStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                request=response;
//                textView.setText(response);
                parseHtml();
//                Log.d("test1",listGun+"");
                adapter=new HomePage_adapter(getContext(),listGun, (AppCompatActivity) getActivity());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
    private void parseHtml(){
        Document doc=Jsoup.parse(request);
        Elements roots=doc.getElementsByTag("p");
        for (Element e:roots){
            if (e.attr("style").equals("margin-top: 5px; margin-bottom: 5px")){
                List<GunModel> list=new ArrayList<>();
                Elements elements=e.getElementsByTag("a");
                for (Element e1:elements){
                    GunModel gunModel=new GunModel(e1.text(),"",e1.attr("href"));
                    list.add(gunModel);
                }
                if (list!=null) {
                        listGun.add(list);
                    }
            }
        }
        bar.setVisibility(View.GONE);
    }
}
