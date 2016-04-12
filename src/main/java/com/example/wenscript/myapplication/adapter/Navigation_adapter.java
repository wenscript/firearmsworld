package com.example.wenscript.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.model.Navigation_item;

import java.util.List;

/**
 * Created by wenscript on 2016/1/18.
 */
public class Navigation_adapter extends BaseAdapter {
    private Context context;
    private List<Navigation_item> list;

    public Navigation_adapter(Context context, List<Navigation_item> list) {
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
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.navigation_list_item,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(list.get(position).getImage());
        viewHolder.textView.setText(list.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
