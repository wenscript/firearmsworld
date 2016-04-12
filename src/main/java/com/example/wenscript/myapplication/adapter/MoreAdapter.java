package com.example.wenscript.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.model.TopicModel;

import java.util.List;

/**
 * Created by wenscript on 2016/3/17.
 */
public class MoreAdapter extends BaseAdapter {
    private Context context;
    private List<TopicModel> tm;

    public MoreAdapter(Context context, List<TopicModel> tm) {
        this.context = context;
        this.tm = tm;
    }

    @Override
    public int getCount() {
        return tm.size();
    }

    @Override
    public Object getItem(int position) {
        return tm.get(position);
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
            convertView= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        String name=tm.get(position).getName();
        if(name.contains("font")){
            String[] ss=name.split("font");
            name=ss[ss.length-1];
        }
        viewHolder.textView.setText(name);
        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }
}
