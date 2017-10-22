package com.bawie.yangqingqing.mytaobao.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bare on 2017/10/11.
 */

public class SousuoSqliteOpenHelper extends SQLiteOpenHelper{

    public SousuoSqliteOpenHelper(Context context) {
        super(context, "taobao.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table taobao(title varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
