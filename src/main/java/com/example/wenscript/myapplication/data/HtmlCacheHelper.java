package com.example.wenscript.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wenscript on 2016/3/23.
 */
public class HtmlCacheHelper extends SQLiteOpenHelper {
    public static final String CREATE_TABLE="create table htmlcache ("
            +" id integer primary key autoincrement,"
            +" url text,"
            +" sdurl text)";
    private Context context;
    public HtmlCacheHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
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
