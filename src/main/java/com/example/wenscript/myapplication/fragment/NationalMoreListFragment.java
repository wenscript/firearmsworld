package com.example.wenscript.myapplication.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.adapter.MoreAdapter;
import com.example.wenscript.myapplication.data.MyCacheSqliteDatabase;
import com.example.wenscript.myapplication.model.TopicModel;

import java.util.List;

/**
 * Created by wenscript on 2016/3/17.
 */
public class NationalMoreListFragment extends Fragment {
    private List<TopicModel> tm;
    private ListView lv;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0x1001){
                Toast.makeText(getContext(),"table is ok",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_nation_more,container,false);
        lv= (ListView) view.findViewById(R.id.list);
        MoreAdapter ma=new MoreAdapter(getContext(),tm);
        lv.setAdapter(ma);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailFragment df = new DetailFragment();
                df.setUrl(tm.get(position).getUrl());
                df.setName(tm.get(position).getName());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, df).addToBackStack(null).commit();
            }
        });
        if (tm.size()>0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MyCacheSqliteDatabase helper=new MyCacheSqliteDatabase(getContext(),"mycache.db",null,2);
                    SQLiteDatabase database=helper.getWritableDatabase();
                    for (TopicModel model:tm){

                        database.execSQL("insert into cache values(null,?,?,?)",new String[]{model.getUrl(),"hh",model.getName()});

                    }

                    handler.sendEmptyMessage(0x1001);
                }
            }).start();

        }
        return view;
    }
    public void setData(List<TopicModel> tm){
        this.tm=tm;
    }
}
