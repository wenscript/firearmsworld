package com.example.wenscript.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.adapter.MoreAdapter;
import com.example.wenscript.myapplication.model.TopicModel;

import java.util.List;

/**
 * Created by wenscript on 2016/3/17.
 * 主题页面的more点击后形成的页面
 */
public class SpecialMoreListFragment extends Fragment {
    private List<TopicModel> tm;
    private ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_special_more,container,false);
        listView= (ListView) view.findViewById(R.id.list);
        MoreAdapter ma=new MoreAdapter(getContext(),tm);
        listView.setAdapter(ma);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailFragment df=new DetailFragment();
                df.setUrl(tm.get(position).getUrl());
                Log.d("syw1",tm.get(position).getUrl());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,df).addToBackStack(null).commit();
            }
        });
        return view;
    }
    public void setData(List<TopicModel> tm){
        this.tm=tm;
    }
}
