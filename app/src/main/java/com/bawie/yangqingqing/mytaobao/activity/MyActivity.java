package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bawie.yangqingqing.mytaobao.R;

public class MyActivity extends AppCompatActivity{

    private Button my_log;
    private Button my_reg;
    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity);
        //找到控件
        my_log = (Button) findViewById(R.id.my_log);
        my_reg = (Button) findViewById(R.id.my_reg);
        iv = (ImageView) findViewById(R.id.my_iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyActivity.this,SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //添加点击事件
        //跳转到登录页面
        my_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyActivity.this,LogActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //跳转到注册页面
        my_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyActivity.this,RegActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
