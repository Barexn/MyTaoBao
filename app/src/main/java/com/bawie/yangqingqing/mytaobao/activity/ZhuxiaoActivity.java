package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.bean.ZhuxiaoBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ZhuxiaoActivity extends AppCompatActivity{

    private TextView tv;
    private String key;
    private String username;
    private SharedPreferences preferences;
    private ZhuxiaoBean zhuxiaoBean;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuxiao);
        tv = (TextView) findViewById(R.id.zhuxiao_back);
        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        edit = preferences.edit();
        key = preferences.getString("key", "");
        username = preferences.getString("username", "");

        if (username.length()<=0){
            tv.setText("未登录");
            Intent intent=new Intent(ZhuxiaoActivity.this,MyActivity.class);
            startActivity(intent);
        }else{
            tv.setText("退出登录");

        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.putString("username","");
                edit.putString("key","");
                edit.commit();
                Intent intent = new Intent(ZhuxiaoActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }
}
