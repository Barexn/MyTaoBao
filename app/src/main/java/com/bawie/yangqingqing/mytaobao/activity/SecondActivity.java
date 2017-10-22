package com.bawie.yangqingqing.mytaobao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.fragment.GouwucheFragment;
import com.bawie.yangqingqing.mytaobao.fragment.MyFragment;
import com.bawie.yangqingqing.mytaobao.fragment.ShouyeFragment;
import com.bawie.yangqingqing.mytaobao.fragment.WeitaoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */

public class SecondActivity extends AppCompatActivity implements  View.OnClickListener{

    private RadioButton shouye;
    private RadioButton weitao;
    private RadioButton xiaoxi;
    private RadioButton gouwuche;
    private RadioButton my;
    private RadioGroup radioGroup;
    private FragmentManager manager;
    private ShouyeFragment fragment1;
    private WeitaoFragment fragment2;
    //private XiaoxiFragment fragment3;
    private GouwucheFragment fragment4;
    private MyFragment fragment5;
    private FrameLayout frameLayout;
    private List<Fragment> list;
    private List<RadioButton> buttonList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //找到控件
        frameLayout = (FrameLayout) findViewById(R.id.second_frame);
        radioGroup = (RadioGroup) findViewById(R.id.second_rg);
        shouye = (RadioButton) findViewById(R.id.second_shouye);
        weitao = (RadioButton) findViewById(R.id.second_weitao);
        //xiaoxi = (RadioButton) findViewById(R.id.second_xiaoxi);
        gouwuche = (RadioButton) findViewById(R.id.second_gouwuche);
        my = (RadioButton) findViewById(R.id.second_my);

        shouye.setOnClickListener(this);
        weitao.setOnClickListener(this);
        //xiaoxi.setOnClickListener(this);
        gouwuche.setOnClickListener(this);
        my.setOnClickListener(this);
        init();
//        final SharedPreferences sp=getSharedPreferences("config",MODE_PRIVATE);
//        final SharedPreferences.Editor ed=sp.edit();
//        if (sp.getBoolean("f",true)){
//            return;
//        }else{
    }

    public void init(){
        list = new ArrayList<>();
        fragment1 = new ShouyeFragment();
        fragment2 = new WeitaoFragment();
        //fragment3 = new XiaoxiFragment();
        fragment4 = new GouwucheFragment();
        fragment5 = new MyFragment();
        list.add(fragment1);
        list.add(fragment2);
        //list.add(fragment3);
        list.add(fragment4);
        list.add(fragment5);
        manager = getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.second_frame,fragment1);
        transaction.add(R.id.second_frame,fragment2);
        //transaction.add(R.id.second_frame,fragment3);
        transaction.add(R.id.second_frame,fragment4);
        transaction.add(R.id.second_frame,fragment5);
        transaction.commit();
        isnoShow(0);
        buttonList = new ArrayList<>();
        buttonList.add(shouye);
        buttonList.add(weitao);
        //buttonList.add(xiaoxi);
        buttonList.add(gouwuche);
        buttonList.add(my);
        shouye.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.second_shouye:
                radioButton(0);
                isnoShow(0);
                break;
            case R.id.second_weitao:
                radioButton(1);
                isnoShow(1);
                break;
            case R.id.second_gouwuche:
                SharedPreferences preferences2 = getSharedPreferences("config",MODE_PRIVATE);
                String username2 = preferences2.getString("username", "");
                if (username2.length()<=0){
                    Intent intent = new Intent(SecondActivity.this, MyActivity.class);
                    startActivityForResult(intent,100);}
                else{
                    radioButton(2);
                    isnoShow(2);
                    }
                break;
            case R.id.second_my:
                SharedPreferences preferences1 = getSharedPreferences("config",MODE_PRIVATE);
                String username = preferences1.getString("username", "");
                if (username.length()<=0){
                    Intent intent = new Intent(SecondActivity.this, MyActivity.class);
                    startActivityForResult(intent,100);}else{
                radioButton(3);
                isnoShow(3);}
                break;
        }
    }
    public void radioButton(int index){
        for (int i = 0; i < buttonList.size(); i++) {
            RadioButton radioButton = buttonList.get(i);
            if(index == i){
                radioButton.setChecked(true);
            }else {
                radioButton.setChecked(false);
            }
        }
    }
    public void isnoShow(int index){
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            Fragment fragment = list.get(i);
            if(index == i){
                fragmentTransaction.show(fragment);
            }else {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commit();
    }
}
