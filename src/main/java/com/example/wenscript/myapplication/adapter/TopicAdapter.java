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
 * Created by wenscript on 2016/3/16.
 */
public class TopicAdapter extends BaseAdapter {
    private Context context;
    private List<TopicModel> list;

    public TopicAdapter(Context context, List<TopicModel> list) {
        this.context = context;
        this.list = list;
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
        if (convertView==null) {
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        TextView textView;
    }
}
