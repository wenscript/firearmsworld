package com.example.wenscript.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.adapter.TopicAdapter;
import com.example.wenscript.myapplication.model.GunModel;
import com.example.wenscript.myapplication.model.TopicModel;
import com.example.wenscript.myapplication.util.CharsetStringRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenscript on 2016/3/5.
 */
public class TopicFragment extends Fragment {
    private String url;
    private String request;
    private RequestQueue rq;
    private CharsetStringRequest charsetStringRequest;
    private ListView special;//特辑列表
    private ListView production;//各公司的列表
    private ListView national;//各国家列表
    private List<TopicModel> specialList;
    private List<TopicModel> productionList;
    private List<TopicModel> nationalList;
    private TextView sMore;
    private TextView pMore;
    private TextView nMore;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq= Volley.newRequestQueue(getActivity());
        specialList=new ArrayList<>();
        productionList=new ArrayList<>();
        nationalList=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        url=bundle.getString("url");
        View view=inflater.inflate(R.layout.fragment_topic,container,false);
        special= (ListView) view.findViewById(R.id.spacial);
        production= (ListView) view.findViewById(R.id.production);
        national= (ListView) view.findViewById(R.id.national);
        sMore= (TextView) view.findViewById(R.id.specialMore);
        pMore= (TextView) view.findViewById(R.id.productionMore);
        nMore= (TextView) view.findViewById(R.id.nationalMore);
        network();
        rq.add(charsetStringRequest);
        sMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (specialList.size()>0){
                    SpecialMoreListFragment smlf=new SpecialMoreListFragment();
                    smlf.setData(specialList);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,smlf).addToBackStack(null).commit();
                }
            }
        });
        pMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productionList.size()>0){
                    ProductionMoreListFragment pmlf=new ProductionMoreListFragment();
                    pmlf.setData(productionList);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,pmlf).addToBackStack(null).commit();
                }
            }
        });
        nMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nationalList.size()>0){
                    NationalMoreListFragment nmlf=new NationalMoreListFragment();
                    nmlf.setData(nationalList);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,nmlf).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }

    private void network() {
        charsetStringRequest=new CharsetStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                request=response;
                parseHtml();
                List<TopicModel> l1=new ArrayList<>();
                List<TopicModel> l2=new ArrayList<>();
                List<TopicModel> l3=new ArrayList<>();
                for (int i=0;i<7;i++){
                    l1.add(specialList.get(i));
                }
                for (int i=0;i<8;i++){
                    l2.add(productionList.get(i));
                }
                for (int i=0;i<8;i++){
                    l3.add(nationalList.get(i));
                }
                special.setAdapter(new TopicAdapter(getContext(), l1));
                production.setAdapter(new TopicAdapter(getContext(),l2));
                national.setAdapter(new TopicAdapter(getContext(),l3));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void parseHtml() {
        Document doc= Jsoup.parse(request);
        Elements roots=doc.getElementsByTag("tr");
        for (Element e: roots){
            List<TopicModel> newList=new ArrayList<>();
            Elements roots1=e.getElementsByTag("a");
            if (roots1.size()!=0) {
                for (Element e1 : roots1) {
                    StringBuilder sb=new StringBuilder();
                    Elements fonts=e1.getElementsByTag("font");
                    if (fonts.size()!=0){
                        for(Element font:fonts){
                            sb.append(font.tag());
                        }
                    }
                    sb.append(e1.text());
                    TopicModel tm=new TopicModel(sb.toString(),e1.attr("href"));
                    newList.add(tm);
                }
            }
            if (newList.size()==1){
                productionList.add(newList.get(0));
            }
            if (newList.size()==2){
                productionList.add(newList.get(0));
                nationalList.add(newList.get(1));
            }
            if (newList.size()==3){
                specialList.add(newList.get(0));
                specialList.add(newList.get(1));
                nationalList.add(newList.get(2));
            }
        }
        specialList.add(productionList.remove(0));
        specialList.add(productionList.remove(0));
        specialList.add(productionList.remove(0));
        for (TopicModel tm:specialList){
            Log.d("heihei",tm.getName()+" "+tm.getUrl()+"\n");
        }
        for(TopicModel tm:productionList){
            Log.d("heihei",tm.getName()+" "+tm.getUrl()+"\n");
        }
        for (TopicModel tm:nationalList){
            Log.d("heihei",tm.getName()+" "+tm.getUrl()+"\n");
        }
    }
}
