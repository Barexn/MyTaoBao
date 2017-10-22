package com.bawie.yangqingqing.mytaobao.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bare on 2017/10/11.
 */

public class SousuoSqlite {

    private final SQLiteDatabase db;
    private String sql;

    public SousuoSqlite(Context context) {
        SousuoSqliteOpenHelper helper=new SousuoSqliteOpenHelper(context);
        db = helper.getWritableDatabase();
    }
    //添加
    public void add(String title){
        db.execSQL("insert into taaobao(title) values(?)",new String[]{title});
    }
    //查询
    public List<String> sele(){
        Cursor cursor=db.query("taobao",null,null,null,null,null,null);
        List<String> list=new ArrayList<>();
        while ((cursor.moveToNext())){
            String title=cursor.getString(cursor.getColumnIndex("title"));
            list.add(title);
        }
        return list;
    }
    public void dele(int no,String title){
        if (no==0){
            sql="delete from taobao";
        }else{
            sql="delete from taobao where title=(?)";
            db.execSQL(sql,new String[]{title});
        }
    }
}
