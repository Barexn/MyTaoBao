package com.bawie.yangqingqing.mytaobao.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bawie.yangqingqing.mytaobao.R;
import com.bawie.yangqingqing.mytaobao.adapter.FenleiOneAdapter;
import com.bawie.yangqingqing.mytaobao.adapter.FenleiTwoAdapter;
import com.bawie.yangqingqing.mytaobao.bean.FenleiBean;
import com.bawie.yangqingqing.mytaobao.bean.UrlBean;
import com.bawie.yangqingqing.mytaobao.presenter.FenleiPresenter;
import com.bawie.yangqingqing.mytaobao.utils.UnicodeStreamUtils;
import com.bawie.yangqingqing.mytaobao.view.FenleiView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;


public class WeitaoFragment extends Fragment implements FenleiView{

    private ListView lv1;
    private ListView lv2;
    private FenleiPresenter presenter;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.arg1==1){
                FenleiBean.DatasBean.ClassListBean bean = (FenleiBean.DatasBean.ClassListBean) msg.obj;

                OkHttpUtils
                        .get()
                        .url(UrlBean.ER_ONE + "&gc_id=" + bean.getGc_id())
                        .build()
                        .execute(new StringCallback() {

                            @Override
                            public void onError(com.squareup.okhttp.Request request, Exception e) {

                            }

                            @Override
                            public void onResponse(String response) {

                                String str = UnicodeStreamUtils.getStr(response);
                                Gson gson = new Gson();
                                FenleiBean bean = gson.fromJson(str, FenleiBean.class);

                                lv2.setAdapter(new FenleiTwoAdapter(getActivity(),bean.getDatas().getClass_list()));

                            }
                        });
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_weitao,null);
        //找到控件
        lv1 = (ListView) view.findViewById(R.id.fenlei_lv1);
        lv2 = (ListView) view.findViewById(R.id.fenlei_lv2);

        presenter = new FenleiPresenter(this);
        presenter.initView1();
        return view;
    }

    @Override
    public void initView1(final List<FenleiBean.DatasBean.ClassListBean> list) {
        lv1.setAdapter(new FenleiOneAdapter(getActivity(),list));
        //二级默认为第一个
        OkHttpUtils
                .get()
                .url(UrlBean.ER_ONE + "&gc_id=" + list.get(0).getGc_id())
                .build()
                .execute(new StringCallback() {


                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {

                        String str = UnicodeStreamUtils.getStr(response);
                        Gson gson = new Gson();
                        FenleiBean bean = gson.fromJson(str, FenleiBean.class);

                        lv2.setAdapter(new FenleiTwoAdapter(getActivity(),bean.getDatas().getClass_list()));

                    }
                });

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message message = Message.obtain();
                message.obj=list.get(i);
                message.arg1=1;
                handler.sendMessage(message);

            }
        });
    }
}
