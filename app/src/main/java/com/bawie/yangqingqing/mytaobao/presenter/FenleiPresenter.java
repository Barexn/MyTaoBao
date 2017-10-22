package com.bawie.yangqingqing.mytaobao.presenter;

import android.support.v4.app.FragmentActivity;

import com.bawie.yangqingqing.mytaobao.bean.FenleiBean;
import com.bawie.yangqingqing.mytaobao.bean.UrlBean;
import com.bawie.yangqingqing.mytaobao.model.FenleiModel;
import com.bawie.yangqingqing.mytaobao.view.FenleiView;
import com.google.gson.Gson;

public class FenleiPresenter {
    private FenleiModel model;
    private FenleiView view;

    public FenleiPresenter(FenleiView view) {
        this.model = new FenleiModel();
        this.view = view;
    }

    public void initView1(){
        model.getDate(UrlBean.ER_ONE,new FenleiModel.FenleiModelIs(){

            @Override
            public void isForBean(String str) {
                Gson gson=new Gson();
                FenleiBean bean=gson.fromJson(str,FenleiBean.class);
                view.initView1(bean.getDatas().getClass_list());
            }
        });
    }
}
