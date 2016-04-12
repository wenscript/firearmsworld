package com.example.wenscript.myapplication.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.fragment.DetailFragment;
import com.example.wenscript.myapplication.model.GunModel;
import com.example.wenscript.myapplication.model.MyTextView;

import java.util.List;

/**
 * Created by wenscript on 2016/2/22.
 */
public class HomePage_adapter extends BaseAdapter {
    private Context context;
    private List<List<GunModel>> list;
    private AppCompatActivity activity;

    public HomePage_adapter(Context context, List<List<GunModel>> list,AppCompatActivity activity) {
        this.context = context;
        this.list = list;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.homepage_item,parent,false);
            viewHolder.ll= (LinearLayout) convertView.findViewById(R.id.ll);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        for (int i=0;i<list.get(position).size();i++){
            final MyTextView mv=new MyTextView(context);
            mv.setUrl(list.get(position).get(i).getUrl());
            mv.setText(list.get(position).get(i).getName());
            viewHolder.ll.addView(mv);
            mv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putString("url",mv.getUrl());
                    DetailFragment df=new DetailFragment();
                    df.setUrl(mv.getUrl());
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,df,"homepagefragment").addToBackStack("homepagefragment").commit();
                }
            });
        }
        return convertView;
    }
    class ViewHolder{
        LinearLayout ll;
    }
}
