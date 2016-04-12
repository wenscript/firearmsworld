package com.example.wenscript.myapplication.actvity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.wenscript.myapplication.BaseApplication;
import com.example.wenscript.myapplication.MainActivity;
import com.example.wenscript.myapplication.R;
import com.example.wenscript.myapplication.adapter.Navigation_adapter;
import com.example.wenscript.myapplication.fragment.HomePageFragment;
import com.example.wenscript.myapplication.fragment.TopicFragment;
import com.example.wenscript.myapplication.model.Navigation_item;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenscript on 2016/1/18.
 */
public class HomeActivity extends MainActivity {
    private ListView listView;
    private DrawerLayout dl;
    private List<Navigation_item> list;
    private Navigation_adapter adapter;
    private RequestQueue requestQueue;
    private SwitchButton switchButton;
    private BaseApplication ba;
    public static final String HOME_PAGE="http://www.firearmsworld.net/";
    public static final String TOPIC_URL="http://www.firearmsworld.net/smallarms.htm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        listView= (ListView) findViewById(R.id.listview);
        dl= (DrawerLayout) findViewById(R.id.drawerlayout);
        ba= (BaseApplication) getApplication();
        switchButton= (SwitchButton) findViewById(R.id.switchButton);
        switchButton.setTintColor(Color.RED);
        switchButton.setChecked(ba.isDayMode());
        switchButton.setText("on");
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean b=switchButton.isChecked();
                if (b){
                    changeToNight();
                }else {
                    changeToDay();
                }
                dl.closeDrawer(Gravity.LEFT);
                recreateOnResume();
            }
        });
//        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Toast.makeText(HomeActivity.this,""+isChecked,Toast.LENGTH_SHORT).show();
//                if (isChecked){
//                    changeToNight();
//                }else {
//                    changeToDay();
//                }
//                dl.closeDrawer(Gravity.LEFT);
//                recreateOnResume();
//            }
//        });
        init();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=list.get(position).getName();
                dl.closeDrawer(Gravity.LEFT);
                toolbar.setTitle(name);
                if (name.equals("首页")){
                    Bundle bundle=new Bundle();
                    bundle.putString("url",HOME_PAGE);
                    HomePageFragment hpf=new HomePageFragment();
                    hpf.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,hpf).commit();
                }
                if (name.equals("主题")){
                    Bundle bundle=new Bundle();
                    bundle.putString("url",TOPIC_URL);
                    TopicFragment tf=new TopicFragment();
                    tf.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,tf).commit();
                }
            }
        });

        toolbar.setTitle("枪炮世界");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void init() {//listview的数据初始化
        list=new ArrayList<>();
        list.add(new Navigation_item(R.mipmap.home_page,"首页"));
        list.add(new Navigation_item(R.mipmap.topic,"主题"));
        list.add(new Navigation_item(R.mipmap.equipments,"装备"));
        list.add(new Navigation_item(R.mipmap.base,"基础知识"));
        list.add(new Navigation_item(R.mipmap.recommend,"推荐网站"));
        adapter=new Navigation_adapter(this,list);
        requestQueue= Volley.newRequestQueue(this);
    }
}
