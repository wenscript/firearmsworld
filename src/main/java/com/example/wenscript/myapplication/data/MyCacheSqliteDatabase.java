package com.example.wenscript.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wenscript on 2016/3/19.
 * 该表存储了一些缓存信息
 */
public class MyCacheSqliteDatabase extends SQLiteOpenHelper {
    private Context context;
    public static final String CREATE_TABLE="create table cache ("
            +"id integer primary key autoincrement, "
            +"url text, "//该枪械的网址
            +"sdUrl text, "//缓存的目录
            +"name text)";//该枪械的名称

    public MyCacheSqliteDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
