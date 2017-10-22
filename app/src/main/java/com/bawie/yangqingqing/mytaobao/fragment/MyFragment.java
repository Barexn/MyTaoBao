package com.bawie.yangqingqing.mytaobao.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.activity.ZhuxiaoActivity;

public class MyFragment extends Fragment {

    private SharedPreferences sp;
    private TextView shezhi;
    private TextView username;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my, null);
        //找到控件
        shezhi = (TextView) view.findViewById(R.id.tv_shezhi);
        username = (TextView) view.findViewById(R.id.tv_username);
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ZhuxiaoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

